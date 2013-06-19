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
import jalview.ws.params.ParameterI;
import jalview.ws.params.ValueConstrainI;
import compbio.metadata.Parameter;
import compbio.metadata.ValueConstrain;

public class JabaParameter extends JabaOption implements ParameterI
{

  public JabaParameter(Parameter rg)
  {
    super(rg);

  }

  @Override
  public ValueConstrainI getValidValue()
  {
    ValueConstrain vc = ((Parameter) opt).getValidValue();
    if (vc == null)
    {
      return null;
    }
    else
    {
      return new JabaValueConstrain(vc);
    }
  }

  @Override
  public ParameterI copy()
  {
    return new JabaParameter(ParameterUtils.copyParameter((Parameter) opt));
  }
}
