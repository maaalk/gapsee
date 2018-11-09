package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
public class Level  extends Model {

    @Id
    private Integer id;
    private String finalDate;
    private String levelName;
    private String bossFightName;
    @OneToMany
    private List<Badge> badgeList;
    @ManyToOne
    private Course course;


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public static Finder<Integer, Level> find = new Finder<>(Level.class);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
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

    public void addBadge(Badge badge) {
        if(this.badgeList.contains(badge)){
            return;
        }
        else {
            this.badgeList.add(badge);
        }

    }

   /* public List<String> allDates(){
        List<String> levelDates = Badge.find.query()
                .setDistinct(true)
                .select("finalDate")
                .where().eq("LEVEL_ID",this.getId())
                .orderBy("finalDate")
                .findSingleAttributeList();
        return levelDates;
    }*/

   /* public List<String> allTopics(){
        List<String> levelTopics = Badge.find.query()
                .setDistinct(true)
                .select("topic")
                .where().eq("LEVEL_ID",this.getId())
                .orderBy("TOPIC")
                .findSingleAttributeList();
        return levelTopics;
    }*/


    public List<Date> allDates(){
        List<Date> listDates = new ArrayList<>();
        for (Badge badge:this.badgeList){
            if(!listDates.contains(badge.getFinalDate())){
                listDates.add(badge.getFinalDate());
            }
        }
        Collections.sort(listDates);
        return listDates;
    }

    public List<String> topicsByDate(Date date){
        List<String> listTopics = new ArrayList<>();
        for (Badge badge:this.badgeList){
            if((date.equals(badge.getFinalDate()))&&(!listTopics.contains(badge.getTopic())) ){
                listTopics.add(badge.getTopic());
            }
        }
        return listTopics;
    }




}
