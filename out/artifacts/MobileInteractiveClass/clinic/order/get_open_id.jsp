<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wechat.WECHAT"%>
<%
	//运行getOpenid去获取openid后，腾讯微信公众号返回来的结果转到哪里去（自己的服务器，非80端口的）
	String resultUrl="http://www.cdylx.org:9999/TodoDemo/clinic/order/get_open_id_result.jsp";
	WECHAT wechat=new WECHAT();
	wechat.appId="wx18fba49991e7f791";
	wechat.appSecret="16e92f75aed7b67bebdb55c8b0755c73";
	wechat.wechatServerAddress="www.cdylx.com";		//用了一个带有80端口的服务器去跳转，上面有一个wechat/auth_callback.php文件，执行向腾讯查询open_id功能
	wechat.getOpenid(request,response,resultUrl);
%>