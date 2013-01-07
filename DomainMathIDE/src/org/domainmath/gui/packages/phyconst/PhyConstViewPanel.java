/*
 * Copyright (C) 2012 Vinu K.N
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package org.domainmath.gui.packages.phyconst;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class PhyConstViewPanel extends JPanel {
  
    JTable table;
    private final PhyConstTableModel model;
    private JToolBar phyConstToolBar;
    private JButton refreshButton;
   java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/pathsview/resources/pathsview_en");
    private final ToolBarActionListener toolBarActionListener;
    public PhyConstViewPanel(String directory) {
        super(new BorderLayout());
        
        table = new JTable();
        
       
        model = new PhyConstTableModel(directory);
        
        model.init();
        table.setModel(model);
       
       // table.setAutoCreateColumnsFromModel(true);
       // table.setAutoCreateRowSorter(true);
        toolBarActionListener = new ToolBarActionListener();
        table.getTableHeader().setReorderingAllowed(false);
      
       
        table.setRowHeight(20);
        
         JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        setUpToolBar();
        add(scrollPane,BorderLayout.CENTER);
        
      
    }
    private void setUpToolBar() {
        phyConstToolBar = new JToolBar();
        phyConstToolBar.setFloatable(false);
        phyConstToolBar.setRollover(true);
        refreshButton = new JButton(bundle.getString("refreshItem.tooltip"));
        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/view-refresh.png"))); 
        refreshButton.setToolTipText(bundle.getString("refreshItem.tooltip"));
        refreshButton.addActionListener(toolBarActionListener);
        phyConstToolBar.add(refreshButton);
        add(phyConstToolBar,BorderLayout.PAGE_START);
        
    }
     private void reload() {
       model.refresh();
       table.repaint();
       }
  public PhyConstTableModel getModel() {
        return model;
    }
    private  class ToolBarActionListener implements ActionListener {
       
    @Override
       public void actionPerformed(ActionEvent e) {
             JButton source = (JButton)(e.getSource());
            
             
             if(refreshButton.equals(source)) {
                reload();
            }
    }
  
 }
     
}
