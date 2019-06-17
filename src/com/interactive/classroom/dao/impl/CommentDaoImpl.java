package com.interactive.classroom.dao.impl;

import com.interactive.classroom.bean.CommentBean;
import com.interactive.classroom.dao.CommentDao;
import com.interactive.classroom.utils.DatabaseHelper;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.TimeUtil;
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
public class CommentDaoImpl implements CommentDao {


    @Override
    public List<List<String>> getComments(String fileId) {
        List<List<String>> jsonList = new ArrayList<>();
        try {
            String sql = "select * from file_comment where file_id=" + fileId;
            System.out.println("TodoDao sql=" + sql);
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                list.add(rs.getString("id"));
                list.add(rs.getString("file_id"));
                list.add(rs.getString("user_id"));
                list.add(rs.getString("comment_content"));
                list.add(rs.getString("score"));
                list.add(rs.getString("publish_date"));
                jsonList.add(list);
            }
            rs.close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        return jsonList;
    }

    @Override
    public JSONArray getFileComments(String fileId) throws JSONException {
        JSONArray jsonArray = new JSONArray();
//        List<List<String>> jsonList = new ArrayList<>();
        try {
            String sql = "select * from file_comment where file_id=" + fileId;
            System.out.println("getFileComments sql=" + sql);
            ResultSet rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                for (String label : LABELS) {
                    jsonObject.put(label, rs.getString(label));
                }
//                List<String> list = new ArrayList<>();
//                list.add(rs.getString("id"));
//                list.add(rs.getString("file_id"));
//                list.add(rs.getString("user_id"));
//                list.add(rs.getString("comment_content"));
//                list.add(rs.getString("score"));
//                list.add(rs.getString("publish_date"));
//                jsonList.add(list);
                jsonArray.put(jsonObject);
            }
            rs.close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        Log.d("getFileComments", "count=" + jsonArray.length());
        return jsonArray;
    }

    @Override
    public void submitComment(CommentBean comment) {
        String sql = "insert into file_comment (file_id,user_id,comment_content,score,publish_date) values("
                + comment.getFileId() + ",'" + comment.getUserName() + "','" + comment.getCommentContent() +
                "'," + comment.getScore() + ",'" + comment.getPublishDate() + "')";
        Log.d("submitComment", "sql=" + sql);
        DatabaseHelper.executeUpdate(sql);
    }

}
