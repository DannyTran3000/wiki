package com.wiki.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {
  /**
   * Formats the given LocalDateTime object into a string representation using the
   * pattern "yyyy-MM-dd HH:mm:ss".
   *
   * @param t The LocalDateTime object to be formatted.
   * @return The formatted string representing the date and time.
   */
  public static String getDateTime(LocalDateTime t) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return t.format(formatter);
  }

  /**
   * Retrieves the current date and time and formats it into a string using the
   * pattern "yyyy-MM-dd HH:mm:ss".
   *
   * @return The formatted string representing the current date and time.
   */
  public static String getCurrentDateTime() {
    LocalDateTime currentDateTime = LocalDateTime.now();
    return getDateTime(currentDateTime);
  }
}
