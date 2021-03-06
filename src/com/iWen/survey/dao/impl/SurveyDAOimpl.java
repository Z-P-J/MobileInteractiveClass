package com.iWen.survey.dao.impl;

import com.iWen.survey.dao.SurveyDAO;
import com.iWen.survey.dto.Survey;
import com.iWen.survey.pager.PageConfig;
import com.iWen.survey.sql.ConnectionFactory;
import com.iWen.survey.sql.SQLCommand;

import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SurveyDAOimpl implements SurveyDAO {

	private static final String TABLE_NAME = "survey_all";

	private List<Survey> list_survey = null;

	private String type;

	public void setType(String type) {
		this.type = type;
	}

	public SurveyDAOimpl() {
		this.type = "";
	}

	public SurveyDAOimpl(String type) {
		this.type = type;
	}

	@Override
	public boolean addSurvey(Survey survey) {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO " + TABLE_NAME + "( s_name, s_desc, s_author,s_img,  s_createDate,"
				+ "s_password, s_isOpen, s_expireDate, s_isAudited,  s_usehits, s_type"
				+ ") VALUES( ?, ?, ?, ?, ?,?, ?,?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, survey.getSName());
			pstmt.setString(2, survey.getSDesc());
			pstmt.setString(3, survey.getSAuthor());
			pstmt.setString(4, survey.getSImg());
			pstmt.setDate(5, new java.sql.Date(survey.getSCreateDate()
					.getTime()));
			pstmt.setString(6, survey.getSPassword());
			pstmt.setBoolean(7, survey.getSIsOpen());
			pstmt.setDate(8, new java.sql.Date(survey.getSExpireDate()
					.getTime()));
			pstmt.setBoolean(9, survey.getSIsAudited());
			pstmt.setLong(10, survey.getSUsehits());
			pstmt.setString(11, survey.getsType());

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
	public boolean delSurvey(Long surveyId) {
		SQLCommand cmd = new SQLCommand();
		return -1 != cmd
				.executeSQL("delete from " + TABLE_NAME + " where s_id=" + surveyId);

	}

	@Override
	public Survey findSurvey(Long surveyId) {
		SQLCommand cmd = new SQLCommand();
		RowSet rs = cmd.queryRowSet("select * from " + TABLE_NAME + " where s_id="
				+ surveyId);
		Survey survey = new Survey();
		try {
			if (rs.next()) {
				survey.setSId(rs.getLong("s_id"));
				survey.setSName(rs.getString("s_name"));
				survey.setSDesc(rs.getString("s_desc"));
				survey.setSAuthor(rs.getString("s_author"));
				survey.setSImg(rs.getString("s_img"));
				survey.setSCreateDate(rs.getDate("s_createdate"));
				survey.setSPassword(rs.getString("s_password"));
				survey.setSIsOpen(rs.getBoolean("s_isopen"));
				survey.setSExpireDate(rs.getDate("s_expiredate"));
				survey.setSIsAudited(rs.getBoolean("s_isaudited"));
				survey.setSUsehits(rs.getLong("s_usehits"));
				survey.setsType(rs.getString("s_type"));
				return survey;
			} else {
				return null;
			}

		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		} finally {
			SQLCommand.close(rs);
		}

	}

	@Override
	public List<Survey> listAllSurvey(String order) {
		SQLCommand cmd = new SQLCommand();
		String sql = "select * from " + TABLE_NAME + " ";
		if (!type.isEmpty()) {
			sql +=  "where s_type='" + type + "' ";
		}
		RowSet rs = cmd.queryRowSet(sql + "order by " + order);
		List<Survey> list = new ArrayList<Survey>();
		Survey survey;
		try {
			while (rs.next()) {
				survey = new Survey();
				survey.setSId(rs.getLong("s_id"));
				survey.setSName(rs.getString("s_name"));
				survey.setSDesc(rs.getString("s_desc"));
				survey.setSAuthor(rs.getString("s_author"));
				survey.setSImg(rs.getString("s_img"));
				survey.setSCreateDate(rs.getDate("s_createdate"));
				survey.setSPassword(rs.getString("s_password"));
				survey.setSIsOpen(rs.getBoolean("s_isopen"));
				survey.setSExpireDate(rs.getDate("s_expiredate"));
				survey.setSIsAudited(rs.getBoolean("s_isaudited"));
				survey.setSUsehits(rs.getLong("s_usehits"));
				survey.setsType(rs.getString("s_type"));
				list.add(survey);
			}

			return list;

		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean updateSurvey(Survey survey) {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE " + TABLE_NAME
				+ " SET s_name=?, s_desc=?, s_author=?, s_img=?,  s_createDate=?,"
				+ " s_password=?, s_isOpen=?, s_expireDate=?, "
				+ "s_isAudited=?,s_usehits=?,s_type=? " + "WHERE s_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, survey.getSName());
			pstmt.setString(2, survey.getSDesc());
			pstmt.setString(3, survey.getSAuthor());
			pstmt.setString(4, survey.getSImg());
			pstmt.setDate(5, new java.sql.Date(survey.getSCreateDate().getTime()));
			pstmt.setString(6, survey.getSPassword());
			pstmt.setBoolean(7, survey.getSIsOpen());
			pstmt.setDate(8, new java.sql.Date(survey.getSExpireDate().getTime()));
			pstmt.setBoolean(9, survey.getSIsAudited());
			pstmt.setLong(10, survey.getSUsehits());
			pstmt.setString(11, survey.getsType());
			pstmt.setLong(12, survey.getSId());

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
    public List<Survey> doSelect(int recordStart, int sizePage,
                                 PageConfig pageConfig) {
		List<Survey> newlist = new ArrayList<>();
		if (this.list_survey == null) {
			if ("front_end".equals(pageConfig.getAction())) {
				list_survey = this.listVisitableSurvey();
			} else {
				list_survey = this.listAllSurvey();
			}
		}
		for (int i = recordStart; i < recordStart + sizePage; i++) {
			if (i < list_survey.size()) {
				newlist.add(list_survey.get(i));
			} else {
				break;
			}

		}
		return newlist;
	}

	@Override
	public int getCount(PageConfig pageConfig) {
		if (this.list_survey == null) {
			list_survey = this.listAllSurvey();
		}

		return list_survey.size();
	}

	@Override
	public List<Survey> listAllSurvey() {
		return this.listAllSurvey("s_id desc");
	}

	@Override
	public List<Survey> listVisitableSurvey() {

		return listVisitableSurvey("s_createdate desc,s_id desc");
	}

	@Override
	public List<Survey> listVisitableSurvey(String order) {
		String sql = "select * from " + TABLE_NAME + " where s_isOpen ='1' and s_isAudited=1 ";
		if (!type.isEmpty()) {
			sql += "s_type='" + type + "' ";
		}
 		sql += ("and s_expiredate>='"
				+ new java.sql.Date(new java.util.Date().getTime())
				+ "' order by " + order);
		SQLCommand cmd = new SQLCommand();
		RowSet rs = cmd.queryRowSet(sql);
		List<Survey> list = new ArrayList<Survey>();
		Survey survey;
		try {
			while (rs.next()) {
				survey = new Survey();
				survey.setSId(rs.getLong("s_id"));
				survey.setSName(rs.getString("s_name"));
				survey.setSDesc(rs.getString("s_desc"));
				survey.setSAuthor(rs.getString("s_author"));
				survey.setSImg(rs.getString("s_img"));
				survey.setSCreateDate(rs.getDate("s_createdate"));
				survey.setSPassword(rs.getString("s_password"));
				survey.setSIsOpen(rs.getBoolean("s_isopen"));
				survey.setSExpireDate(rs.getDate("s_expiredate"));
				survey.setSIsAudited(rs.getBoolean("s_isaudited"));
				survey.setSUsehits(rs.getLong("s_usehits"));
				survey.setsType(rs.getString("s_type"));
				list.add(survey);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			SQLCommand.close(rs);
		}

	}



}
