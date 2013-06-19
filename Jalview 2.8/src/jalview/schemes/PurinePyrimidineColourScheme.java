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

import java.awt.*;

/**
 * Class is based off of NucleotideColourScheme
 * 
 * @author Lauren Michelle Lui
 */
public class PurinePyrimidineColourScheme extends ResidueColourScheme
{
  /**
   * Creates a new PurinePyrimidineColourScheme object.
   */
  public PurinePyrimidineColourScheme()
  {
    super(ResidueProperties.purinepyrimidineIndex,
            ResidueProperties.purinepyrimidine, 0);
  }

  /**
   * Finds the corresponding color for the type of character inputed
   * 
   * @param c
   *          Character in sequence
   * 
   * @return Color from purinepyrimidineIndex in
   *         jalview.schemes.ResidueProperties
   */
  public Color findColour(char c)
  {
    return colors[ResidueProperties.purinepyrimidineIndex[c]];
  }

  /**
   * Returns color based on conservation
   * 
   * @param c
   *          Character in sequence
   * @param j
   *          Threshold
   * 
   * @return Color in RGB
   */
  public Color findColour(char c, int j)
  {
    Color currentColour;
    if ((threshold == 0) || aboveThreshold(c, j))
    {
      try
      {
        currentColour = colors[ResidueProperties.purinepyrimidineIndex[c]];
      } catch (Exception ex)
      {
        return Color.white;
      }
    }
    else
    {
      return Color.white;
    }

    if (conservationColouring)
    {
      currentColour = applyConservation(currentColour, j);
    }

    return currentColour;
  }
}
