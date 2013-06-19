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
package jalview.jbgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

/**
 * Preference dialog for jalview web services
 * 
 * @author JimP
 */
public class GWsPreferences extends JPanel
{
  protected JList sbrsList = new JList();

  protected TitledBorder sbrsListTitleBorder = new TitledBorder(
          "Simple Bioinformatics Rest Services");

  protected JButton newSbrsUrl = new JButton();

  protected JButton editSbrsUrl = new JButton();

  protected JButton deleteSbrsUrl = new JButton();

  // Web service status and url table
  protected JTable wsList = new JTable();

  protected TitledBorder wsListTitleBorder = new TitledBorder(
          "Web Service Discovery URLS");

  protected JButton newWsUrl = new JButton();

  protected JButton editWsUrl = new JButton();

  protected JButton deleteWsUrl = new JButton();

  protected JButton moveWsUrlUp = new JButton();

  protected JButton moveWsUrlDown = new JButton();

  protected JCheckBox indexByHost = new JCheckBox();

  protected JCheckBox indexByType = new JCheckBox();

  protected JCheckBox enableJws2Services = new JCheckBox();

  protected JCheckBox enableEnfinServices = new JCheckBox();

  protected JCheckBox displayWsWarning = new JCheckBox();

  protected JButton refreshWs = new JButton();

  protected JButton resetWs = new JButton();

  protected JProgressBar progressBar = new JProgressBar();

  JScrollPane wsListPane = new JScrollPane();

  JPanel wsListUrlPanel = new JPanel();

  JPanel wsListPanel = new JPanel();

  JPanel wsListButtons = new JPanel();

  JPanel wsListNavButs = new JPanel();

  JScrollPane srbsListPane = new JScrollPane();

  JPanel srbsListUrlPanel = new JPanel();

  JPanel srbsListPanel = new JPanel();

  JPanel srbsListButtons = new JPanel();

  JPanel srbsListNavButs = new JPanel();

  BorderLayout myBorderlayout = new BorderLayout();

  BorderLayout wsListBorderlayout = new BorderLayout();

  BorderLayout srbsListBorderlayout = new BorderLayout();

  GridBagLayout wsPrefLayout = new GridBagLayout();

  GridBagLayout wsListLayout = new GridBagLayout();

  GridBagLayout srbsListLayout = new GridBagLayout();

  GridBagLayout wsMenuLayout = new GridBagLayout();

  JPanel wsMenuButtons = new JPanel();

  JPanel wsMenuRefreshButs = new JPanel();

  public GWsPreferences()
  {
    jbInit();
  }

  private void jbInit()
  {

    refreshWs.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    refreshWs.setText("Refresh Services");
    refreshWs.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        refreshWs_actionPerformed(e);
      }
    });
    resetWs.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    resetWs.setText("Reset Services");

    resetWs.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        resetWs_actionPerformed(e);
      }
    });
    indexByHost.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    indexByHost.setText("Index by host");
    indexByHost
            .setToolTipText("Index web services in menu by the host site.");
    indexByHost.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        indexByHost_actionPerformed(e);
      }
    });
    indexByType.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    indexByType.setText("Index by type");
    indexByType.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        indexByType_actionPerformed(e);
      }
    });
    enableEnfinServices
            .setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    enableEnfinServices.setText("Enable Enfin Services");
    enableEnfinServices.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        enableEnfinServices_actionPerformed(e);
      }
    });
    enableJws2Services
            .setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    enableJws2Services.setText("Enable JABAWS Services");
    enableJws2Services.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        enableJws2Services_actionPerformed(e);
      }
    });
    displayWsWarning.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    displayWsWarning.setText("Display warnings");
    displayWsWarning
            .setToolTipText("<html>Check this option if you want to be informed<br>when a web service URL cannot be accessed by Jalview<br>when it starts up");
    displayWsWarning.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        displayWsWarning_actionPerformed(e);
      }
    });
    newWsUrl.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    newWsUrl.setText("New Service URL");
    newWsUrl.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        newWsUrl_actionPerformed(e);
      }
    });
    editWsUrl.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    editWsUrl.setText("Edit Service URL");
    editWsUrl.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        editWsUrl_actionPerformed(e);
      }
    });

    deleteWsUrl.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    deleteWsUrl.setText("Delete Service URL");
    deleteWsUrl.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        deleteWsUrl_actionPerformed(e);
      }
    });
    moveWsUrlUp.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    moveWsUrlUp.setText("Up");
    moveWsUrlUp.setToolTipText("Move URL up");
    moveWsUrlUp.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        moveWsUrlUp_actionPerformed(e);
      }
    });
    moveWsUrlDown.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    moveWsUrlDown.setText("Down");
    moveWsUrlDown.setToolTipText("Move URL Down");
    moveWsUrlDown.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        moveWsUrlDown_actionPerformed(e);
      }
    });
    newSbrsUrl.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    newSbrsUrl.setText("Add a SBRS definition");
    newSbrsUrl.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        newSbrsUrl_actionPerformed(e);
      }
    });
    editSbrsUrl.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    editSbrsUrl.setText("Edit SBRS definition");
    editSbrsUrl.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        editSbrsUrl_actionPerformed(e);
      }
    });

    deleteSbrsUrl.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    deleteSbrsUrl.setText("Delete SBRS definition");
    deleteSbrsUrl.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        deleteSbrsUrl_actionPerformed(e);
      }
    });

    setLayout(myBorderlayout);
    setPreferredSize(new Dimension(500, 400));
    progressBar.setPreferredSize(new Dimension(450, 20));
    progressBar.setString("");
    wsListUrlPanel.setBorder(BorderFactory.createEtchedBorder());
    wsListUrlPanel.setLayout(new BorderLayout());
    wsListPane.setBorder(BorderFactory.createEtchedBorder());
    wsListPane.getViewport().add(wsList);
    wsList.setPreferredSize(new Dimension(482, 202));
    wsListPane.setPreferredSize(new Dimension(380, 80));
    wsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    wsList.setColumnSelectionAllowed(false);
    wsList.addMouseListener(new MouseListener()
    {

      public void mouseClicked(MouseEvent e)
      {
        if (e.getClickCount() > 1)
        {
          editWsUrl_actionPerformed(null);
        }

      }

      public void mouseEntered(MouseEvent e)
      {

      }

      public void mouseExited(MouseEvent e)
      {
      }

      public void mousePressed(MouseEvent e)
      {

      }

      public void mouseReleased(MouseEvent e)
      {

      }

    });
    wsListButtons.setLayout(new FlowLayout());
    wsListButtons.add(newWsUrl);
    wsListButtons.add(editWsUrl);
    wsListButtons.add(deleteWsUrl);
    wsListButtons.setMinimumSize(new Dimension(350, 80));
    wsListNavButs.setSize(new Dimension(80, 80));
    wsListNavButs.setPreferredSize(new Dimension(80, 80));
    wsListNavButs.setLayout(new FlowLayout());
    wsListNavButs.add(moveWsUrlUp);
    wsListNavButs.add(moveWsUrlDown);
    wsListUrlPanel.add(wsListPane, BorderLayout.CENTER);
    wsListUrlPanel.add(wsListNavButs, BorderLayout.WEST);
    wsListPanel.setBorder(wsListTitleBorder);
    wsListPanel.setLayout(new BorderLayout());
    wsListPanel.add(wsListUrlPanel, BorderLayout.NORTH);
    wsListPanel.add(wsListButtons, BorderLayout.SOUTH);

    srbsListUrlPanel.setBorder(BorderFactory.createEtchedBorder());
    srbsListUrlPanel.setLayout(new BorderLayout());
    srbsListPane.setBorder(BorderFactory.createEtchedBorder());
    srbsListPane.getViewport().add(sbrsList);
    sbrsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    sbrsList.addMouseListener(new MouseListener()
    {

      public void mouseClicked(MouseEvent e)
      {
        if (e.getClickCount() > 1)
        {
          editSbrsUrl_actionPerformed(null);
        }

      }

      public void mouseEntered(MouseEvent e)
      {

      }

      public void mouseExited(MouseEvent e)
      {
      }

      public void mousePressed(MouseEvent e)
      {

      }

      public void mouseReleased(MouseEvent e)
      {

      }

    });
    srbsListButtons.setLayout(new FlowLayout());
    srbsListButtons.add(newSbrsUrl);
    srbsListButtons.add(editSbrsUrl);
    srbsListButtons.add(deleteSbrsUrl);
    srbsListUrlPanel.add(srbsListPane, BorderLayout.CENTER);
    srbsListPanel.setBorder(sbrsListTitleBorder);
    srbsListPanel.setLayout(new BorderLayout());
    srbsListPanel.add(srbsListUrlPanel, BorderLayout.NORTH);
    srbsListPanel.add(srbsListButtons, BorderLayout.CENTER);

    wsMenuButtons.setLayout(new GridLayout(2, 3));
    wsMenuButtons.add(indexByHost);
    wsMenuButtons.add(indexByType);
    wsMenuButtons.add(enableJws2Services);
    wsMenuButtons.add(enableEnfinServices);
    wsMenuButtons.add(displayWsWarning);
    wsMenuRefreshButs.setLayout(new FlowLayout());
    wsMenuRefreshButs.setPreferredSize(new Dimension(480, 30));
    wsMenuRefreshButs.setSize(new Dimension(480, 30));
    wsMenuRefreshButs.add(refreshWs, null);
    wsMenuRefreshButs.add(resetWs, null);
    wsMenuRefreshButs.add(progressBar, null);
    myBorderlayout.setHgap(3);
    if (jalview.bin.Cache.getDefault("ENABLE_RSBS_EDITOR", false))
    {
      JTabbedPane listPanels = new JTabbedPane();
      listPanels.addTab("JABAWS Servers", wsListPanel);
      listPanels.addTab("RSB Services", srbsListPanel);
      add(listPanels, BorderLayout.NORTH);
    }
    else
    {
      add(wsListPanel, BorderLayout.NORTH);
    }
    add(wsMenuButtons, BorderLayout.CENTER);
    add(wsMenuRefreshButs, BorderLayout.SOUTH);
  }

  protected void deleteSbrsUrl_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void editSbrsUrl_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void newSbrsUrl_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void displayWsWarning_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void resetWs_actionPerformed(ActionEvent e)
  {

  }

  protected void indexByType_actionPerformed(ActionEvent e)
  {

  }

  protected void indexByHost_actionPerformed(ActionEvent e)
  {

  }

  protected void newWsUrl_actionPerformed(ActionEvent e)
  {

  }

  protected void editWsUrl_actionPerformed(ActionEvent e)
  {

  }

  protected void deleteWsUrl_actionPerformed(ActionEvent e)
  {

  }

  protected void moveWsUrlUp_actionPerformed(ActionEvent e)
  {

  }

  protected void moveWsUrlDown_actionPerformed(ActionEvent e)
  {

  }

  protected void enableEnfinServices_actionPerformed(ActionEvent e)
  {

  }

  protected void enableJws2Services_actionPerformed(ActionEvent e)
  {

  }

  protected void refreshWs_actionPerformed(ActionEvent e)
  {

  }

}
