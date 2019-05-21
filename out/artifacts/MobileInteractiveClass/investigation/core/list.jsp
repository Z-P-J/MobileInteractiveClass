<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.text.*" %>
<%
    //做调试用，这里要在正式发布的时候去掉
    System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date()) + "]=======================调试：" + request.getServletPath() + "开始==============================");
%>
<%
    String id = request.getParameter("id");
    String existResultset = request.getParameter("exist_resultset");
    String type = request.getParameter("type");
%>
<!DOCTYPE html>
<head>
    <title>管理系统</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="../../home/frame/frame_style.jsp" %>
    <!-- BEGIN PAGE LEVEL STYLES -->
    <!-- END PAGE LEVEL SCRIPTS -->
</head>
<body class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo page-container-bg-solid">
<input type="hidden" name="type" id="type" value="<%=type%>">
<%@include file="../../home/frame/frame_top.jsp" %>
<div class="clearfix"></div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
    <%@include file="../../home/frame/frame_left.jsp" %>
    <div class="page-content-wrapper">
        <!-- BEGIN CONTENT -->
        <div class="page-content">
            <%@include file="../../home/frame/frame_portlet.jsp" %>
            <%@include file="../../home/frame/frame_theme_color.jsp" %>
            <%@include file="../../home/frame/frame_page_header.jsp" %>
            <!-- BEGIN PAGE CONTENT-->
            <!-- ----------------------------------------页面开始，替换这里即可---------------------------------------- -->
            <div style="margin: 0 auto;  width: 100%; height: 800px">
                <iframe id="iframe" name="right" src="all.jsp?type=<%=type%>"
                        allowtransparency="true" marginwidth='0' marginheight='0' width="100%" height="100%"
                        frameborder="0" scrolling="yes"></iframe>
            </div>

            <!-- ----------------------------------------页面结束，替换上面即可---------------------------------------- -->
            <!-- END PAGE CONTENT-->
        </div>
        <!-- END CONTENT -->
        <%@include file="../../home/frame/frame_sidebar.jsp" %>
    </div>
    <!-- END CONTENT WRAPPER-->
</div>
<!-- END CONTAINER -->
<%@include file="../../home/frame/frame_bottom.jsp" %>
<%@include file="../../home/frame/frame_ajax_modal.jsp" %>
</body>
</html>
<%@include file="../../home/frame/frame_page_component.jsp" %>
<%@include file="../../home/frame/frame_javascript.jsp" %>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE SCRIPTS -->
<script type="text/javascript" src="../../assets/module/scripts/investigation/investigation_list.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->