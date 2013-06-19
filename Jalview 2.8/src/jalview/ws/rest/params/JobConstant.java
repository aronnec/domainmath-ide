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
package jalview.ws.rest.params;

import jalview.ws.params.OptionI;
import jalview.ws.rest.InputType;
import jalview.ws.rest.NoValidInputDataException;
import jalview.ws.rest.RestJob;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;

/**
 * defines a constant value always provided as a parameter.
 * 
 * @author JimP
 * 
 */
public class JobConstant extends InputType
{

  String value;

  /**
   * 
   * @param param
   *          name of parameter
   * @param val
   *          value of parameter
   */
  public JobConstant(String param, String val)
  {
    // needs no data from the restJob
    super(null);
    this.token = param;
    value = val;
  }

  @Override
  public ContentBody formatForInput(RestJob rj)
          throws UnsupportedEncodingException, NoValidInputDataException
  {

    return new StringBody(value);
  }

  @Override
  public List<String> getURLEncodedParameter()
  {
    ArrayList<String> prm = new ArrayList<String>();

    if (value != null && value.length() > 0)
    {
      try
      {
        prm.add(URLEncoder.encode(value, "UTF-8"));
      } catch (UnsupportedEncodingException ex)
      {
        throw new Error("Couldn't encode '" + value + "' as UTF-8.", ex);

      }
    }
    return prm;
  }

  @Override
  public String getURLtokenPrefix()
  {
    return "";
  }

  @Override
  public boolean configureFromURLtokenString(List<String> tokenstring,
          StringBuffer warnings)
  {
    if (tokenstring.size() > 1)
    {
      warnings.append("IMPLEMENTATION ERROR: Constant POST parameters cannot have more than one value.");
      return false;
    }
    if (tokenstring.size() == 1)
    {
      value = tokenstring.get(0);
    }
    return true;
  }

  @Override
  public boolean configureProperty(String tok, String val,
          StringBuffer warnings)
  {
    warnings.append("IMPLEMENTATION ERROR: No Properties to configure for a Constant parameter.");
    return false;
  }

  @Override
  public List<OptionI> getOptions()
  {
    // empty list - this parameter isn't configurable, so don't try.
    return new ArrayList<OptionI>();
  }
}
