package com.coremedia.contribution.contentcreator.strategy;

/**
 * Abstract implementation of the FolderStrategy interface.
 */
public abstract class AbstractFolderStrategy implements FolderStrategy {

  private String baseFolder;
  private String folderName;
  private int totalCopies;
  private int maxItemsPerFolder;


  public void setBaseFolder(String baseFolder) {
    this.baseFolder = baseFolder;
  }

  public String getBaseFolder() {
    return baseFolder;
  }

  public String getFolderName() {
    return folderName;
  }

  public void setFolderName(String folderName) {
    this.folderName = folderName;
  }

  public int getTotalCopies() {
    return totalCopies;
  }

  public void setTotalCopies(int totalCopies) {
    this.totalCopies = totalCopies;
  }

  public int getMaxItemsPerFolder() {
    return maxItemsPerFolder;
  }

  public void setMaxItemsPerFolder(int maxItemsPerFolder) {
    this.maxItemsPerFolder = maxItemsPerFolder;
  }

}
