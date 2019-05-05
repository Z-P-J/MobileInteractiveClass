package utility;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class DBHelper {

    private static DBHelper dbHelper;

    public static final String DATEBASE_NAME = "my_test";

    private Connection a;
    private Statement statement;
    private String drivername;
    private String database;
    private String url1;
    private String url2;
    private Integer debugLevel = 1;

    private DBHelper() {
        System.out.println("ylx_db");
        getProperty();
//		String s1 = url1 + s + "?" + url2;
        database = "my_test";
        String s1 = "jdbc:mysql://localhost:3306/my_test?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";
        try {
            System.out.println("drivername=" + drivername);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException classnotfoundexception) {
            classnotfoundexception.printStackTrace();
        }
        try {
            a = DriverManager.getConnection(s1, "root", "zpj19990509");
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
            if (debugLevel > 0) {
                Log.d(getClass().getName(), "[" + TimeUtil.currentDate() + "]" + "ylx_db executeQuery:" + s);
            }
            resultset = statement.executeQuery(s);
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        return resultset;
    }

    public DBHelper executeUpdate(String s) {
        try {
            if (debugLevel > 0) {
                System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + "]" + "ylx_db executeUpdate:" + s);
            }
            statement.executeUpdate(s);
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }
        return dbHelper;
    }

    public String getTable() {
        return database;
    }

    public void getProperty() {
        Properties properties = new Properties();
        Properties properties1 = new Properties();
        try {
            InputStream inputstream = getClass().getClassLoader().getResourceAsStream("/conf/db.properties");
            InputStream inputstream1 = getClass().getClassLoader().getResourceAsStream("/conf/dbip.properties");
            if (inputstream == null || inputstream1 == null) {
                throw new NullPointerException("inputstream is null");
            }
            properties.load(inputstream);
            properties1.load(inputstream1);
            inputstream.close();
            inputstream1.close();
        } catch (IOException ex) {
            System.err.println("Open Propety FileBean Error");
        }
        drivername = properties.getProperty("DRIVER");
        url1 = properties.getProperty("URL1") + properties1.getProperty("IP") + ":3306/";
        url2 = properties.getProperty("URL2");
        //调试级别
        String level = properties.getProperty("debuglevel");
        if (level != null) {
            debugLevel = Integer.parseInt(level);
        } else {
            debugLevel = 0;
        }
    }

    public void setTable(String s) {
        database = s;
    }

}
