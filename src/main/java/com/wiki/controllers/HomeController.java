package com.wiki.controllers;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.sql.SQLException;

import com.wiki.auth.Auth;
import com.wiki.helpers.CookieHelper;
import com.wiki.interfaces.UserPublicData;

public class HomeController extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String accessToken = CookieHelper.readToken(request);
    UserPublicData user = null;
    if (!accessToken.equals("")) {
      try {
        user = Auth.isAuthenticated(accessToken);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    if (user != null) {
      request.setAttribute("user_fullname", user.fullname);
      request.setAttribute("user_email", user.email);
      request.setAttribute("user_access_token", user.access_token);
      request.setAttribute("user_role", user.role);
    }

    request.getRequestDispatcher("/WEB-INF/components/pages/index.jsp").forward(request, response);
  }
}
