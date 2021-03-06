<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.text.*" %>
<%
    String id = request.getParameter("id");
    String attendanceId = request.getParameter("homework_id");
    if (attendanceId == null) {
        attendanceId = "";
    }
    String existResultset = request.getParameter("exist_resultset");
%>
<!DOCTYPE html>
<head>
    <title>管理系统</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="../../home/frame/frame_style.jsp" %>
    <!-- BEGIN PAGE LEVEL STYLES -->
    <link rel="stylesheet" type="text/css"
          href="../../assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
    <!-- END PAGE LEVEL SCRIPTS -->
</head>
<body class="page-header-fixed page-quick-sidebar-over-content page-sidebar-closed-hide-logo page-container-bg-solid">
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
            <div class="row">
                <div class="col-md-12">
                    <div class="portlet box blue ">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa fa-gift"></i>记录显示
                            </div>
                            <div class="tools">
                                <a href="" class="reload"> </a>
                                <a href="" class="remove"> </a>
                            </div>
                        </div>
                        <div class="portlet-body form">
                            <form class="form-horizontal" role="form" id="page_form" name="page_form" action="#">
                                <input type="hidden" id="action" name="action" value="modify_record"/>
                                <input type="hidden" id="id" name="id" value="<%=id%>"/>
                                <input type="hidden" id="homework_id" name="homework_id" value="<%=attendanceId%>"/>
                                <input type="hidden" id="exist_resultset" name="exist_resultset"
                                       value="<%=existResultset%>"/>
                                <input type="hidden" id="file_id" name="file_id" value="" />
                                <div class="form-body">
                                    <div class="form-group">
                                        <label class="col-md-3" id="record_list_tip">
                                        </label>
                                    </div>
                                    <%--                                    <div class="form-group">--%>
                                    <%--                                        <label class="col-md-3 control-label">--%>
                                    <%--                                            项目名称--%>
                                    <%--                                        </label>--%>
                                    <%--                                        <div class="col-md-9">--%>
                                    <%--                                            <select class="table-group-action-input form-control input-medium"--%>
                                    <%--                                                    id="project_id_select" name="project_id_select">--%>
                                    <%--                                                <option value="1">--%>
                                    <%--                                                    （无）--%>
                                    <%--                                                </option>--%>
                                    <%--                                            </select>--%>
                                    <%--                                        </div>--%>
                                    <%--                                    </div>--%>
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">
                                            文件名（必填）
                                            <font color="red">*</font>
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" id="file_name" name="file_name" class="form-control"
                                                   placeholder="请填写文件名" value=""/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">
                                            上传者
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" id="uploader" name="uploader" class="form-control"
                                                   placeholder="请输入上传者" value=""/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">
                                            文件大小（必填）
                                            <font color="red">*</font>
                                        </label>
                                        <div class="col-md-9">
                                            <input type="text" id="file_size" name="file_size" class="form-control"
                                                   placeholder="请填写文件大小" value=""/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">
                                            上传时间
                                        </label>
                                        <div class="col-md-4">
                                            <div class="input-group date form_datetime">
                                                <input type="text" id="upload_time" name="upload_time"
                                                       class="form-control"
                                                       size="16" placeholder="请输入上传日期" value=""/>
                                                <span class="input-group-btn">
														<button class="btn default date-set" type="button">
															<i class="fa fa-calendar"></i>
														</button> </span>
                                            </div>
                                        </div>
                                        之前
                                    </div>
                                </div>
                                <div class="form-actions right1">
                                    <button type="button" id="first_button" class="btn green" title="跳到首记录">
                                        第一个
                                    </button>
                                    <button type="button" id="prev_button" class="btn green" title="跳到前一个记录">
                                        前一个
                                    </button>
                                    <button type="button" id="next_button" class="btn green" title="跳到后一个记录">
                                        后一个
                                    </button>
                                    <button type="button" id="last_button" class="btn green" title="跳到最后一条记录">
                                        最后一个
                                    </button>
                                    <%--										<label class="btn red">注意：单页浏览设计得过于复杂难懂，仅作为进阶参考，初步入门可不管这个</label>--%>
                                </div>
                                <div class="form-actions right1">
                                    <%--                                    <button type="button" id="submit_button" class="btn green" title="把修改好的信息提交到后台保存">--%>
                                    <%--                                        提交修改--%>
                                    <%--                                    </button>--%>
                                    <button type="button" id="return_button" class="btn green">
                                        返回
                                    </button>
                                </div>
                                <div style="text-align: center; color: black;"><h1>评论</h1></div>
                                <div id="comment">
                                    <div class="word" id="comment-list">
                                    </div>

                                    <div class="bottom" id="wenda_input">
                                        <div class="st2">
                                            <textarea style="border: 1px solid #7bc71f;width: 98%;height: 90px;padding: 5px 1%;overflow: hidden;color: #444;line-height: 24px;font-size: 14px;border-radius: 3px;" name="content" id="comment_text" class="Insider_box"></textarea>
                                        </div>
                                        <div class="st3">
                                            <input name="objID" type="hidden" id="objID" value="1554971">
                                            <input name="objType" type="hidden" id="objType" value="1">
                                            <p><input type="button"
                                                      value="提交评论"
                                                      id="submit_comment"
                                                      class="btms"
                                                      style="display: block;width: 96px;height: 32px;line-height: 32px;color: #fff;font-size: 16px;background: #7bc71f;margin: 0;padding: 0;text-align: center;border-radius: 4px;float: right;border: 0;cursor: pointer;">
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <div id="comment_list_div"></div>
                                    </div>
                                </div>
                                <%--                                <div style="clear:both;width:auto;height:100px;margin:5px;border:1px solid lightgrey;text-align: center;">--%>
                                <%--                                    <div style="float:left;border:0px solid green;text-align: center;margin:5px;">--%>
                                <%--                                        <img src="../../assets/module/img/security/user/avatar/avatar.jpg" style="width:18px;height:auto;border-radius:50%!important;border:0px solid red;">--%>
                                <%--&lt;%&ndash;                                        <img src="" style="width:18px;height:auto;border-radius:50%!important;border:0px solid red;">&ndash;%&gt;--%>
                                <%--                                        <div style="text-align: center; color: black;"><p>生来彷徨</p></div>--%>
                                <%--                                        <div class="comt-meta" ><span class="comt-author"></span>2019-05-07 01:16:16</div>--%>
                                <%--                                    </div>--%>
                                <%--                                    <div style="width:auto;height:100px;display:table-cell;margin-left:10px;margin-right:10px;margin-top:10px;margin-bottom:10px;border:0px solid blue;text-align: center;">--%>
                                <%--                                        <p><span>不错不错不错</span></p>--%>
                                <%--                                    </div>--%>
                                <%--                                </div>--%>
                            </form>
                        </div>
                    </div>
                </div>
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
<script type="text/javascript" src="../../assets/global/plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script type="text/javascript"
        src="../../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE SCRIPTS -->
<script src="../../assets/module/scripts/file/file_view.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
