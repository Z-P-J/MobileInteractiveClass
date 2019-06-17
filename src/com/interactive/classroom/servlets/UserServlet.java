package com.interactive.classroom.servlets;

import com.interactive.classroom.constant.ActionType;
import com.interactive.classroom.dao.filters.FilterFactory;
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
            if (ActionType.ACTION_GET_USRES.equals(action)) {
                getUsers(request, response);
            } else if (ActionType.ACTION_ADD_USER.equals(action)) {
                addUser(request, response);
            } else if (ActionType.ACTION_GET_USRE_DETAIL.equals(action)) {
                getUserDetail(request, response);
            } else if (ActionType.ACTION_UPDATE_USER.equals(action)) {
                updateUser(request, response);
            } else if (ActionType.ACTION_DELETE_USER.equals(action)) {
                deleteUser(request, response);
            } else if ("export_record".equals(action)) {
                // 仅管理员
                JSONObject jsonObj = ExportUtil.exportRecord(request, response, MODULE, SUB, "用户管理");
                jsonObj.put("result_url", RESULT_URL);
                onEnd(request, response, jsonObj);
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
    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException {

        UserFilter filter = FilterFactory.getUserFilter()
                .setUserId(userId)
                .setUserType(userType)
                .setOrder(request.getParameter("order_by"));

        JSONObject jsonObj = DaoFactory.getUserDao().queryUsers(filter);
        responseByJson(response, jsonObj);

//        String action = request.getParameter("action");
//        String id = request.getParameter("id");
//        String title = request.getParameter("title");
//        String content = request.getParameter("content");
//        String type = request.getParameter("type");
//        String timeFrom = request.getParameter("time_from");
//        String timeTo = request.getParameter("time_to");
//        String sortIndex = request.getParameter("sort_index");
//        String orderBy = request.getParameter("order_by");
//        String existResultset = request.getParameter("exist_resultset");
//        if ((existResultset == null) || (existResultset.equals("null") || existResultset.isEmpty()))
//            existResultset = "0";
//        debug("[getRecord]收到页面传过来的参数是：" + existResultset + "," + action + "," + type + "," + id + "," + timeFrom + "," + timeTo);
//
//        UserBean query = new UserBean();
//        query.setId(id);
//        query.setAction(action);
//        query.setType(type);
//        query.setTitle(title);
//        query.setContent(content);
//        query.setTimeFrom(timeFrom);
//        query.setTimeTo(timeTo);
//        query.setUserId(userId);
//        query.setSortIndex(sortIndex);
//        query.setOrderBy(orderBy);
//
//        JSONObject jsonObj;
//        if (existResultset.equals("1")) {
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
//            com.interactive.classroom.dao.UserDao infoDao = DaoFactory.getUserDao();
//            jsonObj = infoDao.getRecord(query);
////            String sql = "select * from ";
//            session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
//        }
//        jsonObj.put("user_id", userId);
//        jsonObj.put("user_name", userName);
//        jsonObj.put("user_role", userType);
//        jsonObj.put("user_avatar", userAvatar);
//        jsonObj.put("action", action);
//        /*--------------------数据查询完毕，根据交互方式返回数据--------------------*/
//
//        onEnd(request, response, jsonObj, REDIRECT_PATH + "/" + REDIRECT_PAGE + "?exist_resultset=1");
    }

    private void getUserDetail(HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException {
        String id = request.getParameter("id");
        String index = request.getParameter("index");
        UserFilter filter = FilterFactory.getUserFilter()
                .setOrder(request.getParameter("order_by"))
                .setUserType(userType)
                .setUserId(userId);
        JSONObject jsonObj = DaoFactory.getUserDao().queryUsers(filter);
        ServletUtil.getResultSetNavigateId(id, index, jsonObj);
        responseByJson(response, jsonObj);




//        String action = request.getParameter("action");
//        String id = request.getParameter("id");
//        String index = request.getParameter("index");
//        String existResultset = request.getParameter("exist_resultset");
//        if ((existResultset == null) || (existResultset.equals("null") || existResultset.isEmpty()))
//            existResultset = "0";
//
//        debug("收到页面传过来的参数是：exist_resultset=" + existResultset + ",action=" + action + ",id=" + id + ",index=" + index);
//
//        JSONObject jsonObj = null;
//        UserBean query = new UserBean();
//        query.setAction(action);
//        query.setUserId(userId);
//        if (existResultset.equals("1")) {            //如果是新查询
//            //如果有就取出来，如果没有就重新查询一次，并且保存进session里
//            if (session.getAttribute(MODULE + "_" + SUB + "_get_record_result") != null) {
//                JSONObject json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
//                debug(json.toString());
//                jsonObj = ServletUtil.getResultSetNavigateId(id, index, json);
//                jsonObj.put("user_id", userId);
//                jsonObj.put("user_name", userName);
//                jsonObj.put("action", action);
//                jsonObj.put("result_code", 0);
//                jsonObj.put("result_msg", "ok");
//                //然后还有导航信息
//                json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
//                debug("[getRecordView]重新取出来的数据是：" + json.toString());
//            } else {
//                //如果没有就重新查询一次
//                debug("[getRecordView]没有就重新查询一次。");
//                UserDao dao = DaoFactory.getUserDao();
//                jsonObj = dao.getRecord(query);
//                jsonObj.put("user_id", userId);
//                jsonObj.put("user_name", userName);
//                jsonObj.put("action", action);
//                jsonObj.put("result_code", 0);
//                jsonObj.put("result_msg", "ok");
//                session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
//            }
//        } else {
//            debug("[getRecordView]existsResult=0，重新查询");
//            UserDao infoDao = DaoFactory.getUserDao();
//            jsonObj = infoDao.getRecord(query);
//            jsonObj.put("user_id", userId);
//            jsonObj.put("user_name", userName);
//            jsonObj.put("action", action);
//            session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
//        }
//
//        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
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
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", new ArrayList());
        jsonObj.put("result_msg", "ok");
        jsonObj.put("result_code", 0);
        log("用户 " + userName + " 于 " + createTime + " 添加了 [" + MODULE + "][" + SUB + "] 记录", "添加记录", MODULE);

        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String id = request.getParameter("id");
        JSONObject jsonObj = null;
        if (id != null) {
            String createTime = TimeUtil.currentDate();
            UserBean user = new UserBean(request);
            jsonObj = DaoFactory.getUserDao().updateUser(user);
            log("用户 " + userName + " 于 " + createTime + " 修改了 [" + MODULE + "][" + SUB + "] 记录", "修改记录", MODULE);
        }
        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException {
        String[] ids = request.getParameterValues("id");
        Log.d(TAG, "ids=" + Arrays.toString(ids));

    }

}
