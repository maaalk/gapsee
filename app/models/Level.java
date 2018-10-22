package models;

import java.util.Set;

public class Level {
    private Set<Badge> badges;

    public BossFight getBossFight() {
        return bossFight;
    }

    // constructor
    public Level(Set<Badge> badges) {
        this.badges = badges;
    }



    public void setBossFight(BossFight bossFight) {
        this.bossFight = bossFight;
    }

    private BossFight bossFight;



    public Set<Badge> getBadges() {
        return badges;
    }

    public void setBadges(Set<Badge> badges) {
        this.badges = badges;
    }

    public void add(Badge badge){
        this.badges.add(badge);
    }

    public void remove(Integer id){
        this.badges.remove(findById(this.badges,id));
    }

    public Badge findById(Set<Badge> badges, Integer id) {
        for (Badge badge : badges) {
            if (id.equals(badge.getId())) {
                return badge;
            }
        }
        return null;
    }
}
