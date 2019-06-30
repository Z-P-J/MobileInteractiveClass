package com.interactive.classroom.bean;

/**
 * @author Z-P-J
 */
public class AttendanceBean {
    //数据库字段
    private String id;
    private String courseId;
    private String publisherId;
    private String publisherName;
    private String attendanceTitle;
    private String attendanceRequirement;
    private String publishTime;
    private String deadline;
    private String attendanceFlag;

    public AttendanceBean() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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

    public String getAttendanceTitle() {
        return attendanceTitle;
    }

    public void setAttendanceTitle(String attendanceTitle) {
        this.attendanceTitle = attendanceTitle;
    }

    public String getAttendanceRequirement() {
        return attendanceRequirement;
    }

    public void setAttendanceRequirement(String attendanceRequirement) {
        this.attendanceRequirement = attendanceRequirement;
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

    public void setAttendanceFlag(String attendanceFlag) {
        this.attendanceFlag = attendanceFlag;
    }

    public String getAttendanceFlag() {
        return attendanceFlag;
    }
}
