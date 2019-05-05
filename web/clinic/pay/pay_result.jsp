<%@page contentType="text/html; charset=UTF-8"%>
<%
	String resultMsg=request.getParameter("result_msg");
%>
<h2 style="color:red;"><%=resultMsg %></h2>
<input type="button" value="返回" onclick="window.location.href='../order/order_view.jsp'"/>