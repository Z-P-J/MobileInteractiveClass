

var spanStyle = "font-size: 14.0pt; font-family: 宋体; mso-ascii-font-family: Calibri;" +
                " mso-ascii-theme-font: minor-latin; mso-fareast-font-family: 宋体; " +
                "mso-fareast-theme-font: minor-fareast; mso-hansi-font-family: Calibri; " +
                "mso-hansi-theme-font: minor-latin";
function getPrintData() {
    var moduleName = $("#module_name").val();
    // alert(moduleName);
    var url = "../../print_servlet?action=get_print_data&module_name=" + moduleName;
    $.get(url, function (json) {
        // alert(JSON.stringify(json));
        if (json.result_code === 0) {
            var html = "";
            var data = json.aaData;
            var columns = json.table_column_names;
            var columnsZh = json.table_column_names_zh;
            html += initTop(columnsZh);
            html += initMiddle(data, columns);
            html += initBottom();
            $("#table_print").html(html);
        } else {
            alert("获取数据失败！msg=" + json.result_msg);
        }
    }).error(function (xhr, errorText, errorType) {
        alert('错误信息：' + errorText + ",错误类型：" + errorType);
    });
}

function initTop(columns) {
    // alert(JSON.stringify(columns));
    var html = "<tr id=\"tr_columns\" style='mso-yfti-irow: 0; mso-yfti-firstrow: yes'>";
    for (var i = 0; i < columns.length; i++) {
        html += "<td width=95 valign=top style='width: 71.0pt; border: solid black 1.0pt; mso-border-themecolor: text1; mso-border-alt: solid black .5pt; mso-border-themecolor: text1; background: #BFBFBF; mso-shading: windowtext; mso-pattern: gray-25 auto; padding: 0cm 5.4pt 0cm 5.4pt'>\n" +
            "                            <p class=MsoNormal align=center style='text-align: center'>\n" +
            "                                <b style='mso-bidi-font-weight: normal'>\n" +
            "                                    <span style='" + spanStyle + "'>\n" +
            "                                        " + columns[i] +
            "                                    </span>" +
            "                                </b>" +
            "                                <b style='mso-bidi-font-weight: normal'>" +
            "                                    <span lang=EN-US style='font-size: 14.0pt'>" +
            "                                        <o:p></o:p>" +
            "                                    </span>" +
            "                                </b>" +
            "                            </p>" +
            "                        </td>";
    }
    html += "</tr>";
    return html;
}

function initMiddle(data, columns) {
    var html = "";
    for (var i = 0; i < data.length; i++) {
        var list = data[i];
        html += "<tr style='mso-yfti-irow: 1'>";
        for (var j = 0; j < columns.length; j++) {
            html += "<td width=95 valign=top style='width: 71.0pt; border: solid black 1.0pt; mso-border-themecolor: text1; border-top: none; mso-border-top-alt: solid black .5pt; mso-border-top-themecolor: text1; mso-border-alt: solid black .5pt; mso-border-themecolor: text1; padding: 0cm 5.4pt 0cm 5.4pt'>\n" +
                "                            <p class=MsoNormal>\n" +
                "                                <span lang=EN-US style='font-size: 14.0pt'>\n" +
                "                                    <o:p>" + list[columns[j]] + "</o:p>\n" +
                "                                </span>\n" +
                "                            </p>\n" +
                "                        </td>";
        }
        html += "</tr>";
    }
    return html;
}

function initBottom() {
    var date = new Date();

    return "<tr style='mso-yfti-irow: 2; mso-yfti-lastrow: yes'>\n" +
        "                        <td width=555 colspan=4 valign=top style='width: 416.5pt; border: none; mso-border-top-alt: solid black .5pt; mso-border-top-themecolor: text1; padding: 0cm 5.4pt 0cm 5.4pt'>\n" +
        "                            <p class=MsoNormal align=right style='text-align: right'>\n" +
        "                                <span lang=EN-US style='font-size: 14.0pt'>" + date.getFullYear() + "</span>\n" +
        "                                <span style='" + spanStyle + "'>年</span>\n" +
        "                                <span lang=EN-US style='font-size: 14.0pt'>" + (date.getMonth() + 1) + "</span>\n" +
        "                                <span style='" + spanStyle + "'>月</span>\n" +
        "                                <span lang=EN-US style='font-size: 14.0pt'>" + date.getDay() + "</span>\n" +
        "                                <span style='" + spanStyle + "'>日</span>\n" +
        "                                <span lang=EN-US style='font-size: 14.0pt'>\n" +
        "                                    <o:p></o:p>\n" +
        "                                </span>\n" +
        "                            </p>\n" +
        "                        </td>\n" +
        "                    </tr>";
}

// getPrintData();
window.onload = function (ev) {
    getPrintData();
};