<%@page contentType="text/html; charset=UTF-8"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">
		添加课程
	</h4>
</div>
<div class="modal-body">
	<div class="row">
		<div class="col-md-12">
			<h4>
				请输入课程的信息
			</h4>
			<p>
				选择课程：
				<select class="table-group-action-input form-control input-medium" id="not_joined_course" name="not_joined_course">
					<option value="-1">（无）</option>
				</select>
			</p>
		</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" id="close_btn" class="btn btn-default" data-dismiss="modal">
		关闭
	</button>
	<button type="button" class="btn btn-success" onclick="joinCourse();">
		加入
	</button>
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

	function getCourses() {
		var url = "../../course_servlet?action=query_courses&joined=0";
		$.get(url, function (json) {
			console.log(JSON.stringify(json));
			if (json.result_code === 0) {
				var html = "";
				for (var i = 0; i < json.aaData.length; i++) {
					var course = json.aaData[i];
					html += "<option value=\"" + course.id + "\">课程名:" + course.course_name + " 教师姓名:" + course.teacher_name + "</option>"
				}
				$("#not_joined_course").html(html);
			} else {
				alert("获取课程失败！msg=" + json.result_msg);
			}
		});
	}

	function joinCourse() {
		var courseId = $("#not_joined_course option:selected").val();
		if (courseId === "-1") {
			return;
		}
		var url = "../../course_servlet?action=join_course"
				+ "&course_id=" + courseId;
		$.post(url, function (json) {
			console.log(JSON.stringify(json));
			if (json.result_code === 0) {
				alert("加入课程成功！");
				Record.init();
				$("#close_btn").click();
			} else {
				alert("加入课程失败！msg=" + json.result_msg);
			}
		});
	}

	getCourses();

</script>