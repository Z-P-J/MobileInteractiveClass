package com.iWen.survey.dao.impl;

import com.iWen.survey.dao.LinkDAO;
import com.iWen.survey.dto.Link;
import com.iWen.survey.pager.PageConfig;
import com.iWen.survey.sql.ConnectionFactory;
import com.iWen.survey.sql.SQLCommand;

import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LinkDAOimpl implements LinkDAO {

    private static final String TABLE_NAME = "survey_link";

    @Override
    public boolean addLink(Link link) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO " + TABLE_NAME + "( l_url, l_name, l_img, l_info, l_isLock, l_addtime)"
                + "VALUES( ?, ?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            //pstmt.setLong(1, survey.getTemplet());
            pstmt.setString(1, link.getLUrl());
            pstmt.setString(2, link.getLName());
            pstmt.setString(3, link.getLImg());
            pstmt.setString(4, link.getLInfo());
            pstmt.setBoolean(5, link.getLIsLock());
            pstmt.setDate(6, new java.sql.Date(link.getLAddtime().getTime()));


            return pstmt.executeUpdate() == 1 ? true : false;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);

        }
    }

    @Override
    public boolean delLink(Long linkId) {
        SQLCommand cmd = new SQLCommand();
        return -1 != cmd.executeSQL("delete from " + TABLE_NAME + " where l_id=" + linkId);
    }

    @Override
    public Link findLink(Long linkId) {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from " + TABLE_NAME + " where l_id=" + linkId);
        Link link = new Link();
        try {
            if (rs.next()) {
                link.setLId(rs.getLong("l_id"));
                link.setLName(rs.getString("l_name"));
                link.setLUrl(rs.getString("l_url"));
                link.setLImg(rs.getString("l_img"));
                link.setLInfo(rs.getString("l_info"));
                link.setLIsLock(rs.getBoolean("l_islock"));
                link.setLAddtime(rs.getDate("l_addtime"));
                return link;
            } else {
                return null;
            }

        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Link> listAllLink() {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from " + TABLE_NAME);
        List<Link> list = new ArrayList<Link>();
        Link link;
        try {
            while (rs.next()) {
                link = new Link();
                link.setLId(rs.getLong("l_id"));
                link.setLAddtime(rs.getDate("l_addtime"));
                link.setLImg(rs.getString("l_img"));
                link.setLInfo(rs.getString("l_info"));
                link.setLIsLock(rs.getBoolean("l_islock"));
                link.setLName(rs.getString("l_name"));
                link.setLUrl(rs.getString("l_url"));

                list.add(link);
            }

            return list;

        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean updateLink(Link link) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "	UPDATE " + TABLE_NAME
                + " SET l_url=?, l_name=?, l_img=?, l_info=?, l_isLock=? "
                + "WHERE l_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, link.getLUrl());
            pstmt.setString(2, link.getLName());
            pstmt.setString(3, link.getLImg());
            pstmt.setString(4, link.getLInfo());
            pstmt.setBoolean(5, link.getLIsLock());
            pstmt.setLong(6, link.getLId());
            //	pstmt.setDate(6, new java.sql.Date(link.getLAddtime().getTime()));
            return pstmt.executeUpdate() == 1 ? true : false;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);

        }
    }

    @Override
    public List<?> doSelect(int recordStart, int sizePage, PageConfig pageConfig) {
        return null;
    }

    @Override
    public int getCount(PageConfig pageConfig) {
        return 0;
    }

}
