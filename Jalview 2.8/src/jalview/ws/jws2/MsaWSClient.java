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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import jalview.datamodel.*;
import jalview.gui.*;
import compbio.data.msa.MsaWS;
import compbio.metadata.Argument;
import jalview.ws.jws2.jabaws2.Jws2Instance;
import jalview.ws.params.WsParamSetI;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class MsaWSClient extends Jws2Client
{
  /**
   * server is a WSDL2Java generated stub for an archetypal MsaWSI service.
   */
  MsaWS server;

  public MsaWSClient(Jws2Instance sh, String altitle,
          jalview.datamodel.AlignmentView msa, boolean submitGaps,
          boolean preserveOrder, Alignment seqdataset,
          AlignFrame _alignFrame)
  {
    this(sh, null, null, false, altitle, msa, submitGaps, preserveOrder,
            seqdataset, _alignFrame);
    // TODO Auto-generated constructor stub
  }

  public MsaWSClient(Jws2Instance sh, WsParamSetI preset, String altitle,
          jalview.datamodel.AlignmentView msa, boolean submitGaps,
          boolean preserveOrder, Alignment seqdataset,
          AlignFrame _alignFrame)
  {
    this(sh, preset, null, false, altitle, msa, submitGaps, preserveOrder,
            seqdataset, _alignFrame);
    // TODO Auto-generated constructor stub
  }

  /**
   * Creates a new MsaWSClient object that uses a service given by an externally
   * retrieved ServiceHandle
   * 
   * @param sh
   *          service handle of type AbstractName(MsaWS)
   * @param altitle
   *          DOCUMENT ME!
   * @param msa
   *          DOCUMENT ME!
   * @param submitGaps
   *          DOCUMENT ME!
   * @param preserveOrder
   *          DOCUMENT ME!
   */

  public MsaWSClient(Jws2Instance sh, WsParamSetI preset,
          List<Argument> arguments, boolean editParams, String altitle,
          jalview.datamodel.AlignmentView msa, boolean submitGaps,
          boolean preserveOrder, Alignment seqdataset,
          AlignFrame _alignFrame)
  {
    super(_alignFrame, preset, arguments);
    if (!processParams(sh, editParams))
    {
      return;
    }

    if (!(sh.service instanceof MsaWS))
    {
      // redundant at mo - but may change
      JOptionPane
              .showMessageDialog(
                      Desktop.desktop,
                      "The Service called \n"
                              + sh.serviceType
                              + "\nis not a \nMultiple Sequence Alignment Service !",
                      "Internal Jalview Error", JOptionPane.WARNING_MESSAGE);

      return;
    }
    server = (MsaWS) sh.service;
    if ((wsInfo = setWebService(sh, false)) == null)
    {
      JOptionPane.showMessageDialog(Desktop.desktop,
              "The Multiple Sequence Alignment Service named "
                      + sh.serviceType + " is unknown",
              "Internal Jalview Error", JOptionPane.WARNING_MESSAGE);

      return;
    }
    startMsaWSClient(altitle, msa, submitGaps, preserveOrder, seqdataset);

  }

  public MsaWSClient()
  {
    super();
    // add a class reference to the list
  }

  private void startMsaWSClient(String altitle, AlignmentView msa,
          boolean submitGaps, boolean preserveOrder, Alignment seqdataset)
  {
    // if (!locateWebService())
    // {
    // return;
    // }

    wsInfo.setProgressText(((submitGaps) ? "Re-alignment" : "Alignment")
            + " of " + altitle + "\nJob details\n");
    String jobtitle = WebServiceName.toLowerCase();
    if (jobtitle.endsWith("alignment"))
    {
      if (submitGaps
              && (!jobtitle.endsWith("realignment") || jobtitle
                      .indexOf("profile") == -1))
      {
        int pos = jobtitle.indexOf("alignment");
        jobtitle = WebServiceName.substring(0, pos) + "re-alignment of "
                + altitle;
      }
      else
      {
        jobtitle = WebServiceName + " of " + altitle;
      }
    }
    else
    {
      jobtitle = WebServiceName + (submitGaps ? " re" : " ")
              + "alignment of " + altitle;
    }

    MsaWSThread msathread = new MsaWSThread(server, preset, paramset,
            WsURL, wsInfo, alignFrame, WebServiceName, jobtitle, msa,
            submitGaps, preserveOrder, seqdataset);
    wsInfo.setthisService(msathread);
    msathread.start();
  }

  protected String getServiceActionKey()
  {
    return "MsaWS";
  }

  protected String getServiceActionDescription()
  {
    return "Multiple Sequence Alignment";
  }

  /**
   * look at ourselves and work out if we are a service that can take a profile
   * and align to it
   * 
   * @return true if we can send gapped sequences to the alignment service
   */
  private boolean canSubmitGaps()
  {
    // TODO: query service or extract service handle props to check if we can
    // realign
    return (WebServiceName.indexOf("lustal") > -1); // cheat!
  }

  public void attachWSMenuEntry(JMenu rmsawsmenu,
          final Jws2Instance service, final AlignFrame alignFrame)
  {
    setWebService(service, true); // headless
    boolean finished = true, submitGaps = false;
    JMenu msawsmenu = rmsawsmenu;
    String svcname = WebServiceName;
    if (svcname.endsWith("WS"))
    {
      svcname = svcname.substring(0, svcname.length() - 2);
    }
    String calcName = svcname + " ";
    if (canSubmitGaps())
    {
      msawsmenu = new JMenu(svcname);
      rmsawsmenu.add(msawsmenu);
      calcName = "";
    }
    boolean hasparams = service.hasParameters();
    do
    {
      String action = "Align ";
      if (submitGaps == true)
      {
        action = "Realign ";
        msawsmenu = new JMenu("Realign with " + svcname);
        msawsmenu
                .setToolTipText("Align sequences to an existing alignment");
        rmsawsmenu.add(msawsmenu);
      }
      final boolean withGaps = submitGaps;

      JMenuItem method = new JMenuItem(calcName + "with Defaults");
      method.setToolTipText(action + "with default settings");

      method.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          AlignmentView msa = alignFrame.gatherSequencesForAlignment();
          new MsaWSClient(service, alignFrame.getTitle(), msa, withGaps,
                  true, alignFrame.getViewport().getAlignment()
                          .getDataset(), alignFrame);

        }
      });
      msawsmenu.add(method);
      if (hasparams)
      {
        // only add these menu options if the service has user-modifiable
        // arguments
        method = new JMenuItem("Edit settings and run ...");
        method.setToolTipText("View and change the parameters before alignment.");

        method.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            AlignmentView msa = alignFrame.gatherSequencesForAlignment();
            new MsaWSClient(service, null, null, true, alignFrame
                    .getTitle(), msa, withGaps, true, alignFrame
                    .getViewport().getAlignment().getDataset(), alignFrame);

          }
        });
        msawsmenu.add(method);
        List<WsParamSetI> presets = service.getParamStore().getPresets();
        if (presets != null && presets.size() > 0)
        {
          JMenu presetlist = new JMenu("Run " + calcName + "with preset");

          for (final WsParamSetI preset : presets)
          {
            final JMenuItem methodR = new JMenuItem(preset.getName());
            methodR.setToolTipText("<html><p>"
                    + JvSwingUtils.wrapTooltip("<strong>"
                            + (preset.isModifiable() ? "User Preset"
                                    : "Service Preset") + "</strong><br/>"
                            + preset.getDescription() + "</p>") + "</html>");
            methodR.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                AlignmentView msa = alignFrame
                        .gatherSequencesForAlignment();
                new MsaWSClient(service, preset, alignFrame.getTitle(),
                        msa, false, true, alignFrame.getViewport()
                                .getAlignment().getDataset(), alignFrame);

              }

            });
            presetlist.add(methodR);
          }
          msawsmenu.add(presetlist);
        }
      }
      if (!submitGaps && canSubmitGaps())
      {
        submitGaps = true;
        finished = false;
      }
      else
      {
        finished = true;
      }
    } while (!finished);
  }
}
