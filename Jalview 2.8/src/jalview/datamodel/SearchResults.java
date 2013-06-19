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

public class SearchResults
{

  Match[] matches;

  /**
   * This method replaces the old search results which merely held an alignment
   * index of search matches. This broke when sequences were moved around the
   * alignment
   * 
   * @param seq
   *          Sequence
   * @param start
   *          int
   * @param end
   *          int
   */
  public void addResult(SequenceI seq, int start, int end)
  {
    if (matches == null)
    {
      matches = new Match[]
      { new Match(seq, start, end) };
      return;
    }

    int mSize = matches.length;

    Match[] tmp = new Match[mSize + 1];
    int m;
    for (m = 0; m < mSize; m++)
    {
      tmp[m] = matches[m];
    }

    tmp[m] = new Match(seq, start, end);

    matches = tmp;
  }

  /**
   * Quickly check if the given sequence is referred to in the search results
   * 
   * @param sequence
   *          (specific alignment sequence or a dataset sequence)
   * @return true if the results involve sequence
   */
  public boolean involvesSequence(SequenceI sequence)
  {
    if (matches == null || matches.length == 0)
    {
      return false;
    }
    SequenceI ds = sequence.getDatasetSequence();
    for (int m = 0; m < matches.length; m++)
    {
      if (matches[m].sequence != null
              && (matches[m].sequence == sequence || matches[m].sequence == ds))
      {
        return true;
      }
    }
    return false;
  }

  /**
   * This Method returns the search matches which lie between the start and end
   * points of the sequence in question. It is optimised for returning objects
   * for drawing on SequenceCanvas
   */
  public int[] getResults(SequenceI sequence, int start, int end)
  {
    if (matches == null)
    {
      return null;
    }

    int[] result = null;
    int[] tmp = null;
    int resultLength, matchStart = 0, matchEnd = 0;
    boolean mfound;
    for (int m = 0; m < matches.length; m++)
    {
      mfound = false;
      if (matches[m].sequence == sequence)
      {
        mfound = true;
        // locate aligned position
        matchStart = sequence.findIndex(matches[m].start) - 1;
        matchEnd = sequence.findIndex(matches[m].end) - 1;
      }
      else if (matches[m].sequence == sequence.getDatasetSequence())
      {
        mfound = true;
        // locate region in local context
        matchStart = sequence.findIndex(matches[m].start) - 1;
        matchEnd = sequence.findIndex(matches[m].end) - 1;
      }
      if (mfound)
      {
        if (matchStart <= end && matchEnd >= start)
        {
          if (matchStart < start)
          {
            matchStart = start;
          }

          if (matchEnd > end)
          {
            matchEnd = end;
          }

          if (result == null)
          {
            result = new int[]
            { matchStart, matchEnd };
          }
          else
          {
            resultLength = result.length;
            tmp = new int[resultLength + 2];
            System.arraycopy(result, 0, tmp, 0, resultLength);
            result = tmp;
            result[resultLength] = matchStart;
            result[resultLength + 1] = matchEnd;
          }
        }
        else
        {
          // debug
          // System.err.println("Outwith bounds!" + matchStart+">"+end +"  or "
          // + matchEnd+"<"+start);
        }
      }
    }
    return result;
  }

  public int getSize()
  {
    return matches == null ? 0 : matches.length;
  }

  public SequenceI getResultSequence(int index)
  {
    return matches[index].sequence;
  }

  public int getResultStart(int index)
  {
    return matches[index].start;
  }

  public int getResultEnd(int index)
  {
    return matches[index].end;
  }

  class Match
  {
    SequenceI sequence;

    int start;

    int end;

    public Match(SequenceI seq, int start, int end)
    {
      sequence = seq;
      this.start = start;
      this.end = end;
    }
  }
}
