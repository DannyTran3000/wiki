package com.wiki.migration;

import java.sql.SQLException;
import java.util.Random;

import com.wiki.helpers.SeederHelper;
import com.wiki.services.ArticleService;
import com.wiki.services.AuthService;
import com.wiki.services.CategoryService;

public class Seeder {
  public static void main(String[] args) throws Exception {
    runUserSeeder();
    runCategorySeeder();
    runArticleSeeder();
  }

  /**
   * Runs the user seeder to create an admin user.
   *
   * @throws Exception If an error occurs during user creation.
   */
  private static void runUserSeeder() throws Exception {
    AuthService.register("s1529456@student.mp.edu.au", "Admin", "Wiki3000.");
  }

  /**
   * Runs the category seeder to create predefined categories.
   *
   * @throws SQLException If an SQL error occurs during category creation.
   */
  private static void runCategorySeeder() throws SQLException {
    CategoryService.create("Tech Insights", "https://wiki-article.strawflag.com/wp-content/uploads/2024/04/TECH.png");
    CategoryService.create("Science Discoveries",
        "https://wiki-article.strawflag.com/wp-content/uploads/2024/04/SCIENCE.png");
    CategoryService.create("Medicine and Health",
        "https://wiki-article.strawflag.com/wp-content/uploads/2024/04/MEDECINE.png");
    CategoryService.create("Sports and Games",
        "https://wiki-article.strawflag.com/wp-content/uploads/2024/04/logo-football.png");
    CategoryService.create("Arts and Cultures",
        "https://wiki-article.strawflag.com/wp-content/uploads/2024/04/ART.png");
    CategoryService.create("Politic", "https://wiki-article.strawflag.com/wp-content/uploads/2024/04/POLITIC.png");
  }

  /**
   * Runs the article seeder to create predefined articles.
   *
   * @throws SQLException If an SQL error occurs during article creation.
   */
  private static void runArticleSeeder() throws SQLException {
    final int newArticleNumber = 50; // max should be 90 for once !!! out of memory

    Random random = new Random();

    for (int i = 0; i < newArticleNumber; i++)
      ArticleService.create(
          SeederHelper.shuffleWords(Sample.title),
          Sample.thumbnails[random.nextInt(Sample.thumbnails.length)],
          SeederHelper.shuffleWords(Sample.description),
          Sample.content,
          random.nextInt(6) + 1);
  }
}
