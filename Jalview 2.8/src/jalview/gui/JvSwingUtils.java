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

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * useful functions for building Swing GUIs
 * 
 * @author JimP
 * 
 */
public final class JvSwingUtils
{
  /**
   * wrap a bare html safe string to around 60 characters per line using a
   * <table width=350>
   * <tr>
   * <td></td> field
   * 
   * @param ttext
   * @return
   */
  public static String wrapTooltip(String ttext)
  {
    if (ttext.length() < 60)
    {
      return ttext;
    }
    else
    {
      return "<table width=350 border=0><tr><td>" + ttext
              + "</td></tr></table>";
    }
  }

  public static JButton makeButton(String label, String tooltip,
          ActionListener action)
  {
    JButton button = new JButton();
    button.setText(label);
    // TODO: get the base font metrics for the Jalview gui from somewhere
    button.setFont(new java.awt.Font("Verdana", Font.PLAIN, 10));
    button.setForeground(Color.black);
    button.setHorizontalAlignment(SwingConstants.CENTER);
    button.setToolTipText(tooltip);
    button.addActionListener(action);
    return button;
  }

  /**
   * find or add a submenu with the given title in the given menu
   * 
   * @param menu
   * @param submenu
   * @return the new or existing submenu
   */
  public static JMenu findOrCreateMenu(JMenu menu, String submenu)
  {
    JMenu submenuinstance = null;
    for (int i = 0, iSize = menu.getMenuComponentCount(); i < iSize; i++)
    {
      if (menu.getMenuComponent(i) instanceof JMenu
              && ((JMenu) menu.getMenuComponent(i)).getText().equals(
                      submenu))
      {
        submenuinstance = (JMenu) menu.getMenuComponent(i);
      }
    }
    if (submenuinstance == null)
    {
      submenuinstance = new JMenu(submenu);
      menu.add(submenuinstance);
    }
    return submenuinstance;

  }

  /**
   * 
   * @param panel
   * @param tooltip
   * @param label
   * @param valBox
   * @return the GUI element created that was added to the layout so it's
   *         attributes can be changed.
   */
  public static JPanel addtoLayout(JPanel panel, String tooltip,
          JComponent label, JComponent valBox)
  {
    JPanel laypanel = new JPanel(), labPanel = new JPanel(), valPanel = new JPanel();
    // laypanel.setSize(panel.getPreferredSize());
    // laypanel.setLayout(null);
    labPanel.setBounds(new Rectangle(7, 7, 158, 23));
    valPanel.setBounds(new Rectangle(172, 7, 270, 23));
    // labPanel.setLayout(new GridLayout(1,1));
    // valPanel.setLayout(new GridLayout(1,1));
    labPanel.add(label);
    valPanel.add(valBox);
    laypanel.add(labPanel);
    laypanel.add(valPanel);
    valPanel.setToolTipText(tooltip);
    labPanel.setToolTipText(tooltip);
    valBox.setToolTipText(tooltip);
    panel.add(laypanel);
    panel.validate();
    return laypanel;
  }

  public static void mgAddtoLayout(JPanel cpanel, String tooltip,
          JLabel jLabel, JComponent name)
  {
    mgAddtoLayout(cpanel, tooltip, jLabel, name, null);
  }

  public static void mgAddtoLayout(JPanel cpanel, String tooltip,
          JLabel jLabel, JComponent name, String params)
  {
    cpanel.add(jLabel);
    if (params == null)
    {
      cpanel.add(name);
    }
    else
    {
      cpanel.add(name, params);
    }
    name.setToolTipText(tooltip);
    jLabel.setToolTipText(tooltip);
  }

  /**
   * standard font for labels and check boxes in dialog boxes
   * 
   * @return
   */

  public static Font getLabelFont()
  {
    return getLabelFont(false, false);
  }

  public static Font getLabelFont(boolean bold, boolean italic)
  {
    return new java.awt.Font("Verdana", (!bold && !italic) ? Font.PLAIN
            : (bold ? Font.BOLD : 0) + (italic ? Font.ITALIC : 0), 11);
  }

  /**
   * standard font for editable text areas
   * 
   * @return
   */
  public static Font getTextAreaFont()
  {
    return getLabelFont(false, false);
  }

  /**
   * clean up a swing menu. Removes any empty submenus without selection
   * listeners.
   * 
   * @param webService
   */
  public static void cleanMenu(JMenu webService)
  {
    for (int i = 0; i < webService.getItemCount();)
    {
      JMenuItem item = webService.getItem(i);
      if (item instanceof JMenu && ((JMenu) item).getItemCount() == 0)
      {
        webService.remove(i);
      }
      else
      {
        i++;
      }
    }
  }

}
