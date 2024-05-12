package com.wiki.services;

import java.sql.SQLException;
import java.util.List;

import com.wiki.helpers.DateTimeHelper;
import com.wiki.helpers.SlugHelper;
import com.wiki.interfaces.article.ArticlePublic;
import com.wiki.models.ArticleModel;
import com.wiki.models.CategoryModel;

public class ArticleService {
  /**
   * Creates a new article and inserts it into the database.
   *
   * @param title       The title of the article.
   * @param thumbnail   The URL/path to the article's thumbnail image.
   * @param description A brief description or summary of the article.
   * @param content     The main content or body of the article.
   * @param categoryId  The ID of the category to which the article belongs.
   * @throws SQLException If a database error occurs while creating the article.
   */
  public static void create(String title, String thumbnail, String description, String content, int categoryId)
      throws SQLException {
    // Get category pathname
    String categoryPathname = CategoryModel.selectCategoryPathnameById(categoryId);

    // Get current date time
    String currentDateTime = DateTimeHelper.getCurrentDateTime();

    // Generate new slug by article title
    String slug = SlugHelper.generate(title + " " + currentDateTime);
    String pathname = categoryPathname + "/" + slug;

    ArticleModel.insertArticle(title, thumbnail, description, content, pathname, categoryId);
  }

  /**
   * Reads the latest articles from the database.
   *
   * @return a list of latest articles (ArticlePublic objects)
   * @throws SQLException if a database access error occurs
   */
  public static List<ArticlePublic> readLatest() throws SQLException {
    // The 3 latest articles
    final int limit = 3;
    return ArticleModel.selectLatestArticles(limit);
  }

  public static ArticlePublic readSingle(String pathname) throws SQLException {
    return ArticleModel.selectArticleByPathname(pathname);
  }
}
