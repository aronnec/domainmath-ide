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

import jalview.analysis.AlignSeq;
import jalview.bin.Cache;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.DBRefEntry;
import jalview.datamodel.DBRefSource;
import jalview.datamodel.Mapping;
import jalview.datamodel.SequenceFeature;
import jalview.datamodel.SequenceI;
import jalview.gui.AlignFrame;
import jalview.gui.CutAndPasteTransfer;
import jalview.gui.Desktop;
import jalview.gui.IProgressIndicator;
import jalview.gui.OOMWarning;
import jalview.ws.dbsources.das.api.jalviewSourceI;
import jalview.ws.seqfetcher.DbSourceProxy;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import uk.ac.ebi.picr.model.UPEntry;

/**
 * Implements a runnable for validating a sequence against external databases
 * and then propagating references and features onto the sequence(s)
 * 
 * @author $author$
 * @version $Revision$
 */
public class DBRefFetcher implements Runnable
{
  SequenceI[] dataset;

  IProgressIndicator af;

  CutAndPasteTransfer output = new CutAndPasteTransfer();

  StringBuffer sbuffer = new StringBuffer();

  boolean running = false;

  /**
   * picr client instance
   */
  uk.ac.ebi.www.picr.AccessionMappingService.AccessionMapperInterface picrClient = null;

  // /This will be a collection of Vectors of sequenceI refs.
  // The key will be the seq name or accession id of the seq
  Hashtable seqRefs;

  DbSourceProxy[] dbSources;

  SequenceFetcher sfetcher;

  private SequenceI[] alseqs;

  public DBRefFetcher()
  {
  }

  /**
   * Creates a new SequenceFeatureFetcher object and fetches from the currently
   * selected set of databases.
   * 
   * @param seqs
   *          fetch references for these sequences
   * @param af
   *          the parent alignframe for progress bar monitoring.
   */
  public DBRefFetcher(SequenceI[] seqs, AlignFrame af)
  {
    this(seqs, af, null);
  }

  /**
   * Creates a new SequenceFeatureFetcher object and fetches from the currently
   * selected set of databases.
   * 
   * @param seqs
   *          fetch references for these sequences
   * @param af
   *          the parent alignframe for progress bar monitoring.
   * @param sources
   *          array of database source strings to query references from
   */
  public DBRefFetcher(SequenceI[] seqs, AlignFrame af,
          DbSourceProxy[] sources)
  {
    this.af = af;
    alseqs = new SequenceI[seqs.length];
    SequenceI[] ds = new SequenceI[seqs.length];
    for (int i = 0; i < seqs.length; i++)
    {
      alseqs[i] = seqs[i];
      if (seqs[i].getDatasetSequence() != null)
        ds[i] = seqs[i].getDatasetSequence();
      else
        ds[i] = seqs[i];
    }
    this.dataset = ds;
    // TODO Jalview 2.5 lots of this code should be in the gui package!
    sfetcher = jalview.gui.SequenceFetcher.getSequenceFetcherSingleton(af);
    if (sources == null)
    {
      // af.featureSettings_actionPerformed(null);
      String[] defdb = null, otherdb = sfetcher
              .getDbInstances(jalview.ws.dbsources.das.datamodel.DasSequenceSource.class);
      List<DbSourceProxy> selsources = new ArrayList<DbSourceProxy>();
      Vector dasselsrc = (af.featureSettings != null) ? af.featureSettings
              .getSelectedSources() : new jalview.gui.DasSourceBrowser()
              .getSelectedSources();
      Enumeration<jalviewSourceI> en = dasselsrc.elements();
      while (en.hasMoreElements())
      {
        jalviewSourceI src = en.nextElement();
        List<DbSourceProxy> sp = src.getSequenceSourceProxies();
        selsources.addAll(sp);
        if (sp.size() > 1)
        {
          Cache.log.debug("Added many Db Sources for :" + src.getTitle());
        }
      }
      // select appropriate databases based on alignFrame context.
      if (af.getViewport().getAlignment().isNucleotide())
      {
        defdb = DBRefSource.DNACODINGDBS;
      }
      else
      {
        defdb = DBRefSource.PROTEINDBS;
      }
      List<DbSourceProxy> srces = new ArrayList<DbSourceProxy>();
      for (String ddb : defdb)
      {
        List<DbSourceProxy> srcesfordb = sfetcher.getSourceProxy(ddb);
        if (srcesfordb != null)
        {
          srces.addAll(srcesfordb);
        }
      }

      // append the selected sequence sources to the default dbs
      srces.addAll(selsources);
      dbSources = srces.toArray(new DbSourceProxy[0]);
    }
    else
    {
      // we assume the caller knows what they're doing and ensured that all the
      // db source names are valid
      dbSources = sources;
    }
  }

  /**
   * retrieve all the das sequence sources and add them to the list of db
   * sources to retrieve from
   */
  public void appendAllDasSources()
  {
    if (dbSources == null)
    {
      dbSources = new DbSourceProxy[0];
    }
    // append additional sources
    DbSourceProxy[] otherdb = sfetcher
            .getDbSourceProxyInstances(jalview.ws.dbsources.das.datamodel.DasSequenceSource.class);
    if (otherdb != null && otherdb.length > 0)
    {
      DbSourceProxy[] newsrc = new DbSourceProxy[dbSources.length
              + otherdb.length];
      System.arraycopy(dbSources, 0, newsrc, 0, dbSources.length);
      System.arraycopy(otherdb, 0, newsrc, dbSources.length, otherdb.length);
      dbSources = newsrc;
    }
  }

  /**
   * start the fetcher thread
   * 
   * @param waitTillFinished
   *          true to block until the fetcher has finished
   */
  public void fetchDBRefs(boolean waitTillFinished)
  {
    Thread thread = new Thread(this);
    thread.start();
    running = true;

    if (waitTillFinished)
    {
      while (running)
      {
        try
        {
          Thread.sleep(500);
        } catch (Exception ex)
        {
        }
      }
    }
  }

  /**
   * The sequence will be added to a vector of sequences belonging to key which
   * could be either seq name or dbref id
   * 
   * @param seq
   *          SequenceI
   * @param key
   *          String
   */
  void addSeqId(SequenceI seq, String key)
  {
    key = key.toUpperCase();

    Vector seqs;
    if (seqRefs.containsKey(key))
    {
      seqs = (Vector) seqRefs.get(key);

      if (seqs != null && !seqs.contains(seq))
      {
        seqs.addElement(seq);
      }
      else if (seqs == null)
      {
        seqs = new Vector();
        seqs.addElement(seq);
      }

    }
    else
    {
      seqs = new Vector();
      seqs.addElement(seq);
    }

    seqRefs.put(key, seqs);
  }

  /**
   * DOCUMENT ME!
   */
  public void run()
  {
    if (dbSources == null)
    {
      throw new Error("Implementation error. Must initialise dbSources");
    }
    running = true;
    long startTime = System.currentTimeMillis();
    af.setProgressBar("Fetching db refs", startTime);
    try
    {
      if (Cache.getDefault("DBREFFETCH_USEPICR", false))
      {
        picrClient = new uk.ac.ebi.www.picr.AccessionMappingService.AccessionMapperServiceLocator()
                .getAccessionMapperPort();
      }
    } catch (Exception e)
    {
      System.err.println("Couldn't locate PICR service instance.\n");
      e.printStackTrace();
    }
    int db = 0;
    Vector sdataset = new Vector();
    for (int s = 0; s < dataset.length; s++)
    {
      sdataset.addElement(dataset[s]);
    }
    while (sdataset.size() > 0 && db < dbSources.length)
    {
      int maxqlen = 1; // default number of queries made to at one time
      System.err.println("Verifying against " + dbSources[db].getDbName());
      boolean dn = false;

      // iterate through db for each remaining un-verified sequence
      SequenceI[] currSeqs = new SequenceI[sdataset.size()];
      sdataset.copyInto(currSeqs);// seqs that are to be validated against
      // dbSources[db]
      Vector queries = new Vector(); // generated queries curSeq
      seqRefs = new Hashtable();

      int seqIndex = 0;

      jalview.ws.seqfetcher.DbSourceProxy dbsource = dbSources[db];
      {
        // for moment, we dumbly iterate over all retrieval sources for a
        // particular database
        // TODO: introduce multithread multisource queries and logic to remove a
        // query from other sources if any source for a database returns a
        // record
        if (dbsource.getDbSourceProperties().containsKey(
                DBRefSource.MULTIACC))
        {
          maxqlen = ((Integer) dbsource.getDbSourceProperties().get(
                  DBRefSource.MULTIACC)).intValue();
        }
        else
        {
          maxqlen = 1;
        }
        while (queries.size() > 0 || seqIndex < currSeqs.length)
        {
          if (queries.size() > 0)
          {
            // Still queries to make for current seqIndex
            StringBuffer queryString = new StringBuffer("");
            int numq = 0, nqSize = (maxqlen > queries.size()) ? queries
                    .size() : maxqlen;

            while (queries.size() > 0 && numq < nqSize)
            {
              String query = (String) queries.elementAt(0);
              if (dbsource.isValidReference(query))
              {
                queryString.append((numq == 0) ? "" : dbsource
                        .getAccessionSeparator());
                queryString.append(query);
                numq++;
              }
              // remove the extracted query string
              queries.removeElementAt(0);
            }
            // make the queries and process the response
            AlignmentI retrieved = null;
            try
            {
              if (jalview.bin.Cache.log.isDebugEnabled())
              {
                jalview.bin.Cache.log.debug("Querying "
                        + dbsource.getDbName() + " with : '"
                        + queryString.toString() + "'");
              }
              retrieved = dbsource.getSequenceRecords(queryString
                      .toString());
            } catch (Exception ex)
            {
              ex.printStackTrace();
            } catch (OutOfMemoryError err)
            {
              new OOMWarning("retrieving database references ("
                      + queryString.toString() + ")", err);
            }
            if (retrieved != null)
            {
              transferReferences(sdataset, dbsource.getDbSource(),
                      retrieved);
            }
          }
          else
          {
            // make some more strings for use as queries
            for (int i = 0; (seqIndex < dataset.length) && (i < 50); seqIndex++, i++)
            {
              SequenceI sequence = dataset[seqIndex];
              DBRefEntry[] uprefs = jalview.util.DBRefUtils.selectRefs(
                      sequence.getDBRef(), new String[]
                      { dbsource.getDbSource() }); // jalview.datamodel.DBRefSource.UNIPROT
              // });
              // check for existing dbrefs to use
              if (uprefs != null && uprefs.length > 0)
              {
                for (int j = 0; j < uprefs.length; j++)
                {
                  addSeqId(sequence, uprefs[j].getAccessionId());
                  queries.addElement(uprefs[j].getAccessionId()
                          .toUpperCase());
                }
              }
              else
              {
                // generate queries from sequence ID string
                StringTokenizer st = new StringTokenizer(
                        sequence.getName(), "|");
                while (st.hasMoreTokens())
                {
                  String token = st.nextToken();
                  UPEntry[] presp = null;
                  if (picrClient != null)
                  {
                    // resolve the string against PICR to recover valid IDs
                    try
                    {
                      presp = picrClient.getUPIForAccession(token, null,
                              picrClient.getMappedDatabaseNames(), null,
                              true);
                    } catch (Exception e)
                    {
                      System.err.println("Exception with Picr for '"
                              + token + "'\n");
                      e.printStackTrace();
                    }
                  }
                  if (presp != null && presp.length > 0)
                  {
                    for (int id = 0; id < presp.length; id++)
                    {
                      // construct sequences from response if sequences are
                      // present, and do a transferReferences
                      // otherwise transfer non sequence x-references directly.
                    }
                    System.out
                            .println("Validated ID against PICR... (for what its worth):"
                                    + token);
                    addSeqId(sequence, token);
                    queries.addElement(token.toUpperCase());
                  }
                  else
                  {
                    // if ()
                    // System.out.println("Not querying source with token="+token+"\n");
                    addSeqId(sequence, token);
                    queries.addElement(token.toUpperCase());
                  }
                }
              }
            }
          }
        }
      }
      // advance to next database
      db++;
    } // all databases have been queries.
    if (sbuffer.length() > 0)
    {
      output.setText("Your sequences have been verified against known sequence databases. Some of the ids have been\n"
              + "altered, most likely the start/end residue will have been updated.\n"
              + "Save your alignment to maintain the updated id.\n\n"
              + sbuffer.toString());
      Desktop.addInternalFrame(output, "Sequence names updated ", 600, 300);
      // The above is the dataset, we must now find out the index
      // of the viewed sequence

    }

    af.setProgressBar("DBRef search completed", startTime);
    // promptBeforeBlast();

    running = false;

  }

  /**
   * Verify local sequences in seqRefs against the retrieved sequence database
   * records.
   * 
   */
  void transferReferences(Vector sdataset, String dbSource,
          AlignmentI retrievedAl) // File
  // file)
  {
    if (retrievedAl == null || retrievedAl.getHeight() == 0)
    {
      return;
    }
    SequenceI[] retrieved = recoverDbSequences(retrievedAl
            .getSequencesArray());
    SequenceI sequence = null;
    boolean transferred = false;
    StringBuffer messages = new StringBuffer();

    // Vector entries = new Uniprot().getUniprotEntries(file);

    int i, iSize = retrieved.length; // entries == null ? 0 : entries.size();
    // UniprotEntry entry;
    for (i = 0; i < iSize; i++)
    {
      SequenceI entry = retrieved[i]; // (UniprotEntry) entries.elementAt(i);

      // Work out which sequences this sequence matches,
      // taking into account all accessionIds and names in the file
      Vector sequenceMatches = new Vector();
      // look for corresponding accession ids
      DBRefEntry[] entryRefs = jalview.util.DBRefUtils.selectRefs(
              entry.getDBRef(), new String[]
              { dbSource });
      if (entryRefs == null)
      {
        System.err
                .println("Dud dbSource string ? no entryrefs selected for "
                        + dbSource + " on " + entry.getName());
        continue;
      }
      for (int j = 0; j < entryRefs.length; j++)
      {
        String accessionId = entryRefs[j].getAccessionId(); // .getAccession().elementAt(j).toString();
        // match up on accessionId
        if (seqRefs.containsKey(accessionId.toUpperCase()))
        {
          Vector seqs = (Vector) seqRefs.get(accessionId);
          for (int jj = 0; jj < seqs.size(); jj++)
          {
            sequence = (SequenceI) seqs.elementAt(jj);
            if (!sequenceMatches.contains(sequence))
            {
              sequenceMatches.addElement(sequence);
            }
          }
        }
      }
      if (sequenceMatches.size() == 0)
      {
        // failed to match directly on accessionId==query so just compare all
        // sequences to entry
        Enumeration e = seqRefs.keys();
        while (e.hasMoreElements())
        {
          Vector sqs = (Vector) seqRefs.get(e.nextElement());
          if (sqs != null && sqs.size() > 0)
          {
            Enumeration sqe = sqs.elements();
            while (sqe.hasMoreElements())
            {
              sequenceMatches.addElement(sqe.nextElement());
            }
          }
        }
      }
      // look for corresponding names
      // this is uniprot specific ?
      // could be useful to extend this so we try to find any 'significant'
      // information in common between two sequence objects.
      /*
       * DBRefEntry[] entryRefs =
       * jalview.util.DBRefUtils.selectRefs(entry.getDBRef(), new String[] {
       * dbSource }); for (int j = 0; j < entry.getName().size(); j++) { String
       * name = entry.getName().elementAt(j).toString(); if
       * (seqRefs.containsKey(name)) { Vector seqs = (Vector) seqRefs.get(name);
       * for (int jj = 0; jj < seqs.size(); jj++) { sequence = (SequenceI)
       * seqs.elementAt(jj); if (!sequenceMatches.contains(sequence)) {
       * sequenceMatches.addElement(sequence); } } } }
       */
      // sequenceMatches now contains the set of all sequences associated with
      // the returned db record
      String entrySeq = entry.getSequenceAsString().toUpperCase();
      for (int m = 0; m < sequenceMatches.size(); m++)
      {
        sequence = (SequenceI) sequenceMatches.elementAt(m);
        // only update start and end positions and shift features if there are
        // no existing references
        // TODO: test for legacy where uniprot or EMBL refs exist but no
        // mappings are made (but content matches retrieved set)
        boolean updateRefFrame = sequence.getDBRef() == null
                || sequence.getDBRef().length == 0;
        // verify sequence against the entry sequence

        String nonGapped = AlignSeq.extractGaps("-. ",
                sequence.getSequenceAsString()).toUpperCase();

        int absStart = entrySeq.indexOf(nonGapped);
        int mapStart = entry.getStart();
        jalview.datamodel.Mapping mp;

        if (absStart == -1)
        {
          // Is local sequence contained in dataset sequence?
          absStart = nonGapped.indexOf(entrySeq);
          if (absStart == -1)
          { // verification failed.
            messages.append(sequence.getName()
                    + " SEQUENCE NOT %100 MATCH \n");
            continue;
          }
          transferred = true;
          sbuffer.append(sequence.getName() + " HAS " + absStart
                  + " PREFIXED RESIDUES COMPARED TO " + dbSource + "\n");
          //
          // + " - ANY SEQUENCE FEATURES"
          // + " HAVE BEEN ADJUSTED ACCORDINGLY \n");
          // absStart = 0;
          // create valid mapping between matching region of local sequence and
          // the mapped sequence
          mp = new Mapping(null, new int[]
          { sequence.getStart() + absStart,
              sequence.getStart() + absStart + entrySeq.length() - 1 },
                  new int[]
                  { entry.getStart(),
                      entry.getStart() + entrySeq.length() - 1 }, 1, 1);
          updateRefFrame = false; // mapping is based on current start/end so
          // don't modify start and end
        }
        else
        {
          transferred = true;
          // update start and end of local sequence to place it in entry's
          // reference frame.
          // apply identity map map from whole of local sequence to matching
          // region of database
          // sequence
          mp = null; // Mapping.getIdentityMap();
          // new Mapping(null,
          // new int[] { absStart+sequence.getStart(),
          // absStart+sequence.getStart()+entrySeq.length()-1},
          // new int[] { entry.getStart(), entry.getEnd() }, 1, 1);
          // relocate local features for updated start
          if (updateRefFrame)
          {
            if (sequence.getSequenceFeatures() != null)
            {
              SequenceFeature[] sf = sequence.getSequenceFeatures();
              int start = sequence.getStart();
              int end = sequence.getEnd();
              int startShift = 1 - absStart - start; // how much the features
                                                     // are
              // to be shifted by
              for (int sfi = 0; sfi < sf.length; sfi++)
              {
                if (sf[sfi].getBegin() >= start && sf[sfi].getEnd() <= end)
                {
                  // shift feature along by absstart
                  sf[sfi].setBegin(sf[sfi].getBegin() + startShift);
                  sf[sfi].setEnd(sf[sfi].getEnd() + startShift);
                }
              }
            }
          }
        }

        System.out.println("Adding dbrefs to " + sequence.getName()
                + " from " + dbSource + " sequence : " + entry.getName());
        sequence.transferAnnotation(entry, mp);
        // unknownSequences.remove(sequence);
        int absEnd = absStart + nonGapped.length();
        absStart += 1;
        if (updateRefFrame)
        {
          // finally, update local sequence reference frame if we're allowed
          sequence.setStart(absStart);
          sequence.setEnd(absEnd);
          // search for alignment sequences to update coordinate frame for
          for (int alsq = 0; alsq < alseqs.length; alsq++)
          {
            if (alseqs[alsq].getDatasetSequence() == sequence)
            {
              String ngAlsq = AlignSeq.extractGaps("-. ",
                      alseqs[alsq].getSequenceAsString()).toUpperCase();
              int oldstrt = alseqs[alsq].getStart();
              alseqs[alsq].setStart(sequence.getSequenceAsString()
                      .toUpperCase().indexOf(ngAlsq)
                      + sequence.getStart());
              if (oldstrt != alseqs[alsq].getStart())
              {
                alseqs[alsq].setEnd(ngAlsq.length()
                        + alseqs[alsq].getStart() - 1);
              }
            }
          }
          // TODO: search for all other references to this dataset sequence, and
          // update start/end
          // TODO: update all AlCodonMappings which involve this alignment
          // sequence (e.g. Q30167 cdna translation from exon2 product (vamsas
          // demo)
        }
        // and remove it from the rest
        // TODO: decide if we should remove annotated sequence from set
        sdataset.remove(sequence);
        // TODO: should we make a note of sequences that have received new DB
        // ids, so we can query all enabled DAS servers for them ?
      }
    }
    if (!transferred)
    {
      // report the ID/sequence mismatches
      sbuffer.append(messages);
    }
  }

  /**
   * loop thru and collect additional sequences in Map.
   * 
   * @param sequencesArray
   * @return
   */
  private SequenceI[] recoverDbSequences(SequenceI[] sequencesArray)
  {
    Vector nseq = new Vector();
    for (int i = 0; sequencesArray != null && i < sequencesArray.length; i++)
    {
      nseq.addElement(sequencesArray[i]);
      DBRefEntry dbr[] = sequencesArray[i].getDBRef();
      jalview.datamodel.Mapping map = null;
      for (int r = 0; (dbr != null) && r < dbr.length; r++)
      {
        if ((map = dbr[r].getMap()) != null)
        {
          if (map.getTo() != null && !nseq.contains(map.getTo()))
          {
            nseq.addElement(map.getTo());
          }
        }
      }
    }
    if (nseq.size() > 0)
    {
      sequencesArray = new SequenceI[nseq.size()];
      nseq.toArray(sequencesArray);
    }
    return sequencesArray;
  }
}
