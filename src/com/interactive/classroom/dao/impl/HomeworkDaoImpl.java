package com.interactive.classroom.dao.impl;

import com.interactive.classroom.bean.HomeworkBean;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.HomeworkDao;
import com.interactive.classroom.dao.filters.FileFilter;
import com.interactive.classroom.dao.filters.FilterFactory;
import com.interactive.classroom.dao.filters.HomeworkFilter;
import com.interactive.classroom.utils.DatabaseHelper;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.TextUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Z-P-J
 */
public class HomeworkDaoImpl implements HomeworkDao {

//    @Override
//    public JSONObject getAllHomeworks(String order) throws SQLException, JSONException {
//        String sql = "select * from " + TABLE_NAME + wrapOrder(order);
//        return findHomeworks(sql);
//    }

//    @Override
//    public JSONObject getHomeworksByCourse(String courseId, String order) throws SQLException, JSONException {
//        String sql = "select * from " + TABLE_NAME + "where course_id=" + courseId + wrapOrder(order);
//        return findHomeworks(sql);
//    }

    @Override
    public JSONObject queryHomeworks(HomeworkFilter filter) throws SQLException, JSONException {
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
                if (filter.getUserId() != null && filter.getUserId().equals(rs.getString("publisher_id"))) {
                    jsonObj.put("isOwner", 1);
                } else {
                    jsonObj.put("isOwner", 0);
                }
                if (!TextUtil.isEmpty(filter.getHomeworkId())) {
                    FileFilter fileFilter = FilterFactory.getFileFilter()
                            .setUserId(filter.getUserId())
                            .setUserType(filter.getUserType())
                            .setHomeworkId(filter.getHomeworkId())
                            .setOrder(filter.getOrder());
                    JSONObject jsonObject = DaoFactory.getFileDao().queryFiles(fileFilter);
                    jsonObj.put("file_list", jsonObject.get("aaData"));
                }
                jsonArray.put(jsonObj);
            }
            rs.close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonArray);
        DatabaseHelper.putTableColumnNames(LABELS, LABELS_CH, jsonObj);
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

    @Override
    public JSONObject updateHomeworkInfo(HomeworkBean homework) throws JSONException {
        String sql = "UPDATE " + TABLE_NAME + " set publisher_name=?,homework_title=?,homework_requirement=?,deadline=? where id=" + homework.getId();
        boolean isSuccess = DatabaseHelper.executeUpdate(
                sql, homework.getPublisherName(),
                homework.getHomeworkTitle(), homework.getHomeworkRequirement(),
                homework.getDeadline());
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", isSuccess ? "ok" : "更新作业信息失败！");
        jsonObj.put("result_code", isSuccess ? 0 : 10);
        return jsonObj;
    }

    @Override
    public JSONObject publishHomework(HomeworkBean bean) throws JSONException {
        String resultMsg = "ok";
        int resultCode = 0;
        String sql = "insert into " + TABLE_NAME + "(publisher_id,publisher_name,homework_title,homework_requirement,publish_time,deadline,file_name_format) values("
                + bean.getPublisherId() + ",'"
                + bean.getPublisherName() + "','"
                + bean.getHomeworkTitle() + "','"
                + bean.getHomeworkRequirement() + "','"
                + bean.getPublishTime() + "','"
                + bean.getDeadline() + "','"
                + bean.getFileNameFormat() + "')";
        Log.d(getClass().getName(), "publishAttendance sql=" + sql);
        DatabaseHelper.executeUpdate(sql);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

    @Override
    public JSONObject deleteHomework(String[] ids) throws JSONException {
        String resultMsg = "ok";
        int resultCode = 0;
        for (String id : ids) {
            String sql = "delete from " + TABLE_NAME + " where id=" + id;
            DatabaseHelper.executeUpdate(sql);
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

}
