package com.coremedia.contribution.contentcreator.strategy;

import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.cap.content.ContentType;
import com.coremedia.contribution.contentcreator.populator.PropertyPopulator;
import org.springframework.beans.factory.annotation.Required;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public abstract class AbstractContentCreationStrategy implements ContentCreationStrategy {

  /**
   * The content repository.
   */
  protected ContentRepository contentRepository;

  /**
   * The document type this content creation strategy is used for.
   */
  protected String contentType;

  /**
   * The naming strategy for new created content.
   */
  protected NamingStrategy namingStrategy;

  /**
   * The folder strategy for new created content.
   */
  protected FolderStrategy folderStrategy;

  /**
   * The folder strategy for source folder.
   */
  protected FolderStrategy sourceFolderStrategy;

  /**
   * Mapping of property name to property populator.
   */
  protected Map<String, PropertyPopulator> propertyPopulators;

  public ContentRepository getContentRepository() {
    return contentRepository;
  }

  /**
   * Flag that determines, whether the created content should be checked in or not. (default=true)
   */
  protected boolean checkIn = true;


  @Required
  public void setContentRepository(ContentRepository contentRepository) {
    this.contentRepository = contentRepository;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public NamingStrategy getNamingStrategy() {
    return namingStrategy;
  }

  public void setNamingStrategy(NamingStrategy namingStrategy) {
    this.namingStrategy = namingStrategy;
  }

  public FolderStrategy getFolderStrategy() {
    return folderStrategy;
  }

  public void setFolderStrategy(FolderStrategy folderStrategy) {
    this.folderStrategy = folderStrategy;
  }

  /**
   * A sourceFolderStrategy is used to define a source directory to copy files from
   *
   * @return the sourceFolderStrategy
   */
  public FolderStrategy getSourceFolderStrategy() {
    return sourceFolderStrategy;
  }

  /**
   * Simple Setter for sourceFolderStrategy
   *
   * @param sourceFolderStrategy the sourceFolderStrategy
   */
  public void setSourceFolderStrategy(FolderStrategy sourceFolderStrategy) {
    this.sourceFolderStrategy = sourceFolderStrategy;
  }

  public Map<String, PropertyPopulator> getPropertyPopulators() {
    return propertyPopulators;
  }

  public void setPropertyPopulators(Map<String, PropertyPopulator> propertyPopulators) {
    this.propertyPopulators = propertyPopulators;
  }

  public boolean isCheckIn() {
    return checkIn;
  }

  public void setCheckIn(boolean checkin) {
    this.checkIn = checkin;
  }

  public Content doCreateContent() {
    String folderPath = getFolderStrategy().getFolderPath();

    Content folder = getContentRepository().getChild(folderPath);
    if (folder == null) {
      // Folder does not exist so we need to create it first
      folder = getContentRepository().createSubfolders(folderPath);
    }

    String preferredName = getNamingStrategy().getPreferredName();
    String nameTemplate = getNamingStrategy().getNameTemplate();

    ContentType ct = getContentRepository().getContentType(getContentType());
    Content createdContent = ct.createByTemplate(folder, preferredName, nameTemplate, new HashMap<String, Object>());

    // Fill the property fields
    if (propertyPopulators != null) {
      applyFieldPopulators(createdContent);
    }

    // Check in the created content
    if (isCheckIn()) {
      doCheckIn(createdContent);
    }

    return createdContent;
  }

  /**
   * Check in the given content.
   * @param content The content to check in.
   */
  protected void doCheckIn(Content content) {
    if (content.isCheckedOut()) {
      content.checkIn();
    }
  }

  protected void applyFieldPopulators(Content content) {
    Set<String> availableProperties = content.getProperties().keySet();
    Set<String> propertyNames = propertyPopulators.keySet();
    for (String propertyName : propertyNames) {
      if (availableProperties.contains(propertyName)) {
        propertyPopulators.get(propertyName).populateProperty(propertyName, content);
      }
    }
  }


}
