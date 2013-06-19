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
package jalview.ws.dbsources;

import jalview.datamodel.Alignment;
import jalview.datamodel.DBRefEntry;
import jalview.datamodel.DBRefSource;
import jalview.datamodel.SequenceI;

import java.util.Vector;

import MCview.PDBChain;
import MCview.PDBfile;

import com.stevesoft.pat.Regex;

import jalview.datamodel.AlignmentI;
import jalview.ws.ebi.EBIFetchClient;
import jalview.ws.seqfetcher.DbSourceProxy;

/**
 * @author JimP
 * 
 */
public class Pdb extends EbiFileRetrievedProxy implements DbSourceProxy
{
  public Pdb()
  {
    super();
    addDbSourceProperty(DBRefSource.PROTSEQDB);
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getAccessionSeparator()
   */
  public String getAccessionSeparator()
  {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getAccessionValidator()
   */
  public Regex getAccessionValidator()
  {
    return new Regex("([1-9][0-9A-Za-z]{3}):?([ _A-Za-z0-9]?)");
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getDbSource()
   */
  public String getDbSource()
  {
    return DBRefSource.PDB;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getDbVersion()
   */
  public String getDbVersion()
  {
    return "0";
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getSequenceRecords(java.lang.String[])
   */
  public AlignmentI getSequenceRecords(String queries) throws Exception
  {

    Vector result = new Vector();
    String chain = null;
    String id = null;
    if (queries.indexOf(":") > -1)
    {
      chain = queries.substring(queries.indexOf(":") + 1);
      id = queries.substring(0, queries.indexOf(":"));
    }
    else
    {
      id = queries;
    }
    if (queries.length() > 4 && chain == null)
    {
      chain = queries.substring(4, 5);
      id = queries.substring(0, 4);
    }
    if (!isValidReference(id))
    {
      System.err.println("Ignoring invalid pdb query: '" + id + "'");
      stopQuery();
      return null;
    }
    EBIFetchClient ebi = new EBIFetchClient();
    file = ebi.fetchDataAsFile("pdb:" + id, "pdb", "raw").getAbsolutePath();
    stopQuery();
    if (file == null)
    {
      return null;
    }
    try
    {

      PDBfile pdbfile = new PDBfile(file,
              jalview.io.AppletFormatAdapter.FILE);
      for (int i = 0; i < pdbfile.chains.size(); i++)
      {
        if (chain == null
                || ((PDBChain) pdbfile.chains.elementAt(i)).id
                        .toUpperCase().equals(chain))
        {
          PDBChain pdbchain = (PDBChain) pdbfile.chains.elementAt(i);
          // Get the Chain's Sequence - who's dataset includes any special
          // features added from the PDB file
          SequenceI sq = pdbchain.sequence;
          // Specially formatted name for the PDB chain sequences retrieved from
          // the PDB
          sq.setName(jalview.datamodel.DBRefSource.PDB + "|" + id + "|"
                  + sq.getName());
          // Might need to add more metadata to the PDBEntry object
          // like below
          /*
           * PDBEntry entry = new PDBEntry(); // Construct the PDBEntry
           * entry.setId(id); if (entry.getProperty() == null)
           * entry.setProperty(new Hashtable());
           * entry.getProperty().put("chains", pdbchain.id + "=" + sq.getStart()
           * + "-" + sq.getEnd()); sq.getDatasetSequence().addPDBId(entry);
           */
          // Add PDB DB Refs
          // We make a DBRefEtntry because we have obtained the PDB file from a
          // verifiable source
          // JBPNote - PDB DBRefEntry should also carry the chain and mapping
          // information
          DBRefEntry dbentry = new DBRefEntry(getDbSource(),
                  getDbVersion(), id + pdbchain.id);
          sq.addDBRef(dbentry);
          // and add seuqence to the retrieved set
          result.addElement(sq.deriveSequence());
        }
      }

      if (result.size() < 1)
      {
        throw new Exception("No PDB Records for " + id + " chain "
                + ((chain == null) ? "' '" : chain));
      }
    } catch (Exception ex) // Problem parsing PDB file
    {
      stopQuery();
      throw (ex);
    }

    SequenceI[] results = new SequenceI[result.size()];
    for (int i = 0, j = result.size(); i < j; i++)
    {
      results[i] = (SequenceI) result.elementAt(i);
      result.setElementAt(null, i);
    }
    return new Alignment(results);
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#isValidReference(java.lang.String)
   */
  public boolean isValidReference(String accession)
  {
    Regex r = getAccessionValidator();
    return r.search(accession.trim());
  }

  /**
   * obtain human glyoxalase chain A sequence
   */
  public String getTestQuery()
  {
    return "1QIPA";
  }

  public String getDbName()
  {
    return "PDB"; // getDbSource();
  }

  @Override
  public int getTier()
  {
    return 0;
  }
}
