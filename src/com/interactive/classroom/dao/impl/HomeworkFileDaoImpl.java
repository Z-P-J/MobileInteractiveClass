package com.interactive.classroom.dao.impl;

import com.interactive.classroom.bean.HomeworkFileBean;
import com.interactive.classroom.dao.HomeworkFileDao;
import com.interactive.classroom.utils.DBHelper;
import com.interactive.classroom.utils.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeworkFileDaoImpl implements HomeworkFileDao {

    @Override
    public JSONObject getRecord(HomeworkFileBean query) throws SQLException, JSONException {
        //开始查询数据库
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
//        List<List<List<String>>> commentList = new ArrayList<>();
        try {
            //构造sql语句，根据传递过来的查询条件参数
            String sql = "";
            int count = 0;
            query.setTableName(TABLE_NAME);
            sql = createGetRecordSql(query);
//            sql = "select * from " + TABLE_NAME + " where homework_id=" + query.getHomeworkId();
            System.out.println("HomeworkDao sql=" + sql);
            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                for (String label : LABELS) {
                    list.add(rs.getString(label));
                }

                if (query.getUserId() != null && query.getUserId().equals(rs.getString("uploader_id"))) {
                    list.add("1");
                } else {
                    list.add("0");
                }



                list.add("" + count);
//                list.add(rs.getString("type"));
                //做上下记录导航用
                count = count + 1;
                jsonList.add(list);
            }
            rs.close();
            DBHelper.getInstance().close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        //////////数据库查询完毕，得到了json数组jsonList//////////
        //jsonList.clear();
        //下面开始构建返回的json
        JSONObject jsonObj = new JSONObject();

        for (Object file : jsonList) {
            List list = (List) file;
            list.add(HomeworkFileDao.getComments((String) list.get(1)));
        }
        jsonObj.put("aaData", jsonList);
        DBHelper.getInstance().putTableColumnNames(LABELS_CH, jsonObj);
        jsonObj.put("result_msg", resultMsg);
        //返回0表示正常，不等于0就表示有错误产生，错误代码
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

    @Override
    public JSONObject modifyRecord(HomeworkFileBean bean) throws JSONException {
        //String action,String dbName,String id,String title,String content,String creator,String createTime
        String resultMsg = "ok";
        int resultCode = 0;
        List<List<String>> jsonList = new ArrayList<>();
        try {
            //构造sql语句，根据传递过来的查询条件参数
            String sql = "update " + TABLE_NAME + " set file_name='" + bean.getFileName() + "' where id=" + bean.getId();
            DBHelper.getInstance().executeUpdate(sql);
            sql = "select * from " + TABLE_NAME + " order by create_time desc";
            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                list.add(rs.getString("id"));
//                list.add(rs.getString("content"));
                jsonList.add(list);
            }
            rs.close();
            DBHelper.getInstance().close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        //下面开始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", bean.getAction());
        //如果发生错误就设置成"error"等
        jsonObj.put("result_msg", resultMsg);
        //返回0表示正常，不等于0就表示有错误产生，错误代码
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

    @Override
    public JSONObject getRecordById(String action, String id) throws JSONException {
        String resultMsg = "ok";
        int resultCode = 0;
        List<List<String>> jsonList = new ArrayList<>();
        try {
//			ylx_db query_db = new ylx_db(dbName);
            //构造sql语句，根据传递过来的查询条件参数
            String sql = "select * from " + TABLE_NAME + " where id=" + id + " order by create_time desc";
            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                list.add(rs.getString("id"));
//                list.add(rs.getString("content"));
                jsonList.add(list);
            }
            rs.close();
            DBHelper.getInstance().close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        //下面开始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", action);
        //如果发生错误就设置成"error"等
        jsonObj.put("result_msg", resultMsg);
        //返回0表示正常，不等于0就表示有错误产生，错误代码
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

    @Override
    public JSONObject addRecord(HomeworkFileBean bean) throws JSONException, SQLException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
//		ylx_db query_db = new ylx_db(bean.getDbName());
        //构造sql语句，根据传递过来的查询条件参数
        String sql = "insert into " + TABLE_NAME + "(homework_id,uploader_id,uploader_name,file_name,file_size,upload_time,download_link) values(" + bean.getHomeworkId() + ",'" + bean.getUploaderId() + "','" + bean.getUploaderName() + "','" + bean.getFileName() +
                "'," + bean.getFileSize() + ",'" + bean.getUploadTime() + "','" + bean.getDownloadLink() + "')";
        Log.d(getClass().getName(), "sql=" + sql);

        DBHelper.getInstance().executeUpdate(sql).close();
        //下面开始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", bean.getAction());
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"等
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
        return jsonObj;
    }

    @Override
    public JSONObject deleteRecord(String action, String[] ids, String creator, String createTime, String folder) throws JSONException, SQLException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
//		ylx_db query_db = new ylx_db(dbName);
        //构造sql语句，根据传递过来的查询条件参数
        for (String id : ids) {
            String sql = "select * from " + TABLE_NAME + " where id=" + id;
            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
            String fileName = "";
            while (rs.next()) {
                fileName = rs.getString("file_name");
                rs.close();
                break;
            }
            sql = "delete from " + TABLE_NAME + " where id=" + id;
            DBHelper dbHelper = DBHelper.getInstance().executeUpdate(sql);
            if (dbHelper != null && !fileName.isEmpty()) {
                File file = new File(folder + fileName);
                file.delete();
            }

        }
        DBHelper.getInstance().close();
        //下面开始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", action);
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"等
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
        return jsonObj;
    }

    /*
     * 功能：构造历史记录查询的sql语句,type=all查询所有，type=id查询某个记录，余下按照条件设置查询
     */
    private String createGetRecordSql(HomeworkFileBean query) {
        String sql = "";
        String where = "";


        if (query.getId() != null && !"null".equals(query.getId())) {
            where = "where id=" + query.getId();
        }
        if (query.getFileName() != null && !"null".equals(query.getFileName()) && !query.getFileName().isEmpty()) {
            if (!where.isEmpty()) {
                where = where + " and file_name like '%" + query.getFileName() + "%'";
            } else {
                where = "where file_name like '%" + query.getFileName() + "%'";
            }
        }
        if (query.getTimeFrom() != null && query.getTimeTo() != null && !query.getTimeFrom().isEmpty()) {
            if (!where.isEmpty()) {
                where = where + " and upload_time between '" + query.getTimeFrom() + "' and '" + query.getTimeTo() + "'";
            } else {
                where = "where upload_time between '" + query.getTimeFrom() + "' and '" + query.getTimeTo() + "'";
            }
        }

        String orderBy = " order by upload_time desc";
        System.out.println("query.getSortIndex()=" + query.getSortIndex());
        if (query.getSortIndex() != null && !"null".equals(query.getSortIndex())) {
            orderBy = " order by " + query.getOrderBy() + " desc";
        }

        if (query.getType() != null && "all".equals(query.getType())) {
            sql = "select * from " + query.getTableName() + orderBy; //" order by create_time desc"
        } else {
            if (query.getHomeworkId() != null && !"null".equals(query.getHomeworkId())) {
                sql = "select * from " + query.getTableName() + " where homework_id=" + query.getHomeworkId() + orderBy;
            } else {
                sql = "select * from " + query.getTableName() + orderBy;
            }
        }
        return sql;
    }

}