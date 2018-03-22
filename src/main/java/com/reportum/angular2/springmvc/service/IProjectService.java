package com.reportum.angular2.springmvc.service;

import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;

import java.util.List;

public interface IProjectService {

    List<Project> findProjects(User user);

    Project findProject(Long id);

    void saveProject(Project project);

    void saveProjects(List<Project> projects);

    List<Project> findAllProjects();

    void deleteProject(Long projectId);

    Project prepareProject(Project project);
}
