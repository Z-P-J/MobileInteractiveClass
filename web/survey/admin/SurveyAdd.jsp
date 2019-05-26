<%@ page language="java" import="java.util.*,com.iWen.survey.sql.*,com.iWen.survey.dao.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/survey/";
    String type = request.getParameter("type");
    String action = basePath + "surveyManage/addSurvey.do?op=AddSurvey&type=" + type;
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script language="JavaScript" src="../../assets/survey/js/Func.js"></script>
    <script language="javascript">window.onload = tableFix;</script>
    <script type="text/javascript" src="../../assets/survey/js/jquery-1.7.2.js"></script>
    <link rel="stylesheet" href="../../assets/survey/css/Admin.css" type="text/css"/>
    <script language="javascript" type="text/javascript" src="../../assets/survey/js/Date.js"></script>
</head>
<body id="body">
<div class=nav><a href=admin_main.jsp>桌面</a>»问卷添加
    <hr>
</div>
<form name="from1" action="<%=action%>" method="post"
      onSubmit="return CheckForm();">
    <input name="Survey_type" id="Survey_type" type="hidden" value="<%=type%>">
    <table width="585" border="0" cellspacing="0" cellpadding="0" class="table">
        <tr>
            <th>问卷添加</th>
            <th>&nbsp;</th>
            <th width="374"><span class="R">*</span> 为必填项目</th>
        </tr>
        <tr>
            <td>问卷名称</td>
            <td><input name="Survey_name" type="text" size="50"></td>
            <td width="374"><span class="R">*</span> 问卷的名称，既问卷的总标题</td>
        </tr>
        <tr>
            <td>问卷发起人(单位)</td>
            <td><input disabled name="Survey_author" id="survey_author" type="text" size="50"
                       value="<%=session.getAttribute("user_name")%>"></td>
            <td width="374"><span class="R">*</span> 问卷发起人，此问卷的发布者，不可更改</td>
        </tr>
        <tr>
            <td>问卷描述</td>
            <td><textarea rows="2" name="Survey_description" cols="60"
                          onpropertychange="this.style.posHeight=this.scrollHeight"></textarea></td>
            <td> &nbsp;&nbsp;对此问卷的简单描述，这段描述将放在问卷前</td>
        </tr>
        <tr>
            <td>问卷结束日期</td>
            <td><input type="text" name="Survey_ExpireDate" readOnly="" onClick="setDay(this);"
                       value="<%=new java.sql.Date(System.currentTimeMillis() +1000L*60*60*24*30)%>"></td>
            <td><span class="R">*</span> 问卷结束日期，到期后该问卷将不能接受</td>
        </tr>

        <tr>
            <td>公开此问卷调查</td>
            <td><input name="Survey_isOpen" type="checkbox" value="true" checked="checked"></td>
            <td><span class="R">*</span> 不公开的问卷，只能通过编号进行访问</td>
        </tr>
        <tr>
            <td width="">为问卷添加主题图片</td>
            <td width=""><input type="checkbox" name="Survey_isImg" value="true"
                                onClick="SwitchHidden('hidden_isImg');">
                <div id="hidden_isImg" style="display:none;">
                    <input type=hidden name="imgfilepath" id="imgfilepath">
                    <iframe name=myiframe src="upload.jsp" frameborder="0" width="400" height="50"></iframe>
                </div>
            </td>
            <td width=""> &nbsp;&nbsp;主题图片的格式必须为.JPG或GIF</td>
        </tr>

        <tr>
            <td>设置问卷密码</td>
            <td><input type="checkbox" name="Survey_isPassword" value=true onClick="SwitchHidden('hidden_isPassword');">
                <div id="hidden_isPassword" style="display:none;">
                    输入密码：<input type="password" name="Survey_Password1" id="Survey_Password1"><br/>
                    确认密码：<input type="password" name="Survey_Password2" id="Survey_Password2"
                                onBlur="return CheckPassword('Survey_Password1','Survey_Password2','4');">
                </div>
            </td>
            <td> &nbsp;&nbsp;选中此项，只有输入正确的密码才能完成问卷</td>
        </tr>
        <tr>
            <td width="179">&nbsp;</td>
            <td>
                <div id="button">
                    <input type="submit" name="submit" value="完成">
                    <input type="button" onclick="javascript:history.back();" value="取消">

                </div>
            </td>
            <td>&nbsp;</td>
        </tr>
    </table>
</form>
</body>
<script>
    if ($("#Survey_type").val() === "vote") {
        var html = $("#body").html();
        var reg = new RegExp('问卷', "g");
        html = html.replace(reg, "投票");
        $("#body").html(html);
    }
</script>
</html>