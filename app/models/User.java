package models;


import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;

import java.util.List;


@Entity
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



    public static Finder<Integer, User> find = new Finder<>(User.class);

    public static User findByUserName(String username){
        User user = User.find.query()
                .where().eq("USERNAME",username)
                .findOne();
        return user;

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


}
