<%@page contentType="text/html; charset=UTF-8"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">
		查询考勤
	</h4>
</div>
<div class="modal-body">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet-body form">
				<form class="form-horizontal" role="form" method="post" id="page_form" name="page_form" action="#">
					<input type="hidden" id="action" name="action" value="get_record"/>
					<%--						<input type="hidden" id="id" name="id" value="<%=id %>"/>--%>
					<%--						<input type="hidden" id="exist_resultset" name="exist_resultset" value="<%=existResultset %>"/>--%>
					<div class="form-body">
						<div class="form-group">
							<label id="page_title" class="col-md-12">
								请填写您需要查询的关键词
							</label>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">关键词（必填）<font color="red">*</font></label>
							<div class="col-md-9">
								<input type="text" id="query_keyword" name="query_keyword" class="form-control" placeholder="请填写查询关键词">
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="modal-footer">
	<button id="close_btn" type="button" class="btn btn-default" data-dismiss="modal">
		取消
	</button>
	<button type="button" class="btn btn-success" onclick="query();">
		查询
	</button>
</div>
<div class="row" style="padding: 15px;">
	<div class="col-md-12">
		<div id="record_list_div_2"></div>
	</div>
</div>
<script>

	//这个本页面要编写对应的对象，时间拾取控件
	ComponentsPickers.init();

	// 监听返回，当用户返回时关闭弹窗而不是直接返回当前网页
	$(function(){
		pushHistory();
		window.addEventListener("popstate", function(e) {
			// alert("我监听到了浏览器的返回按钮事件啦");
			if ($("#ajax").is(":hidden")) {
				window.history.go(-1);
			} else {
				$("#close_btn").click();
			}
		}, false);
		function pushHistory() {
			var state = {
				title: "title",
				url: "#"
			};
			window.history.pushState(state, "title", "#");
		}
	});

	// 查询
	function query() {
		var queryKeyword = $("#query_keyword").val();
		if (queryKeyword === "") {
			alert("请输入关键词！");
			return;
		}
		var url = "../../course_servlet"
				+ "?action=query_courses"
				+ "&keyword=" + queryKeyword;
		$.get(url, function (jsonObj) {
			if (jsonObj.result_code === 0) {
				var list = jsonObj.aaData;
				var tip = "当前查询到了 " + list.length + " 条记录";
				var html = "													<div><span id=\"tip_div\" style='color: red'>" + tip + "</span>";
				for (var i = 0; i < list.length; i++) {
					var json = list[i];
					html += Page.showRecord(json);
				}
				html = html + "													</div>";
				// alert(html);
				$("#record_list_div_2").html(html);
			} else {
				alert("查询失败！");
			}
		}).error(function (xhr, errorText, errorType) {
			alert('错误信息：' + errorText + ",错误类型：" + errorType);
		});
	}

</script>