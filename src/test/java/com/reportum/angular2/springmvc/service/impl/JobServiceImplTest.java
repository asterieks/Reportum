package com.reportum.angular2.springmvc.service.impl;


import com.reportum.angular2.springmvc.dao.IJobStateDAO;
import com.reportum.angular2.springmvc.persistence.entities.JobStateHolder;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IEmailService;
import com.reportum.angular2.springmvc.service.IJobService;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.utils.enums.State;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class JobServiceImplTest {

    @Mock
    private IProjectService projectService;

    @Mock
    private IJobStateDAO jobStateDAO;

    @Mock
    private IEmailService emailService;

    @InjectMocks
    private IJobService jobService = new JobServiceImpl();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateProjectsStatesCronTest(){
        CronTrigger trigger = new CronTrigger("0 0 0 * * MON");
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        today.add(Calendar.DAY_OF_WEEK, -1);

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss EEEE");
        final Date prevMonday = today.getTime();

        Date nextExecutionTime = trigger.nextExecutionTime(
                new TriggerContext() {

                    @Override
                    public Date lastScheduledExecutionTime() {
                        return prevMonday;
                    }

                    @Override
                    public Date lastActualExecutionTime() {
                        return prevMonday;
                    }

                    @Override
                    public Date lastCompletionTime() {
                        return prevMonday;
                    }
                });

        String message = "Next Execution date: " + df.format(nextExecutionTime);
        assertThat(message).contains("00:00:00 Monday");
    }

    @Test
    public void updateProjectsStatesEmptyTest(){
        when(jobStateDAO.getJobState(1L)).thenReturn(new JobStateHolder());

        jobService.updateProjectsStates();
        verify(projectService).findAllProjects();
        verify(projectService, never()).saveProjects(anyList());
        verify(jobStateDAO,never()).updateJobStateHolder(any());
        verify(emailService,never()).sendSimpleMessage(any(),any(),any());
    }

    @Test
    public void updateProjectsStatesTest(){
        when(jobStateDAO.getJobState(1L)).thenReturn(new JobStateHolder());

        Project project=new Project();
        project.setProjectId(25L);
        List<Project> projects=new ArrayList<>();
        projects.add(project);
        when(projectService.findAllProjects()).thenReturn(projects);

        jobService.updateProjectsStates();
        verify(projectService).findAllProjects();
        verify(projectService).saveProjects(anyList());
        verify(jobStateDAO).updateJobStateHolder(any());
        verify(emailService).sendSimpleMessage(any(),any(),any());
    }


    @Test
    public void getJobStateHolderTest(){
        jobService.getJobStateHolder(1L);
        verify(jobStateDAO).getJobState(1L);
    }

    @Test
    public void updateJobStateHolderTest(){
        JobStateHolder jobStateHolder = new JobStateHolder();
        jobService.updateJobStateHolder(jobStateHolder);
        verify(jobStateDAO).updateJobStateHolder(jobStateHolder);
    }

    @Test
    public void checkAndRemindCronTest(){
        CronTrigger trigger = new CronTrigger("0 0 0 * * FRI");
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss EEEE");
        final Date friday = today.getTime();

        Date nextExecutionTime = trigger.nextExecutionTime(
                new TriggerContext() {

                    @Override
                    public Date lastScheduledExecutionTime() {
                        return friday;
                    }

                    @Override
                    public Date lastActualExecutionTime() {
                        return friday;
                    }

                    @Override
                    public Date lastCompletionTime() {
                        return friday;
                    }
                });

        String message = "Next Execution date: " + df.format(nextExecutionTime);
        assertThat(message).contains("00:00:00 Friday");
    }

    @Test
    public void checkAndRemindEmptyTest(){
        jobService.checkAndRemind();
        verify(projectService).findAllProjects();
        verify(emailService,never()).sendSimpleMessage(any(),any(),any());
    }

    @Test
    public void checkAndRemindNoNotificationTest(){
        User team1= new User();
        team1.setId("x@gmail.com");
        Project project1=new Project();
        project1.setProjectId(26L);
        project1.setProjectName("X1REP");
        project1.setState(State.REVIEWED.getValue());
        project1.setTeamLeader(team1);

        Project project2=new Project();
        project2.setProjectId(27L);
        project2.setProjectName("X2REW");
        project2.setState(State.REVIEWED.getValue());
        project2.setTeamLeader(team1);

        List<Project> projects=new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        when(projectService.findAllProjects()).thenReturn(projects);

        jobService.checkAndRemind();
        verify(projectService).findAllProjects();
        verify(emailService,never()).sendSimpleMessage(any(),any(),any());
    }

    @Test
    public void checkAndRemindTest(){
        User team1= new User();
        team1.setId("x@gmail.com");
        Project project1=new Project();
        project1.setProjectId(26L);
        project1.setProjectName("X1REP");
        project1.setState(State.REPORTED.getValue());
        project1.setTeamLeader(team1);

        Project project2=new Project();
        project2.setProjectId(27L);
        project2.setProjectName("X2REW");
        project2.setState(State.REVIEWED.getValue());
        project2.setTeamLeader(team1);

        Project project3=new Project();
        project3.setProjectId(28L);
        project3.setProjectName("X3DEL");
        project3.setState(State.DELAYED.getValue());
        project3.setTeamLeader(team1);

        User team2= new User();
        team2.setId("z@gmail.com");
        Project project4=new Project();
        project4.setProjectId(29L);
        project4.setProjectName("Z4DEL");
        project4.setState(State.DELAYED.getValue());
        project4.setTeamLeader(team2);

        Project project5=new Project();
        project5.setProjectId(30L);
        project5.setProjectName("Z5REW");
        project5.setState(State.REVIEWED.getValue());
        project5.setTeamLeader(team2);

        Project project6=new Project();
        project6.setProjectId(31L);
        project6.setProjectName("Z6DEL");
        project6.setState(State.DELAYED.getValue());
        project6.setTeamLeader(team2);

        List<Project> projects=new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        projects.add(project4);
        projects.add(project5);
        projects.add(project6);
        when(projectService.findAllProjects()).thenReturn(projects);

        jobService.checkAndRemind();
        verify(projectService).findAllProjects();
        verify(emailService).sendSimpleMessage("x@gmail.com",null,"null X1REP, X3DEL");
        verify(emailService).sendSimpleMessage("z@gmail.com",null,"null Z4DEL, Z6DEL");
    }
}
