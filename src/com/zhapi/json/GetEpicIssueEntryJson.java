
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
public class GetEpicIssueEntryJson extends AbstractJson {

	private int issue_number;
	private boolean is_epic;
	private long repo_id;
	private EstimateJson estimate;
	private PipelineJson pipeline;
	private List<PipelineJson> pipelines = new ArrayList<>();

	public int getIssue_number() {
		return issue_number;
	}

	public void setIssue_number(int issue_number) {
		this.issue_number = issue_number;
	}

	public boolean isIs_epic() {
		return is_epic;
	}

	public void setIs_epic(boolean is_epic) {
		this.is_epic = is_epic;
	}

	public long getRepo_id() {
		return repo_id;
	}

	public void setRepo_id(long repo_id) {
		this.repo_id = repo_id;
	}

	public EstimateJson getEstimate() {
		return estimate;
	}

	public void setEstimate(EstimateJson estimate) {
		this.estimate = estimate;
	}

	public PipelineJson getPipeline() {
		return pipeline;
	}

	public void setPipeline(PipelineJson pipeline) {
		this.pipeline = pipeline;
	}

	public List<PipelineJson> getPipelines() {
		return pipelines;
	}

	public void setPipelines(List<PipelineJson> pipelines) {
		this.pipelines = pipelines;
	}

}
