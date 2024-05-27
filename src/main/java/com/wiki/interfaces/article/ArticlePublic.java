package com.wiki.interfaces.article;

public class ArticlePublic {
  public String title, thumbnail, description, content, slug, categoryName, categorySlug, createdAt;
  public int views, categoryId, status;

  public ArticlePublic(String title, String thumbnail, String description, String content, int views, String slug,
      int categoryId, String categoryName, String categorySlug, int status, String createdAt) {
    this.title = title;
    this.thumbnail = thumbnail;
    this.description = description;
    this.content = content;
    this.views = views;
    this.slug = slug;
    this.categoryId = categoryId;
    this.categoryName = categoryName;
    this.categorySlug = categorySlug;
    this.status = status;
    this.createdAt = createdAt;
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

  public int getViews() {
    return views;
  }

  public String getSlug() {
    return slug;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public String getCategorySlug() {
    return categorySlug;
  }

  public int getStatus() {
    return status;
  }

  public String getCreatedAt() {
    return createdAt;
  }
}
