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
    public List<Project> findProjects(User user) {
        return projectDAO.findProjects(user);
    }

    @Override
    public Project findProject(Long id) {
        return projectDAO.findProject(id);
    }

    @Override
    public void saveProject(Project project) {
        projectDAO.saveProject(project);
    }

    @Override
    public void saveProjects(List<Project> projects) {
        projectDAO.saveProjects(projects);
    }

    @Override
    public List<Project> findAllProjects() {
        return projectDAO.findAllProjects();
    }
}
