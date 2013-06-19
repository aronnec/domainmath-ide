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

import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;

import jalview.datamodel.Alignment;
import jalview.datamodel.AlignmentI;

/**
 * Render associated attributes of an alignment. The heart of this code was
 * refactored from jalview.gui.AlignFrame and jalview.appletgui.AlignFrame TODO:
 * consider extending the html renderer to annotate elements with CSS ids
 * enabling finer output format control.
 * 
 */
public class AlignmentProperties
{
  AlignmentI alignment;

  public AlignmentProperties(AlignmentI alignment)
  {
    this.alignment = alignment;
  }

  /**
   * render the alignment's properties report as text or an HTML fragment
   * 
   * @param pw
   * @param html
   */
  public void writeProperties(PrintWriter pw, boolean html)
  {
    final String nl = html ? "<br>" : System.getProperty("line.separator");
    float avg = 0;
    int min = Integer.MAX_VALUE, max = 0;
    for (int i = 0; i < alignment.getHeight(); i++)
    {
      int size = 1 + alignment.getSequenceAt(i).getEnd()
              - alignment.getSequenceAt(i).getStart();
      avg += size;
      if (size > max)
        max = size;
      if (size < min)
        min = size;
    }
    avg = avg / (float) alignment.getHeight();
    pw.print(nl);
    pw.print("Sequences: " + alignment.getHeight());
    pw.print(nl);
    pw.print("Minimum Sequence Length: " + min);
    pw.print(nl);
    pw.print("Maximum Sequence Length: " + max);
    pw.print(nl);
    pw.print("Average Length: " + (int) avg);

    if (((Alignment) alignment).alignmentProperties != null)
    {
      pw.print(nl);
      pw.print(nl);
      if (html)
      {
        pw.print("<table border=\"1\">");
      }
      Hashtable props = ((Alignment) alignment).alignmentProperties;
      Enumeration en = props.keys();
      while (en.hasMoreElements())
      {
        String key = en.nextElement().toString();
        String vals = props.get(key).toString();
        if (html)
        {
          // wrap the text in the table
          StringBuffer val = new StringBuffer();
          int pos = 0, npos;
          do
          {
            npos = vals.indexOf("\n", pos);
            if (npos == -1)
            {
              val.append(vals.substring(pos));
            }
            else
            {
              val.append(vals.substring(pos, npos));
              val.append("<br>");
            }
            pos = npos + 1;
          } while (npos != -1);
          pw.print("<tr><td>" + key + "</td><td>" + val + "</td></tr>");
        }
        else
        {
          pw.print(nl + key + "\t" + vals);
        }
      }
      if (html)
      {
        pw.print("</table>");
      }
    }
  }

  /**
   * generate a report as plain text
   * 
   * @return
   */
  public StringBuffer formatAsString()
  {
    return formatReport(false);
  }

  protected StringBuffer formatReport(boolean html)
  {
    StringWriter content = new StringWriter();
    writeProperties(new PrintWriter(content), html);
    return content.getBuffer();
  }

  /**
   * generate a report as a fragment of html
   * 
   * @return
   */
  public StringBuffer formatAsHtml()
  {
    return formatReport(true);
  }

}
