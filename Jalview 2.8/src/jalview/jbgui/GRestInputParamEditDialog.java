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

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jalview.gui.JvSwingUtils;
import jalview.gui.OptsAndParamsPage;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

public class GRestInputParamEditDialog
{

  protected JPanel dpane;

  protected JPanel okcancel;

  protected JList typeList;

  protected JTextField tok;

  protected JPanel options;

  protected JPanel optionsPanel;

  public GRestInputParamEditDialog()
  {
    jbInit();
  }

  protected void jbInit()
  {
    dpane = new JPanel(new MigLayout("", "[][][fill]", "[][fill][]"));
    dpane.setPreferredSize(new Dimension(
            110 + 100 + OptsAndParamsPage.PARAM_WIDTH, 400));
    typeList = new JList();
    typeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    typeList.getSelectionModel().addListSelectionListener(
            new ListSelectionListener()
            {

              @Override
              public void valueChanged(ListSelectionEvent e)
              {
                type_SelectionChangedActionPerformed(e);
              };
            });

    tok = new JTextField();
    tok.addKeyListener(new KeyListener()
    {

      @Override
      public void keyTyped(KeyEvent e)
      {
      }

      @Override
      public void keyReleased(KeyEvent e)
      {
        tokChanged_actionPerformed();
      }

      @Override
      public void keyPressed(KeyEvent e)
      {

      }
    });
    options = new JPanel(new MigLayout("", "[grow 100,fill]", ""));
    optionsPanel = new JPanel(new MigLayout("", "[fill]", "[fill]"));
    JScrollPane optionView = new JScrollPane();
    optionView.setViewportView(options);
    JvSwingUtils.mgAddtoLayout(dpane, "Input Parameter name", new JLabel(
            "Name"), tok, "grow,spanx 3,wrap");
    JPanel paramsType = new JPanel(new MigLayout("", "[grow 100,fill]",
            "[grow 100,fill]"));
    paramsType.setBorder(new TitledBorder("Select input type"));
    JScrollPane jlistScroller = new JScrollPane();
    jlistScroller.setViewportView(typeList);
    paramsType.add(jlistScroller, "spanx 2,spany 2");
    dpane.add(paramsType);
    optionsPanel.setBorder(new TitledBorder("Set options for type"));
    optionsPanel.add(optionView);
    dpane.add(optionsPanel, "wrap");
    okcancel = new JPanel(new MigLayout("", "[center][center]", "[]"));
    dpane.add(okcancel, "spanx 3,wrap");

  }

  protected void tokChanged_actionPerformed()
  {

  }

  protected void type_SelectionChangedActionPerformed(ListSelectionEvent e)
  {
  }

}
