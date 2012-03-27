package com.coremedia.contribution.contentcreator.populator;


import com.coremedia.cap.common.IdHelper;
import com.coremedia.cap.content.Content;


/**
 * A simple string property populator that fills the given property with the content id.
 */
public class IdStringPropertyPopulator extends StringPropertyPopulator {

  @Override
  protected String calculateStringValue(String propertyName, Content content) {
    String value = "" + IdHelper.parseContentId(content.getId());
    setValue(value);
    return value;
  }

}
