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

import jalview.api.AlignCalcWorkerI;
import jalview.bin.Cache;
import jalview.gui.AlignFrame;
import jalview.gui.Desktop;
import jalview.gui.JvSwingUtils;
import jalview.ws.jws2.dm.AAConSettings;
import jalview.ws.jws2.jabaws2.Jws2Instance;
import jalview.ws.params.WsParamSetI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * @author jprocter
 * 
 */
public class SequenceAnnotationWSClient extends Jws2Client
{

  public static final String AAConCalcId = "jabaws2.AACon";

  /**
   * initialise a client so its attachWSMenuEntry method can be called.
   */
  public SequenceAnnotationWSClient()
  {
    // TODO Auto-generated constructor stub
  }

  public SequenceAnnotationWSClient(final Jws2Instance sh,
          AlignFrame alignFrame, WsParamSetI preset, boolean editParams)
  {
    super(alignFrame, preset, null);
    initSequenceAnnotationWSClient(sh, alignFrame, preset, editParams);
  }

  public void initSequenceAnnotationWSClient(final Jws2Instance sh,
          AlignFrame alignFrame, WsParamSetI preset, boolean editParams)
  {
    if (alignFrame.getViewport().getAlignment().isNucleotide())
    {
      JOptionPane.showMessageDialog(Desktop.desktop, sh.serviceType
              + " can only be used\nfor amino acid alignments.",
              "Wrong type of sequences!", JOptionPane.WARNING_MESSAGE);
      return;

    }
    if (sh.action.toLowerCase().contains("conservation"))
    {
      // Build an AACon style client - take alignment, return annotation for
      // columns

      List<AlignCalcWorkerI> clnts = alignFrame.getViewport()
              .getCalcManager()
              .getRegisteredWorkersOfClass(AAConClient.class);
      if (clnts == null || clnts.size() == 0)
      {
        if (!processParams(sh, editParams))
        {
          return;
        }
        AAConClient worker;
        alignFrame
                .getViewport()
                .getCalcManager()
                .registerWorker(
                        worker = new AAConClient(sh, alignFrame,
                                this.preset, paramset));
        alignFrame.getViewport().getCalcManager().startWorker(worker);

      }
      else
      {
        AAConClient worker = (AAConClient) clnts.get(0);
        if (editParams)
        {
          paramset = worker.getArguments();
          preset = worker.getPreset();
        }

        if (!processParams(sh, editParams, true))
        {
          return;
        }
        // reinstate worker if it was blacklisted (might have happened due to
        // invalid parameters)
        alignFrame.getViewport().getCalcManager().workerMayRun(worker);
        worker.updateParameters(this.preset, paramset);

      }
    }
    if (sh.action.toLowerCase().contains("disorder"))
    {
      // build IUPred style client. take sequences, returns annotation per
      // sequence.
      if (!processParams(sh, editParams))
      {
        return;
      }

      alignFrame
              .getViewport()
              .getCalcManager()
              .startWorker(
                      new AADisorderClient(sh, alignFrame, preset, paramset));
    }

  }

  public SequenceAnnotationWSClient(AAConSettings fave,
          AlignFrame alignFrame, boolean b)
  {
    super(alignFrame, fave.getPreset(), fave.getJobArgset());
    initSequenceAnnotationWSClient(fave.getService(), alignFrame,
            fave.getPreset(), b);
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.ws.jws2.Jws2Client#attachWSMenuEntry(javax.swing.JMenu,
   * jalview.ws.jws2.jabaws2.Jws2Instance, jalview.gui.AlignFrame)
   */
  public void attachWSMenuEntry(JMenu wsmenu, final Jws2Instance service,
          final AlignFrame alignFrame)
  {
    if (service.serviceType.equals(compbio.ws.client.Services.AAConWS
            .toString()))
    {
      registerAAConWSInstance(wsmenu, service, alignFrame);
      return;
    }
    boolean hasparams = service.hasParameters();
    // Assume name ends in WS
    String calcName = service.serviceType.substring(0,
            service.serviceType.length() - 2);

    JMenuItem annotservice = new JMenuItem(calcName + " Defaults");
    annotservice.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        new SequenceAnnotationWSClient(service, alignFrame, null, false);
      }
    });
    wsmenu.add(annotservice);
    if (hasparams)
    {
      // only add these menu options if the service has user-modifiable
      // arguments
      annotservice = new JMenuItem("Edit settings and run ...");
      annotservice
              .setToolTipText("View and change parameters before running calculation");

      annotservice.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          new SequenceAnnotationWSClient(service, alignFrame, null, true);
        }
      });
      wsmenu.add(annotservice);
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
              new SequenceAnnotationWSClient(service, alignFrame, preset,
                      false);
            }

          });
          presetlist.add(methodR);
        }
        wsmenu.add(presetlist);
      }

    }
    else
    {
      annotservice = new JMenuItem("View documentation");
      if (service.docUrl != null)
      {
        annotservice.addActionListener(new ActionListener()
        {

          @Override
          public void actionPerformed(ActionEvent arg0)
          {
            Desktop.instance.showUrl(service.docUrl);
          }
        });
        annotservice.setToolTipText("<html>"
                + JvSwingUtils.wrapTooltip("View <a href=\""
                        + service.docUrl + "\">" + service.docUrl + "</a>")
                + "</html>");
        wsmenu.add(annotservice);
      }
    }
  }

  private final String AAconToggle = "AACon Calculations",
          AAconToggleTooltip = "When checked, AACon calculations are updated automatically.",
          AAeditSettings = "Change AACon Settings...",
          AAeditSettingsTooltip = "Modify settings for AACon calculations.";

  private void registerAAConWSInstance(final JMenu wsmenu,
          final Jws2Instance service, final AlignFrame alignFrame)
  {
    // register this in the AACon settings set
    JCheckBoxMenuItem _aaConEnabled = null;
    for (int i = 0; i < wsmenu.getItemCount(); i++)
    {
      JMenuItem item = wsmenu.getItem(i);
      if (item instanceof JCheckBoxMenuItem
              && item.getText().equals(AAconToggle))
      {
        _aaConEnabled = (JCheckBoxMenuItem) item;
      }
    }
    // is there an aaCon worker already present - if so, set it to use the
    // given service handle
    {
      List<AlignCalcWorkerI> aaconClient = alignFrame.getViewport()
              .getCalcManager()
              .getRegisteredWorkersOfClass(AAConClient.class);
      if (aaconClient != null && aaconClient.size() > 0)
      {
        AAConClient worker = (AAConClient) aaconClient.get(0);
        if (!worker.service.hosturl.equals(service.hosturl))
        {
          // javax.swing.SwingUtilities.invokeLater(new Runnable()
          {
            // @Override
            // public void run()
            {
              removeCurrentAAConWorkerFor(alignFrame);
              buildCurrentAAConWorkerFor(alignFrame, service);
            }
          }// );
        }
      }
    }

    // is there a service already registered ? there shouldn't be if we are
    // being called correctly
    if (_aaConEnabled == null)
    {
      final JCheckBoxMenuItem aaConEnabled = new JCheckBoxMenuItem(
              AAconToggle);
      wsmenu.addMenuListener(new MenuListener()
      {

        @Override
        public void menuSelected(MenuEvent arg0)
        {
          wsmenu.setEnabled(!alignFrame.getViewport().getAlignment()
                  .isNucleotide());
          List<AlignCalcWorkerI> aaconClient = alignFrame.getViewport()
                  .getCalcManager()
                  .getRegisteredWorkersOfClass(AAConClient.class);
          if (aaconClient != null && aaconClient.size() > 0)
          {
            aaConEnabled.setSelected(true);
          }
          else
          {
            aaConEnabled.setSelected(false);
          }
        }

        @Override
        public void menuDeselected(MenuEvent arg0)
        {
          // TODO Auto-generated method stub

        }

        @Override
        public void menuCanceled(MenuEvent arg0)
        {
          // TODO Auto-generated method stub

        }
      });
      aaConEnabled.setToolTipText("<html><p>"
              + JvSwingUtils.wrapTooltip(AAconToggleTooltip + "</p>")
              + "</html>");
      aaConEnabled.addActionListener(new ActionListener()
      {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
          List<AlignCalcWorkerI> aaconClient = alignFrame.getViewport()
                  .getCalcManager()
                  .getRegisteredWorkersOfClass(AAConClient.class);
          if (aaconClient != null && aaconClient.size() > 0)
          {
            removeCurrentAAConWorkerFor(alignFrame);
          }
          else
          {
            buildCurrentAAConWorkerFor(alignFrame);

          }
        }

      });
      wsmenu.add(aaConEnabled);
      JMenuItem modifyParams = new JMenuItem(AAeditSettings);
      modifyParams.setToolTipText("<html><p>"
              + JvSwingUtils.wrapTooltip(AAeditSettingsTooltip + "</p>")
              + "</html>");
      modifyParams.addActionListener(new ActionListener()
      {

        @Override
        public void actionPerformed(ActionEvent arg0)
        {
          showAAConAnnotationSettingsFor(alignFrame);
        }
      });
      wsmenu.add(modifyParams);

    }
  }

  private static void showAAConAnnotationSettingsFor(AlignFrame alignFrame)
  {
    /*
     * preferred settings Whether AACon is automatically recalculated Which
     * AACon server to use What parameters to use
     */
    // could actually do a class search for this too
    AAConSettings fave = (AAConSettings) alignFrame.getViewport()
            .getCalcIdSettingsFor(AAConCalcId);
    if (fave == null)
    {
      fave = createDefaultAAConSettings();
    }
    new SequenceAnnotationWSClient(fave, alignFrame, true);

  }

  private static void buildCurrentAAConWorkerFor(AlignFrame alignFrame)
  {
    buildCurrentAAConWorkerFor(alignFrame, null);
  }

  private static void buildCurrentAAConWorkerFor(AlignFrame alignFrame,
          Jws2Instance service)
  {
    /*
     * preferred settings Whether AACon is automatically recalculated Which
     * AACon server to use What parameters to use
     */
    AAConSettings fave = (AAConSettings) alignFrame.getViewport()
            .getCalcIdSettingsFor(AAConCalcId);
    if (fave == null)
    {
      fave = createDefaultAAConSettings(service);
    }
    else
    {
      if (service != null
              && !fave.getService().hosturl.equals(service.hosturl))
      {
        Cache.log.debug("Changing AACon service to " + service.hosturl
                + " from " + fave.getService().hosturl);
        fave.setService(service);
      }
    }
    new SequenceAnnotationWSClient(fave, alignFrame, false);
  }

  private static AAConSettings createDefaultAAConSettings()
  {
    return createDefaultAAConSettings(null);
  }

  private static AAConSettings createDefaultAAConSettings(
          Jws2Instance service)
  {
    if (service != null)
    {
      if (!service.serviceType.toString().equals(
              compbio.ws.client.Services.AAConWS.toString()))
      {
        Cache.log
                .warn("Ignoring invalid preferred service for AACon calculations (service type was "
                        + service.serviceType + ")");
        service = null;
      }
      else
      {
        // check service is actually in the list of currently avaialable
        // services
        if (!Jws2Discoverer.getDiscoverer().getServices().contains(service))
        {
          // it isn't ..
          service = null;
        }
      }
    }
    if (service == null)
    {
      // get the default service for AACon
      service = Jws2Discoverer.getDiscoverer().getPreferredServiceFor(null,
              compbio.ws.client.Services.AAConWS.toString());
    }
    if (service == null)
    {
      // TODO raise dialog box explaining error, and/or open the JABA
      // preferences menu.
      throw new Error("No AACon service found.");
    }
    return new AAConSettings(true, service, null, null);
  }

  private static void removeCurrentAAConWorkerFor(AlignFrame alignFrame)
  {
    alignFrame.getViewport().getCalcManager()
            .removeRegisteredWorkersOfClass(AAConClient.class);
  }
}
