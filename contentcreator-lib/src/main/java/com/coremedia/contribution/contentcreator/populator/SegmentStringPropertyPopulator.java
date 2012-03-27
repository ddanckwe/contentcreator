package com.coremedia.contribution.contentcreator.populator;


import com.coremedia.cap.common.IdHelper;
import com.coremedia.cap.content.Content;

/**
 * A simple property populator that uses the document type and id
 * to create a segment string for the specified property field.
 */
public class SegmentStringPropertyPopulator extends StringPropertyPopulator {

  @Override
  protected String calculateStringValue(String propertyName, Content content) {
    StringBuilder segmentBuilder = new StringBuilder();

    int contentId = IdHelper.parseContentId(content.getId());
    String typeName = content.getType().getName();

    segmentBuilder.append(typeName.toLowerCase());
    segmentBuilder.append("-");
    segmentBuilder.append(contentId);

    setValue(segmentBuilder.toString());
    return segmentBuilder.toString();
  }

}
