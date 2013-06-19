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

import java.awt.Color;
import jalview.datamodel.SequenceI;

public class Blosum62ColourScheme extends ResidueColourScheme
{
  public Blosum62ColourScheme()
  {
    super();
  }

  @Override
  public Color findColour(char res, int j, SequenceI seq)
  {
    if ('a' <= res && res <= 'z')
    {
      // TO UPPERCASE !!!
      res -= ('a' - 'A');
    }

    if (consensus == null || j >= consensus.length || consensus[j] == null
            || (threshold != 0 && !aboveThreshold(res, j)))
    {
      return Color.white;
    }

    Color currentColour;

    if (!jalview.util.Comparison.isGap(res))
    {
      String max = (String) consensus[j].get(AAFrequency.MAXRESIDUE);

      if (max.indexOf(res) > -1)
      {
        currentColour = new Color(154, 154, 255);
      }
      else
      {
        int c = 0;
        int max_aa = 0;
        int n = max.length();

        do
        {
          c += ResidueProperties.getBLOSUM62(max.charAt(max_aa), res);
        } while (++max_aa < n);

        if (c > 0)
        {
          currentColour = new Color(204, 204, 255);
        }
        else
        {
          currentColour = Color.white;
        }
      }

      if (conservationColouring)
      {
        currentColour = applyConservation(currentColour, j);
      }
    }
    else
    {
      return Color.white;
    }

    return currentColour;
  }
}
