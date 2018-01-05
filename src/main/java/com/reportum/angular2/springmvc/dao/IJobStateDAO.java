package com.reportum.angular2.springmvc.dao;

import com.reportum.angular2.springmvc.persistence.entities.JobStateHolder;

public interface IJobStateDAO {

    JobStateHolder getJobState(Long id);

    void updateJobStateHolder(JobStateHolder jobStateHolder);
}
