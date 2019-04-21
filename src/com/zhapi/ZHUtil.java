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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Miscellaneous static utility methods. */
public class ZHUtil {

	public static void throwAsUnchecked(Throwable t) {

		if (t instanceof RuntimeException) {
			throw (RuntimeException) t;
		} else if (t instanceof Error) {
			throw (Error) t;
		} else {
			throw new RuntimeException(t);
		}

	}

	public static String toPrettyString(Object j) {
		ObjectMapper om = new ObjectMapper();

		try {
			om.setSerializationInclusion(Include.NON_NULL);
			return om.writerWithDefaultPrettyPrinter().writeValueAsString(j);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

	}

	public static void sleep(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			throwAsUnchecked(e);
		}
	}

}
