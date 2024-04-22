package com.wiki.auth;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.crypto.SecretKey;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wiki.helpers.CryptoHelper;
import com.wiki.helpers.DateTimeHelper;
import com.wiki.helpers.MailHelper;
import com.wiki.interfaces.UserData;
import com.wiki.interfaces.UserPublicData;
import com.wiki.models.UserModel;

public class Auth {
  public static UserData forgotPassword(String email) throws Exception {
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

    // Generate new password
    String newPassword = generateNewPassword();

    // Encrypts the given password
    String encryptedPassword = CryptoHelper.encrypt(newPassword, userKey);

    // update password in user table
    UserModel.setPasswordById(user.id, encryptedPassword);

    MailHelper.sendForgotPasswordEmail(user, newPassword);

    String msg = "New Password has been sent to the email";
    System.out.println("Message: " + msg);
    return new UserData(null, 200, "Success", msg);
  }

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
    String combinationString = user.id + "==" + currentTime;
    String accessToken = CryptoHelper.encrypt(combinationString, userKey);

    // update access_token in user table
    UserModel.setAccessTokenById(user.id, accessToken);

    // Gather information for the return process.
    String jsonData = jsonParseUserData(user.fullname, user.email, accessToken, user.role);

    return new UserData(jsonData, 200, "Success", "You have successfully logged in! Welcome back!");
  }

  public static UserData register(String fullname, String email, String password) throws Exception {
    try {
      // Check if the email is unique (not already registered) before proceeding with
      // registration
      UserModel user = UserModel.getUserByEmail(email);
      if (user != null) {
        String msg = "This email has already been registered.";
        System.out.println("Warning: " + msg);
        return new UserData(null, 409, "Warning", msg);
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

      if (res == 1) {
        System.out.println("Message: Your account has been created successfully.");

        UserModel newUser = UserModel.getUserByEmail(email);

        // Get current time
        String combinationString = newUser.id + "==" + currentTime;
        String accessToken = CryptoHelper.encrypt(combinationString, secretKey);

        // update access_token in user table
        UserModel.setAccessTokenById(newUser.id, accessToken);

        // Gather information for the return process.
        String jsonData = jsonParseUserData(newUser.fullname, newUser.email, accessToken, newUser.role);

        return new UserData(jsonData, 201, "Success", "You have successfully logged in! Welcome!");
      }

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    String msg = "Something went wrong while creating your account. Please try again.";
    System.out.println("Error: " + msg);
    return new UserData(null, 400, "Error", msg);
  }

  private static String generateNewPassword() {
    final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    final int length = 8;
    Random random = new Random();
    StringBuilder sb = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      int index = random.nextInt(CHARACTERS.length());
      char randomChar = CHARACTERS.charAt(index);
      sb.append(randomChar);
    }

    return sb.toString();
  }

  public static UserPublicData isAuthenticated(String token) throws SQLException {
    ResultSet resultSet = UserModel.getUserByAccessToken(token);

    UserPublicData user = null;
    while (resultSet.next()) {
      user = new UserPublicData(
          resultSet.getString("fullname"),
          resultSet.getString("email"),
          resultSet.getString("access_token"),
          resultSet.getInt("role"));
    }

    return user;
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
