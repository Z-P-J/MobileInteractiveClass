package com.interactive.classroom.user.dao;

import com.interactive.classroom.user.bean.UserBean;
import org.json.JSONException;
import org.json.JSONObject;
import com.interactive.classroom.utils.DBHelper;
import com.interactive.classroom.utils.TimeUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final String TABLE_NAME = "user_manage";

    private static final String[] LABELS = {"id", "user_name", "user_id", "name", "sex", "email", "phone", "user_type", "wechat", "grade", "class", "student_num", "faculty", "register_date"};
    private static final String[] LABELS_CH = {"ID", "用户名", "用户id", "用户名", "性别", "Email", "手机", "用户类别", "微信", "年级", "班级", "学号", "学院", "注册时间"};

    /*
     * 功能：返回结果集
     */
    public JSONObject getRecord(UserBean query) throws SQLException, IOException, JSONException {
        //开始查询数据库
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        try {
            //构造sql语句，根据传递过来的查询条件参数
            String sql = "select * from " + TABLE_NAME;
            int count = 0;
            query.setTableName(TABLE_NAME);
            sql = createGetRecordSql(query);
            System.out.println("------------------------[getRecord]" + sql);
            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
            while (rs.next()) {
                List list = new ArrayList();
                for (String label : LABELS) {
                    list.add(rs.getString(label));
                }
                if (query.getUserId() != null && query.getUserId().equals(rs.getString("user_id"))) {
                    list.add("1");
                } else {
                    list.add("0");
                }
                list.add(count);
                count = count + 1;    //做上下记录导航用
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
        //下面�?始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        DBHelper.getInstance().putTableColumnNames(LABELS_CH, jsonObj);
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"�?
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代�?
        return jsonObj;
    }

    public UserBean getUserByUserName(String username) {
        String sql = "select * from " + TABLE_NAME + " where user_name='" + username + "'";
        ResultSet rs = DBHelper.getInstance().executeQuery(sql);
        try {
            if (rs.next()) {
                UserBean user = new UserBean();
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setSex(rs.getString("sex"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setUserRole(rs.getString("user_type"));
                user.setWechat(rs.getString("wechat"));
                user.setGrade(rs.getString("grade"));
                user.setClassStr(rs.getString("class"));
                user.setFaculty(rs.getString("faculty"));
                user.setStudentNum(rs.getString("student_num"));
                user.setCreateTime(rs.getString("register_date"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject modifyRecord(UserBean info) throws JSONException {
        //String action,String dbName,String id,String title,String content,String creator,String createTime
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        try {
            //构�?�sql语句，根据传递过来的查询条件参数
            String sql = "update " + TABLE_NAME
                    + " set user_name='" + info.getUserName()
//                    + "',name='" + info.getName()
                    + "',sex='" + info.getSex()
                    + "',email='" + info.getEmail()
                    + "',phone='" + info.getPhone()
                    + "',wechat='" + info.getWechat()
                    + "',grade='" + info.getGrade()
                    + "',student_num='" + info.getStudentNum()
                    + "',faculty='" + info.getFaculty()
                    + "' where name='" + info.getName() + "'";
            DBHelper.getInstance().executeUpdate(sql);
            sql = "select * from " + TABLE_NAME + " order by register_date desc";
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
        //下面�?始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", info.getAction());
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"�?
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代�?
        return jsonObj;
    }

    public JSONObject getRecordById(String action, String dbName, String id) throws JSONException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        try {
            //构�?�sql语句，根据传递过来的查询条件参数
            String sql = "select * from " + TABLE_NAME + " where id=" + id + " order by register_date desc";
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
        //下面�?始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", action);
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"�?
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代�?
        return jsonObj;
    }

    public JSONObject addRecord(UserBean info) throws JSONException, SQLException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        //构�?�sql语句，根据传递过来的查询条件参数
        String sql = "insert into " + TABLE_NAME + "(user_name,password,name,sex,email,wechat,grade,class,student_num,faculty,register_date) values('"
                + info.getUserName() + "','"
                + info.getPassword() + "','"
                + info.getName() + "','"
                + info.getSex() + "','"
                + info.getEmail() + "','"
                + info.getWechat() + "','"
                + info.getGrade() + "','"
                + info.getClassStr() + "','"
                + info.getStudentNum() + "','"
                + info.getFaculty() + "','"
                + TimeUtil.currentDate() + "')";
        DBHelper.getInstance().executeUpdate(sql).close();
        //下面�?始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", info.getAction());
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"�?
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代�?
        return jsonObj;
    }

    public JSONObject deleteRecord(String action, String name, String creator, String createTime) throws JSONException, SQLException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        //构�?�sql语句，根据传递过来的查询条件参数
        String sql = "delete from " + TABLE_NAME + " where id=" + name;
        DBHelper.getInstance().executeUpdate(sql).close();
        //下面�?始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", action);
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"�?
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代�?
        return jsonObj;
    }

    public JSONObject deleteRecord(String action, String dbName, String[] ids, String creator, String createTime) throws JSONException, SQLException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        //构�?�sql语句，根据传递过来的查询条件参数
        for (String id : ids) {
            String sql = "delete from " + TABLE_NAME + " where id=" + id;
            DBHelper.getInstance().executeUpdate(sql);
        }
        DBHelper.getInstance().close();
        //下面�?始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", action);
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"�?
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代�?
        return jsonObj;
    }

    public JSONObject setRecordTop(String action, String dbName, String type, String userId, String id) throws JSONException, SQLException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        //构�?�sql语句，根据传递过来的查询条件参数
        String sql = "select max(priority) as priority from " + TABLE_NAME + " where user_id='" + userId + "'";
        int priority = 0;
        ResultSet rs = DBHelper.getInstance().executeQuery(sql);
        if (rs.next()) {
            priority = rs.getInt("priority");
        }
        DBHelper.getInstance().executeUpdate("update " + TABLE_NAME + " set priority=" + (priority + 1) + " where id=" + id).close();
        //下面�?始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", action);
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"�?
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代�?
        return jsonObj;
    }

    /*
     * 功能：构造历史记录查询的sql语句,type=all查询�?有，type=id查询某个记录，余下按照条件设置查�?
     */
    private String createGetRecordSql(UserBean query) {
        String sql = "select * from " + TABLE_NAME;
        String where = "";
//        if (query.getId() != null && !query.getId().equals("null")) {
//            where = "where id=" + query.getId();
//        }
        if (query.getTitle() != null && !query.getTitle().equals("null") && !query.getTitle().isEmpty()) {
            if (!where.isEmpty()) {
                where = where + " and name like '%" + query.getTitle() + "%'";
            } else {
                where = "where name like '%" + query.getTitle() + "%'";
            }
        }
        if (query.getTimeFrom() != null && query.getTimeTo() != null && !query.getTimeFrom().isEmpty()) {
            if (!where.isEmpty()) {
                where = where + " and register_date between '" + query.getTimeFrom() + "' and '" + query.getTimeTo() + "'";
            } else {
                where = "where register_date between '" + query.getTimeFrom() + "' and '" + query.getTimeTo() + "'";
            }
        }


        String orderBy = "";
        if ((query.getSortIndex() != null) && (!query.getSortIndex().equals("null"))) {
            if (query.getOrderBy() != null) {
                orderBy = " order by " + getOrderBy(query.getOrderBy());
                System.out.println("---------------------------------orderBy:" + orderBy);
            }
        }

//        if (where.isEmpty()) {
//            sql = "select * from " + query.getTableName() + " where user_id='" + query.getUserId() + "'" + orderBy;
//            System.out.println("---------------------------------orderBy:" + orderBy);
//        } else {
//            sql = "select * from " + query.getTableName() + " " + where + " and user_id='" + query.getUserId() + "'" + orderBy;
//        }


        if (query.getType() != null && query.getType().equals("all") && query.getUserRole().equals("manager")) {
            sql = "select * from " + query.getTableName() + " order by register_date desc";
        } else {
            if (query.getId() != null && !query.getId().equals("null")) {
                sql = "select * from " + query.getTableName() + " where id=" + query.getId();
            } else {
                if (where.isEmpty()) {
                    sql = "select * from " + query.getTableName() + " " + orderBy;
//                    sql = "select * from " + query.getTableName() + " where user_id='" + query.getUserId() + "'" + orderBy;
                    System.out.println("---------------------------------orderBy:" + orderBy);
                } else {
                    sql = "select * from " + query.getTableName() + " " + where + " " + orderBy;
                }
            }
        }
        return sql;
    }

    private String getOrderBy(String orderName) {
        if (orderName.equals("register_date")) {
            orderName = "register_date";
        }
        if (orderName.equals("name")) {
            orderName = "name";
        }
        if (orderName.equals("student_num")) {
            orderName = "student_num";
        }
        return orderName;
    }
}
