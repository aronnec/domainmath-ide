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
package jalview.ws.dbsources.das.datamodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.biodas.jdas.client.SequenceClient;
import org.biodas.jdas.client.adapters.sequence.DasSequenceAdapter;
import org.biodas.jdas.client.threads.MultipleConnectionPropertyProviderI;
import org.biodas.jdas.client.threads.SequenceClientMultipleSources;
import org.biodas.jdas.schema.sequence.SEQUENCE;
import org.biodas.jdas.schema.sources.COORDINATES;
import org.biodas.jdas.schema.sources.SOURCE;
import org.biodas.jdas.schema.sources.VERSION;

import com.stevesoft.pat.Regex;

import jalview.ws.dbsources.das.api.jalviewSourceI;
import jalview.ws.seqfetcher.*;
import jalview.bin.Cache;
import jalview.datamodel.Alignment;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.DBRefEntry;
import jalview.datamodel.Sequence;
import jalview.datamodel.SequenceI;

/**
 * an instance of this class is created for each unique DAS Sequence source (ie
 * one capable of handling the 'sequence' for a particular MapMaster)
 * 
 * @author JimP
 * 
 */
public class DasSequenceSource extends DbSourceProxyImpl implements
        DbSourceProxy
{
  private jalviewSourceI jsrc;

  protected SOURCE source = null;

  protected VERSION version = null;

  protected COORDINATES coordsys = null;

  protected String dbname = "DASCS";

  protected String dbrefname = "das:source";

  protected MultipleConnectionPropertyProviderI connprops = null;

  /**
   * DAS sources are tier 1 - if we have a direct DB connection then we should prefer it
   */
  private int tier=1;

  /**
   * create a new DbSource proxy for a DAS 1 source
   * 
   * @param dbnbame
   *          Human Readable Name to use when fetching from this source
   * @param dbrefname
   *          DbRefName for DbRefs attached to sequences retrieved from this
   *          source
   * @param source
   *          Das1Source
   * @param coordsys
   *          specific coordinate system to use for this source
   * @throws Exception
   *           if source is not capable of the 'sequence' command
   */
  public DasSequenceSource(String dbname, String dbrefname, SOURCE source,
          VERSION version, COORDINATES coordsys,
          MultipleConnectionPropertyProviderI connprops) throws Exception
  {
    if (!(jsrc = new JalviewSource(source, connprops, false))
            .isSequenceSource())
    {
      throw new Exception("Source " + source.getTitle()
              + " does not support the sequence command.");
    }
    this.tier = 1+((jsrc.isLocal() || jsrc.isReferenceSource()) ? 0 : 1);
    this.source = source;
    this.dbname = dbname;
    this.dbrefname = dbrefname.toUpperCase();
    if (coordsys != null)
    {
      this.coordsys = coordsys;
    }
    this.connprops = connprops;
  }

  public String getAccessionSeparator()
  {
    return "\t";
  }

  public Regex getAccessionValidator()
  {
    /** ? * */
    return Regex.perlCode("m/([^:]+)(:\\d+,\\d+)?/");
  }

  public String getDbName()
  {
    // TODO: map to
    return dbname + " (DAS)";
  }

  public String getDbSource()
  {
    return dbrefname;
  }

  public String getDbVersion()
  {
    return coordsys != null ? coordsys.getVersion() : "";
  }

  public AlignmentI getSequenceRecords(String queries) throws Exception
  {
    StringTokenizer st = new StringTokenizer(queries, "\t");
    List<String> toks = new ArrayList<String>(), src = new ArrayList<String>(), acIds = new ArrayList<String>();
    while (st.hasMoreTokens())
    {
      String t;
      toks.add(t = st.nextToken());
      acIds.add(t.replaceAll(":[0-9,]+", ""));
    }
    src.add(jsrc.getSourceURL());
    Map<String, Map<List<String>, DasSequenceAdapter>> resultset = new HashMap<String, Map<List<String>, DasSequenceAdapter>>();
    Map<String, Map<List<String>, Exception>> errors = new HashMap<String, Map<List<String>, Exception>>();

    // First try multiple sources
    boolean multiple = true, retry = false;
    do
    {
      if (!multiple)
      {
        retry = false;
        // slow, fetch one at a time.
        for (String sr : src)
        {
          System.err
                  .println("Retrieving IDs individually from das source: "
                          + sr);
          org.biodas.jdas.client.SequenceClient sq = new SequenceClient(
                  connprops.getConnectionPropertyProviderFor(sr));
          for (String q : toks)
          {
            List<String> qset = Arrays.asList(new String[]
            { q });
            try
            {
              DasSequenceAdapter s = sq.fetchData(sr, qset);
              Map<List<String>, DasSequenceAdapter> dss = resultset.get(sr);
              if (dss == null)
              {
                resultset
                        .put(sr,
                                dss = new HashMap<List<String>, DasSequenceAdapter>());
              }
              dss.put(qset, s);
            } catch (Exception x)
            {
              Map<List<String>, Exception> ers = errors.get(sr);
              if (ers == null)
              {
                errors.put(sr, ers = new HashMap<List<String>, Exception>());
              }
              ers.put(qset, x);
            }
          }
        }
      }
      else
      {
        SequenceClientMultipleSources sclient;
        sclient = new SequenceClientMultipleSources();
        sclient.fetchData(src, toks, resultset, errors);
        sclient.shutDown();
        while (!sclient.isTerminated())
        {
          try
          {
            Thread.sleep(200);

          } catch (InterruptedException x)
          {
          }
        }
        if (resultset.isEmpty() && !errors.isEmpty())
        {
          retry = true;
          multiple = false;
        }
      }
    } while (retry);

    if (resultset.isEmpty())
    {
      System.err.println("Sequence Query to " + jsrc.getTitle() + " with '"
              + queries + "' returned no sequences.");
      return null;
    }
    else
    {
      Vector<SequenceI> seqs = null;
      for (Map.Entry<String, Map<List<String>, DasSequenceAdapter>> resset : resultset
              .entrySet())
      {
        for (Map.Entry<List<String>, DasSequenceAdapter> result : resset
                .getValue().entrySet())
        {
          DasSequenceAdapter dasseqresp = result.getValue();
          List<String> accessions = result.getKey();
          for (SEQUENCE e : dasseqresp.getSequence())
          {
            String lbl = e.getId();

            if (acIds.indexOf(lbl) == -1)
            {
              System.err
                      .println("Warning - received sequence event for strange accession code ("
                              + lbl + ")");
            }
            else
            {
              if (seqs == null)
              {
                if (e.getContent().length() == 0)
                {
                  System.err
                          .println("Empty sequence returned for accession code ("
                                  + lbl
                                  + ") from "
                                  + resset.getKey()
                                  + " (source is " + getDbName());
                  continue;
                }
              }
              seqs = new java.util.Vector<SequenceI>();
              // JDAS returns a sequence complete with any newlines and spaces
              // in the XML
              Sequence sq = new Sequence(lbl, e.getContent().replaceAll(
                      "\\s+", ""));
              sq.setStart(e.getStart().intValue());
              sq.addDBRef(new DBRefEntry(getDbSource(), getDbVersion()
                      + ":" + e.getVersion(), lbl));
              seqs.addElement(sq);
            }
          }
        }
      }

      if (seqs == null || seqs.size() == 0)
        return null;
      SequenceI[] sqs = new SequenceI[seqs.size()];
      for (int i = 0, iSize = seqs.size(); i < iSize; i++)
      {
        sqs[i] = (SequenceI) seqs.elementAt(i);
      }
      Alignment al = new Alignment(sqs);
      if (jsrc.isFeatureSource())
      {
        java.util.Vector<jalviewSourceI> srcs = new java.util.Vector<jalviewSourceI>();
        srcs.addElement(jsrc);
        try
        {
          jalview.ws.DasSequenceFeatureFetcher dssf = new jalview.ws.DasSequenceFeatureFetcher(
                  sqs, null, srcs, false, false, multiple);
          while (dssf.isRunning())
          {
            try
            {
              Thread.sleep(200);
            } catch (InterruptedException x)
            {

            }
          }

        } catch (Exception x)
        {
          Cache.log
                  .error("Couldn't retrieve features for sequence from its source.",
                          x);
        }
      }

      return al;
    }
  }

  public String getTestQuery()
  {
    return coordsys == null ? "" : coordsys.getTestRange();
  }

  public boolean isValidReference(String accession)
  {
    // TODO try to validate an accession against source
    // We don't really know how to do this without querying source

    return true;
  }

  /**
   * @return the source
   */
  public SOURCE getSource()
  {
    return source;
  }

  /**
   * @return the coordsys
   */
  public COORDINATES getCoordsys()
  {
    return coordsys;
  }

  @Override
  public int getTier()
  {
    return tier;
  }
}
