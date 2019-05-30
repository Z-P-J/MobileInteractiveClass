package com.interactive.classroom.vote.dao;

import com.iWen.survey.sql.SQLCommand;
import org.json.JSONException;
import org.json.JSONObject;
import com.interactive.classroom.utils.DBHelper;
import com.interactive.classroom.utils.TimeUtil;
import com.interactive.classroom.vote.bean.VoteBean;

import javax.sql.RowSet;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VoteDao {

    private static final String TABLE_NAME = "vote_manage";
    //    private static final String[] LABELS = {"id", "vote_id", "user_id", "title", "content", "course_id", "publish_date", "deadline", "status"};
    private static final String[] LABELS = {"s_id", "s_name", "s_desc", "s_author", "s_img", "s_createdate", "s_password", "s_isopen", "s_expiredate", "s_isaudited", "s_usehits", "s_type", "status"};

    /*
     * 功能：返回结果集
     */
    public JSONObject getRecord(VoteBean query) throws SQLException, IOException, JSONException {
        //开始查询数据库
        String resultMsg = "ok";
        int resultCode = 0;
        List<List<String>> jsonList = new ArrayList<>();
        try {
            //构造sql语句，根据传递过来的查询条件参数
//            String sql = "";
//            query.setTableName(TABLE_NAME);
//            sql = createGetRecordSql(query);
            String sql = "select * from survey where  s_isOpen ='1' and s_isAudited=1 and s_type=2 and s_expiredate>='"
                    + new java.sql.Date(new java.util.Date().getTime())
                    + "' order by s_createdate desc,s_id desc";
            SQLCommand cmd = new SQLCommand();
            RowSet rs = cmd.queryRowSet(sql);
//            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
            while (rs.next()) {
                List<String> list = new ArrayList<>();
//                int count = 0;
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
//                if (query.getUserId() != null && query.getUserId().equals(rs.getString("user_id"))) {
//                    list.add("1");
//                } else {
//                    list.add("0");
//                }
//                list.add("" + count);
//                count = count + 1;    //做上下记录导航用
                jsonList.add(list);
            }
            rs.close();
//            DBHelper.getInstance().close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        //////////数据库查询完毕，得到了json数组jsonList//////////
        //jsonList.clear();
        //下面开始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        DBHelper.getInstance().putTableColumnNames(LABELS, jsonObj);
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"等
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
        return jsonObj;
    }

    public JSONObject modifyRecord(VoteBean file) throws JSONException {
        //String action,String dbName,String id,String title,String content,String creator,String createTime
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        try {
            //构造sql语句，根据传递过来的查询条件参数
            String sql = "update " + TABLE_NAME + " set title='" + file.getTitle() + "',content='" + file.getContent() + "',limit_time='" + file.getLimitTime() + "',status='" + file.getStatus() + "' where id=" + file.getId();
            DBHelper.getInstance().executeUpdate(sql);
            sql = "select * from " + TABLE_NAME + " order by create_time desc";
            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
            while (rs.next()) {
                List list = new ArrayList();
                list.add(rs.getString("id"));
                list.add(rs.getString("content"));
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
        jsonObj.put("action", file.getAction());
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"等
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
        return jsonObj;
    }

    public JSONObject getRecordById(String action, String dbName, String id) throws JSONException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        try {
            //构造sql语句，根据传递过来的查询条件参数
            String sql = "select * from " + TABLE_NAME + " where id=" + id + " order by create_time desc";
            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
            while (rs.next()) {
                List list = new ArrayList();
                list.add(rs.getString("id"));
                list.add(rs.getString("content"));
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
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"等
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
        return jsonObj;
    }

    public JSONObject addRecord(VoteBean file) throws JSONException, SQLException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        //构造sql语句，根据传递过来的查询条件参数
        String sql = "insert into " + TABLE_NAME + "(vote_id,user_id,title,content,course_id,publish_date,deadline) values("
                + 1 + ",'"
                + file.getUserId() + "','"
                + file.getTitle() + "','"
                + file.getContent() + "',"
                + 1 + ",'"
                + file.getCreateTime() + "','"
                + file.getLimitTime() + "')";
        DBHelper.getInstance().executeUpdate(sql).close();
        //下面开始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", file.getAction());
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"等
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
        return jsonObj;
    }

    public JSONObject deleteRecord(String action, String[] ids, String creator, String createTime) throws JSONException, SQLException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        //构造sql语句，根据传递过来的查询条件参数
        for (String id : ids) {
            String sql = "delete from " + TABLE_NAME + " where id=" + id;
            DBHelper.getInstance().executeUpdate(sql);
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

    public JSONObject setRecordTop(String action, String type, String userId, String id) throws JSONException, SQLException {

        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        //构造sql语句，根据传递过来的查询条件参数
        String sql = "select max(priority) as priority from " + TABLE_NAME + " where user_id='" + userId + "'";
        int priority = 0;
        ResultSet rs = DBHelper.getInstance().executeQuery(sql);
        if (rs.next()) {
            priority = rs.getInt("priority");
        }
        DBHelper.getInstance().executeUpdate("update " + TABLE_NAME + " set priority=" + (priority + 1) + " where id=" + id);
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
    private String createGetRecordSql(VoteBean query) {
        String sql = "";
        String where = "";
        if (query.getId() != null && !query.getId().equals("null")) {
            where = "where id=" + query.getId();
        }
        if (query.getTitle() != null && !query.getTitle().equals("null") && !query.getTitle().isEmpty()) {
            if (!where.isEmpty()) {
                where = where + " and title like '%" + query.getTitle() + "%'";
            } else {
                where = "where title like '%" + query.getTitle() + "%'";
            }
        }
        if (query.getTimeFrom() != null && query.getTimeTo() != null && !query.getTimeFrom().isEmpty()) {
            if (!where.isEmpty()) {
                where = where + " and publish_date between '" + query.getTimeFrom() + "' and '" + query.getTimeTo() + "'";
            } else {
                where = "where publish_date between '" + query.getTimeFrom() + "' and '" + query.getTimeTo() + "'";
            }
        }
        /*----------------------------构造排序条件--------------------------------*/
        String orderBy = "";
        if ((query.getSortIndex() != null) && !query.getSortIndex().equals("null")) {
            if (query.getOrderBy() != null) {
                orderBy = "order by " + getOrderBy(query.getOrderBy());
            }
        }
        System.out.println(getClass().getName() + " orderBy=" + orderBy);
        /*----------------------------构造排序完毕--------------------------------*/
        if (query.getType() != null && query.getType().equals("all") && query.getUserRole().equals("manager")) {
            sql = "select * from " + query.getTableName() + " order by publish_date desc";
        } else {
            if (query.getId() != null && !query.getId().equals("null")) {
                sql = "select * from " + query.getTableName() + " where id=" + query.getId();
            } else {
                if (where.isEmpty()) {
                    sql = "select * from " + query.getTableName() + " where user_id='" + query.getUserId() + "'" + orderBy;
                } else {
                    sql = "select * from " + query.getTableName() + " " + where + " and user_id='" + query.getUserId() + "'" + orderBy;
                }
            }
        }
        System.out.println(getClass().getName() + " sql=" + sql);
        return sql;
    }

    private String getOrderBy(String orderName) {
        if (orderName.equals("by_datetime")) {
            orderName = "publish_date";
        }
        if (orderName.equals("by_title")) {
            orderName = "title";
        }
        if (orderName.equals("by_status")) {
            orderName = "status";
        }
        return orderName;
    }
}
