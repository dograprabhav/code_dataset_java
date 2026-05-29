package com.project.models;

public enum TaskStatus {

    OPEN("open"),
    IN_PROGRESS("in_progress"),
    DONE("done");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
