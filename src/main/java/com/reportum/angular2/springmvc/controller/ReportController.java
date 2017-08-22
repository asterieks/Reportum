package com.reportum.angular2.springmvc.controller;

import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.service.IReportService;
import com.reportum.angular2.springmvc.service.IUserService;
import com.reportum.angular2.springmvc.utils.CustomStringUtils;
import com.reportum.angular2.springmvc.utils.enums.Profile;
import com.reportum.angular2.springmvc.utils.enums.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ReportController {

    @Autowired
    private IReportService reportService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProjectService projectService;

    //POST new
    @RequestMapping(value = "/reports", method = RequestMethod.POST)
    public ResponseEntity<Void> addNewReport(@RequestBody Report newReport) {
        Project project=projectService.findProject(newReport.getProject().getProjectId());

        Report report=createReport(newReport, project);
        reportService.saveReport(report);

        User reportedBy=userService.findUser(report.getReportedBy());
        project=updateProject(project,reportedBy);
        projectService.saveProject(project);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //PUT specific
    @RequestMapping(value = "/projects/{projectId}/reports", method = RequestMethod.PUT)
    public ResponseEntity<Report> updateSpecificReport(@PathVariable long projectId, @RequestBody Report newReport) {
        Report prevReport = reportService.findReportByProjectId(projectId);
        if(prevReport == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Report report=updateReport(prevReport, newReport);

        reportService.saveReport(report);

        User reportedBy=userService.findUser(report.getReportedBy());
        Project project=updateProject(report.getProject(),reportedBy);
        projectService.saveProject(project);
        return new ResponseEntity<>(report, HttpStatus.CREATED);
    }

    //GET specific
    @RequestMapping(value = "/projects/{projectId}/reports", method = RequestMethod.GET)
    public ResponseEntity<Report> getSpecificReport(@PathVariable long projectId) {
        Report report = reportService.findReportByProjectId(projectId);
        if(report == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    // /GET all depends on user
    @RequestMapping(value = "/users/{userId:.+}/reports", method = RequestMethod.GET)
    public ResponseEntity<List<Report>> getAllUserReports(@PathVariable String userId) {
        User user= userService.findUser(userId);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Project> projects = projectService.findProjects(user);
        if(projects.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<Report> reports=reportService.findReports(projects);
        if(reports.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            replaceTextInReports(reports);    
        }
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    //GET specific
    @RequestMapping(value = "/projects/{projectId}/prev/reports", method = RequestMethod.GET)
    public ResponseEntity<Report> getPrevReport(@PathVariable long projectId) {
        Report report = reportService.findPrevReportByProjectId(projectId);
        if(report == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    private Project updateProject(Project project, User reportedBy) {
        if(Profile.REPORTER==reportedBy.getProfile()){
            project.setState(State.REPORTED.getValue());
        } else {
            project.setState(State.REVIEWED.getValue());
        }
        project.setStateDate(new Date());
        return project;
    }

    private void replaceTextInReports(List<Report> reports) {
        reports.forEach(report-> {
                report.setReviewPart(CustomStringUtils
                        .convertText(report.getReviewPart()));
                report.setIssuePart(CustomStringUtils
                        .convertText(report.getIssuePart()));
                report.setPlanPart(CustomStringUtils
                        .convertText(report.getPlanPart()));
                }
        );
    }

    private Report createReport(Report newReport, Project project) {
        Report report=new Report();
        report.setReviewPart(newReport.getReviewPart());
        report.setIssuePart(newReport.getIssuePart());
        report.setPlanPart(newReport.getPlanPart());
        report.setProject(project);
        report.setDate(new Date());
        report.setReportedBy(newReport.getReportedBy());
        return  report;
    }

    private Report updateReport(Report prevReport, Report newReport) {
        prevReport.setReviewPart(newReport.getReviewPart());
        prevReport.setIssuePart(newReport.getIssuePart());
        prevReport.setPlanPart(newReport.getPlanPart());
        prevReport.setDate(new Date());
        prevReport.setReportedBy(newReport.getReportedBy());
        return  prevReport;
    }
}
