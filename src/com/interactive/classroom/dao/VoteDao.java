package com.interactive.classroom.dao;

import com.interactive.classroom.bean.VoteBean;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

/**
 * @author Z-P-J
 */
public interface VoteDao {

    /**
     * 表名
     */
    String TABLE_NAME = "vote_manage";
    /**
     * 数据库中的字段
     */
    String[] LABELS = {"s_id", "s_name", "s_desc", "s_author", "s_img", "s_createdate", "s_password", "s_isopen", "s_expiredate", "s_isaudited", "s_usehits", "s_type", "status"};
    /**
     * 数据库中字段中文解释
     */
    String[] LABELS_CH = {"s_id", "s_name", "s_desc", "s_author", "s_img", "s_createdate", "s_password", "s_isopen", "s_expiredate", "s_isaudited", "s_usehits", "s_type", "status"};

    JSONObject getRecord(VoteBean query) throws SQLException, JSONException;

    JSONObject modifyRecord(VoteBean file) throws JSONException;

    JSONObject getRecordById(String action, String dbName, String id) throws JSONException;

    JSONObject addRecord(VoteBean file) throws JSONException, SQLException;

    JSONObject deleteRecord(String action, String[] ids, String creator, String createTime) throws JSONException, SQLException;

    JSONObject setRecordTop(String action, String type, String userId, String id) throws JSONException, SQLException;

}
