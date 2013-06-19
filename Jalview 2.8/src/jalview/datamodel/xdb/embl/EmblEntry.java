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

import jalview.datamodel.DBRefEntry;
import jalview.datamodel.DBRefSource;
import jalview.datamodel.FeatureProperties;
import jalview.datamodel.Mapping;
import jalview.datamodel.Sequence;
import jalview.datamodel.SequenceFeature;
import jalview.datamodel.SequenceI;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class EmblEntry
{
  String accession;

  String version;

  String taxDivision;

  String desc;

  String rCreated;

  String rLastUpdated;

  String lastUpdated;

  Vector keywords;

  Vector refs;

  Vector dbRefs;

  Vector features;

  EmblSequence sequence;

  /**
   * @return the accession
   */
  public String getAccession()
  {
    return accession;
  }

  /**
   * @param accession
   *          the accession to set
   */
  public void setAccession(String accession)
  {
    this.accession = accession;
  }

  /**
   * @return the dbRefs
   */
  public Vector getDbRefs()
  {
    return dbRefs;
  }

  /**
   * @param dbRefs
   *          the dbRefs to set
   */
  public void setDbRefs(Vector dbRefs)
  {
    this.dbRefs = dbRefs;
  }

  /**
   * @return the desc
   */
  public String getDesc()
  {
    return desc;
  }

  /**
   * @param desc
   *          the desc to set
   */
  public void setDesc(String desc)
  {
    this.desc = desc;
  }

  /**
   * @return the features
   */
  public Vector getFeatures()
  {
    return features;
  }

  /**
   * @param features
   *          the features to set
   */
  public void setFeatures(Vector features)
  {
    this.features = features;
  }

  /**
   * @return the keywords
   */
  public Vector getKeywords()
  {
    return keywords;
  }

  /**
   * @param keywords
   *          the keywords to set
   */
  public void setKeywords(Vector keywords)
  {
    this.keywords = keywords;
  }

  /**
   * @return the lastUpdated
   */
  public String getLastUpdated()
  {
    return lastUpdated;
  }

  /**
   * @param lastUpdated
   *          the lastUpdated to set
   */
  public void setLastUpdated(String lastUpdated)
  {
    this.lastUpdated = lastUpdated;
  }

  /**
   * @return the refs
   */
  public Vector getRefs()
  {
    return refs;
  }

  /**
   * @param refs
   *          the refs to set
   */
  public void setRefs(Vector refs)
  {
    this.refs = refs;
  }

  /**
   * @return the releaseCreated
   */
  public String getRCreated()
  {
    return rCreated;
  }

  /**
   * @param releaseCreated
   *          the releaseCreated to set
   */
  public void setRcreated(String releaseCreated)
  {
    this.rCreated = releaseCreated;
  }

  /**
   * @return the releaseLastUpdated
   */
  public String getRLastUpdated()
  {
    return rLastUpdated;
  }

  /**
   * @param releaseLastUpdated
   *          the releaseLastUpdated to set
   */
  public void setRLastUpdated(String releaseLastUpdated)
  {
    this.rLastUpdated = releaseLastUpdated;
  }

  /**
   * @return the sequence
   */
  public EmblSequence getSequence()
  {
    return sequence;
  }

  /**
   * @param sequence
   *          the sequence to set
   */
  public void setSequence(EmblSequence sequence)
  {
    this.sequence = sequence;
  }

  /**
   * @return the taxDivision
   */
  public String getTaxDivision()
  {
    return taxDivision;
  }

  /**
   * @param taxDivision
   *          the taxDivision to set
   */
  public void setTaxDivision(String taxDivision)
  {
    this.taxDivision = taxDivision;
  }

  /**
   * @return the version
   */
  public String getVersion()
  {
    return version;
  }

  /**
   * @param version
   *          the version to set
   */
  public void setVersion(String version)
  {
    this.version = version;
  }

  /*
   * EMBL Feature support is limited. The text below is included for the benefit
   * of any developer working on improving EMBL feature import in Jalview.
   * Extract from EMBL feature specification see
   * http://www.embl-ebi.ac.uk/embl/Documentation
   * /FT_definitions/feature_table.html 3.5 Location 3.5.1 Purpose
   * 
   * The location indicates the region of the presented sequence which
   * corresponds to a feature.
   * 
   * 3.5.2 Format and conventions The location contains at least one sequence
   * location descriptor and may contain one or more operators with one or more
   * sequence location descriptors. Base numbers refer to the numbering in the
   * entry. This numbering designates the first base (5' end) of the presented
   * sequence as base 1. Base locations beyond the range of the presented
   * sequence may not be used in location descriptors, the only exception being
   * location in a remote entry (see 3.5.2.1, e).
   * 
   * Location operators and descriptors are discussed in more detail below.
   * 
   * 3.5.2.1 Location descriptors
   * 
   * The location descriptor can be one of the following: (a) a single base
   * number (b) a site between two indicated adjoining bases (c) a single base
   * chosen from within a specified range of bases (not allowed for new entries)
   * (d) the base numbers delimiting a sequence span (e) a remote entry
   * identifier followed by a local location descriptor (i.e., a-d)
   * 
   * A site between two adjoining nucleotides, such as endonucleolytic cleavage
   * site, is indicated by listing the two points separated by a carat (^). The
   * permitted formats for this descriptor are n^n+1 (for example 55^56), or,
   * for circular molecules, n^1, where "n" is the full length of the molecule,
   * ie 1000^1 for circular molecule with length 1000.
   * 
   * A single base chosen from a range of bases is indicated by the first base
   * number and the last base number of the range separated by a single period
   * (e.g., '12.21' indicates a single base taken from between the indicated
   * points). From October 2006 the usage of this descriptor is restricted : it
   * is illegal to use "a single base from a range" (c) either on its own or in
   * combination with the "sequence span" (d) descriptor for newly created
   * entries. The existing entries where such descriptors exist are going to be
   * retrofitted.
   * 
   * Sequence spans are indicated by the starting base number and the ending
   * base number separated by two periods (e.g., '34..456'). The '<' and '>'
   * symbols may be used with the starting and ending base numbers to indicate
   * that an end point is beyond the specified base number. The starting and
   * ending base positions can be represented as distinct base numbers
   * ('34..456') or a site between two indicated adjoining bases.
   * 
   * A location in a remote entry (not the entry to which the feature table
   * belongs) can be specified by giving the accession-number and sequence
   * version of the remote entry, followed by a colon ":", followed by a
   * location descriptor which applies to that entry's sequence (i.e.
   * J12345.1:1..15, see also examples below)
   * 
   * 3.5.2.2 Operators
   * 
   * The location operator is a prefix that specifies what must be done to the
   * indicated sequence to find or construct the location corresponding to the
   * feature. A list of operators is given below with their definitions and most
   * common format.
   * 
   * complement(location) Find the complement of the presented sequence in the
   * span specified by " location" (i.e., read the complement of the presented
   * strand in its 5'-to-3' direction)
   * 
   * join(location,location, ... location) The indicated elements should be
   * joined (placed end-to-end) to form one contiguous sequence
   * 
   * order(location,location, ... location) The elements can be found in the
   * specified order (5' to 3' direction), but nothing is implied about the
   * reasonableness about joining them
   * 
   * Note : location operator "complement" can be used in combination with
   * either " join" or "order" within the same location; combinations of "join"
   * and "order" within the same location (nested operators) are illegal.
   * 
   * 
   * 
   * 3.5.3 Location examples
   * 
   * The following is a list of common location descriptors with their meanings:
   * 
   * Location Description
   * 
   * 467 Points to a single base in the presented sequence
   * 
   * 340..565 Points to a continuous range of bases bounded by and including the
   * starting and ending bases
   * 
   * <345..500 Indicates that the exact lower boundary point of a feature is
   * unknown. The location begins at some base previous to the first base
   * specified (which need not be contained in the presented sequence) and
   * continues to and includes the ending base
   * 
   * <1..888 The feature starts before the first sequenced base and continues to
   * and includes base 888
   * 
   * 1..>888 The feature starts at the first sequenced base and continues beyond
   * base 888
   * 
   * 102.110 Indicates that the exact location is unknown but that it is one of
   * the bases between bases 102 and 110, inclusive
   * 
   * 123^124 Points to a site between bases 123 and 124
   * 
   * join(12..78,134..202) Regions 12 to 78 and 134 to 202 should be joined to
   * form one contiguous sequence
   * 
   * 
   * complement(34..126) Start at the base complementary to 126 and finish at
   * the base complementary to base 34 (the feature is on the strand
   * complementary to the presented strand)
   * 
   * 
   * complement(join(2691..4571,4918..5163)) Joins regions 2691 to 4571 and 4918
   * to 5163, then complements the joined segments (the feature is on the strand
   * complementary to the presented strand)
   * 
   * join(complement(4918..5163),complement(2691..4571)) Complements regions
   * 4918 to 5163 and 2691 to 4571, then joins the complemented segments (the
   * feature is on the strand complementary to the presented strand)
   * 
   * J00194.1:100..202 Points to bases 100 to 202, inclusive, in the entry (in
   * this database) with primary accession number 'J00194'
   * 
   * join(1..100,J00194.1:100..202) Joins region 1..100 of the existing entry
   * with the region 100..202 of remote entry J00194
   */
  /**
   * Recover annotated sequences from EMBL file
   * 
   * @param noNa
   *          don't return nucleic acid sequences
   * @param sourceDb
   *          TODO
   * @param noProtein
   *          don't return any translated protein sequences marked in features
   * @return dataset sequences with DBRefs and features - DNA always comes first
   */
  public jalview.datamodel.SequenceI[] getSequences(boolean noNa,
          boolean noPeptide, String sourceDb)
  { // TODO: ensure emblEntry.getSequences behaves correctly for returning all
    // cases of noNa and noPeptide
    Vector seqs = new Vector();
    Sequence dna = null;
    if (!noNa)
    {
      // In theory we still need to create this if noNa is set to avoid a null
      // pointer exception
      dna = new Sequence(sourceDb + "|" + accession, sequence.getSequence());
      dna.setDescription(desc);
      DBRefEntry retrievedref = new DBRefEntry(sourceDb, version, accession);
      dna.addDBRef(retrievedref);
      // add map to indicate the sequence is a valid coordinate frame for the
      // dbref
      retrievedref.setMap(new Mapping(null, new int[]
      { 1, dna.getLength() }, new int[]
      { 1, dna.getLength() }, 1, 1));
      // TODO: transform EMBL Database refs to canonical form
      if (dbRefs != null)
        for (Iterator i = dbRefs.iterator(); i.hasNext(); dna
                .addDBRef((DBRefEntry) i.next()))
          ;
    }
    try
    {
      for (Iterator i = features.iterator(); i.hasNext();)
      {
        EmblFeature feature = (EmblFeature) i.next();
        if (!noNa)
        {
          if (feature.dbRefs != null && feature.dbRefs.size() > 0)
          {
            for (Iterator dbr = feature.dbRefs.iterator(); dbr.hasNext(); dna
                    .addDBRef((DBRefEntry) dbr.next()))
              ;
          }
        }
        if (FeatureProperties.isCodingFeature(sourceDb, feature.getName()))
        {
          parseCodingFeature(feature, sourceDb, seqs, dna, noPeptide);
        }
        else
        {
          // General feature type.
          if (!noNa)
          {
            if (feature.dbRefs != null && feature.dbRefs.size() > 0)
            {
              for (Iterator dbr = feature.dbRefs.iterator(); dbr.hasNext(); dna
                      .addDBRef((DBRefEntry) dbr.next()))
                ;
            }
          }
        }
      }
    } catch (Exception e)
    {
      System.err.println("EMBL Record Features parsing error!");
      System.err
              .println("Please report the following to help@jalview.org :");
      System.err.println("EMBL Record " + accession);
      System.err.println("Resulted in exception: " + e.getMessage());
      e.printStackTrace(System.err);
    }
    if (!noNa && dna != null)
    {
      seqs.add(dna);
    }
    SequenceI[] sqs = new SequenceI[seqs.size()];
    for (int i = 0, j = seqs.size(); i < j; i++)
    {
      sqs[i] = (SequenceI) seqs.elementAt(i);
      seqs.set(i, null);
    }
    return sqs;
  }

  /**
   * attempt to extract coding region and product from a feature and properly
   * decorate it with annotations.
   * 
   * @param feature
   *          coding feature
   * @param sourceDb
   *          source database for the EMBLXML
   * @param seqs
   *          place where sequences go
   * @param dna
   *          parent dna sequence for this record
   * @param noPeptide
   *          flag for generation of Peptide sequence objects
   */
  private void parseCodingFeature(EmblFeature feature, String sourceDb,
          Vector seqs, Sequence dna, boolean noPeptide)
  {
    boolean isEmblCdna = sourceDb.equals(DBRefSource.EMBLCDS);
    // extract coding region(s)
    jalview.datamodel.Mapping map = null;
    int[] exon = null;
    if (feature.locations != null && feature.locations.size() > 0)
    {
      for (Enumeration locs = feature.locations.elements(); locs
              .hasMoreElements();)
      {
        EmblFeatureLocations loc = (EmblFeatureLocations) locs
                .nextElement();
        int[] se = loc.getElementRanges(accession);
        if (exon == null)
        {
          exon = se;
        }
        else
        {
          int[] t = new int[exon.length + se.length];
          System.arraycopy(exon, 0, t, 0, exon.length);
          System.arraycopy(se, 0, t, exon.length, se.length);
          exon = t;
        }
      }
    }
    String prseq = null;
    String prname = new String();
    String prid = null;
    Hashtable vals = new Hashtable();
    int prstart = 1;
    // get qualifiers
    if (feature.getQualifiers() != null
            && feature.getQualifiers().size() > 0)
    {
      for (Iterator quals = feature.getQualifiers().iterator(); quals
              .hasNext();)
      {
        Qualifier q = (Qualifier) quals.next();
        if (q.getName().equals("translation"))
        {
          StringBuffer prsq = new StringBuffer(q.getValues()[0]);
          int p = prsq.indexOf(" ");
          while (p > -1)
          {
            prsq.deleteCharAt(p);
            p = prsq.indexOf(" ", p);
          }
          prseq = prsq.toString();
          prsq = null;

        }
        else if (q.getName().equals("protein_id"))
        {
          prid = q.getValues()[0];
        }
        else if (q.getName().equals("codon_start"))
        {
          prstart = Integer.parseInt(q.getValues()[0]);
        }
        else if (q.getName().equals("product"))
        {
          prname = q.getValues()[0];
        }
        else
        {
          // throw anything else into the additional properties hash
          String[] s = q.getValues();
          StringBuffer sb = new StringBuffer();
          if (s != null)
          {
            for (int i = 0; i < s.length; i++)
            {
              sb.append(s[i]);
              sb.append("\n");
            }
          }
          vals.put(q.getName(), sb.toString());
        }
      }
    }
    Sequence product = null;
    exon = adjustForPrStart(prstart, exon);

    if (prseq != null && prname != null && prid != null)
    {
      // extract proteins.
      product = new Sequence(prid, prseq, 1, prseq.length());
      product.setDescription(((prname.length() == 0) ? "Protein Product from "
              + sourceDb
              : prname));
      if (!noPeptide)
      {
        // Protein is also added to vector of sequences returned
        seqs.add(product);
      }
      // we have everything - create the mapping and perhaps the protein
      // sequence
      if (exon == null || exon.length == 0)
      {
        System.err
                .println("Implementation Notice: EMBLCDS records not properly supported yet - Making up the CDNA region of this sequence... may be incorrect ("
                        + sourceDb + ":" + getAccession() + ")");
        if (prseq.length() * 3 == (1 - prstart + dna.getSequence().length))
        {
          System.err
                  .println("Not allowing for additional stop codon at end of cDNA fragment... !");
          // this might occur for CDS sequences where no features are
          // marked.
          exon = new int[]
          { dna.getStart() + (prstart - 1), dna.getEnd() };
          map = new jalview.datamodel.Mapping(product, exon, new int[]
          { 1, prseq.length() }, 3, 1);
        }
        if ((prseq.length() + 1) * 3 == (1 - prstart + dna.getSequence().length))
        {
          System.err
                  .println("Allowing for additional stop codon at end of cDNA fragment... will probably cause an error in VAMSAs!");
          exon = new int[]
          { dna.getStart() + (prstart - 1), dna.getEnd() - 3 };
          map = new jalview.datamodel.Mapping(product, exon, new int[]
          { 1, prseq.length() }, 3, 1);
        }
      }
      else
      {
        // Trim the exon mapping if necessary - the given product may only be a
        // fragment of a larger protein. (EMBL:AY043181 is an example)

        if (isEmblCdna)
        {
          // TODO: Add a DbRef back to the parent EMBL sequence with the exon
          // map
          // if given a dataset reference, search dataset for parent EMBL
          // sequence if it exists and set its map
          // make a new feature annotating the coding contig
        }
        else
        {
          // final product length trunctation check

          map = new jalview.datamodel.Mapping(product,
                  adjustForProteinLength(prseq.length(), exon), new int[]
                  { 1, prseq.length() }, 3, 1);
          // reconstruct the EMBLCDS entry
          // TODO: this is only necessary when there codon annotation is
          // complete (I think JBPNote)
          DBRefEntry pcdnaref = new DBRefEntry();
          pcdnaref.setAccessionId(prid);
          pcdnaref.setSource(DBRefSource.EMBLCDS);
          pcdnaref.setVersion(getVersion()); // same as parent EMBL version.
          jalview.util.MapList mp = new jalview.util.MapList(new int[]
          { 1, prseq.length() }, new int[]
          { 1 + (prstart - 1), (prstart - 1) + 3 * prseq.length() }, 1, 3);
          // { 1 + (prstart - 1) * 3,
          // 1 + (prstart - 1) * 3 + prseq.length() * 3 - 1 }, new int[]
          // { 1prstart, prstart + prseq.length() - 1 }, 3, 1);
          pcdnaref.setMap(new Mapping(mp));
          if (product != null)
            product.addDBRef(pcdnaref);

        }
      }
      // add cds feature to dna seq - this may include the stop codon
      for (int xint = 0; exon != null && xint < exon.length; xint += 2)
      {
        SequenceFeature sf = new SequenceFeature();
        sf.setBegin(exon[xint]);
        sf.setEnd(exon[xint + 1]);
        sf.setType(feature.getName());
        sf.setFeatureGroup(sourceDb);
        sf.setDescription("Exon " + (1 + (int) (xint / 2))
                + " for protein '" + prname + "' EMBLCDS:" + prid);
        sf.setValue(FeatureProperties.EXONPOS, new Integer(1 + xint));
        sf.setValue(FeatureProperties.EXONPRODUCT, prname);
        if (vals != null && vals.size() > 0)
        {
          Enumeration kv = vals.elements();
          while (kv.hasMoreElements())
          {
            Object key = kv.nextElement();
            if (key != null)
              sf.setValue(key.toString(), vals.get(key));
          }
        }
        dna.addSequenceFeature(sf);
      }
    }
    // add dbRefs to sequence
    if (feature.dbRefs != null && feature.dbRefs.size() > 0)
    {
      for (Iterator dbr = feature.dbRefs.iterator(); dbr.hasNext();)
      {
        DBRefEntry ref = (DBRefEntry) dbr.next();
        ref.setSource(jalview.util.DBRefUtils.getCanonicalName(ref
                .getSource()));
        // Hard code the kind of protein product accessions that EMBL cite
        if (ref.getSource().equals(jalview.datamodel.DBRefSource.UNIPROT))
        {
          ref.setMap(map);
          if (map != null && map.getTo() != null)
          {
            map.getTo().addDBRef(
                    new DBRefEntry(ref.getSource(), ref.getVersion(), ref
                            .getAccessionId())); // don't copy map over.
            if (map.getTo().getName().indexOf(prid) == 0)
            {
              map.getTo().setName(
                      jalview.datamodel.DBRefSource.UNIPROT + "|"
                              + ref.getAccessionId());
            }
          }
        }
        if (product != null)
        {
          DBRefEntry pref = new DBRefEntry(ref.getSource(),
                  ref.getVersion(), ref.getAccessionId());
          pref.setMap(null); // reference is direct
          product.addDBRef(pref);
          // Add converse mapping reference
          if (map != null)
          {
            Mapping pmap = new Mapping(dna, map.getMap().getInverse());
            pref = new DBRefEntry(sourceDb, getVersion(),
                    this.getAccession());
            pref.setMap(pmap);
            if (map.getTo() != null)
            {
              map.getTo().addDBRef(pref);
            }
          }
        }
        dna.addDBRef(ref);
      }
    }
  }

  private int[] adjustForPrStart(int prstart, int[] exon)
  {

    int origxon[], sxpos = -1;
    int sxstart, sxstop; // unnecessary variables used for debugging
    // first adjust range for codon start attribute
    if (prstart > 1)
    {
      origxon = new int[exon.length];
      System.arraycopy(exon, 0, origxon, 0, exon.length);
      int cdspos = 0;
      for (int x = 0; x < exon.length && sxpos == -1; x += 2)
      {
        cdspos += exon[x + 1] - exon[x] + 1;
        if (prstart <= cdspos)
        {
          sxpos = x;
          sxstart = exon[x];
          sxstop = exon[x + 1];
          // and adjust start boundary of first exon.
          exon[x] = exon[x + 1] - cdspos + prstart;
          break;
        }
      }

      if (sxpos > 0)
      {
        int[] nxon = new int[exon.length - sxpos];
        System.arraycopy(exon, sxpos, nxon, 0, exon.length - sxpos);
        exon = nxon;
      }
    }
    return exon;
  }

  /**
   * truncate the last exon interval to the prlength'th codon
   * 
   * @param prlength
   * @param exon
   * @return new exon
   */
  private int[] adjustForProteinLength(int prlength, int[] exon)
  {

    int origxon[], sxpos = -1, endxon = 0, cdslength = prlength * 3;
    int sxstart, sxstop; // unnecessary variables used for debugging
    // first adjust range for codon start attribute
    if (prlength >= 1 && exon != null)
    {
      origxon = new int[exon.length];
      System.arraycopy(exon, 0, origxon, 0, exon.length);
      int cdspos = 0;
      for (int x = 0; x < exon.length && sxpos == -1; x += 2)
      {
        cdspos += exon[x + 1] - exon[x] + 1;
        if (cdslength <= cdspos)
        {
          // advanced beyond last codon.
          sxpos = x;
          sxstart = exon[x];
          sxstop = exon[x + 1];
          if (cdslength != cdspos)
          {
            System.err
                    .println("Truncating final exon interval on region by "
                            + (cdspos - cdslength));
          }
          // locate the new end boundary of final exon as endxon
          endxon = exon[x + 1] - cdspos + cdslength;
          break;
        }
      }

      if (sxpos != -1)
      {
        // and trim the exon interval set if necessary
        int[] nxon = new int[sxpos + 2];
        System.arraycopy(exon, 0, nxon, 0, sxpos + 2);
        nxon[sxpos + 1] = endxon; // update the end boundary for the new exon
                                  // set
        exon = nxon;
      }
    }
    return exon;
  }
}
