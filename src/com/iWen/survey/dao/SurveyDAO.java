package com.iWen.survey.dao;

import com.iWen.survey.dto.Survey;
import com.iWen.survey.pager.PageListener;

import java.util.List;

public interface SurveyDAO extends PageListener {
	boolean addSurvey(Survey survey);
	boolean updateSurvey(Survey survey);
	boolean delSurvey(Long surveyId);
	List<Survey> listAllSurvey();
	List<Survey> listAllSurvey(String order);
	Survey findSurvey(Long surveyId);
	List<Survey> listVisitableSurvey();
	List<Survey> listVisitableSurvey(String order);
}
