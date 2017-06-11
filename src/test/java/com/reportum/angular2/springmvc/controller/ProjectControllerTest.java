package com.reportum.angular2.springmvc.controller;


import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.service.IUserService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ProjectControllerTest {
    @Mock
    private IProjectService projectService;

    @Mock
    private IUserService userService;

    @InjectMocks
    private ProjectController projectController=new ProjectController();

    public MockMvc mockMvc;

    private User user;
    private Project project;
    private List<Project> projects;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();

        projects = new ArrayList<>();
        project=new Project();
        user=new User();
        User user2=new User();
        user2.setId("13");
        user.setId("12");
        user.setFullName("fullName");
        user.setPassword("password");
        project.setReporter(user);

        project.setProjectId(23L);
        projects.add(project);
    }

    @Test
    public void getProjectsTest()throws Exception{
        when(userService.findUser("12")).thenReturn(user);
        this.mockMvc.perform(get("/api/users/13/projects").param("userId", "13"))
                .andExpect(status().isNoContent());
        when(projectService.findProjects(user)).thenReturn(projects);
        verify(userService).findUser("13");
        this.mockMvc.perform(get("/api/users/13/projects").param("userId", "13"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(get("/api/users/12/projects").param("userId", "12"))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].projectId", is(23)))
                .andExpect(status().isOk());
        verify(projectService).findProjects(user);
    }

    @Test
    public void getSpecificReportTest() throws Exception {
        when(projectService.findProject(23L)).thenReturn(project);
        this.mockMvc.perform(get("/api/projects/12").param("projectId", "12"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(get("/api/projects/23").param("projectId", "23"))
                .andExpect(jsonPath("$.projectId", is(23)))
                .andExpect(status().isOk());
        verify(projectService).findProject(23L);
    }
}
