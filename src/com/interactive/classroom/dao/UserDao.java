package com.interactive.classroom.dao;

import com.interactive.classroom.bean.UserBean;
import com.interactive.classroom.dao.filters.UserFilter;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Z-P-J
 */
public interface UserDao {

    /**
     * 表名
     */
    String TABLE_NAME = "user_manage";
    /**
     * 数据库中的字段
     */
    String[] LABELS = {"id", "user_name", "name", "sex", "email", "phone", "user_type", "wechat", "grade", "class", "student_num", "faculty", "register_date"};
    /**
     * 数据库中字段中文解释
     */
    String[] LABELS_CH = {"ID", "用户名", "用户名", "性别", "Email", "手机", "用户类别", "微信", "年级", "班级", "学号", "学院", "注册时间"};

    /**
     * 查询用户
     * @param filter UserFilter
     * @return JSONObject
     * @throws SQLException SQLException
     * @throws JSONException JSONException
     */
    JSONObject queryUsers(UserFilter filter) throws SQLException, JSONException;

    /**
     * 根据用户名获取用户（该系统中用户名必须为唯一值，不可重复）
     * @param username 用户名
     * @return 返回查询到的用户bean，若用户不存在，则返回null
     * */
    UserBean getUserByUserName(String username);

    /**
     * 根据用户id获取用户
     * @param id 用户id
     * @return UserBean
     */
    UserBean getUserById(int id);

    /**
     *  注册用户
     * @param bean UserBean对象
     * @return 返回用户注册成功的ID
     * */
    int registerUser(UserBean bean);

    /**
     * 根据用户名删除用户
     * @param userName 用户名
     * @return boolean 删除是否成功（成功：true 失败：false）
     */
    boolean deleteUserByUserName(String userName);

    /**
     * 根据ID删除用户
     * @param ids 用户id
     * @return JSONObject JSONObject
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     * */
    JSONObject deleteUsers(String[] ids) throws JSONException, SQLException;

    /**
     * 更新用户信息
     * @param user UserBean对象
     * @return JSONObject JSONObject
     * @throws JSONException JSONException
     */
    JSONObject updateUser(UserBean user) throws JSONException;

}
