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

import java.awt.Component;

public class OOMWarning implements Runnable
{
  String action = null;

  String instructions = "";

  public static boolean oomInprogress = false;

  Component desktop = null;

  /**
   * Raise an out of memory error.
   * 
   * @param action
   *          - what was going on when OutOfMemory exception occured.
   * @param instance
   *          - Window where the dialog will appear
   * @param oomex
   *          - the actual exception - to be written to stderr or debugger.
   */
  OOMWarning(final String action, final OutOfMemoryError oomex,
          final Component instance)
  {
    if (!oomInprogress)
    {
      oomInprogress = true;
      this.action = action;
      desktop = instance;
      if (oomex != null)
      {
        if (jalview.bin.Cache.log != null)
        {
          jalview.bin.Cache.log
                  .error("Out of Memory when " + action, oomex);
        }
        else
        {
          System.err.println("Out of Memory when " + action);
          oomex.printStackTrace();
        }
      }
      javax.swing.SwingUtilities.invokeLater(this);
      System.gc();
    }
  }

  public OOMWarning(String string, OutOfMemoryError oomerror)
  {
    this(string, oomerror, Desktop.desktop);
  }

  public void run()
  {
    javax.swing.JOptionPane
            .showInternalMessageDialog(
                    desktop,
                    "Out of memory when "
                            + action
                            + "!!"
                            + "\nSee help files for increasing Java Virtual Machine memory.",
                    "Out of memory",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
    // hope that there's enough memory left that no more appear.
    oomInprogress = false;
  }

}
