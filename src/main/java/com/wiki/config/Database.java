package com.wiki.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	private static Connection con;

	private static void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ResultSet query(String q, Object... params) throws SQLException {
		ResultSet resultSet = null;

		connect();

		PreparedStatement stmt = con.prepareStatement(q);
		// Set parameters if any
		for (int i = 0; i < params.length; i++) {
			stmt.setObject(i + 1, params[i]);
		}
		resultSet = stmt.executeQuery();

		return resultSet;
	}

	public static int update(String q, Object... params) throws SQLException {
		connect();

		PreparedStatement stmt = con.prepareStatement(q);
		// Set parameters if any
		for (int i = 0; i < params.length; i++) {
			stmt.setObject(i + 1, params[i]);
		}
		int response = stmt.executeUpdate();

		return response;
	}
}