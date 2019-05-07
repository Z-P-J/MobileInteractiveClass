package com.interactive.classroom.upload;

import com.interactive.classroom.base.BaseServlet;
import com.interactive.classroom.file.bean.FileBean;
import com.interactive.classroom.file.dao.FileDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.interactive.classroom.utils.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

//@WebServlet(name = "UploadServlet", urlPatterns = "/UploadServlet")
public class UploadServlet extends BaseServlet {

    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";

    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 100; // 100MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 120; // 120MB


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
//        PrintWriter writer = resp.getWriter();

        System.out.println(req.getMethod());
        //检查是否为post请求以及是否为多媒体上传
        if ("POST".equalsIgnoreCase(req.getMethod()) && ServletFileUpload.isMultipartContent(req)) {
            System.out.println("--------------------post-------------------------");
            //配置上传参数
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);
            //设置最大文件上传值
            upload.setFileSizeMax(MAX_FILE_SIZE);
            //设置最大请求值
            upload.setSizeMax(MAX_REQUEST_SIZE);
            //中文处理
            upload.setHeaderEncoding("UTF-8");

            String uploadPath = getServletContext().getRealPath("/") + File.separator + UPLOAD_DIRECTORY;
            File file = new File(uploadPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            System.out.println("1111111111111111111111111");
            try {
                //解析请求内容提取上传文件数据
                System.out.println("121221211312212122122121");
                List<FileItem> fileItems = upload.parseRequest(req);
                System.out.println("66666666666666666666666");
                System.out.println("fileItems=" + fileItems);
                if (fileItems != null) {
                    System.out.println("fileItems.size=" + fileItems.size());
                }
                if (fileItems != null && fileItems.size() > 0) {
                    System.out.println("2222222222222222222222222222");
                    for (FileItem fileItem : fileItems) {
                        //处理不在表单中的字段
                        if (!fileItem.isFormField()) {
                            System.out.println("333333333333333333333333333333333");
                            String fileName = new File(fileItem.getName()).getName();
                            String filePath = uploadPath + File.separator + fileName;
                            System.out.println(filePath);
                            File storeFile = new File(filePath);

                            fileItem.write(storeFile);

                            FileBean bean = new FileBean();
                            bean.setFileId("1");
                            bean.setFileName(fileName);
                            bean.setUploaderId("zhangsan");
                            bean.setFileSize(fileItem.getSize());
                            bean.setUploadTime(TimeUtil.currentDate());
                            bean.setDownloadLink("../../DownloadServlet?file_name=" + fileName);
                            FileDao dao = new FileDao();
                            dao.addRecord(bean);

//                            req.setAttribute("message", "文件上传成功！");
//                            req.setAttribute("file_name", fileName);
//                            req.setAttribute("download_link", "../../DownloadServlet?file_name=" + fileName);
                        }
                    }
                }
                onEnd(req, resp, null, "base/export/export_result.jsp", "上传文件成功！", 0, "record_list.jsp");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("message", "出错了！" + e.getMessage());
            }
        }
        onEnd(req, resp, null, "base/export/export_result.jsp", "上传文件失败！", 0, "record_list.jsp");
    }
}
