<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.iWen.survey.dao.ConfigDAO" %>
<%@ page import="com.iWen.survey.dao.DAOFactory" %>
<%@ page import="com.iWen.survey.dto.Config" %>
<%
    ConfigDAO configdao = DAOFactory.getConfigDAO();
    Config cfg = configdao.findConfig();
%>
<%--<html>--%>
<%--<head>--%>

<%--    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>--%>
<%--    <title><%=cfg.getCSiteName() %>-后台管理</title>--%>
<%--</head>--%>
<%--&lt;%&ndash;<frameset rows="63,*,32" cols="*" frameborder="no" border="0" framespacing="0">&ndash;%&gt;--%>

<%--&lt;%&ndash;    <frame src="admin_top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame"/>&ndash;%&gt;--%>

<%--&lt;%&ndash;    <frameset rows="*" cols="177,*" framespacing="0" frameborder="no" border="0">&ndash;%&gt;--%>
<%--&lt;%&ndash;        <frame src="admin_left.jsp" name='left' scrolling='yes' noresize='noresize'/>&ndash;%&gt;--%>
<%--&lt;%&ndash;        <frame src="admin_main.jsp" name='right' scrolling='yes' noresize='noresize'/>&ndash;%&gt;--%>
<%--&lt;%&ndash;    </frameset>&ndash;%&gt;--%>

<%--&lt;%&ndash;</frameset>&ndash;%&gt;--%>

<%--</html>--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%=cfg.getCSiteName() %>-后台管理</title>

    <style>
        * {
            margin: 0;
            padding: 0
        }

        body, html {
            height: 100%;
            width: 100%;
            overflow: hidden;
        }

        /*这个高度很重要*/
        #frametable .header {
            height: 63px;
            background: #ddd;
            border-bottom: 2px solid #999;
        }

        #frametable .left {
            width: 160px;
            border-right: 2px solid #999;
            background: #ddd;
            height: 100%;
        }
    </style>
</head>
<body style="margin:0"> <!-- 无四个外边距 -->
<table id="frametable" cellpadding="0" cellspacing="0" width="100%" height="100%" style="width: 100%;">
    <tr>
        <td colspan="2" height="63">
            <div class="header">
                <!-- header menu -->
                <iframe src="admin_top.jsp" width="100%" height="63" frameborder="0"></iframe>
            </div>
        </td>
    </tr>
    <tr>
        <td valign="top" width="160" height="100%"> <!--这个高度很重要-->
            <div class="left"><!-- left menu -->
                <iframe src="admin_left.jsp" width="160" height="100%" frameborder="0" scrolling="yes">
                </iframe>
            </div>
        </td>
        <td valign="top" width="100%" height="100%"> <!--这个高度很重要-->
            <iframe id="iframe" name="right" src="admin_main.jsp" allowtransparency="true"
                    width="100%" height="100%" frameborder="0" scrolling="yes" style="overflow:visible;"></iframe>
        </td>
    </tr>
</table>
</body>
</html>