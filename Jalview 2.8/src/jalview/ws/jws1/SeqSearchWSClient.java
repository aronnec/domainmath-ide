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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

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
public class SeqSearchWSClient extends WS1Client
{
  /**
   * server is a WSDL2Java generated stub for an archetypal MsaWSI service.
   */
  ext.vamsas.SeqSearchI server;

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

  public SeqSearchWSClient(ext.vamsas.ServiceHandle sh, String altitle,
          jalview.datamodel.AlignmentView msa, String db,
          Alignment seqdataset, AlignFrame _alignFrame)
  {
    super();
    alignFrame = _alignFrame;
    // can generalise the two errors below for metadata mapping from interface
    // name to service client name
    if (!sh.getAbstractName().equals(this.getServiceActionKey()))
    {
      JOptionPane.showMessageDialog(Desktop.desktop,
              "The Service called \n" + sh.getName()
                      + "\nis not a \nSequence Search Service !",
              "Internal Jalview Error", JOptionPane.WARNING_MESSAGE);

      return;
    }

    if ((wsInfo = setWebService(sh)) == null)
    {
      JOptionPane.showMessageDialog(Desktop.desktop,
              "The Sequence Search Service named " + sh.getName()
                      + " is unknown", "Internal Jalview Error",
              JOptionPane.WARNING_MESSAGE);

      return;
    }
    startSeqSearchClient(altitle, msa, db, seqdataset);

  }

  /**
   * non-process web service interaction - use this for calling HEADLESS
   * synchronous service methods
   * 
   * @param sh
   */
  public SeqSearchWSClient(ServiceHandle sh)
  {
    setWebService(sh, true);
  }

  public SeqSearchWSClient()
  {

    super();
    // add a class reference to the list
  }

  private void startSeqSearchClient(String altitle, AlignmentView msa,
          String db, Alignment seqdataset)
  {
    if (!locateWebService())
    {
      return;
    }
    String visdb = (db == null || db == "") ? "default" : db; // need a visible
    // name for a
    // sequence db
    boolean profileSearch = msa.getSequences().length > 2 ? true : false;
    // single sequence or profile from alignment view
    wsInfo.setProgressText("Searching "
            + visdb
            + (!profileSearch ? " with sequence "
                    + msa.getSequences()[0].getRefSeq().getName()
                    : " with profile") + " from " + altitle
            + "\nJob details\n");

    String jobtitle = WebServiceName
            + ((WebServiceName.indexOf("earch") > -1) ? " " : " search ")
            + " of "
            + visdb
            + (!profileSearch ? " with sequence "
                    + msa.getSequences()[0].getRefSeq().getName()
                    : " with profile") + " from " + altitle;
    SeqSearchWSThread ssthread = new SeqSearchWSThread(server, WsURL,
            wsInfo, alignFrame, WebServiceName, jobtitle, msa, db,
            seqdataset);
    wsInfo.setthisService(ssthread);
    ssthread.start();
  }

  /**
   * Initializes the server field with a valid service implementation.
   * 
   * @return true if service was located.
   */
  private boolean locateWebService()
  {
    // this can be abstracted using reflection
    // TODO: MuscleWS transmuted to generic MsaWS client
    SeqSearchServiceLocator loc = new SeqSearchServiceLocator(); // Default

    try
    {
      this.server = (SeqSearchI) loc.getSeqSearchService(new java.net.URL(
              WsURL));
      ((SeqSearchServiceSoapBindingStub) this.server).setTimeout(60000); // One
      // minute
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
    return "SeqSearch";
  }

  protected String getServiceActionDescription()
  {
    return "Sequence Database Search";
  }

  // simple caching of db parameters for each service endpoint
  private static Hashtable dbParamsForEndpoint;
  static
  {
    dbParamsForEndpoint = new Hashtable();
  }

  public String[] getSupportedDatabases() throws Exception
  {

    // check that we haven't already been to this service endpoint
    if (dbParamsForEndpoint.containsKey(WsURL))
    {
      return (String[]) dbParamsForEndpoint.get(WsURL);
    }
    if (!locateWebService())
    {
      throw new Exception("Cannot contact service endpoint at " + WsURL);
    }
    String database = server.getDatabase();
    if (database == null)
    {
      dbParamsForEndpoint.put(WsURL, new String[]
      {});
      return null;
    }
    StringTokenizer en = new StringTokenizer(database.trim(), ",| ");
    String[] dbs = new String[en.countTokens()];
    for (int i = 0; i < dbs.length; i++)
    {
      dbs[i++] = en.nextToken().trim();
    }
    dbParamsForEndpoint.put(WsURL, dbs);
    return dbs;
  }

  public void attachWSMenuEntry(JMenu wsmenu, final ServiceHandle sh,
          final AlignFrame af)
  {
    // look for existing database service submenus on wsmenu
    Hashtable dbsrchs = new Hashtable();
    Vector newdbsrch = new Vector();
    Component entries[] = wsmenu.getComponents();
    for (int i = 0; entries != null && i < entries.length; i++)
    {
      if (entries[i] instanceof JMenu)
      {
        dbsrchs.put(entries[i].getName(), entries[i]);
      }
    }
    JMenu defmenu = (JMenu) dbsrchs.get("Default Database");
    if (defmenu == null)
    {
      dbsrchs.put("Default Database", defmenu = new JMenu(
              "Default Database"));
      newdbsrch.addElement(defmenu);
    }

    String dbs[] = null;
    try
    {
      dbs = new jalview.ws.jws1.SeqSearchWSClient(sh)
              .getSupportedDatabases();
    } catch (Exception e)
    {
      jalview.bin.Cache.log.warn(
              "Database list request failed, so disabling SeqSearch Service client "
                      + sh.getName() + " at " + sh.getEndpointURL(), e);
      return;
    }
    JMenuItem method;
    // do default entry
    defmenu.add(method = new JMenuItem(sh.getName()));
    method.setToolTipText(sh.getEndpointURL());
    method.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        // use same input gatherer as for secondary structure prediction
        // we could actually parameterise the gatherer method here...
        AlignmentView msa = af.gatherSeqOrMsaForSecStrPrediction();
        new jalview.ws.jws1.SeqSearchWSClient(sh, af.getTitle(), msa, null,
                af.getViewport().getAlignment().getDataset(), af);
      }
    });
    // add entry for each database the service supports
    for (int db = 0; dbs != null && db < dbs.length; db++)
    {
      JMenu dbmenu = (JMenu) dbsrchs.get(dbs[db]);
      if (dbmenu == null)
      {
        dbsrchs.put(dbs[db], dbmenu = new JMenu(dbs[db]));
        newdbsrch.addElement(dbmenu);
      }
      // add the client handler code for this service
      dbmenu.add(method = new JMenuItem(sh.getName()));
      method.setToolTipText(sh.getEndpointURL());
      final String searchdb = dbs[db];
      method.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          AlignmentView msa = af.gatherSeqOrMsaForSecStrPrediction();
          new jalview.ws.jws1.SeqSearchWSClient(sh, af.getTitle(), msa,
                  searchdb, af.getViewport().getAlignment().getDataset(),
                  af);
        }
      });
    }
    // add the databases onto the seqsearch menu
    Enumeration e = newdbsrch.elements();
    while (e.hasMoreElements())
    {
      Object el = e.nextElement();
      if (el instanceof JMenu)
      {
        wsmenu.add((JMenu) el);
      }
      else
      {
        wsmenu.add((JMenuItem) el);
      }
    }

  }
}
