package com.project.models;

import java.util.Map;

public class Task extends BaseEntity {

    private final String title;
    private final String projectId;
    private String assigneeId;
    private TaskStatus status;

    public Task(String id, String title, String projectId) {
        this(id, title, projectId, null);
    }

    public Task(String id, String title, String projectId, String assigneeId) {
        super(id);
        this.title = title;
        this.projectId = projectId;
        this.assigneeId = assigneeId;
        this.status = TaskStatus.OPEN;
    }

    public void assign(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("assigneeId must not be blank");
        }
        this.assigneeId = userId;
        if (this.status != TaskStatus.IN_PROGRESS) {
            this.status = TaskStatus.IN_PROGRESS;
        }
    }

    public void complete() {
        this.status = TaskStatus.DONE;
    }

    public void reopen() {
        this.status = TaskStatus.OPEN;
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

    @Override
    public Map<String, Object> toDict() {
        Map<String, Object> base = super.toDict();
        base.put("title", title);
        base.put("project_id", projectId);
        base.put("assignee_id", assigneeId);
        base.put("status", status.getValue());
        return base;
    }
}
