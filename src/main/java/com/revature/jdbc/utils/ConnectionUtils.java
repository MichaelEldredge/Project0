package com.revature.jdbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
	private Connection connection;
	private static ConnectionUtils connectionUtils;

	public Connection getConnection() {
		return connection;
	}
	
	private ConnectionUtils() throws SQLException {

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(
					System.getenv("DB_URL")+System.getenv("DB_NAME"),
					System.getenv("DB_USER"),
					System.getenv("DB_PASS"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ConnectionUtils getInstance() throws SQLException {
		if(connectionUtils == null) {
			connectionUtils = new ConnectionUtils();
		}else if(connectionUtils.getConnection().isClosed()) {
			connectionUtils = new ConnectionUtils();
		}
		return connectionUtils;
	}
}
