package com.websystique.springmvc.dao;


import com.websystique.springmvc.persistence.entities.Project;

import java.util.List;

public interface IProjectDAO {

    List<Project> getProjectsByUserId();

}
