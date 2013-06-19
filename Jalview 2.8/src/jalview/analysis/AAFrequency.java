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

/**
 * Takes in a vector or array of sequences and column start and column end and
 * returns a new Hashtable[] of size maxSeqLength, if Hashtable not supplied.
 * This class is used extensively in calculating alignment colourschemes that
 * depend on the amount of conservation in each alignment column.
 * 
 * @author $author$
 * @version $Revision$
 */
public class AAFrequency
{
  // No need to store 1000s of strings which are not
  // visible to the user.
  public static final String MAXCOUNT = "C";

  public static final String MAXRESIDUE = "R";

  public static final String PID_GAPS = "G";

  public static final String PID_NOGAPS = "N";

  public static final String PROFILE = "P";

  public static final Hashtable[] calculate(List<SequenceI> list,
          int start, int end)
  {
    return calculate(list, start, end, false);
  }

  public static final Hashtable[] calculate(List<SequenceI> sequences,
          int start, int end, boolean profile)
  {
    SequenceI[] seqs = new SequenceI[sequences.size()];
    int width = 0;
    synchronized (sequences)
    {
      for (int i = 0; i < sequences.size(); i++)
      {
        seqs[i] = sequences.get(i);
        if (seqs[i].getLength() > width)
        {
          width = seqs[i].getLength();
        }
      }

      Hashtable[] reply = new Hashtable[width];

      if (end >= width)
      {
        end = width;
      }

      calculate(seqs, start, end, reply, profile);
      return reply;
    }
  }

  public static final void calculate(SequenceI[] sequences, int start,
          int end, Hashtable[] result)
  {
    calculate(sequences, start, end, result, false);
  }

  public static final void calculate(SequenceI[] sequences, int start,
          int end, Hashtable[] result, boolean profile)
  {
    Hashtable residueHash;
    int maxCount, nongap, i, j, v, jSize = sequences.length;
    String maxResidue;
    char c;
    float percentage;

    int[] values = new int[255];

    char[] seq;

    for (i = start; i < end; i++)
    {
      residueHash = new Hashtable();
      maxCount = 0;
      maxResidue = "";
      nongap = 0;
      values = new int[255];

      for (j = 0; j < jSize; j++)
      {
        if (sequences[j] == null)
        {
          System.err
                  .println("WARNING: Consensus skipping null sequence - possible race condition.");
          continue;
        }
        seq = sequences[j].getSequence();
        if (seq.length > i)
        {
          c = seq[i];

          if (c == '.' || c == ' ')
          {
            c = '-';
          }

          if (c == '-')
          {
            values['-']++;
            continue;
          }
          else if ('a' <= c && c <= 'z')
          {
            c -= 32; // ('a' - 'A');
          }

          nongap++;
          values[c]++;

        }
        else
        {
          values['-']++;
        }
      }

      for (v = 'A'; v < 'Z'; v++)
      {
        if (values[v] < 2 || values[v] < maxCount)
        {
          continue;
        }

        if (values[v] > maxCount)
        {
          maxResidue = String.valueOf((char) v);
        }
        else if (values[v] == maxCount)
        {
          maxResidue += String.valueOf((char) v);
        }
        maxCount = values[v];
      }

      if (maxResidue.length() == 0)
      {
        maxResidue = "-";
      }
      if (profile)
      {
        residueHash.put(PROFILE, new int[][]
        { values, new int[]
        { jSize, nongap } });
      }
      residueHash.put(MAXCOUNT, new Integer(maxCount));
      residueHash.put(MAXRESIDUE, maxResidue);

      percentage = ((float) maxCount * 100) / jSize;
      residueHash.put(PID_GAPS, new Float(percentage));

      percentage = ((float) maxCount * 100) / nongap;
      residueHash.put(PID_NOGAPS, new Float(percentage));
      result[i] = residueHash;
    }
  }

  /**
   * Compute all or part of the annotation row from the given consensus
   * hashtable
   * 
   * @param consensus
   *          - pre-allocated annotation row
   * @param hconsensus
   * @param iStart
   * @param width
   * @param ignoreGapsInConsensusCalculation
   * @param includeAllConsSymbols
   */
  public static void completeConsensus(AlignmentAnnotation consensus,
          Hashtable[] hconsensus, int iStart, int width,
          boolean ignoreGapsInConsensusCalculation,
          boolean includeAllConsSymbols)
  {
    completeConsensus(consensus, hconsensus, iStart, width,
            ignoreGapsInConsensusCalculation, includeAllConsSymbols, null); // new
                                                                            // char[]
    // { 'A', 'C', 'G', 'T', 'U' });
  }

  public static void completeConsensus(AlignmentAnnotation consensus,
          Hashtable[] hconsensus, int iStart, int width,
          boolean ignoreGapsInConsensusCalculation,
          boolean includeAllConsSymbols, char[] alphabet)
  {
    float tval, value;
    if (consensus == null || consensus.annotations == null
            || consensus.annotations.length < width)
    {
      // called with a bad alignment annotation row - wait for it to be
      // initialised properly
      return;
    }
    for (int i = iStart; i < width; i++)
    {
      Hashtable hci;
      if (i >= hconsensus.length || ((hci = hconsensus[i]) == null))
      {
        // happens if sequences calculated over were shorter than alignment
        // width
        consensus.annotations[i] = null;
        continue;
      }

      value = 0;
      Float fv;
      if (ignoreGapsInConsensusCalculation)
      {
        fv = (Float) hci.get(AAFrequency.PID_NOGAPS);
      }
      else
      {
        fv = (Float) hci.get(AAFrequency.PID_GAPS);
      }
      if (fv == null)
      {
        consensus.annotations[i] = null;
        // data has changed below us .. give up and
        continue;
      }
      value = fv.floatValue();
      String maxRes = hci.get(AAFrequency.MAXRESIDUE).toString();
      String mouseOver = hci.get(AAFrequency.MAXRESIDUE) + " ";
      if (maxRes.length() > 1)
      {
        mouseOver = "[" + maxRes + "] ";
        maxRes = "+";
      }
      int[][] profile = (int[][]) hci.get(AAFrequency.PROFILE);
      if (profile != null && includeAllConsSymbols)
      {
        mouseOver = "";
        if (alphabet != null)
        {
          for (int c = 0; c < alphabet.length; c++)
          {
            tval = profile[0][alphabet[c]] * 100f
                    / profile[1][ignoreGapsInConsensusCalculation ? 1 : 0];
            mouseOver += ((c == 0) ? "" : "; ") + alphabet[c] + " "
                    + ((int) tval) + "%";
          }
        }
        else
        {
          Object[] ca = new Object[profile[0].length];
          float[] vl = new float[profile[0].length];
          for (int c = 0; c < ca.length; c++)
          {
            ca[c] = new char[]
            { (char) c };
            vl[c] = profile[0][c];
          }
          ;
          jalview.util.QuickSort.sort(vl, ca);
          for (int p = 0, c = ca.length - 1; profile[0][((char[]) ca[c])[0]] > 0; c--)
          {
            if (((char[]) ca[c])[0] != '-')
            {
              tval = profile[0][((char[]) ca[c])[0]]
                      * 100f
                      / profile[1][ignoreGapsInConsensusCalculation ? 1 : 0];
              mouseOver += ((p == 0) ? "" : "; ") + ((char[]) ca[c])[0]
                      + " " + ((int) tval) + "%";
              p++;

            }
          }

        }
      }
      else
      {
        mouseOver += ((int) value + "%");
      }
      consensus.annotations[i] = new Annotation(maxRes, mouseOver, ' ',
              value);
    }
  }

  /**
   * get the sorted profile for the given position of the consensus
   * 
   * @param hconsensus
   * @return
   */
  public static int[] extractProfile(Hashtable hconsensus,
          boolean ignoreGapsInConsensusCalculation)
  {
    int[] rtnval = new int[64];
    int[][] profile = (int[][]) hconsensus.get(AAFrequency.PROFILE);
    if (profile == null)
      return null;
    Object[] ca = new Object[profile[0].length];
    float[] vl = new float[profile[0].length];
    for (int c = 0; c < ca.length; c++)
    {
      ca[c] = new char[]
      { (char) c };
      vl[c] = profile[0][c];
    }
    ;
    jalview.util.QuickSort.sort(vl, ca);
    rtnval[0] = 2;
    rtnval[1] = 0;
    for (int c = ca.length - 1; profile[0][((char[]) ca[c])[0]] > 0; c--)
    {
      if (((char[]) ca[c])[0] != '-')
      {
        rtnval[rtnval[0]++] = ((char[]) ca[c])[0];
        rtnval[rtnval[0]] = (int) (profile[0][((char[]) ca[c])[0]] * 100f / profile[1][ignoreGapsInConsensusCalculation ? 1
                : 0]);
        rtnval[1] += rtnval[rtnval[0]++];
      }
    }
    return rtnval;
  }
}
