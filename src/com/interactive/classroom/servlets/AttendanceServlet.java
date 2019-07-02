package com.interactive.classroom.servlets;
/*
 * 待完成：用MVC模式分开DB和Action操作
 * 增删改查看导印统功能的实现
 */

import com.interactive.classroom.bean.AttendanceBean;
import com.interactive.classroom.constant.ActionType;
import com.interactive.classroom.dao.AttendanceDao;
import com.interactive.classroom.dao.CourseDao;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.filters.AttendanceFilter;
import com.interactive.classroom.dao.filters.CourseFilter;
import com.interactive.classroom.dao.filters.FileFilter;
import com.interactive.classroom.dao.filters.FilterFactory;
import com.interactive.classroom.servlets.base.BaseHttpServlet;
import com.interactive.classroom.utils.DatabaseHelper;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.ServletUtil;
import com.interactive.classroom.utils.TimeUtil;
import com.interactive.classroom.utils.export.ExportUtil;
import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Z-P-J
 */
public class AttendanceServlet extends BaseHttpServlet {

    private static final String MODULE = "attendance";

    @Override
    protected void handleAction(HttpServletRequest request, HttpServletResponse response, String action) {
        try {
            if (ActionType.ACTION_GET_ATTENDANCES.equals(action)) {
                queryAttendances(request, response);
            } else if (ActionType.ACTION_CHECK_ATTENDANCE.equals(action)) {
                checkAttendance(request, response);
            } else if (ActionType.ACTION_QUERY_ATTENDANCES.equals(action)) {
                queryAttendances(request, response);
            } else if (ActionType.ACTION_PUBLISH_ATTENDANCE.equals(action)) {
                publishAttendance(request, response);
            } else if (ActionType.ACTION_EXPORT_ATTENDANCES.equals(action)) {
                exportAttendances(response);
            } else if (ActionType.ACTION_DELETE_ATTENDANCES.equals(action)) {
                deleteAttendance(request, response);
            } else if (ActionType.ACTION_QUERY_ATTENDANCE_USERS.equals(action)) {
                queryAttendanceUsers(request, response);
            } else {
                onError(response, "没有对应的action处理过程，请检查action是否正确！action=" + action);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始考勤
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    private void checkAttendance(HttpServletRequest request, HttpServletResponse response) throws JSONException, SQLException {
        String courseId = request.getParameter("course_id");
        String id = request.getParameter("id");
        boolean isSuccess = DaoFactory.getAttendanceDao().checkAttendance(userId, id, courseId);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_code", isSuccess ? 0 : 10);
        jsonObj.put("result_msg", isSuccess ? "ok" : "考勤失败！");
        responseByJson(response, jsonObj);
    }

    /**
     * 查询考勤信息
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    private void queryAttendances(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String keyword = request.getParameter("keyword");
        String timeFrom = request.getParameter("time_from");
        String timeTo = request.getParameter("time_to");
        String orderBy = request.getParameter("order_by");

        AttendanceFilter filter = FilterFactory.getAttendanceFilter()
                .setUserId(userId)
                .setUserType(userType)
                .setOrder(orderBy)
                .setKeyword(keyword)
                .setPublishTimeFrom(timeFrom)
                .setPublishTimeTo(timeTo);
        JSONObject jsonObj = DaoFactory.getAttendanceDao().queryAttendances(filter);
        responseByJson(response, jsonObj);
    }

    /**
     * 老师发布考勤
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    private void publishAttendance(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String courseId = request.getParameter("course_id");

        CourseDao dao = DaoFactory.getCourseDao();
        CourseFilter filter = FilterFactory.getCourseFilter()
                .setCourseId(courseId)
                .setUserType(userType)
                .setUserId(userId);
        JSONArray jsonArray = dao.queryCourses(filter).getJSONArray("aaData");
        Log.d(getClass().getName(), jsonArray.toString());
        JSONObject jsonObject;
        if (jsonArray.length() <1) {
            jsonObject = new JSONObject();
            jsonObject.put("result_msg", "课程不存在！");
            jsonObject.put("result_code", 10);
        } else {
            JSONObject courseObj = jsonArray.getJSONObject(0);
            int flag = courseObj.getInt("attendance_count") + 1;
            String createTime = TimeUtil.currentDate();
            AttendanceBean bean = new AttendanceBean();
            bean.setPublisherId(userId);
            bean.setPublisherName(userName);
            bean.setAttendanceTitle(courseObj.getString("course_name") + "第" + flag + "次考勤");
            bean.setAttendanceRequirement(request.getParameter("requirement"));
            bean.setPublishTime(createTime);
            bean.setDeadline(request.getParameter("deadline"));
            bean.setAttendanceFlag(String.valueOf(flag));
            bean.setCourseId(courseId);

            jsonObject = DaoFactory.getAttendanceDao().publishAttendance(bean);
            log("用户 " + userName + " 于 " + createTime + " 添加了 [" + MODULE + "] 记录", "添加记录", MODULE);

            boolean isSuccess = dao.increaseAttendanceCount(courseId, flag);
        }
        responseByJson(response, jsonObject);
    }

    /**
     * 老师删除考勤
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    private void deleteAttendance(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = request.getParameterValues("id");
        JSONObject jsonObj = null;
        if (ids != null) {
            jsonObj = DaoFactory.getAttendanceDao().deleteAttendance(ids);
            log("用户 " + userName + " 于 " + TimeUtil.currentDate() + " 删除了 [" + MODULE + "] 记录", "删除记录", MODULE);
        }
        responseByJson(response, jsonObj);
    }

    /**
     * 查询考勤用户
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    private void queryAttendanceUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String courseId = request.getParameter("course_id");
        String attendanceFlag = request.getParameter("attendance_flag");
        JSONObject jsonObj = DaoFactory.getAttendanceDao().queryAttendanceUsers(courseId, attendanceFlag);
        Log.d(getClass().getName(), jsonObj.toString());
        responseByJson(response, jsonObj);
    }

    /**
     * 导出考勤信息
     * @param response HttpServletResponse
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    private void exportAttendances(HttpServletResponse response) throws SQLException, JSONException {
        AttendanceFilter filter = FilterFactory.getAttendanceFilter()
                .setUserId(userId)
                .setUserType(userType);
        JSONObject jsonObj = DaoFactory.getAttendanceDao().queryAttendances(filter);

        ExportUtil.with(jsonObj)
                .setModuleName(MODULE)
                .setTableNickName("考勤管理")
                .setLabels(AttendanceDao.LABELS)
                .setLabelsZh(AttendanceDao.LABELS_CH)
                .export();

        responseByJson(response, jsonObj);
    }

}
