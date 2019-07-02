var module = "homework";
var sub = "core";

// var progressbar={
//     init:function(){
//         var fill=document.getElementById('fill');
//         var count=0;
//         //通过间隔定时器实现百分比文字效果,通过计算CSS动画持续时间进行间隔设置
//         var timer=setInterval(function(e){
//             count++;
//             fill.innerHTML=count+'%';
//             $("#pg").val(count);
//             if(count===100) clearInterval(timer);
//         },17);
//     }
// };
// progressbar.init();

function parseDate(date) {
    var t = Date.parse(date);
    if (!isNaN(t)) {
        return new Date(Date.parse(date.replace(/-/g, "/")))
    } else {
        return new Date();
    }
}

function compareDate(s1, s2) {
    return ((new Date(s1.replace(/-/g, "\/"))) > (new Date(s2.replace(/-/g, "\/"))));
}

function checkDeadline() {
    var time = new Date().format("yyyy-MM-dd hh:mm:ss");
    var deadline = $("#deadline").html();
    var a = compareDate(time, deadline);
    // alert("time=" + time + " deadline=" + deadline + "" + a);
    if (a) {
        alert("提交作业时间已截止！");
        return true;
    }
    return false;
}

jQuery(document).ready(function () {
    Metronic.init(); // init metronic investigation components
    Layout.init(); // init current layout
    QuickSidebar.init(); // init quick sidebar
    Demo.init(); // init demo features
    ComponentsDropdowns.init();
    Frame.init(module, sub);
    Page.init();
    Record.init();
});
/*================================================================================*/
var Record = function () {
    var init = function () {
        var homeworkId = $("#homework_id").val();
        var sortName = $("#sort_01").val();
        var url = "../../homework_servlet?action=get_homework_detail&homework_id=" + homeworkId + "&order_by=" + sortName;
        getHomeworkDetail(url);
    };
    var getHomeworkDetail = function (url) {
        // Metronic.startPageLoading({message: '正在查询中，请稍候...'});	//开始等待动画
        console.log(url);
        $.post(url, function (json) {
            if (json.result_code === 0) {
                var list = json.aaData;

                for (var i = 0; i < list.length; i++) {
                    var homework = list[i];
                    alert(JSON.stringify(homework));
                    var title =  homework.homework_title;
                    $("#homework_detail_title").html(title);
                    var flag = compareDate(new Date().format("yyyy-MM-dd hh:mm:ss"), homework.deadline);
                    var state = "";
                    if (flag) {
                        state = "已截止";
                    } else {
                        state = "未截止";
                    }

                    var html = "";
                    html = html + "																<span>作业标题：" + title + "</span><p>";
                    html = html + "																<span>作业要求:" + homework.homework_requirement + "</span><p>";
                    html = html + "																<span>上传文件格式要求:" + homework.file_name_format + "</span><p>";
                    html = html + "																<span>上传时间：" + homework.publish_time + "</span><p>";
                    html = html + "																<span>截止时间:<h id='deadline'>" + homework.deadline + "</h></span><p>";
                    html = html + "																<span>状态:" + state + "</span><p>";

                    $("#homework_detail_div").html(html);

                    Page.showRecordList(homework.file_list);
                }
            } else {
                if (Page != null) {
                    Page.processError(json);
                }
            }
            // Metronic.stopPageLoading();	//停止等待动画
        }).error(function (xhr, errorText, errorType) {
            alert('错误信息：' + errorText + ",错误类型：" + errorType);
        });
    };
    var viewFile = function (id) {
        var homeworkId = $("#homework_id").val();
        var orderBy = $("#sort_01").val();
        window.location.href = "../../file/core/view.jsp?homework_id=" + homeworkId + "&id=" + id + "&order_by=" + orderBy;
    };
    var deleteFile = function (id) {
        if (confirm("您确定要删除这条记录吗？")) {
            if (id > -1) {
                $.post("../../file_servlet?action=delete_file&id=" + id, function (json) {
                    if (json.result_code === 0) {
                        var count = json.count;
                        var amount = json.amount;
                        initRecordList();
                        alert("已经从数据表删除该记录！");
                        $("#has_upload").val("0");
                    }
                })
            }
        }
    };
    var sortFiles = function (index, sortName) {
        console.log("sortName=" + sortName);
        var homeworkId = $("#homework_id").val();
        $.post("../../file_servlet?action=query_files&homework_id=" + homeworkId + "&sort_index=" + index + "&order_by=" + sortName, function (json) {
            console.log(JSON.stringify(json));
            if (json.result_code === 0) {
                Page.showRecordList(json.aaData);
            } else {
                if (Page != null) {
                    Page.processError(json);
                }
            }
        });
    };
    var search = function () {
        page_form.submit();
    };
    return {
        init: function () {
            init();
        },
        deleteFile: function (id) {
            deleteFile(id);
        },
        viewFile: function (id) {
            viewFile(id);
        },
        sortFiles: function (index, sortName) {
            sortFiles(index, sortName);
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
    var layout = 1;
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

            var size = "" + fileSize;
            if (fileSize < 1024) {
                size = fileSize.toFixed(2) + "B";
            } else if (fileSize < 1048576) {
                size = (fileSize / 1024).toFixed(2) + "KB";
            } else if (fileSize < 1073741824) {
                size = (fileSize / 1048576).toFixed(2) + "MB";
            } else {
                size = (fileSize / 1073741824).toFixed(2) + "GB";
            }

            $("#file_name").val(fileName);
            // $("#file_path").val(getFullPath(this));
            $("#file_size_hidden").val(fileSize);
            $("#file_size").val(size);

            var index1 = fileName.lastIndexOf(".");
            var index2 = fileName.length;
            var fileSuffix = fileName.substring(index1, index2);

            var studentNum = $("#student_num").val();
            var userName = $("#user_name").val();
            var renameTo = studentNum + "_" + userName + "_" + $("#homework_detail_title").html() + fileSuffix;
            $("#rename_to").val(renameTo);
            var newUrl = $("#page_form").attr('action') + "&rename_to=" + renameTo;
            $("#page_form").attr('action', newUrl);
            $("#span_tip").html("文件将自动命名为：" + renameTo);

            // $("#file_type").val(fileType);
            // var fileSize = parseInt($("#file_size").val());
        });
        var btnNode = document.getElementById('btn');
        var inputNode = document.getElementById('fileinp');
        btnNode.addEventListener('click', function (e) {
            if (checkDeadline()) {
                return;
            }

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
        $('#search_button').click(function () {
            Page.searchRecord();
        });
        $('#delete_button').click(function () {
            Page.deleteRecord();
        });
        $('#add_button').click(function () {
            Page.addRecord();
        });
        $('#submit_button').click(function () {
            // Page.submitRecord();
            if (checkDeadline()) {
                return;
            }
            var fileName = $("#file_name").val();
            if (fileName == "") {
                alert("请选择上传文件！");
                return;
            }
            var fileSize = parseInt($("#file_size_hidden").val());
            if (fileSize > 1024 * 1024 * 100) {
                alert("上传文件过大！");
                return;
            }
            if ($("#has_upload").val() == "1") {
                alert("请勿重复提交作业！请删除已提交的作业后在提交！");
                return;
            }
            $('#page_form').submit();
            // var fill = document.getElementById('fill');
            document.getElementById('wrapper').style.display = "block";
            var renameTo = $("#rename_to").val();
            var t = setInterval(function(){
                $.ajax({
                    url: '../../UploadServlet?action=get_upload_progress&filename=' + renameTo,
                    type: 'POST',
                    dataType: 'text',
                    data: {
                        filename: renameTo
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
                            document.getElementById('wrapper').style.display = "none";
                            window.location.reload();
                            clearInterval(t);
                        }

                    },
                    error: function(){
                        console.log("error");
                        $("#uploading").html("上传失败！");
                    }
                });
            }, 500);
            // var form = document.getElementById('page_form');
            // form.submit();
        });
        $('#tools_menu_reload').click(function () {
            Page.reload();
        });
        $('#help_button').click(function () {
            Page.help();
        });
        $('#layout_button').click(function () {
            Page.layoutRecord();
        });
        $('#print_button').click(function () {
            Page.printRecord();
        });
    };
    var addRecord = function () {
        window.location.href = "add.jsp";//sub + "_add.jsp";
    }
    // var showResult = function (json) {
    //
    //     if (json != null) {
    //         var list = json.aaData;
    //         // var tip = "当前查询到了 " + list.length + " 条记录";
    //         // if ($("#tip_div")) $("#tip_div").html(tip);
    //         // if ($("#record_list_tip")) $("#record_list_tip").html("已提交的作业");
    //         showRecordList(list);
    //     }
    // };
    var showRecordList = function (list) {
        var title = "记录显示";
        $("#title_div").html(title);
        html = "													<div><span id=\"tip_div\"></span>";
        for (var i = 0; i < list.length; i++) {
            showRecord(list[i]);
        }
        html = html + "													</div>";
        $("#record_list_div").html(html);
    };
    var showRecord = function (json) {
        var image = "../../assets/module/img/public/wkbj.jpg";

        html = html + "														<div style=\"clear:both;width:100%;margin: 0 auto;border:0px solid blue;display: flex\">";
        html = html + "															<div style=\"margin-left: 30%; border:0px solid green;\">";
        html = html + "																<img src=\"" + image + "\" style=\"width:100px;height:auto;border-radius:50%!important;border:0px solid red;\"></img>";
        html = html + "															</div>";
        html = html + "															<div style=\"display:table-cell;margin-left:10px;margin-right:10px;margin-top:10px;margin-bottom:10px;border:0px solid blue;\"><p>";
        html = html + "																<span>文件名：" + json.file_name + "</span><p>";
        html = html + "																<span>文件大小：" + json.file_size + "</span><p>";
        html = html + "																<span>上传者：" + json.uploader_name + "</span><p>";
        html = html + "																<span>上传时间：" + json.upload_time + "</span><p>";
        html = html + "																<span>下载链接：<a href='" + json.download_link + "' onClick='return confirm(\"确定下载名为“" + json.file_name + "”的文件?\");'>点击下载</a></span><p>";
        if (json.isOwner === 1) {
            $("#has_upload").val("1");
            html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.deleteRecord(" + json.id + ");\">删除</button>";
            // html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.modifyRecord(" + id + ");\">修改</button>";
        }
        // html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.viewRecord(" + json.id + ");\">详细信息</button>";
        html = html + "															</div>";
        html = html + "														</div>";
    };
    var help = function () {
        var strUrl = location.pathname;
        window.open('../../help/online/new_win_help.jsp?page_url=' + strUrl, 'big', 'fullscreen=yes');
    }
    var submitRecord = function () {
        if (checkInput()) {
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
        return bOk;
    };
    var deleteRecord = function (id) {
        Record.deleteFile(id);
    };
    var viewRecord = function (id) {
        Record.viewFile(id);
    };
    var modifyRecord = function (id) {
        window.location.href = "view.jsp?id=" + id;
    };
    var searchRecord = function () {
        window.location.href = "query.jsp";
    };
    var statisticRecord = function () {
        // window.location.href = "statistic.jsp";
        window.location.href = "../../base/statistic/statistic_query.jsp?table_name=file_manage";
    }
    var layoutRecord = function () {
        if (layout == 1)
            window.location.href = "record_list.jsp";
        if (layout == 2)
            window.location.href = "list.jsp";
    }
    var printRecord = function () {
        // window.location.href = "print.jsp?exist_resultset=1";
        window.location.href = "../../base/print/print.jsp?record_result=" + module + "_" + sub + "_get_record_result";
    };
    var sortRecord = function (index) {
        var sortName = $("#sort_01").val();
        console.log(sortName);
        Record.sortFiles(index, sortName);
    };
    var confirmBack = function () {
        DraggableDialog.setId("confirm_back");
        DraggableDialog.setCancel(Page.onCancel);
        DraggableDialog.setButtonTitle("确定", "取消");
        DraggableDialog.setOk(Page.returnBack);
        DraggableDialog.showMsg("确定要返回上一页吗？", "提示");
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
        },
        processError: function (json) {
            processError(json);
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
        statisticRecord: function () {
            statisticRecord();
        },
        printRecord: function () {
            printRecord();
        },
        modifyRecord: function (id) {
            modifyRecord(id);
        },
        reload: function () {
            window.location.reload();
        },
        layoutRecord: function () {
            layoutRecord();
        },
        sortRecord: function (index) {
            sortRecord(index);
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
