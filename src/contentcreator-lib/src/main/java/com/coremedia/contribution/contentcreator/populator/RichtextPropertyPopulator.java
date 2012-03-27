package com.coremedia.contribution.contentcreator.populator;

/**
 * XmlPropertyPopulator that uses the coremedia-richtext-1.0 grammar.
 * The populator basicly wraps the given richtext in the markup prefix and suffix specified by the grammar.
 */
public class RichtextPropertyPopulator extends XmlPropertyPopulator {

  protected static final String MARKUP_PREFIX = "<div xmlns=\"http://www.coremedia.com/2003/richtext-1.0\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"><p>";
  protected static final String MARKUP_SUFFIX = "</p></div>";

  @Override
  public void setValue(String value) {
    if (value != null) {
      // Remove unneeded whitespace
      value = value.trim();
    } 
    String xmlString = MARKUP_PREFIX + value + MARKUP_SUFFIX;
    super.setValue(xmlString);
  }

}
