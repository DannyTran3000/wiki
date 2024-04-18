package com.wiki.auth;

import java.security.NoSuchAlgorithmException;

import java.sql.SQLException;

import javax.crypto.SecretKey;

import com.wiki.helpers.CryptoHelper;
import com.wiki.helpers.DateTimeHelper;
import com.wiki.helpers.ValidationHelper;
import com.wiki.interfaces.user.UserInstance;
import com.wiki.interfaces.user.UserMeta;
import com.wiki.models.UserModel;

public class Auth {
  public static UserMeta login(String email, String password) throws Exception {
    // Query the database to retrieve user information based on email
    UserModel user = UserModel.getUserByEmail(email);
    // Checks if the given email already exists in the database
    if (user == null) {
      String msg = "This email does not exist.";
      System.out.println("Warning: " + msg);
      return new UserMeta(null, 404, "Warning", msg);
    }

    // Encodes user salt into a cryptographic key
    SecretKey userKey = CryptoHelper.convertStringToKey(user.salt);

    // Encrypts the given password
    String encryptedPassword = CryptoHelper.encrypt(password, userKey);
    // Checks if the given password match with user password
    if (!encryptedPassword.equals(user.password)) {
      String msg = "The password you entered is incorrect. Please try again.";
      System.out.println("Warning: " + msg);
      return new UserMeta(null, 401, "Warning", msg);
    }

    // Get current time
    String currentTime = DateTimeHelper.getCurrentDateTime();
    String combinationString = user.email + "==" + encryptedPassword + "==" + currentTime;
    String accessToken = CryptoHelper.encrypt(combinationString, userKey);

    // update access_token in user table
    UserModel.setAccessTokenById(user.id, accessToken);

    // Gather information for the return process.
    UserInstance data = new UserInstance(user.id, user.fullname, user.email, accessToken, user.role);

    return new UserMeta(data, 200, "Message", "You have successfully logged in! Welcome back!");
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
    }

    return 0;
  }

  public static UserInstance isAuthenticated(String token) throws SQLException {
    return UserModel.getUserByAccessToken(token);
  }

  private static boolean validateDataRegister(String email, String password) {
    // Validate email and password format
    if (!ValidationHelper.isValidEmail(email) || !ValidationHelper.isValidPassword(password))
      return false;

    return true;
  }
}
