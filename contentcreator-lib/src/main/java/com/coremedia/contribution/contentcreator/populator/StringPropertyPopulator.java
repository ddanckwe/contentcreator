package com.coremedia.contribution.contentcreator.populator;

import com.coremedia.cap.content.Content;

/**
 * A simple property populator that fills the property field with the given string value.
 */
public class StringPropertyPopulator extends AbstractPropertyPopulator implements PropertyPopulator {

  private String value = "";

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void populateProperty(String propertyName, Content content) {
    populateStringProperty(propertyName, calculateStringValue(propertyName, content), content);
  }

  protected String calculateStringValue(String propertyName, Content content) {
    return getValue();
  }

}
