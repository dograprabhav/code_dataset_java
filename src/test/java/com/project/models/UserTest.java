package com.project.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void createUserWithDefaults() {
        User user = new User("u1", "Alice", "alice@example.com");
        assertEquals("u1", user.getId());
        assertEquals("Alice", user.getName());
        assertEquals("viewer", user.getRole());
        assertTrue(user.isActive());
    }

    @Test
    void promoteToAdmin() {
        User user = new User("u1", "Alice", "alice@example.com");
        user.promote("admin");
        assertEquals("admin", user.getRole());
    }
}
