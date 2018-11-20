package controllers;
import models.Course;
import models.User;
import models.UserCourse;
import play.api.mvc.Session;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.ActionAuthenticator;
import views.html.course.*;

import java.util.List;

@Security.Authenticated(ActionAuthenticator.class)

public class CourseController extends Controller {

    public Result index(){
        System.out.println(session("connected"));

        User user = User.findByUserName(session("username"));
        List<Course> courseList = UserCourse.courseList(user.getId());

        System.out.println(courseList.toString());


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
        return ok(courseshow.render(course,course.getLevelList()));
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
