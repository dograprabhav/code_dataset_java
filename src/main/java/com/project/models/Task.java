package com.project.models;

public class Task {

    private final String id;
    private final String title;
    private final String projectId;
    private String assigneeId;
    private TaskStatus status;

    public Task(String id, String title, String projectId) {
        this(id, title, projectId, null);
    }

    public Task(String id, String title, String projectId, String assigneeId) {
        this.id = id;
        this.title = title;
        this.projectId = projectId;
        this.assigneeId = assigneeId;
        this.status = TaskStatus.OPEN;
    }

    public void assign(String userId) {
        this.assigneeId = userId;
        if (this.status == TaskStatus.OPEN) {
            this.status = TaskStatus.IN_PROGRESS;
        }
    }

    public void complete() {
        this.status = TaskStatus.DONE;
    }

    public void reopen() {
        this.status = TaskStatus.OPEN;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
