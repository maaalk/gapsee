package controllers;
import models.Badge;
import models.Course;
import models.User;
import models.UserBadge;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.ActionAuthenticator;
import views.html.course.*;

import java.util.List;
import java.util.Map;

@Security.Authenticated(ActionAuthenticator.class)

public class CourseController extends Controller {

    public Result index(){
        System.out.println(session("connected"));
        List<Course> courseList = Course.find.all();
        User user = User.findByUserName(session("username"));
        User user2 = User.findByUserName("aluno");
        List<Badge> earnedBadges = Badge.listEarnedBadges(user);
        List<Badge> submittedBadges = Badge.listBadgeSubmissions(user);
        List<Badge> allBadges = Badge.find.all();
        allBadges.removeAll(submittedBadges);
        submittedBadges.removeAll(earnedBadges);
        System.out.println("Earned: "+earnedBadges.toString());
        System.out.println("Submitted: "+submittedBadges.toString());
        System.out.println("Remaining: "+allBadges.toString());
        return ok(courseindex.render(courseList));
    }

    public Result create(){
        return ok();
    }

    public Result save(){
        return ok();
    }

    public Result edit(){
        return ok();
    }

    public Result update(){
        return ok();
    }

    public Result show(Integer id){
        Course course = Course.find.byId(id);
        if (course==null){
            return notFound("Course not found");
        }
        User user = User.findByUserName(session("username"));
        System.out.println(user.getId()+" "+user.getUsername());
        Map<String,UserBadge> badges = UserBadge.findUser(user.getId());
        System.out.println(badges.toString());
        try{
            System.out.println(badges.get(Badge.find.byId(3)).getStatus());
        } catch (Exception e){
            System.out.println(e);
        }

        System.out.println("OK");
        return ok(courseshow.render(course,course.getLevelList(),badges));
    }

    public Result destroy(Integer id){
        Course course = Course.find.byId(id);
        if (course==null){
            flash("danger", "course not Found");
            return notFound();
        }
        course.delete();
        flash("success","course deleted successfully!");
        return ok();
    }

}
