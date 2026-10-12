package org.example.backend.controller;


import io.javalin.http.Handler;
import org.example.backend.persistance.PersonDAO;
import org.example.backend.server.Routes;
import org.example.backend.server.ServiceRegistry;
import org.example.backend.server.TmpServer;
import org.example.backend.user.Person;

import java.util.Objects;

public class PersonController {

    public static final Handler logout = ctx -> {
        ctx.sessionAttribute(TmpServer.SESSION_USER_KEY,null);
        ctx.redirect(Routes.LOGIN_PAGE);
    };

    private static final PersonDAO personDAO = ServiceRegistry.lookup(PersonDAO.class);

    public static final Handler login = context ->{
        String email = context.formParamAsClass("email" , String.class)
                .check(Objects::nonNull, "email is require")
                .get();

        Person person = personDAO.savePerson( new Person(email));
        context.sessionAttribute(TmpServer.SESSION_USER_KEY , person);
        context.redirect(Routes.PROJECTS);

    };

}
