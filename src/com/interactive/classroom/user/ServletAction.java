package com.interactive.classroom.user;
/*
 * 待完成：用MVC模式分开DB和Action操作
 * 增删改查看导印统功能的实现
 */

import com.interactive.classroom.base.BaseHttpServlet;
import com.interactive.classroom.bean.UserBean;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.UserDao;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.ServletUtil;
import com.interactive.classroom.utils.TimeUtil;
import com.interactive.classroom.utils.export.ExportUtil;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;

public class ServletAction extends BaseHttpServlet {

    private static final String TAG = "";

    //这里是需要改的,module和sub
    private static final String MODULE = "user";
    private static final String SUB = "info";

    //    public String preFix = MODULE + "_" + SUB;
    private static final String RESULT_PATH = MODULE + "/" + SUB;
    private static final String RESULT_PAGE = "result.jsp";
    private static final String RESULT_URL = RESULT_PATH + "/" + RESULT_PAGE;
    private static final String REDIRECT_PATH = MODULE + "/" + SUB;
    private static final String REDIRECT_PAGE = "record_list.jsp";

    @Override
    protected void handleAction(HttpServletRequest request, HttpServletResponse response, String action) {
        try {
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
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    /*
     * 功能：查询记录
     */
    public void getRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        debug("[getRecord]收到页面传过来的参数是：" + existResultset + "," + action + "," + type + "," + id + "," + timeFrom + "," + timeTo);

        UserBean query = new UserBean();
        query.setId(id);
        query.setAction(action);
        query.setType(type);
        query.setTitle(title);
        query.setContent(content);
        query.setTimeFrom(timeFrom);
        query.setTimeTo(timeTo);
        query.setUserId(userId);
        query.setSortIndex(sortIndex);
        query.setOrderBy(orderBy);

        JSONObject jsonObj;
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
            com.interactive.classroom.dao.UserDao infoDao = DaoFactory.getUserDao();
            jsonObj = infoDao.getRecord(query);
//            String sql = "select * from ";
            session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        }
        jsonObj.put("user_id", userId);
        jsonObj.put("user_name", userName);
        jsonObj.put("user_role", userType);
        jsonObj.put("user_avatar", userAvatar);
        jsonObj.put("action", action);
        /*--------------------数据查询完毕，根据交互方式返回数据--------------------*/

        onEnd(request, response, jsonObj, REDIRECT_PATH + "/" + REDIRECT_PAGE + "?exist_resultset=1");
    }

    public void getRecordView(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String index = request.getParameter("index");
        String existResultset = request.getParameter("exist_resultset");
        if ((existResultset == null) || (existResultset.equals("null") || existResultset.isEmpty()))
            existResultset = "0";

        debug("收到页面传过来的参数是：exist_resultset=" + existResultset + ",action=" + action + ",id=" + id + ",index=" + index);

        JSONObject jsonObj = null;
        UserBean query = new UserBean();
        query.setAction(action);
        query.setUserId(userId);
        if (existResultset.equals("1")) {            //如果是新查询
            //如果有就取出来，如果没有就重新查询一次，并且保存进session里
            if (session.getAttribute(MODULE + "_" + SUB + "_get_record_result") != null) {
                JSONObject json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
                debug(json.toString());
                jsonObj = ServletUtil.getResultSetNavigateId(id, index, json);
                jsonObj.put("user_id", userId);
                jsonObj.put("user_name", userName);
                jsonObj.put("action", action);
                jsonObj.put("result_code", 0);
                jsonObj.put("result_msg", "ok");
                //然后还有导航信息
                json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
                debug("[getRecordView]重新取出来的数据是：" + json.toString());
            } else {
                //如果没有就重新查询一次
                debug("[getRecordView]没有就重新查询一次。");
                UserDao dao = DaoFactory.getUserDao();
                jsonObj = dao.getRecord(query);
                jsonObj.put("user_id", userId);
                jsonObj.put("user_name", userName);
                jsonObj.put("action", action);
                jsonObj.put("result_code", 0);
                jsonObj.put("result_msg", "ok");
                session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
            }
        } else {
            debug("[getRecordView]existsResult=0，重新查询");
            UserDao infoDao = DaoFactory.getUserDao();
            jsonObj = infoDao.getRecord(query);
            jsonObj.put("user_id", userId);
            jsonObj.put("user_name", userName);
            jsonObj.put("action", action);
            session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        }

        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

    //管理员才调用该方法
    public void addRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        String userManageId = request.getParameter("userManage_id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String limitTime = request.getParameter("limit_time");
        String createTime = TimeUtil.currentDate();

        UserDao infoDao = DaoFactory.getUserDao();
        UserBean user = new UserBean();
        user.setAction(action);
        user.setParentId(userManageId);
        user.setTitle(title);
        user.setContent(content);
        user.setLimitTime(limitTime);
        user.setUserId(userId);
        user.setCreator(userName);
        user.setCreateTime(createTime);

        user.setUserName(request.getParameter("user_name"));
        user.setName(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setSex(request.getParameter("sex"));
        user.setEmail(request.getParameter("email"));
        user.setPhone(request.getParameter("phone"));
        user.setWechat(request.getParameter("wechat"));
        user.setGrade(request.getParameter("grade"));
        user.setClassStr(request.getParameter("class"));
        user.setStudentNum(request.getParameter("student_num"));
        user.setFaculty(request.getParameter("faculty"));

        int id = infoDao.registerUser(user);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", new ArrayList());
        jsonObj.put("action", user.getAction());
        jsonObj.put("result_msg", "ok");
        jsonObj.put("result_code", 0);
        log("用户 " + userName + " 于 " + createTime + " 添加了 [" + MODULE + "][" + SUB + "] 记录", "添加记录", MODULE);

        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

    /*
     * 功能：修改记录
     */
    public void modifyRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String limitTime = request.getParameter("limit_time");
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (id != null) {
            String createTime = TimeUtil.currentDate();
            UserDao infoDao = DaoFactory.getUserDao();
            UserBean info = new UserBean();
            info.setId(id);
            info.setTitle(title);
            info.setContent(content);
            info.setLimitTime(limitTime);
            info.setCreator(userName);
            info.setCreateTime(createTime);

            info.setUserName(request.getParameter("user_name"));
            info.setName(request.getParameter("name"));
            info.setSex(request.getParameter("sex"));
            info.setEmail(request.getParameter("email"));
            info.setPhone(request.getParameter("phone"));
            info.setWechat(request.getParameter("wechat"));
            info.setGrade(request.getParameter("grade"));
            info.setClassStr(request.getParameter("class"));
            info.setStudentNum(request.getParameter("student_num"));
            info.setFaculty(request.getParameter("faculty"));

            jsonObj = infoDao.modifyRecord(info);
            log("用户 " + userName + " 于 " + createTime + " 修改了 [" + MODULE + "][" + SUB + "] 记录", "修改记录", MODULE);
        }

        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

    public void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
//        String id = request.getParameter("id");
        String[] ids = request.getParameterValues("id");
        Log.d(TAG, "ids=" + Arrays.toString(ids));

        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (ids != null) {
            String creator = (String) session.getAttribute("user_name");
            String createTime = TimeUtil.currentDate();
            /*----------------------------------------数据获取完毕，开始和数据库交互*/
            UserDao infoDao = DaoFactory.getUserDao();
            jsonObj = infoDao.deleteRecord(action, ids);
            log("用户 " + creator + " 于 " + createTime + " 删除了 [" + MODULE + "][" + SUB + "] 记录", "删除记录", MODULE);
        }

//        if (id != null) {
//            String creator = (String) session.getAttribute("user_name");
//            String createTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
//            /*----------------------------------------数据获取完毕，开始和数据库交互*/
//            UserDao infoDao = DaoFactory.getUserDao();
//            jsonObj = infoDao.deleteRecord(action, id);
//            ylxLog.log("用户 " + creator + " 于 " + createTime + " 删除了 [" + MODULE + "][" + SUB + "] 记录", "删除记录", MODULE);
//        }

        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

}
