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

import compbio.metadata.ValueConstrain;

import jalview.ws.params.ValueConstrainI;

public class JabaValueConstrain implements ValueConstrainI
{

  ValueConstrain vc = null;

  public JabaValueConstrain(ValueConstrain vc)
  {
    this.vc = vc;
  }

  @Override
  public ValueType getType()
  {
    if (vc.getType() == ValueConstrain.Type.Float)
    {
      return ValueType.Float;
    }
    if (vc.getType() == ValueConstrain.Type.Integer)
    {
      return ValueType.Integer;
    }
    throw new Error(
            "IMPLEMENTATION ERROR: jalview.ws.params.ValueConstrainI.ValueType does not support the JABAWS type :"
                    + vc.toString());
  }

  @Override
  public Number getMax()
  {
    return vc.getMax();
  }

  @Override
  public Number getMin()
  {
    return vc.getMin();
  }

}
