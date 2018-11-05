package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Evidence extends Model {

    @Id
    private Integer id;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String feedback;
    private Date date;
    private String fileName;
    private String filePath;
    @ManyToOne
    private Badge badge;




    public static Finder<Integer, Evidence> find = new Finder<>(Evidence.class);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String showDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(this.date);
    };

    public void saveFile(File file, String fileNameme){
        return;
    }

}
