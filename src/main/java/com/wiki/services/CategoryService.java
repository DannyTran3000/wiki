package com.wiki.services;

import java.sql.SQLException;
import java.util.List;

import com.wiki.helpers.SlugHelper;
import com.wiki.interfaces.category.CategoryPublic;
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

    // Insert new category
    CategoryModel.insertCategory(name, icon, slug);
  }

  public static List<CategoryPublic> read() throws SQLException {
    return CategoryModel.selectAll();
  }

  public static String readName(String slug) throws SQLException {
    return CategoryModel.selectCategoryNameBySlug(slug);
  }
}
