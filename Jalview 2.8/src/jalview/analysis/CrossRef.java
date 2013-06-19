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
package jalview.analysis;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.Hashtable;

import jalview.datamodel.AlignedCodonFrame;
import jalview.datamodel.Alignment;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.DBRefSource;
import jalview.datamodel.DBRefEntry;
import jalview.datamodel.Sequence;
import jalview.datamodel.SequenceI;
import jalview.ws.SequenceFetcher;
import jalview.ws.seqfetcher.ASequenceFetcher;

/**
 * Functions for cross-referencing sequence databases. user must first specify
 * if cross-referencing from protein or dna (set dna==true)
 * 
 * @author JimP
 * 
 */
public class CrossRef
{
  /**
   * get the DNA or protein references for a protein or dna sequence
   * 
   * @param dna
   * @param rfs
   * @return
   */
  public static DBRefEntry[] findXDbRefs(boolean dna, DBRefEntry[] rfs)
  {
    if (dna)
    {
      rfs = jalview.util.DBRefUtils.selectRefs(rfs, DBRefSource.PROTEINDBS);
    }
    else
    {
      rfs = jalview.util.DBRefUtils.selectRefs(rfs,
              DBRefSource.DNACODINGDBS); // could attempt to find other cross
      // refs and return here - ie PDB xrefs
      // (not dna, not protein seq)
    }
    return rfs;
  }

  public static Hashtable classifyDbRefs(DBRefEntry[] rfs)
  {
    Hashtable classes = new Hashtable();
    classes.put(DBRefSource.PROTEINDBS,
            jalview.util.DBRefUtils.selectRefs(rfs, DBRefSource.PROTEINDBS));
    classes.put(DBRefSource.DNACODINGDBS, jalview.util.DBRefUtils
            .selectRefs(rfs, DBRefSource.DNACODINGDBS));
    classes.put(DBRefSource.DOMAINDBS,
            jalview.util.DBRefUtils.selectRefs(rfs, DBRefSource.DOMAINDBS));
    // classes.put(OTHER, )
    return classes;
  }

  /**
   * @param dna
   *          true if seqs are DNA seqs
   * @param seqs
   * @return a list of sequence database cross reference source types
   */
  public static String[] findSequenceXrefTypes(boolean dna, SequenceI[] seqs)
  {
    return findSequenceXrefTypes(dna, seqs, null);
  }

  /**
   * Indirect references are references from other sequences from the dataset to
   * any of the direct DBRefEntrys on the given sequences.
   * 
   * @param dna
   *          true if seqs are DNA seqs
   * @param seqs
   * @return a list of sequence database cross reference source types
   */
  public static String[] findSequenceXrefTypes(boolean dna,
          SequenceI[] seqs, AlignmentI dataset)
  {
    String[] dbrefs = null;
    Vector refs = new Vector();
    for (int s = 0; s < seqs.length; s++)
    {
      if (seqs[s] != null)
      {

        SequenceI dss = seqs[s];
        while (dss.getDatasetSequence() != null)
        {
          dss = dss.getDatasetSequence();
        }
        DBRefEntry[] rfs = findXDbRefs(dna, dss.getDBRef());
        for (int r = 0; rfs != null && r < rfs.length; r++)
        {
          if (!refs.contains(rfs[r].getSource()))
          {
            refs.addElement(rfs[r].getSource());
          }
        }
        if (dataset != null)
        {
          // search for references to this sequence's direct references.
          DBRefEntry[] lrfs = CrossRef
                  .findXDbRefs(!dna, seqs[s].getDBRef());
          Vector rseqs = new Vector();
          CrossRef.searchDatasetXrefs(seqs[s], !dna, lrfs, dataset, rseqs,
                  null); // don't need to specify codon frame for mapping here
          Enumeration lr = rseqs.elements();
          while (lr.hasMoreElements())
          {
            SequenceI rs = (SequenceI) lr.nextElement();
            DBRefEntry[] xrs = findXDbRefs(dna, rs.getDBRef());
            for (int r = 0; rfs != null && r < rfs.length; r++)
            {
              if (!refs.contains(rfs[r].getSource()))
              {
                refs.addElement(rfs[r].getSource());
              }
            }
          }
        }
      }
    }
    if (refs.size() > 0)
    {
      dbrefs = new String[refs.size()];
      refs.copyInto(dbrefs);
    }
    return dbrefs;
  }

  /*
   * if (dna) { if (rfs[r].hasMap()) { // most likely this is a protein cross
   * reference if (!refs.contains(rfs[r].getSource())) {
   * refs.addElement(rfs[r].getSource()); } } }
   */
  public static boolean hasCdnaMap(SequenceI[] seqs)
  {
    String[] reftypes = findSequenceXrefTypes(false, seqs);
    for (int s = 0; s < reftypes.length; s++)
    {
      if (reftypes.equals(DBRefSource.EMBLCDS))
      {
        return true;
        // no map
      }
    }
    return false;
  }

  public static SequenceI[] getCdnaMap(SequenceI[] seqs)
  {
    Vector cseqs = new Vector();
    for (int s = 0; s < seqs.length; s++)
    {
      DBRefEntry[] cdna = findXDbRefs(true, seqs[s].getDBRef());
      for (int c = 0; c < cdna.length; c++)
      {
        if (cdna[c].getSource().equals(DBRefSource.EMBLCDS))
        {
          System.err
                  .println("TODO: unimplemented sequence retrieval for coding region sequence.");
          // TODO: retrieve CDS dataset sequences
          // need global dataset sequence retriever/resolver to reuse refs
          // and construct Mapping entry.
          // insert gaps in CDS according to peptide gaps.
          // add gapped sequence to cseqs
        }
      }
    }
    if (cseqs.size() > 0)
    {
      SequenceI[] rsqs = new SequenceI[cseqs.size()];
      cseqs.copyInto(rsqs);
      return rsqs;
    }
    return null;

  }

  /**
   * 
   * @param dna
   * @param seqs
   * @return
   */
  public static Alignment findXrefSequences(SequenceI[] seqs, boolean dna,
          String source)
  {
    return findXrefSequences(seqs, dna, source, null);
  }

  /**
   * 
   * @param seqs
   * @param dna
   * @param source
   * @param dataset
   *          alignment to search for product sequences.
   * @return products (as dataset sequences)
   */
  public static Alignment findXrefSequences(SequenceI[] seqs, boolean dna,
          String source, AlignmentI dataset)
  {
    Vector rseqs = new Vector();
    Alignment ral = null;
    AlignedCodonFrame cf = new AlignedCodonFrame(0); // nominal width
    for (int s = 0; s < seqs.length; s++)
    {
      SequenceI dss = seqs[s];
      while (dss.getDatasetSequence() != null)
      {
        dss = dss.getDatasetSequence();
      }
      boolean found = false;
      DBRefEntry[] xrfs = CrossRef.findXDbRefs(dna, dss.getDBRef());
      if ((xrfs == null || xrfs.length == 0) && dataset != null)
      {
        System.out.println("Attempting to find ds Xrefs refs.");
        DBRefEntry[] lrfs = CrossRef.findXDbRefs(!dna, seqs[s].getDBRef()); // less
        // ambiguous
        // would
        // be a
        // 'find
        // primary
        // dbRefEntry'
        // method.
        // filter for desired source xref here
        found = CrossRef.searchDatasetXrefs(dss, !dna, lrfs, dataset,
                rseqs, cf);
      }
      for (int r = 0; xrfs != null && r < xrfs.length; r++)
      {
        if (source != null && !source.equals(xrfs[r].getSource()))
          continue;
        if (xrfs[r].hasMap())
        {
          if (xrfs[r].getMap().getTo() != null)
          {
            Sequence rsq = new Sequence(xrfs[r].getMap().getTo());
            rseqs.addElement(rsq);
            if (xrfs[r].getMap().getMap().getFromRatio() != xrfs[r]
                    .getMap().getMap().getToRatio())
            {
              // get sense of map correct for adding to product alignment.
              if (dna)
              {
                // map is from dna seq to a protein product
                cf.addMap(dss, rsq, xrfs[r].getMap().getMap());
              }
              else
              {
                // map should be from protein seq to its coding dna
                cf.addMap(rsq, dss, xrfs[r].getMap().getMap().getInverse());
              }
            }
            found = true;
          }
        }
        if (!found)
        {
          // do a bit more work - search for sequences with references matching
          // xrefs on this sequence.
          if (dataset != null)
          {
            found |= searchDataset(dss, xrfs[r], dataset, rseqs, cf); // ,false,!dna);
            if (found)
              xrfs[r] = null; // we've recovered seqs for this one.
          }
        }
      }
      if (!found)
      {
        if (xrfs != null && xrfs.length > 0)
        {
          // Try and get the sequence reference...
          /*
           * Ideal world - we ask for a sequence fetcher implementation here if
           * (jalview.io.RunTimeEnvironment.getSequenceFetcher()) (
           */
          ASequenceFetcher sftch = new SequenceFetcher();
          SequenceI[] retrieved = null;
          int l = xrfs.length;
          for (int r = 0; r < xrfs.length; r++)
          {
            // filter out any irrelevant or irretrievable references
            if (xrfs[r] == null
                    || ((source != null && !source.equals(xrfs[r]
                            .getSource())) || !sftch.isFetchable(xrfs[r]
                            .getSource())))
            {
              l--;
              xrfs[r] = null;
            }
          }
          if (l > 0)
          {
            System.out
                    .println("Attempting to retrieve cross referenced sequences.");
            DBRefEntry[] t = new DBRefEntry[l];
            l = 0;
            for (int r = 0; r < xrfs.length; r++)
            {
              if (xrfs[r] != null)
                t[l++] = xrfs[r];
            }
            xrfs = t;
            try
            {
              retrieved = sftch.getSequences(xrfs); // problem here is we don't
              // know which of xrfs
              // resulted in which
              // retrieved element
            } catch (Exception e)
            {
              System.err
                      .println("Problem whilst retrieving cross references for Sequence : "
                              + seqs[s].getName());
              e.printStackTrace();
            }
            if (retrieved != null)
            {
              for (int rs = 0; rs < retrieved.length; rs++)
              {
                // TODO: examine each sequence for 'redundancy'
                jalview.datamodel.DBRefEntry[] dbr = retrieved[rs]
                        .getDBRef();
                if (dbr != null && dbr.length > 0)
                {
                  for (int di = 0; di < dbr.length; di++)
                  {
                    // find any entry where we should put in the sequence being
                    // cross-referenced into the map
                    jalview.datamodel.Mapping map = dbr[di].getMap();
                    if (map != null)
                    {
                      if (map.getTo() != null && map.getMap() != null)
                      {
                        // should search the local dataset to find any existing
                        // candidates for To !
                        try
                        {
                          // compare ms with dss and replace with dss in mapping
                          // if map is congruent
                          SequenceI ms = map.getTo();
                          int sf = map.getMap().getToLowest();
                          int st = map.getMap().getToHighest();
                          SequenceI mappedrg = ms.getSubSequence(sf, st);
                          SequenceI loc = dss.getSubSequence(sf, st);
                          if (mappedrg.getLength() > 0
                                  && mappedrg.getSequenceAsString().equals(
                                          loc.getSequenceAsString()))
                          {
                            System.err
                                    .println("Mapping updated for retrieved crossreference");
                            // method to update all refs of existing To on
                            // retrieved sequence with dss and merge any props
                            // on To onto dss.
                            map.setTo(dss);
                          }
                        } catch (Exception e)
                        {
                          System.err
                                  .println("Exception when consolidating Mapped sequence set...");
                          e.printStackTrace(System.err);
                        }
                      }
                    }
                  }
                }
                retrieved[rs].updatePDBIds();
                rseqs.addElement(retrieved[rs]);
              }
            }
          }
        }
      }
    }
    if (rseqs.size() > 0)
    {
      SequenceI[] rsqs = new SequenceI[rseqs.size()];
      rseqs.copyInto(rsqs);
      ral = new Alignment(rsqs);
      if (cf != null && cf.getProtMappings() != null)
      {
        ral.addCodonFrame(cf);
      }
    }
    return ral;
  }

  /**
   * find references to lrfs in the cross-reference set of each sequence in
   * dataset (that is not equal to sequenceI) Identifies matching DBRefEntry
   * based on source and accession string only - Map and Version are nulled.
   * 
   * @param sequenceI
   * @param lrfs
   * @param dataset
   * @param rseqs
   * @return true if matches were found.
   */
  private static boolean searchDatasetXrefs(SequenceI sequenceI,
          boolean dna, DBRefEntry[] lrfs, AlignmentI dataset, Vector rseqs,
          AlignedCodonFrame cf)
  {
    boolean found = false;
    if (lrfs == null)
      return false;
    for (int i = 0; i < lrfs.length; i++)
    {
      DBRefEntry xref = new DBRefEntry(lrfs[i]);
      // add in wildcards
      xref.setVersion(null);
      xref.setMap(null);
      found = searchDataset(sequenceI, xref, dataset, rseqs, cf, false, dna);
    }
    return found;
  }

  /**
   * search a given sequence dataset for references matching cross-references to
   * the given sequence
   * 
   * @param sequenceI
   * @param xrf
   * @param dataset
   * @param rseqs
   *          set of unique sequences
   * @param cf
   * @return true if one or more unique sequences were found and added
   */
  public static boolean searchDataset(SequenceI sequenceI, DBRefEntry xrf,
          AlignmentI dataset, Vector rseqs, AlignedCodonFrame cf)
  {
    return searchDataset(sequenceI, xrf, dataset, rseqs, cf, true, false);
  }

  /**
   * TODO: generalise to different protein classifications Search dataset for
   * DBRefEntrys matching the given one (xrf) and add the associated sequence to
   * rseq.
   * 
   * @param sequenceI
   * @param xrf
   * @param dataset
   * @param rseqs
   * @param direct
   *          - search all references or only subset
   * @param dna
   *          search dna or protein xrefs (if direct=false)
   * @return true if relationship found and sequence added.
   */
  public static boolean searchDataset(SequenceI sequenceI, DBRefEntry xrf,
          AlignmentI dataset, Vector rseqs, AlignedCodonFrame cf,
          boolean direct, boolean dna)
  {
    boolean found = false;
    SequenceI[] typer = new SequenceI[1];
    if (dataset == null)
      return false;
    if (dataset.getSequences() == null)
    {
      System.err.println("Empty dataset sequence set - NO VECTOR");
      return false;
    }
    List<SequenceI> ds;
    synchronized (ds = dataset.getSequences())
    {
      for (SequenceI nxt : ds)
        if (nxt != null)
        {
          if (nxt.getDatasetSequence() != null)
          {
            System.err
                    .println("Implementation warning: getProducts passed a dataset alignment without dataset sequences in it!");
          }
          if (nxt != sequenceI && nxt != sequenceI.getDatasetSequence())
          {
            // check if this is the correct sequence type
            {
              typer[0] = nxt;
              boolean isDna = jalview.util.Comparison.isNucleotide(typer);
              if ((direct && isDna == dna) || (!direct && isDna != dna))
              {
                // skip this sequence because it is same molecule type
                continue;
              }
            }

            // look for direct or indirect references in common
            DBRefEntry[] poss = nxt.getDBRef(), cands = null;
            if (direct)
            {
              cands = jalview.util.DBRefUtils.searchRefs(poss, xrf);
            }
            else
            {
              poss = CrossRef.findXDbRefs(dna, poss); //
              cands = jalview.util.DBRefUtils.searchRefs(poss, xrf);
            }
            if (cands != null)
            {
              if (!rseqs.contains(nxt))
              {
                rseqs.addElement(nxt);
                boolean foundmap = cf != null; // don't search if we aren't
                                               // given
                // a codon map object
                for (int r = 0; foundmap && r < cands.length; r++)
                {
                  if (cands[r].hasMap())
                  {
                    if (cands[r].getMap().getTo() != null
                            && cands[r].getMap().getMap().getFromRatio() != cands[r]
                                    .getMap().getMap().getToRatio())
                    {
                      foundmap = true;
                      // get sense of map correct for adding to product
                      // alignment.
                      if (dna)
                      {
                        // map is from dna seq to a protein product
                        cf.addMap(sequenceI, nxt, cands[r].getMap()
                                .getMap());
                      }
                      else
                      {
                        // map should be from protein seq to its coding dna
                        cf.addMap(nxt, sequenceI, cands[r].getMap()
                                .getMap().getInverse());
                      }
                    }
                  }
                }
                // TODO: add mapping between sequences if necessary
                found = true;
              }
            }

          }
        }
    }
    return found;
  }

  /**
   * precalculate different products that can be found for seqs in dataset and
   * return them.
   * 
   * @param dna
   * @param seqs
   * @param dataset
   * @param fake
   *          - don't actually build lists - just get types
   * @return public static Object[] buildXProductsList(boolean dna, SequenceI[]
   *         seqs, AlignmentI dataset, boolean fake) { String types[] =
   *         jalview.analysis.CrossRef.findSequenceXrefTypes( dna, seqs,
   *         dataset); if (types != null) { System.out.println("Xref Types for:
   *         "+(dna ? "dna" : "prot")); for (int t = 0; t < types.length; t++) {
   *         System.out.println("Type: " + types[t]); SequenceI[] prod =
   *         jalview.analysis.CrossRef.findXrefSequences(seqs, dna, types[t]);
   *         System.out.println("Found " + ((prod == null) ? "no" : "" +
   *         prod.length) + " products"); if (prod!=null) { for (int p=0;
   *         p<prod.length; p++) { System.out.println("Prod "+p+":
   *         "+prod[p].getDisplayId(true)); } } } } else {
   *         System.out.println("Trying getProducts for
   *         "+al.getSequenceAt(0).getDisplayId(true));
   *         System.out.println("Search DS Xref for: "+(dna ? "dna" : "prot"));
   *         // have a bash at finding the products amongst all the retrieved
   *         sequences. SequenceI[] prod =
   *         jalview.analysis.CrossRef.findXrefSequences(al
   *         .getSequencesArray(), dna, null, ds); System.out.println("Found " +
   *         ((prod == null) ? "no" : "" + prod.length) + " products"); if
   *         (prod!=null) { // select non-equivalent sequences from dataset list
   *         for (int p=0; p<prod.length; p++) { System.out.println("Prod "+p+":
   *         "+prod[p].getDisplayId(true)); } } } }
   */
}
