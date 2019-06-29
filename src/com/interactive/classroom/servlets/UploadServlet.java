package com.interactive.classroom.servlets;

import com.interactive.classroom.servlets.base.BaseHttpServlet;
import com.interactive.classroom.bean.FileBean;
import com.interactive.classroom.constant.ActionType;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.ServletUtil;
import com.interactive.classroom.utils.TextUtil;
import com.interactive.classroom.utils.TimeUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Z-P-J
 */
public class UploadServlet extends BaseHttpServlet {

    /**
     * 使用Map<String, Long>装文件上传进度（String：上传文件名 LONG：上传进度）
     * ConcurrentHashMap在一定程度上保证线程安全
     */
    private static final ConcurrentHashMap<String, Long> PROGRESS_MAP = new ConcurrentHashMap<>();

    /**
     * 上传配置
     */
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 100;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 120;

    @Override
    protected void handleAction(HttpServletRequest request, HttpServletResponse response, String action) {
        if (ActionType.ACTION_UPLOAD_FILE.equals(action)) {
            uploadFile(request, response);
        } else if (ActionType.ACTION_GET_UPLOAD_PROGRESS.equals(action)) {
            getUploadProgress(request, response);
        }
    }

    private void uploadFile(HttpServletRequest request, HttpServletResponse response) {
        String from = request.getParameter("from");
        Log.d(getClass().getName(), "from=" + from);
        String fileName = request.getParameter("rename_to");
        Log.d(getClass().getName(), "fileName111=" + fileName);
        if (TextUtil.isEmpty(fileName)) {
            fileName = request.getParameter("file_name");
        }
        Log.d(getClass().getName(), "fileName222=" + fileName);

        System.out.println(request.getMethod());
        //检查是否为post请求以及是否为多媒体上传
        if ("POST".equalsIgnoreCase(request.getMethod()) && ServletFileUpload.isMultipartContent(request)) {
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

            String uploadPath = ServletUtil.getUploadPath(this);
            File file = new File(uploadPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                //解析请求内容提取上传文件数据
                System.out.println("121221211312212122122121");
                List<FileItem> fileItems = upload.parseRequest(request);
                System.out.println("66666666666666666666666");
                System.out.println("fileItems=" + fileItems);
                if (fileItems != null) {
                    System.out.println("fileItems.size=" + fileItems.size());
                }
                if (fileItems != null && fileItems.size() > 0) {
                    System.out.println("2222222222222222222222222222");
                    for (FileItem fileItem : fileItems) {
                        //处理不在表单中的字段
                        Log.d(getClass().getName(), "fileItem size=" + fileItem.getSize());
                        Log.d(getClass().getName(), "fileItem size=" + fileItem.getName());
                        Log.d(getClass().getName(), "fileItem size=" + fileItem.isFormField());

                        if (!fileItem.isFormField()) {
                            System.out.println("333333333333333333333333333333333");
                            long fileSize = fileItem.getSize();
                            if (TextUtil.isEmpty(fileName)) {
                                fileName = fileItem.getName();
                            }
                            PROGRESS_MAP.put(fileName + "_progress", 0L);
                            PROGRESS_MAP.put(fileName + "_size", fileSize);
                            Log.d(getClass().getName(), "key=" + (fileName + "_size"));
                            Log.d(getClass().getName(), "size=" + fileItem.getSize());
                            String filePath = uploadPath + fileName;

                            System.out.println(filePath);
                            File storeFile = new File(filePath);
                            long progress = 0;
                            //用流的方式读取文件，以便可以实时的获取进度
                            InputStream in = fileItem.getInputStream();
                            storeFile.createNewFile();
                            FileOutputStream out = new FileOutputStream(storeFile);
                            byte[] buffer = new byte[1024];
                            int readNumber = 0;
                            while ((readNumber = in.read(buffer)) != -1) {
                                //每读取一次，更新一次进度大小
                                progress = progress + readNumber;
                                //向单例Map写入进度
                                PROGRESS_MAP.put(fileName + "_progress", progress);
                                Log.d(getClass().getName(), "key=" + (fileName + "_progress"));
                                Log.d(getClass().getName(), "progress=" + progress);
                                out.write(buffer);
                            }
                            //当文件上传完成之后，从单例中移除此次上传的状态信息
                            PROGRESS_MAP.put(fileName + "_progress", fileSize);
                            in.close();
                            out.close();

                            FileBean bean = new FileBean();
                            bean.setFileName(fileName);
                            bean.setUploaderId(userId);
                            bean.setUploaderName(userName);
                            bean.setFileSize(fileItem.getSize());
                            bean.setUploadTime(TimeUtil.currentDate());
                            bean.setDownloadLink("../../DownloadServlet?file_name=" + fileName);
                            if ("homework".equals(from)) {
                                bean.setHomeworkId(request.getParameter("homework_id"));
                            } else {
                                bean.setHomeworkId("-1");
                            }
                            DaoFactory.getFileDao().addFile(bean);
                        }
                    }
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "出错了！" + e.getMessage());
            }
        }
        PROGRESS_MAP.put(fileName + "_progress", -1L);
    }

    private void getUploadProgress(HttpServletRequest request, HttpServletResponse response) {
        //        String id = request.getSession().getId();
        String filename = request.getParameter("filename");
        Log.d(getClass().getName(), "filename=" + filename);
        //使用sessionid + 文件名生成文件号，与上传的文件保持一致
//        id = id + filename;
        long size = PROGRESS_MAP.get(filename + "_size");
//        size = size == null ? 100 : size;
        long progress = PROGRESS_MAP.get(filename + "_progress");
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
        try {
            response.getWriter().print(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
