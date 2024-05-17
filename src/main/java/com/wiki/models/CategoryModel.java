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

  public static int insertCategory(String name, String icon, String slug) throws SQLException {
    String statement = "INSERT INTO category (name, icon, slug) VALUES(?,?,?)";
    return Database.update(statement, name, icon, slug);
  }

  public static List<CategoryPublic> selectAll() throws SQLException {
    String select = Database.prepareStructureSQL(
        "SELECT ?,?,?,?",
        "C.name",
        "C.icon",
        "C.slug",
        "COUNT(A.id) AS article_count");
    String from = "FROM category AS C";
    String join = "LEFT JOIN article AS A ON C.id = A.category_id";
    String where = "WHERE C.status = 1";
    String rest = "GROUP BY C.id";

    String statement = select + " " + from + " " + join + " " + where + " " + rest;
    ResultSet resultSet = Database.query(statement);

    List<CategoryPublic> categoryList = new ArrayList<>();
    while (resultSet.next()) {
      CategoryPublic category = new CategoryPublic(
          resultSet.getString("name"),
          resultSet.getString("icon"),
          resultSet.getString("slug"),
          resultSet.getInt("article_count"));

      categoryList.add(category);
    }

    return categoryList;
  }

  public static String selectCategorySlugById(int id) throws SQLException {
    String statement = "SELECT slug FROM category WHERE id = ? AND status = 1";
    ResultSet resultSet = Database.query(statement, id);

    String slug = "";
    while (resultSet.next()) {
      slug = resultSet.getString("slug");
    }

    return slug;
  }
}
