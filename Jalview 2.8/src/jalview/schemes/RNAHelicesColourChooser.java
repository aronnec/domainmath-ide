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

import java.util.*;
import java.awt.event.*;

import jalview.api.AlignViewportI;
import jalview.api.AlignmentViewPanel;
import jalview.datamodel.*;
import jalview.schemes.*;

/**
 * Helps generate the colors for RNA secondary structure. Future: add option to
 * change colors based on covariation.
 * 
 * @author Lauren Michelle Lui
 * 
 */
public class RNAHelicesColourChooser
{

  AlignViewportI av;

  AlignmentViewPanel ap;

  ColourSchemeI oldcs;

  Hashtable oldgroupColours;

  jalview.datamodel.AlignmentAnnotation currentAnnotation;

  boolean adjusting = false;

  public RNAHelicesColourChooser(AlignViewportI av,
          final AlignmentViewPanel ap)
  {
    oldcs = av.getGlobalColourScheme();
    if (av.getAlignment().getGroups() != null)
    {
      oldgroupColours = new Hashtable();
      for (SequenceGroup sg : ap.getAlignment().getGroups())
      {
        if (sg.cs != null)
        {
          oldgroupColours.put(sg, sg.cs);
        }
      }
    }
    this.av = av;
    this.ap = ap;

    if (oldcs instanceof RNAHelicesColour)
    {
      RNAHelicesColour rhc = (RNAHelicesColour) oldcs;

    }

    adjusting = true;
    Vector list = new Vector();
    int index = 1;
    for (int i = 0; i < av.getAlignment().getAlignmentAnnotation().length; i++)
    {
      String label = av.getAlignment().getAlignmentAnnotation()[i].label;
      if (!list.contains(label))
        list.addElement(label);
      else
        list.addElement(label + "_" + (index++));
    }

    adjusting = false;

    changeColour();

  }

  void changeColour()
  {
    // Check if combobox is still adjusting
    if (adjusting)
    {
      return;
    }

    currentAnnotation = av.getAlignment().getAlignmentAnnotation()[0];// annotations.getSelectedIndex()];

    RNAHelicesColour rhc = null;

    rhc = new RNAHelicesColour(currentAnnotation);

    av.setGlobalColourScheme(rhc);

    if (av.getAlignment().getGroups() != null)
    {
      for (SequenceGroup sg : ap.getAlignment().getGroups())
      {
        if (sg.cs == null)
        {
          continue;
        }

        sg.cs = new RNAHelicesColour(currentAnnotation);

      }
    }

    ap.paintAlignment(false);
  }

  void reset()
  {
    av.setGlobalColourScheme(oldcs);
    if (av.getAlignment().getGroups() != null)
    {
      for (SequenceGroup sg : ap.getAlignment().getGroups())
      {
        sg.cs = (ColourSchemeI) oldgroupColours.get(sg);
      }
    }
  }

  public void annotations_actionPerformed(ActionEvent e)
  {
    changeColour();
  }

}
