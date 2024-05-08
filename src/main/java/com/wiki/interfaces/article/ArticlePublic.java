package com.wiki.interfaces.article;

import java.sql.Timestamp;

public class ArticlePublic {
  public String title, thumbnail, description, content, pathname, categoryName, categoryPathname;
  public Timestamp createdAt;

  public ArticlePublic(String title, String thumbnail, String description, String content, String pathname,
      String categoryName, String categoryPathname, Timestamp timestamp) {
    this.title = title;
    this.thumbnail = thumbnail;
    this.description = description;
    this.content = content;
    this.pathname = pathname;
    this.categoryName = categoryName;
    this.categoryPathname = categoryPathname;
    this.createdAt = timestamp;
  }

  public String getTitle() {
    return title;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public String getDescription() {
    return description;
  }

  public String getContent() {
    return content;
  }

  public String getPathname() {
    return pathname;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public String getCategoryPathname() {
    return categoryPathname;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }
}
