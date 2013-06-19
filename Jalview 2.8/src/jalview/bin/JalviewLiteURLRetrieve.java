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
package jalview.bin;

import jalview.datamodel.Alignment;
import jalview.io.AppletFormatAdapter;
import jalview.io.FileParse;

import java.applet.Applet;
import java.io.InputStream;

public class JalviewLiteURLRetrieve extends Applet
{

  private static final long serialVersionUID = 1L;

  /**
   * This is the default constructor
   */
  public JalviewLiteURLRetrieve()
  {
    super();
  }

  /**
   * This method initializes this
   * 
   * @return void
   */
  public void init()
  {
    this.setSize(300, 200);
    String file = getParameter("file");
    if (file == null)
    {
      System.out
              .println("Specify a resource to read on the file parameter");
      return;
    }
    String protocol = null;
    try
    {
      System.out.println("Loading thread started with:\n>>file\n" + file
              + ">>endfile");
      // This might throw a security exception in certain browsers
      // Netscape Communicator for instance.
      try
      {
        boolean rtn = false;
        InputStream is = getClass().getResourceAsStream("/" + file);
        if (is != null)
        {
          rtn = true;
          is.close();
        }
        System.err.println("Resource '" + file + "' was "
                + (rtn ? "" : "not") + " located by classloader.");
        if (rtn)
        {
          protocol = AppletFormatAdapter.CLASSLOADER;
        }

      } catch (Exception ex)
      {
        System.out.println("Exception checking resources: " + file + " "
                + ex);
      }
      if (file.indexOf("://") > -1)
      {
        protocol = AppletFormatAdapter.URL;
      }
      else
      {
        // skipping codebase prepend check.
        protocol = AppletFormatAdapter.FILE;
      }

      System.out.println("Trying to get contents of resource:");
      FileParse fp = new FileParse(file, protocol);
      if (fp.isValid())
      {
        String ln = null;
        while ((ln = fp.nextLine()) != null)
        {
          System.out.print(ln);
        }
        fp.close();
      }
      else
      {
        System.out.println("Resource at " + file
                + " cannot be read with protocol==" + protocol);
        return;
      }
      String format = getParameter("format");
      if (format == null || format.length() == 0)
      {
        format = new jalview.io.IdentifyFile().Identify(file, protocol);
        System.out.println("Format is " + format);
      }
      else
      {
        System.out.println("User specified Format is " + format);
      }
      Alignment al = null;
      try
      {
        al = new AppletFormatAdapter().readFile(file, protocol, format);
      } catch (java.io.IOException ex)
      {
        System.err.println("Failed to open the file.");
        ex.printStackTrace();
      }
      if (al != null)
      {
        System.out.println(new AppletFormatAdapter().formatSequences(
                "FASTA", al, false));
      }
    } catch (Exception e)
    {
      System.err.println("bailing out : Unexpected exception:");
      e.printStackTrace();
    }
  }

}
