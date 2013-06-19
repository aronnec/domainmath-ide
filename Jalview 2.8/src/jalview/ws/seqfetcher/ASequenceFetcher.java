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
package jalview.ws.seqfetcher;

import jalview.datamodel.AlignmentI;
import jalview.datamodel.DBRefEntry;
import jalview.datamodel.SequenceI;
import jalview.util.DBRefUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

public class ASequenceFetcher
{

  /**
   * set of databases we can retrieve entries from
   */
  protected Hashtable<String, Map<String, DbSourceProxy>> FETCHABLEDBS;

  public ASequenceFetcher()
  {
    super();
  }

  /**
   * get list of supported Databases
   * 
   * @return database source string for each database - only the latest version
   *         of a source db is bound to each source.
   */
  public String[] getSupportedDb()
  {
    if (FETCHABLEDBS == null)
      return null;
    String[] sf = new String[FETCHABLEDBS.size()];
    Enumeration e = FETCHABLEDBS.keys();
    int i = 0;
    while (e.hasMoreElements())
    {
      sf[i++] = (String) e.nextElement();
    }
    ;
    return sf;
  }

  public boolean isFetchable(String source)
  {
    Enumeration e = FETCHABLEDBS.keys();
    while (e.hasMoreElements())
    {
      String db = (String) e.nextElement();
      if (source.compareToIgnoreCase(db) == 0)
        return true;
    }
    jalview.bin.Cache.log.warn("isFetchable doesn't know about '" + source
            + "'");
    return false;
  }

  public SequenceI[] getSequences(jalview.datamodel.DBRefEntry[] refs)
  {
    SequenceI[] ret = null;
    Vector<SequenceI> rseqs = new Vector();
    Hashtable<String, List<String>> queries = new Hashtable();
    for (int r = 0; r < refs.length; r++)
    {
      if (!queries.containsKey(refs[r].getSource()))
      {
        queries.put(refs[r].getSource(), new ArrayList<String>());
      }
      List<String> qset = queries.get(refs[r].getSource());
      if (!qset.contains(refs[r].getAccessionId()))
      {
        qset.add(refs[r].getAccessionId());
      }
    }
    Enumeration<String> e = queries.keys();
    while (e.hasMoreElements())
    {
      List<String> query = null;
      String db = null;
      db = e.nextElement();
      query = queries.get(db);
      if (!isFetchable(db))
      {
        reportStdError(db, query, new Exception(
                "Don't know how to fetch from this database :" + db));
        continue;
      }
      Iterator<DbSourceProxy> fetchers = getSourceProxy(db).iterator();
      Stack<String> queriesLeft = new Stack<String>();
      // List<String> queriesFailed = new ArrayList<String>();
      queriesLeft.addAll(query);
      while (fetchers.hasNext())
      {
        List<String> queriesMade = new ArrayList<String>();
        HashSet queriesFound = new HashSet<String>();
        try
        {
          DbSourceProxy fetcher = fetchers.next();
          boolean doMultiple = fetcher.getAccessionSeparator() != null; // No
          // separator
          // - no
          // Multiple
          // Queries
          while (!queriesLeft.isEmpty())
          {
            StringBuffer qsb = new StringBuffer();
            do
            {
              if (qsb.length() > 0)
              {
                qsb.append(fetcher.getAccessionSeparator());
              }
              String q = queriesLeft.pop();
              queriesMade.add(q);
              qsb.append(q);
            } while (doMultiple && !queriesLeft.isEmpty());

            AlignmentI seqset = null;
            try
            {
              // create a fetcher and go to it
              seqset = fetcher.getSequenceRecords(qsb.toString()); // ,
              // queriesFailed);
            } catch (Exception ex)
            {
              System.err.println("Failed to retrieve the following from "
                      + db);
              System.err.println(qsb);
              ex.printStackTrace(System.err);
            }
            // TODO: Merge alignment together - perhaps
            if (seqset != null)
            {
              SequenceI seqs[] = seqset.getSequencesArray();
              if (seqs != null)
              {
                for (int is = 0; is < seqs.length; is++)
                {
                  rseqs.addElement(seqs[is]);
                  DBRefEntry[] frefs = DBRefUtils.searchRefs(seqs[is]
                          .getDBRef(), new DBRefEntry(db, null, null));
                  if (frefs != null)
                  {
                    for (DBRefEntry dbr : frefs)
                    {
                      queriesFound.add(dbr.getAccessionId());
                      queriesMade.remove(dbr.getAccessionId());
                    }
                  }
                  seqs[is] = null;
                }
              }
              else
              {
                if (fetcher.getRawRecords() != null)
                {
                  System.out.println("# Retrieved from " + db + ":"
                          + qsb.toString());
                  StringBuffer rrb = fetcher.getRawRecords();
                  /*
                   * for (int rr = 0; rr<rrb.length; rr++) {
                   */
                  String hdr;
                  // if (rr<qs.length)
                  // {
                  hdr = "# " + db + ":" + qsb.toString();
                  /*
                   * } else { hdr = "# part "+rr; }
                   */
                  System.out.println(hdr);
                  if (rrb != null)
                    System.out.println(rrb);
                  System.out.println("# end of " + hdr);
                }

              }
            }

          }
        } catch (Exception ex)
        {
          reportStdError(db, queriesMade, ex);
        }
        if (queriesMade.size() > 0)
        {
          System.out.println("# Adding " + queriesMade.size()
                  + " ids back to queries list for searching again (" + db
                  + ".");
          queriesLeft.addAll(queriesMade);
        }
      }
    }
    if (rseqs.size() > 0)
    {
      ret = new SequenceI[rseqs.size()];
      Enumeration sqs = rseqs.elements();
      int si = 0;
      while (sqs.hasMoreElements())
      {
        SequenceI s = (SequenceI) sqs.nextElement();
        ret[si++] = s;
        s.updatePDBIds();
      }
    }
    return ret;
  }

  public void reportStdError(String db, List<String> queriesMade,
          Exception ex)
  {

    System.err.println("Failed to retrieve the following references from "
            + db);
    int n = 0;
    for (String qv : queriesMade)
    {
      System.err.print(" " + qv + ";");
      if (n++ > 10)
      {
        System.err.println();
        n = 0;
      }
    }
    System.err.println();
    ex.printStackTrace();
  }

  /**
   * Retrieve an instance of the proxy for the given source
   * 
   * @param db
   *          database source string TODO: add version string/wildcard for
   *          retrieval of specific DB source/version combinations.
   * @return an instance of DbSourceProxy for that db.
   */
  public List<DbSourceProxy> getSourceProxy(String db)
  {
    List<DbSourceProxy> dbs;
    Map<String, DbSourceProxy> dblist = FETCHABLEDBS.get(db);
    if (dblist == null)
    {
      return new ArrayList<DbSourceProxy>();
    }
    ;
    if (dblist.size() > 1)
    {
      DbSourceProxy[] l = dblist.values().toArray(new DbSourceProxy[0]);
      int i = 0;
      String[] nm = new String[l.length];
      // make sure standard dbs appear first, followed by reference das sources, followed by anything else.
      for (DbSourceProxy s : l)
      {
        nm[i++] = ""+s.getTier()+s.getDbName().toLowerCase();
      }
      jalview.util.QuickSort.sort(nm, l);
      dbs = new ArrayList<DbSourceProxy>();
      for (i = l.length - 1; i >= 0; i--)
      {
        dbs.add(l[i]);
      }
    }
    else
    {
      dbs = new ArrayList<DbSourceProxy>(dblist.values());
    }
    return dbs;
  }

  /**
   * constructs and instance of the proxy and registers it as a valid
   * dbrefsource
   * 
   * @param dbSourceProxy
   *          reference for class implementing
   *          jalview.ws.seqfetcher.DbSourceProxy
   * @throws java.lang.IllegalArgumentException
   *           if class does not implement jalview.ws.seqfetcher.DbSourceProxy
   */
  protected void addDBRefSourceImpl(Class dbSourceProxy)
          throws java.lang.IllegalArgumentException
  {
    DbSourceProxy proxy = null;
    try
    {
      Object proxyObj = dbSourceProxy.getConstructor(null)
              .newInstance(null);
      if (!DbSourceProxy.class.isInstance(proxyObj))
      {
        throw new IllegalArgumentException(
                dbSourceProxy.toString()
                        + " does not implement the jalview.ws.seqfetcher.DbSourceProxy");
      }
      proxy = (DbSourceProxy) proxyObj;
    } catch (IllegalArgumentException e)
    {
      throw e;
    } catch (Exception e)
    {
      // Serious problems if this happens.
      throw new Error("DBRefSource Implementation Exception", e);
    }
    addDbRefSourceImpl(proxy);
  }

  /**
   * add the properly initialised DbSourceProxy object 'proxy' to the list of
   * sequence fetchers
   * 
   * @param proxy
   */
  protected void addDbRefSourceImpl(DbSourceProxy proxy)
  {
    if (proxy != null)
    {
      if (FETCHABLEDBS == null)
      {
        FETCHABLEDBS = new Hashtable<String, Map<String, DbSourceProxy>>();
      }
      Map<String, DbSourceProxy> slist = FETCHABLEDBS.get(proxy
              .getDbSource());
      if (slist == null)
      {
        FETCHABLEDBS.put(proxy.getDbSource(),
                slist = new Hashtable<String, DbSourceProxy>());
      }
      slist.put(proxy.getDbName(), proxy);
    }
  }

  /**
   * test if the database handler for dbName contains the given dbProperty when
   * a dbName resolves to a set of proxies - this method will return the result
   * of the test for the first instance. TODO implement additional method to
   * query all sources for a db to find one with a particular property
   * 
   * @param dbName
   * @param dbProperty
   * @return true if proxy has the given property
   */
  public boolean hasDbSourceProperty(String dbName, String dbProperty)
  {
    // TODO: decide if invalidDbName exception is thrown here.

    List<DbSourceProxy> proxies = getSourceProxy(dbName);
    if (proxies != null)
    {
      for (DbSourceProxy proxy : proxies)
      {
        if (proxy.getDbSourceProperties() != null)
        {
          return proxy.getDbSourceProperties().containsKey(dbProperty);
        }
      }
    }
    return false;
  }

  /**
   * select sources which are implemented by instances of the given class
   * 
   * @param class that implements DbSourceProxy
   * @return null or vector of source names for fetchers
   */
  public String[] getDbInstances(Class class1)
  {
    if (!jalview.ws.seqfetcher.DbSourceProxy.class.isAssignableFrom(class1))
    {
      throw new Error(
              "Implmentation Error - getDbInstances must be given a class that implements jalview.ws.seqfetcher.DbSourceProxy (was given '"
                      + class1 + "')");
    }
    if (FETCHABLEDBS == null)
    {
      return null;
    }
    String[] sources = null;
    Vector src = new Vector();
    Enumeration dbs = FETCHABLEDBS.keys();
    while (dbs.hasMoreElements())
    {
      String dbn = (String) dbs.nextElement();
      for (DbSourceProxy dbp : FETCHABLEDBS.get(dbn).values())
      {
        if (class1.isAssignableFrom(dbp.getClass()))
        {
          src.addElement(dbn);
        }
      }
    }
    if (src.size() > 0)
    {
      src.copyInto(sources = new String[src.size()]);
    }
    return sources;
  }

  public DbSourceProxy[] getDbSourceProxyInstances(Class class1)
  {
    ArrayList<DbSourceProxy> prlist = new ArrayList<DbSourceProxy>();
    for (String fetchable : getSupportedDb())
      for (DbSourceProxy pr : getSourceProxy(fetchable))
      {
        if (class1.isInstance(pr))
        {
          prlist.add(pr);
        }
      }
    if (prlist.size() == 0)
    {
      return null;
    }
    return prlist.toArray(new DbSourceProxy[0]);
  }

}
