package com.reportum.angular2.springmvc.service.impl;


import com.reportum.angular2.springmvc.dao.IProjectDAO;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IProjectService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class ProjectServiceImplTest {

    @Mock
    private IProjectDAO projectDAO;

    @InjectMocks
    private IProjectService projectService=new ProjectServiceImpl();

    private Project project;
    private List<Project> projects=new ArrayList<>();
    private User user;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        project=new Project();
        user=new User();
    }

    @Test
    public void findProjectsTest(){
        projectService.findProjects(user);
        verify(projectDAO).findProjects(user);
    }

    @Test
    public void findProjectTest(){
        projectService.findProject(1L);
        verify(projectDAO).findProject(1L);
    }

    @Test
    public void saveProjectTest(){
        projectService.saveProject(project);
        verify(projectDAO).saveProject((Project)any());
    }

    @Test
    public void saveProjectsTest(){
        projectService.saveProjects(projects);
        verify(projectDAO).saveProjects(projects);
    }

    @Test
    public void findAllProjectsTest(){
        projectService.findAllProjects();
        verify(projectDAO).findAllProjects();
    }
}
