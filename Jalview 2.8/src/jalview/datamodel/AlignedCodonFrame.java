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

import java.util.Enumeration;
import java.util.Vector;

import jalview.util.MapList;

/**
 * Stores mapping between the columns of a protein alignment and a DNA alignment
 * and a list of individual codon to amino acid mappings between sequences.
 */

public class AlignedCodonFrame
{
  /**
   * array of nucleotide positions for aligned codons at column of aligned
   * proteins.
   */
  public int[][] codons = null;

  /**
   * width of protein sequence alignement implicit assertion that codons.length
   * >= aaWidth
   */
  public int aaWidth = 0;

  /**
   * initialise codon frame with a nominal alignment width
   * 
   * @param aWidth
   */
  public AlignedCodonFrame(int aWidth)
  {
    if (aWidth <= 0)
    {
      codons = null;
      return;
    }
    codons = new int[aWidth][];
    for (int res = 0; res < aWidth; res++)
      codons[res] = null;
  }

  /**
   * ensure that codons array is at least as wide as aslen residues
   * 
   * @param aslen
   * @return (possibly newly expanded) codon array
   */
  public int[][] checkCodonFrameWidth(int aslen)
  {
    if (codons.length <= aslen + 1)
    {
      // probably never have to do this ?
      int[][] c = new int[codons.length + 10][];
      for (int i = 0; i < codons.length; i++)
      {
        c[i] = codons[i];
        codons[i] = null;
      }
      codons = c;
    }
    return codons;
  }

  /**
   * @return width of aligned translated amino acid residues
   */
  public int getaaWidth()
  {
    return aaWidth;
  }

  /**
   * TODO: not an ideal solution - we reference the aligned amino acid sequences
   * in order to make insertions on them Better would be dnaAlignment and
   * aaAlignment reference....
   */
  Vector a_aaSeqs = new Vector();

  /**
   * increase aaWidth by one and insert a new aligned codon position space at
   * aspos.
   * 
   * @param aspos
   */
  public void insertAAGap(int aspos, char gapCharacter)
  {
    // this aa appears before the aligned codons at aspos - so shift them in
    // each pair of mapped sequences
    aaWidth++;
    if (a_aaSeqs != null)
    {
      // we actually have to modify the aligned sequences here, so use the
      // a_aaSeqs vector
      Enumeration sq = a_aaSeqs.elements();
      while (sq.hasMoreElements())
      {
        ((SequenceI) sq.nextElement()).insertCharAt(aspos, gapCharacter);
      }
    }
    checkCodonFrameWidth(aspos);
    if (aspos < aaWidth)
    {
      aaWidth++;
      System.arraycopy(codons, aspos, codons, aspos + 1, aaWidth - aspos);
      codons[aspos] = null; // clear so new codon position can be marked.
    }
  }

  public void setAaWidth(int aapos)
  {
    aaWidth = aapos;
  }

  /**
   * tied array of na Sequence objects.
   */
  SequenceI[] dnaSeqs = null;

  /**
   * tied array of Mappings to protein sequence Objects and SequenceI[]
   * aaSeqs=null; MapLists where eac maps from the corresponding dnaSeqs element
   * to corresponding aaSeqs element
   */
  Mapping[] dnaToProt = null;

  /**
   * add a mapping between the dataset sequences for the associated dna and
   * protein sequence objects
   * 
   * @param dnaseq
   * @param aaseq
   * @param map
   */
  public void addMap(SequenceI dnaseq, SequenceI aaseq, MapList map)
  {
    int nlen = 1;
    if (dnaSeqs != null)
    {
      nlen = dnaSeqs.length + 1;
    }
    SequenceI[] ndna = new SequenceI[nlen];
    Mapping[] ndtp = new Mapping[nlen];
    if (dnaSeqs != null)
    {
      System.arraycopy(dnaSeqs, 0, ndna, 0, dnaSeqs.length);
      System.arraycopy(dnaToProt, 0, ndtp, 0, dnaSeqs.length);
    }
    dnaSeqs = ndna;
    dnaToProt = ndtp;
    nlen--;
    dnaSeqs[nlen] = (dnaseq.getDatasetSequence() == null) ? dnaseq : dnaseq
            .getDatasetSequence();
    Mapping mp = new Mapping(map);
    // JBPNote DEBUG! THIS !
    // dnaseq.transferAnnotation(aaseq, mp);
    // aaseq.transferAnnotation(dnaseq, new Mapping(map.getInverse()));
    mp.to = (aaseq.getDatasetSequence() == null) ? aaseq : aaseq
            .getDatasetSequence();
    a_aaSeqs.addElement(aaseq);
    dnaToProt[nlen] = mp;
  }

  public SequenceI[] getdnaSeqs()
  {
    return dnaSeqs;
  }

  public SequenceI[] getAaSeqs()
  {
    if (dnaToProt == null)
      return null;
    SequenceI[] sqs = new SequenceI[dnaToProt.length];
    for (int sz = 0; sz < dnaToProt.length; sz++)
    {
      sqs[sz] = dnaToProt[sz].to;
    }
    return sqs;
  }

  public MapList[] getdnaToProt()
  {
    if (dnaToProt == null)
      return null;
    MapList[] sqs = new MapList[dnaToProt.length];
    for (int sz = 0; sz < dnaToProt.length; sz++)
    {
      sqs[sz] = dnaToProt[sz].map;
    }
    return sqs;
  }

  public Mapping[] getProtMappings()
  {
    return dnaToProt;
  }

  /**
   * 
   * @param sequenceRef
   * @return null or corresponding aaSeq entry for dnaSeq entry
   */
  public SequenceI getAaForDnaSeq(SequenceI dnaSeqRef)
  {
    if (dnaSeqs == null)
    {
      return null;
    }
    SequenceI dnads = dnaSeqRef.getDatasetSequence();
    for (int ds = 0; ds < dnaSeqs.length; ds++)
    {
      if (dnaSeqs[ds] == dnaSeqRef || dnaSeqs[ds] == dnads)
        return dnaToProt[ds].to;
    }
    return null;
  }

  /**
   * 
   * @param sequenceRef
   * @return null or corresponding aaSeq entry for dnaSeq entry
   */
  public SequenceI getDnaForAaSeq(SequenceI aaSeqRef)
  {
    if (dnaToProt == null)
    {
      return null;
    }
    SequenceI aads = aaSeqRef.getDatasetSequence();
    for (int as = 0; as < dnaToProt.length; as++)
    {
      if (dnaToProt[as].to == aaSeqRef || dnaToProt[as].to == aads)
        return dnaSeqs[as];
    }
    return null;
  }

  /**
   * test to see if codon frame involves seq in any way
   * 
   * @param seq
   *          a nucleotide or protein sequence
   * @return true if a mapping exists to or from this sequence to any translated
   *         sequence
   */
  public boolean involvesSequence(SequenceI seq)
  {
    return getAaForDnaSeq(seq) != null || getDnaForAaSeq(seq) != null;
  }

  /**
   * Add search results for regions in other sequences that translate or are
   * translated from a particular position in seq
   * 
   * @param seq
   * @param index
   *          position in seq
   * @param results
   *          where highlighted regions go
   */
  public void markMappedRegion(SequenceI seq, int index,
          SearchResults results)
  {
    if (dnaToProt == null)
    {
      return;
    }
    int[] codon;
    SequenceI ds = seq.getDatasetSequence();
    for (int mi = 0; mi < dnaToProt.length; mi++)
    {
      if (dnaSeqs[mi] == seq || dnaSeqs[mi] == ds)
      {
        // DEBUG System.err.println("dna pos "+index);
        codon = dnaToProt[mi].map.locateInTo(index, index);
        if (codon != null)
        {
          for (int i = 0; i < codon.length; i += 2)
          {
            results.addResult(dnaToProt[mi].to, codon[i], codon[i + 1]);
          }
        }
      }
      else if (dnaToProt[mi].to == seq || dnaToProt[mi].to == ds)
      {
        // DEBUG System.err.println("aa pos "+index);
        {
          codon = dnaToProt[mi].map.locateInFrom(index, index);
          if (codon != null)
          {
            for (int i = 0; i < codon.length; i += 2)
            {
              results.addResult(dnaSeqs[mi], codon[i], codon[i + 1]);
            }
          }
        }
      }
    }
  }
}
