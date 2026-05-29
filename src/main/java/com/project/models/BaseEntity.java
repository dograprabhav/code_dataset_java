package com.project.models;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class BaseEntity {

    private final String id;
    private final Instant createdAt;

    public BaseEntity(String id) {
        this(id, null);
    }

    public BaseEntity(String id, Instant createdAt) {
        this.id = id;
        this.createdAt = (createdAt != null) ? createdAt : Instant.now();
    }

    public String getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Map<String, Object> toDict() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("created_at", createdAt.toString());
        return map;
    }
}
