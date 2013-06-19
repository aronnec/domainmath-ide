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
import jalview.datamodel.SequenceI;

/**
 * Looks at the information computed from an RNA Stockholm format file on the
 * secondary structure of the alignment. Extracts the information on the
 * positions of the helices present and assigns colors.
 * 
 * @author Lauren Michelle Lui
 * @version 2.5
 */
public class RNAHelicesColour extends ResidueColourScheme
{

  /**
   * Stores random colors generated for the number of helices
   */
  public Hashtable helixcolorhash = new Hashtable();

  /**
   * Maps sequence positions to the RNA helix they belong to. Key: position,
   * Value: helix
   */
  public Hashtable positionsToHelix = new Hashtable();

  /**
   * Number of helices in the RNA secondary structure
   */
  int numHelix = 0;

  public AlignmentAnnotation annotation;

  /**
   * Creates a new RNAHelicesColour object.
   */
  public RNAHelicesColour(AlignmentAnnotation annotation)
  {
    super(ResidueProperties.nucleotideIndex);
    this.annotation = annotation;
    refresh();
  }

  private long lastrefresh = -1;

  public void refresh()
  {
    if (lastrefresh != annotation._rnasecstr.hashCode()
            && annotation.isValidStruc())
    {
      annotation.getRNAStruc();
      lastrefresh = annotation._rnasecstr.hashCode();
      numHelix = 0;
      positionsToHelix = new Hashtable();

      // Figure out number of helices
      // Length of rnasecstr is the number of pairs of positions that base pair
      // with each other in the secondary structure
      for (int x = 0; x < this.annotation._rnasecstr.length; x++)
      {

        /*
         * System.out.println(this.annotation._rnasecstr[x] + " Begin" +
         * this.annotation._rnasecstr[x].getBegin());
         */
        // System.out.println(this.annotation._rnasecstr[x].getFeatureGroup());

        positionsToHelix.put(this.annotation._rnasecstr[x].getBegin(),
                this.annotation._rnasecstr[x].getFeatureGroup());
        positionsToHelix.put(this.annotation._rnasecstr[x].getEnd(),
                this.annotation._rnasecstr[x].getFeatureGroup());

        if (Integer.parseInt(this.annotation._rnasecstr[x]
                .getFeatureGroup()) > numHelix)
        {
          numHelix = Integer.parseInt(this.annotation._rnasecstr[x]
                  .getFeatureGroup());
        }

      }

      // Generate random colors and store
      for (int j = 0; j <= numHelix; j++)
      {
        if (!helixcolorhash.containsKey(Integer.toString(j)))
        {
          helixcolorhash.put(Integer.toString(j),
                  jalview.util.ColorUtils.generateRandomColor(Color.white));
        }
      }
    }
  }

  /**
   * Returns default color base on purinepyrimidineIndex in
   * jalview.schemes.ResidueProperties (Allows coloring in sequence logo)
   * 
   * @param c
   *          Character in sequence
   * 
   * @return color in RGB
   */
  @Override
  public Color findColour(char c)
  {
    return ResidueProperties.purinepyrimidine[ResidueProperties.purinepyrimidineIndex[c]];
    // random colors for all positions
    // jalview.util.ColorUtils.generateRandomColor(Color.white); If you want
  }

  /**
   * Returns color based on helices
   * 
   * @param c
   *          Character in sequence
   * @param j
   *          Threshold
   * 
   * @return Color in RGB
   */
  @Override
  public Color findColour(char c, int j, SequenceI seq)
  {
    refresh();
    Color currentColour = Color.white;
    String currentHelix = null;
    currentHelix = (String) positionsToHelix.get(j);

    if (currentHelix != null)
    {
      currentColour = (Color) helixcolorhash.get(currentHelix);
    }

    // System.out.println(c + " " + j + " helix " + currentHelix + " " +
    // currentColour);
    return currentColour;
  }
}
