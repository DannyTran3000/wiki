package com.wiki.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wiki.interfaces.article.ArticlePublic;
import com.wiki.interfaces.category.CategoryPublic;
import com.wiki.middlewares.AuthMiddleware;
import com.wiki.services.ArticleService;
import com.wiki.services.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ArticlesController extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Authorization
    AuthMiddleware.authorize(request, response, null);

    // Get articles by filter and categories
    List<ArticlePublic> articles = new ArrayList<>();
    List<CategoryPublic> categories = new ArrayList<>();
    try {
      articles = ArticleService.filter(null, null, "date", 1);
      categories = CategoryService.read();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    request.setAttribute("filterArticles", articles);
    request.setAttribute("categories", categories);

    request.getRequestDispatcher("/WEB-INF/components/pages/Articles.jsp").forward(request, response);
  }
}
