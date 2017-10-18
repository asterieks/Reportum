package com.reportum.angular2.springmvc.controller.integrationtest;

import com.reportum.angular2.springmvc.controller.integrationtest.config.IntegrationTestConfigurer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReportControllerOnlyLoginPasswordIT extends IntegrationTestConfigurer {

    @Test
    public void addNewReportWithOnlyLoginPasswordTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(ReportControllerIT.REPORTS)
                        .with(user("asterieks@gmail.com").password("1"))
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateSpecificReportWithOnlyLoginPasswordTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put(ReportControllerIT.REPORTS_BY_PROJECT)
                        .param("projectId", "1")
                        .with(user("asterieks@gmail.com").password("1"))
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void getSpecificReportWithOnlyLoginPasswordTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(ReportControllerIT.REPORTS_BY_PROJECT)
                        .param("projectId", "1")
                        .with(user("asterieks@gmail.com").password("1"))
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllUserReportsWithOnlyLoginPasswordTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(ReportControllerIT.REPORTS_BY_USER)
                        .param("userId", "asterieks@gmail.com")
                        .with(user("asterieks@gmail.com").password("1"))
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void getPrevReportWithOnlyLoginPasswordTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(ReportControllerIT.PREV_REPORT)
                        .param("projectId", "1")
                        .with(user("asterieks@gmail.com").password("1"))
        )
                .andExpect(status().isForbidden());
    }
}
