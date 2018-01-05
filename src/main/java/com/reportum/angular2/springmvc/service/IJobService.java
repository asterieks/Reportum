package com.reportum.angular2.springmvc.service;

import com.reportum.angular2.springmvc.persistence.entities.JobStateHolder;

public interface IJobService {

    void updateProjectsStates();

    JobStateHolder getJobStateHolder(Long id);

    void updateJobStateHolder(JobStateHolder jobStateHolder);

    void checkStateAndUpdate();
}
