package com.interactive.classroom.dao.filters;

import com.interactive.classroom.constant.UserType;
import com.interactive.classroom.utils.TextUtil;

/**
 * @author Z-P-J
 */
public class AttendanceFilter extends BaseFilter {


    private String courseId;

    private String attendanceId;

    private String publishTimeFrom;

    private String publishTimeTo;

    private String deadlineFrom;

    private String deadlineTo;

    private boolean getFiles;

    AttendanceFilter() { }

    //========================================================override=========================================================

    @Override
    public String getQuerySql(String tableName) {
        String sql;
        if (UserType.STUDENT.equals(userType)) {
            sql = "select " + tableName + ".*,course_student.*" // ,course_manage.*
                    + " from " + tableName + ",course_student" //,course_manage
                    + " where " + tableName + ".course_id = course_student.course_id"
                    + " and course_student.student_id = " + userId;
//                    + " and " + tableName + ".course_id = course_student.course_id";
//                    + " and course_manage.id = course_student.course_id";
        } else if (UserType.TEACHER.equals(userType)) {
            sql = "select * from " + tableName + " where publisher_id=" + userId;
        } else {
            sql = "select * from " + tableName;
        }

        if (!TextUtil.isEmpty(attendanceId)) {
            sql = checkWhere(sql);
            sql += "id=" + attendanceId;
        } else if (!TextUtil.isEmpty(courseId)) {
            sql = checkWhere(sql);
            sql += "course_id=" + courseId;
        }
        if (!TextUtil.isEmpty(keyword)) {
            sql = checkWhere(sql);
            sql += "(attendance_title like '%" + keyword + "%' or attendance_requirement like '%" + keyword + "%')";
        }
        if (!TextUtil.isEmpty(publishTimeFrom) && !TextUtil.isEmpty(publishTimeTo)) {
            sql = checkWhere(sql);
            sql += tableName + ".publish_time between '" + publishTimeFrom + "' and '" + publishTimeTo + "'";
            sql += " or " + tableName + ".deadline between '" + publishTimeFrom + "' and '" + publishTimeTo + "'";
        }
//        if (!TextUtil.isEmpty(publishTimeFrom) && !TextUtil.isEmpty(publishTimeTo)) {
//            sql = checkWhere(sql);
//            sql += "deadline between '" + publishTimeFrom + "' and '" + publishTimeTo + "'";
//        }
        sql += " group by attendance_manage.id";
        if (TextUtil.isEmpty(attendanceId)) {
            sql += wrapOrder(order);
        }
        return sql;
    }

    @Override
    public String getStatisticSql(String tableName) {
        String sql = "select date_format(publish_time,\"" + getTimeInterval() + "\") as time_interval,count(*) as count";
        if (UserType.STUDENT.equals(userType)) {
            sql += "," + tableName + ".*,course_student.*,course_manage.*"
                    + " from " + tableName + ",course_student,course_manage"
                    + " where course_student.student_id = " + userId
                    + " and course_student.course_id = " + tableName + ".course_id"
                    + " and course_manage.id = course_student.course_id";
        } else if (UserType.TEACHER.equals(userType)) {
            sql = " from " + tableName + " where publisher_id=" + userId;
        } else {
            sql = " from " + tableName;
        }
        sql = checkWhere(sql);
        sql = sql + "publish_time between \"" + timeFrom + "\" and \"" + timeTo + "\"";

//        sql = "select date_format(publish_time,\"" + timeInterval + "\") as time_interval,count(*) as count from " + tableName + " a";
//        sql = sql + " where publish_time between \"" + timeFrom + "\" and \"" + timeTo + "\"";
        sql = sql + " group by time_interval order by time_interval";
        return sql;
    }

    @Override
    public String getDefaultOrder() {
        return "publish_time";
    }

    @Override
    public AttendanceFilter setOrder(String order) {
        this.order = order;
        return this;
    }

    @Override
    public AttendanceFilter setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public AttendanceFilter setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    @Override
    public AttendanceFilter setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    @Override
    public AttendanceFilter setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
        return this;
    }

    @Override
    public AttendanceFilter setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    @Override
    public AttendanceFilter setTimeTo(String timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    //==========================================================setter================================================

    public AttendanceFilter setCourseId(String courseId) {
        this.courseId = courseId;
        return this;
    }

    public AttendanceFilter setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
        return this;
    }

    public AttendanceFilter setPublishTimeFrom(String publishTimeFrom) {
        this.publishTimeFrom = publishTimeFrom;
        return this;
    }

    public AttendanceFilter setPublishTimeTo(String publishTimeTo) {
        this.publishTimeTo = publishTimeTo;
        return this;
    }

    public AttendanceFilter setDeadlineFrom(String deadlineFrom) {
        this.deadlineFrom = deadlineFrom;
        return this;
    }

    public AttendanceFilter setDeadlineTo(String deadlineTo) {
        this.deadlineTo = deadlineTo;
        return this;
    }

    public AttendanceFilter setGetFiles(boolean getFiles) {
        this.getFiles = getFiles;
        return this;
    }

    //======================================================getter=====================================================

    public String getCourseId() {
        return courseId;
    }

    public String getAttendanceId() {
        return attendanceId;
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
