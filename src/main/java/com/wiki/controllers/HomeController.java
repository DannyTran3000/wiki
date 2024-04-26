package com.wiki.controllers;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.IOException;

import com.wiki.middlewares.AuthMiddleware;

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

    request.getRequestDispatcher("/WEB-INF/components/pages/index.jsp").forward(request, response);
  }
}
