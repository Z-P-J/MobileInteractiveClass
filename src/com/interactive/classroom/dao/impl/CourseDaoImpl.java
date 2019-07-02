package com.interactive.classroom.dao.impl;

import com.interactive.classroom.bean.CourseBean;
import com.interactive.classroom.dao.CourseDao;
import com.interactive.classroom.dao.filters.CourseFilter;
import com.interactive.classroom.utils.DatabaseHelper;
import com.interactive.classroom.utils.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Z-P-J
 */
public class CourseDaoImpl implements CourseDao {

    @Override
    public JSONObject queryCourses(CourseFilter filter) throws JSONException, SQLException {
        String sql = filter.getQuerySql(TABLE_NAME);
        Log.d(getClass().getName(), "queryCourses sql=" + sql);
        ResultSet rs = DatabaseHelper.executeQuery(sql);
        JSONArray jsonArray = new JSONArray();
        while (rs.next()) {
            JSONObject jsonObject = new JSONObject();
            for (String label : LABELS) {
                jsonObject.put(label, rs.getString(label));
            }
            jsonArray.put(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aaData", jsonArray);
        DatabaseHelper.putTableColumnNames(LABELS, LABELS_CH, jsonObject);
        jsonObject.put("result_msg", "ok");
        jsonObject.put("result_code", 0);
        return jsonObject;
    }

    @Override
    public JSONObject addCourse(CourseBean bean) throws JSONException, SQLException {
        String sql = "insert into " + TABLE_NAME + "(teacher_id,teacher_name,course_name,attendance_count) values("
                + bean.getTeacherId() + ",'"
                + bean.getTeacherName() + "','"
                + bean.getCourseName() + "',"
                + bean.getAttendanceCount() + ")";
        Log.d(getClass().getName(), "addCourse sql=" + sql);
        boolean isSuccess = DatabaseHelper.executeUpdate(sql);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", isSuccess ? "ok" : "更新课程信息失败！");
        jsonObj.put("result_code", isSuccess ? 0 : 10);
        return jsonObj;
    }

    @Override
    public JSONObject joinCourse(String userId, String courseId) throws JSONException, SQLException {
        String sql = "insert into course_student (course_id, student_id) values(" + courseId + "," + userId + ")";
        boolean isSuccess = DatabaseHelper.executeUpdate(sql);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", isSuccess ? "ok" : "更新课程信息失败！");
        jsonObj.put("result_code", isSuccess ? 0 : 10);
        return jsonObj;
    }

    @Override
    public JSONObject exitCourse(String userId, String courseId) throws JSONException, SQLException {
        String sql = "delete from course_student" +
                " where course_id=" + courseId +
                " and student_id=" + userId;
        boolean isSuccess = DatabaseHelper.executeUpdate(sql);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", isSuccess ? "ok" : "删除课程失败！");
        jsonObj.put("result_code", isSuccess ? 0 : 10);
        return jsonObj;
    }

    @Override
    public JSONObject deleteCourse(String id) throws JSONException {
        String sql = "delete from " + TABLE_NAME + " where id=" + id;
        boolean isSuccess = DatabaseHelper.executeUpdate(sql);

        if (isSuccess) {
            sql = "delete from course_student where course_id=" + id;
            isSuccess = DatabaseHelper.executeUpdate(sql);
        }

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", isSuccess ? "ok" : "删除课程失败！");
        jsonObj.put("result_code", isSuccess ? 0 : 10);
        return jsonObj;
    }

    @Override
    public JSONObject updateCourse(CourseBean bean) throws JSONException, SQLException {
        String sql = "UPDATE " + TABLE_NAME + " set course_name=?,teacher_name=? where id=" + bean.getId();
        boolean isSuccess = DatabaseHelper.executeUpdate(
                sql, bean.getCourseName(), bean.getTeacherName());
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", isSuccess ? "ok" : "更新课程信息失败！");
        jsonObj.put("result_code", isSuccess ? 0 : 10);
        return jsonObj;
    }

    @Override
    public boolean increaseAttendanceCount(String courseId, int newCount) {
        String sql = "update " + TABLE_NAME + " set attendance_count=" + newCount+ " where id=" + courseId;
        return DatabaseHelper.executeUpdate(sql);
    }

}
