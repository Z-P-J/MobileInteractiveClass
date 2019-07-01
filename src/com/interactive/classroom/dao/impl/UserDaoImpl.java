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
                    String value = rs.getString(label);
                    jsonObj.put(label, value == null ? "" : value);
                }
                boolean isOwner = filter.getUserId() != null && filter.getUserId().equals(rs.getString("id"));
                jsonObj.put("isOwner", isOwner ? 1 : 0);
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
        DatabaseHelper.putTableColumnNames(LABELS, LABELS_CH, jsonObj);
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", resultCode);
        return jsonObj;
    }

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
    public boolean deleteUserByUserName(String userName) {
        // ToDo
        return false;
    }

    @Override
    public JSONObject deleteUsers(String[] ids) throws JSONException {
        for (String id : ids) {
            String sql = "delete from " + TABLE_NAME + " where id=" + id;
            DatabaseHelper.executeUpdate(sql);
        }
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_msg", "ok");
        jsonObj.put("result_code", 0);
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

}
