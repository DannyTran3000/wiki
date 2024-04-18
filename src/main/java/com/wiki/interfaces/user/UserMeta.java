package com.wiki.interfaces.user;

public class UserMeta {
  public UserInstance data;
  public int code;
  public String status;
  public String message;

  public UserMeta(UserInstance data, int code, String status, String message) {
    this.data = data;
    this.code = code;
    this.status = status;
    this.message = message;
  }
}
