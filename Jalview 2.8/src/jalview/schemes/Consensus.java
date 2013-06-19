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

// //////////////////////////////////////////
// This does nothing at all at the moment!!!!!!!!!!
// AW 15th Dec 2004
// ///////////////////////////////////////
public class Consensus
{
  int[] mask;

  double threshold;

  String maskstr;

  public Consensus(String mask, double threshold)
  {
    // this.id = id;
    // this.mask = mask;
    this.maskstr = mask;
    setMask(mask);
    this.threshold = threshold;
  }

  public void setMask(String s)
  {
    this.mask = setNums(s);

    // for (int i=0; i < mask.length; i++) {
    // System.out.println(mask[i] + " " + ResidueProperties.aa[mask[i]]);
    // }
  }

  /**
   * @deprecated Use {@link #isConserved(int[][],int,int,boolean)} instead
   */
  public boolean isConserved(int[][] cons2, int col, int size)
  {
    return isConserved(cons2, col, size, true);
  }

  public boolean isConserved(int[][] cons2, int col, int size,
          boolean includeGaps)
  {
    int tot = 0;
    if (!includeGaps)
    {
      size -= cons2[col][cons2[col].length - 1];
    }
    for (int i = 0; i < mask.length; i++)
    {
      tot += cons2[col][mask[i]];
    }

    if ((double) tot > ((threshold * size) / 100))
    {
      // System.out.println("True conserved "+tot+" from "+threshold+" out of
      // "+size+" : "+maskstr);
      return true;
    }

    return false;
  }

  int[] setNums(String s)
  {
    int[] out = new int[s.length()];
    int i = 0;

    while (i < s.length())
    {
      out[i] = ResidueProperties.aaIndex[s.charAt(i)];
      i++;
    }

    return out;
  }
}
