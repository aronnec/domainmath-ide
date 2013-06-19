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
package jalview.ws.dbsources.das.api;

import java.util.List;

import jalview.ws.seqfetcher.DbSourceProxy;

import org.biodas.jdas.schema.sources.MAINTAINER;
import org.biodas.jdas.schema.sources.VERSION;

public interface jalviewSourceI
{

  String getTitle();

  VERSION getVersion();

  String getDocHref();

  String getDescription();

  String getUri();

  MAINTAINER getMAINTAINER();

  String getEmail();

  boolean isLocal();

  boolean isSequenceSource();

  String[] getCapabilityList(VERSION v);

  String[] getLabelsFor(VERSION v);

  /**
   * 
   * @return null if not a sequence source, otherwise a series of database
   *         sources that can be used to retrieve sequence data for particular
   *         database coordinate systems
   */
  List<DbSourceProxy> getSequenceSourceProxies();

  boolean isFeatureSource();

  /**
   * returns the base URL for the latest version of a source's DAS endpoint set
   * 
   * @return
   */
  String getSourceURL();

  /**
   * test to see if this source's latest version is older than the given source
   * 
   * @param jalviewSourceI
   * @return true if newer than given source
   */
  boolean isNewerThan(jalviewSourceI jalviewSourceI);

  /**
   * test if the source is a reference source for the authority
   * @return
   */
  boolean isReferenceSource();

}
