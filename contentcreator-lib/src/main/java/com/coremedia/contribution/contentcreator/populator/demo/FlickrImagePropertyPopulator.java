package com.coremedia.contribution.contentcreator.populator.demo;


import com.coremedia.cap.common.Blob;
import com.coremedia.cap.common.BlobService;
import com.coremedia.contribution.contentcreator.populator.BlobPropertyPopulator;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.activation.MimeTypeParseException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sample property populator that fills the given property with a picture from a search on flickr.com.
 * Just for demo purposes!
 */
public class FlickrImagePropertyPopulator extends BlobPropertyPopulator {

  private static final Log LOG = LogFactory.getLog(FlickrImagePropertyPopulator.class);

  private static final String FLICKR_BASE_URL = "http://www.flickr.com";

  private BlobService blobService;
  private String searchTerm;


  /**
   * Search images on flickr.com for the given search term.
   *
   * @param searchTerm The search term.
   * @return returns list of image urls.
   */
  public List<String> searchImages(String searchTerm) {
    List<String> imageUrls = new ArrayList<String>();

    HttpClient client = new HttpClient();
    HttpMethod method = new GetMethod(FLICKR_BASE_URL + "/search/?q=" + searchTerm);

    try {
      int statusCode = client.executeMethod(method);

      if (statusCode != HttpStatus.SC_OK) {
        System.err.println("Method failed: " + method.getStatusLine());
      }

      // Read the response body.
      StringWriter writer = new StringWriter();
      IOUtils.copy(method.getResponseBodyAsStream(), writer, "UTF-8");
      String response = writer.toString();


      // Deal with the response.
      // Use caution: ensure correct character encoding and is not binary data

      String imgRegex = "<img src=\"(http://[a-zA-Z0-9\\.\\/_]+)\" .* class=\"pc_img\" .*/>";
      Pattern pattern = Pattern.compile(imgRegex);
      Matcher matcher = pattern.matcher(response);

      while (matcher.find()) {
        String thumbUrl = matcher.group(1);           // First group is image thumbnail url.
        String imageUrl = thumbUrl.replace("_t", ""); // Removing '_t' to convert the thumb url to large image url.
        imageUrls.add(imageUrl);
      }


    } catch (HttpException e) {
      System.err.println("Fatal protocol violation: " + e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("Fatal transport error: " + e.getMessage());
      e.printStackTrace();
    } finally {
      // Release the connection.
      method.releaseConnection();
    }

    return imageUrls;
  }


  /**
   * Download the image from the given URL.
   *
   * @param url The image URL.
   * @return returns a image object wrapping URL, data and content-type. 
   */
  public Image downloadImage(String url) {
    HttpClient client = new HttpClient();
    HttpMethod method = new GetMethod(url);

    Image img = new Image(url);
    try {
      int statusCode = client.executeMethod(method);

      if (statusCode != HttpStatus.SC_OK) {
        System.err.println("Method failed: " + method.getStatusLine());
      }

      // Read the response body.
      byte[] bytes = IOUtils.toByteArray(method.getResponseBodyAsStream());
      img.setData(bytes);
      Header contentTypeHeader = method.getResponseHeader("Content-Type");
      img.setContentType(contentTypeHeader.getValue());

    } catch (IOException e) {
      LOG.error("Unable to download image for URL: '" + url + "'.", e);
    }

    return img;
  }

  @Override
  public Blob getBlob() {
    Blob blob = null;

    List<String> imgUrls = searchImages(getSearchTerm());

    if (!imgUrls.isEmpty()) {

      // We want a random image here
      Collections.shuffle(imgUrls);
      String imgUrl = imgUrls.get(0);

      Image image = downloadImage(imgUrl);


      try {
        blob = blobService.fromBytes(image.getData(), image.getContentType());

      } catch (MimeTypeParseException e) {
        LOG.error("Error during blob creation.", e);
      }

    } else {
      return null;
    }

    return blob;
  }


  public BlobService getBlobService() {
    return blobService;
  }

  public void setBlobService(BlobService blobService) {
    this.blobService = blobService;
  }

  public String getSearchTerm() {
    return searchTerm;
  }

  public void setSearchTerm(String searchTerm) {
    this.searchTerm = searchTerm;
  }


  /**
   * Inner class for image objects.
   * The image object wraps the image URL, data and content-type.
   */
  private class Image {

    private String url;
    private byte[] data;
    private String contentType;


    public Image(String url) {
      this.url = url;
    }

    public boolean isLoaded() {
      return data.length != 0;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public byte[] getData() {
      return data;
    }

    public void setData(byte[] data) {
      this.data = data;
    }

    public String getContentType() {
      return contentType;
    }

    public void setContentType(String contentType) {
      this.contentType = contentType;
    }
  }

}
