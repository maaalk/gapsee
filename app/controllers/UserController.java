package controllers;

import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class UserController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result authenticate(){
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();
        User login = userForm.get();
        System.out.println("attempt to login from user "+ login.getUsername());
        try{
            User user = User.find.query()
                    .select("username")
                    .where().eq("PASSWORD",login.getPassword())
                    .findOne();
            session().clear();
            session("username",user.getUsername());
            flash("success", "Bem vindo"+user.getUsername());
            return redirect(routes.CourseController.index());
        }catch(Exception e){
            System.out.println("Usuário não encontrado! "+e.toString());
            flash("fail", "Not connected");
            return redirect(routes.HomeController.index());
        }




    }



}
