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
package jalview.ws.ebi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class EBIFetchClient
{
  String format = "default";

  String style = "raw";

  /**
   * Creates a new EBIFetchClient object.
   */
  public EBIFetchClient()
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String[] getSupportedDBs()
  {
    // TODO - implement rest call for dbfetch getSupportedDBs
    throw new Error("Not yet implemented");
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String[] getSupportedFormats()
  {
    // TODO - implement rest call for dbfetch getSupportedFormats
    throw new Error("Not yet implemented");
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String[] getSupportedStyles()
  {
    // TODO - implement rest call for dbfetch getSupportedStyles
    throw new Error("Not yet implemented");
  }

  public File fetchDataAsFile(String ids, String f, String s)
          throws OutOfMemoryError
  {
    File outFile = null;
    try
    {
      outFile = File.createTempFile("jalview", ".xml");
      outFile.deleteOnExit();
      fetchData(ids, f, s, outFile);
      if (outFile.length() == 0)
      {
        outFile.delete();
        return null;
      }
    } catch (Exception ex)
    {
    }
    return outFile;
  }

  /**
   * Single DB multiple record retrieval
   * 
   * @param ids
   *          db:query1;query2;query3
   * @param f
   *          raw/xml
   * @param s
   *          ?
   * 
   * @return Raw string array result of query set
   */
  public String[] fetchData(String ids, String f, String s)
          throws OutOfMemoryError
  {
    return fetchData(ids, f, s, null);
  }

  public String[] fetchData(String ids, String f, String s, File outFile)
          throws OutOfMemoryError
  {
    // Need to split
    // ids of the form uniprot:25KD_SARPE;ADHR_DROPS;
    String[] rslts = new String[0];
    StringTokenizer queries = new StringTokenizer(ids, ";");
    String db = null;
    StringBuffer querystring = null;
    int nq = 0;
    while (queries.hasMoreTokens())
    {
      String query = queries.nextToken();
      int p;
      if ((p = query.indexOf(':')) > -1)
      {
        db = query.substring(0, p);
        query = query.substring(p + 1);
      }
      if (querystring == null)
      {
        querystring = new StringBuffer(query);
        nq++;
      }
      else
      {
        querystring.append("," + query);
        nq++;
      }
    }
    if (db == null)
    {
      System.err.println("Invalid Query string : '" + ids
              + "'\nShould be of form 'dbname:q1;q2;q3;q4'");
      return null;
    }
    String[] rslt = fetchBatch(querystring.toString(), db, f, s, outFile);
    if (rslt != null)
    {
      String[] nrslts = new String[rslt.length + rslts.length];
      System.arraycopy(rslts, 0, nrslts, 0, rslts.length);
      System.arraycopy(rslt, 0, nrslts, rslts.length, rslt.length);
      rslts = nrslts;
    }

    return (rslts.length == 0 ? null : rslts);
  }

  public String[] fetchBatch(String ids, String db, String f, String s,
          File outFile) throws OutOfMemoryError
  {
    long time = System.currentTimeMillis();
    // max 200 ids can be added at one time
    try
    {
      URL rcall = new URL("http://www.ebi.ac.uk/Tools/dbfetch/dbfetch/"
              + db.toLowerCase() + "/" + ids.toLowerCase()
              + (f != null ? "/" + f : ""));

      BufferedInputStream is = new BufferedInputStream(rcall.openStream());
      if (outFile != null)
      {
        FileOutputStream fio = new FileOutputStream(outFile);
        byte[] bb = new byte[32 * 1024];
        int l;
        while ((l = is.read(bb)) > 0)
        {
          fio.write(bb, 0, l);
        }
        fio.close();
        is.close();
      }
      else
      {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String rtn;
        ArrayList<String> arl = new ArrayList<String>();
        while ((rtn = br.readLine()) != null)
        {
          arl.add(rtn);
        }
        return arl.toArray(new String[arl.size()]);
      }
    } catch (OutOfMemoryError er)
    {

      System.out.println("OUT OF MEMORY DOWNLOADING QUERY FROM " + db
              + ":\n" + ids);
      throw er;
    } catch (Exception ex)
    {
      if (ex.getMessage().startsWith(
              "uk.ac.ebi.jdbfetch.exceptions.DbfNoEntryFoundException"))
      {
        return null;
      }
      System.err.println("Unexpected exception when retrieving from " + db
              + "\nQuery was : '" + ids + "'");
      ex.printStackTrace(System.err);
      return null;
    } finally
    {
      //System.err.println("Took " + (System.currentTimeMillis() - time)
      //        / 1000 + " secs for one call.");
    }
    return null;
  }
}

