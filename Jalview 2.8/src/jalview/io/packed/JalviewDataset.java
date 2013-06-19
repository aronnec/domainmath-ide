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

package jalview.io.packed;

import jalview.datamodel.AlignmentI;
import jalview.datamodel.SequenceI;
import jalview.io.NewickFile;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class JalviewDataset
{
  /**
   * dataset that new data (sequences, alignments) will be added to
   */
  AlignmentI parentDataset;

  /**
   * @return the parentDataset
   */
  public AlignmentI getParentDataset()
  {
    return parentDataset;
  }

  /**
   * @param parentDataset
   *          the parentDataset to set
   */
  public void setParentDataset(AlignmentI parentDataset)
  {
    this.parentDataset = parentDataset;
  }

  /**
   * @return the featureColours
   */
  public Hashtable getFeatureColours()
  {
    return featureColours;
  }

  /**
   * @param featureColours
   *          the featureColours to set
   */
  public void setFeatureColours(Hashtable featureColours)
  {
    this.featureColours = featureColours;
  }

  /**
   * @return the seqDetails
   */
  public Hashtable getSeqDetails()
  {
    return seqDetails;
  }

  /**
   * @param seqDetails
   *          the seqDetails to set
   */
  public void setSeqDetails(Hashtable seqDetails)
  {
    this.seqDetails = seqDetails;
  }

  /**
   * @return the al
   */
  public List<AlignmentSet> getAl()
  {
    return (al == null) ? new ArrayList<AlignmentSet>() : al;
  }

  /**
   * current alignment being worked on.
   */
  List<AlignmentSet> al;

  public class AlignmentSet
  {
    public AlignmentI al;

    public List<jalview.io.NewickFile> trees;

    AlignmentSet(AlignmentI a)
    {
      al = a;
      trees = new ArrayList<jalview.io.NewickFile>();
    }

    /**
     * deuniquify the current alignment in the context, merging any new
     * annotation/features with the existing set
     * 
     * @param context
     */
    void deuniquifyAlignment()
    {
      if (seqDetails == null || seqDetails.size() == 0)
      {
        // nothing to do
        return;
      }
      // 1. recover correct names and attributes for each sequence in alignment.
      /*
       * TODO: housekeeping w.r.t. recovery of dataset and annotation references
       * for input sequences, and then dataset sequence creation for new
       * sequences retrieved from service // finally, attempt to de-uniquify to
       * recover input sequence identity, and try to map back onto dataset Note:
       * this jalview.analysis.SeqsetUtils.deuniquify(SeqNames, alseqs, true);
       * will NOT WORK - the returned alignment may contain multiple versions of
       * the input sequence, each being a subsequence of the original.
       * deuniquify also removes existing annotation and features added in the
       * previous step... al.setDataset(dataset); // add in new sequences
       * retrieved from sequence search which are not already in dataset. //
       * trigger a 'fetchDBids' to annotate sequences with database ids...
       */
      // jalview.analysis.SeqsetUtils.deuniquifyAndMerge(parentDataset,
      // seqDetails, al,true);

      jalview.analysis.SeqsetUtils.deuniquify(seqDetails,
              al.getSequencesArray(), true);
      // 2. Update names of associated nodes in any trees
      for (NewickFile nf : trees)
      {
        // the following works because all trees are already had node/SequenceI
        // associations created.
        jalview.analysis.NJTree njt = new jalview.analysis.NJTree(
                al.getSequencesArray(), nf);
        // this just updates the displayed leaf name on the tree according to
        // the SequenceIs.
        njt.renameAssociatedNodes();
      }

    }

    /**
     * set modification flag. If anything modifies the alignment in the current
     * set, this flag should be true
     */
    private boolean modified = false;

    /**
     * @return the modified
     */
    public boolean isModified()
    {
      return modified;
    }

    /**
     * or the modification state with the given state
     * 
     * @param modifiedFromAction
     */
    public void updateSetModified(boolean modifiedFromAction)
    {
      modified |= modifiedFromAction;
    }
  }

  /**
   * current set of feature colours
   */
  Hashtable featureColours;

  /**
   * original identity of each sequence in results
   */
  Hashtable seqDetails;

  public boolean relaxedIdMatching = false;

  public JalviewDataset()
  {
    seqDetails = new Hashtable();
    al = new ArrayList<AlignmentSet>();
    parentDataset = null;
    featureColours = new Hashtable();
  }

  /**
   * context created from an existing alignment.
   * 
   * @param parentAlignment
   */
  public JalviewDataset(AlignmentI aldataset, Hashtable fc,
          Hashtable seqDets)
  {
    this(aldataset, fc, seqDets, null);
  }

  /**
   * 
   * @param aldataset
   *          - parent dataset for any new alignment/sequence data (must not be
   *          null)
   * @param fc
   *          (may be null) feature settings for the alignment where new feature
   *          renderstyles are stored
   * @param seqDets
   *          - (may be null) anonymised sequence information created by
   *          Sequence uniquifier
   * @param parentAlignment
   *          (may be null) alignment to associate new annotation and trees
   *          with.
   */
  public JalviewDataset(AlignmentI aldataset, Hashtable fc,
          Hashtable seqDets, AlignmentI parentAlignment)
  {
    this();
    parentDataset = aldataset;
    if (parentAlignment != null)
    {
      parentDataset = parentAlignment.getDataset();
      if (parentDataset == null)
      {
        parentDataset = parentAlignment;
      }
      else
      {
        addAlignment(parentAlignment);
      }
    }
    if (seqDets != null)
    {
      seqDetails = seqDets;
    }
    if (fc != null)
    {
      featureColours = fc;
    }

  }

  public boolean hasAlignments()
  {
    return al != null && al.size() > 0;
  }

  public AlignmentI getLastAlignment()
  {
    return (al == null || al.size() < 1) ? null : al.get(al.size() - 1).al;
  }

  public AlignmentSet getLastAlignmentSet()
  {
    return (al == null || al.size() < 1) ? null : al.get(al.size() - 1);
  }

  /**
   * post process (deuniquify) the current alignment and its dependent data, and
   * then add newal to the dataset.
   * 
   * @param newal
   */
  public void addAlignment(AlignmentI newal)
  {
    if (!hasAlignments())
    {
      al = new ArrayList<AlignmentSet>();
    }
    AlignmentSet last = getLastAlignmentSet();
    if (last != null)
    {
      System.err.println("Deuniquifying last alignment set.");
      last.deuniquifyAlignment();
    }
    al.add(new AlignmentSet(newal));
  }

  public void addTreeFromFile(NewickFile nf)
  {
    AlignmentSet lal = getLastAlignmentSet();
    lal.trees.add(nf);
  }

  public boolean hasSequenceAssoc()
  {
    // TODO: discover where sequence associated data should be put.
    return false;
  }

  public SequenceI getLastAssociatedSequence()
  {
    // TODO: delineate semantics for associating uniquified data with
    // potentially de-uniquified sequence.
    return null;
  }

  /**
   * update the modified state flag for the current set with the given
   * modification state
   * 
   * @param modified
   *          - this will be ored with current modification state
   */
  public void updateSetModified(boolean modified)
  {
    getLastAlignmentSet().updateSetModified(modified);
  }
}
