package com.interactive.classroom.servlets;

import com.interactive.classroom.constant.ActionType;
import com.interactive.classroom.dao.HomeworkDao;
import com.interactive.classroom.dao.filters.FilterFactory;
import com.interactive.classroom.dao.filters.HomeworkFilter;
import com.interactive.classroom.dao.filters.UserFilter;
import com.interactive.classroom.servlets.base.BaseHttpServlet;
import com.interactive.classroom.bean.UserBean;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.UserDao;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.ServletUtil;
import com.interactive.classroom.utils.TimeUtil;
import com.interactive.classroom.utils.export.ExportUtil;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Z-P-J
 */
public class UserServlet extends BaseHttpServlet {

    private static final String TAG = "";

    private static final String MODULE = "user";
    private static final String SUB = "info";
    private static final String RESULT_PATH = MODULE + "/" + SUB;
    private static final String RESULT_PAGE = "result.jsp";
    private static final String RESULT_URL = RESULT_PATH + "/" + RESULT_PAGE;
    private static final String REDIRECT_PAGE = "record_list.jsp";

    @Override
    protected void handleAction(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        try {
            if (ActionType.ACTION_QUERY_USRES.equals(action)) {
                queryUsers(request, response);
            } else if (ActionType.ACTION_ADD_USER.equals(action)) {
                addUser(request, response);
            } else if (ActionType.ACTION_GET_USRE_DETAIL.equals(action)) {
                getUserDetail(request, response);
            } else if (ActionType.ACTION_UPDATE_USER.equals(action)) {
                updateUser(request, response);
            } else if (ActionType.ACTION_DELETE_USER.equals(action)) {
                deleteUser(request, response);
            } else if (ActionType.ACTION_EXPORT_USERS.equals(action)) {
                exportUsers(response);
            } else {
                onError(response, "没有对应的action处理过程，请检查action是否正确！action=" + action);
            }
        } catch (JSONException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户（教师或管理员可用）
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws SQLException SQLException
     * @throws JSONException JSONException
     */
    private void queryUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException {
        String keyword = request.getParameter("keyword");
        String timeFrom = request.getParameter("time_from");
        String timeTo = request.getParameter("time_to");
        String orderBy = request.getParameter("order_by");
        UserFilter filter = FilterFactory.getUserFilter()
                .setUserId(userId)
                .setUserType(userType)
                .setOrder(orderBy)
                .setTimeFrom(timeFrom)
                .setTimeTo(timeTo)
                .setKeyword(keyword);
        JSONObject jsonObject = DaoFactory.getUserDao().queryUsers(filter);
        responseByJson(response, jsonObject);
    }

    private void getUserDetail(HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException {
        String id = request.getParameter("id");
        String index = request.getParameter("index");
        UserFilter filter = FilterFactory.getUserFilter()
                .setOrder(request.getParameter("order_by"))
                .setUserType(userType)
                .setUserId(userId);
        JSONObject jsonObj = DaoFactory.getUserDao().queryUsers(filter);
        ServletUtil.wrapJson(id, index, jsonObj);
        responseByJson(response, jsonObj);
    }

    /**
     * 管理员才调用该方法
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws JSONException JSONException
     */
    private void addUser(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String createTime = TimeUtil.currentDate();

        UserDao infoDao = DaoFactory.getUserDao();
        UserBean user = new UserBean();
        user.setRegisterDate(createTime);
        user.setUserName(request.getParameter("user_name"));
        user.setName(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setSex(request.getParameter("sex"));
        user.setEmail(request.getParameter("email"));
        user.setPhone(request.getParameter("phone"));
        user.setWeChat(request.getParameter("wechat"));
        user.setGrade(request.getParameter("grade"));
        user.setClassName(request.getParameter("class"));
        user.setStudentNum(request.getParameter("student_num"));
        user.setFaculty(request.getParameter("faculty"));

        int id = infoDao.registerUser(user);
        log("用户 " + userName + " 于 " + createTime + " 添加了 [" + MODULE + "][" + SUB + "] 记录", "添加记录", MODULE);
        onSuccess(response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String createTime = TimeUtil.currentDate();
        UserBean user = new UserBean(request);
        JSONObject jsonObj = DaoFactory.getUserDao().updateUser(user);
        log("用户 " + userName + " 于 " + createTime + " 修改了 [" + MODULE + "][" + SUB + "] 记录", "修改记录", MODULE);
        responseByJson(response, jsonObj);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException {
        String[] ids = request.getParameterValues("id");
        Log.d(TAG, "ids=" + Arrays.toString(ids));
        JSONObject jsonObject = DaoFactory.getUserDao().deleteUsers(ids);
        responseByJson(response, jsonObject);
    }

    private void exportUsers(HttpServletResponse response) throws JSONException, SQLException {
        UserFilter filter = FilterFactory.getUserFilter()
                .setUserId(userId)
                .setUserType(userType);
        JSONObject jsonObj = DaoFactory.getUserDao().queryUsers(filter);

        ExportUtil.with(jsonObj)
                .setModuleName(MODULE)
                .setTableNickName("用户管理")
                .setLabels(UserDao.LABELS)
                .setLabelsZh(UserDao.LABELS_CH)
                .export();

        responseByJson(response, jsonObj);
    }

}
