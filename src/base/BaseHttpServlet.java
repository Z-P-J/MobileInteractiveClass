package base;

import org.json.JSONException;
import org.json.JSONObject;
import utility.Log;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class BaseHttpServlet extends HttpServlet {

    protected String userId = null;
    protected String userName = null;
    protected String userRole = null;
    protected String userAvatar = null;

    protected void processError(HttpServletRequest request, HttpServletResponse response, int errorNo, String errorMsg, String resultPath, String resultPage, String redirectPath, String redirectPage) throws JSONException, IOException {
        String action = request.getParameter("action");
        //errorNo=0->没有错误
        //errorNo=1->action是空值
        //errorNo=2->没有对应的处理该action的函数
        //errorNo=3->session超时
        Log.d(getClass().getName(), "错误信息：" + errorMsg + "，代码：" + errorNo);

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result_code", errorNo);
        jsonObj.put("result_msg", errorMsg);
        jsonObj.put("action", action);
        String url = resultPath + "/" + resultPage + "?action=" + action + "&result_code=" + errorNo + "&redirect_path=" + redirectPath + "&redirect_page=" + redirectPage + "&result_msg=" + errorMsg;
        onEnd(request, response, jsonObj, url);

//        if (request.getHeader("x-requested-with") == null) {
//            errorMsg = java.net.URLEncoder.encode(errorMsg, "UTF-8");
//            String url = resultPath + "/" + resultPage + "?action=" + action + "&result_code=" + errorNo + "&redirect_path=" + redirectPath + "&redirect_page=" + redirectPage + "&result_msg=" + errorMsg;
//            Log.d(getClass().getName(), "url=" + url);
//            response.sendRedirect(url);
//        } else {
//            response.setContentType("application/json; charset=UTF-8");
//            JSONObject jsonObj = new JSONObject();
//            jsonObj.put("result_code", errorNo);
//            jsonObj.put("result_msg", errorMsg);
//            jsonObj.put("action", action);
//            try {
//                PrintWriter out = response.getWriter();
//                out.print(jsonObj);
//                out.flush();
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    protected void onEnd(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject, String resultUrl, String resultMsg, int resultCode, String redirectUrl) {
        try {
            resultMsg = java.net.URLEncoder.encode(resultMsg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = resultUrl + "?result_msg=" + resultMsg + "&result_code=" + resultCode + "&redirect_url=" + redirectUrl;
        onEnd(request, response, jsonObject, url);
    }

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

    protected void onNormalEnd(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObj) {
        onEnd(request, response, jsonObj, "base/export/export_result.jsp", "操作已经执行，请按返回按钮返回列表页面！", 0, "record_list.jsp");
    }

    protected void initUserInfo(HttpSession session) {
        userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        userName = session.getAttribute("user_name") == null ? null : (String) session.getAttribute("user_name");
        userRole = session.getAttribute("user_role") == null ? null : (String) session.getAttribute("user_role");
        userAvatar = session.getAttribute("user_avatar") == null ? null : (String) session.getAttribute("user_avatar");
    }

}
