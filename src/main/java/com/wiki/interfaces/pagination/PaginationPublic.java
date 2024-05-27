package com.wiki.interfaces.pagination;

import java.util.List;

public class PaginationPublic {
  public int offset;
  public String currentPage, totalPages, perPage;
  public List<String> display;

  public PaginationPublic(String currentPage, String totalPages, String perPage, List<String> display) {
    this.offset = (Integer.valueOf(currentPage) - 1) * Integer.valueOf(perPage);
    this.currentPage = currentPage;
    this.totalPages = totalPages;
    this.perPage = perPage;
    this.display = display;
  }

  
  public int getOffset() {
    return offset;
  }

  public String getCurrentPage() {
    return currentPage;
  }

  public String getTotalPages() {
    return totalPages;
  }

  public String getPerPage() {
    return perPage;
  }

  public List<String> getDisplay() {
    return display;
  }
}
