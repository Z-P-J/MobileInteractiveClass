<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%@ page import="com.interactive.classroom.utils.TimeUtil" %>
<%
	//做调试用，这里要在正式发布的时候去掉
	System.out.println("[" + TimeUtil.currentDate() + "]=======================调试：" + request.getServletPath() + "开始==============================");
%>
<%
	String id = request.getParameter("id");
	String existResultset = request.getParameter("exist_resultset");
%>
<!DOCTYPE html>
<head>
	<title>管理系统</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1" name="viewport" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../../home/frame/frame_style.jsp"%>
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
							<div style="text-align: center; color: white;"><h1>作业管理</h1></div>
							<div class="portlet-title">
								<div class="caption">
									<i class="fa fa-gift"></i><span id="title_div">记录显示</span>
								</div>
								<div class="tools">
									<a id="tools_menu_reload" href="" class="reload"> </a>
									<a id="tools_menu_remove" href="" class="remove"> </a>
								</div>
							</div>
							<div class="portlet-body form">
								<form class="form-horizontal" role="form" method="post" id="page_form" name="page_form" action="#">
									<input type="hidden" id="action" name="action" value="get_record" />
									<input type="hidden" id="id" name="id" value="<%=id%>" />
									<input type="hidden" id="exist_resultset" name="exist_resultset" value="<%=existResultset%>" />
									<div class="form-body">
										<div class="form-group">
											<div class="btn-group"
												 style="float: left; margin-bottom: 10px; margin-right: 10px; margin-left: 10px; margin-top: 10px;">
												<a id="search_button_1" class="btn green" href="homework_query_div.jsp"
												   data-target="#ajax" data-toggle="modal">查询作业</a>
											</div>
											<div class="btn-group"
												 style="float: left; margin-bottom: 10px; margin-right: 10px; margin-left: 10px; margin-top: 10px;">
												<a id="publish_homework" class="btn green"
												   href="homework_publish_div.jsp" data-target="#ajax"
												   data-toggle="modal" style="display: none">发布作业 <i class="fa fa-plus" style="display: none"></i> </a>
											</div>

											<div class="btn-group"
												 style="float: left; margin-bottom: 10px; margin-right: 10px; margin-left: 10px; margin-top: 10px;">
												<a id="statistic_attendance" class="btn green"
												   href="../../base/statistic/statistic_div.jsp" data-target="#ajax"
												   data-toggle="modal">作业统计 <i class="fa fa-plus"></i> </a>
											</div>
											<div class="btn-group"
												 style="float: left; margin-bottom: 10px; margin-right: 10px; margin-left: 10px; margin-top: 10px;display: none">
												<a id="update_attendance" class="btn green"
												   href="homework_update_div.jsp" data-target="#ajax"
												   data-toggle="modal">修改作业 <i class="fa fa-plus"></i> </a>
											</div>
											<div style="float: left; margin-bottom: 10px; margin-right: 10px; margin-left: 10px; margin-top: 10px;">
<%--												<button type="button" id="statistic_button" class="btn green" title="统计记录">--%>
<%--													统计报表--%>
<%--												</button>--%>
												<button type="button" id="print_button" class="btn green" title="打印记录">
													打印
												</button>
												<button type="button" id="export_button" class="btn green" title="导出 记录">
													导出
												</button>
											</div>
<%--											<div style="float: left; margin-bottom: 10px; margin-right: 10px; margin-left: 10px; margin-top: 10px;">--%>
<%--												<button type="button" id="return_button" class="btn green" title="返回到前一个页面">--%>
<%--													返回--%>
<%--												</button>--%>
<%--												<button type="button" id="layout_button" class="btn green" title="布局切换">--%>
<%--													<i class="fa fa-th-list"></i>--%>
<%--												</button>--%>
<%--												<button type="button" id="help_button" class="btn green" title="在线帮助">--%>
<%--													<i class="fa fa-question"></i>--%>
<%--												</button>--%>
<%--											</div>--%>
										</div>
										<div class="form-group">
											<label class="control-label col-md-2">排序</label>
											<div class="col-md-2">
												<select class="bs-select form-control" id="sort_01" onchange="Page.sortRecord(1);">
<%--													<option>（请选择）</option>--%>

													<option value="publish_time">按发布时间</option>
													<option value="deadline">按截止时间</option>
                                                    <option value="homework_title">按标题</option>
												</select>
											</div>
<%--											<div class="col-md-2">--%>
<%--												<select class="bs-select form-control" id="sort_02" onchange="Page.sortRecord(2);">--%>
<%--													<option>（请选择）</option>--%>
<%--													<option value="upload_time">按发布日期</option>--%>
<%--													<option value="deadline">按截止日期</option>--%>
<%--                                                    <option value="file_name">按标题</option>--%>
<%--												</select>--%>
<%--											</div>--%>
<%--											<div class="col-md-2">--%>
<%--												<select class="bs-select form-control" id="sort_03" onchange="Page.sortRecord(3);">--%>
<%--													<option>（请选择）</option>--%>
<%--													<option value="upload_time">按发布日期</option>--%>
<%--													<option value="deadline">按截止日期</option>--%>
<%--                                                    <option value="file_name">按标题</option>--%>
<%--												</select>--%>
<%--											</div>--%>
										</div>
									</div>
									<div class="form-body">
										<div class="form-group">
											<label id="record_list_tip" class="col-md-12">
												记录列表
											</label>
										</div>
										<div class="form-group">
											<div class="col-md-12">
												<div id="record_list_div"></div>
											</div>
										</div>
									</div>
								</form>
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
<script src="../../assets/global/plugins/amcharts4/lib/core.js"></script>
<script src="../../assets/global/plugins/amcharts4/lib/charts.js"></script>
<script src="../../assets/global/plugins/amcharts4/lib/themes/material.js"></script>
<script src="../../assets/global/plugins/amcharts4/lib/lang/zh_Hans.js"></script>
<script src="../../assets/global/plugins/amcharts4/lib/themes/animated.js"></script>
<!-- BEGIN PAGE SCRIPTS -->
<script type="text/javascript" src="../../assets/module/scripts/homework/homework_list.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->