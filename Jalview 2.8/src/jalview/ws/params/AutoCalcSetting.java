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

import java.util.List;

public abstract class AutoCalcSetting
{

  protected boolean autoUpdate;

  protected WsParamSetI preset;

  protected List<ArgumentI> jobArgset;

  public AutoCalcSetting(WsParamSetI preset2, List<ArgumentI> jobArgset2,
          boolean autoUpdate2)
  {
    autoUpdate = autoUpdate2;
    preset = preset2;
    jobArgset = jobArgset2;
  }

  public boolean isAutoUpdate()
  {
    return autoUpdate;
  }

  public void setAutoUpdate(boolean autoUpdate)
  {
    this.autoUpdate = autoUpdate;
  }

  public WsParamSetI getPreset()
  {
    return preset;
  }

  public void setPreset(WsParamSetI preset)
  {
    // TODO: test if service URL is in presets
    this.preset = preset;
  }

  public List<ArgumentI> getArgumentSet()
  {
    return jobArgset;
  }

  /**
   * 
   * @return characteristic URI for this service. The URI should reflect the
   *         type and version of this service, enabling the service client code
   *         to recover the correct client for this calculation.
   */
  public abstract String getServiceURI();

  /**
   * return any concrete service endpoints associated with this calculation.
   * built in services should return a zero length array
   * 
   * @return
   */
  public abstract String[] getServiceURLs();

  /**
   * 
   * @return stringified representation of the parameters for this setting
   */
  public abstract String getWsParamFile();

}
