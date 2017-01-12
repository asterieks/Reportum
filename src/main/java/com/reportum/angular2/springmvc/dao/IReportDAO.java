package com.reportum.angular2.springmvc.dao;

import com.reportum.angular2.springmvc.persistence.entities.UserReport;

import java.util.List;

public interface IReportDAO {

    void saveReporterReport(UserReport userReport);

    List<UserReport> getActualReport(Long projectId);

    UserReport getReport(String reportId);
}
