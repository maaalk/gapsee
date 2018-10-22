package controllers;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.*;
import models.Badge;
import java.util.Set;
import javax.inject.Inject;

public class BadgeController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result index(){
        Set<Badge> badges = Badge.allBadges();
        return ok(badgeindex.render(badges));
    }

    public Result create(){
        Form<Badge> badgeForm= formFactory.form(Badge.class);
        return ok(badgecreate.render(badgeForm));
    }

    public Result save(){
        Form<Badge> badgeForm = formFactory.form(Badge.class).bindFromRequest();
        Badge badge = badgeForm.get();
        Badge.add(badge);
        return redirect(routes.BadgeController.index());
    }

    public Result edit(Integer id){
        Badge badge = Badge.findById(id);
        if (badge==null){
            return notFound("Badge not found");
        }
        Form<Badge> badgeForm = formFactory.form(Badge.class).fill(badge);
        return  ok(badgeedit.render(badgeForm));
    }

    public Result update(){
        Badge badge = formFactory.form(Badge.class).bindFromRequest().get();
        Badge oldBadge = Badge.findById(badge.getId());
        if (oldBadge == null){
            return notFound("Badge not found");
        }
        oldBadge.setCategory(badge.getCategory());
        oldBadge.setPoints(badge.getPoints());
        oldBadge.setName(badge.getName());
        oldBadge.setDescription(badge.getDescription());
        return redirect(routes.BadgeController.index());


    }

    public Result show(Integer id){
        Badge badge = Badge.findById(id);
        if (badge==null){
            return notFound("Badge not found");
        }
        return ok(badgeshow.render(badge));
    }


    public Result destroy(Integer id){
        Badge badge = Badge.findById(id);
        if (badge==null){
            return notFound("Badge not found");
        }
        Badge.remove(badge);
        return redirect(routes.BadgeController.index());
    }



}