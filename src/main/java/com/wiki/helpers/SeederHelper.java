package com.wiki.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeederHelper {
  /**
   * Shuffles the words in the input string.
   *
   * This method performs the following actions:
   * - Splits the input string into words based on whitespace.
   * - Converts the array of words into a list for shuffling.
   * - Shuffles the words in the list.
   * - Joins the shuffled words back into a single string.
   * - Removes any trailing spaces and returns the shuffled string.
   *
   * @param input the input string containing the words to be shuffled
   * @return a string with the words from the input string shuffled in random
   *         order
   */
  public static String shuffleWords(String input) {
    // Split the input string into words
    String[] words = input.split("\\s+"); // Split by whitespace

    // Convert the array of words to a list for shuffling
    List<String> wordList = new ArrayList<>();
    Collections.addAll(wordList, words);

    // Shuffle the words
    Collections.shuffle(wordList);

    // Join the shuffled words back into a string
    StringBuilder shuffled = new StringBuilder();
    for (String word : wordList) {
      shuffled.append(word).append(" ");
    }

    // Remove trailing space and return the shuffled string
    return shuffled.toString().trim();
  }
}
