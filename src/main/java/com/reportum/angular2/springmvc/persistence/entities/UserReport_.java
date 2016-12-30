package com.reportum.angular2.springmvc.persistence.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(UserReport.class)
public class UserReport_ {
    public static volatile SingularAttribute<UserReport, Long> reportId;
    public static volatile SingularAttribute<UserReport, Long> recordId;
    public static volatile SingularAttribute<UserReport, Project> projectId;
    public static volatile SingularAttribute<UserReport, String> reviewPart;
    public static volatile SingularAttribute<UserReport, String> issuePart;
    public static volatile SingularAttribute<UserReport, String> planPart;
    public static volatile SingularAttribute<UserReport, Date> date;
}
