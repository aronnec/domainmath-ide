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
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.StringWriter;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent.EventType;

import jalview.io.*;
import jalview.jbgui.*;

/**
 * Cut'n'paste files into the desktop See JAL-1105
 * 
 * @author $author$
 * @version $Revision$
 */
public class CutAndPasteHtmlTransfer extends GCutAndPasteHtmlTransfer
{

  AlignViewport viewport;

  public CutAndPasteHtmlTransfer()
  {
    super();
    displaySource.setSelected(false);
    textarea.addKeyListener(new KeyListener()
    {

      @Override
      public void keyTyped(KeyEvent arg0)
      {
        // if (arg0.isControlDown() && arg0.getKeyCode()==KeyEvent.VK_C)
        // {
        // copyItem_actionPerformed(null);
        // }
        arg0.consume();
      }

      @Override
      public void keyReleased(KeyEvent arg0)
      {
        // TODO Auto-generated method stub

      }

      @Override
      public void keyPressed(KeyEvent arg0)
      {
        // TODO Auto-generated method stub

      }
    });
    textarea.setEditable(false);
    textarea.addHyperlinkListener(new HyperlinkListener()
    {

      @Override
      public void hyperlinkUpdate(HyperlinkEvent e)
      {
        if (e.getEventType().equals(EventType.ACTIVATED))
        {
          Desktop.showUrl(e.getURL().toExternalForm());
        }
      }
    });
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        textarea.requestFocus();
      }
    });

  }

  /**
   * DOCUMENT ME!
   */
  public void setForInput(AlignViewport viewport)
  {
    this.viewport = viewport;
    if (viewport != null)
    {
      ok.setText("Add");
    }

    getContentPane().add(inputButtonPanel, java.awt.BorderLayout.SOUTH);
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public String getText()
  {
    return textarea.getText();
  }

  /**
   * Set contents of HTML Display pane
   * 
   * @param text
   *          HTML text
   */
  public void setText(String text)
  {
    textarea.setText(text);
  }

  public void save_actionPerformed(ActionEvent e)
  {
    JalviewFileChooser chooser = new JalviewFileChooser(
            jalview.bin.Cache.getProperty("LAST_DIRECTORY"));

    chooser.setAcceptAllFileFilterUsed(false);
    chooser.setFileView(new JalviewFileView());
    chooser.setDialogTitle("Save Text to File");
    chooser.setToolTipText("Save");

    int value = chooser.showSaveDialog(this);

    if (value == JalviewFileChooser.APPROVE_OPTION)
    {
      try
      {
        java.io.PrintWriter out = new java.io.PrintWriter(
                new java.io.FileWriter(chooser.getSelectedFile()));

        out.print(getText());
        out.close();
      } catch (Exception ex)
      {
        ex.printStackTrace();
      }

    }
  }

  public void toggleHtml_actionPerformed(ActionEvent e)
  {
    String txt = textarea.getText();
    textarea.setContentType(displaySource.isSelected() ? "text/text"
            : "text/html");
    textarea.setText(txt);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void copyItem_actionPerformed(ActionEvent e)
  {
    Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
    StringWriter sw = new StringWriter();
    try
    {
      textarea.getEditorKit().write(sw, textarea.getDocument(),
              textarea.getSelectionStart(),
              textarea.getSelectionEnd() - textarea.getSelectionStart());
    } catch (Exception x)
    {
    }
    ;
    StringSelection ssel = new StringSelection(sw.getBuffer().toString());
    c.setContents(ssel, ssel);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void cancel_actionPerformed(ActionEvent e)
  {
    try
    {
      this.setClosed(true);
    } catch (Exception ex)
    {
    }
  }

  public void textarea_mousePressed(MouseEvent e)
  {
    if (SwingUtilities.isRightMouseButton(e))
    {
      JPopupMenu popup = new JPopupMenu("Edit");
      JMenuItem item = new JMenuItem("Copy");
      item.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          copyItem_actionPerformed(e);
        }
      });
      popup.add(item);
      popup.show(this, e.getX() + 10, e.getY() + textarea.getY() + 40);

    }
  }

}
