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
package jalview.ws.params.simple;

import jalview.ws.params.OptionI;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Option implements OptionI
{

  String name, value, defvalue, description;

  ArrayList<String> possibleVals = new ArrayList<String>();

  boolean required;

  URL fdetails;

  @Override
  public String getName()
  {
    return name;
  }

  @Override
  public String getValue()
  {
    return value == null ? defvalue : value;
  }

  @Override
  public void setValue(String selectedItem)
  {
    value = selectedItem;
  }

  @Override
  public URL getFurtherDetails()
  {
    return fdetails;
  }

  @Override
  public boolean isRequired()
  {
    return required;
  }

  @Override
  public String getDescription()
  {
    return description;
  }

  @Override
  public List<String> getPossibleValues()
  {
    return possibleVals;
  }

  public Option(Option opt)
  {
    name = new String(opt.name);
    if (opt.value != null)
      value = new String(opt.value);
    if (opt.defvalue != null)
      defvalue = new String(opt.defvalue);
    if (opt.description != null)
      description = new String(opt.description);
    if (opt.possibleVals != null)
    {
      possibleVals = (ArrayList<String>) opt.possibleVals.clone();
    }
    required = opt.required;
    // URLs are singletons - so we copy by reference. nasty but true.
    fdetails = opt.fdetails;
  }

  public Option()
  {
  }

  public Option(String name2, String description2, boolean isrequired,
          String defValue, String value, Collection<String> possibleVals,
          URL fdetails)
  {
    name = name2;
    description = description2;
    this.value = value;
    this.required = isrequired;
    this.defvalue = defValue;
    if (possibleVals != null)
    {
      this.possibleVals = new ArrayList<String>();
      this.possibleVals.addAll(possibleVals);
    }
    this.fdetails = fdetails;
  }

  @Override
  public OptionI copy()
  {
    Option opt = new Option(this);
    return opt;
  }
}
