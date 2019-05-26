package com.iWen.survey.ctrl;

import com.iWen.survey.dao.DAOFactory;
import com.iWen.survey.dao.QuestionDAO;
import com.iWen.survey.dto.Question;
import com.iWen.survey.util.StringUtil;

public class QuestionAction extends BaseAction {

    private String sid;

    private String type = "";

    public String AddQuestion() {
        sid = request.getParameter("sid");
        type = request.getParameter("type");
        String qType = request.getParameter("qType");
        String qhead = StringUtil.encodeString(request.getParameter("qHead"));
        String qbody = StringUtil.encodeString(request.getParameter("qBody"));
        String qresult = StringUtil.encodeString(request.getParameter("qResult"));
        String qimg = request.getParameter("qImg");
        Question question = new Question();
        question.setSurvey(Long.valueOf(sid));
        question.setQType(Long.valueOf(qType));
        question.setQHead(qhead);
        question.setQBody(qbody);
        question.setQResult(qresult);
        question.setQImg(qimg);
        question.setQOrder(0L);
        if (qbody == null) {
            qbody = "";
        }
        String[] qbodys = qbody.split("&\\$\\$&");
        String spliter = "";
        for (int i = 1; i < qbodys.length; i++)
            if (i == qbodys.length - 1)
                spliter = spliter + "null&null";
            else
                spliter = spliter + "null&";
        question.setQJdtz(spliter);
        QuestionDAO dao = DAOFactory.getQuestionDAO();
        boolean ret = dao.addQuestion(question);
        if (ret) {
//		response.sendRedirect("../admin/OpResult.jsp?op=Question&ret=true&sid="+sid);
            return "success";
        } else {
//		response.sendRedirect("../admin/OpResult.jsp?op=Question&ret=false");
            return "fail";
        }
    }

    public String DelQuestion() {
        type = request.getParameter("type");
        String sid = request.getParameter("sid");
        String qid = request.getParameter("qid");
        QuestionDAO dao = DAOFactory.getQuestionDAO();
        boolean ret = dao.delQuestion(Long.valueOf(qid));
        if (ret) {
//		response.sendRedirect("../admin/QuestionAdmin.jsp?sid="+sid);
            return "success";
        } else {
//		response.sendRedirect("../admin/OpResult.jsp?op=Question&ret=false");
            return "fail";
        }
    }

    public String EditQuestion() {
        type = request.getParameter("type");
        String qhead = StringUtil.encodeString(request.getParameter("qHead"));
        String qbody = StringUtil.encodeString(request.getParameter("qBody"));
        String qresult = StringUtil.encodeString(request.getParameter("qResult"));
        String sid = request.getParameter("sid");
        String qid = request.getParameter("qid");
        String type = request.getParameter("type");
        String qimg = request.getParameter("qImg");
        QuestionDAO dao = DAOFactory.getQuestionDAO();
        Question question = dao.findQuestion(Long.valueOf(qid));

        question.setQType(Long.valueOf(type));
        question.setQHead(qhead);
        question.setQBody(qbody);
        question.setQImg(qimg);
        String[] qbodys = qbody.split("&\\$\\$&");
        String spliter = "";
        for (int i = 1; i < qbodys.length; i++)
            if (i == qbodys.length - 1)
                spliter = spliter + "null&null";
            else
                spliter = spliter + "null&";
        question.setQJdtz(spliter);
        question.setQOrder(0L);
        boolean ret = dao.updateQuestion(question);
        if (ret) {
            //response.sendRedirect("../admin/OpResult.jsp?op=Question&ret=true&sid="+sid);
            return "success";
        } else {
            //response.sendRedirect("../admin/OpResult.jsp?op=Question&ret=false");
            return "fail";
        }
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
