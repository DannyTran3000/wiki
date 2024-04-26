package com.wiki.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {
  /**
   * Checks if the provided email address is valid according to the specified
   * regex pattern.
   *
   * @param email The email address to validate.
   * @return true if the email is valid, false otherwise.
   */
  public static boolean isValidEmail(String email) {
    final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

  /**
   * Checks if the provided password meets the specified criteria based on the
   * regex pattern.
   *
   * @param password The password to validate.
   * @return true if the password is valid, false otherwise.
   */
  public static boolean isValidPassword(String password) {
    final String regex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[\\w._\\-+=]{8,}";

    return Pattern.compile(regex).matcher(password).matches();
  }
}
