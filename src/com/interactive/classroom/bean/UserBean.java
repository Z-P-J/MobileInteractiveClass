package com.interactive.classroom.bean;

public class UserBean {
    //数据库字�?
    private String id;
    private String parentId;
    private String title;
    private String content;
    private String limitTime;
    private String userId;
    private String userRole;
    private String creator;
    private String createTime;
    //传�?�条件查询用
    private String action;
    private String dbName;
    private String type = "";
    private String timeFrom = "";
    private String timeTo = "";
    private String timeSelect = "";
    private String groupId = "";
    private String groupSelect = "";
    private String timeInterval = "";
    private String statisticType = "";
    private String sortIndex = "";
    private String orderBy = "";


    private String userName = "";
    private String password = "";
    private String name = "";
    private String sex = "";
    private String email = "";
    private String phone = "";
    private String wechat = "";
    private String grade = "";
    private String classStr = "";
    private String studentNum = "";
    private String faculty = "";

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassStr() {
        return classStr;
    }

    public void setClassStr(String classStr) {
        this.classStr = classStr;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public UserBean() {
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

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime;
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

    public String getTimeSelect() {
        return timeSelect;
    }

    public void setTimeSelect(String timeSelect) {
        this.timeSelect = timeSelect;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupSelect() {
        return groupSelect;
    }

    public void setGroupSelect(String groupSelect) {
        this.groupSelect = groupSelect;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getStatisticType() {
        return statisticType;
    }

    public void setStatisticType(String statisticType) {
        this.statisticType = statisticType;
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

    @Override
    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", limitTime='" + limitTime + '\'' +
                ", userId='" + userId + '\'' +
                ", userType='" + userRole + '\'' +
                ", creator='" + creator + '\'' +
                ", createTime='" + createTime + '\'' +
                ", action='" + action + '\'' +
                ", dbName='" + dbName + '\'' +
                ", type='" + type + '\'' +
                ", timeFrom='" + timeFrom + '\'' +
                ", timeTo='" + timeTo + '\'' +
                ", timeSelect='" + timeSelect + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupSelect='" + groupSelect + '\'' +
                ", timeInterval='" + timeInterval + '\'' +
                ", statisticType='" + statisticType + '\'' +
                ", sortIndex='" + sortIndex + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", wechat='" + wechat + '\'' +
                ", grade='" + grade + '\'' +
                ", classStr='" + classStr + '\'' +
                ", studentNum='" + studentNum + '\'' +
                ", faculty='" + faculty + '\'' +
                '}';
    }
}
