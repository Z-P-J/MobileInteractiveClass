package com.interactive.classroom.dao.impl;

import com.iWen.survey.sql.SQLCommand;
import com.interactive.classroom.bean.InvestigationBean;
import com.interactive.classroom.dao.InvestigationDao;
import com.interactive.classroom.utils.DatabaseHelper;
import com.interactive.classroom.utils.Log;
import org.json.JSONException;
import org.json.JSONObject;

import javax.sql.RowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Z-P-J
 */
public class InvestigationDaoImpl implements InvestigationDao {

    @Override
    public JSONObject getRecord(InvestigationBean query, String type) throws SQLException, JSONException {
        //开始查询数据库
        String resultMsg = "ok";
        int resultCode = 0;
        List<List<String>> jsonList = new ArrayList<>();

        String sql = "select * from survey where  s_isOpen ='1' and s_isAudited=1 and s_type='" + type + "' and s_expiredate>='"
                + new java.sql.Date(new java.util.Date().getTime())
                + "' order by s_createdate desc,s_id desc";
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet(sql);
        try {
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                for (String label : LABELS) {
                    if ("status".equals(label)) {
                        break;
                    }
                    list.add(rs.getString(label));
                }
                Date date = rs.getDate("s_expiredate");
                System.out.println("date == null?" + (date == null));
                if (date != null && System.currentTimeMillis() >= date.getTime()) {
                    list.add("已完成");
                } else {
                    list.add("未完成");
                }
                jsonList.add(list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + e.getMessage();
        } finally {
            SQLCommand.close(rs);
        }

//        try {
//            //构造sql语句，根据传递过来的查询条件参数
////            String sql = "";
//            int count = 0;
////            query.setTableName(TABLE_NAME);
////            sql = createGetRecordSql(query);
////            System.out.println("TodoDao sql=" + sql);
////            ResultSet rs = DatabaseHelper.executeQuery(sql);
//
////            while (rs.next()) {
////                List<String> list = new ArrayList<>();
////                list.add(rs.getString("id"));
////                list.add(rs.getString("title"));
////                list.add(rs.getString("content"));
////                list.add(rs.getString("link"));
////                list.add(rs.getString("create_time"));
////                String endTime = rs.getString("end_time");
////                list.add(endTime);
////
////                list.add(rs.getString("user_id"));
////                list.add(rs.getString("creator"));
////
////                Date date = null; //初始化date
////                try {
////                    date = TimeUtil.FORMATTER.parse(endTime); //Mon Jan 14 00:00:00 CST 2013
////                } catch (ParseException e) {
////                    e.printStackTrace();
////                }
////                System.out.println("date == null?" + (date == null));
////                if (date != null && System.currentTimeMillis() >= date.getTime()) {
////                    list.add("已完成");
////                } else {
////                    list.add("未完成");
////                }
////
////                if (query.getUserId() != null && query.getUserId().equals(rs.getString("user_id"))) {
////                    list.add("1");
////                } else {
////                    list.add("0");
////                }
////                list.add("" + count);
//////                list.add(rs.getString("type"));
////                //做上下记录导航用
////                count = count + 1;
////                jsonList.add(list);
////            }
////            rs.close();
////
//        } catch (SQLException sqlexception) {
//            sqlexception.printStackTrace();
//            resultCode = 10;
//            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
//        }
        //////////数据库查询完毕，得到了json数组jsonList//////////
        //jsonList.clear();
        //下面开始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        DatabaseHelper.putTableColumnNames(LABELS, jsonObj);
        //如果发生错误就设置成"error"等
        jsonObj.put("result_msg", resultMsg);
        //返回0表示正常，不等于0就表示有错误产生，错误代码
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

    @Override
    public JSONObject modifyRecord(InvestigationBean bean) throws JSONException {
        //String action,String dbName,String id,String title,String content,String creator,String createTime
        String resultMsg = "ok";
        int resultCode = 0;
        List<List<String>> jsonList = new ArrayList<>();
        try {
            //构造sql语句，根据传递过来的查询条件参数
            String sql = "update " + TABLE_NAME + " set title='" + bean.getTitle() + "',content='" + bean.getContent() + "',end_time='" + bean.getEndTime() + "',link='" + bean.getLink() + "' where id=" + bean.getId();
            DatabaseHelper.executeUpdate(sql);
            sql = "select * from " + TABLE_NAME + " order by create_time desc";
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                list.add(rs.getString("id"));
                list.add(rs.getString("content"));
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
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                list.add(rs.getString("id"));
                list.add(rs.getString("content"));
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
        jsonObj.put("action", action);
        //如果发生错误就设置成"error"等
        jsonObj.put("result_msg", resultMsg);
        //返回0表示正常，不等于0就表示有错误产生，错误代码
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

    @Override
    public JSONObject addRecord(InvestigationBean bean) throws JSONException, SQLException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
//		ylx_db query_db = new ylx_db(bean.getDbName());
        //构造sql语句，根据传递过来的查询条件参数
        String sql = "insert into " + TABLE_NAME + "(parent_id,title,content,user_id,creator,create_time,link,end_time) values('" + bean.getParentId() + "','" + bean.getTitle() + "','" + bean.getContent() +
                "','" + bean.getUserId() + "','" + bean.getCreator() + "','" + bean.getCreateTime() + "','" + bean.getLink() + "','" + bean.getEndTime() + "')";
        Log.d(getClass().getName(), "sql=" + sql);

        DatabaseHelper.executeUpdate(sql);
        //下面开始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", bean.getAction());
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"等
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
        return jsonObj;
    }

    @Override
    public JSONObject deleteRecord(String action, String[] ids, String creator, String createTime) throws JSONException, SQLException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
//		ylx_db query_db = new ylx_db(dbName);
        //构造sql语句，根据传递过来的查询条件参数
        for (String id : ids) {
            String sql = "delete from " + TABLE_NAME + " where id=" + id;
            DatabaseHelper.executeUpdate(sql);
        }

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
    private String createGetRecordSql(InvestigationBean query) {
        String sql = "";
        String where = "";
        if (query.getId() != null && !"null".equals(query.getId())) {
            where = "where id=" + query.getId();
        }
        if (query.getTitle() != null && !"null".equals(query.getTitle()) && !query.getTitle().isEmpty()) {
            if (!where.isEmpty()) {
                where = where + " and title like '%" + query.getTitle() + "%'";
            } else {
                where = "where title like '%" + query.getTitle() + "%'";
            }
        }
        if (query.getTimeFrom() != null && query.getTimeTo() != null && !query.getTimeFrom().isEmpty()) {
            if (!where.isEmpty()) {
                where = where + " and create_time between '" + query.getTimeFrom() + "' and '" + query.getTimeTo() + "'";
            } else {
                where = "where create_time between '" + query.getTimeFrom() + "' and '" + query.getTimeTo() + "'";
            }
        }

        String orderBy = " order by create_time desc";
        System.out.println("query.getSortIndex()=" + query.getSortIndex());
        if (query.getSortIndex() != null && !"null".equals(query.getSortIndex())) {
            orderBy = " order by " + query.getOrderBy() + " desc";
        }

        if (query.getType() != null && "all".equals(query.getType()) && "manager".equals(query.getUserRole())) {
            sql = "select * from " + query.getTableName() + orderBy; //" order by create_time desc"
        } else {
            if (query.getId() != null && !"null".equals(query.getId())) {
                sql = "select * from " + query.getTableName() + " where id=" + query.getId();
            } else {
                if (where.isEmpty()) {
                    sql = "select * from " + query.getTableName() + " where user_id='" + query.getUserId() + "'" + orderBy;
                } else {
                    sql = "select * from " + query.getTableName() + " " + where + " and user_id='" + query.getUserId() + "'" + orderBy;
                }
            }
        }
        return sql;
    }

}
