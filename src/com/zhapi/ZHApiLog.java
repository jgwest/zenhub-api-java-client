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

/** Simple bare bones logging class. */
public class ZHApiLog {

	private static final ZHApiLog instance = new ZHApiLog();

	private ZHApiLog() {
	}

	public static ZHApiLog getInstance() {
		return instance;
	}

	public void out() {

		System.out.println("");
	}

	public void out(String str) {
		System.out.println(str);

	}

	public void err() {
		System.err.println("");
	}

	public void err(String str) {
		System.err.println(str);
	}

}
