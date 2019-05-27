<%@page contentType="text/html; charset=UTF-8"
        import="java.sql.*,java.io.*" %>
<%
    String userId = request.getParameter("user_id");
    System.out.println("userId=" + userId);
    boolean isLogined = session.getAttribute("user_id") != null || userId != null;
    if (isLogined) {
        if (userId != null) {
            session.setAttribute("user_role", "normal");
            session.setAttribute("user_name", userId);//"张三"
            String studentNum = "20171414";
            for (int i = 0; i < 5; i++) {
                int num = (int) (Math.random() * 10);
                studentNum += num;
            }
            session.setAttribute("student_num", studentNum);
            session.setAttribute("user_id", userId); // "zhangsan"
            session.setAttribute("user_avatar", "assets/module/img/security/user/avatar.jpg");
            session.setAttribute("unit_db_name", "my_test");
            session.setAttribute("wechat_server_address", "www.cdylx.com");
        }
    }
%>
<html>
<body>
<input type="hidden" name="user_id" id="user_id" value="<%=isLogined%>">
</body>
<script>
    // var userId = ;
    var value = document.getElementById("user_id").value;
    // alert(value);
    if (value === "true") {
        window.location.href = "home/main/index.jsp";
    } else {
        window.location.href = "home/main/login.jsp";
    }
    // window.location.href="home/main/index.jsp";
</script>
</html>