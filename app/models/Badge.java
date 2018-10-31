package models;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import io.ebean.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import play.data.validation.*;

@Entity
public class Badge extends Model{

    @Id
    private Integer id;
    @Constraints.Required
    private String name;
    @Constraints.Required
    private String description;
    @Constraints.Required
    private String category;
    private String date;
    @Constraints.Required
    private Integer points;

    public static Finder<Integer, Badge> find = new Finder<>(Badge.class);


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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
