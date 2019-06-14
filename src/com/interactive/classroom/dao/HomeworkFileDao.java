package com.interactive.classroom.dao;

import com.interactive.classroom.bean.HomeworkFileBean;
import com.interactive.classroom.utils.DBHelper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface HomeworkFileDao {

    String TABLE_NAME = "homework_file_manage";
    String[] LABELS = {"id", "homework_id", "uploader_id", "file_name", "file_size", "upload_time", "download_link"};
    String[] LABELS_CH = {"ID", "文件ID", "上传用户", "文件名字", "文件大小", "上传时间", "下载地址"};

    static List<List<String>> getComments(String fileId) throws JSONException {
        List<List<String>> jsonList = new ArrayList<>();
        try {
            String sql = "select * from file_comment where file_id=" + fileId;
            System.out.println("TodoDao sql=" + sql);
            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                list.add(rs.getString("id"));
                list.add(rs.getString("file_id"));
                list.add(rs.getString("user_id"));
                list.add(rs.getString("comment_content"));
                list.add(rs.getString("score"));
                list.add(rs.getString("publish_date"));
                jsonList.add(list);
            }
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        return jsonList;
    }

    JSONObject getRecord(HomeworkFileBean query) throws SQLException, IOException, JSONException;

    JSONObject modifyRecord(HomeworkFileBean bean) throws JSONException;

    JSONObject getRecordById(String action, String id) throws JSONException;

    JSONObject addRecord(HomeworkFileBean bean) throws JSONException, SQLException;

    JSONObject deleteRecord(String action, String[] ids, String creator, String createTime, String folder) throws JSONException, SQLException;


}
