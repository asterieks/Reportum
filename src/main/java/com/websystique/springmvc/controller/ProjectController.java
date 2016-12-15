package com.websystique.springmvc.controller;

import com.websystique.springmvc.persistence.entities.Project;
import com.websystique.springmvc.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> listAllProjects() {
        System.out.println("in controller");
        List<Project> organizations = projectService.getProjectsByUserId();
        System.out.println("size " +organizations.size());
        if(organizations.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(organizations, HttpStatus.OK);
    }

}
