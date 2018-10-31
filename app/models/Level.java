package models;

import java.util.Date;
import java.util.List;

public class Level {

    private Integer id;
    private Date finalDate;
    private String levelName;
    private String bossFightName;
    private List<Badge> badgeList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getBossFightName() {
        return bossFightName;
    }

    public void setBossFightName(String bossFightName) {
        this.bossFightName = bossFightName;
    }

    public List<Badge> getBadgeList() {
        return badgeList;
    }

    public void setBadgeList(List<Badge> badgeList) {
        this.badgeList = badgeList;
    }
}
