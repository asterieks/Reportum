package com.websystique.springmvc.service;

import com.websystique.springmvc.persistence.entities.Project;

import java.util.List;

public interface IProjectService {

    List<Project> getProjectsByUserId();

}
