ComponentsPickers.init();	//这个本页面要编写对应的对象，时间拾取控件

function query() {
    var id = $("#id").val();
    var queryKeyword = $("#query_keyword").val();
    var timeFrom = $("#time_from").val();
    var timeTo = $("#time_to").val();
    var url = "../../attendance_servlet"
        + "?action=query_attendances"
        + "&id=" + id
        + "&keyword=" + queryKeyword
        + "&time_from=" + timeFrom
        + "&time_to=" + timeTo;
    $.get(url, function (jsonObj) {
        if (jsonObj.result_code === 0) {

            // Page.showResult(json);
            // alert(JSON.stringify(json));
            if (jsonObj != null) {
                var list = jsonObj.aaData;
                var tip = "当前查询到了 " + list.length + " 条记录";
                if ($("#tip_div")) $("#tip_div").html(tip);
                if ($("#record_list_tip")) $("#record_list_tip").html(tip);
                var html = "													<div><span id=\"tip_div\"></span>";
                for (var i = 0; i < list.length; i++) {
                    var json = list[i];
                    html += Page.showRecord(json);
                }

                html = html + "													</div>";
                $("#record_list_div_2").html(html);
            }
        } else {
            alert("查询失败！");
        }
    }).error(function (xhr, errorText, errorType) {
        alert('错误信息：' + errorText + ",错误类型：" + errorType);
    });
}

function todayTime() {
    //将两个时间控件范围限定为今天
    var today = (new Date()).format("yyyy-MM-dd");
    $("#time_from").val(today + " 00:00:00");
    $("#time_to").val(today + " 23:59:59");
}
function yestodayTime () {
    //将两个时间控件范围限定为昨天
    var yestoday = new Date();
    yestoday.setDate(yestoday.getDate() - 1);
    $("#time_from").val(yestoday.format("yyyy-MM-dd") + " 00:00:00");
    $("#time_to").val(yestoday.format("yyyy-MM-dd") + " 23:59:59");
}
function beforeYestodayTime () {
    //将两个时间控件范围限定为前天
    var beforeYestoday = new Date();
    beforeYestoday.setDate(beforeYestoday.getDate() - 2);
    $("#time_from").val(beforeYestoday.format("yyyy-MM-dd") + " 00:00:00");
    $("#time_to").val(beforeYestoday.format("yyyy-MM-dd") + " 23:59:59");
}
function monthTime () {
    var today = new Date();
    var thisYear = today.getFullYear();
    var thisMonth = today.getMonth() + 1;
    var firstDay = thisYear + '-' + thisMonth + '-01';
    firstDay = ComponentsPickers.formatDate(ComponentsPickers.parseDate(firstDay), "yyyy-MM-dd") + " 00:00:00";
    var nowDay = ComponentsPickers.formatDate(today, "yyyy-MM-dd") + " 23:59:59";
    $("#time_from").val(firstDay);
    $("#time_to").val(nowDay);
}

monthTime();

// //设置当前时间10天以后
// Page.initLimitTime();
// Page.initProjectInfo();