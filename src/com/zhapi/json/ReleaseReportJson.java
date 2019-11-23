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
import java.util.Date;
import java.util.List;

/**
 * Serializes to/from JSON, from the ZenHub API.
 */
public class ReleaseReportJson extends AbstractJson {

	String release_id;
	String title;
	String description;
	Date start_date;
	Date desired_end_date;
	Date created_at;
	Date closed_at;
	String state;
	List<Long> repositories = new ArrayList<>();

	public String getRelease_id() {
		return release_id;
	}

	public void setRelease_id(String release_id) {
		this.release_id = release_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getDesired_end_date() {
		return desired_end_date;
	}

	public void setDesired_end_date(Date desired_end_date) {
		this.desired_end_date = desired_end_date;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getClosed_at() {
		return closed_at;
	}

	public void setClosed_at(Date closed_at) {
		this.closed_at = closed_at;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Long> getRepositories() {
		return repositories;
	}

	public void setRepositories(List<Long> repositories) {
		this.repositories = repositories;
	}

}
