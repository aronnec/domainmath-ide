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

import jalview.util.ShiftList;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 * Transient object compactly representing a 'view' of an alignment - with
 * discontinuities marked. Extended in Jalview 2.7 to optionally record sequence
 * groups and specific selected regions on the alignment.
 */
public class AlignmentView
{
  private SeqCigar[] sequences = null;

  private int[] contigs = null;

  private int width = 0;

  private int firstCol = 0;

  /**
   * one or more ScGroup objects, which are referenced by each seqCigar's group
   * membership
   */
  private Vector scGroups;

  /**
   * Group defined over SeqCigars. Unlike AlignmentI associated groups, each
   * SequenceGroup hold just the essential properties for the group, but no
   * references to the sequences involved. SeqCigars hold references to the
   * seuqenceGroup entities themselves.
   */
  private class ScGroup
  {
    public Vector seqs;

    public SequenceGroup sg;

    ScGroup()
    {
      seqs = new Vector();
    }
  }

  /**
   * vector of selected seqCigars. This vector is also referenced by each
   * seqCigar contained in it.
   */
  private Vector selected;

  /**
   * Construct an alignmentView from a live jalview alignment view. Note -
   * hidden rows will be excluded from alignmentView Note: JAL-1179
   * 
   * @param alignment
   *          - alignment as referenced by an AlignViewport
   * @param columnSelection
   *          -
   * @param selection
   * @param hasHiddenColumns
   *          - mark the hidden columns in columnSelection as hidden in the view
   * @param selectedRegionOnly
   *          - when set, only include the selected region in the view,
   *          otherwise just mark the selected region on the constructed view.
   * @param recordGroups
   *          - when set, any groups on the given alignment will be marked on
   *          the view
   */
  public AlignmentView(AlignmentI alignment,
          ColumnSelection columnSelection, SequenceGroup selection,
          boolean hasHiddenColumns, boolean selectedRegionOnly,
          boolean recordGroups)
  {
    // refactored from AlignViewport.getAlignmentView(selectedOnly);
    this(new jalview.datamodel.CigarArray(alignment,
            (hasHiddenColumns ? columnSelection : null),
            (selectedRegionOnly ? selection : null)),
            (selectedRegionOnly && selection != null) ? selection
                    .getStartRes() : 0);
    // walk down SeqCigar array and Alignment Array - optionally restricted by
    // selected region.
    // test group membership for each sequence in each group, store membership
    // and record non-empty groups in group list.
    // record / sub-select selected region on the alignment view
    SequenceI[] selseqs;
    if (selection != null && selection.getSize() > 0)
    {
      List<SequenceI> sel = selection.getSequences(null);
      this.selected = new Vector();
      selseqs = selection
              .getSequencesInOrder(alignment, selectedRegionOnly);
    }
    else
    {
      selseqs = alignment.getSequencesArray();
    }

    // get the alignment's group list and make a copy
    Vector grps = new Vector();
    List<SequenceGroup> gg = alignment.getGroups();
    grps.addAll(gg);
    ScGroup[] sgrps = null;
    boolean addedgps[] = null;
    if (grps != null)
    {
      SequenceGroup sg;
      if (selection != null && selectedRegionOnly)
      {
        // trim annotation to the region being stored.
        // strip out any groups that do not actually intersect with the
        // visible and selected region
        int ssel = selection.getStartRes(), esel = selection.getEndRes();
        Vector isg = new Vector();
        Enumeration en = grps.elements();
        while (en.hasMoreElements())
        {
          sg = (SequenceGroup) en.nextElement();

          if (!(sg.getStartRes() > esel || sg.getEndRes() < ssel))
          {
            // adjust bounds of new group, if necessary.
            if (sg.getStartRes() < ssel)
            {
              sg.setStartRes(ssel);
            }
            if (sg.getEndRes() > esel)
            {
              sg.setEndRes(esel);
            }
            sg.setStartRes(sg.getStartRes() - ssel + 1);
            sg.setEndRes(sg.getEndRes() - ssel + 1);

            isg.addElement(sg);
          }
        }
        grps = isg;
      }

      sgrps = new ScGroup[grps.size()];
      addedgps = new boolean[grps.size()];
      for (int g = 0; g < sgrps.length; g++)
      {
        sg = (SequenceGroup) grps.elementAt(g);
        sgrps[g] = new ScGroup();
        sgrps[g].sg = new SequenceGroup(sg);
        addedgps[g] = false;
        grps.setElementAt(sg.getSequences(null), g);
      }
      // grps now contains vectors (should be sets) for each group, so we can
      // track when we've done with the group
    }
    int csi = 0;
    for (int i = 0; i < selseqs.length; i++)
    {
      if (selseqs[i] != null)
      {
        if (selection != null && selection.getSize() > 0
                && !selectedRegionOnly)
        {
          sequences[csi].setGroupMembership(selected);
          selected.addElement(sequences[csi]);
        }
        if (grps != null)
        {
          for (int sg = 0; sg < sgrps.length; sg++)
          {
            if (((Vector) grps.elementAt(sg)).contains(selseqs[i]))
            {
              sequences[csi].setGroupMembership(sgrps[sg]);
              sgrps[sg].sg.deleteSequence(selseqs[i], false);
              sgrps[sg].seqs.addElement(sequences[csi]);
              if (!addedgps[sg])
              {
                if (scGroups == null)
                {
                  scGroups = new Vector();
                }
                addedgps[sg] = true;
                scGroups.addElement(sgrps[sg]);
              }
            }
          }
        }
        csi++;
      }
    }
    // finally, delete the remaining sequences (if any) not selected
    for (int sg = 0; sg < sgrps.length; sg++)
    {
      SequenceI[] sqs = sgrps[sg].sg.getSequencesAsArray(null);
      for (int si = 0; si < sqs.length; si++)
      {
        sgrps[sg].sg.deleteSequence(sqs[si], false);
      }
      sgrps[sg] = null;
    }
  }

  /**
   * construct an alignmentView from a SeqCigarArray. Errors are thrown if the
   * seqcigararray.isSeqCigarArray() flag is not set.
   */
  public AlignmentView(CigarArray seqcigararray)
  {
    if (!seqcigararray.isSeqCigarArray())
    {
      throw new Error(
              "Implementation Error - can only make an alignment view from a CigarArray of sequences.");
    }
    // contigs = seqcigararray.applyDeletions();
    contigs = seqcigararray.getDeletedRegions();
    sequences = seqcigararray.getSeqCigarArray();
    width = seqcigararray.getWidth(); // visible width
  }

  /**
   * Create an alignmentView where the first column corresponds with the
   * 'firstcol' column of some reference alignment
   * 
   * @param sdata
   * @param firstcol
   */
  public AlignmentView(CigarArray sdata, int firstcol)
  {
    this(sdata);
    firstCol = firstcol;
  }

  public void setSequences(SeqCigar[] sequences)
  {
    this.sequences = sequences;
  }

  public void setContigs(int[] contigs)
  {
    this.contigs = contigs;
  }

  public SeqCigar[] getSequences()
  {
    return sequences;
  }

  /**
   * @see CigarArray.getDeletedRegions
   * @return int[] { vis_start, sym_start, length }
   */
  public int[] getContigs()
  {
    return contigs;
  }

  /**
   * get the full alignment and a columnselection object marking the hidden
   * regions
   * 
   * @param gapCharacter
   *          char
   * @return Object[] { SequenceI[], ColumnSelection}
   */
  public Object[] getAlignmentAndColumnSelection(char gapCharacter)
  {
    ColumnSelection colsel = new ColumnSelection();

    return new Object[]
    {
        SeqCigar.createAlignmentSequences(sequences, gapCharacter, colsel,
                contigs), colsel };
  }

  /**
   * return the visible alignment corresponding to this view. Sequences in this
   * alignment are edited versions of the parent sequences - where hidden
   * regions have been removed. NOTE: the sequence data in this alignment is not
   * complete!
   * 
   * @param c
   * @return
   */
  public AlignmentI getVisibleAlignment(char c)
  {
    SequenceI[] aln = getVisibleSeqs(c);

    AlignmentI vcal = new Alignment(aln);
    addPrunedGroupsInOrder(vcal, -1, -1, true);
    return vcal;
  }

  /**
   * add groups from view to the given alignment
   * 
   * @param vcal
   * @param gstart
   *          -1 or 0 to width-1
   * @param gend
   *          -1 or gstart to width-1
   * @param viscontigs
   *          - true if vcal is alignment of the visible regions of the view
   *          (e.g. as returned from getVisibleAlignment)
   */
  private void addPrunedGroupsInOrder(AlignmentI vcal, int gstart,
          int gend, boolean viscontigs)
  {
    boolean r = false;
    if (gstart > -1 && gstart <= gend)
    {
      r = true;
    }

    SequenceI[] aln = vcal.getSequencesArray();
    {
      /**
       * prune any groups to the visible coordinates of the alignment.
       */
      {
        int nvg = (scGroups != null) ? scGroups.size() : 0;
        if (nvg > 0)
        {
          SequenceGroup[] nsg = new SequenceGroup[nvg];
          for (int g = 0; g < nvg; g++)
          {
            SequenceGroup sg = ((ScGroup) scGroups.elementAt(g)).sg;
            if (r)
            {
              if (sg.getStartRes() > gend || sg.getEndRes() < gstart)
              {
                // Skip this group
                nsg[g] = null;
                continue;
              }
            }

            // clone group properties
            nsg[g] = new SequenceGroup(sg);

            // may need to shift/trim start and end ?
            if (r && !viscontigs)
            {
              // Not fully tested code - routine not yet called with
              // viscontigs==false
              if (nsg[g].getStartRes() < gstart)
              {
                nsg[g].setStartRes(0);
              }
              else
              {
                nsg[g].setStartRes(nsg[g].getStartRes() - gstart);
                nsg[g].setEndRes(nsg[g].getEndRes() - gstart);
              }
              if (nsg[g].getEndRes() > (gend - gstart))
              {
                nsg[g].setEndRes(gend - gstart);
              }
            }
          }
          if (viscontigs)
          {
            // prune groups to cover just the visible positions between
            // gstart/gend.
            if (contigs != null)
            {
              int p = 0;
              ShiftList prune = new ShiftList();
              if (r)
              {
                // adjust for start of alignment within visible window.
                prune.addShift(gstart, -gstart); //
              }
              for (int h = 0; h < contigs.length; h += 3)
              {
                {
                  prune.addShift(p + contigs[h + 1], contigs[h + 2]
                          - contigs[h + 1]);
                }
                p = contigs[h + 1] + contigs[h + 2];
              }
              for (int g = 0; g < nsg.length; g++)
              {
                if (nsg[g] != null)
                {
                  int s = nsg[g].getStartRes(), t = nsg[g].getEndRes();
                  int w = 1 + t - s;
                  if (r)
                  {
                    if (s < gstart)
                    {
                      s = gstart;
                    }
                    if (t > gend)
                    {
                      t = gend;
                    }
                  }
                  s = prune.shift(s);
                  t = prune.shift(t);
                  nsg[g].setStartRes(s);
                  nsg[g].setEndRes(t);
                }
              }
            }
          }

          for (int nsq = 0; nsq < aln.length; nsq++)
          {
            for (int g = 0; g < nvg; g++)
            {
              if (nsg[g] != null
                      && sequences[nsq].isMemberOf(scGroups.elementAt(g)))
              {
                nsg[g].addSequence(aln[nsq], false);
              }
            }
          }
          for (int g = 0; g < nvg; g++)
          {
            if (nsg[g] != null && nsg[g].getSize() > 0)
            {
              vcal.addGroup(nsg[g]);
            }
            nsg[g] = null;
          }
        }
      }
    }
  }

  /**
   * generate sequence array corresponding to the visible parts of the
   * alignment.
   * 
   * @param c
   * @return
   */
  private SequenceI[] getVisibleSeqs(char c)
  {
    SequenceI[] aln = new SequenceI[sequences.length];
    for (int i = 0, j = sequences.length; i < j; i++)
    {
      aln[i] = sequences[i].getSeq('-');
    }
    // Remove hidden regions from sequence objects.
    String seqs[] = getSequenceStrings('-');
    for (int i = 0, j = aln.length; i < j; i++)
    {
      aln[i].setSequence(seqs[i]);
    }
    return aln;
  }

  /**
   * creates new alignment objects for all contiguous visible segments
   * 
   * @param c
   * @param start
   * @param end
   * @param regionOfInterest
   *          specify which sequences to include (or null to include all
   *          sequences)
   * @return AlignmentI[] - all alignments where each sequence is a subsequence
   *         constructed from visible contig regions of view
   */
  public AlignmentI[] getVisibleContigAlignments(char c)
  {
    int nvc = 0;
    int[] vcontigs = getVisibleContigs();
    SequenceI[][] contigviews = getVisibleContigs(c);
    AlignmentI[] vcals = new AlignmentI[contigviews.length];
    for (nvc = 0; nvc < contigviews.length; nvc++)
    {
      vcals[nvc] = new Alignment(contigviews[nvc]);
      if (scGroups != null && scGroups.size() > 0)
      {
        addPrunedGroupsInOrder(vcals[nvc], vcontigs[nvc * 2],
                vcontigs[nvc * 2 + 1], true);
      }
    }
    return vcals;
  }

  /**
   * get an array of visible sequence strings for a view on an alignment using
   * the given gap character
   * 
   * @param c
   *          char
   * @return String[]
   */
  public String[] getSequenceStrings(char c)
  {
    String[] seqs = new String[sequences.length];
    for (int n = 0; n < sequences.length; n++)
    {
      String fullseq = sequences[n].getSequenceString(c);
      if (contigs != null)
      {
        seqs[n] = "";
        int p = 0;
        for (int h = 0; h < contigs.length; h += 3)
        {
          seqs[n] += fullseq.substring(p, contigs[h + 1]);
          p = contigs[h + 1] + contigs[h + 2];
        }
        seqs[n] += fullseq.substring(p);
      }
      else
      {
        seqs[n] = fullseq;
      }
    }
    return seqs;
  }

  /**
   * 
   * @return visible number of columns in alignment view
   */
  public int getWidth()
  {
    return width;
  }

  protected void setWidth(int width)
  {
    this.width = width;
  }

  /**
   * get the contiguous subalignments in an alignment view.
   * 
   * @param gapCharacter
   *          char
   * @return SequenceI[][]
   */
  public SequenceI[][] getVisibleContigs(char gapCharacter)
  {
    SequenceI[][] smsa;
    int njobs = 1;
    if (sequences == null || width <= 0)
    {
      return null;
    }
    if (contigs != null && contigs.length > 0)
    {
      int start = 0;
      njobs = 0;
      int fwidth = width;
      for (int contig = 0; contig < contigs.length; contig += 3)
      {
        if ((contigs[contig + 1] - start) > 0)
        {
          njobs++;
        }
        fwidth += contigs[contig + 2]; // end up with full region width
        // (including hidden regions)
        start = contigs[contig + 1] + contigs[contig + 2];
      }
      if (start < fwidth)
      {
        njobs++;
      }
      smsa = new SequenceI[njobs][];
      start = 0;
      int j = 0;
      for (int contig = 0; contig < contigs.length; contig += 3)
      {
        if (contigs[contig + 1] - start > 0)
        {
          SequenceI mseq[] = new SequenceI[sequences.length];
          for (int s = 0; s < mseq.length; s++)
          {
            mseq[s] = sequences[s].getSeq(gapCharacter).getSubSequence(
                    start, contigs[contig + 1]);
          }
          smsa[j] = mseq;
          j++;
        }
        start = contigs[contig + 1] + contigs[contig + 2];
      }
      if (start < fwidth)
      {
        SequenceI mseq[] = new SequenceI[sequences.length];
        for (int s = 0; s < mseq.length; s++)
        {
          mseq[s] = sequences[s].getSeq(gapCharacter).getSubSequence(start,
                  fwidth + 1);
        }
        smsa[j] = mseq;
        j++;
      }
    }
    else
    {
      smsa = new SequenceI[1][];
      smsa[0] = new SequenceI[sequences.length];
      for (int s = 0; s < sequences.length; s++)
      {
        smsa[0][s] = sequences[s].getSeq(gapCharacter);
      }
    }
    return smsa;
  }

  /**
   * return full msa and hidden regions with visible blocks replaced with new
   * sub alignments
   * 
   * @param nvismsa
   *          SequenceI[][]
   * @param orders
   *          AlignmentOrder[] corresponding to each SequenceI[] block.
   * @return Object[]
   */
  public Object[] getUpdatedView(SequenceI[][] nvismsa,
          AlignmentOrder[] orders, char gapCharacter)
  {
    if (sequences == null || width <= 0)
    {
      throw new Error("empty view cannot be updated.");
    }
    if (nvismsa == null)
    {
      throw new Error(
              "nvismsa==null. use getAlignmentAndColumnSelection() instead.");
    }
    if (contigs != null && contigs.length > 0)
    {
      SequenceI[] alignment = new SequenceI[sequences.length];
      ColumnSelection columnselection = new ColumnSelection();
      if (contigs != null && contigs.length > 0)
      {
        int start = 0;
        int nwidth = 0;
        int owidth = width;
        int j = 0;
        for (int contig = 0; contig < contigs.length; contig += 3)
        {
          owidth += contigs[contig + 2]; // recover final column width
          if (contigs[contig + 1] - start > 0)
          {
            int swidth = 0; // subalignment width
            if (nvismsa[j] != null)
            {
              SequenceI mseq[] = nvismsa[j];
              AlignmentOrder order = (orders == null) ? null : orders[j];
              j++;
              if (mseq.length != sequences.length)
              {
                throw new Error(
                        "Mismatch between number of sequences in block "
                                + j + " (" + mseq.length
                                + ") and the original view ("
                                + sequences.length + ")");
              }
              swidth = mseq[0].getLength(); // JBPNote: could ensure padded
              // here.
              for (int s = 0; s < mseq.length; s++)
              {
                if (alignment[s] == null)
                {
                  alignment[s] = mseq[s];
                }
                else
                {
                  alignment[s].setSequence(alignment[s]
                          .getSequenceAsString()
                          + mseq[s].getSequenceAsString());
                  if (mseq[s].getStart() <= mseq[s].getEnd())
                  {
                    alignment[s].setEnd(mseq[s].getEnd());
                  }
                  if (order != null)
                  {
                    order.updateSequence(mseq[s], alignment[s]);
                  }
                }
              }
            }
            else
            {
              // recover original alignment block or place gaps
              if (true)
              {
                // recover input data
                for (int s = 0; s < sequences.length; s++)
                {
                  SequenceI oseq = sequences[s].getSeq(gapCharacter)
                          .getSubSequence(start, contigs[contig + 1]);
                  if (swidth < oseq.getLength())
                  {
                    swidth = oseq.getLength();
                  }
                  if (alignment[s] == null)
                  {
                    alignment[s] = oseq;
                  }
                  else
                  {
                    alignment[s].setSequence(alignment[s]
                            .getSequenceAsString()
                            + oseq.getSequenceAsString());
                    if (oseq.getEnd() >= oseq.getStart())
                    {
                      alignment[s].setEnd(oseq.getEnd());
                    }
                  }
                }

              }
              j++;
            }
            nwidth += swidth;
          }
          // advance to begining of visible region
          start = contigs[contig + 1] + contigs[contig + 2];
          // add hidden segment to right of next region
          for (int s = 0; s < sequences.length; s++)
          {
            SequenceI hseq = sequences[s].getSeq(gapCharacter)
                    .getSubSequence(contigs[contig + 1], start);
            if (alignment[s] == null)
            {
              alignment[s] = hseq;
            }
            else
            {
              alignment[s].setSequence(alignment[s].getSequenceAsString()
                      + hseq.getSequenceAsString());
              if (hseq.getEnd() >= hseq.getStart())
              {
                alignment[s].setEnd(hseq.getEnd());
              }
            }
          }
          // mark hidden segment as hidden in the new alignment
          columnselection.hideColumns(nwidth, nwidth + contigs[contig + 2]
                  - 1);
          nwidth += contigs[contig + 2];
        }
        // Do final segment - if it exists
        if (j < nvismsa.length)
        {
          int swidth = 0;
          if (nvismsa[j] != null)
          {
            SequenceI mseq[] = nvismsa[j];
            AlignmentOrder order = (orders != null) ? orders[j] : null;
            swidth = mseq[0].getLength();
            for (int s = 0; s < mseq.length; s++)
            {
              if (alignment[s] == null)
              {
                alignment[s] = mseq[s];
              }
              else
              {
                alignment[s].setSequence(alignment[s].getSequenceAsString()
                        + mseq[s].getSequenceAsString());
                if (mseq[s].getEnd() >= mseq[s].getStart())
                {
                  alignment[s].setEnd(mseq[s].getEnd());
                }
                if (order != null)
                {
                  order.updateSequence(mseq[s], alignment[s]);
                }
              }
            }
          }
          else
          {
            if (start < owidth)
            {
              // recover input data or place gaps
              if (true)
              {
                // recover input data
                for (int s = 0; s < sequences.length; s++)
                {
                  SequenceI oseq = sequences[s].getSeq(gapCharacter)
                          .getSubSequence(start, owidth + 1);
                  if (swidth < oseq.getLength())
                  {
                    swidth = oseq.getLength();
                  }
                  if (alignment[s] == null)
                  {
                    alignment[s] = oseq;
                  }
                  else
                  {
                    alignment[s].setSequence(alignment[s]
                            .getSequenceAsString()
                            + oseq.getSequenceAsString());
                    if (oseq.getEnd() >= oseq.getStart())
                    {
                      alignment[s].setEnd(oseq.getEnd());
                    }
                  }
                }
                nwidth += swidth;
              }
              else
              {
                // place gaps.
                throw new Error("Padding not yet implemented.");
              }
            }
          }
        }
      }
      return new Object[]
      { alignment, columnselection };
    }
    else
    {
      if (nvismsa.length != 1)
      {
        throw new Error(
                "Mismatch between visible blocks to update and number of contigs in view (contigs=0,blocks="
                        + nvismsa.length);
      }
      if (nvismsa[0] != null)
      {
        return new Object[]
        { nvismsa[0], new ColumnSelection() };
      }
      else
      {
        return getAlignmentAndColumnSelection(gapCharacter);
      }
    }
  }

  /**
   * returns simple array of start end positions of visible range on alignment.
   * vis_start and vis_end are inclusive - use
   * SequenceI.getSubSequence(vis_start, vis_end+1) to recover visible sequence
   * from underlying alignment.
   * 
   * @return int[] { start_i, end_i } for 1<i<n visible regions.
   */
  public int[] getVisibleContigs()
  {
    if (contigs != null && contigs.length > 0)
    {
      int start = 0;
      int nvis = 0;
      int fwidth = width;
      for (int contig = 0; contig < contigs.length; contig += 3)
      {
        if ((contigs[contig + 1] - start) > 0)
        {
          nvis++;
        }
        fwidth += contigs[contig + 2]; // end up with full region width
        // (including hidden regions)
        start = contigs[contig + 1] + contigs[contig + 2];
      }
      if (start < fwidth)
      {
        nvis++;
      }
      int viscontigs[] = new int[nvis * 2];
      nvis = 0;
      start = 0;
      for (int contig = 0; contig < contigs.length; contig += 3)
      {
        if ((contigs[contig + 1] - start) > 0)
        {
          viscontigs[nvis] = start;
          viscontigs[nvis + 1] = contigs[contig + 1] - 1; // end is inclusive
          nvis += 2;
        }
        start = contigs[contig + 1] + contigs[contig + 2];
      }
      if (start < fwidth)
      {
        viscontigs[nvis] = start;
        viscontigs[nvis + 1] = fwidth; // end is inclusive
        nvis += 2;
      }
      return viscontigs;
    }
    else
    {
      return new int[]
      { 0, width };
    }
  }

  /**
   * 
   * @return position of first visible column of AlignmentView within its
   *         parent's alignment reference frame
   */
  public int getAlignmentOrigin()
  {
    return firstCol;
  }

  /**
   * compute a deletion map for the current view according to the given
   * gap/match map
   * 
   * @param gapMap
   *          (as returned from SequenceI.gapMap())
   * @return int[] {intersection of visible regions with gapMap)
   */
  public int[] getVisibleContigMapFor(int[] gapMap)
  {
    int[] delMap = null;
    int[] viscontigs = getVisibleContigs();
    int spos = 0;
    int i = 0;
    if (viscontigs != null)
    {
      // viscontigs maps from a subset of the gapMap to the gapMap, so it will
      // always be equal to or shorter than gapMap
      delMap = new int[gapMap.length];
      for (int contig = 0; contig < viscontigs.length; contig += 2)
      {

        while (spos < gapMap.length && gapMap[spos] < viscontigs[contig])
        {
          spos++;
        }
        while (spos < gapMap.length
                && gapMap[spos] <= viscontigs[contig + 1])
        {
          delMap[i++] = spos++;
        }
      }
      int tmap[] = new int[i];
      System.arraycopy(delMap, 0, tmap, 0, i);
      delMap = tmap;
    }
    return delMap;
  }

  /**
   * apply the getSeq(gc) method to each sequence cigar, and return the array of
   * edited sequences, optionally with hidden regions removed.
   * 
   * @param gc
   *          gap character to use for insertions
   * @param delete
   *          remove hidden regions from sequences. Note: currently implemented
   *          in a memory inefficient way - space needed is 2*result set for
   *          deletion
   * 
   * @return SequenceI[]
   */
  public SequenceI[] getEditedSequences(char gc, boolean delete)
  {
    SeqCigar[] msf = getSequences();
    SequenceI[] aln = new SequenceI[msf.length];
    for (int i = 0, j = msf.length; i < j; i++)
    {
      aln[i] = msf[i].getSeq(gc);
    }
    if (delete)
    {
      String[] sqs = getSequenceStrings(gc);
      for (int i = 0; i < sqs.length; i++)
      {
        aln[i].setSequence(sqs[i]);
        sqs[i] = null;
      }
    }
    return aln;
  }

  public static void summariseAlignmentView(AlignmentView view,
          PrintStream os)
  {
    os.print("View has " + view.sequences.length + " of which ");
    if (view.selected == null)
    {
      os.print("None");
    }
    else
    {
      os.print(" " + view.selected.size());
    }
    os.println(" are selected.");
    os.print("View is " + view.getWidth() + " columns wide");
    int viswid = 0;
    int[] contigs = view.getContigs();
    if (contigs != null)
    {
      viswid = view.width;
      for (int i = 0; i < contigs.length; i += 3)
      {
        viswid += contigs[i + 2];
      }
      os.println("with " + viswid + " visible columns spread over "
              + contigs.length / 3 + " regions.");
    }
    else
    {
      viswid = view.width;
      os.println(".");
    }
    if (view.scGroups != null)
    {
      os.println("There are " + view.scGroups.size()
              + " groups defined on the view.");
      for (int g = 0; g < view.scGroups.size(); g++)
      {
        ScGroup sgr = (ScGroup) view.scGroups.elementAt(g);
        os.println("Group " + g + ": Name = " + sgr.sg.getName()
                + " Contains " + sgr.seqs.size() + " Seqs.");
        os.println("This group runs from " + sgr.sg.getStartRes() + " to "
                + sgr.sg.getEndRes());
        for (int s = 0; s < sgr.seqs.size(); s++)
        {
          if (!((SeqCigar) sgr.seqs.elementAt(s)).isMemberOf(sgr))
          {
            os.println("** WARNING: sequence "
                    + ((SeqCigar) sgr.seqs.elementAt(s)).toString()
                    + " is not marked as member of group.");
          }
        }
      }
      AlignmentI visal = view.getVisibleAlignment('-');
      if (visal != null)
      {
        os.println("Vis. alignment is " + visal.getWidth()
                + " wide and has " + visal.getHeight() + " seqs.");
        if (visal.getGroups() != null && visal.getGroups().size() > 0)
        {

          int i = 1;
          for (SequenceGroup sg : visal.getGroups())
          {
            os.println("Group " + (i++) + " begins at column "
                    + sg.getStartRes() + " and ends at " + sg.getEndRes());
          }
        }
      }
    }
  }

  public static void testSelectionViews(AlignmentI alignment,
          ColumnSelection csel, SequenceGroup selection)
  {
    System.out.println("Testing standard view creation:\n");
    AlignmentView view = null;
    try
    {
      System.out
              .println("View with no hidden columns, no limit to selection, no groups to be collected:");
      view = new AlignmentView(alignment, csel, selection, false, false,
              false);
      summariseAlignmentView(view, System.out);

    } catch (Exception e)
    {
      e.printStackTrace();
      System.err
              .println("Failed to generate alignment with selection but no groups marked.");
    }
    try
    {
      System.out
              .println("View with no hidden columns, no limit to selection, and all groups to be collected:");
      view = new AlignmentView(alignment, csel, selection, false, false,
              true);
      summariseAlignmentView(view, System.out);
    } catch (Exception e)
    {
      e.printStackTrace();
      System.err
              .println("Failed to generate alignment with selection marked but no groups marked.");
    }
    try
    {
      System.out
              .println("View with no hidden columns, limited to selection and no groups to be collected:");
      view = new AlignmentView(alignment, csel, selection, false, true,
              false);
      summariseAlignmentView(view, System.out);
    } catch (Exception e)
    {
      e.printStackTrace();
      System.err
              .println("Failed to generate alignment with selection restricted but no groups marked.");
    }
    try
    {
      System.out
              .println("View with no hidden columns, limited to selection, and all groups to be collected:");
      view = new AlignmentView(alignment, csel, selection, false, true,
              true);
      summariseAlignmentView(view, System.out);
    } catch (Exception e)
    {
      e.printStackTrace();
      System.err
              .println("Failed to generate alignment with selection restricted and groups marked.");
    }
    try
    {
      System.out
              .println("View *with* hidden columns, no limit to selection, no groups to be collected:");
      view = new AlignmentView(alignment, csel, selection, true, false,
              false);
      summariseAlignmentView(view, System.out);
    } catch (Exception e)
    {
      e.printStackTrace();
      System.err
              .println("Failed to generate alignment with selection but no groups marked.");
    }
    try
    {
      System.out
              .println("View *with* hidden columns, no limit to selection, and all groups to be collected:");
      view = new AlignmentView(alignment, csel, selection, true, false,
              true);
      summariseAlignmentView(view, System.out);
    } catch (Exception e)
    {
      e.printStackTrace();
      System.err
              .println("Failed to generate alignment with selection marked but no groups marked.");
    }
    try
    {
      System.out
              .println("View *with* hidden columns, limited to selection and no groups to be collected:");
      view = new AlignmentView(alignment, csel, selection, true, true,
              false);
      summariseAlignmentView(view, System.out);
    } catch (Exception e)
    {
      e.printStackTrace();
      System.err
              .println("Failed to generate alignment with selection restricted but no groups marked.");
    }
    try
    {
      System.out
              .println("View *with* hidden columns, limited to selection, and all groups to be collected:");
      view = new AlignmentView(alignment, csel, selection, true, true, true);
      summariseAlignmentView(view, System.out);
    } catch (Exception e)
    {
      e.printStackTrace();
      System.err
              .println("Failed to generate alignment with selection restricted and groups marked.");
    }

  }
}
