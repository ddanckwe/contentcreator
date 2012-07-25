package com.coremedia.contribution.contentcreator.populator;

import java.util.Random;

/**
 * A LinkListPopulator that fills the given linklist property with a random amount of values found by a content query.
 */
public class RandomContentQueryLinkListPropertyPopulator extends ContentQueryLinkListPropertyPopulator {

  public static final int DEFAULT_MIN_ITEMS = 0;
  public static final int DEFAULT_MAX_ITEMS = 10;

  private int min;
  private int max;

  public int getMin() {
    return min;
  }

  public void setMin(int min) {
    this.min = min;
  }

  public int getMax() {
    return max;
  }

  public void setMax(int max) {
    this.max = max;
  }

  @Override
  public int getMaxUseResultItems() {
    Random random = new Random();
    int randomMaxUseResultItems = random.nextInt(max - min + 1) + min;
    setMaxUseResultItems(randomMaxUseResultItems);
    return randomMaxUseResultItems;
  }

}
