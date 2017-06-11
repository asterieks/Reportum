package com.reportum.angular2.springmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reportum.angular2.springmvc.configuration.ApplicationInitializer;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.service.IReportService;
import com.reportum.angular2.springmvc.service.IUserService;
import com.reportum.angular2.springmvc.utils.enums.Profile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {ApplicationInitializer.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ReportControllerTest {

    
    @Mock
    private IProjectService projectService;

    @Mock
    private IReportService reportService;

    @Mock
    private IUserService userService;

    @InjectMocks
    private ReportController reportController;

    public MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private User user;
    private Report newReport;
    private Report prevReport;
    private Project project;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();

        newReport=new Report();
        prevReport=new Report();
        user=new User();
        user.setId("23");
        user.setFullName("FullName");
        user.setPassword("password");
        user.setProfile(Profile.REPORTER);
        project=new Project();
        project.setProjectId(12L);
        project.setManager(user);
        project.setStateDate(new Date());
        newReport.setReportId(15L);
        newReport.setProject(project);
        newReport.setDate(new Date());
        prevReport.setProject(project);
    }

    @Test
    public void addNewReportTest() throws Exception{
        when(projectService.findProject(newReport.getProject().getProjectId())).thenReturn(project);
        when(userService.findUser(newReport.getReportedBy())).thenReturn(user);
        this.mockMvc.perform(post("/api/reports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newReport)))
                .andExpect(status().isCreated());
        verify(projectService).findProject(newReport.getProject().getProjectId());
        verify(reportService).saveReport(any());
        verify(userService).findUser(newReport.getReportedBy());
        verify(projectService).saveProject(project);
    }

    @Test
    public void updateSpecificReportTest() throws Exception{
        when(reportService.findReportByProjectId(12L)).thenReturn(prevReport);
        this.mockMvc.perform(put("/api/projects/13/reports").param("projectId", "13")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(prevReport)))
                .andExpect(status().isNoContent());
        when(userService.findUser(newReport.getReportedBy())).thenReturn(user);
        this.mockMvc.perform(put("/api/projects/12/reports").param("projectId", "12")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(prevReport)))
                .andExpect(status().isCreated());
        verify(reportService).findReportByProjectId(12L);
        verify(reportService).saveReport(any());
        verify(userService).findUser(any());
        verify(projectService).saveProject(project);
    }

    @Test
    public void getSpecificReportTest() throws Exception{
        when(reportService.findReportByProjectId(12L)).thenReturn(newReport);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/13/reports").param("projectId", "13"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/12/reports").param("projectId", "12"))
                .andExpect(status().isOk());
        verify(reportService).findReportByProjectId(12L);
    }

    @Test
    public void getAllUserReportsTest() throws Exception{
        List<Project> projects=new ArrayList<>();
        List<Report> reports=new ArrayList<>();
        projects.add(project);
        reports.add(newReport);
        when(userService.findUser("23")).thenReturn(user);
        this.mockMvc.perform(get("/api/users/22/reports").param("userId", "22"))
                .andExpect(status().isNoContent());
        when(projectService.findProjects(user)).thenReturn(projects);
        verify(userService).findUser(any());
        this.mockMvc.perform(get("/api/users/23/reports").param("userId", "23"))
                .andExpect(status().isNoContent());
        when(reportService.findReports(projects)).thenReturn(reports);
        verify(projectService).findProjects(user);
        verify(reportService).findReports(any());
        this.mockMvc.perform(get("/api/users/22/reports").param("userId", "22"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(get("/api/users/23/reports").param("userId", "23"))
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
