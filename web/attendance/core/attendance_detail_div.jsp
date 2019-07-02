<%@page contentType="text/html; charset=UTF-8"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">
		考勤详情
	</h4>
</div>
<div class="modal-body">
	<div class="row">
		<div class="col-md-12">
			<h4>
				考勤的信息
			</h4>
			<p>
				考勤标题：
				<input type="text" class="col-md-12 form-control" id="attendance_title" name="attendance_title" value="">
			</p>
			<p>
				考勤要求：
				<input type="text" class="col-md-12 form-control" id="attendance_requirement" name="attendance_requirement" value="请在规定时间内考勤">
			</p>
			<p>
				发布时间：
				<input type="text" class="col-md-12 form-control" id="publish_time" name="attendance_requirement" value="请在规定时间内考勤">
			</p>
			<p>
				截止时间：
				<input type="text" class="col-md-12 form-control" id="deadline" name="attendance_requirement" value="请在规定时间内考勤">
			</p>
			<p>
				状态：
				<input type="text" class="col-md-12 form-control" id="state" name="attendance_requirement" value="请在规定时间内考勤">
			</p>
		</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" id="close_btn" class="btn btn-default" data-dismiss="modal">
		关闭
	</button>
<%--	<button type="button" class="btn btn-success" data-dismiss="modal" onclick="publishAttendance();">--%>
<%--		发布--%>
<%--	</button>--%>
</div>
<%--<div class="row" style="padding: 15px;">--%>
<%--	<div class="col-md-12">--%>
<%--		<div id="attendanced_user"></div>--%>
<%--	</div>--%>
<%--</div>--%>
<div class="row" style="padding: 15px;">
	<div class="col-md-12">
		<div id="attendance_user"></div>
	</div>
</div>
<script>

	//这个本页面要编写对应的对象，时间拾取控件
	ComponentsPickers.init();

	var flag = compareDate(new Date().format("yyyy-MM-dd hh:mm:ss"), Record.json.deadline);
	var state = "";
	if (flag) {
		state = "已截止";
	} else {
		state = "未截止";
	}
	$("#attendance_title").val(Record.json.attendance_title);
	$("#attendance_requirement").val(Record.json.attendance_requirement);
	$("#publish_time").val(Record.json.publish_time);
	$("#deadline").val(Record.json.deadline);
	$("#state").val(state);


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

	function showStudent(json) {
		var html = "														<div style=\"clear:both;width:100%;margin-top:5px;border:0px solid blue;\">";
		html = html + "															<div style=\"float:left;border:0px solid green;\">";
		html = html + "																<img src=\"../../assets/module/img/public/wkbj.jpg\" style=\"width:100px;height:auto;border-radius:50%!important;border:0px solid red;\"></img>";
		html = html + "															</div>";
		html = html + "															<div style=\"display:table-cell;margin-left:10px;margin-right:10px;margin-top:10px;margin-bottom:10px;border:0px solid blue;\"><p>";
		html = html + "																<span>姓名：" + json.name + "</span><p>";
		html = html + "																<span>学号：" + json.student_num + "</span><p>";
		html = html + "																<span>年级：" + json.grade + "</span><p>";
		html = html + "																<span>班级：" + json.class + "</span><p>";
		html = html + "																<span>学院：" + json.faculty + "</span><p>";
		if (json.has_attendanced) {
			html = html + "																<span>考勤状态：<span>已签到</span></span><p>";
		} else {
			html = html + "																<span>考勤状态：<span style='color: red'>未签到</span></span><p>";
		}

		html = html + "															</div>";
		html = html + "														</div>";
		return html;
	}

	var url = "../../attendance_servlet"
			+ "?action=query_attendance_users"
			+ "&course_id=" + Record.json.course_id
			+ "&attendance_flag=" + Record.json.attendance_flag;
	alert(url);
	$.get(url, function (jsonObj) {
		if (jsonObj.result_code === 0) {
			alert(JSON.stringify(jsonObj));
			var list = jsonObj.aaData;
			var tip = "该课程有 " + list.length + " 名学生";
			var html = "													<div><span id=\"tip_div\" style='color: red'>" + tip + "</span>";
			for (var i = 0; i < list.length; i++) {
				var json = list[i];
				html += showStudent(json);
			}
			html = html + "													</div>";
			$("#attendance_user").html(html);
		} else {
			alert("查询失败！msg=" + jsonObj.result_msg);
		}
	}).error(function (xhr, errorText, errorType) {
		alert('错误信息：' + errorText + ",错误类型：" + errorType);
	});


</script>