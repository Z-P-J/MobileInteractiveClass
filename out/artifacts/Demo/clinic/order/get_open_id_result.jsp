<%@page contentType="text/html; charset=UTF-8"%>
<%
	String openId=request.getParameter("open_id");
	if(openId!=null){
		//有openId就跳转
		String redirectUrl="order_view.jsp?open_id="+openId;
		response.sendRedirect(redirectUrl);
	}else{
		System.out.println("获取到的openId是空的！！！");
		out.println("获取到的openId是空的！！！");
	}
%>
