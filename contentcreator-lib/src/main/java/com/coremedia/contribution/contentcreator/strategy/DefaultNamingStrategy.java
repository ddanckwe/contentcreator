package com.coremedia.contribution.contentcreator.strategy;

/**
 * Simple naming strategy
 */
public class DefaultNamingStrategy implements NamingStrategy {

  public static final String DEFAULT_NAME_PREFIX = "New content";
  public static final String DEFAULT_NAME_TEMPLATE = "{3} ({1})";

  private String namePrefix;
  private String nameTemplate;

  public String getNamePrefix() {
    return namePrefix;
  }

  public void setNamePrefix(String namePrefix) {
    this.namePrefix = namePrefix;
  }

  public String getPreferredName() {
    if (namePrefix == null) {
      return DEFAULT_NAME_PREFIX;
    }
    return namePrefix;
  }

  public String getNameTemplate() {
    if (nameTemplate == null) {
      return DEFAULT_NAME_TEMPLATE;
    }
    return nameTemplate;
  }

  public void setNameTemplate(String template) {
    this.nameTemplate = template;
  }


}
