package com.interactive.classroom.utils;

/*
 * 调用步骤：
 * 1.import security.include.LogEvent;
 * 2.LogEvent log=new LogEvent(session);
 * 3.log.log("某某信息");
 * 4.或者：log.log("某某信息","某某操作");
 * 5.或者：log.log("某某信息","某某操作",1);
 */

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;

public class LogEvent {

    private HttpSession session = null;

    public LogEvent(HttpSession session) {
        this.session = session;
    }

//    public void log(String msg) {
//        try {
//            log(msg, "未定义操作");
//        } catch (Exception e) {
//            System.out.println("log出现错误！" + e.getMessage());
//        }
//    }

//    public void log(String msg, String operation) {
//        log(msg, operation, "security");
//    }

    public void log(String msg, String operation, String module) {
        String time = "";
        String operator = (String) session.getAttribute("user_id");
        java.util.Date now = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = formatter.format(now);
        int type = 0;
        try {
            String sql = "insert into public_log(colTime,colType,colContent,colOperation,colUserId,colModule) values(" + "'" + time + "'" + "," + type + "" + ",'" + msg + "'" + ",'" + operation + "'" + ",'" + operator + "'" + ",'" + module + "')";
            DatabaseHelper.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
