package com.coremedia.contribution.contentcreator.populator;

import com.coremedia.cap.content.Content;
import com.coremedia.cap.content.query.QueryService;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A LinkListPopulator that fills the given linklist property with values found by a content query.
 */
public class ContentQueryLinkListPropertyPopulator extends LinkListPropertyPopulator {

  public static final int DEFAULT_MAX_USE_SEARCH_RESULT_ITEMS = 1;

  /**
   * The content query string.
   */
  private String query;

  /*
   * Number of result items to use for the linklist
   */
  private int maxUseResultItems = DEFAULT_MAX_USE_SEARCH_RESULT_ITEMS;


  /**
   * true to shuffle the query results, false if not.
   * When false, the first maxUseResultItems items will be used.
   */
  private boolean shuffle;


  private QueryService queryService;


  public int getMaxUseResultItems() {
    return maxUseResultItems;
  }

  public void setMaxUseResultItems(int maxUseResultItems) {
    this.maxUseResultItems = maxUseResultItems;
  }

  public QueryService getQueryService() {
    return queryService;
  }

  public void setQueryService(QueryService queryService) {
    this.queryService = queryService;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public boolean isShuffle() {
    return shuffle;
  }

  public void setShuffle(boolean shuffle) {
    this.shuffle = shuffle;
  }

  @Override
  public void populateProperty(String propertyName, Content content) {
    // Search for contents ...
    ArrayList<Content> results = new ArrayList<Content>(queryService.poseContentQuery(query));

    // ... shuffle the results
    Collections.shuffle(results);

    // and link them to the property
    ArrayList<Content> links = new ArrayList<Content>();
    for (int i = 0; i < results.size() && i < getMaxUseResultItems(); i++) {
      links.add(results.get(i));
    }
    setValues(results);

    populateLinkListProperty(propertyName, links, content);
  }

}
