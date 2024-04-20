package com.wiki.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieHelper {
  public static String readToken(HttpServletRequest request) {
    // Get all cookies sent by the browser
    Cookie[] cookies = request.getCookies();

    // Check if cookies are not null and iterate through them
    String accessToken = "";
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        String cookieName = cookie.getName();
        String cookieValue = cookie.getValue();
        if (cookieName.equals("authToken")) {
          String decodedCookieValue;
          try {
            decodedCookieValue = URLDecoder.decode(cookieValue, StandardCharsets.UTF_8.toString());
            accessToken = decodedCookieValue;
          } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          }
          break;
        }
      }
    }
    return accessToken;
  }
}
