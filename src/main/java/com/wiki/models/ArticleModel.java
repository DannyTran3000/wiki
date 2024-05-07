package com.wiki.models;

import java.sql.SQLException;
import java.sql.Timestamp;

import com.wiki.config.Database;

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

  
}
