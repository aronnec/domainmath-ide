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

import java.io.*;
import java.util.*;

import jalview.datamodel.*;

/**
 * parse a simple blast report. Attempt to cope with query anchored and pairwise
 * alignments only.
 * 
 * @author Jim Procter
 */

public class SimpleBlastFile extends AlignFile
{
  /**
   * header and footer info goes into alignment annotation.
   */
  StringBuffer headerLines, footerLines;

  /**
   * hold sequence ids in order of appearance in file
   */
  Vector seqids;

  public SimpleBlastFile()
  {
  }

  public SimpleBlastFile(String inFile, String type) throws IOException
  {
    super(inFile, type);
  }

  public SimpleBlastFile(FileParse source) throws IOException
  {
    super(source);
  }

  public void initData()
  {
    super.initData();
    headerLines = new StringBuffer();
    footerLines = new StringBuffer();
    seqids = new Vector();
  }

  public void parse() throws IOException
  {
    String line;
    char gapc = ' '; // nominal gap character
    Hashtable seqhash = new Hashtable();
    boolean inAlignments = false;
    int padding = -1, numcol = -1, aligcol = -1, lastcol = -1;
    long qlen = 0, rstart, rend; // total number of query bases so far
    boolean padseq = false;
    while ((line = nextLine()) != null)
    {
      if (line.indexOf("ALIGNMENTS") == 0)
      {
        inAlignments = true;
      }
      else
      {
        if (inAlignments)
        {
          if (line.trim().length() == 0)
          {
            continue;
          }
          // parse out the sequences
          // query anchored means that we use the query sequence as the
          // alignment ruler
          if (line.indexOf("Query") == 0)
          {
            padding = -1;
            // reset column markers for this block
            numcol = -1;
            aligcol = -1;
            lastcol = -1;
            // init or reset the column positions
            for (int p = 5, mLen = line.length(); p < mLen; p++)
            {
              char c = line.charAt(p);
              if (c >= '0' && c <= '9')
              {
                if (numcol == -1)
                {
                  numcol = p;
                }
                else if (aligcol != -1 && lastcol == -1)
                {
                  lastcol = p;
                }
              }
              else
              {
                if (c >= 'A' && c <= 'z')
                {
                  if (aligcol == -1)
                  {
                    aligcol = p;
                    padding = -1;
                  }
                }
                else
                {
                  if (padding == -1)
                  {
                    padding = p; // beginning of last stretch of whitespace
                  }
                }
              }
            }
            if (padding == -1)
            {
              padding = aligcol;
            }
          }
          if (line.indexOf("Database:") > -1
                  || (aligcol == -1 || numcol == -1 || lastcol == -1)
                  || line.length() < lastcol)
          {
            inAlignments = false;
          }
          else
          {
            // now extract the alignment.
            String sqid = line.substring(0, numcol).trim();
            String stindx = line.substring(numcol, aligcol).trim();
            String aligseg = line.substring(aligcol, padding);
            String endindx = line.substring(lastcol).trim();
            // init start/end prior to parsing
            rstart = 1; // best guess we have
            rend = 0; // if zero at end of parsing, then we count non-gaps
            try
            {
              rstart = Long.parseLong(stindx);
            } catch (Exception e)
            {
              System.err.println("Couldn't parse '" + stindx
                      + "' as start of row");
              // inAlignments = false;
              // warn for this line
            }
            try
            {
              rend = Long.parseLong(endindx);
            } catch (Exception e)
            {
              System.err.println("Couldn't parse '" + endindx
                      + "' as end of row");
              // inAlignments = false;

              // warn for this line
            }
            Vector seqentries = (Vector) seqhash.get(sqid);
            if (seqentries == null)
            {
              seqentries = new Vector();
              seqhash.put(sqid, seqentries);
              seqids.addElement(sqid);
            }

            Object[] seqentry = null;
            Enumeration sqent = seqentries.elements();
            while (seqentry == null && sqent.hasMoreElements())
            {
              seqentry = (Object[]) sqent.nextElement();
              if (((long[]) seqentry[1])[1] + 1 != rstart)
              {
                seqentry = null;
              }
            }
            padseq = false;
            if (seqentry == null)
            {
              padseq = true; // prepend gaps to new sequences in this block
              seqentry = new Object[]
              { new StringBuffer(), new long[]
              { rstart, rend } };
              seqentries.addElement(seqentry);
              seqhash.put(sqid, seqentry);

            }
            if (sqid.equals("Query"))
            {
              // update current block length in case we need to pad
              qlen = ((StringBuffer) seqentry[0]).length();
            }
            StringBuffer sqs = ((StringBuffer) seqentry[0]);
            if (padseq)
            {
              for (long c = sqs.length(); c < qlen; c++)
              {
                sqs.append(gapc);
              }
            }
            sqs.append(aligseg);
            if (rend > 0)
            {
              ((long[]) seqentry[1])[1] = rend;
            }
          }
          // end of parsing out the sequences
        }
        // if we haven't parsed the line as an alignment, then
        // add to the sequence header
        if (!inAlignments)
        {
          String ln = line.trim();
          // save any header stuff for the user
          if (ln.length() > 0)
          {
            StringBuffer addto = (seqhash.size() > 0) ? footerLines
                    : headerLines;
            addto.append(line);
            addto.append("\n");
          }
        }
      }
    }
    if (seqhash.size() > 0)
    {
      // make the sequence vector
      Enumeration seqid = seqids.elements();
      while (seqid.hasMoreElements())
      {
        String idstring = (String) seqid.nextElement();
        Object[] seqentry = (Object[]) seqhash.get(idstring);
        try
        {
          Sequence newseq = new Sequence(idstring,

          ((StringBuffer) seqentry[0]).toString(),
                  (int) ((long[]) seqentry[1])[0],
                  (int) ((long[]) seqentry[1])[1]);
          if (newseq.getEnd() == 0)
          {
            // assume there are no deletions in the sequence.
            newseq.setEnd(newseq.findPosition(newseq.getLength()));
          }
          seqs.addElement(newseq);
        } catch (Exception e)
        {
          if (warningMessage == null)
          {
            warningMessage = "";
          }
          warningMessage += "Couldn't add Sequence - ID is '" + idstring
                  + "' : Exception was " + e.toString() + "\n";
        }
      }
      // add any annotation
      if (headerLines.length() > 1)
      {
        setAlignmentProperty("HEADER", headerLines.toString());
      }
      if (footerLines.length() > 1)
      {
        setAlignmentProperty("FOOTER", footerLines.toString());
      }
    }
  }

  public String print(SequenceI[] s)
  {
    return new String("Not Implemented.");
  }

  public String print()
  {
    return print(getSeqsAsArray());
  }
}
