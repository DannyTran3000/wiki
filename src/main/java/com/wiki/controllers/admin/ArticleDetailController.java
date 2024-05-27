package com.wiki.controllers.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiki.helpers.SlugHelper;
import com.wiki.interfaces.article.ArticlePublic;
import com.wiki.interfaces.category.CategoryPublic;
import com.wiki.middlewares.AuthMiddleware;
import com.wiki.services.ArticleService;
import com.wiki.services.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ArticleDetailController extends HttpServlet {
  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    int[] permissions = { 0 };
    // Authorization
    AuthMiddleware.authorize(request, response, permissions);

    // Get request url
    String url = request.getRequestURL().toString();
    // Extract category slug
    String articleSlug = SlugHelper.extractAdminArticleSlug(url);

    try {
      ArticleService.delete(articleSlug);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int[] permissions = { 0 };
    // Authorization
    AuthMiddleware.authorize(request, response, permissions);

    // Get request url
    String url = request.getRequestURL().toString();
    // Extract article slug
    String articleSlug = SlugHelper.extractAdminArticleSlug(url);

    boolean isCreatePage = articleSlug.equals("create");

    List<CategoryPublic> categories = new ArrayList<>();
    try {
      categories = CategoryService.read();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if (!isCreatePage) {
      ArticlePublic single = null;
      try {
        single = ArticleService.readSingle(articleSlug, false);
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      // Redirect to an not found page if the article does nWot exist
      if (single == null) {
        // Set attributes for the error page to display appropriate message
        request.setAttribute("page_code", 404);
        request.setAttribute("page_message", "Page not found!!!");

        // Set attributes for the error page to display appropriate message
        request.getRequestDispatcher("/WEB-INF/components/pages/Error.jsp").forward(request, response);
      }

      request.setAttribute("single", single);
    }
    request.setAttribute("categories", categories);

    request.setAttribute("mode", isCreatePage ? "create" : "edit");

    request.getRequestDispatcher("/WEB-INF/components/pages/admin/ArticleDetail.jsp").forward(request, response);
  }

  protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int[] permissions = { 0 };
    // Authorization
    AuthMiddleware.authorize(request, response, permissions);

    // Get request url
    String url = request.getRequestURL().toString();
    // Extract category slug
    String articleSlug = SlugHelper.extractAdminArticleSlug(url);

    // Get JSON data from request body
    BufferedReader reader = request.getReader();
    StringBuilder requestBody = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      requestBody.append(line);
    }
    reader.close();

    String title = "", thumbnail = "", description = "", content = "";
    int category = 0, showHidden = 0;
    try {
      // Use Jackson ObjectMapper to parse JSON data
      ObjectMapper objectMapper = new ObjectMapper();
      RequestUpdateArticleBody body = objectMapper.readValue(requestBody.toString(), RequestUpdateArticleBody.class);

      // Access the email property from the parsed data
      title = body.getTitle();
      thumbnail = body.getThumbnail();
      description = body.getDescription();
      content = body.getContent();
      category = body.getCategory();
      showHidden = body.getShowHidden();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    try {
      ArticleService.update(title, thumbnail, description, content, category, showHidden, articleSlug);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private static class RequestUpdateArticleBody {
    private String title, thumbnail, description, content;
    private int category, showHidden;

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

    public int getShowHidden() {
      return showHidden;
    }
  }
}
