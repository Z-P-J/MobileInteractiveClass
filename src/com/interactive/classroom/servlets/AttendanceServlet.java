package com.interactive.classroom.servlets;
/*
 * 待完成：用MVC模式分开DB和Action操作
 * 增删改查看导印统功能的实现
 */

import com.interactive.classroom.bean.HomeworkBean;
import com.interactive.classroom.dao.AttendanceDao;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.HomeworkDao;
import com.interactive.classroom.dao.filters.FileFilter;
import com.interactive.classroom.dao.filters.FilterFactory;
import com.interactive.classroom.dao.filters.HomeworkFilter;
import com.interactive.classroom.servlets.base.BaseHttpServlet;
import com.interactive.classroom.utils.DatabaseHelper;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.ServletUtil;
import com.interactive.classroom.utils.TimeUtil;
import com.interactive.classroom.utils.export.ExportUtil;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * @author Z-P-J
 */
public class AttendanceServlet extends BaseHttpServlet {

    private static final String MODULE = "homework";
    private static final String SUB = "core";

    private static final String RESULT_PATH = MODULE + "/" + SUB;
    private static final String RESULT_PAGE = "result.jsp";
    private static final String REDIRECT_PATH = MODULE + "/" + SUB;
    private static final String REDIRECT_PAGE = "record_list.jsp";

    @Override
    protected void handleAction(HttpServletRequest request, HttpServletResponse response, String action) {
        try {
            switch (action) {
                //考勤管理
                case "get_attendances":
                    getAttendances(request, response);
                    break;
                case "query_homeworks":
                    queryHomeworks(request, response);
                    break;
                case "publish_homework":
                    publishHomework(request, response);
                    break;
                case "modify_record":
                    updateHomeworkInfo(request, response);
                    break;
                case "delete_record":
                    deleteRecord(request, response);
                    break;
                case "delete_file_record":
                    deleteHomeworkFile(request, response);
                    break;
                case "export_record":
                    JSONObject jsonObj = ExportUtil.exportRecord(request, response, MODULE, SUB, "调查管理");
                    onEnd(request, response, jsonObj);
                    break;
                default:
                    processError(request, response, 2, "[" + MODULE + "/" + SUB + "/FileServlet]没有对应的action处理过程，请检查action是否正确！action=" + action, RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
                    break;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void getAttendances(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jsonObj;
        String orderBy = request.getParameter("order_by");
        HomeworkFilter filter = FilterFactory.getHomeworkFilter()
                .setUserId(userId)
                .setUserType(userType)
                .setOrder(orderBy);
        jsonObj = DaoFactory.getAttendanceDao().queryHomeworks(filter);
//        if (UserType.MANAGER.equals(userType)) {
//            // 管理员
//            // TODO 直接获取数据库所有作业
//            HomeworkFilter filter = FilterFactory.getHomeworkFilter()
//                    .setOrder(orderBy);
//            jsonObj = DaoFactory.getHomeworkDao().queryHomeworks(filter);
//        } else {
//            // TODO 弄个课程管理模块，根据课程获取作业
//            jsonObj = DaoFactory.getHomeworkDao().getAllHomeworks(orderBy);
//        }
        session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        String url = REDIRECT_PATH + "/" + REDIRECT_PAGE + "?exist_resultset=1";
        onEnd(request, response, jsonObj, url);
    }

    private void queryHomeworks(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String keyword = request.getParameter("keyword");
        String timeFrom = request.getParameter("time_from");
        String timeTo = request.getParameter("time_to");
        String orderBy = request.getParameter("order_by");

        String existResultset = request.getParameter("exist_resultset");
        if ((existResultset == null) || ("null".equals(existResultset) || existResultset.isEmpty())) {
            existResultset = "0";
        }
        JSONObject jsonObj;
        if ("1".equals(existResultset)) {
            //要求提取之前查询结果，如果有就取出来，如果没有就重新查询一次，并且保存进session里
            if (session.getAttribute(MODULE + "_" + SUB + "_get_record_result") != null) {
                jsonObj = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
            } else {
                //如果没有就报错
                jsonObj = new JSONObject();
                jsonObj.put("result_code", 10);
                jsonObj.put("result_msg", "exist_resultset参数不当，服务器当前没有结果数据！请重新设置！");
            }
        } else {
            //如果是新查询
            HomeworkFilter filter = FilterFactory.getHomeworkFilter()
                    .setOrder(orderBy)
                    .setKeyword(keyword)
                    .setPublishTimeFrom(timeFrom)
                    .setPublishTimeTo(timeTo);
            jsonObj = DaoFactory.getAttendanceDao().queryHomeworks(filter);
            session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        }
        String url = REDIRECT_PATH + "/" + REDIRECT_PAGE + "?exist_resultset=1";
        onEnd(request, response, jsonObj, url);
    }

    private void publishHomework(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String createTime = TimeUtil.currentDate();
        AttendanceDao dao = DaoFactory.getAttendanceDao();
        HomeworkBean bean = new HomeworkBean();
        bean.setPublisherId(userId);
        bean.setPublisherName(userName);
        bean.setHomeworkTitle(request.getParameter("homework_title"));
        bean.setHomeworkRequirement(request.getParameter("homework_requirement"));
        bean.setPublishTime(request.getParameter("publish_time"));
        bean.setDeadline(request.getParameter("deadline"));

        JSONObject jsonObj = dao.publishHomework(bean);
        log("用户 " + userName + " 于 " + createTime + " 添加了 [" + MODULE + "][" + SUB + "] 记录", "添加记录", MODULE);
        onEndDefault(request, response, jsonObj);
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
            HomeworkBean bean = new HomeworkBean();
            bean.setId(id);
            bean.setPublisherName(userName);
            bean.setHomeworkTitle(request.getParameter("homework_title"));
            bean.setHomeworkRequirement(request.getParameter("homework_requirement"));
            bean.setPublishTime(request.getParameter("publish_time"));
            bean.setDeadline(request.getParameter("deadline"));
            jsonObj = DaoFactory.getAttendanceDao().updateHomeworkInfo(bean);
            log("用户 " + userName + " 于 " + TimeUtil.currentDate() + " 修改了 [" + MODULE + "][" + SUB + "] 记录", "修改记录", MODULE);
        }
        onEndDefault(request, response, jsonObj);
    }

    private void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        String[] ids = request.getParameterValues("id");
        JSONObject jsonObj = null;
        if (ids != null) {
            jsonObj = DaoFactory.getAttendanceDao().deleteHomework(ids);
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


    //作业管理文件详细信息

    private void getUploadedFiles(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String homeworkId = request.getParameter("homework_id");
        FileFilter filter = FilterFactory.getFileFilter()
                .setHomeworkId(homeworkId)
                .setUserId(userId);
        JSONObject jsonObj = DaoFactory.getFileDao().queryFiles(filter);
        String url = REDIRECT_PATH + "/" + REDIRECT_PAGE + "?exist_resultset=1";
        onEnd(request, response, jsonObj, url);
    }

    private void getHomeworkDetail(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String homeworkId = request.getParameter("homework_id");
//        String index = request.getParameter("index");

        HomeworkFilter filter = FilterFactory.getHomeworkFilter()
                // 注：在此处order为文件的排序
                .setOrder(request.getParameter("order_by"))
                .setHomeworkId(homeworkId)
                .setUserId(userId)
                .setUserType(userType)
                .setGetFiles(true);

        JSONObject jsonObj = DaoFactory.getAttendanceDao().queryHomeworks(filter);
        responseByJson(response, jsonObj);

//        String attr = MODULE + "_" + SUB + "_get_record_result";
//        String existResultset = request.getParameter("exist_resultset");
//        if ((existResultset == null) || ("null".equals(existResultset) || existResultset.isEmpty())) {
//            existResultset = "0";
//        }
//
//        //如果是新查询
//        JSONObject jsonObj;
//        if ("1".equals(existResultset)) {
//            //如果有就取出来，如果没有就重新查询一次，并且保存进session里
//            if (session.getAttribute(attr) != null) {
//                JSONObject json = (JSONObject) session.getAttribute(attr);
//                Log.d(getClass().getName(), json.toString());
//                jsonObj = ServletUtil.getResultSetNavigateId(homeworkId, index, json);
//                jsonObj.put("user_id", userId);
//                jsonObj.put("user_name", userName);
//                jsonObj.put("result_code", 0);
//                jsonObj.put("result_msg", "ok");
//                //然后还有导航信息
//                json = (JSONObject) session.getAttribute(attr);
//                debug("[getRecordView]重新取出来的数据是：" + json.toString());
//            } else {
//                //如果没有就重新查询一次
//                debug("[getRecordView]没有就重新查询一次。");
//                jsonObj = DaoFactory.getAttendanceDao().getAllHomeworks(null);
//                jsonObj.put("user_id", userId);
//                jsonObj.put("user_name", userName);
//                jsonObj.put("result_code", 0);
//                jsonObj.put("result_msg", "ok");
//                session.setAttribute(attr, jsonObj);
//            }
//        } else {
//            debug("[getRecordView]existsResult=0，重新查询");
//            jsonObj = DaoFactory.getAttendanceDao().getAllHomeworks(null);
//            jsonObj.put("user_id", userId);
//            jsonObj.put("user_name", userName);
//            session.setAttribute(attr, jsonObj);
//        }
//        onEndDefault(request, response, jsonObj);
    }

    private void submitComment(HttpServletRequest request) {
        String fileId = request.getParameter("file_id");
        String sql = "insert into file_comment (file_id,user_id,comment_content,score,publish_date) values("
                + fileId + ",'" + userName + "','" + request.getParameter("comment_text") +
                "'," + 50 + ",'" + TimeUtil.currentDate() + "')";
        Log.d("submitComment", "sql=" + sql);
        DatabaseHelper.executeUpdate(sql);
        if (session.getAttribute(MODULE + "_" + SUB + "_get_record_result") != null) {
            JSONObject json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
            Log.d(getClass().getName(), json.toString());
            try {
                ArrayList list = ServletUtil.getIndexFromFileId(fileId, json);
                if (list != null) {
                    list.add(9, DaoFactory.getCommentDao().getComments(fileId));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //然后还有导航信息
            json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
            Log.d(getClass().getName(), "[submitComment]重新取出来的数据是：" + json.toString());
        }
    }

}
