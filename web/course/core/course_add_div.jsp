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
				课程名：
				<input type="text" class="col-md-12 form-control" id="course_name" name="course_name" placeholder="请输入课程标题">
			</p>
			<p>
				教师名：
				<input type="text" class="col-md-12 form-control" id="teacher_name" name="teacher_name" placeholder="请输入课程标题">
			</p>
		</div>
	</div>
</div>
<div class="modal-footer">
	<button type="button" id="close_btn" class="btn btn-default" data-dismiss="modal">
		关闭
	</button>
	<button type="button" class="btn btn-success" onclick="addCourse();">
		发布
	</button>
</div>
<script>

	//这个本页面要编写对应的对象，时间拾取控件
	ComponentsPickers.init();

	document.getElementById("teacher_name").value = Record.userName;

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


	function addCourse() {
		var courseName = $("#course_name").val();
		var teacherName = $("#teacher_name").val();
		var url = "../../course_servlet?action=add_course"
				// "&user_name=" + Record.userName
				+ "&course_name=" + courseName
				+ "&teacher_name=" + teacherName;
		$.post(url, function (json) {
			console.log(JSON.stringify(json));
			if (json.result_code === 0) {
				alert("发布课程成功！");
				Record.init();
			} else {
				alert("发布课程失败！msg=" + json.result_msg);
			}
		});
	}

</script>