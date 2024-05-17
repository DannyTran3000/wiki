package com.wiki.interfaces.category;

public class CategoryPublic {
  public String name, icon, slug, formatArticleCount;
  public int articleCount;

  public CategoryPublic(String name, String icon, String slug, int articleCount) {
    this.name = name;
    this.icon = icon;
    this.slug = slug;
    this.articleCount = articleCount;
    this.formatArticleCount = formatCount(articleCount);
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
