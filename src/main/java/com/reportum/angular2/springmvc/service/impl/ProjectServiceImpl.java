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
    public List<Project> getProjectsByReporter(User reporter) {
        return projectDAO.getProjectsByReporter(reporter);
    }

    @Override
    public List<Project> getProjectsByTeamLead(User teamLeader) {
        return projectDAO.getProjectsByTeamLead(teamLeader);
    }

    @Override
    public Project getProjectsByProjectId(Long id) {
        return projectDAO.getProjectsByProjectId(id);
    }

    @Override
    public void save(Project project) {
        projectDAO.save(project);
    }
}
