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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zhapi.ApiResponse;
import com.zhapi.ZenHubClient;
import com.zhapi.json.IssueEventJson;
import com.zhapi.json.responses.GetIssueDataResponseJson;

/**
 * Implements 'Get Issue Data' and 'Get Issue Events' API from
 * https://github.com/ZenHubIO/API#issues
 */
public class IssuesService {

	private final ZenHubClient zenhubClient;

	public IssuesService(ZenHubClient zenhubClient) {
		this.zenhubClient = zenhubClient;
	}

	public ApiResponse<GetIssueDataResponseJson> getIssueData(long repoId, int issueNumber) {

		String url = "/p1/repositories/" + repoId + "/issues/" + issueNumber;

		ApiResponse<GetIssueDataResponseJson> response = zenhubClient.get(url, GetIssueDataResponseJson.class);

		return new ApiResponse<GetIssueDataResponseJson>((GetIssueDataResponseJson) response.getResponse(),
				response.getRateLimitStatus(), response.getResponseBody());

	}

	public ApiResponse<List<IssueEventJson>> getIssueEvents(long repoId, int issueNumber) {

		String url = "/p1/repositories/" + repoId + "/issues/" + issueNumber + "/events";

		ApiResponse<IssueEventJson[]> response = zenhubClient.get(url, IssueEventJson[].class);

		IssueEventJson[] arrayResult = (IssueEventJson[]) response.getResponse();

		List<IssueEventJson> result = new ArrayList<>();

		result.addAll(Arrays.asList(arrayResult));

		return new ApiResponse<List<IssueEventJson>>(result, response.getRateLimitStatus(), response.getResponseBody());

	}
}
