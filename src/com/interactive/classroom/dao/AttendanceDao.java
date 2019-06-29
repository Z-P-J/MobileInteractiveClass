package com.interactive.classroom.dao;

import com.interactive.classroom.bean.HomeworkBean;
import com.interactive.classroom.dao.filters.HomeworkFilter;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

/**
 * @author Z-P-J
 */
public interface AttendanceDao {

    /**
     * 表名
     */
    String TABLE_NAME = "attendance_manage";
    /**
     * 数据库中的字段
     */
    String[] LABELS = {"id", "publisher_id", "publisher_name", "attendance_title", "attendance_requirement", "publish_time", "deadline", "file_name_format"};
    /**
     * 数据库中字段中文解释
     */
    String[] LABELS_CH = {"ID", "发布人ID", "发布人名字", "考勤课程", "考勤要求", "发布时间", "截止时间", "文件命名格式"};

//    /**
//     * 获取数据库中所有作业（管理员）
//     * @param order 排序
//     * @return org.json.JSONObject
//     * @throws JSONException JSONException
//     * @throws SQLException SQLException
//     */
//    JSONObject getAllHomeworks(String order) throws SQLException, JSONException;

//    /**
//     * 根据课程id获取作业（管理员）
//     * @param courseId 课程id
//     * @param order 排序
//     * @return org.json.JSONObject
//     * @throws JSONException JSONException
//     * @throws SQLException SQLException
//     */
//    JSONObject getHomeworksByCourse(String courseId, String order) throws SQLException, JSONException;

    /**
     * 过滤查询作业
     * @param filter HomeworkFilter
     * @return org.json.JSONObject
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    JSONObject queryHomeworks(HomeworkFilter filter) throws SQLException, JSONException;

    /**
     * 更新作业信息
     * @param bean 带更新的HomeworkBean对象
     * @return org.json.JSONObject
     */
    JSONObject updateHomeworkInfo(HomeworkBean bean) throws JSONException;

    /**
     * 发布作业
     * @param bean 待发布的HomeworkBean对象
     * @return JSONObject
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    JSONObject publishHomework(HomeworkBean bean) throws JSONException, SQLException;

    /**
     * 根据id删除作业
     * @param ids 待删除的ids
     * @return JSONObject
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    JSONObject deleteHomework(String[] ids) throws JSONException, SQLException;

}
