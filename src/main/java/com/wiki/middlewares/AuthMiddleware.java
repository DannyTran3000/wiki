package com.wiki.middlewares;

import java.io.IOException;
import java.sql.SQLException;

import com.wiki.helpers.CookieHelper;
import com.wiki.interfaces.user.UserPublic;
import com.wiki.services.AuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthMiddleware {
  /**
   * Authorizes a user's access based on permissions.
   *
   * @param request     The HttpServletRequest object representing the HTTP
   *                    request.
   * @param response    The HttpServletResponse object representing the HTTP
   *                    response.
   * @param permissions An array of integers representing the permissions required
   *                    for access.
   *                    If null or empty, no specific permissions are required for
   *                    access.
   * @throws ServletException If there is an issue during servlet processing.
   * @throws IOException      If an I/O error occurs during servlet processing.
   */
  public static void authorize(HttpServletRequest request, HttpServletResponse response, int[] permissions)
      throws ServletException, IOException {
    // Read the access token from the request's cookies using a helper method
    String accessToken = CookieHelper.readToken(request);
    UserPublic user = null;

    // Check if the access token is not empty
    if (!accessToken.equals("")) {
      try {
        // Authenticate the user based on the access token obtained
        user = AuthService.isAuthenticated(accessToken);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    if (permissions != null && permissions.length > 0) {
      // Determine if the user should be redirected to an unauthorized page based on
      // permissions
      boolean shouldRedirectToUnauthorized = false;
      for (int i = 0; i < permissions.length; i++) {
        // Check if the user's role matches any of the required permissions
        if (user.role == permissions[i]) {
          // User has required permission
          shouldRedirectToUnauthorized = true;
          break;
        }
      }

      // Redirect to an unauthorized page if the user lacks required permissions
      if (!shouldRedirectToUnauthorized) {
        // Set attributes for the error page to display appropriate message
        request.setAttribute("page_code", 401);
        request.setAttribute("page_message", "Unauthorized: You have no permission to access this page.");

        // Set attributes for the error page to display appropriate message
        request.getRequestDispatcher("/WEB-INF/components/pages/Error.jsp").forward(request, response);
      }
    }

    // Set user attributes if the user is authenticated
    if (user != null) {
      request.setAttribute("user_fullname", user.fullname);
      request.setAttribute("user_email", user.email);
      request.setAttribute("user_access_token", user.accessToken);
      request.setAttribute("user_role", user.role);
    }
  }
}
