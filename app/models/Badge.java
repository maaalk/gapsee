package models;

import io.ebean.*;

import javax.persistence.*;

import play.data.validation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Entity
public class Badge extends Model{

    @Id
    private Integer id;
    @Constraints.Required
    private String name;
    @Constraints.Required
    private String description;
    @Constraints.Required
    private String topic;
    private String finalDate;
    @Constraints.Required
    private Integer tier;
    @ManyToOne
    private Level level;
    @Enumerated(EnumType.STRING)
    private BadgeStatus status;


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

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getTier() {
        return this.tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public BadgeStatus getStatus() {
        return status;
    }

    public void setStatus(BadgeStatus status) {
        this.status = status;
    }


}
