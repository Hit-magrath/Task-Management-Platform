package org.example.backend.server;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.core.security.AccessManager;
import io.javalin.core.security.RouteRole;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.NullSessionDataStore;
import org.eclipse.jetty.server.session.SessionCache;
import org.eclipse.jetty.server.session.SessionHandler;
import org.example.backend.persistance.PersonDAO;
import org.example.backend.persistance.collectionbased.PersonDAOImpl;
import org.example.backend.user.Person;
import org.jetbrains.annotations.NotNull;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.annotation.Nullable;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class TmpServer {
    public static final String SESSION_USER_KEY = "user";
    private static final String PAGES_DIR = "/html" ;
    private static final String TEMPLATES = "/templtes/";

    private final Javalin appSever;

    public TmpServer(){
        JavalinThymeleaf.configure(templateEngine());

        appSever = Javalin.create( config -> {
            config.addStaticFiles(PAGES_DIR , Location.CLASSPATH);
            config.accessManager(accessManager());
            config.sessionHandler(sessionHandler());
        });

        ServiceRegistry.configure(PersonDAO.class, new PersonDAOImpl());
        Routes.configure(this);
        configureExceptionsPage();
    }

    public static void main(String[] args) {
        TmpServer server = new TmpServer();
        server.start(5050);
    }
    public void start(int port){this.appSever.start(port); }


    @Nullable
    public static Person getPersonLoggedIn(Context context){
        return context.sessionAttribute(SESSION_USER_KEY);
    }

    private static Supplier<SessionHandler> sessionHandler(){
        SessionHandler sessionHandler = new SessionHandler();
        SessionCache sessionCache = new DefaultSessionCache(sessionHandler);
        sessionCache.setSessionDataStore(new NullSessionDataStore());
        sessionHandler.setSessionCache(sessionCache);
        sessionHandler.setHttpOnly(true);
        return () -> sessionHandler;
    }

    private Javalin configureExceptionsPage(){
        return appSever.exception(Exception.class, (e, context) -> {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTrace = sw.toString().replace(System.getProperty("line.seperator"), "<br/> \n");
            context.render("exception.html",
                    Map.of("exception", e,
                            "stacktrace", stackTrace));
        });
    }

    public void routes(EndpointGroup group){
        appSever.routes(group);
    }

    private AccessManager accessManager(){
        return new AccessManager() {
            @Override
            public void manage(@NotNull Handler handler, @NotNull Context ctx, @NotNull Set<RouteRole> routeRoles) throws Exception {
                if (hasNoSession(ctx)){
                    ctx.redirect(Routes.LOGIN_PAGE);
                }else {
                    handler.handle(ctx);
                }
            }
            private boolean hasNoSession(@NotNull Context context){
                Person loggedInPerson = context.sessionAttribute(SESSION_USER_KEY);
                System.out.println((context.path()));
                return Objects.isNull(loggedInPerson) && !context.path().equals(Routes.LOGIN_ACTION);
            }
        };
    }

    private TemplateEngine templateEngine(){
        TemplateEngine templateEngine = new TemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix(TEMPLATES);
        templateEngine.setTemplateResolver(resolver);
        templateEngine.addDialect(new LayoutDialect());
        return templateEngine;
    }
}