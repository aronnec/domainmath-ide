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

import jalview.datamodel.AlignmentI;

import java.util.Hashtable;

import com.stevesoft.pat.Regex;

/**
 * generic Reference Retrieval interface for a particular database
 * source/version as cited in DBRefEntry. TODO: add/define property to describe
 * max number of queries that this source can cope with at once. TODO:
 * add/define mechanism for retrieval of Trees and distance matrices from a
 * database (unify with io)
 * 
 * @author JimP TODO: promote to API
 */
public interface DbSourceProxy
{
  /**
   * 
   * @return source string constant used for this DB source
   */
  public String getDbSource();

  /**
   * Short meaningful name for this data source for display in menus or
   * selection boxes.
   * 
   * @return String
   */
  public String getDbName();

  /**
   * 
   * @return version string for this database.
   */
  public String getDbVersion();

  /**
   * Separator between individual accession queries for a database that allows
   * multiple IDs to be fetched in a single query. Null implies that only a
   * single ID can be fetched at a time.
   * 
   * @return string for separating concatenated queries (as individually
   *         validated by the accession validator)
   */
  public String getAccessionSeparator();

  /**
   * Regular expression for checking form of query string understood by this
   * source. If the Regex includes parenthesis, then the first parenthesis
   * should yield the same accession string as the one used to annotate the
   * sequence. This is used to match query strings to returned sequences.
   * 
   * @return null or a validation regex
   */
  public Regex getAccessionValidator();

  /**
   * DbSource properties hash - define the capabilities of this source Property
   * hash methods defined in DbSourceProxyImpl. See constants in
   * jalview.datamodel.DBRefSource for definition of properties.
   * 
   * @return
   */
  public Hashtable getDbSourceProperties();

  /**
   * 
   * @return a test/example query that can be used to validate retrieval and
   *         parsing mechanisms
   */
  public String getTestQuery();

  /**
   * optionally implemented
   * 
   * @param accession
   * @return
   */
  public boolean isValidReference(String accession);

  /**
   * make one or more queries to the database and attempt to parse the response
   * into an alignment
   * 
   * @param queries
   *          - one or more queries for database in expected form
   * @return null if queries were successful but result was not parsable
   * @throws Exception
   *           - propagated from underlying transport to database (note -
   *           exceptions are not raised if query not found in database)
   * 
   */
  public AlignmentI getSequenceRecords(String queries) throws Exception;

  /**
   * 
   * @return true if a query is currently being made
   */
  public boolean queryInProgress();

  /**
   * get the raw reponse from the last set of queries
   * 
   * @return one or more string buffers for each individual query
   */
  public StringBuffer getRawRecords();

  /**
   * Find out more info about the source.
   * 
   * @param dbsourceproperty
   *          - one of the database reference source properties in
   *          jalview.datamodel.DBRefSource
   * @return true if the source has this property
   */
  public boolean isA(Object dbsourceproperty);

  /**
   * Tier for this data source
   *  
   * @return 0 - primary datasource, 1 - das primary source, 2 - secondary
   */
  public int getTier();
}
