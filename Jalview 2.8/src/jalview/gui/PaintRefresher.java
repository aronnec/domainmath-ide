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

import java.util.*;
import java.util.List;

import java.awt.*;

import jalview.datamodel.*;

/**
 * Route datamodel/view update events for a sequence set to any display components involved
 * TODO: JV3 refactor to abstract gui/view package
 * 
 * @author $author$
 * @version $Revision$
 */
public class PaintRefresher
{
  static Hashtable components;

  /**
   * DOCUMENT ME!
   * 
   * @param comp
   *          DOCUMENT ME!
   * @param al
   *          DOCUMENT ME!
   */
  public static void Register(Component comp, String seqSetId)
  {
    if (components == null)
    {
      components = new Hashtable();
    }

    if (components.containsKey(seqSetId))
    {
      Vector comps = (Vector) components.get(seqSetId);
      if (!comps.contains(comp))
      {
        comps.addElement(comp);
      }
    }
    else
    {
      Vector vcoms = new Vector();
      vcoms.addElement(comp);
      components.put(seqSetId, vcoms);
    }
  }

  public static void RemoveComponent(Component comp)
  {
    if (components == null)
    {
      return;
    }

    Enumeration en = components.keys();
    while (en.hasMoreElements())
    {
      String id = en.nextElement().toString();
      Vector comps = (Vector) components.get(id);
      comps.remove(comp);
      if (comps.size() == 0)
      {
        components.remove(id);
      }
    }
  }

  public static void Refresh(Component source, String id)
  {
    Refresh(source, id, false, false);
  }

  public static void Refresh(Component source, String id,
          boolean alignmentChanged, boolean validateSequences)
  {
    if (components == null)
    {
      return;
    }

    Component comp;
    Vector comps = (Vector) components.get(id);

    if (comps == null)
    {
      return;
    }

    Enumeration e = comps.elements();
    while (e.hasMoreElements())
    {
      comp = (Component) e.nextElement();

      if (comp == source)
      {
        continue;
      }

      if (validateSequences && comp instanceof AlignmentPanel
              && source instanceof AlignmentPanel)
      {
        validateSequences(((AlignmentPanel) source).av.getAlignment(),
                ((AlignmentPanel) comp).av.getAlignment());
      }

      if (comp instanceof AlignmentPanel && alignmentChanged)
      {
        ((AlignmentPanel) comp).alignmentChanged();
      }

      comp.repaint();
    }
  }

  static void validateSequences(AlignmentI source, AlignmentI comp)
  {
    SequenceI[] a1;
    if (source.getHiddenSequences().getSize() > 0)
    {
      a1 = source.getHiddenSequences().getFullAlignment()
              .getSequencesArray();
    }
    else
    {
      a1 = source.getSequencesArray();
    }

    SequenceI[] a2;
    if (comp.getHiddenSequences().getSize() > 0)
    {
      a2 = comp.getHiddenSequences().getFullAlignment().getSequencesArray();
    }
    else
    {
      a2 = comp.getSequencesArray();
    }

    int i, iSize = a1.length, j, jSize = a2.length;

    if (iSize == jSize)
    {
      return;
    }

    boolean exists = false;
    for (i = 0; i < iSize; i++)
    {
      exists = false;

      for (j = 0; j < jSize; j++)
      {
        if (a2[j] == a1[i])
        {
          exists = true;
          break;
        }
      }

      if (!exists)
      {
        if (i < comp.getHeight())
        {
          // TODO: the following does not trigger any recalculation of
          // height/etc, or maintain the dataset
          if (comp.getDataset() != source.getDataset())
          {
            // raise an implementation warning here - not sure if this situation
            // will ever occur
            System.err
                    .println("IMPLEMENTATION PROBLEM: DATASET out of sync due to an insert whilst calling PaintRefresher.validateSequences(AlignmentI, ALignmentI)");
          }
          List<SequenceI> alsq;
          synchronized (alsq = comp.getSequences())
          {
            alsq.add(i, a1[i]);
          }
        }
        else
        {
          comp.addSequence(a1[i]);
        }

        if (comp.getHiddenSequences().getSize() > 0)
        {
          a2 = comp.getHiddenSequences().getFullAlignment()
                  .getSequencesArray();
        }
        else
        {
          a2 = comp.getSequencesArray();
        }

        jSize = a2.length;
      }
    }

    iSize = a1.length;
    jSize = a2.length;

    for (j = 0; j < jSize; j++)
    {
      exists = false;
      for (i = 0; i < iSize; i++)
      {
        if (a2[j] == a1[i])
        {
          exists = true;
          break;
        }
      }

      if (!exists)
      {
        comp.deleteSequence(a2[j]);
      }
    }
  }

  static AlignmentPanel[] getAssociatedPanels(String id)
  {
    if (components == null)
    {
      return new AlignmentPanel[0];
    }
    ;
    Vector comps = (Vector) components.get(id);
    if (comps == null)
    {
      return new AlignmentPanel[0];
    }
    ;
    Vector tmp = new Vector();
    int i, iSize = comps.size();
    for (i = 0; i < iSize; i++)
    {
      if (comps.elementAt(i) instanceof AlignmentPanel)
      {
        tmp.addElement(comps.elementAt(i));
      }
    }
    AlignmentPanel[] result = new AlignmentPanel[tmp.size()];
    tmp.toArray(result);

    return result;
  }

}
