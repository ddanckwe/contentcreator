package com.coremedia.contribution.contentcreator.populator;

import com.coremedia.cap.content.Content;

import java.util.Random;

/**
 * Integer property populator that fills the property field with a random integer value.
 * Min and max values can be specified. Default values are min=0 max=100.
 */
public class RandomIntPropertyPopulator extends IntPropertyPopulator {

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
  protected Integer calculatePropertyValue(String propertyName, Content content) {
    Random random = new Random();
    int randomValue = random.nextInt(max - min + 1) + min;
    setValue(randomValue);
    return randomValue;
  }
}
