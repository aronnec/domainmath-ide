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
package jalview.ws;

import jalview.bin.Cache;
import jalview.datamodel.DBRefEntry;
import jalview.datamodel.SequenceFeature;
import jalview.datamodel.SequenceI;
import jalview.gui.AlignFrame;
import jalview.gui.Desktop;
import jalview.gui.FeatureSettings;
import jalview.util.UrlLink;
import jalview.ws.dbsources.das.api.DasSourceRegistryI;
import jalview.ws.dbsources.das.api.jalviewSourceI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.biodas.jdas.client.FeaturesClient;
import org.biodas.jdas.client.adapters.features.DasGFFAdapter;
import org.biodas.jdas.client.adapters.features.DasGFFAdapter.GFFAdapter;
import org.biodas.jdas.client.threads.FeaturesClientMultipleSources;
import org.biodas.jdas.schema.features.ERRORSEGMENT;
import org.biodas.jdas.schema.features.FEATURE;
import org.biodas.jdas.schema.features.LINK;
import org.biodas.jdas.schema.features.SEGMENT;
import org.biodas.jdas.schema.features.TYPE;
import org.biodas.jdas.schema.features.UNKNOWNFEATURE;
import org.biodas.jdas.schema.features.UNKNOWNSEGMENT;
import org.biodas.jdas.schema.sources.COORDINATES;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class DasSequenceFeatureFetcher
{
  SequenceI[] sequences;

  AlignFrame af;

  FeatureSettings fsettings;

  StringBuffer sbuffer = new StringBuffer();

  List<jalviewSourceI> selectedSources;

  boolean cancelled = false;

  private void debug(String mesg)
  {
    debug(mesg, null);
  }

  private void debug(String mesg, Exception e)
  {
    if (Cache.log != null)
    {
      Cache.log.debug(mesg, e);
    }
    else
    {
      System.err.println(mesg);
      if (e != null)
      {
        e.printStackTrace();
      }
    }
  }

  long startTime;

  private DasSourceRegistryI sourceRegistry;

  private boolean useJDASMultiThread = true;

  /**
   * Creates a new SequenceFeatureFetcher object. Uses default
   * 
   * @param align
   *          DOCUMENT ME!
   * @param ap
   *          DOCUMENT ME!
   */
  public DasSequenceFeatureFetcher(SequenceI[] sequences,
          FeatureSettings fsettings, Vector selectedSources)
  {
    this(sequences, fsettings, selectedSources, true, true, true);
  }

  public DasSequenceFeatureFetcher(SequenceI[] oursequences,
          FeatureSettings fsettings, List<jalviewSourceI> selectedSources2,
          boolean checkDbrefs, boolean promptFetchDbrefs)
  {
    this(oursequences, fsettings, selectedSources2, checkDbrefs,
            promptFetchDbrefs, true);
  }

  public DasSequenceFeatureFetcher(SequenceI[] oursequences,
          FeatureSettings fsettings, List<jalviewSourceI> selectedSources2,
          boolean checkDbrefs, boolean promptFetchDbrefs,
          boolean useJDasMultiThread)
  {
    this.useJDASMultiThread = useJDasMultiThread;
    this.selectedSources = new ArrayList<jalviewSourceI>();
    // filter both sequences and sources to eliminate duplicates
    for (jalviewSourceI src : selectedSources2)
    {
      if (!selectedSources.contains(src))
      {
        selectedSources.add(src);
      }
      ;
    }
    Vector sqs = new Vector();
    for (int i = 0; i < oursequences.length; i++)
    {
      if (!sqs.contains(oursequences[i]))
      {
        sqs.addElement(oursequences[i]);
      }
    }
    sequences = new SequenceI[sqs.size()];
    for (int i = 0; i < sequences.length; i++)
    {
      sequences[i] = (SequenceI) sqs.elementAt(i);
    }
    if (fsettings != null)
    {
      this.fsettings = fsettings;
      this.af = fsettings.af;
      af.setShowSeqFeatures(true);
    }
    int uniprotCount = 0;
    for (jalviewSourceI source : selectedSources)
    {
      for (COORDINATES coords : source.getVersion().getCOORDINATES())
      {
        // TODO: match UniProt coord system canonically (?) - does
        // UniProt==uniprot==UNIPROT ?
        if (coords.getAuthority().toLowerCase().equals("uniprot"))
        {
          uniprotCount++;
          break;
        }
      }
    }

    int refCount = 0;
    for (int i = 0; i < sequences.length; i++)
    {
      DBRefEntry[] dbref = sequences[i].getDBRef();
      if (dbref != null)
      {
        for (int j = 0; j < dbref.length; j++)
        {
          if (dbref[j].getSource().equals(
                  jalview.datamodel.DBRefSource.UNIPROT))
          {
            refCount++;
            break;
          }
        }
      }
    }

    if (checkDbrefs && refCount < sequences.length && uniprotCount > 0)
    {

      int reply = JOptionPane.YES_OPTION;
      if (promptFetchDbrefs)
      {
        reply = JOptionPane
                .showInternalConfirmDialog(
                        Desktop.desktop,
                        "Do you want Jalview to find\n"
                                + "Uniprot Accession ids for given sequence names?",
                        "Find Uniprot Accession Ids",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
      }

      if (reply == JOptionPane.YES_OPTION)
      {
        Thread thread = new Thread(new FetchDBRefs());
        thread.start();
      }
      else
      {
        _startFetching();
      }
    }
    else
    {
      _startFetching();
    }

  }

  private void _startFetching()
  {
    running = true;
    new Thread(new FetchSeqFeatures()).start();
  }

  class FetchSeqFeatures implements Runnable
  {
    public void run()
    {
      startFetching();
      setGuiFetchComplete();
    }
  }

  class FetchDBRefs implements Runnable
  {
    public void run()
    {
      running = true;
      new DBRefFetcher(sequences, af).fetchDBRefs(true);
      startFetching();
      setGuiFetchComplete();
    }
  }

  /**
   * Spawns Fetcher threads to add features to sequences in the dataset
   */
  void startFetching()
  {
    running = true;
    cancelled = false;
    startTime = System.currentTimeMillis();
    if (af != null)
    {
      af.setProgressBar("Fetching DAS Sequence Features", startTime);
    }
    if (sourceRegistry == null)
    {
      sourceRegistry = Cache.getDasSourceRegistry();
    }
    if (selectedSources == null || selectedSources.size() == 0)
    {
      try
      {
        jalviewSourceI[] sources = sourceRegistry.getSources().toArray(
                new jalviewSourceI[0]);
        String active = jalview.bin.Cache.getDefault("DAS_ACTIVE_SOURCE",
                "uniprot");
        StringTokenizer st = new StringTokenizer(active, "\t");
        selectedSources = new Vector();
        String token;
        while (st.hasMoreTokens())
        {
          token = st.nextToken();
          for (int i = 0; i < sources.length; i++)
          {
            if (sources[i].getTitle().equals(token))
            {
              selectedSources.add(sources[i]);
              break;
            }
          }
        }
      } catch (Exception ex)
      {
        debug("Exception whilst setting default feature sources from registry and local preferences.",
                ex);
      }
    }

    if (selectedSources == null || selectedSources.size() == 0)
    {
      System.out.println("No DAS Sources active");
      cancelled = true;
      setGuiNoDassourceActive();
      return;
    }

    sourcesRemaining = selectedSources.size();
    FeaturesClientMultipleSources fc = new FeaturesClientMultipleSources();
    fc.setConnProps(sourceRegistry.getSessionHandler());
    // Now sending requests one at a time to each server
    ArrayList<jalviewSourceI> srcobj = new ArrayList<jalviewSourceI>();
    ArrayList<String> src = new ArrayList<String>();
    List<List<String>> ids = new ArrayList<List<String>>();
    List<List<DBRefEntry>> idobj = new ArrayList<List<DBRefEntry>>();
    List<Map<String, SequenceI>> sqset = new ArrayList<Map<String, SequenceI>>();
    for (jalviewSourceI _sr : selectedSources)
    {

      Map<String, SequenceI> slist = new HashMap<String, SequenceI>();
      List<DBRefEntry> idob = new ArrayList<DBRefEntry>();
      List<String> qset = new ArrayList<String>();

      for (SequenceI seq : sequences)
      {
        Object[] idset = nextSequence(_sr, seq);
        if (idset != null)
        {
          List<DBRefEntry> _idob = (List<DBRefEntry>) idset[0];
          List<String> _qset = (List<String>) idset[1];
          if (_idob.size() > 0)
          {
            // add sequence's ref for each id derived from it
            // (space inefficient, but most unambiguous)
            // could replace with hash with _qset values as keys.
            Iterator<DBRefEntry> dbobj = _idob.iterator();
            for (String q : _qset)
            {
              SequenceI osq = slist.get(q);
              DBRefEntry dr = dbobj.next();
              if (osq != null && osq != seq)
              {
                // skip - non-canonical query
              }
              else
              {
                idob.add(dr);
                qset.add(q);
                slist.put(q, seq);
              }
            }
          }
        }
      }
      if (idob.size() > 0)
      {
        srcobj.add(_sr);
        src.add(_sr.getSourceURL());
        ids.add(qset);
        idobj.add(idob);
        sqset.add(slist);
      }
    }
    Map<String, Map<List<String>, Exception>> errors = new HashMap<String, Map<List<String>, Exception>>();
    Map<String, Map<List<String>, DasGFFAdapter>> results = new HashMap<String, Map<List<String>, DasGFFAdapter>>();
    if (!useJDASMultiThread)
    {
      Iterator<String> sources = src.iterator();
      // iterate over each query for each source and do each one individually
      for (List<String> idl : ids)
      {
        String source = sources.next();
        FeaturesClient featuresc = new FeaturesClient(sourceRegistry
                .getSessionHandler().getConnectionPropertyProviderFor(
                        source));
        for (String id : idl)
        {
          List<String> qid = Arrays.asList(new String[]
          { id });
          try
          {
            DasGFFAdapter dga = featuresc.fetchData(source, qid);
            Map<List<String>, DasGFFAdapter> ers = results.get(source);
            if (ers == null)
            {
              results.put(source,
                      ers = new HashMap<List<String>, DasGFFAdapter>());
            }
            ers.put(qid, dga);
          } catch (Exception ex)
          {
            Map<List<String>, Exception> ers = errors.get(source);
            if (ers == null)
            {
              errors.put(source,
                      ers = new HashMap<List<String>, Exception>());
            }
            ers.put(qid, ex);
          }
        }
      }
    }
    else
    {
      // pass them all at once
      fc.fetchData(src, ids, false, results, errors);
      fc.shutDown();
      while (!fc.isTerminated())
      {
        try
        {
          Thread.sleep(200);
        } catch (InterruptedException x)
        {

        }
      }
    }
    Iterator<List<String>> idset = ids.iterator();
    Iterator<List<DBRefEntry>> idobjset = idobj.iterator();
    Iterator<Map<String, SequenceI>> seqset = sqset.iterator();
    for (jalviewSourceI source : srcobj)
    {
      processResponse(seqset.next(), source, idset.next(), idobjset.next(),
              results.get(source.getSourceURL()),
              errors.get(source.getSourceURL()));
    }
  }

  private void processResponse(Map<String, SequenceI> sequencemap,
          jalviewSourceI jvsource, List<String> ids,
          List<DBRefEntry> idobj, Map<List<String>, DasGFFAdapter> results,
          Map<List<String>, Exception> errors)
  {
    Set<SequenceI> sequences = new HashSet<SequenceI>();
    String source = jvsource.getSourceURL();
    // process features
    DasGFFAdapter result = (results == null) ? null : results.get(ids);
    Exception error = (errors == null) ? null : errors.get(ids);
    if (result == null)
    {
      debug("das source " + source + " could not be contacted. "
              + (error == null ? "" : error.toString()));
    }
    else
    {

      GFFAdapter gff = result.getGFF();
      List<SEGMENT> segments = gff.getSegments();
      List<ERRORSEGMENT> errorsegs = gff.getErrorSegments();
      List<UNKNOWNFEATURE> unkfeats = gff.getUnknownFeatures();
      List<UNKNOWNSEGMENT> unksegs = gff.getUnknownSegments();
      debug("das source " + source + " returned " + gff.getTotal()
              + " responses. " + (errorsegs != null ? errorsegs.size() : 0)
              + " were incorrect segment queries, "
              + (unkfeats != null ? unkfeats.size() : 0)
              + " were unknown features "
              + (unksegs != null ? unksegs.size() : 0)
              + " were unknown segments and "
              + (segments != null ? segments.size() : 0)
              + " were segment responses.");
      Iterator<DBRefEntry> dbr = idobj.iterator();
      if (segments != null)
      {
        for (SEGMENT seg : segments)
        {
          String id = seg.getId();
          if (ids.indexOf(id) == -1)
          {
            id = id.toUpperCase();
          }
          DBRefEntry dbref = idobj.get(ids.indexOf(id));
          SequenceI sequence = sequencemap.get(id);
          boolean added = false;
          sequences.add(sequence);

          for (FEATURE feat : seg.getFEATURE())
          {
            // standard DAS feature-> jalview sequence feature transformation
            SequenceFeature f = newSequenceFeature(feat,
                    jvsource.getTitle());
            if (!parseSeqFeature(sequence, f, feat, jvsource))
            {
              if (dbref.getMap() != null && f.getBegin() > 0
                      && f.getEnd() > 0)
              {
                debug("mapping from " + f.getBegin() + " - " + f.getEnd());
                SequenceFeature vf[] = null;

                try
                {
                  vf = dbref.getMap().locateFeature(f);
                } catch (Exception ex)
                {
                  Cache.log
                          .info("Error in 'experimental' mapping of features. Please try to reproduce and then report info to jalview-discuss@jalview.org.");
                  Cache.log.info("Mapping feature from " + f.getBegin()
                          + " to " + f.getEnd() + " in dbref "
                          + dbref.getAccessionId() + " in "
                          + dbref.getSource());
                  Cache.log.info("using das Source " + source);
                  Cache.log.info("Exception", ex);
                }

                if (vf != null)
                {
                  for (int v = 0; v < vf.length; v++)
                  {
                    debug("mapping to " + v + ": " + vf[v].getBegin()
                            + " - " + vf[v].getEnd());
                    sequence.addSequenceFeature(vf[v]);
                  }
                }
              }
              else
              {
                sequence.addSequenceFeature(f);
              }
            }
          }
        }
        featuresAdded(sequences);
      }
      else
      {
        // System.out.println("No features found for " + seq.getName()
        // + " from: " + e.getDasSource().getNickname());
      }
    }
  }

  private void setGuiNoDassourceActive()
  {

    if (af != null)
    {
      af.setProgressBar("No DAS Sources Active", startTime);
    }
    if (getFeatSettings() != null)
    {
      fsettings.noDasSourceActive();
    }
  }

  /**
   * Update our fsettings dialog reference if we didn't have one when we were
   * first initialised.
   * 
   * @return fsettings
   */
  private FeatureSettings getFeatSettings()
  {
    if (fsettings == null)
    {
      if (af != null)
      {
        fsettings = af.featureSettings;
      }
    }
    return fsettings;
  }

  public void cancel()
  {
    if (af != null)
    {
      af.setProgressBar("DAS Feature Fetching Cancelled", startTime);
    }
    cancelled = true;
  }

  int sourcesRemaining = 0;

  private boolean running = false;

  private void setGuiFetchComplete()
  {
    running = false;
    if (!cancelled && af != null)
    {
      // only update the progress bar if we've completed the fetch normally
      af.setProgressBar("DAS Feature Fetching Complete", startTime);
    }

    if (af != null && af.featureSettings != null)
    {
      af.featureSettings.setTableData();
    }

    if (getFeatSettings() != null)
    {
      fsettings.complete();
    }
  }

  void featuresAdded(Set<SequenceI> seqs)
  {
    if (af == null)
    {
      // no gui to update with features.
      return;
    }
    af.getFeatureRenderer().featuresAdded();

    int start = af.getViewport().getStartSeq();
    int end = af.getViewport().getEndSeq();
    int index;
    for (index = start; index < end; index++)
    {
      for (SequenceI seq : seqs)
      {
        if (seq == af.getViewport().getAlignment().getSequenceAt(index)
                .getDatasetSequence())
        {
          af.alignPanel.paintAlignment(true);
          index = end;
          break;
        }
      }
    }
  }

  Object[] nextSequence(jalviewSourceI dasSource, SequenceI seq)
  {
    if (cancelled)
      return null;
    DBRefEntry[] uprefs = jalview.util.DBRefUtils.selectRefs(
            seq.getDBRef(), new String[]
            {
            // jalview.datamodel.DBRefSource.PDB,
            jalview.datamodel.DBRefSource.UNIPROT,
            // jalview.datamodel.DBRefSource.EMBL - not tested on any EMBL coord
            // sys sources
            });
    // TODO: minimal list of DAS queries to make by querying with untyped ID if
    // distinct from any typed IDs

    List<DBRefEntry> ids = new ArrayList<DBRefEntry>();
    List<String> qstring = new ArrayList<String>();
    boolean dasCoordSysFound = false;

    if (uprefs != null)
    {
      // do any of these ids match the source's coordinate system ?
      for (int j = 0; !dasCoordSysFound && j < uprefs.length; j++)
      {

        for (COORDINATES csys : dasSource.getVersion().getCOORDINATES())
        {
          if (jalview.util.DBRefUtils.isDasCoordinateSystem(
                  csys.getAuthority(), uprefs[j]))
          {
            debug("Launched fetcher for coordinate system "
                    + csys.getAuthority());
            // Will have to pass any mapping information to the fetcher
            // - the start/end for the DBRefEntry may not be the same as the
            // sequence's start/end

            System.out.println(seq.getName() + " "
                    + (seq.getDatasetSequence() == null) + " "
                    + csys.getUri());

            dasCoordSysFound = true; // break's out of the loop
            ids.add(uprefs[j]);
            qstring.add(uprefs[j].getAccessionId());
          }
          else
            System.out.println("IGNORE " + csys.getAuthority());
        }
      }
    }

    if (!dasCoordSysFound)
    {
      String id = null;
      // try and use the name as the sequence id
      if (seq.getName().indexOf("|") > -1)
      {
        id = seq.getName().substring(seq.getName().lastIndexOf("|") + 1);
        if (id.trim().length() < 4)
        {
          // hack - we regard a significant ID as being at least 4
          // non-whitespace characters
          id = seq.getName().substring(0, seq.getName().lastIndexOf("|"));
          if (id.indexOf("|") > -1)
          {
            id = id.substring(id.lastIndexOf("|") + 1);
          }
        }
      }
      else
      {
        id = seq.getName();
      }
      if (id != null)
      {
        DBRefEntry dbre = new DBRefEntry();
        dbre.setAccessionId(id);
        // Should try to call a general feature fetcher that
        // queries many sources with name to discover applicable ID references
        ids.add(dbre);
        qstring.add(dbre.getAccessionId());
      }
    }

    return new Object[]
    { ids, qstring };
  }

  /**
   * examine the given sequence feature to determine if it should actually be
   * turned into sequence annotation or database cross references rather than a
   * simple sequence feature.
   * 
   * @param seq
   *          the sequence to annotate
   * @param f
   *          the jalview sequence feature generated from the DAS feature
   * @param map
   *          the sequence feature attributes
   * @param source
   *          the source that emitted the feature
   * @return true if feature was consumed as another kind of annotation.
   */
  protected boolean parseSeqFeature(SequenceI seq, SequenceFeature f,
          FEATURE feature, jalviewSourceI source)
  {
    SequenceI mseq = seq;
    while (seq.getDatasetSequence() != null)
    {
      seq = seq.getDatasetSequence();
    }
    if (f.getType() != null)
    {
      String type = f.getType();
      if (type.equalsIgnoreCase("protein_name"))
      {
        // parse name onto the alignment sequence or the dataset sequence.
        if (seq.getDescription() == null
                || seq.getDescription().trim().length() == 0)
        {
          // could look at the note series to pick out the first long name, for
          // the moment just use the whole description string
          seq.setDescription(f.getDescription());
        }
        if (mseq.getDescription() == null
                || mseq.getDescription().trim().length() == 0)
        {
          // could look at the note series to pick out the first long name, for
          // the moment just use the whole description string
          mseq.setDescription(f.getDescription());
        }
        return true;
      }
      // check if source has biosapiens or other sequence ontology label
      if (type.equalsIgnoreCase("DBXREF") || type.equalsIgnoreCase("DBREF"))
      {
        // try to parse the accession out

        DBRefEntry dbr = new DBRefEntry();
        dbr.setVersion(source.getTitle());
        StringTokenizer st = new StringTokenizer(f.getDescription(), ":");
        if (st.hasMoreTokens())
        {
          dbr.setSource(st.nextToken());
        }
        if (st.hasMoreTokens())
        {
          dbr.setAccessionId(st.nextToken());
        }
        seq.addDBRef(dbr);

        if (f.links != null && f.links.size() > 0)
        {
          // feature is also appended to enable links to be seen.
          // TODO: consider extending dbrefs to have their own links ?
          // TODO: new feature: extract dbref links from DAS servers and add the
          // URL pattern to the list of DB name associated links in the user's
          // preferences ?
          // for the moment - just fix up the existing feature so it displays
          // correctly.
          // f.setType(dbr.getSource());
          // f.setDescription();
          f.setValue("linkonly", Boolean.TRUE);
          // f.setDescription("");
          Vector newlinks = new Vector();
          Enumeration it = f.links.elements();
          while (it.hasMoreElements())
          {
            String elm;
            UrlLink urllink = new UrlLink(elm = (String) it.nextElement());
            if (urllink.isValid())
            {
              urllink.setLabel(f.getDescription());
              newlinks.addElement(urllink.toString());
            }
            else
            {
              // couldn't parse the link properly. Keep it anyway - just in
              // case.
              debug("couldn't parse link string - " + elm);
              newlinks.addElement(elm);
            }
          }
          f.links = newlinks;
          seq.addSequenceFeature(f);
        }
        return true;
      }
    }
    return false;
  }

  /**
   * creates a jalview sequence feature from a das feature document
   * 
   * @param feat
   * @return sequence feature object created using dasfeature information
   */
  SequenceFeature newSequenceFeature(FEATURE feat, String nickname)
  {
    if (feat == null)
    {
      return null;
    }
    try
    {
      /**
       * Different qNames for a DAS Feature - are string keys to the HashMaps in
       * features "METHOD") || qName.equals("TYPE") || qName.equals("START") ||
       * qName.equals("END") || qName.equals("NOTE") || qName.equals("LINK") ||
       * qName.equals("SCORE")
       */
      String desc = new String();
      if (feat.getNOTE() != null)
      {
        for (String note : feat.getNOTE())
        {
          desc += (String) note;
        }
      }

      int start = 0, end = 0;
      float score = 0f;

      try
      {
        start = Integer.parseInt(feat.getSTART().toString());
      } catch (Exception ex)
      {
      }
      try
      {
        end = Integer.parseInt(feat.getEND().toString());
      } catch (Exception ex)
      {
      }
      try
      {
        Object scr = feat.getSCORE();
        if (scr != null)
        {
          score = (float) Double.parseDouble(scr.toString());

        }
      } catch (Exception ex)
      {
      }

      SequenceFeature f = new SequenceFeature(
              getTypeString(feat.getTYPE()), desc, start, end, score,
              nickname);

      if (feat.getLINK() != null)
      {
        for (LINK link : feat.getLINK())
        {
          // Do not put feature extent in link text for non-positional features
          if (f.begin == 0 && f.end == 0)
          {
            f.addLink(f.getType() + " " + link.getContent() + "|"
                    + link.getHref());
          }
          else
          {
            f.addLink(f.getType() + " " + f.begin + "_" + f.end + " "
                    + link.getContent() + "|" + link.getHref());
          }
        }
      }

      return f;
    } catch (Exception e)
    {
      System.out.println("ERRR " + e);
      e.printStackTrace();
      System.out.println("############");
      debug("Failed to parse " + feat.toString(), e);
      return null;
    }
  }

  private String getTypeString(TYPE type)
  {
    return type.getContent();
  }

  public boolean isRunning()
  {
    return running;
  }

}
