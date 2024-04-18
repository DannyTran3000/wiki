package com.wiki.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {
  public static String getDateTime(LocalDateTime t) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return t.format(formatter);
  }

  public static String getCurrentDateTime() {
    LocalDateTime currentDateTime = LocalDateTime.now();
    return getDateTime(currentDateTime);
  }
}
