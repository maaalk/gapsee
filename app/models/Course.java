package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course extends Model {
    @Id
    private Integer id;
    private String name;
    @OneToMany
    private List<Level> levelList;
    @OneToMany
    private List<UserCourse> userCourses;

    public static Finder<Integer, Course> find = new Finder<>(Course.class);

    public static List<Course> findByUser(User user){
        System.out.println("Course.findByUser:");
        List<Course> courseList = Course.find.query()
                .fetch("userCourses")
                .where().eq("userCourses.user", user)
                .findList();
        System.out.println(courseList.toString());
        return courseList;
    }

    public static List<Course> findByUserRole(User user, UserRole role){
        System.out.println("Course.findByUser:");
        List<Course> courseList = Course.find.query()
                .fetch("userCourses")
                .where().eq("userCourses.user", user)
                .where().eq("userCourses.role",role)
                .findList();
        System.out.println(courseList.toString());
        return courseList;
    }


    public List<Badge> getBadgeList(){
        List<Badge> badgeList= new ArrayList<Badge>();
        for(Level level:levelList){
            badgeList.addAll(level.getBadgeList());
        }
        return badgeList;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Level> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<Level> levelList) {
        this.levelList = levelList;
    }

    public void addLevel(Level level){
        if (this.levelList.contains(level)){
            return;
        }
        else{
            this.levelList.add(level);
        }
    }

    public Integer countBadges(){
        int count=0;
        for(Level level:levelList){
            count+= level.getBadgeList().size();
        }
        return count;
    }

}
