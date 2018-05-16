package com.reportum.angular2.springmvc.dao;


import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;

import java.util.List;

public interface IProjectDAO {

    List<Project> findProjects(User user);

    Project findProject(Long id);

    void saveProject(Project project);

    List<Project> findAllProjects();

    void saveProjects(List<Project> projects);

    void deleteProject(Long id);

    List<Project> findAllActiveProjects();
}
