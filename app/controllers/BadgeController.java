package controllers;
import models.Level;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import views.html.*;
import models.Badge;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

public class BadgeController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result index(){
        List<Level> levelList = Level.find.all();
        return ok(badgeindex.render(levelList));
    }

    public Result create(){
        Form<Badge> badgeForm= formFactory.form(Badge.class);
        return ok(badgecreate.render(badgeForm));
    }

    public Result save(){
        Form<Badge> badgeForm = formFactory.form(Badge.class).bindFromRequest();
        Badge badge = badgeForm.get();
        badge.save();
        return redirect(routes.BadgeController.index());
    }

    public Result edit(Integer id){
        Badge badge = Badge.find.byId(id);
        if (badge==null){
            return notFound("Badge not found");
        }
        Form<Badge> badgeForm = formFactory.form(Badge.class).fill(badge);
        return  ok(badgeedit.render(badgeForm));
    }

    public Result update(){
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


    }

    public Result show(Integer id){
        Badge badge = Badge.find.byId(id);
        if (badge==null){
            return notFound("Badge not found");
        }
        return ok(badgeshow.render(badge));
    }


    public Result destroy(Integer id){
        Badge badge = Badge.find.byId(id);
        if (badge==null){
            flash("danger", "Book not Found");
            return notFound();
        }
        badge.delete();
        flash("success","Book deleted successfully!");
        return ok();
    }

    public Result upload() {
        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> evidence = body.getFile("evidence");
        if (evidence != null) {
            String fileName = evidence.getFilename();
            String contentType = evidence.getContentType();
            File file = evidence.getFile();
            return ok("File uploaded");
        } else {
            flash("error", "Missing file");
            return badRequest();
        }
    }



}