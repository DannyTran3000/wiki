package com.wiki.helpers;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.Locale;

public class SlugHelper {
  /**
   * Generates a slug from the given name by removing diacritics, replacing
   * non-word characters with hyphens,
   * and removing leading/trailing hyphens.
   *
   * @param name The input name from which to generate the slug.
   * @return The generated slug string.
   */
  public static String generate(String name) {
    // Normalize the string to remove diacritics (accents)
    String normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

    // Replace non-word characters with hyphens
    String slug = normalized.toLowerCase(Locale.ENGLISH).replaceAll("[^\\w\\s-]", "")
        .replaceAll("\\s+", "-");

    // Remove leading and trailing hyphens
    slug = slug.replaceAll("^-|-$", "");

    return slug;
  }

  /**
   * Extracts the article pathname from a given URL string.
   *
   * This method performs the following actions:
   * - Creates a URL object from the input string.
   * - Retrieves the path component of the URL.
   * - Splits the path into parts based on the '/' delimiter.
   * - Extracts and returns the category and slug parts if they exist in the
   * expected structure.
   * - If the URL does not conform to the expected structure, returns an empty
   * string and logs an appropriate message.
   *
   * @param urlString the input URL string from which to extract the article
   *                  pathname
   * @return a string containing the extracted article pathname in the format
   *         "/{category}/{slug}",
   *         or an empty string if the URL is invalid or does not contain the
   *         expected structure
   */
  public static String extractArticlePathname(String urlString) {
    try {
      // Create a URL object
      @SuppressWarnings("deprecation")
      URL url = new URL(urlString);

      // Get the path
      String path = url.getPath();

      // Extract the slug from the path
      String[] pathParts = path.split("/");
      if (pathParts.length >= 5) { // Assuming "/wiki-portal/articles/{category}/{slug}" structure
        String category = pathParts[3];
        String slug = pathParts[4];
        return "/" + category + "/" + slug;
      } else {
        System.out.println("Category or Slug not found in URL.");
        return "";
      }

    } catch (MalformedURLException e) {
      System.err.println("Invalid URL: " + urlString);
    }

    return "";
  }
}
