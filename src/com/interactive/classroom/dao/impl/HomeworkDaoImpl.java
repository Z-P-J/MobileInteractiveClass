package com.interactive.classroom.dao.impl;

import com.interactive.classroom.bean.HomeworkBean;
import com.interactive.classroom.dao.HomeworkDao;
import com.interactive.classroom.utils.DatabaseHelper;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.TextUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Z-P-J
 */
public class HomeworkDaoImpl implements HomeworkDao {

    @Override
    public JSONObject getAllHomeworks(String order) throws SQLException, JSONException {
        String sql = "select * from " + TABLE_NAME + wrapOrder(order);
        return findHomeworks(sql);
    }

    @Override
    public JSONObject getHomeworksByCourse(String courseId, String order) throws SQLException, JSONException {
        String sql = "select * from " + TABLE_NAME + "where course_id=" + courseId + wrapOrder(order);
        return findHomeworks(sql);
    }

    @Override
    public JSONObject queryHomeworks(String courseId, String keyword, String publishTimeFrom, String publishTimeTo, String deadlineFrom, String deadlineTo, String order) throws SQLException, JSONException {
        return findHomeworks(createQuerySql(courseId, keyword, publishTimeFrom, publishTimeTo, deadlineFrom, deadlineTo, order));
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
        Log.d(getClass().getName(), "publishHomework sql=" + sql);
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

    private String createQuerySql(String courseId, String keyword, String publishTimeFrom, String publishTimeTo, String deadlineFrom, String deadlineTo, String order) {
        String sql = "select * from " + TABLE_NAME;
        if (!TextUtil.isEmpty(courseId)) {
            sql += " where course_id=" + courseId;
        }
        if (!TextUtil.isEmpty(keyword)) {
            if (sql.contains("where")) {
                sql += " and ";
            } else {
                sql += " where ";
            }
            sql += "(homework_title like '%" + keyword + "%' or homework_requirement like '%" + keyword + "%')";
        }
        if (!TextUtil.isEmpty(publishTimeFrom) && !TextUtil.isEmpty(publishTimeTo)) {
            if (sql.contains("where")) {
                sql += " and ";
            } else {
                sql += " where ";
            }
            sql += "publish_time between '" + publishTimeFrom + "' and '" + publishTimeTo + "'";
        }
        if (!TextUtil.isEmpty(deadlineFrom) && !TextUtil.isEmpty(deadlineTo)) {
            if (sql.contains("where")) {
                sql += " and ";
            } else {
                sql += " where ";
            }
            sql += "deadline between '" + deadlineFrom + "' and '" + deadlineTo + "'";
        }
        sql += wrapOrder(order);
        return sql;
    }

    private JSONObject findHomeworks(String sql) throws JSONException, SQLException {
        Log.d(getClass().getName(), "findHomeworks sql=" + sql);
        String resultMsg = "ok";
        int resultCode = 0;
        List<List<String>> jsonList = new ArrayList<>();
        try {
            int count = 0;
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                for (String label : LABELS) {
                    list.add(rs.getString(label));
                }
                list.add("1");
                list.add("" + count);
                count = count + 1;
                jsonList.add(list);
            }
            rs.close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        DatabaseHelper.putTableColumnNames(LABELS_CH, jsonObj);
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

    private String wrapOrder(String order) {
        String orderBy;
        if (TextUtil.isEmpty(order)) {
            orderBy = " order by publish_time desc";
        } else {
            orderBy = " order by " + order + " desc";
        }
        return orderBy;
    }

}
