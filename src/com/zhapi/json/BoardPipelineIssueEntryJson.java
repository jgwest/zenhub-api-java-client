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
public class BoardPipelineIssueEntryJson extends AbstractJson {

	private int issue_number;
	private EstimateJson estimate;
	private int position;
	private boolean is_epic;

	public int getIssue_number() {
		return issue_number;
	}

	public void setIssue_number(int issue_number) {
		this.issue_number = issue_number;
	}

	public EstimateJson getEstimate() {
		return estimate;
	}

	public void setEstimate(EstimateJson estimate) {
		this.estimate = estimate;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isIs_epic() {
		return is_epic;
	}

	public void setIs_epic(boolean is_epic) {
		this.is_epic = is_epic;
	}

}
