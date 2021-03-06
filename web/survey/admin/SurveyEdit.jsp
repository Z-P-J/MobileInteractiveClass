<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.iWen.survey.dao.*" %>
<%@ page import="com.iWen.survey.dto.Survey" %>
<%@ page import="com.iWen.survey.util.*" %>
<%@ page import="com.iWen.survey.dao.impl.SurveyDAOimpl" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/survey/";
%>
<%
    String type = request.getParameter("type");
    String Survey_id = request.getParameter("sid");
    SurveyDAOimpl dao = DAOFactory.getSurveyDAO(type);
    Survey survey = dao.findSurvey(Long.valueOf(Survey_id));
    System.out.println(survey.toString());
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script language="JavaScript" src="../../assets/survey/js/Func.js"></script>
    <script language="javascript">window.onload = tableFix;</script>
    <script type="text/javascript" src="../../assets/survey/js/jquery-1.7.2.js"></script>
    <link rel="stylesheet" href="../../assets/survey/css/Admin.css" type="text/css"/>
    <script language="javascript" type="text/javascript" src="../../assets/survey/js/Date.js"></script>
    <script type="text/javascript">
        var words = "<%=StringUtil.encodeString(request.getParameter("words") ) %>";
        if (words != "null" && words != "")
            alert(words);
    </script>
</head>
<body id="body">
<div id="admin_surveyAdd_main">
    <form name="from1" action="<%=basePath%>surveyManage/editSurvey.do?op=EditSurvey&type=<%=type%>" method="post"
          onSubmit="return CheckForm();">
        <input name="Survey_type" id="Survey_type" type="hidden" value="<%=type%>">
        <table width="585" border="0" cellspacing="0" cellpadding="0" class="table">
            <tr>
                <th>问卷编辑</th>
                <th>&nbsp;</th>
                <th width="374"><span class="R">*</span> 为必填项目</th>
            </tr>
            <tr>
                <td>问卷编号</td>
                <td><input name="Survey_id" value=<%=Survey_id %> type="text" size="50" id="Survey_id" readonly></td>
                <td width="374"></td>
            </tr>
            <tr>
                <td>问卷名称</td>
                <td><input id="Survey_name" name="Survey_name" type="text" size="50" value="<%=survey.getSName().replace("\"","\\\"") %>"></td>
                <td width="374"><span class="R">*</span> 问卷的名称，既问卷的总标题</td>
            </tr>
            <tr>
                <td>问卷发起人(单位)</td>
                <td>
                    <input disabled id="Survey_author" type="text" size="50" value="<%=survey.getSAuthor().replace("\"","\\\"") %>">
                    <input id="Survey_author_hidden" name="Survey_author" type="hidden" value="<%=survey.getSAuthor().replace("\"","\\\"") %>">
                </td>
                <td width="374"><span class="R">*</span> 问卷发起人，此问卷的所有单位</td>
            </tr>
            <tr>
                <td>问卷描述</td>
                <td><textarea rows="2" name="Survey_description" cols="60"
                              onpropertychange="this.style.posHeight=this.scrollHeight"><%=survey.getSDesc() %></textarea>
                </td>
                <td> &nbsp;&nbsp;对此问卷的简单描述，这段描述将放在问卷前</td>
            </tr>
            <tr>
                <td>问卷结束日期</td>
                <td><input type="text" name="Survey_ExpireDate" readOnly="" onClick="setDay(this);"
                           value="<%=survey.getSExpireDate() %>"></td>
                <td><span class="R">*</span> 问卷结束日期，到期后该问卷将不能接受</td>
            </tr>
            <tr>
                <td>公开此问卷调查</td>
                <td><input name="Survey_isOpen" type="checkbox" value=true <%=survey.getSIsOpen()?"checked":"" %>></td>
                <td><span class="R">*</span> 不公开的问卷，只能通过编号进行访问</td>
            </tr>

            <tr>
                <td width="179">&nbsp;</td>
                <td>
                    <div id="button">
                        <input type="submit" name="submit" value="完成"><input type="Button" name="reset1" value="取消"
                                                                             onclick="history.back()">
                    </div>
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
    </form>
</div>
<div id="admin_surveyAdd_bottom">
</div>
</body>
<script>

    function replaceAll(str, oldStr, newStr) {
        var reg = new RegExp( oldStr , "g" );
        return str.replace(reg, newStr);
    }

    if ($("#Survey_type").val() === "vote") {
        var html = $("#body").html();
        // var reg = new RegExp( '问卷' , "g" );
        // html = html.replace(reg, "投票");
        $("#body").html(replaceAll(html, "问卷", "投票"));
    }
</script>
<%=survey.getSImg() == null || "".equals(survey.getSImg()) ? "" : "<script>document.forms[0].Survey_isImg.click();</script>" %>
<%=survey.getSPassword() == null || "".equals(survey.getSPassword()) ? "" : "<script>document.forms[0].Survey_isPassword.click();</script>" %>
</html>