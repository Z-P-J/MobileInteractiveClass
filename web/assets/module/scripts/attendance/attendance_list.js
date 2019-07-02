var module = "attendance";
var sub = "core";
var tableName = "attendance_manage";
function compareDate(s1, s2) {
    return ((new Date(s1.replace(/-/g, "\/"))) > (new Date(s2.replace(/-/g, "\/"))));
}

jQuery(document).ready(function () {
    // if (window.history && window.history.pushState) {
    //
    // }
    // $(window).on('popstate', function () {
    //     window.history.pushState('forward', null, '#');
    //     // window.history.forward(1);
    //     // // alert("不可回退");  //如果需在弹框就有它
    //     // self.location="orderinfo.html"; //如查需要跳转页面就用它
    //     alert("test");
    // });
    Metronic.init(); // init metronic investigation components
    Layout.init(); // init current layout
    QuickSidebar.init(); // init quick sidebar
    Demo.init(); // init demo features
    ComponentsDropdowns.init();
    Frame.init(module, sub);
    Page.init();
    Page.tableName = tableName;
    Record.init();
});
/*================================================================================*/
var Record = function () {
    var initRecordStyle = function () {
    };
    var initRecordList = function () {
        getAttendances();
    };
    var getAttendances = function () {
        Metronic.startPageLoading({message: '正在查询中，请稍候...'});	//开始等待动画
        var id = $("#id").val();
        var existResultset = $("#exist_resultset").val();
        var url = "../../attendance_servlet?action=query_attendances&type=all&id=" + id + "&exist_resultset=" + existResultset;
        $.get(url, function (json) {
            if (json.result_code === 0) {
                Record.userId = json.user_id;
                Record.userName = json.user_name;
                Record.userRole = json.user_role;
                if (json.user_role === "teacher" || json.user_role === "manage") {
                    $("#div_publish_attendance").show();
                }
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
    var checkAttendance = function (id, courseId) {
        $.post("../../attendance_servlet?action=check_attendance&id=" + id + "&course_id=" + courseId, function (json) {
            console.log(JSON.stringify(json));
            if (json.result_code === 0) {
                alert("考勤成功！");
                $("#attendance_btn_" + id).html("已考勤");
            } else {
                alert("考勤失败！");
            }
        });

    };
    var viewAttendance = function (position) {
        Record.json = Record.aaData[position];
        $("#attendance_detail").click();
        // window.location.href = "attendance_detail.jsp?id=" + id + "&course_id=" + courseId;
    };
    var deleteRecord = function (id) {
        if (confirm("您确定要删除这条记录吗？")) {
            if (id > -1) {
                $.post("../../attendance_servlet?action=delete_record&id=" + id, function (json) {
                    if (json.result_code === 0) {
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
    var exportAttendances = function () {
        if (confirm("导出之前，必须在指定的分区创建对应的目录，否则导出会出错！\r\n请在导出前确保目录C:\\xm05\\export\\" + module + "存在，如果没有就创建一个。\r\n请问条件符合了吗？")) {
            $.get("../../attendance_servlet?action=export_attendances", function (json) {
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
    var sortRecord1 = function (index, sortName) {
        // Metronic.startPageLoading({message: '正在查询中，请稍候...'});	//开始等待动画
        $.post("../../attendance_servlet?action=get_attendances&sort_index=" + index + "&order_by=" + sortName, function (json) {
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
        checkAttendance: function (id, courseId) {
            checkAttendance(id, courseId);
        },
        viewAttendance: function (json) {
            viewAttendance(json);
        },
        exportAttendances: function () {
            exportAttendances();
        },
        sortRecord1: function (index, sortName) {
            sortRecord1(index, sortName);
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
            Page.exportAttendances();
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
        window.location.href = "add.jsp";//sub + "_add.jsp";
    }
    var showResult = function (json) {
        var title = "记录显示";
        if ($("#title_div")) $("#title_div").html(title);
        if (json != null) {
            var list = json.aaData;
            Record.aaData = list;
            var tip = "当前查询到了 " + list.length + " 条记录";
            if ($("#tip_div")) $("#tip_div").html(tip);
            if ($("#record_list_tip")) $("#record_list_tip").html(tip);
            showRecordList(list);
        }
    };
    var showRecordList = function (list) {
        html = "													<div><span id=\"tip_div\"></span>";
        for (var i = 0; i < list.length; i++) {
            list[i].position = i;
            html += showRecord(list[i]);
        }
        html = html + "													</div>";
        $("#record_list_div").html(html);
    };
    var showRecord = function (json) {
        var image = "../../assets/module/img/public/wkbj.jpg";

        var flag = compareDate(new Date().format("yyyy-MM-dd hh:mm:ss"), json.deadline);
        var state = "";
        if (flag) {
            state = "已截止";
        } else {
            state = "未截止";
        }

        var html = "														<div style=\"clear:both;width:100%;margin-top:5px;border:0px solid blue;\">";
        html = html + "															<div style=\"float:left;border:0px solid green;\">";
        html = html + "																<img src=\"" + image + "\" style=\"width:100px;height:auto;border-radius:50%!important;border:0px solid red;\"></img>";
        html = html + "															</div>";
        html = html + "															<div style=\"display:table-cell;margin-left:10px;margin-right:10px;margin-top:10px;margin-bottom:10px;border:0px solid blue;\"><p>";
        html = html + "																<span>考勤课程：" + json.attendance_title + "</span><p>";
        html = html + "																<span>考勤要求：" + json.attendance_requirement + "</span><p>";
        html = html + "																<span>发布时间：" + json.publish_time + "</span><p>";
        html = html + "																<span>截止时间：" + json.deadline + "</span><p>";
        html = html + "																<span id='attendance_state_" + json.id + "'>状态：" + state + "</span><p>";

        if (state === "未截止") {
            html = html + "																<span id='attendance_" + json.id + "' style='color: red'>还剩：计算中...</span><p>";


            if (Record.userRole === "student") {
                html = html + "																<button id='attendance_btn_" + json.id + "' type=\"button\" class=\"btn-small\" " + (json.has_attendance ? "" : "onclick=\"Page.checkAttendance(" + json.id + "," + json.course_id + ");\"") + ">" + (json.has_attendance ? "已考勤" : "点击考勤") + "</button>";
            } else {
                html = html + "																<button id='attendance_btn_" + json.id + "' type=\"button\" class=\"btn-small\" onclick=\"Page.deleteRecord(" + json.id + ");\">删除</button>";
            }

            var t = setInterval(function () {
                var sec =  (new Date(json.deadline.replace(/-/g, "\/")).getTime() - new Date().getTime()) / 1000;
                $("#attendance_" + json.id).html("还剩：" + Math.floor(sec) + "秒");
                if (sec <= 0) {
                    $("#attendance_state_" + json.id).html("状态：已截止");
                    // $("#attendance_btn_" + json.id).hide();
                    document.getElementById("attendance_" + json.id).style.display = "none";
                    clearInterval(t);
                }
            }, 1000);
        } else {
            if (Record.userRole === "student") {
                html = html + "																<button id='attendance_btn_" + json.id + "' type=\"button\" class=\"btn-small\" disabled>" + (json.has_attendance ? "已考勤" : "未考勤") + "</button>";
            } else if (Record.userRole === "teacher") {
                html = html + "																<button id='attendance_btn_" + json.id + "' type=\"button\" class=\"btn-small\" onclick=\"Page.viewAttendance(" + json.position + ");\">详细信息</button>";
            } else {

            }
        }

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
            page_form.action = "../../attendance_servlet";
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
    var checkAttendance = function (id, courseId) {
        Record.checkAttendance(id, courseId);
    };
    var viewAttendance = function (json) {
        Record.viewAttendance(json);
    };
    var modifyRecord = function (id) {
        window.location.href = "view.jsp?id=" + id;
    };
    var searchRecord = function () {
        window.location.href = "query.jsp";
    };
    var statisticRecord = function () {
        // window.location.href = "statistic.jsp";
        window.location.href = "../../base/statistic/statistic_query.jsp?table_name=attendance_manage";
    }
    var layoutRecord = function () {
        if (layout == 1)
            window.location.href = "record_list.jsp";
        if (layout == 2)
            window.location.href = "list.jsp";
    }
    var printRecord = function () {
        // window.location.href = "print.jsp?exist_resultset=1";
        window.location.href = "../../base/print/print.jsp?module_name=" + module + "&record_result=" + module + "_" + sub + "_get_record_result&exist_resultset=1";
    };
    var sortRecord = function (index) {
        var sortName = $("#sort_01").val();
        // if (index == 1) sortName = $("#sort_01").val();
        // if (index == 2) sortName = $("#sort_01").val() + "," + $("#sort_02").val();
        // if (index == 3) sortName = $("#sort_01").val() + "," + $("#sort_02").val() + "," + $("#sort_03").val();
        console.log(sortName);
        Record.sortRecord1(index, sortName);
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
        showRecord: function (json) {
            return showRecord(json);
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
        checkAttendance: function (id, courseId) {
            checkAttendance(id, courseId);
        },
        viewAttendance: function (json) {
            viewAttendance(json);
        },
        searchRecord: function () {
            searchRecord();
        },
        exportAttendances: function () {
            Record.exportAttendances();
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
