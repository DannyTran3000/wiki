package com.wiki.controllers;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wiki.interfaces.article.ArticlePublic;
import com.wiki.interfaces.category.CategoryPublic;
import com.wiki.middlewares.AuthMiddleware;
import com.wiki.services.ArticleService;
import com.wiki.services.CategoryService;

public class HomeController extends HttpServlet {
  /**
   * Handles HTTP GET requests for displaying content on the index page.
   *
   * This method performs authorization, retrieves the latest articles and
   * categories,
   * sets them as attributes in the request, and forwards the request to the
   * index.jsp page
   * for rendering.
   *
   * @param request  The HttpServletRequest object representing the client's
   *                 request.
   * @param response The HttpServletResponse object for sending the response back
   *                 to the client.
   * @throws ServletException If an error occurs during servlet processing.
   * @throws IOException      If an I/O error occurs during request handling.
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Authorization
    AuthMiddleware.authorize(request, response, null);

    // Get latest articles & categories
    List<ArticlePublic> highestViewsArticles = new ArrayList<>();
    List<ArticlePublic> latestArticles = new ArrayList<>();
    List<CategoryPublic> categories = new ArrayList<>();
    try {
      highestViewsArticles = ArticleService.readHighestViewsArticles();
      latestArticles = ArticleService.readLatestArticles();
      categories = CategoryService.read();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    request.setAttribute("highestViewsArticles", highestViewsArticles);
    request.setAttribute("latestArticles", latestArticles);
    request.setAttribute("categories", categories);

    request.getRequestDispatcher("/WEB-INF/components/pages/index.jsp").forward(request, response);
  }
}
