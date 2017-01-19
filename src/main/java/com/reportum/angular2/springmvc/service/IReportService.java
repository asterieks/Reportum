package com.reportum.angular2.springmvc.service;

import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;
import com.reportum.angular2.springmvc.utils.beans.ProjectBean;

import java.util.List;

public interface IReportService {

    Report saveReport(Report report);

    List<ProjectBean> getProjectBeans(List<Project> projects);

    Report getReport(String reportId);

    List<Report> getReportByProject(Long id);

    List<Report> findAll();
}
