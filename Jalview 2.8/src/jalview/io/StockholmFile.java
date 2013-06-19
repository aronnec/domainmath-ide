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
/*
 * This extension was written by Benjamin Schuster-Boeckler at sanger.ac.uk
 */
package jalview.io;

import java.io.*;
import java.util.*;

import com.stevesoft.pat.*;
import jalview.datamodel.*;
import jalview.util.Format;

// import org.apache.log4j.*;

/**
 * This class is supposed to parse a Stockholm format file into Jalview There
 * are TODOs in this class: we do not know what the database source and version
 * is for the file when parsing the #GS= AC tag which associates accessions with
 * sequences. Database references are also not parsed correctly: a separate
 * reference string parser must be added to parse the database reference form
 * into Jalview's local representation.
 * 
 * @author bsb at sanger.ac.uk
 * @version 0.3 + jalview mods
 * 
 */
public class StockholmFile extends AlignFile
{
  // static Logger logger = Logger.getLogger("jalview.io.StockholmFile");
  StringBuffer out; // output buffer

  AlignmentI al;

  public StockholmFile()
  {
  }

  /**
   * Creates a new StockholmFile object for output.
   */
  public StockholmFile(AlignmentI al)
  {
    this.al = al;
  }

  public StockholmFile(String inFile, String type) throws IOException
  {
    super(inFile, type);
  }

  public StockholmFile(FileParse source) throws IOException
  {
    super(source);
  }

  public void initData()
  {
    super.initData();
  }

  /**
   * Parse a file in Stockholm format into Jalview's data model. The file has to
   * be passed at construction time
   * 
   * @throws IOException
   *           If there is an error with the input file
   */
  public void parse() throws IOException
  {
    StringBuffer treeString = new StringBuffer();
    String treeName = null;
    // --------------- Variable Definitions -------------------
    String line;
    String version;
    // String id;
    Hashtable seqAnn = new Hashtable(); // Sequence related annotations
    Hashtable seqs = new Hashtable();
    Regex p, r, rend, s, x;

    // Temporary line for processing RNA annotation
    // String RNAannot = "";

    // ------------------ Parsing File ----------------------
    // First, we have to check that this file has STOCKHOLM format, i.e. the
    // first line must match
    r = new Regex("# STOCKHOLM ([\\d\\.]+)");
    if (!r.search(nextLine()))
    {
      throw new IOException(
              "This file is not in valid STOCKHOLM format: First line does not contain '# STOCKHOLM'");
    }
    else
    {
      version = r.stringMatched(1);
      // logger.debug("Stockholm version: " + version);
    }

    // We define some Regexes here that will be used regularily later
    rend = new Regex("^\\s*\\/\\/"); // Find the end of an alignment
    p = new Regex("(\\S+)\\/(\\d+)\\-(\\d+)"); // split sequence id in
    // id/from/to
    s = new Regex("(\\S+)\\s+(\\S*)\\s+(.*)"); // Parses annotation subtype
    r = new Regex("#=(G[FSRC]?)\\s+(.*)"); // Finds any annotation line
    x = new Regex("(\\S+)\\s+(\\S+)"); // split id from sequence

    // Convert all bracket types to parentheses (necessary for passing to VARNA)
    Regex openparen = new Regex("(<|\\[)", "(");
    Regex closeparen = new Regex("(>|\\])", ")");

    // Detect if file is RNA by looking for bracket types
    Regex detectbrackets = new Regex("(<|>|\\[|\\]|\\(|\\))");

    rend.optimize();
    p.optimize();
    s.optimize();
    r.optimize();
    x.optimize();
    openparen.optimize();
    closeparen.optimize();

    while ((line = nextLine()) != null)
    {
      if (line.length() == 0)
      {
        continue;
      }
      if (rend.search(line))
      {
        // End of the alignment, pass stuff back
        this.noSeqs = seqs.size();

        String seqdb,dbsource = null;
        Regex pf = new Regex("PF[0-9]{5}(.*)"); // Finds AC for Pfam
        Regex rf = new Regex("RF[0-9]{5}(.*)"); // Finds AC for Rfam
        if (getAlignmentProperty("AC") != null)
        {
          String dbType = getAlignmentProperty("AC").toString();
          if (pf.search(dbType))
          {
            // PFAM Alignment - so references are typically from Uniprot
            dbsource = "PFAM";
          }
          else if (rf.search(dbType))
          {
            dbsource = "RFAM";
          }
        }
        // logger.debug("Number of sequences: " + this.noSeqs);
        Enumeration accs = seqs.keys();
        while (accs.hasMoreElements())
        {
          String acc = (String) accs.nextElement();
          // logger.debug("Processing sequence " + acc);
          String seq = (String) seqs.remove(acc);
          if (maxLength < seq.length())
          {
            maxLength = seq.length();
          }
          int start = 1;
          int end = -1;
          String sid = acc;
          /*
           * Retrieve hash of annotations for this accession Associate
           * Annotation with accession
           */
          Hashtable accAnnotations = null;

          if (seqAnn != null && seqAnn.containsKey(acc))
          {
            accAnnotations = (Hashtable) seqAnn.remove(acc);
            // TODO: add structures to sequence
          }

          // Split accession in id and from/to
          if (p.search(acc))
          {
            sid = p.stringMatched(1);
            start = Integer.parseInt(p.stringMatched(2));
            end = Integer.parseInt(p.stringMatched(3));
          }
          // logger.debug(sid + ", " + start + ", " + end);

          Sequence seqO = new Sequence(sid, seq, start, end);
          // Add Description (if any)
          if (accAnnotations != null && accAnnotations.containsKey("DE"))
          {
            String desc = (String) accAnnotations.get("DE");
            seqO.setDescription((desc == null) ? "" : desc);
          }

          // Add DB References (if any)
          if (accAnnotations != null && accAnnotations.containsKey("DR"))
          {
            String dbr = (String) accAnnotations.get("DR");
            if (dbr != null && dbr.indexOf(";") > -1)
            {
              String src = dbr.substring(0, dbr.indexOf(";"));
              String acn = dbr.substring(dbr.indexOf(";") + 1);
              jalview.util.DBRefUtils.parseToDbRef(seqO, src, "0", acn);
            }
          }

          if (accAnnotations != null && accAnnotations.containsKey("AC"))
          {
            if (dbsource != null)
            {
              String dbr = (String) accAnnotations.get("AC");
              if (dbr != null)
              {
                // we could get very clever here - but for now - just try to guess accession type from source of alignment plus structure of accession
                guessDatabaseFor(seqO, dbr, dbsource);
                
              }
            } 
            // else - do what ?  add the data anyway and prompt the user to specify what references these are ?
          }

          Hashtable features = null;
          // We need to adjust the positions of all features to account for gaps
          try
          {
            features = (Hashtable) accAnnotations.remove("features");
          } catch (java.lang.NullPointerException e)
          {
            // loggerwarn("Getting Features for " + acc + ": " +
            // e.getMessage());
            // continue;
          }
          // if we have features
          if (features != null)
          {
            int posmap[] = seqO.findPositionMap();
            Enumeration i = features.keys();
            while (i.hasMoreElements())
            {
              // TODO: parse out secondary structure annotation as annotation
              // row
              // TODO: parse out scores as annotation row
              // TODO: map coding region to core jalview feature types
              String type = i.nextElement().toString();
              Hashtable content = (Hashtable) features.remove(type);

              // add alignment annotation for this feature
              String key = type2id(type);
              if (key != null)
              {
                if (accAnnotations != null
                        && accAnnotations.containsKey(key))
                {
                  Vector vv = (Vector) accAnnotations.get(key);
                  for (int ii = 0; ii < vv.size(); ii++)
                  {
                    AlignmentAnnotation an = (AlignmentAnnotation) vv
                            .elementAt(ii);
                    seqO.addAlignmentAnnotation(an);
                  }
                }
              }

              Enumeration j = content.keys();
              while (j.hasMoreElements())
              {
                String desc = j.nextElement().toString();
                String ns = content.get(desc).toString();
                char[] byChar = ns.toCharArray();
                for (int k = 0; k < byChar.length; k++)
                {
                  char c = byChar[k];
                  if (!(c == ' ' || c == '_' || c == '-' || c == '.')) // PFAM
                  // uses
                  // '.'
                  // for
                  // feature
                  // background
                  {
                    int new_pos = posmap[k]; // look up nearest seqeunce
                    // position to this column
                    SequenceFeature feat = new SequenceFeature(type, desc,
                            new_pos, new_pos, 0f, null);

                    seqO.addSequenceFeature(feat);
                  }
                }
              }

            }

          }
          // garbage collect

          // logger.debug("Adding seq " + acc + " from " + start + " to " + end
          // + ": " + seq);
          this.seqs.addElement(seqO);
        }
        return; // finished parsing this segment of source
      }
      else if (!r.search(line))
      {
        // System.err.println("Found sequence line: " + line);

        // Split sequence in sequence and accession parts
        if (!x.search(line))
        {
          // logger.error("Could not parse sequence line: " + line);
          throw new IOException("Could not parse sequence line: " + line);
        }
        String ns = (String) seqs.get(x.stringMatched(1));
        if (ns == null)
        {
          ns = "";
        }
        ns += x.stringMatched(2);

        seqs.put(x.stringMatched(1), ns);
      }
      else
      {
        String annType = r.stringMatched(1);
        String annContent = r.stringMatched(2);

        // System.err.println("type:" + annType + " content: " + annContent);

        if (annType.equals("GF"))
        {
          /*
           * Generic per-File annotation, free text Magic features: #=GF NH
           * <tree in New Hampshire eXtended format> #=GF TN <Unique identifier
           * for the next tree> Pfam descriptions: 7. DESCRIPTION OF FIELDS
           * 
           * Compulsory fields: ------------------
           * 
           * AC Accession number: Accession number in form PFxxxxx.version or
           * PBxxxxxx. ID Identification: One word name for family. DE
           * Definition: Short description of family. AU Author: Authors of the
           * entry. SE Source of seed: The source suggesting the seed members
           * belong to one family. GA Gathering method: Search threshold to
           * build the full alignment. TC Trusted Cutoff: Lowest sequence score
           * and domain score of match in the full alignment. NC Noise Cutoff:
           * Highest sequence score and domain score of match not in full
           * alignment. TP Type: Type of family -- presently Family, Domain,
           * Motif or Repeat. SQ Sequence: Number of sequences in alignment. AM
           * Alignment Method The order ls and fs hits are aligned to the model
           * to build the full align. // End of alignment.
           * 
           * Optional fields: ----------------
           * 
           * DC Database Comment: Comment about database reference. DR Database
           * Reference: Reference to external database. RC Reference Comment:
           * Comment about literature reference. RN Reference Number: Reference
           * Number. RM Reference Medline: Eight digit medline UI number. RT
           * Reference Title: Reference Title. RA Reference Author: Reference
           * Author RL Reference Location: Journal location. PI Previous
           * identifier: Record of all previous ID lines. KW Keywords: Keywords.
           * CC Comment: Comments. NE Pfam accession: Indicates a nested domain.
           * NL Location: Location of nested domains - sequence ID, start and
           * end of insert.
           * 
           * Obsolete fields: ----------- AL Alignment method of seed: The
           * method used to align the seed members.
           */
          // Let's save the annotations, maybe we'll be able to do something
          // with them later...
          Regex an = new Regex("(\\w+)\\s*(.*)");
          if (an.search(annContent))
          {
            if (an.stringMatched(1).equals("NH"))
            {
              treeString.append(an.stringMatched(2));
            }
            else if (an.stringMatched(1).equals("TN"))
            {
              if (treeString.length() > 0)
              {
                if (treeName == null)
                {
                  treeName = "Tree " + (getTreeCount() + 1);
                }
                addNewickTree(treeName, treeString.toString());
              }
              treeName = an.stringMatched(2);
              treeString = new StringBuffer();
            }
            setAlignmentProperty(an.stringMatched(1), an.stringMatched(2));
          }
        }
        else if (annType.equals("GS"))
        {
          // Generic per-Sequence annotation, free text
          /*
           * Pfam uses these features: Feature Description ---------------------
           * ----------- AC <accession> ACcession number DE <freetext>
           * DEscription DR <db>; <accession>; Database Reference OS <organism>
           * OrganiSm (species) OC <clade> Organism Classification (clade, etc.)
           * LO <look> Look (Color, etc.)
           */
          if (s.search(annContent))
          {
            String acc = s.stringMatched(1);
            String type = s.stringMatched(2);
            String content = s.stringMatched(3);
            // TODO: store DR in a vector.
            // TODO: store AC according to generic file db annotation.
            Hashtable ann;
            if (seqAnn.containsKey(acc))
            {
              ann = (Hashtable) seqAnn.get(acc);
            }
            else
            {
              ann = new Hashtable();
            }
            ann.put(type, content);
            seqAnn.put(acc, ann);
          }
          else
          {
            throw new IOException("Error parsing " + line);
          }
        }
        else if (annType.equals("GC"))
        {
          // Generic per-Column annotation, exactly 1 char per column
          // always need a label.
          if (x.search(annContent))
          {
            // parse out and create alignment annotation directly.
            parseAnnotationRow(annotations, x.stringMatched(1),
                    x.stringMatched(2));
          }
        }
        else if (annType.equals("GR"))
        {
          // Generic per-Sequence AND per-Column markup, exactly 1 char per
          // column
          /*
           * Feature Description Markup letters ------- -----------
           * -------------- SS Secondary Structure [HGIEBTSCX] SA Surface
           * Accessibility [0-9X] (0=0%-10%; ...; 9=90%-100%) TM TransMembrane
           * [Mio] PP Posterior Probability [0-9*] (0=0.00-0.05; 1=0.05-0.15;
           * *=0.95-1.00) LI LIgand binding [*] AS Active Site [*] IN INtron (in
           * or after) [0-2]
           */
          if (s.search(annContent))
          {
            String acc = s.stringMatched(1);
            String type = s.stringMatched(2);
            String seq = new String(s.stringMatched(3));
            String description = null;
            // Check for additional information about the current annotation
            // We use a simple string tokenizer here for speed
            StringTokenizer sep = new StringTokenizer(seq, " \t");
            description = sep.nextToken();
            if (sep.hasMoreTokens())
            {
              seq = sep.nextToken();
            }
            else
            {
              seq = description;
              description = new String();
            }
            // sequence id with from-to fields

            Hashtable ann;
            // Get an object with all the annotations for this sequence
            if (seqAnn.containsKey(acc))
            {
              // logger.debug("Found annotations for " + acc);
              ann = (Hashtable) seqAnn.get(acc);
            }
            else
            {
              // logger.debug("Creating new annotations holder for " + acc);
              ann = new Hashtable();
              seqAnn.put(acc, ann);
            }
            // TODO test structure, call parseAnnotationRow with vector from
            // hashtable for specific sequence
            Hashtable features;
            // Get an object with all the content for an annotation
            if (ann.containsKey("features"))
            {
              // logger.debug("Found features for " + acc);
              features = (Hashtable) ann.get("features");
            }
            else
            {
              // logger.debug("Creating new features holder for " + acc);
              features = new Hashtable();
              ann.put("features", features);
            }

            Hashtable content;
            if (features.containsKey(this.id2type(type)))
            {
              // logger.debug("Found content for " + this.id2type(type));
              content = (Hashtable) features.get(this.id2type(type));
            }
            else
            {
              // logger.debug("Creating new content holder for " +
              // this.id2type(type));
              content = new Hashtable();
              features.put(this.id2type(type), content);
            }
            String ns = (String) content.get(description);
            if (ns == null)
            {
              ns = "";
            }
            ns += seq;
            content.put(description, ns);
            Hashtable strucAnn;
            if (seqAnn.containsKey(acc))
            {
              strucAnn = (Hashtable) seqAnn.get(acc);
            }
            else
            {
              strucAnn = new Hashtable();
            }

            Vector newStruc = new Vector();
            parseAnnotationRow(newStruc, type, ns);
            strucAnn.put(type, newStruc);
            seqAnn.put(acc, strucAnn);
          }
          else
          {
            System.err
                    .println("Warning - couldn't parse sequence annotation row line:\n"
                            + line);
            // throw new IOException("Error parsing " + line);
          }
        }
        else
        {
          throw new IOException("Unknown annotation detected: " + annType
                  + " " + annContent);
        }
      }
    }
    if (treeString.length() > 0)
    {
      if (treeName == null)
      {
        treeName = "Tree " + (1 + getTreeCount());
      }
      addNewickTree(treeName, treeString.toString());
    }
  }

  /**
   * Demangle an accession string and guess the originating sequence database for a given sequence
   * @param seqO sequence to be annotated
   * @param dbr Accession string for sequence
   * @param dbsource source database for alignment (PFAM or RFAM)
   */
  private void guessDatabaseFor(Sequence seqO, String dbr, String dbsource)
  {
    DBRefEntry dbrf=null;
    List<DBRefEntry> dbrs=new ArrayList<DBRefEntry>();
    String seqdb="Unknown",sdbac=""+dbr;
    int st=-1,en=-1,p;
    if ((st=sdbac.indexOf("/"))>-1)
    {
      String num,range=sdbac.substring(st+1);
      sdbac = sdbac.substring(0,st);
      if ((p=range.indexOf("-"))>-1)
      {
        p++;
        if (p<range.length())
        {
        num = range.substring(p).trim();
        try {
          en = Integer.parseInt(num);
        } catch (NumberFormatException x)
        {
          // could warn here that index is invalid
          en = -1;
        }
        }
      } else {
        p=range.length();
      }
      num=range.substring(0,p).trim();
      try {
        st = Integer.parseInt(num);
      } catch (NumberFormatException x)
      {
        // could warn here that index is invalid
        st = -1;
      }
    }
    if (dbsource.equals("PFAM")) {
      seqdb = "UNIPROT";
      if (sdbac.indexOf(".")>-1)
      {
        // strip of last subdomain
        sdbac = sdbac.substring(0,sdbac.indexOf("."));
        dbrf = jalview.util.DBRefUtils.parseToDbRef(seqO, seqdb, dbsource, sdbac);
        if (dbrf!=null)
        {
          dbrs.add(dbrf);
        }
      }
      dbrf = jalview.util.DBRefUtils.parseToDbRef(seqO, dbsource, dbsource, dbr);
      if (dbr!=null)
      {
        dbrs.add(dbrf);
      }
    } else {
      seqdb = "EMBL"; // total guess - could be ENA, or something else these days
      if (sdbac.indexOf(".")>-1)
      {
        // strip off last subdomain
        sdbac = sdbac.substring(0,sdbac.indexOf("."));
        dbrf = jalview.util.DBRefUtils.parseToDbRef(seqO, seqdb, dbsource, sdbac);
        if (dbrf!=null)
        {
          dbrs.add(dbrf);
        }
      }
      
      dbrf = jalview.util.DBRefUtils.parseToDbRef(seqO, dbsource, dbsource, dbr);
      if (dbrf!=null)
      {
        dbrs.add(dbrf);
      }
    }
    if (st!=-1 && en!=-1)
    {
      for (DBRefEntry d:dbrs)
      {
        jalview.util.MapList mp = new jalview.util.MapList(new int[] { seqO.getStart(),seqO.getEnd()},new int[] { st,en},1,1);
        jalview.datamodel.Mapping mping = new Mapping(mp);
        d.setMap(mping);
      }
    }
  }

  protected static AlignmentAnnotation parseAnnotationRow(
          Vector annotation, String label, String annots)
  {
    String convert1, convert2 = null;

    // Convert all bracket types to parentheses
    Regex openparen = new Regex("(<|\\[)", "(");
    Regex closeparen = new Regex("(>|\\])", ")");

    // Detect if file is RNA by looking for bracket types
    Regex detectbrackets = new Regex("(<|>|\\[|\\]|\\(|\\))");

    convert1 = openparen.replaceAll(annots);
    convert2 = closeparen.replaceAll(convert1);
    annots = convert2;

    String type = label;
    if (label.contains("_cons"))
    {
      type = (label.indexOf("_cons") == label.length() - 5) ? label
              .substring(0, label.length() - 5) : label;
    }
    boolean ss = false;
    type = id2type(type);
    if (type.equals("secondary structure"))
    {
      ss = true;
    }
    // decide on secondary structure or not.
    Annotation[] els = new Annotation[annots.length()];
    for (int i = 0; i < annots.length(); i++)
    {
      String pos = annots.substring(i, i + 1);
      Annotation ann;
      ann = new Annotation(pos, "", ' ', 0f); // 0f is 'valid' null - will not
      // be written out
      if (ss)
      {
        if (detectbrackets.search(pos))
        {
          ann.secondaryStructure = jalview.schemes.ResidueProperties
                  .getRNASecStrucState(pos).charAt(0);
        }
        else
        {
          ann.secondaryStructure = jalview.schemes.ResidueProperties
                  .getDssp3state(pos).charAt(0);
        }

        if (ann.secondaryStructure == pos.charAt(0) || pos.charAt(0) == 'C')
        {
          ann.displayCharacter = ""; // null; // " ";
        }
        else
        {
          ann.displayCharacter = " " + ann.displayCharacter;
        }
      }

      els[i] = ann;
    }
    AlignmentAnnotation annot = null;
    Enumeration e = annotation.elements();
    while (e.hasMoreElements())
    {
      annot = (AlignmentAnnotation) e.nextElement();
      if (annot.label.equals(type))
        break;
      annot = null;
    }
    if (annot == null)
    {
      annot = new AlignmentAnnotation(type, type, els);
      annotation.addElement(annot);
    }
    else
    {
      Annotation[] anns = new Annotation[annot.annotations.length
              + els.length];
      System.arraycopy(annot.annotations, 0, anns, 0,
              annot.annotations.length);
      System.arraycopy(els, 0, anns, annot.annotations.length, els.length);
      annot.annotations = anns;
      // System.out.println("else: ");
    }
    return annot;
  }

  public String print(SequenceI[] s)
  {
    // find max length of id
    int max = 0;
    int maxid = 0;
    int in = 0;
    Hashtable dataRef = null;
    while ((in < s.length) && (s[in] != null))
    {
      String tmp = printId(s[in]);
      if (s[in].getSequence().length > max)
      {
        max = s[in].getSequence().length;
      }

      if (tmp.length() > maxid)
      {
        maxid = tmp.length();
      }
      if (s[in].getDBRef() != null)
      {
        for (int idb = 0; idb < s[in].getDBRef().length; idb++)
        {
          if (dataRef == null)
            dataRef = new Hashtable();

          String datAs1 = s[in].getDBRef()[idb].getSource().toString()
                  + " ; "
                  + s[in].getDBRef()[idb].getAccessionId().toString();
          dataRef.put(tmp, datAs1);
        }
      }
      in++;
    }
    maxid += 9;
    int i = 0;

    // output database type
    if (al.getProperties() != null)
    {
      if (!al.getProperties().isEmpty())
      {
        Enumeration key = al.getProperties().keys();
        Enumeration val = al.getProperties().elements();
        while (key.hasMoreElements())
        {
          out.append("#=GF " + key.nextElement() + " " + val.nextElement());
          out.append(newline);
        }
      }
    }

    // output database accessions
    if (dataRef != null)
    {
      Enumeration en = dataRef.keys();
      while (en.hasMoreElements())
      {
        Object idd = en.nextElement();
        String type = (String) dataRef.remove(idd);
        out.append(new Format("%-" + (maxid - 2) + "s").form("#=GS "
                + idd.toString() + " "));
        if (type.contains("PFAM") || type.contains("RFAM"))
        {

          out.append(" AC " + type.substring(type.indexOf(";") + 1));
        }
        else
        {
          out.append(" DR " + type + " ");
        }
        out.append(newline);
      }
    }

    // output annotations
    while (i < s.length && s[i] != null)
    {
      if (s[i].getDatasetSequence() != null)
      {
        SequenceI ds = s[i].getDatasetSequence();
        AlignmentAnnotation[] alAnot;
        Annotation[] ann;
        Annotation annot;
        alAnot = s[i].getAnnotation();
        String feature = "";
        if (alAnot != null)
        {
          for (int j = 0; j < alAnot.length; j++)
          {
            if (ds.getSequenceFeatures() != null)
            {
              feature = ds.getSequenceFeatures()[0].type;
            }
            String key = type2id(feature);

            if (key == null)
              continue;

            // out.append("#=GR ");
            out.append(new Format("%-" + maxid + "s").form("#=GR "
                    + printId(s[i]) + " " + key + " "));
            ann = alAnot[j].annotations;
            String seq = "";
            for (int k = 0; k < ann.length; k++)
            {
              annot = ann[k];
              String ch = (annot == null) ? Character.toString(s[i]
                      .getCharAt(k)) : annot.displayCharacter;
              if (ch.length() == 0)
              {
                if (key.equals("SS"))
                {
                  char ll = annot.secondaryStructure;
                  seq = (Character.toString(ll).equals(" ")) ? seq + "C"
                          : seq + ll;
                }
                else
                {
                  seq += ".";
                }
              }
              else if (ch.length() == 1)
              {
                seq += ch;
              }
              else if (ch.length() > 1)
              {
                seq += ch.charAt(1);
              }
            }
            out.append(seq);
            out.append(newline);
          }
        }
      }

      out.append(new Format("%-" + maxid + "s").form(printId(s[i]) + " "));
      out.append(s[i].getSequenceAsString());
      out.append(newline);
      i++;
    }

    // alignment annotation
    AlignmentAnnotation aa;
    if (al.getAlignmentAnnotation() != null)
    {
      for (int ia = 0; ia < al.getAlignmentAnnotation().length; ia++)
      {
        aa = al.getAlignmentAnnotation()[ia];
        if (aa.autoCalculated || !aa.visible)
        {
          continue;
        }
        String seq = "";
        String label;

        if (aa.label.equals("seq"))
          label = "seq_cons";
        else
          label = type2id(aa.label.toLowerCase()) + "_cons";

        if (label == null)
          label = aa.label;

        out.append(new Format("%-" + maxid + "s").form("#=GC " + label
                + " "));
        for (int j = 0; j < aa.annotations.length; j++)
        {
          String ch = (aa.annotations[j] == null) ? "-"
                  : aa.annotations[j].displayCharacter;
          if (ch.length() == 0)
          {
            char ll = aa.annotations[j].secondaryStructure;
            if (Character.toString(ll).equals(" "))
              seq += "C";
            else
              seq += ll;
          }
          else if (ch.length() == 1)
          {
            seq += ch;
          }
          else if (ch.length() > 1)
          {
            seq += ch.charAt(1);
          }
        }
        out.append(seq);
        out.append(newline);
      }
    }
    return out.toString();
  }

  public String print()
  {
    out = new StringBuffer();
    out.append("# STOCKHOLM 1.0");
    out.append(newline);
    print(getSeqsAsArray());

    out.append("//");
    out.append(newline);
    return out.toString();
  }

  private static Hashtable typeIds = null;
  static
  {
    if (typeIds == null)
    {
      typeIds = new Hashtable();
      typeIds.put("SS", "secondary structure");
      typeIds.put("SA", "surface accessibility");
      typeIds.put("TM", "transmembrane");
      typeIds.put("PP", "posterior probability");
      typeIds.put("LI", "ligand binding");
      typeIds.put("AS", "active site");
      typeIds.put("IN", "intron");
      typeIds.put("IR", "interacting residue");
      typeIds.put("AC", "accession");
      typeIds.put("OS", "organism");
      typeIds.put("CL", "class");
      typeIds.put("DE", "description");
      typeIds.put("DR", "reference");
      typeIds.put("LO", "look");
      typeIds.put("RF", "reference positions");

    }
  }

  protected static String id2type(String id)
  {
    if (typeIds.containsKey(id))
    {
      return (String) typeIds.get(id);
    }
    System.err.println("Warning : Unknown Stockholm annotation type code "
            + id);
    return id;
  }

  protected static String type2id(String type)
  {
    String key = null;
    Enumeration e = typeIds.keys();
    while (e.hasMoreElements())
    {
      Object ll = e.nextElement();
      if (typeIds.get(ll).toString().equals(type))
      {
        key = (String) ll;
        break;
      }
    }
    if (key != null)
    {
      return (String) key;
    }
    System.err.println("Warning : Unknown Stockholm annotation type: "
            + type);
    return key;
  }
  /**
   * //ssline is complete secondary structure line private AlignmentAnnotation
   * addHelices(Vector annotation, String label, String ssline) {
   * 
   * // decide on secondary structure or not. Annotation[] els = new
   * Annotation[ssline.length()]; for (int i = 0; i < ssline.length(); i++) {
   * String pos = ssline.substring(i, i + 1); Annotation ann; ann = new
   * Annotation(pos, "", ' ', 0f); // 0f is 'valid' null - will not
   * 
   * ann.secondaryStructure =
   * jalview.schemes.ResidueProperties.getRNAssState(pos).charAt(0);
   * 
   * ann.displayCharacter = "x" + ann.displayCharacter;
   * 
   * System.out.println(ann.displayCharacter);
   * 
   * els[i] = ann; } AlignmentAnnotation helicesAnnot = null; Enumeration e =
   * annotation.elements(); while (e.hasMoreElements()) { helicesAnnot =
   * (AlignmentAnnotation) e.nextElement(); if (helicesAnnot.label.equals(type))
   * break; helicesAnnot = null; } if (helicesAnnot == null) { helicesAnnot =
   * new AlignmentAnnotation(type, type, els);
   * annotation.addElement(helicesAnnot); } else { Annotation[] anns = new
   * Annotation[helicesAnnot.annotations.length + els.length];
   * System.arraycopy(helicesAnnot.annotations, 0, anns, 0,
   * helicesAnnot.annotations.length); System.arraycopy(els, 0, anns,
   * helicesAnnot.annotations.length, els.length); helicesAnnot.annotations =
   * anns; }
   * 
   * helicesAnnot.features = Rna.GetBasePairs(ssline);
   * Rna.HelixMap(helicesAnnot.features);
   * 
   * 
   * return helicesAnnot; }
   */
}
