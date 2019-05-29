package com.interactive.classroom.base;

import org.json.JSONException;
import org.json.JSONObject;
import com.interactive.classroom.utils.Log;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
public class BaseHttpServlet extends HttpServlet {

    protected String userId = null;
    protected String userName = null;
    protected String userRole = null;
    protected String userAvatar = null;

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
            Log.d(getClass().getName(), "url=" + url);
            try {
                response.sendRedirect(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response.setContentType("application/json; charset=UTF-8");
            try {
                PrintWriter out = response.getWriter();
                out.print(jsonObject);
                out.flush();
                out.close();
            } catch (IOException e) {
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

    /**
     * 获取userId，userName，userRole，userAvatar
     * @param session The HttpSession
     */
    protected void initUserInfo(HttpSession session) {
        userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        userName = session.getAttribute("user_name") == null ? null : (String) session.getAttribute("user_name");
        userRole = session.getAttribute("user_role") == null ? null : (String) session.getAttribute("user_role");
        userAvatar = session.getAttribute("user_avatar") == null ? null : (String) session.getAttribute("user_avatar");
    }

}
