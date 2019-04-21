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

package com.zhapi.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.zhapi.ZHUtil;
import com.zhapi.ZenHubApiException;
import com.zhapi.ZenHubClient;

/**
 * Utility methods used by the test class(es).
 * 
 * In order to run these tests, you will need to generate an API token using
 * your GitHub ID, at https://app.zenhub.com/dashboard/tokens
 * 
 * When running the test, use the parameter: -Dzenhubapikey=(your key api)
 * 
 * Instead of passing the above parameter, you may instead create a file at
 * ~/.zenhubapitests/test.properties with the following line:
 * 
 * zenhubapikey=(your API key)
 * 
 */
public abstract class AbstractTest {

	// https://github.com/OpenLiberty/open-liberty/
	protected final long openLibertyRepoId = 103694377;

	protected static ZenHubClient getClient() {
		File f = new File(new File(System.getProperty("user.home"), ".zenhubapitests"), "test.properties");

		String apiKey = null;

		if (f.exists()) {
			Properties p = new Properties();
			try {
				p.load(new FileInputStream(f));
			} catch (IOException e) {
				e.printStackTrace();
			}
			apiKey = (String) p.getOrDefault("zenhubapikey", null);
		}

		if (apiKey == null) {
			apiKey = System.getProperty("zenhubapikey");
		}

		if (apiKey == null) {
			// Generate a token in the API Tokens section of your ZenHub Dashboard
			// (https://app.zenhub.com/dashboard/tokens)
			throw new ZenHubApiException(
					"ZenHub API Key not found, specify -Dzenhubapikey=(your key api) with your key. Generate a key at https://app.zenhub.com/dashboard/tokens");
		}

		ZenHubClient client = new ZenHubClient("https://api.zenhub.io", apiKey);
		return client;
	}

	void waitForPass(long timeToWaitInSeconds, Runnable r) {
		waitForPass(timeToWaitInSeconds, false, r);

	}

	void waitForPass(long timeToWaitInSeconds, boolean showExceptions, Runnable r) {

		long expireTimeInNanos = System.nanoTime() + TimeUnit.NANOSECONDS.convert(timeToWaitInSeconds, TimeUnit.SECONDS);

		Throwable lastThrowable = null;

		int delay = 50;

		while (System.nanoTime() < expireTimeInNanos) {

			try {
				r.run();
				return;
			} catch (Throwable t) {
				if (showExceptions) {
					t.printStackTrace();
				}

				lastThrowable = t;
				ZHUtil.sleep(delay);
				delay *= 1.5;
				if (delay >= 1000) {
					delay = 1000;
				}
			}

		}

		if (lastThrowable instanceof RuntimeException) {
			throw (RuntimeException) lastThrowable;
		} else {
			throw (Error) lastThrowable;
		}

	}
}
