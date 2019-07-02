package com.interactive.classroom.dao;

import com.interactive.classroom.bean.CommentBean;
import com.interactive.classroom.utils.DatabaseHelper;
import org.apache.struts2.json.annotations.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Z-P-J
 */
public interface CommentDao {

    /**
     * 表名
     */
    String TABLE_NAME = "file_comment";
    /**
     * 数据库中的字段
     */
    String[] LABELS = {"id", "file_id", "user_id", "user_name", "comment_content", "score", "publish_date"};
    /**
     * 数据库中字段中文解释
     */
    String[] LABELS_CH = {"ID", "文件ID", "用户ID", "用户名", "评论内容", "文评分", "评论时间"};

    /**
     * 根据文件id获取评论
     * @param fileId 文件id
     * @return java.util.List<java.util.List<java.lang.String>>
     */
    List<List<String>> getComments(String fileId);

    /**
     * 根据文件id获取评论
     * @param fileId 文件id
     * @return JSONArray
     * @throws JSONException JSONException
     */
    JSONArray getFileComments(String fileId) throws JSONException;

    /**
     * 发布评论
     * @param comment CommentBean
     */
    void submitComment(CommentBean comment);

}
