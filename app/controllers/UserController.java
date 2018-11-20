package controllers;

import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.user.usercreate;

import javax.inject.Inject;

public class UserController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result authenticate(){
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();
        User login = userForm.get();
        System.out.println("attempt to login from user "+ login.getUsername());
        try{
            User user = User.authenticate(login.getUsername(),login.getPassword());
            session().clear();
            session("username",user.getUsername());
           /// session().put("role",user.getRole().toString());
            flash("success", "Bem vindo, "+user.getUsername()+"!"/*+" Você está logado como "+user.getRole().toString()*/);
            return redirect(routes.CourseController.index());
        }catch(Exception e){
            System.out.println("Usuário não encontrado! "+e.toString());
            flash("fail", "Usuário não encontrado!");
            return redirect(routes.HomeController.index());
        }

    }

    public Result create(){
        return ok(usercreate.render());
    }

    public Result save(){
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();
        User user = userForm.get();
        user.setAdm(false);
        try {
            user.save();
            flash("success", "Usuário criado com sucesso");
            return ok(index.render(""));
        }catch (Exception e){
            System.out.println("Erro ao cadastrar usuário! "+e.toString());
            flash("fail","Erro ao cadastrar usuário! =/");
            return badRequest(usercreate.render());
        }

    }


}
