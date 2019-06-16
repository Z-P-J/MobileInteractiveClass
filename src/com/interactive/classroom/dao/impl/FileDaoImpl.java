package com.interactive.classroom.dao.impl;

import com.interactive.classroom.bean.FileBean;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.FileDao;
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
    public JSONObject getHomeworkFilesById(String homeworkId, String userId) throws SQLException, JSONException {
        return findFiles("select * from " + TABLE_NAME + " where homework_id=" + homeworkId, userId, false, false);
    }

    @Override
    public JSONObject getFilesById(String id, String userId) throws SQLException, JSONException {
        return findFiles("select * from " + TABLE_NAME + " where id=" + id, null, false, false);
    }

    @Override
    public JSONObject getAllFiles() throws SQLException, JSONException {
        return findFiles("select * from " + TABLE_NAME, null, false, true);
    }

    @Override
    public JSONObject getAllFilesWithComments() throws SQLException, JSONException {
        return findFiles("select * from " + TABLE_NAME, null, true, true);
    }

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
        String resultMsg = "ok";
        int resultCode = 0;
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
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

    @Override
    public JSONObject updateFileInfo(FileBean bean) throws JSONException {
        //String action,String dbName,String id,String title,String content,String creator,String createTime
        String resultMsg = "ok";
        int resultCode = 0;
        List<List<String>> jsonList = new ArrayList<>();
        try {
            //构造sql语句，根据传递过来的查询条件参数
            String sql = "update " + TABLE_NAME + " set file_name='" + bean.getFileName() + "' where id=" + bean.getId();
            DatabaseHelper.executeUpdate(sql);
            sql = "select * from " + TABLE_NAME + " order by create_time desc";
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                list.add(rs.getString("id"));
//                list.add(rs.getString("content"));
                jsonList.add(list);
            }
            rs.close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        //下面开始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        //如果发生错误就设置成"error"等
        jsonObj.put("result_msg", resultMsg);
        //返回0表示正常，不等于0就表示有错误产生，错误代码
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

//    private String createGetRecordSql(FileBean query) {
//        String sql = "";
//        String where = "";
//        if (query.getId() != null && !"null".equals(query.getId())) {
//            where = "where id=" + query.getId();
//        }
//        if (query.getFileName() != null && !"null".equals(query.getFileName()) && !query.getFileName().isEmpty()) {
//            if (!where.isEmpty()) {
//                where = where + " and file_name like '%" + query.getFileName() + "%'";
//            } else {
//                where = "where file_name like '%" + query.getFileName() + "%'";
//            }
//        }
//        if (query.getTimeFrom() != null && query.getTimeTo() != null && !query.getTimeFrom().isEmpty()) {
//            if (!where.isEmpty()) {
//                where = where + " and upload_time between '" + query.getTimeFrom() + "' and '" + query.getTimeTo() + "'";
//            } else {
//                where = "where upload_time between '" + query.getTimeFrom() + "' and '" + query.getTimeTo() + "'";
//            }
//        }
//
//        String orderBy = " order by upload_time desc";
//        System.out.println("query.getSortIndex()=" + query.getSortIndex());
//        if (query.getSortIndex() != null && !"null".equals(query.getSortIndex())) {
//            orderBy = " order by " + query.getOrderBy() + " desc";
//        }
//
//        if (query.getType() != null && "all".equals(query.getType())) {
//            sql = "select * from " + TABLE_NAME + orderBy; //" order by create_time desc"
//        } else {
//            if (query.getId() != null && !"null".equals(query.getId())) {
//                sql = "select * from " + TABLE_NAME + " where id=" + query.getId();
//            } else {
//                if (where.isEmpty()) {
//                    sql = "select * from " + TABLE_NAME + orderBy; //" where uploader_id='" + query.getUserId() + "'"
//                } else {
//                    sql = "select * from " + TABLE_NAME + " " + where + orderBy; //" and uploader_id='" + query.getUserId() + "'"
//                }
//            }
//        }
//        return sql;
//    }

    private JSONObject findFiles(String sql, String userId, boolean getComment, boolean isManager) throws SQLException, JSONException {
        //开始查询数据库
        String resultMsg = "ok";
        int resultCode = 0;
        JSONArray jsonArray = new JSONArray();
        try {
            System.out.println("HomeworkDao sql=" + sql);
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                for (String label : LABELS) {
                    jsonObject.put(label, rs.getString(label));
                }
                if (isManager) {
                    jsonObject.put("isOwner", 1);
                } else {
                    if (userId != null && userId.equals(rs.getString("uploader_id"))) {
                        jsonObject.put("isOwner", 1);
                    } else {
                        jsonObject.put("isOwner", 0);
                    }
                }
                if (getComment) {
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
        DatabaseHelper.putTableColumnNames(LABELS_CH, jsonObj);
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

}
