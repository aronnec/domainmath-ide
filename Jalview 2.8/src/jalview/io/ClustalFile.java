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
import jalview.util.*;

public class ClustalFile extends AlignFile
{

  public ClustalFile()
  {
  }

  public ClustalFile(String inFile, String type) throws IOException
  {
    super(inFile, type);
  }

  public ClustalFile(FileParse source) throws IOException
  {
    super(source);
  }

  public void initData()
  {
    super.initData();
  }

  public void parse() throws IOException
  {
    int i = 0;
    boolean flag = false;
    boolean rna = false;
    boolean top = false;
    StringBuffer pssecstr = new StringBuffer(), consstr = new StringBuffer();
    Vector headers = new Vector();
    Hashtable seqhash = new Hashtable();
    StringBuffer tempseq;
    String line, id;
    StringTokenizer str;

    try
    {
      while ((line = nextLine()) != null)
      {
        if (line.length() == 0)
        {
          top = true;
        }
        if (line.indexOf(" ") != 0)
        {
          str = new StringTokenizer(line, " ");

          if (str.hasMoreTokens())
          {
            id = str.nextToken();

            if (id.equalsIgnoreCase("CLUSTAL"))
            {
              flag = true;
            }
            else
            {
              if (flag)
              {
                if (seqhash.containsKey(id))
                {
                  tempseq = (StringBuffer) seqhash.get(id);
                }
                else
                {
                  tempseq = new StringBuffer();
                  seqhash.put(id, tempseq);
                }

                if (!(headers.contains(id)))
                {
                  headers.addElement(id);
                }

                if (str.hasMoreTokens())
                {
                  tempseq.append(str.nextToken());
                }
                top = false;
              }
            }
          }
          else
          {
            flag = true;
          }
        }
        else
        {
          if (line.matches("\\s+(-|\\.|\\(|\\[|\\]|\\))+"))
          {
            if (top)
            {
              pssecstr.append(line.trim());
            }
            else
            {
              consstr.append(line.trim());
            }
          }
        }
      }
    } catch (IOException e)
    {
      System.err.println("Exception parsing clustal file " + e);
      e.printStackTrace();
    }

    if (flag)
    {
      this.noSeqs = headers.size();

      // Add sequences to the hash
      for (i = 0; i < headers.size(); i++)
      {
        if (seqhash.get(headers.elementAt(i)) != null)
        {
          if (maxLength < seqhash.get(headers.elementAt(i)).toString()
                  .length())
          {
            maxLength = seqhash.get(headers.elementAt(i)).toString()
                    .length();
          }

          Sequence newSeq = parseId(headers.elementAt(i).toString());
          newSeq.setSequence(seqhash.get(headers.elementAt(i).toString())
                  .toString());

          seqs.addElement(newSeq);
        }
        else
        {
          System.err
                  .println("Clustal File Reader: Can't find sequence for "
                          + headers.elementAt(i));
        }
      }
      AlignmentAnnotation lastssa = null;
      if (pssecstr.length() == maxLength)
      {
        Vector ss = new Vector();
        AlignmentAnnotation ssa = lastssa = StockholmFile
                .parseAnnotationRow(ss, "secondary structure",
                        pssecstr.toString());
        ssa.label = "Secondary Structure";
        annotations.addElement(ssa);
      }
      if (consstr.length() == maxLength)
      {
        Vector ss = new Vector();
        AlignmentAnnotation ssa = StockholmFile.parseAnnotationRow(ss,
                "secondary structure", consstr.toString());
        ssa.label = "Consensus Secondary Structure";
        if (lastssa == null
                || !lastssa.getRNAStruc().equals(
                        ssa.getRNAStruc().replace('-', '.')))
        {
          annotations.addElement(ssa);
        }
      }
    }
  }

  public String print()
  {
    return print(getSeqsAsArray());
    // TODO: locaRNA style aln output
  }

  public String print(SequenceI[] s)
  {
    StringBuffer out = new StringBuffer("CLUSTAL" + newline + newline);

    int max = 0;
    int maxid = 0;

    int i = 0;

    while ((i < s.length) && (s[i] != null))
    {
      String tmp = printId(s[i]);

      if (s[i].getSequence().length > max)
      {
        max = s[i].getSequence().length;
      }

      if (tmp.length() > maxid)
      {
        maxid = tmp.length();
      }

      i++;
    }

    if (maxid < 15)
    {
      maxid = 15;
    }

    maxid++;

    int len = 60;
    int nochunks = (max / len) + 1;

    for (i = 0; i < nochunks; i++)
    {
      int j = 0;

      while ((j < s.length) && (s[j] != null))
      {
        out.append(new Format("%-" + maxid + "s").form(printId(s[j]) + " "));

        int start = i * len;
        int end = start + len;

        if ((end < s[j].getSequence().length)
                && (start < s[j].getSequence().length))
        {
          out.append(s[j].getSequenceAsString(start, end));
        }
        else
        {
          if (start < s[j].getSequence().length)
          {
            out.append(s[j].getSequenceAsString().substring(start));
          }
        }

        out.append(newline);
        j++;
      }

      out.append(newline);
    }

    return out.toString();
  }
}
