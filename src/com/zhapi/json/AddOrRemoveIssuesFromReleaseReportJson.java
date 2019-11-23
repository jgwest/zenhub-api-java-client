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

import java.util.ArrayList;
import java.util.List;

public class AddOrRemoveIssuesFromReleaseReportJson extends AbstractJson {

	List<ReleaseReportIssueJson> add_issues = new ArrayList<>();
	List<ReleaseReportIssueJson> remove_issues = new ArrayList<>();

	public List<ReleaseReportIssueJson> getAdd_issues() {
		return add_issues;
	}

	public void setAdd_issues(List<ReleaseReportIssueJson> add_issues) {
		this.add_issues = add_issues;
	}

	public List<ReleaseReportIssueJson> getRemove_issues() {
		return remove_issues;
	}

	public void setRemove_issues(List<ReleaseReportIssueJson> remove_issues) {
		this.remove_issues = remove_issues;
	}

}
