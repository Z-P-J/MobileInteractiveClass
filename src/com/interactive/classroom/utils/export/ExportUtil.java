package com.interactive.classroom.utils.export;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.interactive.classroom.utils.Log;
import com.interactive.classroom.utils.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Z-P-J
 */
public final class ExportUtil {

    private JSONObject jsonObj;

    private String moduleName;

    private String[] labels;

    private String[] labelsZh;

    private String tableNickName;

//    private ExportUtil() {
//
//    }

    private ExportUtil(JSONObject jsonObj) {
        this.jsonObj = jsonObj;
    }

//    public static ExportUtil newInstance() {
//        return new ExportUtil();
//    }

    public static ExportUtil with(JSONObject jsonObj) {
        return new ExportUtil(jsonObj);
    }

//    public ExportUtil setJsonObj(JSONObject jsonObj) {
//        this.jsonObj = jsonObj;
//        return this;
//    }

    public ExportUtil setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public ExportUtil setLabels(String[] labels) {
        this.labels = labels;
        return this;
    }

    public ExportUtil setLabelsZh(String[] labelsZh) {
        this.labelsZh = labelsZh;
        return this;
    }

    public ExportUtil setTableNickName(String tableNick) {
        this.tableNickName = tableNick;
        return this;
    }

    public void export() throws JSONException {
        File folder = new File("C:\\xm05\\export\\" + moduleName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String exportFilePathName = folder.getPath() + "\\" + moduleName + "_" + "export_" + TimeUtil.currentDate("yyyyMMddHHmmss") + ".xls";
        Log.d("exportRecord", "exportFilePathName=" + exportFilePathName);
        creExcel(exportFilePathName);
        String resultMsg = "操作已经执行，文件已导出到：“" + exportFilePathName + "”，请按返回按钮返回列表页面！";
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", 0);
    }

    private void creExcel(String filePath) throws JSONException {
        try {
            OutputStream os = new FileOutputStream(filePath);
            WritableWorkbook workbook = Workbook.createWorkbook(os);
            WritableSheet sheet = workbook.createSheet(tableNickName, 0);

            for (int i = 0; i < labelsZh.length; i++) {
                sheet.addCell(new Label(i, 0, labelsZh[i]));
            }

            JSONArray arr = jsonObj.getJSONArray("aaData");
            int col = 1;
            for (int j = 0; j < arr.length(); j++) {
                JSONObject jsonObject = (JSONObject) arr.get(j);
                for (int i = 0; i < labels.length; i++) {
                    sheet.addCell(new Label(i, col, jsonObject.getString(labels[i])));
                }
                col++;
            }
            workbook.write();
            workbook.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }












    public static void exportData(JSONObject jsonObj, String tableNick, String filePathName) throws JSONException {
        if (jsonObj != null) {
            //开始导出到excel
            //name⊙type⊙nick
            String[] cols = {"index⊙text⊙nick", "time_interval⊙text⊙nick", "count⊙text⊙nick", "color⊙text⊙nick", "color_name⊙text⊙nick"};
            creExcel(jsonObj, tableNick, cols, filePathName);
            jsonObj.put("result_code", 0);
            jsonObj.put("result_msg", "读取了上一次的查询配置");
        }
    }

    public static void exportData(JSONObject jsonObj, String[] labels, String[] labelsZh, String tableNick, String filePathName) throws JSONException {
        if (jsonObj != null) {
            creExcel(jsonObj, labels, labelsZh, tableNick, filePathName);
            jsonObj.put("result_code", 0);
            jsonObj.put("result_msg", "读取了上一次的查询配置");
        }
    }

    public static JSONObject exportRecord(HttpServletRequest request, HttpServletResponse response, String module, String sub, String tableNick) throws JSONException {
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

    public static void exportRecord(JSONObject jsonObj, String module, String tableNick) throws JSONException {
        File folder = new File("C:\\xm05\\export\\" + module);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String exportFilePathName = folder.getPath() + "\\" + module + "_" + "export_" + TimeUtil.currentDate("yyyyMMddHHmmss") + ".xls";
        Log.d("exportRecord", "exportFilePathName=" + exportFilePathName);
        String[] LABELS = {"id", "publisher_id", "publisher_name", "attendance_title", "attendance_requirement", "publish_time", "deadline", "course_id", "attendance_flag"};
        String[] LABELS_CH = {"ID", "发布人ID", "发布人名字", "考勤课程", "考勤要求", "发布时间", "截止时间", "课程id", "考勤flag"};
        ExportUtil.exportData(jsonObj, LABELS, LABELS_CH, tableNick, exportFilePathName);
        String resultMsg = "操作已经执行，文件已导出到：“" + exportFilePathName + "”，请按返回按钮返回列表页面！";
        jsonObj.put("result_msg", resultMsg);
        jsonObj.put("result_code", 0);
    }

    public static void creExcel(JSONObject jsonObj, String tableNick, String[] cols, String filePath) throws JSONException {
        JSONArray arr = jsonObj.getJSONArray("aaData");
        WritableWorkbook workbook;
        try {

            OutputStream os = new FileOutputStream(filePath);
            workbook = Workbook.createWorkbook(os);

            WritableSheet sheet = workbook.createSheet(tableNick, 0);
            jxl.write.Label label;

            List<String> excelColName = new ArrayList<>();
            List<String> excelColType = new ArrayList<>();
            List<String> excelColNick = new ArrayList<>();

            for (String col : cols) {

                excelColName.add(col.split("⊙")[1]);
                excelColType.add(col.split("⊙")[0]);
                excelColNick.add(col.split("⊙")[2]);

            }

            for (int i = 0; i < excelColNick.size(); i++) {
                String colNick = excelColNick.get(i);

                label = new jxl.write.Label(i, 0, colNick); // put the title in
                // row1
                sheet.addCell(label);

            }

            List data = new ArrayList();

            int n = 1;

            for (int j = 0; j < arr.length(); j++) {
                ArrayList list = (ArrayList) arr.get(j);
                JSONObject jsonObject = (JSONObject) arr.get(j);
                for (int i = 0; i < excelColName.size(); i++) {
                    String colType = excelColType.get(i);

                    if (colType.contains("text") || colType.contains("mediumtext") || colType.contains("varchar")) {

                        label = new jxl.write.Label(i, n, list.get(i) + "");
                        sheet.addCell(label);
                    } else {

                        label = new jxl.write.Label(i, n, list.get(i) + "");
                        sheet.addCell(label);
                    }
                }
                n++;
            }
            workbook.write();
            workbook.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void creExcel(JSONObject jsonObj, String[] labels, String[] labelsZh, String tableNick, String filePath) throws JSONException {
        try {
            OutputStream os = new FileOutputStream(filePath);
            WritableWorkbook workbook = Workbook.createWorkbook(os);
            WritableSheet sheet = workbook.createSheet(tableNick, 0);

            for (int i = 0; i < labelsZh.length; i++) {
                sheet.addCell(new Label(i, 0, labelsZh[i]));
            }

            JSONArray arr = jsonObj.getJSONArray("aaData");
            int col = 1;
            for (int j = 0; j < arr.length(); j++) {
                JSONObject jsonObject = (JSONObject) arr.get(j);
                for (int i = 0; i < labels.length; i++) {
                    sheet.addCell(new Label(i, col, jsonObject.getString(labels[i])));
                }
                col++;
            }
            workbook.write();
            workbook.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
