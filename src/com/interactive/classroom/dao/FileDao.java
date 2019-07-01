package com.interactive.classroom.dao;

import com.interactive.classroom.bean.FileBean;
import com.interactive.classroom.dao.filters.FileFilter;
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
    String[] LABELS = {"id", "homework_id", "uploader_id", "uploader_name", "file_name", "file_size", "upload_time", "download_link"};
    /**
     * 数据库中字段中文解释
     */
    String[] LABELS_CH = {"ID", "作业ID", "上传者ID", "上传者", "文件名", "文件大小", "上传时间", "下载地址"};

    /**
     *
     * @param filter
     * @return
     * @throws SQLException
     * @throws JSONException
     */
    JSONObject queryFiles(FileFilter filter) throws SQLException, JSONException;

//    /**
//     * 根据作业id获取文件信息
//     * @param homeworkId 作业id
//     * @param userId 用户id
//     * @return JSONObject
//     * @throws SQLException SQLException
//     * @throws JSONException JSONException
//     */
//    JSONObject getHomeworkFilesById(String homeworkId, String userId) throws SQLException, JSONException;

//    JSONObject getFilesById(String id, String userId) throws SQLException, JSONException;

//    /**
//     * 获取数据库所有文件信息(管理员)
//     * @return JSONObject
//     * @throws SQLException SQLException
//     * @throws JSONException JSONException
//     */
//    JSONObject getAllFiles() throws SQLException, JSONException;

//    /**
//     *
//     * @param filter FileFilter
//     * @return
//     * @throws SQLException
//     * @throws JSONException
//     */
//    JSONObject getFilesWithComments(FileFilter filter) throws SQLException, JSONException;

    /**
     * 添加文件信息到数据库
     * @param bean FileBean
     * @return JSONObject
     * @throws JSONException JSONException
     */
    JSONObject addFile(FileBean bean) throws JSONException;

    /**
     * 根据id删除文件
     * @param ids 多个id
     * @param folder 文件保存路径
     * @return JSONObject
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    JSONObject deleteFileById(String[] ids, String folder) throws JSONException, SQLException;

    /**
     * 更新文件信息
     * @param bean the FileBean
     * @return org.json.JSONObject
     * @throws JSONException JSONException
     */
    JSONObject updateFileInfo(FileBean bean) throws JSONException;

}
