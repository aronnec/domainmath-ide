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
package jalview.viewmodel;

import jalview.analysis.AAFrequency;
import jalview.analysis.Conservation;
import jalview.api.AlignCalcManagerI;
import jalview.api.AlignViewportI;
import jalview.api.AlignmentViewPanel;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.AlignmentView;
import jalview.datamodel.Annotation;
import jalview.datamodel.ColumnSelection;
import jalview.datamodel.Sequence;
import jalview.datamodel.SequenceCollectionI;
import jalview.datamodel.SequenceGroup;
import jalview.datamodel.SequenceI;
import jalview.schemes.Blosum62ColourScheme;
import jalview.schemes.ClustalxColourScheme;
import jalview.schemes.ColourSchemeI;
import jalview.schemes.PIDColourScheme;
import jalview.schemes.ResidueProperties;
import jalview.workers.AlignCalcManager;
import jalview.workers.ConsensusThread;
import jalview.workers.StrucConsensusThread;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * base class holding visualization and analysis attributes and common logic for
 * an active alignment view displayed in the GUI
 * 
 * @author jimp
 * 
 */
public abstract class AlignmentViewport implements AlignViewportI
{
  /**
   * alignment displayed in the viewport. Please use get/setter
   */
  protected AlignmentI alignment;

  protected String sequenceSetID;

  /**
   * probably unused indicator that view is of a dataset rather than an
   * alignment
   */
  protected boolean isDataset = false;

  private Map<SequenceI, SequenceCollectionI> hiddenRepSequences;

  protected ColumnSelection colSel = new ColumnSelection();

  public boolean autoCalculateConsensus = true;

  protected boolean autoCalculateStrucConsensus = true;

  protected boolean ignoreGapsInConsensusCalculation = false;

  protected ColourSchemeI globalColourScheme = null;

  /**
   * gui state - changes to colour scheme propagated to all groups
   */
  private boolean colourAppliesToAllGroups;

  /**
   * @param value
   *          indicating if subsequent colourscheme changes will be propagated
   *          to all groups
   */
  public void setColourAppliesToAllGroups(boolean b)
  {
    colourAppliesToAllGroups = b;
  }

  /**
   * 
   * 
   * @return flag indicating if colourchanges propagated to all groups
   */
  public boolean getColourAppliesToAllGroups()
  {
    return colourAppliesToAllGroups;
  }

  boolean abovePIDThreshold = false;

  /**
   * GUI state
   * 
   * @return true if percent identity threshold is applied to shading
   */
  public boolean getAbovePIDThreshold()
  {
    return abovePIDThreshold;
  }

  /**
   * GUI state
   * 
   * 
   * @param b
   *          indicate if percent identity threshold is applied to shading
   */
  public void setAbovePIDThreshold(boolean b)
  {
    abovePIDThreshold = b;
  }

  int threshold;

  /**
   * DOCUMENT ME!
   * 
   * @param thresh
   *          DOCUMENT ME!
   */
  public void setThreshold(int thresh)
  {
    threshold = thresh;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public int getThreshold()
  {
    return threshold;
  }

  int increment;

  /**
   * 
   * @param inc
   *          set the scalar for bleaching colourschemes according to degree of
   *          conservation
   */
  public void setIncrement(int inc)
  {
    increment = inc;
  }

  /**
   * GUI State
   * 
   * @return get scalar for bleaching colourschemes by conservation
   */
  public int getIncrement()
  {
    return increment;
  }

  boolean conservationColourSelected = false;

  /**
   * GUI state
   * 
   * @return true if conservation based shading is enabled
   */
  public boolean getConservationSelected()
  {
    return conservationColourSelected;
  }

  /**
   * GUI state
   * 
   * @param b
   *          enable conservation based shading
   */
  public void setConservationSelected(boolean b)
  {
    conservationColourSelected = b;
  }

  @Override
  public void setGlobalColourScheme(ColourSchemeI cs)
  {
    // TODO: logic refactored from AlignFrame changeColour -
    // autorecalc stuff should be changed to rely on the worker system
    // check to see if we should implement a changeColour(cs) method rather than
    // put th logic in here
    // - means that caller decides if they want to just modify state and defer
    // calculation till later or to do all calculations in thread.
    // via changecolour
    globalColourScheme = cs;
    if (getColourAppliesToAllGroups())
    {
      for (SequenceGroup sg : getAlignment().getGroups())
      {
        if (cs == null)
        {
          sg.cs = null;
          continue;
        }
        if (cs instanceof ClustalxColourScheme)
        {
          sg.cs = new ClustalxColourScheme(sg, getHiddenRepSequences());
        }
        else
        {
          try
          {
            sg.cs = cs.getClass().newInstance();
          } catch (Exception ex)
          {
            ex.printStackTrace();
            sg.cs = cs;
          }
        }

        if (getAbovePIDThreshold() || cs instanceof PIDColourScheme
                || cs instanceof Blosum62ColourScheme)
        {
          sg.cs.setThreshold(threshold, getIgnoreGapsConsensus());
          sg.cs.setConsensus(AAFrequency.calculate(
                  sg.getSequences(getHiddenRepSequences()), 0,
                  sg.getWidth()));
        }
        else
        {
          sg.cs.setThreshold(0, getIgnoreGapsConsensus());
        }

        if (getConservationSelected())
        {
          Conservation c = new Conservation("Group",
                  ResidueProperties.propHash, 3,
                  sg.getSequences(getHiddenRepSequences()), 0,
                  getAlignment().getWidth() - 1);
          c.calculate();
          c.verdict(false, getConsPercGaps());
          sg.cs.setConservation(c);
        }
        else
        {
          sg.cs.setConservation(null);
          sg.cs.setThreshold(0, getIgnoreGapsConsensus());
        }

      }
    }

  }

  @Override
  public ColourSchemeI getGlobalColourScheme()
  {
    return globalColourScheme;
  }

  protected AlignmentAnnotation consensus;

  protected AlignmentAnnotation strucConsensus;

  protected AlignmentAnnotation conservation;

  protected AlignmentAnnotation quality;

  protected AlignmentAnnotation[] groupConsensus;

  protected AlignmentAnnotation[] groupConservation;

  /**
   * results of alignment consensus analysis for visible portion of view
   */
  protected Hashtable[] hconsensus = null;

  /**
   * results of secondary structure base pair consensus for visible portion of
   * view
   */
  protected Hashtable[] hStrucConsensus = null;

  /**
   * percentage gaps allowed in a column before all amino acid properties should
   * be considered unconserved
   */
  int ConsPercGaps = 25; // JBPNote : This should be a scalable property!

  @Override
  public int getConsPercGaps()
  {
    return ConsPercGaps;
  }

  @Override
  public void setSequenceConsensusHash(Hashtable[] hconsensus)
  {
    this.hconsensus = hconsensus;

  }

  @Override
  public Hashtable[] getSequenceConsensusHash()
  {
    return hconsensus;
  }

  @Override
  public Hashtable[] getRnaStructureConsensusHash()
  {
    return hStrucConsensus;
  }

  @Override
  public void setRnaStructureConsensusHash(Hashtable[] hStrucConsensus)
  {
    this.hStrucConsensus = hStrucConsensus;

  }

  @Override
  public AlignmentAnnotation getAlignmentQualityAnnot()
  {
    return quality;
  }

  @Override
  public AlignmentAnnotation getAlignmentConservationAnnotation()
  {
    return conservation;
  }

  @Override
  public AlignmentAnnotation getAlignmentConsensusAnnotation()
  {
    return consensus;
  }

  @Override
  public AlignmentAnnotation getAlignmentStrucConsensusAnnotation()
  {
    return strucConsensus;
  }

  protected AlignCalcManagerI calculator = new AlignCalcManager();

  /**
   * trigger update of conservation annotation
   */
  public void updateConservation(final AlignmentViewPanel ap)
  {
    // see note in mantis : issue number 8585
    if (alignment.isNucleotide() || conservation == null
            || !autoCalculateConsensus)
    {
      return;
    }
    if (calculator
            .getRegisteredWorkersOfClass(jalview.workers.ConservationThread.class) == null)
    {
      calculator.registerWorker(new jalview.workers.ConservationThread(
              this, ap));
    }
  }

  /**
   * trigger update of consensus annotation
   */
  public void updateConsensus(final AlignmentViewPanel ap)
  {
    // see note in mantis : issue number 8585
    if (consensus == null || !autoCalculateConsensus)
    {
      return;
    }
    if (calculator.getRegisteredWorkersOfClass(ConsensusThread.class) == null)
    {
      calculator.registerWorker(new ConsensusThread(this, ap));
    }
  }

  // --------START Structure Conservation
  public void updateStrucConsensus(final AlignmentViewPanel ap)
  {
    if (autoCalculateStrucConsensus && strucConsensus == null
            && alignment.isNucleotide() && alignment.hasRNAStructure())
    {

    }

    // see note in mantis : issue number 8585
    if (strucConsensus == null || !autoCalculateStrucConsensus)
    {
      return;
    }
    if (calculator.getRegisteredWorkersOfClass(StrucConsensusThread.class) == null)
    {
      calculator.registerWorker(new StrucConsensusThread(this, ap));
    }
  }

  public boolean isCalcInProgress()
  {
    return calculator.isWorking();
  }

  @Override
  public boolean isCalculationInProgress(
          AlignmentAnnotation alignmentAnnotation)
  {
    if (!alignmentAnnotation.autoCalculated)
      return false;
    if (calculator.workingInvolvedWith(alignmentAnnotation))
    {
      // System.err.println("grey out ("+alignmentAnnotation.label+")");
      return true;
    }
    return false;
  }

  @Override
  public boolean isClosed()
  {
    // TODO: check that this isClosed is only true after panel is closed, not
    // before it is fully constructed.
    return alignment == null;
  }

  @Override
  public AlignCalcManagerI getCalcManager()
  {
    return calculator;
  }

  /**
   * should conservation rows be shown for groups
   */
  protected boolean showGroupConservation = false;

  /**
   * should consensus rows be shown for groups
   */
  protected boolean showGroupConsensus = false;

  /**
   * should consensus profile be rendered by default
   */
  protected boolean showSequenceLogo = false;

  /**
   * should consensus profile be rendered normalised to row height
   */
  protected boolean normaliseSequenceLogo = false;

  /**
   * should consensus histograms be rendered by default
   */
  protected boolean showConsensusHistogram = true;

  /**
   * @return the showConsensusProfile
   */
  @Override
  public boolean isShowSequenceLogo()
  {
    return showSequenceLogo;
  }

  /**
   * @param showSequenceLogo
   *          the new value
   */
  public void setShowSequenceLogo(boolean showSequenceLogo)
  {
    if (showSequenceLogo != this.showSequenceLogo)
    {
      // TODO: decouple settings setting from calculation when refactoring
      // annotation update method from alignframe to viewport
      this.showSequenceLogo = showSequenceLogo;
      calculator.updateAnnotationFor(ConsensusThread.class);
      calculator.updateAnnotationFor(StrucConsensusThread.class);
    }
    this.showSequenceLogo = showSequenceLogo;
  }

  /**
   * @param showConsensusHistogram
   *          the showConsensusHistogram to set
   */
  public void setShowConsensusHistogram(boolean showConsensusHistogram)
  {
    this.showConsensusHistogram = showConsensusHistogram;
  }

  /**
   * @return the showGroupConservation
   */
  public boolean isShowGroupConservation()
  {
    return showGroupConservation;
  }

  /**
   * @param showGroupConservation
   *          the showGroupConservation to set
   */
  public void setShowGroupConservation(boolean showGroupConservation)
  {
    this.showGroupConservation = showGroupConservation;
  }

  /**
   * @return the showGroupConsensus
   */
  public boolean isShowGroupConsensus()
  {
    return showGroupConsensus;
  }

  /**
   * @param showGroupConsensus
   *          the showGroupConsensus to set
   */
  public void setShowGroupConsensus(boolean showGroupConsensus)
  {
    this.showGroupConsensus = showGroupConsensus;
  }

  /**
   * 
   * @return flag to indicate if the consensus histogram should be rendered by
   *         default
   */
  @Override
  public boolean isShowConsensusHistogram()
  {
    return this.showConsensusHistogram;
  }

  /**
   * show non-conserved residues only
   */
  protected boolean showUnconserved = false;

  /**
   * when set, updateAlignment will always ensure sequences are of equal length
   */
  private boolean padGaps = false;

  /**
   * when set, alignment should be reordered according to a newly opened tree
   */
  public boolean sortByTree = false;

  public boolean getShowUnconserved()
  {
    return showUnconserved;
  }

  public void setShowUnconserved(boolean showunconserved)
  {
    showUnconserved = showunconserved;
  }

  /**
   * @param showNonconserved
   *          the showUnconserved to set
   */
  public void setShowunconserved(boolean displayNonconserved)
  {
    this.showUnconserved = displayNonconserved;
  }

  /**
   * 
   * 
   * @return null or the currently selected sequence region
   */
  public SequenceGroup getSelectionGroup()
  {
    return selectionGroup;
  }

  /**
   * Set the selection group for this window.
   * 
   * @param sg
   *          - group holding references to sequences in this alignment view
   * 
   */
  public void setSelectionGroup(SequenceGroup sg)
  {
    selectionGroup = sg;
  }

  public void setHiddenColumns(ColumnSelection colsel)
  {
    this.colSel = colsel;
    if (colSel.getHiddenColumns() != null)
    {
      hasHiddenColumns = true;
    }
  }

  @Override
  public ColumnSelection getColumnSelection()
  {
    return colSel;
  }

  public void setColumnSelection(ColumnSelection colSel)
  {
    this.colSel = colSel;
  }

  /**
   * 
   * @return
   */
  @Override
  public Map<SequenceI, SequenceCollectionI> getHiddenRepSequences()
  {
    return hiddenRepSequences;
  }

  @Override
  public void setHiddenRepSequences(
          Map<SequenceI, SequenceCollectionI> hiddenRepSequences)
  {
    this.hiddenRepSequences = hiddenRepSequences;
  }

  protected boolean hasHiddenColumns = false;

  public void updateHiddenColumns()
  {
    hasHiddenColumns = colSel.getHiddenColumns() != null;
  }

  protected boolean hasHiddenRows = false;

  public boolean hasHiddenRows()
  {
    return hasHiddenRows;
  }

  protected SequenceGroup selectionGroup;

  public void setSequenceSetId(String newid)
  {
    if (sequenceSetID != null)
    {
      System.err
              .println("Warning - overwriting a sequenceSetId for a viewport!");
    }
    sequenceSetID = new String(newid);
  }

  public String getSequenceSetId()
  {
    if (sequenceSetID == null)
    {
      sequenceSetID = alignment.hashCode() + "";
    }

    return sequenceSetID;
  }

  /**
   * unique viewId for synchronizing state (e.g. with stored Jalview Project)
   * 
   */
  protected String viewId = null;

  public String getViewId()
  {
    if (viewId == null)
    {
      viewId = this.getSequenceSetId() + "." + this.hashCode() + "";
    }
    return viewId;
  }

  public void setIgnoreGapsConsensus(boolean b, AlignmentViewPanel ap)
  {
    ignoreGapsInConsensusCalculation = b;
    if (ap != null)
    {
      updateConsensus(ap);
      if (globalColourScheme != null)
      {
        globalColourScheme.setThreshold(globalColourScheme.getThreshold(),
                ignoreGapsInConsensusCalculation);
      }
    }

  }

  private long sgrouphash = -1, colselhash = -1;

  /**
   * checks current SelectionGroup against record of last hash value, and
   * updates record.
   * 
   * @param b
   *          update the record of last hash value
   * 
   * @return true if SelectionGroup changed since last call (when b is true)
   */
  public boolean isSelectionGroupChanged(boolean b)
  {
    int hc = (selectionGroup == null || selectionGroup.getSize() == 0) ? -1
            : selectionGroup.hashCode();
    if (hc != -1 && hc != sgrouphash)
    {
      if (b)
      {
        sgrouphash = hc;
      }
      return true;
    }
    return false;
  }

  /**
   * checks current colsel against record of last hash value, and optionally
   * updates record.
   * 
   * @param b
   *          update the record of last hash value
   * @return true if colsel changed since last call (when b is true)
   */
  public boolean isColSelChanged(boolean b)
  {
    int hc = (colSel == null || colSel.size() == 0) ? -1 : colSel
            .hashCode();
    if (hc != -1 && hc != colselhash)
    {
      if (b)
      {
        colselhash = hc;
      }
      return true;
    }
    return false;
  }

  @Override
  public boolean getIgnoreGapsConsensus()
  {
    return ignoreGapsInConsensusCalculation;
  }

  // / property change stuff

  // JBPNote Prolly only need this in the applet version.
  private final java.beans.PropertyChangeSupport changeSupport = new java.beans.PropertyChangeSupport(
          this);

  protected boolean showConservation = true;

  protected boolean showQuality = true;

  protected boolean showConsensus = true;

  /**
   * Property change listener for changes in alignment
   * 
   * @param listener
   *          DOCUMENT ME!
   */
  public void addPropertyChangeListener(
          java.beans.PropertyChangeListener listener)
  {
    changeSupport.addPropertyChangeListener(listener);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param listener
   *          DOCUMENT ME!
   */
  public void removePropertyChangeListener(
          java.beans.PropertyChangeListener listener)
  {
    changeSupport.removePropertyChangeListener(listener);
  }

  /**
   * Property change listener for changes in alignment
   * 
   * @param prop
   *          DOCUMENT ME!
   * @param oldvalue
   *          DOCUMENT ME!
   * @param newvalue
   *          DOCUMENT ME!
   */
  public void firePropertyChange(String prop, Object oldvalue,
          Object newvalue)
  {
    changeSupport.firePropertyChange(prop, oldvalue, newvalue);
  }

  // common hide/show column stuff

  public void hideSelectedColumns()
  {
    if (colSel.size() < 1)
    {
      return;
    }

    colSel.hideSelectedColumns();
    setSelectionGroup(null);

    hasHiddenColumns = true;
  }

  public void hideColumns(int start, int end)
  {
    if (start == end)
    {
      colSel.hideColumns(start);
    }
    else
    {
      colSel.hideColumns(start, end);
    }

    hasHiddenColumns = true;
  }

  public void showColumn(int col)
  {
    colSel.revealHiddenColumns(col);
    if (colSel.getHiddenColumns() == null)
    {
      hasHiddenColumns = false;
    }
  }

  public void showAllHiddenColumns()
  {
    colSel.revealAllHiddenColumns();
    hasHiddenColumns = false;
  }

  // common hide/show seq stuff
  public void showAllHiddenSeqs()
  {
    if (alignment.getHiddenSequences().getSize() > 0)
    {
      if (selectionGroup == null)
      {
        selectionGroup = new SequenceGroup();
        selectionGroup.setEndRes(alignment.getWidth() - 1);
      }
      Vector tmp = alignment.getHiddenSequences().showAll(
              hiddenRepSequences);
      for (int t = 0; t < tmp.size(); t++)
      {
        selectionGroup.addSequence((SequenceI) tmp.elementAt(t), false);
      }

      hasHiddenRows = false;
      hiddenRepSequences = null;

      firePropertyChange("alignment", null, alignment.getSequences());
      // used to set hasHiddenRows/hiddenRepSequences here, after the property
      // changed event
      sendSelection();
    }
  }

  public void showSequence(int index)
  {
    Vector tmp = alignment.getHiddenSequences().showSequence(index,
            hiddenRepSequences);
    if (tmp.size() > 0)
    {
      if (selectionGroup == null)
      {
        selectionGroup = new SequenceGroup();
        selectionGroup.setEndRes(alignment.getWidth() - 1);
      }

      for (int t = 0; t < tmp.size(); t++)
      {
        selectionGroup.addSequence((SequenceI) tmp.elementAt(t), false);
      }
      // JBPNote: refactor: only update flag if we modified visiblity (used to
      // do this regardless)
      if (alignment.getHiddenSequences().getSize() < 1)
      {
        hasHiddenRows = false;
      }
      firePropertyChange("alignment", null, alignment.getSequences());
      sendSelection();
    }
  }

  public void hideAllSelectedSeqs()
  {
    if (selectionGroup == null || selectionGroup.getSize() < 1)
    {
      return;
    }

    SequenceI[] seqs = selectionGroup.getSequencesInOrder(alignment);

    hideSequence(seqs);

    setSelectionGroup(null);
  }

  public void hideSequence(SequenceI[] seq)
  {
    if (seq != null)
    {
      for (int i = 0; i < seq.length; i++)
      {
        alignment.getHiddenSequences().hideSequence(seq[i]);
      }
      hasHiddenRows = true;
      firePropertyChange("alignment", null, alignment.getSequences());
    }
  }

  public void hideRepSequences(SequenceI repSequence, SequenceGroup sg)
  {
    int sSize = sg.getSize();
    if (sSize < 2)
    {
      return;
    }

    if (hiddenRepSequences == null)
    {
      hiddenRepSequences = new Hashtable();
    }

    hiddenRepSequences.put(repSequence, sg);

    // Hide all sequences except the repSequence
    SequenceI[] seqs = new SequenceI[sSize - 1];
    int index = 0;
    for (int i = 0; i < sSize; i++)
    {
      if (sg.getSequenceAt(i) != repSequence)
      {
        if (index == sSize - 1)
        {
          return;
        }

        seqs[index++] = sg.getSequenceAt(i);
      }
    }
    sg.setSeqrep(repSequence); // note: not done in 2.7applet
    sg.setHidereps(true); // note: not done in 2.7applet
    hideSequence(seqs);

  }

  public boolean isHiddenRepSequence(SequenceI seq)
  {
    return hiddenRepSequences != null
            && hiddenRepSequences.containsKey(seq);
  }

  public SequenceGroup getRepresentedSequences(SequenceI seq)
  {
    return (SequenceGroup) (hiddenRepSequences == null ? null
            : hiddenRepSequences.get(seq));
  }

  public int adjustForHiddenSeqs(int alignmentIndex)
  {
    return alignment.getHiddenSequences().adjustForHiddenSeqs(
            alignmentIndex);
  }

  // Selection manipulation
  /**
   * broadcast selection to any interested parties
   */
  public abstract void sendSelection();

  public void invertColumnSelection()
  {
    colSel.invertColumnSelection(0, alignment.getWidth());
  }

  /**
   * This method returns an array of new SequenceI objects derived from the
   * whole alignment or just the current selection with start and end points
   * adjusted
   * 
   * @note if you need references to the actual SequenceI objects in the
   *       alignment or currently selected then use getSequenceSelection()
   * @return selection as new sequenceI objects
   */
  public SequenceI[] getSelectionAsNewSequence()
  {
    SequenceI[] sequences;
    // JBPNote: Need to test jalviewLite.getSelectedSequencesAsAlignmentFrom -
    // this was the only caller in the applet for this method
    // JBPNote: in applet, this method returned references to the alignment
    // sequences, and it did not honour the presence/absence of annotation
    // attached to the alignment (probably!)
    if (selectionGroup == null || selectionGroup.getSize() == 0)
    {
      sequences = alignment.getSequencesArray();
      AlignmentAnnotation[] annots = alignment.getAlignmentAnnotation();
      for (int i = 0; i < sequences.length; i++)
      {
        sequences[i] = new Sequence(sequences[i], annots); // construct new
        // sequence with
        // subset of visible
        // annotation
      }
    }
    else
    {
      sequences = selectionGroup.getSelectionAsNewSequences(alignment);
    }

    return sequences;
  }

  /**
   * get the currently selected sequence objects or all the sequences in the
   * alignment.
   * 
   * @return array of references to sequence objects
   */
  public SequenceI[] getSequenceSelection()
  {
    SequenceI[] sequences = null;
    if (selectionGroup != null)
    {
      sequences = selectionGroup.getSequencesInOrder(alignment);
    }
    if (sequences == null)
    {
      sequences = alignment.getSequencesArray();
    }
    return sequences;
  }

  /**
   * This method returns the visible alignment as text, as seen on the GUI, ie
   * if columns are hidden they will not be returned in the result. Use this for
   * calculating trees, PCA, redundancy etc on views which contain hidden
   * columns.
   * 
   * @return String[]
   */
  public jalview.datamodel.CigarArray getViewAsCigars(
          boolean selectedRegionOnly)
  {
    return new jalview.datamodel.CigarArray(alignment,
            (hasHiddenColumns ? colSel : null),
            (selectedRegionOnly ? selectionGroup : null));
  }

  /**
   * return a compact representation of the current alignment selection to pass
   * to an analysis function
   * 
   * @param selectedOnly
   *          boolean true to just return the selected view
   * @return AlignmentView
   */
  public jalview.datamodel.AlignmentView getAlignmentView(
          boolean selectedOnly)
  {
    return getAlignmentView(selectedOnly, false);
  }

  /**
   * return a compact representation of the current alignment selection to pass
   * to an analysis function
   * 
   * @param selectedOnly
   *          boolean true to just return the selected view
   * @param markGroups
   *          boolean true to annotate the alignment view with groups on the
   *          alignment (and intersecting with selected region if selectedOnly
   *          is true)
   * @return AlignmentView
   */
  public jalview.datamodel.AlignmentView getAlignmentView(
          boolean selectedOnly, boolean markGroups)
  {
    return new AlignmentView(alignment, colSel, selectionGroup,
            hasHiddenColumns, selectedOnly, markGroups);
  }

  /**
   * This method returns the visible alignment as text, as seen on the GUI, ie
   * if columns are hidden they will not be returned in the result. Use this for
   * calculating trees, PCA, redundancy etc on views which contain hidden
   * columns.
   * 
   * @return String[]
   */
  public String[] getViewAsString(boolean selectedRegionOnly)
  {
    String[] selection = null;
    SequenceI[] seqs = null;
    int i, iSize;
    int start = 0, end = 0;
    if (selectedRegionOnly && selectionGroup != null)
    {
      iSize = selectionGroup.getSize();
      seqs = selectionGroup.getSequencesInOrder(alignment);
      start = selectionGroup.getStartRes();
      end = selectionGroup.getEndRes() + 1;
    }
    else
    {
      iSize = alignment.getHeight();
      seqs = alignment.getSequencesArray();
      end = alignment.getWidth();
    }

    selection = new String[iSize];
    if (hasHiddenColumns)
    {
      selection = colSel.getVisibleSequenceStrings(start, end, seqs);
    }
    else
    {
      for (i = 0; i < iSize; i++)
      {
        selection[i] = seqs[i].getSequenceAsString(start, end);
      }

    }
    return selection;
  }

  /**
   * return visible region boundaries within given column range
   * 
   * @param min
   *          first column (inclusive, from 0)
   * @param max
   *          last column (exclusive)
   * @return int[][] range of {start,end} visible positions
   */
  public int[][] getVisibleRegionBoundaries(int min, int max)
  {
    Vector regions = new Vector();
    int start = min;
    int end = max;

    do
    {
      if (hasHiddenColumns)
      {
        if (start == 0)
        {
          start = colSel.adjustForHiddenColumns(start);
        }

        end = colSel.getHiddenBoundaryRight(start);
        if (start == end)
        {
          end = max;
        }
        if (end > max)
        {
          end = max;
        }
      }

      regions.addElement(new int[]
      { start, end });

      if (hasHiddenColumns)
      {
        start = colSel.adjustForHiddenColumns(end);
        start = colSel.getHiddenBoundaryLeft(start) + 1;
      }
    } while (end < max);

    int[][] startEnd = new int[regions.size()][2];

    regions.copyInto(startEnd);

    return startEnd;

  }

  /**
   * @return the padGaps
   */
  public boolean isPadGaps()
  {
    return padGaps;
  }

  /**
   * @param padGaps
   *          the padGaps to set
   */
  public void setPadGaps(boolean padGaps)
  {
    this.padGaps = padGaps;
  }

  /**
   * apply any post-edit constraints and trigger any calculations needed after
   * an edit has been performed on the alignment
   * 
   * @param ap
   */
  public void alignmentChanged(AlignmentViewPanel ap)
  {
    if (isPadGaps())
    {
      alignment.padGaps();
    }
    if (autoCalculateConsensus)
    {
      updateConsensus(ap);
    }
    if (hconsensus != null && autoCalculateConsensus)
    {
      updateConservation(ap);
    }
    if (autoCalculateStrucConsensus)
    {
      updateStrucConsensus(ap);
    }

    // Reset endRes of groups if beyond alignment width
    int alWidth = alignment.getWidth();
    List<SequenceGroup> groups = alignment.getGroups();
    if (groups != null)
    {
      for (SequenceGroup sg : groups)
      {
        if (sg.getEndRes() > alWidth)
        {
          sg.setEndRes(alWidth - 1);
        }
      }
    }

    if (selectionGroup != null && selectionGroup.getEndRes() > alWidth)
    {
      selectionGroup.setEndRes(alWidth - 1);
    }

    resetAllColourSchemes();
    calculator.restartWorkers();
    // alignment.adjustSequenceAnnotations();
  }

  /**
   * reset scope and do calculations for all applied colourschemes on alignment
   */
  void resetAllColourSchemes()
  {
    ColourSchemeI cs = globalColourScheme;
    if (cs != null)
    {
      cs.alignmentChanged(alignment, null);

      cs.setConsensus(hconsensus);
      if (cs.conservationApplied())
      {
        cs.setConservation(Conservation.calculateConservation("All",
                ResidueProperties.propHash, 3, alignment.getSequences(), 0,
                alignment.getWidth(), false, getConsPercGaps(), false));
      }
    }

    for (SequenceGroup sg : alignment.getGroups())
    {
      if (sg.cs != null)
      {
        sg.cs.alignmentChanged(sg, hiddenRepSequences);
      }
      sg.recalcConservation();
    }
  }

  protected void initAutoAnnotation()
  {
    // TODO: add menu option action that nulls or creates consensus object
    // depending on if the user wants to see the annotation or not in a
    // specific alignment

    if (hconsensus == null && !isDataset)
    {
      if (!alignment.isNucleotide())
      {
        if (showConservation)
        {
          if (conservation == null)
          {
            conservation = new AlignmentAnnotation("Conservation",
                    "Conservation of total alignment less than "
                            + getConsPercGaps() + "% gaps",
                    new Annotation[1], 0f, 11f,
                    AlignmentAnnotation.BAR_GRAPH);
            conservation.hasText = true;
            conservation.autoCalculated = true;
            alignment.addAnnotation(conservation);
          }
        }
        if (showQuality)
        {
          if (quality == null)
          {
            quality = new AlignmentAnnotation("Quality",
                    "Alignment Quality based on Blosum62 scores",
                    new Annotation[1], 0f, 11f,
                    AlignmentAnnotation.BAR_GRAPH);
            quality.hasText = true;
            quality.autoCalculated = true;
            alignment.addAnnotation(quality);
          }
        }
      }
      else
      {
        if (alignment.hasRNAStructure())
        {
          strucConsensus = new AlignmentAnnotation("StrucConsensus", "PID",
                  new Annotation[1], 0f, 100f,
                  AlignmentAnnotation.BAR_GRAPH);
          strucConsensus.hasText = true;
          strucConsensus.autoCalculated = true;
        }
      }

      consensus = new AlignmentAnnotation("Consensus", "PID",
              new Annotation[1], 0f, 100f, AlignmentAnnotation.BAR_GRAPH);
      consensus.hasText = true;
      consensus.autoCalculated = true;

      if (showConsensus)
      {
        alignment.addAnnotation(consensus);
        if (strucConsensus != null)
        {
          alignment.addAnnotation(strucConsensus);
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.api.AlignViewportI#calcPanelHeight()
   */
  public int calcPanelHeight()
  {
    // setHeight of panels
    AlignmentAnnotation[] aa = getAlignment().getAlignmentAnnotation();
    int height = 0;
    int charHeight = getCharHeight();
    if (aa != null)
    {
      boolean graphgrp[] = null;
      for (int i = 0; i < aa.length; i++)
      {
        if (aa[i] == null)
        {
          System.err.println("Null annotation row: ignoring.");
          continue;
        }
        if (!aa[i].visible)
        {
          continue;
        }
        if (aa[i].graphGroup > -1)
        {
          if (graphgrp == null)
          {
            graphgrp = new boolean[aa.length];
          }
          if (graphgrp[aa[i].graphGroup])
          {
            continue;
          }
          else
          {
            graphgrp[aa[i].graphGroup] = true;
          }
        }
        aa[i].height = 0;

        if (aa[i].hasText)
        {
          aa[i].height += charHeight;
        }

        if (aa[i].hasIcons)
        {
          aa[i].height += 16;
        }

        if (aa[i].graph > 0)
        {
          aa[i].height += aa[i].graphHeight;
        }

        if (aa[i].height == 0)
        {
          aa[i].height = 20;
        }

        height += aa[i].height;
      }
    }
    if (height == 0)
    {
      // set minimum
      height = 20;
    }
    return height;
  }

  @Override
  public void updateGroupAnnotationSettings(boolean applyGlobalSettings,
          boolean preserveNewGroupSettings)
  {
    boolean updateCalcs = false;
    boolean conv = isShowGroupConservation();
    boolean cons = isShowGroupConsensus();
    boolean showprf = isShowSequenceLogo();
    boolean showConsHist = isShowConsensusHistogram();
    boolean normLogo = isNormaliseSequenceLogo();

    /**
     * TODO reorder the annotation rows according to group/sequence ordering on
     * alignment
     */
    boolean sortg = true;

    // remove old automatic annotation
    // add any new annotation

    // intersect alignment annotation with alignment groups

    AlignmentAnnotation[] aan = alignment.getAlignmentAnnotation();
    List<SequenceGroup> oldrfs = new ArrayList<SequenceGroup>();
    if (aan != null)
    {
      for (int an = 0; an < aan.length; an++)
      {
        if (aan[an].autoCalculated && aan[an].groupRef != null)
        {
          oldrfs.add(aan[an].groupRef);
          alignment.deleteAnnotation(aan[an]);
          aan[an] = null;
        }
      }
    }
    if (alignment.getGroups() != null)
    {
      for (SequenceGroup sg : alignment.getGroups())
      {
        updateCalcs = false;
        if (applyGlobalSettings
                || (!preserveNewGroupSettings && !oldrfs.contains(sg)))
        {
          // set defaults for this group's conservation/consensus
          sg.setshowSequenceLogo(showprf);
          sg.setShowConsensusHistogram(showConsHist);
          sg.setNormaliseSequenceLogo(normLogo);
        }
        if (conv)
        {
          updateCalcs = true;
          alignment.addAnnotation(sg.getConservationRow(), 0);
        }
        if (cons)
        {
          updateCalcs = true;
          alignment.addAnnotation(sg.getConsensus(), 0);
        }
        // refresh the annotation rows
        if (updateCalcs)
        {
          sg.recalcConservation();
        }
      }
    }
    oldrfs.clear();
  }

}
