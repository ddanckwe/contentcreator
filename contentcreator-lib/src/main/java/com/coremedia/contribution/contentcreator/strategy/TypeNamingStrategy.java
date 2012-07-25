package com.coremedia.contribution.contentcreator.strategy;


import com.coremedia.cap.content.ContentType;

/**
 * A naming strategy that uses the content type for the name.
 */
public class TypeNamingStrategy extends DefaultNamingStrategy implements NamingStrategy {

  private ContentType type;

  public ContentType getType() {
    return type;
  }

  public void setType(ContentType type) {
    this.type = type;
  }

  @Override
  public String getPreferredName() {
    if (getType() != null) {
      return getType().getName();
    }
    return super.getPreferredName();
  }


}
