/*
 * Jalview - A Sequence Alignment Editor and Viewer (Version 2.8)
 * Copyright (C) 2012 J Procter, AM Waterhouse, LM Lui, J Engelhardt, G Barton, M Clamp, S Searle
 * 
 * This file is part of Jalview.
 * 
 * Jalview is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * Jalview is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
 * PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Jalview.  If not, see <http://www.gnu.org/licenses/>.
 */
package jalview.bin;

import jalview.ws.dbsources.das.api.DasSourceRegistryI;
import jalview.ws.dbsources.das.datamodel.DasSourceRegistry;

import java.awt.Color;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.*;

/**
 * Stores and retrieves Jalview Application Properties Lists and fields within
 * list entries are separated by '|' symbols unless otherwise stated (|) clauses
 * are alternative values for a tag. <br>
 * <br>
 * Current properties include:
 * <ul>
 * <br>
 * logs.Axis.Level - one of the stringified Levels for log4j controlling the
 * logging level for axis (used for web services) <br>
 * </li>
 * <li>logs.Castor.Level - one of the stringified Levels for log4j controlling
 * the logging level for castor (used for serialization) <br>
 * </li>
 * <li>logs.Jalview.Level - Cache.log stringified level. <br>
 * </li>
 * <li>SCREEN_WIDTH</li>
 * <li>SCREEN_HEIGHT</li>
 * <li>SCREEN_Y=285</li>
 * <li>SCREEN_X=371</li>
 * <li>SHOW_FULLSCREEN boolean</li>
 * <li>FONT_NAME java font name for alignment text display</li>
 * <li>FONT_SIZE size of displayed alignment text</li>
 * <li>FONT_STYLE style of font displayed (sequence labels are always italic)</li>
 * <li>GAP_SYMBOL character to treat as gap symbol (usually -,.,' ')</li>
 * <li>LAST_DIRECTORY last directory for browsing alignment</li>
 * <li>USER_DEFINED_COLOURS list of user defined colour scheme files</li>
 * <li>SHOW_FULL_ID show id with '/start-end' numbers appended</li>
 * <li>SHOW_IDENTITY show percentage identity annotation</li>
 * <li>SHOW_QUALITY show alignment quality annotation</li>
 * <li>SHOW_ANNOTATIONS show alignment annotation rows</li>
 * <li>SHOW_CONSERVATION show alignment conservation annotation</li>
 * <li>CENTRE_COLUMN_LABELS centre the labels at each column in a displayed
 * annotation row</li>
 * <li>DEFAULT_COLOUR default colour scheme to apply for a new alignment</li>
 * <li>DEFAULT_FILE_FORMAT file format used to save</li>
 * <li>STARTUP_FILE file loaded on startup (may be a fully qualified url)</li>
 * <li>SHOW_STARTUP_FILE flag to control loading of startup file</li>
 * <li>VERSION the version of the jalview build</li>
 * <li>BUILD_DATE date of this build</li>
 * <li>LATEST_VERSION the latest jalview version advertised on the
 * www.jalview.org</li>
 * <li>PIR_MODELLER boolean indicating if PIR files are written with MODELLER
 * descriptions</li>
 * <li>(FASTA,MSF,PILEUP,CLUSTAL,BLC,PIR,PFAM)_JVSUFFIX boolean for adding jv
 * suffix to file</li>
 * <li>RECENT_URL list of recently retrieved URLs</li>
 * <li>RECENT_FILE list of recently opened files</li>
 * <li>USE_PROXY flag for whether a http proxy is to be used</li>
 * <li>PROXY_SERVER the proxy</li>
 * <li>PROXY_PORT</li>
 * <li>NOQUESTIONNAIRES true to prevent jalview from checking the questionnaire
 * service</li>
 * <li>QUESTIONNAIRE last questionnaire:responder id string from questionnaire
 * service</li>
 * <li>USAGESTATS (false - user prompted) Enable google analytics tracker for
 * collecting usage statistics</li>
 * <li>DAS_LOCAL_SOURCE list of local das sources</li>
 * <li>SHOW_OVERVIEW boolean for overview window display</li>
 * <li>ANTI_ALIAS boolean for smooth fonts</li>
 * <li>RIGHT_ALIGN_IDS boolean</li>
 * <li>AUTO_CALC_CONSENSUS boolean for automatic recalculation of consensus</li>
 * <li>PAD_GAPS boolean</li>
 * <li>ID_ITALICS boolean</li>
 * <li>SHOW_JV_SUFFIX</li>
 * <li>WRAP_ALIGNMENT</li>
 * <li>EPS_RENDERING (Prompt each time|Lineart|Text) default for EPS rendering
 * style check</li>
 * <li>SORT_ALIGNMENT (No sort|Id|Pairwise Identity)</li>
 * <li>SEQUENCE_LINKS list of name|URL pairs for opening a url with
 * $SEQUENCE_ID$</li>
 * <li>GROUP_LINKS list of name|URL[|&lt;separator&gt;] tuples - see
 * jalview.utils.GroupURLLink for more info</li>
 * <li>DAS_REGISTRY_URL the registry to query</li>
 * <li>DEFAULT_BROWSER for unix</li>
 * <li>DAS_ACTIVE_SOURCE list of active sources</li>
 * <li>SHOW_MEMUSAGE boolean show memory usage and warning indicator on desktop
 * (false)</li>
 * <li>VERSION_CHECK (true) check for the latest release version from
 * www.jalview.org (or the alias given by the www.jalview.org property)</li>
 * <li>SHOW_NPFEATS_TOOLTIP (true) show non-positional features in the Sequence
 * ID tooltip</li>
 * <li>SHOW_DBREFS_TOOLTIP (true) show Database Cross References in the Sequence
 * ID tooltip</li>
 * <li>SHOW_UNCONSERVED (false) only render unconserved residues - conserved
 * displayed as '.'</li>
 * <li>SORT_BY_TREE (false) sort the current alignment view according to the
 * order of a newly displayed tree</li>
 * <li>DBFETCH_USEPICR (false) use PICR to recover valid DB references from
 * sequence ID strings before attempting retrieval from any datasource</li>
 * <li>SHOW_GROUP_CONSENSUS (false) Show consensus annotation for groups in the
 * alignment.</li>
 * <li>SHOW_GROUP_CONSERVATION (false) Show conservation annotation for groups
 * in the alignment.</li>
 * <li>SHOW_CONSENSUS_HISTOGRAM (false) Show consensus annotation row's
 * histogram.</li>
 * <li>SHOW_CONSENSUS_LOGO (false) Show consensus annotation row's sequence
 * logo.</li>
 * <li>NORMALISE_CONSENSUS_LOGO (false) Show consensus annotation row's sequence
 * logo normalised to row height rather than histogram height.</li>
 * <li>FOLLOW_SELECTIONS (true) Controls whether a new alignment view should
 * respond to selections made in other alignments containing the same sequences.
 * </li>
 * <li>SHOW_WSDISCOVERY_ERRORS (true) Controls if the web service URL discovery
 * warning dialog box is displayed.</li>
 * <li>ANNOTATIONCOLOUR_MIN (orange) Shade used for minimum value of annotation
 * when shading by annotation</li>
 * <li>ANNOTATIONCOLOUR_MAX (red) Shade used for maximum value of annotation
 * when shading by annotation</li>
 * <li>www.jalview.org (http://www.jalview.org) a property enabling all HTTP
 * requests to be redirected to a mirror of http://www.jalview.org</li>
 * 
 * <li>FIGURE_AUTOIDWIDTH (false) Expand the left hand column of an exported
 * alignment figure to accommodate even the longest sequence ID or annotation
 * label.</li>
 * <li>FIGURE_FIXEDIDWIDTH Specifies the width to use for the left-hand column
 * when exporting an alignment as a figure (setting FIGURE_AUTOIDWIDTH to true
 * will override this).</li>
 * <li></li>
 * 
 * </ul>
 * Deprecated settings:
 * <ul>
 * *
 * <li>DISCOVERY_START - Boolean - controls if discovery services are queried on
 * startup (JWS1 services only)</li>
 * <li>DISCOVERY_URLS - comma separated list of Discovery Service endpoints.
 * (JWS1 services only)</li>
 * <li>SHOW_JWS1_SERVICES (true) enable or disable the original Jalview 2
 * services in the desktop GUI</li>
 * <li>ENABLE_RSBS_EDITOR (false for 2.7 release) enable or disable RSBS editing
 * panel in web service preferences</li>
 * </ul>
 * 
 * @author $author$
 * @version $Revision$
 */
public class Cache
{
  /**
   * property giving log4j level for CASTOR loggers
   */
  public static final String CASTORLOGLEVEL = "logs.Castor.level";

  /**
   * property giving log4j level for AXIS loggers
   */
  public static final String AXISLOGLEVEL = "logs.Axis.level";

  /**
   * property giving log4j level for Jalview Log
   */
  public static final String JALVIEWLOGLEVEL = "logs.Jalview.level";

  public static final String DAS_LOCAL_SOURCE = "DAS_LOCAL_SOURCE";

  public static final String DAS_REGISTRY_URL = "DAS_REGISTRY_URL";

  public static final String DAS_ACTIVE_SOURCE = "DAS_ACTIVE_SOURCE";

  /**
   * Initialises the Jalview Application Log
   */
  public static Logger log;

  /** Jalview Properties */
  public static Properties applicationProperties = new Properties();

  /** Default file is ~/.jalview_properties */
  static String propertiesFile;

  private static boolean propsAreReadOnly = false;

  public static void initLogger()
  {
    if (log != null)
    {
      return;
    }
    try
    {
      // TODO: redirect stdout and stderr here in order to grab the output of
      // the log

      ConsoleAppender ap = new ConsoleAppender(new SimpleLayout(),
              "System.err");
      ap.setName("JalviewLogger");
      org.apache.log4j.Logger.getRootLogger().addAppender(ap); // catch all for
      // log output
      Logger laxis = Logger.getLogger("org.apache.axis");
      Logger lcastor = Logger.getLogger("org.exolab.castor");
      jalview.bin.Cache.log = Logger.getLogger("jalview.bin.Jalview");

      laxis.setLevel(Level.toLevel(Cache.getDefault("logs.Axis.Level",
              Level.INFO.toString())));
      lcastor.setLevel(Level.toLevel(Cache.getDefault("logs.Castor.Level",
              Level.INFO.toString())));
      lcastor = Logger.getLogger("org.exolab.castor.xml");
      lcastor.setLevel(Level.toLevel(Cache.getDefault("logs.Castor.Level",
              Level.INFO.toString())));
      // lcastor = Logger.getLogger("org.exolab.castor.xml.Marshaller");
      // lcastor.setLevel(Level.toLevel(Cache.getDefault("logs.Castor.Level",
      // Level.INFO.toString())));
      jalview.bin.Cache.log.setLevel(Level.toLevel(Cache.getDefault(
              "logs.Jalview.level", Level.INFO.toString())));
      // laxis.addAppender(ap);
      // lcastor.addAppender(ap);
      // jalview.bin.Cache.log.addAppender(ap);
      // Tell the user that debug is enabled
      jalview.bin.Cache.log.debug("Jalview Debugging Output Follows.");
    } catch (Exception ex)
    {
      System.err.println("Problems initializing the log4j system\n");
      ex.printStackTrace(System.err);
    }
  }

  /** Called when Jalview is started */
  public static void loadProperties(String propsFile)
  {
    propertiesFile = propsFile;
    if (propsFile == null)
    {
      propertiesFile = System.getProperty("user.home") + File.separatorChar
              + ".jalview_properties";
    }
    else
    {
      // don't corrupt the file we've been given.
      propsAreReadOnly = true;
    }

    try
    {
      InputStream fis;
      try
      {
        fis = new java.net.URL(propertiesFile).openStream();
        System.out.println("Loading jalview properties from : "
                + propertiesFile);
        System.out
                .println("Disabling Jalview writing to user's local properties file.");
        propsAreReadOnly = true;

      } catch (Exception ex)
      {
        fis = null;
      }
      if (fis == null)
      {
        fis = new FileInputStream(propertiesFile);
      }
      applicationProperties.load(fis);
      applicationProperties.remove("LATEST_VERSION");
      applicationProperties.remove("VERSION");
      fis.close();
    } catch (Exception ex)
    {
      System.out.println("Error reading properties file: " + ex);
    }

    if (getDefault("USE_PROXY", false))
    {
      String proxyServer = getDefault("PROXY_SERVER", ""), proxyPort = getDefault(
              "PROXY_PORT", "8080");

      System.out.println("Using proxyServer: " + proxyServer
              + " proxyPort: " + proxyPort);

      System.setProperty("http.proxyHost", proxyServer);
      System.setProperty("http.proxyPort", proxyPort);
    }

    // LOAD THE AUTHORS FROM THE authors.props file
    try
    {
//      String authorDetails = "jar:".concat(Cache.class
//              .getProtectionDomain().getCodeSource().getLocation()
//              .toString().concat("!/authors.props"));
      URL resource = Cache.class.getResource("/authors.props");
     // java.net.URL localJarFileURL = new java.net.URL(authorDetails);

      //InputStream in = localJarFileURL.openStream();
      InputStream in = resource.openStream();
      applicationProperties.load(in);
      in.close();
    } catch (Exception ex)
    {
      System.out.println("Error reading author details: " + ex);
      applicationProperties.remove("AUTHORS");
      applicationProperties.remove("AUTHORFNAMES");
      applicationProperties.remove("YEAR");
    }

    // FIND THE VERSION NUMBER AND BUILD DATE FROM jalview.jar
    // MUST FOLLOW READING OF LOCAL PROPERTIES FILE AS THE
    // VERSION MAY HAVE CHANGED SINCE LAST USING JALVIEW
    try
    {
     // String buildDetails = "/.build_properties";
          URL resource = Cache.class.getResource("/jalview_en.properties");
     // java.net.URL localJarFileURL = new java.net.URL(buildDetails);
      //FileInputStream in = new FileInputStream(new File("/.build_properties"));
      //InputStream in = localJarFileURL.openStream();
      InputStream in = resource.openStream();
      applicationProperties.load(in);
      in.close();
    } catch (Exception ex)
    {
      System.out.println("Error reading build details: " + ex);
      applicationProperties.remove("VERSION");
    }

    String jnlpVersion = System.getProperty("jalview.version");
    String codeVersion = getProperty("VERSION");

    if (codeVersion == null)
    {
      // THIS SHOULD ONLY BE THE CASE WHEN TESTING!!
      codeVersion = "2.8";
      jnlpVersion = "2.8";
    }

    System.out.println("Jalview Version: " + codeVersion);

    // jnlpVersion will be null if we're using InstallAnywhere
    // Dont do this check if running in headless mode
    if (jnlpVersion == null
            && getDefault("VERSION_CHECK", true)
            && (System.getProperty("java.awt.headless") == null || System
                    .getProperty("java.awt.headless").equals("false")))
    {

      class VersionChecker extends Thread
      {
        public void run()
        {
          String orgtimeout = System
                  .getProperty("sun.net.client.defaultConnectTimeout");
          if (orgtimeout == null)
          {
            orgtimeout = "30";
            System.out.println("# INFO: Setting default net timeout to "
                    + orgtimeout + " seconds.");
          }
          String jnlpVersion = null;
          try
          {
            System.setProperty("sun.net.client.defaultConnectTimeout",
                    "5000");
            java.net.URL url = new java.net.URL(Cache.getDefault(
                    "www.jalview.org", "http://www.jalview.org")
                    + "/webstart/jalview.jnlp");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            String line = null;
            while ((line = in.readLine()) != null)
            {
              if (line.indexOf("jalview.version") == -1)
              {
                continue;
              }

              line = line.substring(line.indexOf("value=") + 7);
              line = line.substring(0, line.lastIndexOf("\""));
              jnlpVersion = line;
              break;
            }
          } catch (Exception ex)
          {
            System.out
                    .println("Non-fatal exceptions when checking version at www.jalview.org :");
            System.out.println(ex);
            jnlpVersion = getProperty("VERSION");
          }
          System.setProperty("sun.net.client.defaultConnectTimeout",
                  orgtimeout);

          setProperty("LATEST_VERSION", jnlpVersion);
        }
      }

      VersionChecker vc = new VersionChecker();
      vc.start();
    }
    else
    {
      if (jnlpVersion != null)
      {
        setProperty("LATEST_VERSION", jnlpVersion);
      }
      else
      {
        applicationProperties.remove("LATEST_VERSION");
      }
    }

    setProperty("VERSION", codeVersion);

    // LOAD USERDEFINED COLOURS
    jalview.gui.UserDefinedColours
            .initUserColourSchemes(getProperty("USER_DEFINED_COLOURS"));
    jalview.io.PIRFile.useModellerOutput = Cache.getDefault("PIR_MODELLER",
            false);
  }

  /**
   * Gets Jalview application property of given key. Returns null if key not
   * found
   * 
   * @param key
   *          Name of property
   * 
   * @return Property value
   */
  public static String getProperty(String key)
  {
    return applicationProperties.getProperty(key);
  }

  /**
   * These methods are used when checking if the saved preference is different
   * to the default setting
   */

  public static boolean getDefault(String property, boolean def)
  {
    String string = getProperty(property);
    if (string != null)
    {
      def = Boolean.valueOf(string).booleanValue();
    }

    return def;
  }

  /**
   * These methods are used when checking if the saved preference is different
   * to the default setting
   */
  public static String getDefault(String property, String def)
  {
    String string = getProperty(property);
    if (string != null)
    {
      return string;
    }

    return def;
  }

  /**
   * Stores property in the file "HOME_DIR/.jalview_properties"
   * 
   * @param key
   *          Name of object
   * @param obj
   *          String value of property
   * 
   * @return String value of property
   */
  public static String setProperty(String key, String obj)
  {

    try
    {
      applicationProperties.setProperty(key, obj);
      if (!propsAreReadOnly)
      {
        FileOutputStream out = new FileOutputStream(propertiesFile);
        applicationProperties.store(out, "---JalviewX Properties File---");
        out.close();
      }
    } catch (Exception ex)
    {
      System.out.println("Error setting property: " + key + " " + obj
              + "\n" + ex);
    }
    return obj;
  }

  /**
   * remove the specified property from the jalview properties file
   * 
   * @param string
   */
  public static void removeProperty(String string)
  {
    applicationProperties.remove(string);
    saveProperties();
  }

  /**
   * save the properties to the jalview properties path
   */
  public static void saveProperties()
  {
    if (!propsAreReadOnly)
    {
      try
      {
        FileOutputStream out = new FileOutputStream(propertiesFile);
        applicationProperties.store(out, "---JalviewX Properties File---");
        out.close();
      } catch (Exception ex)
      {
        System.out.println("Error saving properties: " + ex);
      }
    }
  }

  /**
   * internal vamsas class discovery state
   */
  private static int vamsasJarsArePresent = -1;

  /**
   * Searches for vamsas client classes on class path.
   * 
   * @return true if vamsas client is present on classpath
   */
  public static boolean vamsasJarsPresent()
  {
    if (vamsasJarsArePresent == -1)
    {
      try
      {
        if (jalview.jbgui.GDesktop.class.getClassLoader().loadClass(
                "uk.ac.vamsas.client.VorbaId") != null)
        {
          jalview.bin.Cache.log
                  .debug("Found Vamsas Classes (uk.ac..vamsas.client.VorbaId can be loaded)");
          vamsasJarsArePresent = 1;
          Logger lvclient = Logger.getLogger("uk.ac.vamsas");
          lvclient.setLevel(Level.toLevel(Cache.getDefault(
                  "logs.Vamsas.Level", Level.INFO.toString())));

          lvclient.addAppender(log.getAppender("JalviewLogger"));
          // Tell the user that debug is enabled
          lvclient.debug("Jalview Vamsas Client Debugging Output Follows.");
        }
      } catch (Exception e)
      {
        vamsasJarsArePresent = 0;
        jalview.bin.Cache.log.debug("Vamsas Classes are not present");
      }
    }
    return (vamsasJarsArePresent > 0);
  }

  /**
   * internal vamsas class discovery state
   */
  private static int groovyJarsArePresent = -1;

  /**
   * Searches for vamsas client classes on class path.
   * 
   * @return true if vamsas client is present on classpath
   */
  public static boolean groovyJarsPresent()
  {
    if (groovyJarsArePresent == -1)
    {
      try
      {
        if (Cache.class.getClassLoader().loadClass(
                "groovy.lang.GroovyObject") != null)
        {
          jalview.bin.Cache.log
                  .debug("Found Groovy (groovy.lang.GroovyObject can be loaded)");
          groovyJarsArePresent = 1;
          Logger lgclient = Logger.getLogger("groovy");
          lgclient.setLevel(Level.toLevel(Cache.getDefault(
                  "logs.Groovy.Level", Level.INFO.toString())));

          lgclient.addAppender(log.getAppender("JalviewLogger"));
          // Tell the user that debug is enabled
          lgclient.debug("Jalview Groovy Client Debugging Output Follows.");
        }
      } catch (Error e)
      {
        groovyJarsArePresent = 0;
        jalview.bin.Cache.log.debug("Groovy Classes are not present", e);
      } catch (Exception e)
      {
        groovyJarsArePresent = 0;
        jalview.bin.Cache.log.debug("Groovy Classes are not present");
      }
    }
    return (groovyJarsArePresent > 0);
  }

  /**
   * GA tracker object - actually JGoogleAnalyticsTracker null if tracking not
   * enabled.
   */
  protected static Object tracker = null;

  protected static Class trackerfocus = null;

  protected static Class jgoogleanalyticstracker = null;

  /**
   * Initialise the google tracker if it is not done already.
   */
  public static void initGoogleTracker()
  {
    if (tracker == null)
    {
      if (jgoogleanalyticstracker == null)
      {
        // try to get the tracker class
        try
        {
          jgoogleanalyticstracker = Cache.class
                  .getClassLoader()
                  .loadClass(
                          "com.boxysystems.jgoogleanalytics.JGoogleAnalyticsTracker");
          trackerfocus = Cache.class.getClassLoader().loadClass(
                  "com.boxysystems.jgoogleanalytics.FocusPoint");
        } catch (Exception e)
        {
          log.debug("com.boxysystems.jgoogleanalytics package is not present - tracking not enabled.");
          tracker = null;
          jgoogleanalyticstracker = null;
          trackerfocus = null;
          return;
        }
      }
      // now initialise tracker
      Exception re = null, ex = null;
      Error err = null;
      String vrs = "No Version Accessible";
      try
      {
        // Google analytics tracking code for Library Finder
        tracker = jgoogleanalyticstracker.getConstructor(new Class[]
        { String.class, String.class, String.class }).newInstance(
                new Object[]
                {
                    "Jalview Desktop",
                    (vrs = jalview.bin.Cache.getProperty("VERSION")
                            + "_"
                            + jalview.bin.Cache.getDefault("BUILD_DATE",
                                    "unknown")), "UA-9060947-1" });
        jgoogleanalyticstracker.getMethod("trackAsynchronously",
                new Class[]
                { trackerfocus }).invoke(tracker, new Object[]
        { trackerfocus.getConstructor(new Class[]
        { String.class }).newInstance(new Object[]
        { "Application Started." }) });
      } catch (RuntimeException e)
      {
        re = e;
      } catch (Exception e)
      {
        ex = e;
      } catch (Error e)
      {
        err = e;
      }
      if (re != null || ex != null || err != null)
      {
        if (log != null)
        {
          if (re != null)
            log.debug("Caught runtime exception in googletracker init:", re);
          if (ex != null)
            log.warn(
                    "Failed to initialise GoogleTracker for Jalview Desktop with version "
                            + vrs, ex);
          if (err != null)
            log.error(
                    "Whilst initing GoogleTracker for Jalview Desktop version "
                            + vrs, err);
        }
        else
        {
          if (re != null)
          {
            System.err
                    .println("Debug: Caught runtime exception in googletracker init:"
                            + vrs);
            re.printStackTrace();
          }
          if (ex != null)
          {
            System.err
                    .println("Warning:  Failed to initialise GoogleTracker for Jalview Desktop with version "
                            + vrs);
            ex.printStackTrace();
          }

          if (err != null)
          {
            System.err
                    .println("ERROR: Whilst initing GoogleTracker for Jalview Desktop version "
                            + vrs);
            err.printStackTrace();
          }
        }
      }
      else
      {
        log.debug("Successfully initialised tracker.");
      }
    }
  }

  /**
   * get the user's default colour if available
   * 
   * @param property
   * @param defcolour
   * @return
   */
  public static Color getDefaultColour(String property, Color defcolour)
  {
    String colprop = getProperty(property);
    if (colprop == null)
    {
      return defcolour;
    }
    Color col = jalview.schemes.ColourSchemeProperty
            .getAWTColorFromName(colprop);
    if (col == null)
    {
      try
      {
        col = new jalview.schemes.UserColourScheme(colprop).findColour('A');
      } catch (Exception ex)
      {
        log.warn("Couldn't parse '" + colprop + "' as a colour for "
                + property);
        col = null;
      }
    }
    return (col == null) ? defcolour : col;
  }

  /**
   * store a colour as a Jalview user default property
   * 
   * @param property
   * @param colour
   */
  public static void setColourProperty(String property, Color colour)
  {
    setProperty(property, jalview.util.Format.getHexString(colour));
  }

  public static final DateFormat date_format = SimpleDateFormat
          .getDateTimeInstance();

  /**
   * store a date in a jalview property
   * 
   * @param string
   * @param time
   */
  public static void setDateProperty(String property, Date time)
  {
    setProperty(property, date_format.format(time));
  }

  /**
   * read a date stored in a jalview property
   * 
   * @param property
   * @return valid date as stored by setDateProperty, or null
   * 
   */
  public static Date getDateProperty(String property)
  {
    String val = getProperty(property);
    if (val != null)
    {
      try
      {
        return date_format.parse(val);
      } catch (Exception ex)
      {
        System.err.println("Invalid or corrupt date in property '"
                + property + "' : value was '" + val + "'");
      }
    }
    return null;
  }

  /**
   * get and parse a property as an integer. send any parsing problems to
   * System.err
   * 
   * @param property
   * @return null or Integer
   */
  public static Integer getIntegerProperty(String property)
  {
    String val = getProperty(property);
    if (val != null && (val = val.trim()).length() > 0)
    {
      try
      {
        return Integer.valueOf(val);
      } catch (NumberFormatException x)
      {
        System.err.println("Invalid integer in property '" + property
                + "' (value was '" + val + "')");
      }
    }
    return null;
  }

  private static DasSourceRegistryI sourceRegistry = null;

  /**
   * initialise and ..
   * 
   * @return instance of the das source registry
   */
  public static DasSourceRegistryI getDasSourceRegistry()
  {
    if (sourceRegistry == null)
    {
      sourceRegistry = new DasSourceRegistry();
    }
    return sourceRegistry;
  }
}
