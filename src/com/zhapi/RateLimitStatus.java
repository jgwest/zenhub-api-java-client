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

import java.util.Date;

/**
 * Contains status from the ZenHub API on how close are we to exhausting our
 * requests under the ZenHub rate limit.
 */
public class RateLimitStatus {
	private final int rateLimit_limit;
	private final int rateLimit_used;
	private final Date rateLimit_reset;

	public RateLimitStatus(int rateLimit_limit, int rateLimit_used, long rateLimit_reset) {
		this.rateLimit_limit = rateLimit_limit;
		this.rateLimit_used = rateLimit_used;
		this.rateLimit_reset = new Date(rateLimit_reset * 1000); // convert from epoch seconds to epoch msecs
	}

	public int getRateLimit_limit() {
		return rateLimit_limit;
	}

	public int getRateLimit_used() {
		return rateLimit_used;
	}

	public Date getRateLimit_reset() {
		return rateLimit_reset;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("rateLimit_limit: ");
		sb.append(rateLimit_limit);
		sb.append("  rateLimit_used: ");
		sb.append(rateLimit_used);
		sb.append("  rateLimit_reset: ");
		sb.append(rateLimit_reset);
		return sb.toString();

	}

}
