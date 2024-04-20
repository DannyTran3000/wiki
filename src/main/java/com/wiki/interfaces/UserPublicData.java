package com.wiki.interfaces;

public class UserPublicData {
  public int role;
  public String fullname, email, access_token;

  public UserPublicData(String fullname, String email, String access_token, int role) {
    this.fullname = fullname;
    this.email = email;
    this.access_token = access_token;
    this.role = role;
  }
}
