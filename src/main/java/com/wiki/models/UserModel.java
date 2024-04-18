package com.wiki.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wiki.config.Database;
import com.wiki.interfaces.user.UserInstance;

public class UserModel {
  public int id, role;
  public String fullname, email, password, salt, access_token, created_at, updated_at;

  public UserModel(int id, String fullname, String email, String password, String salt, String access_token, int role,
      String created_at, String updated_at) {
    this.id = id;
    this.fullname = fullname;
    this.email = email;
    this.password = password;
    this.salt = salt;
    this.access_token = access_token;
    this.role = role;
    this.created_at = created_at;
    this.updated_at = updated_at;
  }

  public static int insertUser(String fullname, String email, String password, String salt, String currentTime)
      throws SQLException {
    String statement = "INSERT INTO user (fullname, email, password, salt, created_at) VALUES(?,?,?,?,?)";
    return Database.update(statement, fullname, email.toLowerCase(), password, salt, currentTime);
  }

  public static UserInstance getUserByAccessToken(String token) throws SQLException {
    String statement = "SELECT * FROM user WHERE access_token = ? LIMIT 1";
    ResultSet res = Database.query(statement, token);

    UserInstance user = null;
    while (res.next()) {
      user = new UserInstance(
          res.getInt("id"),
          res.getString("fullname"),
          res.getString("email"),
          res.getString("access_token"),
          res.getInt("role"));
    }

    return user;
  }

  public static UserModel getUserByEmail(String email) throws SQLException {
    String statement = "SELECT * FROM user WHERE email = ? LIMIT 1";
    ResultSet res = Database.query(statement, email.toLowerCase());

    UserModel user = null;
    while (res.next()) {
      user = new UserModel(
          res.getInt("id"),
          res.getString("fullname"),
          res.getString("email"),
          res.getString("password"),
          res.getString("salt"),
          res.getString("access_token"),
          res.getInt("role"),
          res.getString("created_at"),
          res.getString("updated_at"));
    }

    if (user != null)
      return user;

    return null;
  }

  public static void setAccessTokenById(int id, String token) throws SQLException {
    String statement = "UPDATE user SET access_token = ?";
    Database.update(statement, token);
  }
}