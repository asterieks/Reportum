package com.websystique.springmvc.service.impl;

import com.websystique.springmvc.dao.IProjectDAO;
import com.websystique.springmvc.persistence.entities.Project;
import com.websystique.springmvc.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements IProjectService{

    @Autowired
    private IProjectDAO projectDAO;

    @Override
    public List<Project> getProjectsByUserId() {
        return projectDAO.getProjectsByUserId();
    }
}
