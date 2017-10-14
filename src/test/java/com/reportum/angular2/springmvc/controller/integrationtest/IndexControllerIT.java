package com.reportum.angular2.springmvc.controller.integrationtest;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IndexControllerIT extends IntegrationTestConfig {

    @Test
    public void getIndexPageTest() throws Exception {
        mockMvc
                .perform(get("/"))
                .andExpect(forwardedUrl("/WEB-INF/views/index.html"))
                .andExpect(status().isOk());
    }
}
