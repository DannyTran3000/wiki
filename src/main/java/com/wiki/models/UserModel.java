package com.wiki.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.wiki.config.Database;
import com.wiki.interfaces.user.UserPublic;

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

  public static UserPublic selectUserByAccessToken(String token) throws SQLException {
    String statement = "SELECT email, fullname, access_token, role FROM user WHERE access_token = ? AND status = 1";
    ResultSet resultSet = Database.query(statement, token);

    UserPublic user = null;
    while (resultSet.next()) {
      user = new UserPublic(
          resultSet.getString("email"),
          resultSet.getString("fullname"),
          resultSet.getString("access_token"),
          resultSet.getInt("role"));
    }

    return user;
  }

  public static UserModel selectUserByEmail(String email) throws SQLException {
    String statement = "SELECT * FROM user WHERE email = ? AND status = 1";
    ResultSet resultSet = Database.query(statement, email.toLowerCase());

    UserModel user = null;
    while (resultSet.next()) {
      user = new UserModel(
          resultSet.getString("email"),
          resultSet.getString("fullname"),
          resultSet.getString("password"),
          resultSet.getString("salt"),
          resultSet.getString("access_token"),
          resultSet.getInt("role"),
          resultSet.getInt("status"),
          resultSet.getTimestamp("created_at"));
    }

    if (user != null)
      return user;

    return null;
  }

  public static int updateAccessTokenByEmail(String email, String token) throws SQLException {
    String statement = "UPDATE user SET access_token = ? WHERE email = ?";
    return Database.update(statement, token, email);
  }

  public static int updateAccessTokenToNull(String token) throws SQLException {
    String statement = "UPDATE user SET access_token = ? WHERE access_token = ?";
    return Database.update(statement, null, token);
  }

  public static int updatePasswordByEmail(String email, String password) throws SQLException {
    String statement = "UPDATE user SET password = ? WHERE email = ?";
    return Database.update(statement, password, email);
  }

  public static int updateRoleByEmail(String email, int role) throws SQLException {
    String statement = "UPDATE user SET role = ? WHERE email = ?";
    return Database.update(statement, role, email);
  }
}