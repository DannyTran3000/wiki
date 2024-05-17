package com.wiki.interfaces.filterSystem;

public class FilterSystemPublic {
  public String keyword, category, categoryValue, sort, sortValue;

  public FilterSystemPublic(String keyword, String category, String categoryValue, String sort, String sortValue) {
    this.keyword = keyword;
    this.category = category;
    this.categoryValue = categoryValue;
    this.sort = sort;
    this.sortValue = sortValue;
  }

  public String getKeyword() {
    return keyword;
  }

  public String getCategory() {
    return category;
  }

  public String getCategoryValue() {
    return categoryValue;
  }

  public String getSort() {
    return sort;
  }

  
  public String getSortValue() {
    return sortValue;
  }
}