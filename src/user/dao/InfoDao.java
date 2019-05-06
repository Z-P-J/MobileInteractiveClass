package user.dao;

import org.json.JSONException;
import org.json.JSONObject;
import user.bean.InfoBean;
import utility.DBHelper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InfoDao {

    private static final String TABLE_NAME = "user_info";

    /*
     * 功能：返回结果集
     */
    public JSONObject getRecord(InfoBean query) throws SQLException, IOException, JSONException {
        //开始查询数据库
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        try {
            //构造sql语句，根据传递过来的查询条件参数
            String sql = "";
            int count = 0;
            query.setTableName(TABLE_NAME);
            sql = createGetRecordSql(query);
            System.out.println("------------------------[getRecord]" + sql);
            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
            while (rs.next()) {
                List list = new ArrayList();
                list.add(rs.getString("id"));
                list.add(rs.getString("title"));
                list.add(rs.getString("content"));
                list.add(rs.getString("type"));
                list.add(rs.getString("limit_time"));
                list.add(rs.getString("end_time"));
                list.add(rs.getString("end_tag"));
                list.add(rs.getString("user_id"));
                list.add(rs.getString("creator"));
                list.add(rs.getString("create_time"));
                if (query.getUserId() != null && query.getUserId().equals(rs.getString("user_id"))) {
                    list.add("1");
                } else {
                    list.add("0");
                }
                list.add(count);
                count = count + 1;    //做上下记录导航用
                jsonList.add(list);
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
        //下面�?始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"�?
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代�?
        return jsonObj;
    }

    public JSONObject modifyRecord(InfoBean info) throws JSONException {
        //String action,String dbName,String id,String title,String content,String creator,String createTime
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        try {
            //构�?�sql语句，根据传递过来的查询条件参数
            String sql = "update " + TABLE_NAME + " set title='" + info.getTitle() + "',content='" + info.getContent() + "',limit_time='" + info.getLimitTime() + "' where id=" + info.getId();
            DBHelper.getInstance().executeUpdate(sql);
            sql = "select * from " + TABLE_NAME + " order by create_time desc";
            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
            while (rs.next()) {
                List list = new ArrayList();
                list.add(rs.getString("id"));
                list.add(rs.getString("content"));
                jsonList.add(list);
            }
            rs.close();
            DBHelper.getInstance().close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        //下面�?始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", info.getAction());
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"�?
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代�?
        return jsonObj;
    }

    public JSONObject getRecordById(String action, String dbName, String id) throws JSONException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        try {
            //构�?�sql语句，根据传递过来的查询条件参数
            String sql = "select * from " + TABLE_NAME + " where id=" + id + " order by create_time desc";
            ResultSet rs = DBHelper.getInstance().executeQuery(sql);
            while (rs.next()) {
                List list = new ArrayList();
                list.add(rs.getString("id"));
                list.add(rs.getString("content"));
                jsonList.add(list);
            }
            rs.close();
            DBHelper.getInstance().close();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            resultCode = 10;
            resultMsg = "查询数据库出现错误！" + sqlexception.getMessage();
        }
        //下面�?始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", action);
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"�?
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代�?
        return jsonObj;
    }

    public JSONObject addRecord(InfoBean info) throws JSONException, SQLException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        //构�?�sql语句，根据传递过来的查询条件参数
        String sql = "insert into " + TABLE_NAME + "(parent_id,title,content,limit_time,user_id,creator,create_time) values('" + info.getParentId() + "','" + info.getTitle() + "','" + info.getContent() +
                "','" + info.getLimitTime() + "','" + info.getUserId() + "','" + info.getCreator() + "','" + info.getCreateTime() + "')";
        DBHelper.getInstance().executeUpdate(sql).close();
        //下面�?始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", info.getAction());
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"�?
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代�?
        return jsonObj;
    }

    public JSONObject deleteRecord(String action, String dbName, String[] ids, String creator, String createTime) throws JSONException, SQLException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        //构�?�sql语句，根据传递过来的查询条件参数
        for (String id : ids) {
            String sql = "delete from " + TABLE_NAME + " where id=" + id;
            DBHelper.getInstance().executeUpdate(sql);
        }
        DBHelper.getInstance().close();
        //下面�?始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", action);
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"�?
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代�?
        return jsonObj;
    }

    public JSONObject setRecordTop(String action, String dbName, String type, String userId, String id) throws JSONException, SQLException {
        String resultMsg = "ok";
        int resultCode = 0;
        List jsonList = new ArrayList();
        //构�?�sql语句，根据传递过来的查询条件参数
        String sql = "select max(priority) as priority from " + TABLE_NAME + " where user_id='" + userId + "'";
        int priority = 0;
        ResultSet rs = DBHelper.getInstance().executeQuery(sql);
        if (rs.next()) {
            priority = rs.getInt("priority");
        }
        DBHelper.getInstance().executeUpdate("update " + TABLE_NAME + " set priority=" + (priority + 1) + " where id=" + id).close();
        //下面�?始构建返回的json
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("aaData", jsonList);
        jsonObj.put("action", action);
        jsonObj.put("result_msg", resultMsg);//如果发生错误就设置成"error"�?
        jsonObj.put("result_code", resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代�?
        return jsonObj;
    }

    /*
     * 功能：构造历史记录查询的sql语句,type=all查询�?有，type=id查询某个记录，余下按照条件设置查�?
     */
    private String createGetRecordSql(InfoBean query) {
        String sql = "";
        String where = "";
        if (query.getId() != null && !query.getId().equals("null")) {
            where = "where id=" + query.getId();
        }
        if (query.getTitle() != null && !query.getTitle().equals("null") && !query.getTitle().isEmpty()) {
            if (!where.isEmpty()) {
                where = where + " and title like '%" + query.getTitle() + "%'";
            } else {
                where = "where title like '%" + query.getTitle() + "%'";
            }
        }
        if (query.getTimeFrom() != null && query.getTimeTo() != null && !query.getTimeFrom().isEmpty()) {
            if (!where.isEmpty()) {
                where = where + " and create_time between '" + query.getTimeFrom() + "' and '" + query.getTimeTo() + "'";
            } else {
                where = "where create_time between '" + query.getTimeFrom() + "' and '" + query.getTimeTo() + "'";
            }
        }


        String orderBy = "";
        if ((query.getSortIndex() != null) && (!query.getSortIndex().equals("null"))) {
            if (query.getOrderBy() != null) {
                orderBy = " order by " + getOrderBy(query.getOrderBy());
                System.out.println("---------------------------------orderBy:" + orderBy);
            }
        }


        if (query.getType() != null && query.getType().equals("all") && query.getUserRole().equals("manager")) {
            sql = "select * from " + query.getTableName() + " order by create_time desc";
        } else {
            if (query.getId() != null && !query.getId().equals("null")) {
                sql = "select * from " + query.getTableName() + " where id=" + query.getId();
            } else {
                if (where.isEmpty()) {
                    sql = "select * from " + query.getTableName() + " where user_id='" + query.getUserId() + "'" + orderBy;
                    System.out.println("---------------------------------orderBy:" + orderBy);
                } else {
                    sql = "select * from " + query.getTableName() + " " + where + " and user_id='" + query.getUserId() + "'" + orderBy;
                }
            }
        }
        return sql;
    }

    private String getOrderBy(String orderName) {
        if (orderName.equals("create_time")) {
            orderName = "create_time";
        }
        if (orderName.equals("title")) {
            orderName = "title";
        }
        if (orderName.equals("content")) {
            orderName = "content";
        }
        return orderName;
    }
}
