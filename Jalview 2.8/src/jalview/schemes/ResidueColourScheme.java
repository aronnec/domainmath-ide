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
import jalview.analysis.Conservation;
import jalview.datamodel.AnnotatedCollectionI;
import jalview.datamodel.SequenceCollectionI;
import jalview.datamodel.SequenceI;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Map;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class ResidueColourScheme implements ColourSchemeI
{
  final int[] symbolIndex;

  boolean conservationColouring = false;

  Color[] colors = null;

  int threshold = 0;

  /* Set when threshold colouring to either pid_gaps or pid_nogaps */
  protected String ignoreGaps = AAFrequency.PID_GAPS;

  /** Consenus as a hashtable array */
  Hashtable[] consensus;

  /** Conservation string as a char array */
  char[] conservation;

  int conservationLength = 0;

  /** DOCUMENT ME!! */
  int inc = 30;

  /**
   * Creates a new ResidueColourScheme object.
   * 
   * @param final int[] index table into colors (ResidueProperties.naIndex or
   *        ResidueProperties.aaIndex)
   * @param colors
   *          colours for symbols in sequences
   * @param threshold
   *          threshold for conservation shading
   */
  public ResidueColourScheme(int[] aaOrnaIndex, Color[] colours,
          int threshold)
  {
    symbolIndex = aaOrnaIndex;
    this.colors = colours;
    this.threshold = threshold;
  }

  /**
   * Creates a new ResidueColourScheme object with a lookup table for indexing
   * the colour map
   */
  public ResidueColourScheme(int[] aaOrNaIndex)
  {
    symbolIndex = aaOrNaIndex;
  }

  /**
   * Creates a new ResidueColourScheme object - default constructor for
   * non-sequence dependent colourschemes
   */
  public ResidueColourScheme()
  {
    symbolIndex = null;
  }

  /**
   * Find a colour without an index in a sequence
   */
  public Color findColour(char c)
  {
    return colors == null ? Color.white : colors[symbolIndex[c]];
  }

  @Override
  public Color findColour(char c, int j, SequenceI seq)
  {
    Color currentColour;

    if (colors != null && symbolIndex != null && (threshold == 0)
            || aboveThreshold(c, j))
    {
      currentColour = colors[symbolIndex[c]];
    }
    else
    {
      currentColour = Color.white;
    }

    if (conservationColouring)
    {
      currentColour = applyConservation(currentColour, j);
    }

    return currentColour;
  }

  /**
   * Get the percentage threshold for this colour scheme
   * 
   * @return Returns the percentage threshold
   */
  public int getThreshold()
  {
    return threshold;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param ct
   *          DOCUMENT ME!
   */
  public void setThreshold(int ct, boolean ignoreGaps)
  {
    threshold = ct;
    if (ignoreGaps)
    {
      this.ignoreGaps = AAFrequency.PID_NOGAPS;
    }
    else
    {
      this.ignoreGaps = AAFrequency.PID_GAPS;
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
  public boolean aboveThreshold(char c, int j)
  {
    if ('a' <= c && c <= 'z')
    {
      // TO UPPERCASE !!!
      // Faster than toUpperCase
      c -= ('a' - 'A');
    }

    if (consensus == null || consensus.length < j || consensus[j] == null)
    {
      return false;
    }

    if ((((Integer) consensus[j].get(AAFrequency.MAXCOUNT)).intValue() != -1)
            && consensus[j].contains(String.valueOf(c)))
    {
      if (((Float) consensus[j].get(ignoreGaps)).floatValue() >= threshold)
      {
        return true;
      }
    }

    return false;
  }

  public boolean conservationApplied()
  {
    return conservationColouring;
  }

  public void setConservationInc(int i)
  {
    inc = i;
  }

  public int getConservationInc()
  {
    return inc;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param consensus
   *          DOCUMENT ME!
   */
  public void setConsensus(Hashtable[] consensus)
  {
    if (consensus == null)
    {
      return;
    }

    this.consensus = consensus;
  }

  public void setConservation(Conservation cons)
  {
    if (cons == null)
    {
      conservationColouring = false;
      conservation = null;
    }
    else
    {
      conservationColouring = true;
      int i, iSize = cons.getConsSequence().getLength();
      conservation = new char[iSize];
      for (i = 0; i < iSize; i++)
      {
        conservation[i] = cons.getConsSequence().getCharAt(i);
      }
      conservationLength = conservation.length;
    }

  }

  /**
   * DOCUMENT ME!
   * 
   * @param s
   *          DOCUMENT ME!
   * @param i
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */

  Color applyConservation(Color currentColour, int i)
  {

    if ((conservationLength > i) && (conservation[i] != '*')
            && (conservation[i] != '+'))
    {
      if (jalview.util.Comparison.isGap(conservation[i]))
      {
        currentColour = Color.white;
      }
      else
      {
        float t = 11 - (conservation[i] - '0');
        if (t == 0)
        {
          return Color.white;
        }

        int red = currentColour.getRed();
        int green = currentColour.getGreen();
        int blue = currentColour.getBlue();

        int dr = 255 - red;
        int dg = 255 - green;
        int db = 255 - blue;

        dr *= t / 10f;
        dg *= t / 10f;
        db *= t / 10f;

        red += (inc / 20f) * dr;
        green += (inc / 20f) * dg;
        blue += (inc / 20f) * db;

        if (red > 255 || green > 255 || blue > 255)
        {
          currentColour = Color.white;
        }
        else
        {
          currentColour = new Color(red, green, blue);
        }
      }
    }
    return currentColour;
  }

  @Override
  public void alignmentChanged(AnnotatedCollectionI alignment,
          Map<SequenceI, SequenceCollectionI> hiddenReps)
  {
  }

}
