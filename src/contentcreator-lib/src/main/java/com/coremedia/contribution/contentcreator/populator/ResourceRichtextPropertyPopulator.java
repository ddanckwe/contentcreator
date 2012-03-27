package com.coremedia.contribution.contentcreator.populator;

import com.coremedia.contribution.contentcreator.util.ResourceUtil;
import com.coremedia.xml.Markup;
import com.coremedia.xml.MarkupFactory;

import java.io.*;

/**
 * A richtext property populator that fills the property field with richtext read from a file or classpath resource.
 */
public class ResourceRichtextPropertyPopulator extends RichtextPropertyPopulator {

  public static final String CLASSPATH_LOCATOR_PREFIX = "classpath:";
  public static final String FILESYSTEM_LOCATOR_PREFIX = "file:";

  private String resourcePath;
  private String data = null;

  public String getResourcePath() {
    return resourcePath;
  }

  public void setResourcePath(String resourcePath) {
    this.resourcePath = resourcePath;
  }

  @Override
  public String getValue() {
    if (resourcePath == null) {
      return null;
    }

    if (data != null) {
      return data;
    }

    // Read resource
    String data = "";
    try {

      if (resourcePath.startsWith(FILESYSTEM_LOCATOR_PREFIX)) {
        // Read file system resource
        data = ResourceUtil.readFileSystemResource(resourcePath.replace(FILESYSTEM_LOCATOR_PREFIX, ""));
      }

      if (resourcePath.startsWith(CLASSPATH_LOCATOR_PREFIX)) {
        // Read classpath resource
        data = ResourceUtil.readClasspathResource(resourcePath.replace(CLASSPATH_LOCATOR_PREFIX, ""));
      }

    } catch (IOException e) {
      getLogger().error(e);
    }
    this.data = data;
    
    return data;
  }

  @Override
  public Markup getMarkup() {
    String xml = MARKUP_PREFIX + getValue() + MARKUP_SUFFIX;
    return MarkupFactory.fromString(xml);
  }

}
