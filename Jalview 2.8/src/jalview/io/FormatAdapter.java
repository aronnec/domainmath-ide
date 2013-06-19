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
package jalview.io;

import jalview.datamodel.*;

/**
 * Additional formatting methods used by the application in a number of places.
 * 
 * @author $author$
 * @version $Revision$
 */
public class FormatAdapter extends AppletFormatAdapter
{

  public String formatSequences(String format, SequenceI[] seqs,
          String[] omitHiddenColumns)
  {

    return formatSequences(format, replaceStrings(seqs, omitHiddenColumns));
  }

  /**
   * create sequences with each sequence string replaced with the one given in
   * omitHiddenCOlumns
   * 
   * @param seqs
   * @param omitHiddenColumns
   * @return new sequences
   */
  public SequenceI[] replaceStrings(SequenceI[] seqs,
          String[] omitHiddenColumns)
  {
    if (omitHiddenColumns != null)
    {
      SequenceI[] tmp = new SequenceI[seqs.length];
      for (int i = 0; i < seqs.length; i++)
      {
        tmp[i] = new Sequence(seqs[i].getName(), omitHiddenColumns[i],
                seqs[i].getStart(), seqs[i].getEnd());
        tmp[i].setDescription(seqs[i].getDescription());
      }
      seqs = tmp;
    }
    return seqs;
  }

  /**
   * Format a vector of sequences as a flat alignment file. TODO: allow caller
   * to detect errors and warnings encountered when generating output
   * 
   * 
   * @param format
   *          Format string as givien in the AppletFormatAdaptor list (exact
   *          match to name of class implementing file io for that format)
   * @param seqs
   *          vector of sequences to write
   * 
   * @return String containing sequences in desired format
   */
  public String formatSequences(String format, SequenceI[] seqs)
  {

    try
    {
      AlignFile afile = null;

      if (format.equalsIgnoreCase("FASTA"))
      {
        afile = new FastaFile();
        afile.addJVSuffix(jalview.bin.Cache.getDefault("FASTA_JVSUFFIX",
                true));
      }
      else if (format.equalsIgnoreCase("MSF"))
      {
        afile = new MSFfile();
        afile.addJVSuffix(jalview.bin.Cache
                .getDefault("MSF_JVSUFFIX", true));
      }
      else if (format.equalsIgnoreCase("PileUp"))
      {
        afile = new PileUpfile();
        afile.addJVSuffix(jalview.bin.Cache.getDefault("PILEUP_JVSUFFIX",
                true));
      }
      else if (format.equalsIgnoreCase("CLUSTAL"))
      {
        afile = new ClustalFile();
        afile.addJVSuffix(jalview.bin.Cache.getDefault("CLUSTAL_JVSUFFIX",
                true));
      }
      else if (format.equalsIgnoreCase("BLC"))
      {
        afile = new BLCFile();
        afile.addJVSuffix(jalview.bin.Cache
                .getDefault("BLC_JVSUFFIX", true));
      }
      else if (format.equalsIgnoreCase("PIR"))
      {
        afile = new PIRFile();
        afile.addJVSuffix(jalview.bin.Cache
                .getDefault("PIR_JVSUFFIX", true));
      }
      else if (format.equalsIgnoreCase("PFAM"))
      {
        afile = new PfamFile();
        afile.addJVSuffix(jalview.bin.Cache.getDefault("PFAM_JVSUFFIX",
                true));
      }
      /*
       * amsa is not supported by this function - it requires an alignment
       * rather than a sequence vector else if (format.equalsIgnoreCase("AMSA"))
       * { afile = new AMSAFile(); afile.addJVSuffix(
       * jalview.bin.Cache.getDefault("AMSA_JVSUFFIX", true)); }
       */

      afile.setSeqs(seqs);
      String afileresp = afile.print();
      if (afile.hasWarningMessage())
      {
        System.err.println("Warning raised when writing as " + format
                + " : " + afile.getWarningMessage());
      }
      return afileresp;
    } catch (Exception e)
    {
      System.err.println("Failed to write alignment as a '" + format
              + "' file\n");
    }

    return null;
  }

  public boolean getCacheSuffixDefault(String format)
  {
    if (isValidFormat(format)) {
          return jalview.bin.Cache.getDefault(format.toUpperCase()
                  + "_JVSUFFIX", true);
      }
    return false;
  }

  public String formatSequences(String format, AlignmentI alignment,
          String[] omitHidden, ColumnSelection colSel)
  {
    return formatSequences(format, alignment, omitHidden,
            getCacheSuffixDefault(format), colSel, null);
  }

  public String formatSequences(String format, AlignmentI alignment,
          String[] omitHidden, ColumnSelection colSel, SequenceGroup sgp)
  {
    return formatSequences(format, alignment, omitHidden,
            getCacheSuffixDefault(format), colSel, sgp);
  }

  /**
   * hack function to replace seuqences with visible sequence strings before
   * generating a string of the alignment in the given format.
   * 
   * @param format
   * @param alignment
   * @param omitHidden
   *          sequence strings to write out in order of sequences in alignment
   * @param colSel
   *          defines hidden columns that are edited out of annotation
   * @return string representation of the alignment formatted as format
   */
  public String formatSequences(String format, AlignmentI alignment,
          String[] omitHidden, boolean suffix, ColumnSelection colSel)
  {
    return formatSequences(format, alignment, omitHidden, suffix, colSel,
            null);
  }

  public String formatSequences(String format, AlignmentI alignment,
          String[] omitHidden, boolean suffix, ColumnSelection colSel,
          jalview.datamodel.SequenceGroup selgp)
  {
    if (omitHidden != null)
    {
      // TODO consider using AlignmentView to prune to visible region
      // TODO prune sequence annotation and groups to visible region
      Alignment alv = new Alignment(replaceStrings(
              alignment.getSequencesArray(), omitHidden));
      AlignmentAnnotation[] ala = alignment.getAlignmentAnnotation();
      if (ala != null)
      {
        for (int i = 0; i < ala.length; i++)
        {
          AlignmentAnnotation na = new AlignmentAnnotation(ala[i]);
          if (selgp != null)
          {
            colSel.makeVisibleAnnotation(selgp.getStartRes(),
                    selgp.getEndRes(), na);
          }
          else
          {
            colSel.makeVisibleAnnotation(na);
          }
          alv.addAnnotation(na);
        }
      }
      return this.formatSequences(format, alv, suffix);
    }
    return this.formatSequences(format, alignment, suffix);
  }

  /**
   * validate format is valid for IO in Application. This is basically the
   * AppletFormatAdapter.isValidFormat call with additional checks for
   * Application only formats like 'Jalview'.
   * 
   * @param format
   *          a format string to be compared with list of readable or writable
   *          formats (READABLE_FORMATS or WRITABLE_FORMATS)
   * @param forwriting
   *          when true, format is checked against list of writable formats.
   * @return true if format is valid
   */
  public static boolean isValidIOFormat(String format,
          boolean forwriting)
  {
    if (format.equalsIgnoreCase("jalview"))
    {
      return true;
    }
    return AppletFormatAdapter.isValidFormat(format, forwriting);
  }
}
