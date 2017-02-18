package com.reportum.angular2.springmvc.service.impl;


import com.reportum.angular2.springmvc.dao.IReportDAO;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IReportService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

public class ReportServiceImplTest {

    @Mock
    private IReportDAO reportDAO;

    @InjectMocks
    private IReportService reportService=new ReportServiceImpl();

    private Report report;
    private List<Project> projects=new ArrayList<>();
    private User user;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        report=new Report();
        List<Project> projects=new ArrayList<>();
    }

    @Test
    public void saveReportTest(){
        reportService.saveReport(report);
        verify(reportDAO).saveReport(report);
    }

    @Test
    public void findReportTest(){
        reportService.findReport(1L);
        verify(reportDAO).findReport(1L);
    }

    @Test
    public void findReportsTest(){
        reportService.findReports(projects);
        verify(reportDAO).findReports(projects);
    }

    @Test
    public void findAllActualReportsTest(){
        reportService.findAllActualReports();
        verify(reportDAO).findAllActualReports();
    }
}
