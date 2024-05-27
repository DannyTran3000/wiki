package com.wiki.controllers.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiki.helpers.SlugHelper;
import com.wiki.interfaces.category.CategoryPublic;
import com.wiki.middlewares.AuthMiddleware;
import com.wiki.services.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CategoryDetailController extends HttpServlet {
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int[] permissions = { 0 };
    // Authorization
    AuthMiddleware.authorize(request, response, permissions);

    // Get request url
    String url = request.getRequestURL().toString();
    // Extract category slug
    String categorySlug = SlugHelper.extractAdminCategorySlug(url);

    try {
      CategoryService.delete(categorySlug);
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
    // Extract category slug
    String categorySlug = SlugHelper.extractAdminCategorySlug(url);

    boolean isCreatePage = categorySlug.equals("create");

    if (!isCreatePage) {
      CategoryPublic single = null;
      try {
        single = CategoryService.readSingle(categorySlug);
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      // Redirect to an not found page if the category does not exist
      if (single == null) {
        // Set attributes for the error page to display appropriate message
        request.setAttribute("page_code", 404);
        request.setAttribute("page_message", "Page not found!!!");

        // Set attributes for the error page to display appropriate message
        request.getRequestDispatcher("/WEB-INF/components/pages/Error.jsp").forward(request, response);
      }

      request.setAttribute("single", single);
    }
    request.setAttribute("mode", isCreatePage ? "create" : "edit");

    request.getRequestDispatcher("/WEB-INF/components/pages/admin/CategoryDetail.jsp").forward(request, response);
  }

  protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int[] permissions = { 0 };
    // Authorization
    AuthMiddleware.authorize(request, response, permissions);

    // Get request url
    String url = request.getRequestURL().toString();
    // Extract category slug
    String categorySlug = SlugHelper.extractAdminCategorySlug(url);

    // Get JSON data from request body
    BufferedReader reader = request.getReader();
    StringBuilder requestBody = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      requestBody.append(line);
    }
    reader.close();

    String name = "", icon = "";
    try {
      // Use Jackson ObjectMapper to parse JSON data
      ObjectMapper objectMapper = new ObjectMapper();
      RequestUpdateCategoryBody body = objectMapper.readValue(requestBody.toString(), RequestUpdateCategoryBody.class);

      // Access the email property from the parsed data
      name = body.getName();
      icon = body.getIcon();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    try {
      CategoryService.update(name, icon, categorySlug);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private static class RequestUpdateCategoryBody {
    private String name, icon;

    public String getName() {
      return name;
    }

    public String getIcon() {
      return icon;
    }
  }
}
