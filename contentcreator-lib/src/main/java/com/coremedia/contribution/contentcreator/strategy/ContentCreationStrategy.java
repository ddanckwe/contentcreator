package com.coremedia.contribution.contentcreator.strategy;

import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentRepository;
import com.coremedia.contribution.contentcreator.populator.PropertyPopulator;

import java.util.Map;

/**
 * Interface for all content creation strategies.
 */
public interface ContentCreationStrategy {

  void setContentRepository(ContentRepository contentRepository);
  ContentRepository getContentRepository();

  String getContentType();
  void setContentType(String contentType);

  NamingStrategy getNamingStrategy();
  void setNamingStrategy(NamingStrategy namingStrategy);

  FolderStrategy getFolderStrategy();
  void setFolderStrategy(FolderStrategy folderStrategy);

  Map<String, PropertyPopulator> getPropertyPopulators();
  void setPropertyPopulators(Map<String, PropertyPopulator> propertyPopulators);

  /**
   * Creates a new content object in the repository and returns it.
   * @return The new created content.
   */
  Content createContent();

}
