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
    private static final String SUB = "core";

    private static final String RESULT_PATH = MODULE + "/" + SUB;
    private static final String RESULT_PAGE = "result.jsp";
    private static final String REDIRECT_PATH = MODULE + "/" + SUB;
    private static final String REDIRECT_PAGE = "record_list.jsp";

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
            } else {
                processError(request, response, 2, "[" + MODULE + "/" + SUB + "/FileServlet]没有对应的action处理过程，请检查action是否正确！action=" + action, RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
            }

//            switch (action) {
//                //考勤管理
//                case "get_attendances":
//
//                    break;
//                case "check_attendance":
//                    checkAttendance(request, response);
//                    break;
//                case "query_attendances":
//                    queryAttendances(request, response);
//                    break;
//                case "publish_attendance":
//                    publishAttendance(request, response);
//                    break;
//                case "modify_record":
//                    updateHomeworkInfo(request, response);
//                    break;
//                case "delete_record":
//                    deleteRecord(request, response);
//                    break;
//                case "delete_file_record":
//                    deleteHomeworkFile(request, response);
//                    break;
//                case "export_attendances":
//                    AttendanceFilter filter = FilterFactory.getAttendanceFilter()
//                            .setUserId(userId)
//                            .setUserType(userType);
//                    JSONObject jsonObj = DaoFactory.getAttendanceDao().queryAttendances(filter);
//
//                    ExportUtil.with(jsonObj)
//                            .setModuleName(MODULE)
//                            .setTableNickName("考勤管理")
//                            .setLabels(AttendanceDao.LABELS)
//                            .setLabelsZh(AttendanceDao.LABELS_CH)
//                            .export();
//
//                    responseByJson(response, jsonObj);
//                    break;
//                default:
//                    processError(request, response, 2, "[" + MODULE + "/" + SUB + "/FileServlet]没有对应的action处理过程，请检查action是否正确！action=" + action, RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
//                    break;
//            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void getAttendances(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jsonObj;
        String orderBy = request.getParameter("order_by");
        AttendanceFilter filter = FilterFactory.getAttendanceFilter()
                .setUserId(userId)
                .setUserType(userType)
                .setOrder(orderBy);
        jsonObj = DaoFactory.getAttendanceDao().queryAttendances(filter);
//        if (UserType.MANAGER.equals(userType)) {
//            // 管理员
//            // TODO 直接获取数据库所有作业
//            HomeworkFilter filter = FilterFactory.getHomeworkFilter()
//                    .setOrder(orderBy);
//            jsonObj = DaoFactory.getHomeworkDao().queryAttendances(filter);
//        } else {
//            // TODO 弄个课程管理模块，根据课程获取作业
//            jsonObj = DaoFactory.getHomeworkDao().getAllHomeworks(orderBy);
//        }
        session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        String url = REDIRECT_PATH + "/" + REDIRECT_PAGE + "?exist_resultset=1";
        onEnd(request, response, jsonObj, url);
    }

    private void checkAttendance(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String courseId = request.getParameter("course_id");
        String id = request.getParameter("id");
        boolean isSuccess = DaoFactory.getAttendanceDao().checkAttendance(userId, id, courseId);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_code", isSuccess ? 0 : 10);
        jsonObj.put("result_msg", isSuccess ? "ok" : "考勤失败！");
        responseByJson(response, jsonObj);
    }

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

//        String existResultset = request.getParameter("exist_resultset");
//        if ((existResultset == null) || ("null".equals(existResultset) || existResultset.isEmpty())) {
//            existResultset = "0";
//        }
//        JSONObject jsonObj;
//        if ("1".equals(existResultset)) {
//            //要求提取之前查询结果，如果有就取出来，如果没有就重新查询一次，并且保存进session里
//            if (session.getAttribute(MODULE + "_" + SUB + "_get_record_result") != null) {
//                jsonObj = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
//            } else {
//                //如果没有就报错
//                jsonObj = new JSONObject();
//                jsonObj.put("result_code", 10);
//                jsonObj.put("result_msg", "exist_resultset参数不当，服务器当前没有结果数据！请重新设置！");
//            }
//        } else {
//            //如果是新查询
//
//            session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
//        }
//        String url = REDIRECT_PATH + "/" + REDIRECT_PAGE + "?exist_resultset=1";
//        onEnd(request, response, jsonObj, url);
    }

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
            log("用户 " + userName + " 于 " + createTime + " 添加了 [" + MODULE + "][" + SUB + "] 记录", "添加记录", MODULE);

            dao.increaseAttendanceCount(courseId, flag);
        }
        responseByJson(response, jsonObject);
    }

    /**
     * 修改作业信息（仅发布者可以修改）
     * @param request HttpServletRequest
	 * @param response HttpServletResponse
     */
    private void updateHomeworkInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        JSONObject jsonObj = null;
        if (id != null) {
            AttendanceBean bean = new AttendanceBean();
            bean.setId(id);
            bean.setPublisherName(userName);
            bean.setAttendanceTitle(request.getParameter("homework_title"));
            bean.setAttendanceRequirement(request.getParameter("homework_requirement"));
            bean.setPublishTime(request.getParameter("publish_time"));
            bean.setDeadline(request.getParameter("deadline"));
            jsonObj = DaoFactory.getAttendanceDao().updateAttendanceInfo(bean);
            log("用户 " + userName + " 于 " + TimeUtil.currentDate() + " 修改了 [" + MODULE + "][" + SUB + "] 记录", "修改记录", MODULE);
        }
        onEndDefault(request, response, jsonObj);
    }

    private void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        String[] ids = request.getParameterValues("id");
        JSONObject jsonObj = null;
        if (ids != null) {
            jsonObj = DaoFactory.getAttendanceDao().deleteAttendance(ids);
            jsonObj.put("action", action);
            log("用户 " + userName + " 于 " + TimeUtil.currentDate() + " 删除了 [" + MODULE + "][" + SUB + "] 记录", "删除记录", MODULE);
        }
        onEndDefault(request, response, jsonObj);
    }

    private void deleteHomeworkFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = request.getParameterValues("id");
        JSONObject jsonObj = null;
        if (ids != null) {
            String createTime = TimeUtil.currentDate();
            jsonObj = DaoFactory.getFileDao().deleteFileById(ids, ServletUtil.getUploadPath(this));
            jsonObj.put("action", request.getParameter("action"));
            log("用户 " + userName + " 于 " + createTime + " 删除了 [" + MODULE + "][" + SUB + "] 记录", "删除记录", MODULE);
        }
        onEndDefault(request, response, jsonObj);
    }

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
