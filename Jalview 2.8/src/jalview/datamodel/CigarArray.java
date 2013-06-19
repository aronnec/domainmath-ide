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

import java.util.Vector;

public class CigarArray extends CigarBase
{
  /**
   * Do CIGAR operations on a set of sequences from many other cigars BAD THINGS
   * WILL HAPPEN IF A CIGARARRAY IS PASSED TO A CIGARARRAY or a CIGARCIGAR is
   * given a CIGARARRAY to insert gaps into.
   */
  /**
   * array of subject cigars
   */
  public CigarSimple refCigars[] = null;

  private boolean seqcigararray = false;

  private CigarArray()
  {
    super();
  }

  /**
   * isSeqCigarArray()
   * 
   * @return boolean true if all refCigars resolve to a SeqCigar or a CigarCigar
   */
  public boolean isSeqCigarArray()
  {
    return seqcigararray;
  }

  /**
   * Apply CIGAR operations to several cigars in parallel will throw an error if
   * any of cigar are actually CigarArrays.
   * 
   * @param cigar
   *          Cigar[]
   */
  public CigarArray(CigarSimple[] cigars)
  {
    super();
    seqcigararray = true;
    if (cigars != null && cigars.length > 0)
    {
      refCigars = new CigarSimple[cigars.length];
      for (int c = 0; c < cigars.length; c++)
      {
        refCigars[c] = cigars[c];
        if (!((cigars[c] instanceof SeqCigar) || cigars[c] instanceof CigarCigar))
        {
          seqcigararray = false;
        }
      }
    }
  }

  /**
   * construct a cigar array from the current alignment, or just the subset of
   * the current alignment specified by selectionGroup. Any columns marked as
   * hidden in columnSelection will be marked as deleted in the array.
   * 
   * @param alignment
   * @param columnSelection
   * @param selectionGroup
   */
  public CigarArray(AlignmentI alignment, ColumnSelection columnSelection,
          SequenceGroup selectionGroup)
  {
    this(constructSeqCigarArray(alignment, selectionGroup));
    constructFromAlignment(alignment,
            columnSelection != null ? columnSelection.getHiddenColumns()
                    : null, selectionGroup);
  }

  private static int[] _calcStartEndBounds(AlignmentI alignment,
          SequenceGroup selectionGroup)
  {
    int[] startend = new int[]
    { 0, 0, 0 };
    if (selectionGroup != null)
    {
      startend[0] = selectionGroup.getSize();
      startend[1] = selectionGroup.getStartRes();
      startend[2] = selectionGroup.getEndRes(); // inclusive for start and end
                                                // in
      // SeqCigar constructor
    }
    else
    {
      startend[0] = alignment.getHeight();
      startend[2] = alignment.getWidth() - 1;
    }
    return startend;
  }

  public static SeqCigar[] constructSeqCigarArray(AlignmentI alignment,
          SequenceGroup selectionGroup)
  {
    SequenceI[] seqs = null;
    int i, iSize;
    int _startend[] = _calcStartEndBounds(alignment, selectionGroup);
    int start = _startend[1], end = _startend[2];
    if (selectionGroup != null)
    {
      iSize = selectionGroup.getSize();
      seqs = selectionGroup.getSequencesInOrder(alignment);
      start = selectionGroup.getStartRes();
      end = selectionGroup.getEndRes(); // inclusive for start and end in
      // SeqCigar constructor
    }
    else
    {
      iSize = alignment.getHeight();
      seqs = alignment.getSequencesArray();
      end = alignment.getWidth() - 1;
    }
    SeqCigar[] selseqs = new SeqCigar[iSize];
    for (i = 0; i < iSize; i++)
    {
      selseqs[i] = new SeqCigar(seqs[i], start, end);
    }
    return selseqs;
  }

  /**
   * internal constructor function - called by CigarArray(AlignmentI, ...);
   * 
   * @param alignment
   * @param columnSelection
   *          - vector of visible regions as returned from
   *          columnSelection.getHiddenColumns()
   * @param selectionGroup
   */
  private void constructFromAlignment(AlignmentI alignment,
          Vector columnSelection, SequenceGroup selectionGroup)
  {
    int[] _startend = _calcStartEndBounds(alignment, selectionGroup);
    int start = _startend[1], end = _startend[2];
    // now construct the CigarArray operations
    if (columnSelection != null)
    {
      int[] region;
      int hideStart, hideEnd;
      int last = start;
      for (int j = 0; last < end & j < columnSelection.size(); j++)
      {
        region = (int[]) columnSelection.elementAt(j);
        hideStart = region[0];
        hideEnd = region[1];
        // edit hidden regions to selection range
        if (hideStart < last)
        {
          if (hideEnd > last)
          {
            hideStart = last;
          }
          else
          {
            continue;
          }
        }

        if (hideStart > end)
        {
          break;
        }

        if (hideEnd > end)
        {
          hideEnd = end;
        }

        if (hideStart > hideEnd)
        {
          break;
        }
        /**
         * form operations...
         */
        if (last < hideStart)
        {
          addOperation(CigarArray.M, hideStart - last);
        }
        addOperation(CigarArray.D, 1 + hideEnd - hideStart);
        last = hideEnd + 1;
      }
      // Final match if necessary.
      if (last < end)
      {
        addOperation(CigarArray.M, end - last + 1);
      }
    }
    else
    {
      addOperation(CigarArray.M, end - start + 1);
    }
  }

  /**
   * @see Cigar.getSequenceAndDeletions
   * @param GapChar
   *          char
   * @return Object[][]
   */
  protected Object[][] getArrayofSequenceAndDeletions(char GapChar)
  {
    if (refCigars == null || refCigars.length == 0 || length == 0)
    {
      return null;
    }
    Object[][] sqanddels = new Object[refCigars.length][];
    for (int c = 0; c < refCigars.length; c++)
    {
      String refString = refCigars[c].getSequenceString(GapChar);
      if (refString != null)
      {
        sqanddels[c] = getSequenceAndDeletions(refString, GapChar);
      }
      else
      {
        sqanddels[c] = null;
      }
    }
    return sqanddels;
  }

  /**
   * NOTE: this is an improper sequence string function
   * 
   * @return String formed by newline concatenated results of applying CIGAR
   *         operations to each reference object in turn.
   * @param GapChar
   *          char
   * @return '\n' separated strings (empty results included as \n\n)
   */
  public String getSequenceString(char GapChar)
  {
    if (length == 0 || refCigars == null)
    {
      return "";
    }
    StringBuffer seqStrings = new StringBuffer();
    Object[][] sqanddels = getArrayofSequenceAndDeletions(GapChar);
    for (int c = 0; c < refCigars.length; c++)
    {
      if (sqanddels[c] != null)
      {
        seqStrings.append((String) sqanddels[c][0]);
        sqanddels[c][0] = null;
      }
      seqStrings.append('\n');
    }
    return seqStrings.toString();
  }

  /**
   * return string results of applying cigar string to all reference cigars
   * 
   * @param GapChar
   *          char
   * @return String[]
   */
  public String[] getSequenceStrings(char GapChar)
  {

    if (length == 0 || refCigars == null || refCigars.length == 0)
    {
      return null;
    }
    Object[][] sqanddels = getArrayofSequenceAndDeletions(GapChar);
    String[] seqs = new String[sqanddels.length];
    for (int c = 0; c < refCigars.length; c++)
    {
      seqs[c] = (String) sqanddels[c][0];
    }
    return seqs;
  }

  /**
   * Combines the CigarArray cigar operations with the operations in each
   * reference cigar - creating a new reference cigar
   * 
   * @return Cigar[]
   * 
   *         public CigarBase[] getEditedCigars() {
   * 
   *         return new CigarBase[] {}; }
   */
  /**
   * applyDeletions edits underlying refCigars to propagate deleted regions, and
   * removes deletion operations from CigarArray operation list.
   * 
   * @return int[] position after deletion occured and range of deletion in
   *         cigarArray or null if none occured
   */
  public int[] applyDeletions()
  {
    java.util.Vector delpos = null;
    if (length == 0)
    {
      return null;
    }
    int cursor = 0; // range counter for deletions
    int vcursor = 0; // visible column index
    int offset = 0; // shift in visible column index as deletions are made
    int i = 0;
    while (i < length)
    {
      if (operation[i] != D)
      {
        if (operation[i] == M)
        {
          cursor += range[i];
        }
        vcursor += range[i++];
      }
      else
      {
        if (delpos == null)
        {
          delpos = new java.util.Vector();
        }
        int delstart = cursor, delend = cursor + range[i] - 1; // inclusive
        delpos.addElement(new int[]
        { vcursor + offset, range[i] }); // index of right hand column after
        // hidden region boundary
        offset += range[i] - 1; // shift in visible column coordinates
        System.arraycopy(operation, i + 1, operation, i, length - i);
        System.arraycopy(range, i + 1, range, i, length - i);
        length--;
        /*
         * int dmax=0; for (int s=0; s<refCigars.length; s++) { int d =
         * refCigars[s].deleteRange(delstart, delend); if (d>dmax) dmax=d; }
         * offset+=dmax; // shift in visible column coordinates
         */
        for (int s = 0; s < refCigars.length; s++)
        {
          int d = refCigars[s].deleteRange(delstart, delend);
        }

      }
    }
    if (delpos != null)
    {
      int[] pos = new int[delpos.size() * 2];
      for (int k = 0, l = delpos.size(); k < l; k++)
      {
        int[] dr = ((int[]) delpos.elementAt(k));
        pos[k * 2] = dr[0];
        pos[k * 2 + 1] = dr[1];
        delpos.setElementAt(null, k);
      }
      delpos = null;
      return pos;
    }
    return null;
  }

  /**
   * 
   * @return SeqCigar[] or null if CigarArray is not a SeqCigarArray (ie it does
   *         not resolve to set of seqCigars)
   */
  public SeqCigar[] getSeqCigarArray()
  {
    if (!isSeqCigarArray())
    {
      return null;
    }
    SeqCigar[] sa = new SeqCigar[refCigars.length];
    for (int i = 0; i < refCigars.length; i++)
    {
      sa[i] = (SeqCigar) refCigars[i];
    }
    return sa;
  }
}
