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
package jalview.ws.jws2.jabaws2;

import jalview.gui.AlignFrame;
import jalview.gui.Desktop;
import jalview.ws.jws2.JabaParamStore;
import jalview.ws.jws2.MsaWSClient;
import jalview.ws.jws2.SequenceAnnotationWSClient;
import jalview.ws.params.ParamDatastoreI;

import java.io.Closeable;

import javax.swing.JMenu;

import compbio.data.msa.JABAService;
import compbio.data.msa.MsaWS;
import compbio.data.msa.SequenceAnnotation;
import compbio.metadata.PresetManager;
import compbio.metadata.RunnerConfig;

public class Jws2Instance
{
  public String hosturl;

  public String serviceType;

  public String action;

  public JABAService service;

  public String description;

  public String docUrl;

  public Jws2Instance(String hosturl, String serviceType, String action,
          String description, JABAService service)
  {
    super();
    this.hosturl = hosturl;
    this.serviceType = serviceType;
    this.service = service;
    this.action = action;
    this.description = description;
    int p = description.indexOf("MORE INFORMATION:");
    if (p > -1)
    {
      docUrl = description.substring(description.indexOf("http", p)).trim();
      if (docUrl.indexOf('\n') > -1)
      {
        docUrl = docUrl.substring(0, docUrl.indexOf("\n")).trim();
      }

    }
  }

  PresetManager presets = null;

  public JabaParamStore paramStore = null;

  /**
   * non thread safe - gets the presets for this service (blocks whilst it calls
   * the service to get the preset set)
   * 
   * @return service presets or null if exceptions were raised.
   */
  public PresetManager getPresets()
  {
    if (presets == null)
    {
      try
      {
        if (service instanceof MsaWS<?>)
        {
          presets = ((MsaWS) service).getPresets();

        }
        if (service instanceof SequenceAnnotation<?>)
        {
          presets = ((SequenceAnnotation) service).getPresets();
        }
      } catch (Exception ex)
      {
        System.err.println("Exception when retrieving presets for service "
                + serviceType + " at " + hosturl);
      }
    }
    return presets;
  }

  public String getHost()
  {
    return hosturl;
    /*
     * try { URL serviceurl = new URL(hosturl); if (serviceurl.getPort()!=80) {
     * return serviceurl.getHost()+":"+serviceurl.getPort(); } return
     * serviceurl.getHost(); } catch (Exception e) {
     * System.err.println("Failed to parse service URL '" + hosturl +
     * "' as a valid URL!"); } return null;
     */
  }

  /**
   * @return short description of what the service will do
   */
  public String getActionText()
  {
    return action + " with " + serviceType;
  }

  /**
   * non-thread safe - blocks whilst accessing service to get complete set of
   * available options and parameters
   * 
   * @return
   */
  public RunnerConfig getRunnerConfig()
  {
    if (service instanceof MsaWS<?>)
    {
      return ((MsaWS) service).getRunnerOptions();
    }
    if (service instanceof SequenceAnnotation<?>)
    {
      return ((SequenceAnnotation) service).getRunnerOptions();
    }
    throw new Error(
            "Implementation Error: Runner Config not available for a JABAWS service of type "
                    + serviceType + " (" + service.getClass() + ")");
  }

  @Override
  protected void finalize() throws Throwable
  {
    if (service != null)
    {
      try
      {
        Closeable svc = (Closeable) service;
        service = null;
        svc.close();
      } catch (Exception e)
      {
      }
      ;
    }
    super.finalize();
  }

  public ParamDatastoreI getParamStore()
  {
    if (paramStore == null)
    {
      try
      {
        paramStore = new JabaParamStore(this,
                (Desktop.instance != null ? Desktop.getUserParameterStore()
                        : null));
      } catch (Exception ex)
      {
        System.err.println("Unexpected exception creating JabaParamStore.");
        ex.printStackTrace();
      }

    }
    return paramStore;
  }

  public String getUri()
  {
    // this is only valid for Jaba 1.0 - this formula might have to change!
    return hosturl
            + (hosturl.lastIndexOf("/") == (hosturl.length() - 1) ? ""
                    : "/") + serviceType;
  }

  private boolean hasParams = false, lookedForParams = false;

  public boolean hasParameters()
  {
    if (!lookedForParams)
    {
      lookedForParams = true;
      try
      {
        hasParams = (getRunnerConfig().getArguments().size() > 0);
      } catch (Exception e)
      {

      }
    }
    return hasParams;
  }

  public void attachWSMenuEntry(JMenu atpoint, AlignFrame alignFrame)
  {
    if (service instanceof MsaWS<?>)
    {
      new MsaWSClient().attachWSMenuEntry(atpoint, this, alignFrame);
    }
    else if (service instanceof SequenceAnnotation<?>)
    {
      new SequenceAnnotationWSClient().attachWSMenuEntry(atpoint, this,
              alignFrame);
    }
  }

  public String getServiceTypeURI()
  {
    return "java:" + serviceType;
  }
}
