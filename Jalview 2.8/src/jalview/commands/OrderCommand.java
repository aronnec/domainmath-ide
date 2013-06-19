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
package jalview.commands;

import jalview.analysis.*;
import jalview.datamodel.*;

public class OrderCommand implements CommandI
{
  String description;

  SequenceI[] seqs;

  SequenceI[] seqs2;

  AlignmentI al;

  public OrderCommand(String description, SequenceI[] seqs, AlignmentI al)
  {
    this.description = description;
    this.seqs = seqs;
    this.seqs2 = al.getSequencesArray();
    this.al = al;
    doCommand(null);
  }

  public String getDescription()
  {
    return description;
  }

  public int getSize()
  {
    return 1;
  }

  public void doCommand(AlignmentI[] views)
  {
    AlignmentSorter.setOrder(al, seqs2);
  }

  public void undoCommand(AlignmentI[] views)
  {
    AlignmentSorter.setOrder(al, seqs);
  }
}
