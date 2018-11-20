package models;


import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;

@Entity
public class UserCourse extends Model {

    @ManyToOne
    private User user;

    @ManyToOne
    private Course course;

    @Enumerated(EnumType.STRING)
    private UserRole role;


    public static Finder<Integer, UserCourse> find = new Finder<>(UserCourse.class);

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }



    public static List<Course> courseList(Integer id){

        List<Course> listCourse = UserCourse.find.query()
                .select("course")
                .where().eq("user",id)
                .findSingleAttributeList();

        return listCourse;
    }


}
