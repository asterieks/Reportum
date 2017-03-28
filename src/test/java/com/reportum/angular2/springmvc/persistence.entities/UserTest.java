package com.reportum.angular2.springmvc.persistence.entities;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserTest {
    private User user;
    private List<Project> reporterList = new ArrayList<>();
    private List<Project> teamLeaderList = new ArrayList<>();
    private List<Project> managerList = new ArrayList<>();
    private Project project;

    @Before
    public void setUp(){
        user=new User();
        project=new Project();

        user.setFullName("FullName");
        project.setReporter(user);

        reporterList.add(project);
        teamLeaderList.add(project);
        managerList.add(project);

        user.setId("userId");
        user.setReporterList(reporterList);
        user.setTeamLeaderList(teamLeaderList);
        user.setManagerList(managerList);
    }

    @Test
    public void getterAndsetterTest(){
        assertEquals("userId", user.getId());
        assertEquals("FullName", user.getFullName());
        assertEquals("FullName", user.getReporterList().get(0).getReporter().getFullName());
        assertEquals("FullName", user.getTeamLeaderList().get(0).getReporter().getFullName());
        assertEquals("FullName", user.getManagerList().get(0).getReporter().getFullName());
    }

}
