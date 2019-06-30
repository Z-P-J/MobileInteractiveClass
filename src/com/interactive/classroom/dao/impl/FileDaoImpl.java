package com.interactive.classroom.dao.impl;

import com.interactive.classroom.bean.FileBean;
import com.interactive.classroom.constant.UserType;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.FileDao;
import com.interactive.classroom.dao.filters.FileFilter;
import com.interactive.classroom.utils.DatabaseHelper;
import com.interactive.classroom.utils.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Z-P-J
 */
public class FileDaoImpl implements FileDao {

    @Override
    public JSONObject queryFiles(FileFilter filter) throws SQLException, JSONException {
        //开始查询数据库
        String resultMsg = "ok";
        int resultCode = 0;
        JSONArray jsonArray = new JSONArray();
        try {
            String sql = filter.getQuerySql(TABLE_NAME);
            System.out.println("HomeworkDao sql=" + sql);
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                for (String label : LABELS) {
                    jsonObject.put(label, rs.getString(label));
                }
                if (UserType.MANAGER.equals(filter.getUserType())) {
                    jsonObject.put("isOwner", 1);
                } else {
                    if (filter.getUserId() != null && filter.getUserId().equals(rs.getString("uploader_id"))) {
                        jsonObject.put("isOwner", 1);
                    } else {
                        jsonObject.put("isOwner", 0);
                    }
                }
                if (filter.isGetComments()) {
                    jsonObject.put("comments", DaoFactory.getCommentDao().getFileComments(jsonObject.getString("id")));
                }
                jsonArray.put(jsonObject);
            }
            rs.close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonArray);
        DatabaseHelper.putTableColumnNames(LABELS, LABELS_CH, jsonObj);
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

//    @Override
//    public JSONObject getHomeworkFilesById(String homeworkId, String userId) throws SQLException, JSONException {
//        return queryFiles("select * from " + TABLE_NAME + " where homework_id=" + homeworkId, userId, false, false);
//    }

//    @Override
//    public JSONObject getFilesById(String id, String userId) throws SQLException, JSONException {
//        return queryFiles("select * from " + TABLE_NAME + " where id=" + id, null, false, false);
//    }

//    @Override
//    public JSONObject getAllFiles() throws SQLException, JSONException {
//        return queryFiles("select * from " + TABLE_NAME, null, false, true);
//    }

//    @Override
//    public JSONObject getFilesWithComments(FileFilter filter) throws SQLException, JSONException {
//        String sql = createQuerySql(filter);
//        return queryFiles(sql, null, true, true);
//    }

    @Override
    public JSONObject addFile(FileBean bean) throws JSONException {
        String sql = "insert into " + TABLE_NAME + "(homework_id,uploader_id,uploader_name,file_name,file_size,upload_time,download_link) values(" + bean.getHomeworkId() + ",'" + bean.getUploaderId() + "','" + bean.getUploaderName() + "','" + bean.getFileName() +
                "'," + bean.getFileSize() + ",'" + bean.getUploadTime() + "','" + bean.getDownloadLink() + "')";
        Log.d(getClass().getName(), "sql=" + sql);
        DatabaseHelper.executeUpdate(sql);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", "ok");
        jsonObj.put("result_code", 0);
        return jsonObj;
    }

    @Override
    public JSONObject deleteFileById(String[] ids, String folder) throws JSONException, SQLException {
        for (String id : ids) {
            String sql = "select * from " + TABLE_NAME + " where id=" + id;
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            String fileName = "";
            if (rs.next()) {
                fileName = rs.getString("file_name");
                rs.close();
            }
            sql = "delete from " + TABLE_NAME + " where id=" + id;
            DatabaseHelper.executeUpdate(sql);
            if (!fileName.isEmpty()) {
                File file = new File(folder + fileName);
                file.delete();
            }
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", "ok");
        jsonObj.put("result_code", 0);
        return jsonObj;
    }

    @Override
    public JSONObject updateFileInfo(FileBean bean) throws JSONException {
        String resultMsg = "ok";
        int resultCode = 0;
        List<List<String>> jsonList = new ArrayList<>();
        try {
            String sql = "update " + TABLE_NAME + " set file_name='" + bean.getFileName() + "' where id=" + bean.getId();
            DatabaseHelper.executeUpdate(sql);
            sql = "select * from " + TABLE_NAME + " order by upload_time desc";
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                list.add(rs.getString("id"));
                jsonList.add(list);
            }
            rs.close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

}
