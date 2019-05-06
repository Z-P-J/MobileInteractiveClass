package utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author 25714
 * 封装一些该项目中Servlet中常用函数
 */
public final class ServletUtil {

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
        return jsonObj;
    }

    private static String getRecordIndexFromId(String id, JSONObject json) throws JSONException {
        String index = "-1";
        JSONArray jsonArr = json.getJSONArray("aaData");
        for (int i = 0; i < jsonArr.length(); i++) {
            ArrayList list = (ArrayList) jsonArr.get(i);
            if (id.equals(list.get(0) + "")) {
                index = list.get(11) + "";
                break;
            }
        }
        return index;
    }

}
