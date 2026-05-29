package com.project.service;

import com.project.models.Project;
import com.project.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class ProjectSummaryBuilder {

    private ProjectSummaryBuilder() {
    }

    public static Map<String, Object> buildProjectSummary(Project project, UserService userService) {
        List<Map<String, String>> members = new ArrayList<>();
        for (String uid : project.getMembers()) {
            Optional<User> user = userService.getUser(uid);
            Map<String, String> memberEntry = new HashMap<>();
            memberEntry.put("id", uid);
            memberEntry.put("name", user.map(User::getName).orElse("unknown"));
            members.add(memberEntry);
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("project", project.toDict());
        summary.put("members", members);
        return summary;
    }
}
