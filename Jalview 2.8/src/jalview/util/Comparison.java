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
package jalview.util;

import jalview.datamodel.*;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class Comparison
{
  /** DOCUMENT ME!! */
  public static final String GapChars = " .-";

  /**
   * DOCUMENT ME!
   * 
   * @param ii
   *          DOCUMENT ME!
   * @param jj
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public static final float compare(SequenceI ii, SequenceI jj)
  {
    return Comparison.compare(ii, jj, 0, ii.getLength() - 1);
  }

  /**
   * this was supposed to be an ungapped pid calculation
   * 
   * @param ii
   *          SequenceI
   * @param jj
   *          SequenceI
   * @param start
   *          int
   * @param end
   *          int
   * @return float
   */
  public static float compare(SequenceI ii, SequenceI jj, int start, int end)
  {
    String si = ii.getSequenceAsString();
    String sj = jj.getSequenceAsString();

    int ilen = si.length() - 1;
    int jlen = sj.length() - 1;

    while (jalview.util.Comparison.isGap(si.charAt(start + ilen)))
    {
      ilen--;
    }

    while (jalview.util.Comparison.isGap(sj.charAt(start + jlen)))
    {
      jlen--;
    }

    int count = 0;
    int match = 0;
    float pid = -1;

    if (ilen > jlen)
    {
      for (int j = 0; j < jlen; j++)
      {
        if (si.substring(start + j, start + j + 1).equals(
                sj.substring(start + j, start + j + 1)))
        {
          match++;
        }

        count++;
      }

      pid = (float) match / (float) ilen * 100;
    }
    else
    {
      for (int j = 0; j < jlen; j++)
      {
        if (si.substring(start + j, start + j + 1).equals(
                sj.substring(start + j, start + j + 1)))
        {
          match++;
        }

        count++;
      }

      pid = (float) match / (float) jlen * 100;
    }

    return pid;
  }

  /**
   * this is a gapped PID calculation
   * 
   * @param s1
   *          SequenceI
   * @param s2
   *          SequenceI
   * @return float
   */
  public final static float PID(String seq1, String seq2)
  {
    return PID(seq1, seq2, 0, seq1.length());
  }

  static final int caseShift = 'a' - 'A';

  // Another pid with region specification
  public final static float PID(String seq1, String seq2, int start, int end)
  {
    return PID(seq1, seq2, start, end, true, false);
  }

  /**
   * Calculate percent identity for a pair of sequences over a particular range,
   * with different options for ignoring gaps.
   * 
   * @param seq1
   * @param seq2
   * @param start
   *          - position in seqs
   * @param end
   *          - position in seqs
   * @param wcGaps
   *          - if true - gaps match any character, if false, do not match
   *          anything
   * @param ungappedOnly
   *          - if true - only count PID over ungapped columns
   * @return
   */
  public final static float PID(String seq1, String seq2, int start,
          int end, boolean wcGaps, boolean ungappedOnly)
  {
    int s1len = seq1.length();
    int s2len = seq2.length();

    int len = Math.min(s1len, s2len);

    if (end < len)
    {
      len = end;
    }

    if (len < start)
    {
      start = len - 1; // we just use a single residue for the difference
    }

    int elen = len - start, bad = 0;
    char chr1;
    char chr2;
    boolean agap;
    for (int i = start; i < len; i++)
    {
      chr1 = seq1.charAt(i);

      chr2 = seq2.charAt(i);
      agap = isGap(chr1) || isGap(chr2);
      if ('a' <= chr1 && chr1 <= 'z')
      {
        // TO UPPERCASE !!!
        // Faster than toUpperCase
        chr1 -= caseShift;
      }
      if ('a' <= chr2 && chr2 <= 'z')
      {
        // TO UPPERCASE !!!
        // Faster than toUpperCase
        chr2 -= caseShift;
      }

      if (chr1 != chr2)
      {
        if (agap)
        {
          if (ungappedOnly)
          {
            elen--;
          }
          else if (!wcGaps)
          {
            bad++;
          }
        }
        else
        {
          bad++;
        }
      }

    }
    if (elen < 1)
    {
      return 0f;
    }
    return ((float) 100 * (elen - bad)) / elen;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param c
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public static final boolean isGap(char c)
  {
    return (c == '-' || c == '.' || c == ' ') ? true : false;
  }

  public static final boolean isNucleotide(SequenceI[] seqs)
  {
    int i = 0, iSize = seqs.length, j, jSize;
    float nt = 0, aa = 0;
    char c;
    while (i < iSize)
    {
      jSize = seqs[i].getLength();
      for (j = 0; j < jSize; j++)
      {
        c = seqs[i].getCharAt(j);
        if ('a' <= c && c <= 'z')
        {
          c -= ('a' - 'A');
        }

        if (c == 'A' || c == 'G' || c == 'C' || c == 'T' || c == 'U')
        {
          nt++;
        }
        else if (!jalview.util.Comparison.isGap(seqs[i].getCharAt(j)))
        {
          aa++;
        }
      }
      i++;
    }

    if ((nt / (nt + aa)) > 0.85f)
    {
      return true;
    }
    else
    {
      return false;
    }

  }
}
