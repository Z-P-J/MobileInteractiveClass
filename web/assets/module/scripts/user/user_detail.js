var module = "user";
var sub = "info";
jQuery(document).ready(function () {
    Metronic.init(); // init metronic core components
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
        getRecord();
    }
    var getRecord = function () {
        var id = $("#id").val();
        getRecordViewById(id);
    }
    var getRecordViewById = function (id) {
        var url = "../../user_servlet?action=get_user_detail&id=" + id + "&exist_resultset=1";
        getRecordView(url);
    }
    var getRecordViewByIndex = function (index) {
        var url = "../../user_servlet?action=get_user_detail&index=" + index + "&exist_resultset=1";
        getRecordView(url);
    }
    var getRecordView = function (url) {
        Metronic.startPageLoading({message: '正在查询中，请稍候...'});	//开始等待动画
        $.post(url, function (json) {
            if (json.result_code === 0) {
                Record.firstId = json.first;
                Record.prevId = json.prev;
                Record.nextId = json.next;
                Record.lastId = json.last;
                Record.totalCount = json.total;
                Record.aaData = json.aaData;
                Record.currentId = json.current;
                // alert(JSON.stringify(json.aaData));
                Page.showResult(json.aaData[json.current]);
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
        window.location.href = "user_detail.jsp?id=" + id;
    };
    var deleteRecord = function (id) {
        if (confirm("您确定要删除这条学生信息吗？")) {
            if (id > -1) {
                $.post("../../user_servlet?action=delete_record&id=" + id, function (json) {
                    if (json.result_code == 0) {
                        window.location.href = "user_list.jsp";
                        alert("已经从数据表删除该学生信息！");
                    }
                })
            }
        }
    };
    var exportRecord = function () {
        window.location.href = "../../user_servlet?action=export_record&exist_resultset=1";
    }
    var search = function () {
        page_form.submit();
    }
    var first = function () {
        Record.prevId = 0;
        Record.nextId = 1;
        Record.currentId = 0;
        Page.showResult(Record.aaData[0]);
    };
    var prev = function () {
        if (Record.currentId !== 0) {
            Record.prevId -= 1;
            Record.currentId -= 1;
            Record.nextId -= 1;
        }
        Page.showResult(Record.aaData[Record.currentId]);
    };
    var next = function () {
        if (Record.currentId !== Record.totalCount - 1) {
            Record.prevId += 1;
            Record.nextId += 1;
            Record.currentId += 1;
        }
        Page.showResult(Record.aaData[Record.currentId]);
    };
    var last = function () {
        Record.prevId = Record.lastId - 1;
        Record.nextId = Record.lastId;
        Record.currentId = Record.lastId;
        Page.showResult(Record.aaData[Record.lastId]);
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
        first: function () {
            first();
        },
        prev: function () {
            prev();
        },
        next: function () {
            next();
        },
        last: function () {
            last();
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
//关于页面的控件生成等操作都放在Page里，和Record独立，Record主要是和学生信息集交互
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
            Page.exportRecord();
        });
        $('#first_button').click(function () {
            Page.first();
        });
        $('#prev_button').click(function () {
            Page.prev();
        });
        $('#next_button').click(function () {
            Page.next();
        });
        $('#last_button').click(function () {
            Page.last();
        });
    };
    var addRecord = function () {
        window.location.href = "user_add.jsp";
    }
    var showResult = function (json) {
        var title = "学生信息显示";
        $("#title_div").html(title);
        if (json != null) {
            var tip = "当前查询到了 " + Record.totalCount + " 条记录";
            tip = tip + "，现在是第 " + (parseInt(Record.currentId) + 1) + " 条记录。";
            $("#tip_div").html(tip);
            $("#record_list_tip").html(tip);
            html = "													<div><span id=\"tip_div\"></span>";
            $("#user_name").val(json.user_name);
            $("#name").val(json.name);
            $("#sex").val(json.sex);
            $("#email").val(json.email);
            $("#phone").val(json.phone);
            $("#wechat").val(json.wechat);
            $("#grade").val(json.grade);
            $("#class").val(json.class);
            $("#student_num").val(json.student_num);
            $("#faculty").val(json.faculty);
            $("#register_date").val(json.register_date);
            html = html + "													</div>";
            $("#record_list_div").html(html);
        }
    };
    var showRecordList = function (list) {
        html = "													<div><span id=\"tip_div\"></span>";
        for (var i = 0; i < list.length; i++) {
            showRecord(list[i]);
        }
        html = html + "													</div>";
        $("#record_list_div").html(html);
    };
    var showRecord = function (json) {
        var user_type = json.user_type;
        if (user_type == "student") {
            user_type = "学生";
        } else if (user_type == "teacher") {
            user_type = "老师";
        }
        $("#user_name").val(json.user_name);
        $("#name").val(json.name);
        $("#sex").val(json.sex);
        $("#email").val(json.email);
        $("#phone").val(json.phone);
        $("#wechat").val(json.wechat);
        $("#grade").val(json.grade);
        $("#class").val(json.class);
        $("#student_num").val(json.student_num);
        $("#faculty").val(json.faculty);
        $("#register_date").val(json.register_date);
    };
    var help = function () {
        if (checkInput() == true) {
            var strUrl = location.pathname;
            window.open('../../help/online/new_win_help.jsp?page_url=' + strUrl, 'big', 'fullscreen=yes');
        }

    };
    var submitRecord = function () {
        var url = "../../user_servlet?action=update_user" +
            "&user_name=" + $("#user_name").val() +
            "&name=" + $("#name").val() +
            "&sex=" + $("#sex").val() +
            "&email" + $("#email").val() +
            "&phone=" + $("#phone").val() +
            "&wechat=" + $("#wechat").val() +
            "&grade=" + $("#grade").val() +
            "&class=" + $("#class").val() +
            "&student_num=" + $("#student_num").val() +
            "&faculty=" + $("#faculty").val() +
            "&register_date" + $("#register_date").val();
        $.post(url, function (json) {
            if (json.result_code === 0) {
                alert("更新成功！");
            } else {
                alert("更新失败！msg=" + json.result_msg);
            }
        }).error(function (xhr, errorText, errorType) {
            alert('错误信息：' + errorText + ",错误类型：" + errorType);
        });
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
        id = $("#id").val();
        Record.deleteRecord(id);
    };
    var viewRecord = function (id) {
        Record.viewRecord(id);
    };
    var modifyRecord = function (id) {
        window.location.href = "user_detail.jsp?id=" + id;
    };
    var searchRecord = function () {
        window.location.href = "user_query.jsp";
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
        first: function () {
            Record.first();
        },
        prev: function () {
            Record.prev();
        },
        next: function () {
            Record.next();
        },
        last: function () {
            Record.last();
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
