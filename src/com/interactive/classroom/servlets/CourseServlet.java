package com.interactive.classroom.servlets;

import com.interactive.classroom.constant.ActionType;
import com.interactive.classroom.constant.UserType;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.filters.CourseFilter;
import com.interactive.classroom.dao.filters.FilterFactory;
import com.interactive.classroom.servlets.base.BaseHttpServlet;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.export.ExportUtil;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class CourseServlet extends BaseHttpServlet {

    @Override
    protected void handleAction(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {

        try {
            if (ActionType.ACTION_GET_COURSES.equals(action)) {
                getCourses(request, response);
            } else {
                onError(response, "没有对应的action处理过程，请检查action是否正确！action=" + action);
//                processError(request, response, 2, "[" + MODULE + "/" + SUB + "/FileServlet]没有对应的action处理过程，请检查action是否正确！action=" + action, RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
            }
        } catch (JSONException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void getCourses(HttpServletRequest request, HttpServletResponse response) throws JSONException, SQLException {
//        String id = request.getParameter("id");
//        Log.d(getClass().getName(), "getCourses id=" + id);
        CourseFilter filter = FilterFactory.getCourseFilter()
                .setUserId(userId)
                .setUserType(userType);
        if (UserType.STUDENT.equals(userType)) {
            filter.setStudentId(userId);
        }
        JSONObject jsonObject = DaoFactory.getCourseDao().queryCourses(filter);
        responseByJson(response, jsonObject);
    }

}
