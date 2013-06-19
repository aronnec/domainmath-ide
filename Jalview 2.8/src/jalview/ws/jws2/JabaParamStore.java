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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import compbio.metadata.Argument;
import compbio.metadata.Option;
import compbio.metadata.Parameter;
import compbio.metadata.Preset;
import compbio.metadata.PresetManager;
import compbio.metadata.RunnerConfig;

import jalview.ws.jws2.dm.JabaOption;
import jalview.ws.jws2.dm.JabaParameter;
import jalview.ws.jws2.dm.JabaWsParamSet;
import jalview.ws.jws2.jabaws2.Jws2Instance;
import jalview.ws.params.ArgumentI;
import jalview.ws.params.ParamDatastoreI;
import jalview.ws.params.ParamManager;
import jalview.ws.params.WsParamSetI;

public class JabaParamStore implements ParamDatastoreI
{

  Hashtable<String, JabaWsParamSet> editedParams = new Hashtable<String, JabaWsParamSet>();

  private Jws2Instance service;

  private RunnerConfig serviceOptions;

  private Hashtable<String, JabaPreset> servicePresets;

  public JabaParamStore(Jws2Instance service)
  {
    this(service, null);
  }

  ParamManager manager;

  public JabaParamStore(Jws2Instance service, ParamManager manager)
  {
    this.service = service;
    serviceOptions = service.getRunnerConfig();
    this.manager = manager;
    // discover all the user's locally stored presets for this service and
    // populate the hash table
    if (manager != null)
    {
      manager.registerParser(service.getUri(), this);
      WsParamSetI[] prams = manager.getParameterSet(null, service.getUri(),
              true, false);
      if (prams != null)
      {
        for (WsParamSetI paramset : prams)
        {
          if (paramset instanceof JabaWsParamSet)
          {
            editedParams.put(paramset.getName(), (JabaWsParamSet) paramset);
          }
          else
          {
            System.err
                    .println("Warning: Ignoring parameter set instance of type "
                            + paramset.getClass()
                            + " : Bound but not applicable for service at "
                            + service.getUri());
          }
        }
      }
    }
  }

  @Override
  public List<WsParamSetI> getPresets()
  {
    List<WsParamSetI> prefs = new ArrayList();
    if (servicePresets == null)
    {
      servicePresets = new Hashtable<String, JabaPreset>();
      PresetManager prman;
      if ((prman = service.getPresets()) != null)
      {
        List pset = prman.getPresets();
        if (pset != null)
        {
          for (Object pr : pset)
          {
            JabaPreset prset = new JabaPreset(service, (Preset) pr);
            servicePresets.put(prset.getName(), prset);
          }
        }
      }
    }
    for (JabaPreset pr : servicePresets.values())
    {
      prefs.add(pr);
    }
    for (WsParamSetI wspset : editedParams.values())
    {
      prefs.add(wspset);
    }
    return prefs;
  }

  @Override
  public WsParamSetI getPreset(String name)
  {
    for (WsParamSetI pr : getPresets())
    {
      if (pr.getName().equals(name))
      {
        return pr;
      }
    }
    return null;
  }

  public static List<ArgumentI> getJwsArgsfromJaba(List jabargs)
  {
    List<ArgumentI> rgs = new ArrayList<ArgumentI>();
    for (Object rg : jabargs)
    {
      ArgumentI narg = (rg instanceof Parameter) ? new JabaParameter(
              (Parameter) rg) : (rg instanceof Option) ? new JabaOption(
              (Option) rg) : null;
      if (narg == null)
      {
        throw new Error(
                "Implementation Error: Cannot handle Jaba parameter object "
                        + rg.getClass());
      }
      else
      {
        rgs.add(narg);
      }
    }
    return rgs;
  }

  public static List getJabafromJwsArgs(List<ArgumentI> jwsargs)
  {
    List rgs = new ArrayList();
    for (ArgumentI rg : jwsargs)
    {
      Argument narg = (rg instanceof JabaOption) ? ((JabaOption) rg)
              .getOption() : null;
      if (narg == null)
      {
        throw new Error(
                "Implementation Error: Cannot handle Jaba parameter object "
                        + rg.getClass());
      }
      else
      {
        rgs.add(narg);
      }
    }
    return rgs;
  }

  @Override
  public List<ArgumentI> getServiceParameters()
  {
    return getJwsArgsfromJaba(serviceOptions.getArguments());
  }

  @Override
  public boolean presetExists(String name)
  {
    return (editedParams.containsKey(name) || servicePresets
            .containsKey(name));
  }

  @Override
  public void deletePreset(String name)
  {
    if (editedParams.containsKey(name))
    {
      WsParamSetI parameterSet = editedParams.get(name);
      editedParams.remove(name);
      if (manager != null)
      {
        manager.deleteParameterSet(parameterSet);
      }
      return;
    }
    if (servicePresets.containsKey(name))
    {
      throw new Error(
              "Implementation error: Attempt to delete a service preset!");
    }
  }

  @Override
  public void storePreset(String presetName, String text,
          List<ArgumentI> jobParams)
  {
    JabaWsParamSet jps = new JabaWsParamSet(presetName, text, jobParams);
    jps.setApplicableUrls(new String[]
    { service.getUri() });
    editedParams.put(jps.getName(), jps);
    if (manager != null)
    {
      manager.storeParameterSet(jps);
    }
  }

  @Override
  public void updatePreset(String oldName, String presetName, String text,
          List<ArgumentI> jobParams)
  {
    JabaWsParamSet jps = (JabaWsParamSet) ((oldName != null) ? getPreset(oldName)
            : getPreset(presetName));
    if (jps == null)
    {
      throw new Error("Implementation error: Can't locate either oldname ("
              + oldName + ") or presetName (" + presetName
              + "in the datastore!");
    }
    jps.setName(presetName);
    jps.setDescription(text);
    jps.setArguments(jobParams);
    jps.setApplicableUrls(new String[]
    { service.getUri() });
    if (oldName != null && !oldName.equals(jps.getName()))
    {
      editedParams.remove(oldName);
    }
    editedParams.put(jps.getName(), jps);

    if (manager != null)
    {
      manager.storeParameterSet(jps);
    }
  }

  /**
   * create a new, empty parameter set for this service
   * 
   * @return
   */
  WsParamSetI newWsParamSet()
  {
    return new JabaWsParamSet();
  };

  private boolean involves(String[] urls)
  {
    boolean found = false;
    for (String url : urls)
    {
      if (service.getServiceTypeURI().equals(url)
              || service.getUri().equalsIgnoreCase(url))
      {
        found = true;
        break;
      }
    }
    return found;
  }

  @Override
  public WsParamSetI parseServiceParameterFile(String name, String descr,
          String[] urls, String parameterfile) throws IOException
  {
    if (!involves(urls))
    {
      throw new IOException(
              "Implementation error: Cannot find service url in the given url set!");

    }
    JabaWsParamSet wsp = new JabaWsParamSet();
    wsp.setName(name);
    wsp.setDescription(descr);
    wsp.setApplicableUrls(urls.clone());

    List<String> lines = new ArrayList<String>();
    StringTokenizer st = new StringTokenizer(parameterfile, "\n");
    while (st.hasMoreTokens())
    {
      lines.add(st.nextToken());
    }
    wsp.setjabaArguments(ParameterUtils.processParameters(lines,
            serviceOptions, " "));
    return wsp;
  }

  @Override
  public String generateServiceParameterFile(WsParamSetI pset)
          throws IOException
  {
    if (!involves(pset.getApplicableUrls()))
    {
      throw new IOException(
              "Implementation error: Cannot find service url in the given url set for this service parameter store ("
                      + service.getUri() + ") !");

    }
    if (!(pset instanceof JabaWsParamSet))
    {
      throw new Error(
              "Implementation error: JabaWsParamSets can only be handled by JabaParamStore");
    }

    StringBuffer rslt = new StringBuffer();
    for (String ln : ParameterUtils.writeParameterSet(
            ((JabaWsParamSet) pset).getjabaArguments(), " "))
    {
      rslt.append(ln);
      rslt.append("\n");
    }
    ;
    return rslt.toString();
  }

}
