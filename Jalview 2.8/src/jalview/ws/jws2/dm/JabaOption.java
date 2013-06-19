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
package jalview.ws.jws2.dm;

import jalview.ws.jws2.ParameterUtils;
import jalview.ws.params.OptionI;

import java.net.URL;
import java.util.List;

import compbio.metadata.Option;

public class JabaOption implements jalview.ws.params.OptionI
{
  public JabaOption(Option rg)
  {
    opt = rg;
  }

  Option opt;

  @Override
  public String getValue()
  {
    return opt.getDefaultValue();
  }

  @Override
  public String getName()
  {
    return opt.getName();
  }

  @Override
  public URL getFurtherDetails()
  {
    return opt.getFurtherDetails();
  }

  @Override
  public boolean isRequired()
  {
    return opt.isRequired();
  }

  @Override
  public String getDescription()
  {
    return opt.getDescription();
  }

  @Override
  public List<String> getPossibleValues()
  {
    // TODO: assert typesafety
    return opt.getPossibleValues();
  }

  @Override
  public void setValue(String selectedItem)
  {
    try
    {
      opt.setDefaultValue(selectedItem);
    } catch (Exception e)
    {
      e.printStackTrace();
      throw new Error(
              "Implementation error: cannot set Jaba Option to a value outside its allowed value range!");
    }
  }

  @Override
  public OptionI copy()
  {
    return new JabaOption(ParameterUtils.copyOption(opt));
  }

  /**
   * get the underlying Jaba option or parameter object. Note - use copy first
   * if you want to modify the value of the option.
   * 
   * @return
   */
  public Option getOption()
  {
    return opt;
  }

}
