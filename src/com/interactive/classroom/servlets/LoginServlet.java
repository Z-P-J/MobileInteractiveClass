package com.interactive.classroom.servlets;

import com.interactive.classroom.constant.ActionType;
import com.interactive.classroom.servlets.base.BaseHttpServlet;
import com.interactive.classroom.bean.UserBean;
import com.interactive.classroom.constant.UserType;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.UserDao;
import com.interactive.classroom.utils.TimeUtil;
import org.json.JSONException;
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
        try {
            if (ActionType.ACTION_LOG_IN.equals(action)) {
                login(request, response);
            } else if (ActionType.ACTION_SIGN_UP.equals(action)) {
                signUp(request, response);
            } else if (ActionType.ACTION_CHECK_USER_NAME.equals(action)) {
                String userName = request.getParameter("user_name");
                UserDao dao = DaoFactory.getUserDao();
                UserBean bean = dao.getUserByUserName(userName);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("valid_user_name", bean == null ? "true" : "false");
                responseByJson(response, jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String password = request.getParameter("user_password");
        UserDao dao = DaoFactory.getUserDao();
        UserBean user = dao.getUserByUserName(request.getParameter("user_name"));
        if (user != null) {
            debug("password=" + password + "    saveedPassword=" + user.getPassword());
            if (user.getPassword().trim().equals(password.trim())) {
                wrapSession(session, user);
            }
        }
        response.sendRedirect("index.jsp");
    }

    private void signUp(HttpServletRequest request, HttpServletResponse response) {
        UserDao userDao = DaoFactory.getUserDao();
        UserBean bean = new UserBean();
        bean.setUserName(request.getParameter("register_user_name"));
        bean.setPassword(request.getParameter("register_password"));
        bean.setName(request.getParameter("register_full_name"));
        bean.setEmail(request.getParameter("register_email"));
        bean.setRegisterDate(TimeUtil.currentDate());
        bean.setUserType(request.getParameter("register_user_type"));
        if (UserType.STUDENT.equals(bean.getUserType())) {
            bean.setStudentNum(request.getParameter("register_student_num"));
        }

        try {
            int id = userDao.registerUser(bean);
            bean = userDao.getUserByUserName(bean.getUserName());
            debug("注册成功   id=" + bean.getId());
            wrapSession(session, bean);
//            if (id != -1) {
//                bean.setId("" + id);
//                wrapSession(session, bean);
//            }
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
//            processError(request, response, 3, "session超时，请重新登录系统！", RESULT_PATH, RESULT_PAGE, REDIRECT_PATH, REDIRECT_PAGE);
        }
    }

    private void wrapSession(HttpSession session, UserBean user) {
        debug("wrapSession   user=" + user.toString());
        session.setAttribute("user_id", user.getId());
        session.setAttribute("user_name", user.getUserName());
        session.setAttribute("name", user.getName());
        session.setAttribute("sex", user.getSex());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("phone", user.getPhone());
        session.setAttribute("user_role", user.getUserType());
        session.setAttribute("wechat", user.getWeChat());
        session.setAttribute("grade", user.getGrade());
        session.setAttribute("class", user.getClassName());
        session.setAttribute("faculty", user.getFaculty());
        if (UserType.STUDENT.equals(user.getUserType())) {
            session.setAttribute("student_num", user.getStudentNum());
        }
        session.setAttribute("register_date", user.getRegisterDate());
        session.setAttribute("user_avatar", "assets/module/img/security/user/avatar.jpg");
    }

}
