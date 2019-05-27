<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.iWen.survey.dao.*" %>
<%@ page import="com.iWen.survey.dto.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.iWen.survey.dao.impl.SurveyDAOimpl" %>


<%
    String type = request.getParameter("type");
    long sid = Long.valueOf(request.getParameter("sid"));
    SurveyDAOimpl sdao = DAOFactory.getSurveyDAO(type);
    Survey survey = sdao.findSurvey(sid);
    QuestionDAO qdao = DAOFactory.getQuestionDAO();
    List<Question> qlist = qdao.listAllQuestion(sid);
    DecimalFormat df = new DecimalFormat("0.00");
    String title;
    if ("vote".equals(type)) {
        title = "投票列表";
    } else {
        title = "问卷列表";
    }
%>
<script language="JavaScript" src="../../assets/survey/js/Func.js"></script>
<script language="javascript1.2">window.onload = tableFix;</script>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="../../assets/survey/css/Admin.css" type="text/css"/>
</head>
<body>
<input name="survey_type" id="survey_type" type="hidden" value="<%=type%>">
<title>问卷---<%=survey.getSName() %> 统计结果</title>
<div class=nav style="text-align:left"><a href=admin_main.jsp?type=<%=type%>>桌面</a>»<a
        href=SurveyStatis.jsp?type=<%=type%>><%=title%>
</a>»查看统计
    <hr>
</div>
<!--startprint-->
<table width="100%" align="center" cellpadding="0" cellspacing="0" id="Survey_title" class="table">
    <tr>
        <th colspan="4">
            <div class="Survey_title"><span class="large"><%=survey.getSName() %></span></div>
        </th>
    </tr>
    <%
        int i = 1;
        for (Question q : qlist) {
            String[] results = q.getQResult().split(",");
            if (q.getQType() == 5) {
                out.print("<tr><td colspan=\"4\">" + i++ + "、" + q.getQHead() + "</td></tr>");
                out.print("<tr><td><td>这是一道简答题<td><td><a href=ShowText.jsp?qid=" + q.getQId() + " target=_blank>查看具体</a>");
                AnswersheetDAO answersheetDAO = DAOFactory.getAnswersheetDAO();
                List<Answersheet> answersheetList = answersheetDAO.listAllAnswersheet(sid);
                for (Answersheet answersheet : answersheetList) {
                    out.print("<tr><td><td><span>" + answersheet.getAsResult() + "</span><td><td>");
                }
            } else {
                String[] bodys = q.getQBody().split("&\\$\\$&");
                int total = 0;
                for (String s : results) {
                    total = total + Integer.valueOf(s);
                }

    %>
    <tr>
        <td colspan="4"><%=i++ %>、<%=q.getQHead() %>
        </td>
    </tr>

    <%
        for (int j = 0; j < bodys.length; j++) {

    %>

    <tr>
        <td><%=bodys[j] %>:</td>
        <td><img src="images/blue.gif" width="<%=(total == 0 ? 0 : (Integer.valueOf(results[j]) * 100.00 / total * 10))%>" height="20"></td>
        <td><%=results[j] %>/<%=total %>&nbsp;&nbsp;<%=df.format(total == 0 ? 0 : (Integer.valueOf(results[j]) * 100.00 / total))%>%</td>
        <td>
                <%=(q.getQType() == 3 || q.getQType() == 4) && j == bodys.length - 1 ? "<a href=ShowText.jsp?qid=" + q.getQId() + " target=_blank>查看具体</a>" : "" %>
    </tr>

    <%
                }
            }
        }
    %>
    <!--endprint-->
    <tr>
        <td>&nbsp;</td>
        <td align=center>
            <div id="button">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="button1" type="button" value="打印统计"
                                                                  onClick="preview();"></div>
        </td>
        <td>&nbsp;</td>
    </tr>
</table>

</body>
</html>


