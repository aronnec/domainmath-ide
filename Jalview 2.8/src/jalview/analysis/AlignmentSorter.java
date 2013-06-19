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
package jalview.analysis;

import java.util.*;

import jalview.datamodel.*;
import jalview.util.*;

/**
 * Routines for manipulating the order of a multiple sequence alignment TODO:
 * this class retains some global states concerning sort-order which should be
 * made attributes for the caller's alignment visualization. TODO: refactor to
 * allow a subset of selected sequences to be sorted within the context of a
 * whole alignment. Sort method template is: SequenceI[] tobesorted, [ input
 * data mapping to each tobesorted element to use ], Alignment context of
 * tobesorted that are to be re-ordered, boolean sortinplace, [special data - ie
 * seuqence to be sorted w.r.t.]) sortinplace implies that the sorted vector
 * resulting from applying the operation to tobesorted should be mapped back to
 * the original positions in alignment. Otherwise, normal behaviour is to re
 * order alignment so that tobesorted is sorted and grouped together starting
 * from the first tobesorted position in the alignment. e.g. (a,tb2,b,tb1,c,tb3
 * becomes a,tb1,tb2,tb3,b,c)
 */
public class AlignmentSorter
{
  /**
   * todo: refactor searches to follow a basic pattern: (search property, last
   * search state, current sort direction)
   */
  static boolean sortIdAscending = true;

  static int lastGroupHash = 0;

  static boolean sortGroupAscending = true;

  static AlignmentOrder lastOrder = null;

  static boolean sortOrderAscending = true;

  static NJTree lastTree = null;

  static boolean sortTreeAscending = true;

  /**
   * last Annotation Label used by sortByScore
   */
  private static String lastSortByScore;

  private static boolean sortByScoreAscending = true;

  /**
   * compact representation of last arguments to SortByFeatureScore
   */
  private static String lastSortByFeatureScore;

  private static boolean sortByFeatureScoreAscending = true;

  private static boolean sortLengthAscending;

  /**
   * Sort by Percentage Identity w.r.t. s
   * 
   * @param align
   *          AlignmentI
   * @param s
   *          SequenceI
   * @param tosort
   *          sequences from align that are to be sorted.
   */
  public static void sortByPID(AlignmentI align, SequenceI s,
          SequenceI[] tosort)
  {
    sortByPID(align, s, tosort, 0, -1);
  }

  /**
   * Sort by Percentage Identity w.r.t. s
   * 
   * @param align
   *          AlignmentI
   * @param s
   *          SequenceI
   * @param tosort
   *          sequences from align that are to be sorted.
   * @param start
   *          start column (0 for beginning
   * @param end
   */
  public static void sortByPID(AlignmentI align, SequenceI s,
          SequenceI[] tosort, int start, int end)
  {
    int nSeq = align.getHeight();

    float[] scores = new float[nSeq];
    SequenceI[] seqs = new SequenceI[nSeq];

    for (int i = 0; i < nSeq; i++)
    {
      scores[i] = Comparison.PID(align.getSequenceAt(i)
              .getSequenceAsString(), s.getSequenceAsString());
      seqs[i] = align.getSequenceAt(i);
    }

    QuickSort.sort(scores, 0, scores.length - 1, seqs);

    setReverseOrder(align, seqs);
  }

  /**
   * Reverse the order of the sort
   * 
   * @param align
   *          DOCUMENT ME!
   * @param seqs
   *          DOCUMENT ME!
   */
  private static void setReverseOrder(AlignmentI align, SequenceI[] seqs)
  {
    int nSeq = seqs.length;

    int len = 0;

    if ((nSeq % 2) == 0)
    {
      len = nSeq / 2;
    }
    else
    {
      len = (nSeq + 1) / 2;
    }

    // NOTE: DO NOT USE align.setSequenceAt() here - it will NOT work
    List<SequenceI> asq;
    synchronized (asq = align.getSequences())
    {
      for (int i = 0; i < len; i++)
      {
        // SequenceI tmp = seqs[i];
        asq.set(i, seqs[nSeq - i - 1]);
        asq.set(nSeq - i - 1, seqs[i]);
      }
    }
  }

  /**
   * Sets the Alignment object with the given sequences
   * 
   * @param align
   *          Alignment object to be updated
   * @param tmp
   *          sequences as a vector
   */
  private static void setOrder(AlignmentI align, Vector tmp)
  {
    setOrder(align, vectorSubsetToArray(tmp, align.getSequences()));
  }

  /**
   * Sets the Alignment object with the given sequences
   * 
   * @param align
   *          DOCUMENT ME!
   * @param seqs
   *          sequences as an array
   */
  public static void setOrder(AlignmentI align, SequenceI[] seqs)
  {
    // NOTE: DO NOT USE align.setSequenceAt() here - it will NOT work
    List<SequenceI> algn;
    synchronized (algn = align.getSequences())
    {
      List<SequenceI> tmp = new ArrayList<SequenceI>();

      for (int i = 0; i < seqs.length; i++)
      {
        if (algn.contains(seqs[i]))
        {
          tmp.add(seqs[i]);
        }
      }

      algn.clear();
      // User may have hidden seqs, then clicked undo or redo
      for (int i = 0; i < tmp.size(); i++)
      {
        algn.add(tmp.get(i));
      }
    }
  }

  /**
   * Sorts by ID. Numbers are sorted before letters.
   * 
   * @param align
   *          The alignment object to sort
   */
  public static void sortByID(AlignmentI align)
  {
    int nSeq = align.getHeight();

    String[] ids = new String[nSeq];
    SequenceI[] seqs = new SequenceI[nSeq];

    for (int i = 0; i < nSeq; i++)
    {
      ids[i] = align.getSequenceAt(i).getName();
      seqs[i] = align.getSequenceAt(i);
    }

    QuickSort.sort(ids, seqs);

    if (sortIdAscending)
    {
      setReverseOrder(align, seqs);
    }
    else
    {
      setOrder(align, seqs);
    }

    sortIdAscending = !sortIdAscending;
  }

  /**
   * Sorts by sequence length
   * 
   * @param align
   *          The alignment object to sort
   */
  public static void sortByLength(AlignmentI align)
  {
    int nSeq = align.getHeight();

    float[] length = new float[nSeq];
    SequenceI[] seqs = new SequenceI[nSeq];

    for (int i = 0; i < nSeq; i++)
    {
      seqs[i] = align.getSequenceAt(i);
      length[i] = (seqs[i].getEnd() - seqs[i].getStart());
    }

    QuickSort.sort(length, seqs);

    if (sortLengthAscending)
    {
      setReverseOrder(align, seqs);
    }
    else
    {
      setOrder(align, seqs);
    }

    sortLengthAscending = !sortLengthAscending;
  }

  /**
   * Sorts the alignment by size of group. <br>
   * Maintains the order of sequences in each group by order in given alignment
   * object.
   * 
   * @param align
   *          sorts the given alignment object by group
   */
  public static void sortByGroup(AlignmentI align)
  {
    // MAINTAINS ORIGNAL SEQUENCE ORDER,
    // ORDERS BY GROUP SIZE
    Vector groups = new Vector();

    if (groups.hashCode() != lastGroupHash)
    {
      sortGroupAscending = true;
      lastGroupHash = groups.hashCode();
    }
    else
    {
      sortGroupAscending = !sortGroupAscending;
    }

    // SORTS GROUPS BY SIZE
    // ////////////////////
    for (SequenceGroup sg : align.getGroups())
    {
      for (int j = 0; j < groups.size(); j++)
      {
        SequenceGroup sg2 = (SequenceGroup) groups.elementAt(j);

        if (sg.getSize() > sg2.getSize())
        {
          groups.insertElementAt(sg, j);

          break;
        }
      }

      if (!groups.contains(sg))
      {
        groups.addElement(sg);
      }
    }

    // NOW ADD SEQUENCES MAINTAINING ALIGNMENT ORDER
    // /////////////////////////////////////////////
    Vector seqs = new Vector();

    for (int i = 0; i < groups.size(); i++)
    {
      SequenceGroup sg = (SequenceGroup) groups.elementAt(i);
      SequenceI[] orderedseqs = sg.getSequencesInOrder(align);

      for (int j = 0; j < orderedseqs.length; j++)
      {
        seqs.addElement(orderedseqs[j]);
      }
    }

    if (sortGroupAscending)
    {
      setOrder(align, seqs);
    }
    else
    {
      setReverseOrder(align,
              vectorSubsetToArray(seqs, align.getSequences()));
    }
  }

  /**
   * Converts Vector to array. java 1.18 does not have Vector.toArray()
   * 
   * @param tmp
   *          Vector of SequenceI objects
   * 
   * @return array of Sequence[]
   */
  private static SequenceI[] vectorToArray(Vector tmp)
  {
    SequenceI[] seqs = new SequenceI[tmp.size()];

    for (int i = 0; i < tmp.size(); i++)
    {
      seqs[i] = (SequenceI) tmp.elementAt(i);
    }

    return seqs;
  }

  /**
   * Select sequences in order from tmp that is present in mask, and any
   * remaining seqeunces in mask not in tmp
   * 
   * @param tmp
   *          thread safe collection of sequences
   * @param mask
   *          thread safe collection of sequences
   * 
   * @return intersect(tmp,mask)+intersect(complement(tmp),mask)
   */
  private static SequenceI[] vectorSubsetToArray(List<SequenceI> tmp,
          List<SequenceI> mask)
  {
    ArrayList<SequenceI> seqs = new ArrayList<SequenceI>();
    int i, idx;
    boolean[] tmask = new boolean[mask.size()];

    for (i = 0; i < mask.size(); i++)
    {
      tmask[i] = true;
    }

    for (i = 0; i < tmp.size(); i++)
    {
      SequenceI sq = tmp.get(i);
      idx = mask.indexOf(sq);
      if (idx > -1 && tmask[idx])
      {
        tmask[idx] = false;
        seqs.add(sq);
      }
    }

    for (i = 0; i < tmask.length; i++)
    {
      if (tmask[i])
      {
        seqs.add(mask.get(i));
      }
    }

    return seqs.toArray(new SequenceI[seqs.size()]);
  }

  /**
   * Sorts by a given AlignmentOrder object
   * 
   * @param align
   *          Alignment to order
   * @param order
   *          specified order for alignment
   */
  public static void sortBy(AlignmentI align, AlignmentOrder order)
  {
    // Get an ordered vector of sequences which may also be present in align
    Vector tmp = order.getOrder();

    if (lastOrder == order)
    {
      sortOrderAscending = !sortOrderAscending;
    }
    else
    {
      sortOrderAscending = true;
    }

    if (sortOrderAscending)
    {
      setOrder(align, tmp);
    }
    else
    {
      setReverseOrder(align, vectorSubsetToArray(tmp, align.getSequences()));
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param align
   *          alignment to order
   * @param tree
   *          tree which has
   * 
   * @return DOCUMENT ME!
   */
  private static Vector getOrderByTree(AlignmentI align, NJTree tree)
  {
    int nSeq = align.getHeight();

    Vector tmp = new Vector();

    tmp = _sortByTree(tree.getTopNode(), tmp, align.getSequences());

    if (tmp.size() != nSeq)
    {
      // TODO: JBPNote - decide if this is always an error
      // (eg. not when a tree is associated to another alignment which has more
      // sequences)
      if (tmp.size() != nSeq)
      {
        addStrays(align, tmp);
      }

      if (tmp.size() != nSeq)
      {
        System.err
                .println("WARNING: tmp.size()="
                        + tmp.size()
                        + " != nseq="
                        + nSeq
                        + " in getOrderByTree - tree contains sequences not in alignment");
      }
    }

    return tmp;
  }

  /**
   * Sorts the alignment by a given tree
   * 
   * @param align
   *          alignment to order
   * @param tree
   *          tree which has
   */
  public static void sortByTree(AlignmentI align, NJTree tree)
  {
    Vector tmp = getOrderByTree(align, tree);

    // tmp should properly permute align with tree.
    if (lastTree != tree)
    {
      sortTreeAscending = true;
      lastTree = tree;
    }
    else
    {
      sortTreeAscending = !sortTreeAscending;
    }

    if (sortTreeAscending)
    {
      setOrder(align, tmp);
    }
    else
    {
      setReverseOrder(align, vectorSubsetToArray(tmp, align.getSequences()));
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param align
   *          DOCUMENT ME!
   * @param seqs
   *          DOCUMENT ME!
   */
  private static void addStrays(AlignmentI align, Vector seqs)
  {
    int nSeq = align.getHeight();

    for (int i = 0; i < nSeq; i++)
    {
      if (!seqs.contains(align.getSequenceAt(i)))
      {
        seqs.addElement(align.getSequenceAt(i));
      }
    }

    if (nSeq != seqs.size())
    {
      System.err
              .println("ERROR: Size still not right even after addStrays");
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param node
   *          DOCUMENT ME!
   * @param tmp
   *          DOCUMENT ME!
   * @param seqset
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  private static Vector _sortByTree(SequenceNode node, Vector tmp,
          List<SequenceI> seqset)
  {
    if (node == null)
    {
      return tmp;
    }

    SequenceNode left = (SequenceNode) node.left();
    SequenceNode right = (SequenceNode) node.right();

    if ((left == null) && (right == null))
    {
      if (!node.isPlaceholder() && (node.element() != null))
      {
        if (node.element() instanceof SequenceI)
        {
          if (!tmp.contains(node.element())) // && (seqset==null ||
                                             // seqset.size()==0 ||
                                             // seqset.contains(tmp)))
          {
            tmp.addElement(node.element());
          }
        }
      }

      return tmp;
    }
    else
    {
      _sortByTree(left, tmp, seqset);
      _sortByTree(right, tmp, seqset);
    }

    return tmp;
  }

  // Ordering Objects
  // Alignment.sortBy(OrderObj) - sequence of sequence pointer refs in
  // appropriate order
  //

  /**
   * recover the order of sequences given by the safe numbering scheme introducd
   * SeqsetUtils.uniquify.
   */
  public static void recoverOrder(SequenceI[] alignment)
  {
    float[] ids = new float[alignment.length];

    for (int i = 0; i < alignment.length; i++)
    {
      ids[i] = (new Float(alignment[i].getName().substring(8)))
              .floatValue();
    }

    jalview.util.QuickSort.sort(ids, alignment);
  }

  /**
   * Sort sequence in order of increasing score attribute for annotation with a
   * particular scoreLabel. Or reverse if same label was used previously
   * 
   * @param scoreLabel
   *          exact label for sequence associated AlignmentAnnotation scores to
   *          use for sorting.
   * @param alignment
   *          sequences to be sorted
   */
  public static void sortByAnnotationScore(String scoreLabel,
          AlignmentI alignment)
  {
    SequenceI[] seqs = alignment.getSequencesArray();
    boolean[] hasScore = new boolean[seqs.length]; // per sequence score
    // presence
    int hasScores = 0; // number of scores present on set
    double[] scores = new double[seqs.length];
    double min = 0, max = 0;
    for (int i = 0; i < seqs.length; i++)
    {
      AlignmentAnnotation[] scoreAnn = seqs[i].getAnnotation(scoreLabel);
      if (scoreAnn != null)
      {
        hasScores++;
        hasScore[i] = true;
        scores[i] = scoreAnn[0].getScore(); // take the first instance of this
        // score.
        if (hasScores == 1)
        {
          max = min = scores[i];
        }
        else
        {
          if (max < scores[i])
          {
            max = scores[i];
          }
          if (min > scores[i])
          {
            min = scores[i];
          }
        }
      }
      else
      {
        hasScore[i] = false;
      }
    }
    if (hasScores == 0)
    {
      return; // do nothing - no scores present to sort by.
    }
    if (hasScores < seqs.length)
    {
      for (int i = 0; i < seqs.length; i++)
      {
        if (!hasScore[i])
        {
          scores[i] = (max + i + 1.0);
        }
      }
    }

    jalview.util.QuickSort.sort(scores, seqs);
    if (lastSortByScore != scoreLabel)
    {
      lastSortByScore = scoreLabel;
      setOrder(alignment, seqs);
    }
    else
    {
      setReverseOrder(alignment, seqs);
    }
  }

  /**
   * types of feature ordering: Sort by score : average score - or total score -
   * over all features in region Sort by feature label text: (or if null -
   * feature type text) - numerical or alphabetical Sort by feature density:
   * based on counts - ignoring individual text or scores for each feature
   */
  public static String FEATURE_SCORE = "average_score";

  public static String FEATURE_LABEL = "text";

  public static String FEATURE_DENSITY = "density";

  /**
   * sort the alignment using the features on each sequence found between start
   * and stop with the given featureLabel (and optional group qualifier)
   * 
   * @param featureLabel
   *          (may not be null)
   * @param groupLabel
   *          (may be null)
   * @param start
   *          (-1 to include non-positional features)
   * @param stop
   *          (-1 to only sort on non-positional features)
   * @param alignment
   *          - aligned sequences containing features
   * @param method
   *          - one of the string constants FEATURE_SCORE, FEATURE_LABEL,
   *          FEATURE_DENSITY
   */
  public static void sortByFeature(String featureLabel, String groupLabel,
          int start, int stop, AlignmentI alignment, String method)
  {
    sortByFeature(featureLabel == null ? null : new String[]
    { featureLabel }, groupLabel == null ? null : new String[]
    { groupLabel }, start, stop, alignment, method);
  }

  private static boolean containsIgnoreCase(final String lab,
          final String[] labs)
  {
    if (labs == null)
    {
      return true;
    }
    if (lab == null)
    {
      return false;
    }
    for (int q = 0; q < labs.length; q++)
    {
      if (labs[q] != null && lab.equalsIgnoreCase(labs[q]))
      {
        return true;
      }
    }
    return false;
  }

  public static void sortByFeature(String[] featureLabels,
          String[] groupLabels, int start, int stop, AlignmentI alignment,
          String method)
  {
    if (method != FEATURE_SCORE && method != FEATURE_LABEL
            && method != FEATURE_DENSITY)
    {
      throw new Error(
              "Implementation Error - sortByFeature method must be one of FEATURE_SCORE, FEATURE_LABEL or FEATURE_DENSITY.");
    }
    boolean ignoreScore = method != FEATURE_SCORE;
    StringBuffer scoreLabel = new StringBuffer();
    scoreLabel.append(start + stop + method);
    // This doesn't quite work yet - we'd like to have a canonical ordering that
    // can be preserved from call to call
    for (int i = 0; featureLabels != null && i < featureLabels.length; i++)
    {
      scoreLabel.append(featureLabels[i] == null ? "null"
              : featureLabels[i]);
    }
    for (int i = 0; groupLabels != null && i < groupLabels.length; i++)
    {
      scoreLabel.append(groupLabels[i] == null ? "null" : groupLabels[i]);
    }
    SequenceI[] seqs = alignment.getSequencesArray();

    boolean[] hasScore = new boolean[seqs.length]; // per sequence score
    // presence
    int hasScores = 0; // number of scores present on set
    double[] scores = new double[seqs.length];
    int[] seqScores = new int[seqs.length];
    Object[] feats = new Object[seqs.length];
    double min = 0, max = 0;
    for (int i = 0; i < seqs.length; i++)
    {
      SequenceFeature[] sf = seqs[i].getSequenceFeatures();
      if (sf == null && seqs[i].getDatasetSequence() != null)
      {
        sf = seqs[i].getDatasetSequence().getSequenceFeatures();
      }
      if (sf == null)
      {
        sf = new SequenceFeature[0];
      }
      else
      {
        SequenceFeature[] tmp = new SequenceFeature[sf.length];
        for (int s = 0; s < tmp.length; s++)
        {
          tmp[s] = sf[s];
        }
        sf = tmp;
      }
      int sstart = (start == -1) ? start : seqs[i].findPosition(start);
      int sstop = (stop == -1) ? stop : seqs[i].findPosition(stop);
      seqScores[i] = 0;
      scores[i] = 0.0;
      int n = sf.length;
      for (int f = 0; f < sf.length; f++)
      {
        // filter for selection criteria
        if (
        // ignore features outwith alignment start-stop positions.
        (sf[f].end < sstart || sf[f].begin > sstop) ||
        // or ignore based on selection criteria
                (featureLabels != null && !AlignmentSorter
                        .containsIgnoreCase(sf[f].type, featureLabels))
                || (groupLabels != null
                // problem here: we cannot eliminate null feature group features
                && (sf[f].getFeatureGroup() != null && !AlignmentSorter
                        .containsIgnoreCase(sf[f].getFeatureGroup(),
                                groupLabels))))
        {
          // forget about this feature
          sf[f] = null;
          n--;
        }
        else
        {
          // or, also take a look at the scores if necessary.
          if (!ignoreScore && sf[f].getScore() != Float.NaN)
          {
            if (seqScores[i] == 0)
            {
              hasScores++;
            }
            seqScores[i]++;
            hasScore[i] = true;
            scores[i] += sf[f].getScore(); // take the first instance of this
            // score.
          }
        }
      }
      SequenceFeature[] fs;
      feats[i] = fs = new SequenceFeature[n];
      if (n > 0)
      {
        n = 0;
        for (int f = 0; f < sf.length; f++)
        {
          if (sf[f] != null)
          {
            ((SequenceFeature[]) feats[i])[n++] = sf[f];
          }
        }
        if (method == FEATURE_LABEL)
        {
          // order the labels by alphabet
          String[] labs = new String[fs.length];
          for (int l = 0; l < labs.length; l++)
          {
            labs[l] = (fs[l].getDescription() != null ? fs[l]
                    .getDescription() : fs[l].getType());
          }
          jalview.util.QuickSort.sort(labs, ((Object[]) feats[i]));
        }
      }
      if (hasScore[i])
      {
        // compute average score
        scores[i] /= seqScores[i];
        // update the score bounds.
        if (hasScores == 1)
        {
          max = min = scores[i];
        }
        else
        {
          if (max < scores[i])
          {
            max = scores[i];
          }
          if (min > scores[i])
          {
            min = scores[i];
          }
        }
      }
    }

    if (method == FEATURE_SCORE)
    {
      if (hasScores == 0)
      {
        return; // do nothing - no scores present to sort by.
      }
      // pad score matrix
      if (hasScores < seqs.length)
      {
        for (int i = 0; i < seqs.length; i++)
        {
          if (!hasScore[i])
          {
            scores[i] = (max + 1 + i);
          }
          else
          {
            int nf = (feats[i] == null) ? 0
                    : ((SequenceFeature[]) feats[i]).length;
            // System.err.println("Sorting on Score: seq "+seqs[i].getName()+
            // " Feats: "+nf+" Score : "+scores[i]);
          }
        }
      }

      jalview.util.QuickSort.sort(scores, seqs);
    }
    else if (method == FEATURE_DENSITY)
    {

      // break ties between equivalent numbers for adjacent sequences by adding
      // 1/Nseq*i on the original order
      double fr = 0.9 / (1.0 * seqs.length);
      for (int i = 0; i < seqs.length; i++)
      {
        double nf;
        scores[i] = (0.05 + fr * i)
                + (nf = ((feats[i] == null) ? 0.0
                        : 1.0 * ((SequenceFeature[]) feats[i]).length));
        // System.err.println("Sorting on Density: seq "+seqs[i].getName()+
        // " Feats: "+nf+" Score : "+scores[i]);
      }
      jalview.util.QuickSort.sort(scores, seqs);
    }
    else
    {
      if (method == FEATURE_LABEL)
      {
        throw new Error("Not yet implemented.");
      }
    }
    if (lastSortByFeatureScore == null
            || !scoreLabel.toString().equals(lastSortByFeatureScore))
    {
      sortByFeatureScoreAscending = true;
    }
    else
    {
      sortByFeatureScoreAscending = !sortByFeatureScoreAscending;
    }
    if (sortByFeatureScoreAscending)
    {
      setOrder(alignment, seqs);
    }
    else
    {
      setReverseOrder(alignment, seqs);
    }
    lastSortByFeatureScore = scoreLabel.toString();
  }

}
