package com.coremedia.contribution.contentcreator.strategy;

import com.coremedia.cap.content.Content;

/**
 * Default implementation of a content creation strategy.
 */
public class DefaultContentCreationStrategy extends AbstractContentCreationStrategy implements ContentCreationStrategy {

  public Content createContent() {
    return doCreateContent();
  }

}
