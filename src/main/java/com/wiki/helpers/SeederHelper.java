package com.wiki.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeederHelper {
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
