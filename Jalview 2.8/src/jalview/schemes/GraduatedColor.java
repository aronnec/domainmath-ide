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
package jalview.schemes;

import jalview.datamodel.SequenceFeature;

import java.awt.Color;

/**
 * Value and/or thresholded colour scale used for colouring by annotation and
 * feature score
 * 
 * @author JimP
 * 
 */
public class GraduatedColor
{
  int thresholdState = AnnotationColourGradient.NO_THRESHOLD; // or
                                                              // ABOVE_THRESHOLD
                                                              // or
                                                              // BELOW_THRESHOLD

  float lr, lg, lb, dr, dg, db;

  /**
   * linear scaling parameters, base, minimum colour threshold, range of linear
   * scale from lower to upper
   */
  float base, range, thrsh;

  /**
   * when true, colour from u to u-d rather than u to u+d
   */
  boolean tolow = false;

  /**
   * when false, min/max range has been manually set so should not be
   * dynamically adjusted.
   */
  boolean autoScale = true;

  /**
   * construct a graduatedColor object from simple parameters
   * 
   * @param low
   * @param high
   * @param min
   * @param max
   *          color low->high from min->max
   */
  public GraduatedColor(Color low, Color high, float min, float max)
  {
    thrsh = Float.NaN;
    tolow = min >= max;
    lr = low.getRed() / 255f;
    lg = low.getGreen() / 255f;
    lb = low.getBlue() / 255f;
    dr = (high.getRed() / 255f) - lr;
    dg = (high.getGreen() / 255f) - lg;
    db = (high.getBlue() / 255f) - lb;
    if (tolow)
    {
      base = max;
      range = min - max;
    }
    else
    {
      base = min;
      range = max - min;
    }
  }

  public GraduatedColor(GraduatedColor oldcs)
  {
    lr = oldcs.lr;
    lg = oldcs.lg;
    lb = oldcs.lb;
    dr = oldcs.dr;
    dg = oldcs.dg;
    db = oldcs.db;
    base = oldcs.base;
    range = oldcs.range;
    tolow = oldcs.tolow;
    thresholdState = oldcs.thresholdState;
    thrsh = oldcs.thrsh;
    autoScale = oldcs.autoScale;
    colourByLabel = oldcs.colourByLabel;
  }

  /**
   * make a new gradient from an old one with a different scale range
   * 
   * @param oldcs
   * @param min
   * @param max
   */
  public GraduatedColor(GraduatedColor oldcs, float min, float max)
  {
    this(oldcs);
    updateBounds(min, max);
  }

  public Color getMinColor()
  {
    return new Color(lr, lg, lb);
  }

  public Color getMaxColor()
  {
    return new Color(lr + dr, lg + dg, lb + db);
  }

  /**
   * 
   * @return true if original min/max scale was from high to low
   */
  public boolean getTolow()
  {
    return tolow;
  }

  public void setTolow(boolean tolower)
  {
    tolow = tolower;
  }

  public boolean isColored(SequenceFeature feature)
  {
    float val = feature.getScore();
    if (val == Float.NaN)
    {
      return true;
    }
    if (this.thresholdState == AnnotationColourGradient.NO_THRESHOLD)
    {
      return true;
    }
    if (this.thrsh == Float.NaN)
    {
      return true;
    }
    boolean rtn = thresholdState == AnnotationColourGradient.ABOVE_THRESHOLD;
    if (val <= thrsh)
    {
      return !rtn; // ? !tolow : tolow;
    }
    else
    {
      return rtn; // ? tolow : !tolow;
    }
  }

  /**
   * default implementor of a getColourFromString method. TODO: abstract an
   * interface enabling pluggable colour from string
   */
  private UserColourScheme ucs = null;

  private boolean colourByLabel = false;

  /**
   * 
   * @return true if colourByLabel style is set
   */
  public boolean isColourByLabel()
  {
    return colourByLabel;
  }

  /**
   * @param colourByLabel
   *          the colourByLabel to set
   */
  public void setColourByLabel(boolean colourByLabel)
  {
    this.colourByLabel = colourByLabel;
  }

  public Color findColor(SequenceFeature feature)
  {
    if (colourByLabel)
    {
      // TODO: allow user defined feature label colourschemes. Colour space is
      // {type,regex,%anytype%}x{description string, regex, keyword}
      if (ucs == null)
      {
        ucs = new UserColourScheme();
      }
      return ucs.createColourFromName(feature.getDescription());
    }
    if (range == 0.0)
    {
      return getMaxColor();
    }
    float scr = feature.getScore();
    if (scr == Float.NaN)
    {
      return getMinColor();
    }
    float scl = (scr - base) / range;
    if (tolow)
    {
      scl = -scl;
    }
    if (scl < 0f)
    {
      scl = 0f;
    }
    if (scl > 1f)
    {
      scl = 1f;
    }
    return new Color(lr + scl * dr, lg + scl * dg, lb + scl * db);
  }

  public void setThresh(float value)
  {
    thrsh = value;
  }

  public float getThresh()
  {
    return thrsh;
  }

  public void setThreshType(int aboveThreshold)
  {
    thresholdState = aboveThreshold;
  }

  public int getThreshType()
  {
    return thresholdState;
  }

  public float getMax()
  {
    // regenerate the original values passed in to the constructor
    return (tolow) ? base : (base + range);
  }

  public float getMin()
  {
    // regenerate the original value passed in to the constructor
    return (tolow) ? (base + range) : base;
  }

  public boolean isAutoScale()
  {
    return autoScale;
  }

  public void setAutoScaled(boolean autoscale)
  {
    autoScale = autoscale;
  }

  /**
   * update the base and range appropriatly for the given minmax range
   * 
   * @param a
   *          float[] {min,max} array containing minmax range for the associated
   *          score values
   */
  public void updateBounds(float min, float max)
  {
    if (max < min)
    {
      base = max;
      range = min - max;
      tolow = true;
    }
    else
    {
      base = min;
      range = max - min;
      tolow = false;
    }
  }
}
