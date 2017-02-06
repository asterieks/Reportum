package com.reportum.angular2.springmvc.jobs.impl;

import com.reportum.angular2.springmvc.jobs.JobSchedulerService;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.utils.enums.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

@Service
@EnableScheduling
public class JobSchedulerServiceImpl implements JobSchedulerService {

    @Autowired
    private IProjectService projectService;

    private final Logger LOGGER = LoggerFactory.getLogger(JobSchedulerServiceImpl.class);

    @Override
    @Scheduled(cron = "0 0 0 * * MON")
    public boolean updateProjectsStates(){
        LOGGER.debug("Job started.");
        System.out.println("Started "+ new Date());

        List<Project> projects=projectService.findAllProjects();
        if(!isEmpty(projects)){
            List<Project> updatedProjects=resetStateAndDate(projects);
            projectService.saveProjects(updatedProjects);
            LOGGER.debug("Job completed.");
            System.out.println("completed");
            return true;
        }
        return false;
    }

    private List<Project> resetStateAndDate(List<Project> projects) {
        List<Project> resultList=new ArrayList<>();
        projects.forEach(project -> {
            project.setState(State.DELAYED.getValue());
            project.setStateDate(null);
            resultList.add(project);
        });
        return resultList;
    }
}
