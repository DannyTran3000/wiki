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
import com.wiki.middlewares.AuthMiddleware;
import com.wiki.services.ArticleService;

public class HomeController extends HttpServlet {
  /**
   * Handles HTTP GET requests for displaying user information on the index page.
   *
   * @param request  The HttpServletRequest object containing the request
   *                 parameters.
   * @param response The HttpServletResponse object for sending the response.
   * @throws ServletException If an error occurs during servlet processing.
   * @throws IOException      If an I/O error occurs during request handling.
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Authorization
    AuthMiddleware.authorize(request, response, null);

    // Get latest articles
    List<ArticlePublic> articles = new ArrayList<>();
    try {
      articles = ArticleService.readLatest();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    request.setAttribute("latestArticles", articles);

    request.getRequestDispatcher("/WEB-INF/components/pages/index.jsp").forward(request, response);
  }
}
