package com.wiki.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.wiki.config.Database;

public class CategoryModel {
  public int id, status;
  public String name, icon, pathname;
  public Timestamp createdAt;

  public CategoryModel(int id, String name, String icon, String pathname, int status, Timestamp createdAt) {
    this.id = id;
    this.name = name;
    this.icon = icon;
    this.pathname = pathname;
    this.status = status;
    this.createdAt = createdAt;
  }

  public static int insertCategory(String name, String icon, String pathname) throws SQLException {
    String statement = "INSERT INTO category (name, icon, pathname) VALUES(?,?,?)";
    return Database.update(statement, name, icon, pathname);
  }

  public static String selectCategoryPathnameById(int id) throws SQLException {
    String statement = "SELECT pathname FROM category WHERE id = ? AND status = 1 LIMIT 1";
    ResultSet resultSet = Database.query(statement, id);

    String pathname = "";
    while (resultSet.next()) {
      pathname = resultSet.getString("pathname");
    }

    return pathname;
  }
}
