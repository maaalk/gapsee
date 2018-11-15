package utils;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import views.html.defaultpages.unauthorized;

import static play.mvc.Controller.flash;

public class AdminActionAuthenticator extends Security.Authenticator{

    @Override
    public String getUsername(Http.Context context) {
        String role = context.session().get("role");
        if(role.equals("ADMIN")){
            return role;
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        flash("fail","You must login first!");
        return unauthorized(unauthorized.render());

    }
}
