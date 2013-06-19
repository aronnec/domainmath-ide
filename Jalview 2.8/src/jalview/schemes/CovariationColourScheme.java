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
import java.util.Hashtable;

import jalview.datamodel.AlignmentAnnotation;

/**
 * Became RNAHelicesColour.java. Placeholder for true covariation color scheme
 * 
 * @author Lauren Michelle Lui
 * @version 2.5
 */
public class CovariationColourScheme extends ResidueColourScheme
{
  public Hashtable helixcolorhash = new Hashtable();

  public Hashtable positionsToHelix = new Hashtable();

  int numHelix = 0;

  public AlignmentAnnotation annotation;

  /**
   * Creates a new CovariationColourScheme object.
   */
  public CovariationColourScheme(AlignmentAnnotation annotation)
  {
    this.annotation = annotation;

    for (int x = 0; x < this.annotation._rnasecstr.length; x++)
    {
      // System.out.println(this.annotation._rnasecstr[x] + " Begin" +
      // this.annotation._rnasecstr[x].getBegin());
      // System.out.println(this.annotation._rnasecstr[x].getFeatureGroup());
      // pairs.put(this.annotation._rnasecstr[x].getBegin(),
      // this.annotation._rnasecstr[x].getEnd());

      positionsToHelix.put(this.annotation._rnasecstr[x].getBegin(),
              this.annotation._rnasecstr[x].getFeatureGroup());
      positionsToHelix.put(this.annotation._rnasecstr[x].getEnd(),
              this.annotation._rnasecstr[x].getFeatureGroup());

      if (Integer.parseInt(this.annotation._rnasecstr[x].getFeatureGroup()) > numHelix)
      {
        numHelix = Integer.parseInt(this.annotation._rnasecstr[x]
                .getFeatureGroup());
      }

    }

    for (int j = 0; j <= numHelix; j++)
    {
      helixcolorhash.put(Integer.toString(j),
              jalview.util.ColorUtils.generateRandomColor(Color.white));
    }

  }

  /**
   * DOCUMENT ME!
   * 
   * @param n
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Color findColour(char c)
  {
    // System.out.println("called"); log.debug
    // Generate a random pastel color

    return ResidueProperties.purinepyrimidine[ResidueProperties.purinepyrimidineIndex[c]];// jalview.util.ColorUtils.generateRandomColor(Color.white);
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
  public Color findColour(char c, int j)
  {
    Color currentColour = Color.white;
    String currentHelix = null;
    // System.out.println(c + " " + j);
    currentHelix = (String) positionsToHelix.get(j);
    // System.out.println(positionsToHelix.get(j));

    if (currentHelix != null)
    {
      currentColour = (Color) helixcolorhash.get(currentHelix);
    }

    // System.out.println(c + " " + j + " helix " + currentHelix + " " +
    // currentColour);
    return currentColour;
  }

}
