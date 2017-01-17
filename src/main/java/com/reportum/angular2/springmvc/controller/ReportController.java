package com.reportum.angular2.springmvc.controller;

import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.service.IReportService;
import com.reportum.angular2.springmvc.utils.EntityUtils;
import com.reportum.angular2.springmvc.utils.beans.ReportBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

@RestController
public class ReportController {

    @Autowired
    private IReportService reportService;

    @Autowired
    private IProjectService projectService;

    private final String STATE_UPDATED="Updated";

    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public ResponseEntity<Report> addReport(@RequestBody ReportBean reportBean) {
        Project project = projectService.getProjectsByProjectId(reportBean.getProject());
        Report report=reportService.saveReport(EntityUtils.createReport(project, reportBean));
        projectService.save(updateProject(project));
        return new ResponseEntity<>(report, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/reporter/report/{reportId}", method = RequestMethod.GET)
    public ResponseEntity<Report> getReport(@PathVariable String reportId) {
        Report report =reportService.getReport(reportId);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @RequestMapping(value = "/report", method = RequestMethod.PUT)
    public ResponseEntity<Report> updateReport(@RequestBody ReportBean reportBean) {
        List<Report> reports = reportService.getReportByProject(reportBean.getProject());
        if(!isEmpty(reports)){
            Report report = EntityUtils.updateReport(reports.get(0), reportBean);
            return new ResponseEntity<>(reportService.saveReport(report), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Project updateProject(Project project) {
        project.setState(STATE_UPDATED);
        project.setStateDate(new Date());
        return project;
    }
}
