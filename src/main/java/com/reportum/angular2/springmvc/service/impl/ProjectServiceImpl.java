package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.dao.IProjectDAO;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IEmailService;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.utils.enums.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class ProjectServiceImpl implements IProjectService{

    @Autowired
    private IProjectDAO projectDAO;

    @Autowired
    private IEmailService emailService;

    @Value("${manager.receiver.email}")
    private String managerEmail;

    @Value("${job.notification.title}")
    private String subject;

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
        if(State.REVIEWED.getValue().equals(project.getState())){
            emailService.sendSimpleMessage(managerEmail,subject,project.getProjectName() + " report is done.");
        }
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
