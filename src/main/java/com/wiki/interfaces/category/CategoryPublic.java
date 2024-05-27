package com.wiki.interfaces.category;

public class CategoryPublic {
  public String name, icon, slug, createdAt, formatArticleCount;
  public int id, status, articleCount;

  public CategoryPublic(int id, String name, String icon, String slug, int status, String createdAt, int articleCount) {
    this.id = id;
    this.name = name;
    this.icon = icon;
    this.slug = slug;
    this.status = status;
    this.createdAt = createdAt;
    this.articleCount = articleCount;
    this.formatArticleCount = formatCount(articleCount);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getIcon() {
    return icon;
  }

  public String getSlug() {
    return slug;
  }

  public int getStatus() {
    return status;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public int getArticleCount() {
    return articleCount;
  }

  public String getFormatArticleCount() {
    return formatArticleCount;
  }

  private String formatCount(int n) {
    if (n >= 1000000000)
      return String.format("%.0fB", n / 1000000000.0);
    else if (n >= 1000000)
      return String.format("%.0fM", n / 1000000.0);
    else if (n >= 1000)
      return String.format("%.0fK", n / 1000.0);
    else
      return Long.toString(n);
  }
}
