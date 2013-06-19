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
public class StructureFrequency
{
  // No need to store 1000s of strings which are not
  // visible to the user.
  public static final String MAXCOUNT = "C";

  public static final String MAXRESIDUE = "R";

  public static final String PID_GAPS = "G";

  public static final String PID_NOGAPS = "N";

  public static final String PROFILE = "P";

  public static final String PAIRPROFILE = "B";

  /**
   * Returns the 3' position of a base pair
   * 
   * @param pairs
   *          Secondary structure annotation
   * @param indice
   *          5' position of a base pair
   * @return 3' position of a base pair
   */
  public static int findPair(SequenceFeature[] pairs, int indice)
  {
    for (int i = 0; i < pairs.length; i++)
    {
      if (pairs[i].getBegin() == indice)
      {
        return pairs[i].getEnd();
      }
    }
    return -1;
  }

  /**
   * Method to calculate a 'base pair consensus row', very similar to nucleotide
   * consensus but takes into account a given structure
   * 
   * @param sequences
   * @param start
   * @param end
   * @param result
   * @param profile
   * @param rnaStruc
   */
  public static final void calculate(SequenceI[] sequences, int start,
          int end, Hashtable[] result, boolean profile,
          AlignmentAnnotation rnaStruc)
  {
    Hashtable residueHash;
    String maxResidue;
    char[] seq, struc = rnaStruc.getRNAStruc().toCharArray();
    SequenceFeature[] rna = rnaStruc._rnasecstr;
    char c, s, cEnd;
    int count, nonGap = 0, i, bpEnd = -1, j, jSize = sequences.length;
    int[] values;
    int[][] pairs;
    float percentage;

    for (i = start; i < end; i++) // foreach column
    {
      residueHash = new Hashtable();
      maxResidue = "-";
      values = new int[255];
      pairs = new int[255][255];
      bpEnd = -1;
      if (i < struc.length)
      {
        s = struc[i];
      }
      else
      {
        s = '-';
      }
      if (s == '.' || s == ' ')
      {
        s = '-';
      }

      if (s != '(')
      {
        if (s == '-')
        {
          values['-']++;
        }
      }
      else
      {
        bpEnd = findPair(rna, i);
        if (bpEnd > -1)
        {
          for (j = 0; j < jSize; j++) // foreach row
          {
            if (sequences[j] == null)
            {
              System.err
                      .println("WARNING: Consensus skipping null sequence - possible race condition.");
              continue;
            }
            c = sequences[j].getCharAt(i);
            {

              // standard representation for gaps in sequence and structure
              if (c == '.' || c == ' ')
              {
                c = '-';
              }

              if (c == '-')
              {
                values['-']++;
                continue;
              }
              cEnd = sequences[j].getCharAt(bpEnd);
              if (checkBpType(c, cEnd))
              {
                values['(']++; // H means it's a helix (structured)
              }
              pairs[c][cEnd]++;

              maxResidue = "(";
            }
          }
        }
        // nonGap++;
      }
      // UPDATE this for new values
      if (profile)
      {
        residueHash.put(PROFILE, new int[][]
        { values, new int[]
        { jSize, (jSize - values['-']) } });

        residueHash.put(PAIRPROFILE, pairs);
      }

      count = values['('];

      residueHash.put(MAXCOUNT, new Integer(count));
      residueHash.put(MAXRESIDUE, maxResidue);

      percentage = ((float) count * 100) / jSize;
      residueHash.put(PID_GAPS, new Float(percentage));

      // percentage = ((float) count * 100) / (float) nongap;
      // residueHash.put(PID_NOGAPS, new Float(percentage));
      if (result[i] == null)
      {
        result[i] = residueHash;
      }
      if (bpEnd > 0)
      {
        values[')'] = values['('];
        values['('] = 0;

        residueHash = new Hashtable();
        maxResidue = ")";

        if (profile)
        {
          residueHash.put(PROFILE, new int[][]
          { values, new int[]
          { jSize, (jSize - values['-']) } });

          residueHash.put(PAIRPROFILE, pairs);
        }

        residueHash.put(MAXCOUNT, new Integer(count));
        residueHash.put(MAXRESIDUE, maxResidue);

        percentage = ((float) count * 100) / jSize;
        residueHash.put(PID_GAPS, new Float(percentage));

        result[bpEnd] = residueHash;
      }
    }
  }

  /**
   * Method to check if a base-pair is a canonical or a wobble bp
   * 
   * @param up
   *          5' base
   * @param down
   *          3' base
   * @return True if it is a canonical/wobble bp
   */
  public static boolean checkBpType(char up, char down)
  {
    if (up > 'Z')
    {
      up -= 32;
    }
    if (down > 'Z')
    {
      down -= 32;
    }

    switch (up)
    {
    case 'A':
      switch (down)
      {
      case 'T':
        return true;
      case 'U':
        return true;
      }
      break;
    case 'C':
      switch (down)
      {
      case 'G':
        return true;
      }
      break;
    case 'T':
      switch (down)
      {
      case 'A':
        return true;
      case 'G':
        return true;
      }
      break;
    case 'G':
      switch (down)
      {
      case 'C':
        return true;
      case 'T':
        return true;
      case 'U':
        return true;
      }
      break;
    case 'U':
      switch (down)
      {
      case 'A':
        return true;
      case 'G':
        return true;
      }
      break;
    }
    return false;
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
        fv = (Float) hci.get(StructureFrequency.PID_NOGAPS);
      }
      else
      {
        fv = (Float) hci.get(StructureFrequency.PID_GAPS);
      }
      if (fv == null)
      {
        consensus.annotations[i] = null;
        // data has changed below us .. give up and
        continue;
      }
      value = fv.floatValue();
      String maxRes = hci.get(StructureFrequency.MAXRESIDUE).toString();
      String mouseOver = hci.get(StructureFrequency.MAXRESIDUE) + " ";
      if (maxRes.length() > 1)
      {
        mouseOver = "[" + maxRes + "] ";
        maxRes = "+";
      }
      int[][] profile = (int[][]) hci.get(StructureFrequency.PROFILE);
      int[][] pairs = (int[][]) hci.get(StructureFrequency.PAIRPROFILE);

      if (pairs != null && includeAllConsSymbols) // Just responsible for the
      // tooltip
      // TODO Update tooltips for Structure row
      {
        mouseOver = "";

        /*
         * TODO It's not sure what is the purpose of the alphabet and wheter it
         * is useful for structure?
         * 
         * if (alphabet != null) { for (int c = 0; c < alphabet.length; c++) {
         * tval = ((float) profile[0][alphabet[c]]) 100f / (float)
         * profile[1][ignoreGapsInConsensusCalculation ? 1 : 0]; mouseOver +=
         * ((c == 0) ? "" : "; ") + alphabet[c] + " " + ((int) tval) + "%"; } }
         * else {
         */
        Object[] ca = new Object[625];
        float[] vl = new float[625];
        int x = 0;
        for (int c = 65; c < 90; c++)
        {
          for (int d = 65; d < 90; d++)
          {
            ca[x] = new int[]
            { c, d };
            vl[x] = pairs[c][d];
            x++;
          }
        }
        jalview.util.QuickSort.sort(vl, ca);
        int p = 0;

        for (int c = 624; c > 0; c--)
        {
          if (vl[c] > 0)
          {
            tval = (vl[c] * 100f / profile[1][ignoreGapsInConsensusCalculation ? 1
                    : 0]);
            mouseOver += ((p == 0) ? "" : "; ") + (char) ((int[]) ca[c])[0]
                    + (char) ((int[]) ca[c])[1] + " " + ((int) tval) + "%";
            p++;

          }
        }

        // }
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
   * get the sorted base-pair profile for the given position of the consensus
   * 
   * @param hconsensus
   * @return profile of the given column
   */
  public static int[] extractProfile(Hashtable hconsensus,
          boolean ignoreGapsInConsensusCalculation)
  {
    int[] rtnval = new int[74]; // 2*(5*5)+2
    int[][] profile = (int[][]) hconsensus.get(StructureFrequency.PROFILE);
    int[][] pairs = (int[][]) hconsensus
            .get(StructureFrequency.PAIRPROFILE);

    if (profile == null)
      return null;

    // TODO fix the object length, also do it in completeConsensus
    Object[] ca = new Object[625];
    float[] vl = new float[625];
    int x = 0;
    for (int c = 65; c < 90; c++)
    {
      for (int d = 65; d < 90; d++)
      {
        ca[x] = new int[]
        { c, d };
        vl[x] = pairs[c][d];
        x++;
      }
    }
    jalview.util.QuickSort.sort(vl, ca);

    rtnval[0] = 2;
    rtnval[1] = 0;
    for (int c = 624; c > 0; c--)
    {
      if (vl[c] > 0)
      {
        rtnval[rtnval[0]++] = ((int[]) ca[c])[0];
        rtnval[rtnval[0]++] = ((int[]) ca[c])[1];
        rtnval[rtnval[0]] = (int) (vl[c] * 100f / profile[1][ignoreGapsInConsensusCalculation ? 1
                : 0]);
        rtnval[1] += rtnval[rtnval[0]++];
      }
    }

    return rtnval;
  }

  public static void main(String args[])
  {
    // Short test to see if checkBpType works
    ArrayList<String> test = new ArrayList<String>();
    test.add("A");
    test.add("c");
    test.add("g");
    test.add("T");
    test.add("U");
    for (String i : test)
    {
      for (String j : test)
      {
        System.out.println(i + "-" + j + ": "
                + StructureFrequency.checkBpType(i.charAt(0), j.charAt(0)));
      }
    }
  }
}
