package com.reportum.angular2.springmvc.dao;

import com.reportum.angular2.springmvc.configuration.TestDataSourceConfig;
import com.reportum.angular2.springmvc.dao.impl.ReportDAOImpl;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestDataSourceConfig.class)
@Transactional
public class ReportDAOImplTest {

    @PersistenceContext
    private EntityManager em;

    @InjectMocks
    private ReportDAOImpl reportDAO=new ReportDAOImpl();

    private User reporter;
    private User reporter1;
    private User manager;
    private User lead;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        reportDAO.setEm(em);

        reporter = new User();
        reporter.setId("r2");
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
    public void saveReportTest() {
        Report report = new Report();
        report.setDate(new Date());
        report = reportDAO.saveReport(report);

        boolean actual = em.contains(report);
        assertTrue(actual);
        em.clear();
    }

    @Test
    public void findReportTest() {
        Report report = new Report();
        report.setDate(new Date());
        report = em.merge(report);

        Long id = report.getReportId();
        Report actual = reportDAO.findReport(id);
        assertEquals(id, actual.getReportId());
        em.clear();
    }

    @Test
    public void findReportsTest() {
        Project project1 = new Project();
        project1.setProjectId(1L);
        em.merge(project1);

        Project project11 = new Project();
        project11.setProjectId(11L);
        em.merge(project11);

        Project project111 = new Project();
        project111.setProjectId(111L);
        em.merge(project111);

        Report report = new Report();
        report.setDate(new Date());
        report.setProject(project1);
        em.merge(report);

        Report report1 = new Report();
        report1.setDate(new Date());
        report1.setProject(project1);
        em.merge(report1);

        Report report2 = new Report();
        report2.setDate(new Date());
        report2.setProject(project11);
        em.merge(report2);

        Report report3 = new Report();
        report3.setDate(new Date());
        report3.setProject(project111);
        em.merge(report3);

        List<Project> list = new ArrayList<>();
        list.add(project1);
        list.add(project11);

        List<Report> actual = reportDAO.findReports(list);
        assertEquals(3, actual.size());
        assertEquals(new Long(1), actual.get(0).getProject().getProjectId());
        assertEquals(new Long(1), actual.get(1).getProject().getProjectId());
        assertEquals(new Long(11), actual.get(2).getProject().getProjectId());
        em.clear();
    }

    @Test
    public void findAllActualReportsTest() {
        Report report = new Report();
        report.setDate(new Date());
        em.merge(report);

        Report report1 = new Report();
        report1.setDate(new Date());
        em.merge(report1);

        Report report2 = new Report();
        report2.setDate(new Date());
        em.merge(report2);

        Report report3 = new Report();
        report3.setDate(new Date());
        em.merge(report3);

        List<Report> actual = reportDAO.findAllActualReports();
        assertEquals(4, actual.size());
        em.clear();
    }

    @Test
    public void findReportByProjectIdTest() {
        Project project1 = new Project();
        project1.setProjectId(13L);
        project1 = em.merge(project1);

        Project project11 = new Project();
        project11.setProjectId(113L);
        em.merge(project11);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_MONTH,-1);
        Date weekAgoDate = calendar.getTime();
        calendar.add(Calendar.WEEK_OF_MONTH,-2);
        Date twoWeekAgoDate = calendar.getTime();

        Report report = new Report();
        report.setDate(weekAgoDate);
        report.setProject(project1);
        em.merge(report);

        Report report1 = new Report();
        report1.setDate(twoWeekAgoDate);
        report1.setProject(project1);
        em.merge(report1);

        Report report2 = new Report();
        report2.setDate(new Date());
        report2.setProject(project1);
        report2 = em.merge(report2);

        Report report3 = new Report();
        report3.setDate(new Date());
        report3.setProject(project11);
        em.merge(report3);

        Report actual = reportDAO.findReportByProjectId(project1.getProjectId());
        assertEquals(project1.getProjectId(), actual.getProject().getProjectId());
        assertEquals(report2.getReportId(), actual.getReportId());
        assertNull(reportDAO.findReportByProjectId(333L));
        em.clear();
    }

    @Test
    public void findPrevReportByProjectIdTest() {
        Project project1 = new Project();
        project1.setProjectId(13L);
        project1 = em.merge(project1);

        Project project11 = new Project();
        project11.setProjectId(113L);
        em.merge(project11);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR,-1);
        Date weekAgoDate = calendar.getTime();
        calendar.add(Calendar.WEEK_OF_YEAR,-1);
        Date twoWeekAgoDate = calendar.getTime();

        Report report = new Report();
        report.setDate(twoWeekAgoDate);
        report.setProject(project1);
        em.merge(report);

        Report report1 = new Report();
        report1.setDate(weekAgoDate);
        report1.setProject(project1);
        report1 = em.merge(report1);

        Report report2 = new Report();
        report2.setDate(new Date());
        report2.setProject(project1);
        em.merge(report2);

        Report report3 = new Report();
        report3.setDate(new Date());
        report3.setProject(project11);
        em.merge(report3);

        Report actual = reportDAO.findPrevReportByProjectId(project1.getProjectId());
        assertEquals(project1.getProjectId(), actual.getProject().getProjectId());
        assertEquals(report1.getReportId(), actual.getReportId());
        assertNull(reportDAO.findPrevReportByProjectId(334L));
        em.clear();
    }
}
