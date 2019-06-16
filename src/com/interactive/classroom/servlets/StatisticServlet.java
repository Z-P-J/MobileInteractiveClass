package com.interactive.classroom.servlets;
/*
 * 增删改查看导印统功能的实现
 * 待完成：用MVC模式分开DB和Action操作
 */

import com.interactive.classroom.servlets.base.BaseHttpServlet;
import com.interactive.classroom.bean.StatisticBean;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.StatisticDao;
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
    /*----------线程需要的信息----------*/
    String sessionId = null;
    String filePathName = null;
    String fileName = null;
    String filePath = null;
    String fileUrl = null;
    String fileSize = "0";
    int percent = 0;
    boolean threadRunning = false;
    private ExportThread exportThread;
    /*----------------------------------*/
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

    @Override
    public void handleAction(HttpServletRequest request, HttpServletResponse response, String action) {
        switch (action) {
            case "statistic_record":
                try {
                    statisticRecord(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "get_statistic_record":
                try {
                    getStatisticRecord(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "export_statistic_resultset":
                try {
                    exportStatisticResultset(request, response);
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

    /*
     * 功能：根据前台页面的设置，统计对应的记录数据
     */
    public void statisticRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        String timeFrom = request.getParameter("time_from");
        String timeTo = request.getParameter("time_to");
        String timeInterval = request.getParameter("time_interval");
        String addressId = request.getParameter("address_id");
        String statisticType = request.getParameter("statistic_type");
        if (statisticType == null) {
            statisticType = "no";
        }
        debug("[statisticRecord]收到页面传过来的参数是：" + timeFrom + "," + timeTo + "," + timeInterval + ",statisticType=" + statisticType);

        StatisticDao statisticDao = DaoFactory.getStatisticDao();
        StatisticBean bean = new StatisticBean();
        bean.setTimeFrom(timeFrom);
        bean.setTimeTo(timeTo);
        bean.setUserId(userId);
        bean.setTimeInterval(timeInterval);
        bean.setAddressId(addressId);
        bean.setStatisticType(statisticType);
        bean.setTableName(request.getParameter("table_name"));
        /*---------------------------------把状态保存进session---------------------------*/
        if (timeFrom != null) {
            session.setAttribute(MODULE + "_" + SUB + "_statistic_query_time_from", timeFrom);
        }
        if (timeTo != null) {
            session.setAttribute(MODULE + "_" + SUB + "_statistic_query_time_to", timeTo);
        }
        if (timeInterval != null) {
            session.setAttribute(MODULE + "_" + SUB + "_statistic_query_timeinterval", timeInterval);
        }
        if (addressId != null) {
            session.setAttribute(MODULE + "_" + SUB + "_statistic_query_addressid", addressId);
        }
        /*---------------------------------开始进行统计---------------------------*/
        JSONObject jsonObj = statisticDao.statisticRecord(action, bean);
        jsonObj.put("user_id", userId);
        jsonObj.put("user_name", userName);
        jsonObj.put("result_image", fileName);

        session.setAttribute(MODULE + "_" + SUB + "_statistic_record_result", jsonObj);
        debug("[statisticRecord]统计完毕，保存进session：" + MODULE + "_" + SUB + "_statistic_record_result");

        onEnd(request, response, jsonObj, RESULT_PATH + "/statistic_result.jsp", "操作已经执行，请按返回按钮返回列表页面！", 0, "record_list.jsp");
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

    /*
     * 功能：导出已存在的结果集的数据
     */
    private void exportStatisticResultset(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dateTime = TimeUtil.currentDate();
        sessionId = session.getId();
        fileName = "zakk_statistic_export_" + sessionId + "_" + dateTime + ".xls";
        filePath = "C:\\upload\\project\\exportBean\\temp";
        filePathName = filePath + "\\" + fileName;
        fileUrl = "/upload/project/exportBean/temp/" + fileName;
        /*--------------------赋值完毕--------------------*/
        String action = request.getParameter("action");
        String existResultset = request.getParameter("exist_resultset");
        if ((existResultset == null) || (existResultset.equals("null") || existResultset.isEmpty())) {
            existResultset = "0";
        }
        String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        String userName = session.getAttribute("user_name") == null ? null : (String) session.getAttribute("user_name");
        int resultCode = 0;
        //检查输入参数是否正确先
        debug("收到页面传过来的参数是：" + existResultset + "," + action);
        String creator = (String) session.getAttribute("user_name");
        String createTime = TimeUtil.currentDate();
        JSONObject jsonObj = null;

        ExportBean exportBean = new ExportBean();
        exportBean.setSessionId(session.getId());
        exportBean.setCreateTime(createTime);
        exportBean.setCreator(creator);
        exportBean.setUserId(userId);
        exportBean.setUserName(userName);
        exportBean.setExportStatus("1");
        exportBean.setFileName(fileName);
        exportBean.setFilePath(filePath.replaceAll("\\\\", "/"));
        exportBean.setFileUrl(fileUrl);
        exportBean.setFileSize(fileSize);
        exportBean.setLimitTime(createTime);
        exportBean.setDownloadCount("0");
        exportBean.setExportPercent("0");
        exportBean.setExportType("1");
        processExportThread(request, response, exportBean);

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
            String resultMsg = "操作已经执行，请按返回按钮返回列表页面！";
            redirectUrl = SUB + "_list.jsp";
            resultUrl = RESULT_PATH + "/../../user/center/export_list.jsp";
            resultMsg = java.net.URLEncoder.encode(resultMsg, "UTF-8");
            String url = resultUrl + "?exist_resultset=1&result_msg=" + resultMsg + "&result_code=" + resultCode + "&redirect_url=" + redirectUrl;
            response.sendRedirect(url);
        }
    }

    /*
     * 功能：开启线程，进行导出
     */
    private void processExportThread(HttpServletRequest request, HttpServletResponse response, ExportBean exportBean) throws SQLException, IOException, ServletException, JSONException {
        String tempDir = filePath + "/" + session.getId() + "_" + TimeUtil.currentDate();
        java.io.File f = new java.io.File(tempDir);
        if (!f.exists()) {
            f.mkdir();
        }
        String zipFilename = "zakk_file_export_" + session.getId() + "_" + TimeUtil.currentDate() + ".rar";
        //首先找到原来的结果集
        JSONObject jsonObj = null;
        if (session.getAttribute(MODULE + "_" + SUB + "_statistic_record_result") != null) {
            jsonObj = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_statistic_record_result");
            debug("[getExportRecord]取出了原来的结果");
            jsonObj.put("result_code", 0);
            jsonObj.put("result_msg", "读取了上一次的查询配置");
        } else {
            jsonObj = new JSONObject();
            jsonObj.put("result_code", 10);
            jsonObj.put("result_msg", "session里没有找到之前统计的数据！");
            debug("[getExportRecord]没有结果");
        }
        //找到以后，进行开启线程导出

        if (exportThread != null) {
            debug("[processExportThread]发现exportThread不为null！");
        } else {
            exportThread = new ExportThread();
            exportThread.setResultset(jsonObj);
            exportThread.setExportBean(exportBean);
            exportThread.setTempDir(tempDir);
            exportThread.setZipFilename(zipFilename);
            exportThread.start();
        }
    }

    class ExportThread extends Thread {
        private JSONObject jsonObj = null;
        private ExportBean exportBean = null;
        String tempPath = null;
        String zipFilename = null;
        String module = null;

        @Override
        public void run() {
            if (!this.isInterrupted()) {// 线程未中断执行循环
                try {
                    threadExecute(module, jsonObj, exportBean, tempPath, zipFilename);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void setResultset(JSONObject jsonObj) {
            this.jsonObj = jsonObj;
        }

        public void setExportBean(ExportBean exportBean) {
            this.exportBean = exportBean;
        }

        void setTempDir(String tempDir) {
            this.tempPath = tempDir;
        }

        void setZipFilename(String zipFilename) {
            this.zipFilename = zipFilename;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }
    }

    private void threadExecute(String module, JSONObject jsonObj, ExportBean exportBean, String tempPath, String zipFilename) throws Exception {
        ExportDao exportDao = new ExportDao();
        threadRunning = true;
        exportDao.setExportBegin(exportBean);
        debug("threadExecute的线程开始了！");
        ExportUtil.exportData(jsonObj, "数据统计", filePathName);
        exportBean.setExportPercent("20");
        exportDao.setExportPercent(exportBean);
        threadRunning = false;
        debug("threadExecute的线程退出了！");
        //导出完毕后，就写数据库
        exportDao.setExportEnd(exportBean);
    }
}
