package com.coremedia.contribution.contentcreator.populator;

import com.coremedia.cap.content.Content;

/**
 * A simple string property populator that fills the given property with the content type.
 */
public class TypeStringPropertyPopulator extends StringPropertyPopulator {

  @Override
  protected String calculateStringValue(String propertyName, Content content) {
    String value = content.getType().getName();
    setValue(value);
    return value;
  }

}
