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

import jalview.datamodel.SequenceGroup;
import jalview.datamodel.SequenceI;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 * various methods for defining groups on an alignment based on some other
 * properties
 * 
 * @author JimP
 * 
 */
public class Grouping
{
  /**
   * Divide the given sequences based on the equivalence of their corresponding
   * selectedChars string. If exgroups is provided, existing groups will be
   * subdivided.
   * 
   * @param sequences
   * @param selectedChars
   * @param list
   * @return
   */
  public static SequenceGroup[] makeGroupsFrom(SequenceI[] sequences,
          String[] selectedChars, List<SequenceGroup> list)
  {
    // TODO: determine how to get/recover input data for group generation
    Hashtable gps = new Hashtable();
    int width = 0, i;
    Hashtable pgroup = new Hashtable();
    if (list != null)
    {
      for (SequenceGroup sg : list)
      {
        for (SequenceI sq : sg.getSequences(null))
        {
          pgroup.put(sq.toString(), sg);
        }
      }
    }
    for (i = 0; i < sequences.length; i++)
    {
      String schar = selectedChars[i];
      SequenceGroup pgp = (SequenceGroup) pgroup
              .get(((Object) sequences[i]).toString());
      if (pgp != null)
      {
        schar = pgp.getName() + ":" + schar;
      }
      Vector svec = (Vector) gps.get(schar);
      if (svec == null)
      {
        svec = new Vector();
        gps.put(schar, svec);
      }
      if (width < sequences[i].getLength())
      {
        width = sequences[i].getLength();
      }
      svec.addElement(sequences[i]);
    }
    // make some groups
    java.util.Enumeration sge = gps.keys();
    SequenceGroup[] groups = new SequenceGroup[gps.size()];
    i = 0;
    while (sge.hasMoreElements())
    {
      String key = (String) sge.nextElement();
      SequenceGroup group = new SequenceGroup((Vector) gps.get(key),
              "Subseq: " + key, null, true, true, false, 0, width - 1);

      groups[i++] = group;
    }
    gps.clear();
    pgroup.clear();
    return groups;
  }

  /**
   * subdivide the given sequences based on the distribution of features
   * 
   * @param featureLabels
   *          - null or one or more feature types to filter on.
   * @param groupLabels
   *          - null or set of groups to filter features on
   * @param start
   *          - range for feature filter
   * @param stop
   *          - range for feature filter
   * @param sequences
   *          - sequences to be divided
   * @param exgroups
   *          - existing groups to be subdivided
   * @param method
   *          - density, description, score
   */
  public static void divideByFeature(String[] featureLabels,
          String[] groupLabels, int start, int stop, SequenceI[] sequences,
          Vector exgroups, String method)
  {
    // TODO implement divideByFeature
    /*
     * if (method!=AlignmentSorter.FEATURE_SCORE &&
     * method!=AlignmentSorter.FEATURE_LABEL &&
     * method!=AlignmentSorter.FEATURE_DENSITY) { throw newError(
     * "Implementation Error - sortByFeature method must be one of FEATURE_SCORE, FEATURE_LABEL or FEATURE_DENSITY."
     * ); } boolean ignoreScore=method!=AlignmentSorter.FEATURE_SCORE;
     * StringBuffer scoreLabel = new StringBuffer();
     * scoreLabel.append(start+stop+method); // This doesn't work yet - we'd
     * like to have a canonical ordering that can be preserved from call to call
     * for (int i=0;featureLabels!=null && i<featureLabels.length; i++) {
     * scoreLabel.append(featureLabels[i]==null ? "null" : featureLabels[i]); }
     * for (int i=0;groupLabels!=null && i<groupLabels.length; i++) {
     * scoreLabel.append(groupLabels[i]==null ? "null" : groupLabels[i]); }
     * SequenceI[] seqs = alignment.getSequencesArray();
     * 
     * boolean[] hasScore = new boolean[seqs.length]; // per sequence score //
     * presence int hasScores = 0; // number of scores present on set double[]
     * scores = new double[seqs.length]; int[] seqScores = new int[seqs.length];
     * Object[] feats = new Object[seqs.length]; double min = 0, max = 0; for
     * (int i = 0; i < seqs.length; i++) { SequenceFeature[] sf =
     * seqs[i].getSequenceFeatures(); if (sf==null &&
     * seqs[i].getDatasetSequence()!=null) { sf =
     * seqs[i].getDatasetSequence().getSequenceFeatures(); } if (sf==null) { sf
     * = new SequenceFeature[0]; } else { SequenceFeature[] tmp = new
     * SequenceFeature[sf.length]; for (int s=0; s<tmp.length;s++) { tmp[s] =
     * sf[s]; } sf = tmp; } int sstart = (start==-1) ? start :
     * seqs[i].findPosition(start); int sstop = (stop==-1) ? stop :
     * seqs[i].findPosition(stop); seqScores[i]=0; scores[i]=0.0; int
     * n=sf.length; for (int f=0;f<sf.length;f++) { // filter for selection
     * criteria if ( // ignore features outwith alignment start-stop positions.
     * (sf[f].end < sstart || sf[f].begin > sstop) || // or ignore based on
     * selection criteria (featureLabels != null &&
     * !AlignmentSorter.containsIgnoreCase(sf[f].type, featureLabels)) ||
     * (groupLabels != null // problem here: we cannot eliminate null feature
     * group features && (sf[f].getFeatureGroup() != null &&
     * !AlignmentSorter.containsIgnoreCase(sf[f].getFeatureGroup(),
     * groupLabels)))) { // forget about this feature sf[f] = null; n--; } else
     * { // or, also take a look at the scores if necessary. if (!ignoreScore &&
     * sf[f].getScore()!=Float.NaN) { if (seqScores[i]==0) { hasScores++; }
     * seqScores[i]++; hasScore[i] = true; scores[i] += sf[f].getScore(); //
     * take the first instance of this // score. } } } SequenceFeature[] fs;
     * feats[i] = fs = new SequenceFeature[n]; if (n>0) { n=0; for (int
     * f=0;f<sf.length;f++) { if (sf[f]!=null) { ((SequenceFeature[])
     * feats[i])[n++] = sf[f]; } } if (method==FEATURE_LABEL) { // order the
     * labels by alphabet String[] labs = new String[fs.length]; for (int
     * l=0;l<labs.length; l++) { labs[l] = (fs[l].getDescription()!=null ?
     * fs[l].getDescription() : fs[l].getType()); }
     * jalview.util.QuickSort.sort(labs, ((Object[]) feats[i])); } } if
     * (hasScore[i]) { // compute average score scores[i]/=seqScores[i]; //
     * update the score bounds. if (hasScores == 1) { max = min = scores[i]; }
     * else { if (max < scores[i]) { max = scores[i]; } if (min > scores[i]) {
     * min = scores[i]; } } } }
     * 
     * if (method==FEATURE_SCORE) { if (hasScores == 0) { return; // do nothing
     * - no scores present to sort by. } // pad score matrix if (hasScores <
     * seqs.length) { for (int i = 0; i < seqs.length; i++) { if (!hasScore[i])
     * { scores[i] = (max + i); } else { int nf=(feats[i]==null) ? 0
     * :((SequenceFeature[]) feats[i]).length;
     * System.err.println("Sorting on Score: seq "+seqs[i].getName()+
     * " Feats: "+nf+" Score : "+scores[i]); } } }
     * 
     * jalview.util.QuickSort.sort(scores, seqs); } else if
     * (method==FEATURE_DENSITY) {
     * 
     * // break ties between equivalent numbers for adjacent sequences by adding
     * 1/Nseq*i on the original order double fr = 0.9/(1.0*seqs.length); for
     * (int i=0;i<seqs.length; i++) { double nf; scores[i] =
     * (0.05+fr*i)+(nf=((feats[i]==null) ? 0.0 :1.0*((SequenceFeature[])
     * feats[i]).length));
     * System.err.println("Sorting on Density: seq "+seqs[i].getName()+
     * " Feats: "+nf+" Score : "+scores[i]); }
     * jalview.util.QuickSort.sort(scores, seqs); } else { if
     * (method==FEATURE_LABEL) { throw new Error("Not yet implemented."); } } if
     * (lastSortByFeatureScore ==null ||
     * scoreLabel.equals(lastSortByFeatureScore)) { setOrder(alignment, seqs); }
     * else { setReverseOrder(alignment, seqs); } lastSortByFeatureScore =
     * scoreLabel.toString();
     */
  }

}
