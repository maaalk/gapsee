package controllers;

import models.User;
import models.UserRole;
import play.api.mvc.RangeSet;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import utils.ActionAuthenticator;
import views.html.*;

import models.Badge;

import javax.inject.Inject;


public class HomeController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result index() {
        return ok(index.render());
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result home() {
        return ok(home.render());
    }

    public Result login()   {
        return ok(login.render());
    }

    public Result logout(){
        session().clear();
        flash("success","Sessão encerrada com sucesso! Sentiremos sua falta T___T");
        return redirect(controllers.routes.HomeController.index()).withCookies();
    }

    public Result authenticate(){
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();
        User login = userForm.get();
        System.out.println("attempt to login from user "+ login.getUsername());
        try{
            User user = User.authenticate(login.getUsername(),login.getPassword());
            session().clear();
            session("username",user.getUsername());
            session().put("role",user.getRole().toString());
            flash("success", "Bem vindo, "+user.getUsername()+"! Você está logado como "+user.getRole().toString().toLowerCase());
            if(user.getRole().equals(UserRole.TUTOR)|| user.getRole().equals(UserRole.ADMIN)){
                return redirect(routes.CourseController.tutorIndex());
            }
            return redirect(routes.CourseController.index());
        }catch(Exception e){
            System.out.println("Usuário não encontrado! "+e.toString());
            flash("fail", "Usuário não encontrado!");
            return redirect(routes.HomeController.login());
        }

    }
}
