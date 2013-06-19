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
package jalview.ws.params;

import java.io.IOException;
import java.util.List;

public interface ParamDatastoreI
{

  public List<WsParamSetI> getPresets();

  public WsParamSetI getPreset(String name);

  public List<ArgumentI> getServiceParameters();

  public boolean presetExists(String name);

  public void deletePreset(String name);

  /**
   * writes or overwrites the record for a modifiable WsParamSetI entry with a
   * given name in the datastore.
   * 
   * @param presetName
   * @param text
   * @param jobParams
   *          may throw an illegal argument RunTimeException if the presetName
   *          overwrites an existing, unmodifiable preset.
   */
  public void storePreset(String presetName, String text,
          List<ArgumentI> jobParams);

  /**
   * update an existing instance with a new name, descriptive text and
   * parameters.
   * 
   * @param oldName
   * @param presetName
   * @param text
   * @param jobParams
   */
  public void updatePreset(String oldName, String presetName, String text,
          List<ArgumentI> jobParams);

  /**
   * factory method - builds a service specific parameter object using the given
   * data
   * 
   * @param name
   * @param description
   * @param applicable
   *          URLs
   * @param parameterfile
   *          Service specific jalview parameter file (as returned from new
   *          method)
   * @return null or valid WsParamSetI object for this service.
   */

  public WsParamSetI parseServiceParameterFile(String name,
          String description, String[] serviceURL, String parameters)
          throws IOException;

  /**
   * create the service specific parameter file for this pset object.
   * 
   * @param pset
   * @return string representation of the parameters specified by this set.
   * @throws IOException
   */
  public String generateServiceParameterFile(WsParamSetI pset)
          throws IOException;

}
