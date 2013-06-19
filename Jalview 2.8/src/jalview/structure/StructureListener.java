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

public interface StructureListener
{
  /**
   * 
   * @return list of structure files (unique IDs/filenames) that this listener
   *         handles messages for, or null if generic listener (only used by
   *         removeListener method)
   */
  public String[] getPdbFile();

  /**
   * NOT A LISTENER METHOD! called by structure viewer when the given
   * atom/structure has been moused over. Typically, implementors call
   * StructureSelectionManager.mouseOverStructure
   * 
   * @param atomIndex
   * @param strInfo
   */
  public void mouseOverStructure(int atomIndex, String strInfo);

  /**
   * called by StructureSelectionManager to inform viewer to highlight given
   * atomspec
   * 
   * @param atomIndex
   * @param pdbResNum
   * @param chain
   * @param pdbId
   */
  public void highlightAtom(int atomIndex, int pdbResNum, String chain,
          String pdbId);

  /**
   * called by StructureSelectionManager when the colours of a sequence
   * associated with a structure have changed.
   * 
   * @param source
   *          (untyped) usually an alignPanel
   */
  public void updateColours(Object source);

  /**
   * called by Jalview to get the colour for the given atomspec
   * 
   * @param atomIndex
   * @param pdbResNum
   * @param chain
   * @param pdbId
   * @return
   */
  public java.awt.Color getColour(int atomIndex, int pdbResNum,
          String chain, String pdbId);

  /**
   * called by structureSelectionManager to instruct implementor to release any
   * direct references it may hold to the given object (typically, these are
   * Jalview alignment panels).
   * 
   * @param svl
   */
  public void releaseReferences(Object svl);
}
