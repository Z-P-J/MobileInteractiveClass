package com.interactive.classroom.dao;

import com.interactive.classroom.bean.HomeworkBean;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Z-P-J
 */
public interface HomeworkDao {

    String TABLE_NAME = "homework_manage";
    String[] LABELS = {"id", "file_id", "uploader_id", "file_name", "file_size", "upload_time", "download_link", "deadline", "homework_requirement", "file_format"};
    String[] LABELS_CH = {"ID", "文件ID", "上传用户", "文件名字", "文件大小", "上传时间"};

    JSONObject getRecord(HomeworkBean query) throws SQLException, IOException, JSONException;

    JSONObject modifyRecord(HomeworkBean bean) throws JSONException;

    JSONObject getRecordById(String action, String id) throws JSONException;

    JSONObject addRecord(HomeworkBean bean) throws JSONException, SQLException;

    JSONObject deleteRecord(String action, String[] ids, String creator, String createTime) throws JSONException, SQLException;

}
