package com.reportum.angular2.springmvc.jobs.impl;

import com.reportum.angular2.springmvc.jobs.JobSchedulerService;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.service.IReportService;
import com.reportum.angular2.springmvc.utils.enums.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

@Service
@EnableScheduling
public class JobSchedulerServiceImpl implements JobSchedulerService {

    @Autowired
    private IReportService reportService;

    @Autowired
    private IProjectService projectService;


    private final Logger LOGGER = LoggerFactory.getLogger(JobSchedulerServiceImpl.class);

    @Override
    @Scheduled(cron = "0 0 * * * MON")
    public boolean updateProjectsStates(){
        LOGGER.debug("Job started.");

        List<Project> projects=projectService.findAllProjects();
        List<Report> reports=reportService.findAllActualReports();
        if(!isEmpty(projects)&&!isEmpty(reports)){
            List<Project> updatedProjects=getUpdated(projects, reports);
            projectService.saveProjects(updatedProjects);
            LOGGER.debug("Job completed.");
            return true;
        }
        return false;
    }

    private List<Project> getUpdated(List<Project> projects, List<Report> reports) {
        List<Project> resultList=new ArrayList<>();

        List<Project> updatedList=new ArrayList<>();
        List<Project> notUpdatedList=new ArrayList<>();
        divideInTwo(projects, reports, updatedList, notUpdatedList);

        updateState(notUpdatedList);
        updateState(updatedList,reports, State.UPDATED.getValue());

        resultList.addAll(notUpdatedList);
        resultList.addAll(updatedList);

        return resultList;
    }

    private void updateState(List<Project> updatedList, List<Report> reports, String state) {
        updatedList.forEach(project -> {
            Long projectId1=project.getProjectId();
            reports.forEach(report -> {
                Long projectId2=report.getProject().getProjectId();
                if(projectId1!=null && projectId2!=null && projectId1.equals(projectId2)){
                    project.setState(state);
                    project.setStateDate(report.getDate());
                }
            });
        });
    }

    private void updateState(List<Project> notUpdatedList) {
        notUpdatedList.forEach(project -> {
            project.setState(null);
            project.setStateDate(null);
        });
    }

    private void divideInTwo(List<Project> projects, List<Report> reports, List<Project> updatedList, List<Project> notUpdatedList) {
        projects.forEach(project -> {
            Long projectId1=project.getProjectId();
            reports.forEach(report -> {
                Long projectId2=report.getProject().getProjectId();
                if(projectId1!=null && projectId2!=null && projectId1.equals(projectId2)){
                    updatedList.add(project);
                }
            });
        });
        notUpdatedList.addAll(projects);
        notUpdatedList.removeAll(updatedList);
    }
}
