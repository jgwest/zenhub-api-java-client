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
import com.zhapi.json.PipelineJson;
import com.zhapi.json.PlusOneEntryJson;

/**
 * Returned by the IssuesService class. Serializes to/from JSON, from the ZenHub
 * API.
 */
public class GetIssueDataResponseJson extends AbstractJson {

	private List<PlusOneEntryJson> plus_ones = new ArrayList<>();

	private EstimateJson estimate;

	private List<PipelineJson> pipelines;
	private PipelineJson pipeline;
	private boolean is_epic;

	public GetIssueDataResponseJson() {
	}

	public List<PlusOneEntryJson> getPlus_ones() {
		return plus_ones;
	}

	public void setPlus_ones(List<PlusOneEntryJson> plus_ones) {
		this.plus_ones = plus_ones;
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

	public boolean isIs_epic() {
		return is_epic;
	}

	public void setIs_epic(boolean is_epic) {
		this.is_epic = is_epic;
	}

	public List<PipelineJson> getPipelines() {
		return pipelines;
	}

	public void setPipelines(List<PipelineJson> pipelines) {
		this.pipelines = pipelines;
	}

}
