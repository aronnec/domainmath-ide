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
package jalview.structure;

/**
 * Implemented by objects listening for selection events on SelectionSources
 */
public interface SelectionListener
{
  /**
   * method called by the event broadcaster (see
   * jalview.structure.StructureSelectionManager) to pass on a selection event
   * to listeners
   * 
   * @param seqsel
   *          - group of selected sequences
   * @param colsel
   *          - specific columns in selection
   * @param source
   *          - source of the selection event
   */
  public void selection(jalview.datamodel.SequenceGroup seqsel,
          jalview.datamodel.ColumnSelection colsel, SelectionSource source);
}
