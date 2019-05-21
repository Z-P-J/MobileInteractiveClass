package com.iWen.survey.sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	public static final String DATEBASE_NAME = "survey";
	private static final String URL = "jdbc:mysql://localhost:3306/" + DATEBASE_NAME + "?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";
	private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "zpj19990509";

	private ConnectionFactory() {
	}

	public static Connection getConnection() {
		try {
			Class.forName(DRIVER_NAME);
			return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("数据库连接异常");
		}
		return null;
	}
}
