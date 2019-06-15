package com.interactive.classroom.bean;

public class InvestigationBean {
    //数据库字段
    private String id;
    private String parentId;
    private String title;
    private String content;
    private String endTime;
    private String userId;
    private String userRole;
    private String creator;
    private String createTime;
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
    private String link = "";


    public InvestigationBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", endTime='" + endTime + '\'' +
                ", userId='" + userId + '\'' +
                ", userType='" + userRole + '\'' +
                ", creator='" + creator + '\'' +
                ", createTime='" + createTime + '\'' +
                ", action='" + action + '\'' +
                ", tableName='" + tableName + '\'' +
                ", type='" + type + '\'' +
                ", timeFrom='" + timeFrom + '\'' +
                ", timeTo='" + timeTo + '\'' +
                ", groupId='" + groupId + '\'' +
                ", timeInterval='" + timeInterval + '\'' +
                ", sortIndex='" + sortIndex + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

}
