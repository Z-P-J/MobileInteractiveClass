<%@page contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" type="text/css" href="../../assets/module/css/file/add.css"/>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
	<h4 class="modal-title">
		文件上传
	</h4>
</div>
<div class="modal-body">
	<div class="row">
		<div class="col-md-12">
			<div class="portlet-body form">
				<iframe name="frame1" frameborder="0" height="0" width="0" style="display: none"></iframe>
				<form class="form-horizontal" target="frame1" role="form" id="page_form" name="page_form"  method="post" action="../../UploadServlet?action=upload_file" enctype="multipart/form-data">
					<div class="form-body">
						<div class="form-group">
							<div class="form-group" style="text-align: center">
								<%--													<label class="col-md-3 control-label">文件名</label>--%>
								<div class="col-md-4" style="text-align: center">
									<div class="input-group" style="text-align: center;margin:0 auto;">
										文件名
										<input type="text" id="file_name" name="file_name" class="form-control" size="16" placeholder="请选择上传文件" value=""/>
									</div>
								</div>
							</div>
							<div class="form-group" style="text-align: center">
								<%--													<label class="col-md-3 control-label">文件大小</label>--%>
								<div class="col-md-4">
									<div class="input-group" style="text-align: center;margin:0 auto;">
										文件大小
										<input type="text" id="file_size" name="file_size" class="form-control" size="16" placeholder="上传文件大小" value=""/>
									</div>
									<div class="input-group" style="text-align: center;margin:0 auto;">
										<%--															<input type="file" name="uploadFile"  id="choose_file" value="选择文件" style="text-align: center;margin:0 0;"/>--%>
										<label for="fileinp">
											<input type="button" id="btn_select_file" value="选择文件">
											<input type="file" id="fileinp" name="file">
											<%--																<span id="text">请选择上传文件</span>--%>

										</label>
									</div>
									<div id="wrapper" style="display: none">
										<!--进度条容器-->
										<progress max="100" value="0" id="pg"></progress>
										<span id="uploading">上传中...</span>
									</div>
								</div>
							</div>
						</div>

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
<div class="modal-footer">
	<button type="button" id="close_btn" class="btn btn-default" data-dismiss="modal">
		关闭
	</button>
	<button id="btn_upload" type="button" class="btn btn-success" onclick="uploadFile();">
		上传
	</button>
</div>
<script>

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

	$("#fileinp").change(function (e) {
		$("#text").html($("#fileinp").val());
		console.log(e);

		console.log($(this).val());
		// console.log(getFullPath(this));

		var fileMsg = e.currentTarget.files;
		var fileName = fileMsg[0].name;
		console.log(fileName);
		var fileSize = fileMsg[0].size;
		console.log(fileSize);
		var fileType = fileMsg[0].type;
		console.log(fileType);

		$("#file_name").val(fileName);
		// $("#file_path").val(getFullPath(this));
		$("#file_size").val(fileSize);
		// $("#file_type").val(fileType);
	});

	var btnNode = document.getElementById('btn_select_file');
	var inputNode = document.getElementById('fileinp');
	btnNode.addEventListener('click', function (e) {
		// 模拟input点击事件
		var evt = new MouseEvent("click", {
			bubbles: false,
			cancelable: true,
			view: window
		});
		inputNode.dispatchEvent(evt);
	}, false);

	// 上传文件
	function uploadFile() {
		var fileName = $("#file_name").val();
		if (fileName === "") {
			alert("请选择上传文件！");
			return;
		}
		var fileSize = parseInt($("#file_size").val());
		if (fileSize > 1024 * 1024 * 100) {
			alert("上传文件过大！");
			return;
		}

		$('#page_form').submit();
		document.getElementById('wrapper').style.display = "block";

		var t = setInterval(function(){
			$.ajax({
				url: '../../UploadServlet?action=get_upload_progress&filename=' + fileName,
				type: 'POST',
				dataType: 'text',
				data: {
					filename: fileName
				},
				success: function (responseText) {
					var data = JSON.parse(responseText);
					//前台更新进度
					var progress = parseInt((data.progress / data.size) * 100);
					console.log("progress=" + progress);
					$("#pg").val(progress);

					if (progress >= 0 && progress < 100) {
						$("#uploading").html("上传中..." + progress + "%");
					} else if (progress === 100) {
						$("#uploading").html("上传完成！");
						alert("上传完成！");
					} else if (progress < 0) {
						$("#uploading").html("上传失败！");
						alert("上传失败！");
					}

					if(progress === 100 || progress < 0) {
						// document.getElementById('wrapper').style.display = "none";
						// window.location.reload();
						clearInterval(t);
					}

				},
				error: function(){
					console.log("error");
					$("#uploading").html("上传失败！");
				}
			});
		}, 500);
	}

</script>