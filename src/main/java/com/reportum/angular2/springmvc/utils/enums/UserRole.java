package com.reportum.angular2.springmvc.utils.enums;

/**
 * Created by Anna on 02.02.2017.
 */
public enum UserRole {
    REPORTER ("REPORTER"),
    LEAD ("LEAD"),
    MANAGER ("MANAGER");

    private String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
