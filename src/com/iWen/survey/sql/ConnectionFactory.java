package com.iWen.survey.sql;

import com.interactive.classroom.utils.DatabaseHelper;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {

	private ConnectionFactory() {
	}

	public static Connection getConnection() {
//		try {
//			Class.forName(DRIVER_NAME);
//			return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.print("数据库连接异常");
//		}
//		return null;
		try {
			return DatabaseHelper.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
