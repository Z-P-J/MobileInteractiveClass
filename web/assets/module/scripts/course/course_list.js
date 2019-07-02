var module = "course";
var sub = "core";
function compareDate(s1, s2) {
    return ((new Date(s1.replace(/-/g, "\/"))) > (new Date(s2.replace(/-/g, "\/"))));
}
jQuery(document).ready(function () {
    Metronic.init(); // init metronic investigation components
    Layout.init(); // init current layout
    QuickSidebar.init(); // init quick sidebar
    Demo.init(); // init demo features
    ComponentsDropdowns.init();
    Frame.init(module, sub);
    Page.init();
    Page.tableName = "course_manage";
    Record.init();
});
/*================================================================================*/
var Record = function () {
    var init = function () {
        getCourses();
    };
    var getCourses = function () {
        Metronic.startPageLoading({message: '正在查询中，请稍候...'});	//开始等待动画
        var id = $("#id").val();
        var existResultset = $("#exist_resultset").val();
        var url = "../../course_servlet?action=query_courses&id=" + id + "&exist_resultset=" + existResultset;
        $.post(url, function (json) {
            if (json.result_code === 0) {
                Record.userId = json.user_id;
                Record.userName = json.user_name;
                Record.name = json.name;
                Record.userRole = json.user_role;
                if (json.user_role === "student") {
                    $("#join_course").show();
                } else {
                    $("#add_course").show();
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
    var viewRecord = function (id) {
        window.location.href = "homework_detail.jsp?homework_id=" + id + "&exist_resultset=0";
    };
    var deleteCourse = function (id) {
        if (confirm("您确定要删除该课程吗？（不建议删除！）")) {
            if (id > -1) {
                $.post("../../course_servlet?action=delete_course&id=" + id, function (json) {
                    if (json.result_code === 0) {
                        getCourses();
                        alert("已经从数据表删除该记录！");
                    } else {
                        alert("删除失败！msg=" + json.result_msg);
                    }
                })
            }
        }
    };
    var exitCourse = function (id) {
        if (confirm("您确定要退出该课程吗？")) {
            alert(id);
            if (id > -1) {
                $.post("../../course_servlet?action=exit_course&course_id=" + id, function (json) {
                    if (json.result_code === 0) {
                        getCourses();
                        alert("已退出该课程！");
                    } else {
                        alert("退出失败！msg=" + json.result_msg);
                    }
                })
            }
        }
    };
    var exportHomeworks = function () {
        if (confirm("导出之前，必须在指定的分区创建对应的目录，否则导出会出错！\r\n请在导出前确保目录C:\\xm05\\export\\" + module + "存在，如果没有就创建一个。\r\n请问条件符合了吗？")) {
            $.get("../../course_servlet?action=export_courses", function (json) {
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
        $.post("../../course_servlet?action=query_courses&sort_index=" + index + "&order_by=" + sortName, function (json) {
            console.log(JSON.stringify(json));
            if (json.result_code === 0) {
                Record.userId = json.user_id;
                Record.userName = json.user_name;
                Record.name = json.name;
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
            init();
        },
        deleteCourse: function (id) {
            deleteCourse(id);
        },
        exitCourse: function (id) {
            exitCourse(id);
        },
        viewRecord: function (id) {
            viewRecord(id);
        },
        exportHomeworks: function () {
            exportHomeworks();
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
            Page.exportHomeworks();
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
        $("#title_div").html(title);
        if (json != null) {
            var list = json.aaData;
            var tip = "当前查询到了 " + list.length + " 条记录";
            html = "													<div><span id=\"tip_div\">" + tip + "</span>";
            for (var i = 0; i < list.length; i++) {
                html += showRecord(list[i]);
            }
            html = html + "													</div>";
            $("#record_list_div").html(html);
        }
    };
    var showRecord = function (json) {
        var image = "../../assets/module/img/public/wkbj.jpg";
        var html = "														<div style=\"clear:both;width:100%;margin-top:5px;border:0px solid blue;\">";
        html = html + "															<div style=\"float:left;border:0px solid green;\">";
        html = html + "																<img src=\"" + image + "\" style=\"width:100px;height:auto;border-radius:50%!important;border:0px solid red;\"></img>";
        html = html + "															</div>";
        html = html + "															<div style=\"display:table-cell;margin-left:10px;margin-right:10px;margin-top:10px;margin-bottom:10px;border:0px solid blue;\"><p>";
        html = html + "																<span>课程名：" + json.course_name + "</span><p>";
        html = html + "																<span>教师姓名：" + json.teacher_name + "</span><p>";
        html = html + "																<span>老师考勤次数：" + json.attendance_count + "</span><p>";
        // html = html + "																<span>发布时间：" + json.publish_time + "</span><p>";
        // html = html + "																<span>截止时间：" + json.deadline + "</span><p>";
        // html = html + "																<span>状态：" + state + "</span><p>";
        // if (me == "1") {

        // }
        // html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.viewRecord(" + json.id + ");\">详细信息</button>";

        if (Record.userRole === "student") {
            html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.exitCourse(" + json.id + ");\">退出课程</button>";
            html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"window.location.href = '../../homework/main';\">作业管理</button>";
            html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"window.location.href = '../../attendance/main';\">考勤管理</button>";
        } else if (Record.userRole === "teacher") {
            html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.deleteCourse(" + json.id + ");\">删除</button>";
            html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.modifyRecord(" + json.id + ",'" + json.course_name + "','" + json.teacher_name + "');\">修改</button>";
            html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.publishAttendance(" + json.id + ",'" + json.course_name + "');\">发布考勤</button>";
            html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"window.location.href = '../../attendance/main';\">考勤情况</button>";
            html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"window.location.href = '../../user/main';\">学生管理</button>";
        } else {
            html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.deleteCourse(" + json.id + ");\">删除</button>";
            html = html + "																<button  type=\"button\" class=\"btn-small\" onclick=\"Page.modifyRecord(" + json.id + ");\">修改</button>";
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
    var deleteCourse = function (id) {
        Record.deleteCourse(id);
    };
    var exitCourse = function (id) {
        Record.exitCourse(id);
    };
    var publishAttendance = function (courseId, courseName) {
        Record.courseId = courseId;
        Record.courseName = courseName;
        $("#publish_attendance").click();
    }
    var viewRecord = function (id) {
        Record.viewRecord(id);
    };
    var modifyRecord = function (id, courseName, teacherName) {
        Record.courseId = id;
        Record.courseName = courseName;
        Record.teacherName = teacherName;
        $("#update_course").click();
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
        window.location.href = "../../base/print/print.jsp?module_name=" + module;
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
        showRecord: function (list) {
            return showRecord(list);
        },
        submitRecord: function () {
            submitRecord();
        },
        addRecord: function () {
            addRecord();
        },
        deleteCourse: function (id) {
            deleteCourse(id);
        },
        exitCourse: function (id) {
            exitCourse(id);
        },
        publishAttendance: function (courseId, courseName) {
            publishAttendance(courseId, courseName);
        },
        viewRecord: function (id) {
            viewRecord(id);
        },
        exportHomeworks: function () {
            Record.exportHomeworks();
        },
        statisticRecord: function () {
            statisticRecord();
        },
        printRecord: function () {
            printRecord();
        },
        modifyRecord: function (id, courseName, teacherName) {
            modifyRecord(id, courseName, teacherName);
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
