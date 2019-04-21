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
import com.zhapi.json.responses.DependenciesForARepoResponseJson;

/**
 * Implements 'Get Dependencies for a Repository' API from
 * https://github.com/ZenHubIO/API#dependencies
 */
public class DependenciesService {

	private final ZenHubClient zenhubClient;

	public DependenciesService(ZenHubClient zenhubClient) {
		this.zenhubClient = zenhubClient;
	}

	public ApiResponse<DependenciesForARepoResponseJson> getDependenciesForARepository(long repoId) {

		String url = "/p1/repositories/" + repoId + "/dependencies";

		ApiResponse<DependenciesForARepoResponseJson> api = zenhubClient.get(url, DependenciesForARepoResponseJson.class);

		return new ApiResponse<DependenciesForARepoResponseJson>((DependenciesForARepoResponseJson) api.getResponse(),
				api.getRateLimitStatus(), api.getResponseBody());

	}
}
