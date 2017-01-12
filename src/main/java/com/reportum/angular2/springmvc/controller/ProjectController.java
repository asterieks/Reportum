package com.reportum.angular2.springmvc.controller;

import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.service.IReportService;
import com.reportum.angular2.springmvc.service.IUserService;
import com.reportum.angular2.springmvc.utils.beans.ProjectBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IReportService reportService;

    @RequestMapping(value = "/projects/reporter/{reporterId:.+}", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getProjectsByReporter(@PathVariable String reporterId) {
        User user= userService.findUserById(reporterId);
        List<Project> projects = projectService.getProjectsByReporter(user);
        if(projects.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/lead/{leadId:.+}", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectBean>> getProjectsByTeamLead(@PathVariable String leadId) {
        User user= userService.findUserById(leadId);
        List<Project> projects = projectService.getProjectsByTeamLead(user);
        if(!projects.isEmpty()){
            List<ProjectBean> reportsStates=reportService.getProjectBeans(projects);
            return new ResponseEntity<>(reportsStates, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
