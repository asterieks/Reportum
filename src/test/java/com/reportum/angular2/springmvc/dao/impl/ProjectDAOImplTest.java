package com.reportum.angular2.springmvc.dao.impl;

import com.reportum.angular2.springmvc.configuration.TestDataBaseConfig;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.utils.enums.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDataBaseConfig.class)
@Transactional
public class ProjectDAOImplTest {
    @PersistenceContext
    EntityManager em;

    @Mock
    private CriteriaQuery<Project> criteriaQuery;

    @InjectMocks
    private ProjectDAOImpl projectDAO = new ProjectDAOImpl();

    private Project project;
    private User reporter;
    private List<Project> projects;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        projectDAO.setEm(em);

        reporter=new User();
        reporter.setId("22");
        reporter.setFullName("fullName");
        reporter.setRole(Role.REPORTER);
        project=new Project();
        project.setProjectId(12L);
        project.setReporter(reporter);

        projects=new ArrayList<>();
        projects.add(project);

        em.persist(project);
        em.persist(reporter);

    }

    @Test
    public void findProjectsTest(){
        List<Project> projects = projectDAO.findProjects(reporter);
        Project actual = em.find(Project.class, 12L);
        assertEquals(projects.get(0).getProjectId(), actual.getProjectId());
    }

    @Test
    public void findProjectTest(){
        Project project=projectDAO.findProject(12L);
        Project actual = em.find(Project.class, 12L);
        assertEquals(project.getProjectId(), actual.getProjectId());
    }

    @Test
    public void saveProjectTest(){
        projectDAO.saveProject(project);
        Project actual = em.find(Project.class, 12L);
        assertEquals(new Long(12L), actual.getProjectId());
    }

    @Test
    public void findAllProjectsTest(){
        projectDAO.saveProjects(projects);
        Project actual = em.find(Project.class, 12L);
        assertEquals(new Long(12L), actual.getProjectId());
    }

    @Test
    public void  saveProjectsTest(){
        projectDAO.saveProjects(projects);
        Project actual = em.find(Project.class, 12L);
        assertEquals(new Long(12L), actual.getProjectId());
    }
}
