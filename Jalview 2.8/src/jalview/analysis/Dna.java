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

import java.util.Hashtable;
import java.util.Vector;

import jalview.datamodel.AlignedCodonFrame;
import jalview.datamodel.Alignment;
import jalview.datamodel.AlignmentAnnotation;
import jalview.datamodel.AlignmentI;
import jalview.datamodel.Annotation;
import jalview.datamodel.DBRefEntry;
import jalview.datamodel.FeatureProperties;
import jalview.datamodel.Mapping;
import jalview.datamodel.Sequence;
import jalview.datamodel.SequenceFeature;
import jalview.datamodel.SequenceI;
import jalview.schemes.ResidueProperties;
import jalview.util.MapList;
import jalview.util.ShiftList;

public class Dna
{
  /**
   * 
   * @param cdp1
   * @param cdp2
   * @return -1 if cdp1 aligns before cdp2, 0 if in the same column or cdp2 is
   *         null, +1 if after cdp2
   */
  private static int compare_codonpos(int[] cdp1, int[] cdp2)
  {
    if (cdp2 == null
            || (cdp1[0] == cdp2[0] && cdp1[1] == cdp2[1] && cdp1[2] == cdp2[2]))
      return 0;
    if (cdp1[0] < cdp2[0] || cdp1[1] < cdp2[1] || cdp1[2] < cdp2[2])
      return -1; // one base in cdp1 precedes the corresponding base in the
    // other codon
    return 1; // one base in cdp1 appears after the corresponding base in the
    // other codon.
  }

  /**
   * DNA->mapped protein sequence alignment translation given set of sequences
   * 1. id distinct coding regions within selected region for each sequence 2.
   * generate peptides based on inframe (or given) translation or (optionally
   * and where specified) out of frame translations (annotated appropriately) 3.
   * align peptides based on codon alignment
   */
  /**
   * id potential products from dna 1. search for distinct products within
   * selected region for each selected sequence 2. group by associated DB type.
   * 3. return as form for input into above function
   */
  /**
   * 
   */
  /**
   * create a new alignment of protein sequences by an inframe translation of
   * the provided NA sequences
   * 
   * @param selection
   * @param seqstring
   * @param viscontigs
   * @param gapCharacter
   * @param annotations
   * @param aWidth
   * @param dataset
   *          destination dataset for translated sequences and mappings
   * @return
   */
  public static AlignmentI CdnaTranslate(SequenceI[] selection,
          String[] seqstring, int viscontigs[], char gapCharacter,
          AlignmentAnnotation[] annotations, int aWidth, Alignment dataset)
  {
    return CdnaTranslate(selection, seqstring, null, viscontigs,
            gapCharacter, annotations, aWidth, dataset);
  }

  /**
   * 
   * @param selection
   * @param seqstring
   * @param product
   *          - array of DbRefEntry objects from which exon map in seqstring is
   *          derived
   * @param viscontigs
   * @param gapCharacter
   * @param annotations
   * @param aWidth
   * @param dataset
   * @return
   */
  public static AlignmentI CdnaTranslate(SequenceI[] selection,
          String[] seqstring, DBRefEntry[] product, int viscontigs[],
          char gapCharacter, AlignmentAnnotation[] annotations, int aWidth,
          Alignment dataset)
  {
    AlignedCodonFrame codons = new AlignedCodonFrame(aWidth); // stores hash of
    // subsequent
    // positions for
    // each codon
    // start position
    // in alignment
    int s, sSize = selection.length;
    Vector pepseqs = new Vector();
    for (s = 0; s < sSize; s++)
    {
      SequenceI newseq = translateCodingRegion(selection[s], seqstring[s],
              viscontigs, codons, gapCharacter,
              (product != null) ? product[s] : null); // possibly anonymous
      // product
      if (newseq != null)
      {
        pepseqs.addElement(newseq);
        SequenceI ds = newseq;
        while (ds.getDatasetSequence() != null)
        {
          ds = ds.getDatasetSequence();
        }
        dataset.addSequence(ds);
      }
    }
    if (codons.aaWidth == 0)
      return null;
    SequenceI[] newseqs = new SequenceI[pepseqs.size()];
    pepseqs.copyInto(newseqs);
    AlignmentI al = new Alignment(newseqs);
    al.padGaps(); // ensure we look aligned.
    al.setDataset(dataset);
    translateAlignedAnnotations(annotations, al, codons);
    al.addCodonFrame(codons);
    return al;
  }

  /**
   * fake the collection of DbRefs with associated exon mappings to identify if
   * a translation would generate distinct product in the currently selected
   * region.
   * 
   * @param selection
   * @param viscontigs
   * @return
   */
  public static boolean canTranslate(SequenceI[] selection,
          int viscontigs[])
  {
    for (int gd = 0; gd < selection.length; gd++)
    {
      SequenceI dna = selection[gd];
      jalview.datamodel.DBRefEntry[] dnarefs = jalview.util.DBRefUtils
              .selectRefs(dna.getDBRef(),
                      jalview.datamodel.DBRefSource.DNACODINGDBS);
      if (dnarefs != null)
      {
        // intersect with pep
        // intersect with pep
        Vector mappedrefs = new Vector();
        DBRefEntry[] refs = dna.getDBRef();
        for (int d = 0; d < refs.length; d++)
        {
          if (refs[d].getMap() != null && refs[d].getMap().getMap() != null
                  && refs[d].getMap().getMap().getFromRatio() == 3
                  && refs[d].getMap().getMap().getToRatio() == 1)
          {
            mappedrefs.addElement(refs[d]); // add translated protein maps
          }
        }
        dnarefs = new DBRefEntry[mappedrefs.size()];
        mappedrefs.copyInto(dnarefs);
        for (int d = 0; d < dnarefs.length; d++)
        {
          Mapping mp = dnarefs[d].getMap();
          if (mp != null)
          {
            for (int vc = 0; vc < viscontigs.length; vc += 2)
            {
              int[] mpr = mp.locateMappedRange(viscontigs[vc],
                      viscontigs[vc + 1]);
              if (mpr != null)
              {
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  /**
   * generate a set of translated protein products from annotated sequenceI
   * 
   * @param selection
   * @param viscontigs
   * @param gapCharacter
   * @param dataset
   *          destination dataset for translated sequences
   * @param annotations
   * @param aWidth
   * @return
   */
  public static AlignmentI CdnaTranslate(SequenceI[] selection,
          int viscontigs[], char gapCharacter, Alignment dataset)
  {
    int alwidth = 0;
    Vector cdnasqs = new Vector();
    Vector cdnasqi = new Vector();
    Vector cdnaprod = new Vector();
    for (int gd = 0; gd < selection.length; gd++)
    {
      SequenceI dna = selection[gd];
      jalview.datamodel.DBRefEntry[] dnarefs = jalview.util.DBRefUtils
              .selectRefs(dna.getDBRef(),
                      jalview.datamodel.DBRefSource.DNACODINGDBS);
      if (dnarefs != null)
      {
        // intersect with pep
        Vector mappedrefs = new Vector();
        DBRefEntry[] refs = dna.getDBRef();
        for (int d = 0; d < refs.length; d++)
        {
          if (refs[d].getMap() != null && refs[d].getMap().getMap() != null
                  && refs[d].getMap().getMap().getFromRatio() == 3
                  && refs[d].getMap().getMap().getToRatio() == 1)
          {
            mappedrefs.addElement(refs[d]); // add translated protein maps
          }
        }
        dnarefs = new DBRefEntry[mappedrefs.size()];
        mappedrefs.copyInto(dnarefs);
        for (int d = 0; d < dnarefs.length; d++)
        {
          Mapping mp = dnarefs[d].getMap();
          StringBuffer sqstr = new StringBuffer();
          if (mp != null)
          {
            Mapping intersect = mp.intersectVisContigs(viscontigs);
            // generate seqstring for this sequence based on mapping

            if (sqstr.length() > alwidth)
              alwidth = sqstr.length();
            cdnasqs.addElement(sqstr.toString());
            cdnasqi.addElement(dna);
            cdnaprod.addElement(intersect);
          }
        }
      }
      SequenceI[] cdna = new SequenceI[cdnasqs.size()];
      DBRefEntry[] prods = new DBRefEntry[cdnaprod.size()];
      String[] xons = new String[cdnasqs.size()];
      cdnasqs.copyInto(xons);
      cdnaprod.copyInto(prods);
      cdnasqi.copyInto(cdna);
      return CdnaTranslate(cdna, xons, prods, viscontigs, gapCharacter,
              null, alwidth, dataset);
    }
    return null;
  }

  /**
   * translate na alignment annotations onto translated amino acid alignment al
   * using codon mapping codons
   * 
   * @param annotations
   * @param al
   * @param codons
   */
  public static void translateAlignedAnnotations(
          AlignmentAnnotation[] annotations, AlignmentI al,
          AlignedCodonFrame codons)
  {
    // //////////////////////////////
    // Copy annotations across
    //
    // Can only do this for columns with consecutive codons, or where
    // annotation is sequence associated.

    int pos, a, aSize;
    if (annotations != null)
    {
      for (int i = 0; i < annotations.length; i++)
      {
        // Skip any autogenerated annotation
        if (annotations[i].autoCalculated)
        {
          continue;
        }

        aSize = codons.getaaWidth(); // aa alignment width.
        jalview.datamodel.Annotation[] anots = (annotations[i].annotations == null) ? null
                : new jalview.datamodel.Annotation[aSize];
        if (anots != null)
        {
          for (a = 0; a < aSize; a++)
          {
            // process through codon map.
            if (codons.codons[a] != null
                    && codons.codons[a][0] == (codons.codons[a][2] - 2))
            {
              anots[a] = getCodonAnnotation(codons.codons[a],
                      annotations[i].annotations);
            }
          }
        }

        jalview.datamodel.AlignmentAnnotation aa = new jalview.datamodel.AlignmentAnnotation(
                annotations[i].label, annotations[i].description, anots);
        aa.graph = annotations[i].graph;
        aa.graphGroup = annotations[i].graphGroup;
        aa.graphHeight = annotations[i].graphHeight;
        if (annotations[i].getThreshold() != null)
        {
          aa.setThreshold(new jalview.datamodel.GraphLine(annotations[i]
                  .getThreshold()));
        }
        if (annotations[i].hasScore)
        {
          aa.setScore(annotations[i].getScore());
        }
        if (annotations[i].sequenceRef != null)
        {
          SequenceI aaSeq = codons
                  .getAaForDnaSeq(annotations[i].sequenceRef);
          if (aaSeq != null)
          {
            // aa.compactAnnotationArray(); // throw away alignment annotation
            // positioning
            aa.setSequenceRef(aaSeq);
            aa.createSequenceMapping(aaSeq, aaSeq.getStart(), true); // rebuild
            // mapping
            aa.adjustForAlignment();
            aaSeq.addAlignmentAnnotation(aa);
          }

        }
        al.addAnnotation(aa);
      }
    }
  }

  private static Annotation getCodonAnnotation(int[] is,
          Annotation[] annotations)
  {
    // Have a look at all the codon positions for annotation and put the first
    // one found into the translated annotation pos.
    int contrib = 0;
    Annotation annot = null;
    for (int p = 0; p < 3; p++)
    {
      if (annotations[is[p]] != null)
      {
        if (annot == null)
        {
          annot = new Annotation(annotations[is[p]]);
          contrib = 1;
        }
        else
        {
          // merge with last
          Annotation cpy = new Annotation(annotations[is[p]]);
          if (annot.colour == null)
          {
            annot.colour = cpy.colour;
          }
          if (annot.description == null || annot.description.length() == 0)
          {
            annot.description = cpy.description;
          }
          if (annot.displayCharacter == null)
          {
            annot.displayCharacter = cpy.displayCharacter;
          }
          if (annot.secondaryStructure == 0)
          {
            annot.secondaryStructure = cpy.secondaryStructure;
          }
          annot.value += cpy.value;
          contrib++;
        }
      }
    }
    if (contrib > 1)
    {
      annot.value /= (float) contrib;
    }
    return annot;
  }

  /**
   * Translate a na sequence
   * 
   * @param selection
   *          sequence displayed under viscontigs visible columns
   * @param seqstring
   *          ORF read in some global alignment reference frame
   * @param viscontigs
   *          mapping from global reference frame to visible seqstring ORF read
   * @param codons
   *          Definition of global ORF alignment reference frame
   * @param gapCharacter
   * @param newSeq
   * @return sequence ready to be added to alignment.
   */
  public static SequenceI translateCodingRegion(SequenceI selection,
          String seqstring, int[] viscontigs, AlignedCodonFrame codons,
          char gapCharacter, DBRefEntry product)
  {
    Vector skip = new Vector();
    int skipint[] = null;
    ShiftList vismapping = new ShiftList(); // map from viscontigs to seqstring
    // intervals
    int vc, scontigs[] = new int[viscontigs.length];
    int npos = 0;
    for (vc = 0; vc < viscontigs.length; vc += 2)
    {
      if (vc == 0)
      {
        vismapping.addShift(npos, viscontigs[vc]);
      }
      else
      {
        // hidden region
        vismapping.addShift(npos, viscontigs[vc] - viscontigs[vc - 1] + 1);
      }
      scontigs[vc] = viscontigs[vc];
      scontigs[vc + 1] = viscontigs[vc + 1];
    }

    StringBuffer protein = new StringBuffer();
    String seq = seqstring.replace('U', 'T');
    char codon[] = new char[3];
    int cdp[] = new int[3], rf = 0, lastnpos = 0, nend;
    int aspos = 0;
    int resSize = 0;
    for (npos = 0, nend = seq.length(); npos < nend; npos++)
    {
      if (!jalview.util.Comparison.isGap(seq.charAt(npos)))
      {
        cdp[rf] = npos; // store position
        codon[rf++] = seq.charAt(npos); // store base
      }
      // filled an RF yet ?
      if (rf == 3)
      {
        String aa = ResidueProperties.codonTranslate(new String(codon));
        rf = 0;
        if (aa == null)
        {
          aa = String.valueOf(gapCharacter);
          if (skipint == null)
          {
            skipint = new int[]
            { cdp[0], cdp[2] };
          }
          skipint[1] = cdp[2];
        }
        else
        {
          if (skipint != null)
          {
            // edit scontigs
            skipint[0] = vismapping.shift(skipint[0]);
            skipint[1] = vismapping.shift(skipint[1]);
            for (vc = 0; vc < scontigs.length; vc += 2)
            {
              if (scontigs[vc + 1] < skipint[0])
              {
                continue;
              }
              if (scontigs[vc] <= skipint[0])
              {
                if (skipint[0] == scontigs[vc])
                {

                }
                else
                {
                  int[] t = new int[scontigs.length + 2];
                  System.arraycopy(scontigs, 0, t, 0, vc - 1);
                  // scontigs[vc]; //
                }
              }
            }
            skip.addElement(skipint);
            skipint = null;
          }
          if (aa.equals("STOP"))
          {
            aa = "X";
          }
          resSize++;
        }
        // insert/delete gaps prior to this codon - if necessary
        boolean findpos = true;
        while (findpos)
        {
          // first ensure that the codons array is long enough.
          codons.checkCodonFrameWidth(aspos);
          // now check to see if we place the aa at the current aspos in the
          // protein alignment
          switch (Dna.compare_codonpos(cdp, codons.codons[aspos]))
          {
          case -1:
            codons.insertAAGap(aspos, gapCharacter);
            findpos = false;
            break;
          case +1:
            // this aa appears after the aligned codons at aspos, so prefix it
            // with a gap
            aa = "" + gapCharacter + aa;
            aspos++;
            // if (aspos >= codons.aaWidth)
            // codons.aaWidth = aspos + 1;
            break; // check the next position for alignment
          case 0:
            // codon aligns at aspos position.
            findpos = false;
          }
        }
        // codon aligns with all other sequence residues found at aspos
        protein.append(aa);
        lastnpos = npos;
        if (codons.codons[aspos] == null)
        {
          // mark this column as aligning to this aligned reading frame
          codons.codons[aspos] = new int[]
          { cdp[0], cdp[1], cdp[2] };
        }
        if (aspos >= codons.aaWidth)
        {
          // update maximum alignment width
          // (we can do this without calling checkCodonFrameWidth because it was
          // already done above)
          codons.setAaWidth(aspos);
        }
        // ready for next translated reading frame alignment position (if any)
        aspos++;
      }
    }
    if (resSize > 0)
    {
      SequenceI newseq = new Sequence(selection.getName(),
              protein.toString());
      if (rf != 0)
      {
        jalview.bin.Cache.log
                .debug("trimming contigs for incomplete terminal codon.");
        // map and trim contigs to ORF region
        vc = scontigs.length - 1;
        lastnpos = vismapping.shift(lastnpos); // place npos in context of
        // whole dna alignment (rather
        // than visible contigs)
        // incomplete ORF could be broken over one or two visible contig
        // intervals.
        while (vc >= 0 && scontigs[vc] > lastnpos)
        {
          if (vc > 0 && scontigs[vc - 1] > lastnpos)
          {
            vc -= 2;
          }
          else
          {
            // correct last interval in list.
            scontigs[vc] = lastnpos;
          }
        }

        if (vc > 0 && (vc + 1) < scontigs.length)
        {
          // truncate map list to just vc elements
          int t[] = new int[vc + 1];
          System.arraycopy(scontigs, 0, t, 0, vc + 1);
          scontigs = t;
        }
        if (vc <= 0)
          scontigs = null;
      }
      if (scontigs != null)
      {
        npos = 0;
        // map scontigs to actual sequence positions on selection
        for (vc = 0; vc < scontigs.length; vc += 2)
        {
          scontigs[vc] = selection.findPosition(scontigs[vc]); // not from 1!
          scontigs[vc + 1] = selection.findPosition(scontigs[vc + 1]); // exclusive
          if (scontigs[vc + 1] == selection.getEnd())
            break;
        }
        // trim trailing empty intervals.
        if ((vc + 2) < scontigs.length)
        {
          int t[] = new int[vc + 2];
          System.arraycopy(scontigs, 0, t, 0, vc + 2);
          scontigs = t;
        }
        /*
         * delete intervals in scontigs which are not translated. 1. map skip
         * into sequence position intervals 2. truncate existing ranges and add
         * new ranges to exclude untranslated regions. if (skip.size()>0) {
         * Vector narange = new Vector(); for (vc=0; vc<scontigs.length; vc++) {
         * narange.addElement(new int[] {scontigs[vc]}); } int sint=0,iv[]; vc =
         * 0; while (sint<skip.size()) { skipint = (int[]) skip.elementAt(sint);
         * do { iv = (int[]) narange.elementAt(vc); if (iv[0]>=skipint[0] &&
         * iv[0]<=skipint[1]) { if (iv[0]==skipint[0]) { // delete beginning of
         * range } else { // truncate range and create new one if necessary iv =
         * (int[]) narange.elementAt(vc+1); if (iv[0]<=skipint[1]) { // truncate
         * range iv[0] = skipint[1]; } else { } } } else if (iv[0]<skipint[0]) {
         * iv = (int[]) narange.elementAt(vc+1); } } while (iv[0]) } }
         */
        MapList map = new MapList(scontigs, new int[]
        { 1, resSize }, 3, 1);

        // update newseq as if it was generated as mapping from product

        if (product != null)
        {
          newseq.setName(product.getSource() + "|"
                  + product.getAccessionId());
          if (product.getMap() != null)
          {
            // Mapping mp = product.getMap();
            // newseq.setStart(mp.getPosition(scontigs[0]));
            // newseq.setEnd(mp
            // .getPosition(scontigs[scontigs.length - 1]));
          }
        }
        transferCodedFeatures(selection, newseq, map, null, null);
        SequenceI rseq = newseq.deriveSequence(); // construct a dataset
        // sequence for our new
        // peptide, regardless.
        // store a mapping (this actually stores a mapping between the dataset
        // sequences for the two sequences
        codons.addMap(selection, rseq, map);
        return rseq;
      }
    }
    // register the mapping somehow
    //
    return null;
  }

  /**
   * Given a peptide newly translated from a dna sequence, copy over and set any
   * features on the peptide from the DNA. If featureTypes is null, all features
   * on the dna sequence are searched (rather than just the displayed ones), and
   * similarly for featureGroups.
   * 
   * @param dna
   * @param pep
   * @param map
   * @param featureTypes
   *          hash who's keys are the displayed feature type strings
   * @param featureGroups
   *          hash where keys are feature groups and values are Boolean objects
   *          indicating if they are displayed.
   */
  private static void transferCodedFeatures(SequenceI dna, SequenceI pep,
          MapList map, Hashtable featureTypes, Hashtable featureGroups)
  {
    SequenceFeature[] sf = dna.getDatasetSequence().getSequenceFeatures();
    Boolean fgstate;
    jalview.datamodel.DBRefEntry[] dnarefs = jalview.util.DBRefUtils
            .selectRefs(dna.getDBRef(),
                    jalview.datamodel.DBRefSource.DNACODINGDBS);
    if (dnarefs != null)
    {
      // intersect with pep
      for (int d = 0; d < dnarefs.length; d++)
      {
        Mapping mp = dnarefs[d].getMap();
        if (mp != null)
        {
        }
      }
    }
    if (sf != null)
    {
      for (int f = 0; f < sf.length; f++)
      {
        fgstate = (featureGroups == null) ? null : ((Boolean) featureGroups
                .get(sf[f].featureGroup));
        if ((featureTypes == null || featureTypes.containsKey(sf[f]
                .getType())) && (fgstate == null || fgstate.booleanValue()))
        {
          if (FeatureProperties.isCodingFeature(null, sf[f].getType()))
          {
            // if (map.intersectsFrom(sf[f].begin, sf[f].end))
            {

            }
          }
        }
      }
    }
  }
}
