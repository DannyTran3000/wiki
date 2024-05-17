package com.wiki.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.wiki.config.Database;
import com.wiki.helpers.PaginationHelper;
import com.wiki.interfaces.article.ArticlePublic;
import com.wiki.interfaces.pagination.PaginationPublic;

public class ArticleModel {
  public int id, status, categoryId;
  public String title, thumbnail, description, content, slug;
  public Timestamp createdAt;

  public ArticleModel(int id, String title, String thumbnail, String description, String content, String slug,
      int status, int categoryId, Timestamp createdAt) {
    this.id = id;
    this.title = title;
    this.thumbnail = thumbnail;
    this.description = description;
    this.content = content;
    this.slug = slug;
    this.status = status;
    this.categoryId = categoryId;
    this.createdAt = createdAt;
  }

  public static int insertArticle(String title, String thumbnail, String description, String content, String slug,
      int categoryId) throws SQLException {
    String statement = "INSERT INTO article (title, thumbnail, description, content, slug, category_id) VALUES(?,?,?,?,?,?)";
    return Database.update(statement, title, thumbnail, description, content, slug, categoryId);
  }

  public static ArticlePublic selectArticleBySlug(String slug) throws SQLException {
    String select = Database.prepareStructureSQL(
        "SELECT ?,?,?,?,?,?,?,?",
        "A.title",
        "A.thumbnail",
        "A.content",
        "A.views",
        "A.slug",
        "C.name AS category_name",
        "C.slug AS category_slug",
        "DATE_FORMAT(A.created_at, '%d-%m-%Y') AS created_at");
    String from = "FROM article AS A";
    String join = "JOIN category AS C ON A.category_id = C.id";
    String where = "WHERE A.slug = ? AND A.status = 1";

    String statement = select + " " + from + " " + join + " " + where;
    ResultSet resultSet = Database.query(statement, slug);

    ArticlePublic article = null;
    while (resultSet.next()) {
      article = new ArticlePublic(
          resultSet.getString("title"),
          resultSet.getString("thumbnail"),
          "",
          resultSet.getString("content"),
          resultSet.getInt("views"),
          resultSet.getString("slug"),
          resultSet.getString("category_name"),
          resultSet.getString("category_slug"),
          resultSet.getString("created_at"));
    }

    return article;
  }

  public static List<ArticlePublic> selectArticlesByFilter(String keyword, String categorySlug, String orderBy,
      String orderType, int offset, int limit) throws SQLException {
    String select = Database.prepareStructureSQL(
        "SELECT ?,?,?,?,?,?,?,?",
        "A.title",
        "A.thumbnail",
        "A.description",
        "A.views",
        "A.slug",
        "C.name AS category_name",
        "C.slug AS category_slug",
        "DATE_FORMAT(A.created_at, '%d-%m-%Y') AS created_at");
    String from = "FROM article AS A";
    String join = "JOIN category AS C ON A.category_id = C.id";
    String where = "WHERE A.status = 1 AND A.title LIKE ? AND C.slug LIKE ?";
    String order = Database.prepareStructureSQL("ORDER BY ? ?", orderBy, orderType);
    String rest = "LIMIT ? OFFSET ?";

    String statement = select + " " + from + " " + join + " " + where + " " + order + " " + rest;
    ResultSet resultSet = Database.query(
        statement,
        keyword != null ? "%" + keyword + "%" : "%",
        categorySlug != null ? categorySlug : "%",
        limit,
        offset);

    List<ArticlePublic> articleList = new ArrayList<>();
    while (resultSet.next()) {
      ArticlePublic article = new ArticlePublic(
          resultSet.getString("title"),
          resultSet.getString("thumbnail"),
          resultSet.getString("description"),
          "",
          resultSet.getInt("views"),
          resultSet.getString("slug"),
          resultSet.getString("category_name"),
          resultSet.getString("category_slug"),
          resultSet.getString("created_at"));

      articleList.add(article);
    }

    return articleList;
  }

  public static PaginationPublic selectCountArticlesByFilter(String keyword, String categorySlug, String orderBy,
      String orderType, int offset, int limit) throws SQLException {
    String select = "SELECT COUNT(*) AS total_articles";
    String from = "FROM article AS A";
    String join = "JOIN category AS C ON A.category_id = C.id";
    String where = "WHERE A.status = 1 AND A.title LIKE ? AND C.slug LIKE ?";
    String order = Database.prepareStructureSQL("ORDER BY ? ?", orderBy, orderType);

    String statement = select + " " + from + " " + join + " " + where + " " + order;
    ResultSet resultSet = Database.query(
        statement,
        keyword != null ? "%" + keyword + "%" : "%",
        categorySlug != null ? categorySlug : "%");

    int totalPages = 0;
    while (resultSet.next()) {
      int count = resultSet.getInt("total_articles");
      if (count > 0) {
        totalPages = count / limit;
      }
    }

    int perPage = limit;
    int currentPage = (offset / limit) + 1;
    System.out.println("================" + totalPages);
    List<String> display = PaginationHelper.getDisplay(totalPages, currentPage);

    return new PaginationPublic(String.valueOf(currentPage), String.valueOf(totalPages), String.valueOf(perPage),
        display);
  }

  public static List<ArticlePublic> selectLatestArticles(int limit) throws SQLException {
    String select = Database.prepareStructureSQL(
        "SELECT ?,?,?,?,?,?,?,?",
        "A.title",
        "A.thumbnail",
        "A.description",
        "A.views",
        "A.slug",
        "C.name AS category_name",
        "C.slug AS category_slug",
        "DATE_FORMAT(A.created_at, '%d-%m-%Y') AS created_at");
    String from = "FROM article AS A";
    String join = "JOIN category AS C ON A.category_id = C.id";
    String where = "WHERE A.status = 1";
    String rest = "ORDER BY A.created_at DESC LIMIT ?";

    String statement = select + " " + from + " " + join + " " + where + " " + rest;
    ResultSet resultSet = Database.query(statement, limit);

    List<ArticlePublic> articleList = new ArrayList<>();
    while (resultSet.next()) {
      ArticlePublic article = new ArticlePublic(
          resultSet.getString("title"),
          resultSet.getString("thumbnail"),
          resultSet.getString("description"),
          "",
          resultSet.getInt("views"),
          resultSet.getString("slug"),
          resultSet.getString("category_name"),
          resultSet.getString("category_slug"),
          resultSet.getString("created_at"));

      articleList.add(article);
    }

    return articleList;
  }

  public static void updateArticleViews(String slug) throws SQLException {
    String statement = "UPDATE article SET views = views + 1 WHERE slug = ?";
    Database.update(statement, slug);
  }
}
