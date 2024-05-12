package com.wiki.services;

import java.sql.SQLException;

import com.wiki.helpers.SlugHelper;
import com.wiki.models.CategoryModel;

public class CategoryService {
  /**
   * Creates a new category with the specified name and icon.
   * Generates a slug based on the category name and inserts the category into the
   * database.
   *
   * @param name The name of the category.
   * @param icon The icon associated with the category.
   * @throws SQLException If an SQL exception occurs while inserting the category
   *                      into the database.
   */
  public static void create(String name, String icon) throws SQLException {
    // Generate new slug by category name
    String slug = SlugHelper.generate(name);
    String pathname = "/" + slug;

    // Insert new category
    CategoryModel.insertCategory(name, icon, pathname);
  }
}
