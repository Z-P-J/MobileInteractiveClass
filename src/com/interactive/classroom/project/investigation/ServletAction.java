//package com.interactive.classroom.project.investigation;
///*
// * 待完成：用MVC模式分开DB和Action操作
// * 增删改查看导印统功能的实现
// */
//
//import com.interactive.classroom.base.BaseHttpServlet;
//import com.interactive.classroom.utils.*;
//import org.json.JSONException;
//import org.json.JSONObject;
//import com.interactive.classroom.utils.export.ExportUtil;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
///**
// * @author Z-P-J
// */
//public class ServletAction extends BaseHttpServlet {
//    //这里是需要改的,module和sub
//    private static final String MODULE = "project";
//    private static final String SUB = "investigation";
//
////    private static final String PRE_FIX = MODULE + "_" + SUB;
//    private static final String RESULT_PATH = MODULE + "/" + SUB;
//    private static final String RESULT_PAGE = "result.jsp";
//    private static final String RESULT_URL = RESULT_PATH + "/" + RESULT_PAGE;
//    private static final String REDIRECT_PATH = MODULE + "/" + SUB;
//    private static final String REDIRECT_PAGE = "record_list.jsp";
////    private static final String DATABASE_NAME = "my_test";
//
//    private LogEvent ylxLog = new LogEvent();
//
//    /*
//     * 处理顺序：先是service，后根据情况doGet或者doPost
//     */
//    @Override
//    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        processAction(request, response);
//    }
//
//    private void processAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        HttpSession session = request.getSession();
//        request.setCharacterEncoding("UTF-8");
//        try {
//            ylxLog.setSession(session);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String action = request.getParameter("action");
//        Log.d(getClass().getName(), "processAction收到的action是：" + action);
//
//        if (session.getAttribute("user_role") == null) {
//            try {
//                processError(request, response, 3, "session超时，请重新登录系统！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
//            try {
//                if (action == null) {
//                    processError(request, response, 1, "传递过来的action是null！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
//                } else {
//                    //这几个常规增删改查功能
//                    switch (action) {
//                        case "get_record":
//                            getRecord(request, response);
//                            break;
//                        case "get_record_view":
//                            getRecordView(request, response);
//                            break;
//                        case "add_record":
//                            addRecord(request, response);
//                            break;
//                        case "modify_record":
//                            modifyRecord(request, response);
//                            break;
//                        case "delete_record":
//                            deleteRecord(request, response);
//                            break;
//                        case "export_record":
//                            JSONObject jsonObj = ExportUtil.exportRecord(request, response, MODULE, SUB, "调查管理");
//                            jsonObj.put("result_url", RESULT_URL);
//                            onEnd(request, response, jsonObj);
//                            break;
//                        default:
//                            processError(request, response, 2, "[" + MODULE + "/" + SUB + "/ServletAction]没有对应的action处理过程，请检查action是否正确！action=" + action, RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
//                            break;
//                    }
//                }
//            } catch (Throwable e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    /*
//     * 功能：查询记录
//     */
//    private void getRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        HttpSession session = request.getSession();
//        initUserInfo(session);
//        InvestigationBean bean = new InvestigationBean();
//        bean.setId(request.getParameter("id"));
//        bean.setAction(request.getParameter("action"));
//        bean.setDbName(DBHelper.DATEBASE_NAME);
//        bean.setType(request.getParameter("type"));
//        bean.setTitle(request.getParameter("title"));
//        bean.setContent(request.getParameter("content"));
//        bean.setTimeFrom(request.getParameter("time_from"));
//        bean.setTimeTo(request.getParameter("time_to"));
//        bean.setUserId(userId);
//        bean.setUserRole(userRole);
//        bean.setSortIndex(request.getParameter("sort_index"));
//        bean.setOrderBy(request.getParameter("order_by"));
//        Log.d(getClass().getName(), "FileBean=" + bean.toString());
//
//        String existResultset = request.getParameter("exist_resultset");
//        if ((existResultset == null) || ("null".equals(existResultset) || existResultset.isEmpty())) {
//            existResultset = "0";
//        }
//        JSONObject jsonObj;
//        if ("1".equals(existResultset)) {
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
//            InvestigationDao dao = new InvestigationDao();
//            jsonObj = dao.getRecord(bean);
//            session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
//        }
//        jsonObj.put("user_id", userId);
//        jsonObj.put("user_name", userName);
//        jsonObj.put("user_role", userRole);
//        jsonObj.put("user_avatar", userAvatar);
//        jsonObj.put("action", bean.getAction());
//        /*--------------------数据查询完毕，根据交互方式返回数据--------------------*/
//        String url = REDIRECT_PATH + "/" + REDIRECT_PAGE + "?exist_resultset=1";
//        onEnd(request, response, jsonObj, url);
//    }
//
//    private void getRecordView(HttpServletRequest request, HttpServletResponse response) throws Throwable {
//        HttpSession session = request.getSession();
//        String id = request.getParameter("id");
//        String index = request.getParameter("index");
//
//        String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
//        String userName = session.getAttribute("user_name") == null ? null : (String) session.getAttribute("user_name");
//
//        JSONObject jsonObj = null;
//        InvestigationBean bean = new InvestigationBean();
//        bean.setAction(request.getParameter("action"));
//        bean.setUserId(userId);
//        Log.d(getClass().getName(), "getRecordView bean=" + bean.toString());
//
//        String existResultset = request.getParameter("exist_resultset");
//        if ((existResultset == null) || ("null".equals(existResultset) || existResultset.isEmpty())) {
//            existResultset = "0";
//        }
//
//        //如果是新查询
//        if ("1".equals(existResultset)) {
//            //如果有就取出来，如果没有就重新查询一次，并且保存进session里
//            if (session.getAttribute(MODULE + "_" + SUB + "_get_record_result") != null) {
//                JSONObject json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
//                Log.d(getClass().getName(), json.toString());
//                jsonObj = ServletUtil.getResultSetNavigateId(id, index, json);
//                jsonObj.put("user_id", userId);
//                jsonObj.put("user_name", userName);
//                jsonObj.put("action", bean.getAction());
//                jsonObj.put("result_code", 0);
//                jsonObj.put("result_msg", "ok");
//                //然后还有导航信息
//                json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
//                Log.d(getClass().getName(), "[getRecordView]重新取出来的数据是："+json.toString());
//            } else {
//                //如果没有就重新查询一次
//                Log.d(getClass().getName(), "[getRecordView]没有就重新查询一次。");
//                if (bean.getDbName() != null) {
//                    InvestigationDao dao = new InvestigationDao();
//                    jsonObj = dao.getRecord(bean);
//                    jsonObj.put("user_id", userId);
//                    jsonObj.put("user_name", userName);
//                    jsonObj.put("action", bean.getAction());
//                    jsonObj.put("result_code", 0);
//                    jsonObj.put("result_msg", "ok");
//                    session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
//                }
//            }
//        } else {
//            Log.d(getClass().getName(), "[getRecordView]existsResult=0，重新查询");
//            if (bean.getDbName() != null) {
//                InvestigationDao dao = new InvestigationDao();
//                jsonObj = dao.getRecord(bean);
//                jsonObj.put("user_id", userId);
//                jsonObj.put("user_name", userName);
//                jsonObj.put("action", bean.getAction());
//                session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
//            }
//        }
//        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
//    }
//
//    private void addRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        HttpSession session = request.getSession();
//        /*----------------------------------------数据获取完毕，开始和数据库交互*/
//        String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
//        String creator = (String) session.getAttribute("user_name");
//        String createTime = TimeUtil.currentDate();
//        /*----------------------------------------数据获取完毕，开始和数据库交互*/
//        InvestigationDao dao = new InvestigationDao();
//        InvestigationBean bean = new InvestigationBean();
//        bean.setDbName(DBHelper.DATEBASE_NAME);
//        bean.setAction(request.getParameter("action"));
//        bean.setParentId(request.getParameter("project_id"));
//        bean.setTitle(request.getParameter("title"));
//        bean.setLink(request.getParameter("link"));
//        bean.setContent(request.getParameter("content"));
//        bean.setEndTime(request.getParameter("limit_time"));
//        bean.setUserId(userId);
//        bean.setCreator(creator);
//        bean.setCreateTime(createTime);
//        JSONObject jsonObj = dao.addRecord(bean);
//        ylxLog.log("用户 " + creator + " 于 " + createTime + " 添加了 [" + MODULE + "][" + SUB + "] 记录", "添加记录", MODULE);
//        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
//    }
//
//    /*
//     * 功能：修改记录
//     */
//    private void modifyRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        HttpSession session = request.getSession();
//        String id = request.getParameter("id");
//        /*----------------------------------------数据获取完毕，开始和数据库交互*/
//        JSONObject jsonObj = null;
//        //检查输入参数是否正确先
//        if (id != null) {
//            String creator = (String) session.getAttribute("user_name");
//            String createTime = TimeUtil.currentDate();
//            InvestigationDao dao = new InvestigationDao();
//            InvestigationBean bean = new InvestigationBean();
//            bean.setId(id);
//            bean.setDbName(DBHelper.DATEBASE_NAME);
//            bean.setTitle(request.getParameter("title"));
//            bean.setContent(request.getParameter("content"));
//            bean.setEndTime(request.getParameter("limit_time"));
//            bean.setCreator(creator);
//            bean.setCreateTime(createTime);
//            jsonObj = dao.modifyRecord(bean);
//            ylxLog.log("用户 " + creator + " 于 " + createTime + " 修改了 [" + MODULE + "][" + SUB + "] 记录", "修改记录", MODULE);
//        }
//
//
//        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
//    }
//
//    private void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        HttpSession session = request.getSession();
//        String action = request.getParameter("action");
//        String[] ids = request.getParameterValues("id");
//
//        /*----------------------------------------数据获取完毕，开始和数据库交互*/
//        JSONObject jsonObj = null;
//        //检查输入参数是否正确先
//        if (ids != null) {
//            String creator = (String) session.getAttribute("user_name");
//            String createTime = TimeUtil.currentDate();
//            /*----------------------------------------数据获取完毕，开始和数据库交互*/
//            InvestigationDao dao = new InvestigationDao();
//            jsonObj = dao.deleteRecord(action, ids, creator, createTime);
//            ylxLog.log("用户 " + creator + " 于 " + createTime + " 删除了 [" + MODULE + "][" + SUB + "] 记录", "删除记录", MODULE);
//        }
//
//        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
//    }
//
//}
