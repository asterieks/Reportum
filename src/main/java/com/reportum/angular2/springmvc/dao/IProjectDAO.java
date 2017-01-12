package com.reportum.angular2.springmvc.dao;


import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.User;

import java.util.List;

public interface IProjectDAO {

    List<Project> getProjectsByReporter(User reporter);

    List<Project> getProjectsByTeamLead(User teamLeader);

    Project getProjectsByProjectId(Long id);
}
