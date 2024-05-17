package com.wiki.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wiki.helpers.CookieHelper;
import com.wiki.interfaces.user.UserResponse;
import com.wiki.services.AuthService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthController extends HttpServlet {
  /**
   * Handles HTTP POST requests for authentication-related actions based on the
   * 'action' parameter.
   *
   * @param request  The HTTP request object.
   * @param response The HTTP response object.
   * @throws IOException  If an I/O error occurs while processing the request.
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String action = request.getParameter("action");

    switch (action) {
      case "change-password":
        changePassword(request, response);
        break;

      case "forgot-password":
        forgotPassword(request, response);
        break;

      case "login":
        login(request, response);
        break;

      case "logout":
        try {
          logout(request);
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        break;

      case "register":
        register(request, response);
        break;
    }
  }

  /**
   * Changes the user's password based on the provided JSON data in the request
   * body.
   *
   * @param request  The HTTP request object containing the request body with JSON
   *                 data.
   * @param response The HTTP response object.
   * @throws IOException If an I/O error occurs while processing the request.
   */
  private static void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get JSON data from request body
    BufferedReader reader = request.getReader();
    StringBuilder requestBody = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      requestBody.append(line);
    }
    reader.close();

    String email = "", password = "", newPassword = "";
    try {
      // Use Jackson ObjectMapper to parse JSON data
      ObjectMapper objectMapper = new ObjectMapper();
      RequestChangePasswordBody body = objectMapper.readValue(requestBody.toString(), RequestChangePasswordBody.class);

      email = body.getEmail();
      password = body.getPassword();
      newPassword = body.getNewPassword();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    // login
    UserResponse meta = null;
    try {
      meta = AuthService.changePassword(email, password, newPassword);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    // Collect data and parse into json
    JsonObject data = new JsonObject();
    data.addProperty("data", meta.jsonData);
    data.addProperty("status", meta.status);
    data.addProperty("type", meta.type);
    data.addProperty("message", meta.message);
    Gson gson = new Gson();
    String returnJsonData = gson.toJson(data);

    // Send response
    try {
      response.setContentType("application/json");
      response.getWriter().write(returnJsonData);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }
  }

  /**
   * Sends a password reset link to the user's email based on the provided JSON
   * data.
   *
   * @param request  The HTTP request object containing the request body with JSON
   *                 data.
   * @param response The HTTP response object.
   * @throws IOException If an I/O error occurs while processing the request.
   */
  private static void forgotPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get JSON data from request body
    BufferedReader reader = request.getReader();
    StringBuilder requestBody = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      requestBody.append(line);
    }
    reader.close();

    String email = "";
    try {
      // Use Jackson ObjectMapper to parse JSON data
      ObjectMapper objectMapper = new ObjectMapper();
      RequestForgotPasswordBody body = objectMapper.readValue(requestBody.toString(), RequestForgotPasswordBody.class);

      // Access the email property from the parsed data
      email = body.getEmail();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    // login
    UserResponse meta = null;
    try {
      meta = AuthService.forgotPassword(email);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    // Collect data and parse into json
    JsonObject data = new JsonObject();
    data.addProperty("data", meta.jsonData);
    data.addProperty("status", meta.status);
    data.addProperty("type", meta.type);
    data.addProperty("message", meta.message);
    Gson gson = new Gson();
    String returnJsonData = gson.toJson(data);

    // Send response
    try {
      response.setContentType("application/json");
      response.getWriter().write(returnJsonData);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }
  }

  /**
   * Authenticates the user based on the provided JSON data.
   *
   * @param request  The HTTP request object containing the request body with JSON
   *                 data.
   * @param response The HTTP response object.
   * @throws IOException If an I/O error occurs while processing the request.
   */
  private static void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get JSON data from request body
    BufferedReader reader = request.getReader();
    StringBuilder requestBody = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      requestBody.append(line);
    }
    reader.close();

    String email = "", password = "";
    try {
      // Use Jackson ObjectMapper to parse JSON data
      ObjectMapper objectMapper = new ObjectMapper();
      RequestLoginBody body = objectMapper.readValue(requestBody.toString(), RequestLoginBody.class);

      // Access the email property from the parsed data
      email = body.getEmail();
      password = body.getPassword();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    // login
    UserResponse meta = null;
    try {
      meta = AuthService.login(email, password);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    // Collect data and parse into json
    JsonObject data = new JsonObject();
    data.addProperty("data", meta.jsonData);
    data.addProperty("status", meta.status);
    data.addProperty("type", meta.type);
    data.addProperty("message", meta.message);
    Gson gson = new Gson();
    String returnJsonData = gson.toJson(data);

    // Send response
    try {
      response.setContentType("application/json");
      response.getWriter().write(returnJsonData);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }
  }

  /**
   * Logs out the user based on the provided request.
   *
   * @param request The HTTP request object.
   * @throws SQLException If a SQL-related error occurs.
   */
  private static void logout(HttpServletRequest request) throws SQLException {
    String token = CookieHelper.readToken(request);
    AuthService.logout(token);
  }

  /**
   * Registers a new user based on the provided JSON data.
   *
   * @param request  The HTTP request object containing the request body with JSON
   *                 data.
   * @param response The HTTP response object.
   * @throws IOException If an I/O error occurs while processing the request.
   */
  private static void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get JSON data from request body
    BufferedReader reader = request.getReader();
    StringBuilder requestBody = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      requestBody.append(line);
    }
    reader.close();

    String email = "", fullname = "", password = "";
    try {
      // Use Jackson ObjectMapper to parse JSON data
      ObjectMapper objectMapper = new ObjectMapper();
      RequestRegisterBody body = objectMapper.readValue(requestBody.toString(), RequestRegisterBody.class);

      // Access the email property from the parsed data
      email = body.getEmail();
      fullname = body.getFullname();
      password = body.getPassword();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    // login
    UserResponse meta = null;
    try {
      meta = AuthService.register(email, fullname, password);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    // Collect data and parse into json
    JsonObject data = new JsonObject();
    data.addProperty("data", meta.jsonData);
    data.addProperty("status", meta.status);
    data.addProperty("type", meta.type);
    data.addProperty("message", meta.message);
    Gson gson = new Gson();
    String returnJsonData = gson.toJson(data);

    // Send response
    try {
      response.setContentType("application/json");
      response.getWriter().write(returnJsonData);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }
  }

  private static class RequestChangePasswordBody {
    private String email, password, newPassword;

    public String getEmail() {
      return email;
    }

    public String getPassword() {
      return password;
    }

    public String getNewPassword() {
      return newPassword;
    }
  }

  private static class RequestForgotPasswordBody {
    private String email;

    public String getEmail() {
      return email;
    }
  }

  private static class RequestLoginBody {
    private String email;
    private String password;

    public String getEmail() {
      return email;
    }

    public String getPassword() {
      return password;
    }
  }

  private static class RequestRegisterBody {
    private String fullname;
    private String email;
    private String password;

    public String getFullname() {
      return fullname;
    }

    public String getEmail() {
      return email;
    }

    public String getPassword() {
      return password;
    }
  }
}
