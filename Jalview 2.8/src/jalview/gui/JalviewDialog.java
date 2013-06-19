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

import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * Boilerplate dialog class. Implements basic functionality necessary for model
 * blocking/non-blocking dialogs with an OK and Cancel button ready to add to
 * the content pane.
 * 
 * @author jimp
 * 
 */
public abstract class JalviewDialog extends JPanel
{

  protected JDialog frame;

  protected JButton ok = new JButton();

  protected JButton cancel = new JButton();

  boolean block = false;

  public void waitForInput()
  {
    if (!block)
    {
      new Thread(new Runnable()
      {

        public void run()
        {
          frame.setVisible(true);
        }

      }).start();
    }
    else
    {
      frame.setVisible(true);
    }
  }

  protected void initDialogFrame(Container content, boolean modal,
          boolean block, String title, int width, int height)
  {

    frame = new JDialog(Desktop.instance, modal);
    frame.setTitle(title);
    if (Desktop.instance != null)
    {
      Rectangle deskr = Desktop.instance.getBounds();
      frame.setBounds(new Rectangle((int) (deskr.getCenterX() - width / 2),
              (int) (deskr.getCenterY() - height / 2), width, height));
    }
    else
    {
      frame.setSize(width, height);
    }
    frame.setContentPane(content);
    this.block = block;

    ok.setOpaque(false);
    ok.setText("OK");
    ok.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        okPressed();
        closeDialog();
      }
    });
    cancel.setOpaque(false);
    cancel.setText("Cancel");
    cancel.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        cancelPressed();
        closeDialog();
      }
    });
    frame.addWindowListener(new WindowListener()
    {

      @Override
      public void windowOpened(WindowEvent e)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void windowIconified(WindowEvent e)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void windowDeiconified(WindowEvent e)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void windowDeactivated(WindowEvent e)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void windowClosing(WindowEvent e)
      {
        // user has cancelled the dialog
        closeDialog();
      }

      @Override
      public void windowClosed(WindowEvent e)
      {
      }

      @Override
      public void windowActivated(WindowEvent e)
      {
        // TODO Auto-generated method stub

      }
    });
  }

  /**
   * clean up and raise the 'dialog closed' event by calling raiseClosed
   */
  protected void closeDialog()
  {
    try
    {
      frame.dispose();
      raiseClosed();
    } catch (Exception ex)
    {
    }
  }

  protected abstract void raiseClosed();

  protected abstract void okPressed();

  protected abstract void cancelPressed();

}
