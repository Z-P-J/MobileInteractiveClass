<%@page contentType="text/html; charset=UTF-8"%>
<%
	//先获取前一个页面传过来的参数，显示出来让用户确认，用户点击【确认付款】按钮后，执行pay()函数。
	String orderId=request.getParameter("order_id");
	String amount=request.getParameter("amount");
	String openId=request.getParameter("open_id");
%>
您的订单号码是：<%=orderId %><br/>
您的付款金额是：<%=amount %><br/>
您的openId是：<%=openId %><br/><p><p>
<input type="button" value="确认付款" onclick="pay();"/>
<script type="text/javascript" src="../../assets/global/plugins/jquery.min.js"></script>
<script>
//这三个变量是传递给后端用的，wechatAmount是微信专用金额变量，因为微信的金额100是当做1元处理的，1就是0.01元。
var amount=null;
var wechatAmount=null;
var orderId=null;
/* ================================================================================ */
function pay(){
	orderId="<%=orderId %>";
	amount=<%=amount%>;
	openId="<%=openId%>";
	//用户点了【确认付款】按钮后，向后台申请统一的orderid，即UnifiedOrder
	wechatAmount=amount;
	if((typeof amount)=="string"){wechatAmount=parseFloat(amount)*100;};	//这句话在测试的时候可以去掉，用几分钱代替几元钱测试
	var url="../../clinic_pay_servlet_action?action=get_unified_order&open_id="+openId+"&order_id="+orderId+"&amount="+wechatAmount;
	$.post(url,function(json){
		if(json.result_code==0){
			appId=json.app_id;
			timeStamp=json.time_stamp;
			nonceStr=json.nonce_str;
			packageStr=json.package_str;
			openId=json.open_id;
			paySign=json.pay_sign;
			signType="MD5";
			confirmPay();
		}else{
			alert(json.result_msg);
		}
	});
}
function confirmPay(){
	if (typeof WeixinJSBridge == "undefined"){
		if( document.addEventListener ){
			document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
		}else if (document.attachEvent){
			document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
			document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
		}
	}else{
		//alert("onBridgeReady");
		onBridgeReady();
	}
}
function onBridgeReady(){
	var debug="appId="+appId+"\r\ntimeStamp="+timeStamp+"\r\nnonceStr="+nonceStr+"\r\npackage="+packageStr+"\r\nsignType="+signType;
	WeixinJSBridge.invoke(
		'getBrandWCPayRequest', {
			"appId":appId,  // 公众号名称，由商户传入
			"timeStamp":timeStamp,  // 时间戳，自1970年以来的秒数
			"nonceStr":nonceStr, // 随机串
			"package":packageStr,  
			"signType":signType,  // 微信签名方式：
			"paySign":paySign // 微信签名
		},
		function(res){
			if(res.err_msg == "get_brand_wcpay_request:ok" ) {// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
				var amount=$("#amount").val();
				var url="pay_result.jsp?result_msg=成功付款！";
				window.location=url;
			}else if(res.err_msg == "get_brand_wcpay_request:cancel"){
				alert("用户取消了支付，code="+res.err_code+"\r\ndesc="+res.err_desc+"\r\nmsg="+res.err_msg); 
			}else{
				alert("支付遇到问题，请联系开发者："+"code="+res.err_code+"\r\ndesc="+res.err_desc+"\r\nmsg="+res.err_msg);
			}
		}
	);
}
function returnWeiXin(){
	WeixinJSBridge.call('closeWindow');
}
function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}
</script>