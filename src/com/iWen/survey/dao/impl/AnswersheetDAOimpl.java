package com.iWen.survey.dao.impl;

import com.iWen.survey.dao.AnswersheetDAO;
import com.iWen.survey.dto.Answersheet;
import com.iWen.survey.pager.PageConfig;
import com.iWen.survey.sql.ConnectionFactory;
import com.iWen.survey.sql.SQLCommand;

import javax.servlet.http.HttpServletRequest;
import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswersheetDAOimpl implements AnswersheetDAO {

    private static final String TABLE_NAME = "survey_answersheet";

    private List list_answersheet = null;

    @Override
    public boolean addAnswersheet(Answersheet answersheet) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO " + TABLE_NAME + "(s_id, as_result,"
                + "as_postdate,as_userIP) VALUES(?, ?, ?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, answersheet.getSurvey());
            pstmt.setString(2, answersheet.getAsResult());
            pstmt.setDate(3, new java.sql.Date(answersheet.getAsPostdate().getTime()));
            pstmt.setString(4, answersheet.getAsUserIp());


            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        } finally {
            SQLCommand.close(pstmt);
            SQLCommand.close(conn);
        }
    }


    @Override
    public boolean delAnswersheet(Long answersheetId) {
        SQLCommand cmd = new SQLCommand();
        return -1 != cmd.executeSQL("delete from " + TABLE_NAME + " where as_id="
                + answersheetId);
    }


    @Override
    public Answersheet findAnswersheet(Long answersheetId) {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from " + TABLE_NAME + " where as_id="
                + answersheetId);

        Answersheet answersheet = new Answersheet();
        try {
            if (rs.next()) {
                answersheet.setAsId(rs.getLong("as_id"));
                answersheet.setSurvey(rs.getLong("s_id"));
                answersheet.setAsResult(rs.getString("as_result"));
                answersheet.setAsPostdate(rs.getDate("as_postdate"));
                answersheet.setAsUserIp(rs.getString("as_userip"));
            }
            return answersheet;


        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }

    }


    @Override
    public List<Answersheet> listAllAnswersheet(Long surveyId) {
        SQLCommand cmd = new SQLCommand();
        RowSet rs = cmd.queryRowSet("select * from " + TABLE_NAME + " where s_id=" + surveyId + " order by as_postdate desc");
        Answersheet answersheet;
        List<Answersheet> list = new ArrayList<Answersheet>();
        try {
            while (rs.next()) {
                answersheet = new Answersheet();
                answersheet.setAsId(rs.getLong("as_id"));
                answersheet.setSurvey(rs.getLong("s_id"));
                answersheet.setAsResult(rs.getString("as_result"));
                answersheet.setAsPostdate(rs.getDate("as_postdate"));
                answersheet.setAsUserIp(rs.getString("as_userip"));
                list.add(answersheet);
            }

            return list;

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<?> doSelect(int recordStart, int sizePage, PageConfig pageConfig) {
        List<Answersheet> newlist = new ArrayList<Answersheet>();
        HttpServletRequest request = pageConfig.getRequest();
        List<Answersheet> listAnswersheet = this.listAllAnswersheet(Long.valueOf(request.getParameter("sid")));
        for (int i = recordStart; i < recordStart + sizePage; i++) {
            if (i < listAnswersheet.size()) {
                newlist.add(listAnswersheet.get(i));
            } else {
                break;
            }
        }
        return newlist;
    }


    @Override
    public int getCount(PageConfig pageConfig) {
        if (this.list_answersheet == null) {
            HttpServletRequest request = pageConfig.getRequest();
            list_answersheet = this.listAllAnswersheet(Long.valueOf(request.getParameter("sid")));
        }

        return list_answersheet.size();
    }


    @Override
    public boolean delAnswersheets(Long sid) {
        SQLCommand cmd = new SQLCommand();
        return -1 != cmd.executeSQL("delete from " + TABLE_NAME + " where s_id="
                + sid);
    }


}
