package com.coremedia.contribution.contentcreator.strategy;

import java.util.Map;

/**
 * A simple naming strategy that only uses the input pattern.
 */
public class DefaultSourceNamingStrategy extends DefaultNamingStrategy {

  // if true - convert to lower case
  private String ignoreCase;
  // if true - remove file type extension
  private String withoutFileType;
  // if true - replace the patterns provided by the given map
  private Map replaceAll;

  /**
   * Simple setter
   * @param ignoreCase true/false
   */
  public void setIgnoreCase(String ignoreCase) {
    this.ignoreCase = ignoreCase;
  }

  /**
   * Simple getter
   * @return shell be "true" or "false"
   */
  public String getIgnoreCase() {
    return ignoreCase;
  }

  /**
   * Simple setter
   * @param withoutFileType true/false
   */
  public void setWithoutFileType(String withoutFileType) {
    this.withoutFileType = withoutFileType;
  }

  /**
   * Simple getter
   * @return shell be "true" or "false"
   */
  public String getWithoutFileType() {
    return withoutFileType;
  }

  /**
   * Simple setter
   * @param replaceAll map of replace patterns
   */
  public void setReplaceAll(Map replaceAll) {
    this.replaceAll = replaceAll;
  }

  /**
   * Simple getter
   * @return map with replace patterns
   */
  public Map getReplaceAll() {
    return replaceAll;
  }
}
