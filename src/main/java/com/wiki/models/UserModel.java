package com.wiki.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.wiki.config.Database;

public class UserModel {
  public String email, fullname, password, salt, accessToken;
  public int role, status;
  public Timestamp createdAt;

  public UserModel(String email, String fullname, String password, String salt, String accessToken, int role,
      int status, Timestamp createdAt) {
    this.email = email;
    this.fullname = fullname;
    this.password = password;
    this.salt = salt;
    this.accessToken = accessToken;
    this.role = role;
    this.status = status;
    this.createdAt = createdAt;
  }

  public static int insertUser(String email, String fullname, String password, String salt)
      throws SQLException {
    String statement = "INSERT INTO user (email, fullname, password, salt) VALUES(?,?,?,?)";
    return Database.update(statement, email.toLowerCase(), fullname, password, salt);
  }

  public static ResultSet selectUserByAccessToken(String token) throws SQLException {
    String statement = "SELECT * FROM user WHERE access_token = ? LIMIT 1";
    return Database.query(statement, token);
  }

  public static UserModel selectUserByEmail(String email) throws SQLException {
    String statement = "SELECT * FROM user WHERE email = ? LIMIT 1";
    ResultSet res = Database.query(statement, email.toLowerCase());

    UserModel user = null;
    while (res.next()) {
      user = new UserModel(
          res.getString("email"),
          res.getString("fullname"),
          res.getString("password"),
          res.getString("salt"),
          res.getString("access_token"),
          res.getInt("role"),
          res.getInt("status"),
          res.getTimestamp("created_at"));
    }

    if (user != null)
      return user;

    return null;
  }

  public static int updateAccessTokenByEmail(String email, String token) throws SQLException {
    String statement = "UPDATE user SET access_token = ? WHERE email = ?";
    return Database.update(statement, token, email);
  }

  public static int updatePasswordByEmail(String email, String password) throws SQLException {
    String statement = "UPDATE user SET password = ? WHERE email = ?";
    return Database.update(statement, password, email);
  }
}