package com.project.models;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseEntity {

    private final String id;
    private final Instant createdAt;

    protected BaseEntity(String id) {
        this(id, null);
    }

    protected BaseEntity(String id, Instant createdAt) {
        this.id = id;
        this.createdAt = (createdAt != null) ? createdAt : Instant.now();
    }

    public String getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Subclasses must call {@code super.toDict()} and add their own fields
     * to the returned (mutable) map.
     */
    public Map<String, Object> toDict() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("created_at", createdAt.toString());
        return map;
    }
}
