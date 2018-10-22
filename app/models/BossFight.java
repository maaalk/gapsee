package models;

import java.util.Date;

public class BossFight {

    private Date date;
    private Integer description;
    private String  name;

    public BossFight() {
    }

    public BossFight(String name, Integer description, Date date) {
        this.date = date;
        this.description = description;
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDescription() {
        return description;
    }

    public void setDescription(Integer description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
