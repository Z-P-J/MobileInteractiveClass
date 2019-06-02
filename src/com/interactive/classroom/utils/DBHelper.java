package com.interactive.classroom.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Z-P-J
 * 数据库工具类
 */
public class DBHelper {

    private static DBHelper dbHelper;

    private static final int DEBUG_LEVEL = 1;

    public static final String DATEBASE_NAME = "my_test";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "zpj19990509";



//    public static final String DATEBASE_NAME = "xm05_teach";
//    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
//    private static final String USER_NAME = "xm05";
//    private static final String PASSWORD = "12345678";

    private static final String URL = "jdbc:mysql://localhost:3306/" + DATEBASE_NAME + "?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";



    private Connection a;
    private Statement statement;


    private DBHelper() {
        try {
            getStatement();
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
    }

    public synchronized static DBHelper getInstance() {
        if (dbHelper == null) {
            dbHelper = new DBHelper();
        }

        return dbHelper;
    }

    private Statement getStatement() throws SQLException {
        a = getConnection();
        statement = a.createStatement();
        return statement;
    }

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER_NAME);
            return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            statement.close();
            a.close();
            dbHelper = null;
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
    }

    public ResultSet executeQuery(String s) {
        ResultSet resultset = null;
        try {
            if (DEBUG_LEVEL > 0) {
                Log.d(getClass().getName(), "[" + TimeUtil.currentDate() + "]" + " executeQuery:" + s);
            }
            resultset = getStatement().executeQuery(s);
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        return resultset;
    }

    public DBHelper executeUpdate(String s) {
        try {
            if (DEBUG_LEVEL > 0) {
                Log.d(getClass().getName(), "[" + TimeUtil.currentDate() + "]" + " executeUpdate:" + s);
            }
            getStatement().executeUpdate(s);
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            return null;
        }
        return dbHelper;
    }

    public int executeUpdateAndGetId(String s) {
        try {
            if (DEBUG_LEVEL > 0) {
                Log.d(getClass().getName(), "[" + TimeUtil.currentDate() + "]" + " executeUpdate:" + s);
            }
            return getStatement().executeUpdate(s, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        return -1;
    }

    public void putTableColumnNames(String tableName, JSONObject jsonObj) {
        ResultSet rs = executeQuery("select * from " + tableName);
        try {
            ResultSetMetaData data = rs.getMetaData();
            List<String> columnList = new ArrayList<>();
            System.out.println("getColumnCount=" + data.getColumnCount());
            for (int i = 0; i < data.getColumnCount(); i++) {
                System.out.println("i=" + i);
                columnList.add(data.getColumnLabel(i + 1));
            }
            jsonObj.put("table_column_names", columnList);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        close();
    }

    public void putTableColumnNames(String[] labels, JSONObject jsonObj) {
        List<String> columnList = new ArrayList<>();
        Collections.addAll(columnList, labels);
        try {
            jsonObj.put("table_column_names", columnList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public void getProperty() {
//        Properties properties = new Properties();
//        Properties properties1 = new Properties();
//        try {
//            InputStream inputstream = getClass().getClassLoader().getResourceAsStream("/conf/db.properties");
//            InputStream inputstream1 = getClass().getClassLoader().getResourceAsStream("/conf/dbip.properties");
//            if (inputstream == null || inputstream1 == null) {
//                throw new NullPointerException("inputstream is null");
//            }
//            properties.load(inputstream);
//            properties1.load(inputstream1);
//            inputstream.close();
//            inputstream1.close();
//        } catch (IOException ex) {
//            System.err.println("Open Propety FileBean Error");
//        }
//        drivername = properties.getProperty("DRIVER");
//        url1 = properties.getProperty("URL1") + properties1.getProperty("IP") + ":3306/";
//        url2 = properties.getProperty("URL2");
//        //调试级别
//        String level = properties.getProperty("debuglevel");
//        if (level != null) {
//            DEBUG_LEVEL = Integer.parseInt(level);
//        } else {
//            DEBUG_LEVEL = 0;
//        }
//    }

}
