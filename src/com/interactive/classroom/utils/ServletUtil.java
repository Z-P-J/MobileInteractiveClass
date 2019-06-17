package com.interactive.classroom.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Z-P-J
 * 封装一些该项目中Servlet中常用函数
 */
public final class ServletUtil {

    /**
     * 上传文件存储目录
     */
    private static final String UPLOAD_DIRECTORY = "upload";

    private ServletUtil() {

    }

    public static JSONObject getResultSetNavigateId(String id, String index, JSONObject json) throws NumberFormatException, JSONException {
        //然后找到对应id的记录
        JSONArray jsonList = new JSONArray();
        //根据id获得index
        if (id != null && !id.isEmpty()) {
            index = getRecordIndexFromId(id, json);
        }
        ArrayList list = (ArrayList) json.getJSONArray("aaData").get(Integer.parseInt(index));
        jsonList.put(list);
        int count = json.getJSONArray("aaData").length();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("first", 0);
        if (Integer.parseInt(index) > 0) {
            jsonObj.put("prev", Integer.parseInt(index) - 1);
        } else {
            jsonObj.put("prev", 0);
        }
        if (Integer.parseInt(index) < (count - 1)) {
            jsonObj.put("next", Integer.parseInt(index) + 1);
        } else {
            jsonObj.put("next", count - 1);
        }
        jsonObj.put("last", count - 1);
        jsonObj.put("total", count);
        jsonObj.put("current_index", index);
        jsonObj.put("aaData", jsonList);
        System.out.println("getResultSetNavigateId" + list.toString());
        return jsonObj;
    }

    public static void wrapJson(String id, String index, JSONObject jsonObj) throws NumberFormatException, JSONException {
        //根据id获得index
        if (id != null && !id.isEmpty()) {
            index = getRecordIndexFromId(id, jsonObj);
        }
        int indexNum = Integer.parseInt(index);
        int count = jsonObj.getJSONArray("aaData").length();
        jsonObj.put("first", 0);
        if (indexNum > 0) {
            jsonObj.put("prev", indexNum - 1);
        } else {
            jsonObj.put("prev", 0);
        }
        if (indexNum < (count - 1)) {
            jsonObj.put("next", indexNum + 1);
        } else {
            jsonObj.put("next", count - 1);
        }
        jsonObj.put("last", count - 1);
        jsonObj.put("total", count);
        jsonObj.put("current", indexNum);
    }

    private static String getRecordIndexFromId(String id, JSONObject json) throws JSONException {
        String index = "-1";
        JSONArray jsonArr = json.getJSONArray("aaData");
        for (int i = 0; i < jsonArr.length(); i++) {
            Object obj = jsonArr.get(i);
            String currentId;
            if (obj instanceof JSONObject) {
                JSONObject jsonObj = (JSONObject) obj;
                currentId = jsonObj.getString("id");
            } else {
                ArrayList list = (ArrayList) jsonArr.get(i);
                currentId = "" + list.get(0);
            }
            if (id.equals(currentId)) {
                return i + "";
            }
        }
        return index;
    }

    public static ArrayList getIndexFromFileId(String fileId, JSONObject json) throws JSONException {
//        int index = -1;
        JSONArray jsonArr = json.getJSONArray("aaData");
        for (int i = 0; i < jsonArr.length(); i++) {
            ArrayList list = (ArrayList) jsonArr.get(i);
            if (fileId.equals(list.get(1) + "")) {
//                index = list.get(11) + "";
                return list;
//                break;
            }
        }
        return null;
    }

    /**
     * 获取上传文件保存路径
     * @param servlet the HttpServlet
     * @return 保存路径
     */
    public static String getUploadPath(HttpServlet servlet) {
        return servlet.getServletContext().getRealPath("/") + File.separator + UPLOAD_DIRECTORY + File.separator;
    }

}
