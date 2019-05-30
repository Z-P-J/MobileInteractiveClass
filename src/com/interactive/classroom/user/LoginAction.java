package com.interactive.classroom.user;

import com.interactive.classroom.base.BaseHttpServlet;
import com.interactive.classroom.user.bean.UserBean;
import com.interactive.classroom.user.dao.UserDao;
import com.interactive.classroom.utils.TimeUtil;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private static final String RESULT_URL = RESULT_PATH + "/" + RESULT_PAGE;
    private static final String REDIRECT_PATH = MODULE + "/" + SUB;
    private static final String REDIRECT_PAGE = "record_list.jsp";

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
//        if (session.getAttribute("user_role") == null) {
//            try {
//                processError(request, response, 3, "session超时，请重新登录系统！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        String action = request.getParameter("action");
        if (action == null) {
//            processError(request, response, );
        } else if ("log_in".equals(action)) {
            String user_id = request.getParameter("user_id");
            response.sendRedirect("index.jsp?user_id=" + user_id);
        } else if ("sign_up".equals(action)) {
            addRecord(request, response);
        }
    }

    public void addRecord(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        UserDao userDao = new UserDao();
        UserBean bean = new UserBean();
        String userName = request.getParameter("register_user_name");
        String password = request.getParameter("register_password");
        String name = request.getParameter("register_full_name");
        System.out.println("name=" + name);
        String email = request.getParameter("email");
        bean.setUserName(userName);
        bean.setPassword(password);
        bean.setName(name);
        bean.setEmail(email);

        JSONObject jsonObj = null;
        try {
            jsonObj = userDao.addRecord(bean);
            System.out.println("注册成功");
            session.setAttribute("user_role", "normal");
            session.setAttribute("user_name", userName);
            session.setAttribute("full_name", name);
            session.setAttribute("email", email);
            session.setAttribute("user_avatar", "assets/module/img/security/user/avatar.jpg");
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
//            processError(request, response, 3, "session超时，请重新登录系统！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
        }

//        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

}
