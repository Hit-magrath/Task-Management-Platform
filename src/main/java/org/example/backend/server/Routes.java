package org.example.backend.server;

import org.example.backend.controller.PersonController;
import org.example.backend.controller.ProjectController;

import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.get;


public class Routes {
    public static final String LOGIN_PAGE = "/";
    public static final String LOGIN_ACTION = "/login.action";
    public static final String LOGOUT = "/logout";
    public static final String PROJECTS = "/projects";


    public static void configure(TmpServer server){
        server.routes(() ->{
            post(LOGIN_ACTION, PersonController.login);
            get(LOGOUT, PersonController.logout);
            get(PROJECTS , ProjectController.view);

        });
    }
}
