package com.interactive.classroom.dao;

import com.interactive.classroom.bean.UserBean;
import com.interactive.classroom.utils.DBHelper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Z-P-J
 */
public interface UserDao {

    String TABLE_NAME = "user_manage";

    String[] LABELS = {"id", "user_name", "user_id", "name", "sex", "email", "phone", "user_type", "wechat", "grade", "class", "student_num", "faculty", "register_date"};
    String[] LABELS_CH = {"ID", "用户名", "用户id", "用户名", "性别", "Email", "手机", "用户类别", "微信", "年级", "班级", "学号", "学院", "注册时间"};

    /**
     * 获取所有用户
     * @param bean UserBean对象
     * @return List<UserBean>
     */
    List<UserBean> getAllUsers(UserBean bean);

    /**
     * 根据老师用户获取所有学生
     * @param bean UserBean对象
     * @return List<UserBean>
     */
    List<UserBean> getAllStudents(UserBean bean);

    /**
     * 根据学生用户获取所有老师
     * @param bean UserBean对象
     * @return List<UserBean>
     */
    List<UserBean> getAllTeachers(UserBean bean);

    JSONObject getRecord(UserBean query) throws SQLException, JSONException;

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
     * 根据ID删除用户
     * @param id 用户id
     * @return 删除是否成功（成功：true 失败：false）
     * */
    boolean deleteUserById(int id);

    /**
     * 根据用户名删除用户
     * @param userName 用户名
     * @return boolean 删除是否成功（成功：true 失败：false）
     */
    boolean deleteUserByUserName(String userName);

    JSONObject deleteRecord(String action, String[] ids) throws JSONException, SQLException;

    /**
     * 更新用户信息
     * @param user UserBean对象
     * @return boolean
     */
    boolean updateUser(UserBean user);

    JSONObject modifyRecord(UserBean info) throws JSONException;



}
