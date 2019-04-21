/*
 * Copyright 2019 Jonathan West
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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

	public ZenHubClient(String apiUrl, String apiKey) {

		if (apiUrl.startsWith(HTTPS_PREFIX)) {
			// Strip https:// prefix, if preseent.
			apiUrl = apiUrl.substring(HTTPS_PREFIX.length());
		}

		this.apiKey = apiKey;
		this.baseApiUrl = ensureDoesNotEndWithSlash(apiUrl);
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

	private ApiResponse<String> getRequest(String requestUrlParam) {

		requestUrlParam = ensureDoesNotBeginsWithSlash(requestUrlParam);

		HttpURLConnection httpRequest;
		try {
			httpRequest = createConnection("https://" + this.baseApiUrl + "/" + requestUrlParam, "GET", apiKey);
			final int code = httpRequest.getResponseCode();

			int rateLimit_limit = httpRequest.getHeaderFieldInt("X-RateLimit-Limit", 0);
			int rateLimit_used = httpRequest.getHeaderFieldInt("X-RateLimit-Used", 0);
			long rateLimit_reset = httpRequest.getHeaderFieldLong("X-RateLimit-Reset", 0);

			RateLimitStatus rateLimit = new RateLimitStatus(rateLimit_limit, rateLimit_used, rateLimit_reset);

			InputStream is = httpRequest.getInputStream();

			String body = getBody(is);

			if (code != 200) {
				throw new ZenHubApiException("Request failed - HTTP Code: " + code + "  body: " + body);
			}

			return new ApiResponse<String>(body, rateLimit, body);

		} catch (IOException e) {
			throw ZenHubApiException.createFromThrowable(e);
		}

	}

	private static HttpURLConnection createConnection(String uri, String method, String authorization) throws IOException {

		URL url = new URL(uri);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		if (connection instanceof HttpsURLConnection) {
			HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
			httpsConnection.setSSLSocketFactory(generateSslContext(false).getSocketFactory());
		}
		connection.setRequestMethod(method);

		if (authorization != null) {
			connection.setRequestProperty(HEADER_AUTHORIZATION, authorization);
		}

		return connection;
	}

	private static String getBody(InputStream is) {
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
