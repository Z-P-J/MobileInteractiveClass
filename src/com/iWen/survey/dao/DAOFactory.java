package com.iWen.survey.dao;

import com.iWen.survey.dao.impl.*;

public class DAOFactory {

	private DAOFactory() {

	}
	public static SurveyDAO getSurveyDAO() {
		return new SurveyDAOimpl();
	}

	public static SurveyDAOimpl getSurveyDAO(String type) {
		return new SurveyDAOimpl(type);
	}

	public static QuestionDAO getQuestionDAO() {
		return new QuestionDAOimpl();
	}

	public static ConfigDAO getConfigDAO() {
		return new ConfigDAOimpl();
	}

	public static AnswersheetDAO getAnswersheetDAO() {
		return new AnswersheetDAOimpl();
	}

	public static AdminDAO getAdminDAO() {
		return new AdminDAOimpl();
	}

	public static LinkDAO getLinkDAO() {
		return new LinkDAOimpl();
	}

	public static UserDAO getUserDAO() {
		return new UserDAOImpl();
	}
}
