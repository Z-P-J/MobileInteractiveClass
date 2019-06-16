package com.interactive.classroom.servlets;

import com.interactive.classroom.constant.ActionType;
import com.interactive.classroom.servlets.base.BaseHttpServlet;
import com.interactive.classroom.bean.CommentBean;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.utils.TimeUtil;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Z-P-J
 */
public class CommentServlet extends BaseHttpServlet {

    @Override
    protected void handleAction(HttpServletRequest request, HttpServletResponse response, String action) {
        if (ActionType.ACTION_GET_FILE_COMMENTS.equals(action)) {
            getFileComments(request, response);
        } else if (ActionType.ACTION_SUBMIT_COMMENT.equals(action)) {
            submitComment(request, response);
        }
    }

    private void getFileComments(HttpServletRequest request, HttpServletResponse response) {
        String fileId = request.getParameter("file_id");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("comments", DaoFactory.getCommentDao().getFileComments(fileId));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        onEndDefault(request, response, jsonObject);
    }

    private void submitComment(HttpServletRequest request, HttpServletResponse response) {
        CommentBean comment = new CommentBean();
        comment.setFileId(request.getParameter("file_id"));
        comment.setPublishDate(TimeUtil.currentDate());
        comment.setUserName(userName);
        comment.setCommentContent(request.getParameter("comment_text"));
        comment.setScore("50");
        DaoFactory.getCommentDao().submitComment(comment);
        JSONObject jsonObject = null;
        try {
            jsonObject = getSuccessJsonObject();
            jsonObject.put("comments", DaoFactory.getCommentDao().getFileComments(comment.getFileId()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        onEndDefault(request, response, jsonObject);
    }

}
