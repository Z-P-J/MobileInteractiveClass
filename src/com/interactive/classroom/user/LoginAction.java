package com.interactive.classroom.user;

import com.interactive.classroom.base.BaseHttpServlet;
import com.interactive.classroom.user.bean.UserBean;
import com.interactive.classroom.user.dao.UserDao;
import com.interactive.classroom.utils.Log;
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
            login(request, response);
        } else if ("sign_up".equals(action)) {
            addRecord(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String userName = request.getParameter("user_name");
        String password = request.getParameter("user_password");
        UserDao dao = new UserDao();
        UserBean user = dao.getUserByUserName(userName);
        if (user != null) {
            Log.d(getClass().getName(), "password=" + password + "    saveedPassword=" + user.getPassword());
            if (user.getPassword().trim().equals(password.trim())) {
                wrapSession(session, user);
            }
        }
        response.sendRedirect("index.jsp");
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
        String registerDate = TimeUtil.currentDate();
        bean.setUserName(userName);
        bean.setPassword(password);
        bean.setName(name);
        bean.setEmail(email);
        bean.setCreateTime(registerDate);

        try {
            int id = userDao.registerUser(bean);
            System.out.println("注册成功");
            if (id != -1) {
                bean.setId("" + id);
                wrapSession(session, bean);
//                session.setAttribute("user_role", "normal");
//                session.setAttribute("user_name", userName);
//                session.setAttribute("full_name", name);
//                session.setAttribute("email", email);
//                session.setAttribute("user_avatar", "assets/module/img/security/user/avatar.jpg");
//                session.setAttribute("register_date", registerDate);
            }
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
//            processError(request, response, 3, "session超时，请重新登录系统！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
        }

//        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

    private void wrapSession(HttpSession session, UserBean user) {
        session.setAttribute("user_id", user.getId());
        session.setAttribute("user_name", user.getUserName());
        session.setAttribute("name", user.getName());
        session.setAttribute("sex", user.getSex());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("phone", user.getPhone());
        session.setAttribute("user_role", user.getUserRole());
        session.setAttribute("wechat", user.getWechat());
        session.setAttribute("grade", user.getGrade());
        session.setAttribute("class", user.getClassStr());
        session.setAttribute("faculty", user.getFaculty());
        session.setAttribute("student_num", user.getStudentNum());
        session.setAttribute("register_date", user.getCreateTime());
        session.setAttribute("user_avatar", "assets/module/img/security/user/avatar.jpg");
    }

}
