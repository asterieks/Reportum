package com.websystique.springmvc.utils.beans;

public class UserReportBean {
    private String userId;
    private String reviewPart;
    private String issuePart;
    private String planPart;

    public UserReportBean(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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
}
