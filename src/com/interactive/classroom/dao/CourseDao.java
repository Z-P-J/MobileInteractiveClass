package com.interactive.classroom.dao;

import com.interactive.classroom.dao.filters.CourseFilter;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

/**
 * @author Z-P-J
 */
public interface CourseDao {

    /**
     * 表名
     */
    String TABLE_NAME = "course_manage";
    /**
     * 数据库中的字段
     */
    String[] LABELS = {"id", "teacher_id", "teacher_name", "course_name", "attendance_count"};
    /**
     * 数据库中字段中文解释
     */
    String[] LABELS_CH = {"ID", "教师ID", "教师姓名", "课程名", "考勤次数"};

    /**
     * 查询课程
     * @param filter CourseFilter
     * @return JSONObject
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    JSONObject queryCourses(CourseFilter filter) throws JSONException, SQLException;

    /**
     * 每次用户添加考勤成功后要增加考勤次数
     * @param courseId 课程ID
     * @param newCount 新的考勤次数
     * @return 是否成功
     * @throws SQLException SQLException
     */
    boolean increaseAttendanceCount(String courseId, int newCount);

}
