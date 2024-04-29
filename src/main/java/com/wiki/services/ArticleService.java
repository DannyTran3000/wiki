package com.wiki.services;

import java.sql.SQLException;

import com.wiki.helpers.SlugHelper;
import com.wiki.models.ArticleModel;
import com.wiki.models.CategoryModel;

public class ArticleService {
  public static void create(String title, String thumbnail, String description, String content, int categoryId)
      throws SQLException {
    // Get category information
    String categoryPathname = CategoryModel.selectCategoryPathnameById(categoryId);

    // Generate new slug by article title
    String slug = SlugHelper.generate(title);
    String pathname = categoryPathname + "/" + slug;
    
    ArticleModel.insertArticle(title, thumbnail, description, content, pathname, categoryId);
  }
}
