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
package jalview.datamodel.xdb.embl;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Vector;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;

public class EmblFile
{
  Vector entries;

  Vector errors;

  /**
   * @return the entries
   */
  public Vector getEntries()
  {
    return entries;
  }

  /**
   * @param entries
   *          the entries to set
   */
  public void setEntries(Vector entries)
  {
    this.entries = entries;
  }

  /**
   * @return the errors
   */
  public Vector getErrors()
  {
    return errors;
  }

  /**
   * @param errors
   *          the errors to set
   */
  public void setErrors(Vector errors)
  {
    this.errors = errors;
  }

  /**
   * Parse an EmblXML file into an EmblFile object
   * 
   * @param file
   * @return parsed EmblXML or null if exceptions were raised
   */
  public static EmblFile getEmblFile(File file)
  {
    if (file == null)
      return null;
    try
    {
      return EmblFile.getEmblFile(new FileReader(file));
    } catch (Exception e)
    {
      System.err.println("Exception whilst reading EMBLfile from " + file);
      e.printStackTrace(System.err);
    }
    return null;
  }

  public static EmblFile getEmblFile(Reader file)
  {
    EmblFile record = new EmblFile();
    try
    {
      // 1. Load the mapping information from the file
      Mapping map = new Mapping(record.getClass().getClassLoader());
      java.net.URL url = record.getClass().getResource("/embl_mapping.xml");
      map.loadMapping(url);

      // 2. Unmarshal the data
      Unmarshaller unmar = new Unmarshaller(record);
      try
      {
        // uncomment to DEBUG EMBLFile reading
        if (((String) jalview.bin.Cache.getDefault(
                jalview.bin.Cache.CASTORLOGLEVEL, "debug"))
                .equalsIgnoreCase("DEBUG"))
          unmar.setDebug(jalview.bin.Cache.log.isDebugEnabled());
      } catch (Exception e)
      {
      }
      ;
      unmar.setIgnoreExtraElements(true);
      unmar.setMapping(map);

      record = (EmblFile) unmar.unmarshal(file);
    } catch (Exception e)
    {
      e.printStackTrace(System.err);
      record = null;
    }

    return record;
  }

  public static void main(String args[])
  {
    File mf = null;
    if (args.length == 1)
    {
      mf = new File(args[0]);
    }
    if (!mf.exists())
    {
      mf = new File(
              "C:\\Documents and Settings\\JimP\\workspace-3.2\\Jalview Release\\schemas\\embleRecordV1.1.xml");
    }
    EmblFile myfile = EmblFile.getEmblFile(mf);
    if (myfile != null && myfile.entries != null
            && myfile.entries.size() > 0)
      System.out.println(myfile.entries.size() + " Records read. (" + mf
              + ")");
  }
}
