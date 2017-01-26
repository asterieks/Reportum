package com.reportum.angular2.springmvc.service;

import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;

import java.util.List;

public interface IReportService {

    Report saveReport(Report report);

    List<Report> findReports(List<Project> projects);

    Report findReport(Long reportId);

    List<Report> findAllActualReports();
}
