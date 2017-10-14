package com.reportum.angular2.springmvc.controller.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.utils.enums.Profile;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReportControllerIT extends IntegrationTestConfig {

    private static final String REPORTS = "/api/reports";
    private static final String REPORTS_BY_PROJECT = "/api/projects/1/reports";
    private static final String REPORTS_BY_USER = "/api/users/asterieks@gmail.com/reports";
    private static final String PREV_REPORT = "/api/projects/1/prev/reports";

    public static class WithoutAnyHeadersTest extends ReportControllerIT {

        @Test
        public void addNewReportWithoutAnyHeadersTest() throws Exception {
            mockMvc.perform(
                    post(REPORTS)
            )
                    .andExpect(status().isForbidden());
        }

        @Test
        public void updateSpecificReportWithoutAnyHeadersTest() throws Exception {
            mockMvc.perform(
                    put(REPORTS_BY_PROJECT)
                            .param("projectId", "1")
            )
                    .andExpect(status().isForbidden());
        }

        @Test
        public void getSpecificReportWithoutAnyHeadersTest() throws Exception {
            mockMvc.perform(
                    get(REPORTS_BY_PROJECT)
                            .param("projectId", "1")
            )
                    .andExpect(status().isForbidden());
        }

        @Test
        public void getAllUserReportsWithoutAnyHeadersTest() throws Exception {
            mockMvc.perform(
                    get(REPORTS_BY_USER)
                            .param("userId", "asterieks@gmail.com")
            )
                    .andExpect(status().isForbidden());
        }

        @Test
        public void getPrevReportWithoutAnyHeadersTest() throws Exception {
            mockMvc.perform(
                    get(PREV_REPORT)
                            .param("projectId", "1")
            )
                    .andExpect(status().isForbidden());
        }
    }

    public static class WithOnlyLoginPasswordTest extends ReportControllerIT {

        @Test
        public void addNewReportWithOnlyLoginPasswordTest() throws Exception {
            mockMvc.perform(
                    post(REPORTS)
                            .with(user("asterieks@gmail.com").password("1"))
            )
                    .andExpect(status().isForbidden());
        }

        @Test
        public void updateSpecificReportWithOnlyLoginPasswordTest() throws Exception {
            mockMvc.perform(
                    put(REPORTS_BY_PROJECT)
                            .param("projectId", "1")
                            .with(user("asterieks@gmail.com").password("1"))
            )
                    .andExpect(status().isForbidden());
        }

        @Test
        public void getSpecificReportWithOnlyLoginPasswordTest() throws Exception {
            mockMvc.perform(
                    get(REPORTS_BY_PROJECT)
                            .param("projectId", "1")
                            .with(user("asterieks@gmail.com").password("1"))
            )
                    .andExpect(status().isForbidden());
        }

        @Test
        public void getAllUserReportsWithOnlyLoginPasswordTest() throws Exception {
            mockMvc.perform(
                    get(REPORTS_BY_USER)
                            .param("userId", "asterieks@gmail.com")
                            .with(user("asterieks@gmail.com").password("1"))
            )
                    .andExpect(status().isForbidden());
        }

        @Test
        public void getPrevReportWithOnlyLoginPasswordTest() throws Exception {
            mockMvc.perform(
                    get(PREV_REPORT)
                            .param("projectId", "1")
                            .with(user("asterieks@gmail.com").password("1"))
            )
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    public void getProjectsTest() throws Exception {
        Report newReport = new Report();
        Report prevReport = new Report();
        User user = new User();
        user.setId("asterieks@gmail.com");
        user.setFullName("Maksym Sokil");
        user.setPassword("1");
        user.setProfile(Profile.REPORTER);
        Project project = new Project();
        project.setProjectId(1L);
        project.setManager(user);
        project.setStateDate(new Date());
        newReport.setProject(project);
        newReport.setDate(new Date());
        newReport.setReviewPart("review");
        newReport.setIssuePart("issue");
        newReport.setPlanPart("plan");
        newReport.setReportedBy("asterieks@gmail.com");
        prevReport.setProject(project);

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
    public void updateSpecificReportTest() throws Exception {
        Report newReport = new Report();
        Report prevReport = new Report();
        User user = new User();
        user.setId("asterieks@gmail.com");
        user.setFullName("Maksym Sokil");
        user.setPassword("1");
        user.setProfile(Profile.REPORTER);
        Project project = new Project();
        project.setProjectId(1L);
        project.setManager(user);
        project.setStateDate(new Date());
        newReport.setProject(project);
        newReport.setDate(new Date());
        newReport.setReviewPart("review_red1");
        newReport.setIssuePart("issue_red1");
        newReport.setPlanPart("plan_red1");
        newReport.setReportedBy("asterieks@gmail.com");
        prevReport.setProject(project);

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
                .andExpect(jsonPath("$.project.projectId", is(1)))
                .andExpect(jsonPath("$.project.projectName", is("BAT")))
                .andExpect(jsonPath("$.project.state", is("Reported")))
                .andExpect(jsonPath("$.project.stateDate", any(Long.class)))
                .andExpect(jsonPath("$.project.reporter.id", is("asterieks@gmail.com")))
                .andExpect(jsonPath("$.project.reporter.fullName", is("Maksym Sokil")))
                .andExpect(jsonPath("$.project.reporter.profile", is("REPORTER")))
                .andExpect(jsonPath("$.project.reporter.authorities", is("ROLE_REPORTER")))
                .andExpect(jsonPath("$.project.teamLeader.id", is("lead@gmail.com")))
                .andExpect(jsonPath("$.project.teamLeader.fullName", is("Yevhenii Reva")))
                .andExpect(jsonPath("$.project.teamLeader.profile", is("LEAD")))
                .andExpect(jsonPath("$.project.teamLeader.authorities", is("ROLE_MANAGER")))
                .andExpect(jsonPath("$.project.manager", isEmptyOrNullString()))
                .andExpect(jsonPath("$.reviewPart", is("review_red1")))
                .andExpect(jsonPath("$.issuePart", is("issue_red1")))
                .andExpect(jsonPath("$.planPart", is("plan_red1")))
                .andExpect(jsonPath("$.date", any(Long.class)))
                .andExpect(jsonPath("$.reportedBy", is("asterieks@gmail.com")))
                .andExpect(status().isCreated());
    }

    @Test
    public void getSpecificReportTest() throws Exception {
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
                .andExpect(jsonPath("$.project.projectId", is(1)))
                .andExpect(jsonPath("$.project.projectName", is("BAT")))
                .andExpect(jsonPath("$.project.state", is("Reported")))
                .andExpect(jsonPath("$.project.stateDate", any(Long.class)))
                .andExpect(jsonPath("$.project.reporter.id", is("asterieks@gmail.com")))
                .andExpect(jsonPath("$.project.reporter.fullName", is("Maksym Sokil")))
                .andExpect(jsonPath("$.project.reporter.profile", is("REPORTER")))
                .andExpect(jsonPath("$.project.reporter.authorities", is("ROLE_REPORTER")))
                .andExpect(jsonPath("$.project.teamLeader.id", is("lead@gmail.com")))
                .andExpect(jsonPath("$.project.teamLeader.fullName", is("Yevhenii Reva")))
                .andExpect(jsonPath("$.project.teamLeader.profile", is("LEAD")))
                .andExpect(jsonPath("$.project.teamLeader.authorities", is("ROLE_MANAGER")))
                .andExpect(jsonPath("$.project.manager", isEmptyOrNullString()))
                .andExpect(jsonPath("$.reviewPart", is("review_red1")))
                .andExpect(jsonPath("$.issuePart", is("issue_red1")))
                .andExpect(jsonPath("$.planPart", is("plan_red1")))
                .andExpect(jsonPath("$.date", any(Long.class)))
                .andExpect(jsonPath("$.reportedBy", is("asterieks@gmail.com")))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUserReportsTest() throws Exception {
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
//                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].project.projectId", is(1)))
                .andExpect(jsonPath("$.[0].project.projectName", is("BAT")))
                .andExpect(jsonPath("$.[0].project.state", is("Reported")))
                .andExpect(jsonPath("$.[0].project.stateDate", any(Long.class)))
                .andExpect(jsonPath("$.[0].project.reporter.id", is("asterieks@gmail.com")))
                .andExpect(jsonPath("$.[0].project.reporter.fullName", is("Maksym Sokil")))
                .andExpect(jsonPath("$.[0].project.reporter.profile", is("REPORTER")))
                .andExpect(jsonPath("$.[0].project.reporter.authorities", is("ROLE_REPORTER")))
                .andExpect(jsonPath("$.[0].project.teamLeader.id", is("lead@gmail.com")))
                .andExpect(jsonPath("$.[0].project.teamLeader.fullName", is("Yevhenii Reva")))
                .andExpect(jsonPath("$.[0].project.teamLeader.profile", is("LEAD")))
                .andExpect(jsonPath("$.[0].project.teamLeader.authorities", is("ROLE_MANAGER")))
                .andExpect(jsonPath("$.[0].project.manager", isEmptyOrNullString()))
                .andExpect(jsonPath("$.[0].reviewPart", is("review_red1<br>")))
                .andExpect(jsonPath("$.[0].issuePart", is("issue_red1<br>")))
                .andExpect(jsonPath("$.[0].planPart", is("plan_red1<br>")))
                .andExpect(jsonPath("$.[0].date", any(Long.class)))
                .andExpect(jsonPath("$.[0].reportedBy", is("asterieks@gmail.com")))
                .andExpect(status().isOk());
    }

    @Test
    public void getPrevReportTest() throws Exception {
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
                .andExpect(jsonPath("$.reportId", is(54)))
                .andExpect(jsonPath("$.project.projectId", is(1)))
                .andExpect(jsonPath("$.project.projectName", is("BAT")))
                .andExpect(jsonPath("$.project.state", is("Reported")))
                .andExpect(jsonPath("$.project.stateDate", any(Long.class)))
                .andExpect(jsonPath("$.project.reporter.id", is("asterieks@gmail.com")))
                .andExpect(jsonPath("$.project.reporter.fullName", is("Maksym Sokil")))
                .andExpect(jsonPath("$.project.reporter.profile", is("REPORTER")))
                .andExpect(jsonPath("$.project.reporter.authorities", is("ROLE_REPORTER")))
                .andExpect(jsonPath("$.project.teamLeader.id", is("lead@gmail.com")))
                .andExpect(jsonPath("$.project.teamLeader.fullName", is("Yevhenii Reva")))
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

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
