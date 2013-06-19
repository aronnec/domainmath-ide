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
import javax.swing.*;

public class EditNameDialog
{
  JTextField id, description;

  JButton ok = new JButton("Accept");

  JButton cancel = new JButton("Cancel");

  boolean accept = false;

  public String getName()
  {
    return id.getText();
  }

  public String getDescription()
  {
    if (description.getText().length() < 1)
    {
      return null;
    }
    else
    {
      return description.getText();
    }
  }

  public EditNameDialog(String name, String desc, String label1,
          String label2, String title, JComponent parent)
  {
    JLabel idlabel = new JLabel(label1);
    JLabel desclabel = new JLabel(label2);
    idlabel.setFont(new Font("Courier", Font.PLAIN, 12));
    desclabel.setFont(new Font("Courier", Font.PLAIN, 12));
    id = new JTextField(name, 40);
    description = new JTextField(desc, 40);
    JPanel panel = new JPanel(new BorderLayout());
    JPanel panel2 = new JPanel(new BorderLayout());
    panel2.add(idlabel, BorderLayout.WEST);
    panel2.add(id, BorderLayout.CENTER);
    panel.add(panel2, BorderLayout.NORTH);
    if (desc != null || label2 != null)
    {
      panel2 = new JPanel(new BorderLayout());
      panel2.add(desclabel, BorderLayout.WEST);
      panel2.add(description, BorderLayout.CENTER);
      panel.add(panel2, BorderLayout.SOUTH);
    }
    int reply = JOptionPane.showInternalConfirmDialog(parent, panel, title,
            JOptionPane.OK_CANCEL_OPTION);
    if (!parent.requestFocusInWindow())
    {
      System.err.println("Bad focus for dialog!");
    }
    if (reply == JOptionPane.OK_OPTION)
    {
      accept = true;
    }
  }
}
