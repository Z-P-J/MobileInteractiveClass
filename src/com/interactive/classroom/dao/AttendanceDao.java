package com.interactive.classroom.dao;

import com.interactive.classroom.bean.AttendanceBean;
import com.interactive.classroom.bean.HomeworkBean;
import com.interactive.classroom.dao.filters.AttendanceFilter;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

/**
 * @author Z-P-J
 */
public interface AttendanceDao {

    /**
     * 表名
     */
    String TABLE_NAME = "attendance_manage";
    /**
     * 数据库中的字段
     */
    String[] LABELS = {"id", "publisher_id", "publisher_name", "attendance_title", "attendance_requirement", "publish_time", "deadline", "course_id", "attendance_flag"};
    /**
     * 数据库中字段中文解释
     */
    String[] LABELS_CH = {"ID", "发布人ID", "发布人名字", "考勤课程", "考勤要求", "发布时间", "截止时间", "课程id", "考勤flag"};

    /**
     * 过滤查询考勤
     * @param filter HomeworkFilter
     * @return org.json.JSONObject
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    JSONObject queryAttendances(AttendanceFilter filter) throws SQLException, JSONException;

    /**
     * 教师更新考勤信息
     * @param bean 带更新的AttendanceBean对象
     * @return org.json.JSONObject
     */
    JSONObject updateAttendanceInfo(AttendanceBean bean) throws JSONException;

    /**
     * 教师发布考勤
     * @param bean 待发布的AttendanceBean对象
     * @return JSONObject
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    JSONObject publishAttendance(AttendanceBean bean) throws JSONException, SQLException;

    /**
     * 根据id删除考勤
     * @param ids 待删除的ids
     * @return JSONObject
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    JSONObject deleteAttendance(String[] ids) throws JSONException, SQLException;

    /**
     * 考勤
     * @return 考勤是否成功
     */
    boolean checkAttendance(String userId, String attendanceId, String courseId) throws SQLException;

}
