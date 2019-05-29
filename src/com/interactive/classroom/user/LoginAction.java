package com.interactive.classroom.user;

import com.interactive.classroom.base.BaseHttpServlet;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Z-P-J
 * @date 2019/5/28 0:02
 */
public class LoginAction extends BaseHttpServlet {

    private static final String MODULE = "user";
    private static final String SUB = "info";

    private static final String RESULT_PATH = MODULE + "/" + SUB;
    private static final String RESULT_PAGE = "result.jsp";
    //    private static final String RESULT_URL = RESULT_PATH + "/" + RESULT_PAGE;
//    private static final String RESULT_URL = "base/export/export_result.jsp";
    private static final String REDIRECT_PATH = MODULE + "/" + SUB;
    private static final String REDIRECT_PAGE = "record_list.jsp";

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user_role") == null) {
            try {
                processError(request, response, 3, "session超时，请重新登录系统！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String action = request.getParameter("action");
        if (action == null) {
//            processError(request, response, );
        } else if ("login".equals(action)) {
            String user_id = request.getParameter("user_id");
            response.sendRedirect("index.jsp?user_id=" + user_id);
        } else if ("logon".equals(action)) {

        }
    }

}
