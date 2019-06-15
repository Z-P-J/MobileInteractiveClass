package com.interactive.classroom.dao;

import com.interactive.classroom.bean.FileBean;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

/**
 * @author Z-P-J
 */
public interface FileDao {

    /**
     * 表名
     */
    String TABLE_NAME = "file_manage";
    /**
     * 数据库中的字段
     */
    String[] LABELS = {"id", "file_id", "uploader_id", "file_name", "file_size", "upload_time", "download_link"};
    /**
     * 数据库中字段中文解释
     */
    String[] LABELS_CH = {"ID", "文件ID", "上传用户", "文件名字", "文件大小", "上传时间", "下载地址"};

    JSONObject getRecord(FileBean query) throws SQLException, JSONException;

    JSONObject modifyRecord(FileBean bean) throws JSONException;

    JSONObject getRecordById(String action, String id) throws JSONException;

    JSONObject addRecord(FileBean bean) throws JSONException, SQLException;

    JSONObject deleteRecord(String action, String[] ids, String creator, String createTime) throws JSONException, SQLException;

}
