package com.interactive.classroom.utils.export;

import org.json.JSONObject;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.TimeUtil;
import com.interactive.classroom.utils.excel.ExcelWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * @author 25714
 */
public class ExportUtil {

    private ExportUtil() {

    }

    public static void exportData(JSONObject jsonObj, String tableNick, String filePathName) throws Exception {
        if (jsonObj != null) {
            //开始导出到excel
            //name⊙type⊙nick
            String[] cols = {"index⊙text⊙nick", "time_interval⊙text⊙nick", "count⊙text⊙nick", "color⊙text⊙nick", "color_name⊙text⊙nick"};
            ExcelWriter.creExcel(jsonObj, tableNick, cols, filePathName);
            jsonObj.put("result_code", 0);
            jsonObj.put("result_msg", "读取了上一次的查询配置");
        }
    }

    public static JSONObject exportRecord(HttpServletRequest request, HttpServletResponse response, String module, String sub, String tableNick) throws Exception {
        File folder = new File("C:\\upload\\" + module + "\\export");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String exportFilePathName = folder.getPath() + "\\" + module + "_" + sub + "_" + "export_" + TimeUtil.currentDate("yyyyMMddHHmmss") + ".xls";
        Log.d("exportRecord", "exportFilePathName=" + exportFilePathName);

        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        String existResultset = request.getParameter("exist_resultset");
        if ((existResultset == null) || ("null".equals(existResultset) || existResultset.isEmpty())) {
            existResultset = "0";
        }
        Log.d("exportRecord", "[exportRecord]收到页面传过来的参数是：" + existResultset + "," + action);

        JSONObject jsonObj;
        if ("1".equals(existResultset)) {
            //要求从session提取之前查询结果，如果有就取出来，如果没有就保存
            if (session.getAttribute(module + "_" + sub + "_get_record_result") != null) {
                jsonObj = (JSONObject) session.getAttribute(module + "_" + sub + "_get_record_result");
                //取出来以后，导出
                ExportUtil.exportData(jsonObj, tableNick, exportFilePathName);
            } else {
                //如果没有就报错
                jsonObj = new JSONObject();
                jsonObj.put("result_code", 10);
                jsonObj.put("result_msg", "exist_resultset参数不当，服务器当前没有结果数据！请重新设置！");
            }
        } else {
            //如果没有就报错
            jsonObj = new JSONObject();
            jsonObj.put("result_code", 10);
            jsonObj.put("result_msg", "exist_resultset参数不当，服务器当前没有结果数据！请重新设置！");
        }
        String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");
        String userName = session.getAttribute("user_name") == null ? null : (String) session.getAttribute("user_name");
        String userRole = session.getAttribute("user_role") == null ? null : (String) session.getAttribute("user_role");
        String userAvatar = session.getAttribute("user_avatar") == null ? null : (String) session.getAttribute("user_avatar");
        String resultMsg = "操作已经执行，文件已导出到：“" + exportFilePathName + "”，请按返回按钮返回列表页面！";
        jsonObj.put("user_id", userId);
        jsonObj.put("user_name", userName);
        jsonObj.put("user_role", userRole);
        jsonObj.put("user_avatar", userAvatar);
        jsonObj.put("action", action);
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", 0);
        //"../export/export_result.jsp?exist_resultset=1"
        jsonObj.put("redirect_url", "../investigation/list.jsp?exist_resultset=1");
        jsonObj.put("result_url", "base/export/export_result.jsp");
        /*--------------------数据查询完毕，根据交互方式返回数据--------------------*/
        return jsonObj;

    }

}
