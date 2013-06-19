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

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class SequencePoint
{
  // SMJS PUBLIC
  /**
   * for points with no real physical association with an alignment sequence
   */
  public boolean isPlaceholder = false;

  /**
   * Associated alignment sequence, or dummy sequence object.
   */
  public SequenceI sequence;

  /**
   * array of coordinates in embedded sequence space.
   */
  public float[] coord;

  // SMJS ENDPUBLIC
  public SequencePoint(SequenceI sequence, float[] coord)
  {
    this.sequence = sequence;
    this.coord = coord;
  }
}
