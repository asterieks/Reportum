package com.reportum.angular2.springmvc.persistence.entities;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Project.class)
public class Project_ {
    public static volatile SingularAttribute<Project, Long> projectId;
    public static volatile SingularAttribute<Project, String> projectName;
    public static volatile SingularAttribute<Project, User> reporter;
    public static volatile SingularAttribute<Project, User> teamLeader;
    public static volatile SingularAttribute<Project, User> manager;
    public static volatile ListAttribute<User, Report> projectIdList;
}
