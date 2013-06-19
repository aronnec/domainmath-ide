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

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class GPCAPanel extends JInternalFrame
{
  JPanel jPanel2 = new JPanel();

  JLabel jLabel1 = new JLabel();

  JLabel jLabel2 = new JLabel();

  JLabel jLabel3 = new JLabel();

  protected JComboBox xCombobox = new JComboBox();

  protected JComboBox yCombobox = new JComboBox();

  protected JComboBox zCombobox = new JComboBox();

  protected JButton resetButton = new JButton();

  FlowLayout flowLayout1 = new FlowLayout();

  BorderLayout borderLayout1 = new BorderLayout();

  JMenuBar jMenuBar1 = new JMenuBar();

  JMenu fileMenu = new JMenu();

  JMenu saveMenu = new JMenu();

  JMenuItem eps = new JMenuItem();

  JMenuItem png = new JMenuItem();

  JMenuItem print = new JMenuItem();

  JMenuItem outputValues = new JMenuItem();

  JMenuItem outputPoints = new JMenuItem();

  JMenuItem outputProjPoints = new JMenuItem();

  protected JMenu viewMenu = new JMenu();

  protected JCheckBoxMenuItem showLabels = new JCheckBoxMenuItem();

  JMenuItem bgcolour = new JMenuItem();

  JMenuItem originalSeqData = new JMenuItem();

  protected JMenu associateViewsMenu = new JMenu();

  protected JMenu calcSettings = new JMenu();

  protected JCheckBoxMenuItem nuclSetting = new JCheckBoxMenuItem();

  protected JCheckBoxMenuItem protSetting = new JCheckBoxMenuItem();

  protected JCheckBoxMenuItem jvVersionSetting = new JCheckBoxMenuItem();

  protected JLabel statusBar = new JLabel();

  protected GridLayout statusPanelLayout = new GridLayout();

  protected JPanel statusPanel = new JPanel();

  public GPCAPanel()
  {
    try
    {
      jbInit();
    } catch (Exception e)
    {
      e.printStackTrace();
    }

    for (int i = 1; i < 8; i++)
    {
      xCombobox.addItem("dim " + i);
      yCombobox.addItem("dim " + i);
      zCombobox.addItem("dim " + i);
    }

    setJMenuBar(jMenuBar1);
  }

  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(borderLayout1);
    jPanel2.setLayout(flowLayout1);
    jLabel1.setFont(new java.awt.Font("Verdana", 0, 12));
    jLabel1.setText("x=");
    jLabel2.setFont(new java.awt.Font("Verdana", 0, 12));
    jLabel2.setText("y=");
    jLabel3.setFont(new java.awt.Font("Verdana", 0, 12));
    jLabel3.setText("z=");
    jPanel2.setBackground(Color.white);
    jPanel2.setBorder(null);
    zCombobox.setFont(new java.awt.Font("Verdana", 0, 12));
    zCombobox.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        zCombobox_actionPerformed(e);
      }
    });
    yCombobox.setFont(new java.awt.Font("Verdana", 0, 12));
    yCombobox.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        yCombobox_actionPerformed(e);
      }
    });
    xCombobox.setFont(new java.awt.Font("Verdana", 0, 12));
    xCombobox.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        xCombobox_actionPerformed(e);
      }
    });
    resetButton.setFont(new java.awt.Font("Verdana", 0, 12));
    resetButton.setText("Reset");
    resetButton.addActionListener(new java.awt.event.ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        resetButton_actionPerformed(e);
      }
    });
    fileMenu.setText("File");
    saveMenu.setText("Save as");
    eps.setText("EPS");
    eps.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        eps_actionPerformed(e);
      }
    });
    png.setText("PNG");
    png.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        png_actionPerformed(e);
      }
    });
    outputValues.setText("Output Values...");
    outputValues.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        outputValues_actionPerformed(e);
      }
    });
    outputPoints.setText("Output points...");
    outputPoints.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        outputPoints_actionPerformed(e);
      }
    });
    outputProjPoints.setText("Output transformed points...");
    outputProjPoints.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        outputProjPoints_actionPerformed(e);
      }
    });
    print.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        print_actionPerformed(e);
      }
    });
    viewMenu.setText("View");
    viewMenu.addMenuListener(new MenuListener()
    {
      public void menuSelected(MenuEvent e)
      {
        viewMenu_menuSelected();
      }

      public void menuDeselected(MenuEvent e)
      {
      }

      public void menuCanceled(MenuEvent e)
      {
      }
    });
    showLabels.setText("Show Labels");
    showLabels.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        showLabels_actionPerformed(e);
      }
    });
    print.setText("Print");
    bgcolour.setText("Background Colour...");
    bgcolour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        bgcolour_actionPerformed(e);
      }
    });
    originalSeqData.setText("Input Data...");
    originalSeqData.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        originalSeqData_actionPerformed(e);
      }
    });
    associateViewsMenu.setText("Associate Nodes With");
    calcSettings.setText("Change Parameters");
    nuclSetting.setText("Nucleotide matrix");
    protSetting.setText("Protein matrix");
    nuclSetting.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        nuclSetting_actionPerfomed(arg0);
      }
    });
    protSetting.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        protSetting_actionPerfomed(arg0);
      }
    });
    jvVersionSetting.setText("Jalview PCA Calculation");
    jvVersionSetting.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        jvVersionSetting_actionPerfomed(arg0);
      }
    });
    calcSettings.add(jvVersionSetting);
    calcSettings.add(nuclSetting);
    calcSettings.add(protSetting);
    statusPanel.setLayout(statusPanelLayout);
    statusBar.setFont(new java.awt.Font("Verdana", 0, 12));
    // statusPanel.setBackground(Color.lightGray);
    // statusBar.setBackground(Color.lightGray);
    // statusPanel.add(statusBar, null);
    JPanel panelBar = new JPanel(new BorderLayout());
    panelBar.add(jPanel2, BorderLayout.NORTH);
    panelBar.add(statusPanel, BorderLayout.SOUTH);
    this.getContentPane().add(panelBar, BorderLayout.SOUTH);
    jPanel2.add(jLabel1, null);
    jPanel2.add(xCombobox, null);
    jPanel2.add(jLabel2, null);
    jPanel2.add(yCombobox, null);
    jPanel2.add(jLabel3, null);
    jPanel2.add(zCombobox, null);
    jPanel2.add(resetButton, null);
    jMenuBar1.add(fileMenu);
    jMenuBar1.add(viewMenu);
    jMenuBar1.add(calcSettings);
    fileMenu.add(saveMenu);
    fileMenu.add(outputValues);
    fileMenu.add(print);
    fileMenu.add(originalSeqData);
    fileMenu.add(outputPoints);
    fileMenu.add(outputProjPoints);
    saveMenu.add(eps);
    saveMenu.add(png);
    viewMenu.add(showLabels);
    viewMenu.add(bgcolour);
    viewMenu.add(associateViewsMenu);
  }

  protected void resetButton_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void protSetting_actionPerfomed(ActionEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  protected void nuclSetting_actionPerfomed(ActionEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  protected void outputPoints_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void outputProjPoints_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void xCombobox_actionPerformed(ActionEvent e)
  {
  }

  protected void yCombobox_actionPerformed(ActionEvent e)
  {
  }

  protected void zCombobox_actionPerformed(ActionEvent e)
  {
  }

  public void eps_actionPerformed(ActionEvent e)
  {

  }

  public void png_actionPerformed(ActionEvent e)
  {

  }

  public void outputValues_actionPerformed(ActionEvent e)
  {

  }

  public void print_actionPerformed(ActionEvent e)
  {

  }

  public void showLabels_actionPerformed(ActionEvent e)
  {

  }

  public void bgcolour_actionPerformed(ActionEvent e)
  {

  }

  public void originalSeqData_actionPerformed(ActionEvent e)
  {

  }

  public void viewMenu_menuSelected()
  {

  }

  protected void jvVersionSetting_actionPerfomed(ActionEvent arg0)
  {
    // TODO Auto-generated method stub

  }
}
