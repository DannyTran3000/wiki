package com.wiki.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {
  public static boolean isValidEmail(String email) {
    final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

  public static boolean isValidPassword(String password) {
    final String regex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[\\w._\\-+=]{8,}";

    return Pattern.compile(regex).matcher(password).matches();
  }
}
