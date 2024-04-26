package com.wiki.interfaces.user;

public class UserResponse {
  public String jsonData;
  public int status;
  public String type;
  public String message;

  public UserResponse(String jsonData, int status, String type, String message) {
    this.jsonData = jsonData;
    this.status = status;
    this.type = type;
    this.message = message;
  }
}
