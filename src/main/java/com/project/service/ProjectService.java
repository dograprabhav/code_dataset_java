package com.project.service;

import com.project.models.Project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProjectService {

    private final Map<String, Project> projects = new HashMap<>();
    private final UserService userService;

    public ProjectService(UserService userService) {
        this.userService = userService;
    }

    public Project createProject(String id, String title, String ownerId) {
        if (userService.getUser(ownerId).isEmpty()) {
            throw new IllegalArgumentException("Owner " + ownerId + " does not exist");
        }
        Project project = new Project(id, title, ownerId);
        projects.put(id, project);
        return project;
    }

    public Optional<Project> getProject(String id) {
        return Optional.ofNullable(projects.get(id));
    }

    public boolean addMember(String projectId, String userId) {
        Project project = projects.get(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project " + projectId + " not found");
        }
        if (userService.getUser(userId).isEmpty()) {
            throw new IllegalArgumentException("User " + userId + " not found");
        }
        return project.addMember(userId);
    }

    public List<Project> getUserProjects(String userId) {
        return projects.values().stream()
                .filter(p -> p.getMembers().contains(userId))
                .toList();
    }
}
