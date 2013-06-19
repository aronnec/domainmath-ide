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

import jalview.datamodel.*;

public class StructureMapping
{
  String mappingDetails;

  SequenceI sequence;

  String pdbfile;

  String pdbid;

  String pdbchain;

  // Mapping index 0 is resNum, index 1 is atomNo
  int[][] mapping;

  public StructureMapping(SequenceI seq, String pdbfile, String pdbid,
          String chain, int[][] mapping, String mappingDetails)
  {
    sequence = seq;
    this.pdbfile = pdbfile;
    this.pdbid = pdbid;
    this.pdbchain = chain;
    this.mapping = mapping;
    this.mappingDetails = mappingDetails;
  }

  public SequenceI getSequence()
  {
    return sequence;
  }

  public String getChain()
  {
    return pdbchain;
  }

  public String getPdbId()
  {
    return pdbid;
  }

  public int getAtomNum(int seqpos)
  {
    if (mapping.length > seqpos)
    {
      return mapping[seqpos][1];
    }
    else
    {
      return 0;
    }
  }

  public int getPDBResNum(int seqpos)
  {
    if (mapping.length > seqpos)
    {
      return mapping[seqpos][0];
    }
    else
    {
      return 0;
    }
  }

  public int getSeqPos(int pdbResNum)
  {
    for (int i = 0; i < mapping.length; i++)
    {
      if (mapping[i][0] == pdbResNum)
      {
        return i;
      }
    }
    return -1;
  }
}
