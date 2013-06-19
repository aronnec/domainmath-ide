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

import jalview.datamodel.SequenceI;

import java.awt.Color;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class ScoreColourScheme extends ResidueColourScheme
{
  /** DOCUMENT ME!! */
  public double min;

  /** DOCUMENT ME!! */
  public double max;

  /** DOCUMENT ME!! */
  public double[] scores;

  /**
   * Creates a new ScoreColourScheme object.
   * 
   * @param scores
   *          DOCUMENT ME!
   * @param min
   *          DOCUMENT ME!
   * @param max
   *          DOCUMENT ME!
   */
  public ScoreColourScheme(int symbolIndex[], double[] scores, double min,
          double max)
  {
    super(symbolIndex);

    this.scores = scores;
    this.min = min;
    this.max = max;

    // Make colours in constructor
    // Why wasn't this done earlier?
    int i, iSize = scores.length;
    colors = new Color[scores.length];
    for (i = 0; i < iSize; i++)
    {
      float red = (float) (scores[i] - (float) min) / (float) (max - min);

      if (red > 1.0f)
      {
        red = 1.0f;
      }

      if (red < 0.0f)
      {
        red = 0.0f;
      }
      colors[i] = makeColour(red);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param s
   *          DOCUMENT ME!
   * @param j
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  @Override
  public Color findColour(char c, int j, SequenceI seq)
  {
    if (threshold > 0)
    {
      if (!aboveThreshold(c, j))
      {
        return Color.white;
      }
    }

    if (jalview.util.Comparison.isGap(c))
    {
      return Color.white;
    }

    Color currentColour = colors[ResidueProperties.aaIndex[c]];

    if (conservationColouring)
    {
      currentColour = applyConservation(currentColour, j);
    }

    return currentColour;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param c
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Color makeColour(float c)
  {
    return new Color(c, (float) 0.0, (float) 1.0 - c);
  }
}
