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
import jalview.ws.rest.RestJob;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.mime.content.ContentBody;

/**
 * format a tree for input to a rest service
 * 
 * @author JimP
 * 
 */
public class Tree extends InputType
{
  public Tree()
  {
    super(new Class[]
    { jalview.analysis.NJTree.class });
  }

  // TODO specify modifiers for tree output format
  @Override
  public ContentBody formatForInput(RestJob rj)
          throws UnsupportedEncodingException
  {
    // TODO: implement tree inputType
    /*
     * rj.getTreeForInput(token); return new StringBody(new )
     */
    throw new Error("Tree InputType not yet implemented");
    // return null;
  }

  public String getURLtokenPrefix()
  {
    return "NEWICK";
  }

  @Override
  public List<String> getURLEncodedParameter()
  {
    ArrayList<String> prms = new ArrayList<String>();
    super.addBaseParams(prms);
    return prms;
  }

  @Override
  public boolean configureProperty(String tok, String val,
          StringBuffer warnings)
  {
    return true;
  }

  @Override
  public List<OptionI> getOptions()
  {
    return getBaseOptions();
  }

}
