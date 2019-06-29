<%--
  Created by IntelliJ IDEA.
  User: Z-P-J
  Date: 2019/6/17
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
p<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%
    String homeworkId = request.getParameter("homework_id");
    String userName = session.getAttribute("user_name") == null ? null : (String) session.getAttribute("user_name");
    String studentNum = session.getAttribute("student_num") == null ? null : (String) session.getAttribute("student_num");
%>
<!DOCTYPE html>
<head>
    <title>管理系统</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@include file="../../home/frame/frame_style.jsp"%>
    <link rel="stylesheet" type="text/css" href="../../assets/module/css/file/add.css"/>
    <link rel="stylesheet" type="text/css" href="../../assets/module/css/base/progress_bar.css"/>
    <!-- BEGIN PAGE LEVEL STYLES -->
    <!-- END PAGE LEVEL SCRIPTS -->
</head>
<body class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo page-container-bg-solid">
<%@include file="../../home/frame/frame_top.jsp"%>
<div class="clearfix"></div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
    <%@include file="../../home/frame/frame_left.jsp"%>
    <div class="page-content-wrapper">
        <!-- BEGIN CONTENT -->
        <div class="page-content">
            <%@include file="../../home/frame/frame_portlet.jsp"%>
            <%@include file="../../home/frame/frame_theme_color.jsp"%>
            <%@include file="../../home/frame/frame_page_header.jsp"%>
            <!-- BEGIN PAGE CONTENT-->
            <!-- ----------------------------------------页面开始，替换这里即可---------------------------------------- -->
            <div class="row">
                <div class="col-md-12">
                    <div class="portlet box blue ">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa fa-gift"></i><span id="title_div2">作业详情</span>
                            </div>
                            <div style="text-align: center; color: white;">
                                <h1 id="homework_detail_title">作业详情</h1>
                            </div>
                            <div class="tools">
                                <a id="tools_menu_reload" href="" class="reload"> </a>
                                <a id="tools_menu_remove" href="" class="remove"> </a>
                            </div>
                        </div>
                        <div class="portlet-body form">
                            <iframe name="frame1" frameborder="0" height="0" width="0" style="display: none"></iframe>
                            <form class="form-horizontal" target="frame1" role="form" method="post" name="page_form" id="page_form"  method="post" action="../../UploadServlet?homework_id=<%=homeworkId%>&from=homework" enctype="multipart/form-data">
                                <input type="hidden" id="action" name="action" value="get_record" />
                                <input type="hidden" id="homework_id" name="homework_id" value="<%=homeworkId%>" />
                                <input type="hidden" id="file_size_hidden" name="file_size_hidden" value=""/>
                                <input type="hidden" id="user_name" name="user_name" value="<%=userName%>"/>
                                <input type="hidden" id="student_num" name="student_num" value="<%=studentNum%>"/>
                                <input type="hidden" id="rename_to" name="rename_to" value=""/>
                                <input type="hidden" id="has_upload" name="has_upload" value="0"/>
                                <div class="form-body">
                                    <div class="form-group" style="margin: 0 auto">
                                        <div id="homework_detail_div" style="margin: 0 auto;text-align: center; border: lightgrey solid">
                                            <%--												<span>作业要求:xxxxxxxxxxxxxxxxxxxxxx</span><p>--%>
                                            <%--												<span>上传文件格式:xxxxxxxxxxxx</span><p>--%>
                                            <%--												<span>发布时间:2019-05-13 11:10:16</span><p>--%>
                                            <%--												<span>截止时间:<p id="deadline">2019-05-2 12:00:00</p></span><p>--%>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="form-group" style="text-align: center; margin:0 auto;display: flex;">
                                            <%--													<label class="col-md-3 control-label">文件名</label>--%>
                                            <div class="col-md-4" style="text-align: center;margin:0 auto;">
                                                <div class="input-group" style="text-align: center;margin:0 auto;">
                                                    文件名
                                                    <input type="text" id="file_name" name="file_name" class="form-control" size="16" placeholder="请选择上传文件" value=""/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group" style="text-align: center; margin:0 auto;display: flex;">
                                            <%--													<label class="col-md-3 control-label">文件大小</label>--%>
                                            <div class="col-md-4" style="text-align: center;margin:0 auto;">
                                                <div class="input-group" style="text-align: center;margin:0 auto;">
                                                    文件大小
                                                    <input type="text" id="file_size" name="file_size" class="form-control" size="16" placeholder="上传文件大小" value=""/>

                                                </div>
                                                <span id="span_tip"></span>
                                                <div class="input-group" style="text-align: center;margin:0 auto;">
                                                    <%--															<input type="file" name="uploadFile"  id="choose_file" value="选择文件" style="text-align: center;margin:0 0;"/>--%>
                                                    <label for="fileinp">
                                                        <input type="button" id="btn" class="btn_input" value="选择文件">
                                                        <input type="file" id="fileinp" name="fileinp">
                                                        <%--																<span id="text">请选择上传文件</span>--%>
                                                        <input type="button" id="submit_button" class="btn_input" value="上传文件">
                                                    </label>
                                                </div>
                                                <!--外层容器-->
                                                <div id="wrapper">
                                                    <!--进度条容器-->


                                                    <progress max="100" value="0" id="pg"></progress>
                                                    <span id="uploading">上传中...</span>
                                                    <%--														<div id="progressbar">--%>

                                                    <%--															<!--用来模仿进度条推进效果的进度条元素-->--%>
                                                    <%--&lt;%&ndash;															<div id="fill"></div>&ndash;%&gt;--%>
                                                    <%--															--%>
                                                    <%--														</div>--%>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%--									<div class="form-actions right1">--%>
                                <%--										<button type="button" id="submit_button" class="btn green">--%>
                                <%--											上传文件--%>
                                <%--										</button>--%>
                                <%--										<button type="button" id="return_button" class="btn green" title="返回到前一个页面">--%>
                                <%--											返回--%>
                                <%--										</button>--%>
                                <%--										<button type="button" id="layout_button" class="btn green" title="布局切换">--%>
                                <%--											<i class="fa fa-th-list"></i>--%>
                                <%--										</button>--%>
                                <%--										<button type="button" id="help_button" class="btn green" title="在线帮助">--%>
                                <%--											<i class="fa fa-question"></i>--%>
                                <%--										</button>--%>
                                <%--									</div>--%>

                                <hr style="background: black; color: black">
                                <div class="form-body">
                                    <div class="form-group">
                                        <%--											<label id="record_list_tip" class="col-md-12" style="text-align: center;">--%>
                                        <%--&lt;%&ndash;												记录列表&ndash;%&gt;--%>

                                        <%--											</label>--%>
                                        <div style="text-align: center; color: black;">
                                            <h1 id="homework_uploaded_title">已提交的作业</h1>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-2">排序</label>
                                        <div class="col-md-2">
                                            <select class="bs-select form-control" id="sort_01" onchange="Page.sortRecord(1);">
<%--                                                <option>（请选择）</option>--%>
                                                <option value="upload_time">按日期</option>
                                                <option value="file_name">按标题</option>
                                                <option value="file_size">按大小</option>
                                            </select>
                                        </div>
<%--                                        <div class="col-md-2">--%>
<%--                                            <select class="bs-select form-control" id="sort_02" onchange="Page.sortRecord(2);">--%>
<%--                                                <option>（请选择）</option>--%>
<%--                                                <option value="upload_time">按日期</option>--%>
<%--                                                <option value="file_name">按标题</option>--%>
<%--                                                <option value="file_size">按大小</option>--%>
<%--                                            </select>--%>
<%--                                        </div>--%>
<%--                                        <div class="col-md-2">--%>
<%--                                            <select class="bs-select form-control" id="sort_03" onchange="Page.sortRecord(3);">--%>
<%--                                                <option>（请选择）</option>--%>
<%--                                                <option value="upload_time">按日期</option>--%>
<%--                                                <option value="file_name">按标题</option>--%>
<%--                                                <option value="file_size">按大小</option>--%>
<%--                                            </select>--%>
<%--                                        </div>--%>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-md-12">
                                            <div id="record_list_div"></div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <%--								<form class="form-horizontal" role="form" id="page_form" name="page_form"  method="post" action="../../UploadServlet" enctype="multipart/form-data">--%>
                            <%--									--%>
                            <%--								</form>--%>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ----------------------------------------页面结束，替换上面即可---------------------------------------- -->
            <!-- END PAGE CONTENT-->
        </div>
        <!-- END CONTENT -->
        <%@include file="../../home/frame/frame_sidebar.jsp"%>
    </div>
    <!-- END CONTENT WRAPPER-->
</div>
<!-- END CONTAINER -->
<%@include file="../../home/frame/frame_bottom.jsp"%>
<%@include file="../../home/frame/frame_ajax_modal.jsp"%>
</body>
</html>
<%@include file="../../home/frame/frame_page_component.jsp"%>
<%@include file="../../home/frame/frame_javascript.jsp"%>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE SCRIPTS -->
<script type="text/javascript" src="../../assets/module/scripts/attendance/attendance_detail.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
