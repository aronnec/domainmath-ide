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

import jalview.datamodel.AlignmentI;
import jalview.datamodel.DBRefEntry;
import jalview.ws.seqfetcher.DbSourceProxyImpl;

/**
 * Acts as a superclass for the Rfam and Pfam classes
 * 
 * @author Lauren Michelle Lui
 * 
 */
public abstract class Xfam extends DbSourceProxyImpl
{

  public Xfam()
  {
    super();
  }

  protected abstract String getXFAMURL();

  public abstract String getDbVersion();

  abstract String getXfamSource();

  public AlignmentI getSequenceRecords(String queries) throws Exception
  {
    // TODO: this is not a perfect implementation. We need to be able to add
    // individual references to each sequence in each family alignment that's
    // retrieved.
    startQuery();
    // TODO: trap HTTP 404 exceptions and return null
    AlignmentI rcds = new jalview.io.FormatAdapter().readFile(getXFAMURL()
            + queries.trim().toUpperCase(), jalview.io.FormatAdapter.URL,
            "STH");
    for (int s = 0, sNum = rcds.getHeight(); s < sNum; s++)
    {
      rcds.getSequenceAt(s).addDBRef(new DBRefEntry(getXfamSource(),
      // getDbSource(),
              getDbVersion(), queries.trim().toUpperCase()));
      if (!getDbSource().equals(getXfamSource()))
      { // add the specific ref too
        rcds.getSequenceAt(s).addDBRef(
                new DBRefEntry(getDbSource(), getDbVersion(), queries
                        .trim().toUpperCase()));
      }
    }
    stopQuery();
    return rcds;
  }

}
