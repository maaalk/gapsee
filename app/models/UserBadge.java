package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
public class UserBadge extends Model {

    @ManyToOne
    private User user;

    @ManyToOne
    private Badge badge;

    @Enumerated(EnumType.STRING)
    private BadgeStatus status;

    public UserBadge(User user, Badge badge){
        this.setUser(user);
        this.setBadge(badge);
        this.setStatus(BadgeStatus.SUBMITTED);

    }

    public static Finder<Integer, UserBadge> find = new Finder<>(UserBadge.class);

    public UserBadge findUserBadge(Integer userId, Integer badgeId){
        return find.query()
                .where().eq("USER_ID",userId)
                .where().eq("BADGE_ID", badgeId)
                .findOne();
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
}
