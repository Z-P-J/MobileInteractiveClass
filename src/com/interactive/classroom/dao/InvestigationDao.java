package com.interactive.classroom.dao;

import com.iWen.survey.sql.SQLCommand;
import com.interactive.classroom.bean.InvestigationBean;
import com.interactive.classroom.utils.DBHelper;
import com.interactive.classroom.utils.Log;
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
public interface InvestigationDao {

    String TABLE_NAME = "investigation_manage";

    String[] LABELS = {"s_id", "s_name", "s_desc", "s_author", "s_img", "s_createdate", "s_password", "s_isopen", "s_expiredate", "s_isaudited", "s_usehits", "s_type", "status"};

    JSONObject getRecord(InvestigationBean query, String type) throws SQLException, JSONException;

    JSONObject modifyRecord(InvestigationBean bean) throws JSONException;

    JSONObject getRecordById(String action, String id) throws JSONException;

    JSONObject addRecord(InvestigationBean bean) throws JSONException, SQLException;

    JSONObject deleteRecord(String action, String[] ids, String creator, String createTime) throws JSONException, SQLException;

}
