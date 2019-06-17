package com.interactive.classroom.dao.filters;

import com.interactive.classroom.utils.TextUtil;

/**
 * @author Z-P-J
 */
public class FileFilter extends BaseFilter {

    private String homeworkId;

    private boolean getComments;

    private String timeFrom;

    private String timeTo;

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
    public FileFilter setOrder(String order) {
        this.order = order;
        return this;
    }

    @Override
    public String getQuerySql(String tableName) {
        String sql = "select * from " + tableName;
        if (!TextUtil.isEmpty(homeworkId)) {
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
    public String getDefaultOrder() {
        return "upload_time";
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

    public FileFilter setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    public FileFilter setTimeTo(String timeTo) {
        this.timeTo = timeTo;
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

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
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
