package com.reportum.angular2.springmvc.jobs.impl;


import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IProjectService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class JobSchedulerServiceImplTest {

    @Mock
    private IProjectService projectService;

    @InjectMocks
    private JobSchedulerServiceImpl jobSchedulerService=new JobSchedulerServiceImpl();

    private Project project;
    private List<Project> projects=new ArrayList<>();
    private User user;

    public MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(jobSchedulerService).build();

        project=new Project();
        project.setProjectId(25L);

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

        boolean actual = jobSchedulerService.updateProjectsStates();
        verify(projectService).findAllProjects();
        verify(projectService, never()).saveProjects(anyList());
        assertThat(actual).isFalse();

        Mockito.reset(projectService);
        List<Project> projects=new ArrayList<>();
        projects.add(project);
        when(projectService.findAllProjects()).thenReturn(projects);

        actual = jobSchedulerService.updateProjectsStates();
        verify(projectService).findAllProjects();
        verify(projectService).saveProjects(anyList());
        assertThat(actual).isTrue();
    }
}
