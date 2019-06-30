ComponentsPickers.init();	//这个本页面要编写对应的对象，时间拾取控件

function getAllCourse() {
    $.get("../../course_servlet?action=get_courses", function (json) {
        console.log(JSON.stringify(json));
        if (json.result_code === 0) {
            var html = "";
            for (var i = 0; i < json.aaData.length; i++) {
                var course = json.aaData[i];
                // alert(JSON.stringify(course));
                html += "<option value=\"" + course.id + "\">" + course.course_name + "</option>"
            }
            $("#attendance_course").html(html);
        } else {
            alert("获取所有课程失败！msg=" + json.result_msg);
        }
    });
}

function publishAttendance() {
    // var attendanceCourse = document.getElementById("attendance_course");
    // var courseId = attendanceCourse.options[attendanceCourse.selectedIndex].value;
    var courseId = $("#attendance_course option:selected").val();
    var requirement = $("#attendance_requirement").val();
    var deadline = $("#attendance_deadline").val();
    var url = "../../attendance_servlet?action=publish_attendance&course_id=" + courseId
        + "&requirement=" + requirement
        + "&deadline=" + deadline;
    $.post(url, function (json) {
        console.log(JSON.stringify(json));
        if (json.result_code === 0) {
            alert("发布考勤成功！");
            Record.getAttendances();
        } else {
            alert("发布考勤失败！msg=" + json.result_msg);
        }
    });
}

getAllCourse();

var date = new Date();
date.setTime(date.getTime() + 10 * 60 * 1000);
document.getElementById("attendance_deadline").value = date.format("yyyy-MM-dd hh:mm:ss")