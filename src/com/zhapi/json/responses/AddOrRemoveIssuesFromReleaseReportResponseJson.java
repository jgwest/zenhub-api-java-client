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

package com.zhapi.json.responses;

import java.util.ArrayList;
import java.util.List;

import com.zhapi.json.ReleaseReportIssueJson;

public class AddOrRemoveIssuesFromReleaseReportResponseJson {

	List<ReleaseReportIssueJson> added = new ArrayList<>();
	List<ReleaseReportIssueJson> removed = new ArrayList<>();

	public List<ReleaseReportIssueJson> getAdded() {
		return added;
	}

	public void setAdded(List<ReleaseReportIssueJson> added) {
		this.added = added;
	}

	public List<ReleaseReportIssueJson> getRemoved() {
		return removed;
	}

	public void setRemoved(List<ReleaseReportIssueJson> removed) {
		this.removed = removed;
	}

}
