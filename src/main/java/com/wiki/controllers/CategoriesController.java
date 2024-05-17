package com.wiki.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wiki.interfaces.category.CategoryPublic;
import com.wiki.middlewares.AuthMiddleware;
import com.wiki.services.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CategoriesController extends HttpServlet {
  /**
   * Handles HTTP GET requests for displaying categories on the Categories page.
   *
   * This method performs authorization, retrieves all categories, sets them as an
   * attribute
   * in the request, and forwards the request to the Categories.jsp page for
   * rendering.
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

    // Get all categories
    List<CategoryPublic> categories = new ArrayList<>();
    try {
      categories = CategoryService.read();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    request.setAttribute("categories", categories);

    request.getRequestDispatcher("/WEB-INF/components/pages/Categories.jsp").forward(request, response);
  }
}
