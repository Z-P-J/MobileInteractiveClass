<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.iWen.survey.dao.DAOFactory" %>
<%@ page import="com.iWen.survey.dto.Question" %>
<%@ page import="com.iWen.survey.dao.QuestionDAO" %>
<%@ page import="com.iWen.survey.pager.*" %>

<jsp:useBean id="pageConfig" class="com.iWen.survey.pager.PageConfig"></jsp:useBean>
<jsp:setProperty property="request" name="pageConfig" value="<%=request %>"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/survey/";
%>
<%
    QuestionDAO dao = DAOFactory.getQuestionDAO();
    PageControl pc = new PageControl(dao, pageConfig, "QuestionAdmin.jsp?rand=1");
    pc.setSizePage(5);
    List<Question> sList = pc.getRecord();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>My JSP 'SurveyAudi.jsp' starting page</title>
    <link rel="stylesheet" type="text/css" href="../../assets/survey/css/Admin.css">
    <script language="JavaScript" src="../../assets/survey/js/Func.js"></script>
    <script language="javascript">window.onload = tableFix;</script>
    <script type="text/javascript" src="../../assets/survey/js/jquery-1.7.2.js"></script>
    <script language="JavaScript" src="../../assets/survey/js/QuestAdd.js"></script>
    <script type="text/javascript">
        function delQuestion(qid) {
            if (confirm("确定要删除这个题目吗？") == true)
                window.location = "<%=basePath%>question/delQuestion.do?&qid=" + qid + "&sid=<%=request.getParameter("sid") %>";
        }

        function showType(typecode, type) {
            //typecode: dx--单选 ;fx--复选;
            // document.getElementById("qEditor").innerHTML = document.getElementById(typecode).innerHTML;
            var basePath = $("#base_path").val();
            document.getElementById("form1").action = basePath + "&type=" + type;
            // alert(document.getElementById("form1").action);
            if (type == 5) {
                // document.getElementById("selector").style.display = "none";
                $("#selector").hide();
                $("#text_area").show();
            } else {
                // document.getElementById("selector").style.display = "block";
                $("#selector").show();
                $("#text_area").hide();
            }
        }

        function editQuestion(qid) {
            var sUrl = setQueryString("op", "editQuestion", "QuestionEdit.jsp");
            sUrl = setQueryString("sid", "<%=request.getParameter("sid")%>", sUrl);
            window.location = setQueryString("qid", qid, sUrl);
        }

        function setJD(qid) {
            var sUrl = setQueryString("op", "setJD", "QuestionJD.jsp");
            sUrl = setQueryString("sid", "<%=request.getParameter("sid")%>", sUrl);
            window.location = setQueryString("qid", qid, sUrl);
        }

        function setTZ(qid) {
            var sUrl = setQueryString("op", "setTZ", "QuestionTZ.jsp");
            sUrl = setQueryString("sid", "<%=request.getParameter("sid")%>", sUrl);
            window.location = setQueryString("qid", qid, sUrl);
        }

        function submitForm() {
            if (SubQuestion()) {
                var form1 = document.getElementById("form1");
                // form1.action = "";
                form1.submit();
            }
        }
        
        function deleteLi() {
            var len = $("#ulAnswer").children().length - 1;
            if (len === 1) {
                alert("最少两个选项！");
                return;
            }
            if(len >= 0){
                //表示删除最后一个元素
                $("ul li:eq(" + len + ")").remove();
                if (len - 1 > 0) {
                    $("ul li:eq(" + (len - 1) + ")").append("<input id=\"delete\" type=\"button\" value=\"删除\" onclick=\"deleteLi();\">")
                }
            }else{
                alert("还没有添加元素哦");
            }
        }

        function addLi() {
        // <input id="delete" type="button" value="删除" onclick="deleteLi();">
            if ($("#ulAnswer").children().length >= 10) {
                alert("最多十个选项！");
                return;
            }
            $("#delete").remove();
            $("#ulAnswer").append("<li><input type=\"radio\"><input type=\"text\" name=\"Answer\"><input id=\"delete\" type=\"button\" value=\"删除\" onclick=\"deleteLi();\"></li>");
        }
    </script>
</head>
<body>
<div class=nav><a href=admin_main.jsp>桌面</a>»<a href=SurveyAdmin.jsp>问卷列表</a>»题目管理
    <hr>
</div>
<table class=table cellspacing="0" cellpadding="0" align="center">
    <tbody>
    <tr>
        <th width=7%>编号</th>
        <th>题目标题</th>
        <th width=20%>操作 <br></th>
    </tr>
    <%
        for (Question question : sList) {
    %>
    <tr>
        <td><%=question.getQId() %>
        </td>
        <td><%=question.getQHead() %>
        </td>
        <td><a href="javascript:editQuestion(<%=question.getQId() %>)">查看问题</a>||<a
                href='javascript:delQuestion(<%=question.getQId() %>)'>删除</a></td>
    </tr>
    <%} %>
    <tr>
        <td colspan=3 align="right"><%=pc.getCurrentPageHTML() %><%=pc.getCountPageHTML() %>|<%=pc.getFirstPageHTML() %>
            |<%=pc.getPageUpHTML() %>|<%=pc.getPageDownHTML() %>|<%=pc.getLastPageHTML() %>
        </td>
    </tr>

    </tbody>
</table>
<table class=table cellspacing="0" cellpadding="0">
    <tr>
        <th>添加新题目</th>
        <th></th>
        <th></th>
    </tr>
    <tr>
        <td>选择题型：</td>
        <td>
            <input name="Question_type" id="qtype_dx" type="radio" value="1" onClick="showType('dx', 1);">单选题
            <input name="Question_type" type="radio" value="2" onClick="showType('fx', 2);">多选题
            <input name="Question_type" type="radio" value="3" onClick="showType('ax', 5);">简答题
        </td>
        <td></td>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td></td>
    </tr>
</table>
<%--<div id=qEditor></div>--%>
<div>
    <form name="form1" id="form1" action="<%=basePath%>question/addQuestion.do?op=AddQuestion" method="post">
        <input type="hidden" value=<%=request.getParameter("sid") %>  name="sid">
        <input type="hidden" value="" name="qBody" id="qBody">
        <input type="hidden" value="" name=qResult id="qResult">
        <input name="base_path" id="base_path" type="hidden" value="<%=basePath%>question/addQuestion.do?op=AddQuestion">
        问题：<input name="qHead" type="text" size="40">
        <br/><br/>
        <div id="selector">
            <span>备选项：</span>
            <ul type="1" id="ulAnswer">
                <li><input type="radio"><input type="text" name="Answer"></li>
                <li><input type="radio"><input type="text" name="Answer"></li>
                <li><input type="radio"><input type="text" name="Answer"></li>
                <li><input type="radio"><input type="text" name="Answer"><input id="delete" type="button" value="删除" onclick="deleteLi();"></li>
            </ul>
            <div style="margin: 0 auto; text-align: center">
                <input type="button" onclick="addLi()" value="添加选项">
            </div>

        </div>
        <div id="text_area">
            <span>答题区：</span>
            <div>
                <textarea readonly name="" rows=4 cols=40 onpropertychange='this.style.posHeight=this.scrollHeight'></textarea>
            </div>
        </div>
        <div id="button">
            <input type="button" value="添加题目" onclick="submitForm();">
        </div>
    </form>
</div>
<%--<div id=fx style="display: none">--%>
<%--    <form name="form1" id="form1" action="<%=basePath%>question/addQuestion.do?op=AddQuestion" method="post">--%>
<%--        <input type="hidden" value=<%=request.getParameter("sid") %>  name="sid">--%>
<%--        <input type="hidden" value="" name="qBody" id="qBody">--%>
<%--        <input type="hidden" value="" name=qResult id="qResult">--%>
<%--        问题：<input name="qHead" type="text" size="40">--%>
<%--        <br/><br/>--%>
<%--        备选项：--%>
<%--        <ul type="1" id="ulAnswer">--%>
<%--            <li><input type="checkbox"><input type="text" name="Answer"></li>--%>
<%--            <li><input type="checkbox"><input type="text" name="Answer"></li>--%>
<%--            <li><input type="checkbox"><input type="text" name="Answer"></li>--%>
<%--            <li><input type="checkbox"><input type="text" name="Answer"></li>--%>
<%--        </ul>--%>
<%--        <div id="button">--%>
<%--            <input type="button" value="添加题目" onclick="submitForm();">--%>
<%--        </div>--%>
<%--    </form>--%>
<%--</div>--%>
<%--<div id=ax style="display: none">--%>
<%--    <form name="form1" id="form1" action="<%=basePath%>question/addQuestion.do?op=AddQuestion&type=5" method="post">--%>
<%--        <input type="hidden" value=<%=request.getParameter("sid") %>  name="sid">--%>
<%--        <input type="hidden" value="" name="qBody" id="qBody">--%>
<%--        <input type="hidden" value="" name=qResult id="qResult">--%>
<%--        问题：<input name="qHead" type="text" size="40">--%>
<%--        <br/><br/>--%>
<%--&lt;%&ndash;        备选项：&ndash;%&gt;--%>
<%--&lt;%&ndash;        <ul type="1" id="ulAnswer">&ndash;%&gt;--%>
<%--&lt;%&ndash;            <li><input type="checkbox"><input type="text" name="Answer"></li>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <li><input type="checkbox"><input type="text" name="Answer"></li>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <li><input type="checkbox"><input type="text" name="Answer"></li>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <li><input type="checkbox"><input type="text" name="Answer"></li>&ndash;%&gt;--%>
<%--&lt;%&ndash;        </ul>&ndash;%&gt;--%>
<%--        <div id="button">--%>
<%--            <input type="button" value="添加题目" onclick="submitForm();">--%>
<%--        </div>--%>
<%--    </form>--%>
<%--</div>--%>
<script type="text/javascript">
    document.getElementById("qtype_dx").click();
</script>
</body>
</html>
