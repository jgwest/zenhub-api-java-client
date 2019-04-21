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

package com.zhapi.json;

/**
 * Serializes to/from JSON, from the ZenHub API.
 */
public class DependencyBlockingOrBlockedJson extends AbstractJson {

	private int issue_number;
	private long repo_id;

	public int getIssue_number() {
		return issue_number;
	}

	public void setIssue_number(int issue_number) {
		this.issue_number = issue_number;
	}

	public long getRepo_id() {
		return repo_id;
	}

	public void setRepo_id(long repo_id) {
		this.repo_id = repo_id;
	}

}
