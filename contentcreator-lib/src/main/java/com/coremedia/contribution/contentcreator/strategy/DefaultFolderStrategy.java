package com.coremedia.contribution.contentcreator.strategy;

/**
 * A simple folder strategy that only uses the base folder.
 */
public class DefaultFolderStrategy extends AbstractFolderStrategy implements FolderStrategy {

  public static final String DEFAULT_FOLDER = ROOT_FOLDER;

  public String getFolder() {
    return getBaseFolder();
  }

  public void setFolder(String folder) {
    setBaseFolder(folder);
  }

  public String getFolderPath() {
    if (getFolder() == null || getFolder().equals("")) {
      return DEFAULT_FOLDER;
    }
    return getFolder();
  }

}
