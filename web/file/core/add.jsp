<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.text.*"%>
<%
	//做调试用，这里要在正式发布的时候去掉
	System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date())+"]=======================调试："+request.getServletPath()+"开始==============================");
%>
<!DOCTYPE html>
	<head>
		<title>管理系统</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta content="width=device-width, initial-scale=1" name="viewport" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@include file="../../home/frame/frame_style.jsp"%>
		<!-- BEGIN PAGE LEVEL STYLES -->
		<link rel="stylesheet" type="text/css" href="../../assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />
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
										<i class="fa fa-gift"></i>记录显示
									</div>
									<div class="tools">
										<a href="" class="reload"> </a>
										<a href="" class="remove"> </a>
									</div>
								</div>
								<div class="portlet-body form">
									<form class="form-horizontal" role="form" id="page_form" name="page_form"  method="post" action="../../UploadServlet" enctype="multipart/form-data">
										<div class="form-body">
											<div class="form-group">
												<div class="form-group">
													<label class="col-md-3 control-label">文件名</label>
													<div class="col-md-4">
														<div class="input-group">
															<input type="text" id="file_name" name="file_name" class="form-control" size="16" placeholder="请选择上传文件" value=""/>
														</div>
													</div>
												</div>
												<div class="form-group">
													<label class="col-md-3 control-label">文件大小</label>
													<div class="col-md-4">
														<div class="input-group">
															<input type="text" id="file_size" name="file_size" class="form-control" size="16" placeholder="上传文件大小" value=""/>
														</div>
														<div class="input-group">
															<input type="file" name="uploadFile"  id="choose_file" value="选择文件"/>
														</div>
													</div>
												</div>
											</div>

										</div>
										<div class="form-actions right1">
											<button type="button" id="submit_button" class="btn green">
												上传文件
											</button>
											<button type="button" id="return_button" class="btn green">
												返回
											</button>
											<button type="button" id="help_button" class="btn green" title="在线帮助">
												<i class="fa fa-question"></i>
											</button>
										</div>
									</form>

<%--									<form class="form-horizontal" role="form" id="page_form" name="page_form" method="post" action="../../UploadServlet" enctype="multipart/form-data">--%>
<%--										选择一个文件:--%>

<%--										<br/><br/>--%>
<%--										文件名--%>
<%--										<label><input type="text" name="fileName" id="file_name" value=""></label><input type="file" name="uploadFile"  id="choose_file"/><br>--%>
<%--										文件路径--%>
<%--										<label><input type="text" name="filePath" id="file_path" value=""></label><br>--%>
<%--										文件大小--%>
<%--										<label><input type="text" name="fileSize" id="file_size" value=""></label><br>--%>
<%--										文件类型--%>
<%--										<label><input type="text" name="fileSize" id="file_type" value=""></label><br>--%>
<%--										<input type="submit" value="上传" />--%>
<%--									</form>--%>

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
<script type="text/javascript" src="../../assets/global/plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="../../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE SCRIPTS -->
<script type="text/javascript" src="../../assets/module/scripts/file/file_add.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
