package com.interactive.classroom.bean;

public class HomeworkFileBean {
    //数据库字段
    private String id;
    private String homeworkId;
    private String uploaderId = "zhangsan";
    private String uploaderName = "zhangsan";
    private String fileName;
    private long fileSize;
    private String uploadTime;
    private String downloadLink;
    //传递条件查询用
    private String action;
    private String tableName = "";
    private String type = "";
    private String timeFrom = "";
    private String timeTo = "";
    private String groupId = "";
    private String timeInterval = "";
    private String sortIndex = "";
    private String orderBy = "";
    private String userId = "";


    public HomeworkFileBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(String sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "id='" + id + '\'' +
                ", homeworkId='" + homeworkId + '\'' +
                ", uploader_id='" + uploaderId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", uploadTime='" + uploadTime + '\'' +
                ", action='" + action + '\'' +
                ", tableName='" + tableName + '\'' +
                ", type='" + type + '\'' +
                ", timeFrom='" + timeFrom + '\'' +
                ", timeTo='" + timeTo + '\'' +
                ", groupId='" + groupId + '\'' +
                ", timeInterval='" + timeInterval + '\'' +
                ", sortIndex='" + sortIndex + '\'' +
                ", orderBy='" + orderBy + '\'' +
                '}';
    }
}
