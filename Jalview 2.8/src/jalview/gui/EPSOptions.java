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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EPSOptions extends JPanel
{
  JDialog dialog;

  public boolean cancelled = false;

  String value;

  public EPSOptions()
  {
    try
    {
      jbInit();
    } catch (Exception ex)
    {
      ex.printStackTrace();
    }

    ButtonGroup bg = new ButtonGroup();
    bg.add(lineart);
    bg.add(text);

    JOptionPane pane = new JOptionPane(null, JOptionPane.DEFAULT_OPTION,
            JOptionPane.DEFAULT_OPTION, null, new Object[]
            { this });

    dialog = pane.createDialog(Desktop.desktop, "EPS Rendering options");
    dialog.setVisible(true);

  }

  private void jbInit() throws Exception
  {
    lineart.setFont(JvSwingUtils.getLabelFont());
    lineart.setText("Lineart");
    text.setFont(JvSwingUtils.getLabelFont());
    text.setText("Text");
    text.setSelected(true);
    askAgain.setFont(JvSwingUtils.getLabelFont());
    askAgain.setText("Don\'t ask me again");
    ok.setText("OK");
    ok.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ok_actionPerformed(e);
      }
    });
    cancel.setText("Cancel");
    cancel.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        cancel_actionPerformed(e);
      }
    });
    jLabel1.setFont(JvSwingUtils.getLabelFont());
    jLabel1.setText("Select EPS character rendering style");
    this.setLayout(borderLayout1);
    jPanel3.setBorder(BorderFactory.createEtchedBorder());
    jPanel2.add(text);
    jPanel2.add(lineart);
    jPanel2.add(askAgain);
    jPanel1.add(ok);
    jPanel1.add(cancel);
    jPanel3.add(jLabel1);
    jPanel3.add(jPanel2);
    this.add(jPanel3, java.awt.BorderLayout.CENTER);
    this.add(jPanel1, java.awt.BorderLayout.SOUTH);
  }

  JRadioButton lineart = new JRadioButton();

  JRadioButton text = new JRadioButton();

  JCheckBox askAgain = new JCheckBox();

  JButton ok = new JButton();

  JButton cancel = new JButton();

  JPanel jPanel1 = new JPanel();

  JLabel jLabel1 = new JLabel();

  JPanel jPanel2 = new JPanel();

  JPanel jPanel3 = new JPanel();

  BorderLayout borderLayout1 = new BorderLayout();

  public void ok_actionPerformed(ActionEvent e)
  {
    if (lineart.isSelected())
    {
      value = "Lineart";
    }
    else
    {
      value = "Text";
    }

    if (!askAgain.isSelected())
    {
      jalview.bin.Cache.applicationProperties.remove("EPS_RENDERING");
    }
    else
    {
      jalview.bin.Cache.setProperty("EPS_RENDERING", value);
    }

    dialog.setVisible(false);
  }

  public void cancel_actionPerformed(ActionEvent e)
  {
    cancelled = true;
    dialog.setVisible(false);
  }

  public String getValue()
  {
    return value;
  }
}
