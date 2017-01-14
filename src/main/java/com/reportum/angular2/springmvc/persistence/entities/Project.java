package com.reportum.angular2.springmvc.persistence.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PROJECTS")
public class Project {
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectId")
    private List<Report> projectIdList = new ArrayList<>();

    public Project (){}

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

    public List<Report> getProjectIdList() {
        return projectIdList;
    }

    public void setProjectIdList(List<Report> projectIdList) {
        this.projectIdList = projectIdList;
    }
}
