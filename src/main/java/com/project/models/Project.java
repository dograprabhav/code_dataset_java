package com.project.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Project extends BaseEntity {

    private final String title;
    private final String description;
    private final String ownerId;
    private final List<String> members;

    public Project(String id, String title, String ownerId) {
        this(id, title, ownerId, "");
    }

    public Project(String id, String title, String ownerId, String description) {
        super(id);
        this.title = title;
        this.ownerId = ownerId;
        this.description = description;
        this.members = new ArrayList<>();
        this.members.add(ownerId);
    }

    public boolean addMember(String userId) {
        if (members.contains(userId)) {
            return false;
        }
        members.add(userId);
        return true;
    }

    public boolean removeMember(String userId) {
        if (userId.equals(ownerId)) {
            throw new IllegalArgumentException("Cannot remove the project owner");
        }
        return members.remove(userId);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public List<String> getMembers() {
        return members;
    }

    @Override
    public Map<String, Object> toDict() {
        Map<String, Object> base = super.toDict();
        base.put("title", title);
        base.put("owner_id", ownerId);
        base.put("member_count", members.size());
        return base;
    }
}
