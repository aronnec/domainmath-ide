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
import jalview.datamodel.DBRefSource;
import jalview.ws.seqfetcher.DbSourceProxy;

/**
 * @author JimP
 * 
 */
public class EmblSource extends EmblXmlSource implements DbSourceProxy
{

  public EmblSource()
  {
    addDbSourceProperty(DBRefSource.DNASEQDB);
    addDbSourceProperty(DBRefSource.CODINGSEQDB);
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
    return new com.stevesoft.pat.Regex("^[A-Z]+[0-9]+");
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getDbSource()
   */
  public String getDbSource()
  {
    return DBRefSource.EMBL;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getDbVersion()
   */
  public String getDbVersion()
  {
    // TODO Auto-generated method stub
    return "0";
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getSequenceRecords(java.lang.String[])
   */
  public AlignmentI getSequenceRecords(String queries) throws Exception
  {
    return getEmblSequenceRecords(DBRefSource.EMBL, queries);
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#isValidReference(java.lang.String)
   */
  public boolean isValidReference(String accession)
  {
    // most embl refs look like ..

    return (accession == null || accession.length() < 2) ? false
            : getAccessionValidator().search(accession);

  }

  /**
   * return LHD_CHICK coding gene
   */
  public String getTestQuery()
  {
    return "X53828";
  }

  public String getDbName()
  {
    return "EMBL"; // getDbSource();
  }

  @Override
  public int getTier()
  {
    return 0;
  }
}
