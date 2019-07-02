package com.interactive.classroom.dao;

import com.interactive.classroom.bean.StatisticBean;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Z-P-J
 */
public interface StatisticDao {

    /**
     * 统计信息
     * @param sql sql语句
     * @return JSONObject
     * @throws JSONException JSONException
     */
    JSONObject statisticRecord(String sql) throws JSONException;

}
