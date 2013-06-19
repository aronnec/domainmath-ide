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
package jalview.datamodel;

import java.awt.Color;

/**
 * Holds all annotation values for a position in an AlignmentAnnotation row
 * 
 * @author $author$
 * @version $Revision$
 */
public class Annotation
{
  /** Character label - also shown below histogram */
  public String displayCharacter = "";

  /**
   * Text label for position: shown in mouse over and displayed on secondary
   * structure glyphs
   */
  public String description = "";

  /**
   * Secondary structure symbol: Protein symbols are H, E and S(?), RNA are
   * WUSS/Vienna plus extended pseudoknot symbols
   */
  public char secondaryStructure = ' ';

  /** Score for the position - used in histograms, line graphs and for shading */
  public float value;

  /** Colour for position */
  public Color colour;

  /**
   * Creates a new Annotation object.
   * 
   * @param displayChar
   *          DOCUMENT ME!
   * @param desc
   *          DOCUMENT ME!
   * @param ss
   *          DOCUMENT ME!
   * @param val
   *          DOCUMENT ME!
   */
  public Annotation(String displayChar, String desc, char ss, float val)
  {
    displayCharacter = displayChar;
    description = desc;
    secondaryStructure = ss;
    value = val;
  }

  /**
   * Creates a new Annotation object.
   * 
   * @param displayChar
   *          DOCUMENT ME!
   * @param desc
   *          DOCUMENT ME!
   * @param ss
   *          DOCUMENT ME!
   * @param val
   *          DOCUMENT ME!
   * @param colour
   *          DOCUMENT ME!
   */
  public Annotation(String displayChar, String desc, char ss, float val,
          Color colour)
  {
    this(displayChar, desc, ss, val);
    this.colour = colour;
  }

  /**
   * Copy constructor New annotation takes on the same (or duplicated)
   * attributes as the given template
   * 
   * @param that
   *          template annotation
   */
  public Annotation(Annotation that)
  {
    if (that == null || this == that)
    {
      return;
    }
    if (that.displayCharacter != null)
      displayCharacter = new String(that.displayCharacter);
    if (that.description != null)
      description = new String(that.description);
    secondaryStructure = that.secondaryStructure;
    value = that.value;
    colour = that.colour;
  }

  /**
   * Value only annotation.
   * 
   * @param val
   *          value at this annotation position
   */
  public Annotation(float val)
  {
    this(null, null, ' ', val);
  }

  /**
   * human readable representation of an annotation row element.
   *
   * Format is 'display Char','secondary Structure
   * Char',"description",score,[colourstring]
   *
   * fields may be missing if they are null, whitespace, or equivalent to
   * Float.NaN
   */
  @Override
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    if (displayCharacter != null)
    {
      sb.append("\'");
      sb.append(displayCharacter);
      sb.append("\'");
    }
    {
      sb.append(",");
    }
    if (secondaryStructure != 0
            && !("" + displayCharacter).equals("" + secondaryStructure))
    {
      sb.append("\'");
      sb.append(secondaryStructure);
      sb.append("\'");
    }
    {
      sb.append(",");
    }
    if (description != null && description.length() > 0)
    {
      sb.append("\"");
      sb.append(description);
      sb.append("\"");
    }
    {
      sb.append(",");
    }
    if (value != Float.NaN)
    {
      sb.append(value);
    }
    if (colour != null)
    {
      if (sb.length() > 0)
      {
        sb.append(",");
      }
      sb.append("[");
      sb.append(colour.getRed());
      sb.append(",");
      sb.append(colour.getGreen());
      sb.append(",");
      sb.append(colour.getBlue());
      sb.append("]");
    }
    return sb.toString();
  }
}
