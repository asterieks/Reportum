package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.dao.IJobStateDAO;
import com.reportum.angular2.springmvc.persistence.entities.JobStateHolder;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.service.IEmailService;
import com.reportum.angular2.springmvc.service.IJobService;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.utils.enums.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

@Service
public class JobServiceImpl implements IJobService {

    private static final Long JOB_ID = 1L;
    private final Logger DEBUG_LOG = LoggerFactory.getLogger("DEBUG_LOGGER");

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IJobStateDAO jobStateDAO;

    @Autowired
    private IEmailService emailService;

    @Value("${admin.receiver.email}")
    private String adminEmail;

    @Value("${job.notification.title}")
    private String subject;

    @Value("${update.job.message}")
    private String updateMessage;

    @Value("${check.job.message}")
    private String remindMessage;

    @Override
    @Scheduled(cron = "${reset.state.time}")
    public void updateProjectsStates(){
        DEBUG_LOG.info("JobServiceImpl.updateProjectsStates(): Update state job started.");
        DEBUG_LOG.debug("adminEmail: "+ adminEmail);
        DEBUG_LOG.debug("subject: "+ subject);
        DEBUG_LOG.debug("updateMessage: "+ updateMessage);
        DEBUG_LOG.debug("remindMessage: "+ remindMessage);

        List<Project> projects=projectService.findAllProjects();
        if(!isEmpty(projects)){
            List<Project> updatedProjects=resetStateAndDate(projects);
            projectService.saveProjects(updatedProjects);
            JobStateHolder jobStateHolder = getJobStateHolder(JOB_ID);
            jobStateHolder.setStateDate(new Date());
            updateJobStateHolder(jobStateHolder);
            DEBUG_LOG.info("JobServiceImpl.updateProjectsStates(): Job completed.");
            try {
                emailService.sendSimpleMessage(adminEmail,subject,updateMessage);
            } catch (Exception e){
                DEBUG_LOG.debug("JobServiceImpl.updateProjectsStates(): failed while mail sending" + e.toString());
                e.printStackTrace();
            }
        }
    }

    @Override
    public JobStateHolder getJobStateHolder(Long id) {
        return jobStateDAO.getJobState(id);
    }

    @Override
    public void updateJobStateHolder(JobStateHolder jobStateHolder) {
        jobStateDAO.updateJobStateHolder(jobStateHolder);
    }

    @Override
    @Scheduled(cron = "${check.job.time}")
    public void checkAndRemind() {
        DEBUG_LOG.debug("JobServiceImpl.checkAndRemind(): Job check started.");
        List<Project> allProjects=projectService.findAllProjects();
        List<Project> delayedProjects = getDelayedProjects(allProjects);
        if(!isEmpty(delayedProjects)){
            DEBUG_LOG.debug("JobServiceImpl.checkAndRemind(): An notification has to be sent.");
            Map<String,String> receivers = mapProjectsToReceivers(delayedProjects);
            receivers.forEach((email,projects)->{
                try {
                    emailService.sendSimpleMessage(email,subject,buildMessage(projects));
                } catch (Exception e){
                    DEBUG_LOG.debug("JobServiceImpl.checkAndRemind(): failed while mail sending" + e.toString());
                    e.printStackTrace();
                }
            });
        }
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

    private List<Project> getDelayedProjects(List<Project> projects){
        return projects.stream()
                .filter(project ->
                        State.DELAYED.getValue().equals(project.getState())
                                || State.REPORTED.getValue().equals(project.getState())
                )
                .collect(Collectors.toList());
    }

    private Map<String,String> mapProjectsToReceivers(List<Project> projects) {
        Map<String,String> result = new HashMap<>();
        projects.forEach(project -> {
            String userEmail = project.getTeamLeader().getId();
            if(result.containsKey(userEmail)){
                String value = result.get(userEmail) +", "+ project.getProjectName();
                result.put(userEmail,value);
            } else {
                result.put(userEmail,project.getProjectName());
            }
        });
        return result;
    }

    private String buildMessage(String projects){
        return remindMessage + " " + projects;
    }
}
