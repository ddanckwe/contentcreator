package com.coremedia.contribution.contentcreator.populator;

import com.coremedia.cap.common.Blob;
import com.coremedia.cap.content.Content;

/**
 * A simple property populator that fills the property field with the given blob value.
 */
public class BlobPropertyPopulator extends AbstractPropertyPopulator implements PropertyPopulator {

  private Blob blob;

  public void populateProperty(String propertyName, Content content) {
    populateBlobProperty(propertyName, getBlob(), content);
  }

  public Blob getBlob() {
    return blob;
  }

  public void setBlob(Blob blob) {
    this.blob = blob;
  }

}
