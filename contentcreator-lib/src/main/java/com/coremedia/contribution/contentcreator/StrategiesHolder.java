package com.coremedia.contribution.contentcreator;

import com.coremedia.contribution.contentcreator.strategy.ContentCreationStrategy;

import java.util.Map;

/**
 * Utility class that holds a list of content creation strategies
 */
public class StrategiesHolder {

  private ContentCreationStrategy defaultStrategy;
  private Map<String, ContentCreationStrategy> strategies;


  public ContentCreationStrategy getDefaultStrategy() {
    return defaultStrategy;
  }

  public void setDefaultStrategy(ContentCreationStrategy defaultStrategy) {
    this.defaultStrategy = defaultStrategy;
  }

  public Map<String, ContentCreationStrategy> getStrategies() {
    return strategies;
  }

  public void setStrategies(Map<String, ContentCreationStrategy> strategies) {
    this.strategies = strategies;
  }

  /**
   * Adds the given strategy for the given content type.
   * @param type The content type.
   * @param strategy The content creation strategy.
   */
  public void registerStrategy(String type, ContentCreationStrategy strategy) {
    this.strategies.put(type, strategy);
  }

  /**
   * Removes the ContentCreationStrategy registered for the given content type.
   * @param type The content type.
   */
  public void removeStrategy(String type) {
    this.strategies.remove(type);
  }

  /**
   * Returns the ContentCreationStrategy for the given type.
   * @param contentType The content type
   * @return
   */
  public ContentCreationStrategy getStrategyForContentType(String contentType) {
    ContentCreationStrategy strategy = strategies.get(contentType);
    if (strategy == null) {
      strategy = defaultStrategy;
      strategy.setContentType(contentType);
    }
    return strategy;
  }

}
