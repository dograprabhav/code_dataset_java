package com.project.service;

import com.project.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserService {

    private final Map<String, User> users = new HashMap<>();

    public User createUser(String id, String name, String email) {
        return createUser(id, name, email, "viewer");
    }

    public User createUser(String id, String name, String email, String role) {
        if (users.containsKey(id)) {
            throw new IllegalArgumentException("User " + id + " already exists");
        }
        User user = new User(id, name, email, role);
        users.put(id, user);
        return user;
    }

    public Optional<User> getUser(String id) {
        return Optional.ofNullable(users.get(id));
    }

    public List<User> listActiveUsers() {
        return users.values().stream()
                .filter(User::isActive)
                .toList();
    }

    public boolean deactivateUser(String id) {
        User user = users.get(id);
        if (user == null) {
            return false;
        }
        user.deactivate();
        return true;
    }
}
