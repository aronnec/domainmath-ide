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
import jalview.datamodel.SequenceI;
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
 * input a list of sequences separated by some separator
 * 
 * @author JimP
 * 
 */
public class SeqVector extends InputType
{
  String sep;

  molType type;

  public SeqVector()
  {
    super(new Class[]
    { AlignmentI.class });
  }

  @Override
  public ContentBody formatForInput(RestJob rj)
          throws UnsupportedEncodingException, NoValidInputDataException
  {
    StringBuffer idvector = new StringBuffer();
    boolean list = false;
    for (SequenceI seq : rj.getSequencesForInput(token, type))
    {
      if (list)
      {
        idvector.append(sep);
      }
      idvector.append(seq.getSequence());
    }
    return new StringBody(idvector.toString());
  }

  @Override
  public List<String> getURLEncodedParameter()
  {
    ArrayList<String> prms = new ArrayList<String>();
    super.addBaseParams(prms);
    prms.add("sep='" + sep + "'");
    if (type != null)
    {
      prms.add("type='" + type + "'");
    }
    return prms;
  }

  @Override
  public String getURLtokenPrefix()
  {
    return "SEQS";
  }

  @Override
  public boolean configureProperty(String tok, String val,
          StringBuffer warnings)
  {

    if (tok.startsWith("sep"))
    {
      sep = val;
      return true;
    }
    if (tok.startsWith("type"))
    {
      try
      {
        type = molType.valueOf(val);
        return true;
      } catch (Exception x)
      {
        warnings.append("Invalid molecule type '" + val
                + "'. Must be one of (");
        for (molType v : molType.values())
        {
          warnings.append(" " + v);
        }
        warnings.append(")\n");
      }
    }
    return false;
  }

  @Override
  public List<OptionI> getOptions()
  {
    List<OptionI> lst = getBaseOptions();
    lst.add(new Option("sep",
            "Separator character between elements of vector", true, ",",
            sep, Arrays.asList(new String[]
            { " ", ",", ";", "\t", "|" }), null));
    lst.add(createMolTypeOption("type", "Sequence type", false, type,
            molType.MIX));

    return lst;
  }

}
