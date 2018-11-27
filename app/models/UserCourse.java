package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Optional;

@Entity
public class UserCourse extends Model {

    @Id
    private Integer id;
    @ManyToOne
    private Course course;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Integer score;



    public UserCourse(User user, Course course){
        this.course=course;
        this.user=user;
        role=UserRole.STUDENT;

    }

    public static Finder<Integer, UserCourse> find = new Finder<>(UserCourse.class);

    public static UserCourse findUserCourse(User user, Course course){
        System.out.println("UserCourse.findUserCourse: ");
        UserCourse userCourse = UserCourse.find.query()
                .where().eq("user",user)
                .where().eq("course", course)
                .findOne();
        System.out.println(userCourse.toString());
        return userCourse;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }





    public void updateScore(){
        this.setScore(this.user.calculateScore(this.course));
        this.update();
    }


}
