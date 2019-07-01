package com.interactive.classroom.servlets;

import com.interactive.classroom.constant.ActionType;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.filters.*;
import com.interactive.classroom.servlets.base.BaseHttpServlet;
import com.interactive.classroom.utils.Log;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * @author Z-P-J
 */
public class PrintServlet extends BaseHttpServlet {


    private static final String MODULE = "base";
    private static final String SUB = "statistic";

    private static final String RESULT_PATH = MODULE + "/" + SUB;
    private static final String RESULT_PAGE = "result.jsp";
    private String resultUrl = RESULT_PATH + "/" + RESULT_PAGE;
    private static final String REDIRECT_PATH = MODULE + "/" + SUB;
    private static final String REDIRECT_PAGE = "record_list.jsp";
    private String redirectUrl = REDIRECT_PATH + "/" + REDIRECT_PAGE;

    @Override
    public void handleAction(HttpServletRequest request, HttpServletResponse response, String action) {
        try {
            if (ActionType.ACTION_GET_PRINT_DATA.equals(action)) {
                getPrintData(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPrintData(HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException {
        String moduleName = request.getParameter("module_name");
        Log.d(getClass().getName(), "moduleName=" + moduleName);
        JSONObject jsonObject;
        if ("attendance".equals(moduleName)) {
            AttendanceFilter filter = FilterFactory.getAttendanceFilter()
                    .setUserId(userId)
                    .setUserType(userType);
            jsonObject = DaoFactory.getAttendanceDao().queryAttendances(filter);
        } else if ("homework".equals(moduleName)) {
            HomeworkFilter filter = FilterFactory.getHomeworkFilter()
                    .setUserType(userType)
                    .setUserId(userId);
            jsonObject = DaoFactory.getHomeworkDao().queryHomeworks(filter);
        } else if ("file".equals(moduleName)) {
            FileFilter filter = FilterFactory.getFileFilter()
                    .setUserId(userId)
                    .setUserType(userType);
            jsonObject = DaoFactory.getFileDao().queryFiles(filter);
        } else if ("user".equals(moduleName)) {
            UserFilter filter = FilterFactory.getUserFilter()
                    .setUserType(userType)
                    .setUserId(userId);
            jsonObject = DaoFactory.getUserDao().queryUsers(filter);
        } else {
            jsonObject = getErrorJsonObject("模块不存在！");
        }
        responseByJson(response, jsonObject);
    }

}
