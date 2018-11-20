package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
public class UserBadge extends Model {
    @Id
    private Integer id;


    @ManyToOne
    private User user;
    @ManyToOne
    private Badge badge;
    @Enumerated(EnumType.STRING)
    private BadgeStatus status;
    @OneToMany
    private List<Evidence> evidenceList;
    private Date lastUpdate;

    public UserBadge(User user, Badge badge){
        this.setUser(user);
        this.setBadge(badge);
        this.setStatus(BadgeStatus.SUBMITTED);

    }



    public static Finder<Integer, UserBadge> find = new Finder<>(UserBadge.class);

    public static UserBadge findUserBadge(Integer userId, Integer badgeId){
        System.out.println("FIND USER BADGE");
        try{
            UserBadge userBadge= UserBadge.find.query()
                    .where().eq("USER_ID",userId)
                    .where().eq("BADGE_ID", badgeId)
                    .findOne();
            System.out.println(userBadge.toString());
            return userBadge;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public BadgeStatus getStatus() {
        return status;
    }

    public void setStatus(BadgeStatus status) {
        this.status = status;
    }

    public List<Evidence> getEvidenceList() {
        return evidenceList;
    }

    public void addEvidenceList(Evidence evidence) {
        this.evidenceList.add(evidence);
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String showLastUpdate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(this.lastUpdate);
    }
}
