package com.interactive.classroom.dao;

import com.interactive.classroom.bean.StatisticBean;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Z-P-J
 */
public interface StatisticDao {

    /**
     *
     * @param action
     * @param statisticBean
     * @return
     * @throws JSONException
     */
    JSONObject statisticRecord(String action, StatisticBean statisticBean) throws JSONException;

    /**
     *
     * @param sql
     * @return
     * @throws JSONException
     */
    JSONObject statisticRecord(String sql) throws JSONException;

}
