package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.dao.IProjectDAO;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IEmailService;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.service.IUserService;
import com.reportum.angular2.springmvc.utils.enums.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements IProjectService{

    private final Logger DEBUG_LOG = LoggerFactory.getLogger("DEBUG_LOGGER");

    @Autowired
    private IUserService userService;

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
            try {
                emailService.sendSimpleMessage(managerEmail, subject, project.getProjectName() + " report is done.");
            } catch (Exception e){
                DEBUG_LOG.debug("ProjectServiceImpl.saveProject(): failed while mail sending" + e.toString());
                e.printStackTrace();
            }
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

    @Override
    public void deleteProject(Long id) {
        projectDAO.deleteProject(id);
    }

    @Override
    public Project prepareProject(Project project) {
        User manager = userService.findUser(project.getManager().getId());
        User reporter = userService.findUser(project.getReporter().getId());
        User teamLead = userService.findUser(project.getTeamLeader().getId());
        project.setReporter(reporter);
        project.setTeamLeader(teamLead);
        project.setManager(manager);
        project.setState(State.DELAYED.getValue());
        return project;
    }
}
