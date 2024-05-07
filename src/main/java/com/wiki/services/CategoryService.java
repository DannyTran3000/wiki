package com.wiki.services;

import java.sql.SQLException;

import com.wiki.helpers.SlugHelper;
import com.wiki.models.CategoryModel;

public class CategoryService {
  public static void create(String name, String icon) throws SQLException {
    // Generate new slug by category name
    String slug = SlugHelper.generate(name);
    String pathname = "/" + slug;

    // Insert new category
    CategoryModel.insertCategory(name, icon, pathname);
  }
}
