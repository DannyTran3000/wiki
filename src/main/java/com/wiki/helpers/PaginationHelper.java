package com.wiki.helpers;

import java.util.ArrayList;
import java.util.List;

public class PaginationHelper {
  public static List<String> getDisplay(int totalPages, int currentPage) {
    List<String> pagination = new ArrayList<>();

    // Always include the first page
    pagination.add("1");

    if (totalPages <= 1) {
      return pagination;
    }

    // Determine the range around the current page
    int start = Math.max(2, currentPage - 2);
    int end = Math.min(totalPages - 1, currentPage + 2);

    // If there is a gap between the first page and the start of the range
    if (start > 2) {
      pagination.add("...");
    }

    // Add the range of pages around the current page
    for (int i = start; i <= end; i++) {
      pagination.add(Integer.toString(i));
    }

    // If there is a gap between the end of the range and the last page
    if (end < totalPages - 1) {
      pagination.add("...");
    }

    // Always include the last page
    pagination.add(Integer.toString(totalPages));

    return pagination;
  }
}
