package com.coremedia.contribution.contentcreator.populator;

import java.util.Random;

/**
 * A richtext property populator that fills the given property with a random number of words from a given resource.
 */
public class RandomRichtextPropertyPopulator extends ResourceRichtextPropertyPopulator {

  public static final int DEFAULT_MIN_WORDS = 0;
  public static final int DEFAULT_MAX_WORDS = 100;

  private int minWords = DEFAULT_MIN_WORDS;
  private int maxWords = DEFAULT_MAX_WORDS;

  public int getMinWords() {
    return minWords;
  }

  public void setMinWords(int minWords) {
    this.minWords = minWords;
  }

  public int getMaxWords() {
    return maxWords;
  }

  public void setMaxWords(int maxWords) {
    this.maxWords = maxWords;
  }

  @Override
  public String getValue() {
    String data = super.getValue();

    Random random = new Random(System.currentTimeMillis());
    // Read resources and fill dictionary
    String[] dictionary = data.split(" ");

    int wordsToFetch = random.nextInt(maxWords - minWords) + minWords;

    StringBuilder randomOutput = new StringBuilder();
    for (int wordCount = 0; wordCount < wordsToFetch; wordCount++) {
      int randomIndex = random.nextInt(dictionary.length);
      randomOutput.append(dictionary[randomIndex]);
      if (wordCount != wordsToFetch-1) {
        randomOutput.append(" ");
      } else {
        randomOutput.append(".");
      }
    }

    getLogger().debug("Random text: " + randomOutput.toString());

    return randomOutput.toString();
  }
}
