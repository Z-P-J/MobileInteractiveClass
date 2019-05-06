package utility;

import java.sql.*;

/**
 * @author 25714
 * 数据库工具类
 */
public class DBHelper {

    private static DBHelper dbHelper;

    private static final int DEBUG_LEVEL = 1;

    public static final String DATEBASE_NAME = "my_test";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATEBASE_NAME + "?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "zpj19990509";

    private Connection a;
    private Statement statement;


    private DBHelper() {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException classnotfoundexception) {
            classnotfoundexception.printStackTrace();
        }
        try {
            a = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            statement = a.createStatement();
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
            resultset = statement.executeQuery(s);
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
            statement.executeUpdate(s);
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        return dbHelper;
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
