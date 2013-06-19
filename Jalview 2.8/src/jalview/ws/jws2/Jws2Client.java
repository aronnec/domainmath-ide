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

import java.util.List;

import javax.swing.JMenu;

import compbio.metadata.Argument;

import jalview.gui.AlignFrame;
import jalview.gui.Desktop;
import jalview.gui.WebserviceInfo;
import jalview.gui.WsJobParameters;
import jalview.ws.jws2.dm.JabaWsParamSet;
import jalview.ws.jws2.jabaws2.Jws2Instance;
import jalview.ws.params.WsParamSetI;

/**
 * provides metadata for a jabaws2 service instance - resolves names, etc.
 * 
 * @author JimP
 * 
 */
public abstract class Jws2Client extends jalview.ws.WSClient
{
  protected AlignFrame alignFrame;

  protected WsParamSetI preset;

  protected List<Argument> paramset;

  public Jws2Client(AlignFrame _alignFrame, WsParamSetI preset,
          List<Argument> arguments)
  {
    alignFrame = _alignFrame;
    this.preset = preset;
    if (preset != null)
    {
      if (!((preset instanceof JabaPreset) || preset instanceof JabaWsParamSet))
      {
        /*
         * { this.preset = ((JabaPreset) preset).p; } else if (preset instanceof
         * JabaWsParamSet) { List<Argument> newargs = new ArrayList<Argument>();
         * JabaWsParamSet pset = ((JabaWsParamSet) preset); for (Option opt :
         * pset.getjabaArguments()) { newargs.add(opt); } if (arguments != null
         * && arguments.size() > 0) { // merge arguments with preset's own
         * arguments. for (Argument opt : arguments) { newargs.add(opt); } }
         * paramset = newargs; } else {
         */
        throw new Error(
                "Implementation error: Can only instantiate Jaba parameter sets.");
      }
    }
    else
    {
      // just provided with a bunch of arguments
      this.paramset = arguments;
    }
  }

  boolean processParams(Jws2Instance sh, boolean editParams)
  {
    return processParams(sh, editParams, false);
  }

  protected boolean processParams(Jws2Instance sh, boolean editParams,
          boolean adjustingExisting)
  {

    if (editParams)
    {
      if (sh.paramStore == null)
      {
        sh.paramStore = new JabaParamStore(sh,
                Desktop.getUserParameterStore());
      }
      WsJobParameters jobParams = (preset == null && paramset != null && paramset
              .size() > 0) ? new WsJobParameters(null, sh, null, paramset)
              : new WsJobParameters(sh, preset);
      if (adjustingExisting)
      {
        jobParams.setName("Adjusting parameters for existing Calculation");
      }
      if (!jobParams.showRunDialog())
      {
        return false;
      }
      WsParamSetI prset = jobParams.getPreset();
      if (prset == null)
      {
        paramset = jobParams.isServiceDefaults() ? null : JabaParamStore
                .getJabafromJwsArgs(jobParams.getJobParams());
        this.preset = null;
      }
      else
      {
        this.preset = prset; // ((JabaPreset) prset).p;
        paramset = null; // no user supplied parameters.
      }
    }
    return true;

  }

  public Jws2Client()
  {
    // anonymous constructor - used for headless method calls only
  }

  protected WebserviceInfo setWebService(Jws2Instance serv, boolean b)
  {
    // serviceHandle = serv;
    String serviceInstance = serv.action; // serv.service.getClass().getName();
    WebServiceName = serv.serviceType;
    WebServiceJobTitle = serv.getActionText();
    WsURL = serv.hosturl;
    if (!b)
    {
      return new WebserviceInfo(WebServiceJobTitle, WebServiceJobTitle
              + " using service hosted at " + serv.hosturl + "\n"
              + (serv.description != null ? serv.description : ""));
    }
    return null;
  }

  /*
   * Jws2Instance serviceHandle; (non-Javadoc)
   * 
   * @see jalview.ws.WSMenuEntryProviderI#attachWSMenuEntry(javax.swing.JMenu,
   * jalview.gui.AlignFrame)
   * 
   * @Override public void attachWSMenuEntry(JMenu wsmenu, AlignFrame
   * alignFrame) { if (serviceHandle==null) { throw new
   * Error("Implementation error: No service handle for this Jws2 service."); }
   * attachWSMenuEntry(wsmenu, serviceHandle, alignFrame); }
   */
  /**
   * add the menu item for a particular jws2 service instance
   * 
   * @param wsmenu
   * @param service
   * @param alignFrame
   */
  abstract void attachWSMenuEntry(JMenu wsmenu, final Jws2Instance service,
          final AlignFrame alignFrame);

}
