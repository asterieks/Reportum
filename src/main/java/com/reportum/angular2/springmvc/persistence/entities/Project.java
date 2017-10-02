package com.reportum.angular2.springmvc.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PROJECTS")
public class Project implements Serializable{
    @Id
    @Column(name = "project_id", nullable = false)
    private Long projectId;


    @Column(name = "project_name")
    private String projectName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reporter")
    private User reporter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsible_team_leader")
    private User teamLeader;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager")
    private User manager;

    @Column(name = "state")
    private String state;

    @Column(name = "state_date")
    private Date stateDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<Report> projectList = new ArrayList<>();

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public User getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(User teamLeader) {
        this.teamLeader = teamLeader;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public List<Report> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Report> projectIdList) {
        this.projectList = projectIdList;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getStateDate() {
        return stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }
}
