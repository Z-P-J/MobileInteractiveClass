function initLeftMenu(module) {
    delMenu();
    document.title = "管理系统";
    if (module === "home") {
    // ../../common_data_action?action=get_home_menu&table_name=interactive_classroom_main&where=fieldType_Tag=1
    //     $.post("../../common_data_action?action=get_main_menu&table_name=interactive_classroom_main&module=" + module, function (e) {
    //         processMenuItemResult(e, module)
    //     });
    } else {
        $.post("../../common_data_action?action=get_main_menu&table_name=" + module + "_tree&module=" + module, function (e) {
            // alert(JSON.stringify(e));
            processMenuItemResult(e, module);
        });
    }
}

function processMenuItemResult(text, module) {
    //showMsg("读取了菜单:"+text);
    var json = eval("(" + text + ")");
    initMenu(json, module);
}

function delMenu() {
    var ul = document.getElementById("page_sidebar_menu");
    if (ul != null) {
        var lilength = ul.childNodes.length;
        var str = "";
        for (var i = lilength - 1; i >= 0; i--) {
            str = str + "\r\nindex=" + i + ",id=" + ul.childNodes[i].id;
            var li = ul.childNodes[i];
            if (li.id == "sidebar_toggle_wrapper_li" || li.id == "sidebar_search_wrapper_li") {
            } else {
                ul.removeChild(ul.childNodes[i]);
            }
        }
    }
}

function initMenu(json, module) {
    var ul = document.getElementById("page_sidebar_menu");
    if (ul != null) {
        json = json.record_list;
        var i = 0;
        for (var topIndex in json) {
            if (json[topIndex].parent_item_id == 0) {
                var li = document.createElement("li");
                li.id = itemId;
                li.class = "start active open";
                var itemId = "menu_" + json[topIndex].item_id;
                var itemName = json[topIndex].item_name;
                var html = "<a href=\"javascript:;\"><i class=\"icon-home\"></i><span class=\"title\">" + itemName + "</span><span class=\"selected\"></span><span class=\"arrow open\"></span></a>";
                if (json[topIndex].details_tag == 1) {
                    if (i == 0) {
                        html = html + "<ul class=\"sub-menu\" style=\"display: block;\">";
                    } else {
                        html = html + "<ul class=\"sub-menu\">";
                    }
                    i++;

                    for (var subIndex in json) {
                        if (itemId == "menu_" + json[subIndex].parent_item_id) {
                            var subItemId = "menu_" + json[subIndex].item_id;
                            var subItemName = json[subIndex].item_name;

                            if (module == "investigation" || module == "vote") {
                                var href = "../../survey/admin/" + json[subIndex].href_link + "?type=" + module;
                                html = html + "<li id=\"" + subItemId + "\" onclick=\"change('"+ href +"')\"><a><i class=\"icon-bulb\"></i>" + subItemName + "</a></li>";
                            } else {
                                var href = "../../" + json[subIndex].file_path + json[subIndex].href_link;
                                if (json[subIndex].href_link == "")
                                    href = "../../home/main/index.jsp?content_page=under_construction";
                                html = html + "<li id=\"" + subItemId + "\"><a href=\"" + href + "\" ><i class=\"icon-bulb\"></i>" + subItemName + "</a></li>";
                            }
                        }
                    }
                    html = html + "</ul>";
                }
                li.innerHTML = html;
                ul.appendChild(li);

            }
        }
        var menuDiv = document.getElementById("sidebar_menu_div");
        menuDiv.appendChild(ul);
    }
}

function change(href) {
    var src = $("#iframe").attr("src");
    if (href === src) {
        return;
    }
    var htmlStr = "<iframe id='iframe' name='right' src='" + href + "'\n" +
        "                        allowtransparency='true' marginwidth='0' marginheight='0' width='100%' height='100%'\n" +
        "                        frameborder='0' scrolling='yes'></iframe>"
    $('#div-content').html(htmlStr);
}
