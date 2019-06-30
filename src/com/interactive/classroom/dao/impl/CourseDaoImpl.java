package com.interactive.classroom.dao.impl;

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
        jsonObject.put("result_msg", "ok");
        jsonObject.put("result_code", 0);
        return jsonObject;
    }

    @Override
    public boolean increaseAttendanceCount(String courseId, int newCount) {
        String sql = "update " + TABLE_NAME + " set attendance_count=" + newCount+ " where id=" + courseId;
        return DatabaseHelper.executeUpdate(sql);
    }

}
