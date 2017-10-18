package com.reportum.angular2.springmvc.controller.integrationtest;

import com.reportum.angular2.springmvc.controller.integrationtest.config.IntegrationTestConfigurer;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectControllerIT extends IntegrationTestConfigurer {

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
                .andExpect(jsonPath("$.[0].projectName", is("XXX")))
                .andExpect(jsonPath("$.[0].state", is("Reported")))
                .andExpect(jsonPath("$.[0].stateDate", Matchers.any(Long.class)))
                .andExpect(jsonPath("$.[0].reporter.id", is("asterieks@gmail.com")))
                .andExpect(jsonPath("$.[0].reporter.fullName", is("Name Surname")))
                .andExpect(jsonPath("$.[0].reporter.profile", is("REPORTER")))
                .andExpect(jsonPath("$.[0].reporter.authorities", is("ROLE_REPORTER")))
                .andExpect(jsonPath("$.[0].teamLeader.id", is("lead@gmail.com")))
                .andExpect(jsonPath("$.[0].teamLeader.fullName", is("Name1 Surname1")))
                .andExpect(jsonPath("$.[0].teamLeader.profile", is("LEAD")))
                .andExpect(jsonPath("$.[0].teamLeader.authorities", is("ROLE_MANAGER")))
                .andExpect(jsonPath("$.[0].manager", isEmptyOrNullString()))
                .andExpect(jsonPath("$.[1].projectId", is(2)))
                .andExpect(jsonPath("$.[1].projectName", is("YYY")))
                .andExpect(jsonPath("$.[1].state", is("Reported")))
                .andExpect(jsonPath("$.[1].stateDate", Matchers.any(Long.class)))
                .andExpect(jsonPath("$.[1].reporter.id", is("asterieks@gmail.com")))
                .andExpect(jsonPath("$.[1].reporter.fullName", is("Name Surname")))
                .andExpect(jsonPath("$.[1].reporter.profile", is("REPORTER")))
                .andExpect(jsonPath("$.[1].reporter.authorities", is("ROLE_REPORTER")))
                .andExpect(jsonPath("$.[1].teamLeader.id", is("lead@gmail.com")))
                .andExpect(jsonPath("$.[1].teamLeader.fullName", is("Name1 Surname1")))
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
                .andExpect(jsonPath("$.projectName", is("XXX")))
                .andExpect(jsonPath("$.state", is("Reported")))
                .andExpect(jsonPath("$.stateDate", Matchers.any(Long.class)))
                .andExpect(jsonPath("$.reporter.id", is("asterieks@gmail.com")))
                .andExpect(jsonPath("$.reporter.fullName", is("Name Surname")))
                .andExpect(jsonPath("$.reporter.profile", is("REPORTER")))
                .andExpect(jsonPath("$.reporter.authorities", is("ROLE_REPORTER")))
                .andExpect(jsonPath("$.teamLeader.id", is("lead@gmail.com")))
                .andExpect(jsonPath("$.teamLeader.fullName", is("Name1 Surname1")))
                .andExpect(jsonPath("$.teamLeader.profile", is("LEAD")))
                .andExpect(jsonPath("$.teamLeader.authorities", is("ROLE_MANAGER")))
                .andExpect(jsonPath("$.manager", isEmptyOrNullString()))
                .andExpect(status().isOk());
    }
}
