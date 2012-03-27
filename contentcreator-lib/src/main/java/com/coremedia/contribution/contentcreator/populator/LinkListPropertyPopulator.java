package com.coremedia.contribution.contentcreator.populator;


import com.coremedia.cap.content.Content;

import java.util.List;

/**
 * A simple property populator that fills the property field with the given list of contents.
 */
public class LinkListPropertyPopulator extends AbstractPropertyPopulator implements PropertyPopulator {

  private List<Content> values;

  public List<Content> getValues() {
    return values;
  }

  public void setValues(List<Content> values) {
    this.values = values;
  }

  public void populateProperty(String propertyName, Content content) {
    populateLinkListProperty(propertyName, values, content);
  }
}
