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
package jalview.ws.dbsources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import jalview.ws.seqfetcher.DbSourceProxyImpl;

public abstract class EbiFileRetrievedProxy extends DbSourceProxyImpl
{

  /**
   * temp path to retrieved file
   */
  protected String file = null;

  public StringBuffer getRawRecords()
  {
    if (file == null)
      return null;
    StringBuffer bf = null;
    try
    {
      File f = new File(file);
      if (f.exists())
      {
        bf = new StringBuffer();
        BufferedReader breader = new BufferedReader(new FileReader(f));
        String line = null;
        while (breader.ready() && (line = breader.readLine()) != null)
        {
          bf.append(line);
        }
        breader.close();
      }
    } catch (Exception e)
    {
      System.err.println("Warning: problems reading temp file " + file);
      return null;
    }
    return bf;
  }

}
