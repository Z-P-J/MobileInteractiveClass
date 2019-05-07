package com.interactive.classroom.statistic;
/*
 * 增删改查看导印统功能的实现
 * 待完成：用MVC模式分开DB和Action操作
 */

import com.interactive.classroom.base.BaseHttpServlet;
import org.json.JSONException;
import org.json.JSONObject;
import com.interactive.classroom.statistic.bean.StatisticBean;
import com.interactive.classroom.statistic.dao.StatisticDao;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.LogEvent;
import com.interactive.classroom.utils.TimeUtil;
import com.interactive.classroom.utils.export.ExportBean;
import com.interactive.classroom.utils.export.ExportDao;
import com.interactive.classroom.utils.export.ExportUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * @author 25714
 */
public class ServletAction extends BaseHttpServlet {
    /*----------线程需要的信息----------*/
    public HttpSession session = null;
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
//    public String databaseName = "my_test";
    public LogEvent ylxLog = new LogEvent();

    /*
     * 处理顺序：先是service，后根据情况doGet或者doPost
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processAction(request, response);
    }

    public void processAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        try {
            ylxLog.setSession(session);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (session.getAttribute("user_role") == null) {
            try {
                processError(request, response, 3, "session超时，请重新登录系统！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            String action = request.getParameter("action");
            Log.d(getClass().getName(), "processAction收到的action是：" + action);
            if (action == null) {
                try {
                    processError(request, response, 1, "传递过来的action是null！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
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
                            processError(request, response, 2, "[" + MODULE + "/" + SUB + "/ServletAction]没有对应的action处理过程，请检查action是否正确！action=" + action, RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }
    }

    /*
     * 功能：进行一个本类测试，不用启动整个项目，测试所写的Java
     */
    public static void main(String[] args) throws Exception {
        System.out.println("");
    }

    public void showDebug(String msg) {
        System.out.println("[" + TimeUtil.currentDate() + "][" + MODULE + "/" + SUB + "/ServletAction]" + msg);
    }

    /*
     * 功能：根据前台页面的设置，统计对应的记录数据
     */
    public void statisticRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String type = request.getParameter("type");
        String timeFrom = request.getParameter("time_from");
        String timeTo = request.getParameter("time_to");
        String timeInterval = request.getParameter("time_interval");
        String addressId = request.getParameter("address_id");
        String statisticType = request.getParameter("statistic_type");
        if (statisticType == null) {
            statisticType = "no";
        }
        showDebug("[statisticRecord]收到页面传过来的参数是：" + timeFrom + "," + timeTo + "," + timeInterval + ",statisticType=" + statisticType);
        String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        String userName = session.getAttribute("user_name") == null ? null : (String) session.getAttribute("user_name");

        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        StatisticDao statisticDao = new StatisticDao();
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
        //获取完毕，开始生成统计图
        //HttpSession session,String dbName,JSONObject json,String title,String column,int statisticImage,int chartWidth,int chartHeight
//        String fileName = getStatisticGraph(session, jsonObj, "待办事项统计图", "测试Column", statisticType, 1, 1280, 768);
        jsonObj.put("result_image", fileName);

        session.setAttribute(MODULE + "_" + SUB + "_statistic_record_result", jsonObj);
        showDebug("[statisticRecord]统计完毕，保存进session：" + MODULE + "_" + SUB + "_statistic_record_result");

        onEnd(request, response, jsonObj, RESULT_PATH + "/statistic_result.jsp", "操作已经执行，请按返回按钮返回列表页面！", 0, "record_list.jsp");
    }

//    private String getStatisticGraph(HttpSession session, JSONObject json, String title, String column, String statisticType, int statisticImage, int chartWidth, int chartHeight) throws SQLException, IOException, JSONException {
//        //要改动的
//        String chartTitle = "统计图";
//        String tmpDir = "/chart";
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        DefaultPieDataset pieDataSet = new DefaultPieDataset();
//        showDebug("statisticImage的值是：" + statisticImage + ",column=" + column + ",title=" + title);
//        JSONArray arr = json.getJSONArray("aaData");
//        showDebug(arr.toString());
//        for (int i = 0; i < arr.length(); i++) {
//            String timeInterval = (String) ((List) (arr.get(i))).get(1);
//            Integer count = Integer.parseInt((String) ((List) (arr.get(i))).get(2));
//            String countName = "";
//            if ("no".equals(statisticType)) {
//                countName = "所有车辆";
//            } else {
//                String colorId = (String) ((List) (arr.get(i))).get(3);
//                countName = (String) ((List) (arr.get(i))).get(4);
//            }
//            dataset.addValue(count, countName, timeInterval);
//            //pieDataSet.setValue(json.getString("colTime"),json.getInt("colCount"));
//        }
////        JFreeChart chart = null;
////        if (statisticImage == 1) {
////            chart = ChartFactory.createBarChart3D(chartTitle, title, "数量", dataset, PlotOrientation.VERTICAL, true, false, false);
////        } else if (statisticImage == 2) {
////            chart = ChartFactory.createLineChart(chartTitle, title, "数量", dataset, PlotOrientation.VERTICAL, true, false, false);
////        } else if (statisticImage == 3) {
////            chart = ChartFactory.createPieChart(chartTitle, pieDataSet, true, false, false);
////        }
//        String chartFilename = "";//ServletUtilities.saveChartAsJPEG(chart, chartWidth, chartHeight, null, session);
//        chartFilename = tmpDir + "/" + chartFilename;
//        return chartFilename;
//    }

    private void getStatisticRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String dbName = (String) session.getAttribute("unit_db_name");
        String type = request.getParameter("type");
        String timeFrom = request.getParameter("time_from");
        String timeTo = request.getParameter("time_to");
        String timeInterval = request.getParameter("time_interval");
        String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        String userName = session.getAttribute("user_name") == null ? null : (String) session.getAttribute("user_name");
        String existResultset = request.getParameter("exist_resultset");
        if ((existResultset == null) || (existResultset.equals("null") || existResultset.isEmpty())) {
            existResultset = "0";
        }
        showDebug("getStatisticRecord收到页面传过来的参数是：" + existResultset + "," + timeFrom + "," + timeTo + "," + timeInterval);

        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (existResultset.equals("1")) {            //如果是新查询
            //如果有就取出来，如果没有就重新查询一次，并且保存进session里
            if (session.getAttribute(MODULE + "_" + SUB + "_statistic_record_result") != null) {
                jsonObj = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_statistic_record_result");
                showDebug("取出了原来的结果");
            } else {
                jsonObj = new JSONObject();
                jsonObj.put("result_code", 10);
                jsonObj.put("result_msg", "session里没有找到之前统计的数据！");
                showDebug("没有结果：" + MODULE + "_" + SUB + "_statistic_record_result");
            }
        } else {
            //这里无法进行新的统计
            showDebug("没有结果，而且不进行新的统计");
        }

        onEnd(request, response, jsonObj, resultUrl, "操作已经执行，请按返回按钮返回列表页面！", 0, "record_list.jsp");
    }

    public void getFlowStatisticQuerySetup(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String dbName = (String) session.getAttribute("unit_db_name");
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
        if (status != null)
            statusSelect = Integer.parseInt(status);
        if ((existResultset == null) || (existResultset.equals("null") || existResultset.isEmpty()))
            existResultset = "0";
        String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        String userName = session.getAttribute("user_name") == null ? null : (String) session.getAttribute("user_name");
        int resultCode = 0;
        //检查输入参数是否正确先
        showDebug("收到页面传过来的参数是：" + existResultset + "," + action + "," + type + "," + id + "," + groupSelect + "," + titleSearch + "," + timeFrom + "," + timeTo);
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
        showDebug(jsonObj.toString());
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
        HttpSession session = request.getSession();
        /*--------------------为线程里用的变量赋值--------------------*/
        String dateTime = TimeUtil.currentDate();
        sessionId = session.getId();
        fileName = "zakk_statistic_export_" + sessionId + "_" + dateTime + ".xls";
        filePath = "C:\\upload\\project\\exportBean\\temp";
        filePathName = filePath + "\\" + fileName;
        fileUrl = "/upload/project/exportBean/temp/" + fileName;
        /*--------------------赋值完毕--------------------*/
        String dbName = (String) session.getAttribute("unit_db_name");
        String action = request.getParameter("action");
        String existResultset = request.getParameter("exist_resultset");
        if ((existResultset == null) || (existResultset.equals("null") || existResultset.isEmpty()))
            existResultset = "0";
        String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        String userName = session.getAttribute("user_name") == null ? null : (String) session.getAttribute("user_name");
        int resultCode = 0;
        //检查输入参数是否正确先
        showDebug("收到页面传过来的参数是：" + existResultset + "," + action);
        String creator = (String) session.getAttribute("user_name");
        String createTime = TimeUtil.currentDate();
        JSONObject jsonObj = null;
        String description = "对导出的描述";
        /*----------------------------------------数据获取完毕，开始返回数值*/
        //如果是新的下载，就执行，否则检查有没有线程在运行，如果有就进入，如果没有就新下载
        /*----------------------------------------构造返回数值*/
        ExportDao exportDao = new ExportDao();
        ExportBean exportBean = new ExportBean();
        exportBean.setSessionId(session.getId());
        exportBean.setCreateTime(createTime);
        exportBean.setCreator(creator);
        exportBean.setUserId(userId);
        exportBean.setUserName(userName);
        exportBean.setDbName(dbName);
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
        HttpSession session = request.getSession();
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
            showDebug("[getExportRecord]取出了原来的结果");
            jsonObj.put("result_code", 0);
            jsonObj.put("result_msg", "读取了上一次的查询配置");
        } else {
            jsonObj = new JSONObject();
            jsonObj.put("result_code", 10);
            jsonObj.put("result_msg", "session里没有找到之前统计的数据！");
            showDebug("[getExportRecord]没有结果");
        }
        //找到以后，进行开启线程导出

        if (exportThread != null) {
            showDebug("[processExportThread]发现exportThread不为null！");
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
        showDebug("threadExecute的线程开始了！");
        ExportUtil.exportData(jsonObj, "数据统计", filePathName);
        exportBean.setExportPercent("20");
        exportDao.setExportPercent(exportBean);
        threadRunning = false;
        showDebug("threadExecute的线程退出了！");
        //导出完毕后，就写数据库
        exportDao.setExportEnd(exportBean);
    }
}
