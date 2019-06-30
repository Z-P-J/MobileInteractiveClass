package com.interactive.classroom.utils;

import com.sun.rowset.CachedRowSetImpl;
import org.json.JSONException;
import org.json.JSONObject;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 数据库工具类
 * @author Z-P-J
 */
public final class DatabaseHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "my_test";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "zpj19990509";

//    private static final String DATABASE_NAME = "xm05_teach";
//    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
//    private static final String USER_NAME = "xm05";
//    private static final String PASSWORD = "12345678";

    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";

    private DatabaseHelper() { }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }

    private static void close(Statement statement, Connection connection) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
//            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        return false;
    }

    public static ResultSet executeQuery(String s) throws SQLException {
        Log.d(TAG, "[" + TimeUtil.currentDate() + "]" + " executeQuery:" + s);
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet =  statement.executeQuery(s);
        CachedRowSet rowSet = new CachedRowSetImpl();
        rowSet.populate(resultSet);
        resultSet.close();
        close(statement, connection);
        return rowSet;
    }

    public static boolean executeUpdate(String s){
        Log.d(TAG, "[" + TimeUtil.currentDate() + "]" + " executeUpdate:" + s);
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(s);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(statement, connection);
        }
    }

    public static boolean executeUpdate(String sql, String ... args){
        Log.d(TAG, "[" + TimeUtil.currentDate() + "]" + " executeUpdateWithArgs:" + sql);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            for (int i = 1; i <= args.length; i++) {
                statement.setString(i, args[i - 1]);
            }
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(statement, connection);
        }
    }

    public static int executeUpdateAndGetId(String sql) {
        Log.d(TAG, "[" + TimeUtil.currentDate() + "]" + " executeUpdateAndGetId:" + sql);
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            return statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
            return -1;
        } finally {
            close(statement, connection);
        }
    }

    public static void putTableColumnNames(String tableName, JSONObject jsonObj) throws SQLException, JSONException {
        ResultSet rs = executeQuery("select * from " + tableName);
        ResultSetMetaData data = rs.getMetaData();
        List<String> columnList = new ArrayList<>();
        System.out.println("getColumnCount=" + data.getColumnCount());
        for (int i = 0; i < data.getColumnCount(); i++) {
            System.out.println("i=" + i);
            columnList.add(data.getColumnLabel(i + 1));
        }
        jsonObj.put("table_column_names", columnList);
        rs.close();
    }

    public static void putTableColumnNames(String[] labels, String[] labelsZh, JSONObject jsonObj) throws SQLException, JSONException {
        List<String> columnList = new ArrayList<>();
        List<String> columnListZh = new ArrayList<>();
        Collections.addAll(columnList, labels);
        Collections.addAll(columnListZh, labelsZh);
        jsonObj.put("table_column_names", columnList);
        jsonObj.put("table_column_names_zh", columnListZh);
    }

}
