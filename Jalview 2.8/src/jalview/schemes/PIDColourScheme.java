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

import jalview.analysis.AAFrequency;
import jalview.datamodel.SequenceGroup;
import jalview.datamodel.SequenceI;

import java.awt.Color;

public class PIDColourScheme extends ResidueColourScheme
{
  public Color[] pidColours;

  public float[] thresholds;

  SequenceGroup group;

  public PIDColourScheme()
  {
    this.pidColours = ResidueProperties.pidColours;
    this.thresholds = ResidueProperties.pidThresholds;
  }

  @Override
  public Color findColour(char c, int j, SequenceI seq)
  {
    if ('a' <= c && c <= 'z')
    {
      c -= ('a' - 'A');
    }

    if (consensus == null || j >= consensus.length || consensus[j] == null)
    {
      return Color.white;
    }

    if ((threshold != 0) && !aboveThreshold(c, j))
    {
      return Color.white;
    }

    Color currentColour = Color.white;

    double sc = 0;

    if (consensus.length <= j)
    {
      return Color.white;
    }

    if ((Integer
            .parseInt(consensus[j].get(AAFrequency.MAXCOUNT).toString()) != -1)
            && consensus[j].contains(String.valueOf(c)))
    {
      sc = ((Float) consensus[j].get(ignoreGaps)).floatValue();

      if (!jalview.util.Comparison.isGap(c))
      {
        for (int i = 0; i < thresholds.length; i++)
        {
          if (sc > thresholds[i])
          {
            currentColour = pidColours[i];

            break;
          }
        }
      }
    }

    if (conservationColouring)
    {
      currentColour = applyConservation(currentColour, j);
    }

    return currentColour;
  }
}
