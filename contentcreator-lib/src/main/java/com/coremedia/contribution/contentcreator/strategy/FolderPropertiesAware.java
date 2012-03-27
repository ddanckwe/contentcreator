package com.coremedia.contribution.contentcreator.strategy;

/**
 * Interface for folder strategies that support folderProperties.
 */
public interface FolderPropertiesAware {

  public static final String FOLDER_PROPERTIES_TYPE = "CMFolderProperties";
  public static final String FOLDER_PROPERTIES_NAME = "_folderProperties";

  void setFolderPropertiesTemplatePath(String path);
  String getFolderPropertiesTemplatePath();
  boolean hasFolderPropertiesTemplate();

}
