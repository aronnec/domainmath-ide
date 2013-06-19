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


import com.stevesoft.pat.Regex;

import jalview.datamodel.AlignmentI;
import jalview.datamodel.DBRefEntry;
import jalview.ws.seqfetcher.DbSourceProxy;

/**
 * TODO: later PFAM is a complex datasource - it could return a tree in addition
 * to an alignment TODO: create interface to pass alignment properties and tree
 * back to sequence fetcher
 * 
 * @author JimP
 * 
 */
abstract public class Pfam extends Xfam implements DbSourceProxy
{

  public Pfam()
  {
    super();
    // all extensions of this PFAM source base class are DOMAINDB sources
    addDbSourceProperty(jalview.datamodel.DBRefSource.DOMAINDB);
    addDbSourceProperty(jalview.datamodel.DBRefSource.ALIGNMENTDB);
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
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getDbSource() public String getDbSource() { *
   * this doesn't work - DbSource is key for the hash of DbSourceProxy instances
   * - 1:many mapping for DbSource to proxy will be lost. * suggest : PFAM is an
   * 'alignment' source - means proxy is higher level than a sequence source.
   * return jalview.datamodel.DBRefSource.PFAM; }
   */

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getDbSourceProperties() public Hashtable
   * getDbSourceProperties() {
   * 
   * return null; }
   */

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getDbVersion()
   */
  @Override
  public String getDbVersion()
  {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Returns base URL for selected Pfam alignment type
   * 
   * @return PFAM URL stub for this DbSource
   */
  @Override
  protected abstract String getXFAMURL();

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getSequenceRecords(java.lang.String[])
   */
  public AlignmentI getSequenceRecords(String queries) throws Exception
  {
    // TODO: this is not a perfect implementation. We need to be able to add
    // individual references to each sequence in each family alignment that's
    // retrieved.
    startQuery();
    AlignmentI rcds = new jalview.io.FormatAdapter().readFile(getXFAMURL()
            + queries.trim().toUpperCase(), jalview.io.FormatAdapter.URL,
            "STH");
    for (int s = 0, sNum = rcds.getHeight(); s < sNum; s++)
    {
      rcds.getSequenceAt(s).addDBRef(
              new DBRefEntry(jalview.datamodel.DBRefSource.PFAM,
              // getDbSource(),
                      getDbVersion(), queries.trim().toUpperCase()));
      if (!getDbSource().equals(jalview.datamodel.DBRefSource.PFAM))
      { // add the specific ref too
        rcds.getSequenceAt(s).addDBRef(
                new DBRefEntry(getDbSource(), getDbVersion(), queries
                        .trim().toUpperCase()));
      }
    }
    stopQuery();
    return rcds;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#isValidReference(java.lang.String)
   */
  public boolean isValidReference(String accession)
  {
    return accession.indexOf("PF") == 0;
  }

  /*
   * public String getDbName() { return "PFAM"; // getDbSource(); }
   */

  public String getXfamSource()
  {
    return jalview.datamodel.DBRefSource.PFAM;
  }

}
