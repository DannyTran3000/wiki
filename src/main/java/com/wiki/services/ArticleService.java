package com.wiki.services;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.wiki.config.Database;
import com.wiki.helpers.DateTimeHelper;
import com.wiki.helpers.SlugHelper;
import com.wiki.interfaces.article.ArticlePublic;
import com.wiki.interfaces.pagination.PaginationPublic;
import com.wiki.models.ArticleModel;

public class ArticleService {
  private static final int articlesPerPage = 20;

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
    // Get current date time
    String currentDateTime = DateTimeHelper.getCurrentDateTime();

    // Generate new slug by article title
    String slug = SlugHelper.generate(title + " " + currentDateTime);
    ArticleModel.insertArticle(title, thumbnail, description, content, slug, categoryId);
  }

  public static List<ArticlePublic> filter(String keyword, String categorySlug, int status, String orderBy, int page)
      throws SQLException {
    // validate sort type
    String[] sortTypes = { "date", "views", "title" };
    boolean isValidSortType = Arrays.asList(sortTypes).contains(orderBy);
    String currentSortType = isValidSortType ? orderBy : "date";
    String sortColumn = "";
    String sortType = "DESC";
    switch (currentSortType) {
      case "title":
        sortColumn = "A.title";
        sortType = "ASC";
        break;

      case "views":
        sortColumn = "A.views";
        break;

      default:
        sortColumn = "A.created_at";
    }

    // calculate pagination
    int currentPage = page > 0 ? page : 1;
    int offset = (currentPage - 1) * articlesPerPage;

    return ArticleModel.selectArticlesByFilter(keyword, categorySlug, sortColumn, sortType, status, offset,
        articlesPerPage);
  }

  public static PaginationPublic filterPagination(String keyword, String categorySlug, int status, String orderBy,
      int page)
      throws SQLException {
    // validate sort type
    String[] sortTypes = { "date", "views", "title" };
    boolean isValidSortType = Arrays.asList(sortTypes).contains(orderBy);
    String currentSortType = isValidSortType ? orderBy : "date";
    String sortColumn = "";
    String sortType = "DESC";
    switch (currentSortType) {
      case "title":
        sortColumn = "A.title";
        sortType = "ASC";
        break;

      case "views":
        sortColumn = "A.views";
        break;

      default:
        sortColumn = "A.created_at";
    }

    // calculate pagination
    int currentPage = page > 0 ? page : 1;
    int offset = (currentPage - 1) * articlesPerPage;

    return ArticleModel.selectCountArticlesByFilter(keyword, categorySlug, sortColumn, sortType, status, offset,
        articlesPerPage);
  }

  public static List<ArticlePublic> readHighestViewsArticles() throws SQLException {
    final int limit = 1;
    return ArticleModel.selectHighestViewsArticles(limit);
  }

  /**
   * Reads the latest articles from the database.
   *
   * @return a list of latest articles (ArticlePublic objects)
   * @throws SQLException if a database access error occurs
   */
  public static List<ArticlePublic> readLatestArticles() throws SQLException {
    // The 3 latest articles
    final int limit = 4;
    return ArticleModel.selectLatestArticles(limit);
  }

  /**
   * Reads a single article from the database based on the provided slug.
   *
   * This method performs the following actions:
   * - Increases the view count of the article identified by the given slug.
   * - Retrieves and returns the article from the database.
   *
   * @param slug the slug of the article to be read
   * @return the ArticlePublic object representing the article
   * @throws SQLException if a database access error occurs or the slug is
   *                      invalid
   */
  public static ArticlePublic readSingle(String slug, boolean shouldCheckStatus) throws SQLException {
    // Increase views
    if (shouldCheckStatus)
      ArticleModel.updateArticleViews(slug);
    // Get article
    return ArticleModel.selectArticleBySlug(slug, shouldCheckStatus);
  }

  public static void update(String title, String thumbnail, String description, String content,
      int category, int status, String slug) throws SQLException {
    ArticleModel.updateArticleBySlug(title, thumbnail, description, content, category, status, slug);
  }

  public static void delete(String slug) throws SQLException {
    String statement = "DELETE FROM article WHERE slug = ?";
    Database.update(statement, slug);
  }
}
