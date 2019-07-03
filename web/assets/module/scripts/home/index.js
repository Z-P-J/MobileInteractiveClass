jQuery(document).ready(function () {
    console.log("执行js初始化开始");
    Metronic.init(); // init metronic investigation componets
    Layout.init(); // init layout
    QuickSidebar.init(); // init quick sidebar
    Demo.init(); // init demo features
    initHomeMenu();
    Page.init();
    console.log("执行js初始化结束");
    $.get("https://www.tianqiapi.com/api/", function (json) {
        // alert(JSON.stringify(json));
        $("#city").html(json.city);
        $("#today").html(json.data[0].date + " " + json.data[0].week);
        $("#weather").html(json.data[0].wea);
        $("#temp").html("当前温度" + json.data[0].tem + "  白天温度" + json.data[0].tem1 + "  晚上温度" + json.data[0].tem2);
        // $("#min_temp").html("平均温度" + json.data[0].tem);
        $("#win").html(json.data[0].win);
        $("#win_speed").html(json.data[0].win_speed);
        $("#air_condition").html("空气指数" + json.data[0].air + " " + json.data[0].air_level);
        $("#humidity").html("湿度：" + json.data[0].humidity);
        $("#tip").html(json.data[0].air_tips);
    });
});

function initHomeMenu() {
    delHomeMenu();
    addHomeMenu();
}

function delHomeMenu() {
    jQuery("#home_content_div").empty();
}

function addHomeMenu() {
    $.post("../../common_data_action?action=get_home_menu&table_name=interactive_classroom_main&where=fieldType_Tag=1", function (e) {
        processHomeMenuResult(e);
        initLeftMenu(e);
    });
}

function processHomeMenuResult(data) {
    console.log(JSON.stringify(data));
    var json = eval("(" + data + ")");
    if (json.result_code == 0) {
        var list = json.record_list;
        var ul = document.getElementById("page_sidebar_menu");
        for (var i = 0; i < list.length; i++) {
            //逐项添加菜单
            var module = list[i].reason;
            var linkImage = list[i].picture;
            var title = list[i].value;
            var linkHref = "../../" + module + "/main";
            addMenuDiv(title, linkImage, linkHref);

            var li = document.createElement("li");
            // li.id = itemId;
            li.class = "start active open";
            // var itemId = "menu_" + json[topIndex].item_id;
            // var itemName = json.value;
            var html = "<a href=\"" + linkHref + "\"><i class=\"icon-home\"></i><span class=\"title\">" + title + "</span><span class=\"selected\"></span><span class=\"arrow open\"></span></a>";
            li.innerHTML = html;
            ul.appendChild(li);
        }
        var menuDiv = document.getElementById("sidebar_menu_div");
        menuDiv.appendChild(ul);
        Frame.init("home", "main");
    } else {
        Page.processError(json);
    }
}

function initLeftMenu(data) {
    var ul = document.getElementById("page_sidebar_menu");
    if (ul != null) {
        // json = json.record_list;
        // var i = 0;
        for (var json in data.record_list) {
            var li = document.createElement("li");
            // li.id = itemId;
            li.class = "start active open";
            // var itemId = "menu_" + json[topIndex].item_id;
            var itemName = json.value;
            var html = "<a href=\"javascript:;\"><i class=\"icon-home\"></i><span class=\"title\">" + itemName + "</span><span class=\"selected\"></span><span class=\"arrow open\"></span></a>";
            li.innerHTML = html;
            ul.appendChild(li);
        }
        var menuDiv = document.getElementById("sidebar_menu_div");
        menuDiv.appendChild(ul);
    }
}

function addMenuDiv(title, imageLink, hrefLink) {
    var html = "<div class=\"col-md-3 col-sm-4 mix category_1\">";
    html = html + "<div class=\"mix-inner\">";
    html = html + "<a href=\"" + hrefLink + "\">";
    html = html + "<img class=\"img-responsive\" src=\"../../assets/module/img/home/menu/" + imageLink + "\" alt=\"\">";
    html = html + "</a>";
    html = html + "<div class=\"mix-details\">";
    html = html + "<h4>" + title + "</h4>";
    html = html + "<a class=\"mix-link\"> <i class=\"fa fa-link\"></i> </a>";
    html = html + "<a class=\"mix-preview fancybox-button\" href=\"../../assets/admin/pages/media/works/img2.jpg\" title=\"Project Name\" data-rel=\"fancybox-button\"> <i class=\"fa fa-search\"></i> </a>";
    html = html + "</div></div></div>";
    jQuery("#home_content_div").append(html);
}

var Page = function () {
    var html = "";
    var initPageStyle = function () {
        //隐藏page header
        //$("#page_header_div").hide();
        //隐藏提醒图标
        $("#header_inbox_bar").hide();
        $("#header_calendar_bar").hide();
    }
    var processError = function (json) {
        if (json.result_code == 1) {
        }
        if (json.result_code == 2) {
        }
        if (json.result_code == 3) {
            //session超时了
            alert("长时间没有操作导致系统需要重新登陆，请您前往登陆界面登陆。");
            window.location.href = "../../home/main/login.jsp";
        }
    };
    return {
        init: function () {
            initPageStyle();
        },
        processError: function (json) {
            processError(json);
        }
    }
}();
/*================================================================================*/
