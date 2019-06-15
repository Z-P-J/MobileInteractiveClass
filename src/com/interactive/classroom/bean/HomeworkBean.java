package com.interactive.classroom.bean;

/**
 * @author Z-P-J
 */
public class HomeworkBean {
    //数据库字段
    private String id;
    private String publisherId;
    private String publisherName;
    private String homeworkTitle;
    private String homeworkRequirement;
    private String publishTime;
    private String deadline;
    private final String fileNameFormat = "学号_姓名_作业名";

    public HomeworkBean() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getHomeworkTitle() {
        return homeworkTitle;
    }

    public void setHomeworkTitle(String homeworkTitle) {
        this.homeworkTitle = homeworkTitle;
    }

    public String getHomeworkRequirement() {
        return homeworkRequirement;
    }

    public void setHomeworkRequirement(String homeworkRequirement) {
        this.homeworkRequirement = homeworkRequirement;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getFileNameFormat() {
        return fileNameFormat;
    }
}
