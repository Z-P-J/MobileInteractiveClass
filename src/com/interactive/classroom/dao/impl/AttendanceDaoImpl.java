package com.interactive.classroom.dao.impl;

import com.interactive.classroom.bean.AttendanceBean;
import com.interactive.classroom.constant.UserType;
import com.interactive.classroom.dao.AttendanceDao;
import com.interactive.classroom.dao.UserDao;
import com.interactive.classroom.dao.filters.AttendanceFilter;
import com.interactive.classroom.utils.DatabaseHelper;
import com.interactive.classroom.utils.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author Z-P-J
 */
public class AttendanceDaoImpl implements AttendanceDao {

    @Override
    public JSONObject queryAttendances(AttendanceFilter filter) throws SQLException, JSONException {
        String sql = filter.getQuerySql(TABLE_NAME);
        Log.d(getClass().getName(), "findHomeworks sql=" + sql);
        String resultMsg = "ok";
        int resultCode = 0;
        JSONArray jsonArray = new JSONArray();
        try {
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                JSONObject jsonObj = new JSONObject();
                for (String label : LABELS) {
                    jsonObj.put(label, rs.getString(label));
                }
                if (UserType.STUDENT.equals(filter.getUserType())) {
                    Log.d(getClass().getName(), "attendance=" + rs.getString("attendance"));
                    String attendances = rs.getString("attendance");
                    if (attendances == null) {
                        jsonObj.put("has_attendance", false);
                    } else {
                        String[] hasAttendances = attendances.split(",");
                        Log.d(getClass().getName(), "hasAttendances=" + Arrays.toString(hasAttendances));
                        boolean hasAttendanced = false;
                        String attendanceFlag = jsonObj.getString("attendance_flag");
                        Log.d(getClass().getName(), "attendanceFlag=" + attendanceFlag);
                        for (String hasAttendance : hasAttendances) {
                            Log.d(getClass().getName(), "hasAttendance=" + hasAttendance);
                            if (hasAttendance.equals(attendanceFlag)) {
                                hasAttendanced = true;
                                break;
                            }
                        }
                        jsonObj.put("has_attendance", hasAttendanced);
                    }
                    jsonObj.put("attendance_count", rs.getString("attendance_count"));
                } else if (UserType.TEACHER.equals(filter.getUserType())) {

                }

                boolean isOwner = filter.getUserId() != null && filter.getUserId().equals(rs.getString("publisher_id"));
                jsonObj.put("isOwner", isOwner ? 1 : 0);
                jsonArray.put(jsonObj);
            }
            rs.close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        Log.d(getClass().getName(), "jsonArray=" + jsonArray);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonArray);
        DatabaseHelper.putTableColumnNames(LABELS, LABELS_CH, jsonObj);
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

    @Override
    public JSONObject queryAttendanceUsers(String courseId, String attendanceFlag) throws SQLException, JSONException {
        String sql = "select user_manage.*,course_student.*" +
                " from user_manage,course_student" +
                " where course_student.course_id=" + courseId +
                " and user_manage.id=course_student.student_id" +
                " group by user_manage.id";
        ResultSet rs = DatabaseHelper.executeQuery(sql);
        JSONArray jsonArray = new JSONArray();
        while (rs.next()) {
            JSONObject jsonObject = new JSONObject();
            String name = rs.getString("name");
            String grade = rs.getString("grade");
            String className = rs.getString("class");
            String faculty = rs.getString("faculty");
            String studentNum = rs.getString("student_num");
            jsonObject.put("name", name == null ? "未知" : name);
            jsonObject.put("grade", grade == null ? "未知" : grade);
            jsonObject.put("class", className == null ? "未知" : className);
            jsonObject.put("faculty", faculty == null ? "未知" : faculty);
            jsonObject.put("student_num", studentNum == null ? "未知" : studentNum);
            String attendance = rs.getString("attendance");
            boolean hasAttendanced = false;
            if (attendance != null) {
                for (String flag : attendance.split(",")) {
                    if (attendanceFlag.equals(flag)) {
                        hasAttendanced = true;
                        break;
                    }
                }
            }
            jsonObject.put("has_attendanced", hasAttendanced);
            jsonArray.put(jsonObject);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aaData", jsonArray);
        jsonObject.put("result_msg", "ok");
        jsonObject.put("result_code", 0);
        return jsonObject;
    }

    @Override
    public JSONObject updateAttendanceInfo(AttendanceBean attendance) throws JSONException {
        String sql = "UPDATE " + TABLE_NAME + " set publisher_name=?,attendance_title=?,attendance_requirement=?,deadline=? where id=" + attendance.getId();
        boolean isSuccess = DatabaseHelper.executeUpdate(
                sql, attendance.getPublisherName(),
                attendance.getAttendanceTitle(), attendance.getAttendanceRequirement(),
                attendance.getDeadline());
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", isSuccess ? "ok" : "更新作业信息失败！");
        jsonObj.put("result_code", isSuccess ? 0 : 10);
        return jsonObj;
    }

    @Override
    public JSONObject publishAttendance(AttendanceBean bean) throws JSONException {
        String sql = "insert into " + TABLE_NAME + "(publisher_id,publisher_name,attendance_title,attendance_requirement,publish_time,deadline,course_id,attendance_flag) values("
                + bean.getPublisherId() + ",'"
                + bean.getPublisherName() + "','"
                + bean.getAttendanceTitle() + "','"
                + bean.getAttendanceRequirement() + "','"
                + bean.getPublishTime() + "','"
                + bean.getDeadline() + "',"
                + bean.getCourseId() + ","
                + bean.getAttendanceFlag() + ")";
        Log.d(getClass().getName(), "publishAttendance sql=" + sql);
        boolean isSuccess = DatabaseHelper.executeUpdate(sql);
        if (isSuccess) {
            sql = "UPDATE course_manage set attendance_count=attendance_count+1 where id=" + bean.getCourseId();
            isSuccess = DatabaseHelper.executeUpdate(sql);
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", isSuccess ? "ok" : "发布失败！");
        jsonObj.put("result_code", isSuccess ? 0 : 10);
        return jsonObj;
    }

    @Override
    public JSONObject deleteAttendance(String[] ids) throws JSONException, SQLException {
        boolean isSuccess = false;
        for (String id : ids) {
            String sql = "select * from " + TABLE_NAME + " where id=" + id;
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            String courseId;
            if (rs.next()) {
                courseId = rs.getString("course_id");
                int attendanceFlag = rs.getInt("attendance_flag");
                Log.d(getClass().getName(), "course_id=" + courseId);
                sql = "delete from " + TABLE_NAME + " where id=" + id;
                isSuccess = DatabaseHelper.executeUpdate(sql);
                if (isSuccess) {
                    sql = "UPDATE course_manage set attendance_count=attendance_count-1 where id=" + courseId;
                    DatabaseHelper.executeUpdate(sql);
                    sql = "update course_student set attendance=replace(attendance,'" + attendanceFlag + "','') where course_id=" + courseId;
                    isSuccess = DatabaseHelper.executeUpdate(sql);
                }
            }
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", isSuccess ? "ok" : "出错了！");
        jsonObj.put("result_code", isSuccess ? 0 : 10);
        return jsonObj;
    }

    @Override
    public boolean checkAttendance(String userId, String attendanceId, String courseId) throws SQLException {
        String sql = "select course_student.attendance,course_manage.attendance_count"
                + " from course_student,course_manage"
                + " where course_student.course_id=" + courseId
                + " and course_student.student_id=" + userId
                + " and course_manage.id=" + courseId + "";
        ResultSet rs = DatabaseHelper.executeQuery(sql);
        if (rs.next()) {
            String attendance = rs.getString("attendance");
            int attendanceCount = rs.getInt("attendance_count");
            if (attendance == null) {
                attendance = String.valueOf(attendanceCount);
            } else {
                attendance += ("," + attendanceCount);
            }
            sql = "UPDATE course_student set attendance='" + attendance + "' where course_id=" + courseId + " and student_id=" + userId;
            Log.d(getClass().getName(), "checkAttendance sql=" + sql);
            return DatabaseHelper.executeUpdate(sql);
        }
        return false;
    }

}
