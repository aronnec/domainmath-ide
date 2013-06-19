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

import jalview.datamodel.AlignmentI;
import jalview.ws.params.OptionI;
import jalview.ws.params.simple.Option;
import jalview.ws.rest.InputType;
import jalview.ws.rest.NoValidInputDataException;
import jalview.ws.rest.RestJob;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;

/**
 * format a jalview annotation file for input to a rest service.
 * 
 * @author JimP
 * 
 */
public class AnnotationFile extends InputType
{
  public AnnotationFile()
  {
    super(new Class[]
    { AlignmentI.class });
  }

  /**
   * standard jalview annotation file
   */
  final String JVANNOT = "JalviewAnnotation";

  /**
   * export annotation row as simple csv
   */
  final String CSVANNOT = "CsvAnnotationRow";

  /**
   * format of annotation file
   */
  String format = JVANNOT;

  // TODO verify annotation file format enumeration
  @Override
  public ContentBody formatForInput(RestJob rj)
          throws UnsupportedEncodingException, NoValidInputDataException
  {
    AlignmentI al = rj.getAlignmentForInput(token, molType.MIX);
    if (format.equals(JVANNOT))
    {
      return new StringBody(
              new jalview.io.AnnotationFile().printAnnotations(
                      al.getAlignmentAnnotation(), al.getGroups(),
                      al.getProperties()));
    }
    else
    {
      if (!format.equals(CSVANNOT))
      {
        throw new UnsupportedEncodingException(
                "Unrecognised format for exporting Annotation (" + format
                        + ")");
      }
      return new StringBody(
              new jalview.io.AnnotationFile().printCSVAnnotations(al
                      .getAlignmentAnnotation()));
    }
  }

  @Override
  public List<String> getURLEncodedParameter()
  {
    ArrayList<String> prms = new ArrayList<String>();
    super.addBaseParams(prms);
    prms.add("format='" + format + "'");
    return prms;
  }

  @Override
  public String getURLtokenPrefix()
  {
    return "ALANNOTATION";
  }

  @Override
  public boolean configureProperty(String tok, String val,
          StringBuffer warnings)
  {

    if (tok.startsWith("format"))
    {
      for (String fmt : new String[]
      { CSVANNOT, JVANNOT })
      {
        if (val.equalsIgnoreCase(fmt))
        {
          format = fmt;
          return true;
        }
      }
      warnings.append("Invalid annotation file format '" + val
              + "'. Must be one of (");
      for (String fmt : new String[]
      { CSVANNOT, JVANNOT })
      {
        warnings.append(" " + fmt);
      }
      warnings.append(")\n");
    }
    return false;
  }

  @Override
  public List<OptionI> getOptions()
  {
    // TODO - consider disregarding base options here.
    List<OptionI> lst = getBaseOptions();
    lst.add(new Option("format", "Alignment annotation upload format",
            true, JVANNOT, format, Arrays.asList(new String[]
            { JVANNOT, CSVANNOT }), null));
    return lst;
  }
}
