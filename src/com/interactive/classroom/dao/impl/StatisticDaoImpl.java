package com.interactive.classroom.dao.impl;

import com.interactive.classroom.bean.StatisticBean;
import com.interactive.classroom.dao.StatisticDao;
import com.interactive.classroom.utils.DatabaseHelper;
import com.interactive.classroom.utils.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Z-P-J
 */
public class StatisticDaoImpl implements StatisticDao {

    @Override
    public JSONObject statisticRecord(String sql) throws JSONException {
        //开始查询数据库
        String resultMsg = "ok";
        int resultCode = 0;
        List<List<String>> jsonList = new ArrayList<>();
        int count = 0;
        try {
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                //查询出来有plate_color,time_interval,count,plate_color_name这几项
                List<String> list = new ArrayList<>();
                list.add(count + "");
                list.add(rs.getString("time_interval"));
                list.add(rs.getString("count"));
                jsonList.add(list);
                count = count + 1;
            }
            rs.close();

        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

}
