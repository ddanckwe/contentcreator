package com.coremedia.contribution.contentcreator.populator;


import com.coremedia.cap.content.Content;

/**
 * A simple property populator that fills the property field with the given integer value.
 */
public class IntPropertyPopulator extends AbstractPropertyPopulator implements PropertyPopulator {

  Integer value;

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public void populateProperty(String propertyName, Content content) {
    populateIntegerProperty(propertyName, calculatePropertyValue(propertyName, content), content);
  }

  protected Integer calculatePropertyValue(String propertyName, Content content) {
    return getValue();
  }

}
