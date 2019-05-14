package com.interactive.classroom.upload;

import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.ProgressSingleton;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProgressServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ProgressServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        String id = request.getSession().getId();
        String filename = request.getParameter("filename");
        Log.d(getClass().getName(), "filename=" + filename);
        //使用sessionid + 文件名生成文件号，与上传的文件保持一致
//        id = id + filename;
        long size = ProgressSingleton.get(filename + "_size");
//        size = size == null ? 100 : size;
        long progress = ProgressSingleton.get(filename + "_progress");
//        progress = progress == null ? 0 : progress;
        JSONObject json = new JSONObject();
        try {
            json.put("size", size);
            json.put("progress", progress);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(getClass().getName() + ":::" + json.toString());
        response.setContentType("application/json");
        response.getWriter().print(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
