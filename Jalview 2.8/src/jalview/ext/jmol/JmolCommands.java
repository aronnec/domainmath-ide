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
package jalview.ext.jmol;

import jalview.api.FeatureRenderer;
import jalview.api.SequenceRenderer;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.SequenceI;
import jalview.structure.StructureMapping;
import jalview.structure.StructureMappingcommandSet;
import jalview.structure.StructureSelectionManager;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Routines for generating Jmol commands for Jalview/Jmol binding another
 * cruisecontrol test.
 * 
 * @author JimP
 * 
 */
public class JmolCommands
{

  /**
   * Jmol utility which constructs the commands to colour chains by the given
   * alignment
   * 
   * @returns Object[] { Object[] { <model being coloured>,
   * 
   */
  public static StructureMappingcommandSet[] getColourBySequenceCommand(
          StructureSelectionManager ssm, String[] files,
          SequenceI[][] sequence, SequenceRenderer sr, FeatureRenderer fr,
          AlignmentI alignment)
  {

    ArrayList<StructureMappingcommandSet> cset = new ArrayList<StructureMappingcommandSet>();

    for (int pdbfnum = 0; pdbfnum < files.length; pdbfnum++)
    {
      StructureMapping[] mapping = ssm.getMapping(files[pdbfnum]);
      StringBuffer command = new StringBuffer();
      StructureMappingcommandSet smc;
      ArrayList<String> str = new ArrayList<String>();

      if (mapping == null || mapping.length < 1)
        continue;

      int lastPos = -1;
      for (int s = 0; s < sequence[pdbfnum].length; s++)
      {
        for (int sp, m = 0; m < mapping.length; m++)
        {
          if (mapping[m].getSequence() == sequence[pdbfnum][s]
                  && (sp = alignment.findIndex(sequence[pdbfnum][s])) > -1)
          {
            SequenceI asp = alignment.getSequenceAt(sp);
            for (int r = 0; r < asp.getLength(); r++)
            {
              // no mapping to gaps in sequence
              if (jalview.util.Comparison.isGap(asp.getCharAt(r)))
              {
                continue;
              }
              int pos = mapping[m].getPDBResNum(asp.findPosition(r));

              if (pos < 1 || pos == lastPos)
                continue;

              lastPos = pos;

              Color col = sr.getResidueBoxColour(sequence[pdbfnum][s], r);

              if (fr != null)
                col = fr.findFeatureColour(col, sequence[pdbfnum][s], r);
              String newSelcom = (mapping[m].getChain() != " " ? ":"
                      + mapping[m].getChain() : "")
                      + "/"
                      + (pdbfnum + 1)
                      + ".1"
                      + ";color["
                      + col.getRed()
                      + ","
                      + col.getGreen()
                      + ","
                      + col.getBlue() + "]";
              if (command.length() > newSelcom.length()
                      && command.substring(
                              command.length() - newSelcom.length())
                              .equals(newSelcom))
              {
                command = JmolCommands.condenseCommand(command, pos);
                continue;
              }
              // TODO: deal with case when buffer is too large for Jmol to parse
              // - execute command and flush

              command.append(";");
              if (command.length() > 51200)
              {
                // add another chunk
                str.add(command.toString());
                command.setLength(0);
              }
              command.append("select " + pos);
              command.append(newSelcom);
            }
            break;
          }
        }
      }
      {
        // add final chunk
        str.add(command.toString());
        command.setLength(0);
      }
      // Finally, add the command set ready to be returned.
      cset.add(new StructureMappingcommandSet(JmolCommands.class,
              files[pdbfnum], str.toArray(new String[str.size()])));

    }
    return cset.toArray(new StructureMappingcommandSet[cset.size()]);
  }

  public static StringBuffer condenseCommand(StringBuffer command, int pos)
  {

    // work back to last 'select'
    int p = command.length(), q = p;
    do
    {
      p -= 6;
      if (p < 1)
      {
        p = 0;
      }
      ;
    } while ((q = command.indexOf("select", p)) == -1 && p > 0);

    StringBuffer sb = new StringBuffer(command.substring(0, q + 7));

    command = command.delete(0, q + 7);

    String start;

    if (command.indexOf("-") > -1)
    {
      start = command.substring(0, command.indexOf("-"));
    }
    else
    {
      start = command.substring(0, command.indexOf(":"));
    }

    sb.append(start + "-" + pos + command.substring(command.indexOf(":")));

    return sb;
  }

}
