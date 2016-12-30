package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.dao.IUserReportDAO;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.service.IProjectService;
import com.reportum.angular2.springmvc.service.IUserReportService;
import com.reportum.angular2.springmvc.utils.EntityUtils;
import com.reportum.angular2.springmvc.utils.beans.UserReportBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserReportServiceImpl implements IUserReportService{

    @Autowired
    private IUserReportDAO userReportDAO;

    @Autowired
    private IProjectService projectService;

    @Override
    public void saveReport(UserReportBean userReportBean) {
        Project project = projectService.getProjectsByProjectId(userReportBean.getProject());
        userReportDAO.saveReport(EntityUtils.createAndReturnUserReportEntity(userReportBean, project));
    }
}
