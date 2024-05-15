package com.wiki.services;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Random;

import javax.crypto.SecretKey;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wiki.helpers.CryptoHelper;
import com.wiki.helpers.DateTimeHelper;
import com.wiki.helpers.MailHelper;
import com.wiki.interfaces.user.UserPublic;
import com.wiki.interfaces.user.UserResponse;
import com.wiki.models.UserModel;

public class AuthService {
  /**
   * Changes the user's password in the database.
   *
   * @param email       The user's email.
   * @param password    The user's current password.
   * @param newPassword The new password to be set.
   * @return A UserResponse object indicating the result of the operation.
   * @throws Exception If an error occurs during the password change process.
   */
  public static UserResponse changePassword(String email, String password, String newPassword) throws Exception {
    UserModel user = null;

    try {
      user = UserModel.selectUserByEmail(email);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    // Checks if the given email already exists in the database
    if (user == null) {
      return getUserResponse(null, 404, "Warning", "This email does not exist.");
    }

    // Encodes user salt into a cryptographic key
    SecretKey userKey = CryptoHelper.convertStringToKey(user.salt);

    // Encrypt current password
    String encryptedPassword = CryptoHelper.encrypt(password, userKey);
    if (!user.password.equals(encryptedPassword)) {
      return getUserResponse(null, 401, "Warning", "Your current password is incorrect.");
    }

    // Encrypted new password
    String newEncryptedPassword = CryptoHelper.encrypt(newPassword, userKey);

    // update password in user table
    int res = UserModel.updatePasswordByEmail(user.email, newEncryptedPassword);
    if (res < 1) {

    }

    return getUserResponse(null, 200, "Success", "Your password has been updated.");
  }

  /**
   * Initiates the forgot password process by generating a new password and
   * sending it to the user's email.
   *
   * @param email The user's email.
   * @return A UserResponse object indicating the result of the operation.
   * @throws Exception If an error occurs during the forgot password process.
   */
  public static UserResponse forgotPassword(String email) throws Exception {
    UserModel user = null;

    try {
      user = UserModel.selectUserByEmail(email);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    // Checks if the given email already exists in the database
    if (user == null) {
      return getUserResponse(null, 404, "Warning", "This email does not exist.");
    }

    // Encodes user salt into a cryptographic key
    SecretKey userKey = CryptoHelper.convertStringToKey(user.salt);

    // Generate new password
    String newPassword = generateNewPassword();

    // Encrypts the given password
    String encryptedPassword = CryptoHelper.encrypt(newPassword, userKey);

    // Update password in user table
    UserModel.updatePasswordByEmail(user.email, encryptedPassword);

    // Send a new password to the email
    MailHelper.sendForgotPasswordEmail(user, newPassword);

    return getUserResponse(null, 200, "Success", "New Password has been sent to the email");
  }

  /**
   * Authenticates the user based on the provided email and password.
   *
   * @param email    The user's email.
   * @param password The user's password.
   * @return A UserResponse object containing authentication details.
   * @throws Exception If an error occurs during the login process.
   */
  public static UserResponse login(String email, String password) throws Exception {
    UserModel user = null;

    try {
      user = UserModel.selectUserByEmail(email);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    // Checks if the given email already exists in the database
    if (user == null) {
      return getUserResponse(null, 404, "Warning", "Your email or password is incorrect. Please try again.");
    }

    // Encodes user salt into a cryptographic key
    SecretKey userKey = CryptoHelper.convertStringToKey(user.salt);

    // Encrypts the given password
    String encryptedPassword = CryptoHelper.encrypt(password, userKey);
    // Checks if the given password match with user password
    if (!encryptedPassword.equals(user.password)) {
      return getUserResponse(null, 401, "Warning", "Your email or password is incorrect. Please try again.");
    }

    // Get current time
    String currentTime = DateTimeHelper.getCurrentDateTime();
    String combinationString = user.email + "==" + currentTime;
    String accessToken = CryptoHelper.encrypt(combinationString, userKey);

    // update access_token in user table
    UserModel.updateAccessTokenByEmail(user.email, accessToken);

    // Gather information for the return process.
    String jsonData = jsonParseUserData(user.fullname, user.email, accessToken, user.role);

    return getUserResponse(jsonData, 200, "Success", "You have successfully logged in! Welcome back!");
  }

  /**
   * Logs out a user by removing their access token from the database.
   * 
   * @param accessToken The access token of the user to be logged out.
   * @return A UserResponse indicating the result of the logout operation.
   * @throws SQLException If a database error occurs during the logout process.
   */
  public static UserResponse logout(String accessToken) throws SQLException {
    // Remove token
    int res = UserModel.updateAccessTokenToNull(accessToken);
    if (res == -1) {
      return getUserResponse(null, 404, "Error", "Remove token failed!!!");
    }
    return getUserResponse(null, 200, "Success", "Remove token successfully.");
  }

  /**
   * Registers a new user in the system.
   *
   * @param email    The user's email.
   * @param fullname The user's full name.
   * @param password The user's password.
   * @return A UserResponse object indicating the result of the registration
   *         process.
   * @throws Exception If an error occurs during the registration process.
   */
  public static UserResponse register(String email, String fullname, String password) throws Exception {
    try {
      // Check if the email is unique (not already registered) before proceeding with
      // registration
      UserModel user = UserModel.selectUserByEmail(email);
      if (user != null) {
        return getUserResponse(null, 409, "Warning", "This email has already been registered.");
      }

      // Generate a unique secret key for new user
      SecretKey secretKey = CryptoHelper.generateKey();
      String salt = CryptoHelper.convertKeyToString(secretKey);
      // Encrypt password by the above secret key
      String encryptedPassword = CryptoHelper.encrypt(password, secretKey);

      // Insert new user
      int res = UserModel.insertUser(email, fullname, encryptedPassword, salt);

      if (res == 1) {
        System.out.println("Message: Your account has been created successfully.");

        UserModel newUser = UserModel.selectUserByEmail(email);

        String currentTime = DateTimeHelper.getCurrentDateTime();
        String combinationString = newUser.email + "==" + currentTime;
        String accessToken = CryptoHelper.encrypt(combinationString, secretKey);

        // update access_token in user table
        UserModel.updateAccessTokenByEmail(newUser.email, accessToken);

        // Gather information for the return process.
        String jsonData = jsonParseUserData(newUser.fullname, newUser.email, accessToken, newUser.role);

        return getUserResponse(jsonData, 201, "Success", "You have successfully logged in! Welcome!");
      }

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      System.err.println("Error: " + e.getMessage());
    }

    return getUserResponse(null, 400, "Error", "Something went wrong while creating your account. Please try again.");
  }

  /**
   * Checks if a user is authenticated based on the provided access token.
   *
   * @param token The user's access token.
   * @return A UserPublic object containing authenticated user details, or null if
   *         not authenticated.
   * @throws SQLException If an SQL error occurs during the authentication
   *                      process.
   */
  public static UserPublic isAuthenticated(String token) throws SQLException {
    return UserModel.selectUserByAccessToken(token);
  }

  public static int updateIntoAdminRole(String email) throws SQLException {
    return UserModel.updateRoleByEmail(email, 0);
  }

  /**
   * Generates a new random password consisting of uppercase letters, lowercase
   * letters, and digits.
   *
   * @return A randomly generated password.
   */
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

  /**
   * Creates a JSON string representing user data for authentication responses.
   *
   * @param fullname    The user's full name.
   * @param email       The user's email.
   * @param accessToken The user's access token.
   * @param role        The user's role.
   * @return A JSON string representing the user data.
   */
  private static String jsonParseUserData(String fullname, String email, String accessToken, int role) {
    JsonObject data = new JsonObject();
    data.addProperty("fullname", fullname);
    data.addProperty("email", email);
    data.addProperty("accessToken", accessToken);
    data.addProperty("role", role);
    Gson gson = new Gson();
    return gson.toJson(data);
  }

  /**
   * Creates a UserResponse object with the provided data.
   * 
   * @param jsonData The JSON data to include in the response.
   * @param code     The HTTP status code to set in the response.
   * @param type     The type of response (e.g., "Success", "Error", "Warning").
   * @param message  The message describing the response or operation.
   * @return A UserResponse object representing the response data.
   */
  private static UserResponse getUserResponse(String jsonData, int code, String type, String message) {
    System.out.println(type + ": " + message);
    return new UserResponse(jsonData, code, type, message);
  }
}
