package com.reportum.angular2.springmvc.dao;

import com.reportum.angular2.springmvc.persistence.entities.UserReport;

public interface IUserReportDAO {

    void saveReport(UserReport userReport);

}
