var module = "file";
var sub = "core";
jQuery(document).ready(function () {
    Metronic.init(); // init metronic investigation components
    Layout.init(); // init current layout
    QuickSidebar.init(); // init quick sidebar
    Demo.init(); // init demo features
    ComponentsDropdowns.init();
    Frame.init(module, sub);
    Page.init();
    Page.tableName = "file_manage";
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
        getRecord();
    };
    var getRecord = function () {
        Metronic.startPageLoading({message: '正在查询中，请稍候...'});	//开始等待动画
        var id = $("#id").val();
        var existResultset = $("#exist_resultset").val();
        var url = "../../file_servlet?action=query_files";
        $.post(url, function (json) {
            if (json.result_code === 0) {
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
        window.location.href = "file_detail.jsp?id=" + id + "&order_by=" + $("#sort_01").val();;
    };
    var deleteRecord = function (id) {
        if (confirm("您确定要删除这条记录文件吗？该操作将同时删除服务器中相应的文件！")) {
            if (id > -1) {
                $.post("../../file_servlet?action=delete_file&id=" + id, function (json) {
                    if (json.result_code === 0) {
                        var count = json.count;
                        var amount = json.amount;
                        initRecordList();
                        initRecordStyle();
                        alert("操作成功！");
                    } else {
                        alert("操作失败！" + json.result_msg);
                    }
                })
            }
        }
    };
    var exportFiles = function () {
        if (confirm("导出之前，必须在指定的分区创建对应的目录，否则导出会出错！\r\n请在导出前确保目录C:\\xm05\\export\\" + module + "存在，如果没有就创建一个。\r\n请问条件符合了吗？")) {
            $.get("../../file_servlet?action=export_files", function (json) {
                console.log(JSON.stringify(json));
                if (json.result_code === 0) {
                    alert("导出成功！");
                } else {
                    if (Page != null) {
                        Page.processError(json);
                    }
                    alert("导出失败！msg=" + json.result_msg);
                }
            });
        }
    };
    var sortFiles = function (index, sortName) {
        // Metronic.startPageLoading({message: '正在查询中，请稍候...'});	//开始等待动画
        $.post("../../file_servlet?action=query_files&sort_index=" + index + "&order_by=" + sortName, function (json) {
            console.log(JSON.stringify(json));
            if (json.result_code === 0) {
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
            // Metronic.stopPageLoading();	//停止等待动画
        });
    };
    var search = function () {
        page_form.submit();
    };
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
        exportFiles: function () {
            exportFiles();
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
            Page.submitRecord();
        });
        $('#tools_menu_reload').click(function () {
            Page.reload();
        });
        $('#help_button').click(function () {
            Page.help();
        });
        $('#export_button').click(function () {
            Page.exportFiles();
        });
        $('#statistic_button').click(function () {
            Page.statisticRecord();
        });
        $('#layout_button').click(function () {
            Page.layoutRecord();
        });
        $('#print_button').click(function () {
            Page.printRecord();
        });
    };
    var addRecord = function () {
        window.location.href = "file_upload.jsp";//sub + "_add.jsp";
    }
    var showResult = function (json) {
        var title = "记录显示";
        $("#title_div").html(title);
        if (json != null) {
            var list = json.aaData;
            showRecordList(list);
        }
    };
    var showRecordList = function (list) {
        var tip = "当前查询到了 " + list.length + " 条记录";
        html = "													<div><span id=\"tip_div\">" + tip + "</span>";
        for (var i = 0; i < list.length; i++) {
            html += showRecord(list[i]);
        }
        html = html + "													</div>";
        $("#record_list_div").html(html);
    };
    var showRecord = function (json) {
        var image = "../../assets/module/img/public/wkbj.jpg";

        var html = "														<div style=\"clear:both;width:100%;margin-top:5px;border:0px solid blue;\">";
        html = html + "															<div style=\"float:left;border:0px solid green;\">";
        html = html + "																<img src=\"" + image + "\" style=\"width:100px;height:auto;border-radius:50%!important;border:0px solid red;\"></img>";
        html = html + "															</div>";
        html = html + "															<div style=\"display:table-cell;margin-left:10px;margin-right:10px;margin-top:10px;margin-bottom:10px;border:0px solid blue;\"><p>";
        html = html + "																<span>文件名：" + json.file_name + "</span><p>";
        html = html + "																<span>文件大小：" + json.file_size + "</span><p>";
        html = html + "																<span>上传时间：" + json.upload_time + "</span><p>";
        html = html + "																<span>下载链接：<a href='" + json.download_link + "' onClick='return confirm(\"确定下载名为“" + json.file_name + "”的文件?\");'>点击下载</a></span><p>";
        if (json.isOwner === 1) {
            html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.deleteRecord(" + json.id + ");\">删除</button>";
            html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.modifyRecord(" + json.id + ");\">修改</button>";
        }
        html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.viewRecord(" + json.id + ");\">详细信息</button>";
        html = html + "															</div>";
        html = html + "														</div>";
        return html;
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
        window.location.href = "file_detail.jsp?id=" + id;
    };
    var searchRecord = function () {
        window.location.href = "file_query.jsp";
    };
    var statisticRecord = function () {
        // window.location.href = "statistic.jsp";
        window.location.href = "../../base/statistic/statistic_query.jsp?table_name=file_manage";
    }
    var layoutRecord = function () {
        if (layout === 1)
            window.location.href = "record_list.jsp";
        if (layout === 2)
            window.location.href = "list.jsp";
    }
    var printRecord = function () {
        window.location.href = "../../base/print/print.jsp?module_name=" + module;
    };
    var sortFiles = function (index) {
        var sortName = $("#sort_01").val();
        // if (index === 1) sortName = $("#sort_01").val();
        // if (index === 2) sortName = $("#sort_01").val() + "," + $("#sort_02").val();
        // if (index === 3) sortName = $("#sort_01").val() + "," + $("#sort_02").val() + "," + $("#sort_03").val();
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
        showResult: function (json) {
            showResult(json);
        },
        showRecordList: function (list) {
            showRecordList(list);
        },
        showRecord: function (list) {
            return showRecord(list);
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
        exportFiles: function () {
            Record.exportFiles();
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
        sortFiles: function (index) {
            sortFiles(index);
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
