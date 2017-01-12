package com.reportum.angular2.springmvc.service;

import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.UserReport;
import com.reportum.angular2.springmvc.utils.beans.ProjectBean;

import java.util.List;

public interface IReportService {

    void saveReporterReport(UserReport report);

    List<ProjectBean> getProjectBeans(List<Project> projects);

    UserReport getReport(String reportId);
}
