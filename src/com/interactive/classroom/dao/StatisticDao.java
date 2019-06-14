package com.interactive.classroom.dao;

import com.interactive.classroom.bean.StatisticBean;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Z-P-J
 */
public interface StatisticDao {

    JSONObject statisticRecord(String action, StatisticBean statisticBean) throws JSONException;

}
