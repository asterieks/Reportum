package com.reportum.angular2.springmvc.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "JOB_STATE")
public class JobStateHolder {
    @Id
    @Column(name = "job_id", nullable = false, unique=true)
    private Long jobId;

    @Column (name = "job_name")
    private String jobName;

    @Column (name = "state_date")
    private Date stateDate;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Date getStateDate() {
        return stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }
}
