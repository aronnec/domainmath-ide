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
package jalview.ws.rest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import jalview.bin.Cache;
import jalview.datamodel.AlignmentView;
import jalview.gui.AlignFrame;
import jalview.gui.AlignViewport;
import jalview.gui.AlignmentPanel;
import jalview.gui.Desktop;
import jalview.gui.WebserviceInfo;
import jalview.io.packed.DataProvider.JvDataType;
import jalview.ws.WSClient;
import jalview.ws.WSClientI;
import jalview.ws.WSMenuEntryProviderI;

/**
 * @author JimP
 * 
 */
public class RestClient extends WSClient implements WSClientI,
        WSMenuEntryProviderI
{
  RestServiceDescription service;

  public RestClient(RestServiceDescription rsd)
  {
    service = rsd;
  }

  /**
   * parent alignframe for this job
   */
  AlignFrame af;

  /**
   * alignment view which provides data for job.
   */
  AlignViewport av;

  /**
   * get the alignFrame for the associated input data if it exists.
   * 
   * @return
   */
  protected AlignFrame recoverAlignFrameForView()
  {
    return jalview.gui.Desktop.getAlignFrameFor(av);
  }

  public RestClient(RestServiceDescription service2, AlignFrame alignFrame)
  {
    this(service2, alignFrame, false);
  }
  boolean headless = false;
  public RestClient(RestServiceDescription service2, AlignFrame alignFrame, boolean nogui)
  {
    service = service2;
    af = alignFrame;
    av = alignFrame.getViewport();
    headless = nogui;
    constructJob();
  }

  public void setWebserviceInfo(boolean headless)
  {
    WebServiceJobTitle = service.details.Action + " using "
            + service.details.Name;
    WebServiceName = service.details.Name;
    WebServiceReference = "No reference - go to url for more info";
    if (service.details.description != null)
    {
      WebServiceReference = service.details.description;
    }
    if (!headless)
    {
      wsInfo = new WebserviceInfo(WebServiceJobTitle, WebServiceName + "\n"
              + WebServiceReference);
      wsInfo.setRenderAsHtml(true);
    }

  }

  @Override
  public boolean isCancellable()
  {
    // TODO define process for cancelling rsbws jobs
    return false;
  }

  @Override
  public boolean canMergeResults()
  {
    // TODO process service definition to identify if the results might be
    // mergeable
    // TODO: change comparison for annotation merge
    return false;
  }

  @Override
  public void cancelJob()
  {
    System.err.println("Cannot cancel this job type: " + service);
  }

  @Override
  public void attachWSMenuEntry(final JMenu wsmenu,
          final AlignFrame alignFrame)
  {
    JMenuItem submit = new JMenuItem(service.details.Name);
    submit.setToolTipText(service.details.Action + " using "
            + service.details.Name);
    submit.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        new RestClient(service, alignFrame);
      }

    });
    wsmenu.add(submit);
    // TODO: menu listener should enable/disable entry depending upon selection
    // state of the alignment
    wsmenu.addMenuListener(new MenuListener()
    {

      @Override
      public void menuSelected(MenuEvent e)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void menuDeselected(MenuEvent e)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void menuCanceled(MenuEvent e)
      {
        // TODO Auto-generated method stub

      }

    });

  }

  /**
   * record of initial undoredo hash for the alignFrame providing data for this
   * job.
   */
  long[] undoredo = null;

  /**
   * Compare the original input data to the data currently presented to the
   * user. // LOGIC: compare undo/redo - if same, merge regardless (coping with
   * any changes in hidden columns as normal) // if different undo/redo then
   * compare region that was submitted // if same, then merge as before, if
   * different then prompt user to open a new window.
   * 
   * @return
   */
  protected boolean isAlignmentModified()
  {
    if (undoredo == null || av == null || av.getAlignment() == null)
    {
      // always return modified if we don't have access to live GUI elements
      // anymore.
      return true;
    }
    if (av.isUndoRedoHashModified(undoredo))
    {
      // alignment has been modified in some way.
      return true;
    }
    // TODO: look deeper into modification of selection state, etc that may
    // affect RestJobThread.realiseResults(boolean merge);
    return false;

  }

  /**
   * TODO: combine to form a dataset+alignment+annotation context
   */
  AlignmentView _input;

  /**
   * input data context
   */
  jalview.io.packed.JalviewDataset jds;

  /**
   * informative name for results
   */
  public String viewTitle;
  protected void constructJob()
  {
    service.setInvolvesFlags();
    // record all aspects of alignment view so we can merge back or recreate
    // later
    undoredo = av.getUndoRedoHash();
    /**
     * delete ? Vector sgs = av.getAlignment().getGroups(); if (sgs!=null) {
     * _sgs = new SequenceGroup[sgs.size()]; sgs.copyInto(_sgs); } else { _sgs =
     * new SequenceGroup[0]; }
     */
    boolean selExists = (av.getSelectionGroup() != null)
            && (av.getSelectionGroup().getSize() > 1);
    // TODO: JAL-715: refactor to alignViewport methods and revise to full
    // focus+context+dataset input data staging model
    if (selExists)
    {
      if (service.partitiondata)
      {
        if (av.getAlignment().getGroups() != null
                && av.getAlignment().getGroups().size() > 0)
        {
          // intersect groups with selected region
          _input = new AlignmentView(av.getAlignment(),
                  av.getColumnSelection(), av.getSelectionGroup(),
                  av.hasHiddenColumns(), true, true);
          viewTitle = "selected "
                  + (av.hasHiddenColumns() ? "visible" : "")
                  + " region of " + af.getTitle();
        }
        else
        {
          // use selected region to partition alignment
          _input = new AlignmentView(av.getAlignment(),
                  av.getColumnSelection(), av.getSelectionGroup(),
                  av.hasHiddenColumns(), false, true);
        }
        viewTitle = "select and unselected "
                + (av.hasHiddenColumns() ? "visible" : "")
                + " regions from " + af.getTitle();
      }
      else
      {
        // just take selected region intersection
        _input = new AlignmentView(av.getAlignment(),
                av.getColumnSelection(), av.getSelectionGroup(),
                av.hasHiddenColumns(), true, true);
        viewTitle = "selected " + (av.hasHiddenColumns() ? "visible" : "")
                + " region of " + af.getTitle();
      }
    }
    else
    {
      // standard alignment view without selection present
      _input = new AlignmentView(av.getAlignment(),
              av.getColumnSelection(), null, av.hasHiddenColumns(), false,
              true);
      viewTitle = "" + (av.hasHiddenColumns() ? "visible region of " : "")
              + af.getTitle();
    }

    RestJobThread jobsthread = new RestJobThread(this);

    if (jobsthread.isValid())
    {
      setWebserviceInfo(headless);
      if (!headless) {
        wsInfo.setthisService(this);
        jobsthread.setWebServiceInfo(wsInfo);
      }
      jobsthread.start();
    }
    else
    {
      // TODO: try to tell the user why the job couldn't be started.
      JOptionPane
              .showMessageDialog(
                      Desktop.desktop,
                      (jobsthread.hasWarnings() ? jobsthread.getWarnings()
                              : "The Job couldn't be started. Please check your input, and the Jalview console for any warning messages."),
                      "Unable to start web service analysis",
                      JOptionPane.WARNING_MESSAGE);
    }
  }

  public static RestClient makeShmmrRestClient()
  {
    String action = "Analysis", description = "Sequence Harmony and Multi-Relief (Brandt et al. 2010)", name = "Multi-Harmony";
    Hashtable<String, InputType> iparams = new Hashtable<String, InputType>();
    jalview.ws.rest.params.JobConstant toolp;
    // toolp = new jalview.ws.rest.JobConstant("tool","jalview");
    // iparams.put(toolp.token, toolp);
    // toolp = new jalview.ws.rest.params.JobConstant("mbjob[method]","shmr");
    // iparams.put(toolp.token, toolp);
    // toolp = new
    // jalview.ws.rest.params.JobConstant("mbjob[description]","step 1");
    // iparams.put(toolp.token, toolp);
    // toolp = new jalview.ws.rest.params.JobConstant("start_search","1");
    // iparams.put(toolp.token, toolp);
    // toolp = new jalview.ws.rest.params.JobConstant("blast","0");
    // iparams.put(toolp.token, toolp);

    jalview.ws.rest.params.Alignment aliinput = new jalview.ws.rest.params.Alignment();
    // SHMR server has a 65K limit for content pasted into the 'ali' parameter,
    // so we always upload our files.
    aliinput.token = "ali_file";
    aliinput.writeAsFile = true;
    iparams.put(aliinput.token, aliinput);
    jalview.ws.rest.params.SeqGroupIndexVector sgroups = new jalview.ws.rest.params.SeqGroupIndexVector();
    sgroups.setMinsize(2);
    sgroups.min = 2;// need at least two group defined to make a partition
    iparams.put("groups", sgroups);
    sgroups.token = "groups";
    sgroups.sep = " ";
    RestServiceDescription shmrService = new RestServiceDescription(
            action,
            description,
            name,
            "http://zeus.few.vu.nl/programs/shmrwww/index.php?tool=jalview",// ?tool=jalview&mbjob[method]=shmr&mbjob[description]=step1",
            "?tool=jalview", iparams, true, false, '-');
    // a priori knowledge of the data returned from the service
    shmrService.addResultDatatype(JvDataType.ANNOTATION);
    return new RestClient(shmrService);
  }

  public AlignmentPanel recoverAlignPanelForView()
  {
    AlignmentPanel[] aps = Desktop
            .getAlignmentPanels(av.getSequenceSetId());
    for (AlignmentPanel alp : aps)
    {
      if (alp.av == av)
      {
        return alp;
      }
    }
    return null;
  }

  public boolean isShowResultsInNewView()
  {
    // TODO make this a property of the service
    return true;
  }

  protected static Vector<String> services = null;

  public static final String RSBS_SERVICES = "RSBS_SERVICES";

  public static RestClient[] getRestClients()
  {
    if (services == null)
    {
      services = new Vector<String>();
      try
      {
        for (RestServiceDescription descr : RestServiceDescription
                .parseDescriptions(jalview.bin.Cache.getDefault(
                        RSBS_SERVICES,
                        makeShmmrRestClient().service.toString())))
        {
          services.add(descr.toString());
        }
      } catch (Exception ex)
      {
        System.err
                .println("Serious - RSBS descriptions in user preferences are corrupt!");
        ex.printStackTrace();
      }

    }
    RestClient[] lst = new RestClient[services.size()];
    int i = 0;
    for (String svc : services)
    {
      lst[i++] = new RestClient(new RestServiceDescription(svc));
    }
    return lst;
  }

  public static void main(String args[])
  {
    try
    {
      RestClient[] clients = getRestClients();
      System.out.println("Got " + clients.length + " clients.");
      int i = 0;
      Vector<String> urls = new Vector<String>();
      for (RestClient cl : clients)
      {
        System.out.println("" + (++i) + ": " + cl.service.toString());
        urls.add(cl.service.toString());
      }
      setRsbsServices(urls);
      if (clients.length != getRestClients().length)
      {
        System.err
                .println("Failed. Differing numbers of clients when stringified and parsed again.");
      }

    } catch (Throwable x)
    {
      System.err.println("Failed. Unexpected exception.");
      x.printStackTrace();
    }
  }

  public String getAction()
  {
    return service.details.Action;
  }

  public RestServiceDescription getRestDescription()
  {
    return service;
  }

  public static Vector<String> getRsbsDescriptions()
  {
    Vector<String> rsbsDescrs = new Vector<String>();
    for (RestClient rsbs : getRestClients())
    {
      rsbsDescrs.add(rsbs.getRestDescription().toString());
    }
    return rsbsDescrs;
  }

  public static void setRsbsServices(Vector<String> rsbsUrls)
  {
    if (rsbsUrls != null)
    {
      // TODO: consider validating services ?
      services = new Vector<String>(rsbsUrls);
      StringBuffer sprop = new StringBuffer();
      for (String s : services)
      {
        sprop.append(s);
      }
      Cache.setProperty(RSBS_SERVICES, sprop.toString());
    }
    else
    {
      Cache.removeProperty(RSBS_SERVICES);
    }
  }

}
