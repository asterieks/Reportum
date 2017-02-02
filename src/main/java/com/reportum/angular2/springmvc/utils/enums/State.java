package com.reportum.angular2.springmvc.utils.enums;

public enum State {
    REPORTED("Reported"),
    REVIEWED("Reviewed"),
    DELAYED("Delayed");

    private String value;

    State(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
