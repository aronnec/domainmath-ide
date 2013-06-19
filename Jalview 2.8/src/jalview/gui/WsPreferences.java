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
package jalview.gui;

import jalview.bin.Cache;
import jalview.jbgui.GWsPreferences;
import jalview.ws.jws2.Jws2Discoverer;
import jalview.ws.rest.RestServiceDescription;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

public class WsPreferences extends GWsPreferences
{

  public WsPreferences()
  {
    super();
    initFromPreferences();
  }

  Vector<String> wsUrls, oldUrls, rsbsUrls, oldRsbsUrls;

  private boolean needWsMenuUpdate;

  private boolean oldJws1, oldJws2, oldIndexByHost, oldIndexByType,
          oldEnfin, oldWsWarning;

  private void initFromPreferences()
  {

    wsUrls = jalview.ws.jws2.Jws2Discoverer.getServiceUrls();
    if (wsUrls != null)
    {
      oldUrls = new Vector<String>(wsUrls);
    }
    else
    {
      oldUrls = null;
      wsUrls = new Vector<String>();
    }
    wsList.setDefaultRenderer(Integer.class, new JabaWSStatusRenderer());
    wsList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    updateList();
    rsbsUrls = jalview.ws.rest.RestClient.getRsbsDescriptions();
    if (rsbsUrls != null)
    {
      oldRsbsUrls = new Vector<String>(rsbsUrls);
    }
    else
    {
      oldRsbsUrls = null;
      rsbsUrls = new Vector<String>();
    }
    updateRsbsList();
    enableEnfinServices.setSelected(oldEnfin = Cache.getDefault(
            "SHOW_ENFIN_SERVICES", true));
    enableEnfinServices.addActionListener(updateAction);
    enableJws2Services.setSelected(oldJws2 = Cache.getDefault(
            "SHOW_JWS2_SERVICES", true));
    enableJws2Services.addActionListener(updateAction);
    indexByHost.setSelected(oldIndexByHost = Cache.getDefault(
            "WSMENU_BYHOST", false));
    indexByHost.addActionListener(updateAction);
    indexByType.setSelected(oldIndexByType = Cache.getDefault(
            "WSMENU_BYTYPE", false));
    indexByType.addActionListener(updateAction);
    displayWsWarning.setSelected(oldWsWarning = Cache.getDefault(
            "SHOW_WSDISCOVERY_ERRORS", true));
  }

  ActionListener updateAction = new ActionListener()
  {

    @Override
    public void actionPerformed(ActionEvent e)
    {
      update++;
    }

  };

  private void updateList()
  {
    Object tdat[][] = new Object[wsUrls.size()][2];
    int r = 0;
    for (String url : wsUrls)
    {
      int status = Jws2Discoverer.getDiscoverer().getServerStatusFor(url);
      tdat[r][1] = new Integer(status);
      tdat[r++][0] = url;
    }

    wsList.setModel(new WsUrlTableModel(tdat));
    wsList.getColumn("Status").setMinWidth(10);
  }

  private class JabaWSStatusRenderer extends JPanel implements
          TableCellRenderer
  {
    public JabaWSStatusRenderer()
    {
      setOpaque(true);
      setMinimumSize(new Dimension(10, 10));
      // setText(" ");

    }

    /**
     * render an Integer reflecting service status as a colour and symbol
     */

    @Override
    public Component getTableCellRendererComponent(JTable arg0,
            Object status, boolean isSelected, boolean hasFocus, int row,
            int column)
    {
      Color c;
      String t = new String("");
      switch (((Integer) status).intValue())
      {
      case 1:
        // cb.setSelected(true);
        // cb.setBackground(
        c = Color.green;
        break;
      case 0:
        // cb.setSelected(true);
        // cb.setBackground(
        c = Color.lightGray;
        break;
      case -1:
        // cb.setSelected(false);
        // cb.setBackground(
        c = Color.red;
        break;
      default:
        // cb.setSelected(false);
        // cb.setBackground(
        c = Color.orange;
      }
      setBackground(c);
      // setText(t);
      return this;

    }

  }

  private class WsUrlTableModel extends AbstractTableModel
  {

    private Object[][] data;

    public WsUrlTableModel(Object[][] tdat)
    {
      this.data = tdat;
    }

    @Override
    public int getColumnCount()
    {
      return 2;
    }

    @Override
    public String getColumnName(int column)
    {
      if (column == 1)
      {
        return "Status";
      }
      return "Service URL";
    }

    @Override
    public int getRowCount()
    {
      if (data == null)
      {
        return 0;
      }
      return data.length;
    }

    @Override
    public java.lang.Class<?> getColumnClass(int columnIndex)
    {
      return getValueAt(0, columnIndex).getClass();
    };

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
      return data[rowIndex][columnIndex];
    }

  }

  private void updateRsbsList()
  {
    sbrsList.setListData(rsbsUrls);
  }

  private void updateServiceList()
  {
    jalview.ws.jws2.Jws2Discoverer.setServiceUrls(wsUrls);
  }

  private void updateRsbsServiceList()
  {
    jalview.ws.rest.RestClient.setRsbsServices(rsbsUrls);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GWsPreferences#deleteWsUrl_actionPerformed(java.awt.event
   * .ActionEvent)
   */
  @Override
  protected void deleteWsUrl_actionPerformed(ActionEvent e)
  {
    int sel = wsList.getSelectedRow();
    if (sel > -1)
    {
      wsUrls.removeElementAt(sel);
      update++;
      updateList();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.jbgui.GWsPreferences#editWsUrl_actionPerformed(java.awt.event.
   * ActionEvent)
   */
  @Override
  protected void editWsUrl_actionPerformed(ActionEvent e)
  {
    int sel = wsList.getSelectedRow();
    if (sel > -1)
    {
      String url = editUrl(wsUrls.elementAt(sel), "Edit JABAWS URL");
      if (url != null)
      {
        int present = wsUrls.indexOf(url);
        if (present == -1)
        {
          update++;
          wsUrls.setElementAt(url, sel);
          updateList();
        }
        else
        {
          if (present != sel)
          {
            wsUrls.removeElementAt(sel);
            updateList();
          }
        }
      }
    }
  }

  @Override
  protected void newSbrsUrl_actionPerformed(ActionEvent e)
  {
    RestServiceEditorPane rse = new RestServiceEditorPane();
    rse.showDialog("Add a new Simple Bioinformatics Rest Service");
    String rservice = rse.getEditedRestService();
    if (rservice != null && !rsbsUrls.contains(rservice))
    {
      rsbsUrls.add(rservice);
      update++;
      updateRsbsList();
    }
  }

  @Override
  protected void editSbrsUrl_actionPerformed(ActionEvent e)
  {
    int sel = sbrsList.getSelectedIndex();
    if (sel > -1)
    {
      RestServiceEditorPane rse = new RestServiceEditorPane(
              new RestServiceDescription(rsbsUrls.elementAt(sel)));
      rse.showDialog("Edit Simple Bioinformatics Rest Service entry");
      String rservice = rse.getEditedRestService();
      if (rservice != null)
      {
        int present = rsbsUrls.indexOf(rservice);
        if (present == -1)
        {
          update++;
          rsbsUrls.setElementAt(rservice, sel);
          updateRsbsList();
        }
        else
        {
          if (present != sel)
          {
            rsbsUrls.removeElementAt(sel);
            update++;
            updateRsbsList();
          }
        }
      }
    }
  }

  void updateWsMenuConfig(boolean old)
  {
    if (old)
    {
      if (oldUrls != wsUrls
              || (wsUrls != null && oldUrls != null && !wsUrls
                      .equals(oldUrls)))
      {
        update++;
      }
      wsUrls = (oldUrls == null) ? null : new Vector(oldUrls);
      if (oldRsbsUrls != rsbsUrls
              || (rsbsUrls != null && oldRsbsUrls != null && !oldRsbsUrls
                      .equals(rsbsUrls)))
      {
        update++;
      }
      oldRsbsUrls = (oldRsbsUrls == null) ? null : new Vector(oldRsbsUrls);
    }
    else
    {

    }
    Cache.setProperty(
            "SHOW_ENFIN_SERVICES",
            Boolean.valueOf(
                    old ? oldEnfin : enableEnfinServices.isSelected())
                    .toString());
    Cache.setProperty(
            "SHOW_JWS2_SERVICES",
            Boolean.valueOf(old ? oldJws2 : enableJws2Services.isSelected())
                    .toString());
    Cache.setProperty(
            "WSMENU_BYHOST",
            Boolean.valueOf(old ? oldIndexByHost : indexByHost.isSelected())
                    .toString());
    Cache.setProperty(
            "WSMENU_BYTYPE",
            Boolean.valueOf(old ? oldIndexByType : indexByType.isSelected())
                    .toString());

    Cache.setProperty(
            "SHOW_WSDISCOVERY_ERRORS",
            Boolean.valueOf(
                    old ? oldWsWarning : displayWsWarning.isSelected())
                    .toString());
    updateServiceList();
    updateRsbsServiceList();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GWsPreferences#moveWsUrlDown_actionPerformed(java.awt.event
   * .ActionEvent)
   */
  @Override
  protected void moveWsUrlDown_actionPerformed(ActionEvent e)
  {
    int p = wsList.getSelectedRow();
    if (p > -1 && p < wsUrls.size() - 1)
    {
      String t = wsUrls.get(p + 1);
      wsUrls.setElementAt(wsUrls.elementAt(p), p + 1);
      wsUrls.setElementAt(t, p);
      updateList();
      wsList.getSelectionModel().setSelectionInterval(p + 1, p + 1);
      update++;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GWsPreferences#moveWsUrlUp_actionPerformed(java.awt.event
   * .ActionEvent)
   */
  @Override
  protected void moveWsUrlUp_actionPerformed(ActionEvent e)
  {
    int p = wsList.getSelectedRow();
    if (p > 0)
    {
      String t = wsUrls.get(p - 1);
      wsUrls.setElementAt(wsUrls.elementAt(p), p - 1);
      wsUrls.setElementAt(t, p);
      updateList();
      wsList.getSelectionModel().setSelectionInterval(p - 1, p - 1);
      update++;
    }
  }

  private String editUrl(String initUrl, String title)
  {
    String url = initUrl;
    URL foo = null;
    if (url == null)
    {
      url = "";
    }
    JTextField urltf = new JTextField(url, 40);
    JPanel panel = new JPanel(new BorderLayout());
    JPanel pane12 = new JPanel(new BorderLayout());
    pane12.add(new JLabel("URL: "), BorderLayout.CENTER);
    pane12.add(urltf, BorderLayout.EAST);
    panel.add(pane12, BorderLayout.NORTH);
    boolean valid = false;
    int resp = JOptionPane.CANCEL_OPTION;
    while (!valid
            && (resp = JOptionPane.showInternalConfirmDialog(
                    Desktop.desktop, panel, title,
                    JOptionPane.OK_CANCEL_OPTION)) == JOptionPane.OK_OPTION)
    {
      try
      {
        // TODO: do a better job of checking that the url is a valid discovery
        // URL for web services.
        String tx = urltf.getText().trim();
        while (tx.length() > 0 && tx.lastIndexOf('/') == tx.length() - 1)
        {
          tx = tx.substring(0, tx.length() - 1);
        }
        foo = new URL(tx);
        valid = true;
        urltf.setText(tx);
      } catch (Exception e)
      {
        valid = false;
        JOptionPane.showInternalMessageDialog(Desktop.desktop,
                "Invalid URL !");
      }
    }
    if (valid && resp == JOptionPane.OK_OPTION)
    {
      int validate = JOptionPane
              .showInternalConfirmDialog(
                      Desktop.desktop,
                      "Validate JabaWS Server ?\n(Look in console output for results)",
                      "Test Server?", JOptionPane.YES_NO_OPTION);
      if (validate == JOptionPane.OK_OPTION)
      {
        if (jalview.ws.jws2.Jws2Discoverer.testServiceUrl(foo))
        {
          return foo.toString();
        }
        else
        {
          JOptionPane
                  .showInternalMessageDialog(
                          Desktop.desktop,
                          "Service did not pass validation.\nCheck the Jalview Console for more details.");
        }
      }
      else
      {
        // just return the URL anyway
        return foo.toString();
      }
    }
    return initUrl;
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.jbgui.GWsPreferences#newWsUrl_actionPerformed(java.awt.event.
   * ActionEvent)
   */
  @Override
  protected void newWsUrl_actionPerformed(ActionEvent e)
  {
    String url = editUrl(null, "Add new JABAWS URL");
    if (url != null)
    {
      if (!wsUrls.contains(url))
      {
        int selind = wsList.getSelectedRow();
        if (selind > -1)
        {
          wsUrls.insertElementAt(url, selind);
        }
        else
        {
          wsUrls.addElement(url);
        }
        update++;
        updateList();
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see jalview.jbgui.GWsPreferences#refreshWs_actionPerformed(java.awt.event.
   * ActionEvent)
   */
  @Override
  protected void refreshWs_actionPerformed(ActionEvent e)
  {
    new Thread(new Runnable()
    {

      public void run()
      {
        // force a refresh.
        lastrefresh = update - 1;
        updateWsMenuConfig(false);
        refreshWsMenu(true);
      }
    }).start();

  }

  /**
   * Refresh the web services menus - but only if there has been a change in the
   * configuration (indicated by update!=lastrefresh)
   * 
   * @param showProgress
   *          show progress in dialog or on desktop
   */
  protected void refreshWsMenu(boolean showProgress)
  {
    if (showProgress)
    {
      new Thread(new Runnable()
      {

        public void run()
        {
          progressBar.setVisible(true);
          validate();
          progressBar.setIndeterminate(true);
          if (lastrefresh != update)
          {
            lastrefresh = update;
            Desktop.instance.startServiceDiscovery(true); // wait around for all
                                                          // threads to complete
            updateList();

          }
          progressBar.setIndeterminate(false);
          progressBar.setVisible(false);
          validate();
        }
      }).start();

    }
    else
    {
      new Thread(new Runnable()
      {

        public void run()
        {
          long ct = System.currentTimeMillis();
          Desktop.instance.setProgressBar("Refreshing Web Service Menus",
                  ct);
          if (lastrefresh != update)
          {
            lastrefresh = update;
            Desktop.instance.startServiceDiscovery(true);
            updateList();
          }
          Desktop.instance.setProgressBar(null, ct);
        }

      }).start();
    }
  }

  /**
   * state counters for ensuring that updates only happen if config has changed.
   */
  private long update = 0, lastrefresh = 0;

  /*
   * (non-Javadoc)
   * 
   * @see
   * jalview.jbgui.GWsPreferences#resetWs_actionPerformed(java.awt.event.ActionEvent
   * )
   */
  @Override
  protected void resetWs_actionPerformed(ActionEvent e)
  {
    jalview.ws.jws2.Jws2Discoverer.setServiceUrls(null);
    Vector nwsUrls = jalview.ws.jws2.Jws2Discoverer.getServiceUrls();
    if (!wsUrls.equals(nwsUrls))
    {
      update++;
    }
    wsUrls = nwsUrls;
    updateList();

    updateAndRefreshWsMenuConfig(true);
  }

  protected void ok_ActionPerformed(ActionEvent e)
  {
    // update everything regardless.
    updateAndRefreshWsMenuConfig(false);
  }

  public void updateAndRefreshWsMenuConfig(
          final boolean showProgressInDialog)
  {
    new Thread(new Runnable()
    {

      public void run()
      {
        updateWsMenuConfig(false);
        refreshWsMenu(showProgressInDialog);
      }
    }).start();

  }
}
