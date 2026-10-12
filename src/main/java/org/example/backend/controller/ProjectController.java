package org.example.backend.controller;

import io.javalin.http.Handler;
import org.example.backend.user.Project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ProjectController {
    public static final Handler view = ctx -> {
        Collection<Project> projects = new ArrayList<>();
        Map<String , Object> projectsModel = Map.of("projects",projects);
        ctx.render("projects.html" , projectsModel);

    };


}
