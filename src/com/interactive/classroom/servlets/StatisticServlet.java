package com.interactive.classroom.servlets;
/*
 * 增删改查看导印统功能的实现
 * 待完成：用MVC模式分开DB和Action操作
 */

import com.interactive.classroom.constant.ActionType;
import com.interactive.classroom.dao.*;
import com.interactive.classroom.dao.filters.*;
import com.interactive.classroom.servlets.base.BaseHttpServlet;
import com.interactive.classroom.bean.StatisticBean;
import com.interactive.classroom.utils.TimeUtil;
import com.interactive.classroom.utils.export.ExportBean;
import com.interactive.classroom.utils.export.ExportDao;
import com.interactive.classroom.utils.export.ExportUtil;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * @author Z-P-J
 */
public class StatisticServlet extends BaseHttpServlet {

    //这里是需要改的,module和sub
    private static final String MODULE = "base";
    private static final String SUB = "statistic";

    //    public String preFix = MODULE + "_" + SUB;
    private static final String RESULT_PATH = MODULE + "/" + SUB;
    private static final String RESULT_PAGE = "result.jsp";
    private String resultUrl = RESULT_PATH + "/" + RESULT_PAGE;
    private static final String REDIRECT_PATH = MODULE + "/" + SUB;
    private static final String REDIRECT_PAGE = "record_list.jsp";
    private String redirectUrl = REDIRECT_PATH + "/" + REDIRECT_PAGE;

    private String timeFrom;
    private String timeTo;
    private String timeInterval;


    @Override
    public void handleAction(HttpServletRequest request, HttpServletResponse response, String action) {
        if (ActionType.ACTION_START_STATISTIC.equals(action)) {
            try {
                startStatistic(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            switch (action) {
                case "get_statistic_record":
                    try {
                        getStatisticRecord(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    try {
                        processError(request, response, 2, "[" + MODULE + "/" + SUB + "/FileServlet]没有对应的action处理过程，请检查action是否正确！action=" + action, RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void startStatistic(HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException {
        timeFrom = request.getParameter("time_from");
        timeTo = request.getParameter("time_to");
        timeInterval = request.getParameter("time_interval");
        String addressId = request.getParameter("address_id");
        String tableName = request.getParameter("table_name");

        if (AttendanceDao.TABLE_NAME.equals(tableName)) {
            statisticAttendance(request, response);
        } else if (HomeworkDao.TABLE_NAME.equals(tableName)) {
            statisticHomework(request, response);
        } else if (UserDao.TABLE_NAME.equals(tableName)) {
            statisticUser(request, response);
        } else if (FileDao.TABLE_NAME.equals(tableName)){
            statisticFile(request, response);
        }

    }

    private void statisticAttendance(HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException {
        AttendanceFilter filter = FilterFactory.getAttendanceFilter()
                .setUserId(userId)
                .setUserType(userType)
                .setTimeInterval(timeInterval)
                .setTimeFrom(timeFrom)
                .setTimeTo(timeTo);

        JSONObject jsonObj = DaoFactory.getStatisticDao()
                .statisticRecord(filter.getStatisticSql(AttendanceDao.TABLE_NAME));
        responseByJson(response, jsonObj);
    }

    private void statisticHomework(HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException {
        HomeworkFilter filter = FilterFactory.getHomeworkFilter()
                .setUserId(userId)
                .setUserType(userType)
                .setTimeInterval(timeInterval)
                .setTimeFrom(timeFrom)
                .setTimeTo(timeTo);
        JSONObject jsonObj = DaoFactory.getStatisticDao()
                .statisticRecord(filter.getStatisticSql(HomeworkDao.TABLE_NAME));
        responseByJson(response, jsonObj);
    }

    private void statisticUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException {
        String timeFrom = request.getParameter("time_from");
        String timeTo = request.getParameter("time_to");
        String timeInterval = request.getParameter("time_interval");
        UserFilter filter = FilterFactory.getUserFilter()
                .setUserId(userId)
                .setUserType(userType)
                .setTimeInterval(timeInterval)
                .setTimeFrom(timeFrom)
                .setTimeTo(timeTo);
        JSONObject jsonObj = DaoFactory.getStatisticDao()
                .statisticRecord(filter.getStatisticSql(UserDao.TABLE_NAME));
        responseByJson(response, jsonObj);
    }

    private void statisticFile(HttpServletRequest request, HttpServletResponse response) throws SQLException, JSONException {
        FileFilter filter = FilterFactory.getFileFilter()
                .setUserId(userId)
                .setUserType(userType)
                .setTimeInterval(timeInterval)
                .setTimeFrom(timeFrom)
                .setTimeTo(timeTo);
        JSONObject jsonObj = DaoFactory.getStatisticDao()
                .statisticRecord(filter.getStatisticSql(FileDao.TABLE_NAME));
        responseByJson(response, jsonObj);
    }

    private void getStatisticRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String timeFrom = request.getParameter("time_from");
        String timeTo = request.getParameter("time_to");
        String timeInterval = request.getParameter("time_interval");
        String existResultset = request.getParameter("exist_resultset");
        if ((existResultset == null) || (existResultset.equals("null") || existResultset.isEmpty())) {
            existResultset = "0";
        }
        debug("getStatisticRecord收到页面传过来的参数是：" + existResultset + "," + timeFrom + "," + timeTo + "," + timeInterval);

        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (existResultset.equals("1")) {            //如果是新查询
            //如果有就取出来，如果没有就重新查询一次，并且保存进session里
            if (session.getAttribute(MODULE + "_" + SUB + "_statistic_record_result") != null) {
                jsonObj = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_statistic_record_result");
                debug("取出了原来的结果");
            } else {
                jsonObj = new JSONObject();
                jsonObj.put("result_code", 10);
                jsonObj.put("result_msg", "session里没有找到之前统计的数据！");
                debug("没有结果：" + MODULE + "_" + SUB + "_statistic_record_result");
            }
        } else {
            //这里无法进行新的统计
            debug("没有结果，而且不进行新的统计");
        }

        onEnd(request, response, jsonObj, resultUrl, "操作已经执行，请按返回按钮返回列表页面！", 0, "record_list.jsp");
    }

    public void getFlowStatisticQuerySetup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        String content = request.getParameter("content");
        String type = request.getParameter("type");
        String existResultset = request.getParameter("exist_resultset");
        String groupSelect = request.getParameter("group_select");
        String titleSearch = request.getParameter("title_search");
        String timeFrom = request.getParameter("time_from");
        String timeTo = request.getParameter("time_to");
        String status = request.getParameter("status_select");
        int statusSelect = -2;
        if (status != null) {
            statusSelect = Integer.parseInt(status);
        }
        if ((existResultset == null) || (existResultset.equals("null") || existResultset.isEmpty())) {
            existResultset = "0";
        }
        int resultCode = 0;
        //检查输入参数是否正确先
        debug("收到页面传过来的参数是：" + existResultset + "," + action + "," + type + "," + id + "," + groupSelect + "," + titleSearch + "," + timeFrom + "," + timeTo);
        //0,get_record,null,null,wlw,物联网,2016-12-01 00:00:00,2016-12-31 23:59:59
        JSONObject jsonObj = new JSONObject();
        /*----------------------------------------数据获取完毕，开始返回数值*/
        if (session.getAttribute("project_investigation_statistic_query_time_from") != null) {
            jsonObj.put("time_from", (String) session.getAttribute("project_core_statistic_query_time_from"));
        } else {
            resultCode = 1;
        }
        if (session.getAttribute("project_investigation_statistic_query_time_to") != null) {
            jsonObj.put("time_to", (String) session.getAttribute("project_core_statistic_query_time_to"));
        }
        if (session.getAttribute("project_investigation_statistic_query_timeinterval") != null) {
            jsonObj.put("time_interval", (String) session.getAttribute("project_core_statistic_query_timeinterval"));
        }
        if (session.getAttribute("project_investigation_statistic_query_addressid") != null) {
            jsonObj.put("address_id", (String) session.getAttribute("project_core_statistic_query_addressid"));
        }
        jsonObj.put("result_code", resultCode);
        jsonObj.put("result_msg", "读取了上一次的查询配置");
        debug(jsonObj.toString());
        boolean isAjax = true;
        if (request.getHeader("x-requested-with") == null) {
            isAjax = false;
        }    //判断是异步请求还是同步请求
        if (isAjax) {
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out;
            try {
                out = response.getWriter();
                out.print(jsonObj);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
            String resultMsg = "操作已经执行，请按返回按钮返回列表页面！";
            redirectUrl = SUB + "_list.jsp";
            resultUrl = RESULT_PATH + "/reward_list.jsp";
            resultMsg = java.net.URLEncoder.encode(resultMsg, "UTF-8");
            String url = resultUrl + "?exist_resultset=1&result_msg=" + resultMsg + "&result_code=" + resultCode + "&redirect_url=" + redirectUrl;
            response.sendRedirect(url);
        }
    }

}
