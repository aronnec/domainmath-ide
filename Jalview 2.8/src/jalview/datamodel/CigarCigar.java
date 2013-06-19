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

public class CigarCigar extends CigarSimple
{
  SeqCigar refCigar;

  /**
   * Apply CIGAR operations to the result of another cigar
   * 
   * @param cigar
   *          Cigar
   */
  CigarCigar(SeqCigar cigar)
  {
    super();
    refCigar = cigar;
  }

  /**
   * 
   * @return String formed by applying CIGAR operations to the reference object
   * @param GapChar
   *          char
   * @todo Implement this jalview.datamodel.Cigar method
   */
  public String getSequenceString(char GapChar)
  {
    if (length == 0)
    {
      return "";
    }
    String refString = refCigar.getSequenceString(GapChar);
    if (refString != null)
    {
      return (length == 0) ? "" : (String) getSequenceAndDeletions(
              refString, GapChar)[0];
    }
    else
    {
      return null;
    }
  }

}
