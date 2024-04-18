package com.wiki.config;

import java.sql.*;

public class Database {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/mp_wiki";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";

	private static Connection con;

	private static void connect() {
		try {
			con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			System.out.println("sql connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void disconnect() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet query(String q) throws SQLException {
		ResultSet resultSet = null;

		connect();
		PreparedStatement stmt = con.prepareStatement(q);
		resultSet = stmt.executeQuery();

		disconnect();

		return resultSet;
	}

	public static int modify(String q, Object... params) throws SQLException {
		connect();
		PreparedStatement stmt = con.prepareStatement(q);
		// Set parameters if any
		for (int i = 0; i < params.length; i++) {
			stmt.setObject(i + 1, params[i]);
		}

		System.out.println("====================: " + stmt);

		int response = stmt.executeUpdate();

		disconnect();

		return response;
	}
}