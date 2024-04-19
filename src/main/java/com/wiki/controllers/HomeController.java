package com.wiki.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.IOException;

public class HomeController extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // // Get all cookies sent by the browser
    // Cookie[] cookies = request.getCookies();

    // // Check if cookies are not null and iterate through them
    // if (cookies != null) {
    //     for (Cookie cookie : cookies) {
    //         String cookieName = cookie.getName();
    //         String cookieValue = cookie.getValue();
    //         System.out.println("=== Cookie Name: " + cookieName + ", Value: " + cookieValue);

    //         // You can further process the cookie data here as needed
    //     }
    // }

    request.getRequestDispatcher("/WEB-INF/components/templates/index.jsp").forward(request, response);
  }
}
