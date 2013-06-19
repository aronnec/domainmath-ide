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
package jalview.ws.jws2.dm;

import java.util.ArrayList;
import java.util.List;

import compbio.metadata.Argument;
import compbio.metadata.Option;

import jalview.ws.jws2.JabaParamStore;
import jalview.ws.jws2.JabaPreset;
import jalview.ws.jws2.ParameterUtils;
import jalview.ws.jws2.jabaws2.Jws2Instance;
import jalview.ws.params.ArgumentI;
import jalview.ws.params.WsParamSetI;

/**
 * preferences for running AACon service
 * 
 * @author jprocter TODO: refactor to a generic 'last job and service run'
 *         container ?
 */
public class AAConSettings extends jalview.ws.params.AutoCalcSetting
{
  Jws2Instance service;

  public AAConSettings(boolean autoUpdate, Jws2Instance service,
          WsParamSetI preset, List<ArgumentI> jobArgset)
  {
    super(preset, jobArgset, autoUpdate);
    this.service = service;
  }

  public Jws2Instance getService()
  {
    return service;
  }

  public void setService(Jws2Instance service)
  {
    this.service = service;
    if (preset != null)
    {
      // migrate preset to new service
      for (String url : preset.getApplicableUrls())
      {
        if (url.equals(service.getUri()))
        {
          return;
        }
      }
      WsParamSetI pr = service.getParamStore().getPreset(preset.getName());
      if (pr instanceof JabaPreset && preset instanceof JabaPreset)
      {
        // easy - Presets are identical (we assume)
        preset = pr;
        return;
      }
      List<ArgumentI> oldargs = new ArrayList<ArgumentI>(), newargs = new ArrayList<ArgumentI>();
      oldargs.addAll(preset.getArguments());
      // need to compare parameters
      for (ArgumentI newparg : pr.getArguments())
      {
        if (!oldargs.remove(newparg))
        {
          newargs.add(newparg);
        }
      }
      if (oldargs.size() == 0 && newargs.size() == 0)
      {
        // exact match.
        preset = pr;
        return;
      }
      // Try even harder to migrate arguments.
      throw new Error("Parameter migration not implemented yet");
    }
  }

  public List<Argument> getJobArgset()
  {
    return jobArgset == null ? null : JabaParamStore
            .getJabafromJwsArgs(jobArgset);
  }

  public void setJobArgset(List<Argument> jobArgset)
  {
    // TODO: test if parameters valid for service
    this.jobArgset = jobArgset == null ? null : JabaParamStore
            .getJwsArgsfromJaba(jobArgset);
  }

  public String getWsParamFile()
  {
    List<Option> opts = null;
    if (jobArgset != null)
    {
      opts = JabaParamStore.getJabafromJwsArgs(jobArgset);
    }
    else
    {
      if (preset != null)
      {
        opts = JabaParamStore.getJabafromJwsArgs(preset.getArguments());
      }
    }
    if (opts == null || opts.size() == 0)
    {
      return "";
    }
    StringBuffer pset = new StringBuffer();
    for (String ps : ParameterUtils.writeParameterSet(opts, " "))
    {
      pset.append(ps);
      pset.append("\n");
    }
    return pset.toString();
  }

  @Override
  public String getServiceURI()
  {
    return service.getServiceTypeURI();
  }

  @Override
  public String[] getServiceURLs()
  {
    return new String[]
    { service.getUri() };
  }
}
