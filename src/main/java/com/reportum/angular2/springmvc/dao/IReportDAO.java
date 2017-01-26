package com.reportum.angular2.springmvc.dao;

import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;

import java.util.List;

public interface IReportDAO {

    Report saveReport(Report report);

    List<Report> findReports(List<Project> projects);

    Report findReport(Long reportId);

    List<Report> findAllActualReports();
}
