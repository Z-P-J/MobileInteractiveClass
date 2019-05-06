package statistic.dao;

import org.json.JSONException;
import org.json.JSONObject;
import statistic.bean.StatisticBean;
import utility.DBHelper;
import utility.Log;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticDao {

    public String module = "project";
    public String sub = "investigation";

    /*
     * 功能：模板函数
     */
//    public JSONObject doAction(String action, String dbName, String id, String content, String creator, String createTime) throws JSONException {
//        String resultMsg = "ok";
//        int resultCode = 0;
//        List<List<String>> jsonList = new ArrayList<>();
//        try {
//            //构造sql语句，根据传递过来的查询条件参数
//            String tableName = "project_statistic";
//            String sql = "insert into " + tableName + "(content,creator,create_time) values('" + content + "','" + creator + "','" + createTime + "')";
//            DBHelper.getInstance().executeUpdate(sql);
//            sql = "select * from " + tableName + " order by create_time desc";
//            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
//            while (rs.next()) {
//                List<String> list = new ArrayList<>();
//                list.add(rs.getString("id"));
//                list.add(rs.getString("content"));
//                jsonList.add(list);
//            }
//            rs.close();
//            DBHelper.getInstance().close();
//        } catch (SQLException sqlexception) {
//            sqlexception.printStackTrace();
//            resultCode = 10;
//            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
//        }
//        //下面开始构建返回的json
//        JSONObject jsonObj = new JSONObject();
//        jsonObj.put("aaData", jsonList);
//        jsonObj.put("action", action);
//        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"等
//        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
//        return jsonObj;
//    }

    public JSONObject statisticRecord(String action, StatisticBean statisticBean) throws SQLException, IOException, JSONException {
        //开始查询数据库
        String resultMsg = "ok";
        int resultCode = 0;
        List<List<String>> jsonList = new ArrayList<>();
        int count = 0;
        try {
            //构造sql语句，根据传递过来的查询条件参数
            String sql = getStatisticRecordSql(statisticBean);
            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
            while (rs.next()) {
                //查询出来有plate_color,time_interval,count,plate_color_name这几项
                List<String> list = new ArrayList<>();
                list.add(count + "");
                list.add(rs.getString("time_interval"));
                list.add(rs.getString("count"));
                list.add("无区分");
                list.add("无区分");
                //showDebug(rs.getString("plate_color")+","+rs.getString("address_id")+","+rs.getString("time_interval")+","+rs.getString("count"));
                jsonList.add(list);
                count = count + 1;
            }
            rs.close();
            DBHelper.getInstance().close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        //////////数据库查询完毕，得到了json数组jsonList//////////
        //jsonList.clear();
        //下面开始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", action);
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"等
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
        return jsonObj;
    }

    private String getStatisticRecordSql(StatisticBean statisticBean) {
        String sql = "";
        String timeInterval = "";
        if ("hour".equals(statisticBean.getTimeInterval())) {
            timeInterval = "%Y-%m-%d %h";
        }
        if ("day".equals(statisticBean.getTimeInterval())) {
            timeInterval = "%Y-%m-%d";
        }
        if ("month".equals(statisticBean.getTimeInterval())) {
            timeInterval = "%Y-%m";
        }
        sql = "select date_format(create_time,\"" + timeInterval + "\") as time_interval,count(*) as count from " + statisticBean.getTableName() + " a";
        sql = sql + " where create_time between \"" + statisticBean.getTimeFrom() + "\" and \"" + statisticBean.getTimeTo() + "\"";
        sql = sql + " and user_id='" + statisticBean.getUserId() + "'";
        sql = sql + " group by time_interval order by time_interval";
        Log.d(getClass().getName(), "sql=" + sql);
        return sql;
    }
}
