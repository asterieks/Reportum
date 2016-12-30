package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.dao.IProjectDAO;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements IProjectService{

    @Autowired
    private IProjectDAO projectDAO;

    @Override
    public List<Project> getProjectsByUser(User user) {
        return projectDAO.getProjectsByUser(user);
    }

    @Override
    public Project getProjectsByProjectId(Long id) {
        return projectDAO.getProjectsByProjectId(id);
    }
}
