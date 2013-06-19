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

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class GDesktop extends JFrame
{
  protected static JMenu windowMenu = new JMenu();

  JMenuBar desktopMenubar = new JMenuBar();

  JMenu FileMenu = new JMenu();

  JMenu HelpMenu = new JMenu();

  protected JMenu VamsasMenu = new JMenu();

  protected JMenu VamsasStMenu = new JMenu();

  JMenuItem inputLocalFileMenuItem = new JMenuItem();

  JMenuItem inputURLMenuItem = new JMenuItem();

  JMenuItem inputTextboxMenuItem = new JMenuItem();

  JMenuItem quit = new JMenuItem();

  JMenuItem aboutMenuItem = new JMenuItem();

  JMenuItem documentationMenuItem = new JMenuItem();

  FlowLayout flowLayout1 = new FlowLayout();

  protected JMenu toolsMenu = new JMenu();

  JMenuItem preferences = new JMenuItem();

  JMenuItem saveState = new JMenuItem();

  JMenuItem loadState = new JMenuItem();

  JMenu inputMenu = new JMenu();

  protected JMenuItem vamsasStart = new JMenuItem();

  protected JMenuItem vamsasImport = new JMenuItem();

  protected JMenuItem vamsasSave = new JMenuItem();

  JMenuItem inputSequence = new JMenuItem();

  protected JMenuItem vamsasStop = new JMenuItem();

  JMenuItem closeAll = new JMenuItem();

  JMenuItem raiseRelated = new JMenuItem();

  JMenuItem minimizeAssociated = new JMenuItem();

  protected JCheckBoxMenuItem showMemusage = new JCheckBoxMenuItem();

  JMenuItem garbageCollect = new JMenuItem();

  protected JCheckBoxMenuItem showConsole = new JCheckBoxMenuItem();

  protected JCheckBoxMenuItem showNews = new JCheckBoxMenuItem();

  /**
   * Creates a new GDesktop object.
   */
  public GDesktop()
  {
    try
    {
      jbInit();
      this.setJMenuBar(desktopMenubar);
    } catch (Exception e)
    {
    }

    if (!new jalview.util.Platform().isAMac())
    {
      FileMenu.setMnemonic('F');
      inputLocalFileMenuItem.setMnemonic('L');
      VamsasMenu.setMnemonic('V');
      inputURLMenuItem.setMnemonic('U');
      inputTextboxMenuItem.setMnemonic('C');
      quit.setMnemonic('Q');
      saveState.setMnemonic('S');
      loadState.setMnemonic('L');
      inputMenu.setMnemonic('I');
    }
  }

  /**
   * DOCUMENT ME!
   * 
   * @throws Exception
   *           DOCUMENT ME!
   */
  private void jbInit() throws Exception
  {
    FileMenu.setText("File");
    HelpMenu.setText("Help");
    VamsasMenu.setText("Vamsas");
    VamsasMenu.setToolTipText("Share data with other vamsas applications.");
    VamsasStMenu.setText("Connect to");
    VamsasStMenu.setToolTipText("Join an existing vamsas session");
    inputLocalFileMenuItem.setText("from File");
    inputLocalFileMenuItem.setAccelerator(javax.swing.KeyStroke
            .getKeyStroke(java.awt.event.KeyEvent.VK_O, Toolkit
                    .getDefaultToolkit().getMenuShortcutKeyMask(), false));
    inputLocalFileMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
                @Override
              public void actionPerformed(ActionEvent e)
              {
                inputLocalFileMenuItem_actionPerformed(null);
              }
            });
    inputURLMenuItem.setText("from URL");
    inputURLMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        inputURLMenuItem_actionPerformed(null);
      }
    });
    inputTextboxMenuItem.setText("from Textbox");
    inputTextboxMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
                @Override
              public void actionPerformed(ActionEvent e)
              {
                inputTextboxMenuItem_actionPerformed(null);
              }
            });
    quit.setText("Quit");
    quit.addActionListener(new java.awt.event.ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        quit();
      }
    });
    aboutMenuItem.setText("About");
    aboutMenuItem.addActionListener(new java.awt.event.ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        aboutMenuItem_actionPerformed(e);
      }
    });
    documentationMenuItem.setText("Documentation");
    documentationMenuItem.setAccelerator(javax.swing.KeyStroke
            .getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0, false));
    documentationMenuItem
            .addActionListener(new java.awt.event.ActionListener()
            {
                @Override
              public void actionPerformed(ActionEvent e)
              {
                documentationMenuItem_actionPerformed(e);
              }
            });
    this.getContentPane().setLayout(flowLayout1);
    windowMenu.setText("Window");
    preferences.setText("Preferences...");
    preferences.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        preferences_actionPerformed(e);
      }
    });
    toolsMenu.setText("Tools");
    saveState.setText("Save Project");
    saveState.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        saveState_actionPerformed(e);
      }
    });
    loadState.setText("Load Project");
    loadState.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        loadState_actionPerformed(e);
      }
    });
    inputMenu.setText("Input Alignment");
    vamsasStart.setText("New Vamsas Session...");
    vamsasStart.setVisible(false);
    vamsasStart.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        vamsasStart_actionPerformed(e);
      }
    });
    vamsasImport.setText("Load Vamsas Session...");
    vamsasImport.setVisible(false);
    vamsasImport.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        vamsasImport_actionPerformed(e);
      }
    });
    vamsasSave.setText("Save Vamsas Session...");
    vamsasSave.setVisible(false);
    vamsasSave.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        vamsasSave_actionPerformed(e);
      }
    });
    inputSequence.setText("Fetch Sequence(s)...");
    inputSequence.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        inputSequence_actionPerformed(e);
      }
    });
    vamsasStop.setText("Stop Vamsas Session");
    vamsasStop.setVisible(false);
    vamsasStop.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        vamsasStop_actionPerformed(e);
      }
    });
    closeAll.setText("Close All");
    closeAll.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        closeAll_actionPerformed(e);
      }
    });
    raiseRelated.setText("Raise Associated Windows");
    raiseRelated.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        raiseRelated_actionPerformed(e);
      }
    });
    minimizeAssociated.setText("Minimize Associated Windows");
    minimizeAssociated.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        minimizeAssociated_actionPerformed(e);
      }
    });
    garbageCollect.setText("Collect Garbage");
    garbageCollect.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        garbageCollect_actionPerformed(e);
      }
    });
    showMemusage.setText("Show Memory Usage");
    showMemusage.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        showMemusage_actionPerformed(e);
      }
    });
    showConsole.setText("Show Java Console");
    showConsole.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        showConsole_actionPerformed(e);
      }
    });
    showNews.setText("Show Jalview News");
    showNews.addActionListener(new ActionListener()
    {
        @Override
      public void actionPerformed(ActionEvent e)
      {
        showNews_actionPerformed(e);
      }
    });
    desktopMenubar.add(FileMenu);
    desktopMenubar.add(toolsMenu);
    VamsasMenu.setVisible(false);
    desktopMenubar.add(VamsasMenu);
    desktopMenubar.add(HelpMenu);
    desktopMenubar.add(windowMenu);
    FileMenu.add(inputMenu);
    FileMenu.add(inputSequence);
    FileMenu.addSeparator();
    FileMenu.add(saveState);
    FileMenu.add(loadState);
    FileMenu.addSeparator();
    FileMenu.add(quit);
    HelpMenu.add(aboutMenuItem);
    HelpMenu.add(documentationMenuItem);
    VamsasMenu.add(VamsasStMenu);
    VamsasStMenu.setVisible(false);
    VamsasMenu.add(vamsasStart);
    VamsasMenu.add(vamsasImport);
    VamsasMenu.add(vamsasSave);
    VamsasMenu.add(vamsasStop);
    toolsMenu.add(preferences);
    toolsMenu.add(showMemusage);
    toolsMenu.add(showConsole);
    toolsMenu.add(showNews);
    toolsMenu.add(garbageCollect);
    inputMenu.add(inputLocalFileMenuItem);
    inputMenu.add(inputURLMenuItem);
    inputMenu.add(inputTextboxMenuItem);
    windowMenu.add(closeAll);
    windowMenu.add(raiseRelated);
    windowMenu.add(minimizeAssociated);
    windowMenu.addSeparator();
    // inputMenu.add(vamsasLoad);
  }

  protected void showConsole_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void showNews_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void showMemusage_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void garbageCollect_actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub

  }

  protected void vamsasStMenu_actionPerformed()
  {
  }

  public void vamsasSave_actionPerformed(ActionEvent e)
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void inputLocalFileMenuItem_actionPerformed(
          jalview.gui.AlignViewport av)
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void inputURLMenuItem_actionPerformed(
          jalview.gui.AlignViewport av)
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void inputTextboxMenuItem_actionPerformed(
          jalview.gui.AlignViewport av)
  {
  }

  /**
   * DOCUMENT ME!
   */
  protected void quit()
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void aboutMenuItem_actionPerformed(ActionEvent e)
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void documentationMenuItem_actionPerformed(ActionEvent e)
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void SaveState_actionPerformed(ActionEvent e)
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  protected void preferences_actionPerformed(ActionEvent e)
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void saveState_actionPerformed(ActionEvent e)
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void loadState_actionPerformed(ActionEvent e)
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void loadJalviewAlign_actionPerformed(ActionEvent e)
  {
  }

  public void vamsasStart_actionPerformed(ActionEvent e)
  {

  }

  public void inputSequence_actionPerformed(ActionEvent e)
  {

  }

  public void vamsasStop_actionPerformed(ActionEvent e)
  {

  }

  public void closeAll_actionPerformed(ActionEvent e)
  {

  }

  public void raiseRelated_actionPerformed(ActionEvent e)
  {

  }

  public void minimizeAssociated_actionPerformed(ActionEvent e)
  {

  }

  public void vamsasImport_actionPerformed(ActionEvent e)
  {
  }
}
