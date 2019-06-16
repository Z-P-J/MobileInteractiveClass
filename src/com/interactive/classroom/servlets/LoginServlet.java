package com.interactive.classroom.servlets;

import com.interactive.classroom.servlets.base.BaseHttpServlet;
import com.interactive.classroom.bean.UserBean;
import com.interactive.classroom.constant.UserType;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.UserDao;
import com.interactive.classroom.utils.TimeUtil;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Z-P-J
 * @date 2019/5/28 0:02
 */
public class LoginServlet extends BaseHttpServlet {

    /**
     * 登录操作名
     */
    public enum  LoginActionName {
        // 登录
        LOG_IN("log_in"),
        // 注册
        SIGN_UP("sign_up"),
        // 检查用户名
        CHECK_USER_NAME("check_user_name");

        private final String name;

        LoginActionName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @Override
    protected void handleAction(HttpServletRequest request, HttpServletResponse response, String action) {
        if (LoginActionName.LOG_IN.getName().equals(action)) {
            login(request, response);
        } else if (LoginActionName.SIGN_UP.getName().equals(action)) {
            addRecord(request, response);
        } else if (LoginActionName.CHECK_USER_NAME.getName().equals(action)) {
            String userName = request.getParameter("user_name");
            UserDao dao = DaoFactory.getUserDao();
            UserBean bean = dao.getUserByUserName(userName);
            response.setContentType("application/json; charset=UTF-8");
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("valid_user_name", bean == null ? "true" : "false");
                PrintWriter out = response.getWriter();
                out.print(jsonObject.toString());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        String password = request.getParameter("user_password");
        UserDao dao = DaoFactory.getUserDao();
        UserBean user = dao.getUserByUserName(request.getParameter("user_name"));
        if (user != null) {
            debug("password=" + password + "    saveedPassword=" + user.getPassword());
            if (user.getPassword().trim().equals(password.trim())) {
                wrapSession(session, user);
            }
        }
        try {
            response.sendRedirect("index.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRecord(HttpServletRequest request, HttpServletResponse response) {

        UserDao userDao = DaoFactory.getUserDao();
        UserBean bean = new UserBean();
        String userName = request.getParameter("register_user_name");
        String password = request.getParameter("register_password");
        String name = request.getParameter("register_full_name");
        System.out.println("name=" + name);
        String studentNum = request.getParameter("register_student_num");
        String email = request.getParameter("register_email");
        String userType = request.getParameter("register_user_type");
        System.out.println("userType=" + userType);
        String registerDate = TimeUtil.currentDate();
        bean.setUserName(userName);
        bean.setPassword(password);
        bean.setName(name);
        if (UserType.STUDENT.equals(userType)) {
            bean.setStudentNum(studentNum);
        }
        bean.setEmail(email);
        bean.setCreateTime(registerDate);
        bean.setUserRole(userType);

        try {
            int id = userDao.registerUser(bean);
            debug("注册成功   id=" + id);
            if (id != -1) {
                bean.setId("" + id);
                wrapSession(session, bean);
            }
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
//            processError(request, response, 3, "session超时，请重新登录系统！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
        }

//        onEnd(request, response, jsonObj, RESULT_URL, "操作已经执行，请按返回按钮返回列表页面！", 0, REDIRECT_PAGE);
    }

    private void wrapSession(HttpSession session, UserBean user) {
        debug("wrapSession   user=" + user.toString());
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
        if (UserType.STUDENT.equals(user.getUserRole())) {
            session.setAttribute("student_num", user.getStudentNum());
        }
        session.setAttribute("register_date", user.getCreateTime());
        session.setAttribute("user_avatar", "assets/module/img/security/user/avatar.jpg");
    }

}
