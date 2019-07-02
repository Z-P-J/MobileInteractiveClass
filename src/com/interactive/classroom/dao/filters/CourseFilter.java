package com.interactive.classroom.dao.filters;

import com.interactive.classroom.constant.UserType;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.TextUtil;

/**
 * @author Z-P-J
 */
public class CourseFilter extends BaseFilter {

    private String courseId;

    private String studentId;

    private String joined;

    CourseFilter() {

    }

    //----------------------------------------------override------------------------------------

    @Override
    public String getQuerySql(String tableName) {
        String sql;
        if (UserType.TEACHER.equals(userType)) {
            sql = "select * from " + tableName + " where teacher_id=" + userId;
        } else if (UserType.STUDENT.equals(userType)) {
            if ("0".equals(joined)) {
//                sql = "select * from course_manage" +
//                        " where (select count(1) as num from course_student" +
//                        " where course_student.course_id = course_manage.id" +
//                        " and course_student.student_id=" + userId + ") = 0";
                sql = "select * from course_manage" +
                        " where (select count(1) as num" +
                        " from course_student" +
                        " where course_student.course_id = course_manage.id) = 0";
            } else {
                sql = "select course_manage.*,course_student.*"
                        + " from course_manage,course_student"
                        + " where course_student.student_id=" + userId
                        + " and course_student.course_id=course_manage.id";
            }
        } else {
            sql = "select * from " + tableName;
        }
        if (!TextUtil.isEmpty(courseId)) {
            sql = checkWhere(sql);
            sql += "id=" + courseId;
        }
        if (!TextUtil.isEmpty(keyword)) {
            sql = checkWhere(sql);
            sql += "(course_name like '%" + keyword + "%' or teacher_name like '%" + keyword + "%')";
        }
        sql += " group by course_manage.id";
        sql += wrapOrder(order);
        return sql;
    }

    @Override
    public String getStatisticSql(String tableName) {
        return null;
    }

    @Override
    public String getDefaultOrder() {
        return "course_manage.id";
    }

    @Override
    public CourseFilter setOrder(String order) {
        this.order = order;
        return this;
    }

    @Override
    public CourseFilter setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public CourseFilter setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    @Override
    public CourseFilter setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    @Override
    public CourseFilter setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
        return this;
    }

    @Override
    public CourseFilter setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    @Override
    public CourseFilter setTimeTo(String timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    //----------------------------------------setter--------------------------------------------


    public CourseFilter setCourseId(String courseId) {
        this.courseId = courseId;
        return this;
    }

    public CourseFilter setStudentId(String studentId) {
        this.studentId = studentId;
        return this;
    }

    public CourseFilter setJoined(String joined) {
        this.joined = joined;
        return this;
    }

    //-----------------------------------------getter-------------------------------------------


    public String getCourseId() {
        return courseId;
    }

    public String getStudentId() {
        return studentId;
    }

}
