package com.wiki.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.wiki.helpers.SlugHelper;
import com.wiki.interfaces.article.ArticlePublic;
import com.wiki.services.ArticleService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SingleController extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    // Redirect to an not found page if the article does not exist
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
