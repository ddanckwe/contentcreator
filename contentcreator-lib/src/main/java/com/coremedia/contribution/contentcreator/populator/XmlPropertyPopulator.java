package com.coremedia.contribution.contentcreator.populator;

import com.coremedia.cap.content.Content;
import com.coremedia.xml.Markup;
import com.coremedia.xml.MarkupFactory;

/**
 * A simple property populator that fills the property field with the given xml markup value.
 */
public class XmlPropertyPopulator extends AbstractPropertyPopulator implements PropertyPopulator {

  private Markup markup;

  public Markup getMarkup() {
    return markup;
  }

  public void setMarkup(Markup markup) {
    this.markup = markup;
  }

  public String getValue() {
    if (markup != null) {
      return markup.toString();
    }
    return "";
  }

  public void setValue(String value) {
    // Parse the markup string
    markup = MarkupFactory.fromString(value);
  }

  public void populateProperty(String propertyName, Content content) {
    populateXmlProperty(propertyName, getMarkup(), content);
  }

}
