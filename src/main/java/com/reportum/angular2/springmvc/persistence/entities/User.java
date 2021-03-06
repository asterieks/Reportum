package com.reportum.angular2.springmvc.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.reportum.angular2.springmvc.utils.enums.Profile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "USERS")
public class User implements Serializable {
    @Id
    @Column (name = "user_id", nullable = false)
    private String id;
    @Column (name = "full_name", nullable = false)
    private String fullName;
    @Column (name = "password", nullable = false)
    private String password;
    @Column (name = "profile")
    @Enumerated(EnumType.STRING)
    private Profile profile;
    @Column (name = "authorities")
    private String authorities;
    @JsonIgnore
    @Column (name = "private_secret")
    private String privateSecret;
    @JsonIgnore
    @Column (name = "public_secret")
    private String publicSecret;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reporter")
    private List<Project> reporterList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teamLeader")
    private List<Project> teamLeaderList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
    private List<Project> managerList = new ArrayList<>();

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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
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

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public String getPrivateSecret() {
        return privateSecret;
    }

    public void setPrivateSecret(String privateSecret) {
        this.privateSecret = privateSecret;
    }

    public String getPublicSecret() {
        return publicSecret;
    }

    public void setPublicSecret(String publicSecret) {
        this.publicSecret = publicSecret;
    }
}
