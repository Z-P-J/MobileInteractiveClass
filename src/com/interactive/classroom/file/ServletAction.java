package com.interactive.classroom.file;
/*
 * 待完成：用MVC模式分开DB和Action操作
 * 增删改查看导印统功能的实现
 */

import com.interactive.classroom.base.BaseHttpServlet;
import com.interactive.classroom.bean.FileBean;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.FileDao;
import com.interactive.classroom.utils.*;
import org.json.JSONException;
import org.json.JSONObject;
import com.interactive.classroom.utils.export.ExportUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Z-P-J
 */
public class ServletAction extends BaseHttpServlet {
    //这里是需要改的,module和sub
    private static final String MODULE = "file";
    private static final String SUB = "core";

    //    private static final String PRE_FIX = MODULE + "_" + SUB;
    private static final String RESULT_PATH = MODULE + "/" + SUB;
    private static final String RESULT_PAGE = "result.jsp";
    //    private static final String RESULT_URL = RESULT_PATH + "/" + RESULT_PAGE;
//    private static final String RESULT_URL = "base/export/export_result.jsp";
    private static final String REDIRECT_PATH = MODULE + "/" + SUB;
    private static final String REDIRECT_PAGE = "record_list.jsp";

    @Override
    protected void handleAction(HttpServletRequest request, HttpServletResponse response, String action) {

        if (userName == null) {
            try {
                processError(request, response, 3, "session超时，请重新登录系统！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (action == null) {
                    processError(request, response, 1, "传递过来的action是null！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
                } else {
                    //这几个常规增删改查功能
                    switch (action) {
                        case "get_record":
                            getRecord(request, response);
                            break;
                        case "submit_comment":
                            submitComment(request, response);
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
                            JSONObject jsonObj = ExportUtil.exportRecord(request, response, MODULE, SUB, "调查管理");
                            onEnd(request, response, jsonObj);
                            break;
                        default:
                            processError(request, response, 2, "[" + MODULE + "/" + SUB + "/ServletAction]没有对应的action处理过程，请检查action是否正确！action=" + action, RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
                            break;
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }

        }
    }

    /*
     * 功能：查询记录
     */
    private void getRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        FileBean bean = new FileBean();
        bean.setId(request.getParameter("id"));
        bean.setFileId(request.getParameter("file_id"));
        bean.setFileName(request.getParameter("file_name"));
        bean.setUserId(userId);
        bean.setUploaderId(userId);
        bean.setAction(request.getParameter("action"));
        bean.setType(request.getParameter("type"));

        bean.setTimeFrom(request.getParameter("time_from"));
        bean.setTimeTo(request.getParameter("time_to"));
        bean.setSortIndex(request.getParameter("sort_index"));
        bean.setOrderBy(request.getParameter("order_by"));
        debug("FileBean=" + bean.toString());

        String existResultset = request.getParameter("exist_resultset");
        if ((existResultset == null) || ("null".equals(existResultset) || existResultset.isEmpty())) {
            existResultset = "0";
        }
        JSONObject jsonObj;
        if ("1".equals(existResultset)) {
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
            FileDao dao = DaoFactory.getFileDao();
            jsonObj = dao.getRecord(bean);
            session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        }
        jsonObj.put("user_id", userId);
        jsonObj.put("user_name", userName);
        jsonObj.put("user_role", userRole);
        jsonObj.put("user_avatar", userAvatar);
        jsonObj.put("action", bean.getAction());
        /*--------------------数据查询完毕，根据交互方式返回数据--------------------*/
        String url = REDIRECT_PATH + "/" + REDIRECT_PAGE + "?exist_resultset=1";
        onEnd(request, response, jsonObj, url);
    }

    private void submitComment(HttpServletRequest request, HttpServletResponse response) {
        String fileId = request.getParameter("file_id");
        String sql = "insert into file_comment (file_id,user_id,comment_content,score,publish_date) values("
                + fileId + ",'" + userName + "','" + request.getParameter("comment_text") +
                "'," + 50 + ",'" + TimeUtil.currentDate() + "')";
        Log.d("submitComment", "sql=" + sql);
        DBHelper.getInstance().executeUpdate(sql).close();
        if (session.getAttribute(MODULE + "_" + SUB + "_get_record_result") != null) {
            JSONObject json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
            Log.d(getClass().getName(), json.toString());
            try {
                ArrayList list = ServletUtil.getIndexFromFileId(fileId, json);
                if (list != null) {
                    list.add(9, FileDao.getComments(fileId));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //然后还有导航信息
            json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
            Log.d(getClass().getName(), "[submitComment]重新取出来的数据是：" + json.toString());
        }
    }

    private void getRecordView(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String id = request.getParameter("id");
        String index = request.getParameter("index");

        FileBean bean = new FileBean();
        bean.setAction(request.getParameter("action"));
        bean.setUserId(userId);
        Log.d(getClass().getName(), "getRecordView bean=" + bean.toString());

        String existResultset = request.getParameter("exist_resultset");
        if ((existResultset == null) || ("null".equals(existResultset) || existResultset.isEmpty())) {
            existResultset = "0";
        }

        //如果是新查询
        JSONObject jsonObj;
        if ("1".equals(existResultset)) {
            //如果有就取出来，如果没有就重新查询一次，并且保存进session里
            if (session.getAttribute(MODULE + "_" + SUB + "_get_record_result") != null) {
                JSONObject json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
                debug("getRecordView json=" + json.toString());
                jsonObj = ServletUtil.getResultSetNavigateId(id, index, json);
                jsonObj.put("user_id", userId);
                jsonObj.put("user_name", userName);
                jsonObj.put("action", bean.getAction());
                jsonObj.put("result_code", 0);
                jsonObj.put("result_msg", "ok");
                //然后还有导航信息
                json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
                debug("[getRecordView]重新取出来的数据是：" + json.toString());
            } else {
                //如果没有就重新查询一次
                debug("[getRecordView]没有就重新查询一次。");
                FileDao dao = DaoFactory.getFileDao();
                jsonObj = dao.getRecord(bean);
                jsonObj.put("user_id", userId);
                jsonObj.put("user_name", userName);
                jsonObj.put("action", bean.getAction());
                jsonObj.put("result_code", 0);
                jsonObj.put("result_msg", "ok");
                session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
            }
        } else {
            debug("[getRecordView]existsResult=0，重新查询");
            FileDao dao = DaoFactory.getFileDao();
            jsonObj = dao.getRecord(bean);
            jsonObj.put("user_id", userId);
            jsonObj.put("user_name", userName);
            jsonObj.put("action", bean.getAction());
            session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        }
        onEndDefault(request, response, jsonObj);
    }

    private void addRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String createTime = TimeUtil.currentDate();
        FileDao dao = DaoFactory.getFileDao();
        FileBean bean = new FileBean();
        bean.setAction(request.getParameter("action"));

        bean.setUserId(userId);

        JSONObject jsonObj = dao.addRecord(bean);
        log("用户 " + userName + " 于 " + createTime + " 添加了 [" + MODULE + "][" + SUB + "] 记录", "添加记录", MODULE);
        onEndDefault(request, response, jsonObj);
    }

    /*
     * 功能：修改记录
     */
    private void modifyRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (id != null) {
            String createTime = TimeUtil.currentDate();
            FileDao dao = DaoFactory.getFileDao();
            FileBean bean = new FileBean();
            bean.setId(id);
            bean.setFileName(request.getParameter("file_name"));
            jsonObj = dao.modifyRecord(bean);
            log("用户 " + userName + " 于 " + createTime + " 修改了 [" + MODULE + "][" + SUB + "] 记录", "修改记录", MODULE);
        }

        onEndDefault(request, response, jsonObj);
    }

    private void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        String[] ids = request.getParameterValues("id");

        /*----------------------------------------数据获取完毕，开始和数据库交互*/
        JSONObject jsonObj = null;
        //检查输入参数是否正确先
        if (ids != null) {
            String createTime = TimeUtil.currentDate();
            /*----------------------------------------数据获取完毕，开始和数据库交互*/
            FileDao dao = DaoFactory.getFileDao();
            jsonObj = dao.deleteRecord(action, ids, userName, createTime);
            log("用户 " + userName + " 于 " + createTime + " 删除了 [" + MODULE + "][" + SUB + "] 记录", "删除记录", MODULE);
        }

        onEndDefault(request, response, jsonObj);
    }

}
