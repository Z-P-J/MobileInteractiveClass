<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.iWen.survey.dao.DAOFactory" %>
<%@ page import="com.iWen.survey.dto.Survey" %>
<%@ page import="com.iWen.survey.dao.SurveyDAO" %>
<%@ page import="com.iWen.survey.pager.*" %>
<%@ page import="com.iWen.survey.dao.impl.SurveyDAOimpl" %>
<jsp:useBean id="pageConfig" class="com.iWen.survey.pager.PageConfig"></jsp:useBean>
<jsp:setProperty property="request" name="pageConfig" value="<%=request %>"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/survey/";
%>
<%
    String type = request.getParameter("type");
    SurveyDAOimpl dao = DAOFactory.getSurveyDAO(type);
    PageControl pc = new PageControl(dao, pageConfig, "SurveyAudi.jsp");
    pc.setSizePage(20);
    List<Survey> sList = pc.getRecord();


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title></title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" type="text/css" href="../../assets/survey/css/Admin.css">
    <script language="JavaScript" src="../../assets/survey/js/Func.js"></script>
    <script type="text/javascript" src="../../assets/survey/js/jquery-1.7.2.js"></script>
</head>

<body id="body">
<input name="Survey_type" id="Survey_type" type="hidden" value="<%=type%>">
<div class=nav><a href=admin_main.jsp>桌面</a>»问卷审核
    <hr>
</div>
<table class=table cellspacing="0" cellpadding="0" align="center">
    <tbody>
    <tr>
        <th width=7%>编号</th>
        <th width=20%>问卷名称</th>
        <th width=15%>发起者 <br></th>
        <th>创建时间 <br></th>
        <th>结束日期<br></th>
        <th>通过审核 <br></th>
        <th>操作 <br></th>
    </tr>
    <%
        for (Survey survey : sList) {
    %>
    <tr>
        <td><%=survey.getSId() %>
        </td>
        <td><%=survey.getSName() %>
        </td>
        <td><%=survey.getSAuthor() %>
        </td>
        <td><%=survey.getSCreateDate() %>
        </td>
        <td><%=survey.getSExpireDate() %>
        </td>
        <td><%=survey.getSIsAudited() ? "<img src='" + basePath + "admin/images/on.gif'>" : "<img src='" + basePath + "admin/images/off.gif'>" %>
        </td>
        <td><%=survey.getSIsAudited() ? "<a href='javascript:unaudit(" + survey.getSId() + ")'>禁用" : "<a href='javascript:audit(" + survey.getSId() + ")'>通过" %>
        </td>
    </tr>
    <%} %>
    <tr>
        <td colspan=7 align="right"><%=pc.getCurrentPageHTML() %><%=pc.getCountPageHTML() %>|<%=pc.getFirstPageHTML() %>
            |<%=pc.getPageUpHTML() %>|<%=pc.getPageDownHTML() %>|<%=pc.getLastPageHTML() %>
        </td>
    </tr>
    </tbody>
</table>
</body>
<script>
    window.onload = tableFix;

    function replaceAll(str, oldStr, newStr) {
        var reg = new RegExp( oldStr , "g" );
        return str.replace(reg, newStr);
    }

    var type = $("#Survey_type").val();
    if (type === "vote") {
        var html = $("#body").html();
        $("#body").html(replaceAll(html, "问卷", "投票"));
    }

    function audit(sid) {
        // var type = $("#Survey_type").val();
        var msg = '问卷通过审核之后就不能再被编辑了，问卷即正式生效了，你确定通过审核吗？';
        if (type === "vote") {
            msg = replaceAll(msg, "问卷", "投票");
        }
        if (confirm(msg) === true) {
            window.location = '<%=basePath%>surveyManage/surveyAudi.do?sid=' + sid + '&op=SurveyAudi&audit=true&type=' + type;
        }
    }

    function unaudit(sid) {
        // var type = $("#Survey_type").val();
        var msg = '反审核之后就可以编辑该问卷，但是编辑问卷极有可能破坏数据完整性，在没有系统管理员的指导下，我们强烈建议您不要执行此操作。您确定要执行此操作吗？';
        var alertMsg = '您执行了反审核操作，为了保证系统数据完整性，请立即执行问卷统计模块中的对应问卷的"结果清零"操作！';
        if (type === "vote") {
            msg = replaceAll(msg, "问卷", "投票");
            alertMsg = replaceAll(alertMsg, "问卷", "投票");
        }
        if (confirm(msg) === true) {
            window.location = '<%=basePath%>surveyManage/surveyAudi.do?sid=' + sid + '&op=SurveyAudi&audit=false&type=' + type;
            alert(alertMsg);
        }
    }
</script>
</html>
