package user;
/*
 * 待完成：用MVC模式分开DB和Action操作
 * 增删改查看导印统功能的实现
 */

import base.BaseHttpServlet;
import org.json.JSONObject;
import user.bean.InfoBean;
import user.dao.InfoDao;
import utility.LogEvent;
import utility.ServletUtil;
import utility.TimeUtil;
import utility.export.ExportUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServletAction extends BaseHttpServlet {
    //这里是需要改的,module和sub
    private static final String MODULE = "user";
    private static final String SUB = "info";

//    public String preFix = MODULE + "_" + SUB;
    private static final String RESULT_PATH = MODULE + "/" + SUB;
    private static final String RESULT_PAGE = "result.jsp";
    private static final String RESULT_URL = RESULT_PATH + "/" + RESULT_PAGE;
    private static final String REDIRECT_PATH = MODULE + "/" + SUB;
    private static final String REDIRECT_PAGE = "record_list.jsp";
//    private static final String REDIRECT_URL = REDIRECT_PATH + "/" + REDIRECT_PAGE;
//    public String databaseName = "ylxdb";
//    public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        String action = request.getParameter("action");
        boolean actionOk = false;
        showDebug("processAction收到的action是：" + action);
        try {
            if (session.getAttribute("user_role") == null) {
                processError(request, response, 3, "session超时，请重新登录系统！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
            } else {
                if (action == null) {
                    processError(request, response, 1, "传递过来的action是null！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
                } else {
                    //这几个常规增删改查功能
                    switch (action) {
                        case "get_record":
                            getRecord(request, response);
                            break;
                        case "get_record_view":
                            getRecordView(request, response);
                            break;
                        case "add_record":
                            addRecord(request, response);
                            break;
                        case "modify_record":
                            modifyRecord(request, response);
                            break;
                        case "delete_record":
                            deleteRecord(request, response);
                            break;
                        case "set_record_top":
                            setRecordTop(request, response);
                            break;
                        case "export_record":
//                            exportRecord(request, response);
                            JSONObject jsonObj = ExportUtil.exportRecord(request, response, MODULE, SUB, "调查管理");
                            jsonObj.put("result_url", RESULT_URL);
                            onEnd(request, response, jsonObj);
                            break;
                        default:
                            processError(request, response, 2, "[" + MODULE + "/" + SUB + "/ServletAction]没有对应的action处理过程，请检查action是否正确！action=" + action, RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
                            break;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
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
     * 功能：查询记录
     */
    public void getRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String dbName = (String) session.getAttribute("unit_db_name");
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String type = request.getParameter("type");
        String timeFrom = request.getParameter("time_from");
        String timeTo = request.getParameter("time_to");
        String sortIndex = request.getParameter("sort_index");
        String orderBy = request.getParameter("order_by");
        String existResultset = request.getParameter("exist_resultset");
        if ((existResultset == null) || (existResultset.equals("null") || existResultset.isEmpty()))
            existResultset = "0";
        String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        String userName = session.getAttribute("user_name") == null ? null : (String) session.getAttribute("user_name");
        String userRole = session.getAttribute("user_role") == null ? null : (String) session.getAttribute("user_role");
        String userAvatar = session.getAttribute("user_avatar") == null ? null : (String) session.getAttribute("user_avatar");
        //这里可以修改成统一一个函数读取变量，下面的session里的attr可以用一个变量代替
        //检查输入参数是否正确先
        showDebug("[getRecord]收到页面传过来的参数是：" + existResultset + "," + action + "," + type + "," + id + "," + timeFrom + "," + timeTo);
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        String creator = (String) session.getAttribute("user_name");
        String createTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
        InfoBean query = new InfoBean();
        query.setId(id);
        query.setAction(action);
        query.setDbName(dbName);
        query.setType(type);
        query.setTitle(title);
        query.setContent(content);
        query.setTimeFrom(timeFrom);
        query.setTimeTo(timeTo);
        query.setUserId(userId);
        query.setSortIndex(sortIndex);
        query.setOrderBy(orderBy);

        JSONObject jsonObj = null;
        if (existResultset.equals("1")) {
            //要求提取之前查询结果，如果有就取出来，如果没有就重新查询一次，并且保存进session里
            if (session.getAttribute(MODULE + "_" + SUB + "_get_record_result") != null) {
                jsonObj = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
            } else {
                //如果没有就报错
                jsonObj = new JSONObject();
                jsonObj.put("result_code", 10);
                jsonObj.put("result_msg", "exist_resultset参数不当，服务器当前没有结果数据！请重新设置！");
            }
        } else {
            //如果是新查询
            InfoDao infoDao = new InfoDao();
            jsonObj = infoDao.getRecord(query);
            session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        }
        jsonObj.put("user_id", userId);
        jsonObj.put("user_name", userName);
        jsonObj.put("user_role", userRole);
        jsonObj.put("user_avatar", userAvatar);
        jsonObj.put("action", action);
        /*--------------------数据查询完毕，根据交互方式返回数据--------------------*/

        onEnd(request, response, jsonObj, REDIRECT_PATH + "/" + REDIRECT_PAGE + "?exist_resultset=1");
    }

    public void getRecordView(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String dbName = (String) session.getAttribute("unit_db_name");
        String id = request.getParameter("id");
        String index = request.getParameter("index");
        String existResultset = request.getParameter("exist_resultset");
        if ((existResultset == null) || (existResultset.equals("null") || existResultset.isEmpty()))
            existResultset = "0";
        String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        String userName = session.getAttribute("user_name") == null ? null : (String) session.getAttribute("user_name");
        //检查输入参数是否正确先
        showDebug("收到页面传过来的参数是：exist_resultset=" + existResultset + ",action=" + action + ",id=" + id + ",index=" + index);
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        InfoBean query = new InfoBean();
        query.setAction(action);
        query.setDbName(dbName);
        query.setUserId(userId);
        if (existResultset.equals("1")) {            //如果是新查询
            //如果有就取出来，如果没有就重新查询一次，并且保存进session里
            if (session.getAttribute(MODULE + "_" + SUB + "_get_record_result") != null) {
                JSONObject json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
                showDebug(json.toString());
                jsonObj = ServletUtil.getResultSetNavigateId(id, index, json);
                jsonObj.put("user_id", userId);
                jsonObj.put("user_name", userName);
                jsonObj.put("action", action);
                jsonObj.put("result_code", 0);
                jsonObj.put("result_msg", "ok");
                //然后还有导航信息
                json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
                //showDebug("[getRecordView]重新取出来的数据是："+json.toString());
            } else {
                //如果没有就重新查询一次
                showDebug("[getRecordView]没有就重新查询一次。");
                if (dbName != null) {
                    String creator = (String) session.getAttribute("user_name");
                    String createTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
                    InfoDao infoDao = new InfoDao();
                    jsonObj = infoDao.getRecord(query);
                    jsonObj.put("user_id", userId);
                    jsonObj.put("user_name", userName);
                    jsonObj.put("action", action);
                    jsonObj.put("result_code", 0);
                    jsonObj.put("result_msg", "ok");
                    session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
                }
            }
        } else {
            showDebug("[getRecordView]existsResult=0，重新查询");
            if (dbName != null) {
                String creator = (String) session.getAttribute("user_name");
                String createTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
                InfoDao infoDao = new InfoDao();
                jsonObj = infoDao.getRecord(query);
                jsonObj.put("user_id", userId);
                jsonObj.put("user_name", userName);
                jsonObj.put("action", action);
                session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
            }
        }

        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

    public void addRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String dbName = (String) session.getAttribute("unit_db_name");
        String userManageId = request.getParameter("userManage_id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String limitTime = request.getParameter("limit_time");
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (dbName != null) {
            String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
            String creator = (String) session.getAttribute("user_name");
            String createTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
            /*----------------------------------------数据获取完毕，开始和数据库交互*/
            InfoDao infoDao = new InfoDao();
            InfoBean info = new InfoBean();
            info.setAction(action);
            info.setDbName(dbName);
            info.setParentId(userManageId);
            info.setTitle(title);
            info.setContent(content);
            info.setLimitTime(limitTime);
            info.setUserId(userId);
            info.setCreator(creator);
            info.setCreateTime(createTime);
            jsonObj = infoDao.addRecord(info);
            ylxLog.log("用户 " + creator + " 于 " + createTime + " 添加了 [" + MODULE + "][" + SUB + "] 记录", "添加记录", MODULE);
        }

        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

    /*
     * 功能：修改记录
     */
    public void modifyRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String dbName = (String) session.getAttribute("unit_db_name");
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String limitTime = request.getParameter("limit_time");
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (id != null && dbName != null) {
            String creator = (String) session.getAttribute("user_name");
            String createTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
            InfoDao infoDao = new InfoDao();
            InfoBean info = new InfoBean();
            info.setId(id);
            info.setDbName(dbName);
            info.setTitle(title);
            info.setContent(content);
            info.setLimitTime(limitTime);
            info.setCreator(creator);
            info.setCreateTime(createTime);
            jsonObj = infoDao.modifyRecord(info);
            ylxLog.log("用户 " + creator + " 于 " + createTime + " 修改了 [" + MODULE + "][" + SUB + "] 记录", "修改记录", MODULE);
        }

        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

    public void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String dbName = (String) session.getAttribute("unit_db_name");
        String[] ids = request.getParameterValues("id");

        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (ids != null && dbName != null) {
            String creator = (String) session.getAttribute("user_name");
            String createTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
            /*----------------------------------------数据获取完毕，开始和数据库交互*/
            InfoDao infoDao = new InfoDao();
            jsonObj = infoDao.deleteRecord(action, dbName, ids, creator, createTime);
            ylxLog.log("用户 " + creator + " 于 " + createTime + " 删除了 [" + MODULE + "][" + SUB + "] 记录", "删除记录", MODULE);
        }

        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

    public void setRecordTop(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String dbName = (String) session.getAttribute("unit_db_name");
        String id = request.getParameter("id");
        String content = request.getParameter("content");
        String type = request.getParameter("type");
        String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        String userName = session.getAttribute("user_name") == null ? null : (String) session.getAttribute("user_name");

        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (dbName != null) {
            String creator = (String) session.getAttribute("user_name");
            String createTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
            InfoDao infoDao = new InfoDao();
            jsonObj = infoDao.setRecordTop(action, dbName, type, userId, id);
            jsonObj.put("user_id", userId);
            jsonObj.put("user_name", userName);
        }

        session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

}
