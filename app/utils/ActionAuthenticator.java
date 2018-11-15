package utils;

import controllers.routes;
import models.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import static play.mvc.Controller.flash;

public class ActionAuthenticator extends Security.Authenticator {


    @Override
    public Result onUnauthorized(Http.Context context) {
        flash("fail","Sorry, you must be logged to access this page!");
        return redirect(controllers.routes.HomeController.index());

    }


}