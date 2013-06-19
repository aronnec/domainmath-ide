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

import java.awt.Container;
import java.util.BitSet;

import jalview.api.AlignmentViewPanel;
import jalview.bin.Cache;
import jalview.datamodel.PDBEntry;
import jalview.datamodel.SequenceI;
import jalview.structure.StructureSelectionManager;

import org.jmol.api.JmolAppConsoleInterface;
import org.jmol.api.JmolViewer;
import org.jmol.popup.JmolPopup;
import org.openscience.jmol.app.jmolpanel.AppConsole;

public class AppJmolBinding extends jalview.ext.jmol.JalviewJmolBinding
{

  /**
   * 
   */
  private AppJmol appJmolWindow;

  public AppJmolBinding(AppJmol appJmol, StructureSelectionManager sSm,
          PDBEntry[] pdbentry, SequenceI[][] sequenceIs, String[][] chains,
          String protocol)
  {
    super(sSm, pdbentry, sequenceIs, chains, protocol);
    appJmolWindow = appJmol;
  }

  FeatureRenderer fr = null;

  @Override
  public jalview.api.FeatureRenderer getFeatureRenderer(
          AlignmentViewPanel alignment)
  {
    AlignmentPanel ap = (alignment == null) ? appJmolWindow.ap
            : (AlignmentPanel) alignment;
    if (ap.av.showSequenceFeatures)
    {
      if (fr == null)
      {
        fr = ap.cloneFeatureRenderer();
      }
      else
      {
        ap.updateFeatureRenderer(fr);
      }
    }

    return fr;
  }

  @Override
  public jalview.api.SequenceRenderer getSequenceRenderer(
          AlignmentViewPanel alignment)
  {
    return new SequenceRenderer(((AlignmentPanel) alignment).av);
  }

  public void sendConsoleEcho(String strEcho)
  {
    if (console != null)
    {
      console.sendConsoleEcho(strEcho);
    }
  }

  public void sendConsoleMessage(String strStatus)
  {
    if (console != null && strStatus != null)
    // && !strStatus.equals("Script completed"))
    // should we squash the script completed string ?
    {
      console.sendConsoleMessage(strStatus);
    }
  }

  @Override
  public void showUrl(String url, String target)
  {
    try
    {
      jalview.util.BrowserLauncher.openURL(url);
    } catch (Exception e)
    {
      Cache.log.error("Failed to launch Jmol-associated url " + url, e);
      // TODO: 2.6 : warn user if browser was not configured.
    }
  }

  @Override
  public void refreshGUI()
  {
    // appJmolWindow.repaint();
    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        appJmolWindow.updateTitleAndMenus();
        appJmolWindow.revalidate();
      }
    });
  }

  public void updateColours(Object source)
  {
    AlignmentPanel ap = (AlignmentPanel) source, topap;
    // ignore events from panels not used to colour this view
    if (!appJmolWindow.isUsedforcolourby(ap))
      return;
    if (!isLoadingFromArchive())
    {
      colourBySequence(ap.av.getShowSequenceFeatures(), ap);
    }
  }

  public void notifyScriptTermination(String strStatus, int msWalltime)
  {
    // todo - script termination doesn't happen ?
    // if (console != null)
    // console.notifyScriptTermination(strStatus,
    // msWalltime);
  }

  public void showUrl(String url)
  {
    showUrl(url, "jmol");
  }

  public void newJmolPopup(boolean translateLocale, String menuName,
          boolean asPopup)
  {

    jmolpopup = new JmolPopup();
    jmolpopup.initialize(viewer, translateLocale, menuName, asPopup);
  }

  public void selectionChanged(BitSet arg0)
  {
    // TODO Auto-generated method stub

  }

  public void refreshPdbEntries()
  {
    // TODO Auto-generated method stub

  }

  public void showConsole(boolean b)
  {
    appJmolWindow.showConsole(b);
  }

  /**
   * add the given sequences to the mapping scope for the given pdb file handle
   * 
   * @param pdbFile
   *          - pdbFile identifier
   * @param seq
   *          - set of sequences it can be mapped to
   */
  public void addSequenceForStructFile(String pdbFile, SequenceI[] seq)
  {
    for (int pe = 0; pe < pdbentry.length; pe++)
    {
      if (pdbentry[pe].getFile().equals(pdbFile))
      {
        addSequence(pe, seq);
      }
    }
  }

  @Override
  protected JmolAppConsoleInterface createJmolConsole(JmolViewer viewer2,
          Container consolePanel, String buttonsToShow)
  {
    return new AppConsole(viewer, consolePanel, buttonsToShow);
  }

  @Override
  protected void releaseUIResources()
  {
    appJmolWindow = null;
    if (console != null)
    {
      try
      {
        console.setVisible(false);
      } catch (Error e)
      {
      } catch (Exception x)
      {
      }
      ;
      console = null;
    }

  }

  @Override
  public void releaseReferences(Object svl)
  {
    if (svl instanceof SeqPanel)
    {
      appJmolWindow.removeAlignmentPanel(((SeqPanel) svl).ap);

    }
    ;
  }
}
