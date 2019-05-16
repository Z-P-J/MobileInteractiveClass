package com.iWen.survey.dao;

import com.iWen.survey.dto.Question;
import com.iWen.survey.pager.PageListener;

import java.util.List;

public interface QuestionDAO extends PageListener {
	boolean addQuestion(Question question);
	boolean updateQuestion(Question question);
	boolean delQuestion(Long questionId);
	boolean delQuestions(Long surveyId);
	List<Question> listQuestions(String WhereClause);
	List<Question> listAllQuestion(Long surveyId);
	List<Question> listAllQuestion(Long surveyId, String ascORdesc);
	Question findQuestion(Long questionId);
}
