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
public class NucleotideColourScheme extends ResidueColourScheme
{
  /**
   * Creates a new NucleotideColourScheme object.
   */
  public NucleotideColourScheme()
  {
    super(ResidueProperties.nucleotideIndex, ResidueProperties.nucleotide,
            0);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param n
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  @Override
  public Color findColour(char c)
  {
    // System.out.println("called"); log.debug
    return colors[ResidueProperties.nucleotideIndex[c]];
  }

  /**
   * DOCUMENT ME!
   * 
   * @param n
   *          DOCUMENT ME!
   * @param j
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  @Override
  public Color findColour(char c, int j, SequenceI seq)
  {
    Color currentColour;
    if ((threshold == 0) || aboveThreshold(c, j))
    {
      try
      {
        currentColour = colors[ResidueProperties.nucleotideIndex[c]];
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
