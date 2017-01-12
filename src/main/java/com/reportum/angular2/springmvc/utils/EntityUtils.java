package com.reportum.angular2.springmvc.utils;


import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.UserReport;
import com.reportum.angular2.springmvc.utils.beans.ProjectBean;
import com.reportum.angular2.springmvc.utils.beans.ReportBean;

import java.util.Date;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

public class EntityUtils {

    private EntityUtils(){}

    public static UserReport createAndReturnUserReportEntity(ReportBean reportBean, Project project) {
        UserReport entity=new UserReport();
        entity.setDate(new Date());
        entity.setProjectId(project);
        entity.setReviewPart(reportBean.getReview());
        entity.setIssuePart(reportBean.getIssues());
        entity.setPlanPart(reportBean.getPlans());
        return entity;
    }

    public static ProjectBean createAndReturnProjectBean(Project project, List<UserReport> reports){
        ProjectBean projectBean=new ProjectBean();
        projectBean.setProjectId(project.getProjectId());
        projectBean.setProjectName(project.getProjectName());
        projectBean.setReporter(project.getReporter().getFullName());
        projectBean.setReportId(!isEmpty(reports) ? reports.get(0).getReportId(): null);
        projectBean.setActualDate(!isEmpty(reports) ? DateUtils.formatDate(reports.get(0).getDate()): "");
        return projectBean;
    }
}
