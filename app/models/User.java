package models;


import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.HashMap;

@Entity
public class User extends Model {

    @Id
    private Integer id;
    @Column(unique=true)
    private String  username;
    private String  password;
    @Enumerated(EnumType.STRING)
    private UserRole role;



    public static Finder<Integer, User> find = new Finder<>(User.class);

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
}
