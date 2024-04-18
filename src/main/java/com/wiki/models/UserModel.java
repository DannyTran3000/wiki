package com.wiki.models;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.crypto.SecretKey;

import com.wiki.config.Database;
import com.wiki.helpers.CryptoHelper;

public class UserModel {
  // private int id;
  // private String email;
  // private String password;
  // private String salt;
  // private String fullname;
  // private String phone;
  // private int role;
  // private String created_at;
  // private String updated_at;

  // public UserModel(
  //     int id,
  //     String email,
  //     String fullname,
  //     String phone,
  //     int role,
  //     String created_at,
  //     String updated_at) {
  //   this.id = id;
  //   this.email = email;
  //   this.fullname = fullname;
  //   this.phone = phone;
  //   this.role = role;
  //   this.created_at = created_at;
  //   this.updated_at = updated_at;
  // }

  public static void create(String email, String password, String fullname, String phone) throws SQLException {
    try {
      SecretKey newSalt = CryptoHelper.generate();

      LocalDateTime currentDateTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      String formattedDateTime = currentDateTime.format(formatter);
      String statement = "INSERT INTO user (email, password, salt, fullname, phone, created_at) VALUES(?,?,?,?,?,?)";

      int res = Database.modify(statement, email, password, newSalt.toString(), fullname, phone, formattedDateTime);

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }
}
