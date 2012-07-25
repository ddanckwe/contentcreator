package com.coremedia.contribution.contentcreator.strategy;

import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentType;
import com.coremedia.contribution.contentcreator.populator.ResourceBlobPropertyPopulator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Default implementation of a content creation strategy tnat copies files from a given source.
 */
public class DefaultContentCreationFromSourceStrategy extends AbstractContentCreationStrategy {

  /**
   * Creating content from a given source directory.
   * Populators an properties have to be configured by spring.
   * Every property needs an appropriate populator.
   *
   * Calls createContentInternal to recursively go trough the directories and sub-directories
   *
   * @return the created Content or null
   */
  public Content createContent() {
    return createContentInternal(getSourceFolderStrategy().getFolderPath());
  }

  /**
   * Here the content is actually created
   *
   * @param path the path to the directory
   * @return the created content or null
   */
  public Content createContentInternal(String path) {
    // Fill the property fields
    if (propertyPopulators != null) {
      File templateDirectory = new File(path);
      if (templateDirectory.isDirectory()) {
        File[] files = templateDirectory.listFiles();
        for (File file : files) {
          // create all files within this directory
          if (file.isFile()) {
            createFile(file);
          }
          else if (file.isDirectory()) {
            createContentInternal(file.getPath());
          }
        }
      }
    }

    return null;
  }

  /**
   * Getter fpr the path of the directory
   *
   * @return path of the directory
   */
  private String getFolderPath() {
    String folderPath = getFolderStrategy().getFolderPath();

    Content folder = getContentRepository().getChild(folderPath);
    if (folder == null) {
      // Folder noes not exist so we need to create it first
      getContentRepository().createSubfolders(folderPath);
    }

    return folderPath;
  }

  /**
   * Create the given file
   *
   * @param file the file to be created
   */
  private void createFile(File file) {
    ContentType ct = getContentRepository().getContentType(getContentType());
    Content createdContent;

    String name = file.getName();

    // prove weather the namingStrategy is appropriate to a source
    NamingStrategy namingStrategy = getNamingStrategy();
    if (namingStrategy instanceof DefaultSourceNamingStrategy) {
      DefaultSourceNamingStrategy sourceNamingStrategy = ((DefaultSourceNamingStrategy) namingStrategy);

      // Remove the filetype
      if (sourceNamingStrategy.getWithoutFileType().equalsIgnoreCase("true")) {
        if (!name.isEmpty()) {
          if (name.contains(".")) {
            int pos = name.lastIndexOf(".");
            name = name.substring(0, pos);
          }
        }
      }

      // convert to lower case
      if (sourceNamingStrategy.getIgnoreCase().equalsIgnoreCase("true")) {
        name = name.toLowerCase();
      }

      // replace all key-chars by value-chars
      if (!sourceNamingStrategy.getReplaceAll().isEmpty()) {
        Map<String, String> map = sourceNamingStrategy.getReplaceAll();
        for (String key : map.keySet()) {
          if (name.contains(key)) {
            name = name.replace(key, map.get(key));
          }
        }
      }
    }

    // the folder path. If a folder does not exist, create it
    String folderpath = getFolderPath();
    String path = "";
    if (file.getParent() != null && file.getParentFile().getName() != null) {
      path = folderpath + file.getParentFile().getName();
      Content folder = getContentRepository().getChild(path);
      if (folder == null) {
        // Folder does not exist so we need to create it first
        getContentRepository().createSubfolders(path);
      }
    }

    // create the content
    createdContent = ct.createByTemplate(path.isEmpty() ? folderpath : path, name, namingStrategy.getNameTemplate(), new HashMap<String, Object>());

    // populate the properties - the configuration is done via spring
    if (propertyPopulators != null) {
      Set<String> availableProperties = createdContent.getProperties().keySet();
      Set<String> propertyNames = propertyPopulators.keySet();
      for (String propertyName : propertyNames) {
        if (availableProperties.contains(propertyName)) {
          // ResourceBlobPropertyPopulator needs a prefix file or classpath
          if (propertyPopulators.get(propertyName) instanceof ResourceBlobPropertyPopulator) {
            ((ResourceBlobPropertyPopulator) propertyPopulators.get(propertyName)).setResourcePath(ResourceBlobPropertyPopulator.FILESYSTEM_LOCATOR_PREFIX + file.getPath());
          }

          // populate
          propertyPopulators.get(propertyName).populateProperty(propertyName, createdContent);

          // Check in the created content
          if (isCheckIn()) {
            doCheckIn(createdContent);
          }
        }
      }
    }
  }
}
