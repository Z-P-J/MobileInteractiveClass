package com.interactive.classroom.dao.impl;

import com.interactive.classroom.bean.UserBean;
import com.interactive.classroom.dao.UserDao;
import com.interactive.classroom.dao.filters.UserFilter;
import com.interactive.classroom.utils.DatabaseHelper;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.TimeUtil;
import org.apache.struts2.json.annotations.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Z-P-J
 */
public final class UserDaoImpl implements UserDao {

//    @Override
//    public List<UserBean> getAllUsers(UserBean bean) {
//        return findUsers("");
//    }
//
//    @Override
//    public List<UserBean> getAllStudents(UserBean bean) {
//        return findUsers("");
//    }
//
//    @Override
//    public List<UserBean> getAllTeachers(UserBean bean) {
//        return findUsers("");
//    }

    @Override
    public JSONObject queryUsers(UserFilter filter) throws SQLException, JSONException {
        String resultMsg = "ok";
        int resultCode = 0;
        JSONArray jsonArray = new JSONArray();
        try {
            String sql = filter.getQuerySql(TABLE_NAME);
            System.out.println("UserDao--getRecord--sql=" + sql);
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                JSONObject jsonObj = new JSONObject();
                for (String label : LABELS) {
                    jsonObj.put(label, rs.getString(label));
                }
                if (filter.getUserId() != null && filter.getUserId().equals(rs.getString("id"))) {
                    jsonObj.put("isOwner", 1);
                } else {
                    jsonObj.put("isOwner", 0);
                }
                jsonArray.put(jsonObj);
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

//    @Override
//    public JSONObject getRecord(UserBean query) throws SQLException, JSONException {
//        String resultMsg = "ok";
//        int resultCode = 0;
//        List<List<String>> jsonList = new ArrayList<>();
//        try {
//            String sql = createGetRecordSql(query);
//            //做上下记录导航用
//            int count = 0;
//            System.out.println("UserDao--getRecord--sql=" + sql);
//            ResultSet rs = DatabaseHelper.executeQuery(sql);
//            while (rs.next()) {
//                List<String> list = new ArrayList<>();
//                for (String label : LABELS) {
//                    list.add(rs.getString(label));
//                }
//                if (query.getUserId() != null && query.getUserId().equals(rs.getString("user_id"))) {
//                    list.add("1");
//                } else {
//                    list.add("0");
//                }
//                list.add(count + "");
//                count = count + 1;
//                jsonList.add(list);
//            }
//            rs.close();
//
//        } catch (SQLException sqlexception) {
//            sqlexception.printStackTrace();
//            resultCode = 10;
//            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
//        }
//        JSONObject jsonObj = new JSONObject();
//        jsonObj.put("aaData", jsonList);
//        DatabaseHelper.putTableColumnNames(LABELS_CH, jsonObj);
//        jsonObj.put("result_msg", resultMsg);
//        jsonObj.put("result_code", resultCode);
//        return jsonObj;
//    }

    @Override
    public UserBean getUserByUserName(String username) {
        return findUser("where user_name='" + username + "'");
    }

    @Override
    public UserBean getUserById(int id) {
        return findUser("where id='" + id + "'");
    }

    @Override
    public int registerUser(UserBean user) {
        String sql = "insert into " + TABLE_NAME + "(user_name,password,name,sex,email,wechat,grade,class,student_num,faculty,register_date,user_type) values('"
                + user.getUserName() + "','"
                + user.getPassword() + "','"
                + user.getName() + "','"
                + user.getSex() + "','"
                + user.getEmail() + "','"
                + user.getWeChat() + "','"
                + user.getGrade() + "','"
                + user.getClassName() + "','"
                + user.getStudentNum() + "','"
                + user.getFaculty() + "','"
                + TimeUtil.currentDate() + "','"
                + user.getUserType() + "')";
        return DatabaseHelper.executeUpdateAndGetId(sql);
    }

    @Override
    public boolean deleteUserById(int id) {
        return false;
    }

    @Override
    public boolean deleteUserByUserName(String userName) {
        return false;
    }

    @Override
    public JSONObject deleteRecord(String action, String[] ids) throws JSONException, SQLException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        for (String id : ids) {
            String sql = "delete from " + TABLE_NAME + " where id=" + id;
            DatabaseHelper.executeUpdate(sql);
        }

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", action);
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

    @Override
    public JSONObject updateUser(UserBean user) throws JSONException {
        String sql = "update " + TABLE_NAME
                + " set user_name='" + user.getUserName()
//                    + "',name='" + info.getName()
                + "',sex='" + user.getSex()
                + "',email='" + user.getEmail()
                + "',phone='" + user.getPhone()
                + "',wechat='" + user.getWeChat()
                + "',grade='" + user.getGrade()
                + "',student_num='" + user.getStudentNum()
                + "',faculty='" + user.getFaculty()
                + "' where name='" + user.getName() + "'";
        DatabaseHelper.executeUpdate(sql);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", "ok");
        jsonObj.put("result_code", 0);
        return jsonObj;
    }


//    public JSONObject modifyRecord(UserBean info) throws JSONException {
//        String resultMsg = "ok";
//        int resultCode = 0;
//        List<List<String>> jsonList = new ArrayList<>();
//        try {
//            String sql = "update " + TABLE_NAME
//                    + " set user_name='" + info.getUserName()
//                    + "',sex='" + info.getSex()
//                    + "',email='" + info.getEmail()
//                    + "',phone='" + info.getPhone()
//                    + "',wechat='" + info.getWeChat()
//                    + "',grade='" + info.getGrade()
//                    + "',student_num='" + info.getStudentNum()
//                    + "',faculty='" + info.getFaculty()
//                    + "' where name='" + info.getName() + "'";
//            DatabaseHelper.executeUpdate(sql);
//            sql = "select * from " + TABLE_NAME + " order by register_date desc";
//            ResultSet rs = DatabaseHelper.executeQuery(sql);
//            while (rs.next()) {
//                List<String> list = new ArrayList<>();
//                list.add(rs.getString("id"));
//                list.add(rs.getString("content"));
//                jsonList.add(list);
//            }
//            rs.close();
//
//        } catch (SQLException sqlexception) {
//            sqlexception.printStackTrace();
//            resultCode = 10;
//            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
//        }
//        JSONObject jsonObj = new JSONObject();
//        jsonObj.put("aaData", jsonList);
//        jsonObj.put("result_msg", resultMsg);
//        jsonObj.put("result_code", resultCode);
//        return jsonObj;
//    }


    private List<UserBean> findUsers(String where) {
        List<UserBean> userList = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME;
        if (where != null && !where.isEmpty()) {
            sql = sql +  " " + where;
        }
        try {
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            if (rs.next()) {
                UserBean user = new UserBean();
                user.setId(rs.getString("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setSex(rs.getString("sex"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setUserType(rs.getString("user_type"));
                user.setWeChat(rs.getString("wechat"));
                user.setGrade(rs.getString("grade"));
                user.setClassName(rs.getString("class"));
                user.setFaculty(rs.getString("faculty"));
                user.setStudentNum(rs.getString("student_num"));
                user.setRegisterDate(rs.getString("register_date"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    private UserBean findUser(String where) {
        List<UserBean> userBeans = findUsers(where);
        if (userBeans.size() > 0) {
            return userBeans.get(0);
        }
        return null;
    }

//    private String createGetRecordSql(UserBean query) {
//        String sql = "select * from " + TABLE_NAME;
//        String where = "";
////        if (query.getId() != null && !query.getId().equals("null")) {
////            where = "where id=" + query.getId();
////        }
//        if (query.getTitle() != null && !query.getTitle().equals("null") && !query.getTitle().isEmpty()) {
//            if (!where.isEmpty()) {
//                where = where + " and name like '%" + query.getTitle() + "%'";
//            } else {
//                where = "where name like '%" + query.getTitle() + "%'";
//            }
//        }
//        if (query.getTimeFrom() != null && query.getTimeTo() != null && !query.getTimeFrom().isEmpty()) {
//            if (!where.isEmpty()) {
//                where = where + " and register_date between '" + query.getTimeFrom() + "' and '" + query.getTimeTo() + "'";
//            } else {
//                where = "where register_date between '" + query.getTimeFrom() + "' and '" + query.getTimeTo() + "'";
//            }
//        }
//
//
//        String orderBy = "";
//        if ((query.getSortIndex() != null) && (!query.getSortIndex().equals("null"))) {
//            if (query.getOrderBy() != null) {
//                orderBy = " order by " + query.getOrderBy();
//                System.out.println("---------------------------------orderBy:" + orderBy);
//            }
//        }
//
//        if (query.getType() != null && query.getType().equals("all") && query.getUserType().equals("manager")) {
//            sql = "select * from " + TABLE_NAME + " order by register_date desc";
//        } else {
//            if (query.getId() != null && !query.getId().equals("null")) {
//                sql = "select * from " + TABLE_NAME + " where id=" + query.getId();
//            } else {
//                if (where.isEmpty()) {
//                    sql = "select * from " + TABLE_NAME + " " + orderBy;
////                    sql = "select * from " + query.getTableName() + " where user_id='" + query.getUserId() + "'" + orderBy;
//                    System.out.println("---------------------------------orderBy:" + orderBy);
//                } else {
//                    sql = "select * from " + TABLE_NAME + " " + where + " " + orderBy;
//                }
//            }
//        }
//        return sql;
//    }

}
