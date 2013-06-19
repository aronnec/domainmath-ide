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

import jalview.ws.seqfetcher.DbSourceProxy;

/**
 * Flyweight class specifying retrieval of Seed family alignments from RFAM
 * 
 * @author Lauren Michelle Lui
 * 
 */
public class RfamSeed extends Rfam implements DbSourceProxy
{
  public RfamSeed()
  {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.dbsources.Rfam#getRFAMURL()
   */
  protected String getXFAMURL()
  {
    return "http://rfam.sanger.ac.uk/family/alignment/download/format?alnType=seed&nseLabels=0&format=stockholm&acc=";
    // Janelia Farms url
    // "http://rfam.janelia.org/cgi-bin/getalignment?type=seed&fmt=stockholm&acc=";
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.seqfetcher.DbSourceProxy#getDbName()
   */
  public String getDbName()
  {
    return "RFAM (Seed)";
  }

  public String getDbSource()
  {
    return getDbName(); // so we have unique DbSource string.
  }

  public String getTestQuery()
  {
    return "RF00014";
  } // http://rfam.janelia.org/cgi-bin/getdesc?acc=RF00014

  public String getDbVersion()
  {
    return null;
  }

  @Override
  public int getTier()
  {
    return 0;
  }
}
