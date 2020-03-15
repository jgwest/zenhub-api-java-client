/*
 * Copyright 2019, 2020 Jonathan West
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
*/

package com.zhapi;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is responsible for initiating connections to the ZenHub HTTP API,
 * and parsing the given result into the expected JSON response.
 */
public class ZenHubClient {

	private final String apiKey;
	private final String baseApiUrl; // Will not end with a slash

	private static final String HEADER_AUTHORIZATION = "X-Authentication-Token";

	private static final String HTTPS_PREFIX = "https://";

	private final static boolean DEBUG = false;

	private static final ZHApiLog log = ZHApiLog.getInstance();

	private final boolean allowUntrustedCerts;

	public ZenHubClient(String apiUrl, String apiKey, boolean allowUntrustedCerts) {

		if (apiUrl.startsWith(HTTPS_PREFIX)) {
			// Strip https:// prefix, if preseent.
			apiUrl = apiUrl.substring(HTTPS_PREFIX.length());
		}

		this.apiKey = apiKey;
		this.baseApiUrl = ensureDoesNotEndWithSlash(apiUrl);
		this.allowUntrustedCerts = allowUntrustedCerts;
	}

	public ZenHubClient(String apiUrl, String apiKey) {
		this(apiUrl, apiKey, false);
	}

	public <T> ApiResponse<T> get(String apiUrl, Class<T> clazz) {

		ApiResponse<String> body = getRequest(apiUrl);

		if (DEBUG) {
			log.out(body.getResponse());
		}

		ObjectMapper om = new ObjectMapper();
		try {
			T parsed = om.readValue(body.getResponse(), clazz);
			return new ApiResponse<T>(parsed, body.getRateLimitStatus(), body.getResponse());
		} catch (Exception e) {
			System.err.println("Unable to parse: " + body.getResponse());
			throw ZenHubApiException.createFromThrowable(e);
		}

	}

	public <T> ApiResponse<T> post(String apiUrl, Object postBody, Class<T> clazz) {

		ApiResponse<String> body = requestWithBody(apiUrl, "POST", postBody);

		if (DEBUG) {
			log.out(body.getResponse());
		}

		if (clazz == null) {
			return new ApiResponse<T>(null, body.getRateLimitStatus(), body.getResponse());
		}

		ObjectMapper om = new ObjectMapper();
		try {
			T parsed = om.readValue(body.getResponse(), clazz);
			return new ApiResponse<T>(parsed, body.getRateLimitStatus(), body.getResponse());
		} catch (Exception e) {
			System.err.println("Unable to parse: " + body.getResponse());
			throw ZenHubApiException.createFromThrowable(e);
		}

	}

	public <T> ApiResponse<T> patch(String apiUrl, Object patchBody, Class<T> clazz) {

		// Because the HTTP URLConnection API doesn't support PATCH, we have to pull in
		// Apache HTTP Client :|
		try {
			return patchInner(apiUrl, patchBody, clazz);
		} catch (IOException e) {
			throw ZenHubApiException.createFromThrowable(e);
		}
	}

	private <T> ApiResponse<T> patchInner(String apiUrl, Object patchBody, Class<T> clazz)
			throws ClientProtocolException, IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		ObjectMapper om = new ObjectMapper();

		apiUrl = ensureDoesNotBeginsWithSlash(apiUrl);

		HttpPatch httpPatch = new HttpPatch("https://" + this.baseApiUrl + "/" + apiUrl);
		httpPatch.setHeader("Content-Type", "application/json");
		httpPatch.setHeader(HEADER_AUTHORIZATION, apiKey);

		String requestBody = om.writeValueAsString(patchBody);

		httpPatch.setEntity(new StringEntity(requestBody));
		CloseableHttpResponse response = httpclient.execute(httpPatch);

		try {
			HttpEntity entity = response.getEntity();
			String responseBody = ZHUtil.readStringFromInputStream(entity.getContent());
			EntityUtils.consume(entity);

			int statusCode = response.getStatusLine().getStatusCode();
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new ZenHubApiException("Request failed - HTTP Code: " + statusCode + "  body: " + responseBody);
			}

			T resultObj = om.readValue(responseBody, clazz);

			int rateLimit_limit = Integer.parseInt(response.getFirstHeader("X-RateLimit-Limit").getValue());
			int rateLimit_used = Integer.parseInt(response.getFirstHeader("X-RateLimit-Used").getValue());
			int rateLimit_reset = Integer.parseInt(response.getFirstHeader("X-RateLimit-Reset").getValue());

			RateLimitStatus rateLimit = new RateLimitStatus(rateLimit_limit, rateLimit_used, rateLimit_reset);

			return new ApiResponse<T>(resultObj, rateLimit, responseBody);

		} finally {
			response.close();
		}
	}

	private ApiResponse<String> requestWithBody(String requestUrlParam, String method, Object reqBody) {

		requestUrlParam = ensureDoesNotBeginsWithSlash(requestUrlParam);

		HttpURLConnection httpRequest;
		try {
			httpRequest = createConnection("https://" + this.baseApiUrl + "/" + requestUrlParam, method, apiKey,
					allowUntrustedCerts);

			if (reqBody != null) {
				httpRequest.setRequestProperty("Content-Type", "application/json");
				httpRequest.setDoOutput(true);

				DataOutputStream payloadStream = new DataOutputStream(httpRequest.getOutputStream());
				payloadStream.write(reqBody.toString().getBytes());
			}

			final int code = httpRequest.getResponseCode();

			int rateLimit_limit = httpRequest.getHeaderFieldInt("X-RateLimit-Limit", 0);
			int rateLimit_used = httpRequest.getHeaderFieldInt("X-RateLimit-Used", 0);
			long rateLimit_reset = httpRequest.getHeaderFieldLong("X-RateLimit-Reset", 0);

			RateLimitStatus rateLimit = new RateLimitStatus(rateLimit_limit, rateLimit_used, rateLimit_reset);

			InputStream is = httpRequest.getInputStream();

			String body = readBody(is);

			if (code != 200) {
				throw new ZenHubApiException("Request failed - HTTP Code: " + code + "  body: " + body);
			}

			return new ApiResponse<String>(body, rateLimit, body);

		} catch (IOException e) {
			throw ZenHubApiException.createFromThrowable(e);
		}

	}

	private ApiResponse<String> getRequest(String requestUrlParam) {

		requestUrlParam = ensureDoesNotBeginsWithSlash(requestUrlParam);

		HttpURLConnection httpRequest;
		try {
			httpRequest = createConnection("https://" + this.baseApiUrl + "/" + requestUrlParam, "GET", apiKey,
					allowUntrustedCerts);
			final int code = httpRequest.getResponseCode();

			int rateLimit_limit = httpRequest.getHeaderFieldInt("X-RateLimit-Limit", 0);
			int rateLimit_used = httpRequest.getHeaderFieldInt("X-RateLimit-Used", 0);
			long rateLimit_reset = httpRequest.getHeaderFieldLong("X-RateLimit-Reset", 0);

			RateLimitStatus rateLimit = new RateLimitStatus(rateLimit_limit, rateLimit_used, rateLimit_reset);

			InputStream is = httpRequest.getInputStream();

			String body = readBody(is);

			if (code != 200) {
				throw new ZenHubApiException("Request failed - HTTP Code: " + code + "  body: " + body);
			}

			return new ApiResponse<String>(body, rateLimit, body);

		} catch (IOException e) {
			throw ZenHubApiException.createFromThrowable(e);
		}

	}

	private static HttpURLConnection createConnection(String uri, String method, String authorization, boolean allowUntrusted)
			throws IOException {

		URL url = new URL(uri);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		if (connection instanceof HttpsURLConnection) {
			HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
			httpsConnection.setSSLSocketFactory(generateSslContext(allowUntrusted).getSocketFactory());

			HostnameVerifier hostnameVerifier = new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			((HttpsURLConnection) connection).setHostnameVerifier(hostnameVerifier);

		}
		connection.setRequestMethod(method);

		if (authorization != null) {
			connection.setRequestProperty(HEADER_AUTHORIZATION, authorization);
		}

		return connection;
	}

	private static String readBody(InputStream is) {
		StringBuilder sb = new StringBuilder();
		int c;
		byte[] barr = new byte[1024 * 64];
		try {
			while (-1 != (c = is.read(barr))) {
				sb.append(new String(barr, 0, c));
			}
		} catch (IOException e) {
			throw ZenHubApiException.createFromThrowable(e);
		}
		return sb.toString();
	}

	private static SSLContext generateSslContext(boolean allowUntrusted) {
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("TLS");

			if (!allowUntrusted) {
				sslContext.init(null, null, new java.security.SecureRandom());
			} else {
				sslContext.init(null, new TrustManager[] { new X509TrustManager() {
					public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					}

					public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					}

					public X509Certificate[] getAcceptedIssuers() {

						return new X509Certificate[0];
					}

				} }, new java.security.SecureRandom());

			}

		} catch (Exception e) {
			ZHUtil.throwAsUnchecked(e);
		}

		return sslContext;
	}

	private static String ensureDoesNotBeginsWithSlash(String input) {
		while (input.startsWith("/")) {
			input = input.substring(1);
		}

		return input;
	}

	@SuppressWarnings("unused")
	private static String ensureEndsWithSlash(String input) {
		if (!input.endsWith("/")) {
			input = input + "/";
		}
		return input;
	}

	private static String ensureDoesNotEndWithSlash(String input) {
		while (input.endsWith("/")) {
			input = input.substring(0, input.length() - 1);
		}

		return input;

	}

}
