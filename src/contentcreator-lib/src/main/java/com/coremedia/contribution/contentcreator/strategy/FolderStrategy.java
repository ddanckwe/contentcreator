package com.coremedia.contribution.contentcreator.strategy;

/**
 * Strategy for folder name creation.
 */
public interface FolderStrategy {

  public static final String ROOT_FOLDER = "/";
  public static final String DEFAULT_NAME = "Folder";

  void setBaseFolder(String baseFolder);
  String getBaseFolder();

  void setFolderName(String name);
  String getFolderName();

  void setTotalCopies(int copies);
  int getTotalCopies();

  void setMaxItemsPerFolder(int maxItems);
  int getMaxItemsPerFolder();

  String getFolderPath();

}
