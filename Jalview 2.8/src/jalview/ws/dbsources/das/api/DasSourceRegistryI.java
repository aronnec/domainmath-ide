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

import org.biodas.jdas.client.threads.MultipleConnectionPropertyProviderI;

/**
 * API for a registry that provides datasources that jalview can access
 * 
 * @author jprocter
 * 
 */
public interface DasSourceRegistryI
{

  List<jalviewSourceI> getSources();

  String getDasRegistryURL();

  jalviewSourceI getSource(String nickname);

  // TODO: re JAL-424 - introduce form where local source is queried for
  // metadata, rather than have it all provided by caller.
  jalviewSourceI createLocalSource(String uri, String name,
          boolean sequence, boolean features);

  boolean removeLocalSource(jalviewSourceI source);

  void refreshSources();

  String getLocalSourceString();

  List<jalviewSourceI> resolveSourceNicknames(List<String> sources);

  // TODO: refactor to jDAS specific interface
  MultipleConnectionPropertyProviderI getSessionHandler();
}
