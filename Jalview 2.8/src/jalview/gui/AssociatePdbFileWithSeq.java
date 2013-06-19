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
package jalview.gui;

import javax.swing.JOptionPane;

import jalview.datamodel.PDBEntry;
import jalview.datamodel.SequenceI;

/**
 * GUI related routines for associating PDB files with sequences
 * 
 * @author JimP
 * 
 */
public class AssociatePdbFileWithSeq
{

  /**
   * assocate the given PDB file with
   * 
   * @param choice
   * @param sequence
   */
  public PDBEntry associatePdbWithSeq(String choice, String protocol,
          SequenceI sequence, boolean prompt)
  {
    PDBEntry entry = new PDBEntry();
    try
    {
      MCview.PDBfile pdbfile = new MCview.PDBfile(choice, protocol);

      if (pdbfile.id == null)
      {
        String reply = null;

        if (prompt)
        {
          reply = JOptionPane
                  .showInternalInputDialog(
                          Desktop.desktop,
                          "Couldn't find a PDB id in the file supplied."
                                  + "Please enter an Id to identify this structure.",
                          "No PDB Id in File", JOptionPane.QUESTION_MESSAGE);
        }
        if (reply == null)
        {
          return null;
        }

        entry.setId(reply);
      }
      else
      {
        entry.setId(pdbfile.id);
      }
    } catch (java.io.IOException ex)
    {
      ex.printStackTrace();
    }

    entry.setFile(choice);
    sequence.getDatasetSequence().addPDBId(entry);
    return entry;
  }

}
