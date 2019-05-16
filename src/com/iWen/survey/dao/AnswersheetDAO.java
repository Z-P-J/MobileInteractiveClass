package com.iWen.survey.dao;

import com.iWen.survey.dto.Answersheet;
import com.iWen.survey.pager.PageListener;

import java.util.List;

public interface AnswersheetDAO extends PageListener {

	boolean addAnswersheet(Answersheet answersheet);
	boolean delAnswersheet(Long answersheetId);
	boolean delAnswersheets(Long sid);
	List<Answersheet> listAllAnswersheet(Long surveyId);
	Answersheet findAnswersheet(Long answersheetId);
}
