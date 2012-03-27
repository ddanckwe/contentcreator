package com.coremedia.contribution.contentcreator.populator;

import com.coremedia.cap.content.Content;

/**
 * A simple property populator that fills the property field with the given boolean value.
 */
public class BooleanPropertyPopulator extends AbstractPropertyPopulator implements PropertyPopulator{

  Boolean value;

  public Boolean getValue() {
    return value;
  }

  public void setValue(Boolean value) {
    this.value = value;
  }

  public void populateProperty(String propertyName, Content content) {
    populateBooleanProperty(propertyName, getValue(), content);
  }

}
