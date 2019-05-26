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
    final String type = request.getParameter("type");
    QuestionDAO dao = DAOFactory.getQuestionDAO();
    PageControl pc = new PageControl(dao, pageConfig, "QuestionAdmin.jsp?rand=1");
    pc.setSizePage(5);
    List<Question> sList = pc.getRecord();
    int size = sList.size();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>My JSP 'SurveyAudi.jsp' starting page</title>
    <link rel="stylesheet" type="text/css" href="../../assets/survey/css/Admin.css">
    <script language="JavaScript" src="../../assets/survey/js/Func.js"></script>
    <script type="text/javascript" src="../../assets/survey/js/jquery-1.7.2.js"></script>
</head>
<body>
<input name="Survey_type" id="Survey_type" type="hidden" value="<%=type%>">
<input name="survey_size" id="survey_size" type="hidden" value="<%=size%>">
<div class="nav" id="nav">
    <a href=admin_main.jsp>桌面</a>»<a href="SurveyAdmin.jsp?type=<%=type%>">问卷列表</a>»题目管理
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
<table id="table" class="table" cellspacing="0" cellpadding="0">
    <tr>
        <th>添加新题目</th>
        <th></th>
        <th></th>
    </tr>
    <tr id="question_type">
        <td>选择题型：</td>
        <td>
            <span><input name="Question_type" id="qtype_dx" type="radio" value="1"
                         onClick="showType('dx', 1);">单选题</span>
            <span id="qtype_fx"><input name="Question_type" type="radio" value="2"
                                       onClick="showType('fx', 2);">多选题</span>
            <span id="qtype_ax"> <input name="Question_type" type="radio" value="3"
                                        onClick="showType('ax', 5);">简答题</span>
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
<div id="add_question" style="margin: 0 auto; display: flex;">
    <iframe name="frame1" frameborder="0" height="0" width="0" style="display: none"></iframe>
    <form name="form1" id="form1" action="<%=basePath%>question/addQuestion.do?op=AddQuestion&type=<%=type%>" method="post"
          style="margin: 0 auto">
        <input type="hidden" value=<%=request.getParameter("sid") %>  name="sid">
        <input type="hidden" value="" name="qBody" id="qBody">
        <input type="hidden" value="" name=qResult id="qResult">
        <input name="base_path" id="base_path" type="hidden"
               value="<%=basePath%>question/addQuestion.do?op=AddQuestion">
        问题：<input name="qHead" type="text" size="40">
        <br/><br/>
        <div id="selector">
            <span>备选项：</span>
            <ul type="1" id="ulAnswer">
                <li><input type="radio"><input type="text" name="Answer"></li>
                <li><input type="radio"><input type="text" name="Answer"></li>
                <li><input type="radio"><input type="text" name="Answer"></li>
                <li><input type="radio"><input type="text" name="Answer"><input id="delete" type="button" value="删除"
                                                                                onclick="deleteLi();"></li>
            </ul>
            <div style="margin: 0 auto; text-align: center">
                <input type="button" onclick="addLi()" value="添加选项">
            </div>

        </div>
        <div id="text_area">
            <span>答题区：</span>
            <div>
                <textarea readonly name="" rows=4 cols=40
                          onpropertychange='this.style.posHeight=this.scrollHeight'></textarea>
            </div>
        </div>
        <div id="button" style="margin: 0 auto; text-align: center">
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
</body>
<script language="JavaScript" src="../../assets/survey/js/QuestAdd.js"></script>
<script type="text/javascript">

    window.onload = tableFix;
    document.getElementById("qtype_dx").click();

    function replaceAll(str, oldStr, newStr) {
        var reg = new RegExp(oldStr, "g");
        return str.replace(reg, newStr);
    }

    var surveyType = $("#Survey_type").val();
    if (surveyType === "vote") {
        // $("#qtype_fx").hide();
        // $("#qtype_ax").hide();
        $("#question_type").hide();
        var html = $("#nav").html();
        $("#nav").html(replaceAll(html, "问卷", "投票"));
        if ($("#survey_size").val() >= 1) {
            $("#table").hide();
            $("#add_question").hide();
        }
    }

    function delQuestion(qid) {
        if (confirm("确定要删除这个题目吗？") == true)
            window.location.replace("<%=basePath%>question/delQuestion.do?type=" + surveyType+ "&qid=" + qid + "&sid=<%=request.getParameter("sid") %>");
    }

    function showType(typecode, type) {
        //typecode: dx--单选 ;fx--复选;
        // document.getElementById("qEditor").innerHTML = document.getElementById(typecode).innerHTML;
        // var basePath = $("#base_path").val();
        document.getElementById("form1").action = $("#form1").attr("action") + "&qType=" + type;
        // alert(document.getElementById("form1").action);
        if (type === 5) {
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
        window.location = setQueryString("qid", qid, sUrl) + "&type=" + surveyType;
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
            // parent.document.getElementById('iframe').contentWindow.location.reload();
        }
    }

    function deleteLi() {
        var len = $("#ulAnswer").children().length - 1;
        if (len === 1) {
            alert("最少两个选项！");
            return;
        }
        if (len >= 0) {
            //表示删除最后一个元素
            $("ul li:eq(" + len + ")").remove();
            if (len - 1 > 0) {
                $("ul li:eq(" + (len - 1) + ")").append("<input id=\"delete\" type=\"button\" value=\"删除\" onclick=\"deleteLi();\">")
            }
        } else {
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

</html>
