package com.reportum.angular2.springmvc.persistence.entities;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProjectTest {

    private Project project;
    private User reporter;
    private User teamLeader;
    private User manager;
    private List<Report> projectList;
    private Report report;

    @Before
    public void setUp(){
        project=new Project();
        reporter=new User();
        teamLeader=new User();
        manager=new User();
        projectList=new ArrayList<>();
        report=new Report();

        reporter.setFullName("Reporter");
        teamLeader.setFullName("TeamLeader");
        manager.setFullName("Manager");
        report.setReportId(2L);
        projectList.add(report);

        project.setProjectId(1L);
        project.setProjectName("ProjectName");
        project.setState("state");
        project.setStateDate(new Date(25));
        project.setReporter(reporter);
        project.setTeamLeader(teamLeader);
        project.setManager(manager);
        project.setProjectList(projectList);
    }

    @Test
    public void getterAndsetterTest(){
        assertEquals(new Long(1L), project.getProjectId());
        assertEquals("ProjectName", project.getProjectName());
        assertEquals("state", project.getState());
        assertEquals(new Date(25), project.getStateDate());
        assertEquals("Reporter", project.getReporter().getFullName());
        assertEquals("TeamLeader", project.getTeamLeader().getFullName());
        assertEquals("Manager", project.getManager().getFullName());
        assertEquals(new Long(2L), project.getProjectList().get(0).getReportId());
    }
}
