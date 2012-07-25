package com.coremedia.contribution.contentcreator.populator;

import com.coremedia.cap.content.Content;

import java.util.List;

/**
 * A property populator that aggregates a list of string populators to fill the given property.
 */
public class AggregatedStringPropertyPopulator extends StringPropertyPopulator {

  public static final String DEFAULT_SEPARATOR = "-";

  private List<StringPropertyPopulator> populators;
  private String separator;

  public List<StringPropertyPopulator> getPopulators() {
    return populators;
  }

  public void setPopulators(List<StringPropertyPopulator> populators) {
    this.populators = populators;
  }

  public String getSeparator() {
    return separator;
  }

  public void setSeparator(String separator) {
    this.separator = separator;
  }

  @Override
  protected String calculateStringValue(String propertyName, Content content) {
    StringBuilder aggreatedString = new StringBuilder();

    for (int i = 0; i < populators.size(); i++) {
      StringPropertyPopulator populator = populators.get(i);
      aggreatedString.append(populator.calculateStringValue(propertyName, content));
      if (i != populators.size()-1) {
        aggreatedString.append(getSeparator());
      }
    }

    String value = aggreatedString.toString();
    setValue(value);
    return value;
  }
  
}
