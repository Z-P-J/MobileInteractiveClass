<%@page contentType="text/html; charset=UTF-8"%>
<%
	int num=(int)(Math.random()*100001);
	String sub=String.format("%06d", num);
	String orderId="YLX_00000001_01_50_201603_"+sub;
	String openId=request.getParameter("open_id");
%>
<form id="order_form" action="../pay/pay_order.jsp" method="POST">
<input type="hidden" id="action" name="action" value="pay_order" />
请输入金额：<input type="text" id="amount" name="amount" value="3"/><br/><br/>
请输入订单号：<input type="text" id="order_id" name="order_id" value="<%=orderId%>"/><br/>
请输入你的openid：<input type="text" id="open_id" name="open_id" value="<%=openId %>" /><br/>
<input type="submit" value="提交"/>
</form>
