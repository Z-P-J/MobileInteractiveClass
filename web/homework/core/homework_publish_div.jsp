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
				请输入作业的信息
			</h4>
			<p>
				作业课程：
				<select class="table-group-action-input form-control input-medium" id="homework_course" name="homework_course">
					<option value="1">（无）</option>
				</select>
			</p>
			<p>
				作业标题：
				<input type="text" class="col-md-12 form-control" id="homework_title" name="homework_title" placeholder="请输入作业标题">
			</p>
			<p>
				作业要求：
				<input type="text" class="col-md-12 form-control" id="homework_requirement" name="homework_requirement" value="作业内容：xxxx，请在规定时间内完成">
			</p>
			<p>
				截止时间（默认一周后截止）：
				<div class="input-group date form_datetime">
					<input type="text" id="homework_deadline" name="homework_deadline" class="form-control" size="16" placeholder="请输入期限完成时间" value=""/>
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
	<button type="button" class="btn btn-success" data-dismiss="modal" onclick="publishHomework();">
		发布
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

	function getAllCourse() {
		$.get("../../course_servlet?action=query_courses", function (json) {
			console.log(JSON.stringify(json));
			if (json.result_code === 0) {
				var html = "";
				for (var i = 0; i < json.aaData.length; i++) {
					var course = json.aaData[i];
					if (i === 0) {
						$("#homework_title").val(course.course_name + "作业")
					}
					// alert(JSON.stringify(course));
					html += "<option value=\"" + course.id + "\">" + course.course_name + "</option>"
				}
				$("#homework_course").html(html);
			} else {
				alert("获取所有课程失败！msg=" + json.result_msg);
			}
		});
	}

	// 发布考勤
	function publishHomework() {
		var courseId = $("#homework_course option:selected").val();
		var title = $("#homework_title").val();
		var requirement = $("#homework_requirement").val();
		var deadline = $("#homework_deadline").val();
		var url = "../../homework_servlet?action=publish_homework&course_id=" + courseId
				+ "&title=" + title
				+ "&requirement=" + requirement
				+ "&deadline=" + deadline;
		$.post(url, function (json) {
			console.log(JSON.stringify(json));
			if (json.result_code === 0) {
				alert("发布作业成功！");
				Record.init();
			} else {
				alert("发布作业失败！msg=" + json.result_msg);
			}
		});
	}

	getAllCourse();

	var date = new Date();
	date.setTime(date.getTime() + 7 * 24 * 60 * 60 * 1000);
	document.getElementById("homework_deadline").value = date.format("yyyy-MM-dd hh:mm:ss")

</script>