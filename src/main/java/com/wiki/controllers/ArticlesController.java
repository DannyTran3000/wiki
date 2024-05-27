package com.wiki.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    // Authorization
    AuthMiddleware.authorize(request, response, null);

    // get param from URL
    String keyword = request.getParameter("keyword");
    String categorySlug = request.getParameter("category");
    String orderBy = request.getParameter("sort");
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
    FilterSystemPublic fs = new FilterSystemPublic(keyword, categorySlug, categoryName, orderBy, orderNote, null);

    // Get articles by filter and categories
    List<CategoryPublic> categories = new ArrayList<>();
    List<ArticlePublic> articles = new ArrayList<>();
    PaginationPublic pagination = null;
    try {
      categories = CategoryService.read();
      articles = ArticleService.filter(keyword, categorySlug, 1, orderBy, parsePage);
      pagination = ArticleService.filterPagination(keyword, categorySlug, 1, orderBy, parsePage);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    request.setAttribute("filter", fs);
    request.setAttribute("categories", categories);
    request.setAttribute("filterArticles", articles);
    request.setAttribute("pagination", pagination);

    request.getRequestDispatcher("/WEB-INF/components/pages/Articles.jsp").forward(request, response);
  }
}
