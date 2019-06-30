package com.interactive.classroom.dao.filters;

import com.interactive.classroom.utils.TextUtil;

/**
 * @author Z-P-J
 */
public class HomeworkFilter extends BaseFilter {


    private String courseId;

    private String homeworkId;

    private String publishTimeFrom;

    private String publishTimeTo;

    private String deadlineFrom;

    private String deadlineTo;

    private boolean getFiles;

    HomeworkFilter() { }

    //-----------------------------------override-----------------------------------

    @Override
    public String getQuerySql(String tableName) {
        String sql = "select * from " + tableName;
        if (!TextUtil.isEmpty(homeworkId)) {
            sql += " where id=" + homeworkId;
        } else if (!TextUtil.isEmpty(courseId)) {
            sql += " where course_id=" + courseId;
        }
        if (!TextUtil.isEmpty(keyword)) {
            sql = checkWhere(sql);
            sql += "(homework_title like '%" + keyword + "%' or homework_requirement like '%" + keyword + "%')";
        }
        if (!TextUtil.isEmpty(publishTimeFrom) && !TextUtil.isEmpty(publishTimeTo)) {
            sql = checkWhere(sql);
            sql += "publish_time between '" + publishTimeFrom + "' and '" + publishTimeTo + "'";
        }
        if (!TextUtil.isEmpty(publishTimeFrom) && !TextUtil.isEmpty(publishTimeTo)) {
            sql = checkWhere(sql);
            sql += "deadline between '" + publishTimeFrom + "' and '" + publishTimeTo + "'";
        }
        if (TextUtil.isEmpty(homeworkId)) {
            sql += wrapOrder(order);
        }
        return sql;
    }

    @Override
    public String getStatisticSql(String tableName) {
        return null;
    }

    @Override
    public String getDefaultOrder() {
        return "publish_time";
    }

    @Override
    public HomeworkFilter setOrder(String order) {
        this.order = order;
        return this;
    }

    @Override
    public HomeworkFilter setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public HomeworkFilter setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    @Override
    public HomeworkFilter setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    @Override
    public HomeworkFilter setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
        return this;
    }

    @Override
    public HomeworkFilter setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    @Override
    public HomeworkFilter setTimeTo(String timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    //-----------------------------------setter-----------------------------------

    public HomeworkFilter setCourseId(String courseId) {
        this.courseId = courseId;
        return this;
    }

    public HomeworkFilter setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
        return this;
    }

    public HomeworkFilter setPublishTimeFrom(String publishTimeFrom) {
        this.publishTimeFrom = publishTimeFrom;
        return this;
    }

    public HomeworkFilter setPublishTimeTo(String publishTimeTo) {
        this.publishTimeTo = publishTimeTo;
        return this;
    }

    public HomeworkFilter setDeadlineFrom(String deadlineFrom) {
        this.deadlineFrom = deadlineFrom;
        return this;
    }

    public HomeworkFilter setDeadlineTo(String deadlineTo) {
        this.deadlineTo = deadlineTo;
        return this;
    }

    public HomeworkFilter setGetFiles(boolean getFiles) {
        this.getFiles = getFiles;
        return this;
    }

    //-----------------------------------getter-----------------------------------

    public String getCourseId() {
        return courseId;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public String getPublishTimeFrom() {
        return publishTimeFrom;
    }

    public String getPublishTimeTo() {
        return publishTimeTo;
    }

    public String getDeadlineFrom() {
        return deadlineFrom;
    }

    public String getDeadlineTo() {
        return deadlineTo;
    }

    public boolean isGetFiles() {
        return getFiles;
    }


}
