<%@page contentType="text/html; charset=UTF-8"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">
		添加考勤
	</h4>
</div>
<div class="modal-body">
	<div class="row">
		<div class="col-md-12">
			<h4>
				请输入考勤的信息
			</h4>
			<p>
				考勤课程：
<%--				<select class="table-group-action-input form-control input-medium" id="attendance_course" name="attendance_course">--%>
<%--					<option value="1">（无）</option>--%>
<%--				</select>--%>
				<input type="text" class="col-md-12 form-control" id="attendance_course" name="attendance_requirement" value="(无)">
			</p>
			<p>
				考勤要求：
				<input type="text" class="col-md-12 form-control" id="attendance_requirement" name="attendance_requirement" value="请在规定时间内考勤">
			</p>
			<p>
				截止时间(默认10分钟后截止)：
				<div class="input-group date form_datetime">
					<input type="text" id="attendance_deadline" name="attendance_deadline" class="form-control" size="16" placeholder="请输入期限完成时间" value=""/>
					<span class="input-group-btn">
						<button class="btn default date-set" type="button">
							<i class="fa fa-calendar"></i>
						</button>
					</span>
				</div>
			</p>
		</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" id="close_btn" class="btn btn-default" data-dismiss="modal">
		关闭
	</button>
	<button type="button" class="btn btn-success" data-dismiss="modal" onclick="publishAttendance();">
		发布
	</button>
</div>
<script>

	//这个本页面要编写对应的对象，时间拾取控件
	ComponentsPickers.init();

	$("#attendance_course").val(Record.courseName);

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

	// 发布考勤
	function publishAttendance() {
		// var courseId = $("#attendance_course option:selected").val();
		var requirement = $("#attendance_requirement").val();
		var deadline = $("#attendance_deadline").val();
		var url = "../../attendance_servlet?action=publish_attendance&course_id=" + Record.courseId
				+ "&requirement=" + requirement
				+ "&deadline=" + deadline;
		$.post(url, function (json) {
			console.log(JSON.stringify(json));
			if (json.result_code === 0) {
				alert("发布考勤成功！");
			} else {
				alert("发布考勤失败！msg=" + json.result_msg);
			}
		});
	}

	var date = new Date();
	date.setTime(date.getTime() + 10 * 60 * 1000);
	document.getElementById("attendance_deadline").value = date.format("yyyy-MM-dd hh:mm:ss")

// //设置当前时间10天以后
// Page.initLimitTime();
// Page.initProjectInfo();
</script>