package com.wiki.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wiki.interfaces.user.UserResponse;
import com.wiki.services.AuthService;

public class ChangePasswordController extends HttpServlet {
  /**
   * Handles HTTP POST requests from clients to change user passwords.
   *
   * @param request  The HttpServletRequest object containing the request
   *                 parameters.
   * @param response The HttpServletResponse object for sending the response.
   * @throws ServletException If an error occurs during servlet processing.
   * @throws IOException      If an I/O error occurs during request handling.
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
      RequestBody body = objectMapper.readValue(requestBody.toString(), RequestBody.class);

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
   * Represents the structure of the request body JSON data for password change
   * requests.
   * This class is used for deserialization using Jackson ObjectMapper.
   */
  private static class RequestBody {
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
}
