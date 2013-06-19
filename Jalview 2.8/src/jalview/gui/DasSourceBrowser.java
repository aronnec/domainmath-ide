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

import jalview.jbgui.GDasSourceBrowser;
import jalview.util.TableSorter;
import jalview.ws.dbsources.das.api.DasSourceRegistryI;
import jalview.ws.dbsources.das.api.jalviewSourceI;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import org.biodas.jdas.schema.sources.CAPABILITY;
import org.biodas.jdas.schema.sources.COORDINATES;
import org.biodas.jdas.schema.sources.PROP;
import org.biodas.jdas.schema.sources.VERSION;

public class DasSourceBrowser extends GDasSourceBrowser implements
        Runnable, ListSelectionListener
{
  DasSourceRegistryI sourceRegistry = null;

  Vector<String> selectedSources;

  public DasSourceBrowser(FeatureSettings featureSettings)
  {
    fs = featureSettings;
    // TODO DasSourceRegistryProvider API
    sourceRegistry = jalview.bin.Cache.getDasSourceRegistry();
    String registry = sourceRegistry.getDasRegistryURL();

    registryURL.setText(registry);

    setSelectedFromProperties();

    displayFullDetails(null);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    filter1.addListSelectionListener(this);
    filter2.addListSelectionListener(this);
    filter3.addListSelectionListener(this);

    // Ask to be notified of selection changes.
    ListSelectionModel rowSM = table.getSelectionModel();
    rowSM.addListSelectionListener(new ListSelectionListener()
    {
      public void valueChanged(ListSelectionEvent e)
      {
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        if (!lsm.isSelectionEmpty())
        {
          int selectedRow = lsm.getMinSelectionIndex();
          displayFullDetails(table.getValueAt(selectedRow, 0).toString());
        }
      }
    });

    table.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent evt)
      {
        if (evt.getClickCount() == 2
                || SwingUtilities.isRightMouseButton(evt))
        {
          editRemoveLocalSource(evt);
        }
      }
    });

    if (sourceRegistry.getSources() != null)
    {
      init();
    }
  }

  FeatureSettings fs = null;

  private boolean loadingDasSources;

  public DasSourceBrowser()
  {
    this(null);
  }

  public void paintComponent(java.awt.Graphics g)
  {
    if (sourceRegistry == null)
    {
      Thread worker = new Thread(this);
      worker.start();
    }
  }

  void init()
  {
    List<jalviewSourceI> sources = sourceRegistry.getSources();
    int dSize = sources.size();
    Object[][] data = new Object[dSize][2];
    for (int i = 0; i < dSize; i++)
    {
      data[i][0] = sources.get(i).getTitle(); // what's equivalent of nickname
      data[i][1] = new Boolean(selectedSources.contains(sources.get(i)
              .getTitle()));
    }

    refreshTableData(data);
    setCapabilities(sourceRegistry);

    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        TableSorter sorter = (TableSorter) table.getModel();
        sorter.setSortingStatus(1, TableSorter.DESCENDING);
        sorter.setSortingStatus(1, TableSorter.NOT_SORTED);
      }
    });

    progressBar.setIndeterminate(false);
    progressBar.setVisible(false);
    addLocal.setVisible(true);
    refresh.setVisible(true);
  }

  public void refreshTableData(Object[][] data)
  {
    TableSorter sorter = new TableSorter(new DASTableModel(data));
    sorter.setTableHeader(table.getTableHeader());
    table.setModel(sorter);
  }

  void displayFullDetails(String nickName)
  {

    StringBuffer text = new StringBuffer(
            "<HTML><font size=\"2\" face=\"Verdana, Arial, Helvetica, sans-serif\">");

    if (nickName == null)
    {
      fullDetails.setText(text + "Select a DAS service from the table"
              + " to read a full description here.</font></html>");
      return;
    }

    int dSize = sourceRegistry.getSources().size();
    for (jalviewSourceI ds : sourceRegistry.getSources())
    {
      if (!ds.getTitle().equals(nickName))
      {
        continue;
      }

      VERSION latest = ds.getVersion();
      text.append("<font color=\"#0000FF\">Id:</font> " + ds.getUri()
              + "<br>");
      text.append("<font color=\"#0000FF\">Nickname:</font> "
              + ds.getTitle() + "<br>");

      text.append("<font color=\"#0000FF\">URL:</font> <a href=\""
              + ds.getSourceURL() + "\">" + ds.getSourceURL() + "</a>"
              + "<br>");
      if (!ds.isLocal())
      {
        if (ds.getDocHref() != null && ds.getDocHref().length() > 0)
        {
          text.append("<font color=\"#0000FF\">Site:</font> <a href=\""
                  + ds.getDocHref() + "\">" + ds.getDocHref() + "</a>"
                  + "<br>");
        }

        text.append("<font color=\"#0000FF\">Description:</font> "
                + ds.getDescription() + "<br>");

        text.append("<font color=\"#0000FF\">Admin Email:</font> <a href=\"mailto:"
                + ds.getEmail() + "\">" + ds.getEmail() + "</a>" + "<br>");

        text.append("<font color=\"#0000FF\">Registered at:</font> "
                + latest.getCreated() + "<br>");

        // TODO: Identify last successful test date
        // text.append("<font color=\"#0000FF\">Last successful test:</font> "
        // + latest.dasSources[i].getLeaseDate() + "<br>");
      }
      else
      {
        text.append("Source was added manually.<br/>");
      }
      text.append("<font color=\"#0000FF\">Labels:</font> ");
      boolean b = false;
      for (PROP labl : latest.getPROP())
      {
        if (labl.getName().equalsIgnoreCase("LABEL"))
        {
          if (b)
          {
            text.append(",");
          }
          text.append(" ");

          text.append(labl.getValue());
          b = true;
        }
        ;
      }
      text.append("<br>");

      text.append("<font color=\"#0000FF\">Capabilities:</font> ");
      CAPABILITY[] scap = latest.getCAPABILITY().toArray(new CAPABILITY[0]);
      for (int j = 0; j < scap.length; j++)
      {
        text.append(scap[j].getType());
        if (j < scap.length - 1)
        {
          text.append(", ");
        }
      }
      text.append("<br>");

      text.append("<font color=\"#0000FF\">Coordinates:</font>");
      int i = 1;
      for (COORDINATES dcs : latest.getCOORDINATES())
      {
        text.append("<br/>" + i++ + ". ");
        text.append(dcs.getAuthority() + " : " + dcs.getSource());
        if (dcs.getTaxid() != null && dcs.getTaxid().trim().length() > 0)
        {
          text.append(" [TaxId:" + dcs.getTaxid() + "]");
        }
        if (dcs.getVersion() != null
                && dcs.getVersion().trim().length() > 0)
        {
          {
            text.append(" {v. " + dcs.getVersion() + "}");
          }
        }
        text.append(" (<a href=\"" + dcs.getUri() + "\">" + dcs.getUri()
                + "</a>)");
      }
      text.append("</font></html>");

      break;
    }

    fullDetails.setText(text.toString());
    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        fullDetailsScrollpane.getVerticalScrollBar().setValue(0);
      }
    });
  }

  public void run()
  {
    loadingDasSources = true;

    addLocal.setVisible(false);
    refresh.setVisible(false);
    progressBar.setVisible(true);
    progressBar.setIndeterminate(true);
    setParentGuiEnabled(false);
    // Refresh the source list.
    sourceRegistry.refreshSources();

    init();

    setParentGuiEnabled(true);
    loadingDasSources = false;

  }

  private void setParentGuiEnabled(boolean b)
  {
    if (fs != null)
    {
      fs.fetchDAS.setEnabled(b);
      fs.saveDAS.setEnabled(b);
    }
  }

  public Vector<jalviewSourceI> getSelectedSources()
  {
    // wait around if we're still loading.
    while (sourceRegistry == null)
    {
      if (!loadingDasSources)
      {
        new Thread(this).start();
        try
        {
          Thread.sleep(5);
        } catch (Exception e)
        {
        }
        ;
        while (loadingDasSources)
        {
          try
          {
            Thread.sleep(5);
          } catch (Exception e)
          {
          }
          ;
        }
        ;
      }
    }

    Vector<jalviewSourceI> selected = new Vector<jalviewSourceI>();
    for (String source : selectedSources)
    {
      jalviewSourceI srce = sourceRegistry.getSource(source);
      if (srce != null)
      {
        selected.addElement(srce);
      }
    }
    return selected;
  }

  public void refresh_actionPerformed(ActionEvent e)
  {
    saveProperties(jalview.bin.Cache.applicationProperties);

    Thread worker = new Thread(this);
    worker.start();
  }

  private void setCapabilities(DasSourceRegistryI sourceRegistry2)
  {
    Vector<String> authority = new Vector<String>();
    Vector<String> type = new Vector<String>();
    Vector<String> label = new Vector<String>();
    Vector<String> taxIds = new Vector<String>();
    authority.add("Any");
    type.add("Any");
    label.add("Any");

    for (jalviewSourceI ds : sourceRegistry2.getSources())
    {
      VERSION latest = ds.getVersion();

      for (COORDINATES cs : latest.getCOORDINATES())
      {
        if (!type.contains(cs.getSource()))
        {
          type.add(cs.getSource()); // source==category
        }

        if (!authority.contains(cs.getAuthority()))
        {
          authority.add(cs.getAuthority());
        }
      }

      for (PROP slabel : latest.getPROP())
      {
        if (slabel.getName().equalsIgnoreCase("LABEL")
                && !label.contains(slabel.getValue()))
        {
          label.add(slabel.getValue());
        }
      }

    }

    filter1.setListData(authority);
    filter2.setListData(type);
    filter3.setListData(label);
    // filter4 taxIds

    javax.swing.SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        filter1.setSelectedIndex(0);
        filter2.setSelectedIndex(0);
        filter3.setSelectedIndex(0);
      }
    });
  }

  public void amendLocal(boolean newSource)
  {
    String url = "http://localhost:8080/", nickname = "";
    boolean seqsrc = false;
    if (!newSource)
    {
      int selectedRow = table.getSelectionModel().getMinSelectionIndex();
      nickname = table.getValueAt(selectedRow, 0).toString();
      jalviewSourceI source = sourceRegistry.getSource(nickname);
      url = source.getUri();
      seqsrc = source.isSequenceSource();
    }

    JTextField nametf = new JTextField(nickname, 40);
    JTextField urltf = new JTextField(url, 40);
    JCheckBox seqs = new JCheckBox("Sequence Source");
    seqs.setSelected(seqsrc);
    JPanel panel = new JPanel(new BorderLayout());
    JPanel pane12 = new JPanel(new BorderLayout());
    pane12.add(new JLabel("Nickname: "), BorderLayout.CENTER);
    pane12.add(nametf, BorderLayout.EAST);
    panel.add(pane12, BorderLayout.NORTH);
    pane12 = new JPanel(new BorderLayout());
    pane12.add(new JLabel("URL: "), BorderLayout.NORTH);
    pane12.add(seqs, BorderLayout.SOUTH);
    pane12.add(urltf, BorderLayout.EAST);
    panel.add(pane12, BorderLayout.SOUTH);

    int reply = JOptionPane.showInternalConfirmDialog(Desktop.desktop,
            panel, "Enter Nickname & URL of Local DAS Source",
            JOptionPane.OK_CANCEL_OPTION);

    if (reply != JOptionPane.OK_OPTION)
    {
      return;
    }

    if (!urltf.getText().endsWith("/"))
    {
      urltf.setText(urltf.getText() + "/");
    }

    jalviewSourceI local = sourceRegistry.createLocalSource(
            urltf.getText(), nametf.getText(), seqs.isSelected(), true);
    List sources = sourceRegistry.getSources();
    int osize = sources.size();
    int size = osize + (newSource ? 1 : 0);

    Object[][] data = new Object[size][2];
    DASTableModel dtm = (table != null) ? (DASTableModel) ((TableSorter) table
            .getModel()).getTableModel() : null;
    for (int i = 0; i < osize; i++)
    {
      String osrc = (dtm == null || i >= osize) ? null : (String) dtm
              .getValueAt(i, 0);
      if (!newSource && osrc != null
              && dtm.getValueAt(i, 0).equals(nickname))
      {
        data[i][0] = local.getTitle();
        data[i][1] = new Boolean(true);
      }
      else
      {
        data[i][0] = osrc;
        data[i][1] = new Boolean(selectedSources.contains(osrc));
      }
    }
    // Always add a new source at the end
    if (newSource)
    {
      data[osize][0] = local.getTitle();
      data[osize][1] = new Boolean(true);
      selectedSources.add(local.getTitle());
    }

    refreshTableData(data);

    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        scrollPane.getVerticalScrollBar().setValue(
                scrollPane.getVerticalScrollBar().getMaximum());
      }
    });

    displayFullDetails(local.getTitle());
  }

  public void editRemoveLocalSource(MouseEvent evt)
  {
    int selectedRow = table.getSelectionModel().getMinSelectionIndex();
    if (selectedRow == -1)
    {
      return;
    }

    String nickname = table.getValueAt(selectedRow, 0).toString();

    if (!sourceRegistry.getSource(nickname).isLocal())
    {
      JOptionPane.showInternalMessageDialog(Desktop.desktop,
              "You can only edit or remove local DAS Sources!",
              "Public DAS source - not editable",
              JOptionPane.WARNING_MESSAGE);
      return;
    }

    Object[] options =
    { "Edit", "Remove", "Cancel" };
    int choice = JOptionPane.showInternalOptionDialog(Desktop.desktop,
            "Do you want to edit or remove " + nickname + "?",
            "Edit / Remove Local DAS Source",
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[2]);

    switch (choice)
    {
    case 0:
      amendLocal(false);
      break;
    case 1:
      sourceRegistry.removeLocalSource(sourceRegistry.getSource(nickname));
      selectedSources.remove(nickname);
      Object[][] data = new Object[sourceRegistry.getSources().size()][2];
      int index = 0,
      l = table.getRowCount();

      for (int i = 0; i < l; i++)
      {
        String nm;
        if ((nm = (String) table.getValueAt(i, 0)).equals(nickname))
        {
          continue;
        }
        else
        {
          data[index][0] = nm;
          data[index][1] = new Boolean(selectedSources.contains(nm));
          index++;
        }
      }
      refreshTableData(data);
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
          scrollPane.getVerticalScrollBar().setValue(
                  scrollPane.getVerticalScrollBar().getMaximum());
        }
      });

      break;
    }
  }

  public void valueChanged(ListSelectionEvent evt)
  {
    // Called when the MainTable selection changes
    if (evt.getValueIsAdjusting())
    {
      return;
    }

    displayFullDetails(null);

    // Filter the displayed data sources

    ArrayList names = new ArrayList();
    ArrayList selected = new ArrayList();

    // The features filter is not visible, but we must still
    // filter the das source list here.
    // July 2006 - only 6 sources fo not serve features
    Object[] dummyFeatureList = new Object[]
    { "features" };
    List<jalviewSourceI> srcs = sourceRegistry.getSources();
    for (jalviewSourceI ds : srcs)
    {

      VERSION v = ds.getVersion();
      List<COORDINATES> coords = v.getCOORDINATES();
      if (ds.isLocal()
              || ((coords == null || coords.size() == 0)
                      && filter1.getSelectedIndex() == 0
                      && filter2.getSelectedIndex() == 0 && filter3
                      .getSelectedIndex() == 0))
      {
        // THIS IS A FIX FOR LOCAL SOURCES WHICH DO NOT
        // HAVE COORDINATE SYSTEMS, INFO WHICH AT PRESENT
        // IS ADDED FROM THE REGISTRY
        names.add(ds.getTitle());
        selected.add(new Boolean(selectedSources.contains(ds.getTitle())));
        continue;
      }

      if (!selectedInList(dummyFeatureList, ds.getCapabilityList(v))
              || !selectedInList(filter3.getSelectedValues(),
                      ds.getLabelsFor(v)))
      {
        continue;
      }

      for (int j = 0; j < coords.size(); j++)
      {
        if (selectedInList(filter1.getSelectedValues(), new String[]
        { coords.get(j).getAuthority() })
                && selectedInList(filter2.getSelectedValues(), new String[]
                { coords.get(j).getSource() }))
        {
          names.add(ds.getTitle());
          selected.add(new Boolean(selectedSources.contains(ds.getTitle())));
          break;
        }
      }
    }

    int dSize = names.size();
    Object[][] data = new Object[dSize][2];
    for (int d = 0; d < dSize; d++)
    {
      data[d][0] = names.get(d);
      data[d][1] = selected.get(d);
    }

    refreshTableData(data);
  }

  private boolean selectedInList(Object[] selection, String[] items)
  {
    for (int i = 0; i < selection.length; i++)
    {
      if (selection[i].equals("Any"))
      {
        return true;
      }
      if (items == null || items.length == 0)
      {
        return false;
      }
      String sel = (items[0].startsWith("das1:") ? "das1:" : "")
              + selection[i];
      for (int j = 0; j < items.length; j++)
      {
        if (sel.equals(items[j]))
        {
          return true;
        }
      }
    }

    return false;
  }

  void setSelectedFromProperties()
  {
    String active = jalview.bin.Cache.getDefault("DAS_ACTIVE_SOURCE",
            "uniprot");
    StringTokenizer st = new StringTokenizer(active, "\t");
    selectedSources = new Vector();
    while (st.hasMoreTokens())
    {
      selectedSources.addElement(st.nextToken());
    }
  }

  public void reset_actionPerformed(ActionEvent e)
  {
    registryURL.setText(sourceRegistry.getDasRegistryURL());
  }

  /**
   * set the DAS source settings in the given jalview properties.
   * 
   * @param properties
   */
  public void saveProperties(Properties properties)
  {
    if (registryURL.getText() == null || registryURL.getText().length() < 1)
    {
      properties.remove(jalview.bin.Cache.DAS_REGISTRY_URL);
    }
    else
    {
      properties.setProperty(jalview.bin.Cache.DAS_REGISTRY_URL,
              registryURL.getText());
    }

    StringBuffer sb = new StringBuffer();
    for (int r = 0; r < table.getModel().getRowCount(); r++)
    {
      if (((Boolean) table.getValueAt(r, 1)).booleanValue())
      {
        sb.append(table.getValueAt(r, 0) + "\t");
      }
    }

    properties.setProperty(jalview.bin.Cache.DAS_ACTIVE_SOURCE,
            sb.toString());

    String sourceprop = sourceRegistry.getLocalSourceString();
    properties.setProperty(jalview.bin.Cache.DAS_LOCAL_SOURCE, sourceprop);
  }

  class DASTableModel extends AbstractTableModel
  {

    public DASTableModel(Object[][] data)
    {
      this.data = data;
    }

    private String[] columnNames = new String[]
    { "Nickname", "Use Source" };

    private Object[][] data;

    public int getColumnCount()
    {
      return columnNames.length;
    }

    public int getRowCount()
    {
      return data.length;
    }

    public String getColumnName(int col)
    {
      return columnNames[col];
    }

    public Object getValueAt(int row, int col)
    {
      return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/ editor for
     * each cell. If we didn't implement this method, then the last column would
     * contain text ("true"/"false"), rather than a check box.
     */
    public Class getColumnClass(int c)
    {
      return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's editable.
     */
    public boolean isCellEditable(int row, int col)
    {
      // Note that the data/cell address is constant,
      // no matter where the cell appears onscreen.
      return col == 1;

    }

    /*
     * Don't need to implement this method unless your table's data can change.
     */
    public void setValueAt(Object value, int row, int col)
    {
      data[row][col] = value;
      fireTableCellUpdated(row, col);

      String name = getValueAt(row, 0).toString();
      boolean selected = ((Boolean) value).booleanValue();

      if (selectedSources.contains(name) && !selected)
      {
        selectedSources.remove(name);
      }

      if (!selectedSources.contains(name) && selected)
      {
        selectedSources.add(name);
      }
    }
  }

  public void initDasSources()
  {

    Thread thr = new Thread(new Runnable()
    {
      public void run()
      {
        // this actually initialises the das source list
        paintComponent(null); // yuk
      }
    });
    thr.start();
    while (loadingDasSources || sourceRegistry == null)
    {
      try
      {
        Thread.sleep(10);
      } catch (Exception e)
      {
      }
      ;
    }
  }

  /**
   * disable or enable the buttons on the source browser
   * 
   * @param b
   */
  public void setGuiEnabled(boolean b)
  {
    refresh.setEnabled(b);
    addLocal.setEnabled(b);
  }
}
