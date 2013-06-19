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

import jalview.analysis.AAFrequency;
import jalview.analysis.AlignmentSorter;
import jalview.analysis.Conservation;
import jalview.analysis.CrossRef;
import jalview.analysis.NJTree;
import jalview.analysis.ParseProperties;
import jalview.analysis.SequenceIdMatcher;
import jalview.bin.Cache;
import jalview.commands.CommandI;
import jalview.commands.EditCommand;
import jalview.commands.OrderCommand;
import jalview.commands.RemoveGapColCommand;
import jalview.commands.RemoveGapsCommand;
import jalview.commands.SlideSequencesCommand;
import jalview.commands.TrimRegionCommand;
import jalview.datamodel.AlignedCodonFrame;
import jalview.datamodel.Alignment;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.AlignmentOrder;
import jalview.datamodel.AlignmentView;
import jalview.datamodel.ColumnSelection;
import jalview.datamodel.PDBEntry;
import jalview.datamodel.SeqCigar;
import jalview.datamodel.Sequence;
import jalview.datamodel.SequenceGroup;
import jalview.datamodel.SequenceI;
import jalview.io.AlignmentProperties;
import jalview.io.AnnotationFile;
import jalview.io.FeaturesFile;
import jalview.io.FileLoader;
import jalview.io.FormatAdapter;
import jalview.io.HTMLOutput;
import jalview.io.IdentifyFile;
import jalview.io.JalviewFileChooser;
import jalview.io.JalviewFileView;
import jalview.io.JnetAnnotationMaker;
import jalview.io.NewickFile;
import jalview.io.TCoffeeScoreFile;
import jalview.jbgui.GAlignFrame;
import jalview.schemes.Blosum62ColourScheme;
import jalview.schemes.BuriedColourScheme;
import jalview.schemes.ClustalxColourScheme;
import jalview.schemes.ColourSchemeI;
import jalview.schemes.ColourSchemeProperty;
import jalview.schemes.HelixColourScheme;
import jalview.schemes.HydrophobicColourScheme;
import jalview.schemes.NucleotideColourScheme;
import jalview.schemes.PIDColourScheme;
import jalview.schemes.PurinePyrimidineColourScheme;
import jalview.schemes.RNAHelicesColourChooser;
import jalview.schemes.ResidueProperties;
import jalview.schemes.StrandColourScheme;
import jalview.schemes.TCoffeeColourScheme;
import jalview.schemes.TaylorColourScheme;
import jalview.schemes.TurnColourScheme;
import jalview.schemes.UserColourScheme;
import jalview.schemes.ZappoColourScheme;
import jalview.ws.jws1.Discoverer;
import jalview.ws.jws2.Jws2Discoverer;
import jalview.ws.seqfetcher.DbSourceProxy;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import javax.help.BadIDException;
import javax.help.HelpSetException;
import javax.help.UnsupportedOperationException;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import org.domainmath.gui.MainFrame;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class AlignFrame extends GAlignFrame implements DropTargetListener,
        IProgressIndicator
{
 
  /** DOCUMENT ME!! */
  public static final int DEFAULT_WIDTH = 700;

  /** DOCUMENT ME!! */
  public static final int DEFAULT_HEIGHT = 500;

  public AlignmentPanel alignPanel;

  AlignViewport viewport;

  Vector alignPanels = new Vector();

  /**
   * Last format used to load or save alignments in this window
   */
  String currentFileFormat = null;

  /**
   * Current filename for this alignment
   */
  String fileName = null;

  /**
   * Creates a new AlignFrame object with specific width and height.
   * 
   * @param al
   * @param width
   * @param height
   */
  public AlignFrame(AlignmentI al, int width, int height)
  {
    this(al, null, width, height);
  }

  /**
   * Creates a new AlignFrame object with specific width, height and
   * sequenceSetId
   * 
   * @param al
   * @param width
   * @param height
   * @param sequenceSetId
   */
  public AlignFrame(AlignmentI al, int width, int height,
          String sequenceSetId)
  {
    this(al, null, width, height, sequenceSetId);
  }

  /**
   * Creates a new AlignFrame object with specific width, height and
   * sequenceSetId
   * 
   * @param al
   * @param width
   * @param height
   * @param sequenceSetId
   * @param viewId
   */
  public AlignFrame(AlignmentI al, int width, int height,
          String sequenceSetId, String viewId)
  {
    this(al, null, width, height, sequenceSetId, viewId);
  }

  /**
   * new alignment window with hidden columns
   * 
   * @param al
   *          AlignmentI
   * @param hiddenColumns
   *          ColumnSelection or null
   * @param width
   *          Width of alignment frame
   * @param height
   *          height of frame.
   */
  public AlignFrame(AlignmentI al, ColumnSelection hiddenColumns,
          int width, int height)
  {
    this(al, hiddenColumns, width, height, null);
  }

  /**
   * Create alignment frame for al with hiddenColumns, a specific width and
   * height, and specific sequenceId
   * 
   * @param al
   * @param hiddenColumns
   * @param width
   * @param height
   * @param sequenceSetId
   *          (may be null)
   */
  public AlignFrame(AlignmentI al, ColumnSelection hiddenColumns,
          int width, int height, String sequenceSetId)
  {
    this(al, hiddenColumns, width, height, sequenceSetId, null);
  }

  /**
   * Create alignment frame for al with hiddenColumns, a specific width and
   * height, and specific sequenceId
   * 
   * @param al
   * @param hiddenColumns
   * @param width
   * @param height
   * @param sequenceSetId
   *          (may be null)
   * @param viewId
   *          (may be null)
   */
  public AlignFrame(AlignmentI al, ColumnSelection hiddenColumns,
          int width, int height, String sequenceSetId, String viewId)
  {
    setSize(width, height);
    viewport = new AlignViewport(al, hiddenColumns, sequenceSetId, viewId);

    alignPanel = new AlignmentPanel(this, viewport);

    if (al.getDataset() == null)
    {
      al.setDataset(null);
    }

    addAlignmentPanel(alignPanel, true);
    init();
  }

  /**
   * Make a new AlignFrame from exisiting alignmentPanels
   * 
   * @param ap
   *          AlignmentPanel
   * @param av
   *          AlignViewport
   */
  public AlignFrame(AlignmentPanel ap)
  {
    viewport = ap.av;
    alignPanel = ap;
    addAlignmentPanel(ap, false);
    init();
  }

  /**
   * initalise the alignframe from the underlying viewport data and the
   * configurations
   */
  void init()
  {
    if (viewport.getAlignmentConservationAnnotation() == null)
    {
      BLOSUM62Colour.setEnabled(false);
      conservationMenuItem.setEnabled(false);
      modifyConservation.setEnabled(false);
      // PIDColour.setEnabled(false);
      // abovePIDThreshold.setEnabled(false);
      // modifyPID.setEnabled(false);
    }

    String sortby = jalview.bin.Cache.getDefault("SORT_ALIGNMENT",
            "No sort");

    if (sortby.equals("Id"))
    {
      sortIDMenuItem_actionPerformed(null);
    }
    else if (sortby.equals("Pairwise Identity"))
    {
      sortPairwiseMenuItem_actionPerformed(null);
    }

    if (Desktop.desktop != null)
    {
      this.setDropTarget(new java.awt.dnd.DropTarget(this, this));
      addServiceListeners();
      setGUINucleotide(viewport.getAlignment().isNucleotide());
    }

    setMenusFromViewport(viewport);
    buildSortByAnnotationScoresMenu();
    if (viewport.wrapAlignment)
    {
      wrapMenuItem_actionPerformed(null);
    }

    if (jalview.bin.Cache.getDefault("SHOW_OVERVIEW", false))
    {
      this.overviewMenuItem_actionPerformed(null);
    }

    addKeyListener();

  }

  /**
   * Change the filename and format for the alignment, and enable the 'reload'
   * button functionality.
   * 
   * @param file
   *          valid filename
   * @param format
   *          format of file
   */
  public void setFileName(String file, String format)
  {
    fileName = file;
    currentFileFormat = format;
    reload.setEnabled(true);
  }

  void addKeyListener()
  {
    addKeyListener(new KeyAdapter()
    {
      @Override
      public void keyPressed(KeyEvent evt)
      {
        if (viewport.cursorMode
                && ((evt.getKeyCode() >= KeyEvent.VK_0 && evt.getKeyCode() <= KeyEvent.VK_9) || (evt
                        .getKeyCode() >= KeyEvent.VK_NUMPAD0 && evt
                        .getKeyCode() <= KeyEvent.VK_NUMPAD9))
                && Character.isDigit(evt.getKeyChar())) {
              alignPanel.seqPanel.numberPressed(evt.getKeyChar());
          }

        switch (evt.getKeyCode())
        {

        case 27: // escape key
          deselectAllSequenceMenuItem_actionPerformed(null);

          break;

        case KeyEvent.VK_DOWN:
          if (evt.isAltDown() || !viewport.cursorMode) {
              moveSelectedSequences(false);
          }
          if (viewport.cursorMode) {
              alignPanel.seqPanel.moveCursor(0, 1);
          }
          break;

        case KeyEvent.VK_UP:
          if (evt.isAltDown() || !viewport.cursorMode) {
              moveSelectedSequences(true);
          }
          if (viewport.cursorMode) {
              alignPanel.seqPanel.moveCursor(0, -1);
          }

          break;

        case KeyEvent.VK_LEFT:
          if (evt.isAltDown() || !viewport.cursorMode) {
              slideSequences(false, alignPanel.seqPanel.getKeyboardNo1());
          }
          else {
              alignPanel.seqPanel.moveCursor(-1, 0);
          }

          break;

        case KeyEvent.VK_RIGHT:
          if (evt.isAltDown() || !viewport.cursorMode) {
              slideSequences(true, alignPanel.seqPanel.getKeyboardNo1());
          }
          else {
              alignPanel.seqPanel.moveCursor(1, 0);
          }
          break;

        case KeyEvent.VK_SPACE:
          if (viewport.cursorMode)
          {
            alignPanel.seqPanel.insertGapAtCursor(evt.isControlDown()
                    || evt.isShiftDown() || evt.isAltDown());
          }
          break;

        // case KeyEvent.VK_A:
        // if (viewport.cursorMode)
        // {
        // alignPanel.seqPanel.insertNucAtCursor(false,"A");
        // //System.out.println("A");
        // }
        // break;
        /*
         * case KeyEvent.VK_CLOSE_BRACKET: if (viewport.cursorMode) {
         * System.out.println("closing bracket"); } break;
         */
        case KeyEvent.VK_DELETE:
        case KeyEvent.VK_BACK_SPACE:
          if (!viewport.cursorMode)
          {
            cut_actionPerformed(null);
          }
          else
          {
            alignPanel.seqPanel.deleteGapAtCursor(evt.isControlDown()
                    || evt.isShiftDown() || evt.isAltDown());
          }

          break;

        case KeyEvent.VK_S:
          if (viewport.cursorMode)
          {
            alignPanel.seqPanel.setCursorRow();
          }
          break;
        case KeyEvent.VK_C:
          if (viewport.cursorMode && !evt.isControlDown())
          {
            alignPanel.seqPanel.setCursorColumn();
          }
          break;
        case KeyEvent.VK_P:
          if (viewport.cursorMode)
          {
            alignPanel.seqPanel.setCursorPosition();
          }
          break;

        case KeyEvent.VK_ENTER:
        case KeyEvent.VK_COMMA:
          if (viewport.cursorMode)
          {
            alignPanel.seqPanel.setCursorRowAndColumn();
          }
          break;

        case KeyEvent.VK_Q:
          if (viewport.cursorMode)
          {
            alignPanel.seqPanel.setSelectionAreaAtCursor(true);
          }
          break;
        case KeyEvent.VK_M:
          if (viewport.cursorMode)
          {
            alignPanel.seqPanel.setSelectionAreaAtCursor(false);
          }
          break;

        case KeyEvent.VK_F2:
          viewport.cursorMode = !viewport.cursorMode;
          statusBar.setText("Keyboard editing mode is "
                  + (viewport.cursorMode ? "on" : "off"));
          if (viewport.cursorMode)
          {
            alignPanel.seqPanel.seqCanvas.cursorX = viewport.startRes;
            alignPanel.seqPanel.seqCanvas.cursorY = viewport.startSeq;
          }
          alignPanel.seqPanel.seqCanvas.repaint();
          break;

        case KeyEvent.VK_F1:
          try
          {
            ClassLoader cl = jalview.gui.Desktop.class.getClassLoader();
            java.net.URL url = javax.help.HelpSet.findHelpSet(cl,
                    "help/help");
            javax.help.HelpSet hs = new javax.help.HelpSet(cl, url);

            javax.help.HelpBroker hb = hs.createHelpBroker();
            hb.setCurrentID("home");
            hb.setDisplayed(true);
          } catch (HelpSetException | BadIDException | UnsupportedOperationException ex)
          {
          }
          break;
        case KeyEvent.VK_H:
        {
          boolean toggleSeqs = !evt.isControlDown();
          boolean toggleCols = !evt.isShiftDown();
          toggleHiddenRegions(toggleSeqs, toggleCols);
          break;
        }
        case KeyEvent.VK_PAGE_UP:
          if (viewport.wrapAlignment)
          {
            alignPanel.scrollUp(true);
          }
          else
          {
            alignPanel.setScrollValues(viewport.startRes, viewport.startSeq
                    - viewport.endSeq + viewport.startSeq);
          }
          break;
        case KeyEvent.VK_PAGE_DOWN:
          if (viewport.wrapAlignment)
          {
            alignPanel.scrollUp(false);
          }
          else
          {
            alignPanel.setScrollValues(viewport.startRes, viewport.startSeq
                    + viewport.endSeq - viewport.startSeq);
          }
          break;
        }
      }

      @Override
      public void keyReleased(KeyEvent evt)
      {
        switch (evt.getKeyCode())
        {
        case KeyEvent.VK_LEFT:
          if (evt.isAltDown() || !viewport.cursorMode) {
              viewport.firePropertyChange("alignment", null, viewport
                      .getAlignment().getSequences());
          }
          break;

        case KeyEvent.VK_RIGHT:
          if (evt.isAltDown() || !viewport.cursorMode) {
              viewport.firePropertyChange("alignment", null, viewport
                      .getAlignment().getSequences());
          }
          break;
        }
      }
    });
  }

  public void addAlignmentPanel(final AlignmentPanel ap, boolean newPanel)
  {
    ap.alignFrame = this;

    alignPanels.addElement(ap);

    PaintRefresher.Register(ap, ap.av.getSequenceSetId());

    int aSize = alignPanels.size();

    tabbedPane.setVisible(aSize > 1 || ap.av.viewName != null);

    if (aSize == 1 && ap.av.viewName == null)
    {
      this.getContentPane().add(ap, BorderLayout.CENTER);
    }
    else
    {
      if (aSize == 2)
      {
        setInitialTabVisible();
      }

      expandViews.setEnabled(true);
      gatherViews.setEnabled(true);
      tabbedPane.addTab(ap.av.viewName, ap);

      ap.setVisible(false);
    }

    if (newPanel)
    {
      if (ap.av.isPadGaps())
      {
        ap.av.getAlignment().padGaps();
      }
      ap.av.updateConservation(ap);
      ap.av.updateConsensus(ap);
      ap.av.updateStrucConsensus(ap);
    }
  }

  public void setInitialTabVisible()
  {
    expandViews.setEnabled(true);
    gatherViews.setEnabled(true);
    tabbedPane.setVisible(true);
    AlignmentPanel first = (AlignmentPanel) alignPanels.firstElement();
    tabbedPane.addTab(first.av.viewName, first);
    this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
  }

  public AlignViewport getViewport()
  {
    return viewport;
  }

  /* Set up intrinsic listeners for dynamically generated GUI bits. */
  private void addServiceListeners()
  {
    final java.beans.PropertyChangeListener thisListener;
    Desktop.instance.addJalviewPropertyChangeListener("services",
            thisListener = new java.beans.PropertyChangeListener()
            {
              @Override
              public void propertyChange(PropertyChangeEvent evt)
              {
                // // System.out.println("Discoverer property change.");
                // if (evt.getPropertyName().equals("services"))
                {
                  SwingUtilities.invokeLater(new Runnable()
                  {

                    @Override
                    public void run()
                    {
                      System.err
                              .println("Rebuild WS Menu for service change");
                      BuildWebServiceMenu();
                    }

                  });
                }
              }
            });
    addInternalFrameListener(new javax.swing.event.InternalFrameAdapter()
    {
      @Override
      public void internalFrameClosed(
              javax.swing.event.InternalFrameEvent evt)
      {
        System.out.println("deregistering discoverer listener");
        Desktop.instance.removeJalviewPropertyChangeListener("services",
                thisListener);
        closeMenuItem_actionPerformed(true);
      };
    });
    // Finally, build the menu once to get current service state
    new Thread(new Runnable()
    {
      @Override
      public void run()
      {
        BuildWebServiceMenu();
      }
    }).start();
  }

  public void setGUINucleotide(boolean nucleotide)
  {
    showTranslation.setVisible(nucleotide);
    conservationMenuItem.setEnabled(!nucleotide);
    modifyConservation.setEnabled(!nucleotide);
    showGroupConservation.setEnabled(!nucleotide);
    rnahelicesColour.setEnabled(nucleotide);
    purinePyrimidineColour.setEnabled(nucleotide);
    // Remember AlignFrame always starts as protein
    // if (!nucleotide)
    // {
    // showTr
    // calculateMenu.remove(calculateMenu.getItemCount() - 2);
    // }
  }

  /**
   * set up menus for the currently viewport. This may be called after any
   * operation that affects the data in the current view (selection changed,
   * etc) to update the menus to reflect the new state.
   */
  public void setMenusForViewport()
  {
    setMenusFromViewport(viewport);
  }

  /**
   * Need to call this method when tabs are selected for multiple views, or when
   * loading from Jalview2XML.java
   * 
   * @param av
   *          AlignViewport
   */
  void setMenusFromViewport(AlignViewport av)
  {
    padGapsMenuitem.setSelected(av.isPadGaps());
    colourTextMenuItem.setSelected(av.showColourText);
    abovePIDThreshold.setSelected(av.getAbovePIDThreshold());
    conservationMenuItem.setSelected(av.getConservationSelected());
    seqLimits.setSelected(av.getShowJVSuffix());
    idRightAlign.setSelected(av.rightAlignIds);
    centreColumnLabelsMenuItem.setState(av.centreColumnLabels);
    renderGapsMenuItem.setSelected(av.renderGaps);
    wrapMenuItem.setSelected(av.wrapAlignment);
    scaleAbove.setVisible(av.wrapAlignment);
    scaleLeft.setVisible(av.wrapAlignment);
    scaleRight.setVisible(av.wrapAlignment);
    annotationPanelMenuItem.setState(av.showAnnotation);
    viewBoxesMenuItem.setSelected(av.showBoxes);
    viewTextMenuItem.setSelected(av.showText);
    showNonconservedMenuItem.setSelected(av.getShowUnconserved());
    showGroupConsensus.setSelected(av.isShowGroupConsensus());
    showGroupConservation.setSelected(av.isShowGroupConservation());
    showConsensusHistogram.setSelected(av.isShowConsensusHistogram());
    showSequenceLogo.setSelected(av.isShowSequenceLogo());
    normaliseSequenceLogo.setSelected(av.isNormaliseSequenceLogo());

    setColourSelected(ColourSchemeProperty.getColourName(av
            .getGlobalColourScheme()));

    showSeqFeatures.setSelected(av.showSequenceFeatures);
    hiddenMarkers.setState(av.showHiddenMarkers);
    applyToAllGroups.setState(av.getColourAppliesToAllGroups());
    showNpFeatsMenuitem.setSelected(av.isShowNpFeats());
    showDbRefsMenuitem.setSelected(av.isShowDbRefs());
    autoCalculate.setSelected(av.autoCalculateConsensus);
    sortByTree.setSelected(av.sortByTree);
    listenToViewSelections.setSelected(av.followSelection);
    rnahelicesColour.setEnabled(av.getAlignment().hasRNAStructure());
    rnahelicesColour
            .setSelected(av.getGlobalColourScheme() instanceof jalview.schemes.RNAHelicesColour);
    setShowProductsEnabled();

    updateEditMenuBar();
  }

  // methods for implementing IProgressIndicator
  // need to refactor to a reusable stub class
  Hashtable progressBars, progressBarHandlers;

  /*
   * (non-Javadoc)
   * 
   * @see jalview.gui.IProgressIndicator#setProgressBar(java.lang.String, long)
   */
  @Override
  public void setProgressBar(String message, long id)
  {
    if (progressBars == null)
    {
      progressBars = new Hashtable();
      progressBarHandlers = new Hashtable();
    }

    JPanel progressPanel;
    Long lId = new Long(id);
    GridLayout layout = (GridLayout) statusPanel.getLayout();
    if (progressBars.get(lId) != null)
    {
      progressPanel = (JPanel) progressBars.get(new Long(id));
      statusPanel.remove(progressPanel);
      progressBars.remove(lId);
      progressPanel = null;
      if (message != null)
      {
        statusBar.setText(message);
      }
      if (progressBarHandlers.contains(lId))
      {
        progressBarHandlers.remove(lId);
      }
      layout.setRows(layout.getRows() - 1);
    }
    else
    {
      progressPanel = new JPanel(new BorderLayout(10, 5));

      JProgressBar progressBar = new JProgressBar();
      progressBar.setIndeterminate(true);

      progressPanel.add(new JLabel(message), BorderLayout.WEST);
      progressPanel.add(progressBar, BorderLayout.CENTER);

      layout.setRows(layout.getRows() + 1);
      statusPanel.add(progressPanel);

      progressBars.put(lId, progressPanel);
    }
    // update GUI
    // setMenusForViewport();
    validate();
  }

  @Override
  public void registerHandler(final long id,
          final IProgressIndicatorHandler handler)
  {
    if (progressBarHandlers == null || !progressBars.contains(new Long(id)))
    {
      throw new Error(
              "call setProgressBar before registering the progress bar's handler.");
    }
    progressBarHandlers.put(new Long(id), handler);
    final JPanel progressPanel = (JPanel) progressBars.get(new Long(id));
    if (handler.canCancel())
    {
      JButton cancel = new JButton("Cancel");
      final IProgressIndicator us = this;
      cancel.addActionListener(new ActionListener()
      {

        @Override
        public void actionPerformed(ActionEvent e)
        {
          handler.cancelActivity(id);
          us.setProgressBar(
                  "Cancelled "
                          + ((JLabel) progressPanel.getComponent(0))
                                  .getText(), id);
        }
      });
      progressPanel.add(cancel, BorderLayout.EAST);
    }
  }

  /**
   * 
   * @return true if any progress bars are still active
   */
  @Override
  public boolean operationInProgress()
  {
    if (progressBars != null && progressBars.size() > 0)
    {
      return true;
    }
    return false;
  }

  /*
   * Added so Castor Mapping file can obtain Jalview Version
   */
  public String getVersion()
  {
    return jalview.bin.Cache.getProperty("VERSION");
  }

  public FeatureRenderer getFeatureRenderer()
  {
    return alignPanel.seqPanel.seqCanvas.getFeatureRenderer();
  }

  @Override
  public void fetchSequence_actionPerformed(ActionEvent e)
  {
    new SequenceFetcher(this);
  }

  @Override
  public void addFromFile_actionPerformed(ActionEvent e)
  {
    Desktop.instance.inputLocalFileMenuItem_actionPerformed(viewport);
  }

  @Override
  public void reload_actionPerformed(ActionEvent e)
  {
    if (fileName != null)
    {
      // TODO: JAL-1108 - ensure all associated frames are closed regardless of
      // originating file's format
      // TODO: work out how to recover feature settings for correct view(s) when
      // file is reloaded.
      if (currentFileFormat.equals("Jalview"))
      {
        JInternalFrame[] frames = Desktop.desktop.getAllFrames();
        for (int i = 0; i < frames.length; i++)
        {
          if (frames[i] instanceof AlignFrame && frames[i] != this
                  && ((AlignFrame) frames[i]).fileName != null
                  && ((AlignFrame) frames[i]).fileName.equals(fileName))
          {
            try
            {
              frames[i].setSelected(true);
              Desktop.instance.closeAssociatedWindows();
            } catch (java.beans.PropertyVetoException ex)
            {
            }
          }

        }
        Desktop.instance.closeAssociatedWindows();

        FileLoader loader = new FileLoader();
        String protocol = fileName.startsWith("http:") ? "URL" : "File";
        loader.LoadFile(viewport, fileName, protocol, currentFileFormat);
      }
      else
      {
        Rectangle bounds = this.getBounds();

        FileLoader loader = new FileLoader();
        String protocol = fileName.startsWith("http:") ? "URL" : "File";
        AlignFrame newframe = loader.LoadFileWaitTillLoaded(fileName,
                protocol, currentFileFormat);

        newframe.setBounds(bounds);
        if (featureSettings != null && featureSettings.isShowing())
        {
          final Rectangle fspos = featureSettings.frame.getBounds();
          // TODO: need a 'show feature settings' function that takes bounds -
          // need to refactor Desktop.addFrame
          newframe.featureSettings_actionPerformed(null);
          final FeatureSettings nfs = newframe.featureSettings;
          SwingUtilities.invokeLater(new Runnable()
          {
            @Override
            public void run()
            {
              nfs.frame.setBounds(fspos);
            }
          });
          this.featureSettings.close();
          this.featureSettings = null;
        }
        this.closeMenuItem_actionPerformed(true);
      }
    }
  }

  @Override
  public void addFromText_actionPerformed(ActionEvent e)
  {
    Desktop.instance.inputTextboxMenuItem_actionPerformed(viewport);
  }

  @Override
  public void addFromURL_actionPerformed(ActionEvent e)
  {
    Desktop.instance.inputURLMenuItem_actionPerformed(viewport);
  }

  @Override
  public void save_actionPerformed(ActionEvent e)
  {
    if (fileName == null
            || (currentFileFormat == null || !jalview.io.FormatAdapter
                    .isValidIOFormat(currentFileFormat, true))
            || fileName.startsWith("http"))
    {
      saveAs_actionPerformed(null);
    }
    else
    {
      saveAlignment(fileName, currentFileFormat);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void saveAs_actionPerformed(ActionEvent e)
  {
    JalviewFileChooser chooser = new JalviewFileChooser(
            jalview.bin.Cache.getProperty("LAST_DIRECTORY"),
            jalview.io.AppletFormatAdapter.WRITABLE_EXTENSIONS,
            jalview.io.AppletFormatAdapter.WRITABLE_FNAMES,
            currentFileFormat, false);

    chooser.setFileView(new JalviewFileView());
    chooser.setDialogTitle("Save Alignment to file");
    chooser.setToolTipText("Save");

    int value = chooser.showSaveDialog(this);

    if (value == JalviewFileChooser.APPROVE_OPTION)
    {
      currentFileFormat = chooser.getSelectedFormat();
      if (currentFileFormat == null)
      {
        JOptionPane.showInternalMessageDialog(Desktop.desktop,
                "You must select a file format before saving!",
                "File format not specified", JOptionPane.WARNING_MESSAGE);
        value = chooser.showSaveDialog(this);
        return;
      }

      fileName = chooser.getSelectedFile().getPath();

      jalview.bin.Cache.setProperty("DEFAULT_FILE_FORMAT",
              currentFileFormat);

      jalview.bin.Cache.setProperty("LAST_DIRECTORY", fileName);
      if (currentFileFormat.indexOf(" ") > -1)
      {
        currentFileFormat = currentFileFormat.substring(0,
                currentFileFormat.indexOf(" "));
      }
      saveAlignment(fileName, currentFileFormat);
    }
  }

  public boolean saveAlignment(String file, String format)
  {
    boolean success = true;

    if (format.equalsIgnoreCase("Jalview"))
    {
      String shortName = title;

      if (shortName.indexOf(java.io.File.separatorChar) > -1)
      {
        shortName = shortName.substring(shortName
                .lastIndexOf(java.io.File.separatorChar) + 1);
      }

      success = new Jalview2XML().SaveAlignment(this, file, shortName);

      statusBar.setText("Successfully saved to file: " + fileName + " in "
              + format + " format.");

    }
    else
    {
      if (!jalview.io.AppletFormatAdapter.isValidFormat(format, true))
      {
        warningMessage("Cannot save file " + fileName + " using format "
                + format, "Alignment output format not supported");
        saveAs_actionPerformed(null);
        // JBPNote need to have a raise_gui flag here
        return false;
      }

      String[] omitHidden = null;

      if (viewport.hasHiddenColumns())
      {
        int reply = JOptionPane
                .showInternalConfirmDialog(
                        Desktop.desktop,
                        "The Alignment contains hidden columns."
                                + "\nDo you want to save only the visible alignment?",
                        "Save / Omit Hidden Columns",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

        if (reply == JOptionPane.YES_OPTION)
        {
          omitHidden = viewport.getViewAsString(false);
        }
      }
      FormatAdapter f = new FormatAdapter();
      String output = f.formatSequences(format,
              viewport.getAlignment(), // class cast exceptions will
              // occur in the distant future
              omitHidden, f.getCacheSuffixDefault(format),
              viewport.getColumnSelection());

      if (output == null)
      {
        success = false;
      }
      else
      {
        try
        {
          java.io.PrintWriter out = new java.io.PrintWriter(
                  new java.io.FileWriter(file));

          out.print(output);
          out.close();
          this.setTitle(file);
          statusBar.setText("Successfully saved to file: " + fileName
                  + " in " + format + " format.");
        } catch (Exception ex)
        {
          success = false;
        }
      }
    }

    if (!success)
    {
      JOptionPane.showInternalMessageDialog(this, "Couldn't save file: "
              + fileName, "Error Saving File", JOptionPane.WARNING_MESSAGE);
    }

    return success;
  }

  private void warningMessage(String warning, String title)
  {
    if (new jalview.util.Platform().isHeadless())
    {
      System.err.println("Warning: " + title + "\nWarning: " + warning);

    }
    else
    {
      JOptionPane.showInternalMessageDialog(this, warning, title,
              JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void outputText_actionPerformed(ActionEvent e)
  {
    String[] omitHidden = null;

    if (viewport.hasHiddenColumns())
    {
      int reply = JOptionPane
              .showInternalConfirmDialog(
                      Desktop.desktop,
                      "The Alignment contains hidden columns."
                              + "\nDo you want to output only the visible alignment?",
                      "Save / Omit Hidden Columns",
                      JOptionPane.YES_NO_OPTION,
                      JOptionPane.QUESTION_MESSAGE);

      if (reply == JOptionPane.YES_OPTION)
      {
        omitHidden = viewport.getViewAsString(false);
      }
    }

    CutAndPasteTransfer cap = new CutAndPasteTransfer();
    cap.setForInput(null);

    try
    {
      cap.setText(new FormatAdapter().formatSequences(e.getActionCommand(),
              viewport.getAlignment(), omitHidden,
              viewport.getColumnSelection()));
      Desktop.addInternalFrame(cap,
              "Alignment output - " + e.getActionCommand(), 600, 500);
    } catch (OutOfMemoryError oom)
    {
      new OOMWarning("Outputting alignment as " + e.getActionCommand(), oom);
      cap.dispose();
    }

  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void htmlMenuItem_actionPerformed(ActionEvent e)
  {
    new HTMLOutput(alignPanel,
            alignPanel.seqPanel.seqCanvas.getSequenceRenderer(),
            alignPanel.seqPanel.seqCanvas.getFeatureRenderer());
  }

  public void createImageMap(File file, String image)
  {
    alignPanel.makePNGImageMap(file, image);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void createPNG(File f)
  {
    alignPanel.makePNG(f);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void createEPS(File f)
  {
    alignPanel.makeEPS(f);
  }

  @Override
  public void pageSetup_actionPerformed(ActionEvent e)
  {
    PrinterJob printJob = PrinterJob.getPrinterJob();
    PrintThread.pf = printJob.pageDialog(printJob.defaultPage());
  }

    @Override
  public void exportToWorkspaceMenuitem_actionPerformed(ActionEvent e) {
          String[] omitHidden = null;

      if (viewport.hasHiddenColumns())
      {
        int reply = JOptionPane
                .showInternalConfirmDialog(
                        Desktop.desktop,
                        "The Alignment contains hidden columns."
                                + "\nDo you want to save only the visible alignment?",
                        "Save / Omit Hidden Columns",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

        if (reply == JOptionPane.YES_OPTION)
        {
          omitHidden = viewport.getViewAsString(false);
        }
      }
      
        
        
      String var_name=JOptionPane.showInputDialog(this, "Enter Variable Name:");   
        
        List<SequenceI> sequences = viewport.getAlignment().getSequences();
        if(var_name != null ){
           for(int i=0; i<sequences.size(); i++) {
            if(sequences.get(i).getDescription() != null) {
             MainFrame.octavePanel.evaluate(var_name+"("+(i+1)+")"+".Header='"+sequences.get(i).getName()+" "+sequences.get(i).getDescription()+"';"); 
              MainFrame.octavePanel.evaluate(var_name+"("+(i+1)+")"+".Sequence='"+sequences.get(i).getSequenceAsString()+"';");
              
            }else{
               MainFrame.octavePanel.evaluate(var_name+"("+(i+1)+")"+".Header='"+sequences.get(i).getName()+"';");
                 MainFrame.octavePanel.evaluate(var_name+"("+(i+1)+")"+".Sequence='"+sequences.get(i).getSequenceAsString()+"';");
                
            }
            
        }
        MainFrame.reloadWorkspace();  
        }
        

  }
  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void printMenuItem_actionPerformed(ActionEvent e)
  {
    // Putting in a thread avoids Swing painting problems
    PrintThread thread = new PrintThread(alignPanel);
    thread.start();
  }

  @Override
  public void exportFeatures_actionPerformed(ActionEvent e)
  {
    new AnnotationExporter().exportFeatures(alignPanel);
  }

  @Override
  public void exportAnnotations_actionPerformed(ActionEvent e)
  {
    new AnnotationExporter().exportAnnotations(alignPanel,
            viewport.showAnnotation ? viewport.getAlignment()
                    .getAlignmentAnnotation() : null, viewport
                    .getAlignment().getGroups(), ((Alignment) viewport
                    .getAlignment()).alignmentProperties);
  }

  @Override
  public void associatedData_actionPerformed(ActionEvent e)
  {
    // Pick the tree file
    JalviewFileChooser chooser = new JalviewFileChooser(
            jalview.bin.Cache.getProperty("LAST_DIRECTORY"));
    chooser.setFileView(new JalviewFileView());
    chooser.setDialogTitle("Load Jalview Annotations or Features File");
    chooser.setToolTipText("Load Jalview Annotations / Features file");

    int value = chooser.showOpenDialog(null);

    if (value == JalviewFileChooser.APPROVE_OPTION)
    {
      String choice = chooser.getSelectedFile().getPath();
      jalview.bin.Cache.setProperty("LAST_DIRECTORY", choice);
      loadJalviewDataFile(choice, null, null, null);
    }

  }

  /**
   * Close the current view or all views in the alignment frame. If the frame
   * only contains one view then the alignment will be removed from memory.
   * 
   * @param closeAllTabs
   */
  @Override
  public void closeMenuItem_actionPerformed(boolean closeAllTabs)
  {
    if (alignPanels != null && alignPanels.size() < 2)
    {
      closeAllTabs = true;
    }

    try
    {
      if (alignPanels != null)
      {
        if (closeAllTabs)
        {
          if (this.isClosed())
          {
            // really close all the windows - otherwise wait till
            // setClosed(true) is called
            for (int i = 0; i < alignPanels.size(); i++)
            {
              AlignmentPanel ap = (AlignmentPanel) alignPanels.elementAt(i);
              ap.closePanel();
            }
          }
        }
        else
        {
          closeView(alignPanel);
        }
      }

      if (closeAllTabs)
      {
        this.setClosed(true);
      }
    } catch (Exception ex)
    {
    }
  }

  /**
   * close alignPanel2 and shuffle tabs appropriately.
   * 
   * @param alignPanel2
   */
  public void closeView(AlignmentPanel alignPanel2)
  {
    int index = tabbedPane.getSelectedIndex();
    int closedindex = tabbedPane.indexOfComponent(alignPanel2);
    alignPanels.removeElement(alignPanel2);
    // Unnecessary
    // if (viewport == alignPanel2.av)
    // {
    // viewport = null;
    // }
    alignPanel2.closePanel();
    alignPanel2 = null;

    tabbedPane.removeTabAt(closedindex);
    tabbedPane.validate();

    if (index > closedindex || index == tabbedPane.getTabCount())
    {
      // modify currently selected tab index if necessary.
      index--;
    }

    this.tabSelectionChanged(index);
  }

  /**
   * DOCUMENT ME!
   */
  void updateEditMenuBar()
  {

    if (viewport.historyList.size() > 0)
    {
      undoMenuItem.setEnabled(true);
      CommandI command = (CommandI) viewport.historyList.peek();
      undoMenuItem.setText("Undo " + command.getDescription());
    }
    else
    {
      undoMenuItem.setEnabled(false);
      undoMenuItem.setText("Undo");
    }

    if (viewport.redoList.size() > 0)
    {
      redoMenuItem.setEnabled(true);

      CommandI command = (CommandI) viewport.redoList.peek();
      redoMenuItem.setText("Redo " + command.getDescription());
    }
    else
    {
      redoMenuItem.setEnabled(false);
      redoMenuItem.setText("Redo");
    }
  }

  public void addHistoryItem(CommandI command)
  {
    if (command.getSize() > 0)
    {
      viewport.historyList.push(command);
      viewport.redoList.clear();
      updateEditMenuBar();
      viewport.updateHiddenColumns();
      // viewport.hasHiddenColumns = (viewport.getColumnSelection() != null
      // && viewport.getColumnSelection().getHiddenColumns() != null &&
      // viewport.getColumnSelection()
      // .getHiddenColumns().size() > 0);
    }
  }

  /**
   * 
   * @return alignment objects for all views
   */
  AlignmentI[] getViewAlignments()
  {
    if (alignPanels != null)
    {
      Enumeration e = alignPanels.elements();
      AlignmentI[] als = new AlignmentI[alignPanels.size()];
      for (int i = 0; e.hasMoreElements(); i++)
      {
        als[i] = ((AlignmentPanel) e.nextElement()).av.getAlignment();
      }
      return als;
    }
    if (viewport != null)
    {
      return new AlignmentI[]
      { viewport.getAlignment() };
    }
    return null;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void undoMenuItem_actionPerformed(ActionEvent e)
  {
    if (viewport.historyList.empty()) {
          return;
      }
    CommandI command = (CommandI) viewport.historyList.pop();
    viewport.redoList.push(command);
    command.undoCommand(getViewAlignments());

    AlignViewport originalSource = getOriginatingSource(command);
    updateEditMenuBar();

    if (originalSource != null)
    {
      if (originalSource != viewport)
      {
        Cache.log.warn("Implementation worry: mismatch of viewport origin for undo");
      }
      originalSource.updateHiddenColumns();
      // originalSource.hasHiddenColumns = (viewport.getColumnSelection() !=
      // null
      // && viewport.getColumnSelection().getHiddenColumns() != null &&
      // viewport.getColumnSelection()
      // .getHiddenColumns().size() > 0);
      originalSource.firePropertyChange("alignment", null, originalSource
              .getAlignment().getSequences());
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void redoMenuItem_actionPerformed(ActionEvent e)
  {
    if (viewport.redoList.size() < 1)
    {
      return;
    }

    CommandI command = (CommandI) viewport.redoList.pop();
    viewport.historyList.push(command);
    command.doCommand(getViewAlignments());

    AlignViewport originalSource = getOriginatingSource(command);
    updateEditMenuBar();

    if (originalSource != null)
    {

      if (originalSource != viewport)
      {
        Cache.log.warn("Implementation worry: mismatch of viewport origin for redo");
      }
      originalSource.updateHiddenColumns();
      // originalSource.hasHiddenColumns = (viewport.getColumnSelection() !=
      // null
      // && viewport.getColumnSelection().getHiddenColumns() != null &&
      // viewport.getColumnSelection()
      // .getHiddenColumns().size() > 0);
      originalSource.firePropertyChange("alignment", null, originalSource
              .getAlignment().getSequences());
    }
  }

  AlignViewport getOriginatingSource(CommandI command)
  {
    AlignViewport originalSource = null;
    // For sequence removal and addition, we need to fire
    // the property change event FROM the viewport where the
    // original alignment was altered
    AlignmentI al = null;
    if (command instanceof EditCommand)
    {
      EditCommand editCommand = (EditCommand) command;
      al = editCommand.getAlignment();
      Vector comps = (Vector) PaintRefresher.components.get(viewport
              .getSequenceSetId());

      for (int i = 0; i < comps.size(); i++)
      {
        if (comps.elementAt(i) instanceof AlignmentPanel)
        {
          if (al == ((AlignmentPanel) comps.elementAt(i)).av.getAlignment())
          {
            originalSource = ((AlignmentPanel) comps.elementAt(i)).av;
            break;
          }
        }
      }
    }

    if (originalSource == null)
    {
      // The original view is closed, we must validate
      // the current view against the closed view first
      if (al != null)
      {
        PaintRefresher.validateSequences(al, viewport.getAlignment());
      }

      originalSource = viewport;
    }

    return originalSource;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param up
   *          DOCUMENT ME!
   */
  public void moveSelectedSequences(boolean up)
  {
    SequenceGroup sg = viewport.getSelectionGroup();

    if (sg == null)
    {
      return;
    }
    viewport.getAlignment().moveSelectedSequencesByOne(sg,
            viewport.getHiddenRepSequences(), up);
    alignPanel.paintAlignment(true);
  }

  synchronized void slideSequences(boolean right, int size)
  {
    List<SequenceI> sg = new Vector();
    if (viewport.cursorMode)
    {
      sg.add(viewport.getAlignment().getSequenceAt(
              alignPanel.seqPanel.seqCanvas.cursorY));
    }
    else if (viewport.getSelectionGroup() != null
            && viewport.getSelectionGroup().getSize() != viewport
                    .getAlignment().getHeight())
    {
      sg = viewport.getSelectionGroup().getSequences(
              viewport.getHiddenRepSequences());
    }

    if (sg.size() < 1)
    {
      return;
    }

    Vector invertGroup = new Vector();

    for (int i = 0; i < viewport.getAlignment().getHeight(); i++)
    {
      if (!sg.contains(viewport.getAlignment().getSequenceAt(i)))
        invertGroup.add(viewport.getAlignment().getSequenceAt(i));
    }

    SequenceI[] seqs1 = sg.toArray(new SequenceI[0]);

    SequenceI[] seqs2 = new SequenceI[invertGroup.size()];
    for (int i = 0; i < invertGroup.size(); i++)
      seqs2[i] = (SequenceI) invertGroup.elementAt(i);

    SlideSequencesCommand ssc;
    if (right)
      ssc = new SlideSequencesCommand("Slide Sequences", seqs2, seqs1,
              size, viewport.getGapCharacter());
    else
      ssc = new SlideSequencesCommand("Slide Sequences", seqs1, seqs2,
              size, viewport.getGapCharacter());

    int groupAdjustment = 0;
    if (ssc.getGapsInsertedBegin() && right)
    {
      if (viewport.cursorMode)
        alignPanel.seqPanel.moveCursor(size, 0);
      else
        groupAdjustment = size;
    }
    else if (!ssc.getGapsInsertedBegin() && !right)
    {
      if (viewport.cursorMode)
        alignPanel.seqPanel.moveCursor(-size, 0);
      else
        groupAdjustment = -size;
    }

    if (groupAdjustment != 0)
    {
      viewport.getSelectionGroup().setStartRes(
              viewport.getSelectionGroup().getStartRes() + groupAdjustment);
      viewport.getSelectionGroup().setEndRes(
              viewport.getSelectionGroup().getEndRes() + groupAdjustment);
    }

    boolean appendHistoryItem = false;
    if (viewport.historyList != null && viewport.historyList.size() > 0
            && viewport.historyList.peek() instanceof SlideSequencesCommand)
    {
      appendHistoryItem = ssc
              .appendSlideCommand((SlideSequencesCommand) viewport.historyList
                      .peek());
    }

    if (!appendHistoryItem)
      addHistoryItem(ssc);

    repaint();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void copy_actionPerformed(ActionEvent e)
  {
    System.gc();
    if (viewport.getSelectionGroup() == null)
    {
      return;
    }
    // TODO: preserve the ordering of displayed alignment annotation in any
    // internal paste (particularly sequence associated annotation)
    SequenceI[] seqs = viewport.getSelectionAsNewSequence();
    String[] omitHidden = null;

    if (viewport.hasHiddenColumns())
    {
      omitHidden = viewport.getViewAsString(true);
    }

    String output = new FormatAdapter().formatSequences("Fasta", seqs,
            omitHidden);

    StringSelection ss = new StringSelection(output);

    try
    {
      jalview.gui.Desktop.internalCopy = true;
      // Its really worth setting the clipboard contents
      // to empty before setting the large StringSelection!!
      Toolkit.getDefaultToolkit().getSystemClipboard()
              .setContents(new StringSelection(""), null);

      Toolkit.getDefaultToolkit().getSystemClipboard()
              .setContents(ss, Desktop.instance);
    } catch (OutOfMemoryError er)
    {
      new OOMWarning("copying region", er);
      return;
    }

    Vector hiddenColumns = null;
    if (viewport.hasHiddenColumns())
    {
      hiddenColumns = new Vector();
      int hiddenOffset = viewport.getSelectionGroup().getStartRes(), hiddenCutoff = viewport
              .getSelectionGroup().getEndRes();
      for (int i = 0; i < viewport.getColumnSelection().getHiddenColumns()
              .size(); i++)
      {
        int[] region = (int[]) viewport.getColumnSelection()
                .getHiddenColumns().elementAt(i);
        if (region[0] >= hiddenOffset && region[1] <= hiddenCutoff)
        {
          hiddenColumns.addElement(new int[]
          { region[0] - hiddenOffset, region[1] - hiddenOffset });
        }
      }
    }

    Desktop.jalviewClipboard = new Object[]
    { seqs, viewport.getAlignment().getDataset(), hiddenColumns };
    statusBar.setText("Copied " + seqs.length + " sequences to clipboard.");
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void pasteNew_actionPerformed(ActionEvent e)
  {
    paste(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void pasteThis_actionPerformed(ActionEvent e)
  {
    paste(false);
  }

  /**
   * Paste contents of Jalview clipboard
   * 
   * @param newAlignment
   *          true to paste to a new alignment, otherwise add to this.
   */
  void paste(boolean newAlignment)
  {
    boolean externalPaste = true;
    try
    {
      Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
      Transferable contents = c.getContents(this);

      if (contents == null)
      {
        return;
      }

      String str, format;
      try
      {
        str = (String) contents.getTransferData(DataFlavor.stringFlavor);
        if (str.length() < 1)
        {
          return;
        }

        format = new IdentifyFile().Identify(str, "Paste");

      } catch (OutOfMemoryError er)
      {
        new OOMWarning("Out of memory pasting sequences!!", er);
        return;
      }

      SequenceI[] sequences;
      boolean annotationAdded = false;
      AlignmentI alignment = null;

      if (Desktop.jalviewClipboard != null)
      {
        // The clipboard was filled from within Jalview, we must use the
        // sequences
        // And dataset from the copied alignment
        SequenceI[] newseq = (SequenceI[]) Desktop.jalviewClipboard[0];
        // be doubly sure that we create *new* sequence objects.
        sequences = new SequenceI[newseq.length];
        for (int i = 0; i < newseq.length; i++)
        {
          sequences[i] = new Sequence(newseq[i]);
        }
        alignment = new Alignment(sequences);
        externalPaste = false;
      }
      else
      {
        // parse the clipboard as an alignment.
        alignment = new FormatAdapter().readFile(str, "Paste", format);
        sequences = alignment.getSequencesArray();
      }

      int alwidth = 0;
      ArrayList<Integer> newGraphGroups = new ArrayList<Integer>();
      int fgroup = -1;

      if (newAlignment)
      {

        if (Desktop.jalviewClipboard != null)
        {
          // dataset is inherited
          alignment.setDataset((Alignment) Desktop.jalviewClipboard[1]);
        }
        else
        {
          // new dataset is constructed
          alignment.setDataset(null);
        }
        alwidth = alignment.getWidth() + 1;
      }
      else
      {
        AlignmentI pastedal = alignment; // preserve pasted alignment object
        // Add pasted sequences and dataset into existing alignment.
        alignment = viewport.getAlignment();
        alwidth = alignment.getWidth() + 1;
        // decide if we need to import sequences from an existing dataset
        boolean importDs = Desktop.jalviewClipboard != null
                && Desktop.jalviewClipboard[1] != alignment.getDataset();
        // importDs==true instructs us to copy over new dataset sequences from
        // an existing alignment
        Vector newDs = (importDs) ? new Vector() : null; // used to create
        // minimum dataset set

        for (int i = 0; i < sequences.length; i++)
        {
          if (importDs)
          {
            newDs.addElement(null);
          }
          SequenceI ds = sequences[i].getDatasetSequence(); // null for a simple
          // paste
          if (importDs && ds != null)
          {
            if (!newDs.contains(ds))
            {
              newDs.setElementAt(ds, i);
              ds = new Sequence(ds);
              // update with new dataset sequence
              sequences[i].setDatasetSequence(ds);
            }
            else
            {
              ds = sequences[newDs.indexOf(ds)].getDatasetSequence();
            }
          }
          else
          {
            // copy and derive new dataset sequence
            sequences[i] = sequences[i].deriveSequence();
            alignment.getDataset().addSequence(
                    sequences[i].getDatasetSequence());
            // TODO: avoid creation of duplicate dataset sequences with a
            // 'contains' method using SequenceI.equals()/SequenceI.contains()
          }
          alignment.addSequence(sequences[i]); // merges dataset
        }
        if (newDs != null)
        {
          newDs.clear(); // tidy up
        }
        if (alignment.getAlignmentAnnotation() != null)
        {
          for (AlignmentAnnotation alan : alignment
                  .getAlignmentAnnotation())
          {
            if (alan.graphGroup > fgroup)
            {
              fgroup = alan.graphGroup;
            }
          }
        }
        if (pastedal.getAlignmentAnnotation() != null)
        {
          // Add any annotation attached to alignment.
          AlignmentAnnotation[] alann = pastedal.getAlignmentAnnotation();
          for (int i = 0; i < alann.length; i++)
          {
            annotationAdded = true;
            if (alann[i].sequenceRef == null && !alann[i].autoCalculated)
            {
              AlignmentAnnotation newann = new AlignmentAnnotation(alann[i]);
              if (newann.graphGroup > -1)
              {
                if (newGraphGroups.size() <= newann.graphGroup
                        || newGraphGroups.get(newann.graphGroup) == null)
                {
                  for (int q = newGraphGroups.size(); q <= newann.graphGroup; q++)
                  {
                    newGraphGroups.add(q, null);
                  }
                  newGraphGroups.set(newann.graphGroup, new Integer(
                          ++fgroup));
                }
                newann.graphGroup = newGraphGroups.get(newann.graphGroup)
                        .intValue();
              }

              newann.padAnnotation(alwidth);
              alignment.addAnnotation(newann);
            }
          }
        }
      }
      if (!newAlignment)
      {
        // /////
        // ADD HISTORY ITEM
        //
        addHistoryItem(new EditCommand("Add sequences", EditCommand.PASTE,
                sequences, 0, alignment.getWidth(), alignment));
      }
      // Add any annotations attached to sequences
      for (int i = 0; i < sequences.length; i++)
      {
        if (sequences[i].getAnnotation() != null)
        {
          AlignmentAnnotation newann;
          for (int a = 0; a < sequences[i].getAnnotation().length; a++)
          {
            annotationAdded = true;
            newann = sequences[i].getAnnotation()[a];
            newann.adjustForAlignment();
            newann.padAnnotation(alwidth);
            if (newann.graphGroup > -1)
            {
              if (newann.graphGroup > -1)
              {
                if (newGraphGroups.size() <= newann.graphGroup
                        || newGraphGroups.get(newann.graphGroup) == null)
                {
                  for (int q = newGraphGroups.size(); q <= newann.graphGroup; q++)
                  {
                    newGraphGroups.add(q, null);
                  }
                  newGraphGroups.set(newann.graphGroup, new Integer(
                          ++fgroup));
                }
                newann.graphGroup = newGraphGroups.get(newann.graphGroup)
                        .intValue();
              }
            }
            alignment.addAnnotation(sequences[i].getAnnotation()[a]); // annotation
            // was
            // duplicated
            // earlier
            alignment
                    .setAnnotationIndex(sequences[i].getAnnotation()[a], a);
          }
        }
      }
      if (!newAlignment)
      {

        // propagate alignment changed.
        viewport.setEndSeq(alignment.getHeight());
        if (annotationAdded)
        {
          // Duplicate sequence annotation in all views.
          AlignmentI[] alview = this.getViewAlignments();
          for (int i = 0; i < sequences.length; i++)
          {
            AlignmentAnnotation sann[] = sequences[i].getAnnotation();
            if (sann == null)
              continue;
            for (int avnum = 0; avnum < alview.length; avnum++)
            {
              if (alview[avnum] != alignment)
              {
                // duplicate in a view other than the one with input focus
                int avwidth = alview[avnum].getWidth() + 1;
                // this relies on sann being preserved after we
                // modify the sequence's annotation array for each duplication
                for (int a = 0; a < sann.length; a++)
                {
                  AlignmentAnnotation newann = new AlignmentAnnotation(
                          sann[a]);
                  sequences[i].addAlignmentAnnotation(newann);
                  newann.padAnnotation(avwidth);
                  alview[avnum].addAnnotation(newann); // annotation was
                  // duplicated earlier
                  // TODO JAL-1145 graphGroups are not updated for sequence
                  // annotation added to several views. This may cause
                  // strangeness
                  alview[avnum].setAnnotationIndex(newann, a);
                }
              }
            }
          }
          buildSortByAnnotationScoresMenu();
        }
        viewport.firePropertyChange("alignment", null,
                alignment.getSequences());
        if (alignPanels != null)
        {
          for (AlignmentPanel ap : ((Vector<AlignmentPanel>) alignPanels))
          {
            ap.validateAnnotationDimensions(false);
          }
        }
        else
        {
          alignPanel.validateAnnotationDimensions(false);
        }

      }
      else
      {
        AlignFrame af = new AlignFrame(alignment, DEFAULT_WIDTH,
                DEFAULT_HEIGHT);
        String newtitle = new String("Copied sequences");

        if (Desktop.jalviewClipboard != null
                && Desktop.jalviewClipboard[2] != null)
        {
          Vector hc = (Vector) Desktop.jalviewClipboard[2];
          for (int i = 0; i < hc.size(); i++)
          {
            int[] region = (int[]) hc.elementAt(i);
            af.viewport.hideColumns(region[0], region[1]);
          }
        }

        // >>>This is a fix for the moment, until a better solution is
        // found!!<<<
        af.alignPanel.seqPanel.seqCanvas.getFeatureRenderer()
                .transferSettings(
                        alignPanel.seqPanel.seqCanvas.getFeatureRenderer());

        // TODO: maintain provenance of an alignment, rather than just make the
        // title a concatenation of operations.
        if (!externalPaste)
        {
          if (title.startsWith("Copied sequences"))
          {
            newtitle = title;
          }
          else
          {
            newtitle = newtitle.concat("- from " + title);
          }
        }
        else
        {
          newtitle = new String("Pasted sequences");
        }

        Desktop.addInternalFrame(af, newtitle, DEFAULT_WIDTH,
                DEFAULT_HEIGHT);

      }

    } catch (Exception ex)
    {
      ex.printStackTrace();
      System.out.println("Exception whilst pasting: " + ex);
      // could be anything being pasted in here
    }

  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void cut_actionPerformed(ActionEvent e)
  {
    copy_actionPerformed(null);
    delete_actionPerformed(null);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void delete_actionPerformed(ActionEvent evt)
  {

    SequenceGroup sg = viewport.getSelectionGroup();
    if (sg == null)
    {
      return;
    }

    Vector seqs = new Vector();
    SequenceI seq;
    for (int i = 0; i < sg.getSize(); i++)
    {
      seq = sg.getSequenceAt(i);
      seqs.addElement(seq);
    }

    // If the cut affects all sequences, remove highlighted columns
    if (sg.getSize() == viewport.getAlignment().getHeight())
    {
      viewport.getColumnSelection().removeElements(sg.getStartRes(),
              sg.getEndRes() + 1);
    }

    SequenceI[] cut = new SequenceI[seqs.size()];
    for (int i = 0; i < seqs.size(); i++)
    {
      cut[i] = (SequenceI) seqs.elementAt(i);
    }

    /*
     * //ADD HISTORY ITEM
     */
    addHistoryItem(new EditCommand("Cut Sequences", EditCommand.CUT, cut,
            sg.getStartRes(), sg.getEndRes() - sg.getStartRes() + 1,
            viewport.getAlignment()));

    viewport.setSelectionGroup(null);
    viewport.sendSelection();
    viewport.getAlignment().deleteGroup(sg);

    viewport.firePropertyChange("alignment", null, viewport.getAlignment()
            .getSequences());
    if (viewport.getAlignment().getHeight() < 1)
    {
      try
      {
        this.setClosed(true);
      } catch (Exception ex)
      {
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void deleteGroups_actionPerformed(ActionEvent e)
  {
    viewport.getAlignment().deleteAllGroups();
    viewport.sequenceColours = null;
    viewport.setSelectionGroup(null);
    PaintRefresher.Refresh(this, viewport.getSequenceSetId());
    alignPanel.updateAnnotation();
    alignPanel.paintAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void selectAllSequenceMenuItem_actionPerformed(ActionEvent e)
  {
    SequenceGroup sg = new SequenceGroup();

    for (int i = 0; i < viewport.getAlignment().getSequences().size(); i++)
    {
      sg.addSequence(viewport.getAlignment().getSequenceAt(i), false);
    }

    sg.setEndRes(viewport.getAlignment().getWidth() - 1);
    viewport.setSelectionGroup(sg);
    viewport.sendSelection();
    alignPanel.paintAlignment(true);
    PaintRefresher.Refresh(alignPanel, viewport.getSequenceSetId());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void deselectAllSequenceMenuItem_actionPerformed(ActionEvent e)
  {
    if (viewport.cursorMode)
    {
      alignPanel.seqPanel.keyboardNo1 = null;
      alignPanel.seqPanel.keyboardNo2 = null;
    }
    viewport.setSelectionGroup(null);
    viewport.getColumnSelection().clear();
    viewport.setSelectionGroup(null);
    alignPanel.seqPanel.seqCanvas.highlightSearchResults(null);
    alignPanel.idPanel.idCanvas.searchResults = null;
    alignPanel.paintAlignment(true);
    PaintRefresher.Refresh(alignPanel, viewport.getSequenceSetId());
    viewport.sendSelection();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void invertSequenceMenuItem_actionPerformed(ActionEvent e)
  {
    SequenceGroup sg = viewport.getSelectionGroup();

    if (sg == null)
    {
      selectAllSequenceMenuItem_actionPerformed(null);

      return;
    }

    for (int i = 0; i < viewport.getAlignment().getSequences().size(); i++)
    {
      sg.addOrRemove(viewport.getAlignment().getSequenceAt(i), false);
    }

    alignPanel.paintAlignment(true);
    PaintRefresher.Refresh(alignPanel, viewport.getSequenceSetId());
    viewport.sendSelection();
  }

  @Override
  public void invertColSel_actionPerformed(ActionEvent e)
  {
    viewport.invertColumnSelection();
    alignPanel.paintAlignment(true);
    viewport.sendSelection();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void remove2LeftMenuItem_actionPerformed(ActionEvent e)
  {
    trimAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void remove2RightMenuItem_actionPerformed(ActionEvent e)
  {
    trimAlignment(false);
  }

  void trimAlignment(boolean trimLeft)
  {
    ColumnSelection colSel = viewport.getColumnSelection();
    int column;

    if (colSel.size() > 0)
    {
      if (trimLeft)
      {
        column = colSel.getMin();
      }
      else
      {
        column = colSel.getMax();
      }

      SequenceI[] seqs;
      if (viewport.getSelectionGroup() != null)
      {
        seqs = viewport.getSelectionGroup().getSequencesAsArray(
                viewport.getHiddenRepSequences());
      }
      else
      {
        seqs = viewport.getAlignment().getSequencesArray();
      }

      TrimRegionCommand trimRegion;
      if (trimLeft)
      {
        trimRegion = new TrimRegionCommand("Remove Left",
                TrimRegionCommand.TRIM_LEFT, seqs, column,
                viewport.getAlignment(), viewport.getColumnSelection(),
                viewport.getSelectionGroup());
        viewport.setStartRes(0);
      }
      else
      {
        trimRegion = new TrimRegionCommand("Remove Right",
                TrimRegionCommand.TRIM_RIGHT, seqs, column,
                viewport.getAlignment(), viewport.getColumnSelection(),
                viewport.getSelectionGroup());
      }

      statusBar.setText("Removed " + trimRegion.getSize() + " columns.");

      addHistoryItem(trimRegion);

      for (SequenceGroup sg : viewport.getAlignment().getGroups())
      {
        if ((trimLeft && !sg.adjustForRemoveLeft(column))
                || (!trimLeft && !sg.adjustForRemoveRight(column)))
        {
          viewport.getAlignment().deleteGroup(sg);
        }
      }

      viewport.firePropertyChange("alignment", null, viewport
              .getAlignment().getSequences());
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void removeGappedColumnMenuItem_actionPerformed(ActionEvent e)
  {
    int start = 0, end = viewport.getAlignment().getWidth() - 1;

    SequenceI[] seqs;
    if (viewport.getSelectionGroup() != null)
    {
      seqs = viewport.getSelectionGroup().getSequencesAsArray(
              viewport.getHiddenRepSequences());
      start = viewport.getSelectionGroup().getStartRes();
      end = viewport.getSelectionGroup().getEndRes();
    }
    else
    {
      seqs = viewport.getAlignment().getSequencesArray();
    }

    RemoveGapColCommand removeGapCols = new RemoveGapColCommand(
            "Remove Gapped Columns", seqs, start, end,
            viewport.getAlignment());

    addHistoryItem(removeGapCols);

    statusBar.setText("Removed " + removeGapCols.getSize()
            + " empty columns.");

    // This is to maintain viewport position on first residue
    // of first sequence
    SequenceI seq = viewport.getAlignment().getSequenceAt(0);
    int startRes = seq.findPosition(viewport.startRes);
    // ShiftList shifts;
    // viewport.getAlignment().removeGaps(shifts=new ShiftList());
    // edit.alColumnChanges=shifts.getInverse();
    // if (viewport.hasHiddenColumns)
    // viewport.getColumnSelection().compensateForEdits(shifts);
    viewport.setStartRes(seq.findIndex(startRes) - 1);
    viewport.firePropertyChange("alignment", null, viewport.getAlignment()
            .getSequences());

  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void removeAllGapsMenuItem_actionPerformed(ActionEvent e)
  {
    int start = 0, end = viewport.getAlignment().getWidth() - 1;

    SequenceI[] seqs;
    if (viewport.getSelectionGroup() != null)
    {
      seqs = viewport.getSelectionGroup().getSequencesAsArray(
              viewport.getHiddenRepSequences());
      start = viewport.getSelectionGroup().getStartRes();
      end = viewport.getSelectionGroup().getEndRes();
    }
    else
    {
      seqs = viewport.getAlignment().getSequencesArray();
    }

    // This is to maintain viewport position on first residue
    // of first sequence
    SequenceI seq = viewport.getAlignment().getSequenceAt(0);
    int startRes = seq.findPosition(viewport.startRes);

    addHistoryItem(new RemoveGapsCommand("Remove Gaps", seqs, start, end,
            viewport.getAlignment()));

    viewport.setStartRes(seq.findIndex(startRes) - 1);

    viewport.firePropertyChange("alignment", null, viewport.getAlignment()
            .getSequences());

  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void padGapsMenuitem_actionPerformed(ActionEvent e)
  {
    viewport.setPadGaps(padGapsMenuitem.isSelected());
    viewport.firePropertyChange("alignment", null, viewport.getAlignment()
            .getSequences());
  }

  // else
  {
    // if (justifySeqs>0)
    {
      // alignment.justify(justifySeqs!=RIGHT_JUSTIFY);
    }
  }

  // }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void findMenuItem_actionPerformed(ActionEvent e)
  {
    new Finder();
  }

  @Override
  public void newView_actionPerformed(ActionEvent e)
  {
    newView(true);
  }

  /**
   * 
   * @param copyAnnotation
   *          if true then duplicate all annnotation, groups and settings
   * @return new alignment panel, already displayed.
   */
  public AlignmentPanel newView(boolean copyAnnotation)
  {
    return newView(null, copyAnnotation);
  }

  /**
   * 
   * @param viewTitle
   *          title of newly created view
   * @return new alignment panel, already displayed.
   */
  public AlignmentPanel newView(String viewTitle)
  {
    return newView(viewTitle, true);
  }

  /**
   * 
   * @param viewTitle
   *          title of newly created view
   * @param copyAnnotation
   *          if true then duplicate all annnotation, groups and settings
   * @return new alignment panel, already displayed.
   */
  public AlignmentPanel newView(String viewTitle, boolean copyAnnotation)
  {
    AlignmentPanel newap = new Jalview2XML().copyAlignPanel(alignPanel,
            true);
    if (!copyAnnotation)
    {
      // just remove all the current annotation except for the automatic stuff
      newap.av.getAlignment().deleteAllGroups();
      for (AlignmentAnnotation alan : newap.av.getAlignment()
              .getAlignmentAnnotation())
      {
        if (!alan.autoCalculated)
        {
          newap.av.getAlignment().deleteAnnotation(alan);
        }
        ;
      }
    }

    newap.av.gatherViewsHere = false;

    if (viewport.viewName == null)
    {
      viewport.viewName = "Original";
    }

    newap.av.historyList = viewport.historyList;
    newap.av.redoList = viewport.redoList;

    int index = Desktop.getViewCount(viewport.getSequenceSetId());
    // make sure the new view has a unique name - this is essential for Jalview
    // 2 archives
    boolean addFirstIndex = false;
    if (viewTitle == null || viewTitle.trim().length() == 0)
    {
      viewTitle = "View";
      addFirstIndex = true;
    }
    else
    {
      index = 1;// we count from 1 if given a specific name
    }
    String newViewName = viewTitle + ((addFirstIndex) ? " " + index : "");
    Vector comps = (Vector) PaintRefresher.components.get(viewport
            .getSequenceSetId());
    Vector existingNames = new Vector();
    for (int i = 0; i < comps.size(); i++)
    {
      if (comps.elementAt(i) instanceof AlignmentPanel)
      {
        AlignmentPanel ap = (AlignmentPanel) comps.elementAt(i);
        if (!existingNames.contains(ap.av.viewName))
        {
          existingNames.addElement(ap.av.viewName);
        }
      }
    }

    while (existingNames.contains(newViewName))
    {
      newViewName = viewTitle + " " + (++index);
    }

    newap.av.viewName = newViewName;

    addAlignmentPanel(newap, true);

    if (alignPanels.size() == 2)
    {
      viewport.gatherViewsHere = true;
    }
    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
    return newap;
  }

  @Override
  public void expandViews_actionPerformed(ActionEvent e)
  {
    Desktop.instance.explodeViews(this);
  }

  @Override
  public void gatherViews_actionPerformed(ActionEvent e)
  {
    Desktop.instance.gatherViews(this);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void font_actionPerformed(ActionEvent e)
  {
    new FontChooser(alignPanel);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void seqLimit_actionPerformed(ActionEvent e)
  {
    viewport.setShowJVSuffix(seqLimits.isSelected());

    alignPanel.idPanel.idCanvas.setPreferredSize(alignPanel
            .calculateIdWidth());
    alignPanel.paintAlignment(true);
  }

  @Override
  public void idRightAlign_actionPerformed(ActionEvent e)
  {
    viewport.rightAlignIds = idRightAlign.isSelected();
    alignPanel.paintAlignment(true);
  }

  @Override
  public void centreColumnLabels_actionPerformed(ActionEvent e)
  {
    viewport.centreColumnLabels = centreColumnLabelsMenuItem.getState();
    alignPanel.paintAlignment(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.jbgui.GAlignFrame#followHighlight_actionPerformed()
   */
  @Override
  protected void followHighlight_actionPerformed()
  {
    if (viewport.followHighlight = this.followHighlightMenuItem.getState())
    {
      alignPanel.scrollToPosition(
              alignPanel.seqPanel.seqCanvas.searchResults, false);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void colourTextMenuItem_actionPerformed(ActionEvent e)
  {
    viewport.setColourText(colourTextMenuItem.isSelected());
    alignPanel.paintAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void wrapMenuItem_actionPerformed(ActionEvent e)
  {
    scaleAbove.setVisible(wrapMenuItem.isSelected());
    scaleLeft.setVisible(wrapMenuItem.isSelected());
    scaleRight.setVisible(wrapMenuItem.isSelected());
    viewport.setWrapAlignment(wrapMenuItem.isSelected());
    alignPanel.setWrapAlignment(wrapMenuItem.isSelected());
  }

  @Override
  public void showAllSeqs_actionPerformed(ActionEvent e)
  {
    viewport.showAllHiddenSeqs();
  }

  @Override
  public void showAllColumns_actionPerformed(ActionEvent e)
  {
    viewport.showAllHiddenColumns();
    repaint();
  }

  @Override
  public void hideSelSequences_actionPerformed(ActionEvent e)
  {
    viewport.hideAllSelectedSeqs();
    alignPanel.paintAlignment(true);
  }

  /**
   * called by key handler and the hide all/show all menu items
   * 
   * @param toggleSeqs
   * @param toggleCols
   */
  private void toggleHiddenRegions(boolean toggleSeqs, boolean toggleCols)
  {

    boolean hide = false;
    SequenceGroup sg = viewport.getSelectionGroup();
    if (!toggleSeqs && !toggleCols)
    {
      // Hide everything by the current selection - this is a hack - we do the
      // invert and then hide
      // first check that there will be visible columns after the invert.
      if ((viewport.getColumnSelection() != null
              && viewport.getColumnSelection().getSelected() != null && viewport
              .getColumnSelection().getSelected().size() > 0)
              || (sg != null && sg.getSize() > 0 && sg.getStartRes() <= sg
                      .getEndRes()))
      {
        // now invert the sequence set, if required - empty selection implies
        // that no hiding is required.
        if (sg != null)
        {
          invertSequenceMenuItem_actionPerformed(null);
          sg = viewport.getSelectionGroup();
          toggleSeqs = true;

        }
        viewport.expandColSelection(sg, true);
        // finally invert the column selection and get the new sequence
        // selection.
        invertColSel_actionPerformed(null);
        toggleCols = true;
      }
    }

    if (toggleSeqs)
    {
      if (sg != null && sg.getSize() != viewport.getAlignment().getHeight())
      {
        hideSelSequences_actionPerformed(null);
        hide = true;
      }
      else if (!(toggleCols && viewport.getColumnSelection().getSelected()
              .size() > 0))
      {
        showAllSeqs_actionPerformed(null);
      }
    }

    if (toggleCols)
    {
      if (viewport.getColumnSelection().getSelected().size() > 0)
      {
        hideSelColumns_actionPerformed(null);
        if (!toggleSeqs)
        {
          viewport.setSelectionGroup(sg);
        }
      }
      else if (!hide)
      {
        showAllColumns_actionPerformed(null);
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GAlignFrame#hideAllButSelection_actionPerformed(java.awt.
   * event.ActionEvent)
   */
  @Override
  public void hideAllButSelection_actionPerformed(ActionEvent e)
  {
    toggleHiddenRegions(false, false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GAlignFrame#hideAllSelection_actionPerformed(java.awt.event
   * .ActionEvent)
   */
  @Override
  public void hideAllSelection_actionPerformed(ActionEvent e)
  {
    SequenceGroup sg = viewport.getSelectionGroup();
    viewport.expandColSelection(sg, false);
    viewport.hideAllSelectedSeqs();
    viewport.hideSelectedColumns();
    alignPanel.paintAlignment(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GAlignFrame#showAllhidden_actionPerformed(java.awt.event.
   * ActionEvent)
   */
  @Override
  public void showAllhidden_actionPerformed(ActionEvent e)
  {
    viewport.showAllHiddenColumns();
    viewport.showAllHiddenSeqs();
    alignPanel.paintAlignment(true);
  }

  @Override
  public void hideSelColumns_actionPerformed(ActionEvent e)
  {
    viewport.hideSelectedColumns();
    alignPanel.paintAlignment(true);
  }

  @Override
  public void hiddenMarkers_actionPerformed(ActionEvent e)
  {
    viewport.setShowHiddenMarkers(hiddenMarkers.isSelected());
    repaint();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void scaleAbove_actionPerformed(ActionEvent e)
  {
    viewport.setScaleAboveWrapped(scaleAbove.isSelected());
    alignPanel.paintAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void scaleLeft_actionPerformed(ActionEvent e)
  {
    viewport.setScaleLeftWrapped(scaleLeft.isSelected());
    alignPanel.paintAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void scaleRight_actionPerformed(ActionEvent e)
  {
    viewport.setScaleRightWrapped(scaleRight.isSelected());
    alignPanel.paintAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void viewBoxesMenuItem_actionPerformed(ActionEvent e)
  {
    viewport.setShowBoxes(viewBoxesMenuItem.isSelected());
    alignPanel.paintAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void viewTextMenuItem_actionPerformed(ActionEvent e)
  {
    viewport.setShowText(viewTextMenuItem.isSelected());
    alignPanel.paintAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void renderGapsMenuItem_actionPerformed(ActionEvent e)
  {
    viewport.setRenderGaps(renderGapsMenuItem.isSelected());
    alignPanel.paintAlignment(true);
  }

  public FeatureSettings featureSettings;

  @Override
  public void featureSettings_actionPerformed(ActionEvent e)
  {
    if (featureSettings != null)
    {
      featureSettings.close();
      featureSettings = null;
    }
    if (!showSeqFeatures.isSelected())
    {
      // make sure features are actually displayed
      showSeqFeatures.setSelected(true);
      showSeqFeatures_actionPerformed(null);
    }
    featureSettings = new FeatureSettings(this);
  }

  /**
   * Set or clear 'Show Sequence Features'
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  @Override
  public void showSeqFeatures_actionPerformed(ActionEvent evt)
  {
    viewport.setShowSequenceFeatures(showSeqFeatures.isSelected());
    alignPanel.paintAlignment(true);
    if (alignPanel.getOverviewPanel() != null)
    {
      alignPanel.getOverviewPanel().updateOverviewImage();
    }
  }

  /**
   * Set or clear 'Show Sequence Features'
   * 
   * @param evt
   *          DOCUMENT ME!
   */
  @Override
  public void showSeqFeaturesHeight_actionPerformed(ActionEvent evt)
  {
    viewport.setShowSequenceFeaturesHeight(showSeqFeaturesHeight
            .isSelected());
    if (viewport.getShowSequenceFeaturesHeight())
    {
      // ensure we're actually displaying features
      viewport.setShowSequenceFeatures(true);
      showSeqFeatures.setSelected(true);
    }
    alignPanel.paintAlignment(true);
    if (alignPanel.getOverviewPanel() != null)
    {
      alignPanel.getOverviewPanel().updateOverviewImage();
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void annotationPanelMenuItem_actionPerformed(ActionEvent e)
  {
    viewport.setShowAnnotation(annotationPanelMenuItem.isSelected());
    alignPanel.setAnnotationVisible(annotationPanelMenuItem.isSelected());
  }

  @Override
  public void alignmentProperties()
  {
    JEditorPane editPane = new JEditorPane("text/html", "");
    editPane.setEditable(false);
    StringBuffer contents = new AlignmentProperties(viewport.getAlignment())
            .formatAsHtml();
    editPane.setText("<html>" + contents.toString() + "</html>");
    JInternalFrame frame = new JInternalFrame();
    frame.getContentPane().add(new JScrollPane(editPane));

    Desktop.instance.addInternalFrame(frame, "Alignment Properties: "
            + getTitle(), 500, 400);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void overviewMenuItem_actionPerformed(ActionEvent e)
  {
    if (alignPanel.overviewPanel != null)
    {
      return;
    }

    JInternalFrame frame = new JInternalFrame();
    OverviewPanel overview = new OverviewPanel(alignPanel);
    frame.setContentPane(overview);
    Desktop.addInternalFrame(frame, "Overview " + this.getTitle(),
            frame.getWidth(), frame.getHeight());
    frame.pack();
    frame.setLayer(JLayeredPane.PALETTE_LAYER);
    frame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter()
    {
      @Override
      public void internalFrameClosed(
              javax.swing.event.InternalFrameEvent evt)
      {
        alignPanel.setOverviewPanel(null);
      };
    });

    alignPanel.setOverviewPanel(overview);
  }

  @Override
  public void textColour_actionPerformed(ActionEvent e)
  {
    new TextColourChooser().chooseColour(alignPanel, null);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void noColourmenuItem_actionPerformed(ActionEvent e)
  {
    changeColour(null);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void clustalColour_actionPerformed(ActionEvent e)
  {
    changeColour(new ClustalxColourScheme(viewport.getAlignment(),
            viewport.getHiddenRepSequences()));
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void zappoColour_actionPerformed(ActionEvent e)
  {
    changeColour(new ZappoColourScheme());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void taylorColour_actionPerformed(ActionEvent e)
  {
    changeColour(new TaylorColourScheme());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void hydrophobicityColour_actionPerformed(ActionEvent e)
  {
    changeColour(new HydrophobicColourScheme());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void helixColour_actionPerformed(ActionEvent e)
  {
    changeColour(new HelixColourScheme());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void strandColour_actionPerformed(ActionEvent e)
  {
    changeColour(new StrandColourScheme());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void turnColour_actionPerformed(ActionEvent e)
  {
    changeColour(new TurnColourScheme());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void buriedColour_actionPerformed(ActionEvent e)
  {
    changeColour(new BuriedColourScheme());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void nucleotideColour_actionPerformed(ActionEvent e)
  {
    changeColour(new NucleotideColourScheme());
  }

  @Override
  public void purinePyrimidineColour_actionPerformed(ActionEvent e)
  {
    changeColour(new PurinePyrimidineColourScheme());
  }

  /*
   * public void covariationColour_actionPerformed(ActionEvent e) {
   * changeColour(new
   * CovariationColourScheme(viewport.getAlignment().getAlignmentAnnotation
   * ()[0])); }
   */
  @Override
  public void annotationColour_actionPerformed(ActionEvent e)
  {
    new AnnotationColourChooser(viewport, alignPanel);
  }

  @Override
  public void rnahelicesColour_actionPerformed(ActionEvent e)
  {
    new RNAHelicesColourChooser(viewport, alignPanel);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void applyToAllGroups_actionPerformed(ActionEvent e)
  {
    viewport.setColourAppliesToAllGroups(applyToAllGroups.isSelected());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param cs
   *          DOCUMENT ME!
   */
  public void changeColour(ColourSchemeI cs)
  {
    // TODO: compare with applet and pull up to model method
    int threshold = 0;

    if (cs != null)
    {
      if (viewport.getAbovePIDThreshold())
      {
        threshold = SliderPanel.setPIDSliderSource(alignPanel, cs,
                "Background");

        cs.setThreshold(threshold, viewport.getIgnoreGapsConsensus());

        viewport.setGlobalColourScheme(cs);
      }
      else
      {
        cs.setThreshold(0, viewport.getIgnoreGapsConsensus());
      }

      if (viewport.getConservationSelected())
      {

        Alignment al = (Alignment) viewport.getAlignment();
        Conservation c = new Conservation("All",
                ResidueProperties.propHash, 3, al.getSequences(), 0,
                al.getWidth() - 1);

        c.calculate();
        c.verdict(false, viewport.getConsPercGaps());

        cs.setConservation(c);

        cs.setConservationInc(SliderPanel.setConservationSlider(alignPanel,
                cs, "Background"));
      }
      else
      {
        cs.setConservation(null);
      }

      cs.setConsensus(viewport.getSequenceConsensusHash());
    }

    viewport.setGlobalColourScheme(cs);

    if (viewport.getColourAppliesToAllGroups())
    {

      for (SequenceGroup sg : viewport.getAlignment().getGroups())
      {
        if (cs == null)
        {
          sg.cs = null;
          continue;
        }

        if (cs instanceof ClustalxColourScheme)
        {
          sg.cs = new ClustalxColourScheme(sg,
                  viewport.getHiddenRepSequences());
        }
        else if (cs instanceof UserColourScheme)
        {
          sg.cs = new UserColourScheme(((UserColourScheme) cs).getColours());
        }
        else
        {
          try
          {
            sg.cs = cs.getClass().newInstance();
          } catch (Exception ex)
          {
          }
        }

        if (viewport.getAbovePIDThreshold()
                || cs instanceof PIDColourScheme
                || cs instanceof Blosum62ColourScheme)
        {
          sg.cs.setThreshold(threshold, viewport.getIgnoreGapsConsensus());

          sg.cs.setConsensus(AAFrequency.calculate(
                  sg.getSequences(viewport.getHiddenRepSequences()),
                  sg.getStartRes(), sg.getEndRes() + 1));
        }
        else
        {
          sg.cs.setThreshold(0, viewport.getIgnoreGapsConsensus());
        }

        if (viewport.getConservationSelected())
        {
          Conservation c = new Conservation("Group",
                  ResidueProperties.propHash, 3, sg.getSequences(viewport
                          .getHiddenRepSequences()), sg.getStartRes(),
                  sg.getEndRes() + 1);
          c.calculate();
          c.verdict(false, viewport.getConsPercGaps());
          sg.cs.setConservation(c);
        }
        else
        {
          sg.cs.setConservation(null);
        }
      }
    }

    if (alignPanel.getOverviewPanel() != null)
    {
      alignPanel.getOverviewPanel().updateOverviewImage();
    }

    alignPanel.paintAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void modifyPID_actionPerformed(ActionEvent e)
  {
    if (viewport.getAbovePIDThreshold()
            && viewport.getGlobalColourScheme() != null)
    {
      SliderPanel.setPIDSliderSource(alignPanel,
              viewport.getGlobalColourScheme(), "Background");
      SliderPanel.showPIDSlider();
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void modifyConservation_actionPerformed(ActionEvent e)
  {
    if (viewport.getConservationSelected()
            && viewport.getGlobalColourScheme() != null)
    {
      SliderPanel.setConservationSlider(alignPanel,
              viewport.getGlobalColourScheme(), "Background");
      SliderPanel.showConservationSlider();
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void conservationMenuItem_actionPerformed(ActionEvent e)
  {
    viewport.setConservationSelected(conservationMenuItem.isSelected());

    viewport.setAbovePIDThreshold(false);
    abovePIDThreshold.setSelected(false);

    changeColour(viewport.getGlobalColourScheme());

    modifyConservation_actionPerformed(null);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void abovePIDThreshold_actionPerformed(ActionEvent e)
  {
    viewport.setAbovePIDThreshold(abovePIDThreshold.isSelected());

    conservationMenuItem.setSelected(false);
    viewport.setConservationSelected(false);

    changeColour(viewport.getGlobalColourScheme());

    modifyPID_actionPerformed(null);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void userDefinedColour_actionPerformed(ActionEvent e)
  {
    if (e.getActionCommand().equals("User Defined..."))
    {
      new UserDefinedColours(alignPanel, null);
    }
    else
    {
      UserColourScheme udc = (UserColourScheme) UserDefinedColours
              .getUserColourSchemes().get(e.getActionCommand());

      changeColour(udc);
    }
  }

  public void updateUserColourMenu()
  {

    Component[] menuItems = colourMenu.getMenuComponents();
    int i, iSize = menuItems.length;
    for (i = 0; i < iSize; i++)
    {
      if (menuItems[i].getName() != null
              && menuItems[i].getName().equals("USER_DEFINED"))
      {
        colourMenu.remove(menuItems[i]);
        iSize--;
      }
    }
    if (jalview.gui.UserDefinedColours.getUserColourSchemes() != null)
    {
      java.util.Enumeration userColours = jalview.gui.UserDefinedColours
              .getUserColourSchemes().keys();

      while (userColours.hasMoreElements())
      {
        final JRadioButtonMenuItem radioItem = new JRadioButtonMenuItem(
                userColours.nextElement().toString());
        radioItem.setName("USER_DEFINED");
        radioItem.addMouseListener(new MouseAdapter()
        {
          @Override
          public void mousePressed(MouseEvent evt)
          {
            if (evt.isControlDown()
                    || SwingUtilities.isRightMouseButton(evt))
            {
              radioItem.removeActionListener(radioItem.getActionListeners()[0]);

              int option = JOptionPane.showInternalConfirmDialog(
                      jalview.gui.Desktop.desktop,
                      "Remove from default list?",
                      "Remove user defined colour",
                      JOptionPane.YES_NO_OPTION);
              if (option == JOptionPane.YES_OPTION)
              {
                jalview.gui.UserDefinedColours
                        .removeColourFromDefaults(radioItem.getText());
                colourMenu.remove(radioItem);
              }
              else
              {
                radioItem.addActionListener(new ActionListener()
                {
                  @Override
                  public void actionPerformed(ActionEvent evt)
                  {
                    userDefinedColour_actionPerformed(evt);
                  }
                });
              }
            }
          }
        });
        radioItem.addActionListener(new ActionListener()
        {
          @Override
          public void actionPerformed(ActionEvent evt)
          {
            userDefinedColour_actionPerformed(evt);
          }
        });

        colourMenu.insert(radioItem, 15);
        colours.add(radioItem);
      }
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void PIDColour_actionPerformed(ActionEvent e)
  {
    changeColour(new PIDColourScheme());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void BLOSUM62Colour_actionPerformed(ActionEvent e)
  {
    changeColour(new Blosum62ColourScheme());
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void sortPairwiseMenuItem_actionPerformed(ActionEvent e)
  {
    SequenceI[] oldOrder = viewport.getAlignment().getSequencesArray();
    AlignmentSorter.sortByPID(viewport.getAlignment(), viewport
            .getAlignment().getSequenceAt(0), null);
    addHistoryItem(new OrderCommand("Pairwise Sort", oldOrder,
            viewport.getAlignment()));
    alignPanel.paintAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void sortIDMenuItem_actionPerformed(ActionEvent e)
  {
    SequenceI[] oldOrder = viewport.getAlignment().getSequencesArray();
    AlignmentSorter.sortByID(viewport.getAlignment());
    addHistoryItem(new OrderCommand("ID Sort", oldOrder,
            viewport.getAlignment()));
    alignPanel.paintAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void sortLengthMenuItem_actionPerformed(ActionEvent e)
  {
    SequenceI[] oldOrder = viewport.getAlignment().getSequencesArray();
    AlignmentSorter.sortByLength(viewport.getAlignment());
    addHistoryItem(new OrderCommand("Length Sort", oldOrder,
            viewport.getAlignment()));
    alignPanel.paintAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void sortGroupMenuItem_actionPerformed(ActionEvent e)
  {
    SequenceI[] oldOrder = viewport.getAlignment().getSequencesArray();
    AlignmentSorter.sortByGroup(viewport.getAlignment());
    addHistoryItem(new OrderCommand("Group Sort", oldOrder,
            viewport.getAlignment()));

    alignPanel.paintAlignment(true);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void removeRedundancyMenuItem_actionPerformed(ActionEvent e)
  {
    new RedundancyPanel(alignPanel, this);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void pairwiseAlignmentMenuItem_actionPerformed(ActionEvent e)
  {
    if ((viewport.getSelectionGroup() == null)
            || (viewport.getSelectionGroup().getSize() < 2))
    {
      JOptionPane.showInternalMessageDialog(this,
              "You must select at least 2 sequences.", "Invalid Selection",
              JOptionPane.WARNING_MESSAGE);
    }
    else
    {
      JInternalFrame frame = new JInternalFrame();
      frame.setContentPane(new PairwiseAlignPanel(viewport));
      Desktop.addInternalFrame(frame, "Pairwise Alignment", 600, 500);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void PCAMenuItem_actionPerformed(ActionEvent e)
  {
    if (((viewport.getSelectionGroup() != null)
            && (viewport.getSelectionGroup().getSize() < 4) && (viewport
            .getSelectionGroup().getSize() > 0))
            || (viewport.getAlignment().getHeight() < 4))
    {
      JOptionPane.showInternalMessageDialog(this,
              "Principal component analysis must take\n"
                      + "at least 4 input sequences.",
              "Sequence selection insufficient",
              JOptionPane.WARNING_MESSAGE);

      return;
    }

    new PCAPanel(alignPanel);
  }

  @Override
  public void autoCalculate_actionPerformed(ActionEvent e)
  {
    viewport.autoCalculateConsensus = autoCalculate.isSelected();
    if (viewport.autoCalculateConsensus)
    {
      viewport.firePropertyChange("alignment", null, viewport
              .getAlignment().getSequences());
    }
  }

  @Override
  public void sortByTreeOption_actionPerformed(ActionEvent e)
  {
    viewport.sortByTree = sortByTree.isSelected();
  }

  @Override
  protected void listenToViewSelections_actionPerformed(ActionEvent e)
  {
    viewport.followSelection = listenToViewSelections.isSelected();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void averageDistanceTreeMenuItem_actionPerformed(ActionEvent e)
  {
    NewTreePanel("AV", "PID", "Average distance tree using PID");
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  public void neighbourTreeMenuItem_actionPerformed(ActionEvent e)
  {
    NewTreePanel("NJ", "PID", "Neighbour joining tree using PID");
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void njTreeBlosumMenuItem_actionPerformed(ActionEvent e)
  {
    NewTreePanel("NJ", "BL", "Neighbour joining tree using BLOSUM62");
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void avTreeBlosumMenuItem_actionPerformed(ActionEvent e)
  {
    NewTreePanel("AV", "BL", "Average distance tree using BLOSUM62");
  }

  /**
   * DOCUMENT ME!
   * 
   * @param type
   *          DOCUMENT ME!
   * @param pwType
   *          DOCUMENT ME!
   * @param title
   *          DOCUMENT ME!
   */
  void NewTreePanel(String type, String pwType, String title)
  {
    TreePanel tp;

    if (viewport.getSelectionGroup() != null
            && viewport.getSelectionGroup().getSize() > 0)
    {
      if (viewport.getSelectionGroup().getSize() < 3)
      {
        JOptionPane
                .showMessageDialog(
                        Desktop.desktop,
                        "You need to have more than two sequences selected to build a tree!",
                        "Not enough sequences", JOptionPane.WARNING_MESSAGE);
        return;
      }

      SequenceGroup sg = viewport.getSelectionGroup();

      /* Decide if the selection is a column region */
      for (SequenceI _s : sg.getSequences())
      {
        if (_s.getLength() < sg.getEndRes())
        {
          JOptionPane
                  .showMessageDialog(
                          Desktop.desktop,
                          "The selected region to create a tree may\nonly contain residues or gaps.\n"
                                  + "Try using the Pad function in the edit menu,\n"
                                  + "or one of the multiple sequence alignment web services.",
                          "Sequences in selection are not aligned",
                          JOptionPane.WARNING_MESSAGE);

          return;
        }
      }

      title = title + " on region";
      tp = new TreePanel(alignPanel, type, pwType);
    }
    else
    {
      // are the visible sequences aligned?
      if (!viewport.getAlignment().isAligned(false))
      {
        JOptionPane
                .showMessageDialog(
                        Desktop.desktop,
                        "The sequences must be aligned before creating a tree.\n"
                                + "Try using the Pad function in the edit menu,\n"
                                + "or one of the multiple sequence alignment web services.",
                        "Sequences not aligned",
                        JOptionPane.WARNING_MESSAGE);

        return;
      }

      if (viewport.getAlignment().getHeight() < 2)
      {
        return;
      }

      tp = new TreePanel(alignPanel, type, pwType);
    }

    title += " from ";

    if (viewport.viewName != null)
    {
      title += viewport.viewName + " of ";
    }

    title += this.title;

    Desktop.addInternalFrame(tp, title, 600, 500);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param title
   *          DOCUMENT ME!
   * @param order
   *          DOCUMENT ME!
   */
  public void addSortByOrderMenuItem(String title,
          final AlignmentOrder order)
  {
    final JMenuItem item = new JMenuItem("by " + title);
    sort.add(item);
    item.addActionListener(new java.awt.event.ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        SequenceI[] oldOrder = viewport.getAlignment().getSequencesArray();

        // TODO: JBPNote - have to map order entries to curent SequenceI
        // pointers
        AlignmentSorter.sortBy(viewport.getAlignment(), order);

        addHistoryItem(new OrderCommand(order.getName(), oldOrder, viewport
                .getAlignment()));

        alignPanel.paintAlignment(true);
      }
    });
  }

  /**
   * Add a new sort by annotation score menu item
   * 
   * @param sort
   *          the menu to add the option to
   * @param scoreLabel
   *          the label used to retrieve scores for each sequence on the
   *          alignment
   */
  public void addSortByAnnotScoreMenuItem(JMenu sort,
          final String scoreLabel)
  {
    final JMenuItem item = new JMenuItem(scoreLabel);
    sort.add(item);
    item.addActionListener(new java.awt.event.ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        SequenceI[] oldOrder = viewport.getAlignment().getSequencesArray();
        AlignmentSorter.sortByAnnotationScore(scoreLabel,
                viewport.getAlignment());// ,viewport.getSelectionGroup());
        addHistoryItem(new OrderCommand("Sort by " + scoreLabel, oldOrder,
                viewport.getAlignment()));
        alignPanel.paintAlignment(true);
      }
    });
  }

  /**
   * last hash for alignment's annotation array - used to minimise cost of
   * rebuild.
   */
  protected int _annotationScoreVectorHash;

  /**
   * search the alignment and rebuild the sort by annotation score submenu the
   * last alignment annotation vector hash is stored to minimize cost of
   * rebuilding in subsequence calls.
   * 
   */
  @Override
  public void buildSortByAnnotationScoresMenu()
  {
    if (viewport.getAlignment().getAlignmentAnnotation() == null)
    {
      return;
    }

    if (viewport.getAlignment().getAlignmentAnnotation().hashCode() != _annotationScoreVectorHash)
    {
      sortByAnnotScore.removeAll();
      // almost certainly a quicker way to do this - but we keep it simple
      Hashtable scoreSorts = new Hashtable();
      AlignmentAnnotation aann[];
      for (SequenceI sqa : viewport.getAlignment().getSequences())
      {
        aann = sqa.getAnnotation();
        for (int i = 0; aann != null && i < aann.length; i++)
        {
          if (aann[i].hasScore() && aann[i].sequenceRef != null)
          {
            scoreSorts.put(aann[i].label, aann[i].label);
          }
        }
      }
      Enumeration labels = scoreSorts.keys();
      while (labels.hasMoreElements())
      {
        addSortByAnnotScoreMenuItem(sortByAnnotScore,
                (String) labels.nextElement());
      }
      sortByAnnotScore.setVisible(scoreSorts.size() > 0);
      scoreSorts.clear();

      _annotationScoreVectorHash = viewport.getAlignment()
              .getAlignmentAnnotation().hashCode();
    }
  }

  /**
   * Maintain the Order by->Displayed Tree menu. Creates a new menu item for a
   * TreePanel with an appropriate <code>jalview.analysis.AlignmentSorter</code>
   * call. Listeners are added to remove the menu item when the treePanel is
   * closed, and adjust the tree leaf to sequence mapping when the alignment is
   * modified.
   * 
   * @param treePanel
   *          Displayed tree window.
   * @param title
   *          SortBy menu item title.
   */
  @Override
  public void buildTreeMenu()
  {
    sortByTreeMenu.removeAll();

    Vector comps = (Vector) PaintRefresher.components.get(viewport
            .getSequenceSetId());
    Vector treePanels = new Vector();
    int i, iSize = comps.size();
    for (i = 0; i < iSize; i++)
    {
      if (comps.elementAt(i) instanceof TreePanel)
      {
        treePanels.add(comps.elementAt(i));
      }
    }

    iSize = treePanels.size();

    if (iSize < 1)
    {
      sortByTreeMenu.setVisible(false);
      return;
    }

    sortByTreeMenu.setVisible(true);

    for (i = 0; i < treePanels.size(); i++)
    {
      final TreePanel tp = (TreePanel) treePanels.elementAt(i);
      final JMenuItem item = new JMenuItem(tp.getTitle());
      final NJTree tree = ((TreePanel) treePanels.elementAt(i)).getTree();
      item.addActionListener(new java.awt.event.ActionListener()
      {
        @Override
        public void actionPerformed(ActionEvent e)
        {
          tp.sortByTree_actionPerformed(null);
          addHistoryItem(tp.sortAlignmentIn(alignPanel));

        }
      });

      sortByTreeMenu.add(item);
    }
  }

  public boolean sortBy(AlignmentOrder alorder, String undoname)
  {
    SequenceI[] oldOrder = viewport.getAlignment().getSequencesArray();
    AlignmentSorter.sortBy(viewport.getAlignment(), alorder);
    if (undoname != null)
    {
      addHistoryItem(new OrderCommand(undoname, oldOrder,
              viewport.getAlignment()));
    }
    alignPanel.paintAlignment(true);
    return true;
  }

  /**
   * Work out whether the whole set of sequences or just the selected set will
   * be submitted for multiple alignment.
   * 
   */
  public jalview.datamodel.AlignmentView gatherSequencesForAlignment()
  {
    // Now, check we have enough sequences
    AlignmentView msa = null;

    if ((viewport.getSelectionGroup() != null)
            && (viewport.getSelectionGroup().getSize() > 1))
    {
      // JBPNote UGLY! To prettify, make SequenceGroup and Alignment conform to
      // some common interface!
      /*
       * SequenceGroup seqs = viewport.getSelectionGroup(); int sz; msa = new
       * SequenceI[sz = seqs.getSize(false)];
       * 
       * for (int i = 0; i < sz; i++) { msa[i] = (SequenceI)
       * seqs.getSequenceAt(i); }
       */
      msa = viewport.getAlignmentView(true);
    }
    else
    {
      /*
       * Vector seqs = viewport.getAlignment().getSequences();
       * 
       * if (seqs.size() > 1) { msa = new SequenceI[seqs.size()];
       * 
       * for (int i = 0; i < seqs.size(); i++) { msa[i] = (SequenceI)
       * seqs.elementAt(i); } }
       */
      msa = viewport.getAlignmentView(false);
    }
    return msa;
  }

  /**
   * Decides what is submitted to a secondary structure prediction service: the
   * first sequence in the alignment, or in the current selection, or, if the
   * alignment is 'aligned' (ie padded with gaps), then the currently selected
   * region or the whole alignment. (where the first sequence in the set is the
   * one that the prediction will be for).
   */
  public AlignmentView gatherSeqOrMsaForSecStrPrediction()
  {
    AlignmentView seqs = null;

    if ((viewport.getSelectionGroup() != null)
            && (viewport.getSelectionGroup().getSize() > 0))
    {
      seqs = viewport.getAlignmentView(true);
    }
    else
    {
      seqs = viewport.getAlignmentView(false);
    }
    // limit sequences - JBPNote in future - could spawn multiple prediction
    // jobs
    // TODO: viewport.getAlignment().isAligned is a global state - the local
    // selection may well be aligned - we preserve 2.0.8 behaviour for moment.
    if (!viewport.getAlignment().isAligned(false))
    {
      seqs.setSequences(new SeqCigar[]
      { seqs.getSequences()[0] });
      // TODO: if seqs.getSequences().length>1 then should really have warned
      // user!

    }
    return seqs;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  @Override
  protected void LoadtreeMenuItem_actionPerformed(ActionEvent e)
  {
    // Pick the tree file
    JalviewFileChooser chooser = new JalviewFileChooser(
            jalview.bin.Cache.getProperty("LAST_DIRECTORY"));
    chooser.setFileView(new JalviewFileView());
    chooser.setDialogTitle("Select a newick-like tree file");
    chooser.setToolTipText("Load a tree file");

    int value = chooser.showOpenDialog(null);

    if (value == JalviewFileChooser.APPROVE_OPTION)
    {
      String choice = chooser.getSelectedFile().getPath();
      jalview.bin.Cache.setProperty("LAST_DIRECTORY", choice);
      jalview.io.NewickFile fin = null;
      try
      {
        fin = new jalview.io.NewickFile(choice, "File");
        viewport.setCurrentTree(ShowNewickTree(fin, choice).getTree());
      } catch (Exception ex)
      {
        JOptionPane.showMessageDialog(Desktop.desktop, ex.getMessage(),
                "Problem reading tree file", JOptionPane.WARNING_MESSAGE);
        ex.printStackTrace();
      }
      if (fin != null && fin.hasWarningMessage())
      {
        JOptionPane.showMessageDialog(Desktop.desktop,
                fin.getWarningMessage(), "Possible problem with tree file",
                JOptionPane.WARNING_MESSAGE);
      }
    }
  }

  @Override
  protected void tcoffeeColorScheme_actionPerformed(ActionEvent e)
  {
    changeColour(new TCoffeeColourScheme(alignPanel.getAlignment()));
  }

  public TreePanel ShowNewickTree(NewickFile nf, String title)
  {
    return ShowNewickTree(nf, title, 600, 500, 4, 5);
  }

  public TreePanel ShowNewickTree(NewickFile nf, String title,
          AlignmentView input)
  {
    return ShowNewickTree(nf, title, input, 600, 500, 4, 5);
  }

  public TreePanel ShowNewickTree(NewickFile nf, String title, int w,
          int h, int x, int y)
  {
    return ShowNewickTree(nf, title, null, w, h, x, y);
  }

  /**
   * Add a treeviewer for the tree extracted from a newick file object to the
   * current alignment view
   * 
   * @param nf
   *          the tree
   * @param title
   *          tree viewer title
   * @param input
   *          Associated alignment input data (or null)
   * @param w
   *          width
   * @param h
   *          height
   * @param x
   *          position
   * @param y
   *          position
   * @return TreePanel handle
   */
  public TreePanel ShowNewickTree(NewickFile nf, String title,
          AlignmentView input, int w, int h, int x, int y)
  {
    TreePanel tp = null;

    try
    {
      nf.parse();

      if (nf.getTree() != null)
      {
        tp = new TreePanel(alignPanel, "FromFile", title, nf, input);

        tp.setSize(w, h);

        if (x > 0 && y > 0)
        {
          tp.setLocation(x, y);
        }

        Desktop.addInternalFrame(tp, title, w, h);
      }
    } catch (Exception ex)
    {
      ex.printStackTrace();
    }

    return tp;
  }

  private boolean buildingMenu = false;

  /**
   * Generates menu items and listener event actions for web service clients
   * 
   */
  public void BuildWebServiceMenu()
  {
    while (buildingMenu)
    {
      try
      {
        System.err.println("Waiting for building menu to finish.");
        Thread.sleep(10);
      } catch (Exception e)
      {
      }
      ;
    }
    final AlignFrame me = this;
    buildingMenu = true;
    new Thread(new Runnable()
    {
      @Override
      public void run()
      {
        try
        {
          System.err.println("Building ws menu again "
                  + Thread.currentThread());
          // TODO: add support for context dependent disabling of services based
          // on
          // alignment and current selection
          // TODO: add additional serviceHandle parameter to specify abstract
          // handler
          // class independently of AbstractName
          // TODO: add in rediscovery GUI function to restart discoverer
          // TODO: group services by location as well as function and/or
          // introduce
          // object broker mechanism.
          final Vector<JMenu> wsmenu = new Vector<JMenu>();
          final IProgressIndicator af = me;
          final JMenu msawsmenu = new JMenu("Alignment");
          final JMenu secstrmenu = new JMenu(
                  "Secondary Structure Prediction");
          final JMenu seqsrchmenu = new JMenu("Sequence Database Search");
          final JMenu analymenu = new JMenu("Analysis");
          final JMenu dismenu = new JMenu("Protein Disorder");
          // JAL-940 - only show secondary structure prediction services from
          // the legacy server
          if (// Cache.getDefault("SHOW_JWS1_SERVICES", true)
              // &&
          Discoverer.services != null && (Discoverer.services.size() > 0))
          {
            // TODO: refactor to allow list of AbstractName/Handler bindings to
            // be
            // stored or retrieved from elsewhere
            Vector msaws = null; // (Vector) Discoverer.services.get("MsaWS");
            Vector secstrpr = (Vector) Discoverer.services
                    .get("SecStrPred");
            Vector seqsrch = null; // (Vector)
                                   // Discoverer.services.get("SeqSearch");
            // TODO: move GUI generation code onto service implementation - so a
            // client instance attaches itself to the GUI with method call like
            // jalview.ws.MsaWSClient.bind(servicehandle, Desktop.instance,
            // alignframe)
            if (msaws != null)
            {
              // Add any Multiple Sequence Alignment Services
              for (int i = 0, j = msaws.size(); i < j; i++)
              {
                final ext.vamsas.ServiceHandle sh = (ext.vamsas.ServiceHandle) msaws
                        .get(i);
                jalview.ws.WSMenuEntryProviderI impl = jalview.ws.jws1.Discoverer
                        .getServiceClient(sh);
                impl.attachWSMenuEntry(msawsmenu, me);

              }
            }
            if (secstrpr != null)
            {
              // Add any secondary structure prediction services
              for (int i = 0, j = secstrpr.size(); i < j; i++)
              {
                final ext.vamsas.ServiceHandle sh = (ext.vamsas.ServiceHandle) secstrpr
                        .get(i);
                jalview.ws.WSMenuEntryProviderI impl = jalview.ws.jws1.Discoverer
                        .getServiceClient(sh);
                impl.attachWSMenuEntry(secstrmenu, me);
              }
            }
            if (seqsrch != null)
            {
              // Add any sequence search services
              for (int i = 0, j = seqsrch.size(); i < j; i++)
              {
                final ext.vamsas.ServiceHandle sh = (ext.vamsas.ServiceHandle) seqsrch
                        .elementAt(i);
                jalview.ws.WSMenuEntryProviderI impl = jalview.ws.jws1.Discoverer
                        .getServiceClient(sh);
                impl.attachWSMenuEntry(seqsrchmenu, me);
              }
            }
          }

          // Add all submenus in the order they should appear on the web
          // services menu
          wsmenu.add(msawsmenu);
          wsmenu.add(secstrmenu);
          wsmenu.add(dismenu);
          wsmenu.add(analymenu);
          // final ArrayList<JMenu> submens=new ArrayList<JMenu>();
          // submens.add(msawsmenu);
          // submens.add(secstrmenu);
          // submens.add(dismenu);
          // submens.add(analymenu);

          // No search services yet
          // wsmenu.add(seqsrchmenu);

          javax.swing.SwingUtilities.invokeLater(new Runnable()
          {
            @Override
            public void run()
            {
              try
              {
                webService.removeAll();
                // first, add discovered services onto the webservices menu
                if (wsmenu.size() > 0)
                {
                  for (int i = 0, j = wsmenu.size(); i < j; i++)
                  {
                    webService.add(wsmenu.get(i));
                  }
                }
                else
                {
                  webService.add(me.webServiceNoServices);
                }
                // TODO: move into separate menu builder class.
                if (Cache.getDefault("SHOW_JWS2_SERVICES", true))
                {
                  Jws2Discoverer jws2servs = Jws2Discoverer.getDiscoverer();
                  if (jws2servs != null)
                  {
                    if (jws2servs.hasServices())
                    {
                      jws2servs.attachWSMenuEntry(webService, me);
                    }
                    if (jws2servs.isRunning())
                    {
                      JMenuItem tm = new JMenuItem(
                              "Still discovering JABA Services");
                      tm.setEnabled(false);
                      webService.add(tm);
                    }
                  }
                }

                build_urlServiceMenu(me.webService);
                build_fetchdbmenu(webService);
                for (JMenu item : wsmenu)
                {
                  if (item.getItemCount() == 0)
                  {
                    item.setEnabled(false);
                  }
                  else
                  {
                    item.setEnabled(true);
                  }
                }
              } catch (Exception e)
              {
                Cache.log
                        .debug("Exception during web service menu building process.",
                                e);
              }
              ;
            }
          });
        } catch (Exception e)
        {
        }
        ;

        buildingMenu = false;
      }
    }).start();

  }

  /**
   * construct any groupURL type service menu entries.
   * 
   * @param webService
   */
  private void build_urlServiceMenu(JMenu webService)
  {
    // TODO: remove this code when 2.7 is released
    // DEBUG - alignmentView
    /*
     * JMenuItem testAlView = new JMenuItem("Test AlignmentView"); final
     * AlignFrame af = this; testAlView.addActionListener(new ActionListener() {
     * 
     * @Override public void actionPerformed(ActionEvent e) {
     * jalview.datamodel.AlignmentView
     * .testSelectionViews(af.viewport.getAlignment(),
     * af.viewport.getColumnSelection(), af.viewport.selectionGroup); }
     * 
     * }); webService.add(testAlView);
     */
    // TODO: refactor to RestClient discoverer and merge menu entries for
    // rest-style services with other types of analysis/calculation service
    // SHmmr test client - still being implemented.
    // DEBUG - alignmentView

    for (jalview.ws.rest.RestClient client : jalview.ws.rest.RestClient
            .getRestClients())
    {
      client.attachWSMenuEntry(
              JvSwingUtils.findOrCreateMenu(webService, client.getAction()),
              this);
    }

    if (Cache.getDefault("SHOW_ENFIN_SERVICES", true))
    {
      jalview.ws.EnfinEnvision2OneWay.getInstance().attachWSMenuEntry(
              webService, this);
    }
  }

  /*
   * public void vamsasStore_actionPerformed(ActionEvent e) { JalviewFileChooser
   * chooser = new JalviewFileChooser(jalview.bin.Cache.
   * getProperty("LAST_DIRECTORY"));
   * 
   * chooser.setFileView(new JalviewFileView()); chooser.setDialogTitle("Export
   * to Vamsas file"); chooser.setToolTipText("Export");
   * 
   * int value = chooser.showSaveDialog(this);
   * 
   * if (value == JalviewFileChooser.APPROVE_OPTION) {
   * jalview.io.VamsasDatastore vs = new jalview.io.VamsasDatastore(viewport);
   * //vs.store(chooser.getSelectedFile().getAbsolutePath() ); vs.storeJalview(
   * chooser.getSelectedFile().getAbsolutePath(), this); } }
   */
  /**
   * prototype of an automatically enabled/disabled analysis function
   * 
   */
  protected void setShowProductsEnabled()
  {
    SequenceI[] selection = viewport.getSequenceSelection();
    if (canShowProducts(selection, viewport.getSelectionGroup() != null,
            viewport.getAlignment().getDataset()))
    {
      showProducts.setEnabled(true);

    }
    else
    {
      showProducts.setEnabled(false);
    }
  }

  /**
   * search selection for sequence xRef products and build the show products
   * menu.
   * 
   * @param selection
   * @param dataset
   * @return true if showProducts menu should be enabled.
   */
  public boolean canShowProducts(SequenceI[] selection,
          boolean isRegionSelection, Alignment dataset)
  {
    boolean showp = false;
    try
    {
      showProducts.removeAll();
      final boolean dna = viewport.getAlignment().isNucleotide();
      final Alignment ds = dataset;
      String[] ptypes = (selection == null || selection.length == 0) ? null
              : CrossRef.findSequenceXrefTypes(dna, selection, dataset);
      // Object[] prods =
      // CrossRef.buildXProductsList(viewport.getAlignment().isNucleotide(),
      // selection, dataset, true);
      final SequenceI[] sel = selection;
      for (int t = 0; ptypes != null && t < ptypes.length; t++)
      {
        showp = true;
        final boolean isRegSel = isRegionSelection;
        final AlignFrame af = this;
        final String source = ptypes[t];
        JMenuItem xtype = new JMenuItem(ptypes[t]);
        xtype.addActionListener(new ActionListener()
        {

          @Override
          public void actionPerformed(ActionEvent e)
          {
            // TODO: new thread for this call with vis-delay
            af.showProductsFor(af.viewport.getSequenceSelection(), ds,
                    isRegSel, dna, source);
          }

        });
        showProducts.add(xtype);
      }
      showProducts.setVisible(showp);
      showProducts.setEnabled(showp);
    } catch (Exception e)
    {
      jalview.bin.Cache.log
              .warn("canTranslate threw an exception - please report to help@jalview.org",
                      e);
      return false;
    }
    return showp;
  }

  protected void showProductsFor(SequenceI[] sel, Alignment ds,
          boolean isRegSel, boolean dna, String source)
  {
    final boolean fisRegSel = isRegSel;
    final boolean fdna = dna;
    final String fsrc = source;
    final AlignFrame ths = this;
    final SequenceI[] fsel = sel;
    Runnable foo = new Runnable()
    {

      @Override
      public void run()
      {
        final long sttime = System.currentTimeMillis();
        ths.setProgressBar("Searching for sequences from " + fsrc, sttime);
        try
        {
          Alignment ds = ths.getViewport().getAlignment().getDataset(); // update
          // our local
          // dataset
          // reference
          Alignment prods = CrossRef
                  .findXrefSequences(fsel, fdna, fsrc, ds);
          if (prods != null)
          {
            SequenceI[] sprods = new SequenceI[prods.getHeight()];
            for (int s = 0; s < sprods.length; s++)
            {
              sprods[s] = (prods.getSequenceAt(s)).deriveSequence();
              if (ds.getSequences() == null
                      || !ds.getSequences().contains(
                              sprods[s].getDatasetSequence()))
                ds.addSequence(sprods[s].getDatasetSequence());
              sprods[s].updatePDBIds();
            }
            Alignment al = new Alignment(sprods);
            AlignedCodonFrame[] cf = prods.getCodonFrames();
            al.setDataset(ds);
            for (int s = 0; cf != null && s < cf.length; s++)
            {
              al.addCodonFrame(cf[s]);
              cf[s] = null;
            }
            AlignFrame naf = new AlignFrame(al, DEFAULT_WIDTH,
                    DEFAULT_HEIGHT);
            String newtitle = "" + ((fdna) ? "Proteins " : "Nucleotides ")
                    + " for " + ((fisRegSel) ? "selected region of " : "")
                    + getTitle();
            Desktop.addInternalFrame(naf, newtitle, DEFAULT_WIDTH,
                    DEFAULT_HEIGHT);
          }
          else
          {
            System.err.println("No Sequences generated for xRef type "
                    + fsrc);
          }
        } catch (Exception e)
        {
          jalview.bin.Cache.log.error(
                  "Exception when finding crossreferences", e);
        } catch (OutOfMemoryError e)
        {
          new OOMWarning("whilst fetching crossreferences", e);
        } catch (Error e)
        {
          jalview.bin.Cache.log.error("Error when finding crossreferences",
                  e);
        }
        ths.setProgressBar("Finished searching for sequences from " + fsrc,
                sttime);
      }

    };
    Thread frunner = new Thread(foo);
    frunner.start();
  }

  public boolean canShowTranslationProducts(SequenceI[] selection,
          AlignmentI alignment)
  {
    // old way
    try
    {
      return (jalview.analysis.Dna.canTranslate(selection,
              viewport.getViewAsVisibleContigs(true)));
    } catch (Exception e)
    {
      jalview.bin.Cache.log
              .warn("canTranslate threw an exception - please report to help@jalview.org",
                      e);
      return false;
    }
  }

  @Override
  public void showProducts_actionPerformed(ActionEvent e)
  {
    // /////////////////////////////
    // Collect Data to be translated/transferred

    SequenceI[] selection = viewport.getSequenceSelection();
    AlignmentI al = null;
    try
    {
      al = jalview.analysis.Dna.CdnaTranslate(selection, viewport
              .getViewAsVisibleContigs(true), viewport.getGapCharacter(),
              viewport.getAlignment().getDataset());
    } catch (Exception ex)
    {
      al = null;
      jalview.bin.Cache.log.debug("Exception during translation.", ex);
    }
    if (al == null)
    {
      JOptionPane
              .showMessageDialog(
                      Desktop.desktop,
                      "Please select at least three bases in at least one sequence in order to perform a cDNA translation.",
                      "Translation Failed", JOptionPane.WARNING_MESSAGE);
    }
    else
    {
      AlignFrame af = new AlignFrame(al, DEFAULT_WIDTH, DEFAULT_HEIGHT);
      Desktop.addInternalFrame(af, "Translation of " + this.getTitle(),
              DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
  }

  @Override
  public void showTranslation_actionPerformed(ActionEvent e)
  {
    // /////////////////////////////
    // Collect Data to be translated/transferred

    SequenceI[] selection = viewport.getSequenceSelection();
    String[] seqstring = viewport.getViewAsString(true);
    AlignmentI al = null;
    try
    {
      al = jalview.analysis.Dna.CdnaTranslate(selection, seqstring,
              viewport.getViewAsVisibleContigs(true), viewport
                      .getGapCharacter(), viewport.getAlignment()
                      .getAlignmentAnnotation(), viewport.getAlignment()
                      .getWidth(), viewport.getAlignment().getDataset());
    } catch (Exception ex)
    {
      al = null;
      jalview.bin.Cache.log.debug("Exception during translation.", ex);
    }
    if (al == null)
    {
      JOptionPane
              .showMessageDialog(
                      Desktop.desktop,
                      "Please select at least three bases in at least one sequence in order to perform a cDNA translation.",
                      "Translation Failed", JOptionPane.WARNING_MESSAGE);
    }
    else
    {
      AlignFrame af = new AlignFrame(al, DEFAULT_WIDTH, DEFAULT_HEIGHT);
      Desktop.addInternalFrame(af, "Translation of " + this.getTitle(),
              DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
  }

  /**
   * Try to load a features file onto the alignment.
   * 
   * @param file
   *          contents or path to retrieve file
   * @param type
   *          access mode of file (see jalview.io.AlignFile)
   * @return true if features file was parsed corectly.
   */
  public boolean parseFeaturesFile(String file, String type)
  {
    boolean featuresFile = false;
    try
    {
      featuresFile = new FeaturesFile(file, type).parse(viewport
              .getAlignment().getDataset(), alignPanel.seqPanel.seqCanvas
              .getFeatureRenderer().featureColours, false,
              jalview.bin.Cache.getDefault("RELAXEDSEQIDMATCHING", false));
    } catch (Exception ex)
    {
      ex.printStackTrace();
    }

    if (featuresFile)
    {
      viewport.showSequenceFeatures = true;
      showSeqFeatures.setSelected(true);
      if (alignPanel.seqPanel.seqCanvas.fr != null)
      {
        // update the min/max ranges where necessary
        alignPanel.seqPanel.seqCanvas.fr.findAllFeatures(true);
      }
      if (featureSettings != null)
      {
        featureSettings.setTableData();
      }
      alignPanel.paintAlignment(true);
    }

    return featuresFile;
  }

  @Override
  public void dragEnter(DropTargetDragEvent evt)
  {
  }

  @Override
  public void dragExit(DropTargetEvent evt)
  {
  }

  @Override
  public void dragOver(DropTargetDragEvent evt)
  {
  }

  @Override
  public void dropActionChanged(DropTargetDragEvent evt)
  {
  }

  @Override
  public void drop(DropTargetDropEvent evt)
  {
    Transferable t = evt.getTransferable();
    java.util.List files = null;

    try
    {
      DataFlavor uriListFlavor = new DataFlavor(
              "text/uri-list;class=java.lang.String");
      if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
      {
        // Works on Windows and MacOSX
        evt.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        files = (java.util.List) t
                .getTransferData(DataFlavor.javaFileListFlavor);
      }
      else if (t.isDataFlavorSupported(uriListFlavor))
      {
        // This is used by Unix drag system
        evt.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        String data = (String) t.getTransferData(uriListFlavor);
        files = new java.util.ArrayList(1);
        for (java.util.StringTokenizer st = new java.util.StringTokenizer(
                data, "\r\n"); st.hasMoreTokens();)
        {
          String s = st.nextToken();
          if (s.startsWith("#"))
          {
            // the line is a comment (as per the RFC 2483)
            continue;
          }

          java.net.URI uri = new java.net.URI(s);
          // check to see if we can handle this kind of URI
          if (uri.getScheme().toLowerCase().startsWith("http"))
          {
            files.add(uri.toString());
          }
          else
          {
            // otherwise preserve old behaviour: catch all for file objects
            java.io.File file = new java.io.File(uri);
            files.add(file.toString());
          }
        }
      }
    } catch (Exception e)
    {
      e.printStackTrace();
    }
    if (files != null)
    {
      try
      {
        // check to see if any of these files have names matching sequences in
        // the alignment
        SequenceIdMatcher idm = new SequenceIdMatcher(viewport
                .getAlignment().getSequencesArray());
        /**
         * Object[] { String,SequenceI}
         */
        ArrayList<Object[]> filesmatched = new ArrayList<Object[]>();
        ArrayList<String> filesnotmatched = new ArrayList<String>();
        for (int i = 0; i < files.size(); i++)
        {
          String file = files.get(i).toString();
          String pdbfn = "";
          String protocol = FormatAdapter.checkProtocol(file);
          if (protocol == jalview.io.FormatAdapter.FILE)
          {
            File fl = new File(file);
            pdbfn = fl.getName();
          }
          else if (protocol == jalview.io.FormatAdapter.URL)
          {
            URL url = new URL(file);
            pdbfn = url.getFile();
          }
          if (pdbfn.length() > 0)
          {
            // attempt to find a match in the alignment
            SequenceI[] mtch = idm.findAllIdMatches(pdbfn);
            int l = 0, c = pdbfn.indexOf(".");
            while (mtch == null && c != -1)
            {
              do
              {
                l = c;
              } while ((c = pdbfn.indexOf(".", l)) > l);
              if (l > -1)
              {
                pdbfn = pdbfn.substring(0, l);
              }
              mtch = idm.findAllIdMatches(pdbfn);
            }
            if (mtch != null)
            {
              String type = null;
              try
              {
                type = new IdentifyFile().Identify(file, protocol);
              } catch (Exception ex)
              {
                type = null;
              }
              if (type != null)
              {
                if (type.equalsIgnoreCase("PDB"))
                {
                  filesmatched.add(new Object[]
                  { file, protocol, mtch });
                  continue;
                }
              }
            }
            // File wasn't named like one of the sequences or wasn't a PDB file.
            filesnotmatched.add(file);
          }
        }
        int assocfiles = 0;
        if (filesmatched.size() > 0)
        {
          if (Cache.getDefault("AUTOASSOCIATE_PDBANDSEQS", false)
                  || JOptionPane
                          .showConfirmDialog(
                                  this,
                                  "Do you want to automatically associate the "
                                          + filesmatched.size()
                                          + " PDB files with sequences in the alignment that have the same name ?",
                                  "Automatically Associate PDB files by name",
                                  JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)

          {
            for (Object[] fm : filesmatched)
            {
              // try and associate
              // TODO: may want to set a standard ID naming formalism for
              // associating PDB files which have no IDs.
              for (SequenceI toassoc : (SequenceI[]) fm[2])
              {
                PDBEntry pe = new AssociatePdbFileWithSeq()
                        .associatePdbWithSeq((String) fm[0],
                                (String) fm[1], toassoc, false);
                if (pe != null)
                {
                  System.err.println("Associated file : "
                          + ((String) fm[0]) + " with "
                          + toassoc.getDisplayId(true));
                  assocfiles++;
                }
              }
              alignPanel.paintAlignment(true);
            }
          }
        }
        if (filesnotmatched.size() > 0)
        {
          if (assocfiles > 0
                  && (Cache.getDefault(
                          "AUTOASSOCIATE_PDBANDSEQS_IGNOREOTHERS", false) || JOptionPane
                          .showConfirmDialog(
                                  this,
                                  "<html>Do you want to <em>ignore</em> the "
                                          + filesnotmatched.size()
                                          + " files whose names did not match any sequence IDs ?</html>",
                                  "Ignore unmatched dropped files ?",
                                  JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION))
          {
            return;
          }
          for (String fn : filesnotmatched)
          {
            loadJalviewDataFile(fn, null, null, null);
          }

        }
      } catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Attempt to load a "dropped" file or URL string: First by testing whether
   * it's and Annotation file, then a JNet file, and finally a features file. If
   * all are false then the user may have dropped an alignment file onto this
   * AlignFrame.
   * 
   * @param file
   *          either a filename or a URL string.
   */
  public void loadJalviewDataFile(String file, String protocol,
          String format, SequenceI assocSeq)
  {
    try
    {
      if (protocol == null)
      {
        protocol = jalview.io.FormatAdapter.checkProtocol(file);
      }
      // if the file isn't identified, or not positively identified as some
      // other filetype (PFAM is default unidentified alignment file type) then
      // try to parse as annotation.
      boolean isAnnotation = (format == null || format
              .equalsIgnoreCase("PFAM")) ? new AnnotationFile()
              .readAnnotationFile(viewport.getAlignment(), file, protocol)
              : false;

      if (!isAnnotation)
      {
        // first see if its a T-COFFEE score file
        TCoffeeScoreFile tcf = null;
        try
        {
          tcf = new TCoffeeScoreFile(file, protocol);
          if (tcf.isValid())
          {
            if (tcf.annotateAlignment(viewport.getAlignment(), true))
            {
              tcoffeeColour.setEnabled(true);
              tcoffeeColour.setSelected(true);
              changeColour(new TCoffeeColourScheme(viewport.getAlignment()));
              isAnnotation = true;
              statusBar
                      .setText("Successfully pasted T-Coffee scores to alignment.");
            }
            else
            {
              // some problem - if no warning its probable that the ID matching
              // process didn't work
              JOptionPane
                      .showMessageDialog(
                              Desktop.desktop,
                              tcf.getWarningMessage() == null ? "Check that the file matches sequence IDs in the alignment."
                                      : tcf.getWarningMessage(),
                              "Problem reading T-COFFEE score file",
                              JOptionPane.WARNING_MESSAGE);
            }
          }
          else
          {
            tcf = null;
          }
        } catch (Exception x)
        {
          Cache.log
                  .debug("Exception when processing data source as T-COFFEE score file",
                          x);
          tcf = null;
        }
        if (tcf == null)
        {
          // try to see if its a JNet 'concise' style annotation file *before*
          // we
          // try to parse it as a features file
          if (format == null)
          {
            format = new IdentifyFile().Identify(file, protocol);
          }
          if (format.equalsIgnoreCase("JnetFile"))
          {
            jalview.io.JPredFile predictions = new jalview.io.JPredFile(
                    file, protocol);
            new JnetAnnotationMaker().add_annotation(predictions,
                    viewport.getAlignment(), 0, false);
            isAnnotation = true;
          }
          else
          {
            /*
             * if (format.equalsIgnoreCase("PDB")) {
             * 
             * String pdbfn = ""; // try to match up filename with sequence id
             * try { if (protocol == jalview.io.FormatAdapter.FILE) { File fl =
             * new File(file); pdbfn = fl.getName(); } else if (protocol ==
             * jalview.io.FormatAdapter.URL) { URL url = new URL(file); pdbfn =
             * url.getFile(); } } catch (Exception e) { } ; if (assocSeq ==
             * null) { SequenceIdMatcher idm = new SequenceIdMatcher(viewport
             * .getAlignment().getSequencesArray()); if (pdbfn.length() > 0) {
             * // attempt to find a match in the alignment SequenceI mtch =
             * idm.findIdMatch(pdbfn); int l = 0, c = pdbfn.indexOf("."); while
             * (mtch == null && c != -1) { while ((c = pdbfn.indexOf(".", l)) >
             * l) { l = c; } if (l > -1) { pdbfn = pdbfn.substring(0, l); } mtch
             * = idm.findIdMatch(pdbfn); } if (mtch != null) { // try and
             * associate // prompt ? PDBEntry pe = new AssociatePdbFileWithSeq()
             * .associatePdbWithSeq(file, protocol, mtch, true); if (pe != null)
             * { System.err.println("Associated file : " + file + " with " +
             * mtch.getDisplayId(true)); alignPanel.paintAlignment(true); } } //
             * TODO: maybe need to load as normal otherwise return; } }
             */
            // try to parse it as a features file
            boolean isGroupsFile = parseFeaturesFile(file, protocol);
            // if it wasn't a features file then we just treat it as a general
            // alignment file to load into the current view.
            if (!isGroupsFile)
            {
              new FileLoader().LoadFile(viewport, file, protocol, format);
            }
            else
            {
              alignPanel.paintAlignment(true);
            }
          }
        }
      }
      if (isAnnotation)
      {

        alignPanel.adjustAnnotationHeight();
        viewport.updateSequenceIdColours();
        buildSortByAnnotationScoresMenu();
        alignPanel.paintAlignment(true);
      }
    } catch (Exception ex)
    {
      ex.printStackTrace();
    } catch (OutOfMemoryError oom)
    {
      try
      {
        System.gc();
      } catch (Exception x)
      {
      }
      ;
      new OOMWarning(
              "loading data "
                      + (protocol != null ? (protocol.equals(FormatAdapter.PASTE) ? "from clipboard."
                              : "using " + protocol + " from " + file)
                              : ".")
                      + (format != null ? "(parsing as '" + format
                              + "' file)" : ""), oom, Desktop.desktop);
    }
  }

  @Override
  public void tabSelectionChanged(int index)
  {
    if (index > -1)
    {
      alignPanel = (AlignmentPanel) alignPanels.elementAt(index);
      viewport = alignPanel.av;
      setMenusFromViewport(viewport);
    }
  }

  @Override
  public void tabbedPane_mousePressed(MouseEvent e)
  {
    if (SwingUtilities.isRightMouseButton(e))
    {
      String reply = JOptionPane.showInternalInputDialog(this,
              "Enter View Name", "Edit View Name",
              JOptionPane.QUESTION_MESSAGE);

      if (reply != null)
      {
        viewport.viewName = reply;
        tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), reply);
      }
    }
  }

  public AlignViewport getCurrentView()
  {
    return viewport;
  }

  /**
   * Open the dialog for regex description parsing.
   */
  @Override
  protected void extractScores_actionPerformed(ActionEvent e)
  {
    ParseProperties pp = new jalview.analysis.ParseProperties(
            viewport.getAlignment());
    // TODO: verify regex and introduce GUI dialog for version 2.5
    // if (pp.getScoresFromDescription("col", "score column ",
    // "\\W*([-+]?\\d*\\.?\\d*e?-?\\d*)\\W+([-+]?\\d*\\.?\\d*e?-?\\d*)",
    // true)>0)
    if (pp.getScoresFromDescription("description column",
            "score in description column ", "\\W*([-+eE0-9.]+)", true) > 0)
    {
      buildSortByAnnotationScoresMenu();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GAlignFrame#showDbRefs_actionPerformed(java.awt.event.ActionEvent
   * )
   */
  @Override
  protected void showDbRefs_actionPerformed(ActionEvent e)
  {
    viewport.setShowDbRefs(showDbRefsMenuitem.isSelected());
  }

  /*
   * (non-Javadoc)
   * 
   * @seejalview.jbgui.GAlignFrame#showNpFeats_actionPerformed(java.awt.event.
   * ActionEvent)
   */
  @Override
  protected void showNpFeats_actionPerformed(ActionEvent e)
  {
    viewport.setShowNpFeats(showNpFeatsMenuitem.isSelected());
  }

  /**
   * find the viewport amongst the tabs in this alignment frame and close that
   * tab
   * 
   * @param av
   */
  public boolean closeView(AlignViewport av)
  {
    if (viewport == av)
    {
      this.closeMenuItem_actionPerformed(false);
      return true;
    }
    Component[] comp = tabbedPane.getComponents();
    for (int i = 0; comp != null && i < comp.length; i++)
    {
      if (comp[i] instanceof AlignmentPanel)
      {
        if (((AlignmentPanel) comp[i]).av == av)
        {
          // close the view.
          closeView((AlignmentPanel) comp[i]);
          return true;
        }
      }
    }
    return false;
  }

  protected void build_fetchdbmenu(JMenu webService)
  {
    // Temporary hack - DBRef Fetcher always top level ws entry.
    // TODO We probably want to store a sequence database checklist in
    // preferences and have checkboxes.. rather than individual sources selected
    // here
    final JMenu rfetch = new JMenu("Fetch DB References");
    rfetch.setToolTipText("Retrieve and parse sequence database records for the alignment or the currently selected sequences");
    webService.add(rfetch);

    JMenuItem fetchr = new JMenuItem("Standard Databases");
    fetchr.setToolTipText("Fetch from EMBL/EMBLCDS or Uniprot/PDB and any selected DAS sources");
    fetchr.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        new Thread(new Runnable()
        {

          @Override
          public void run()
          {
            new jalview.ws.DBRefFetcher(alignPanel.av
                    .getSequenceSelection(), alignPanel.alignFrame)
                    .fetchDBRefs(false);
          }
        }).start();

      }

    });
    rfetch.add(fetchr);
    final AlignFrame me = this;
    new Thread(new Runnable()
    {
      @Override
      public void run()
      {
        final jalview.ws.SequenceFetcher sf = SequenceFetcher
                .getSequenceFetcherSingleton(me);
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
          @Override
          public void run()
          {
            String[] dbclasses = sf.getOrderedSupportedSources();
            // sf.getDbInstances(jalview.ws.dbsources.DasSequenceSource.class);
            // jalview.util.QuickSort.sort(otherdb, otherdb);
            List<DbSourceProxy> otherdb;
            JMenu dfetch = new JMenu();
            JMenu ifetch = new JMenu();
            JMenuItem fetchr = null;
            int comp = 0, icomp = 0, mcomp = 15;
            String mname = null;
            int dbi = 0;
            for (String dbclass : dbclasses)
            {
              otherdb = sf.getSourceProxy(dbclass);
              // add a single entry for this class, or submenu allowing 'fetch
              // all' or pick one
              if (otherdb == null || otherdb.size() < 1)
              {
                continue;
              }
              // List<DbSourceProxy> dbs=otherdb;
              // otherdb=new ArrayList<DbSourceProxy>();
              // for (DbSourceProxy db:dbs)
              // {
              // if (!db.isA(DBRefSource.ALIGNMENTDB)
              // }
              if (mname == null)
              {
                mname = "From " + dbclass;
              }
              if (otherdb.size() == 1)
              {
                final DbSourceProxy[] dassource = otherdb
                        .toArray(new DbSourceProxy[0]);
                DbSourceProxy src = otherdb.get(0);
                fetchr = new JMenuItem(src.getDbSource());
                fetchr.addActionListener(new ActionListener()
                {

                  @Override
                  public void actionPerformed(ActionEvent e)
                  {
                    new Thread(new Runnable()
                    {

                      @Override
                      public void run()
                      {
                        new jalview.ws.DBRefFetcher(alignPanel.av
                                .getSequenceSelection(),
                                alignPanel.alignFrame, dassource)
                                .fetchDBRefs(false);
                      }
                    }).start();
                  }

                });
                fetchr.setToolTipText("<html>"
                        + JvSwingUtils.wrapTooltip("Retrieve from "
                                + src.getDbName()) + "<html>");
                dfetch.add(fetchr);
                comp++;
              }
              else
              {
                final DbSourceProxy[] dassource = otherdb
                        .toArray(new DbSourceProxy[0]);
                // fetch all entry
                DbSourceProxy src = otherdb.get(0);
                fetchr = new JMenuItem("Fetch All '" + src.getDbSource()
                        + "'");
                fetchr.addActionListener(new ActionListener()
                {
                  @Override
                  public void actionPerformed(ActionEvent e)
                  {
                    new Thread(new Runnable()
                    {

                      @Override
                      public void run()
                      {
                        new jalview.ws.DBRefFetcher(alignPanel.av
                                .getSequenceSelection(),
                                alignPanel.alignFrame, dassource)
                                .fetchDBRefs(false);
                      }
                    }).start();
                  }
                });

                fetchr.setToolTipText("<html>"
                        + JvSwingUtils.wrapTooltip("Retrieve from all "
                                + otherdb.size() + " sources in "
                                + src.getDbSource() + "<br>First is :"
                                + src.getDbName()) + "<html>");
                dfetch.add(fetchr);
                comp++;
                // and then build the rest of the individual menus
                ifetch = new JMenu("Sources from " + src.getDbSource());
                icomp = 0;
                String imname = null;
                int i = 0;
                for (DbSourceProxy sproxy : otherdb)
                {
                  String dbname = sproxy.getDbName();
                  String sname = dbname.length() > 5 ? dbname.substring(0,
                          5) + "..." : dbname;
                  String msname = dbname.length() > 10 ? dbname.substring(
                          0, 10) + "..." : dbname;
                  if (imname == null)
                  {
                    imname = "from '" + sname + "'";
                  }
                  fetchr = new JMenuItem(msname);
                  final DbSourceProxy[] dassrc =
                  { sproxy };
                  fetchr.addActionListener(new ActionListener()
                  {

                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                      new Thread(new Runnable()
                      {

                        @Override
                        public void run()
                        {
                          new jalview.ws.DBRefFetcher(alignPanel.av
                                  .getSequenceSelection(),
                                  alignPanel.alignFrame, dassrc)
                                  .fetchDBRefs(false);
                        }
                      }).start();
                    }

                  });
                  fetchr.setToolTipText("<html>"
                          + JvSwingUtils.wrapTooltip("Retrieve from "
                                  + dbname) + "</html>");
                  ifetch.add(fetchr);
                  ++i;
                  if (++icomp >= mcomp || i == (otherdb.size()))
                  {
                    ifetch.setText(imname + " to '" + sname + "'");
                    dfetch.add(ifetch);
                    ifetch = new JMenu();
                    imname = null;
                    icomp = 0;
                    comp++;
                  }
                }
              }
              ++dbi;
              if (comp >= mcomp || dbi >= (dbclasses.length))
              {
                dfetch.setText(mname + " to '" + dbclass + "'");
                rfetch.add(dfetch);
                dfetch = new JMenu();
                mname = null;
                comp = 0;
              }
            }
          }
        });
      }
    }).start();

  }

  /**
   * Left justify the whole alignment.
   */
  @Override
  protected void justifyLeftMenuItem_actionPerformed(ActionEvent e)
  {
    AlignmentI al = viewport.getAlignment();
    al.justify(false);
    viewport.firePropertyChange("alignment", null, al);
  }

  /**
   * Right justify the whole alignment.
   */
  @Override
  protected void justifyRightMenuItem_actionPerformed(ActionEvent e)
  {
    AlignmentI al = viewport.getAlignment();
    al.justify(true);
    viewport.firePropertyChange("alignment", null, al);
  }

  public void setShowSeqFeatures(boolean b)
  {
    showSeqFeatures.setSelected(true);
    viewport.setShowSequenceFeatures(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GAlignFrame#showUnconservedMenuItem_actionPerformed(java.
   * awt.event.ActionEvent)
   */
  @Override
  protected void showUnconservedMenuItem_actionPerformed(ActionEvent e)
  {
    viewport.setShowUnconserved(showNonconservedMenuItem.getState());
    alignPanel.paintAlignment(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GAlignFrame#showGroupConsensus_actionPerformed(java.awt.event
   * .ActionEvent)
   */
  @Override
  protected void showGroupConsensus_actionPerformed(ActionEvent e)
  {
    viewport.setShowGroupConsensus(showGroupConsensus.getState());
    alignPanel.updateAnnotation(applyAutoAnnotationSettings.getState());

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GAlignFrame#showGroupConservation_actionPerformed(java.awt
   * .event.ActionEvent)
   */
  @Override
  protected void showGroupConservation_actionPerformed(ActionEvent e)
  {
    viewport.setShowGroupConservation(showGroupConservation.getState());
    alignPanel.updateAnnotation(applyAutoAnnotationSettings.getState());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GAlignFrame#showConsensusHistogram_actionPerformed(java.awt
   * .event.ActionEvent)
   */
  @Override
  protected void showConsensusHistogram_actionPerformed(ActionEvent e)
  {
    viewport.setShowConsensusHistogram(showConsensusHistogram.getState());
    alignPanel.updateAnnotation(applyAutoAnnotationSettings.getState());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GAlignFrame#showConsensusProfile_actionPerformed(java.awt
   * .event.ActionEvent)
   */
  @Override
  protected void showSequenceLogo_actionPerformed(ActionEvent e)
  {
    viewport.setShowSequenceLogo(showSequenceLogo.getState());
    alignPanel.updateAnnotation(applyAutoAnnotationSettings.getState());
  }

  @Override
  protected void normaliseSequenceLogo_actionPerformed(ActionEvent e)
  {
    showSequenceLogo.setState(true);
    viewport.setShowSequenceLogo(true);
    viewport.setNormaliseSequenceLogo(normaliseSequenceLogo.getState());
    alignPanel.updateAnnotation(applyAutoAnnotationSettings.getState());
  }

  @Override
  protected void applyAutoAnnotationSettings_actionPerformed(ActionEvent e)
  {
    alignPanel.updateAnnotation(applyAutoAnnotationSettings.getState());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GAlignFrame#makeGrpsFromSelection_actionPerformed(java.awt
   * .event.ActionEvent)
   */
  @Override
  protected void makeGrpsFromSelection_actionPerformed(ActionEvent e)
  {
    if (viewport.getSelectionGroup() != null)
    {
      SequenceGroup[] gps = jalview.analysis.Grouping.makeGroupsFrom(
              viewport.getSequenceSelection(),
              viewport.getAlignmentView(true).getSequenceStrings(
                      viewport.getGapCharacter()), viewport.getAlignment()
                      .getGroups());
      viewport.getAlignment().deleteAllGroups();
      viewport.sequenceColours = null;
      viewport.setSelectionGroup(null);
      // set view properties for each group
      for (int g = 0; g < gps.length; g++)
      {
        gps[g].setShowNonconserved(viewport.getShowUnconserved());
        gps[g].setshowSequenceLogo(viewport.isShowSequenceLogo());
        viewport.getAlignment().addGroup(gps[g]);
        Color col = new Color((int) (Math.random() * 255),
                (int) (Math.random() * 255), (int) (Math.random() * 255));
        col = col.brighter();
        for (SequenceI s : gps[g].getSequences())
          viewport.setSequenceColour(s, col);
      }
      PaintRefresher.Refresh(this, viewport.getSequenceSetId());
      alignPanel.updateAnnotation();
      alignPanel.paintAlignment(true);
    }
  }

  /**
   * make the given alignmentPanel the currently selected tab
   * 
   * @param alignmentPanel
   */
  public void setDisplayedView(AlignmentPanel alignmentPanel)
  {
    if (!viewport.getSequenceSetId().equals(
            alignmentPanel.av.getSequenceSetId()))
    {
      throw new Error(
              "Implementation error: cannot show a view from another alignment in an AlignFrame.");
    }
    if (tabbedPane != null
            & alignPanels.indexOf(alignmentPanel) != tabbedPane
                    .getSelectedIndex())
    {
      tabbedPane.setSelectedIndex(alignPanels.indexOf(alignmentPanel));
    }
  }
}

class PrintThread extends Thread
{
  AlignmentPanel ap;

  public PrintThread(AlignmentPanel ap)
  {
    this.ap = ap;
  }

  static PageFormat pf;

  @Override
  public void run()
  {
    PrinterJob printJob = PrinterJob.getPrinterJob();

    if (pf != null)
    {
      printJob.setPrintable(ap, pf);
    }
    else
    {
      printJob.setPrintable(ap);
    }

    if (printJob.printDialog())
    {
      try
      {
        printJob.print();
      } catch (Exception PrintException)
      {
        PrintException.printStackTrace();
      }
    }
  }
}
