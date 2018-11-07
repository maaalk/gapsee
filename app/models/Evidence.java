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
    private Date submissionDate;
    private Date feedbackDate;
    private String fileName;
    private String filePath;
    @ManyToOne
    private Badge badge;
    @Enumerated(EnumType.STRING)
    private EvidenceStatus status;




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

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
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

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String showSubmissionDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(this.submissionDate);
    }

    public String showFeedbackDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(this.feedbackDate);
    }


    public EvidenceStatus getStatus() {
        return status;
    }

    public void setStatus(EvidenceStatus status) {
        if(status==EvidenceStatus.NEW){
            this.status=status;
            this.badge.setStatus(BadgeStatus.SUBMITTED);
            return;
        }
        if(status==EvidenceStatus.ACCEPTED){
            this.status=status;
            this.badge.setStatus(BadgeStatus.EARNED);
            return;
        }
        if(status==EvidenceStatus.REJECTED){
            this.status=status;
            this.badge.setStatus(BadgeStatus.REJECTED);
            return;
        }

    }
}
