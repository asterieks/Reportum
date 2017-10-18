package com.reportum.angular2.springmvc.controller.integrationtest;

import com.reportum.angular2.springmvc.controller.integrationtest.config.IntegrationTestConfigurer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class IndexControllerIT extends IntegrationTestConfigurer {

    @Test
    public void getIndexPageTest() throws Exception {
        mockMvc
                .perform(get("/"))
                .andExpect(forwardedUrl("/WEB-INF/views/index.html"))
                .andExpect(status().isOk());
    }
}
