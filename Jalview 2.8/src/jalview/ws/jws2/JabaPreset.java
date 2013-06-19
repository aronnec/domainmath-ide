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
package jalview.ws.jws2;

import jalview.ws.jws2.jabaws2.Jws2Instance;
import jalview.ws.params.ArgumentI;
import jalview.ws.params.WsParamSetI;

import java.util.List;

import compbio.metadata.Preset;

public class JabaPreset implements WsParamSetI
{
  Preset p = null;

  Jws2Instance service;

  public JabaPreset(Jws2Instance svc, Preset preset)
  {
    service = svc;
    p = preset;
  }

  @Override
  public String getName()
  {
    return p.getName();
  }

  @Override
  public String getDescription()
  {
    return p.getDescription();
  }

  @Override
  public String[] getApplicableUrls()
  {
    return new String[]
    { service.getUri() };
  }

  @Override
  public String getSourceFile()
  {
    return null;
  }

  @Override
  public boolean isModifiable()
  {
    return false;
  }

  @Override
  public void setSourceFile(String newfile)
  {
    throw new Error("Cannot set source file for " + getClass());
  }

  @Override
  public List<ArgumentI> getArguments()
  {
    try
    {
      return JabaParamStore.getJwsArgsfromJaba(p.getArguments(service
              .getRunnerConfig()));
    } catch (Exception e)
    {
      e.printStackTrace();
      throw new Error(
              "Probable mismatch between service instance and preset!");
    }
  }

  @Override
  public void setArguments(List<ArgumentI> args)
  {
    throw new Error("Cannot set Parameters for a Jaba Web service's preset");
  }
}
