package com.reportum.angular2.springmvc.dao.impl;

import com.reportum.angular2.springmvc.configuration.TestDataBaseConfig;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDataBaseConfig.class)
@Transactional
public class ReportDAOImplTest {
    @PersistenceContext
    EntityManager em;

    @InjectMocks
    private ReportDAOImpl reportDAO = new ReportDAOImpl();

    private Report report;
    private Project project;
    private List<Report> reports;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        reportDAO.setEm(em);

        report=new Report();
        report.setReportId(23L);
        project=new Project();
        project.setProjectId(22L);
        report.setProject(project);
        Date date=new Date();
        report.setDate(date);

        reports=new ArrayList<>();
        reports.add(report);

        /*em.persist(project);
        em.persist(report);*/
    }

    @Test
    public void findReports(){

    }

    @Test
    public void saveReportTest(){
        /*reportDAO.saveReport(report);
        Report actual = em.find(Report.class, 23L);
        assertEquals(new Long(23L), actual.getReportId());*/
    }
}
