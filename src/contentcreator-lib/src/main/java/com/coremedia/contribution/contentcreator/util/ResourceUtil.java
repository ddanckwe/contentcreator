package com.coremedia.contribution.contentcreator.util;

import java.io.*;

/**
 * Utility class for resource reading.
 */
public class ResourceUtil {

  /**
   * Reads a string from the given classpath resource.
   *
   * @param resourcePath The path to the resource.
   * @return
   */
  public static String readClasspathResource(String resourcePath) throws IOException {
    InputStream is = resourcePath.getClass().getResourceAsStream(resourcePath);
    InputStreamReader streamReader = new InputStreamReader(is);
    return readResource(streamReader);
  }

  /**
   * Reads a string from the given filesystem resource.
   * @param resourcePath The path to the resource.
   * @return
   */
  public static String readFileSystemResource(String resourcePath) throws IOException {
    FileReader fileReader = new FileReader(resourcePath);
    return readResource(fileReader);
  }

  private static String readResource(InputStreamReader streamReader) throws IOException {
    BufferedReader reader = new BufferedReader(streamReader);
    String line = null;
    StringBuilder stringBuilder = new StringBuilder();
    String ls = System.getProperty("line.separator");
    while ((line = reader.readLine()) != null) {
      stringBuilder.append(line);
      stringBuilder.append(ls);
    }
    return stringBuilder.toString();
  }
}
