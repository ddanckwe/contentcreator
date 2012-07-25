package com.coremedia.contribution.contentcreator.populator;

import com.coremedia.cap.common.Blob;
import com.coremedia.cap.common.BlobService;
import com.coremedia.contribution.contentcreator.util.ResourceUtil;
import com.coremedia.xml.Markup;
import com.coremedia.xml.MarkupFactory;

import javax.activation.MimeTypeParseException;
import java.io.IOException;

/**
 * A blob property populator that fills the property field with a blob read from a file or classpath resource.
 */
public class ResourceBlobPropertyPopulator extends BlobPropertyPopulator {

  public static final String CLASSPATH_LOCATOR_PREFIX = "classpath:";
  public static final String FILESYSTEM_LOCATOR_PREFIX = "file:";

  private String resourcePath;
  private String contentType;
  private BlobService blobService;

  public String getResourcePath() {
    return resourcePath;
  }

  public void setResourcePath(String resourcePath) {
    this.resourcePath = resourcePath;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public BlobService getBlobService() {
    return blobService;
  }

  public void setBlobService(BlobService blobService) {
    this.blobService = blobService;
  }

  @Override
  public Blob getBlob() {
    Blob blob = null;
    
    if (resourcePath == null) {
      return null;
    }

    // Read resource
    byte[] data = new byte[0];
    try {

      if (resourcePath.startsWith(FILESYSTEM_LOCATOR_PREFIX)) {
        // Read file system resource
        data = ResourceUtil.readBytesFromFileSystemResource(resourcePath.replace(FILESYSTEM_LOCATOR_PREFIX, ""));
      }

      if (resourcePath.startsWith(CLASSPATH_LOCATOR_PREFIX)) {
        // Read classpath resource
        data = ResourceUtil.readBytesFromClasspathResource(resourcePath.replace(CLASSPATH_LOCATOR_PREFIX, ""));
      }

      if (data.length > 0) {
        blob = blobService.fromBytes(data, getContentType());
      }

    } catch (IOException e) {
      getLogger().error(e);
    } catch (MimeTypeParseException e) {
      getLogger().error(e);
    }

    return blob;
  }

}
