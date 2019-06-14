package com.interactive.classroom.dao;

import com.iWen.survey.sql.SQLCommand;
import com.interactive.classroom.bean.VoteBean;
import com.interactive.classroom.utils.DBHelper;
import org.json.JSONException;
import org.json.JSONObject;

import javax.sql.RowSet;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Z-P-J
 */
public interface VoteDao {

    String TABLE_NAME = "vote_manage";
    String[] LABELS = {"s_id", "s_name", "s_desc", "s_author", "s_img", "s_createdate", "s_password", "s_isopen", "s_expiredate", "s_isaudited", "s_usehits", "s_type", "status"};

    JSONObject getRecord(VoteBean query) throws SQLException, JSONException;

    JSONObject modifyRecord(VoteBean file) throws JSONException;

    JSONObject getRecordById(String action, String dbName, String id) throws JSONException;

    JSONObject addRecord(VoteBean file) throws JSONException, SQLException;

    JSONObject deleteRecord(String action, String[] ids, String creator, String createTime) throws JSONException, SQLException;

    JSONObject setRecordTop(String action, String type, String userId, String id) throws JSONException, SQLException;

}
