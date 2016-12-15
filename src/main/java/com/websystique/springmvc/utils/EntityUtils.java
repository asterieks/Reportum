package com.websystique.springmvc.utils;


import com.websystique.springmvc.persistence.entities.UserReport;
import com.websystique.springmvc.utils.beans.UserReportBean;

public class EntityUtils {

    private EntityUtils(){}

    public static UserReport createAndReturnUserReportEntity(UserReportBean userReportBean) {
        UserReport entity=new UserReport();
        //TODO add appropriate code
        return entity;
    }


}
