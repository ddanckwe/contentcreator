package com.coremedia.contribution.contentcreator.populator;

import com.coremedia.cap.common.Blob;
import com.coremedia.cap.content.Content;
import com.coremedia.xml.Markup;
import org.apache.commons.logging.Log;

import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sbuettne
 * Date: 21.10.11
 * Time: 11:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPropertyPopulator implements PropertyPopulator {

  private Log logger;

  public Log getLogger() {
    return logger;
  }

  public void setLogger(Log logger) {
    this.logger = logger;
  }

  /**
   * Populate the given property of the given content with the specified boolean value.
   * Boolean values will be stored as an integer value.
   * @param content The content object.
   * @param propertyName The name of the content property to populate.
   * @param value The value for the field.
   */
  protected void populateBooleanProperty(String propertyName, boolean value, Content content) {
    content.set(propertyName, value ? 1 : 0);
  }

  /**
   * Populate the given property of the given content with the specified blob value.
   * @param content The content object.
   * @param propertyName The name of the content property to populate.
   * @param value The value for the field.
   */
  protected void populateBlobProperty(String propertyName, Blob value, Content content) {
    content.set(propertyName, value);
  }

  /**
   * Populate the given property of the given content with the specified date value.
   * @param content The content object.
   * @param propertyName The name of the content property to populate.
   * @param value The value for the field.
   */
  protected void populateDateProperty(String propertyName, Calendar value, Content content) {
    content.set(propertyName, value);
  }

  /**
   * Populate the given property of the given content with the specified list of content objects.
   * @param content The content object.
   * @param propertyName The name of the content property to populate.
   * @param value The value for the field.
   */
  protected void populateLinkListProperty(String propertyName, List<Content> value, Content content) {
    content.set(propertyName, value);
  }

  /**
   * Populate the given property of the given content with the specified string value.
   * @param content The content object.
   * @param propertyName The name of the content property to populate.
   * @param value The value for the field.
   */
  protected void populateStringProperty(String propertyName, String value, Content content) {
    content.set(propertyName, value);
  }

  /**
   * Populate the given property of the given content with the specified markup value.
   * @param content The content object.
   * @param propertyName The name of the content property to populate.
   * @param value The value for the field.
   */
  protected void populateXmlProperty(String propertyName, Markup value, Content content) {
    content.set(propertyName, value);
  }

  /**
   * Populate the given property of the given content with the specified integer value.
   * @param content The content object.
   * @param propertyName The name of the content property to populate.
   * @param value The value for the field.
   */
  protected void populateIntegerProperty(String propertyName, Integer value, Content content) {
    content.set(propertyName, value);
  }

}
