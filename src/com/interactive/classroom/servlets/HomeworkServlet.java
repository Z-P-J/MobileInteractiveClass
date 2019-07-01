package com.interactive.classroom.servlets;
/*
 * 待完成：用MVC模式分开DB和Action操作
 * 增删改查看导印统功能的实现
 */

import com.interactive.classroom.constant.ActionType;
import com.interactive.classroom.constant.UserType;
import com.interactive.classroom.dao.AttendanceDao;
import com.interactive.classroom.dao.filters.AttendanceFilter;
import com.interactive.classroom.dao.filters.FileFilter;
import com.interactive.classroom.dao.filters.FilterFactory;
import com.interactive.classroom.dao.filters.HomeworkFilter;
import com.interactive.classroom.servlets.base.BaseHttpServlet;
import com.interactive.classroom.bean.HomeworkBean;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.HomeworkDao;
import com.interactive.classroom.utils.DatabaseHelper;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.ServletUtil;
import com.interactive.classroom.utils.TimeUtil;
import com.interactive.classroom.utils.export.ExportUtil;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Z-P-J
 */
public class HomeworkServlet extends BaseHttpServlet {

    private static final String MODULE = "homework";
    private static final String SUB = "core";

    private static final String RESULT_PATH = MODULE + "/" + SUB;
    private static final String RESULT_PAGE = "result.jsp";
    private static final String REDIRECT_PATH = MODULE + "/" + SUB;
    private static final String REDIRECT_PAGE = "record_list.jsp";

    @Override
    protected void handleAction(HttpServletRequest request, HttpServletResponse response, String action) {
        try {
            if (ActionType.ACTION_QUERY_HOMEWORKS.equals(action)) {
                queryHomeworks(request, response);
            } else if (ActionType.ACTION_PUBLISH_HOMEWORK.equals(action)) {
                publishHomework(request, response);
            } else if (ActionType.ACTION_EXPORT_HOMEWORKS.equals(action)) {
                exportHomeworks(response);
            } else if (ActionType.ACTION_DELETE_HOMEWORK.equals(action)) {
                deleteHomework(request, response);
            } else if (ActionType.ACTION_SUBMIT_COMMENT.equals(action)) {
                submitComment(request);
            } else if (ActionType.ACTION_UPDATE_HOMEWORK.equals(action)) {
                updateHomeworkInfo(request, response);
            } else {
                switch (action) {
                    //作业管理
                    case "delete_file_record":
                        deleteHomeworkFile(request, response);
                        break;
                    //作业详情
                    case "get_uploaded_files":
                        getUploadedFiles(request, response);
                        break;
                    case "get_homework_detail":
                        getHomeworkDetail(request, response);
                        break;
                    default:
                        processError(request, response, 2, "[" + MODULE + "/" + SUB + "/FileServlet]没有对应的action处理过程，请检查action是否正确！action=" + action, RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
                        break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void queryHomeworks(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String keyword = request.getParameter("keyword");
        String timeFrom = request.getParameter("time_from");
        String timeTo = request.getParameter("time_to");
        String orderBy = request.getParameter("order_by");
        HomeworkFilter filter = FilterFactory.getHomeworkFilter()
                .setUserType(userType)
                .setUserId(userId)
                .setOrder(orderBy)
                .setKeyword(keyword)
                .setPublishTimeFrom(timeFrom)
                .setPublishTimeTo(timeTo);
        JSONObject jsonObj = DaoFactory.getHomeworkDao().queryHomeworks(filter);
        responseByJson(response, jsonObj);
    }

    private void publishHomework(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (UserType.STUDENT.equals(userType)) {
            onError(response, "非法操作!");
            return;
        }
        String createTime = TimeUtil.currentDate();
        HomeworkDao dao = DaoFactory.getHomeworkDao();
        HomeworkBean bean = new HomeworkBean();
        bean.setPublisherId(userId);
        bean.setPublisherName(userName);
        bean.setHomeworkTitle(request.getParameter("title"));
        bean.setHomeworkRequirement(request.getParameter("requirement"));
        bean.setPublishTime(createTime);
        bean.setDeadline(request.getParameter("deadline"));
        bean.setCourseId(request.getParameter("course_id"));

        JSONObject jsonObj = dao.publishHomework(bean);
        log("用户 " + userName + " 于 " + createTime + " 添加了 [" + MODULE + "][" + SUB + "] 记录", "添加记录", MODULE);
        responseByJson(response, jsonObj);
    }

    /**
     * 修改作业信息（仅发布者可以修改）
     * @param request HttpServletRequest
	 * @param response HttpServletResponse
     */
    private void updateHomeworkInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (UserType.STUDENT.equals(userType)) {
            onError(response, "非法操作!");
            return;
        }
        String id = request.getParameter("id");
        JSONObject jsonObj = null;
        if (id != null) {
            HomeworkBean bean = new HomeworkBean();
            bean.setId(id);
            bean.setPublisherName(userName);
            bean.setHomeworkTitle(request.getParameter("title"));
            bean.setHomeworkRequirement(request.getParameter("requirement"));
            bean.setPublishTime(request.getParameter("publish_time"));
            bean.setDeadline(request.getParameter("deadline"));
            jsonObj = DaoFactory.getHomeworkDao().updateHomeworkInfo(bean);
            log("用户 " + userName + " 于 " + TimeUtil.currentDate() + " 修改了 [" + MODULE + "][" + SUB + "] 记录", "修改记录", MODULE);
        }
        onEndDefault(request, response, jsonObj);
    }

    private void deleteHomework(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = request.getParameterValues("id");
        JSONObject jsonObj = null;
        if (ids != null) {
            jsonObj = DaoFactory.getHomeworkDao().deleteHomework(ids);
            log("用户 " + userName + " 于 " + TimeUtil.currentDate() + " 删除了 [" + MODULE + "][" + SUB + "] 记录", "删除记录", MODULE);
        }
        responseByJson(response, jsonObj);
    }

    private void exportHomeworks(HttpServletResponse response) throws JSONException, SQLException {
        HomeworkFilter filter = FilterFactory.getHomeworkFilter()
                .setUserId(userId)
                .setUserType(userType);
        JSONObject jsonObj = DaoFactory.getHomeworkDao().queryHomeworks(filter);

        ExportUtil.with(jsonObj)
                .setModuleName(MODULE)
                .setTableNickName("作业管理")
                .setLabels(HomeworkDao.LABELS)
                .setLabelsZh(HomeworkDao.LABELS_CH)
                .export();

        responseByJson(response, jsonObj);
    }

    private void deleteHomeworkFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = request.getParameterValues("id");
        JSONObject jsonObj = null;
        if (ids != null) {
            String createTime = TimeUtil.currentDate();
            jsonObj = DaoFactory.getFileDao().deleteFileById(ids, ServletUtil.getUploadPath(this));
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
        HomeworkFilter filter = FilterFactory.getHomeworkFilter()
                // 注：在此处order为文件的排序
                .setOrder(request.getParameter("order_by"))
                .setHomeworkId(homeworkId)
                .setUserId(userId)
                .setUserType(userType)
                .setGetFiles(true);

        JSONObject jsonObj = DaoFactory.getHomeworkDao().queryHomeworks(filter);
        responseByJson(response, jsonObj);
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
