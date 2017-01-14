package com.reportum.angular2.springmvc.service.impl;

import com.reportum.angular2.springmvc.dao.IReportDAO;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Report;
import com.reportum.angular2.springmvc.service.IReportService;
import com.reportum.angular2.springmvc.utils.EntityUtils;
import com.reportum.angular2.springmvc.utils.beans.ProjectBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private IReportDAO reportDAO;

    @Override
    public Report saveReport(Report report) {
        return reportDAO.saveReport(report);
    }

    @Override
    public List<ProjectBean> getProjectBeans(List<Project> projects) {
        List<ProjectBean> projectBeanList=new ArrayList<>();
        for (Project project: projects){
            List<Report> reports=reportDAO.getActualReport(project.getProjectId());
            //TODO
            Collections.reverse(reports);
            ProjectBean projectBean= EntityUtils.createAndReturnProjectBean(project, reports);
            projectBeanList.add(projectBean);
        }
        return projectBeanList;
    }

    @Override
    public Report getReport(String reportId) {
        return reportDAO.getReport(reportId);
    }

    @Override
    public List<Report> getReportByProject(Long id) {
        return reportDAO.getReportByProject(id);
    }
}
