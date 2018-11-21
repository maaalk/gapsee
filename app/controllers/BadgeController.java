package controllers;
import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import utils.ActionAuthenticator;
import utils.AdminActionAuthenticator;
import utils.TutorActionAuthenticator;
import views.html.*;
import views.html.badge.badgetutorshow;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

@Security.Authenticated(ActionAuthenticator.class)
public class BadgeController extends Controller {

    @Inject
    FormFactory formFactory;



   /* @Security.Authenticated(AdminActionAuthenticator.class)
    public Result create(){
        Form<Badge> badgeForm= formFactory.form(Badge.class);
        return ok(badgecreate.render(badgeForm));
    }*/

    /*@Security.Authenticated(AdminActionAuthenticator.class)
    public Result save(){
        Form<Badge> badgeForm = formFactory.form(Badge.class).bindFromRequest();
        Badge badge = badgeForm.get();
        badge.setLevel(Level.find.byId(1));
        badge.save();
        return redirect(routes.BadgeController.index());
    }*/

   /* @Security.Authenticated(AdminActionAuthenticator.class)
    public Result edit(Integer id){
        Badge badge = Badge.find.byId(id);
        if (badge==null){
            return notFound("Badge not found");
        }
        Form<Badge> badgeForm = formFactory.form(Badge.class).fill(badge);
        return  ok(badgeedit.render(badgeForm));
    }*/


    /*public Result update(){
        Badge badge = formFactory.form(Badge.class).bindFromRequest().get();
        Badge oldBadge = Badge.find.byId(badge.getId());
        if (oldBadge == null){
            return notFound("Badge not found");
        }
        oldBadge.setTopic(badge.getTopic());
        oldBadge.setTier(badge.getTier());
        oldBadge.setName(badge.getName());
        oldBadge.setDescription(badge.getDescription());
        oldBadge.update();
        return redirect(routes.BadgeController.index());

           }*/


    public Result show(Integer id){
        Badge badge = Badge.find.byId(id);
        if (badge==null){
            return notFound("Badge not found");
        }
        User user = User.findByUserName(session("username"));
        UserBadge userBadge = UserBadge.findUserBadge(user.getId(),badge.getId());
        List<Evidence> evidenceList = new ArrayList<Evidence>();
            if (userBadge!=null){
                evidenceList = userBadge.getEvidenceList();
                Collections.reverse(evidenceList);
            }
        String role = session("role");
        return ok(badgeshow.render(badge, evidenceList,role));
    }

    public Result tutorShow(Integer id){
        Badge badge = Badge.find.byId(id);
        if (badge==null){
            return notFound("Badge not found");
        }
        List<UserBadge> submissionList = UserBadge.findByBadge(badge);
        return ok(badgetutorshow.render(badge, submissionList));
    }


  /*  @Security.Authenticated(AdminActionAuthenticator.class)
    public Result destroy(Integer id){
        Badge badge = Badge.find.byId(id);
        if (badge==null){
            flash("danger", "Book not Found");
            return notFound();
        }
        badge.delete();
        flash("success","Book deleted successfully!");
        return ok();
    }*/





}