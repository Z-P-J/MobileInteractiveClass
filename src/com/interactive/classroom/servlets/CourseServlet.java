package com.interactive.classroom.servlets;

import com.interactive.classroom.bean.CourseBean;
import com.interactive.classroom.constant.ActionType;
import com.interactive.classroom.constant.UserType;
import com.interactive.classroom.dao.CourseDao;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.HomeworkDao;
import com.interactive.classroom.dao.filters.CourseFilter;
import com.interactive.classroom.dao.filters.FilterFactory;
import com.interactive.classroom.dao.filters.HomeworkFilter;
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

/**
 * @author Z-P-J
 */
public class CourseServlet extends BaseHttpServlet {

    /**
     * 模块名
     */
    private static final String MODULE = "course";

    @Override
    protected void handleAction(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        try {
            if (ActionType.ACTION_QUERY_COURSES.equals(action)) {
                queryCourses(request, response);
            } else if (ActionType.ACTION_DELETE_COURSE.equals(action)) {
                deleteCourse(request, response);
            } else if (ActionType.ACTION_UPDATE_COURSE.equals(action)) {
                updateCourse(request, response);
            } else if (ActionType.ACTION_ADD_COURSE.equals(action)) {
                addCourse(request, response);
            } else if (ActionType.ACTION_EXPORT_COURSES.equals(action)) {
                exportCourses(response);
            } else if (ActionType.ACTION_JOIN_COURSE.equals(action)) {
                joinCourse(request, response);
            } else if (ActionType.ACTION_EXIT_COURSE.equals(action)) {
                exitCourse(request, response);
            } else {
                onError(response, "没有对应的action处理过程，请检查action是否正确！action=" + action);
            }
        } catch (JSONException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询课程
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    private void queryCourses(HttpServletRequest request, HttpServletResponse response) throws JSONException, SQLException {
        CourseFilter filter = FilterFactory.getCourseFilter()
                .setUserId(userId)
                .setUserType(userType)
                .setKeyword(request.getParameter("keyword"))
                .setJoined(request.getParameter("joined"));
        JSONObject jsonObject = DaoFactory.getCourseDao().queryCourses(filter);
        jsonObject.put("name", userName);
        responseByJson(response, jsonObject);
    }

    /**
     * 删除课程
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) throws JSONException, SQLException {
        String id = request.getParameter("id");
        JSONObject jsonObject = DaoFactory.getCourseDao().deleteCourse(id);
        responseByJson(response, jsonObject);
    }

    /**
     * 更新课程信息
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws JSONException, SQLException {
        String id = request.getParameter("id");
        String courseName = request.getParameter("course_name");
        String teacherName = request.getParameter("teacher_name");
        CourseBean bean = new CourseBean();
        bean.setId(id);
        bean.setCourseName(courseName);
        bean.setTeacherName(teacherName);
        JSONObject jsonObject = DaoFactory.getCourseDao().updateCourse(bean);
        responseByJson(response, jsonObject);
    }

    /**
     * 老师添加课程
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    private void addCourse(HttpServletRequest request, HttpServletResponse response) throws JSONException, SQLException {
        String courseName = request.getParameter("course_name");
        String teacherName = request.getParameter("teacher_name");
        CourseBean bean = new CourseBean();
        bean.setCourseName(courseName);
        bean.setTeacherName(teacherName);
        bean.setAttendanceCount("0");
        bean.setTeacherId(userId);
        JSONObject jsonObject = DaoFactory.getCourseDao().addCourse(bean);
        responseByJson(response, jsonObject);
    }

    /**
     * 学生加入课程
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    private void joinCourse(HttpServletRequest request, HttpServletResponse response) throws JSONException, SQLException {
        String courseId = request.getParameter("course_id");
        JSONObject jsonObject = DaoFactory.getCourseDao().joinCourse(userId, courseId);
        responseByJson(response, jsonObject);
    }

    /**
     * 学生退出课程
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    private void exitCourse(HttpServletRequest request, HttpServletResponse response) throws JSONException, SQLException {
        String courseId = request.getParameter("course_id");
        JSONObject jsonObject = DaoFactory.getCourseDao().exitCourse(userId, courseId);
        responseByJson(response, jsonObject);
    }

    /**
     * 导出课程
     * @param response HttpServletResponse
     * @throws JSONException JSONException
     * @throws SQLException SQLException
     */
    private void exportCourses(HttpServletResponse response) throws JSONException, SQLException {
        CourseFilter filter = FilterFactory.getCourseFilter()
                .setUserId(userId)
                .setUserType(userType);
        JSONObject jsonObj = DaoFactory.getCourseDao().queryCourses(filter);

        ExportUtil.with(jsonObj)
                .setModuleName(MODULE)
                .setTableNickName("课程管理")
                .setLabels(CourseDao.LABELS)
                .setLabelsZh(CourseDao.LABELS_CH)
                .export();

        responseByJson(response, jsonObj);
    }

}
