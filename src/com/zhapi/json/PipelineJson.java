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
public class PipelineJson extends AbstractJson {

	private String workspace_id;
	private String pipeline_id;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWorkspace_id() {
		return workspace_id;
	}

	public void setWorkspace_id(String workspace_id) {
		this.workspace_id = workspace_id;
	}

	public String getPipeline_id() {
		return pipeline_id;
	}

	public void setPipeline_id(String pipeline_id) {
		this.pipeline_id = pipeline_id;
	}

}
