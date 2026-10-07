package org.example.backend.server;

public class Routes {
    public static final String LOGIN_PAGE = "/";
    public static final String LOGIN_ACTION = "/login.action";
    public static final String LOGOUT = "/logout";

    public static void configure(TmpServer server){
        server.routes(() ->{
            post(LOGIN_ACTION, PersonController.login);
            get(LOGOUT, PersonController.logout);

        });
    }
}
