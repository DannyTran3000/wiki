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
   * Handles GET requests for fetching and displaying a single article.
   *
   * This method performs the following actions:
   * - Authorizes the request using the AuthMiddleware.
   * - Extracts the article pathname from the request URL.
   * - Attempts to read the article from the database using the ArticleService.
   * - If the article does not exist, redirects to a 404 error page.
   * - If the article exists, forwards the request to the Single.jsp page for
   * display.
   *
   * @param request  the HttpServletRequest object that contains the request the
   *                 client made to the servlet
   * @param response the HttpServletResponse object that contains the response the
   *                 servlet returns to the client
   * @throws ServletException if the request for the GET could not be handled
   * @throws IOException      if an input or output error is detected when the
   *                          servlet handles the GET request
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Authorization
    AuthMiddleware.authorize(request, response, null);

    // Get request url
    String url = request.getRequestURL().toString();
    // Extract article pathname
    String articlePathname = SlugHelper.extractArticlePathname(url);

    ArticlePublic article = null;
    try {
      article = ArticleService.readSingle(articlePathname);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // Redirect to an not found page if the article does nWot exist
    if (article == null) {
      // Set attributes for the error page to display appropriate message
      request.setAttribute("page_code", 404);
      request.setAttribute("page_message", "Page not found!!!");

      // Set attributes for the error page to display appropriate message
      request.getRequestDispatcher("/WEB-INF/components/pages/Error.jsp").forward(request, response);
    }

    request.setAttribute("single", article);

    request.getRequestDispatcher("/WEB-INF/components/pages/Single.jsp").forward(request, response);
  }
}
