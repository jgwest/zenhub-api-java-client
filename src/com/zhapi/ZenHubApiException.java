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

/**
 * Generic unchecked exception for exceptions generated by this library, or
 * wrapped exceptions from called libraries.
 */
public class ZenHubApiException extends RuntimeException {

	private static final long serialVersionUID = -3307260048376204711L;

	public ZenHubApiException(String msg) {
		super(msg);
	}

	private ZenHubApiException(Throwable t) {
		super(t);
	}

	private ZenHubApiException(String msg, Throwable t) {
		super(msg, t);
	}

	public static ZenHubApiException createFromThrowable(Throwable t) {
		return new ZenHubApiException(t);
	}

	public static ZenHubApiException createFromThrowable(String msg, Throwable t) {
		return new ZenHubApiException(msg, t);
	}

}