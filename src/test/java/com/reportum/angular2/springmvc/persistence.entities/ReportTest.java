package com.reportum.angular2.springmvc.persistence.entities;


import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ReportTest {

    private Report report;

    @Before
    public void setUp(){
        report=new Report();
        Project project = new Project();

        project.setProjectId(1L);

        report.setReportId(2L);
        report.setProject(project);
        report.setIssuePart("IssuePart");
        report.setPlanPart("PlanPart");
        report.setReviewPart("ReviewPart");
        report.setDate(new Date(25));
        report.setReportedBy("ReportedBy");
    }

    @Test
    public void getterAndSetterTest(){
        assertEquals(new Long(2L), report.getReportId());
        assertEquals(new Long(1L), report.getProject().getProjectId());
        assertEquals("IssuePart", report.getIssuePart());
        assertEquals("PlanPart", report.getPlanPart());
        assertEquals("ReviewPart", report.getReviewPart());
        assertEquals(new Date(25), report.getDate());
        assertEquals("ReportedBy", report.getReportedBy());
    }

}
