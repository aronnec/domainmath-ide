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

import jalview.datamodel.*;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class FastaFile extends AlignFile
{
  /**
   * Length of a sequence line
   */
  int len = 72;

  StringBuffer out;

  /**
   * Creates a new FastaFile object.
   */
  public FastaFile()
  {
  }

  /**
   * Creates a new FastaFile object.
   * 
   * @param inFile
   *          DOCUMENT ME!
   * @param type
   *          DOCUMENT ME!
   * 
   * @throws IOException
   *           DOCUMENT ME!
   */
  public FastaFile(String inFile, String type) throws IOException
  {
    super(inFile, type);
  }

  public FastaFile(FileParse source) throws IOException
  {
    super(source);
  }

  /**
   * DOCUMENT ME!
   * 
   * @throws IOException
   *           DOCUMENT ME!
   */
  public void parse() throws IOException
  {
    StringBuffer sb = new StringBuffer();
    boolean firstLine = true;

    String line;
    Sequence seq = null;

    boolean annotation = false;

    while ((line = nextLine()) != null)
    {
      line = line.trim();
      if (line.length() > 0)
      {
        if (line.charAt(0) == '>')
        {
          if (line.startsWith(">#_"))
          {
            if (annotation)
            {
              Annotation[] anots = new Annotation[sb.length()];
              String anotString = sb.toString();
              for (int i = 0; i < sb.length(); i++)
              {
                anots[i] = new Annotation(anotString.substring(i, i + 1),
                        null, ' ', 0);
              }
              AlignmentAnnotation aa = new AlignmentAnnotation(seq
                      .getName().substring(2), seq.getDescription(), anots);

              annotations.addElement(aa);
            }
          }
          else
          {
            annotation = false;
          }

          if (!firstLine)
          {
            seq.setSequence(sb.toString());

            if (!annotation)
            {
              seqs.addElement(seq);
            }
          }

          seq = parseId(line.substring(1));
          firstLine = false;

          sb = new StringBuffer();

          if (line.startsWith(">#_"))
          {
            annotation = true;
          }
        }
        else
        {
          sb.append(line);
        }
      }
    }

    if (annotation)
    {
      Annotation[] anots = new Annotation[sb.length()];
      String anotString = sb.toString();
      for (int i = 0; i < sb.length(); i++)
      {
        anots[i] = new Annotation(anotString.substring(i, i + 1), null,
                ' ', 0);
      }
      AlignmentAnnotation aa = new AlignmentAnnotation(seq.getName()
              .substring(2), seq.getDescription(), anots);

      annotations.addElement(aa);
    }

    else if (!firstLine)
    {
      seq.setSequence(sb.toString());
      seqs.addElement(seq);
    }
  }

  /**
   * called by AppletFormatAdapter to generate an annotated alignment, rather
   * than bare sequences.
   * 
   * @param al
   */
  public void addAnnotations(Alignment al)
  {
    addProperties(al);
    for (int i = 0; i < annotations.size(); i++)
    {
      AlignmentAnnotation aa = (AlignmentAnnotation) annotations
              .elementAt(i);
      aa.setPadGaps(true, al.getGapCharacter());
      al.addAnnotation(aa);
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @param s
   *          DOCUMENT ME!
   * @param len
   *          DOCUMENT ME!
   * @param gaps
   *          DOCUMENT ME!
   * @param displayId
   *          DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String print(SequenceI[] s)
  {
    out = new StringBuffer();
    int i = 0;

    while ((i < s.length) && (s[i] != null))
    {
      out.append(">" + printId(s[i]));
      if (s[i].getDescription() != null)
      {
        out.append(" " + s[i].getDescription());
      }

      out.append(newline);

      int nochunks = (s[i].getLength() / len) + 1;

      for (int j = 0; j < nochunks; j++)
      {
        int start = j * len;
        int end = start + len;

        if (end < s[i].getLength())
        {
          out.append(s[i].getSequenceAsString(start, end) + newline);
        }
        else if (start < s[i].getLength())
        {
          out.append(s[i].getSequenceAsString(start, s[i].getLength())
                  + newline);
        }
      }

      i++;
    }

    return out.toString();
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String print()
  {
    return print(getSeqsAsArray());
  }
}
