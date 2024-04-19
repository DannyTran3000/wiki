package com.wiki.interfaces;

public class UserData {
  public String jsonData;
  public int status;
  public String type;
  public String message;

  public UserData(String jsonData, int status, String type, String message) {
    this.jsonData = jsonData;
    this.status = status;
    this.type = type;
    this.message = message;
  }
}
