var module = "file";
var sub = "core";
jQuery(document).ready(function () {
    Metronic.init(); // init metronic investigation components
    Layout.init(); // init current layout
    QuickSidebar.init(); // init quick sidebar
    Demo.init(); // init demo features
    ComponentsDropdowns.init();
    ComponentsPickers.init();	//这个本页面要编写对应的对象
    Frame.init(module, sub);
    Page.init();
    Record.init();
});
/*================================================================================*/
var Record = function () {
    var userId = undefined;
    var userName = undefined;
    var userRole = undefined;
    var userAvatar = undefined;
    var initRecordStyle = function () {
    };
    var initRecordList = function () {
        // getRecord();
    }
    var getRecord = function () {
        Metronic.startPageLoading({message: '正在查询中，请稍候...'});	//开始等待动画
        var id = $("#id").val();
        var existResultset = $("#exist_resultset").val();
        var url = "../../" + module + "_file_servlet_action?action=get_record&id=" + id + "&exist_resultset=" + existResultset;
        $.post(url, function (json) {
            if (json.result_code == 0) {
                Record.userId = json.user_id;
                Record.userName = json.user_name;
                Record.userRole = json.user_role;
                Record.userAvatar = json.user_avatar;
                Page.showResult(json);
            } else {
                if (Page != null) {
                    Page.processError(json);
                }
            }
            Metronic.stopPageLoading();	//停止等待动画
        }).error(function (xhr, errorText, errorType) {
            alert('错误信息：' + errorText + ",错误类型：" + errorType);
        });
    };
    var viewRecord = function (id) {
        window.location.href = "view.jsp?id=" + id;
    };
    var deleteRecord = function (id) {
        if (confirm("您确定要删除这条记录吗？")) {
            if (id > -1) {
                $.post("../../" + module + "_" + sub + "_servlet_action?action=delete_record&id=" + id, function (json) {
                    if (json.result_code == 0) {
                        var count = json.count;
                        var amount = json.amount;
                        initRecordList();
                        initRecordStyle();
                        alert("已经从数据表删除该记录！");
                    }
                })
            }
        }
    };
    var exportRecord = function () {
        window.location.href = "../../" + module + "_" + sub + "_servlet_action?action=export_files&exist_resultset=1";
    }
    var search = function () {
        page_form.submit();
    }
    return {
        init: function () {
            initRecordList();
            initRecordStyle();
        },
        deleteRecord: function (id) {
            deleteRecord(id);
        },
        viewRecord: function (id) {
            viewRecord(id);
        },
        exportRecord: function () {
            exportRecord();
        },
        search: function () {
            search();
        }
    };
}();//Record;
/* ================================================================================ */
//关于页面的控件生成等操作都放在Page里，和Record独立，Record主要是和记录集交互
var Page = function () {
    var html = "";
    var initPageStyle = function () {
        hideFrameNav();
    };
    var hideFrameNav = function () {
        //根据需要隐藏掉一些不需要的面板
        //隐藏顶部菜单栏
        //$("#page_top_div").hide();
        //隐藏旁边的菜单栏
        //$("#page_sidebar_wrapper_div").hide();
        //隐藏风格设置栏
        $("#style_customizer_div").hide();
        //隐藏page header
        $("#page_header_div").hide();
        //隐藏page 底部
        $("#page_footer_div").hide();
        var displaySidebar = true;
        if (!displaySidebar) {
            if ($("body").hasClass("page-container-bg-solid")) {
                $("body").removeClass("page-sidebar-closed-hide-logo page-container-bg-solid");
                $("body").addClass("page-full-width");
            } else {
            }
        }
        //隐藏提醒图标
        $("#header_inbox_bar").hide();
        $("#header_calendar_bar").hide();
    };
    var processError = function (json) {
        if (Frame != null)
            Frame.processError(json);
    };
    var handleButtonEvent = function () {
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
        var btnNode = document.getElementById('btn');
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
        $('#return_button').click(function () {
            Page.confirmBack();
        });
        $('#submit_button').click(function () {

            var fileName = $("#file_name").val();
            if (fileName == "") {
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
        });
        $('#help_button').click(function () {
            Page.help();
        });
    };
    var showResult = function (json) {
        var title = "记录显示";
        if ($("#title_div")) $("#title_div").html(title);
        if (json != null) {
            var list = json.aaData;
            var tip = "当前查询到了 " + list.length + " 条记录";
            if ($("#tip_div")) $("#tip_div").html(tip);
            if ($("#record_list_tip")) $("#record_list_tip").html(tip);
            showRecordList(list);
        }
    };
    var showRecordList = function (list) {
        for (var i = 0; i < list.length; i++) {
            showRecord(list[i]);
        }
        $("#project_id").html(html);
    };
    var showRecord = function (json) {
        var id = json[0];
        var projectId = json[1];
        var projectName = json[2];
        html = html + "<option value=\"" + projectId + "\">" + projectName + "</option>";
    };
    var help = function () {
        var strUrl = location.pathname;
        window.open('../../help/online/new_win_help.jsp?page_url=' + strUrl, 'big', 'fullscreen=yes');
    }
    var submitRecord = function () {
        if (checkInput() == true) {
            page_form.action = "../../" + module + "_" + sub + "_servlet_action";
            page_form.submit();
        }
    };
    var checkInput = function () {
        var bOk = true;
        var action = $("#action").val();
        if (action == null || action == "") {
            Frame.showMsg("名称不能为空！");
            bOk = false;
        }
        ;
        return bOk;
    };
    var deleteRecord = function (id) {
        Record.deleteRecord(id);
    };
    var viewRecord = function (id) {
        Record.viewRecord(id);
    };
    var modifyRecord = function (id) {
        window.location.href = "view.jsp?id=" + id;
    };
    var searchRecord = function () {
        window.location.href = "query.jsp";
    };
    var confirmBack = function () {
        DraggableDialog.setId("confirm_back");
        DraggableDialog.setCancel(Page.onCancel);
        DraggableDialog.setButtonTitle("确定", "取消");
        DraggableDialog.setOk(Page.returnBack);
        DraggableDialog.showMsg("确定要返回上一页吗？", "提示");
    };
    var initLimitTime = function () {
        var today = new Date();
        var limitDay = ComponentsPickers.formatDate(today, "yyyy-MM-dd") + " 23:59:59";
        $("#end_time").val(limitDay);
    };
    var onCancel = function () {
        DraggableDialog.close();
    }
    var returnBack = function () {
        history.go(-1);
    };
    return {
        init: function () {
            initPageStyle();
            handleButtonEvent();
            initLimitTime();
        },
        processError: function (json) {
            processError(json);
        },
        showResult: function (json) {
            showResult(json);
        },
        showRecordList: function (list) {
            showRecordList(list);
        },
        submitRecord: function () {
            submitRecord();
        },
        addRecord: function () {
            addRecord();
        },
        deleteRecord: function (id) {
            deleteRecord(id);
        },
        viewRecord: function (id) {
            viewRecord(id);
        },
        searchRecord: function () {
            searchRecord();
        },
        exportRecord: function () {
            Record.exportRecord();
        },
        reload: function () {
            window.location.reload();
        },
        confirmBack: function () {
            confirmBack();
        },
        returnBack: function () {
            returnBack();
        },
        onCancel: function () {
            onCancel();
        },
        help: function () {
            help();
        }
    }
}();//Page
/*================================================================================*/
