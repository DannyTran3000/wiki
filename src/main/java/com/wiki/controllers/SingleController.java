package com.wiki.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.wiki.helpers.SlugHelper;
import com.wiki.interfaces.article.ArticlePublic;
import com.wiki.middlewares.AuthMiddleware;
import com.wiki.services.ArticleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SingleController extends HttpServlet {
  /**
   * Handles HTTP GET requests for displaying a single article on the Single page.
   *
   * This method performs authorization, extracts the article slug from the
   * request URL,
   * retrieves the corresponding article from the database, and forwards the
   * request to either
   * the Single.jsp page for rendering the article or the Error.jsp page with a
   * 404 status if
   * the article is not found.
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

    // Get request url
    String url = request.getRequestURL().toString();
    // Extract article slug
    String articleSlug = SlugHelper.extractArticleSlug(url);

    ArticlePublic single = null;
    try {
      single = ArticleService.readSingle(articleSlug, true);
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

    request.getRequestDispatcher("/WEB-INF/components/pages/Single.jsp").forward(request, response);
  }
}
