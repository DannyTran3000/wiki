package com.wiki.controllers.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiki.interfaces.article.ArticlePublic;
import com.wiki.interfaces.category.CategoryPublic;
import com.wiki.interfaces.filterSystem.FilterSystemPublic;
import com.wiki.interfaces.pagination.PaginationPublic;
import com.wiki.middlewares.AuthMiddleware;
import com.wiki.services.ArticleService;
import com.wiki.services.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ArticlesController extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int[] permissions = { 0 };
    // Authorization
    AuthMiddleware.authorize(request, response, permissions);

    // get param from URL
    String keyword = request.getParameter("keyword");
    String categorySlug = request.getParameter("category");
    String orderBy = request.getParameter("sort");
    String showHidden = request.getParameter("show-hidden");
    int status = showHidden == null || !showHidden.equals("true") ? 1 : 0;
    String page = request.getParameter("page");

    int parsePage = 1;
    if (page != null)
      parsePage = Integer.parseInt(page);

    String categoryName = "";
    if (categorySlug != null) {
      try {
        categoryName = CategoryService.readName(categorySlug);
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    String orderNote = "Sort by date";
    if (orderBy != null) {
      switch (orderBy) {
        case "title":
          orderNote = "Sort by name";
          break;

        case "views":
          orderNote = "Sort by views";
          break;

        default:
          break;
      }
    }
    FilterSystemPublic fs = new FilterSystemPublic(keyword, categorySlug, categoryName, orderBy, orderNote, showHidden);

    // Get articles by filter and categories
    List<CategoryPublic> categories = new ArrayList<>();
    List<ArticlePublic> articles = new ArrayList<>();
    PaginationPublic pagination = null;
    try {
      categories = CategoryService.read();
      articles = ArticleService.filter(keyword, categorySlug, status, orderBy, parsePage);
      pagination = ArticleService.filterPagination(keyword, categorySlug, status, orderBy,
          parsePage);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    request.setAttribute("filter", fs);
    request.setAttribute("categories", categories);
    request.setAttribute("filterArticles", articles);
    request.setAttribute("pagination", pagination);

    request.getRequestDispatcher("/WEB-INF/components/pages/admin/Articles.jsp").forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int[] permissions = { 0 };
    // Authorization
    AuthMiddleware.authorize(request, response, permissions);

    // Get JSON data from request body
    BufferedReader reader = request.getReader();
    StringBuilder requestBody = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      requestBody.append(line);
    }
    reader.close();

    String title = "", thumbnail = "", description = "", content = "";
    int category = 0;
    try {
      // Use Jackson ObjectMapper to parse JSON data
      ObjectMapper objectMapper = new ObjectMapper();
      RequestCreateArticleBody body = objectMapper.readValue(requestBody.toString(), RequestCreateArticleBody.class);

      // Access the email property from the parsed data
      title = body.getTitle();
      thumbnail = body.getThumbnail();
      description = body.getDescription();
      content = body.getContent();
      category = body.getCategory();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    try {
      ArticleService.create(title, thumbnail, description, content, category);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private static class RequestCreateArticleBody {
    private String title, thumbnail, description, content;
    private int category;

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

    public int getCategory() {
      return category;
    }
  }
}
