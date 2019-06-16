package com.interactive.classroom.servlets;

import com.interactive.classroom.servlets.base.BaseServlet;
import com.interactive.classroom.utils.EncoderUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//@WebServlet(name = "DownloadServlet", urlPatterns = "/DownloadServlet")
public class DownloadServlet extends BaseServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);

        System.out.println("DownloadServlet");
        //接收文件名参数
        String fileName = req.getParameter("file_name");
        System.out.println(fileName);

        //浏览器识别文件类型
        //根据文件名来获取mime类型
        String mime = this.getServletContext().getMimeType(fileName);
        //设置mime类型
        resp.setContentType(mime);

        //告诉浏览器以附件形式下载， 不要解析
        resp.setHeader("Content-Disposition", "attachment;filename=" + EncoderUtil.getStringEncoderByAgent(fileName, req.getHeader("User-Agent")));


        //获取文件路径
        String path = this.getServletContext().getRealPath("/download" + File.separator + fileName);
        File file = new File(path);
        if (!file.exists()) {
            path = getServletContext().getRealPath("/upload" + File.separator + fileName);
        }
        System.out.println(path);

        //根据path加载文件
        FileInputStream fileInputStream = new FileInputStream(path);
        ServletOutputStream servletOutputStream = resp.getOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = fileInputStream.read(buffer)) != -1) {
            servletOutputStream.write(buffer, 0, len);
        }
    }
}
