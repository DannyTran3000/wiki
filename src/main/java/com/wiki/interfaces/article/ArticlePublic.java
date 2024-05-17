package com.wiki.interfaces.article;

public class ArticlePublic {
  public String title, thumbnail, description, content, slug, categoryName, categorySlug, createdAt;
  public int views;

  public ArticlePublic(String title, String thumbnail, String description, String content, int views, String slug,
      String categoryName, String categorySlug, String timestamp) {
    this.title = title;
    this.thumbnail = thumbnail;
    this.description = description;
    this.content = content;
    this.views = views;
    this.slug = slug;
    this.categoryName = categoryName;
    this.categorySlug = categorySlug;
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

  public int getViews() {
    return views;
  }

  public String getSlug() {
    return slug;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public String getCategorySlug() {
    return categorySlug;
  }

  public String getCreatedAt() {
    return createdAt;
  }
}
