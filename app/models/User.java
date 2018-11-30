package models;


import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Entity
@Table(name="PEOPLE")
public class User extends Model {

    @Id
    private Integer id;
    @Column(unique=true)
    private String  username;
    private String  password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @OneToMany
    private List<UserBadge> userBadges;
    @OneToMany
    private List<UserCourse > userCourses;



    public static Finder<Integer, User> find = new Finder<>(User.class);

    public static User findByUserName(String username){
        User user = User.find.query()
                .where().eq("USERNAME",username)
                .findOne();
        return user;

    }

    public static List<User> findStudentsByCourse(Course course){
        System.out.println("User.findStudentsByCourse");
        List<User> userList = User.find.query()
                .fetch("userCourses")
                .where().eq("userCourses.course",course)
                .where().eq("userCourses.role",UserRole.STUDENT)
                .findList();
        System.out.println(userList);
        return userList;
    }

    public static List<User> findByCourse(Course course){
        System.out.println("User.findStudentsByCourse");
        List<User> userList = User.find.query()
                .fetch("userCourses")
                .where().eq("userCourses.course",course)
                .findList();
        System.out.println(userList);
        return userList;
    }

    public Integer getScore(Course course){
        Optional<UserCourse> userCourse = this.userCourses.stream()
                .filter(p->p.getCourse().equals(course))
                .findAny();
        if(userCourse.isPresent()){
            return userCourse.get().getScore();
        }
        return 0;
    }

    public Integer getRank(Course course){
        Optional<UserCourse> userCourse = this.userCourses.stream()
                .filter(p->p.getCourse().equals(course))
                .findAny();
        if(userCourse.isPresent()){
            Map<Integer,List<UserCourse>> leaderboard = course.getRanking();
            for(Map.Entry<Integer,List<UserCourse>> entry:leaderboard.entrySet()){
                if(entry.getValue().contains(userCourse.get())){
                    return entry.getKey();
                }
            }
        }

        return 0;
    }



    public static List<User> listByBadgeSubmission(Badge badge){
        System.out.println("User.listByBadgeSubmission: ");
        List<User> userList = User.find.query()
                .fetch("userBadges")
                .where().eq("userBadges.badge",badge)
                .findList();
        System.out.println(userList.toString());
        return userList;
    }

    public static User authenticate(String username, String password) throws Exception{
        User user = User.find.query()
                .where().eq("USERNAME", username)
                .where().eq("PASSWORD", password)
                .findOne();
//        if (user.equals(null)){
//            return null;
//        } else {
            return user;
//        }

    }

    public Integer calculateScore(Course course){
        Integer score=0;
        System.out.println("User.courseScore ("+course+"):");
        Optional<UserCourse> userCourseFound = findUserCourse(this.userCourses,course);

        if (userCourseFound.isPresent()){
            for (UserBadge userBadge: this.userBadges ){
                if (userBadge.getStatus().equals(BadgeStatus.EARNED) && userBadge.getBadge().getCourse().equals(course)){
                    score+=userBadge.getBadge().getTier();
                }
            }
            System.out.println(score+":  pontos");
            return score;
        } else {
            System.out.println("Aluno não matriculado neste curso! Return 0");
            return score;
        }
    }

    public Integer getNumberTrophy(Course course){
        Integer trohphyCount=0;
        System.out.println("User.courseScore ("+course+"):");
        Optional<UserCourse> userCourseFound = findUserCourse(this.userCourses,course);

        if (userCourseFound.isPresent()){
            for (UserBadge userBadge: this.userBadges ){
                if(userBadge.getStatus().equals(BadgeStatus.EARNED) && userBadge.getBadge().getCourse().equals(course)){
                    trohphyCount++;
                }
            }
            return trohphyCount;
        } else {
            System.out.println("Aluno não matriculado neste curso! Return 0");
            return trohphyCount;
        }
    }

    public Integer getNumberTrophy(Course course, Integer tier){
        Integer trohphyCount=0;
        System.out.println("User.courseScore ("+course+"):");
        Optional<UserCourse> userCourseFound = findUserCourse(this.userCourses,course);

        if (userCourseFound.isPresent()){
            for (UserBadge userBadge: this.userBadges ){
                if(userBadge.getStatus().equals(BadgeStatus.EARNED) && userBadge.getBadge().getCourse().equals(course) && userBadge.getBadge().getTier().equals(tier)){
                    trohphyCount++;
                }
            }
            return trohphyCount;
        } else {
            System.out.println("Aluno não matriculado neste curso! Return 0");
            return trohphyCount;
        }
    }

    public List<UserBadge> getUserBadges() {
        return userBadges;
    }

    public void setUserBadges(List<UserBadge> userBadges) {
        this.userBadges = userBadges;
    }

    public List<UserCourse> getUserCourses() {
        return userCourses;
    }

    public void setUserCourses(List<UserCourse> userCourses) {
        this.userCourses = userCourses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<UserBadge> getUserBadge() {
        return userBadges;
    }

    public void addUserBadge(UserBadge userBadge) {
        userBadges.add(userBadge);
    }

    private final Optional<UserCourse> findUserCourse(Collection<UserCourse> yourList, Course course){
        // This stream will simply return any carnet that matches the filter. It will be wrapped in a Optional object.
        // If no carnets are matched, an "Optional.empty" item will be returned
        return yourList.stream().filter(c -> c.getCourse().equals(course)).findAny();
    }

    private final Optional<UserBadge> findUserBadge(Collection<UserBadge> yourList, Badge badge){
        // This stream will simply return any carnet that matches the filter. It will be wrapped in a Optional object.
        // If no carnets are matched, an "Optional.empty" item will be returned
        return yourList.stream().filter(c -> c.getBadge().equals(badge)).findAny();
    }



    //findUserByScore?

}
