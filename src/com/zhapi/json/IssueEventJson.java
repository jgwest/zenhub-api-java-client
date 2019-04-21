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

import java.util.Date;

/**
 * Serializes to/from JSON, from the ZenHub API.
 */
public class IssueEventJson extends AbstractJson {

	private long user_id;
	private String type;
	private Date created_at;

	private String workspace_id;

	private EstimateJson from_estimate;
	private EstimateJson to_estimate;

	private PipelineJson from_pipeline;
	private PipelineJson to_pipeline;

	public IssueEventJson() {
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public EstimateJson getFrom_estimate() {
		return from_estimate;
	}

	public void setFrom_estimate(EstimateJson from_estimate) {
		this.from_estimate = from_estimate;
	}

	public EstimateJson getTo_estimate() {
		return to_estimate;
	}

	public void setTo_estimate(EstimateJson to_estimate) {
		this.to_estimate = to_estimate;
	}

	public PipelineJson getFrom_pipeline() {
		return from_pipeline;
	}

	public void setFrom_pipeline(PipelineJson from_pipeline) {
		this.from_pipeline = from_pipeline;
	}

	public PipelineJson getTo_pipeline() {
		return to_pipeline;
	}

	public void setTo_pipeline(PipelineJson to_pipeline) {
		this.to_pipeline = to_pipeline;
	}

	public String getWorkspace_id() {
		return workspace_id;
	}

	public void setWorkspace_id(String workspace_id) {
		this.workspace_id = workspace_id;
	}

}
