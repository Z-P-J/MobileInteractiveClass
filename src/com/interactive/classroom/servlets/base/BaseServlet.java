package com.interactive.classroom.servlets.base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
  覆写doGet和doPost, 处理请求在service方法中进行，其他Servlet类都应继承该类。
*/
public class BaseServlet extends BaseHttpServlet {

    private static final String CONTENT_TYPE = "text/html;charset=UTF-8";
    private static final String CHARACTER_ENCODING = "UTF-8";

    private void handleResponse(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleResponse(resp);
        super.service(req, resp);
    }

    @Override
    protected void handleAction(HttpServletRequest request, HttpServletResponse response, String action) { }

}
