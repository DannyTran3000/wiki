package com.wiki.test;

import java.sql.SQLException;

import com.wiki.models.UserModel;

public class Tester {
  public static void main(String[] args) throws SQLException {
    UserModel.create("trandangminhnhat.dev@gmail.com", "123456", "Danny Tran", "0478075375");
  }
}
