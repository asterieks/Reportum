package com.reportum.angular2.springmvc.persistence.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Report.class)
public class Report_ {
    public static volatile SingularAttribute<Report, Long> reportId;
    public static volatile SingularAttribute<Report, Project> project;
    public static volatile SingularAttribute<Report, String> reviewPart;
    public static volatile SingularAttribute<Report, String> issuePart;
    public static volatile SingularAttribute<Report, String> planPart;
    public static volatile SingularAttribute<Report, Date> date;
    public static volatile SingularAttribute<Report, String> reportedBy;
}
