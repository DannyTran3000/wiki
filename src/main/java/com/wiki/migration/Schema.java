package com.wiki.migration;

import java.sql.SQLException;

import com.wiki.config.Database;

public class Schema {
  public static void main(String[] args) throws SQLException {
    createUserTable();
    createCategoryTable();
    createArticleTable();
  }

  /**
   * Creates the 'user' table in the database if it does not exist already.
   *
   * @throws SQLException If an SQL error occurs during table creation.
   */
  private static void createUserTable() throws SQLException {
    String statement = "CREATE TABLE IF NOT EXISTS user (?,?,?,?,?,?,?,?)";
    String prepareStatement = Database.prepareStructureSQL(
        statement,
        "email VARCHAR(255) PRIMARY KEY",
        "fullname VARCHAR(255) NOT NULL",
        "password VARCHAR(255) NOT NULL",
        "salt VARCHAR(255) NOT NULL",
        "access_token VARCHAR(255)",
        "role TINYINT DEFAULT 1 COMMENT '0: Admin; 1: Visitor'",
        "status TINYINT DEFAULT 1 COMMENT '0: Inactive; 1: Active'",
        "created_at DATETIME DEFAULT CURRENT_TIMESTAMP");

    Database.update(prepareStatement);
  }

  /**
   * Creates the 'category' table in the database if it does not exist already.
   *
   * @throws SQLException If an SQL error occurs during table creation.
   */
  private static void createCategoryTable() throws SQLException {
    String statement = "CREATE TABLE IF NOT EXISTS category (?,?,?,?,?,?)";
    String prepareStatement = Database.prepareStructureSQL(
        statement,
        "id INT PRIMARY KEY AUTO_INCREMENT",
        "name VARCHAR(255) NOT NULL",
        "icon VARCHAR(255) NOT NULL",
        "pathname VARCHAR(255) NOT NULL UNIQUE",
        "status TINYINT DEFAULT 1 COMMENT '0: Inactive; 1: Active'",
        "created_at DATETIME DEFAULT CURRENT_TIMESTAMP");

    Database.update(prepareStatement);
  }

  /**
   * Creates the 'article' table in the database if it does not exist already.
   *
   * @throws SQLException If an SQL error occurs during table creation.
   */
  private static void createArticleTable() throws SQLException {
    String statement = "CREATE TABLE IF NOT EXISTS article (?,?,?,?,?,?,?,?,?,?)";
    String prepareStatement = Database.prepareStructureSQL(
        statement,
        "id INT PRIMARY KEY AUTO_INCREMENT",
        "title VARCHAR(255) NOT NULL",
        "thumbnail VARCHAR(255) NOT NULL",
        "description TEXT NOT NULL",
        "content TEXT NOT NULL",
        "views INT DEFAULT 0",
        "pathname VARCHAR(255) NOT NULL UNIQUE",
        "category_id INT NOT NULL",
        "status TINYINT DEFAULT 1 COMMENT '0: Inactive; 1: Active'",
        "created_at DATETIME DEFAULT CURRENT_TIMESTAMP");

    Database.update(prepareStatement);
  }
}
