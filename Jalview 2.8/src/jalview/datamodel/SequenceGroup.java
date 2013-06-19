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
package jalview.datamodel;

import java.util.*;
import java.util.List;

import java.awt.*;

import jalview.analysis.*;
import jalview.schemes.*;

/**
 * Collects a set contiguous ranges on a set of sequences
 * 
 * @author $author$
 * @version $Revision$
 */
public class SequenceGroup implements AnnotatedCollectionI
{
  String groupName;

  String description;

  Conservation conserve;

  Vector aaFrequency;

  boolean displayBoxes = true;

  boolean displayText = true;

  boolean colourText = false;

  /**
   * after Olivier's non-conserved only character display
   */
  boolean showNonconserved = false;

  /**
   * group members
   */
  private Vector<SequenceI> sequences = new Vector<SequenceI>();

  /**
   * representative sequence for this group (if any)
   */
  private SequenceI seqrep = null;

  int width = -1;

  /**
   * Colourscheme applied to group if any
   */
  public ColourSchemeI cs;

  int startRes = 0;

  int endRes = 0;

  public Color outlineColour = Color.black;

  public Color idColour = null;

  public int thresholdTextColour = 0;

  public Color textColour = Color.black;

  public Color textColour2 = Color.white;

  /**
   * consensus calculation property
   */
  private boolean ignoreGapsInConsensus = true;

  /**
   * consensus calculation property
   */
  private boolean showSequenceLogo = false;

  /**
   * flag indicating if logo should be rendered normalised
   */
  private boolean normaliseSequenceLogo;

  /**
   * @return the includeAllConsSymbols
   */
  public boolean isShowSequenceLogo()
  {
    return showSequenceLogo;
  }

  /**
   * Creates a new SequenceGroup object.
   */
  public SequenceGroup()
  {
    groupName = "JGroup:" + this.hashCode();
  }

  /**
   * Creates a new SequenceGroup object.
   * 
   * @param sequences
   * @param groupName
   * @param scheme
   * @param displayBoxes
   * @param displayText
   * @param colourText
   * @param start
   *          first column of group
   * @param end
   *          last column of group
   */
  public SequenceGroup(Vector sequences, String groupName,
          ColourSchemeI scheme, boolean displayBoxes, boolean displayText,
          boolean colourText, int start, int end)
  {
    this.sequences = sequences;
    this.groupName = groupName;
    this.displayBoxes = displayBoxes;
    this.displayText = displayText;
    this.colourText = colourText;
    this.cs = scheme;
    startRes = start;
    endRes = end;
    recalcConservation();
  }

  /**
   * copy constructor
   * 
   * @param seqsel
   */
  public SequenceGroup(SequenceGroup seqsel)
  {
    if (seqsel != null)
    {
      sequences = new Vector();
      Enumeration<SequenceI> sq = seqsel.sequences.elements();
      while (sq.hasMoreElements())
      {
        sequences.addElement(sq.nextElement());
      }
      ;
      if (seqsel.groupName != null)
      {
        groupName = new String(seqsel.groupName);
      }
      displayBoxes = seqsel.displayBoxes;
      displayText = seqsel.displayText;
      colourText = seqsel.colourText;
      startRes = seqsel.startRes;
      endRes = seqsel.endRes;
      cs = seqsel.cs;
      if (seqsel.description != null)
        description = new String(seqsel.description);
      hidecols = seqsel.hidecols;
      hidereps = seqsel.hidereps;
      idColour = seqsel.idColour;
      outlineColour = seqsel.outlineColour;
      seqrep = seqsel.seqrep;
      textColour = seqsel.textColour;
      textColour2 = seqsel.textColour2;
      thresholdTextColour = seqsel.thresholdTextColour;
      width = seqsel.width;
      ignoreGapsInConsensus = seqsel.ignoreGapsInConsensus;
      if (seqsel.conserve != null)
      {
        recalcConservation(); // safer than
        // aaFrequency = (Vector) seqsel.aaFrequency.clone(); // ??
      }
    }
  }

  public SequenceI[] getSelectionAsNewSequences(AlignmentI align)
  {
    int iSize = sequences.size();
    SequenceI[] seqs = new SequenceI[iSize];
    SequenceI[] inorder = getSequencesInOrder(align);

    for (int i = 0, ipos = 0; i < inorder.length; i++)
    {
      SequenceI seq = inorder[i];

      seqs[ipos] = seq.getSubSequence(startRes, endRes + 1);
      if (seqs[ipos] != null)
      {
        seqs[ipos].setDescription(seq.getDescription());
        seqs[ipos].setDBRef(seq.getDBRef());
        seqs[ipos].setSequenceFeatures(seq.getSequenceFeatures());
        if (seq.getDatasetSequence() != null)
        {
          seqs[ipos].setDatasetSequence(seq.getDatasetSequence());
        }

        if (seq.getAnnotation() != null)
        {
          AlignmentAnnotation[] alann = align.getAlignmentAnnotation();
          // Only copy annotation that is either a score or referenced by the
          // alignment's annotation vector
          for (int a = 0; a < seq.getAnnotation().length; a++)
          {
            AlignmentAnnotation tocopy = seq.getAnnotation()[a];
            if (alann != null)
            {
              boolean found = false;
              for (int pos = 0; pos < alann.length; pos++)
              {
                if (alann[pos] == tocopy)
                {
                  found = true;
                  break;
                }
              }
              if (!found)
                continue;
            }
            AlignmentAnnotation newannot = new AlignmentAnnotation(
                    seq.getAnnotation()[a]);
            newannot.restrict(startRes, endRes);
            newannot.setSequenceRef(seqs[ipos]);
            newannot.adjustForAlignment();
            seqs[ipos].addAlignmentAnnotation(newannot);
          }
        }
        ipos++;
      }
      else
      {
        iSize--;
      }
    }
    if (iSize != inorder.length)
    {
      SequenceI[] nseqs = new SequenceI[iSize];
      System.arraycopy(seqs, 0, nseqs, 0, iSize);
      seqs = nseqs;
    }
    return seqs;

  }

  /**
   * If sequence ends in gaps, the end residue can be correctly calculated here
   * 
   * @param seq
   *          SequenceI
   * @return int
   */
  public int findEndRes(SequenceI seq)
  {
    int eres = 0;
    char ch;

    for (int j = 0; j < endRes + 1 && j < seq.getLength(); j++)
    {
      ch = seq.getCharAt(j);
      if (!jalview.util.Comparison.isGap((ch)))
      {
        eres++;
      }
    }

    if (eres > 0)
    {
      eres += seq.getStart() - 1;
    }

    return eres;
  }

  public List<SequenceI> getSequences()
  {
    return sequences;
  }

  public List<SequenceI> getSequences(
          Map<SequenceI, SequenceCollectionI> hiddenReps)
  {
    if (hiddenReps == null)
    {
      return sequences;
    }
    else
    {
      Vector allSequences = new Vector();
      SequenceI seq;
      for (int i = 0; i < sequences.size(); i++)
      {
        seq = (SequenceI) sequences.elementAt(i);
        allSequences.addElement(seq);
        if (hiddenReps.containsKey(seq))
        {
          SequenceCollectionI hsg = hiddenReps.get(seq);
          for (SequenceI seq2 : hsg.getSequences())
          {
            if (seq2 != seq && !allSequences.contains(seq2))
            {
              allSequences.addElement(seq2);
            }
          }
        }
      }

      return allSequences;
    }
  }

  public SequenceI[] getSequencesAsArray(
          Map<SequenceI, SequenceCollectionI> map)
  {
    List<SequenceI> tmp = getSequences(map);
    if (tmp == null)
    {
      return null;
    }
    return tmp.toArray(new SequenceI[tmp.size()]);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param col
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean adjustForRemoveLeft(int col)
  {
    // return value is true if the group still exists
    if (startRes >= col)
    {
      startRes = startRes - col;
    }

    if (endRes >= col)
    {
      endRes = endRes - col;

      if (startRes > endRes)
      {
        startRes = 0;
      }
    }
    else
    {
      // must delete this group!!
      return false;
    }

    return true;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param col
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean adjustForRemoveRight(int col)
  {
    if (startRes > col)
    {
      // delete this group
      return false;
    }

    if (endRes >= col)
    {
      endRes = col;
    }

    return true;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String getName()
  {
    return groupName;
  }

  public String getDescription()
  {
    return description;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param name
   *          DOCUMENT ME!
   */
  public void setName(String name)
  {
    groupName = name;
    // TODO: URGENT: update dependent objects (annotation row)
  }

  public void setDescription(String desc)
  {
    description = desc;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Conservation getConservation()
  {
    return conserve;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param c
   *          DOCUMENT ME!
   */
  public void setConservation(Conservation c)
  {
    conserve = c;
  }

  /**
   * Add s to this sequence group. If aligment sequence is already contained in
   * group, it will not be added again, but recalculation may happen if the flag
   * is set.
   * 
   * @param s
   *          alignment sequence to be added
   * @param recalc
   *          true means Group's conservation should be recalculated
   */
  public void addSequence(SequenceI s, boolean recalc)
  {
    if (s != null && !sequences.contains(s))
    {
      sequences.addElement(s);
    }

    if (recalc)
    {
      recalcConservation();
    }
  }

  /**
   * Max Gaps Threshold for performing a conservation calculation TODO: make
   * this a configurable property - or global to an alignment view
   */
  private int consPercGaps = 25;

  /**
   * calculate residue conservation for group - but only if necessary.
   */
  public void recalcConservation()
  {
    if (cs == null && consensus == null && conservation == null)
    {
      return;
    }
    if (cs != null)
    {
      cs.alignmentChanged(this, null);
    }
    try
    {
      Hashtable cnsns[] = AAFrequency.calculate(sequences, startRes,
              endRes + 1, showSequenceLogo);
      if (consensus != null)
      {
        _updateConsensusRow(cnsns);
      }
      if (cs != null)
      {
        cs.setConsensus(cnsns);
        cs.alignmentChanged(this, null);
      }

      if ((conservation != null)
              || (cs != null && cs.conservationApplied()))
      {
        Conservation c = new Conservation(groupName,
                ResidueProperties.propHash, 3, sequences, startRes,
                endRes + 1);
        c.calculate();
        c.verdict(false, consPercGaps);
        if (conservation != null)
        {
          _updateConservationRow(c);
        }
        if (cs != null)
        {
          if (cs.conservationApplied())
          {
            cs.setConservation(c);
            cs.alignmentChanged(this, null);
          }
        }
      }
    } catch (java.lang.OutOfMemoryError err)
    {
      // TODO: catch OOM
      System.out.println("Out of memory loading groups: " + err);
    }

  }

  private void _updateConservationRow(Conservation c)
  {
    if (conservation == null)
    {
      getConservation();
    }
    // update Labels
    conservation.label = "Conservation for " + getName();
    conservation.description = "Conservation for group " + getName()
            + " less than " + consPercGaps + "% gaps";
    // preserve width if already set
    int aWidth = (conservation.annotations != null) ? (endRes < conservation.annotations.length ? conservation.annotations.length
            : endRes + 1)
            : endRes + 1;
    conservation.annotations = null;
    conservation.annotations = new Annotation[aWidth]; // should be alignment
                                                       // width
    c.completeAnnotations(conservation, null, startRes, endRes + 1);
  }

  public Hashtable[] consensusData = null;

  private void _updateConsensusRow(Hashtable[] cnsns)
  {
    if (consensus == null)
    {
      getConsensus();
    }
    consensus.label = "Consensus for " + getName();
    consensus.description = "Percent Identity";
    consensusData = cnsns;
    // preserve width if already set
    int aWidth = (consensus.annotations != null) ? (endRes < consensus.annotations.length ? consensus.annotations.length
            : endRes + 1)
            : endRes + 1;
    consensus.annotations = null;
    consensus.annotations = new Annotation[aWidth]; // should be alignment width

    AAFrequency.completeConsensus(consensus, cnsns, startRes, endRes + 1,
            ignoreGapsInConsensus, showSequenceLogo); // TODO: setting container
                                                      // for
                                                      // ignoreGapsInConsensusCalculation);
  }

  /**
   * @param s
   *          sequence to either add or remove from group
   * @param recalc
   *          flag passed to delete/addSequence to indicate if group properties
   *          should be recalculated
   */
  public void addOrRemove(SequenceI s, boolean recalc)
  {
    if (sequences.contains(s))
    {
      deleteSequence(s, recalc);
    }
    else
    {
      addSequence(s, recalc);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param s
   *          DOCUMENT ME!
   * @param recalc
   *          DOCUMENT ME!
   */
  public void deleteSequence(SequenceI s, boolean recalc)
  {
    sequences.removeElement(s);

    if (recalc)
    {
      recalcConservation();
    }
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
  public int getEndRes()
  {
    return endRes;
  }

  /**
   * Set the first column selected by this group. Runs from 0<=i<N_cols
   * 
   * @param i
   */
  public void setStartRes(int i)
  {
    startRes = i;
  }

  /**
   * Set the groups last selected column. Runs from 0<=i<N_cols
   * 
   * @param i
   */
  public void setEndRes(int i)
  {
    endRes = i;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public int getSize()
  {
    return sequences.size();
  }

  /**
   * DOCUMENT ME!
   * 
   * @param i
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public SequenceI getSequenceAt(int i)
  {
    return (SequenceI) sequences.elementAt(i);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param state
   *          DOCUMENT ME!
   */
  public void setColourText(boolean state)
  {
    colourText = state;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean getColourText()
  {
    return colourText;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param state
   *          DOCUMENT ME!
   */
  public void setDisplayText(boolean state)
  {
    displayText = state;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean getDisplayText()
  {
    return displayText;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param state
   *          DOCUMENT ME!
   */
  public void setDisplayBoxes(boolean state)
  {
    displayBoxes = state;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public boolean getDisplayBoxes()
  {
    return displayBoxes;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public int getWidth()
  {
    // MC This needs to get reset when characters are inserted and deleted
    if (sequences.size() > 0)
    {
      width = ((SequenceI) sequences.elementAt(0)).getLength();
    }

    for (int i = 1; i < sequences.size(); i++)
    {
      SequenceI seq = (SequenceI) sequences.elementAt(i);

      if (seq.getLength() > width)
      {
        width = seq.getLength();
      }
    }

    return width;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param c
   *          DOCUMENT ME!
   */
  public void setOutlineColour(Color c)
  {
    outlineColour = c;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Color getOutlineColour()
  {
    return outlineColour;
  }

  /**
   * 
   * returns the sequences in the group ordered by the ordering given by al.
   * this used to return an array with null entries regardless, new behaviour is
   * below. TODO: verify that this does not affect use in applet or application
   * 
   * @param al
   *          Alignment
   * @return SequenceI[] intersection of sequences in group with al, ordered by
   *         al, or null if group does not intersect with al
   */
  public SequenceI[] getSequencesInOrder(AlignmentI al)
  {
    return getSequencesInOrder(al, true);
  }

  /**
   * return an array representing the intersection of the group with al,
   * optionally returning an array the size of al.getHeight() where nulls mark
   * the non-intersected sequences
   * 
   * @param al
   * @param trim
   * @return null or array
   */
  public SequenceI[] getSequencesInOrder(AlignmentI al, boolean trim)
  {
    int sSize = sequences.size();
    int alHeight = al.getHeight();

    SequenceI[] seqs = new SequenceI[(trim) ? sSize : alHeight];

    int index = 0;
    for (int i = 0; i < alHeight && index < sSize; i++)
    {
      if (sequences.contains(al.getSequenceAt(i)))
      {
        seqs[(trim) ? index : i] = al.getSequenceAt(i);
        index++;
      }
    }
    if (index == 0)
    {
      return null;
    }
    if (!trim)
    {
      return seqs;
    }
    if (index < seqs.length)
    {
      SequenceI[] dummy = seqs;
      seqs = new SequenceI[index];
      while (--index >= 0)
      {
        seqs[index] = dummy[index];
        dummy[index] = null;
      }
    }
    return seqs;
  }

  /**
   * @return the idColour
   */
  public Color getIdColour()
  {
    return idColour;
  }

  /**
   * @param idColour
   *          the idColour to set
   */
  public void setIdColour(Color idColour)
  {
    this.idColour = idColour;
  }

  /**
   * @return the representative sequence for this group
   */
  public SequenceI getSeqrep()
  {
    return seqrep;
  }

  /**
   * set the representative sequence for this group. Note - this affects the
   * interpretation of the Hidereps attribute.
   * 
   * @param seqrep
   *          the seqrep to set (null means no sequence representative)
   */
  public void setSeqrep(SequenceI seqrep)
  {
    this.seqrep = seqrep;
  }

  /**
   * 
   * @return true if group has a sequence representative
   */
  public boolean hasSeqrep()
  {
    return seqrep != null;
  }

  /**
   * visibility of rows or represented rows covered by group
   */
  private boolean hidereps = false;

  /**
   * set visibility of sequences covered by (if no sequence representative is
   * defined) or represented by this group.
   * 
   * @param visibility
   */
  public void setHidereps(boolean visibility)
  {
    hidereps = visibility;
  }

  /**
   * 
   * @return true if sequences represented (or covered) by this group should be
   *         hidden
   */
  public boolean isHidereps()
  {
    return hidereps;
  }

  /**
   * visibility of columns intersecting this group
   */
  private boolean hidecols = false;

  /**
   * set intended visibility of columns covered by this group
   * 
   * @param visibility
   */
  public void setHideCols(boolean visibility)
  {
    hidecols = visibility;
  }

  /**
   * 
   * @return true if columns covered by group should be hidden
   */
  public boolean isHideCols()
  {
    return hidecols;
  }

  /**
   * create a new sequence group from the intersection of this group with an
   * alignment Hashtable of hidden representatives
   * 
   * @param alignment
   *          (may not be null)
   * @param map
   *          (may be null)
   * @return new group containing sequences common to this group and alignment
   */
  public SequenceGroup intersect(AlignmentI alignment,
          Map<SequenceI, SequenceCollectionI> map)
  {
    SequenceGroup sgroup = new SequenceGroup(this);
    SequenceI[] insect = getSequencesInOrder(alignment);
    sgroup.sequences = new Vector();
    for (int s = 0; insect != null && s < insect.length; s++)
    {
      if (map == null || map.containsKey(insect[s]))
      {
        sgroup.sequences.addElement(insect[s]);
      }
    }
    // Enumeration en =getSequences(hashtable).elements();
    // while (en.hasMoreElements())
    // {
    // SequenceI elem = (SequenceI) en.nextElement();
    // if (alignment.getSequences().contains(elem))
    // {
    // sgroup.addSequence(elem, false);
    // }
    // }
    return sgroup;
  }

  /**
   * @return the showUnconserved
   */
  public boolean getShowNonconserved()
  {
    return showNonconserved;
  }

  /**
   * @param showNonconserved
   *          the showUnconserved to set
   */
  public void setShowNonconserved(boolean displayNonconserved)
  {
    this.showNonconserved = displayNonconserved;
  }

  AlignmentAnnotation consensus = null, conservation = null;

  /**
   * flag indicating if consensus histogram should be rendered
   */
  private boolean showConsensusHistogram;

  /**
   * set this alignmentAnnotation object as the one used to render consensus
   * annotation
   * 
   * @param aan
   */
  public void setConsensus(AlignmentAnnotation aan)
  {
    if (consensus == null)
    {
      consensus = aan;
    }
  }

  /**
   * 
   * @return automatically calculated consensus row
   */
  public AlignmentAnnotation getConsensus()
  {
    // TODO get or calculate and get consensus annotation row for this group
    int aWidth = this.getWidth();
    // pointer
    // possibility
    // here.
    if (aWidth < 0)
    {
      return null;
    }
    if (consensus == null)
    {
      consensus = new AlignmentAnnotation("", "", new Annotation[1], 0f,
              100f, AlignmentAnnotation.BAR_GRAPH);
    }
    consensus.hasText = true;
    consensus.autoCalculated = true;
    consensus.groupRef = this;
    consensus.label = "Consensus for " + getName();
    consensus.description = "Percent Identity";
    return consensus;
  }

  /**
   * set this alignmentAnnotation object as the one used to render consensus
   * annotation
   * 
   * @param aan
   */
  public void setConservationRow(AlignmentAnnotation aan)
  {
    if (conservation == null)
    {
      conservation = aan;
    }
  }

  /**
   * get the conservation annotation row for this group
   * 
   * @return autoCalculated annotation row
   */
  public AlignmentAnnotation getConservationRow()
  {
    if (conservation == null)
    {
      conservation = new AlignmentAnnotation("", "", new Annotation[1], 0f,
              11f, AlignmentAnnotation.BAR_GRAPH);
    }

    conservation.hasText = true;
    conservation.autoCalculated = true;
    conservation.groupRef = this;
    conservation.label = "Conservation for " + getName();
    conservation.description = "Conservation for group " + getName()
            + " less than " + consPercGaps + "% gaps";
    return conservation;
  }

  /**
   * 
   * @return true if annotation rows have been instantiated for this group
   */
  public boolean hasAnnotationRows()
  {
    return consensus != null || conservation != null;
  }

  public SequenceI getConsensusSeq()
  {
    getConsensus();
    StringBuffer seqs = new StringBuffer();
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

    SequenceI sq = new Sequence("Group" + getName() + " Consensus",
            seqs.toString());
    sq.setDescription("Percentage Identity Consensus "
            + ((ignoreGapsInConsensus) ? " without gaps" : ""));
    return sq;
  }

  public void setIgnoreGapsConsensus(boolean state)
  {
    if (this.ignoreGapsInConsensus != state && consensus != null)
    {
      ignoreGapsInConsensus = state;
      recalcConservation();
    }
    ignoreGapsInConsensus = state;
  }

  public boolean getIgnoreGapsConsensus()
  {
    return ignoreGapsInConsensus;
  }

  /**
   * @param showSequenceLogo
   *          indicates if a sequence logo is shown for consensus annotation
   */
  public void setshowSequenceLogo(boolean showSequenceLogo)
  {
    // TODO: decouple calculation from settings update
    if (this.showSequenceLogo != showSequenceLogo && consensus != null)
    {
      this.showSequenceLogo = showSequenceLogo;
      recalcConservation();
    }
    this.showSequenceLogo = showSequenceLogo;
  }

  /**
   * 
   * @param showConsHist
   *          flag indicating if the consensus histogram for this group should
   *          be rendered
   */
  public void setShowConsensusHistogram(boolean showConsHist)
  {

    if (showConsensusHistogram != showConsHist && consensus != null)
    {
      this.showConsensusHistogram = showConsHist;
      recalcConservation();
    }
    this.showConsensusHistogram = showConsHist;
  }

  /**
   * @return the showConsensusHistogram
   */
  public boolean isShowConsensusHistogram()
  {
    return showConsensusHistogram;
  }

  /**
   * set flag indicating if logo should be normalised when rendered
   * 
   * @param norm
   */
  public void setNormaliseSequenceLogo(boolean norm)
  {
    normaliseSequenceLogo = norm;
  }

  public boolean isNormaliseSequenceLogo()
  {
    return normaliseSequenceLogo;
  }

  @Override
  /**
   * returns a new array with all annotation involving this group
   */
  public AlignmentAnnotation[] getAlignmentAnnotation()
  {
    // TODO add in other methods like 'getAlignmentAnnotation(String label),
    // etc'
    ArrayList<AlignmentAnnotation> annot = new ArrayList<AlignmentAnnotation>();
    for (SequenceI seq : (Vector<SequenceI>) sequences)
    {
      for (AlignmentAnnotation al : seq.getAnnotation())
      {
        if (al.groupRef == this)
        {
          annot.add(al);
        }
      }
    }
    if (consensus != null)
    {
      annot.add(consensus);
    }
    if (conservation != null)
    {
      annot.add(conservation);
    }
    return annot.toArray(new AlignmentAnnotation[0]);
  }

  @Override
  public Iterable<AlignmentAnnotation> findAnnotation(String calcId)
  {
    ArrayList<AlignmentAnnotation> aa = new ArrayList<AlignmentAnnotation>();
    for (AlignmentAnnotation a : getAlignmentAnnotation())
    {
      if (a.getCalcId() == calcId)
      {
        aa.add(a);
      }
    }
    return aa;
  }

  public void clear()
  {
    sequences.clear();
  }
}
