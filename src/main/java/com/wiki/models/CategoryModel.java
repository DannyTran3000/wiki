package com.wiki.models;

import java.sql.SQLException;
import java.sql.Timestamp;

import com.wiki.config.Database;

public class CategoryModel {
  public int id, status;
  public String name, iconUrl, slug;
  public Timestamp createdAt;

  public CategoryModel(int id, String name, String iconUrl, String slug, int status, Timestamp createdAt) {
    this.id = id;
    this.name = name;
    this.iconUrl = iconUrl;
    this.slug = slug;
    this.status = status;
    this.createdAt = createdAt;
  }

  public static int insertCategory(String name, String iconUrl, String slug) throws SQLException {
    String statement = "INSERT INTO category (name, icon_url, slug) VALUES(?,?,?)";
    return Database.update(statement, name, iconUrl, slug);
  }
}
