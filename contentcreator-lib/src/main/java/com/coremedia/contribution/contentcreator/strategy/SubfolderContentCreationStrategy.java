package com.coremedia.contribution.contentcreator.strategy;

import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.ContentType;

import java.util.HashMap;

/**
 * A folder strategy that uses the base folder and adds a folder suffix number.
 */
public class SubfolderContentCreationStrategy extends DefaultContentCreationStrategy {

  private static final int DEFAULT_MAX_ITEMS_PER_FOLDER = 100;

  private int numberOfCopies;
  private int maxItemsPerFolder = DEFAULT_MAX_ITEMS_PER_FOLDER;
  private int contentsCreated;
  private int currentFolderIndex;

  private String folderPrefix;

  public SubfolderContentCreationStrategy() {
    this.folderStrategy = new NumberedFolderStrategy();
    this.contentsCreated = 0;
    this.currentFolderIndex = 0;
  }

  public int getMaxItemsPerFolder() {
    return maxItemsPerFolder;
  }

  public void setMaxItemsPerFolder(int maxItemsPerFolder) {
    this.maxItemsPerFolder = maxItemsPerFolder;
  }

  public String getFolderPrefix() {
    return folderPrefix;
  }

  public void setFolderPrefix(String folderPrefix) {
    this.folderPrefix = folderPrefix;
  }

  public int getNumberOfCopies() {
    return numberOfCopies;
  }

  public void setNumberOfCopies(int numberOfCopies) {
    this.numberOfCopies = numberOfCopies;
  }

  @Override
  public Content createContent() {
    NumberedFolderStrategy fs = (NumberedFolderStrategy) getFolderStrategy();
    fs.setCurrentFolderIndex(currentFolderIndex);
    fs.setFolderName(folderPrefix);
    fs.setMaxItemsPerFolder(maxItemsPerFolder);

    String folderPath = fs.getFolderPath();

    Content folder = getContentRepository().getChild(folderPath);
    if (folder == null) {
      // Folder noes not exist so we need to create it first
      folder = getContentRepository().createSubfolders(folderPath);

    }

    String preferredName = getNamingStrategy().getPreferredName();
    String nameTemplate = getNamingStrategy().getNameTemplate();

    ContentType ct = getContentRepository().getContentType(getContentType());
    Content createdContent = ct.createByTemplate(folder, preferredName, nameTemplate, new HashMap<String, Object>());

    // Fill the property fields
    applyFieldPopulators(createdContent);

    if (createdContent != null) {
      contentsCreated++;
      if (contentsCreated >= maxItemsPerFolder) {
        // increase folder count and reset content count
        contentsCreated = 0;
        currentFolderIndex++;
      }

      // Check in the created content
      if (isCheckIn()) {
        doCheckIn(createdContent);
      }

    }

    return createdContent;
  }

}
