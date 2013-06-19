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
package jalview.ws.seqfetcher;

import jalview.datamodel.Alignment;
import jalview.io.FormatAdapter;
import jalview.io.IdentifyFile;

import java.util.Hashtable;

/**
 * common methods for implementations of the DbSourceProxy interface.
 * 
 * @author JimP
 * 
 */
public abstract class DbSourceProxyImpl implements DbSourceProxy
{
  public DbSourceProxyImpl()
  {
    // default constructor - do nothing probably.
  }

  private Hashtable props = null;

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getDbSourceProperties()
   */
  public Hashtable getDbSourceProperties()
  {
    if (props == null)
    {
      props = new Hashtable();
    }
    return props;
  }

  protected void addDbSourceProperty(Object propname)
  {
    addDbSourceProperty(propname, propname);
  }

  protected void addDbSourceProperty(Object propname, Object propvalue)
  {
    if (props == null)
    {
      props = new Hashtable();
    }
    props.put(propname, propvalue);
  }

  boolean queryInProgress = false;

  protected StringBuffer results = null;

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#getRawRecords()
   */
  public StringBuffer getRawRecords()
  {
    return results;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.DbSourceProxy#queryInProgress()
   */
  public boolean queryInProgress()
  {
    return queryInProgress;
  }

  /**
   * call to set the queryInProgress flag
   * 
   */
  protected void startQuery()
  {
    queryInProgress = true;
  }

  /**
   * call to clear the queryInProgress flag
   * 
   */
  protected void stopQuery()
  {
    queryInProgress = false;
  }

  /**
   * create an alignment from raw text file...
   * 
   * @param result
   * @return null or a valid alignment
   * @throws Exception
   */
  protected Alignment parseResult(String result) throws Exception
  {
    Alignment sequences = null;
    String format = new IdentifyFile().Identify(result, "Paste");
    if (FormatAdapter.isValidFormat(format))
    {
      sequences = new FormatAdapter().readFile(result.toString(), "Paste",
              format);
    }
    return sequences;
  }

  @Override
  public boolean isA(Object dbsourceproperty)
  {
    assert (dbsourceproperty != null);
    return (props == null) ? false : props.containsKey(dbsourceproperty);
  }

}
