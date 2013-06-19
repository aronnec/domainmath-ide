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

import jalview.gui.JvSwingUtils;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * DOCUMENT ME!
 * 
 * @author $author$
 * @version $Revision$
 */
public class GCutAndPasteHtmlTransfer extends JInternalFrame
{
  protected JEditorPane textarea = new JEditorPane("text/html", "");

  protected JScrollPane scrollPane = new JScrollPane();

  BorderLayout borderLayout1 = new BorderLayout();

  JMenuBar editMenubar = new JMenuBar();

  JMenu editMenu = new JMenu();

  JMenuItem copyItem = new JMenuItem();

  protected JCheckBoxMenuItem displaySource = new JCheckBoxMenuItem();

  BorderLayout borderLayout2 = new BorderLayout();

  protected JPanel inputButtonPanel = new JPanel();

  protected JButton ok = new JButton();

  JButton cancel = new JButton();

  JMenuItem close = new JMenuItem();

  JMenuItem selectAll = new JMenuItem();

  JMenu jMenu1 = new JMenu();

  JMenuItem save = new JMenuItem();

  /**
   * Creates a new GCutAndPasteTransfer object.
   */
  public GCutAndPasteHtmlTransfer()
  {
    try
    {
      setJMenuBar(editMenubar);
      jbInit();
    } catch (Exception e)
    {
      e.printStackTrace();
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
    scrollPane.setBorder(null);
    ok.setFont(JvSwingUtils.getLabelFont());
    ok.setText("New Window");
    ok.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ok_actionPerformed(e);
      }
    });
    cancel.setText("Close");
    cancel.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        cancel_actionPerformed(e);
      }
    });
    textarea.setBorder(null);
    close.setText("Close");
    close.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        cancel_actionPerformed(e);
      }
    });
    close.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_W, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    selectAll.setText("Select All");
    selectAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_A, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    selectAll.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        selectAll_actionPerformed(e);
      }
    });
    jMenu1.setText("File");
    save.setText("Save");
    save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_S, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));
    save.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        save_actionPerformed(e);
      }
    });
    copyItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_C, Toolkit.getDefaultToolkit()
                    .getMenuShortcutKeyMask(), false));

    editMenubar.add(jMenu1);
    editMenubar.add(editMenu);
    textarea.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
    textarea.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mousePressed(MouseEvent e)
      {
        textarea_mousePressed(e);
      }
    });
    editMenu.setText("Edit");
    copyItem.setText("Copy");
    copyItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        copyItem_actionPerformed(e);
      }
    });
    displaySource.setText("Show HTML Source");
    displaySource
            .setToolTipText("Select this if you want to copy raw html");
    displaySource.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent arg0)
      {
        toggleHtml_actionPerformed(arg0);
      }
    });
    editMenu.add(displaySource);
    this.getContentPane().setLayout(borderLayout2);
    scrollPane.setBorder(null);
    scrollPane.getViewport().add(textarea, null);
    editMenu.add(selectAll);
    editMenu.add(copyItem);
    this.getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);
    inputButtonPanel.add(ok);
    inputButtonPanel.add(cancel);
    jMenu1.add(save);
    jMenu1.add(close);
  }

  protected void toggleHtml_actionPerformed(ActionEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void textarea_mousePressed(MouseEvent e)
  {

  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void copyItem_actionPerformed(ActionEvent e)
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void ok_actionPerformed(ActionEvent e)
  {
  }

  /**
   * DOCUMENT ME!
   * 
   * @param e
   *          DOCUMENT ME!
   */
  public void cancel_actionPerformed(ActionEvent e)
  {
  }

  public void selectAll_actionPerformed(ActionEvent e)
  {
    textarea.selectAll();
  }

  public void save_actionPerformed(ActionEvent e)
  {

  }
}
