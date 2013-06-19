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

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GStructureViewer extends JInternalFrame
{
  public GStructureViewer()
  {
    try
    {
      jbInit();
    } catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception
  {
    this.setJMenuBar(menuBar);
    fileMenu.setText("File");
    savemenu.setActionCommand("Save Image");
    savemenu.setText("Save As");
    pdbFile.setText("PDB File");
    pdbFile.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        pdbFile_actionPerformed(actionEvent);
      }
    });
    png.setText("PNG");
    png.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        png_actionPerformed(actionEvent);
      }
    });
    eps.setText("EPS");
    eps.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        eps_actionPerformed(actionEvent);
      }
    });
    viewMapping.setText("View Mapping");
    viewMapping.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        viewMapping_actionPerformed(actionEvent);
      }
    });
    viewMenu.setText("View");
    chainMenu.setText("Show Chain");
    colourMenu.setText("Colours");
    backGround.setText("Background Colour...");
    backGround.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        backGround_actionPerformed(actionEvent);
      }
    });
    seqColour.setSelected(false);
    seqColour.setText("By Sequence");
    seqColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        seqColour_actionPerformed(actionEvent);
      }
    });
    chainColour.setText("By Chain");
    chainColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        chainColour_actionPerformed(actionEvent);
      }
    });
    chargeColour.setText("Charge & Cysteine");
    chargeColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        chargeColour_actionPerformed(actionEvent);
      }
    });
    zappoColour.setText("Zappo");
    zappoColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        zappoColour_actionPerformed(actionEvent);
      }
    });
    taylorColour.setText("Taylor");
    taylorColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        taylorColour_actionPerformed(actionEvent);
      }
    });
    hydroColour.setText("Hydro");
    hydroColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        hydroColour_actionPerformed(actionEvent);
      }
    });
    strandColour.setText("Strand");
    strandColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        strandColour_actionPerformed(actionEvent);
      }
    });
    helixColour.setText("Helix Propensity");
    helixColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        helixColour_actionPerformed(actionEvent);
      }
    });
    turnColour.setText("Turn Propensity");
    turnColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        turnColour_actionPerformed(actionEvent);
      }
    });
    buriedColour.setText("Buried Index");
    buriedColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        buriedColour_actionPerformed(actionEvent);
      }
    });
    purinePyrimidineColour.setText("Purine/Pyrimidine");
    purinePyrimidineColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        purinePyrimidineColour_actionPerformed(actionEvent);
      }
    });

    userColour.setText("User Defined ...");
    userColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        userColour_actionPerformed(actionEvent);
      }
    });
    jmolColour.setSelected(false);
    jmolColour.setText("Colour with Jmol");
    jmolColour.setToolTipText("Let Jmol manage structure colours.");
    jmolColour.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        jmolColour_actionPerformed(actionEvent);
      }
    });
    helpMenu.setText("Help");
    jmolHelp.setText("Jmol Help");
    jmolHelp.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        jmolHelp_actionPerformed(actionEvent);
      }
    });
    alignStructs.setText("Align structures");
    alignStructs.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
        alignStructs_actionPerformed(actionEvent);
      }
    });
    jmolActionMenu.setText("Jmol");
    menuBar.add(fileMenu);
    menuBar.add(viewMenu);
    menuBar.add(colourMenu);
    menuBar.add(jmolActionMenu);
    jmolActionMenu.setVisible(false);
    menuBar.add(helpMenu);
    fileMenu.add(savemenu);
    fileMenu.add(viewMapping);
    savemenu.add(pdbFile);
    savemenu.add(png);
    savemenu.add(eps);
    viewMenu.add(chainMenu);

    colourMenu.add(seqColour);
    colourMenu.add(chainColour);
    colourMenu.add(chargeColour);
    colourMenu.add(zappoColour);
    colourMenu.add(taylorColour);
    colourMenu.add(hydroColour);
    colourMenu.add(helixColour);
    colourMenu.add(strandColour);
    colourMenu.add(turnColour);
    colourMenu.add(buriedColour);
    colourMenu.add(purinePyrimidineColour);
    colourMenu.add(userColour);
    colourMenu.add(jmolColour);
    colourMenu.add(backGround);

    colourButtons.add(seqColour);
    colourButtons.add(chainColour);
    colourButtons.add(chargeColour);
    colourButtons.add(zappoColour);
    colourButtons.add(taylorColour);
    colourButtons.add(hydroColour);
    colourButtons.add(helixColour);
    colourButtons.add(strandColour);
    colourButtons.add(turnColour);
    colourButtons.add(buriedColour);
    colourButtons.add(userColour);
    colourButtons.add(jmolColour);

    helpMenu.add(jmolHelp);
    jmolActionMenu.add(alignStructs);
  }

  protected void jmolColour_actionPerformed(ActionEvent actionEvent)
  {
  }

  protected void alignStructs_actionPerformed(ActionEvent actionEvent)
  {
  }

  JMenuBar menuBar = new JMenuBar();

  JMenu fileMenu = new JMenu();

  JMenu savemenu = new JMenu();

  JMenuItem pdbFile = new JMenuItem();

  JMenuItem png = new JMenuItem();

  JMenuItem eps = new JMenuItem();

  JMenuItem viewMapping = new JMenuItem();

  protected JMenu viewMenu = new JMenu();

  protected JMenu chainMenu = new JMenu();

  JMenu jMenu1 = new JMenu();

  protected JMenu colourMenu = new JMenu();

  protected JMenu jmolActionMenu = new JMenu();

  protected JMenuItem alignStructs = new JMenuItem();

  JMenuItem backGround = new JMenuItem();

  protected JRadioButtonMenuItem seqColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem chainColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem chargeColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem zappoColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem taylorColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem hydroColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem strandColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem helixColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem turnColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem buriedColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem purinePyrimidineColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem userColour = new JRadioButtonMenuItem();

  protected JRadioButtonMenuItem jmolColour = new JRadioButtonMenuItem();

  protected ButtonGroup colourButtons = new ButtonGroup();

  JMenu helpMenu = new JMenu();

  JMenuItem jmolHelp = new JMenuItem();

  public void pdbFile_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void png_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void eps_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void viewMapping_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void seqColour_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void chainColour_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void chargeColour_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void zappoColour_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void taylorColour_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void hydroColour_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void helixColour_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void strandColour_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void turnColour_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void buriedColour_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void purinePyrimidineColour_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void userColour_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void backGround_actionPerformed(ActionEvent actionEvent)
  {

  }

  public void jmolHelp_actionPerformed(ActionEvent actionEvent)
  {

  }
}
