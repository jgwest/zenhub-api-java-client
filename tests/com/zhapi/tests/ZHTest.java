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

package com.zhapi.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.zhapi.ApiResponse;
import com.zhapi.ZHUtil;
import com.zhapi.json.BoardPipelineEntryJson;
import com.zhapi.json.BoardPipelineIssueEntryJson;
import com.zhapi.json.DependencyBlockingOrBlockedJson;
import com.zhapi.json.DependencyJson;
import com.zhapi.json.EpicIssueJson;
import com.zhapi.json.IssueEventJson;
import com.zhapi.json.PipelineJson;
import com.zhapi.json.WorkspaceJson;
import com.zhapi.json.responses.DependenciesForARepoResponseJson;
import com.zhapi.json.responses.GetBoardForRepositoryResponseJson;
import com.zhapi.json.responses.GetEpicResponseJson;
import com.zhapi.json.responses.GetEpicsResponseJson;
import com.zhapi.json.responses.GetIssueDataResponseJson;
import com.zhapi.services.BoardService;
import com.zhapi.services.DependenciesService;
import com.zhapi.services.EpicsService;
import com.zhapi.services.IssuesService;
import com.zhapi.services.WorkspaceService;

/**
 * NOTE: Running this test requires a ZenHub API token based on your GitHub id.
 * See AbstractTest for details.
 */
public class ZHTest extends AbstractTest {

	@Test
	public void doBoardTest() {

		BoardService bs = new BoardService(getClient());

		GetBoardForRepositoryResponseJson ar = bs.getZenHubBoardForRepo(openLibertyRepoId).getResponse();

		doBoardTest_assertExpectedResponse(ar);

		ar = bs.getZenHubBoardForRepo(openLibertyRepoId, "5c3e0d32cd4b0547e1da3c5b").getResponse();
		doBoardTest_assertExpectedResponse(ar);

	}

	private static void doBoardTest_assertExpectedResponse(GetBoardForRepositoryResponseJson ar) {

		List<BoardPipelineEntryJson> pipelines = ar.getPipelines();

		assertNotNull(pipelines);

		assertTrue("Not enough pipelines", pipelines.size() > 1);

		String[] expectedPipelines = new String[] { "New Issues", "Epics", "Icebox", "Backlog", "In Progress", "Review/QA",
				"Done" };

		List<String> pipelineNames = pipelines.stream().map(e -> e.getName()).collect(Collectors.toList());

		for (String expected : expectedPipelines) {
			assertTrue(expected + " not found", pipelineNames.contains(expected));
		}

		// At least one pipeline must contain several issues
		assertTrue(pipelines.stream().anyMatch(board -> {
			List<BoardPipelineIssueEntryJson> issues = board.getIssues();
			if (issues.size() <= 1) {
				return false;
			}

			// At least one issue must match
			if (!board.getIssues().stream().anyMatch(issue -> {

				if (issue.getPosition() <= 5) {
					return false;
				}
				if (issue.getEstimate() == null) {
					return false;
				}

				if (issue.getEstimate().getValue() <= 2) {
					return false;
				}

				if (issue.getIssue_number() <= 100) {
					return false;
				}

				return true;
			})) {
				return false;
			}

			// At least one match: similar issue tests as above, but for epic.
			if (!board.getIssues().stream().anyMatch(issue -> {
				if (issue.getPosition() <= 1) {
					return false;
				}
				if (issue.getEstimate() == null) {
					return false;
				}

				if (issue.getEstimate().getValue() <= 2) {
					return false;
				}

				if (issue.getIssue_number() <= 100) {
					return false;
				}

				return issue.isIs_epic();

			})) {

			}

			return true;

		}));

	}

	@Test
	public void doDependenciesTest() {

		DependenciesService service = new DependenciesService(getClient());
		DependenciesForARepoResponseJson d = service.getDependenciesForARepository(openLibertyRepoId).getResponse();

		List<DependencyJson> dependencies = d.getDependencies();

		assertNotNull(dependencies);

		assertTrue(dependencies.size() > 10);

		// Look for a specific known dependency
		assertTrue(dependencies.stream().anyMatch(dependency -> {

			DependencyBlockingOrBlockedJson blocked = dependency.getBlocked();
			DependencyBlockingOrBlockedJson blocking = dependency.getBlocking();
			if (blocked == null || blocking == null) {
				return false;
			}

			return blocked.getIssue_number() == 320 && blocked.getRepo_id() == openLibertyRepoId
					&& blocking.getIssue_number() == 155 && blocking.getRepo_id() == openLibertyRepoId;
		}));

		// All dependencies should be non-null, and the values should be sane
		assertTrue(dependencies.stream().allMatch(dependency -> {

			DependencyBlockingOrBlockedJson blocked = dependency.getBlocked();
			DependencyBlockingOrBlockedJson blocking = dependency.getBlocking();
			if (blocked == null || blocking == null) {
				return false;
			}

			return blocked.getIssue_number() > 1 && blocked.getRepo_id() > 1 && blocking.getIssue_number() > 1
					&& blocking.getRepo_id() > 1;

		}));

	}

	@Test
	public void doEpicsTest() {
		EpicsService service = new EpicsService(getClient());

		GetEpicsResponseJson olEpics = service.getEpics(openLibertyRepoId).getResponse();
		assertNotNull(olEpics);

		List<EpicIssueJson> issues = olEpics.getEpic_issues();
		assertNotNull(issues);

		assertTrue(issues.size() > 10);

		assertTrue(issues.stream().allMatch(issue -> issue.getIssue_url() != null && !issue.getIssue_url().trim().isEmpty()));

		// Match a specific known epic
		assertTrue(issues.stream().allMatch(issue -> issue.getIssue_number() > 1 && issue.getRepo_id() == openLibertyRepoId));

		// Match a specific known epic
		assertTrue(issues.stream().anyMatch(issue -> issue.getIssue_number() == 75 && issue.getRepo_id() == openLibertyRepoId
				&& issue.getIssue_url().equals("https://github.com/OpenLiberty/open-liberty/issues/75")));

		List<ApiResponse<GetEpicResponseJson>> firstFiveResponses = issues.subList(0, 5).parallelStream()
				.map(e -> service.getEpic(openLibertyRepoId, e.getIssue_number())).collect(Collectors.toList());

		firstFiveResponses.stream().forEach(r -> {
			assertNotNull(r);
			assertNotNull(r.getResponse());

			GetEpicResponseJson epic = r.getResponse();
			assertNotNull(epic.getPipeline());

			assertNotNull(epic.getTotal_epic_estimates());
			assertTrue(epic.getTotal_epic_estimates().getValue() > 1);

			assertNotNull(epic.getIssues());
			assertTrue(epic.getIssues().size() > 0);

			epic.getIssues().forEach(issue -> {
				assertTrue(issue.getIssue_number() > 1);
				assertTrue(issue.getRepo_id() > 1);
			});

		});

		// At least one must have an estimate
		assertTrue(firstFiveResponses.stream().map(r -> r.getResponse()).anyMatch(e -> {
			return e.getEstimate() != null && e.getEstimate().getValue() > 1;
		}));

	}

	@Test
	public void doEpicTest() {

		// Test a specific epic
		{
			EpicsService service = new EpicsService(getClient());
			ApiResponse<GetEpicResponseJson> r = service.getEpic(openLibertyRepoId, 1423);
			assertNotNull(r);
			assertNotNull(r.getResponse());

			GetEpicResponseJson c = r.getResponse();

			PipelineJson entry = c.getPipeline();

			assertTrue(entry.getPipeline_id() == null && entry.getName() == null);

		}

		// Test a specific epic
		{

			EpicsService service = new EpicsService(getClient());
			ApiResponse<GetEpicResponseJson> r = service.getEpic(openLibertyRepoId, 93);
			assertNotNull(r);
			assertNotNull(r.getResponse());

			GetEpicResponseJson c = r.getResponse();

			List<Integer> expectedIssues = Arrays.asList(75, 90, 94, 283, 364, 421, 512, 515, 552, 592, 640, 642, 644, 645, 646,
					647, 783, 784, 785, 919);

			List<Integer> actualIssues = c.getIssues().stream().map(e -> e.getIssue_number()).sorted()
					.collect(Collectors.toList());

			assertTrue(expectedIssues.equals(actualIssues));

			assertTrue(c.getTotal_epic_estimates().getValue() == 94);

			assertTrue(
					c.getIssues().stream().anyMatch(e -> e.getPipeline() != null && e.getPipeline().getName().equals("closed")));

		}
	}

	@Test
	public void doIssuesTest() {

		IssuesService service = new IssuesService(getClient());

		// Test issue data for a specific issue
		{
			ApiResponse<GetIssueDataResponseJson> ar = service.getIssueData(openLibertyRepoId, 1423);
			assertNotNull(ar);
			assertNotNull(ar.getResponse());

			GetIssueDataResponseJson gidrj = ar.getResponse();

			assertTrue(gidrj.isIs_epic());

		}

		// Test issue events for a specific issue
		{

			ApiResponse<List<IssueEventJson>> ar = service.getIssueEvents(openLibertyRepoId, 1423);
			assertNotNull(ar);
			assertNotNull(ar.getResponse());

			List<IssueEventJson> events = ar.getResponse();

			List<IssueEventJson> expectedEvents = new ArrayList<>();

			// Filter out any events newer than when this test was created.
			events = events.stream().filter(e -> e.getCreated_at().before(new Date(1554826382578l + 1)))
					.collect(Collectors.toList());

			assertTrue(events.size() == 5);

			IssueEventJson next = new IssueEventJson();
			next.setUser_id(5427967);
			next.setType("transferIssue");
			next.setCreated_at(new Date(1554826382578l));
			next.setWorkspace_id("5c3e0d32cd4b0547e1da3c5b");

			PipelineJson fromPipeline = new PipelineJson();
			fromPipeline.setName("New Issues");
			next.setFrom_pipeline(fromPipeline);

			PipelineJson toPipeline = new PipelineJson();
			toPipeline.setName("In Progress");
			next.setTo_pipeline(toPipeline);

			expectedEvents.addAll(Arrays.asList(next,

					createIssueEventJson(5427967, "addIssueToEpic", 1554826369923l),
					createIssueEventJson(5427967, "addIssueToEpic", 1550429936395l),
					createIssueEventJson(5427967, "addEpicToIssue", 1514929331990l),
					createIssueEventJson(5427967, "convertIssueToEpic", 1514929319394l)));

			assertTrue(expectedEvents.toString().equals(events.toString()));

		}
	}

	@Test
	public void doWorkspaceTest() {

		WorkspaceService service = new WorkspaceService(getClient());

		ApiResponse<List<WorkspaceJson>> ar = service.getZenHubWorkspacesForARepository(openLibertyRepoId);
		assertNotNull(ar);
		assertNotNull(ar.getResponse());

		List<WorkspaceJson> workspaces = ar.getResponse();

		System.out.println(ZHUtil.toPrettyString(workspaces));

		WorkspaceJson defaultWorkspace = workspaces.stream().filter(e -> e.getId().equals("5c3e0d32cd4b0547e1da3c5b")).findFirst()
				.orElseThrow(RuntimeException::new);

		assertNotNull(defaultWorkspace);

		WorkspaceJson ssoWorkspace = workspaces.stream().filter(e -> e.getId().equals("5d78fbf2b9080000013ae709")).findFirst()
				.orElseThrow(RuntimeException::new);

		assertNotNull(ssoWorkspace);
		assertTrue(ssoWorkspace.getName().equals("Security SSO"));

	}

	private static IssueEventJson createIssueEventJson(int userId, String type, long createdAt) {
		IssueEventJson iej = new IssueEventJson();
		iej.setUser_id(userId);
		iej.setType(type);
		iej.setCreated_at(new Date(createdAt));
		return iej;
	}
}
