package com.interactive.classroom.dao.filters;

import com.interactive.classroom.constant.UserType;
import com.interactive.classroom.utils.TextUtil;

/**
 * @author Z-P-J
 */
public class FileFilter extends BaseFilter {

    private String homeworkId;

    private boolean getComments;

//    private String timeFrom;
//
//    private String timeTo;

    private String minSize;

    private String maxSize;

    FileFilter() { }

    //----------------------------------------override------------------------------------

    @Override
    public FileFilter setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public FileFilter setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    @Override
    public FileFilter setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    @Override
    public FileFilter setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
        return this;
    }

    @Override
    public FileFilter setOrder(String order) {
        this.order = order;
        return this;
    }

    @Override
    public String getQuerySql(String tableName) {

        String sql = "";
        if (TextUtil.isEmpty(homeworkId)) {
            if (UserType.STUDENT.equals(userType) || UserType.TEACHER.equals(userType)) {
                sql = "select * from " + tableName + " where uploader_id=" + userId;
            } else if (UserType.MANAGER.equals(userType)){
                sql = "select * from " + tableName;
            }
        } else {
            sql = "select * from " + tableName;
            sql += " where homework_id=" + homeworkId;
        }

        if (!TextUtil.isEmpty(keyword)) {
            sql = checkWhere(sql);
            sql += "(file_name like '%" + keyword + "%' or uploader_name like '%" + keyword + "%')";
        }
        if (!TextUtil.isEmpty(timeFrom) && !TextUtil.isEmpty(timeTo)) {
            sql = checkWhere(sql);
            sql += "upload_time between '" + timeFrom + "' and '" + timeTo + "'";
        }
        if (!TextUtil.isEmpty(minSize) && !TextUtil.isEmpty(maxSize)) {
            sql = checkWhere(sql);
            sql += "file_size between " + minSize + " and " + maxSize;
        }
        sql += wrapOrder(order);
        return sql;
    }

    @Override
    public String getStatisticSql(String tableName) {
        String sql = "select date_format(upload_time,\"" + getTimeInterval() + "\") as time_interval,count(*) as count";
        if (TextUtil.isEmpty(homeworkId)) {
            if (UserType.STUDENT.equals(userType) || UserType.TEACHER.equals(userType)) {
                sql += " from " + tableName + " where uploader_id=" + userId;
            } else if (UserType.MANAGER.equals(userType)){
                sql += " from " + tableName;
            }
        } else {
            sql += " from " + tableName;
            sql += " where homework_id=" + homeworkId;
        }
        sql = checkWhere(sql);
        sql = sql + "upload_time between \"" + timeFrom + "\" and \"" + timeTo + "\"";
        sql = sql + " group by time_interval order by time_interval";
        return sql;
    }

    @Override
    public String getDefaultOrder() {
        return "upload_time";
    }

    @Override
    public FileFilter setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    @Override
    public FileFilter setTimeTo(String timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    //----------------------------------------setter------------------------------------

    public FileFilter setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
        return this;
    }

    public FileFilter setGetComments(boolean getComments) {
        this.getComments = getComments;
        return this;
    }

    public FileFilter setMinSize(String minSize) {
        this.minSize = minSize;
        return this;
    }

    public FileFilter setMaxSize(String maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    //---------------------------------------------getter-----------------------------------------

    public String getHomeworkId() {
        return homeworkId;
    }

    public boolean isGetComments() {
        return getComments;
    }

    public String getMinSize() {
        return minSize;
    }

    public String getMaxSize() {
        return maxSize;
    }

    @Override
    public String toString() {
        return "FileFilter{" +
                "homeworkId='" + homeworkId + '\'' +
                ", userId='" + userId + '\'' +
                ", userType=" + userType +
                ", getComments=" + getComments +
                ", keyword='" + keyword + '\'' +
                ", timeFrom='" + timeFrom + '\'' +
                ", timeTo='" + timeTo + '\'' +
                ", minSize='" + minSize + '\'' +
                ", maxSize='" + maxSize + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
