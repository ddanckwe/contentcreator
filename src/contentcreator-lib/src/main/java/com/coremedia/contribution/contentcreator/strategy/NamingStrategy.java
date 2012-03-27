package com.coremedia.contribution.contentcreator.strategy;

/**
 * Strategy for document name creation.
 */
public interface NamingStrategy {

  String getPreferredName();

  String getNameTemplate();
  void setNameTemplate(String template);

}
