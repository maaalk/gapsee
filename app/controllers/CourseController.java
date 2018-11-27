package controllers;
import models.*;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.ActionAuthenticator;
import utils.TutorActionAuthenticator;
import views.html.course.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Security.Authenticated(ActionAuthenticator.class)
public class CourseController extends Controller {

    public Result index(){
        if(session("role").equals(UserRole.TUTOR.toString())|| session("role").equals(UserRole.ADMIN.toString())){
            return redirect(routes.CourseController.tutorIndex());
        }
        List<Course> courseList = Course.find.all();
        User user = User.findByUserName(session("username"));
        List<Course> userCourses = Course.findByUser(user);
        courseList.removeAll(userCourses);
        return ok(courseindex.render(courseList, userCourses));
    }

    @Security.Authenticated(TutorActionAuthenticator.class)
    public Result tutorIndex(){
        User user = User.findByUserName(session("username"));
        List<Course> courseList;
        if(session("role").equals(UserRole.ADMIN.toString())){
            courseList = Course.find.all();
        } else {
            courseList = Course.findByUserRole(user,UserRole.TUTOR);
        }
        return ok(tutorcourseindex.render(courseList));
    }

    public Result join(Integer courseId){
        User user = User.findByUserName(session("username"));
        Course course = Course.find.byId(courseId);
        UserCourse userCourse = new UserCourse(user,course);
        userCourse.save();
        flash("success","You joined the course ' "+course.getName()+"'");
        return redirect(routes.CourseController.index());

    }

    public Result quit(Integer courseId){
        User user = User.findByUserName(session("username"));
        Course course = Course.find.byId(courseId);
        UserCourse userCourse = UserCourse.findUserCourse(user, course);
        userCourse.delete();
        flash("success","You left the course ' "+course.getName()+"'");
        return redirect(routes.CourseController.index());
    }

    @Security.Authenticated(TutorActionAuthenticator.class)
    public Result tutorShow(Integer courseId){
        Course course = Course.find.byId(courseId);
        List<User> userList = User.findByCourse(course);
        List<Badge> pendingBadgeList = Badge.findByStatus(BadgeStatus.SUBMITTED);
        userList.sort(Comparator.comparing(User::getRole).reversed());
        return ok(coursemanager.render(course,userList,course.getLevelList(),pendingBadgeList));
    }

    public Result leaderboard(Integer courseId){
        Course course = Course.find.byId(courseId);
        List<User> userList = course.getUsers(UserRole.STUDENT);
        return ok(leaderboard.render(course,userList));
    }

    public Result userLeaderboard(Integer courseId){
        Course course = Course.find.byId(courseId);
        List<User> userList = course.getUsers(UserRole.STUDENT);
        User user = User.findByUserName(session("username"));
        return ok(userleaderboard.render(course,userList,user));
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
        if(session("role").equals(UserRole.TUTOR.toString())){
            return redirect(routes.CourseController.tutorShow(id));
        }
        Course course = Course.find.byId(id);
        if (course==null){
            return notFound("Course not found");
        }
        User user = User.findByUserName(session("username"));
        System.out.println(user.getId()+" "+user.getUsername());
        Map<String,UserBadge> badges = UserBadge.findByUser(user.getId());
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
