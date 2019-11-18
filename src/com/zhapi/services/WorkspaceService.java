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
import com.zhapi.json.WorkspaceJson;

/**
 * Implements the 'Get ZenHub Workspaces' API from
 * https://github.com/ZenHubIO/API#get-zenhub-workspaces-for-a-repository
 */
public class WorkspaceService {

	private final ZenHubClient zenhubClient;

	public WorkspaceService(ZenHubClient client) {
		this.zenhubClient = client;
	}

	public ApiResponse<List<WorkspaceJson>> getZenHubWorkspacesForARepository(long repoId) {

		ApiResponse<WorkspaceJson[]> response = zenhubClient.get("/p2/repositories/" + repoId + "/workspaces",
				WorkspaceJson[].class);

		WorkspaceJson[] arrayResult = (WorkspaceJson[]) response.getResponse();

		List<WorkspaceJson> result = new ArrayList<>();

		result.addAll(Arrays.asList(arrayResult));

		return new ApiResponse<List<WorkspaceJson>>(result, response.getRateLimitStatus(), response.getResponseBody());

	}

}
