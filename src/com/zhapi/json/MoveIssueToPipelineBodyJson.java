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
 * Used by the IssuesService class. Serializes to/from JSON, from the ZenHub
 * API.
 */
public class MoveIssueToPipelineBodyJson extends AbstractJson {

	String pipeline_id;
	Object position; // String or Number

	public String getPipeline_id() {
		return pipeline_id;
	}

	public void setPipeline_id(String pipeline_id) {
		this.pipeline_id = pipeline_id;
	}

	public Object getPosition() {
		return position;
	}

	public void setPosition(Object position) {
		this.position = position;
	}

}
