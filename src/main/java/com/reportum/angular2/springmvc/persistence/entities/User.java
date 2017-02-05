package com.reportum.angular2.springmvc.persistence.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @Column (name = "user_id", nullable = false)
    private String id;
    @Column (name = "full_name", nullable = false)
    private String fullName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role")
    private Role role;

    @Column (name = "password", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reporter")
    private List<Project> reporterList = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teamLeader")
    private List<Project> teamLeaderList = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
    private List<Project> managerList = new ArrayList<>();

    public User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Project> getReporterList() {
        return reporterList;
    }

    public void setReporterList(List<Project> reporterList) {
        this.reporterList = reporterList;
    }

    public List<Project> getTeamLeaderList() {
        return teamLeaderList;
    }

    public void setTeamLeaderList(List<Project> teamLeaderList) {
        this.teamLeaderList = teamLeaderList;
    }

    public List<Project> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<Project> managerList) {
        this.managerList = managerList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
