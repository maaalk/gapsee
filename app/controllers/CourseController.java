package controllers;
import models.Course;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.course.*;

import java.util.List;

public class CourseController extends Controller {

    public Result index(){
        List<Course> courseList = Course.find.all();
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
