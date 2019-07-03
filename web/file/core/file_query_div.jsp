<%@page contentType="text/html; charset=UTF-8"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">
		查询文件
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
						<div class="form-group">
							<label class="control-label col-md-1" style="display: none;">
								<input type="checkbox" class="icheck" id="record_select_all" name="record_select_all" data-checkbox="icheckbox_minimal-grey" style="border: 0px solid red; display: none;" />
							</label>
							<label class="control-label col-md-3">
								请选择上传的时间段<font color="red">*</font>
							</label>
							<div class="col-md-6">
								<span>从</span>
								<div class="input-group date form_datetime">
									<input type="text" id="time_from" name="time_from" size="16" class="form-control" readonly="true">
									<span class="input-group-btn">
															<button class="btn default date-set" type="button">
																<i class="fa fa-calendar"></i>
															</button> </span>
								</div>
								<span>到</span>
								<div class="input-group date form_datetime">
									<input type="text" id="time_to" name="time_to" size="16" class="form-control" readonly="true">
									<span class="input-group-btn">
															<button class="btn default date-set" type="button">
																<i class="fa fa-calendar"></i>
															</button> </span>
								</div>
								<button type="button" class="btn green-haze btn-circle btn-sm" id="today_button" onclick="todayTime()">今天</button>
								<button type="button" class="btn green-haze btn-circle btn-sm" id="yestoday_button" onclick="yestodayTime()">昨天</button>
								<button type="button" class="btn green-haze btn-circle btn-sm" id="before_yestoday_button" onclick="beforeYestodayTime()">前天</button>
								<button type="button" class="btn green-haze btn-circle btn-sm" id="month_button" onclick="monthTime()">本月</button>
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
		var timeFrom = $("#time_from").val();
		var timeTo = $("#time_to").val();
		var url = "../../file_servlet"
				+ "?action=query_files"
				+ "&keyword=" + queryKeyword
				+ "&time_from=" + timeFrom
				+ "&time_to=" + timeTo;
		$.get(url, function (jsonObj) {
			if (jsonObj.result_code === 0) {

				// Page.showResult(json);
				// alert(JSON.stringify(json));
				if (jsonObj != null) {
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
				}
			} else {
				alert("查询失败！");
			}
		}).error(function (xhr, errorText, errorType) {
			alert('错误信息：' + errorText + ",错误类型：" + errorType);
		});
	}

	function todayTime() {
		//将两个时间控件范围限定为今天
		var today = (new Date()).format("yyyy-MM-dd");
		$("#time_from").val(today + " 00:00:00");
		$("#time_to").val(today + " 23:59:59");
	}
	function yestodayTime () {
		//将两个时间控件范围限定为昨天
		var yestoday = new Date();
		yestoday.setDate(yestoday.getDate() - 1);
		$("#time_from").val(yestoday.format("yyyy-MM-dd") + " 00:00:00");
		$("#time_to").val(yestoday.format("yyyy-MM-dd") + " 23:59:59");
	}
	function beforeYestodayTime () {
		//将两个时间控件范围限定为前天
		var beforeYestoday = new Date();
		beforeYestoday.setDate(beforeYestoday.getDate() - 2);
		$("#time_from").val(beforeYestoday.format("yyyy-MM-dd") + " 00:00:00");
		$("#time_to").val(beforeYestoday.format("yyyy-MM-dd") + " 23:59:59");
	}
	function monthTime () {
		var today = new Date();
		var thisYear = today.getFullYear();
		var thisMonth = today.getMonth() + 1;
		var firstDay = thisYear + '-' + thisMonth + '-01';
		firstDay = ComponentsPickers.formatDate(ComponentsPickers.parseDate(firstDay), "yyyy-MM-dd") + " 00:00:00";
		var nowDay = ComponentsPickers.formatDate(today, "yyyy-MM-dd") + " 23:59:59";
		$("#time_from").val(firstDay);
		$("#time_to").val(nowDay);
	}

	monthTime();

</script>