package com.reportum.angular2.springmvc.utils;


import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.UserReport;
import com.reportum.angular2.springmvc.utils.beans.UserReportBean;

import java.util.Date;

public class EntityUtils {

    private EntityUtils(){}

    public static UserReport createAndReturnUserReportEntity(UserReportBean userReportBean, Project project) {
        UserReport entity=new UserReport();
        entity.setDate(new Date());
        entity.setProjectId(project);
        entity.setReviewPart(userReportBean.getReview());
        entity.setIssuePart(userReportBean.getIssues());
        entity.setPlanPart(userReportBean.getPlans());
        return entity;
    }
}
