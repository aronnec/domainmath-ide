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
package jalview.ws.jws1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import ext.vamsas.*;
import jalview.datamodel.*;
import jalview.gui.*;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class MsaWSClient extends WS1Client
{
  /**
   * server is a WSDL2Java generated stub for an archetypal MsaWSI service.
   */
  ext.vamsas.MuscleWS server;

  AlignFrame alignFrame;

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

  public MsaWSClient(ext.vamsas.ServiceHandle sh, String altitle,
          jalview.datamodel.AlignmentView msa, boolean submitGaps,
          boolean preserveOrder, Alignment seqdataset,
          AlignFrame _alignFrame)
  {
    super();
    alignFrame = _alignFrame;
    if (!sh.getAbstractName().equals("MsaWS"))
    {
      JOptionPane
              .showMessageDialog(
                      Desktop.desktop,
                      "The Service called \n"
                              + sh.getName()
                              + "\nis not a \nMultiple Sequence Alignment Service !",
                      "Internal Jalview Error", JOptionPane.WARNING_MESSAGE);

      return;
    }

    if ((wsInfo = setWebService(sh)) == null)
    {
      JOptionPane.showMessageDialog(
              Desktop.desktop,
              "The Multiple Sequence Alignment Service named "
                      + sh.getName() + " is unknown",
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
    if (!locateWebService())
    {
      return;
    }

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

    MsaWSThread msathread = new MsaWSThread(server, WsURL, wsInfo,
            alignFrame, WebServiceName, jobtitle, msa, submitGaps,
            preserveOrder, seqdataset);
    wsInfo.setthisService(msathread);
    msathread.start();
  }

  /**
   * Initializes the server field with a valid service implementation.
   * 
   * @return true if service was located.
   */
  private boolean locateWebService()
  {
    // TODO: MuscleWS transmuted to generic MsaWS client
    MuscleWSServiceLocator loc = new MuscleWSServiceLocator(); // Default

    try
    {
      this.server = (MuscleWS) loc.getMuscleWS(new java.net.URL(WsURL));
      ((MuscleWSSoapBindingStub) this.server).setTimeout(60000); // One minute
      // timeout
    } catch (Exception ex)
    {
      wsInfo.setProgressText("Serious! " + WebServiceName
              + " Service location failed\nfor URL :" + WsURL + "\n"
              + ex.getMessage());
      wsInfo.setStatus(WebserviceInfo.ERROR);
      ex.printStackTrace();

      return false;
    }

    loc.getEngine().setOption("axis", "1");

    return true;
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

  public void attachWSMenuEntry(JMenu msawsmenu,
          final ServiceHandle serviceHandle, final AlignFrame alignFrame)
  {
    setWebService(serviceHandle, true); // headless
    JMenuItem method = new JMenuItem(WebServiceName);
    method.setToolTipText(WsURL);
    method.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        AlignmentView msa = alignFrame.gatherSequencesForAlignment();
        new jalview.ws.jws1.MsaWSClient(serviceHandle, alignFrame
                .getTitle(), msa, false, true, alignFrame.getViewport()
                .getAlignment().getDataset(), alignFrame);

      }

    });
    msawsmenu.add(method);
    if (canSubmitGaps())
    {
      // We know that ClustalWS can accept partial alignments for refinement.
      final JMenuItem methodR = new JMenuItem(serviceHandle.getName()
              + " Realign");
      methodR.setToolTipText(WsURL);
      methodR.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          AlignmentView msa = alignFrame.gatherSequencesForAlignment();
          new jalview.ws.jws1.MsaWSClient(serviceHandle, alignFrame
                  .getTitle(), msa, true, true, alignFrame.getViewport()
                  .getAlignment().getDataset(), alignFrame);

        }

      });
      msawsmenu.add(methodR);

    }

  }
}
