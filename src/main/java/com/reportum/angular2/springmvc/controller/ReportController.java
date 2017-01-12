package com.reportum.angular2.springmvc.controller;

import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.UserReport;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.service.IReportService;
import com.reportum.angular2.springmvc.utils.EntityUtils;
import com.reportum.angular2.springmvc.utils.beans.ReportBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportController {

    @Autowired
    private IReportService reportService;

    @Autowired
    private IProjectService projectService;

    @RequestMapping(value = "/reporter/report", method = RequestMethod.POST)
    public ResponseEntity<ReportBean> createReporterReport(@RequestBody ReportBean reportBean) {
        Project project = projectService.getProjectsByProjectId(reportBean.getProject());
        reportService.saveReporterReport(EntityUtils.createAndReturnUserReportEntity(reportBean, project));
        return new ResponseEntity<>(new ReportBean(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/reporter/report/{reportId}", method = RequestMethod.GET)
    public ResponseEntity<UserReport> getReport(@PathVariable String reportId) {
        UserReport userReport=reportService.getReport(reportId);
        return new ResponseEntity<>(userReport, HttpStatus.OK);
    }

}
