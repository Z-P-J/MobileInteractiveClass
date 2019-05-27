package com.interactive.classroom.user;

import com.interactive.classroom.base.BaseHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Z-P-J
 * @date 2019/5/28 0:02
 */
public class LoginAction extends BaseHttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String user_id = request.getParameter("user_id");
        response.sendRedirect("index.jsp?user_id=" + user_id);
    }

}
