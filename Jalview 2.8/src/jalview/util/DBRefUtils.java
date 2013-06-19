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
package jalview.util;

import java.util.*;

import jalview.datamodel.*;

public class DBRefUtils
{
  /**
   * Utilities for handling DBRef objects and their collections.
   */
  /**
   * 
   * @param dbrefs
   *          Vector of DBRef objects to search
   * @param sources
   *          String[] array of source DBRef IDs to retrieve
   * @return Vector
   */
  public static DBRefEntry[] selectRefs(DBRefEntry[] dbrefs,
          String[] sources)
  {
    if (dbrefs == null)
    {
      return null;
    }
    if (sources == null)
    {
      return dbrefs;
    }
    Hashtable srcs = new Hashtable();
    Vector res = new Vector();

    for (int i = 0; i < sources.length; i++)
    {
      srcs.put(new String(sources[i]), new Integer(i));
    }
    for (int i = 0, j = dbrefs.length; i < j; i++)
    {
      if (srcs.containsKey(dbrefs[i].getSource()))
      {
        res.addElement(dbrefs[i]);
      }
    }

    if (res.size() > 0)
    {
      DBRefEntry[] reply = new DBRefEntry[res.size()];
      for (int i = 0; i < res.size(); i++)
      {
        reply[i] = (DBRefEntry) res.elementAt(i);
      }
      return reply;
    }
    res = null;
    // there are probable memory leaks in the hashtable!
    return null;
  }

  /**
   * isDasCoordinateSystem
   * 
   * @param string
   *          String
   * @param dBRefEntry
   *          DBRefEntry
   * @return boolean true if Source DBRefEntry is compatible with DAS
   *         CoordinateSystem name
   */
  public static Hashtable DasCoordinateSystemsLookup = null;

  public static boolean isDasCoordinateSystem(String string,
          DBRefEntry dBRefEntry)
  {
    if (DasCoordinateSystemsLookup == null)
    {
      // TODO: Make a DasCoordinateSystemsLookup properties resource
      // Initialise
      DasCoordinateSystemsLookup = new Hashtable();
      DasCoordinateSystemsLookup.put("pdbresnum",
              jalview.datamodel.DBRefSource.PDB);
      DasCoordinateSystemsLookup.put("uniprot",
              jalview.datamodel.DBRefSource.UNIPROT);
      DasCoordinateSystemsLookup.put("EMBL",
              jalview.datamodel.DBRefSource.EMBL);
      // DasCoordinateSystemsLookup.put("EMBL",
      // jalview.datamodel.DBRefSource.EMBLCDS);
    }

    String coordsys = (String) DasCoordinateSystemsLookup.get(string
            .toLowerCase());
    if (coordsys != null)
    {
      return coordsys.equals(dBRefEntry.getSource());
    }
    return false;
  }

  public static Hashtable CanonicalSourceNameLookup = null;

  /**
   * look up source in an internal list of database reference sources and return
   * the canonical jalview name for the source, or the original string if it has
   * no canonical form.
   * 
   * @param source
   * @return canonical jalview source (one of jalview.datamodel.DBRefSource.*)
   *         or original source
   */
  public static String getCanonicalName(String source)
  {
    if (CanonicalSourceNameLookup == null)
    {
      CanonicalSourceNameLookup = new Hashtable();
      CanonicalSourceNameLookup.put("uniprotkb/swiss-prot",
              jalview.datamodel.DBRefSource.UNIPROT);
      CanonicalSourceNameLookup.put("uniprotkb/trembl",
              jalview.datamodel.DBRefSource.UNIPROT);
      CanonicalSourceNameLookup.put("pdb",
              jalview.datamodel.DBRefSource.PDB);
    }
    String canonical = (String) CanonicalSourceNameLookup.get(source
            .toLowerCase());
    if (canonical == null)
    {
      return source;
    }
    return canonical;
  }

  /**
   * find RefEntry corresponding to a particular pattern the equals method of
   * each entry is used, from String attributes right down to Mapping
   * attributes.
   * 
   * @param ref
   *          Set of references to search
   * @param entry
   *          pattern to collect - null any entry for wildcard match
   * @return
   */
  public static DBRefEntry[] searchRefs(DBRefEntry[] ref, DBRefEntry entry)
  {
    return searchRefs(ref, entry,
            matchDbAndIdAndEitherMapOrEquivalentMapList);
  }

  public static DBRefEntry[] searchRefs(DBRefEntry[] ref, DBRefEntry entry,
          DbRefComp comparator)
  {
    if (ref == null || entry == null)
      return null;
    Vector rfs = new Vector();
    for (int i = 0; i < ref.length; i++)
    {
      if (comparator.matches(entry, ref[i]))
      {
        rfs.addElement(ref[i]);
      }
    }
    // TODO Auto-generated method stub
    if (rfs.size() > 0)
    {
      DBRefEntry[] rf = new DBRefEntry[rfs.size()];
      rfs.copyInto(rf);
      return rf;
    }
    return null;
  }

  public interface DbRefComp
  {
    public boolean matches(DBRefEntry refa, DBRefEntry refb);
  }

  /**
   * match on all non-null fields in refa
   */
  public static DbRefComp matchNonNullonA = new DbRefComp()
  {
    public boolean matches(DBRefEntry refa, DBRefEntry refb)
    {
      if (refa.getSource() == null
              || refb.getSource().equals(refa.getSource()))
      {
        if (refa.getVersion() == null
                || refb.getVersion().equals(refa.getVersion()))
        {
          if (refa.getAccessionId() == null
                  || refb.getAccessionId().equals(refa.getAccessionId()))
          {
            if (refa.getMap() == null
                    || (refb.getMap() != null && refb.getMap().equals(
                            refa.getMap())))
            {
              return true;
            }
          }
        }
      }
      return false;
    }
  };

  /**
   * either field is null or field matches for all of source, version, accession
   * id and map.
   */
  public static DbRefComp matchEitherNonNull = new DbRefComp()
  {
    public boolean matches(DBRefEntry refa, DBRefEntry refb)
    {
      if ((refa.getSource() == null || refb.getSource() == null)
              || refb.getSource().equals(refa.getSource()))
      {
        if ((refa.getVersion() == null || refb.getVersion() == null)
                || refb.getVersion().equals(refa.getVersion()))
        {
          if ((refa.getAccessionId() == null || refb.getAccessionId() == null)
                  || refb.getAccessionId().equals(refa.getAccessionId()))
          {
            if ((refa.getMap() == null || refb.getMap() == null)
                    || (refb.getMap() != null && refb.getMap().equals(
                            refa.getMap())))
            {
              return true;
            }
          }
        }
      }
      return false;
    }
  };

  /**
   * accession ID and DB must be identical. Version is ignored. Map is either
   * not defined or is a match (or is compatible?)
   */
  public static DbRefComp matchDbAndIdAndEitherMap = new DbRefComp()
  {
    public boolean matches(DBRefEntry refa, DBRefEntry refb)
    {
      if (refa.getSource() != null && refb.getSource() != null
              && refb.getSource().equals(refa.getSource()))
      {
        // We dont care about version
        // if ((refa.getVersion()==null || refb.getVersion()==null)
        // || refb.getVersion().equals(refa.getVersion()))
        // {
        if (refa.getAccessionId() != null && refb.getAccessionId() != null
                || refb.getAccessionId().equals(refa.getAccessionId()))
        {
          if ((refa.getMap() == null || refb.getMap() == null)
                  || (refa.getMap() != null && refb.getMap() != null && refb
                          .getMap().equals(refa.getMap())))
          {
            return true;
          }
        }
      }
      return false;
    }
  };

  /**
   * accession ID and DB must be identical. Version is ignored. No map on either
   * or map but no maplist on either or maplist of map on a is the complement of
   * maplist of map on b.
   */
  public static DbRefComp matchDbAndIdAndComplementaryMapList = new DbRefComp()
  {
    public boolean matches(DBRefEntry refa, DBRefEntry refb)
    {
      if (refa.getSource() != null && refb.getSource() != null
              && refb.getSource().equals(refa.getSource()))
      {
        // We dont care about version
        // if ((refa.getVersion()==null || refb.getVersion()==null)
        // || refb.getVersion().equals(refa.getVersion()))
        // {
        if (refa.getAccessionId() != null && refb.getAccessionId() != null
                || refb.getAccessionId().equals(refa.getAccessionId()))
        {
          if ((refa.getMap() == null && refb.getMap() == null)
                  || (refa.getMap() != null && refb.getMap() != null))
            if ((refb.getMap().getMap() == null && refa.getMap().getMap() == null)
                    || (refb.getMap().getMap() != null
                            && refa.getMap().getMap() != null && refb
                            .getMap().getMap().getInverse()
                            .equals(refa.getMap().getMap())))
            {
              return true;
            }
        }
      }
      return false;
    }
  };

  /**
   * accession ID and DB must be identical. Version is ignored. No map on both
   * or or map but no maplist on either or maplist of map on a is equivalent to
   * the maplist of map on b.
   */
  public static DbRefComp matchDbAndIdAndEquivalentMapList = new DbRefComp()
  {
    public boolean matches(DBRefEntry refa, DBRefEntry refb)
    {
      if (refa.getSource() != null && refb.getSource() != null
              && refb.getSource().equals(refa.getSource()))
      {
        // We dont care about version
        // if ((refa.getVersion()==null || refb.getVersion()==null)
        // || refb.getVersion().equals(refa.getVersion()))
        // {
        if (refa.getAccessionId() != null && refb.getAccessionId() != null
                || refb.getAccessionId().equals(refa.getAccessionId()))
        {
          if (refa.getMap() == null && refb.getMap() == null)
          {
            return true;
          }
          if (refa.getMap() != null
                  && refb.getMap() != null
                  && ((refb.getMap().getMap() == null && refa.getMap()
                          .getMap() == null) || (refb.getMap().getMap() != null
                          && refa.getMap().getMap() != null && refb
                          .getMap().getMap().equals(refa.getMap().getMap()))))
          {
            return true;
          }
        }
      }
      return false;
    }
  };

  /**
   * accession ID and DB must be identical. Version is ignored. No map on either
   * or map but no maplist on either or maplist of map on a is equivalent to the
   * maplist of map on b.
   */
  public static DbRefComp matchDbAndIdAndEitherMapOrEquivalentMapList = new DbRefComp()
  {
    public boolean matches(DBRefEntry refa, DBRefEntry refb)
    {
      // System.err.println("Comparing A: "+refa.getSrcAccString()+(refa.hasMap()?" has map.":"."));
      // System.err.println("Comparing B: "+refb.getSrcAccString()+(refb.hasMap()?" has map.":"."));
      if (refa.getSource() != null && refb.getSource() != null
              && refb.getSource().equals(refa.getSource()))
      {
        // We dont care about version
        // if ((refa.getVersion()==null || refb.getVersion()==null)
        // || refb.getVersion().equals(refa.getVersion()))
        // {
        if (refa.getAccessionId() != null && refb.getAccessionId() != null
                && refb.getAccessionId().equals(refa.getAccessionId()))
        {
          if (refa.getMap() == null || refb.getMap() == null)
          {
            return true;
          }
          if ((refa.getMap() != null && refb.getMap() != null)
                  && (refb.getMap().getMap() == null && refa.getMap()
                          .getMap() == null)
                  || (refb.getMap().getMap() != null
                          && refa.getMap().getMap() != null && (refb
                          .getMap().getMap().equals(refa.getMap().getMap()))))
          { // getMap().getMap().containsEither(false,refa.getMap().getMap())
            return true;
          }
        }
      }
      return false;
    }
  };

  /**
   * used by file parsers to generate DBRefs from annotation within file (eg
   * stockholm)
   * 
   * @param dbname
   * @param version
   * @param acn
   * @param seq
   *          where to anotate with reference
   * @return parsed version of entry that was added to seq (if any)
   */
  public static DBRefEntry parseToDbRef(SequenceI seq, String dbname,
          String version, String acn)
  {
    DBRefEntry ref = null;
    if (dbname != null)
    {
      String locsrc = jalview.util.DBRefUtils.getCanonicalName(dbname);
      if (locsrc.equals(jalview.datamodel.DBRefSource.PDB))
      {
        // check for chaincode and mapping
        // PFAM style stockhom PDB citation
        com.stevesoft.pat.Regex r = new com.stevesoft.pat.Regex(
                "([0-9][0-9A-Za-z]{3})\\s*(.?)\\s*;([0-9]+)-([0-9]+)");
        if (r.search(acn.trim()))
        {
          String pdbid = r.stringMatched(1);
          String chaincode = r.stringMatched(2);
          String mapstart = r.stringMatched(3);
          String mapend = r.stringMatched(4);
          if (chaincode.equals(" "))
          {
            chaincode = "_";
          }
          // construct pdb ref.
          ref = new DBRefEntry(locsrc, version, pdbid + chaincode);
          PDBEntry pdbr = new PDBEntry();
          pdbr.setId(pdbid);
          seq.addPDBId(pdbr);
        }
      }
      else
      {
        // default:
        ref = new DBRefEntry(locsrc, version, acn);
      }
    }
    if (ref != null)
    {
      seq.addDBRef(ref);
    }
    return ref;
  }

}
