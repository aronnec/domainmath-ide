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

public class ScoreMatrix
{
  String name;

  /**
   * reference to integer score matrix
   */
  int[][] matrix;

  /**
   * 0 for Protein Score matrix. 1 for dna score matrix
   */
  int type;

  ScoreMatrix(String name, int[][] matrix, int type)
  {
    this.matrix = matrix;
    this.type = type;
  }

  public boolean isDNA()
  {
    return type == 1;
  }

  public boolean isProtein()
  {
    return type == 0;
  }

  public int[][] getMatrix()
  {
    return matrix;
  }

  /**
   * 
   * @param A1
   * @param A2
   * @return score for substituting first char in A1 with first char in A2
   */
  public int getPairwiseScore(String A1, String A2)
  {
    return getPairwiseScore(A1.charAt(0), A2.charAt(0));
  }

  public int getPairwiseScore(char c, char d)
  {
    int pog = 0;

    try
    {
      int a = (type == 0) ? ResidueProperties.aaIndex[c]
              : ResidueProperties.nucleotideIndex[c];
      int b = (type == 0) ? ResidueProperties.aaIndex[d]
              : ResidueProperties.nucleotideIndex[d];

      pog = matrix[a][b];
    } catch (Exception e)
    {
      // System.out.println("Unknown residue in " + A1 + " " + A2);
    }

    return pog;
  }

  /**
   * pretty print the matrix
   */
  public String toString()
  {
    return outputMatrix(false);
  }

  public String outputMatrix(boolean html)
  {
    StringBuffer sb = new StringBuffer();
    int[] symbols = (type == 0) ? ResidueProperties.aaIndex
            : ResidueProperties.nucleotideIndex;
    int symMax = (type == 0) ? ResidueProperties.maxProteinIndex
            : ResidueProperties.maxNucleotideIndex;
    boolean header = true;
    if (html)
    {
      sb.append("<table>");
    }
    for (char sym = 'A'; sym <= 'Z'; sym++)
    {
      if (symbols[sym] >= 0 && symbols[sym] < symMax)
      {
        if (header)
        {
          sb.append(html ? "<tr><td></td>" : "");
          for (char sym2 = 'A'; sym2 <= 'Z'; sym2++)
          {
            if (symbols[sym2] >= 0 && symbols[sym2] < symMax)
            {
              sb.append((html ? "<td>&nbsp;" : "\t") + sym2
                      + (html ? "&nbsp;</td>" : ""));
            }
          }
          header = false;
          sb.append(html ? "</tr>\n" : "\n");
        }
        if (html)
        {
          sb.append("<tr>");
        }
        sb.append((html ? "<td>" : "") + sym + (html ? "</td>" : ""));
        for (char sym2 = 'A'; sym2 <= 'Z'; sym2++)
        {
          if (symbols[sym2] >= 0 && symbols[sym2] < symMax)
          {
            sb.append((html ? "<td>" : "\t")
                    + matrix[symbols[sym]][symbols[sym2]]
                    + (html ? "</td>" : ""));
          }
        }
        sb.append(html ? "</tr>\n" : "\n");
      }
    }
    if (html)
    {
      sb.append("</table>");
    }
    return sb.toString();
  }
}
