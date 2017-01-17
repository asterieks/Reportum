package com.reportum.angular2.springmvc.service;

import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;

import java.util.List;

public interface IProjectService {

    List<Project> getProjectsByReporter(User reporter);

    List<Project> getProjectsByTeamLead(User teamLeader);

    Project getProjectsByProjectId(Long id);

    void save(Project project);
}
