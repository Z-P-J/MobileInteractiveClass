package com.interactive.classroom.dao;

import com.interactive.classroom.bean.InvestigationBean;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

/**
 * @author Z-P-J
 */
public interface InvestigationDao {

    /**
     * 表名
     */
    String TABLE_NAME = "investigation_manage";
    /**
     * 数据库中的字段
     */
    String[] LABELS = {"s_id", "s_name", "s_desc", "s_author", "s_img", "s_createdate", "s_password", "s_isopen", "s_expiredate", "s_isaudited", "s_usehits", "s_type", "status"};
    /**
     * 数据库中字段中文解释
     */
    String[] LABELS_CH = {"s_id", "s_name", "s_desc", "s_author", "s_img", "s_createdate", "s_password", "s_isopen", "s_expiredate", "s_isaudited", "s_usehits", "s_type", "status"};

    JSONObject getRecord(InvestigationBean query, String type) throws SQLException, JSONException;

    JSONObject modifyRecord(InvestigationBean bean) throws JSONException;

    JSONObject getRecordById(String action, String id) throws JSONException;

    JSONObject addRecord(InvestigationBean bean) throws JSONException, SQLException;

    JSONObject deleteRecord(String action, String[] ids, String creator, String createTime) throws JSONException, SQLException;

}
