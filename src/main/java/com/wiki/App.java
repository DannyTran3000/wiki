package com.wiki;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import com.wiki.services.CategoryService;

public class App {
  public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
    CategoryService.create("Tech Insights", "https://wiki-article.strawflag.com/wp-content/uploads/2024/04/TECH.png");
    CategoryService.create("Science Discoveries", "https://wiki-article.strawflag.com/wp-content/uploads/2024/04/SCIENCE.png");
    CategoryService.create("Medicine and Health", "https://wiki-article.strawflag.com/wp-content/uploads/2024/04/MEDECINE.png");
    CategoryService.create("Sports and Games", "https://wiki-article.strawflag.com/wp-content/uploads/2024/04/logo-football.png");
    CategoryService.create("Arts and Cultures", "https://wiki-article.strawflag.com/wp-content/uploads/2024/04/ART.png");
    CategoryService.create("Politic", "https://wiki-article.strawflag.com/wp-content/uploads/2024/04/POLITIC.png");
  }
}
