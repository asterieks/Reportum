package com.reportum.angular2.springmvc.controller.integrationtest;

import com.reportum.angular2.springmvc.controller.integrationtest.config.IntegrationTestConfigurer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReportControllerWithoutHeadersIT extends IntegrationTestConfigurer {

    @Test
    public void addNewReportWithoutHeadersTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(ReportControllerIT.REPORTS)
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateSpecificReportWithoutHeadersTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put(ReportControllerIT.REPORTS_BY_PROJECT)
                        .param("projectId", "1")
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void getSpecificReportWithoutHeadersTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(ReportControllerIT.REPORTS_BY_PROJECT)
                        .param("projectId", "1")
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllUserReportsWithoutHeadersTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(ReportControllerIT.REPORTS_BY_USER)
                        .param("userId", "asterieks@gmail.com")
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void getPrevReportWithoutHeadersTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(ReportControllerIT.PREV_REPORT)
                        .param("projectId", "1")
        )
                .andExpect(status().isForbidden());
    }
}
