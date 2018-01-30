package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.dao.IReportDAO;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;
import com.reportum.angular2.springmvc.service.IEmailService;
import com.reportum.angular2.springmvc.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private IReportDAO reportDAO;

    @Autowired
    private IEmailService emailService;

    @Override
    public Report saveReport(Report report) {
        return reportDAO.saveReport(report);
    }

    @Override
    public Report findReport(Long reportId) {
        return reportDAO.findReport(reportId);
    }

    @Override
    public List<Report> findReports(List<Project> projects) {
        return reportDAO.findReports(projects);
    }

    @Override
    public List<Report> findAllActualReports() {
        return reportDAO.findAllActualReports();
    }

    @Override
    public Report findReportByProjectId(Long projectId) {
        return reportDAO.findReportByProjectId(projectId);
    }

    @Override
    public Report findPrevReportByProjectId(Long projectId) {
        emailService.sendSimpleMessage("msokil@luxoft.com","EmailService call","Hello reporter. Make your report");
        return reportDAO.findPrevReportByProjectId(projectId);
    }
}
