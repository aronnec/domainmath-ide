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
package jalview.ws.rest;

import jalview.datamodel.SequenceI;
import jalview.io.packed.DataProvider.JvDataType;
import jalview.ws.rest.params.Alignment;
import jalview.ws.rest.params.AnnotationFile;
import jalview.ws.rest.params.SeqGroupIndexVector;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestServiceDescription
{
  /**
   * create a new rest service description ready to be configured
   */
  public RestServiceDescription()
  {

  }

  /**
   * @param details
   * @param postUrl
   * @param urlSuffix
   * @param inputParams
   * @param hseparable
   * @param vseparable
   * @param gapCharacter
   */
  public RestServiceDescription(String action, String description,
          String name, String postUrl, String urlSuffix,
          Map<String, InputType> inputParams, boolean hseparable,
          boolean vseparable, char gapCharacter)
  {
    super();
    this.details = new UIinfo();
    details.Action = action == null ? "" : action;
    details.description = description == null ? "" : description;
    details.Name = name == null ? "" : name;
    this.postUrl = postUrl == null ? "" : postUrl;
    this.urlSuffix = urlSuffix == null ? "" : urlSuffix;
    if (inputParams != null)
    {
      this.inputParams = inputParams;
    }
    this.hseparable = hseparable;
    this.vseparable = vseparable;
    this.gapCharacter = gapCharacter;
  }

  public boolean equals(Object o)
  {
    if (o == null || !(o instanceof RestServiceDescription))
    {
      return false;
    }
    RestServiceDescription other = (RestServiceDescription) o;
    boolean diff = (gapCharacter != other.gapCharacter);
    diff |= vseparable != other.vseparable;
    diff |= hseparable != other.hseparable;
    diff |= !(urlSuffix.equals(other.urlSuffix));
    // TODO - robust diff that includes constants and reordering of URL
    // diff |= !(postUrl.equals(other.postUrl));
    // diff |= !inputParams.equals(other.inputParams);
    diff |= !details.Name.equals(other.details.Name);
    diff |= !details.Action.equals(other.details.Action);
    diff |= !details.description.equals(other.details.description);
    return !diff;
  }

  /**
   * Service UI Info { Action, Specific Name of Service, Brief Description }
   */

  public class UIinfo
  {
    public String getAction()
    {
      return Action;
    }

    public void setAction(String action)
    {
      Action = action;
    }

    public String getName()
    {
      return Name;
    }

    public void setName(String name)
    {
      Name = name;
    }

    public String getDescription()
    {
      return description;
    }

    public void setDescription(String description)
    {
      this.description = description;
    }

    String Action;

    String Name;

    String description;
  }

  public UIinfo details = new UIinfo();

  public String getAction()
  {
    return details.getAction();
  }

  public void setAction(String action)
  {
    details.setAction(action);
  }

  public String getName()
  {
    return details.getName();
  }

  public void setName(String name)
  {
    details.setName(name);
  }

  public String getDescription()
  {
    return details.getDescription();
  }

  public void setDescription(String description)
  {
    details.setDescription(description);
  }

  /**
   * Service base URL
   */
  String postUrl;

  public String getPostUrl()
  {
    return postUrl;
  }

  public void setPostUrl(String postUrl)
  {
    this.postUrl = postUrl;
  }

  public String getUrlSuffix()
  {
    return urlSuffix;
  }

  public void setUrlSuffix(String urlSuffix)
  {
    this.urlSuffix = urlSuffix;
  }

  public Map<String, InputType> getInputParams()
  {
    return inputParams;
  }

  public void setInputParams(Map<String, InputType> inputParams)
  {
    this.inputParams = inputParams;
  }

  public void setHseparable(boolean hseparable)
  {
    this.hseparable = hseparable;
  }

  public void setVseparable(boolean vseparable)
  {
    this.vseparable = vseparable;
  }

  public void setGapCharacter(char gapCharacter)
  {
    this.gapCharacter = gapCharacter;
  }

  /**
   * suffix that should be added to any url used if it does not already end in
   * the suffix.
   */
  String urlSuffix;

  /**
   * input info given as key/value pairs - mapped to post arguments
   */
  Map<String, InputType> inputParams = new HashMap<String, InputType>();

  /**
   * assigns the given inputType it to its corresponding input parameter token
   * it.token
   * 
   * @param it
   */
  public void setInputParam(InputType it)
  {
    inputParams.put(it.token, it);
  }

  /**
   * remove the given input type it from the set of service input parameters.
   * 
   * @param it
   */
  public void removeInputParam(InputType it)
  {
    inputParams.remove(it.token);
  }

  /**
   * service requests alignment data
   */
  boolean aligndata;

  /**
   * service requests alignment and/or seuqence annotationo data
   */
  boolean annotdata;

  /**
   * service requests partitions defined over input (alignment) data
   */
  boolean partitiondata;

  /**
   * process ths input data and set the appropriate shorthand flags describing
   * the input the service wants
   */
  public void setInvolvesFlags()
  {
    aligndata = inputInvolves(Alignment.class);
    annotdata = inputInvolves(AnnotationFile.class);
    partitiondata = inputInvolves(SeqGroupIndexVector.class);
  }

  /**
   * Service return info { alignment, annotation file (loaded back on to
   * alignment), tree (loaded back on to alignment), sequence annotation -
   * loaded back on to alignment), text report, pdb structures with sequence
   * mapping )
   * 
   */

  /**
   * Start with bare minimum: input is alignment + groups on alignment
   * 
   * @author JimP
   * 
   */

  private String invalidMessage = null;

  /**
   * parse the given linkString of the form '<label>|<url>|separator
   * char[|optional sequence separator char]' into parts. url may contain a
   * string $SEQUENCEIDS<=optional regex=>$ where <=optional regex=> must be of
   * the form =/<perl style regex>/=$ or $SEQUENCES<=optional regex=>$ or
   * $SEQUENCES<=optional regex=>$.
   * 
   * @param link
   */
  public RestServiceDescription(String link)
  {
    StringBuffer warnings = new StringBuffer();
    if (!configureFromEncodedString(link, warnings))
    {
      if (warnings.length() > 0)
      {
        invalidMessage = warnings.toString();
      }
    }
  }

  public RestServiceDescription(RestServiceDescription toedit)
  {
    // Rather then do the above, we cheat and use our human readable
    // serialization code to clone everything
    this(toedit.toString());
    /**
     * if (toedit == null) { return; } /** urlSuffix = toedit.urlSuffix; postUrl
     * = toedit.postUrl; hseparable = toedit.hseparable; vseparable =
     * toedit.vseparable; gapCharacter = toedit.gapCharacter; details = new
     * RestServiceDescription.UIinfo(); details.Action = toedit.details.Action;
     * details.description = toedit.details.description; details.Name =
     * toedit.details.Name; for (InputType itype: toedit.inputParams.values()) {
     * inputParams.put(itype.token, itype.clone());
     * 
     * }
     */
    // TODO Implement copy constructor NOW*/
  }

  /**
   * @return the invalidMessage
   */
  public String getInvalidMessage()
  {
    return invalidMessage;
  }

  /**
   * Check if URL string was parsed properly.
   * 
   * @return boolean - if false then <code>getInvalidMessage</code> returns an
   *         error message
   */
  public boolean isValid()
  {
    return invalidMessage == null;
  }

  private static boolean debug = false;

  /**
   * parse the string into a list
   * 
   * @param list
   * @param separator
   * @return elements separated by separator
   */
  public static String[] separatorListToArray(String list, String separator)
  {
    int seplen = separator.length();
    if (list == null || list.equals("") || list.equals(separator))
      return null;
    java.util.ArrayList<String> jv = new ArrayList<String>();
    int cp = 0, pos, escape;
    boolean wasescaped = false, wasquoted = false;
    String lstitem = null;
    while ((pos = list.indexOf(separator, cp)) >= cp)
    {

      escape = (pos > 0 && list.charAt(pos - 1) == '\\') ? -1 : 0;
      if (wasescaped || wasquoted)
      {
        // append to previous pos
        jv.set(jv.size() - 1,
                lstitem = lstitem + separator
                        + list.substring(cp, pos + escape));

      }
      else
      {
        jv.add(lstitem = list.substring(cp, pos + escape));
      }
      cp = pos + seplen;
      wasescaped = escape == -1;
      if (!wasescaped)
      {
        // last separator may be in an unmatched quote
        if (java.util.regex.Pattern.matches("('[^']*')*[^']*'", lstitem))
        {
          wasquoted = true;
        }
      }

    }
    if (cp < list.length())
    {
      String c = list.substring(cp);
      if (wasescaped || wasquoted)
      {
        // append final separator
        jv.set(jv.size() - 1, lstitem + separator + c);
      }
      else
      {
        if (!c.equals(separator))
        {
          jv.add(c);
        }
      }
    }
    if (jv.size() > 0)
    {
      String[] v = jv.toArray(new String[jv.size()]);
      jv.clear();
      if (debug)
      {
        System.err.println("Array from '" + separator
                + "' separated List:\n" + v.length);
        for (int i = 0; i < v.length; i++)
        {
          System.err.println("item " + i + " '" + v[i] + "'");
        }
      }
      return v;
    }
    if (debug)
    {
      System.err.println("Empty Array from '" + separator
              + "' separated List");
    }
    return null;
  }

  /**
   * concatenate the list with separator
   * 
   * @param list
   * @param separator
   * @return concatenated string
   */
  public static String arrayToSeparatorList(String[] list, String separator)
  {
    StringBuffer v = new StringBuffer();
    if (list != null && list.length > 0)
    {
      for (int i = 0, iSize = list.length; i < iSize; i++)
      {
        if (list[i] != null)
        {
          if (v.length() > 0)
          {
            v.append(separator);
          }
          // TODO - escape any separator values in list[i]
          v.append(list[i]);
        }
      }
      if (debug)
      {
        System.err.println("Returning '" + separator
                + "' separated List:\n");
        System.err.println(v);
      }
      return v.toString();
    }
    if (debug)
    {
      System.err.println("Returning empty '" + separator
              + "' separated List\n");
    }
    return "" + separator;
  }

  /**
   * parse a string containing a list of service properties and configure the
   * service description
   * 
   * @param propList
   *          param warnings a StringBuffer that any warnings about invalid
   *          content will be appended to.
   */
  private boolean configureFromServiceInputProperties(String propList,
          StringBuffer warnings)
  {
    String[] props = separatorListToArray(propList, ",");
    if (props == null)
    {
      return true;
    }
    ;
    boolean valid = true;
    String val = null;
    int l = warnings.length();
    int i;
    for (String prop : props)
    {
      if ((i = prop.indexOf("=")) > -1)
      {
        val = prop.substring(i + 1);
        if (val.startsWith("\'") && val.endsWith("\'"))
        {
          val = val.substring(1, val.length() - 1);
        }
        prop = prop.substring(0, i);
      }

      if (prop.equals("hseparable"))
      {
        hseparable = true;
      }
      if (prop.equals("vseparable"))
      {
        vseparable = true;
      }
      if (prop.equals("gapCharacter"))
      {
        if (val == null || val.length() == 0 || val.length() > 1)
        {
          valid = false;
          warnings.append((warnings.length() > 0 ? "\n" : "")
                  + ("Invalid service property: gapCharacter=' ' (single character) - was given '"
                          + val + "'"));
        }
        else
        {
          gapCharacter = val.charAt(0);
        }
      }
      if (prop.equals("returns"))
      {
        _configureOutputFormatFrom(val, warnings);
      }
    }
    // return true if valid is true and warning buffer was not appended to.
    return valid && (l == warnings.length());
  }

  private String _genOutputFormatString()
  {
    String buff = "";
    if (resultData == null)
    {
      return "";
    }
    for (JvDataType type : resultData)
    {
      if (buff.length() > 0)
      {
        buff += ";";
      }
      buff += type.toString();
    }
    return buff;
  }

  private void _configureOutputFormatFrom(String outstring,
          StringBuffer warnings)
  {
    if (outstring.indexOf(";") == -1)
    {
      // we add a token, for simplicity
      outstring = outstring + ";";
    }
    StringTokenizer st = new StringTokenizer(outstring, ";");
    String tok = "";
    resultData = new ArrayList<JvDataType>();
    while (st.hasMoreTokens())
    {
      try
      {
        resultData.add(JvDataType.valueOf(tok = st.nextToken()));
      } catch (NoSuchElementException x)
      {
        warnings.append("Invalid result type: '" + tok
                + "' (must be one of: ");
        String sep = "";
        for (JvDataType vl : JvDataType.values())
        {
          warnings.append(sep);
          warnings.append(vl.toString());
          sep = " ,";
        }
        warnings.append(" separated by semi-colons)\n");
      }
    }
  }

  private String getServiceIOProperties()
  {
    ArrayList<String> vls = new ArrayList<String>();
    if (isHseparable())
    {
      vls.add("hseparable");
    }
    ;
    if (isVseparable())
    {
      vls.add("vseparable");
    }
    ;
    vls.add(new String("gapCharacter='" + gapCharacter + "'"));
    vls.add(new String("returns='" + _genOutputFormatString() + "'"));
    return arrayToSeparatorList(vls.toArray(new String[0]), ",");
  }

  public String toString()
  {
    StringBuffer result = new StringBuffer();
    result.append("|");
    result.append(details.Name);
    result.append('|');
    result.append(details.Action);
    result.append('|');
    if (details.description != null)
    {
      result.append(details.description);
    }
    ;
    // list job input flags
    result.append('|');
    result.append(getServiceIOProperties());
    // list any additional cgi parameters needed for result retrieval
    if (urlSuffix != null && urlSuffix.length() > 0)
    {
      result.append('|');
      result.append(urlSuffix);
    }
    result.append('|');
    result.append(getInputParamEncodedUrl());
    return result.toString();
  }

  /**
   * processes a service encoded as a string (as generated by
   * RestServiceDescription.toString()) Note - this will only use the first
   * service definition encountered in the string to configure the service.
   * 
   * @param encoding
   * @param warnings
   *          - where warning messages are reported.
   * @return true if configuration was parsed successfully.
   */
  public boolean configureFromEncodedString(String encoding,
          StringBuffer warnings)
  {
    String[] list = separatorListToArray(encoding, "|");

    int nextpos = parseServiceList(list, warnings, 0);
    if (nextpos > 0)
    {
      return true;
    }
    return false;
  }

  /**
   * processes the given list from position p, attempting to configure the
   * service from it. Service lists are formed by concatenating individual
   * stringified services. The first character of a stringified service is '|',
   * enabling this, and the parser will ignore empty fields in a '|' separated
   * list when they fall outside a service definition.
   * 
   * @param list
   * @param warnings
   * @param p
   * @return
   */
  protected int parseServiceList(String[] list, StringBuffer warnings, int p)
  {
    boolean invalid = false;
    // look for the first non-empty position - expect it to be service name
    while (list[p] != null && list[p].trim().length() == 0)
    {
      p++;
    }
    details.Name = list[p];
    details.Action = list[p + 1];
    details.description = list[p + 2];
    invalid |= !configureFromServiceInputProperties(list[p + 3], warnings);
    if (list.length - p > 5 && list[p + 5] != null
            && list[p + 5].trim().length() > 5)
    {
      urlSuffix = list[p + 4];
      invalid |= !configureFromInputParamEncodedUrl(list[p + 5], warnings);
      p += 6;
    }
    else
    {
      if (list.length - p > 4 && list[p + 4] != null
              && list[p + 4].trim().length() > 5)
      {
        urlSuffix = null;
        invalid |= !configureFromInputParamEncodedUrl(list[p + 4], warnings);
        p += 5;
      }
    }
    return invalid ? -1 : p;
  }

  /**
   * @return string representation of the input parameters, their type and
   *         constraints, appended to the service's base submission URL
   */
  private String getInputParamEncodedUrl()
  {
    StringBuffer url = new StringBuffer();
    if (postUrl == null || postUrl.length() < 5)
    {
      return "";
    }

    url.append(postUrl);
    char appendChar = (postUrl.indexOf("?") > -1) ? '&' : '?';
    boolean consts = true;
    do
    {
      for (Map.Entry<String, InputType> param : inputParams.entrySet())
      {
        List<String> vals = param.getValue().getURLEncodedParameter();
        if (param.getValue().isConstant())
        {
          if (consts)
          {
            url.append(appendChar);
            appendChar = '&';
            url.append(param.getValue().token);
            if (vals.size() == 1)
            {
              url.append("=");
              url.append(vals.get(0));
            }
          }
        }
        else
        {
          if (!consts)
          {
            url.append(appendChar);
            appendChar = '&';
            url.append(param.getValue().token);
            url.append("=");
            // write parameter set as $TOKENPREFIX:csv list of params$ for this
            // input param
            url.append("$");
            url.append(param.getValue().getURLtokenPrefix());
            url.append(":");
            url.append(arrayToSeparatorList(vals.toArray(new String[0]),
                    ","));
            url.append("$");
          }
        }

      }
      // toggle consts and repeat until !consts is false:
    } while (!(consts = !consts));
    return url.toString();
  }

  /**
   * parse the service URL and input parameters from the given encoded URL
   * string and configure the RestServiceDescription from it.
   * 
   * @param ipurl
   * @param warnings
   *          where any warnings
   * @return true if URL parsed correctly. false means the configuration failed.
   */
  private boolean configureFromInputParamEncodedUrl(String ipurl,
          StringBuffer warnings)
  {
    boolean valid = true;
    int lastp = 0;
    String url = new String();
    Matcher prms = Pattern.compile("([?&])([A-Za-z0-9_]+)=\\$([^$]+)\\$")
            .matcher(ipurl);
    Map<String, InputType> iparams = new Hashtable<String, InputType>();
    InputType jinput;
    while (prms.find())
    {
      if (lastp < prms.start(0))
      {
        url += ipurl.substring(lastp, prms.start(0));
        lastp = prms.end(0) + 1;
      }
      String sep = prms.group(1);
      String tok = prms.group(2);
      String iprm = prms.group(3);
      int colon = iprm.indexOf(":");
      String iprmparams = "";
      if (colon > -1)
      {
        iprmparams = iprm.substring(colon + 1);
        iprm = iprm.substring(0, colon);
      }
      valid = parseTypeString(prms.group(0), tok, iprm, iprmparams,
              iparams, warnings);
    }
    if (valid)
    {
      try
      {
        URL u = new URL(url);
        postUrl = url;
        inputParams = iparams;
      } catch (Exception e)
      {
        warnings.append("Failed to parse '" + url + "' as a URL.\n");
        valid = false;
      }
    }
    return valid;
  }

  public static Class[] getInputTypes()
  {
    // TODO - find a better way of maintaining this classlist
    return new Class[]
    { jalview.ws.rest.params.Alignment.class,
        jalview.ws.rest.params.AnnotationFile.class,
        SeqGroupIndexVector.class,
        jalview.ws.rest.params.SeqIdVector.class,
        jalview.ws.rest.params.SeqVector.class,
        jalview.ws.rest.params.Tree.class };
  }

  public static boolean parseTypeString(String fullstring, String tok,
          String iprm, String iprmparams, Map<String, InputType> iparams,
          StringBuffer warnings)
  {
    boolean valid = true;
    InputType jinput;
    for (Class type : getInputTypes())
    {
      try
      {
        jinput = (InputType) (type.getConstructor().newInstance(null));
        if (iprm.equalsIgnoreCase(jinput.getURLtokenPrefix()))
        {
          ArrayList<String> al = new ArrayList<String>();
          for (String prprm : separatorListToArray(iprmparams, ","))
          {
            // hack to ensure that strings like "sep=','" containing unescaped
            // commas as values are concatenated
            al.add(prprm.trim());
          }
          if (!jinput.configureFromURLtokenString(al, warnings))
          {
            valid = false;
            warnings.append("Failed to parse '" + fullstring + "' as a "
                    + jinput.getURLtokenPrefix() + " input tag.\n");
          }
          else
          {
            jinput.token = tok;
            iparams.put(tok, jinput);
            valid = true;
          }
          break;
        }

      } catch (Throwable thr)
      {
      }
      ;
    }
    return valid;
  }

  /**
   * covenience method to generate the id and sequence string vector from a set
   * of seuqences using each sequence's getName() and getSequenceAsString()
   * method
   * 
   * @param seqs
   * @return String[][] {{sequence ids},{sequence strings}}
   */
  public static String[][] formStrings(SequenceI[] seqs)
  {
    String[][] idset = new String[2][seqs.length];
    for (int i = 0; i < seqs.length; i++)
    {
      idset[0][i] = seqs[i].getName();
      idset[1][i] = seqs[i].getSequenceAsString();
    }
    return idset;
  }

  /**
   * can this service be run on the visible portion of an alignment regardless
   * of hidden boundaries ?
   */
  boolean hseparable = false;

  boolean vseparable = false;

  public boolean isHseparable()
  {
    return hseparable;
  }

  /**
   * 
   * @return
   */
  public boolean isVseparable()
  {
    return vseparable;
  }

  /**
   * search the input types for an instance of the given class
   * 
   * @param <validInput.inputType> class1
   * @return
   */
  public boolean inputInvolves(Class<?> class1)
  {
    assert (InputType.class.isAssignableFrom(class1));
    for (InputType val : inputParams.values())
    {
      if (class1.isAssignableFrom(val.getClass()))
      {
        return true;
      }
    }
    return false;
  }

  char gapCharacter = '-';

  /**
   * 
   * @return the preferred gap character for alignments input/output by this
   *         service
   */
  public char getGapCharacter()
  {
    return gapCharacter;
  }

  public String getDecoratedResultUrl(String jobId)
  {
    // TODO: correctly write ?/& appropriate to result URL format.
    return jobId + urlSuffix;
  }

  private List<JvDataType> resultData = new ArrayList<JvDataType>();

  /**
   * 
   * 
   * TODO: Extend to optionally specify relative/absolute url where data of this
   * type can be retrieved from
   * 
   * @param dt
   */
  public void addResultDatatype(JvDataType dt)
  {
    if (resultData == null)
    {
      resultData = new ArrayList<JvDataType>();
    }
    resultData.add(dt);
  }

  public boolean removeRsultDatatype(JvDataType dt)
  {
    if (resultData != null)
    {
      return resultData.remove(dt);
    }
    return false;
  }

  public List<JvDataType> getResultDataTypes()
  {
    return resultData;
  }

  /**
   * parse a concatenated list of rest service descriptions into an array
   * 
   * @param services
   * @return zero or more services.
   * @throws exceptions
   *           if the services are improperly encoded.
   */
  public static List<RestServiceDescription> parseDescriptions(
          String services) throws Exception
  {
    String[] list = separatorListToArray(services, "|");
    List<RestServiceDescription> svcparsed = new ArrayList<RestServiceDescription>();
    int p = 0, lastp = 0;
    StringBuffer warnings = new StringBuffer();
    do
    {
      RestServiceDescription rsd = new RestServiceDescription();
      p = rsd.parseServiceList(list, warnings, lastp = p);
      if (p > lastp && rsd.isValid())
      {
        svcparsed.add(rsd);
      }
      else
      {
        throw new Exception(
                "Failed to parse user defined RSBS services from :"
                        + services
                        + "\nFirst error was encountered at token " + lastp
                        + " starting " + list[lastp] + ":\n"
                        + rsd.getInvalidMessage());
      }
    } while (p < lastp && p < list.length - 1);
    return svcparsed;
  }

}
