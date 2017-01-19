package com.reportum.angular2.springmvc.dao;

import com.reportum.angular2.springmvc.persistence.entities.Report;

import java.util.List;

public interface IReportDAO {

    Report saveReport(Report report);

    List<Report> getActualReport(Long projectId);

    Report getReport(String reportId);

    List<Report> getReportByProject(Long id);

    List<Report> findAll();
}
