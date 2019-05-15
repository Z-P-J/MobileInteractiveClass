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
        getRecord();
    }
    var getRecord = function () {
        var id = $("#id").val();
        getRecordViewById(id);
    }
    var getRecordViewById = function (id) {
        var url = "../../" + module + "_" + sub + "_servlet_action?action=get_record_view&id=" + id + "&exist_resultset=1";
        getRecordView(url);
    }
    var getRecordViewByIndex = function (index) {
        var url = "../../" + module + "_" + sub + "_servlet_action?action=get_record_view&index=" + index + "&exist_resultset=1";
        getRecordView(url);
    }
    var getRecordView = function (url) {
        Metronic.startPageLoading({message: '正在查询中，请稍候...'});	//开始等待动画
        $.post(url, function (json) {
            if (json.result_code == 0) {
                Record.userId = json.user_id;
                Record.userName = json.user_name;
                Record.firstId = json.first;
                Record.prevId = json.prev;
                Record.nextId = json.next;
                Record.lastId = json.last;
                Record.totalCount = json.total;
                Record.currentId = json.current_index;
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
                        window.location.href = "list.jsp";
                        alert("已经从数据表删除该记录！");
                    }
                })
            }
        }
    };
    var exportRecord = function () {
        window.location.href = "../../" + module + "_" + sub + "_servlet_action?action=export_record&exist_resultset=1";
    }
    var search = function () {
        page_form.submit();
    }
    var first = function () {
        getRecordViewByIndex(Record.firstId);
    }
    var prev = function () {
        getRecordViewByIndex(Record.prevId);
    }
    var next = function () {
        getRecordViewByIndex(Record.nextId);
    }
    var last = function () {
        getRecordViewByIndex(Record.lastId);
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
        refresh: function () {
            getRecordViewByIndex(Record.currentId);
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
        $('#submit_comment').click(function () {
            var commentText = $("#comment_text").val();
            if (typeof commentText == "undefined" || commentText == null || commentText == "") {
                alert("评论不能为空！");
            } else {
                var fileId = $("#file_id").val();
                $.post("../../" + module + "_" + sub + "_servlet_action?action=submit_comment&comment_text=" + commentText + "&file_id=" + fileId, function () {
                    // location.reload();
                    Metronic.startPageLoading({message: '评论成功'});
                    $("#comment_text").val("");
                    Record.refresh();
                    Metronic.stopPageLoading();
                })
            }
        });
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
        window.location.href = "add.jsp";//sub + "_add.jsp";
    }
    var showResult = function (json) {
        var title = "记录显示";
        if ($("#title_div")) $("#title_div").html(title);
        if (json != null) {
            // console.log(json.comments.toString());
            var list = json.aaData;
            console.log(json.aaData.toString());
            var tip = "当前查询到了 " + Record.totalCount + " 条记录";
            tip = tip + "，现在是第 " + (parseInt(Record.currentId) + 1) + " 条记录。";
            if ($("#tip_div")) $("#tip_div").html(tip);
            if ($("#record_list_tip")) $("#record_list_tip").html(tip);
            showRecordList(list);
            // var index = parseInt(Record.currentId);
            // showRecord(list[index]);
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
    var showCommentList = function (list) {
        html = "<div>";
        var length = list.length;
        if (length === 0) {
            html = html + "<p>无评论</p>"
        } else {
            for (var i = 0; i < length; i++) {
                showComment(list[i]);
            }
        }

        html = html + "													</div>";
        $("#comment_list_div").html(html);
    };
    var showRecord = function (json) {
        console.log(json.toString());
        var id = json[0];
        var fileId = json[1]
        var uploader = json[2];
        var fileName = json[3];
        var fileSize = json[4];
        var uploadTime = json[5];
        var list = json[9];
        $("#file_id").val(fileId);
        $("#file_name").val(fileName);
        $("#uploader").val(uploader);
        $("#file_size").val(fileSize);
        $("#upload_time").val(uploadTime);
        // var comments = json.comments;
        console.log(list.toString());
        showCommentList(list);
    };
    var showComment = function (json) {
        var id = json[0];
        var image = "../../assets/module/img/security/user/avatar/avatar.jpg";
        var fileId = json[1];
        var userId = json[2];
        var commentContent = json[3];
        var score = json[4];
        var publishDate = json[5];


        html = html + "														<div style=\"clear:both;width:auto;height:100px;margin:5px;border:1px solid lightgrey;text-align: center;\">";
        // html = html + "                                                         <input type=\"hidden\" id=\"file_id\" name=\"file_id\" value=" + fileId + " />"
        html = html + "															<div style=\"float:left;border:0px solid green;text-align: center;margin:5px;\">";
        html = html + "																<img src='"+ image +"' style=\"width:18px;height:auto;border-radius:50%!important;border:0px solid red;\">";
        html = html + "															    <div style=\"text-align: center; color: black;\"><p>" + userId + "</p></div>";
        html = html + "															    <div class=\"comt-meta\" ><span class=\"comt-author\"></span>" + publishDate + "</div>";
        html = html + "														    </div>";
        html = html + "														    <div style=\"width:auto;height:100px;display:table-cell;margin-left:10px;margin-right:10px;margin-top:50px;margin-bottom:10px;border:0px solid blue;text-align: center;padding: 10px\">";
        html = html + "																<p><span style='font-weight:bold;'>" + commentContent + "</span></p>";
        html = html + "														    </div>";
        html = html + "														</div>";
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
        id = $("#id").val();
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