package com.reportum.angular2.springmvc.utils.enums;

public enum State {
    UPDATED("Updated");

    private String value;

    State(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
