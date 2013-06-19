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

import jalview.ws.seqfetcher.DbSourceProxy;

/**
 * Contains methods for fetching sequences from Rfam database
 * 
 * @author Lauren Michelle Lui
 */
abstract public class Rfam extends Xfam implements DbSourceProxy
{

  public Rfam()
  {
    super();
    // all extensions of this RFAM source base class are DOMAINDB sources
    addDbSourceProperty(jalview.datamodel.DBRefSource.DOMAINDB);
    addDbSourceProperty(jalview.datamodel.DBRefSource.ALIGNMENTDB);
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getAccessionSeparator() Left here for
   * consistency with Pfam class
   */
  public String getAccessionSeparator()
  {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getAccessionValidator() * Left here for
   */
  public Regex getAccessionValidator()
  {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * Left here for consistency with Pfam class
   * 
   * @see jalview.ws.DbSourceProxy#getDbSource() public String getDbSource() { *
   * this doesn't work - DbSource is key for the hash of DbSourceProxy instances
   * - 1:many mapping for DbSource to proxy will be lost. * suggest : RFAM is an
   * 'alignment' source - means proxy is higher level than a sequence source.
   * return jalview.datamodel.DBRefSource.RFAM; }
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
   * Returns base URL for selected Rfam alignment type
   * 
   * @return RFAM URL stub for this DbSource
   */
  @Override
  protected abstract String getXFAMURL();

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#isValidReference(java.lang.String)
   */
  public boolean isValidReference(String accession)
  {
    return accession.indexOf("RF") == 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.dbsources.Xfam#getXfamSource()
   */
  public String getXfamSource()
  {
    return jalview.datamodel.DBRefSource.RFAM;
  }

}
