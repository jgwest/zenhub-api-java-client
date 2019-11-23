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

public class ReleaseReportIssueJson extends AbstractJson {

	long repo_id;
	long issue_number;

	public ReleaseReportIssueJson(long repo_id, long issue_number) {
		this.repo_id = repo_id;
		this.issue_number = issue_number;
	}

	public ReleaseReportIssueJson() {
	}

	public long getRepo_id() {
		return repo_id;
	}

	public void setRepo_id(long repo_id) {
		this.repo_id = repo_id;
	}

	public long getIssue_number() {
		return issue_number;
	}

	public void setIssue_number(long issue_number) {
		this.issue_number = issue_number;
	}

}
