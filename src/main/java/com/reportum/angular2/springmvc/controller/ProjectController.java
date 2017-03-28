package com.reportum.angular2.springmvc.controller;

import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IUserService userService;
    //GET all projects depend on user
    @RequestMapping(value = "/users/{userId:.+}/projects", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getProjects(@PathVariable String userId) {
        User user= userService.findUser(userId);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<Project> projects = projectService.findProjects(user);
        if(projects.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    //GET specific
    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<Project> getSpecificReport(@PathVariable long projectId) {
        Project project =projectService.findProject(projectId);
        if(project==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(project, HttpStatus.OK);
    }
}
