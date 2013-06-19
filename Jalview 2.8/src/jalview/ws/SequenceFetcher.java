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

import jalview.datamodel.Alignment;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.DBRefSource;
import jalview.datamodel.SequenceI;
import jalview.ws.dbsources.das.api.jalviewSourceI;
import jalview.ws.seqfetcher.ASequenceFetcher;
import jalview.ws.seqfetcher.DbSourceProxy;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 * This is the the concrete implementation of the sequence retrieval interface
 * and abstract class in jalview.ws.seqfetcher. This implements the run-time
 * discovery of sequence database clients, and provides a hardwired main for
 * testing all registered handlers.
 * 
 */
public class SequenceFetcher extends ASequenceFetcher
{
  /**
   * Thread safe construction of database proxies TODO: extend to a configurable
   * database plugin mechanism where classes are instantiated by reflection and
   * queried for their DbRefSource and version association.
   * 
   */
  public SequenceFetcher()
  {
    this(true);
  }
  public SequenceFetcher(boolean addDas)
  {
    addDBRefSourceImpl(jalview.ws.dbsources.EmblSource.class);
    addDBRefSourceImpl(jalview.ws.dbsources.EmblCdsSouce.class);
    addDBRefSourceImpl(jalview.ws.dbsources.Uniprot.class);
    addDBRefSourceImpl(jalview.ws.dbsources.UnprotName.class);
    addDBRefSourceImpl(jalview.ws.dbsources.Pdb.class);
    addDBRefSourceImpl(jalview.ws.dbsources.PfamFull.class);
    addDBRefSourceImpl(jalview.ws.dbsources.PfamSeed.class); // ensures Seed
    // alignment is
    // 'default' for
    // PFAM
    addDBRefSourceImpl(jalview.ws.dbsources.RfamFull.class);
    addDBRefSourceImpl(jalview.ws.dbsources.RfamSeed.class);
    if (addDas) {
      registerDasSequenceSources();
    }
  }

  /**
   * return an ordered list of database sources where non-das database classes
   * appear before das database classes
   */
  public String[] getOrderedSupportedSources()
  {
    String[] srcs = this.getSupportedDb();
    ArrayList<String> dassrc = new ArrayList<String>(), nondas = new ArrayList<String>();
    for (int i = 0; i < srcs.length; i++)
    {
      boolean das = false, skip = false;
      String nm;
      for (DbSourceProxy dbs : getSourceProxy(srcs[i]))
      {
        // Skip the alignment databases for the moment - they're not useful for
        // verifying a single sequence against its reference source
        if (dbs.isA(DBRefSource.ALIGNMENTDB))
        {
          skip = true;
        }
        else
        {
          nm = dbs.getDbName();
          if (getSourceProxy(srcs[i]) instanceof jalview.ws.dbsources.das.datamodel.DasSequenceSource)
          {
            if (nm.startsWith("das:"))
            {
              nm = nm.substring(4);
              das = true;
            }
            break;
          }
        }
      }
      if (skip)
      {
        continue;
      }
      if (das)
      {
        dassrc.add(srcs[i]);
      }
      else
      {
        nondas.add(srcs[i]);
      }
    }
    String[] tosort = nondas.toArray(new String[0]), sorted = nondas
            .toArray(new String[0]);
    for (int j = 0, jSize = sorted.length; j < jSize; j++)
    {
      tosort[j] = tosort[j].toLowerCase();
    }
    jalview.util.QuickSort.sort(tosort, sorted);
    // construct array with all sources listed

    srcs = new String[sorted.length + dassrc.size()];
    int i = 0;
    for (int j = sorted.length - 1; j >= 0; j--, i++)
    {
      srcs[i] = sorted[j];
      sorted[j] = null;
    }

    sorted = dassrc.toArray(new String[0]);
    tosort = dassrc.toArray(new String[0]);
    for (int j = 0, jSize = sorted.length; j < jSize; j++)
    {
      tosort[j] = tosort[j].toLowerCase();
    }
    jalview.util.QuickSort.sort(tosort, sorted);
    for (int j = sorted.length - 1; j >= 0; j--, i++)
    {
      srcs[i] = sorted[j];
    }
    return srcs;
  }

  /**
   * return plaintext databse list suitable for using in a GUI element
   */
  public String[] _getOrderedSupportedSources()
  {
    String[] srcs = this.getSupportedDb();
    ArrayList dassrc = new ArrayList(), nondas = new ArrayList();
    for (int i = 0; i < srcs.length; i++)
    {
      for (DbSourceProxy dbs : getSourceProxy(srcs[i]))
      {
        String nm = dbs.getDbName();
        if (getSourceProxy(srcs[i]) instanceof jalview.ws.dbsources.das.datamodel.DasSequenceSource)
        {
          if (nm.startsWith("das:"))
          {
            nm = nm.substring(4);
          }
          dassrc.add(new String[]
          { srcs[i], nm.toUpperCase() });
        }
        else
        {
          nondas.add(new String[]
          { srcs[i], nm.toUpperCase() });
        }
      }
    }
    Object[] sorted = nondas.toArray();
    String[] tosort = new String[sorted.length];
    nondas.clear();
    for (int j = 0; j < sorted.length; j++)
    {
      tosort[j] = ((String[]) sorted[j])[1];
    }
    jalview.util.QuickSort.sort(tosort, sorted);
    int i = 0;
    // construct array with all sources listed
    srcs = new String[sorted.length + dassrc.size()];
    for (int j = sorted.length - 1; j >= 0; j--, i++)
    {
      srcs[i] = ((String[]) sorted[j])[0];
      sorted[j] = null;
    }

    sorted = dassrc.toArray();
    tosort = new String[sorted.length];
    dassrc.clear();
    for (int j = 0; j < sorted.length; j++)
    {
      tosort[j] = ((String[]) sorted[j])[1];
    }
    jalview.util.QuickSort.sort(tosort, sorted);
    for (int j = sorted.length - 1; j >= 0; j--, i++)
    {
      srcs[i] = ((String[]) sorted[j])[0];
      sorted[j] = null;
    }
    return srcs;
  }

  /**
   * simple run method to test dbsources.
   * 
   * @param argv
   */
  public static void main(String[] argv)
  {
    AlignmentI ds = null;
    Vector noProds = new Vector();
    String usage = "SequenceFetcher.main [-nodas] [<DBNAME> [<ACCNO>]]\n"
            + "With no arguments, all DbSources will be queried with their test Accession number.\n"
            + "With one argument, the argument will be resolved to one or more db sources and each will be queried with their test accession only.\n"
            + "If given two arguments, SequenceFetcher will try to find the DbFetcher corresponding to <DBNAME> and retrieve <ACCNO> from it.\n"
            + "The -nodas option will exclude DAS sources from the database fetchers Jalview will try to use.";
    boolean withDas=true;
    if (argv!=null && argv.length>0 && argv[0].toLowerCase().startsWith("-nodas"))
    {
      withDas=false;
      String targs[] = new String[argv.length-1];
      System.arraycopy(argv, 1, targs, 0, targs.length);
      argv=targs;
    }
    if (argv != null && argv.length > 0)
    {
      List<DbSourceProxy> sps = new SequenceFetcher(withDas)
              .getSourceProxy(argv[0]);

      if (sps != null)
      {
        for (DbSourceProxy sp : sps)
        {
          AlignmentI al = null;
          try
          {
            al = sp.getSequenceRecords(argv.length>1 ? argv[1] : sp.getTestQuery());
          } catch (Exception e)
          {
            e.printStackTrace();
            System.err.println("Error when retrieving " + (argv.length>1 ? argv[1] : sp.getTestQuery())
                    + " from " + argv[0] + "\nUsage: " + usage);
          }
          SequenceI[] prod = al.getSequencesArray();
          if (al != null)
          {
            for (int p = 0; p < prod.length; p++)
            {
              System.out.println("Prod " + p + ": "
                      + prod[p].getDisplayId(true) + " : "
                      + prod[p].getDescription());
            }
          }
        }
        return;
      }
      else
      {
        System.err.println("Can't resolve " + argv[0]
                + " as a database name. Allowed values are :\n"
                + new SequenceFetcher().getSupportedDb());
      }
      System.out.println(usage);
      return;
    }
    ASequenceFetcher sfetcher = new SequenceFetcher(withDas);
    String[] dbSources = sfetcher.getSupportedDb();
    for (int dbsource = 0; dbsource < dbSources.length; dbsource++)
    {
      String db = dbSources[dbsource];
      // skip me
      if (db.equals(DBRefSource.PDB))
        continue;
      for (DbSourceProxy sp : sfetcher.getSourceProxy(db))
      {
        System.out.println("Source: " + sp.getDbName() + " (" + db
                + "): retrieving test:" + sp.getTestQuery());
        AlignmentI al = null;
        try
        {
          al = sp.getSequenceRecords(sp.getTestQuery());
          if (al != null && al.getHeight() > 0
                  && sp.getDbSourceProperties() != null)
          {
            boolean dna = sp.getDbSourceProperties().containsKey(
                    DBRefSource.DNACODINGSEQDB)
                    || sp.getDbSourceProperties().containsKey(
                            DBRefSource.DNASEQDB)
                    || sp.getDbSourceProperties().containsKey(
                            DBRefSource.CODINGSEQDB);
            // try and find products
            String types[] = jalview.analysis.CrossRef
                    .findSequenceXrefTypes(dna, al.getSequencesArray());
            if (types != null)
            {
              System.out.println("Xref Types for: "
                      + (dna ? "dna" : "prot"));
              for (int t = 0; t < types.length; t++)
              {
                System.out.println("Type: " + types[t]);
                SequenceI[] prod = jalview.analysis.CrossRef
                        .findXrefSequences(al.getSequencesArray(), dna,
                                types[t]).getSequencesArray();
                System.out.println("Found "
                        + ((prod == null) ? "no" : "" + prod.length)
                        + " products");
                if (prod != null)
                {
                  for (int p = 0; p < prod.length; p++)
                  {
                    System.out.println("Prod " + p + ": "
                            + prod[p].getDisplayId(true));
                  }
                }
              }
            }
            else
            {
              noProds.addElement((dna ? new Object[]
              { al, al } : new Object[]
              { al }));
            }

          }
        } catch (Exception ex)
        {
          System.out.println("ERROR:Failed to retrieve test query.");
          ex.printStackTrace(System.out);
        }

        if (al == null)
        {
          System.out.println("ERROR:No alignment retrieved.");
          StringBuffer raw = sp.getRawRecords();
          if (raw != null)
            System.out.println(raw.toString());
          else
            System.out.println("ERROR:No Raw results.");
        }
        else
        {
          System.out.println("Retrieved " + al.getHeight() + " sequences.");
          for (int s = 0; s < al.getHeight(); s++)
          {
            SequenceI sq = al.getSequenceAt(s);
            while (sq.getDatasetSequence() != null)
            {
              sq = sq.getDatasetSequence();

            }
            if (ds == null)
            {
              ds = new Alignment(new SequenceI[]
              { sq });

            }
            else
            {
              ds.addSequence(sq);
            }
          }
        }
        System.out.flush();
        System.err.flush();

      }
      if (noProds.size() > 0)
      {
        Enumeration ts = noProds.elements();
        while (ts.hasMoreElements())

        {
          Object[] typeSq = (Object[]) ts.nextElement();
          boolean dna = (typeSq.length > 1);
          AlignmentI al = (AlignmentI) typeSq[0];
          System.out.println("Trying getProducts for "
                  + al.getSequenceAt(0).getDisplayId(true));
          System.out.println("Search DS Xref for: "
                  + (dna ? "dna" : "prot"));
          // have a bash at finding the products amongst all the retrieved
          // sequences.
          SequenceI[] seqs = al.getSequencesArray();
          Alignment prodal = jalview.analysis.CrossRef.findXrefSequences(
                  seqs, dna, null, ds);
          System.out.println("Found "
                  + ((prodal == null) ? "no" : "" + prodal.getHeight())
                  + " products");
          if (prodal != null)
          {
            SequenceI[] prod = prodal.getSequencesArray(); // note
            // should
            // test
            // rather
            // than
            // throw
            // away
            // codon
            // mapping
            // (if
            // present)
            for (int p = 0; p < prod.length; p++)
            {
              System.out.println("Prod " + p + ": "
                      + prod[p].getDisplayId(true));
            }
          }
        }

      }

    }
  }

  /**
   * query the currently defined DAS source registry for sequence sources and
   * add a DasSequenceSource instance for each source to the SequenceFetcher
   * source list.
   */
  public void registerDasSequenceSources()
  {
    // TODO: define a context as a registry provider (either desktop,
    // jalview.bin.cache, or something else).
    for (jalviewSourceI source : jalview.bin.Cache.getDasSourceRegistry()
            .getSources())
    {
      if (source.isSequenceSource())
      {
        List<DbSourceProxy> dassources = source.getSequenceSourceProxies();
        for (DbSourceProxy seqsrc : dassources)
        {
          addDbRefSourceImpl(seqsrc);
        }
      }
    }
  }

}
