package models;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

public class Badge{

    private Integer id;
    private String name;
    private String description;
    private String category;



    private Date deadline;
    private Integer points;

    public Badge(){

    }

    public Badge(Integer id, String name, String description, String category, Integer points){
        this.id=id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.points = points;


    }

    private static Set<Badge> badges;

    static{
        badges = new HashSet<>();
        Badge badge1= new Badge(1, "Trofeu 1", "Lorem ipsum dolor sit amet", "Requirements", 1);
        Badge badge2= new Badge(2,"Trofeu 2", "Lorem ipsum dolor sit amet", "ProjMngmt", 2);
        Badge badge3= new Badge(3,"Trofeu 3", "Lorem ipsum dolor sit amet", "Verification", 3);
        badges.add(badge1);
        badges.add(badge2);
        badges.add(badge3);
    }

    public static Set<Badge> allBadges(){
        return badges;
    }

    public static Badge findById(Integer id) {
        for (Badge badge: badges){
            if (id.equals(badge.getId())){
                return badge;
            }
        }
    
        return null;
    }

    public static void add(Badge badge){
        badges.add(badge);
    }

    public static boolean remove(Badge badge) {
        return badges.remove(badge);
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    /*public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }*/
}
