package com.coremedia.contribution.contentcreator;

import com.coremedia.cap.Cap;
import com.coremedia.cap.common.CapConnection;
import com.coremedia.cap.content.Content;
import com.coremedia.cmdline.AbstractUAPIClient;
import com.coremedia.contribution.contentcreator.strategy.ContentCreationStrategy;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.Console;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * This client creates content for the given type in the given repository folder.
 */
public class ContentCreator extends AbstractUAPIClient {

  private static CapConnection uapiConnection;
  private static Log logger;

  private final String springConfigLocations = "config/createcontent/spring/*.xml";

  /*
   * Default values
   */
  private static final String DEFAULT_BASE_FOLDER = "/Sites/GeneratedContent";
  private static final int DEFAULT_NUMBER_OF_COPIES = 1;

  private static final int VERIFY_COPIES_COUNT = 1000;

  /*
   * Option definitions
   */
  private static final String OPTION_CONTENT_TYPE             = "t";
  private static final String OPTION_CONTENT_TYPE_LONG        = "type";
  private static final String OPTION_CONTENT_TYPE_DESCR       = "Content type";
  private static final String OPTION_BASE_FOLDER              = "f";
  private static final String OPTION_BASE_FOLDER_LONG         = "folder";
  private static final String OPTION_BASE_FOLDER_DESCR        = "Base folder for the created content";
  private static final String OPTION_NUMBER_OF_COPIES         = "c";
  private static final String OPTION_NUMBER_OF_COPIES_LONG    = "copies";
  private static final String OPTION_NUMBER_OF_COPIES_DESCR   = "Number of copies";
  private static final String OPTION_SOURCE_FOLDER            = "s";
  private static final String OPTION_SOURCE_FOLDER_LONG       = "source";
  private static final String OPTION_SOURCE_FOLDER_DESCR      = "Source folder of the original content that shell be created";

  private String baseFolder;
  private String contentType;
  private int numberOfCopies;
  private StrategiesHolder strategiesHolder;
  private String sourceFolder;


  /*
   * Time measuring stuff
   */
  private long startTime;


  @Override
  protected void fillInOptions(Options options) {
    options.addOption(OPTION_CONTENT_TYPE,  OPTION_CONTENT_TYPE_LONG,  true, OPTION_CONTENT_TYPE_DESCR);
    options.addOption(OPTION_BASE_FOLDER,   OPTION_BASE_FOLDER_LONG,   true, OPTION_BASE_FOLDER_DESCR);
    options.addOption(OPTION_NUMBER_OF_COPIES, OPTION_NUMBER_OF_COPIES_LONG, true, OPTION_NUMBER_OF_COPIES_DESCR);
    options.addOption(OPTION_SOURCE_FOLDER, OPTION_SOURCE_FOLDER_LONG, true, OPTION_SOURCE_FOLDER_DESCR);
  }

  @Override
  protected String getUsage() {
    return "cm createcontent -u <user> [other options]"
            + " -" + OPTION_CONTENT_TYPE      + " <" + OPTION_CONTENT_TYPE_LONG     + "> "
            + "[-" + OPTION_BASE_FOLDER       + " <" + OPTION_BASE_FOLDER_LONG      + ">] "
            + "[-" + OPTION_NUMBER_OF_COPIES  + " <" + OPTION_NUMBER_OF_COPIES_LONG + ">] "
            + "[-" + OPTION_SOURCE_FOLDER     + " <" + OPTION_SOURCE_FOLDER_LONG    + ">] ";
  }

  @Override
  protected boolean parseCommandLine(CommandLine commandLine) {
    // Parse content type
    contentType = commandLine.getOptionValue(OPTION_CONTENT_TYPE);
    if (contentType == null || contentType.equals("")) {
      getOut().error("Invalid content type specified!");
      return false;
    }

    // Parse base folder
    baseFolder = DEFAULT_BASE_FOLDER;
    if (commandLine.hasOption(OPTION_BASE_FOLDER)) {
      baseFolder = commandLine.getOptionValue(OPTION_BASE_FOLDER);
    }

    // Parse number of copies
    numberOfCopies = DEFAULT_NUMBER_OF_COPIES;
    if (commandLine.hasOption(OPTION_NUMBER_OF_COPIES)) {
      numberOfCopies = Integer.decode(commandLine.getOptionValue(OPTION_NUMBER_OF_COPIES));
    }

    // Parse source folder
    sourceFolder = "";
    if (commandLine.hasOption(OPTION_SOURCE_FOLDER)) {
      sourceFolder = commandLine.getOptionValue(OPTION_SOURCE_FOLDER);
    }

    return true;
  }

  @Override
  protected void fillInConnectionParameters(Map params) {
    super.fillInConnectionParameters(params);
    params.put(Cap.USE_WORKFLOW, "false");
  }
  
  @Override
  protected void run() throws Exception {
    uapiConnection = getContentRepository().getConnection();
    logger = getOut();

    // Load spring application context
    ApplicationContext ctx = new FileSystemXmlApplicationContext(springConfigLocations);

    // Load all strategies
    strategiesHolder = (StrategiesHolder) ctx.getBean("strategiesHolder");
    
    // Verify number of copies
    if (numberOfCopies >= VERIFY_COPIES_COUNT) {
      Console console = System.console();

      String input = console.readLine(
          "Wow, seems like you want to create a lot of stuff here." +
          "\n  Are you sure you want to create " + numberOfCopies + " documents of type " + contentType +
          "\n  in folder '" + baseFolder + "'" +
          "\n  from source folder '" + sourceFolder + "'?" +
          "\n  Type 'yes' to proceed. [no] ");
      if (!input.toLowerCase().equals("yes") && !input.toLowerCase().equals("y")) {
        System.exit(0);
      }

    }


    startTime = System.currentTimeMillis();

    // Find the matching creation strategy
    ContentCreationStrategy contentCreationStrategy = strategiesHolder.getStrategyForContentType(contentType);
    if (contentCreationStrategy == null) {
      // Use the default creation strategy
      getLogger().error("No content creation strategy defined.");
      System.exit(1);
    }

    contentCreationStrategy.getFolderStrategy().setBaseFolder(baseFolder);
    contentCreationStrategy.getFolderStrategy().setTotalCopies(numberOfCopies);
    if (contentCreationStrategy.getSourceFolderStrategy() != null) {
      contentCreationStrategy.getSourceFolderStrategy().setBaseFolder(sourceFolder);
    }


    int copiesSoFar = 0;
    getOut().info("------------------------------------------------------------------------");
    getOut().info("Starting content creation for type " + contentType + "." +
        "\n  Base folder:\t" + baseFolder +
        "\n  Copies:\t" + numberOfCopies +
        "\n  Source folder:\t" + sourceFolder);
    getOut().info("------------------------------------------------------------------------");

    while (copiesSoFar < numberOfCopies) {
      Content createdContent = contentCreationStrategy.createContent();
      if (createdContent != null) {
        getOut().info("  Content " + createdContent.getId() + " - '" + createdContent.getPath() + "' created.");
      }

      copiesSoFar++;
    }

    long endTime = System.currentTimeMillis();

    getOut().info("------------------------------------------------------------------------");
    getOut().info("Content creation finished. [" + calculateElapsedTime(startTime, endTime) + "]");
  }

  private String calculateElapsedTime(long start, long end) {
    StringBuilder time = new StringBuilder();

    long elapsedTime = end - start;

    long days    = TimeUnit.MILLISECONDS.toDays(elapsedTime);
    long hours   = TimeUnit.MILLISECONDS.toHours(elapsedTime - TimeUnit.DAYS.toMillis(days));
    long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime - TimeUnit.DAYS.toMillis(days) - TimeUnit.HOURS.toMillis(hours));
    long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime - TimeUnit.DAYS.toMillis(days) - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes));
    long milliseconds = TimeUnit.MILLISECONDS.toMillis(elapsedTime - TimeUnit.DAYS.toMillis(days) - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes) - TimeUnit.SECONDS.toMillis(seconds));

    /*
    getOut().debug("Elapsed time: " + elapsedTime + "ms");
    getOut().debug("  " + days + " days");
    getOut().debug("  " + hours + " h");
    getOut().debug("  " + minutes + " m");
    getOut().debug("  " + seconds + " s");
    getOut().debug("  " + milliseconds + " ms");
    */

    // Build time string
    time.append(days > 0 ? days + "days " : "");
    if (hours > 0) {
      time.append(hours);
      time.append(":");
    }
    if (minutes > 0) {
      time.append(minutes);
      time.append(":");
    }
    if (seconds > 0) {
      time.append(seconds);
      if (milliseconds > 0) {
        time.append(".");
        time.append(milliseconds);
      }
      time.append("s");
    } else {
      time.append(milliseconds);
      time.append("ms");
    }

    return time.toString();
  }

  public static void main(String[] args) {
    main(new ContentCreator(), args);
  }

  /**
   * Returns the UAPI-Connection.
   * @return CapConnection
   */
  public static CapConnection getUapiConnection() {
    return uapiConnection;
  }

  /**
   * Returns the logger instance.
   * @return The logger instance.
   */
  public static Log getLogger() {
    return logger;
  }

}
