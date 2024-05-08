package com.wiki.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.wiki.config.Database;
import com.wiki.interfaces.article.ArticlePublic;

public class ArticleModel {
  public int id, status, categoryId;
  public String title, thumbnail, description, content, pathname;
  public Timestamp createdAt;

  public ArticleModel(int id, String title, String thumbnail, String description, String content, String pathname,
      int status, int categoryId, Timestamp createdAt) {
    this.id = id;
    this.title = title;
    this.thumbnail = thumbnail;
    this.description = description;
    this.content = content;
    this.pathname = pathname;
    this.status = status;
    this.categoryId = categoryId;
    this.createdAt = createdAt;
  }

  public static int insertArticle(String title, String thumbnail, String description, String content, String pathname,
      int categoryId) throws SQLException {
    String statement = "INSERT INTO article (title, thumbnail, description, content, pathname, category_id) VALUES(?,?,?,?,?,?)";
    return Database.update(statement, title, thumbnail, description, content, pathname, categoryId);
  }

  public static List<ArticlePublic> selectLatestArticles(int limit) throws SQLException {
    final String select = Database.prepareStructureSQL(
        "SELECT ?,?,?,?,?,?,?,?",
        "A.title",
        "A.thumbnail",
        "A.description",
        "A.content",
        "A.pathname",
        "C.name AS category_name",
        "C.pathname AS category_pathname",
        "A.created_at");
    final String from = "FROM article AS A";
    final String join = "JOIN category AS C ON A.category_id = C.id";
    final String where = "WHERE A.status = 1";
    final String rest = "ORDER BY A.created_at DESC LIMIT ?";
    
    String statement = select + " " + from + " " + join + " " + where + " " + rest;
    ResultSet resultSet = Database.query(statement, limit);

    List<ArticlePublic> articleList = new ArrayList<>();
    while (resultSet.next()) {
      ArticlePublic article = new ArticlePublic(
          resultSet.getString("title"),
          resultSet.getString("thumbnail"),
          resultSet.getString("description"),
          resultSet.getString("content"),
          resultSet.getString("pathname"),
          resultSet.getString("category_name"),
          resultSet.getString("category_pathname"),
          resultSet.getTimestamp("created_at"));

      articleList.add(article);
    }

    return articleList;
  }
}
