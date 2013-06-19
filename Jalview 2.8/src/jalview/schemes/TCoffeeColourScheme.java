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

import jalview.analysis.SequenceIdMatcher;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.AnnotatedCollectionI;
import jalview.datamodel.Annotation;
import jalview.datamodel.SequenceCollectionI;
import jalview.datamodel.SequenceI;
import jalview.io.TCoffeeScoreFile;

import java.awt.Color;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Defines the color score for T-Coffee MSA
 * <p>
 * See http://tcoffee.org
 * 
 * 
 * @author Paolo Di Tommaso
 * 
 */
public class TCoffeeColourScheme extends ResidueColourScheme
{

  static final Color[] colors =
  { new Color(102, 102, 255), // #6666FF
      new Color(0, 255, 0), // #00FF00
      new Color(102, 255, 0), // #66FF00
      new Color(204, 255, 0), // #CCFF00
      new Color(255, 255, 0), // #FFFF00
      new Color(255, 204, 0), // #FFCC00
      new Color(255, 153, 0), // #FF9900
      new Color(255, 102, 0), // #FF6600
      new Color(255, 51, 0), // #FF3300
      new Color(255, 34, 0) // #FF2000
  };

  IdentityHashMap<SequenceI, Color[]> seqMap;

  /**
   * the color scheme needs to look at the alignment to get and cache T-COFFEE
   * scores
   * 
   * @param alignment
   *          - annotated sequences to be searched
   */
  public TCoffeeColourScheme(AnnotatedCollectionI alignment)
  {
    alignmentChanged(alignment, null);
  }

  @Override
  public void alignmentChanged(AnnotatedCollectionI alignment,
          Map<SequenceI, SequenceCollectionI> hiddenReps)
  {
    // TODO: if sequences have been represented and they have scores, could
    // compute an average sequence score for the representative

    // assume only one set of TCOFFEE scores - but could have more than one
    // potentially.
    ArrayList<AlignmentAnnotation> annots = new ArrayList<AlignmentAnnotation>();
    // Search alignment to get all tcoffee annotation and pick one set of
    // annotation to use to colour seqs.
    seqMap = new IdentityHashMap<SequenceI, Color[]>();
    int w = 0;
    for (AlignmentAnnotation al : alignment
            .findAnnotation(TCoffeeScoreFile.TCOFFEE_SCORE))
    {
      if (al.sequenceRef != null && !al.belowAlignment)
      {
        annots.add(al);
        if (w < al.annotations.length)
        {
          w = al.annotations.length;
        }
        Color[] scores = new Color[al.annotations.length];
        int i = 0;
        for (Annotation an : al.annotations)
        {
          scores[i++] = (an != null) ? an.colour : Color.white;
        }
        seqMap.put(al.sequenceRef, scores);
      }
    }
    // TODO: compute average colour for each symbol type in each column - gives
    // a second order colourscheme for colouring a sequence logo derived from
    // the alignment (colour reflects quality of alignment for each residue
    // class)
  }

  @Override
  public Color findColour(char c, int j, SequenceI seq)
  {
    Color[] cols;

    if (seqMap == null || (cols = seqMap.get(seq)) == null)
    {
      // see above TODO about computing a colour for each residue in each
      // column: cc = _rcols[i][indexFor[c]];
      return Color.white;
    }

    if (j < 0 || j >= cols.length)
    {
      return Color.white;
    }
    return cols[j];
  }
}
