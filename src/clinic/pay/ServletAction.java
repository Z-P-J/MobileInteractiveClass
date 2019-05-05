package clinic.pay;
/*
 * 待完成：用MVC模式分开DB和Action操作
 * 增删改查看导印统功能的实现
 */

import org.json.JSONException;
import org.json.JSONObject;
import utility.LogEvent;
import wechat.WECHAT;
import wechat.WxPaySendData;
import wechat.WxSign;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServletAction extends HttpServlet {
	//这里是需要改的,module和sub
	public String module = "clinic";
	public String sub = "pay";
	
	public String preFix = module + "_" + sub;
	public String resultPath = module + "/" + sub;
	public String resultPage = "result.jsp";
	public String resultUrl=resultPath+"/"+resultPage;
	public String redirectPath = module + "/" + sub;
	public String redirectPage = "record_list.jsp";
	public String redirectUrl=redirectPath+"/"+redirectPage;
	public String databaseName="my_test";
	public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public LogEvent ylxLog = new LogEvent();

	/*
	 * 处理顺序：先是service，后根据情况doGet或者doPost
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		processAction(request,response);
	}
	public void processAction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		try {
			ylxLog.setSession(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String action = request.getParameter("action");
		boolean actionOk = false;
		showDebug("processAction收到的action是："+action);
		if(session.getAttribute("user_role")==null && (!action.equals("get_unified_order"))){
			try {
				processError(request, response,3,"session超时，请重新登录系统！");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else{
			if (action == null){
				try {
					processError(request, response,1,"传递过来的action是null！");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else{
				if (action.equals("get_unified_order")) {
					actionOk=true;
					try {
						getUnifiedOrder(request, response);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
				if (!actionOk) {
					try {
						processError(request, response,2,"["+module+"/"+sub+"/ServletAction]没有对应的action处理过程，请检查action是否正确！action="+action);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	public void processError(HttpServletRequest request, HttpServletResponse response,int errorNo,String errorMsg) throws JSONException, IOException{
		String action = request.getParameter("action");
		//errorNo=0->没有错误
		//errorNo=1->action是空值
		//errorNo=2->没有对应的处理该action的函数
		//errorNo=3->session超时
		showDebug("错误信息："+errorMsg+"，代码："+errorNo);
		JSONObject jsonObj=new JSONObject();
		boolean isAjax=true;
		if(request.getHeader("x-requested-with")==null){isAjax=false;}	//判断是异步请求还是同步请求
		if(isAjax){
			jsonObj.put("result_code",errorNo);
			jsonObj.put("result_msg",errorMsg);
			jsonObj.put("action",action);
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonObj);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			errorMsg = java.net.URLEncoder.encode(errorMsg, "UTF-8");
			String url = resultPath+"/"+resultPage+"?action="+action+"&result_code="+errorNo+"&redirect_path=" + redirectPath + "&redirect_page=" + redirectPage + "&result_msg=" + errorMsg;
			showDebug(url);
			response.sendRedirect(url);
		}
	}
	/*
	 * 功能：进行一个本类测试，不用启动整个项目，测试所写的Java
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("");
	}
	public void showDebug(String msg){
		System.out.println("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())+"]["+module+"/"+sub+"/ServletAction]"+msg);
	}
	/*
	 * 功能：支付之前获取规范化订单操作
	 *
	 *以下是遇到过的案例数据
执行unifiedorder返回：
<xml>
	<return_code><![CDATA[SUCCESS]]></return_code>
	<return_msg><![CDATA[OK]]></return_msg>
	<appid><![CDATA[wxfee15e23416475c6]]></appid>
	<mch_id><![CDATA[1305684801]]></mch_id>
	<device_info><![CDATA[1000]]></device_info>
	<nonce_str><![CDATA[I0O7Vg1CkFdxodpa]]></nonce_str>
	<sign><![CDATA[F45EA42F27C98DED70EBA54D93129F79]]></sign>
	<result_code><![CDATA[SUCCESS]]></result_code>
	<prepay_id><![CDATA[wx20160403155253b167c0d0e40866792262]]></prepay_id>
	<trade_type><![CDATA[JSAPI]]></trade_type>
</xml>
执行unifiedorder返回：
<xml>
	<return_code><![CDATA[SUCCESS]]></return_code>
	<return_msg><![CDATA[OK]]></return_msg>
	<appid><![CDATA[wxfee15e23416475c6]]></appid>
	<mch_id><![CDATA[1305684801]]></mch_id>
	<device_info><![CDATA[1000]]></device_info>
	<nonce_str><![CDATA[fPHiSGfceLMIpmYm]]></nonce_str>
	<sign><![CDATA[824476DBAECB629F961CAC9C51041219]]></sign>
	<result_code><![CDATA[FAIL]]></result_code>
	<err_code><![CDATA[ORDERPAID]]></err_code>
	<err_code_des><![CDATA[该订单已支付]]></err_code_des>
</xml>
	 */
	public void getUnifiedOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		JSONObject jsonObj = new JSONObject();
		List jsonList = new ArrayList();
		String action = request.getParameter("action");
		String id = request.getParameter("id");
		String orderId = request.getParameter("order_id");
		String amount = request.getParameter("amount");
		String openId = request.getParameter("open_id");
		showDebug("获得的orderId是：" + orderId+"，openId是："+openId+"，amount是："+amount);
		showDebug("================================================================================");

		WECHAT wechat = new WECHAT();
		/*--------------------在这里可以对wechat进行一个切换，管理多个微信的情况，现在写死赋值--------------------*/
		wechat.appId="wx18fba49991e7f791";
		wechat.openId=openId;
		wechat.tradeId="1316186701";
		wechat.tradeKey="YLX88888888888888888888888888888";
		String serverAddress=(String)session.getAttribute("wechat_server_address");
		/*--------------------赋值完毕--------------------*/
		String appId = wechat.appId;
		String resultMsg="ok";
		int resultCode=0;
		if(openId!=null){
			//在指定的服务器上有一个unifiedorder_callback.php页面，接收微信传过来的返回的UnifiedOrder
			String notifyUrl = "http://"+serverAddress+"/wechat/unifiedorder_callback.php";
			String tradeId = wechat.tradeId;
			String tradeKey = wechat.tradeKey;
			String registerTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			// 开始查询
			String orderName = "产品名称";
			String attach = "产品附加说明";
			WxPaySendData data = new WxPaySendData();
			data.setAppid(appId);
			data.setAttach(attach);
			data.setNonce_str(WxSign.getNonceStr());
			showDebug("时间戳：" + WxSign.getTimeStamp());
			data.setMch_id(tradeId);
			data.setNotify_url(notifyUrl);
			data.setOut_trade_no(orderId);
			data.setOpenid(openId);
			data.setBody(orderName);
			data.setTotal_fee(Integer.parseInt(amount));
			data.setSpbill_create_ip("127.0.0.1");
			data.setTrade_type("JSAPI");
			data.setDevice_info("1000");
			data.setTime_start(registerTime);
			//计算签名
			WxSign.createUnifiedOrderSign(data, tradeKey);

			String prepayId = "";
			String signType = "MD5";
			String timeStamp = WxSign.getTimeStamp();
			String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			String param = wechat.getUnifiedOrderXml(data);
			showDebug("wechat.getUnifiedOrderXml(data)之后的结果是：\r\n" + param);
			//开始去向腾讯服务器获取统一的UnifiedOrder，回调的网址在指定的服务器上的unifiedorder_callback.php里。
			String returnXml = wechat.getUrl(url, param);
			showDebug("执行unifiedorder返回：" + returnXml);
			jsonObj.put("prepay_id", prepayId);
			jsonObj.put("app_id", data.getAppid());
			jsonObj.put("mch_id", data.getMch_id());
			jsonObj.put("open_id", data.getOpenid());
			jsonObj.put("time_stamp", timeStamp);
			jsonObj.put("body", data.getBody());
			jsonObj.put("nonce_str", data.getNonce_str());
			jsonObj.put("total_fee", data.getTotal_fee());
			jsonObj.put("spbill_create_ip", data.getSpbill_create_ip());
			jsonObj.put("trade_type", data.getTrade_type());
			prepayId = wechat.getPrepayId(returnXml);
			jsonObj.put("prepay_id", prepayId);
			jsonObj.put("package_str", "prepay_id=" + prepayId);
			jsonObj.put("pay_sign", wechat.getPaySign(data.getAppid(), timeStamp, data.getNonce_str(), "prepay_id=" + prepayId, signType));
			showDebug(data.getAppid() + "," + timeStamp + "," + data.getNonce_str() + "," + "prepay_id=" + prepayId + "," + signType);
			//最后做成功的标志给json返回
			resultMsg="ok";
			resultCode=0;
		}else{
			resultMsg="session里的wechat是空的或者wechat.openId是空的！";
			resultCode=10;
		}
		// 下面开始构建返回的json
		jsonObj.put("aaData", jsonList);
		jsonObj.put("action", action);
		jsonObj.put("result_msg", resultMsg); // 如果发生错误就设置成"error"等
		jsonObj.put("result_code", resultCode); // 返回0表示正常，不等于0就表示有错误产生，错误代码
		showDebug("[travel_pay_servlet_action]getUnifiedOrder最后构造得到的json是：" + jsonObj.toString());
		response.setContentType("json/application; charset=UTF-8");
		try {
			response.getWriter().print(jsonObj);
			response.getWriter().flush();
			response.getWriter().close();
			showDebug("[travel_pay_servlet_action-getUnifiedOrder]已经给页面返回了json了！！！！！");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
