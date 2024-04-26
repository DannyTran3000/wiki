package com.wiki.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	private static Connection con;

	/**
	 * Establishes a connection to the MySQL database using the configured URL,
	 * username, and password.
	 * This method is private and is called internally by other methods in the
	 * class.
	 */
	private static void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Executes a SQL query with optional parameters and returns the result set.
	 *
	 * @param q      The SQL query string.
	 * @param params Optional parameters to be set in the query.
	 * @return A ResultSet containing the query results.
	 * @throws SQLException If an SQL error occurs during query execution.
	 */
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

	/**
	 * Executes an SQL update (insert, update, delete) statement with optional
	 * parameters and returns the number of affected rows.
	 *
	 * @param q      The SQL update string.
	 * @param params Optional parameters to be set in the update statement.
	 * @return The number of rows affected by the update operation.
	 * @throws SQLException If an SQL error occurs during update execution.
	 */
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