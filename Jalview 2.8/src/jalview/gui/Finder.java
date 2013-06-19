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

import java.awt.event.*;
import javax.swing.*;

import jalview.datamodel.*;
import jalview.jbgui.*;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class Finder extends GFinder
{
  AlignViewport av;

  AlignmentPanel ap;

  JInternalFrame frame;

  int seqIndex = 0;

  int resIndex = -1;

  SearchResults searchResults;

  /**
   * Creates a new Finder object.
   * 
   * @param av
   *          DOCUMENT ME!
   * @param ap
   *          DOCUMENT ME!
   * @param f
   *          DOCUMENT ME!
   */
  public Finder()
  {
    this(null, null);
    focusfixed = false;
  }

  public Finder(AlignViewport viewport, AlignmentPanel alignPanel)
  {
    av = viewport;
    ap = alignPanel;
    focusfixed = true;
    frame = new JInternalFrame();
    frame.setContentPane(this);
    frame.setLayer(JLayeredPane.PALETTE_LAYER);
    Desktop.addInternalFrame(frame, "Find", 340, 110);

    textfield.requestFocus();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void findNext_actionPerformed(ActionEvent e)
  {
    if (getFocusedViewport())
    {
      doSearch(false);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void findAll_actionPerformed(ActionEvent e)
  {
    if (getFocusedViewport())
    {
      resIndex = -1;
      seqIndex = 0;
      doSearch(true);
    }
  }

  /**
   * do we only search a given alignment view ?
   */
  private boolean focusfixed;

  /**
   * if !focusfixed and not in a desktop environment, checks that av and ap are
   * valid. Otherwise, gets the topmost alignment window and sets av and ap
   * accordingly
   * 
   * @return false if no alignment window was found
   */
  boolean getFocusedViewport()
  {
    if (focusfixed || Desktop.desktop == null)
    {
      if (ap != null && av != null)
      {
        return true;
      }
      // we aren't in a desktop environment, so give up now.
      return false;
    }
    // now checks further down the window stack to fix bug
    // https://mantis.lifesci.dundee.ac.uk/view.php?id=36008
    JInternalFrame[] frames = Desktop.desktop.getAllFrames();
    for (int f = 0; f < frames.length; f++)
    {
      JInternalFrame frame = frames[f];
      if (frame != null && frame instanceof AlignFrame)
      {
        av = ((AlignFrame) frame).viewport;
        ap = ((AlignFrame) frame).alignPanel;
        return true;
      }
    }
    return false;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void createNewGroup_actionPerformed(ActionEvent e)
  {
    SequenceI[] seqs = new SequenceI[searchResults.getSize()];
    SequenceFeature[] features = new SequenceFeature[searchResults
            .getSize()];

    for (int i = 0; i < searchResults.getSize(); i++)
    {
      seqs[i] = searchResults.getResultSequence(i).getDatasetSequence();

      features[i] = new SequenceFeature(textfield.getText().trim(),
              "Search Results", null, searchResults.getResultStart(i),
              searchResults.getResultEnd(i), "Search Results");
    }

    if (ap.seqPanel.seqCanvas.getFeatureRenderer().amendFeatures(seqs,
            features, true, ap))
    {
      ap.alignFrame.showSeqFeatures.setSelected(true);
      av.setShowSequenceFeatures(true);
      ap.highlightSearchResults(null);
    }
  }

  /**
   * incrementally search the alignment
   * 
   * @param findAll
   *          true means find all results and raise a dialog box
   */
  void doSearch(boolean findAll)
  {
    createNewGroup.setEnabled(false);

    String searchString = textfield.getText().trim();

    if (searchString.length() < 1)
    {
      return;
    }
    // TODO: extend finder to match descriptions, features and annotation, and
    // other stuff
    // TODO: add switches to control what is searched - sequences, IDS,
    // descriptions, features
    jalview.analysis.Finder finder = new jalview.analysis.Finder(
            av.getAlignment(), av.getSelectionGroup(), seqIndex, resIndex);
    finder.setCaseSensitive(caseSensitive.isSelected());
    finder.setFindAll(findAll);

    finder.find(searchString); // returns true if anything was actually found

    seqIndex = finder.getSeqIndex();
    resIndex = finder.getResIndex();

    searchResults = finder.getSearchResults(); // find(regex,
    // caseSensitive.isSelected(), )
    Vector idMatch = finder.getIdMatch();
    boolean haveResults = false;
    // set or reset the GUI
    if ((idMatch.size() > 0))
    {
      haveResults = true;
      ap.idPanel.highlightSearchResults(idMatch);
    }
    else
    {
      ap.idPanel.highlightSearchResults(null);
    }

    if (searchResults.getSize() > 0)
    {
      haveResults = true;
      createNewGroup.setEnabled(true);
    }
    else
    {
      searchResults = null;
    }

    // if allResults is null, this effectively switches displaySearch flag in
    // seqCanvas
    ap.highlightSearchResults(searchResults);
    // TODO: add enablers for 'SelectSequences' or 'SelectColumns' or
    // 'SelectRegion' selection
    if (!haveResults)
    {
      JOptionPane.showInternalMessageDialog(this, "Finished searching",
              null, JOptionPane.INFORMATION_MESSAGE);
      resIndex = -1;
      seqIndex = 0;
    }

    if (findAll)
    {
      String message = (idMatch.size() > 0) ? "" + idMatch.size() + " IDs"
              : "";
      if (searchResults != null)
      {
        if (idMatch.size() > 0 && searchResults.getSize() > 0)
        {
          message += " and ";
        }
        message += searchResults.getSize() + " subsequence matches found.";
      }
      JOptionPane.showInternalMessageDialog(this, message, null,
              JOptionPane.INFORMATION_MESSAGE);
      resIndex = -1;
      seqIndex = 0;
    }

  }
}
