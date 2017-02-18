package com.reportum.angular2.springmvc.controller;

import com.reportum.angular2.springmvc.configuration.ApplicationInitializer;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.service.IReportService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ApplicationInitializer.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ReportControllerTest {
    @Mock
    private IProjectService projectService;

    @Mock
    private IReportService reportService;

    @InjectMocks
    private ReportController reportController;

    public MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    @Test
    public void addNewReportTest(){

    }
    @Test
    public void updateSpecificReportTest(){

    }
}
