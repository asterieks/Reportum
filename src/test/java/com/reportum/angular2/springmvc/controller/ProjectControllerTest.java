package com.reportum.angular2.springmvc.controller;


import com.reportum.angular2.springmvc.configuration.ApplicationInitializer;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.service.IUserService;
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
public class ProjectControllerTest {
    @Mock
    private IProjectService projectService;

    @Mock
    private IUserService userService;

    @InjectMocks
    private ProjectController projectController;

    public MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    public void getProjectsTest()throws Exception{
       /* List<Project> projects = new ArrayList<>();
        Project project=new Project();
        User user=new User();
        user.setId("12");
        project.setReporter(user);
        project.setProjectId(23L);
        projects.add(project);
        when(userService.findUser("12")).thenReturn(user);
        this.mockMvc.perform(get("/users/13/projects").param("id", "13"))
                .andExpect(status().isNoContent());
        when(projectService.findProjects(user)).thenReturn(projects);
        this.mockMvc.perform(get("/users/13/projects/").param("id", "13"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(get("/users/12/projects").param("id", "12"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].projectId", is(23)))
                .andExpect(status().isOk());*/
    }

    @Test
    public void getSpecificReportTest() throws Exception {
        /*Project project=new Project();
        project.setProjectId(23L);
        when(projectService.findProject(23L)).thenReturn(project);
        this.mockMvc.perform(get("/projects/12").param("id", "12"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(get("/projects/13").param("id", "13"))
                .andExpect(jsonPath("$.projectId", is(23)))
                .andExpect(status().isOk());*/
    }
}
