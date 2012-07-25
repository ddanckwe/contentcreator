package com.coremedia.contribution.contentcreator.populator;

import com.coremedia.cap.content.Content;

/**
 * Interface for all property field populators.
 */
public interface PropertyPopulator {

  public static final String BLOB_PROPERTY = "BlobProperty";
  public static final String DATE_PROPERTY = "DateProperty";
  public static final String LINKLIST_PROPERTY = "LinkListProperty";
  public static final String STRING_PROPERTY = "StringProperty";
  public static final String XML_PROPERTY = "XmlProperty";

  /**
   * Populates the given field of the given content.
   * @param propertyName The name of the property.
   * @param content The content object.
   */
  void populateProperty(String propertyName, Content content);

}
