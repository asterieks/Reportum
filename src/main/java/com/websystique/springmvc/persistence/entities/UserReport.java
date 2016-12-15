package com.websystique.springmvc.persistence.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER_REPORTS")
public class UserReport {
    @Id
    @Column (name = "report_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long reportId;

    @Column (name = "record_id")
    private Long recordId;

   /* @Column (name = "project_id")
    private Project projectId;*/

    @Column (name = "review_part")
    private String reviewPart;

    @Column (name = "issue_part")
    private String issuePart;

    @Column (name = "plan_part")
    private String planPart;

    @Column (name="creation_date")
    private Date date;

    public UserReport(){}

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

  /*  public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }*/

    public String getReviewPart() {
        return reviewPart;
    }

    public void setReviewPart(String reviewPart) {
        this.reviewPart = reviewPart;
    }

    public String getIssuePart() {
        return issuePart;
    }

    public void setIssuePart(String issuePart) {
        this.issuePart = issuePart;
    }

    public String getPlanPart() {
        return planPart;
    }

    public void setPlanPart(String planPart) {
        this.planPart = planPart;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
