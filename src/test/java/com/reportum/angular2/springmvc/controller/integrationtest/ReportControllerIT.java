package com.reportum.angular2.springmvc.controller.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reportum.angular2.springmvc.controller.integrationtest.config.IntegrationTestConfigurer;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.utils.enums.Profile;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class ReportControllerIT extends IntegrationTestConfigurer {

    static final String REPORTS = "/api/reports";
    static final String REPORTS_BY_PROJECT = "/api/projects/1/reports";
    static final String REPORTS_BY_USER = "/api/users/asterieks@gmail.com/reports";
    static final String PREV_REPORT = "/api/projects/1/prev/reports";

    @Test
    public void test1_getSpecificReportTest() throws Exception {
        mockMvc.perform(
                get(REPORTS_BY_PROJECT)
                        .param("projectId", "1")
                        .header(X_SECRET, publicSecret)
                        .header(WWW_AUTHENTICATE, wwwAuthenticateValue)
                        .header(X_HMAC_CSRF, hmacValue)
                        .header(X_ONCE, xOnceValue)
                        .header(X_DIGEST, mockUpClientDigest(get(REPORTS_BY_PROJECT).param("projectId", "1")))
                        .cookie(jwtCookie)
        )
                .andExpect(jsonPath("$.reportId", is(2)))
                .andExpect(jsonPath("$.project.projectId", is(1)))
                .andExpect(jsonPath("$.project.projectName", is("XXX")))
                .andExpect(jsonPath("$.project.state", is("Reported")))
                .andExpect(jsonPath("$.project.stateDate", any(Long.class)))
                .andExpect(jsonPath("$.project.reporter.id", is("asterieks@gmail.com")))
                .andExpect(jsonPath("$.project.reporter.fullName", is("Name Surname")))
                .andExpect(jsonPath("$.project.reporter.profile", is("REPORTER")))
                .andExpect(jsonPath("$.project.reporter.authorities", is("ROLE_REPORTER")))
                .andExpect(jsonPath("$.project.teamLeader.id", is("lead@gmail.com")))
                .andExpect(jsonPath("$.project.teamLeader.fullName", is("Name1 Surname1")))
                .andExpect(jsonPath("$.project.teamLeader.profile", is("LEAD")))
                .andExpect(jsonPath("$.project.teamLeader.authorities", is("ROLE_MANAGER")))
                .andExpect(jsonPath("$.project.manager", isEmptyOrNullString()))
                .andExpect(jsonPath("$.reviewPart", is("review")))
                .andExpect(jsonPath("$.issuePart", is("issue")))
                .andExpect(jsonPath("$.planPart", is("plan")))
                .andExpect(jsonPath("$.date", any(Long.class)))
                .andExpect(jsonPath("$.reportedBy", is("asterieks@gmail.com")))
                .andExpect(status().isOk());
    }

    @Test
    public void test2_getAllUserReportsTest() throws Exception {
        mockMvc.perform(
                get(REPORTS_BY_USER)
                        .param("userId", "asterieks@gmail.com")
                        .header(X_SECRET, publicSecret)
                        .header(WWW_AUTHENTICATE, wwwAuthenticateValue)
                        .header(X_HMAC_CSRF, hmacValue)
                        .header(X_ONCE, xOnceValue)
                        .header(X_DIGEST, mockUpClientDigest(get(REPORTS_BY_USER).param("userId", "asterieks@gmail.com")))
                        .cookie(jwtCookie)
        )
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].reportId", is(2)))
                .andExpect(jsonPath("$.[0].project.projectId", is(1)))
                .andExpect(jsonPath("$.[0].project.projectName", is("XXX")))
                .andExpect(jsonPath("$.[0].project.state", is("Reported")))
                .andExpect(jsonPath("$.[0].project.stateDate", any(Long.class)))
                .andExpect(jsonPath("$.[0].project.reporter.id", is("asterieks@gmail.com")))
                .andExpect(jsonPath("$.[0].project.reporter.fullName", is("Name Surname")))
                .andExpect(jsonPath("$.[0].project.reporter.profile", is("REPORTER")))
                .andExpect(jsonPath("$.[0].project.reporter.authorities", is("ROLE_REPORTER")))
                .andExpect(jsonPath("$.[0].project.teamLeader.id", is("lead@gmail.com")))
                .andExpect(jsonPath("$.[0].project.teamLeader.fullName", is("Name1 Surname1")))
                .andExpect(jsonPath("$.[0].project.teamLeader.profile", is("LEAD")))
                .andExpect(jsonPath("$.[0].project.teamLeader.authorities", is("ROLE_MANAGER")))
                .andExpect(jsonPath("$.[0].project.manager", isEmptyOrNullString()))
                .andExpect(jsonPath("$.[0].reviewPart", is("review")))
                .andExpect(jsonPath("$.[0].issuePart", is("issue")))
                .andExpect(jsonPath("$.[0].planPart", is("plan")))
                .andExpect(jsonPath("$.[0].date", any(Long.class)))
                .andExpect(jsonPath("$.[0].reportedBy", is("asterieks@gmail.com")))
                .andExpect(status().isOk());
    }

    @Test
    public void test3_addNewReportsTest() throws Exception {
        Report newReport = buildFakeReport("reviewY", "issueY", "planY", 2L);
        mockMvc.perform(
                post(REPORTS)
                        .header(X_SECRET, publicSecret)
                        .header(WWW_AUTHENTICATE, wwwAuthenticateValue)
                        .header(X_HMAC_CSRF, hmacValue)
                        .header(X_ONCE, xOnceValue)
                        .header(X_DIGEST, mockUpClientDigest(post(REPORTS).content(asJsonString(newReport))))
                        .cookie(jwtCookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newReport))
        )
                .andExpect(status().isCreated());
    }

    @Test
    public void test4_updateSpecificReportTest() throws Exception {
        Report newReport = buildFakeReport("reviewX", "issueX", "planX", 1L);
        mockMvc.perform(
                put(REPORTS_BY_PROJECT)
                        .param("projectId", "1")
                        .header(X_SECRET, publicSecret)
                        .header(WWW_AUTHENTICATE, wwwAuthenticateValue)
                        .header(X_HMAC_CSRF, hmacValue)
                        .header(X_ONCE, xOnceValue)
                        .header(X_DIGEST, mockUpClientDigest(put(REPORTS_BY_PROJECT).param("projectId", "1").content(asJsonString(newReport))))
                        .cookie(jwtCookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newReport))
        )
                .andExpect(jsonPath("$.reportId", is(2)))
                .andExpect(jsonPath("$.project.projectId", is(1)))
                .andExpect(jsonPath("$.project.projectName", is("XXX")))
                .andExpect(jsonPath("$.project.state", is("Reported")))
                .andExpect(jsonPath("$.project.stateDate", any(Long.class)))
                .andExpect(jsonPath("$.project.reporter.id", is("asterieks@gmail.com")))
                .andExpect(jsonPath("$.project.reporter.fullName", is("Name Surname")))
                .andExpect(jsonPath("$.project.reporter.profile", is("REPORTER")))
                .andExpect(jsonPath("$.project.reporter.authorities", is("ROLE_REPORTER")))
                .andExpect(jsonPath("$.project.teamLeader.id", is("lead@gmail.com")))
                .andExpect(jsonPath("$.project.teamLeader.fullName", is("Name1 Surname1")))
                .andExpect(jsonPath("$.project.teamLeader.profile", is("LEAD")))
                .andExpect(jsonPath("$.project.teamLeader.authorities", is("ROLE_MANAGER")))
                .andExpect(jsonPath("$.project.manager", isEmptyOrNullString()))
                .andExpect(jsonPath("$.reviewPart", is("reviewX")))
                .andExpect(jsonPath("$.issuePart", is("issueX")))
                .andExpect(jsonPath("$.planPart", is("planX")))
                .andExpect(jsonPath("$.date", any(Long.class)))
                .andExpect(jsonPath("$.reportedBy", is("asterieks@gmail.com")))
                .andExpect(status().isCreated());
    }

    @Test
    public void test5_getPrevReportTest() throws Exception {
        mockMvc.perform(
                get(PREV_REPORT)
                        .param("projectId", "1")
                        .header(X_SECRET, publicSecret)
                        .header(WWW_AUTHENTICATE, wwwAuthenticateValue)
                        .header(X_HMAC_CSRF, hmacValue)
                        .header(X_ONCE, xOnceValue)
                        .header(X_DIGEST, mockUpClientDigest(get(PREV_REPORT).param("projectId", "1")))
                        .cookie(jwtCookie)
        )
                .andExpect(jsonPath("$.reportId", is(1)))
                .andExpect(jsonPath("$.project.projectId", is(1)))
                .andExpect(jsonPath("$.project.projectName", is("XXX")))
                .andExpect(jsonPath("$.project.state", is("Reported")))
                .andExpect(jsonPath("$.project.stateDate", any(Long.class)))
                .andExpect(jsonPath("$.project.reporter.id", is("asterieks@gmail.com")))
                .andExpect(jsonPath("$.project.reporter.fullName", is("Name Surname")))
                .andExpect(jsonPath("$.project.reporter.profile", is("REPORTER")))
                .andExpect(jsonPath("$.project.reporter.authorities", is("ROLE_REPORTER")))
                .andExpect(jsonPath("$.project.teamLeader.id", is("lead@gmail.com")))
                .andExpect(jsonPath("$.project.teamLeader.fullName", is("Name1 Surname1")))
                .andExpect(jsonPath("$.project.teamLeader.profile", is("LEAD")))
                .andExpect(jsonPath("$.project.teamLeader.authorities", is("ROLE_MANAGER")))
                .andExpect(jsonPath("$.project.manager", isEmptyOrNullString()))
                .andExpect(jsonPath("$.reviewPart", is("prevreviewX")))
                .andExpect(jsonPath("$.issuePart", is("previssueX")))
                .andExpect(jsonPath("$.planPart", is("prevplanX")))
                .andExpect(jsonPath("$.date", any(Long.class)))
                .andExpect(jsonPath("$.reportedBy", is("asterieks@gmail.com")))
                .andExpect(status().isOk());
    }

    private Report buildFakeReport(String review, String issue, String plans, Long projectId) {
        User user = new User();
        user.setId("asterieks@gmail.com");
        user.setFullName("Name Surname");
        user.setPassword("1");
        user.setProfile(Profile.REPORTER);

        Project project = new Project();
        project.setProjectId(projectId);
        project.setManager(user);
        project.setStateDate(new Date());

        Report report = new Report();
        report.setProject(project);
        report.setDate(new Date());
        report.setReviewPart(review);
        report.setIssuePart(issue);
        report.setPlanPart(plans);
        report.setReportedBy("asterieks@gmail.com");
        return report;
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
