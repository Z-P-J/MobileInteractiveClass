package com.interactive.classroom.servlets;

import com.interactive.classroom.servlets.base.BaseHttpServlet;
import com.interactive.classroom.bean.FileBean;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.FileDao;
import com.interactive.classroom.utils.ServletUtil;
import com.interactive.classroom.utils.TimeUtil;
import com.interactive.classroom.utils.export.ExportUtil;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @author Z-P-J
 */
public class FileServlet extends BaseHttpServlet {
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
        try {
            switch (action) {
                case "get_record":
                    getAllFiles(request, response);
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
                    updateFileInfo(request, response);
                    break;
                case "delete_record":
                    deleteRecord(request, response);
                    break;
                case "export_record":
                    JSONObject jsonObj = ExportUtil.exportRecord(request, response, MODULE, SUB, "调查管理");
                    onEnd(request, response, jsonObj);
                    break;
                default:
                    processError(request, response, 2, "[" + MODULE + "/" + SUB + "/FileServlet]没有对应的action处理过程，请检查action是否正确！action=" + action, RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
                    break;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void getAllFiles(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            jsonObj = dao.getAllFiles();
            session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
        }
        String url = REDIRECT_PATH + "/" + REDIRECT_PAGE + "?exist_resultset=1";
        onEnd(request, response, jsonObj, url);
    }

    private void submitComment(HttpServletRequest request, HttpServletResponse response) {
//        String fileId = request.getParameter("file_id");
//        CommentBean comment = new CommentBean();
//        comment.setFileId(fileId);
//        comment.setPublishDate(TimeUtil.currentDate());
//        comment.setUserName(userName);
//        comment.setCommentContent(request.getParameter("comment_text"));
//        comment.setScore("50");
//        DaoFactory.getCommentDao().submitComment(comment);
//        if (session.getAttribute(MODULE + "_" + SUB + "_get_record_result") != null) {
//            JSONObject json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
//            Log.d(getClass().getName(), json.toString());
//            try {
//                ArrayList list = ServletUtil.getIndexFromFileId(fileId, json);
//                if (list != null) {
//                    list.add(9, DaoFactory.getCommentDao().getComments(fileId));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            //然后还有导航信息
//            json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
//            Log.d(getClass().getName(), "[submitComment]重新取出来的数据是：" + json.toString());
//        }
    }

    private void getRecordView(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String id = request.getParameter("id");
        String index = request.getParameter("index");

        JSONObject jsonObj = DaoFactory.getFileDao().getAllFilesWithComments();
        ServletUtil.wrapJson(id, index, jsonObj);
        jsonObj.put("result_code", 0);
        jsonObj.put("result_msg", "ok");




//        String existResultset = request.getParameter("exist_resultset");
//        if ((existResultset == null) || ("null".equals(existResultset) || existResultset.isEmpty())) {
//            existResultset = "0";
//        }
//        //如果是新查询
//        JSONObject jsonObj;
//        if ("1".equals(existResultset)) {
//            //如果有就取出来，如果没有就重新查询一次，并且保存进session里
//            if (session.getAttribute(MODULE + "_" + SUB + "_get_record_result") != null) {
//                JSONObject json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
//                debug("getRecordView json=" + json.toString());
//                jsonObj = ServletUtil.getResultSetNavigateId(id, index, json);
//                jsonObj.put("user_id", userId);
//                jsonObj.put("user_name", userName);
//                jsonObj.put("result_code", 0);
//                jsonObj.put("result_msg", "ok");
//                //然后还有导航信息
//                json = (JSONObject) session.getAttribute(MODULE + "_" + SUB + "_get_record_result");
//                debug("[getRecordView]重新取出来的数据是：" + json.toString());
//            } else {
//                //如果没有就重新查询一次
//                debug("[getRecordView]没有就重新查询一次。");
//                FileDao dao = DaoFactory.getFileDao();
//                jsonObj = dao.getAllFiles();
//                jsonObj.put("user_id", userId);
//                jsonObj.put("user_name", userName);
//                jsonObj.put("result_code", 0);
//                jsonObj.put("result_msg", "ok");
//                session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
//            }
//        } else {
//            debug("[getRecordView]existsResult=0，重新查询");
//            FileDao dao = DaoFactory.getFileDao();
//            jsonObj = dao.getAllFiles();
//            jsonObj.put("user_id", userId);
//            jsonObj.put("user_name", userName);
//            session.setAttribute(MODULE + "_" + SUB + "_get_record_result", jsonObj);
//        }
        onEndDefault(request, response, jsonObj);
    }

    private void addRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String createTime = TimeUtil.currentDate();
        FileDao dao = DaoFactory.getFileDao();
        FileBean bean = new FileBean();

        JSONObject jsonObj = dao.addFile(bean);
        log("用户 " + userName + " 于 " + createTime + " 添加了 [" + MODULE + "][" + SUB + "] 记录", "添加记录", MODULE);
        onEndDefault(request, response, jsonObj);
    }

    private void updateFileInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        JSONObject jsonObj = null;
        if (id != null) {
            String createTime = TimeUtil.currentDate();
            FileDao dao = DaoFactory.getFileDao();
            FileBean bean = new FileBean();
            bean.setId(id);
            bean.setFileName(request.getParameter("file_name"));
            jsonObj = dao.updateFileInfo(bean);
            log("用户 " + userName + " 于 " + createTime + " 修改了 [" + MODULE + "][" + SUB + "] 记录", "修改记录", MODULE);
        }
        onEndDefault(request, response, jsonObj);
    }

    private void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = request.getParameterValues("id");
        JSONObject jsonObj = null;
        if (ids != null) {
            String createTime = TimeUtil.currentDate();
            FileDao dao = DaoFactory.getFileDao();
            jsonObj = dao.deleteFileById(ids, getServletContext().getRealPath("/") + File.separator);
            log("用户 " + userName + " 于 " + createTime + " 删除了 [" + MODULE + "][" + SUB + "] 记录", "删除记录", MODULE);
        }
        onEndDefault(request, response, jsonObj);
    }

}
