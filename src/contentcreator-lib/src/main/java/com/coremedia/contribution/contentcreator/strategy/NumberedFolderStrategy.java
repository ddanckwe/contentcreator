package com.coremedia.contribution.contentcreator.strategy;

import java.text.DecimalFormat;

/**
 * A folder strategy that uses the base folder and adds a folder suffix number.
 */
public class NumberedFolderStrategy extends AbstractFolderStrategy implements FolderStrategy, FolderPropertiesAware {

  private static final String DEFAULT_SUFFIX_TEMPLATE = "-0";

  private DecimalFormat formatter;
  private int currentFolderIndex;
  private String folderPropertiesPath;


  public NumberedFolderStrategy() {
    this(DEFAULT_SUFFIX_TEMPLATE);
  }

  public NumberedFolderStrategy(String suffixTemplate) {
    this.formatter = new DecimalFormat(suffixTemplate);
    this.currentFolderIndex = 0;
  }

  public int getCurrentFolderIndex() {
    return currentFolderIndex;
  }

  public void setCurrentFolderIndex(int currentFolderIndex) {
    this.currentFolderIndex = currentFolderIndex;
  }

  @Override
  public void setMaxItemsPerFolder(int maxItemsPerFolder) {
    super.setMaxItemsPerFolder(maxItemsPerFolder);
    updateFormatString();
  }

  @Override
  public void setTotalCopies(int totalCopies) {
    super.setTotalCopies(totalCopies);
    updateFormatString();
  }

  private void updateFormatString() {
    if (getMaxItemsPerFolder() > 0) {
      StringBuilder sb = new StringBuilder();
      // Build a new formatter template
      int folderCount = getTotalCopies() / getMaxItemsPerFolder() + (getTotalCopies() % getMaxItemsPerFolder() == 0 ? 0 : 1);
      int digets = Integer.toString(folderCount).length();
      sb.append("-");
      for (int i = 0; i < digets; i++) {
        sb.append("0");
      }
      this.formatter = new DecimalFormat(sb.toString());  
    }
  }

  public String getFolderPath() {
    StringBuilder folderPath = new StringBuilder();

    folderPath.append(getBaseFolder());

    if (getFolderName() != null && !getFolderName().equals("")) {
      folderPath.append("/");
      folderPath.append(getFolderName());
    }

    folderPath.append(formatter.format(currentFolderIndex));

    return folderPath.toString();
  }

  public void setFolderPropertiesTemplatePath(String path) {
    this.folderPropertiesPath = path;
  }

  public String getFolderPropertiesTemplatePath() {
    return this.folderPropertiesPath;
  }

  public boolean hasFolderPropertiesTemplate() {
    return !(getFolderPropertiesTemplatePath() == null || getFolderPropertiesTemplatePath().equals(""));
  }

}
