package com.wiki.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.wiki.config.Database;
import com.wiki.interfaces.category.CategoryPublic;

public class CategoryModel {
  public int id, status;
  public String name, icon, slug;
  public Timestamp createdAt;

  public CategoryModel(int id, String name, String icon, String slug, int status, Timestamp createdAt) {
    this.id = id;
    this.name = name;
    this.icon = icon;
    this.slug = slug;
    this.status = status;
    this.createdAt = createdAt;
  }

  public static int deleteCategory(String slug) throws SQLException {
    String statement = "DELETE FROM category WHERE slug = ?";
    return Database.update(statement, slug);
  }

  public static int insertCategory(String name, String icon, String slug) throws SQLException {
    String statement = "INSERT INTO category (name, icon, slug) VALUES(?,?,?)";
    return Database.update(statement, name, icon, slug);
  }

  public static List<CategoryPublic> selectAll() throws SQLException {
    String select = Database.prepareStructureSQL(
        "SELECT ?,?,?,?,?,?,?",
        "C.id",
        "C.name",
        "C.icon",
        "C.slug",
        "C.status",
        "DATE_FORMAT(C.created_at, '%d-%m-%Y') AS created_at",
        "COUNT(A.id) AS article_count");
    String from = "FROM category AS C";
    String join = "LEFT JOIN article AS A ON C.id = A.category_id";
    String where = "WHERE C.status = 1";
    String rest = "GROUP BY C.id ORDER BY C.created_at DESC";

    String statement = select + " " + from + " " + join + " " + where + " " + rest;
    ResultSet resultSet = Database.query(statement);

    List<CategoryPublic> categoryList = new ArrayList<>();
    while (resultSet.next()) {
      CategoryPublic category = new CategoryPublic(
          resultSet.getInt("id"),
          resultSet.getString("name"),
          resultSet.getString("icon"),
          resultSet.getString("slug"),
          resultSet.getInt("status"),
          resultSet.getString("created_at"),
          resultSet.getInt("article_count"));

      categoryList.add(category);
    }

    return categoryList;
  }

  public static String selectCategoryNameBySlug(String slug) throws SQLException {
    String statement = "SELECT name FROM category WHERE status = 1 AND slug = ?";
    ResultSet resultSet = Database.query(statement, slug);

    String name = "";
    while (resultSet.next()) {
      name = resultSet.getString("name");
    }

    return name;
  }

  public static CategoryPublic selectCategoryBySlug(String slug) throws SQLException {
    String select = Database.prepareStructureSQL(
        "SELECT ?,?,?,?,?,?,?",
        "C.id",
        "C.name",
        "C.icon",
        "C.slug",
        "C.status",
        "DATE_FORMAT(C.created_at, '%d-%m-%Y') AS created_at",
        "COUNT(A.id) AS article_count");
    String from = "FROM category AS C";
    String join = "LEFT JOIN article AS A ON C.id = A.category_id";
    String where = "WHERE C.status = 1 AND C.slug = ?";
    String rest = "GROUP BY C.id";

    String statement = select + " " + from + " " + join + " " + where + " " + rest;
    ResultSet resultSet = Database.query(statement, slug);

    CategoryPublic category = null;
    while (resultSet.next()) {
      category = new CategoryPublic(
          resultSet.getInt("id"),
          resultSet.getString("name"),
          resultSet.getString("icon"),
          resultSet.getString("slug"),
          resultSet.getInt("status"),
          resultSet.getString("created_at"),
          resultSet.getInt("article_count"));
    }

    return category;
  }

  public static int updateCategory(String name, String icon, String slug) throws SQLException {
    String statement = "UPDATE category SET name = ?, icon = ? WHERE slug = ?";
    return Database.update(statement, name, icon, slug);
  }
}
