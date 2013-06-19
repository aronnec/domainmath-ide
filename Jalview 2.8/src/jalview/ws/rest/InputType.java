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

import jalview.ws.params.ArgumentI;
import jalview.ws.params.InvalidArgumentException;
import jalview.ws.params.OptionI;
import jalview.ws.params.ParameterI;
import jalview.ws.params.simple.IntegerParameter;
import jalview.ws.params.simple.Option;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;

/***
 * InputType is the abstract model of each input parameter that a rest service
 * might take. It enables the engine to validate input by providing { formatter
 * for type, parser for type }
 * 
 */
public abstract class InputType
{
  /**
   * not used yet
   */
  boolean replaceids;

  public enum molType
  {
    NUC, PROT, MIX;

    public static Collection<String> toStringValues()
    {
      Collection<String> c = new ArrayList<String>();
      for (molType type : values())
      {
        c.add(type.toString());
      }
      return c;
    }
  }

  public String token;

  public int min = 1;

  public int max = 0; // unbounded

  protected ArrayList<Class> inputData = new ArrayList<Class>();

  /**
   * initialise the InputType with a list of jalview data classes that the
   * RestJob needs to be able to provide to it.
   * 
   * @param types
   */
  protected InputType(Class[] types)
  {
    if (types != null)
    {
      for (Class t : types)
      {
        inputData.add(t);
      }
    }
  }

  /**
   * do basic tests to ensure the job's service takes this parameter, and the
   * job's input data can be used to generate the input data
   * 
   * @param restJob
   * @return
   */
  public boolean validFor(RestJob restJob)
  {
    if (!validFor(restJob.rsd))
      return false;
    for (Class cl : inputData)
    {
      if (!restJob.hasDataOfType(cl))
      {
        return false;
      }
    }
    return true;
  }

  public boolean validFor(RestServiceDescription restServiceDescription)
  {
    if (!restServiceDescription.inputParams.values().contains(this))
      return false;

    return true;
  }

  protected ContentBody utf8StringBody(String content, String type)
  {
    Charset utf8 = Charset.forName("UTF-8");
    try
    {
      if (type == null)
      {
        return new StringBody(utf8.encode(content).asCharBuffer()
                .toString());
      }
      else
      {
        return new StringBody(utf8.encode(content).asCharBuffer()
                .toString(), type, utf8);
      }
    } catch (Exception ex)
    {
      System.err.println("Couldn't transform string\n" + content
              + "\nException was :");
      ex.printStackTrace(System.err);
    }
    return null;
  }

  /**
   * 
   * @param rj
   *          data from which input is to be extracted and formatted
   * @return StringBody or FileBody ready for posting
   */
  abstract public ContentBody formatForInput(RestJob rj)
          throws UnsupportedEncodingException, NoValidInputDataException;

  /**
   * 
   * @return true if no input data needs to be provided for this parameter
   */
  public boolean isConstant()
  {
    return (inputData == null || inputData.size() == 0);
  }

  /**
   * return a url encoded version of this parameter's value, or an empty string
   * if the parameter has no ='value' content.
   * 
   * @return
   */
  public abstract List<String> getURLEncodedParameter();

  /**
   * set the property known as tok, possibly by assigning it with a given val
   * 
   * @param tok
   * @param val
   *          (may be empty or null)
   * @param warnings
   *          place where parse warnings are reported
   * @return true if property was set
   */
  public abstract boolean configureProperty(String tok, String val,
          StringBuffer warnings);

  /**
   * Get unique key for this type of parameter in a URL encoding.
   * 
   * @return the string that prefixes an input parameter of InputType<T> type in
   *         the string returned from getURLEncodedParameter
   */
  public abstract String getURLtokenPrefix();

  /**
   * parse the given token String and set InputParameter properties
   * appropriately
   * 
   * @param tokenstring
   *          - urlencoded parameter string as returned from
   *          getURLEncodedParameter
   * @param warnings
   *          - place where any warning messages about bad property values are
   *          written
   * @return true if configuration succeeded, false otherwise.
   */
  public boolean configureFromURLtokenString(List<String> tokenstring,
          StringBuffer warnings)
  {
    boolean valid = true;
    for (String tok : tokenstring)
    {
      Matcher mtch = Pattern.compile("^([^=]+)=?'?([^']*)?'?").matcher(tok);
      if (mtch.find())
      {
        try
        {
          if (mtch.group(1).equals("min"))
          {
            min = Integer.parseInt(mtch.group(2));
            continue;

          }
          else if (mtch.group(1).equals("max"))
          {
            max = Integer.parseInt(mtch.group(2));
            continue;
          }
        } catch (NumberFormatException x)
        {
          valid = false;
          warnings.append("Invalid value for parameter "
                  + mtch.group(1).toLowerCase() + " '" + mtch.group(2)
                  + "' (expected an integer)\n");
        }

        if (!configureProperty(mtch.group(1), mtch.group(2), warnings))
        {
          if (warnings.length() == 0)
          {
            warnings.append("Failed to configure InputType :"
                    + getURLtokenPrefix() + " with property string: '"
                    + mtch.group(0) + "'\n (token is '" + mtch.group(1)
                    + "' and value is '" + mtch.group(2) + "')\n");
          }
          valid = false;
        }
      }
    }
    return valid;
  }

  public void addBaseParams(ArrayList<String> prms)
  {
    // todo : check if replaceids should be a global for the service, rather
    // than for a specific parameter.
    if (min != 1)
    {
      prms.add("min='" + min + "'");
    }
    if (max != 0)
    {
      prms.add("max='" + max + "'");
    }
  }

  public abstract List<OptionI> getOptions();

  public List<OptionI> getBaseOptions()
  {
    ArrayList<OptionI> opts = new ArrayList<OptionI>();
    opts.add(new IntegerParameter("min",
            "Minimum number of data of this type", true, 1, min, 0, -1));
    opts.add(new IntegerParameter("max",
            "Maximum number of data of this type", false, 0, max, 0, -1));
    return opts;
  }

  /**
   * make a copy of this InputType
   * 
   * @return may not be needed public abstract InputType copy();
   */

  /**
   * parse a set of configuration options
   * 
   * @param currentSettings
   *          - modified settings originally from getOptions
   * @throws InvalidArgumentException
   *           thrown if currentSettings contains invalid options for this type.
   */
  public void configureFromArgumentI(List<ArgumentI> currentSettings)
          throws InvalidArgumentException
  {
    ArrayList<String> urltoks = new ArrayList<String>();
    String rg;
    for (ArgumentI arg : currentSettings)
    {
      if (arg instanceof ParameterI)
      {
        rg = arg.getName() + "='" + arg.getValue() + "'";
      }
      else
      {
        // TODO: revise architecture - this is counter intuitive - options with
        // different values to their names are actually parameters
        rg = (arg.getValue().length() > 0) ? (arg.getValue().equals(
                arg.getName()) ? arg.getName() : arg.getName() + "='"
                + arg.getValue() + "'") : arg.getName();
      }
      if (rg.length() > 0)
      {
        urltoks.add(rg);
      }
    }
    StringBuffer warnings;
    if (!configureFromURLtokenString(urltoks, warnings = new StringBuffer()))
    {
      throw new InvalidArgumentException(warnings.toString());
    }
  }

  protected OptionI createMolTypeOption(String name, String descr,
          boolean req, molType curType, molType defType)
  {
    return new Option(name, descr, req, defType == null ? ""
            : defType.toString(),
            curType == null ? "" : curType.toString(),
            molType.toStringValues(), null);
  }
}
