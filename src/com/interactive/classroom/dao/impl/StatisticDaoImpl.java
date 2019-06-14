package com.interactive.classroom.dao.impl;

import com.interactive.classroom.bean.StatisticBean;
import com.interactive.classroom.dao.StatisticDao;
import com.interactive.classroom.utils.DBHelper;
import com.interactive.classroom.utils.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Z-P-J
 */
public class StatisticDaoImpl implements StatisticDao {

    @Override
    public JSONObject statisticRecord(String action, StatisticBean statisticBean) throws JSONException {
        //开始查询数据库
        String resultMsg = "ok";
        int resultCode = 0;
        List<List<String>> jsonList = new ArrayList<>();
        int count = 0;
        try {
            //构造sql语句，根据传递过来的查询条件参数
            String sql = getStatisticRecordSql(statisticBean);
            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
            while (rs.next()) {
                //查询出来有plate_color,time_interval,count,plate_color_name这几项
                List<String> list = new ArrayList<>();
                list.add(count + "");
                list.add(rs.getString("time_interval"));
                list.add(rs.getString("count"));
                list.add("无区分");
                list.add("无区分");
                //showDebug(rs.getString("plate_color")+","+rs.getString("address_id")+","+rs.getString("time_interval")+","+rs.getString("count"));
                jsonList.add(list);
                count = count + 1;
            }
            rs.close();
            DBHelper.getInstance().close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        //////////数据库查询完毕，得到了json数组jsonList//////////
        //jsonList.clear();
        //下面开始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", action);
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"等
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
        return jsonObj;
    }

    private String getStatisticRecordSql(StatisticBean statisticBean) {
        String sql = "";
        String timeInterval = "";
        if ("hour".equals(statisticBean.getTimeInterval())) {
            timeInterval = "%Y-%m-%d %h";
        }
        if ("day".equals(statisticBean.getTimeInterval())) {
            timeInterval = "%Y-%m-%d";
        }
        if ("month".equals(statisticBean.getTimeInterval())) {
            timeInterval = "%Y-%m";
        }
        String createTime = "create_time";
        String userId = "user_id";
        String tableName = statisticBean.getTableName();
        if ("file_manage".equals(tableName)) {
            createTime = "upload_time";
            userId = "uploader_id";
        }
        if ("user_manage".equals(tableName)) {
            createTime = "register_date";
            userId = "user_id";
        }
        if ("vote_file".equals(tableName)) {
            createTime = "publish_date";
            userId = "user_id";
        }

//        if ("investigation_manage".equals(statisticBean.getTableName())) {
//            createTime = "register_date";
//            userId = "user_id";
//        }

        sql = "select date_format("+ createTime +",\"" + timeInterval + "\") as time_interval,count(*) as count from " + tableName + " a";
        sql = sql + " where " + createTime + " between \"" + statisticBean.getTimeFrom() + "\" and \"" + statisticBean.getTimeTo() + "\"";
//        if (!"user_manage".equals(tableName)) {
//            sql = sql + " and " + userId + "='" + statisticBean.getUserId() + "'";
//        }
        sql = sql + " group by time_interval order by time_interval";
        Log.d(getClass().getName(), "sql=" + sql);
        return sql;
    }

}