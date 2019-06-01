package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Course extends Model {
    @Id
    private Integer id;
    private String name;
    private String insituicao;
    private Date dataPrevistaTermino;
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

    public List<UserCourse> getUserCourses() {
        return userCourses;
    }

    public void setUserCourses(List<UserCourse> userCourses) {
        this.userCourses = userCourses;
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

    public List<User> getUsers(){
        List<User> userList =new ArrayList<User>();
        for(UserCourse userCourse : this.getUserCourses()){
            userList.add(userCourse.getUser());
        }
        return userList;
    }

    public List<User> getUsers(UserRole userRole){
        List<User> userList =new ArrayList<User>();
        for(UserCourse userCourse : this.getUserCourses()){
            if(userCourse.getRole().equals(userRole)){
                userList.add(userCourse.getUser());
            }
        }
        return userList;
    }

    public List<UserCourse> getUserCourses(UserRole userRole){
        List<UserCourse> students = this.userCourses
                .stream()
                .filter(p->p.getRole().equals(userRole))
                .collect(Collectors.toList());

        return students;
    }

    public Map<Integer, List<UserCourse>> getRanking(){
        SortedMap<Integer,List<UserCourse>> ranking=new TreeMap<>(Collections.reverseOrder());
        this.userCourses.stream()
                .filter(p->p.getRole().equals(UserRole.STUDENT))
                .sorted(Comparator.comparing(UserCourse::getScore).reversed())
                .forEach(userCourse -> {
            if(ranking.containsKey(userCourse.getScore())){
                ranking.get(userCourse.getScore()).add(userCourse);
            }else{
                List<UserCourse> userCourses = new ArrayList<UserCourse>();
                userCourses.add(userCourse);
                ranking.put(userCourse.getScore(),userCourses);
            }
                    System.out.println(userCourse.getScore().toString()+ranking.get(userCourse.getScore()).toString());
        });
        SortedMap<Integer,List<UserCourse>> leaderboard=new TreeMap<>();
        int rank=1;
        for(Map.Entry<Integer, List<UserCourse>> entry : ranking.entrySet()){
            leaderboard.put(rank,entry.getValue());
            rank=rank+entry.getValue().size();
        }
        System.out.println(leaderboard.toString());
        return leaderboard;
    }


}
