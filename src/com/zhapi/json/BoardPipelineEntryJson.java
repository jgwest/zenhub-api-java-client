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

/**
 * Serializes to/from JSON, from the ZenHub API.
 */
public class BoardPipelineEntryJson extends AbstractJson {

	private String id;
	private String name;
	private List<BoardPipelineIssueEntryJson> issues = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BoardPipelineIssueEntryJson> getIssues() {
		return issues;
	}

	public void setIssues(List<BoardPipelineIssueEntryJson> issues) {
		this.issues = issues;
	}

}
