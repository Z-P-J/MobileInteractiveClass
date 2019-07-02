package com.interactive.classroom.dao.filters;

import com.iWen.survey.dto.User;
import com.interactive.classroom.constant.UserType;
import com.interactive.classroom.utils.TextUtil;

/**
 * @author Z-P-J
 */
public class UserFilter extends BaseFilter {

    private String courseId;

    UserFilter() { }

    @Override
    public String getQuerySql(String tableName) {
        String sql = "";
        if (UserType.TEACHER.equals(userType)) {
            if (TextUtil.isEmpty(courseId)) {
                sql = "select user_manage.*,course_manage.*,course_student.*" +
                        " from user_manage,course_manage,course_student" +
                        " where course_manage.teacher_id=" + userId +
                        " and course_manage.id=course_student.course_id" +
                        " and course_student.student_id=user_manage.id";
            } else {
                sql = "select user_manage.*,course_student.*" +
                        " from user_manage,course_student" +
                        " where course_manage.teacher_id=" + userId +
                        " and course_student.course_id=" + courseId +
                        " and course_student.student_id=user_manage.id";
            }
        } else if (UserType.MANAGER.equals(userType)){
            sql = "select * from " + tableName;
        }

        if (!TextUtil.isEmpty(keyword)) {
            sql = checkWhere(sql);
            sql += "(user_name like '%" + keyword + "%' or name like '%" + keyword + "%')";
        }
        if (!TextUtil.isEmpty(timeFrom) && !TextUtil.isEmpty(timeTo)) {
            sql = checkWhere(sql);
            sql += "register_date between '" + timeFrom + "' and '" + timeTo + "'";
        }
        sql = sql + " group by user_manage.id";
        sql += wrapOrder(order);
        return sql;
    }

    @Override
    public String getStatisticSql(String tableName) {
        String sql = "select date_format(register_date,\"" + getTimeInterval() + "\") as time_interval,count(*) as count";
        if (UserType.TEACHER.equals(tableName)) {
            sql += ",user_manage.*,course_manage.*,course_student.*" +
                    " from user_manage,course_manage,course_student" +
                    " where course_manage.teacher_id=" + userId +
                    " and course_manage.id=course_student.course_id" +
                    " and course_student.student_id=user_manage.id";
        } else {
            sql += " from " + tableName;
        }
        sql = checkWhere(sql);
        sql = sql + "register_date between \"" + timeFrom + "\" and \"" + timeTo + "\"";
        sql = sql + " group by time_interval order by time_interval";
        return sql;
    }

    @Override
    public String getDefaultOrder() {
        return "register_date";
    }

    @Override
    public UserFilter setOrder(String order) {
        this.order = order;
        return this;
    }

    @Override
    public UserFilter setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public UserFilter setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    @Override
    public UserFilter setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    @Override
    public UserFilter setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
        return this;
    }

    @Override
    public UserFilter setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    @Override
    public UserFilter setTimeTo(String timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }
}
