package com.wiki.helpers;

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
}
