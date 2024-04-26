package com.wiki.interfaces.user;

public class UserPublic {
  public String email, fullname, accessToken;
  public int role;

  public UserPublic(String email, String fullname, String accessToken, int role) {
    this.email = email;
    this.fullname = fullname;
    this.accessToken = accessToken;
    this.role = role;
  }
}
