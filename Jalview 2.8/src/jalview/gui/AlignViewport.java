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
/*
 * Jalview - A Sequence Alignment Editor and Viewer
 * Copyright (C) 2007 AM Waterhouse, J Procter, G Barton, M Clamp, S Searle
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */
package jalview.gui;

import jalview.analysis.NJTree;
import jalview.api.AlignViewportI;
import jalview.bin.Cache;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.Annotation;
import jalview.datamodel.ColumnSelection;
import jalview.datamodel.PDBEntry;
import jalview.datamodel.Sequence;
import jalview.datamodel.SequenceGroup;
import jalview.datamodel.SequenceI;
import jalview.schemes.ColourSchemeProperty;
import jalview.schemes.UserColourScheme;
import jalview.structure.SelectionSource;
import jalview.structure.StructureSelectionManager;
import jalview.structure.VamsasSource;
import jalview.viewmodel.AlignmentViewport;
import jalview.ws.params.AutoCalcSetting;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision: 1.141 $
 */
public class AlignViewport extends AlignmentViewport implements
        SelectionSource, VamsasSource, AlignViewportI
{
  int startRes;

  int endRes;

  int startSeq;

  int endSeq;

  boolean showJVSuffix = true;

  boolean showText = true;

  boolean showColourText = false;

  boolean showBoxes = true;

  boolean wrapAlignment = false;

  boolean renderGaps = true;

  boolean showSequenceFeatures = false;

  boolean showAnnotation = true;

  int charHeight;

  int charWidth;

  boolean validCharWidth;

  int wrappedWidth;

  Font font;

  boolean seqNameItalics;

  NJTree currentTree = null;

  boolean scaleAboveWrapped = false;

  boolean scaleLeftWrapped = true;

  boolean scaleRightWrapped = true;

  boolean showHiddenMarkers = true;

  boolean cursorMode = false;

  /**
   * Keys are the feature types which are currently visible. Note: Values are
   * not used!
   */
  Hashtable featuresDisplayed = null;

  boolean antiAlias = false;

  Rectangle explodedPosition;

  String viewName;

  boolean gatherViewsHere = false;

  Stack historyList = new Stack();

  Stack redoList = new Stack();

  Hashtable sequenceColours;

  int thresholdTextColour = 0;

  Color textColour = Color.black;

  Color textColour2 = Color.white;

  boolean rightAlignIds = false;

  /**
   * Creates a new AlignViewport object.
   * 
   * @param al
   *          alignment to view
   */
  public AlignViewport(AlignmentI al)
  {
    setAlignment(al);
    init();
  }

  /**
   * Create a new AlignViewport object with a specific sequence set ID
   * 
   * @param al
   * @param seqsetid
   *          (may be null - but potential for ambiguous constructor exception)
   */
  public AlignViewport(AlignmentI al, String seqsetid)
  {
    this(al, seqsetid, null);
  }

  public AlignViewport(AlignmentI al, String seqsetid, String viewid)
  {
    sequenceSetID = seqsetid;
    viewId = viewid;
    // TODO remove these once 2.4.VAMSAS release finished
    if (Cache.log != null && Cache.log.isDebugEnabled() && seqsetid != null)
    {
      Cache.log.debug("Setting viewport's sequence set id : "
              + sequenceSetID);
    }
    if (Cache.log != null && Cache.log.isDebugEnabled() && viewId != null)
    {
      Cache.log.debug("Setting viewport's view id : " + viewId);
    }
    setAlignment(al);
    init();
  }

  /**
   * Create a new AlignViewport with hidden regions
   * 
   * @param al
   *          AlignmentI
   * @param hiddenColumns
   *          ColumnSelection
   */
  public AlignViewport(AlignmentI al, ColumnSelection hiddenColumns)
  {
    setAlignment(al);
    if (hiddenColumns != null)
    {
      this.colSel = hiddenColumns;
      if (hiddenColumns.getHiddenColumns() != null
              && hiddenColumns.getHiddenColumns().size() > 0)
      {
        hasHiddenColumns = true;
      }
      else
      {
        hasHiddenColumns = false;
      }
    }
    init();
  }

  /**
   * New viewport with hidden columns and an existing sequence set id
   * 
   * @param al
   * @param hiddenColumns
   * @param seqsetid
   *          (may be null)
   */
  public AlignViewport(AlignmentI al, ColumnSelection hiddenColumns,
          String seqsetid)
  {
    this(al, hiddenColumns, seqsetid, null);
  }

  /**
   * New viewport with hidden columns and an existing sequence set id and viewid
   * 
   * @param al
   * @param hiddenColumns
   * @param seqsetid
   *          (may be null)
   * @param viewid
   *          (may be null)
   */
  public AlignViewport(AlignmentI al, ColumnSelection hiddenColumns,
          String seqsetid, String viewid)
  {
    sequenceSetID = seqsetid;
    viewId = viewid;
    // TODO remove these once 2.4.VAMSAS release finished
    if (Cache.log != null && Cache.log.isDebugEnabled() && seqsetid != null)
    {
      Cache.log.debug("Setting viewport's sequence set id : "
              + sequenceSetID);
    }
    if (Cache.log != null && Cache.log.isDebugEnabled() && viewId != null)
    {
      Cache.log.debug("Setting viewport's view id : " + viewId);
    }
    setAlignment(al);
    if (hiddenColumns != null)
    {
      this.colSel = hiddenColumns;
      if (hiddenColumns.getHiddenColumns() != null
              && hiddenColumns.getHiddenColumns().size() > 0)
      {
        hasHiddenColumns = true;
      }
      else
      {
        hasHiddenColumns = false;
      }
    }
    init();
  }

  void init()
  {
    this.startRes = 0;
    this.endRes = alignment.getWidth() - 1;
    this.startSeq = 0;
    this.endSeq = alignment.getHeight() - 1;

    antiAlias = Cache.getDefault("ANTI_ALIAS", false);

    showJVSuffix = Cache.getDefault("SHOW_JVSUFFIX", true);
    showAnnotation = Cache.getDefault("SHOW_ANNOTATIONS", true);

    rightAlignIds = Cache.getDefault("RIGHT_ALIGN_IDS", false);
    centreColumnLabels = Cache.getDefault("CENTRE_COLUMN_LABELS", false);
    autoCalculateConsensus = Cache.getDefault("AUTO_CALC_CONSENSUS", true);

    setPadGaps(Cache.getDefault("PAD_GAPS", true));
    shownpfeats = Cache.getDefault("SHOW_NPFEATS_TOOLTIP", true);
    showdbrefs = Cache.getDefault("SHOW_DBREFS_TOOLTIP", true);

    String fontName = Cache.getDefault("FONT_NAME", "SansSerif");
    String fontStyle = Cache.getDefault("FONT_STYLE", Font.PLAIN + "");
    String fontSize = Cache.getDefault("FONT_SIZE", "10");

    seqNameItalics = Cache.getDefault("ID_ITALICS", true);

    int style = 0;
        switch (fontStyle) {
            case "bold":
                style = 1;
                break;
            case "italic":
                style = 2;
                break;
        }

    setFont(new Font(fontName, style, Integer.parseInt(fontSize)));

    alignment
            .setGapCharacter(Cache.getDefault("GAP_SYMBOL", "-").charAt(0));

    // We must set conservation and consensus before setting colour,
    // as Blosum and Clustal require this to be done
    if (hconsensus == null && !isDataset)
    {
      if (!alignment.isNucleotide())
      {
        showConservation = Cache.getDefault("SHOW_CONSERVATION", true);
        showQuality = Cache.getDefault("SHOW_QUALITY", true);
        showGroupConservation = Cache.getDefault("SHOW_GROUP_CONSERVATION",
                false);
      }
      showConsensusHistogram = Cache.getDefault("SHOW_CONSENSUS_HISTOGRAM",
              true);
      showSequenceLogo = Cache.getDefault("SHOW_CONSENSUS_LOGO", false);
      normaliseSequenceLogo = Cache.getDefault("NORMALISE_CONSENSUS_LOGO",
              false);
      showGroupConsensus = Cache.getDefault("SHOW_GROUP_CONSENSUS", false);
      showConsensus = Cache.getDefault("SHOW_IDENTITY", true);
      consensus = new AlignmentAnnotation("Consensus", "PID",
              new Annotation[1], 0f, 100f, AlignmentAnnotation.BAR_GRAPH);
      consensus.hasText = true;
      consensus.autoCalculated = true;
    }
    initAutoAnnotation();
    if (jalview.bin.Cache.getProperty("DEFAULT_COLOUR") != null)
    {
      globalColourScheme = ColourSchemeProperty.getColour(alignment,
              jalview.bin.Cache.getProperty("DEFAULT_COLOUR"));

      if (globalColourScheme instanceof UserColourScheme)
      {
        globalColourScheme = UserDefinedColours.loadDefaultColours();
        ((UserColourScheme) globalColourScheme).setThreshold(0,
                getIgnoreGapsConsensus());
      }

      if (globalColourScheme != null)
      {
        globalColourScheme.setConsensus(hconsensus);
      }
    }

    wrapAlignment = jalview.bin.Cache.getDefault("WRAP_ALIGNMENT", false);
    showUnconserved = jalview.bin.Cache.getDefault("SHOW_UNCONSERVED",
            false);
    sortByTree = jalview.bin.Cache.getDefault("SORT_BY_TREE", false);
    followSelection = jalview.bin.Cache.getDefault("FOLLOW_SELECTIONS",
            true);
  }

  /**
   * set the flag
   * 
   * @param b
   *          features are displayed if true
   */
  public void setShowSequenceFeatures(boolean b)
  {
    showSequenceFeatures = b;
  }

  public boolean getShowSequenceFeatures()
  {
    return showSequenceFeatures;
  }

  /**
   * centre columnar annotation labels in displayed alignment annotation TODO:
   * add to jalviewXML and annotation display settings
   */
  boolean centreColumnLabels = false;

  private boolean showdbrefs;

  private boolean shownpfeats;

  // --------END Structure Conservation

  /**
   * get the consensus sequence as displayed under the PID consensus annotation
   * row.
   * 
   * @return consensus sequence as a new sequence object
   */
  public SequenceI getConsensusSeq()
  {
    if (consensus == null)
    {
      updateConsensus(null);
    }
    if (consensus == null)
    {
      return null;
    }
    StringBuilder seqs = new StringBuilder();
    for (int i = 0; i < consensus.annotations.length; i++)
    {
      if (consensus.annotations[i] != null)
      {
        if (consensus.annotations[i].description.charAt(0) == '[')
        {
          seqs.append(consensus.annotations[i].description.charAt(1));
        }
        else
        {
          seqs.append(consensus.annotations[i].displayCharacter);
        }
      }
    }

    SequenceI sq = new Sequence("Consensus", seqs.toString());
    sq.setDescription("Percentage Identity Consensus "
            + ((ignoreGapsInConsensusCalculation) ? " without gaps" : ""));
    return sq;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public int getStartRes()
  {
    return startRes;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
    @Override
  public int getEndRes()
  {
    return endRes;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public int getStartSeq()
  {
    return startSeq;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param res
   *          DOCUMENT ME!
   */
  public void setStartRes(int res)
  {
    this.startRes = res;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param seq
   *          DOCUMENT ME!
   */
  public void setStartSeq(int seq)
  {
    this.startSeq = seq;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param res
   *          DOCUMENT ME!
   */
  public void setEndRes(int res)
  {
    if (res > (alignment.getWidth() - 1))
    {
      // log.System.out.println(" Corrected res from " + res + " to maximum " +
      // (alignment.getWidth()-1));
      res = alignment.getWidth() - 1;
    }

    if (res < 0)
    {
      res = 0;
    }

    this.endRes = res;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param seq
   *          DOCUMENT ME!
   */
  public void setEndSeq(int seq)
  {
    if (seq > alignment.getHeight())
    {
      seq = alignment.getHeight();
    }

    if (seq < 0)
    {
      seq = 0;
    }

    this.endSeq = seq;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public int getEndSeq()
  {
    return endSeq;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param f
   *          DOCUMENT ME!
   */
  public void setFont(Font f)
  {
    font = f;

    Container c = new Container();

    java.awt.FontMetrics fm = c.getFontMetrics(font);
    setCharHeight(fm.getHeight());
    setCharWidth(fm.charWidth('M'));
    validCharWidth = true;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Font getFont()
  {
    return font;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param w
   *          DOCUMENT ME!
   */
  public void setCharWidth(int w)
  {
    this.charWidth = w;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
    @Override
  public int getCharWidth()
  {
    return charWidth;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param h
   *          DOCUMENT ME!
   */
  public void setCharHeight(int h)
  {
    this.charHeight = h;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
    @Override
  public int getCharHeight()
  {
    return charHeight;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param w
   *          DOCUMENT ME!
   */
  public void setWrappedWidth(int w)
  {
    this.wrappedWidth = w;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public int getWrappedWidth()
  {
    return wrappedWidth;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
    @Override
  public AlignmentI getAlignment()
  {
    return alignment;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param align
   *          DOCUMENT ME!
   */
  public void setAlignment(AlignmentI align)
  {
    if (alignment != null && alignment.getCodonFrames() != null)
    {
      StructureSelectionManager.getStructureSelectionManager(
              Desktop.instance).removeMappings(alignment.getCodonFrames());
    }
    this.alignment = align;
    if (alignment != null && alignment.getCodonFrames() != null)
    {
      StructureSelectionManager.getStructureSelectionManager(
              Desktop.instance).addMappings(alignment.getCodonFrames());
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param state
   *          DOCUMENT ME!
   */
  public void setWrapAlignment(boolean state)
  {
    wrapAlignment = state;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param state
   *          DOCUMENT ME!
   */
  public void setShowText(boolean state)
  {
    showText = state;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param state
   *          DOCUMENT ME!
   */
  public void setRenderGaps(boolean state)
  {
    renderGaps = state;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean getColourText()
  {
    return showColourText;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param state
   *          DOCUMENT ME!
   */
  public void setColourText(boolean state)
  {
    showColourText = state;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param state
   *          DOCUMENT ME!
   */
  public void setShowBoxes(boolean state)
  {
    showBoxes = state;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean getWrapAlignment()
  {
    return wrapAlignment;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean getShowText()
  {
    return showText;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean getShowBoxes()
  {
    return showBoxes;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public char getGapCharacter()
  {
    return getAlignment().getGapCharacter();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param gap
   *          DOCUMENT ME!
   */
  public void setGapCharacter(char gap)
  {
    if (getAlignment() != null)
    {
      getAlignment().setGapCharacter(gap);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
    @Override
  public ColumnSelection getColumnSelection()
  {
    return colSel;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param tree
   *          DOCUMENT ME!
   */
  public void setCurrentTree(NJTree tree)
  {
    currentTree = tree;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public NJTree getCurrentTree()
  {
    return currentTree;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean getShowJVSuffix()
  {
    return showJVSuffix;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param b
   *          DOCUMENT ME!
   */
  public void setShowJVSuffix(boolean b)
  {
    showJVSuffix = b;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean getShowAnnotation()
  {
    return showAnnotation;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param b
   *          DOCUMENT ME!
   */
  public void setShowAnnotation(boolean b)
  {
    showAnnotation = b;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean getScaleAboveWrapped()
  {
    return scaleAboveWrapped;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean getScaleLeftWrapped()
  {
    return scaleLeftWrapped;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean getScaleRightWrapped()
  {
    return scaleRightWrapped;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param b
   *          DOCUMENT ME!
   */
  public void setScaleAboveWrapped(boolean b)
  {
    scaleAboveWrapped = b;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param b
   *          DOCUMENT ME!
   */
  public void setScaleLeftWrapped(boolean b)
  {
    scaleLeftWrapped = b;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param b
   *          DOCUMENT ME!
   */
  public void setScaleRightWrapped(boolean b)
  {
    scaleRightWrapped = b;
  }

  public void setDataset(boolean b)
  {
    isDataset = b;
  }

  public boolean isDataset()
  {
    return isDataset;
  }

  public boolean getShowHiddenMarkers()
  {
    return showHiddenMarkers;
  }

  public void setShowHiddenMarkers(boolean show)
  {
    showHiddenMarkers = show;
  }

  public Color getSequenceColour(SequenceI seq)
  {
    if (sequenceColours == null || !sequenceColours.containsKey(seq))
    {
      return Color.white;
    }
    else
    {
      return (Color) sequenceColours.get(seq);
    }
  }

  public void setSequenceColour(SequenceI seq, Color col)
  {
    if (sequenceColours == null)
    {
      sequenceColours = new Hashtable();
    }

    if (col == null)
    {
      sequenceColours.remove(seq);
    }
    else
    {
      sequenceColours.put(seq, col);
    }
  }

  /**
   * returns the visible column regions of the alignment
   * 
   * @param selectedRegionOnly
   *          true to just return the contigs intersecting with the selected
   *          area
   * @return
   */
  public int[] getViewAsVisibleContigs(boolean selectedRegionOnly)
  {
    int[] viscontigs = null;
    int start = 0, end = 0;
    if (selectedRegionOnly && selectionGroup != null)
    {
      start = selectionGroup.getStartRes();
      end = selectionGroup.getEndRes() + 1;
    }
    else
    {
      end = alignment.getWidth();
    }
    viscontigs = colSel.getVisibleContigs(start, end);
    return viscontigs;
  }

  /**
   * get hash of undo and redo list for the alignment
   * 
   * @return long[] { historyList.hashCode, redoList.hashCode };
   */
  public long[] getUndoRedoHash()
  {
    // TODO: JAL-1126
    if (historyList == null || redoList == null)
      return new long[]
      { -1, -1 };
    return new long[]
    { historyList.hashCode(), this.redoList.hashCode() };
  }

  /**
   * test if a particular set of hashcodes are different to the hashcodes for
   * the undo and redo list.
   * 
   * @param undoredo
   *          the stored set of hashcodes as returned by getUndoRedoHash
   * @return true if the hashcodes differ (ie the alignment has been edited) or
   *         the stored hashcode array differs in size
   */
  public boolean isUndoRedoHashModified(long[] undoredo)
  {
    if (undoredo == null)
    {
      return true;
    }
    long[] cstate = getUndoRedoHash();
    if (cstate.length != undoredo.length)
    {
      return true;
    }

    for (int i = 0; i < cstate.length; i++)
    {
      if (cstate[i] != undoredo[i])
      {
        return true;
      }
    }
    return false;
  }

  public boolean getCentreColumnLabels()
  {
    return centreColumnLabels;
  }

  public void setCentreColumnLabels(boolean centrecolumnlabels)
  {
    centreColumnLabels = centrecolumnlabels;
  }

  public void updateSequenceIdColours()
  {
    if (sequenceColours == null)
    {
      sequenceColours = new Hashtable();
    }
    for (SequenceGroup sg : alignment.getGroups())
    {
      if (sg.idColour != null)
      {
        for (SequenceI s : sg.getSequences(getHiddenRepSequences()))
        {
          sequenceColours.put(s, sg.idColour);
        }
      }
    }
  }

  /**
   * enable or disable the display of Database Cross References in the sequence
   * ID tooltip
   */
  public void setShowDbRefs(boolean show)
  {
    showdbrefs = show;
  }

  /**
   * 
   * @return true if Database References are to be displayed on tooltips.
   */
  public boolean isShowDbRefs()
  {
    return showdbrefs;
  }

  /**
   * 
   * @return true if Non-positional features are to be displayed on tooltips.
   */
  public boolean isShowNpFeats()
  {
    return shownpfeats;
  }

  /**
   * enable or disable the display of Non-Positional sequence features in the
   * sequence ID tooltip
   * 
   * @param show
   */
  public void setShowNpFeats(boolean show)
  {
    shownpfeats = show;
  }

  /**
   * 
   * @return true if view has hidden rows
   */
  public boolean hasHiddenRows()
  {
    return hasHiddenRows;
  }

  /**
   * 
   * @return true if view has hidden columns
   */
  public boolean hasHiddenColumns()
  {
    return hasHiddenColumns;
  }

  /**
   * when set, view will scroll to show the highlighted position
   */
  public boolean followHighlight = true;

  /**
   * @return true if view should scroll to show the highlighted region of a
   *         sequence
   * @return
   */
  public boolean getFollowHighlight()
  {
    return followHighlight;
  }

  public boolean followSelection = true;

  /**
   * @return true if view selection should always follow the selections
   *         broadcast by other selection sources
   */
  public boolean getFollowSelection()
  {
    return followSelection;
  }

  boolean showSeqFeaturesHeight;

  public void sendSelection()
  {
    jalview.structure.StructureSelectionManager
            .getStructureSelectionManager(Desktop.instance).sendSelection(
                    new SequenceGroup(getSelectionGroup()),
                    new ColumnSelection(getColumnSelection()), this);
  }

  public void setShowSequenceFeaturesHeight(boolean selected)
  {
    showSeqFeaturesHeight = selected;
  }

  public boolean getShowSequenceFeaturesHeight()
  {
    return showSeqFeaturesHeight;
  }

  /**
   * return the alignPanel containing the given viewport. Use this to get the
   * components currently handling the given viewport.
   * 
   * @param av
   * @return null or an alignPanel guaranteed to have non-null alignFrame
   *         reference
   */
  public AlignmentPanel getAlignPanel()
  {
    AlignmentPanel[] aps = PaintRefresher.getAssociatedPanels(this
            .getSequenceSetId());
    AlignmentPanel ap = null;
    for (int p = 0; aps != null && p < aps.length; p++)
    {
      if (aps[p].av == this)
      {
        return aps[p];
      }
    }
    return null;
  }

  public boolean getSortByTree()
  {
    return sortByTree;
  }

  public void setSortByTree(boolean sort)
  {
    sortByTree = sort;
  }

  /**
   * synthesize a column selection if none exists so it covers the given
   * selection group. if wholewidth is false, no column selection is made if the
   * selection group covers the whole alignment width.
   * 
   * @param sg
   * @param wholewidth
   */
  public void expandColSelection(SequenceGroup sg, boolean wholewidth)
  {
    int sgs, sge;
    if (sg != null
            && (sgs = sg.getStartRes()) >= 0
            && sg.getStartRes() <= (sge = sg.getEndRes())
            && (colSel == null || colSel.getSelected() == null || colSel
                    .getSelected().size() == 0))
    {
      if (!wholewidth && alignment.getWidth() == (1 + sge - sgs))
      {
        // do nothing
        return;
      }
      if (colSel == null)
      {
        colSel = new ColumnSelection();
      }
      for (int cspos = sg.getStartRes(); cspos <= sg.getEndRes(); cspos++)
      {
        colSel.addElement(cspos);
      }
    }
  }

  public StructureSelectionManager getStructureSelectionManager()
  {
    return StructureSelectionManager
            .getStructureSelectionManager(Desktop.instance);
  }

  /**
   * 
   * @param pdbEntries
   * @return a series of SequenceI arrays, one for each PDBEntry, listing which
   *         sequence in the alignment holds a reference to it
   */
  public SequenceI[][] collateForPDB(PDBEntry[] pdbEntries)
  {
    ArrayList<SequenceI[]> seqvectors = new ArrayList<SequenceI[]>();
    for (PDBEntry pdb : pdbEntries)
    {
      ArrayList<SequenceI> seqs = new ArrayList<SequenceI>();
      for (int i = 0; i < alignment.getHeight(); i++)
      {
        Vector pdbs = alignment.getSequenceAt(i).getDatasetSequence()
                .getPDBId();
        if (pdbs == null)
          continue;
        SequenceI sq;
        for (int p = 0; p < pdbs.size(); p++)
        {
          PDBEntry p1 = (PDBEntry) pdbs.elementAt(p);
          if (p1.getId().equals(pdb.getId()))
          {
            if (!seqs.contains(sq = alignment.getSequenceAt(i)))
              seqs.add(sq);

            continue;
          }
        }
      }
      seqvectors.add(seqs.toArray(new SequenceI[seqs.size()]));
    }
    return seqvectors.toArray(new SequenceI[seqvectors.size()][]);
  }

  public boolean isNormaliseSequenceLogo()
  {
    return normaliseSequenceLogo;
  }

  public void setNormaliseSequenceLogo(boolean state)
  {
    normaliseSequenceLogo = state;
  }

  /**
   * 
   * @return true if alignment characters should be displayed
   */
  public boolean isValidCharWidth()
  {
    return validCharWidth;
  }

  private Hashtable<String, AutoCalcSetting> calcIdParams = new Hashtable<String, AutoCalcSetting>();

  public AutoCalcSetting getCalcIdSettingsFor(String calcId)
  {
    return calcIdParams.get(calcId);
  }

  public void setCalcIdSettingsFor(String calcId, AutoCalcSetting settings,
          boolean needsUpdate)
  {
    calcIdParams.put(calcId, settings);
    // TODO: create a restart list to trigger any calculations that need to be
    // restarted after load
    // calculator.getRegisteredWorkersOfClass(settings.getWorkerClass())
    if (needsUpdate)
    {
      Cache.log.debug("trigger update for " + calcId);
    }
  }
}
