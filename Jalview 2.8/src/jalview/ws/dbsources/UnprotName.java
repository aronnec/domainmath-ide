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

/**
 * Canonical Uniprot fetcher instance specifically retrieving UP_NAME
 * references.
 * 
 * @author JimP
 * 
 */
public class UnprotName extends Uniprot implements
        jalview.ws.seqfetcher.DbSourceProxy
{

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.dbsources.Uniprot#getDbSource()
   */
  public String getDbSource()
  {
    return jalview.datamodel.DBRefSource.UP_NAME;
  }

}
