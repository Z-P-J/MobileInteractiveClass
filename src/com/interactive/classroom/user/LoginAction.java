package com.interactive.classroom.user;

import com.interactive.classroom.base.BaseHttpServlet;
import com.interactive.classroom.bean.UserBean;
import com.interactive.classroom.constant.UserType;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.UserDao;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.TimeUtil;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

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
        } else if ("check_user_name".equals(action)) {
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

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String userName = request.getParameter("user_name");
        String password = request.getParameter("user_password");
        UserDao dao = DaoFactory.getUserDao();
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
        if (UserType.STUDENT.getTypeName().equals(userType)) {
            bean.setStudentNum(studentNum);
        }
        bean.setEmail(email);
        bean.setCreateTime(registerDate);
        bean.setUserRole(userType);

        try {
            int id = userDao.registerUser(bean);
            System.out.println("注册成功");
            System.out.println("id=" + id);
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
        Log.d("wrapSession", "wrapSession");
        Log.d("wrapSession", user.toString());
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
        if (UserType.STUDENT.getTypeName().equals(user.getUserRole())) {
            session.setAttribute("student_num", user.getStudentNum());
        }
        session.setAttribute("register_date", user.getCreateTime());
        session.setAttribute("user_avatar", "assets/module/img/security/user/avatar.jpg");
    }

}
