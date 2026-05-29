package com.project.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class User extends BaseEntity {

    private static final Set<String> ALLOWED_ROLES = Set.of("viewer", "editor", "admin");

    private final String name;
    private final String email;
    private String role;
    private boolean active;
    private final List<String> tags;

    public User(String id, String name, String email) {
        this(id, name, email, "viewer");
    }

    public User(String id, String name, String email, String role) {
        super(id);
        this.name = name;
        this.email = email;
        this.role = role;
        this.active = true;
        this.tags = new ArrayList<>();
    }

    public void promote(String newRole) {
        if (!ALLOWED_ROLES.contains(newRole)) {
            throw new IllegalArgumentException("Invalid role: " + newRole);
        }
        this.role = newRole;
    }

    public void deactivate() {
        this.active = false;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public boolean isActive() {
        return active;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public Map<String, Object> toDict() {
        Map<String, Object> base = super.toDict();
        base.put("name", name);
        base.put("email", email);
        base.put("role", role);
        base.put("active", active);
        return base;
    }
}
