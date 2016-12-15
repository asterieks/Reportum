package com.websystique.springmvc.dao;

import com.websystique.springmvc.persistence.entities.UserReport;
import com.websystique.springmvc.utils.beans.UserReportBean;

public interface IUserDAO {

    void saveReport(UserReport userReport);

}
