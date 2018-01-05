package com.reportum.angular2.springmvc.service.impl;


import com.reportum.angular2.springmvc.dao.IJobStateDAO;
import com.reportum.angular2.springmvc.persistence.entities.JobStateHolder;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.service.IJobService;
import com.reportum.angular2.springmvc.service.IProjectService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class JobServiceImplTest {

    @Mock
    private IProjectService projectService;

    @Mock
    private IJobStateDAO jobStateDAO;

    @InjectMocks
    private IJobService jobService = new JobServiceImpl();

    private Project project;
    private JobStateHolder jobStateHolder;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        project=new Project();
        project.setProjectId(25L);

        jobStateHolder = new JobStateHolder();
    }

    @Test
    public void updateProjectsStatesTest(){
        org.springframework.scheduling.support.CronTrigger trigger =
                new CronTrigger("0 0 0 * * MON");
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        today.add(Calendar.DAY_OF_WEEK, -1);

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss EEEE");
        final Date prevMonday = today.getTime();

        log.println("Previous Monday was : " + df.format(prevMonday));
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
        log.println(message);
        assertThat(message).contains("00:00:00 Monday");

        when(jobStateDAO.getJobState(1L)).thenReturn(new JobStateHolder());

        jobService.updateProjectsStates();
        verify(projectService).findAllProjects();
        verify(projectService, never()).saveProjects(anyList());
        verify(jobStateDAO,never()).updateJobStateHolder(any());

        Mockito.reset(projectService);
        List<Project> projects=new ArrayList<>();
        projects.add(project);
        when(projectService.findAllProjects()).thenReturn(projects);

        jobService.updateProjectsStates();
        verify(projectService).findAllProjects();
        verify(projectService).saveProjects(anyList());
        verify(jobStateDAO).updateJobStateHolder(any());
    }

    @Test
    public void getJobStateHolderTest(){
        jobService.getJobStateHolder(1L);
        verify(jobStateDAO).getJobState(1L);
    }

    @Test
    public void updateJobStateHolderTest(){
        jobService.updateJobStateHolder(jobStateHolder);
        verify(jobStateDAO).updateJobStateHolder(jobStateHolder);
    }

    @Test
    public void checkStateAndUpdateTest(){
        jobStateHolder.setStateDate(new Date());
        when(jobStateDAO.getJobState(1L)).thenReturn(jobStateHolder);

        jobService.checkStateAndUpdate();
        verify(jobStateDAO).getJobState(1L);
        verify(projectService, never()).findAllProjects();

        Mockito.reset(projectService);
        Mockito.reset(jobStateDAO);

        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_WEEK, -10);
        jobStateHolder.setStateDate(today.getTime());
        when(jobStateDAO.getJobState(1L)).thenReturn(jobStateHolder);

        jobService.checkStateAndUpdate();
        verify(jobStateDAO).getJobState(1L);
        verify(projectService).findAllProjects();
    }
}
