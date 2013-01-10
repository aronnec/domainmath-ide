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

package org.domainmath.gui.dataview;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import org.domainmath.gui.MainFrame;

public class DataViewPanel extends JPanel {
  
    JTable table;
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/domainmath/gui/dataview/resources/dataview_en"); 
    private final String directory;
   
 
    public List data =Collections.synchronizedList(new ArrayList());
    private JToolBar datViewToolBar;
    private JButton printButton;
    private JButton refreshButton;
    private final ToolBarActionListener toolBarActionListener;
    private final DataViewFileTableModel model;
    private final TableMouseListener tableMouseListener;

    
    public DataViewPanel(String directory) {
        super(new BorderLayout());
        tableMouseListener = new TableMouseListener();
        this.directory = directory;
        toolBarActionListener = new ToolBarActionListener();
        
        model = new DataViewFileTableModel(directory);
        table = new JTable();
        table.setModel(model);
     
        
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        table.addMouseListener(tableMouseListener);
        table.setRowHeight(20);
        final JScrollPane scrollPane = new JScrollPane(table);
        table.setDragEnabled(true);
        setUpToolBar();
        add(scrollPane,BorderLayout.CENTER);
         
      
    }
 private void setUpToolBar() {
        datViewToolBar = new JToolBar();
        datViewToolBar.setFloatable(false);
        datViewToolBar.setRollover(true);
        
        
        printButton = new JButton("");
        
        refreshButton = new JButton("");
        
      
        printButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/document-print.png"))); 
       
        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/domainmath/gui/icons/view-refresh.png"))); 
        
        
        printButton.setToolTipText(bundle.getString("printItem.tooltip"));
        
        refreshButton.setToolTipText(bundle.getString("refreshItem.tooltip"));
        
       
        printButton.addActionListener(toolBarActionListener);
        refreshButton.addActionListener(toolBarActionListener);
        
        
        datViewToolBar.add(printButton);
       
       
        datViewToolBar.add(refreshButton);
        add(datViewToolBar,BorderLayout.PAGE_START);
        
    }
  

   
        public void reload() {
          new DataTask().execute();
       }
  
     private  class ToolBarActionListener implements ActionListener {
        private Path path;

       

        @Override
       public void actionPerformed(ActionEvent e) {
             JButton source = (JButton)(e.getSource());
            
             if(printButton.equals(source) ) {
                 printTable();
             }else if(refreshButton.equals(source)) {
                reload();
            }
    }

        private void printTable() {
            try {
                if (! table.print()) {
                System.err.println("User cancelled printing");
            }
            } catch (java.awt.print.PrinterException e) {
                    System.err.format("Cannot print %s%n", e.getMessage());
            }


        }
  
 }
    
    private class DataTask extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() {
            int delay = 100; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
                @Override
            public void actionPerformed(ActionEvent evt) {
               
                model.refresh();
                model.fireTableStructureChanged();
                model.fireTableDataChanged();
       
                table.repaint();
                
            }
        };
         javax.swing.Timer t= new javax.swing.Timer(delay, taskPerformer);
        t.setRepeats(false);   
       t.start();
            return null;
            
        }

        
        @Override
       protected void done() {
           try {
                 int delay = 100; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
                @Override
            public void actionPerformed(ActionEvent evt) {
               
                model.refresh();
                model.fireTableStructureChanged();
                model.fireTableDataChanged();
       
                table.repaint();
                
            }
        };
        javax.swing.Timer t= new javax.swing.Timer(delay, taskPerformer);
        t.setRepeats(false);   
       t.start();     
           } catch (Exception ignore) {
               
           }
       }

      
    }
    
     private class TableMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2) {
                int r=table.getSelectedRow();
                int c=table.getSelectedColumn();
                  String variable = table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
                  if(!variable.contains(".")){
                     if(r>=0 && c>=0) {
                        File f = new File(directory);
                        String f_name = f.getName();
                        String name = f_name.substring(0, f_name.indexOf(".dat"));

                      MainFrame.octavePanel.evaluate("DomainMath_OctaveDataView('"+MainFrame.log_root+name+"."+variable+".dat',"+name+"."+variable+");");
                                DataViewFrame main =new DataViewFrame(MainFrame.log_root+name+"."+variable+".dat");
                                
                    } 
                  }
                    
               
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
           
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
           
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
     
}
