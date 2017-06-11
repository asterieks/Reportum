package com.reportum.angular2.springmvc.persistence.entities;


import com.reportum.angular2.springmvc.utils.enums.Profile;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.reportum.angular2.springmvc.utils.enums.Profile.REPORTER;
import static org.junit.Assert.assertEquals;

public class UserTest {
    private User user;
    private List<Project> reporterList = new ArrayList<>();
    private List<Project> teamLeaderList = new ArrayList<>();
    private List<Project> managerList = new ArrayList<>();
    private Project project;
    private Profile profile;

    @Before
    public void setUp(){
        user=new User();
        project=new Project();
        profile=REPORTER;

        user.setFullName("FullName");
        project.setReporter(user);

        reporterList.add(project);
        teamLeaderList.add(project);
        managerList.add(project);

        user.setId("userId");
        user.setReporterList(reporterList);
        user.setTeamLeaderList(teamLeaderList);
        user.setManagerList(managerList);
        user.setProfile(profile);
        user.setPassword("password");
        user.setAuthorities("authorities");
        user.setPrivateSecret("PrivateSecret");
        user.setPublicSecret("PublicSecret");
    }

    @Test
    public void getterAndSetterTest(){
        assertEquals("userId", user.getId());
        assertEquals("FullName", user.getFullName());
        assertEquals("FullName", user.getReporterList().get(0).getReporter().getFullName());
        assertEquals("FullName", user.getTeamLeaderList().get(0).getReporter().getFullName());
        assertEquals("FullName", user.getManagerList().get(0).getReporter().getFullName());
        assertEquals(REPORTER, user.getProfile());
        assertEquals("password", user.getPassword());
        assertEquals("authorities", user.getAuthorities());
        assertEquals("PrivateSecret", user.getPrivateSecret());
        assertEquals("PublicSecret", user.getPublicSecret());
    }

}
