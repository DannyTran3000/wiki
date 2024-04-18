package com.wiki.interfaces.user;

public class UserInstance {
  public int id, role;
  public String fullname, email, access_token;

  public UserInstance(int id, String fullname, String email, String access_token, int role) {
    this.id = id;
    this.fullname = fullname;
    this.email = email;
    this.access_token = access_token;
    this.role = role;
  }
}
