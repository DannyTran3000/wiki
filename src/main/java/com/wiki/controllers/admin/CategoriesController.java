package com.wiki.controllers.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiki.interfaces.category.CategoryPublic;
import com.wiki.middlewares.AuthMiddleware;
import com.wiki.services.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CategoriesController extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int[] permissions = { 0 };
    // Authorization
    AuthMiddleware.authorize(request, response, permissions);

    // Get all categories
    List<CategoryPublic> categories = new ArrayList<>();
    try {
      categories = CategoryService.read();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    request.setAttribute("categories", categories);

    request.getRequestDispatcher("/WEB-INF/components/pages/admin/Categories.jsp").forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int[] permissions = { 0 };
    // Authorization
    AuthMiddleware.authorize(request, response, permissions);

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
      RequestCreateCategoryBody body = objectMapper.readValue(requestBody.toString(), RequestCreateCategoryBody.class);

      // Access the email property from the parsed data
      name = body.getName();
      icon = body.getIcon();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    try {
      CategoryService.create(name, icon);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private static class RequestCreateCategoryBody {
    private String name, icon;

    public String getName() {
      return name;
    }

    public String getIcon() {
      return icon;
    }
  }
}
