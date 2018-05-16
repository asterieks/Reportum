package com.reportum.angular2.springmvc.controller;

import com.reportum.angular2.springmvc.dto.PropertiesDTO;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private PropertiesDTO propertiesDTO;

    //add new
    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        userService.saveUser(userService.prepareUser(user));
    }

    @PostMapping("/projects")
    public void addProject(@RequestBody Project project) {
        projectService.saveProject(projectService.prepareProject(project));
    }

    //get all
    @GetMapping(value = "/users")
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/projects")
    public List<Project> getProjects() {
        return projectService.findAllProjects();
    }

    @GetMapping("/properties")
    public PropertiesDTO getProperties() {
        return propertiesDTO;
    }

    //delete by id
    @DeleteMapping("/users/{userId:.+}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @DeleteMapping("/projects/{projectId}")
    public void hideProject(@PathVariable long projectId) {
        projectService.deleteProject(projectId);
    }

    //update
    @PutMapping("/users")
    public void updateUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PutMapping("/projects")
    public void updateProject(@RequestBody Project project) {
        projectService.saveProject(project);
    }
}
