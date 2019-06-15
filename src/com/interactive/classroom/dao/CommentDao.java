package com.interactive.classroom.dao;

import com.interactive.classroom.utils.DatabaseHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Z-P-J
 */
public class CommentDao {

    private CommentDao() { }

    /**
     * 根据文件id获取评论
     * @param fileId 文件id
     * @return java.util.List<java.util.List<java.lang.String>>
     */
    public static List<List<String>> getComments(String fileId) {
        List<List<String>> jsonList = new ArrayList<>();
        try {
            //构造sql语句，根据传递过来的查询条件参数
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

}
