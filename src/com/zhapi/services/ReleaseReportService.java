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

package com.zhapi.services;

import java.util.Arrays;
import java.util.List;

import com.zhapi.ApiResponse;
import com.zhapi.ZenHubClient;
import com.zhapi.json.AddOrRemoveIssuesFromReleaseReportJson;
import com.zhapi.json.ReleaseReportIssueJson;
import com.zhapi.json.ReleaseReportJson;
import com.zhapi.json.responses.AddOrRemoveIssuesFromReleaseReportResponseJson;

/**
 * Implements the 'Get Board Data for a Repository' API from
 * https://github.com/ZenHubIO/API#board.
 */
public class ReleaseReportService {

	private final ZenHubClient zenhubClient;

	public ReleaseReportService(ZenHubClient client) {
		this.zenhubClient = client;
	}

	public ApiResponse<ReleaseReportJson> getReleaseReport(String releaseId) {

		String url = "/p1/reports/release/" + releaseId;

		ApiResponse<ReleaseReportJson> response = zenhubClient.get(url, ReleaseReportJson.class);

		return new ApiResponse<ReleaseReportJson>((ReleaseReportJson) response.getResponse(), response.getRateLimitStatus(),
				response.getResponseBody());

	}

	public ApiResponse<List<ReleaseReportJson>> getReleaseReportsForRepo(long repoId) {
		String url = "/p1/repositories/" + repoId + "/reports/releases";

		ApiResponse<ReleaseReportJson[]> response = zenhubClient.get(url, ReleaseReportJson[].class);

		return new ApiResponse<List<ReleaseReportJson>>(Arrays.asList(response.getResponse()), response.getRateLimitStatus(),
				response.getResponseBody());

	}

	public ApiResponse<List<ReleaseReportIssueJson>> getAllReleaseReportIssues(String releaseId) {
		String url = "/p1/reports/release/" + releaseId + "/issues";

		ApiResponse<ReleaseReportIssueJson[]> response = zenhubClient.get(url, ReleaseReportIssueJson[].class);

		return new ApiResponse<List<ReleaseReportIssueJson>>(Arrays.asList(response.getResponse()), response.getRateLimitStatus(),
				response.getResponseBody());

	}

	public ApiResponse<AddOrRemoveIssuesFromReleaseReportResponseJson> addOrRemoveIssuesFromReleaseReport(String release_id,
			List<ReleaseReportIssueJson> toAdd, List<ReleaseReportIssueJson> toRemove) {

		String url = "/p1/reports/release/" + release_id + "/issues";

		AddOrRemoveIssuesFromReleaseReportJson body = new AddOrRemoveIssuesFromReleaseReportJson();
		body.getAdd_issues().addAll(toAdd);
		body.getRemove_issues().addAll(toRemove);

		ApiResponse<AddOrRemoveIssuesFromReleaseReportResponseJson> response = zenhubClient.patch(url, body,
				AddOrRemoveIssuesFromReleaseReportResponseJson.class);

		return new ApiResponse<AddOrRemoveIssuesFromReleaseReportResponseJson>(
				(AddOrRemoveIssuesFromReleaseReportResponseJson) response.getResponse(), response.getRateLimitStatus(),
				response.getResponseBody());

	}
}
