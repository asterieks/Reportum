package com.reportum.angular2.springmvc.controller.integrationtest;

import org.hamcrest.Matchers;
import org.junit.Test;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectControllerIT extends IntegrationTestConfig {

    private static final String ALL_PROJECTS = "/api/users/asterieks@gmail.com/projects";
    private static final String SPECIFIC_PROJECT = "/api/projects/1";

    @Test
    public void getProjectsWithoutAnyHeadersTest() throws Exception {
        mockMvc.perform(
                get(ALL_PROJECTS)
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void getProjectsWithOnlyLoginPasswordTest() throws Exception {
        mockMvc.perform(
                get(ALL_PROJECTS)
                        .with(user("asterieks@gmail.com").password("1"))
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void getSpecificReportWithoutAnyHeadersTest() throws Exception {
        mockMvc.perform(
                get(SPECIFIC_PROJECT)
                        .param("projectId", "1")
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void getSpecificReportWithOnlyLoginPasswordTest() throws Exception {
        mockMvc.perform(
                get(SPECIFIC_PROJECT)
                        .param("projectId", "1")
                        .with(user("asterieks@gmail.com").password("1"))
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void getProjectsTest() throws Exception {
        mockMvc.perform(
                get(ALL_PROJECTS)
                        .header(X_SECRET, publicSecret)
                        .header(WWW_AUTHENTICATE, wwwAuthenticateValue)
                        .header(X_HMAC_CSRF, hmacValue)
                        .header(X_ONCE, xOnceValue)
                        .header(X_DIGEST, mockUpClientDigest(get(ALL_PROJECTS)))
                        .cookie(jwtCookie)
        )
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].projectId", is(1)))
                .andExpect(jsonPath("$.[0].projectName", is("BAT")))
                .andExpect(jsonPath("$.[0].state", is("Reported")))
                .andExpect(jsonPath("$.[0].stateDate", Matchers.any(Long.class)))
                .andExpect(jsonPath("$.[0].reporter.id", is("asterieks@gmail.com")))
                .andExpect(jsonPath("$.[0].reporter.fullName", is("Maksym Sokil")))
                .andExpect(jsonPath("$.[0].reporter.profile", is("REPORTER")))
                .andExpect(jsonPath("$.[0].reporter.authorities", is("ROLE_REPORTER")))
                .andExpect(jsonPath("$.[0].teamLeader.id", is("lead@gmail.com")))
                .andExpect(jsonPath("$.[0].teamLeader.fullName", is("Yevhenii Reva")))
                .andExpect(jsonPath("$.[0].teamLeader.profile", is("LEAD")))
                .andExpect(jsonPath("$.[0].teamLeader.authorities", is("ROLE_MANAGER")))
                .andExpect(jsonPath("$.[0].manager", isEmptyOrNullString()))
                .andExpect(jsonPath("$.[1].projectId", is(3)))
                .andExpect(jsonPath("$.[1].projectName", is("ULAS")))
                .andExpect(jsonPath("$.[1].state", is("Delayed")))
                .andExpect(jsonPath("$.[1].stateDate", isEmptyOrNullString()))
                .andExpect(jsonPath("$.[1].reporter.id", is("asterieks@gmail.com")))
                .andExpect(jsonPath("$.[1].reporter.fullName", is("Maksym Sokil")))
                .andExpect(jsonPath("$.[1].reporter.profile", is("REPORTER")))
                .andExpect(jsonPath("$.[1].reporter.authorities", is("ROLE_REPORTER")))
                .andExpect(jsonPath("$.[1].teamLeader.id", is("lead@gmail.com")))
                .andExpect(jsonPath("$.[1].teamLeader.fullName", is("Yevhenii Reva")))
                .andExpect(jsonPath("$.[1].teamLeader.profile", is("LEAD")))
                .andExpect(jsonPath("$.[1].teamLeader.authorities", is("ROLE_MANAGER")))
                .andExpect(jsonPath("$.[1].manager", isEmptyOrNullString()))
                .andExpect(status().isOk());
    }

    @Test
    public void getSpecificReportTest() throws Exception {
        mockMvc.perform(
                get(SPECIFIC_PROJECT)
                        .param("projectId", "1")
                        .header(X_SECRET, publicSecret)
                        .header(WWW_AUTHENTICATE, wwwAuthenticateValue)
                        .header(X_HMAC_CSRF, hmacValue)
                        .header(X_ONCE, xOnceValue)
                        .header(X_DIGEST, mockUpClientDigest(get(SPECIFIC_PROJECT)))
                        .cookie(jwtCookie)
        )
                .andExpect(jsonPath("$.projectId", is(1)))
                .andExpect(jsonPath("$.projectName", is("BAT")))
                .andExpect(jsonPath("$.state", is("Reported")))
                .andExpect(jsonPath("$.stateDate", Matchers.any(Long.class)))
                .andExpect(jsonPath("$.reporter.id", is("asterieks@gmail.com")))
                .andExpect(jsonPath("$.reporter.fullName", is("Maksym Sokil")))
                .andExpect(jsonPath("$.reporter.profile", is("REPORTER")))
                .andExpect(jsonPath("$.reporter.authorities", is("ROLE_REPORTER")))
                .andExpect(jsonPath("$.teamLeader.id", is("lead@gmail.com")))
                .andExpect(jsonPath("$.teamLeader.fullName", is("Yevhenii Reva")))
                .andExpect(jsonPath("$.teamLeader.profile", is("LEAD")))
                .andExpect(jsonPath("$.teamLeader.authorities", is("ROLE_MANAGER")))
                .andExpect(jsonPath("$.manager", isEmptyOrNullString()))
                .andExpect(status().isOk());
    }
}
