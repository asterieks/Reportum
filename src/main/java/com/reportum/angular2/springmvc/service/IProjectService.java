package com.reportum.angular2.springmvc.service;

import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;

import java.util.List;

public interface IProjectService {

    List<Project> getProjectsByUser(User user);

    Project getProjectsByProjectId(Long id);

}
