package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.dao.IJobStateDAO;
import com.reportum.angular2.springmvc.persistence.entities.JobStateHolder;
import com.reportum.angular2.springmvc.service.IJobService;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.utils.DateUtils;
import com.reportum.angular2.springmvc.utils.enums.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

@Service
public class JobServiceImpl implements IJobService {

    private static final Long JOB_ID = 1L;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IJobStateDAO jobStateDAO;

    private final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Override
    @Scheduled(cron = "0 0 0 * * MON")
    public void updateProjectsStates(){
        logger.debug("JobServiceImpl.updateProjectsStates(): Job started.");

        List<Project> projects=projectService.findAllProjects();
        if(!isEmpty(projects)){
            List<Project> updatedProjects=resetStateAndDate(projects);
            projectService.saveProjects(updatedProjects);
            JobStateHolder jobStateHolder = getJobStateHolder(JOB_ID);
            jobStateHolder.setStateDate(new Date());
            updateJobStateHolder(jobStateHolder);
            logger.debug("JobServiceImpl.updateProjectsStates(): Job completed.");
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
    public void checkStateAndUpdate() {
        logger.debug("JobServiceImpl.checkStateAndUpdate(): Job check started.");
        JobStateHolder jobStateHolder = getJobStateHolder(JOB_ID);
        Date lastUpdateDate = jobStateHolder.getStateDate();
        Date thisMondayDate = DateUtils.getThisWeekMondayDate();
        if (thisMondayDate.getTime()>lastUpdateDate.getTime()){
            logger.debug("JobServiceImpl.checkStateAndUpdate(): Job was not updated.");
            updateProjectsStates();
        }
        logger.debug("JobServiceImpl.checkStateAndUpdate(): Job was updated correctly.");
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
