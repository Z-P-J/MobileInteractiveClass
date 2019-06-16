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
    var init = function () {
        getFiles();
    };
    var getFiles = function () {
        var id = $("#id").val();
        var url = "../../" + module + "_" + sub + "_servlet_action?action=get_record_view&id=" + id + "&exist_resultset=1";
        getRecordView(url);
    };
    var getRecordView = function (url) {
        // Metronic.startPageLoading({message: '正在查询中，请稍候...'});	//开始等待动画
        $.post(url, function (json) {
            if (json.result_code === 0) {
                Record.firstId = json.first;
                Record.prevId = json.prev;
                Record.nextId = json.next;
                Record.lastId = json.last;
                Record.totalCount = json.total;
                Record.aaData = json.aaData;
                Record.currentId = json.current;
                Page.showResult(json.aaData[json.current]);
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
    var viewRecord = function (id) {
        window.location.href = "view.jsp?id=" + id;
    };
    var deleteRecord = function (id) {
        if (confirm("您确定要删除这条记录吗？")) {
            if (id > -1) {
                $.post("../../" + module + "_" + sub + "_servlet_action?action=delete_record&id=" + id, function (json) {
                    if (json.result_code === 0) {
                        window.location.href = "list.jsp";
                        alert("已经从数据表删除该记录！");
                    }
                })
            }
        }
    };
    var exportRecord = function () {
        window.location.href = "../../" + module + "_" + sub + "_servlet_action?action=export_record&exist_resultset=1";
    };
    var search = function () {
        page_form.submit();
    };
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
            init();
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
            getFiles();
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
                $.post("../../comment?action=submit_comment&comment_text=" + commentText + "&file_id=" + fileId, function (json) {
                    // alert(JSON.stringify(json));
                    if (json.result_code === 0) {
                        $("#comment_text").val("");
                        Record.aaData[Record.currentId]['comments'] = json.comments;
                        showCommentList(json.comments);
                    }
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
    };
    var showResult = function (json) {
        var title = "记录显示";
        $("#title_div").html(title);
        if (json != null) {
            // console.log(json.comments.toString());
            // var list = json.aaData;
            // console.log(json.aaData.toString());
            var tip = "当前查询到了 " + Record.totalCount + " 条记录";
            tip = tip + "，现在是第 " + (parseInt(Record.currentId) + 1) + " 条记录。";
            $("#tip_div").html(tip);
            $("#record_list_tip").html(tip);
            showRecordList(json);
        }
    };
    var showRecordList = function (json) {
        html = "													<div><span id=\"tip_div\"></span>";
        $("#file_id").val(json.id);
        $("#file_name").val(json.file_name);
        $("#uploader").val(json.uploader_name);
        $("#file_size").val(json.file_size);
        $("#upload_time").val(json.upload_time);
        html = html + "													</div>";
        $("#record_list_div").html(html);

        showCommentList(json.comments);
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
    var showComment = function (json) {
        var image = "../../assets/module/img/security/user/avatar/avatar.jpg";

        html = html + "														<div style=\"clear:both;width:auto;height:100px;margin:5px;border:1px solid lightgrey;text-align: center;\">";
        // html = html + "                                                         <input type=\"hidden\" id=\"file_id\" name=\"file_id\" value=" + fileId + " />"
        html = html + "															<div style=\"float:left;border:0px solid green;text-align: center;margin:5px;\">";
        html = html + "																<img src='"+ image +"' style=\"width:18px;height:auto;border-radius:50%!important;border:0px solid red;\">";
        html =   + "															    <div style=\"text-align: center; color: black;\"><p>" + json.user_id + "</p></div>";
        html = html + "															    <div class=\"comt-meta\" ><span class=\"comt-author\"></span>" + json.publish_date + "</div>";
        html = html + "														    </div>";
        html = html + "														    <div style=\"width:auto;height:100px;display:table-cell;margin-left:10px;margin-right:10px;margin-top:50px;margin-bottom:10px;border:0px solid blue;text-align: center;padding: 10px\">";
        html = html + "																<p><span style='font-weight:bold;'>" + json.comment_content + "</span></p>";
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
