package com.interactive.classroom.servlets;

import com.interactive.classroom.bean.FileBean;
import com.interactive.classroom.constant.ActionType;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.FileDao;
import com.interactive.classroom.dao.HomeworkDao;
import com.interactive.classroom.dao.filters.FileFilter;
import com.interactive.classroom.dao.filters.FilterFactory;
import com.interactive.classroom.dao.filters.HomeworkFilter;
import com.interactive.classroom.servlets.base.BaseHttpServlet;
import com.interactive.classroom.utils.ServletUtil;
import com.interactive.classroom.utils.TimeUtil;
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
public class FileServlet extends BaseHttpServlet {

    /**
     *
     */
    private static final String MODULE = "file";
    private static final String SUB = "core";
    private static final String RESULT_PATH = MODULE + "/" + SUB;
    private static final String RESULT_PAGE = "result.jsp";
    private static final String REDIRECT_PATH = MODULE + "/" + SUB;
    private static final String REDIRECT_PAGE = "record_list.jsp";

    @Override
    protected void handleAction(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        try {
            if (ActionType.ACTION_QUERY_FILES.equals(action)) {
                queryFiles(request, response);
            } else if (ActionType.ACTION_DELETE_FILE.equals(action)) {
                deleteFile(request, response);
            } else if (ActionType.ACTION_GET_FILE_DETAIIL.equals(action)){
                getFileDetail(request, response);
            } else if (ActionType.ACTION_UPDATE_FILE_INFO.equals(action)) {
                updateFileInfo(request, response);
            } else if (ActionType.ACTION_EXPORT_FILES.equals(action)) {
                exportFile(response);
                JSONObject jsonObj = ExportUtil.exportRecord(request, response, MODULE, SUB, "文件管理");
                onEnd(request, response, jsonObj);
            } else {
                onError(response, "没有对应的action处理过程，请检查action是否正确！action=" + action);
//                processError(request, response, 2, "[" + MODULE + "/" + SUB + "/FileServlet]没有对应的action处理过程，请检查action是否正确！action=" + action, RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
            }
        } catch (JSONException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void queryFiles(HttpServletRequest request, HttpServletResponse response) throws JSONException, SQLException {
        String keyword = request.getParameter("keyword");
        String timeFrom = request.getParameter("time_from");
        String timeTo = request.getParameter("time_to");
        String orderBy = request.getParameter("order_by");
        FileFilter filter = FilterFactory.getFileFilter()
                .setHomeworkId(request.getParameter("homework_id"))
                .setUserId(userId)
                .setUserType(userType)
                .setTimeFrom(timeFrom)
                .setTimeTo(timeTo)
                .setKeyword(keyword)
                .setOrder(orderBy);
        debug("getAllFiles--filter=" + filter);
        JSONObject jsonObj = DaoFactory.getFileDao().queryFiles(filter);
        responseByJson(response, jsonObj);
    }

    private void deleteFile(HttpServletRequest request, HttpServletResponse response) throws JSONException, SQLException {
        String[] ids = request.getParameterValues("id");
        JSONObject jsonObj = null;
        if (ids != null) {
            String createTime = TimeUtil.currentDate();
            FileDao dao = DaoFactory.getFileDao();
            jsonObj = dao.deleteFileById(ids, ServletUtil.getUploadPath(this));
            log("用户 " + userName + " 于 " + createTime + " 删除了 [" + MODULE + "][" + SUB + "] 记录", "删除记录", MODULE);
        }
//        onEndDefault(request, response, jsonObj);
        responseByJson(response, jsonObj);
    }

    private void exportFile(HttpServletResponse response) throws JSONException, SQLException {
        FileFilter filter = FilterFactory.getFileFilter()
                .setUserId(userId)
                .setUserType(userType);
        JSONObject jsonObj = DaoFactory.getFileDao().queryFiles(filter);

        ExportUtil.with(jsonObj)
                .setModuleName(MODULE)
                .setTableNickName("文件管理")
                .setLabels(FileDao.LABELS)
                .setLabelsZh(FileDao.LABELS_CH)
                .export();

        responseByJson(response, jsonObj);
    }

    private void getFileDetail(HttpServletRequest request, HttpServletResponse response) throws JSONException, SQLException {
        String id = request.getParameter("id");
        String homeworkId = request.getParameter("homework_id");
        String orderBy = request.getParameter("order_by");
        String index = request.getParameter("index");
        FileFilter filter = FilterFactory.getFileFilter()
                .setUserType(userType)
                .setHomeworkId(homeworkId)
                .setGetComments(true)
                .setUserId(userId)
                .setOrder(orderBy);
        debug("getFileDetail--filter=" + filter.toString());
        JSONObject jsonObj = DaoFactory.getFileDao().queryFiles(filter);
        ServletUtil.wrapJson(id, index, jsonObj);
//        onEndDefault(request, response, jsonObj);
        responseByJson(response, jsonObj);
    }

    private void updateFileInfo(HttpServletRequest request, HttpServletResponse response) throws JSONException, SQLException {
        String id = request.getParameter("id");
        JSONObject jsonObj = null;
        if (id != null) {
            String createTime = TimeUtil.currentDate();
            FileBean bean = new FileBean();
            bean.setId(id);
            bean.setFileName(request.getParameter("file_name"));
            jsonObj = DaoFactory.getFileDao().updateFileInfo(bean);
            log("用户 " + userName + " 于 " + createTime + " 修改了 [" + MODULE + "][" + SUB + "] 记录", "修改记录", MODULE);
        }
//        onEndDefault(request, response, jsonObj);
        responseByJson(response, jsonObj);
    }

}
