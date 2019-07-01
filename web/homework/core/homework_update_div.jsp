<%@page contentType="text/html; charset=UTF-8"%>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">
		更新作业信息
	</h4>
</div>
<div class="modal-body">
	<div class="row">
		<div class="col-md-12">
			<h4>
				作业的信息
			</h4>
<%--			<p>--%>
<%--				作业课程：--%>
<%--				<select class="table-group-action-input form-control input-medium" id="homework_course" name="homework_course">--%>
<%--					<option value="1">（无）</option>--%>
<%--				</select>--%>
<%--			</p>--%>
			<p>
				作业标题：
				<input type="text" class="col-md-12 form-control" id="homework_title" name="homework_title" placeholder="请输入作业标题">
			</p>
			<p>
				作业要求：
				<input type="text" class="col-md-12 form-control" id="homework_requirement" name="homework_requirement" value="作业内容：xxxx，请在规定时间内完成">
			</p>
			<p>
				发布时间：
			<div class="input-group date form_datetime">
				<input type="text" id="homework_publish_time" name="homework_publish_time" class="form-control" size="16" placeholder="请输入期限完成时间" valude="" disabled />
				<span class="input-group-btn">
						<button class="btn default date-set" type="button" style="display: none">
							<i class="fa fa-calendar"></i>
						</button>
					</span>
			</div>
			</p>
			<p>
				截止时间：
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
	<button type="button" class="btn btn-success" data-dismiss="modal" onclick="updateHomework();">
		更新
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

	function getHomework() {
		$.get("../../homework_servlet?action=get_homework_detail&homework_id=" + Record.homeworkId, function (json) {
			console.log(JSON.stringify(json));
			if (json.result_code === 0) {

				if (json.aaData.length === 0) {
					alert("课程不存在！");
				} else {
					var homework = json.aaData[0]
					$("#homework_title").val(homework.homework_title);
					$("#homework_requirement").val(homework.homework_requirement);
					$("#homework_publish_time").val(homework.publish_time);
					$("#homework_deadline").val(homework.deadline);
					// $("#").val();
				}
			} else {
				alert("获取所有课程失败！msg=" + json.result_msg);
			}
		});
	}

	// 发布考勤
	function updateHomework() {
		var title = $("#homework_title").val();
		var requirement = $("#homework_requirement").val();
		var publishTime = $("#homework_publish_time").val();
		var deadline = $("#homework_deadline").val();
		var url = "../../homework_servlet?action=update_homework&id=" + Record.homeworkId
				+ "&title=" + title
				+ "&requirement=" + requirement
				+ "&publish_time=" + publishTime
				+ "&deadline=" + deadline;
		$.post(url, function (json) {
			console.log(JSON.stringify(json));
			if (json.result_code === 0) {
				alert("更新作业成功！");
				Record.init();
			} else {
				alert("更新作业失败！msg=" + json.result_msg);
			}
		});
	}

	getHomework();

	// var date = new Date();
	// date.setTime(date.getTime() + 7 * 24 * 60 * 60 * 1000);
	// document.getElementById("homework_deadline").value = date.format("yyyy-MM-dd hh:mm:ss")

</script>