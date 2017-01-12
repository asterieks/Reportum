package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.dao.IReportDAO;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.UserReport;
import com.reportum.angular2.springmvc.service.IReportService;
import com.reportum.angular2.springmvc.utils.EntityUtils;
import com.reportum.angular2.springmvc.utils.beans.ProjectBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private IReportDAO userReportDAO;

    @Override
    public void saveReporterReport(UserReport report) {
        userReportDAO.saveReporterReport(report);
    }

    @Override
    public List<ProjectBean> getProjectBeans(List<Project> projects) {
        List<ProjectBean> projectBeanList=new ArrayList<>();
        for (Project project: projects){
            List<UserReport> reports=userReportDAO.getActualReport(project.getProjectId());
            //List<?> shallowCopy = list.subList(0, list.size());
            Collections.reverse(reports);
            ProjectBean projectBean= EntityUtils.createAndReturnProjectBean(project, reports);
            projectBeanList.add(projectBean);
        }
        return projectBeanList;
    }

    @Override
    public UserReport getReport(String reportId) {
        return userReportDAO.getReport(reportId);
    }
}
