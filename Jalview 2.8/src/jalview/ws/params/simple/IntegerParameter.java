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

import jalview.ws.params.ParameterI;
import jalview.ws.params.ValueConstrainI;

/**
 * @author jimp
 * 
 */
public class IntegerParameter extends Option implements ParameterI
{
  int defval;

  int min, max;

  public ValueConstrainI getValidValue()
  {
    return new ValueConstrainI()
    {

      @Override
      public ValueType getType()
      {
        return ValueType.Integer;
      }

      @Override
      public Number getMin()
      {
        if (min < max)
        {
          return min;
        }
        else
        {
          return null;
        }
      }

      @Override
      public Number getMax()
      {
        if (min < max)
        {
          return max;
        }
        else
        {
          return null;
        }
      }
    };
  }

  public IntegerParameter(IntegerParameter parm)
  {
    super(parm);
    max = parm.max;
    min = parm.min;
  }

  public IntegerParameter(String name, String description,
          boolean required, int defValue, int min, int max)
  {
    super(name, description, required, String.valueOf(defValue), null,
            null, null);
    defval = defValue;
    this.min = min;
    this.max = max;
  }

  public IntegerParameter(String name, String description,
          boolean required, int defValue, int value, int min, int max)
  {
    super(name, description, required, String.valueOf(defValue), String
            .valueOf(value), null, null);
    defval = defValue;
    this.min = min;
    this.max = max;
  }

  @Override
  public IntegerParameter copy()
  {
    return new IntegerParameter(this);
  }

}
