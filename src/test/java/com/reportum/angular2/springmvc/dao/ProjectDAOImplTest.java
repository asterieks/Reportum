package com.reportum.angular2.springmvc.dao;


import com.reportum.angular2.springmvc.configuration.TestDataSourceConfig;
import com.reportum.angular2.springmvc.dao.impl.ProjectDAOImpl;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.utils.enums.Profile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestDataSourceConfig.class)
@Transactional
public class ProjectDAOImplTest {

    @PersistenceContext
    private EntityManager em;

    @InjectMocks
    private ProjectDAOImpl projectDAO=new ProjectDAOImpl();

    private User reporter;
    private User reporter1;
    private User manager;
    private User lead;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        projectDAO.setEm(em);

        reporter = new User();
        reporter.setId("r");
        reporter.setProfile(Profile.REPORTER);
        reporter.setFullName("");
        reporter.setPassword("");
        reporter = em.merge(reporter);

        reporter1 = new User();
        reporter1.setId("r1");
        reporter1.setProfile(Profile.REPORTER);
        reporter1.setFullName("");
        reporter1.setPassword("");
        reporter1 = em.merge(reporter1);

        manager = new User();
        manager.setId("m");
        manager.setProfile(Profile.MANAGER);
        manager.setFullName("");
        manager.setPassword("");
        manager = em.merge(manager);

        lead = new User();
        lead.setId("l");
        lead.setProfile(Profile.LEAD);
        lead.setFullName("");
        lead.setPassword("");
        lead = em.merge(lead);
    }

    @Test
    public void findProjectsTest() {
        Project project1 = new Project();
        project1.setProjectId(1L);
        project1.setReporter(reporter);
        project1.setManager(manager);
        em.merge(project1);

        Project project11 = new Project();
        project11.setProjectId(11L);
        project11.setReporter(reporter);
        project11.setTeamLeader(lead);
        em.merge(project11);

        Project project111 = new Project();
        project111.setProjectId(111L);
        project111.setReporter(reporter1);
        project111.setTeamLeader(lead);
        em.merge(project111);

        List<Project> actual = projectDAO.findProjects(reporter);
        assertEquals(2, actual.size());
        assertEquals(new Long(1), actual.get(0).getProjectId());
        assertEquals(new Long(11), actual.get(1).getProjectId());

        actual = projectDAO.findProjects(reporter1);
        assertEquals(1, actual.size());
        assertEquals(new Long(111), actual.get(0).getProjectId());

        actual = projectDAO.findProjects(manager);
        assertEquals(1, actual.size());
        assertEquals(new Long(1), actual.get(0).getProjectId());

        actual = projectDAO.findProjects(lead);
        assertEquals(2, actual.size());
        assertEquals(new Long(11), actual.get(0).getProjectId());
        assertEquals(new Long(111), actual.get(1).getProjectId());
    }

    @Test
    public void saveProjectTest(){
        Project project1 = new Project();
        project1.setProjectId(100L);

        projectDAO.saveProject(project1);
        Project actual = em.find(Project.class, 100L);
        assertEquals(new Long(100), actual.getProjectId());
    }

    @Test
    public void findProjectTest(){
        Project project1 = new Project();
        project1.setProjectId(190L);
        em.merge(project1);

        Project actual = projectDAO.findProject(190L);
        assertEquals(new Long(190), actual.getProjectId());
    }

    @Test
    public void findAllProjectsTest(){
        Project project1 = new Project();
        project1.setProjectId(9L);
        em.merge(project1);

        Project project11 = new Project();
        project11.setProjectId(99L);
        em.merge(project11);

        List<Project> actual = projectDAO.findAllProjects();
        assertEquals(2, actual.size());
        assertEquals(new Long(9), actual.get(0).getProjectId());
        assertEquals(new Long(99), actual.get(1).getProjectId());
    }

    @Test
    public void saveProjectsTest(){
        Project project1 = new Project();
        project1.setProjectId(8L);

        Project project11 = new Project();
        project11.setProjectId(88L);

        List<Project> list = new ArrayList<>();
        list.add(project1);
        list.add(project11);

        projectDAO.saveProjects(list);
        Project actual = em.find(Project.class, 8L);
        assertEquals(new Long(8), actual.getProjectId());

        actual = em.find(Project.class, 88L);
        assertEquals(new Long(88), actual.getProjectId());
    }
}
