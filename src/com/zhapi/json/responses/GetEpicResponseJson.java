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

import com.zhapi.json.AbstractJson;
import com.zhapi.json.EstimateJson;
import com.zhapi.json.GetEpicIssueEntryJson;
import com.zhapi.json.PipelineJson;

/**
 * Returned by the EpicsService class. Serializes to/from JSON, from the ZenHub
 * API.
 */
public class GetEpicResponseJson extends AbstractJson {
	private EstimateJson total_epic_estimates;
	private EstimateJson estimate;
	private PipelineJson pipeline;
	private List<PipelineJson> pipelines = new ArrayList<>();
	private List<GetEpicIssueEntryJson> issues = new ArrayList<>();

	public EstimateJson getTotal_epic_estimates() {
		return total_epic_estimates;
	}

	public void setTotal_epic_estimates(EstimateJson total_epic_estimates) {
		this.total_epic_estimates = total_epic_estimates;
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

	public List<GetEpicIssueEntryJson> getIssues() {
		return issues;
	}

	public void setIssues(List<GetEpicIssueEntryJson> issues) {
		this.issues = issues;
	}

	public List<PipelineJson> getPipelines() {
		return pipelines;
	}

	public void setPipelines(List<PipelineJson> pipelines) {
		this.pipelines = pipelines;
	}

}
