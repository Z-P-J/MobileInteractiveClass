package com.interactive.classroom.servlets.base;

import com.interactive.classroom.servlets.LoginServlet;
import com.interactive.classroom.utils.LogEvent;
import com.interactive.classroom.utils.TimeUtil;
import org.json.JSONException;
import org.json.JSONObject;
import com.interactive.classroom.utils.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * HttpServlet基类
 * @author Z-P-J
 */
public abstract class BaseHttpServlet extends HttpServlet {

    protected HttpSession session;

    protected String userId = null;
    protected String userName = null;
    protected String userType = null;
    protected String userAvatar = null;

    private LogEvent logEvent;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        req.setCharacterEncoding("UTF-8");
        session = req.getSession();
        initUserInfo();
        String action = req.getParameter("action");
        debug("收到的action是：" + action);
        if (action == null) {
            //todo "传递过来的action是null！"
        } else {
            if (!LoginServlet.LoginActionName.LOG_IN.getName().equals(action)
                    && !LoginServlet.LoginActionName.SIGN_UP.getName().equals(action)
                    && !LoginServlet.LoginActionName.CHECK_USER_NAME.getName().equals(action)) {
                if (userId == null || userType == null || userName == null) {
                    try {
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("user_id", userId);
                        jsonObj.put("user_name", userName);
                        jsonObj.put("user_role", userType);
                        jsonObj.put("user_avatar", userAvatar);
                        jsonObj.put("result_code", 3);
                        jsonObj.put("result_msg", "session超时，请重新登录系统！");
                        jsonObj.put("action", action);
                        responseByJson(resp, jsonObj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
            logEvent = new LogEvent(session);
            handleAction(req, resp, action);
        }
    }

    protected abstract void handleAction(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException;

    /**
     * errorNo=0->没有错误
     * errorNo=1->action是空值
     * errorNo=2->没有对应的处理该action的函数
     * errorNo=3->session超时
     * */
    protected void processError(HttpServletRequest request, HttpServletResponse response, int errorNo, String errorMsg, String resultPath, String resultPage, String redirectPath, String redirectPage) throws JSONException, IOException {
        String action = request.getParameter("action");

        Log.d(getClass().getName(), "错误信息：" + errorMsg + "，代码：" + errorNo);

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_code", errorNo);
        jsonObj.put("result_msg", errorMsg);
        jsonObj.put("action", action);
        String url = resultPath + "/" + resultPage + "?action=" + action + "&result_code=" + errorNo + "&redirect_path=" + redirectPath + "&redirect_page=" + redirectPage + "&result_msg=" + errorMsg;
        onEnd(request, response, jsonObj, url);
    }

    /**
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @param jsonObject a JSONObject
     * @param resultUrl 处理结果显示页面url
     * @param redirectUrl 重定向链接
     * @param resultMsg 对处理结果的描述性消息
     * @param resultCode 结果码
     **/
    protected void onEnd(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject, String resultUrl, String resultMsg, int resultCode, String redirectUrl) {
        try {
            resultMsg = java.net.URLEncoder.encode(resultMsg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = resultUrl + "?result_msg=" + resultMsg + "&result_code=" + resultCode + "&redirect_url=" + redirectUrl;
        onEnd(request, response, jsonObject, url);
    }

    /**
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @param jsonObject a JSONObject
     **/
    protected void onEnd(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject) {
        String resultUrl = null;
        String resultMsg = null;
        int resultCode = 0;
        String redirectUrl = null;
        try {
            resultUrl = jsonObject.getString("result_url");
            resultMsg = jsonObject.getString("result_msg");
            resultMsg = java.net.URLEncoder.encode(resultMsg, "UTF-8");
            resultCode = jsonObject.getInt("result_code");
            redirectUrl = jsonObject.getString("redirect_url");
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = resultUrl + "?result_msg=" + resultMsg + "&result_code=" + resultCode + "&redirect_url=" + redirectUrl;
        onEnd(request, response, jsonObject, url);
    }

    /**
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @param jsonObject a JSONObject
     * @param url 跳转的链接
     **/
    protected void onEnd(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject, String url) {
        if (request.getHeader("x-requested-with") == null) {
            debug("onEnd  url=" + url);
            try {
                response.sendRedirect(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                responseByJson(response, jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 默认处理
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @param jsonObj a JSONObject
     **/
    protected void onEndDefault(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObj) {
        onEnd(request, response, jsonObj, "base/export/export_result.jsp", "操作已经执行，请按返回按钮返回列表页面！", 0, "record_list.jsp");
    }

    protected void onSuccess(HttpServletResponse response) throws JSONException {
        responseByJson(response, getSuccessJsonObject());
    }

    protected void onSuccess(HttpServletResponse response, JSONObject jsonObj) throws JSONException {
        jsonObj.put("result_code", 0);
        jsonObj.put("result_msg", "ok");
        responseByJson(response, jsonObj);
    }

    protected void onError(HttpServletResponse response, String errMsg) throws JSONException {
        responseByJson(response, getErrorJsonObject(errMsg));
    }

    protected void onError(HttpServletResponse response, JSONObject jsonObj, String errMsg) throws JSONException {
        jsonObj.put("result_code", 10);
        jsonObj.put("result_msg", errMsg);
        responseByJson(response, jsonObj);
    }

    protected void responseByJson(HttpServletResponse response, JSONObject jsonObject) throws JSONException {
        response.setContentType("application/json; charset=UTF-8");
        if (jsonObject == null) {
            jsonObject = getErrorJsonObject("JsonObject is null!");
        }
        try {
            jsonObject.put("user_id", userId);
            jsonObject.put("user_name", userName);
            jsonObject.put("user_role", userType);
            jsonObject.put("user_avatar", userAvatar);
            PrintWriter out = response.getWriter();
            out.print(jsonObject);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void log(String msg, String operation, String module) {
        if (logEvent != null) {
            logEvent.log(msg, operation, module);
        }
    }

    protected void debug(String msg) {
        Log.d(getClass().getName(), "[" + TimeUtil.currentDate() + "][" + msg + "]");
    }

    /**
     * 获取userId，userName，userType，userAvatar
     */
    private void initUserInfo() {
        userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        userName = session.getAttribute("user_name") == null ? null : (String) session.getAttribute("user_name");
        userType = session.getAttribute("user_role") == null ? null : (String) session.getAttribute("user_role");
        userAvatar = session.getAttribute("user_avatar") == null ? null : (String) session.getAttribute("user_avatar");
    }

    protected JSONObject getSuccessJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result_code", 0);
        jsonObject.put("result_msg", "ok");
        return jsonObject;
    }

    protected JSONObject getErrorJsonObject(String errMsg) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result_code", 10);
        jsonObject.put("result_msg", errMsg);
        return jsonObject;
    }

}
