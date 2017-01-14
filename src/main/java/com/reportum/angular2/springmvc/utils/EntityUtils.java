package com.reportum.angular2.springmvc.utils;


import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;
import com.reportum.angular2.springmvc.utils.beans.ProjectBean;
import com.reportum.angular2.springmvc.utils.beans.ReportBean;

import java.util.Date;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

public class EntityUtils {

    private EntityUtils(){}

    public static ProjectBean createAndReturnProjectBean(Project project, List<Report> reports){
        ProjectBean projectBean=new ProjectBean();
        projectBean.setProjectId(project.getProjectId());
        projectBean.setProjectName(project.getProjectName());
        projectBean.setReporter(project.getReporter().getFullName());
        projectBean.setReportId(!isEmpty(reports) ? reports.get(0).getReportId(): null);
        projectBean.setActualDate(!isEmpty(reports) ? DateUtils.formatDate(reports.get(0).getDate()): "");
        return projectBean;
    }

    public static Report updateReport(Report report, ReportBean reportBean) {
        report.setDate(new Date());
        report.setReviewPart(reportBean.getReview());
        report.setIssuePart(reportBean.getIssues());
        report.setPlanPart(reportBean.getPlans());
        return report;
    }

    public static Report createReport(Project project, ReportBean reportBean) {
        Report report=new Report();
        report.setDate(new Date());
        report.setProjectId(project);
        report.setReviewPart(reportBean.getReview());
        report.setIssuePart(reportBean.getIssues());
        report.setPlanPart(reportBean.getPlans());
        return report;
    }
}
