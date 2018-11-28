package utils;

import controllers.routes;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import views.html.defaultpages.unauthorized;
import views.html.home;

import static play.mvc.Controller.flash;

public class TutorActionAuthenticator extends Security.Authenticator{
    @Override
    public String getUsername(Http.Context context) {
        String role = context.session().get("role");
        if(role.equals("TUTOR")||role.equals("ADMIN")){
            return role;
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        if(context.session().get("role").equals("STUDENT")){
            flash("fail","You are not authorized to access this page!");
            return redirect(routes.HomeController.home());
        }

        flash("fail","You must login first!");
        return unauthorized(unauthorized.render());

    }

}
