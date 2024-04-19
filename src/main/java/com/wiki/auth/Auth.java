package com.wiki.auth;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.SecretKey;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wiki.helpers.CryptoHelper;
import com.wiki.helpers.DateTimeHelper;
import com.wiki.helpers.ValidationHelper;
import com.wiki.interfaces.UserData;
import com.wiki.models.UserModel;

public class Auth {
  public static UserData login(String email, String password) throws Exception {
    UserModel user = null;

    try {
      user = UserModel.getUserByEmail(email);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    // Checks if the given email already exists in the database
    if (user == null) {
      String msg = "This email does not exist.";
      System.out.println("Warning: " + msg);
      return new UserData(null, 404, "Warning", msg);
    }

    // Encodes user salt into a cryptographic key
    SecretKey userKey = CryptoHelper.convertStringToKey(user.salt);

    // Encrypts the given password
    String encryptedPassword = CryptoHelper.encrypt(password, userKey);
    // Checks if the given password match with user password
    if (!encryptedPassword.equals(user.password)) {
      String msg = "The password you entered is incorrect. Please try again.";
      System.out.println("Warning: " + msg);
      return new UserData(null, 401, "Warning", msg);
    }

    // Get current time
    String currentTime = DateTimeHelper.getCurrentDateTime();
    String combinationString = user.email + "==" + encryptedPassword + "==" + currentTime;
    String accessToken = CryptoHelper.encrypt(combinationString, userKey);

    // update access_token in user table
    UserModel.setAccessTokenById(user.id, accessToken);

    // Gather information for the return process.
    String jsonData = jsonParseUserData(user.fullname, user.email, accessToken, user.role);

    return new UserData(jsonData, 200, "Success", "You have successfully logged in! Welcome back!");
  }

  public static int register(String fullname, String email, String password) throws Exception {
    try {
      // Validate user input data before proceeding with registration
      boolean isValidInput = validateDataRegister(email, password);
      if (!isValidInput) {
        System.out.println("Warning: Input data is invalid.");
        return 0;
      }

      // Check if the email is unique (not already registered) before proceeding with
      // registration
      UserModel user = UserModel.getUserByEmail(email);
      if (user != null) {
        System.out.println("Warning: This email has already been registered.");
        return 0;
      }

      // Generate a unique secret key for new user
      SecretKey secretKey = CryptoHelper.generateKey();
      String salt = CryptoHelper.convertKeyToString(secretKey);
      // Encrypt password by the above secret key
      String encryptedPassword = CryptoHelper.encrypt(password, secretKey);
      // Get current time
      String currentTime = DateTimeHelper.getCurrentDateTime();

      // Insert new user
      int res = UserModel.insertUser(fullname, email, encryptedPassword, salt, currentTime);

      System.out.println(res == 1 ? "Message: Your account has been created successfully."
          : "Error: Something went wrong while creating your account. Please try again.");
      return res;

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    return 0;
  }

  public static String isAuthenticated(String token) throws SQLException {
    ResultSet resultSet = UserModel.getUserByAccessToken(token);

    String jsonData = null;
    while (resultSet.next()) {
      jsonData = jsonParseUserData(
          resultSet.getString("fullname"),
          resultSet.getString("email"),
          resultSet.getString("access_token"),
          resultSet.getInt("role"));
    }

    return jsonData;
  }

  private static boolean validateDataRegister(String email, String password) {
    // Validate email and password format
    if (!ValidationHelper.isValidEmail(email) || !ValidationHelper.isValidPassword(password))
      return false;

    return true;
  }

  private static String jsonParseUserData(String fullname, String email, String accessToken, int role) {
    JsonObject data = new JsonObject();
    data.addProperty("fullname", fullname);
    data.addProperty("email", email);
    data.addProperty("accessToken", accessToken);
    data.addProperty("role", role);
    Gson gson = new Gson();
    return gson.toJson(data);
  }
}
