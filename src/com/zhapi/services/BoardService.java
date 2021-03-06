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

import com.zhapi.ApiResponse;
import com.zhapi.ZenHubClient;
import com.zhapi.json.responses.GetBoardForRepositoryResponseJson;

/**
 * Implements the 'Get Board Data for a Repository' API from
 * https://github.com/ZenHubIO/API#board.
 */
public class BoardService {

	private final ZenHubClient zenhubClient;

	public BoardService(ZenHubClient client) {
		this.zenhubClient = client;
	}

	public ApiResponse<GetBoardForRepositoryResponseJson> getZenHubBoardForRepo(long repoId) {

		String url = "/p1/repositories/" + repoId + "/board";

		ApiResponse<GetBoardForRepositoryResponseJson> response = zenhubClient.get(url, GetBoardForRepositoryResponseJson.class);

		return new ApiResponse<GetBoardForRepositoryResponseJson>((GetBoardForRepositoryResponseJson) response.getResponse(),
				response.getRateLimitStatus(), response.getResponseBody());

	}

	public ApiResponse<GetBoardForRepositoryResponseJson> getZenHubBoardForRepo(long repoId, String workspaceId) {

		String url = "/p2/workspaces/" + workspaceId + "/repositories/" + repoId + "/board";

		ApiResponse<GetBoardForRepositoryResponseJson> response = zenhubClient.get(url, GetBoardForRepositoryResponseJson.class);

		return new ApiResponse<GetBoardForRepositoryResponseJson>((GetBoardForRepositoryResponseJson) response.getResponse(),
				response.getRateLimitStatus(), response.getResponseBody());

	}
}
