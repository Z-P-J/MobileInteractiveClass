package com.interactive.classroom.project.file;
/*
 * 增删改查看导印统功能的实现
 * 待完成：用MVC模式分开DB和Action操作
 */

import com.interactive.classroom.base.BaseHttpServlet;
import com.interactive.classroom.project.dao.*;
import org.json.JSONException;
import org.json.JSONObject;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.LogEvent;
import com.interactive.classroom.utils.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Z-P-J
 */
public class ServletAction extends BaseHttpServlet {
    //这里是需要改的,module和sub
    private static final String MODULE = "project";
    private static final String SUB = "file";

//    public String preFix = MODULE + "_" + SUB;
    private static final String RESULT_PATH = MODULE + "/" + SUB;
    private static final String RESULT_PAGE = "result.jsp";
    private static final String RESULT_URL = RESULT_PATH + "/" + RESULT_PAGE;
    private static final String REDIRECT_PATH = MODULE + "/" + SUB;
    private static final String REDIRECT_PAGE = "project_list.jsp";
//    public String redirectUrl = REDIRECT_PAGE;
    public String databaseName = "my_test";
    private LogEvent ylxLog = new LogEvent();

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
        showDebug("processAction收到的action是：" + action);
        if (session.getAttribute("user_role") == null) {
            try {
                processError(request, response, 3, "session超时，请重新登录系统！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (action == null) {
                    processError(request, response, 1, "传递过来的action是null！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
                } else {
                    //常规增删改查功能
                    switch (action) {
                        case "get_record":
                            getRecord(request, response);
                            break;
                        case "get_record_by_id":
                            getRecordById(request, response);
                            break;
                        case "add_record":
                            addRecord(request, response);
                            break;
                        case "delete_record":
                            deleteRecord(request, response);
                            break;
                        case "modify_record":
                            modifyRecord(request, response);
                            break;
                        case "get_member_record":
                            getMemberRecord(request, response);
                            break;
                        case "get_member_record_by_id":
                            getMemberRecordById(request, response);
                            break;
                        case "add_member_record":
                            addMemberRecord(request, response);
                            break;
                        case "delete_member_record":
                            deleteMemberRecord(request, response);
                            break;
                        case "apply_join":
                            applyJoin(request, response);
                            break;
                        case "apply_check":
                            applyCheck(request, response);
                            break;
                        case "get_apply_record":
                            getApplyRecord(request, response);
                            break;
                        case "get_project":
                            getRecord(request, response);
                            break;
                        case "get_project_by_project_id":
                            getRecordByProjectId(request, response);
                            break;
                        case "project_file_join_apply":
                            processJoinApplyNotice(request, response);
                            break;
                        case "project_file_join_apply_result":
                            processJoinApplyNotice(request, response);
                            break;
                        default:
                            processError(request, response, 2, "没有对应的action处理过程，请检查action是否正确！action=" + action, RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private void showDebug(String msg) {
//        System.out.println("[" + TimeUtil.currentDate() + "][" + MODULE + "/" + SUB + "/ServletAction]" + msg);
        Log.d(getClass().getName(), msg);
    }

    /*
     * 功能：范例函数，待整理，针对json和form两种情况进行分别对待
     */
    public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String content = request.getParameter("content");

        String creator = (String) session.getAttribute("user_name");
        String createTime = TimeUtil.currentDate();
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        FileDao fileDao = new FileDao();
        JSONObject jsonObj = fileDao.doAction(action, id, content, creator, createTime);

        onEndDefault(request, response, jsonObj);
    }

    /*
     * 功能：查询记录
     */
    public void getRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String content = request.getParameter("content");
        String type = request.getParameter("type");
        String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        String creator = (String) session.getAttribute("user_name");
        String createTime = TimeUtil.currentDate();
        FileDao fileDao = new FileDao();
        JSONObject jsonObj = fileDao.getRecord(action, type, userId);

        session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        onEndDefault(request, response, jsonObj);
    }

    /*
     * 功能：修改记录
     */
    public void modifyRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String content = request.getParameter("content");
        String openLevel = request.getParameter("open_level");
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (id != null) {
            String creator = (String) session.getAttribute("user_name");
            String createTime = TimeUtil.currentDate();
            FileDao fileDao = new FileDao();
            jsonObj = fileDao.modifyRecord(action, id, content, openLevel, creator, createTime);
            ylxLog.log("用户 " + creator + " 于 " + createTime + " 修改了 " + SUB + " 记录", "修改记录", MODULE);
        }

        onEndDefault(request, response, jsonObj);
    }

    public void getRecordById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (id != null) {
            String userId = (String) session.getAttribute("user_id");
            String userName = (String) session.getAttribute("user_name");
            FileDao fileDao = new FileDao();
            jsonObj = fileDao.getRecordById(request.getParameter("action"), id, userId);
            jsonObj.put("user_name", userName);
        }

        session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        onEndDefault(request, response, jsonObj);
    }

    public void getRecordByProjectId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String projectId = request.getParameter("project_id");
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (projectId != null) {
            String userId = (String) session.getAttribute("user_id");
            String userName = (String) session.getAttribute("user_name");
            FileDao fileDao = new FileDao();
            jsonObj = fileDao.getRecordByProjectId(request.getParameter("action"), projectId, userId);
            jsonObj.put("user_name", userName);
        }

        session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        onEndDefault(request, response, jsonObj);
    }

    public void addRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String creator = (String) session.getAttribute("user_name");
        String userId = (String) session.getAttribute("user_id");
        String createTime = TimeUtil.currentDate();

        FileBean fileBean = new FileBean();
        fileBean.setProjectId(getProjectId());
        fileBean.setUserId(userId);
        fileBean.setProjectName(request.getParameter("project_name"));
        fileBean.setProjectNick(request.getParameter("project_nick"));
        fileBean.setProjectClass(request.getParameter("project_class"));
        fileBean.setProjectSource(request.getParameter("project_source"));
        fileBean.setProjectDescribe(request.getParameter("project_describe"));
        fileBean.setProjectManagerName(request.getParameter("project_manager_name"));
        fileBean.setProjectManagerId(userId);
        fileBean.setApplyMoney(request.getParameter("cost"));
        fileBean.setApprovalMoney(request.getParameter("approval_money"));
        fileBean.setGroupMember(request.getParameter("group_member"));
        fileBean.setStartTime(request.getParameter("start_time"));
        fileBean.setEndTime(request.getParameter("end_time"));
        fileBean.setSuperiorUnit(request.getParameter("superior_unit"));
        fileBean.setSuperiorManager(request.getParameter("superior_manager"));
        fileBean.setAttachmentName(request.getParameter("attachment_name"));
        fileBean.setOpenLevel(request.getParameter("open_level"));
        fileBean.setCheckTag(request.getParameter("check_tag"));
        fileBean.setChecker(request.getParameter("checker"));
        fileBean.setCheckTime(request.getParameter("check_time"));
        fileBean.setCreator(creator);
        fileBean.setCreateTime(createTime);
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        String avatar = (String) session.getAttribute("user_avatar");
        FileDao fileDao = new FileDao();
        JSONObject jsonObj = fileDao.addRecord(request.getParameter("action"), fileBean, avatar);
        ylxLog.log("用户 " + creator + " 于 " + createTime + " 添加了 " + SUB + " 记录", "添加记录", MODULE);

        String resultUrl = RESULT_URL;
        String redirectUrl = REDIRECT_PAGE;
        if (null != request.getParameter("result_url")) {
            resultUrl = request.getParameter("result_url");
        }

        if (null != request.getParameter("redirect_url")) {
            redirectUrl = request.getParameter("redirect_url");
        }

        onEnd(request, response, jsonObj, resultUrl, "操作已经执行，请按返回按钮返回列表页面！", 0, redirectUrl);
    }

    public String getProjectId() {
        return "PRJ_" + TimeUtil.currentDate();
    }

    public void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String[] ids = request.getParameterValues("id");

        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (ids != null) {
            String creator = (String) session.getAttribute("user_name");
            String createTime = TimeUtil.currentDate();
            /*----------------------------------------数据获取完毕，开始和数据库交互*/
            FileDao fileDao = new FileDao();
            jsonObj = fileDao.deleteRecord(action, ids, creator, createTime);
            ylxLog.log("用户 " + creator + " 于 " + createTime + " 删除了 " + SUB + " 记录", "删除记录", MODULE);
        }

        onEndDefault(request, response, jsonObj);
    }

    /*
     * 功能：申请加入该项目，变成该项目成员
     */
    private void applyJoin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        showDebug(RESULT_URL);
        FileDao fileDao = new FileDao();
        JSONObject jsonObj = fileDao.applyJoin(request.getParameter("action"), new ApplyBean(request));

        onEnd(request, response, jsonObj, RESULT_URL, "您的申请已经提交，请耐心等待审核，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

    private void applyCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        FileDao fileDao = new FileDao();
        JSONObject jsonObj = fileDao.applyCheck(request.getParameter("action"), new ApplyBean(request));

        session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        onEnd(request, response, jsonObj, "project/file/" + REDIRECT_PAGE, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

    /*
     * 功能：查询记录
     */
    private void getMemberRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String projectId = request.getParameter("project_id");
        String type = request.getParameter("type");
        String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        MemberDao memberDao = new MemberDao();
        jsonObj = memberDao.getRecord(action, type, userId, projectId);

        session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        onEndDefault(request, response, jsonObj);
    }

    private void addMemberRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String creator = (String) session.getAttribute("user_name");
        String userId = (String) session.getAttribute("user_id");
        String createTime = TimeUtil.currentDate();

        Member member = new Member();
        member.setProjectId(request.getParameter("project_id"));
        member.setMemberId(request.getParameter("member_id"));
        member.setMemberName(request.getParameter("member_name"));
        member.setMemberRole(request.getParameter("member_role"));
        member.setAvatar(request.getParameter("avatar"));
        member.setCreator(creator);
        member.setCreateTime(createTime);
        String projectId = request.getParameter("project_id");
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        MemberDao memberDao = new MemberDao();
        JSONObject jsonObj = memberDao.addRecord(action, member);
        ylxLog.log("用户 " + creator + " 于 " + createTime + " 添加了 " + SUB + " 记录", "添加记录", MODULE);

        String resultPath = RESULT_PATH;
        String redirectUrl = REDIRECT_PAGE;
        if (null != request.getParameter("result_path")) {
            resultPath = request.getParameter("result_path");
        }
        if (null != request.getParameter("redirect_url")) {
            redirectUrl = request.getParameter("redirect_url");
        }

        String resultMsg = "操作已经执行，请按返回按钮返回列表页面！";
        resultMsg = java.net.URLEncoder.encode(resultMsg, "UTF-8");
        String resultUrl = resultPath + "/" + "member_list.jsp";
        String url = resultUrl + "?project_id=" + projectId + "&result_msg=" + resultMsg + "&result_code=0&redirect_url=" + redirectUrl;
        onEnd(request, response, jsonObj, url);
    }

    private void deleteMemberRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String[] ids = request.getParameterValues("id");

        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (ids != null) {
            String creator = (String) session.getAttribute("user_name");
            String createTime = TimeUtil.currentDate();
            /*----------------------------------------数据获取完毕，开始和数据库交互*/
            MemberDao memberDao = new MemberDao();
            jsonObj = memberDao.deleteRecord(action, ids, creator, createTime);
            ylxLog.log("用户 " + creator + " 于 " + createTime + " 删除了 " + SUB + " 记录", "删除记录", MODULE);
        }

        onEndDefault(request, response, jsonObj);
    }

    /*
     * 功能：查询记录
     */
    private void getMemberRecordById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        MemberDao memberDao = new MemberDao();
        jsonObj = memberDao.getRecordById(action, id);

        session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        onEndDefault(request, response, jsonObj);
    }

    private void getApplyRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        FileDao fileDao = new FileDao();
        JSONObject jsonObj = fileDao.getApplyRecord(request.getParameter("action"), request.getParameter("id"));

        session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        onEndDefault(request, response, jsonObj);
    }

    private void processJoinApplyNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        String creator = (String) session.getAttribute("user_name");
        String createTime = TimeUtil.currentDate();
        FileDao fileDao = new FileDao();
        JSONObject jsonObj = fileDao.setNoticeRead(action, userId, creator, createTime);

        String url = RESULT_PATH + "/member_apply_check.jsp?type=" + action + "&content=" + request.getParameter("content");
        onEnd(request, response, jsonObj, url);
    }

//    @Override
//    private void onEndDefault(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObj) {
//        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
//    }

}
