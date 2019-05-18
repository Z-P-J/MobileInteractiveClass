<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.iWen.survey.dao.DAOFactory" %>
<%@ page import="com.iWen.survey.dto.Question" %>
<%@ page import="com.iWen.survey.dao.QuestionDAO" %>
<%
    QuestionDAO dao = DAOFactory.getQuestionDAO();
    Question question = dao.findQuestion(Long.valueOf(request.getParameter("qid")));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>My JSP 'SurveyAudi.jsp' starting page</title>
    <link rel="stylesheet" type="text/css" href="../../assets/survey/css/Admin.css">
    <script type="text/javascript" src="../../assets/survey/js/Func.js"></script>
    <script language="javascript">window.onload = tableFix;</script>
    <script type="text/javascript" src="../../assets/survey/js/QuestAdd.js"></script>
    <script type="text/javascript">
        function showType(typecode) {
            document.getElementById("qEditor").innerHTML = document.getElementById(typecode).innerHTML;
        }

        function MoreAnswer(type) {
            switch (type) {
                case "DX":
                case "FX":
                    var ul = document.getElementById("ulAnswer");
                    var inputTitle = document.createElement("input");
                    if (type == "DX") {
                        inputTitle.setAttribute("type", "radio");
                    } else {
                        inputTitle.setAttribute("type", "checkbox");
                    }
                    var input = document.createElement("input");
                    var li = document.createElement("li");
                    input.setAttribute("id", "Answer");
                    li.appendChild(inputTitle);
                    li.appendChild(input);
                    ul.appendChild(li);
                    break;
            }
        }
    </script>
</head>
<body>
<table class=table cellspacing="0" cellpadding="0">
    <tr>
        <th>查看题目</th>
        <th></th>
        <th></th>
    </tr>
    <tr>
        <td>选择题型：</td>
        <td>
            <input name="Question_type" id="qtype_dx" type="radio" value="1" onClick="showType('dx');">单选题
            <input name="Question_type" id=qtype_fx type="radio" value="2" onClick="showType('fx');">多选题
            <input name="Question_type" id=qtype_ax type="radio" value="3" onClick="showType('ax');">简答题
            <input type="hidden" id="q_type" name="q_type" value="<%=question.getQType()%>">
            <input type="hidden" id="q_head" name="q_head" value="<%=question.getQHead()%>">
            <input type="hidden" id="q_body" name="q_body" value="<%=question.getQBody()%>">
        </td>
        <td></td>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td></td>
    </tr>
</table>
<div id=qEditor></div>
<div id=dx style="display: none">
    <form name="form1" action="" method="post">
        <input type="hidden" value=<%=request.getParameter("sid") %>  name="sid">
        <input type="hidden" value="" name="qBody" id="qBody">
        <input type="hidden" value="" name=qResult id="qResult">
        问题：<input name="qHead" type="text" size="40">
        <br/><br/>
        备选项：
        <ul type="1" id="ulAnswer">
            <li>
                <input type="radio">
                <input type="text" name="Answer">
            </li>
        </ul>
        <div id="button">
            <input type="button" onclick="javascript:history.back();" value="返回">
        </div>
    </form>
</div>
<div id=fx style="display: none">
    <form name="form1" action="" method="post">
        <input type="hidden" value=<%=request.getParameter("sid") %>  name="sid">
        <input type="hidden" value="" name="qBody" id="qBody">
        <input type="hidden" value="" name=qResult id="qResult">
        问题：<input name="qHead" type="text" size="40">
        <br/><br/>
        备选项：
        <ul type="1" id="ulAnswer">
            <li><input type="checkbox"><input type="text" name="Answer"></li>
            <li><input type="checkbox"><input type="text" name="Answer"></li>
            <li><input type="checkbox"><input type="text" name="Answer"></li>
            <li><input type="checkbox"><input type="text" name="Answer"></li>
        </ul>
        <div id="button">
            <input type="button" onclick="javascript:history.back();" value="返回">
        </div>
    </form>
</div>
<div id=ax style="display: none">
    <form name="form1" action="" method="post">
        <input type="hidden" value=<%=request.getParameter("sid") %>  name="sid">
        <input type="hidden" value="" name="qBody" id="qBody">
        <input type="hidden" value="" name=qResult id="qResult">
        问题：<input name="qHead" type="text" size="40">
        <br/><br/>
<%--        备选项：--%>
<%--        <ul type="1" id="ulAnswer">--%>
<%--            <li><input type="checkbox"><input type="text" name="Answer"></li>--%>
<%--            <li><input type="checkbox"><input type="text" name="Answer"></li>--%>
<%--            <li><input type="checkbox"><input type="text" name="Answer"></li>--%>
<%--            <li><input type="checkbox"><input type="text" name="Answer"></li>--%>
<%--        </ul>--%>
        <div id="button">
            <input type="button" onclick="javascript:history.back();" value="返回">
        </div>
    </form>
</div>
<script type="text/javascript">
    var qType = $("#q_type").val();
    var qHead = $("#q_head").val();
    var qBody = $("#q_body").val();
    console.log("type=" + qType);
    switch (qType) {
        case "1":
            alert(1111111111111111);
            document.getElementById("qtype_dx").click();
            document.forms[0].qHead.value = qHead.replace("\'","\\'").replace("\"","\\\"");
            var qBodys = qBody.replace("\'","\\'").replace("\"","\\\"");
            var qBodyStr = qBodys.split("&$$&");
            document.getElementById("ulAnswer").innerHTML = "";
            for (i = 0; i < qBodyStr.length; i++) {
                MoreAnswer("DX");
            }
            for (i = 0; i < qBodyStr.length; i++) {
                var answer = document.forms[0].Answer;
                answer[i].value = qBodyStr[i];
            }
            break;
        case "2":
            document.getElementById("qtype_fx").click();
            document.forms[0].qHead.value = qHead.replace("\'","\\'").replace("\"","\\\"");
            var qBodys = qBody.replace("\'","\\'").replace("\"","\\\"");
            var qBodyStr = qBodys.split("&$$&");
            document.getElementById("ulAnswer").innerHTML = "";
            for (i = 0; i < qBodyStr.length; i++) {
                MoreAnswer("FX");
            }
            for (i = 0; i < qBodyStr.length; i++) {
                var answer = document.forms[0].Answer;
                answer[i].value = qBodyStr[i];
            }
            break;
        case "5":
            document.getElementById("qtype_ax").click();
            document.forms[0].qHead.value = qHead.replace("\'","\\'").replace("\"","\\\"");
            <%--var qBodys = "<%=question.getQBody().replace("\'","\\'").replace("\"","\\\"") %>";--%>
            <%--var qBody = qBodys.split("&$$&");--%>
            <%--document.getElementById("ulAnswer").innerHTML = "";--%>
            // for (i = 0; i < qBody.length; i++) {
            //     MoreAnswer("AX");
            // }
            // for (i = 0; i < qBody.length; i++) {
            //     var answer = document.forms[0].Answer;
            //     answer[i].value = qBody[i];
            // }
            break;
        default:
            alert("题目类型错误！");
    }
</script>
</body>
</html>
